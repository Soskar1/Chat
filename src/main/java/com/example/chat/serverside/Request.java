package com.example.chat.serverside;

import java.io.Serializable;

public class Request implements Serializable {
    private final String sender;

    public Request(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}
