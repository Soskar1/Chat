package com.example.chat.network.requests;

import com.example.chat.network.Client;

import java.io.Serializable;

public class Request implements Serializable {
    private final String sender;

    public Request() {
        sender = Client.getNickname();
    }

    public String getSender() {
        return sender;
    }
}
