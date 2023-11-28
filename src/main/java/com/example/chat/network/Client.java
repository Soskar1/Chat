package com.example.chat.network;

import com.example.chat.User;
import com.example.chat.network.requests.Request;

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

    public synchronized static void sendDataToServer(Object data) throws IOException {
        output.writeObject(data);
        output.flush();
    }

    public static void disconnect() throws IOException {
        output.close();
        user.disconnect();
    }

    public static boolean isClosed() {
        return user.isClosed();
    }
}
