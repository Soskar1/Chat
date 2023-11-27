package com.example.chat.network;

import com.example.chat.Settings;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;

public class Server {
    private static final Hashtable<String, ClientHandler> clients = new Hashtable<>();
    private static final ArrayList<Room> rooms = new ArrayList<>();
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
        System.out.println("Closing the server");

        try {
            if (server != null) {
                server.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static void addRoom(Room room) {
        rooms.add(room);
    }

    public static Set<String> getUserNicknames() {
        return clients.keySet();
    }

    public synchronized static void addUser(String nickname, ClientHandler handler) {
        clients.put(nickname, handler);
    }

    public static ClientHandler getClient(String nickname) {
        return clients.get(nickname);
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }
}