package com.example.chat.network.requests;

import com.example.chat.network.Room;

public class CreateRoomRequest extends Request {
    public final Room room;

    public CreateRoomRequest(Room room) {
        this.room = room;
    }
}
