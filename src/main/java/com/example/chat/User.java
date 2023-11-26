package com.example.chat;

import java.io.IOException;
import java.net.Socket;

public class User {
    private String nickname;
    private Socket socket;

    public User(String nickname, Socket socket) {
        this.nickname = nickname;
        this.socket = socket;
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
