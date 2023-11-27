package com.example.chat.serverside;

import com.example.chat.User;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private User user;
    private final ObjectOutputStream out;
    private final ObjectInputStream in;

    public ClientHandler(Socket socket) throws IOException, ClassNotFoundException {
        out = new ObjectOutputStream(socket.getOutputStream());
        in = new ObjectInputStream(socket.getInputStream());

        String nickname = (String) in.readObject();
        user = new User(nickname, socket);
        System.out.println("New client " + nickname + " accepted");

        Server.addUser(nickname, this);
    }

    @Override
    public void run() {
        while (user.isConnected()) {
            //try {
            //    Object object = in.readObject();
//
            //    if (object instanceof String) {
            //        // message
            //    } else if (object instanceof GetAllUsersRequest) {
            //        //GetAllUsersRequest request = (GetAllUsersRequest) object;
            //        System.out.println(user.getNickname() + ": ALL_USERS_REQUEST");
            //        String[] userNicknames = server.getUserNicknames();
//
            //        out.writeObject(userNicknames);
            //        out.flush();
            //    }
//
            //} catch (IOException | ClassNotFoundException e) {
            //    throw new RuntimeException(e);
            //}
        }
    }
}
