package com.example.chat.serverside;

import com.example.chat.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

public class Server {
    private static final Hashtable<String, ClientHandler> clients = new Hashtable<>();
    private final ArrayList<Room> rooms = new ArrayList<>();
    private final ServerSocket server;

    public Server() throws IOException {
        server = new ServerSocket(Settings.PORT);
        System.out.println("Server started");
    }

    public void run() {
        try {
            while (!server.isClosed()) {
                Socket client = server.accept();
                ClientHandler clientHandler = new ClientHandler(client);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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

    public static String[] getUserNicknames() {
        return (String[]) clients.keySet().toArray();
    }

    public static void addUser(String nickname, ClientHandler handler) {
        clients.put(nickname, handler);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }
}
