package com.example.chat.network.requests;

import com.example.chat.network.Room;

public class CreateRoomRequest extends Request {
    public final Room room;

    public CreateRoomRequest(String sender, Room room) {
        super(sender);
        this.room = room;
    }
}
