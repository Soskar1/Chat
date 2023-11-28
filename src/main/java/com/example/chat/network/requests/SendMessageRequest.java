package com.example.chat.network.requests;

import com.example.chat.network.Room;

public class SendMessageRequest extends Request {
    public String message;
    public Room room;

    public SendMessageRequest(Room room, String message) {
        this.message = getSender() + ": " + message + "\n";
        this.room = room;
    }
}
