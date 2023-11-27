package com.example.chat.serverside;

import com.example.chat.User;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;

public class Client {
    private static User user;
    private static ObjectOutputStream output;

    public static void setUser(User user) throws IOException {
        Client.user = user;
        output = new ObjectOutputStream(user.getOutputStream());
    }

    public static String getNickname() {
        return Client.user.getNickname();
    }

    public static InputStream getInputStream() throws IOException {
        return Client.user.getInputStream();
    }

    public static void sendDataToServer(Object data) throws IOException {
        output.writeObject(data);
        output.flush();
    }

    public static void disconnect() throws IOException {
        output.close();
        user.disconnect();
    }
}
