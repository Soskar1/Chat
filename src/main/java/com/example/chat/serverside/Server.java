package com.example.chat.serverside;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private final ArrayList<ClientHandler> connections = new ArrayList<>();
    private final ServerSocket server;

    public Server() throws IOException {
        server = new ServerSocket(7777);
        System.out.println("Server started");
    }

    public void run() {
        try {
            while (!server.isClosed()) {
                Socket client = server.accept();
                System.out.println("A new client has connected!");

//                ClientHandler clientConnection = new ClientHandler(client);
//                connections.add(clientConnection);
//
//                Thread thread = new Thread(clientConnection);
//                thread.start();
            }
        } catch (IOException e) {
            close();
        }
    }

    public void close() {
        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }
}
