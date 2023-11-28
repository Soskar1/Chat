package com.example.chat.network;

import com.example.chat.User;
import com.example.chat.network.requests.CreateRoomRequest;
import com.example.chat.network.requests.GetRoomContentRequest;
import com.example.chat.network.requests.GetUsersRequest;
import com.example.chat.network.requests.SendMessageRequest;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ClientHandler implements Runnable {
    private User user;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ClientHandler(Socket socket) throws IOException, ClassNotFoundException {
        in = new ObjectInputStream(socket.getInputStream());
        out = new ObjectOutputStream(socket.getOutputStream());
        user = new User("test", socket);

        String nickname = (String) in.readObject();
        user = new User(nickname, socket);
        System.out.println("New client " + nickname + " accepted");

        Server.addUser(nickname, this);
    }

    @Override
    public void run() {
        while (user.isConnected() && !user.isClosed()) {
            try {
                Object object = in.readObject();

                if (object instanceof SendMessageRequest request) {
                    System.out.println(user.getNickname() + ": SEND_MESSAGE_REQUEST");
                    Server.addContentToRoom(request.room, request.message);

                    ArrayList<String> users = request.room.getUsers();
                    for (String nickname : users)
                        Server.getClient(nickname).sendToClient(request);
                } else if (object instanceof GetUsersRequest request) {
                    System.out.println(user.getNickname() + ": ALL_USERS_REQUEST");

                    var nicknames = Server.getUserNicknames().toArray();
                    request.nicknames = new ArrayList<>();

                    for (Object o : nicknames) {
                        String nickname = (String) o;

                        if (!Objects.equals(nickname, request.getSender()))
                            request.nicknames.add(nickname);
                    }

                    sendToClient(request);
                } else if (object instanceof CreateRoomRequest request) {
                    System.out.println(user.getNickname() + ": CREATE_ROOM_REQUEST");

                    Server.addRoom(request.room);

                    ArrayList<String> users = request.room.getUsers();
                    for (String nickname : users)
                        Server.getClient(nickname).sendToClient(request);
                } else if (object instanceof GetRoomContentRequest request) {
                    System.out.println(user.getNickname() + ": GET_ROOM_CONTENT_REQUEST");

                    request.content = Server.getRoomContent(request.room);
                    sendToClient(request);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                user.disconnect();
            }
        }
    }

    public synchronized void sendToClient(Object object) throws IOException {
        out.writeObject(object);
        out.flush();
    }
}
