package com.example.chat.serverside;

public class Request {
    private final String sender;

    public Request(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
