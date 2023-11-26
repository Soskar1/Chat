package com.example.chat.serverside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class Server {
    private final Hashtable<String, ClientHandler> clients = new Hashtable<>();
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ServerSocket server;

    public Server() throws IOException {
        server = new ServerSocket(7777);
        System.out.println("Server started");
    }

    public void run() {
        try {
            while (!server.isClosed()) {
                Socket client = server.accept();
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String nickname = reader.readLine();

                System.out.println(nickname + " has been connected!");

                ClientHandler clientConnection = new ClientHandler(client);
                clients.put(nickname, clientConnection);

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
