package com.example.chat.network.requests;

import com.example.chat.network.Room;

public class SendMessageRequest extends Request {
    public String message;
    public Room room;

    public SendMessageRequest(String sender, Room room, String message) {
        super(sender);
        this.message = sender + ": " + message + "\n";
        this.room = room;
    }
}
