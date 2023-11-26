package com.example.chat.serverside;

import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ClientHandler(Socket socket) {
        try {
            this.socket = socket;
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            close();
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {

        }
    }

    private void close() {
        try {
            if (!socket.isClosed())
                socket.close();

            if (writer != null)
                writer.close();

            if (reader != null)
                reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
