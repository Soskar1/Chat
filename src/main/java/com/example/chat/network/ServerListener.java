package com.example.chat.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.function.Consumer;

public class ServerListener implements Runnable {
    private final ObjectInputStream input;
    private final Consumer<Object> serverDataHandler;

    public ServerListener(Consumer<Object> serverDataHandler) throws IOException {
        input = new ObjectInputStream(Client.getInputStream());
        this.serverDataHandler = serverDataHandler;
    }

    @Override
    public void run() {
        try {
            while (!Client.isClosed()) {
                Object object = input.readObject();
                serverDataHandler.accept(object);
            }
        } catch (IOException | ClassNotFoundException e) {
            close();
            throw new RuntimeException(e);
        }
    }

    private void close() {
        try {
            input.close();
            Client.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
