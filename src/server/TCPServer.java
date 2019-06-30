package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * TCPServer implements a simple TCP client to receive notifications from Orion (asynchronous).
 * @author Romario Bispo
 * @version %I%, %G%
 * @since 1.0
 */
public class TCPServer {

    private ServerSocket server;

    /**
     * Create a new connection given a IP adress + Port
     *
     * @param  port  An given port from service (Orion listen in 1026).
     * @param ip An given ip address.
     */
    public TCPServer(int port, String ip) throws Exception {
        this.server = new ServerSocket(port, 1, InetAddress.getByName(ip));
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
}