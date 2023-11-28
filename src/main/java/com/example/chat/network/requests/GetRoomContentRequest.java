package com.example.chat.network.requests;

import com.example.chat.network.Room;

public class GetRoomContentRequest extends Request {
    public final Room room;
    public String content = "";

    public GetRoomContentRequest(String sender, Room room) {
        super(sender);
        this.room = room;
    }
}
