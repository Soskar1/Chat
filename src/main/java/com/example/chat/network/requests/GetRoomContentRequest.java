package com.example.chat.network.requests;

import com.example.chat.network.Room;

public class GetRoomContentRequest extends Request {
    public final Room room;
    public String content = "";

    public GetRoomContentRequest(Room room) {
        this.room = room;
    }
}
