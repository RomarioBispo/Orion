package br.com.ufs.examples.room.context.accumulator;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Accumulator {
    private ServerSocket server;

    public Accumulator(int port, String ip) throws Exception {
        this.server = new ServerSocket(port, 1, InetAddress.getByName(ip));
    }

    public String listen() throws Exception {
        String data = null;
        String lastLine = null;
        Socket client = this.server.accept();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(client.getInputStream()));

        while ( (data = in.readLine()) != null ) {
            lastLine = data;
        }
        return lastLine;
    }
}
