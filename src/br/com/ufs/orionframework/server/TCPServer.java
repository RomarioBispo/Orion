package br.com.ufs.orionframework.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;


/**
 * TCPServer implements a simple TCP client to receive notifications from Orion (asynchronous).
 * @author Mariana Martins, Felipe Matheus and Crunchify.com
 */
public class TCPServer implements Callable<String> {

    private ServerSocket server;
    private int port;
    private String ip;

    /**
     * Create a new connection given a IP adress + Port
     *
     * @param  port  An given port from service (Orion listen in 1026).
     * @param ip An given ip address.
     */
    public TCPServer(int port, String ip) throws Exception {
        this.ip = ip;
        this.port = port;
        this.server = new ServerSocket(this.port, 1, InetAddress.getByName(this.ip)); // a porta ?
    }

    public ServerSocket getServer() {
        return server;
    }

    public void setServer(ServerSocket server) {
        this.server = server;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Listen a connection (blocking call). Waits until listen a connection, when the connection is available
     * receive the data from client.
     *
     * @return last line (discarding headers etc) of data contained on message from client side.
     */
    public String listen() throws Exception {
        String data = null;
        String lastLine = null;
        Socket client = this.server.accept();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        while ((data = in.readLine()) != null) {
            lastLine = data;
        }
        return lastLine;
    }

    /**
     * Listen a connection (unblocking call).
     * when the connection is available receive the data from client.
     *
     * @return message from client side.
     */
   public String asyncListen() throws IOException {

        String result = "";

        // Selector: multiplexor of SelectableChannel objects
        Selector selector = Selector.open(); // selector is open here

        // ServerSocketChannel: selectable channel for stream-oriented listening sockets
        ServerSocketChannel crunchifySocket = ServerSocketChannel.open();
        InetSocketAddress crunchifyAddr = new InetSocketAddress(this.ip, this.port);

        // Binds the channel's socket to a local address and configures the socket to listen for connections
        crunchifySocket.bind(crunchifyAddr);

        // Adjusts this channel's blocking mode.
        crunchifySocket.configureBlocking(false);

        int ops = crunchifySocket.validOps();
        SelectionKey selectKy = crunchifySocket.register(selector, ops, null);

        // Infinite loop..
        // Keep server running
        while (true) {

            log("i'm a server and i'm waiting for new connection and buffer select...");
            // Selects a set of keys whose corresponding channels are ready for I/O operations
            selector.select();

            // token representing the registration of a SelectableChannel with a Selector
            Set<SelectionKey> crunchifyKeys = selector.selectedKeys();
            Iterator<SelectionKey> crunchifyIterator = crunchifyKeys.iterator();

            while (crunchifyIterator.hasNext()) {
                SelectionKey myKey = crunchifyIterator.next();

                // Tests whether this key's channel is ready to accept a new socket connection
                if (myKey.isAcceptable()) {
                    SocketChannel crunchifyClient = crunchifySocket.accept();

                    // Adjusts this channel's blocking mode to false
                    crunchifyClient.configureBlocking(false);

                    // Operation-set bit for read operations
                    crunchifyClient.register(selector, SelectionKey.OP_READ);
                    log("Connection Accepted: " + crunchifyClient.getLocalAddress() + "\n");

                    // Tests whether this key's channel is ready for reading
                } else if (myKey.isReadable()) {

                    SocketChannel crunchifyClient = (SocketChannel) myKey.channel();
                    ByteBuffer crunchifyBuffer = ByteBuffer.allocate(256);
                    crunchifyClient.read(crunchifyBuffer);
                    result = new String(crunchifyBuffer.array()).trim();

                    log("Message received: " + result);
                }
                crunchifyIterator.remove();
            }
            return result;
        }
    }

    private static void log(String str) {
        System.out.println(str);
    }

    @Override
    public String call() throws IOException {
        String data = null;
        String lastLine = null;

        Socket client = this.server.accept();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        while ((data = in.readLine()) != null) {
            lastLine = data;
        }
        return lastLine;

    }
}