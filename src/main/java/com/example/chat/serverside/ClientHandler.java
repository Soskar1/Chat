package com.example.chat.serverside;

import com.example.chat.User;

import java.io.*;
import java.net.Socket;
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

                if (object instanceof String) {
                    // message
                } else if (object instanceof GetUsersRequest request) {
                    System.out.println(user.getNickname() + ": ALL_USERS_REQUEST");

                    var nicknames = Server.getUserNicknames();
                    out.writeObject(nicknames.size() - 1);
                    for (String nickname : nicknames)
                        if (!Objects.equals(nickname, request.getSender()))
                            out.writeObject(nickname);

                    out.flush();
                } else if (object instanceof CreateRoomRequest request) {
                    System.out.println(user.getNickname() + ": CREATE_ROOM_REQUEST");


                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                user.disconnect();
            }
        }
    }
}
