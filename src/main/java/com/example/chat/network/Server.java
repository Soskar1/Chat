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
    //private static final Hashtable<Room, StringBuilder> rooms = new Hashtable<>();
    private static final ArrayList<StringBuilder> rooms = new ArrayList<>();
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
        //rooms.put(room, new StringBuilder());
        room.setId(rooms.size());
        rooms.add(new StringBuilder());
    }

    public static String getRoomContent(Room room) {
        return rooms.get(room.getId()).toString();
        //return rooms.get(room).toString();
    }

    public synchronized static void addContentToRoom(Room room, String content) {
        rooms.get(room.getId()).append(content);
        //rooms.get(room).append(content);
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
