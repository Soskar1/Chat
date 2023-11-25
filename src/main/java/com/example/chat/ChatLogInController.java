package com.example.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatLogInController {
    public void connect() throws IOException {
        Socket socket = new Socket("localhost", 7777);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("Hello World!");
        out.println("How are you?");

        out.close();
        socket.close();
    }
}