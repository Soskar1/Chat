package com.example.chat.network;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Database {
    private static final ArrayList<String> users = new ArrayList<>();
    private static final ArrayList<Room> rooms = new ArrayList<>();
    private static final String resources = "src/main/resources";
    private static final String roomDirectory = "/rooms";
    private static final String usersFileName = "/users";
    private static final String extension = ".txt";

    public static void load() throws IOException {
        loadUsers();
        loadRooms();
    }

    private static void loadUsers() throws IOException {
        File file = new File(resources + usersFileName + extension);
        if (!file.isFile()) {
            file.createNewFile();
            return;
        }

        InputStream inputStream = Database.class.getResourceAsStream(usersFileName + extension);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        String line = br.readLine();
        while (line != null) {
            users.add(line);
            line = br.readLine();
        }
        inputStream.close();
    }

    private static void loadRooms() throws IOException {
        String directoryPath = resources + roomDirectory;
        File dir = new File(directoryPath);
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }

        for (File file : files) {
            InputStream inputStream = Database.class.getResourceAsStream(roomDirectory + '/' + file.getName());
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

            String line = br.readLine(); // first line - users
            ArrayList<String> users = new ArrayList<>(Arrays.asList(line.split(" ")));

            Room room = new Room(file.getName(), users);
            room.setId(rooms.size());

            line = br.readLine();
            while (line != null) {
                room.appendContent(line);
                line = br.readLine();
            }

            rooms.add(room);
        }
    }

    public static synchronized void addUser(String userName) throws IOException {
        if (users.contains(userName))
            return;

        users.add(userName);

        FileWriter fileWriter = new FileWriter(resources + usersFileName + extension, true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write(userName);
        bufferedWriter.newLine();
        bufferedWriter.close();
    }

    public static synchronized void addRoom(Room room) {
        room.setId(rooms.size());
        rooms.add(room);
        saveRoomMembers(room);
    }

    private static synchronized void saveRoomMembers(Room room) {
        String filePath = resources + roomDirectory + '/' + room.getName() + extension;

        try {
            File file = new File(filePath);
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            var users = room.getUsers();
            for (int i = 0; i < users.size(); ++i) {
                bufferedWriter.write(users.get(i));
                if (i + 1 < users.size()) {
                    bufferedWriter.write(" ");
                }
            }
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void appendRoomContent(Room room, String newMessage) {
        rooms.get(room.getId()).appendContent(newMessage);
        String filePath = resources + roomDirectory + '/' + room.getName() + extension;

        try {
            FileWriter fileWriter = new FileWriter(filePath, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(newMessage);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getUsers() {
        return users;
    }

    public static ArrayList<Room> getRooms() {
        return rooms;
    }

    public static String getRoomContent(Room room) {
        return rooms.get(room.getId()).getContent();
    }
}
