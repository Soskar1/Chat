package com.example.chat;

import java.io.IOException;
import java.io.OutputStream;
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

    public String getNickname() {
        return nickname;
    }

    public OutputStream getOutputStream() throws IOException {
        return socket.getOutputStream();
    }
}
