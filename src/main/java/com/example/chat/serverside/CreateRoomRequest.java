package com.example.chat.serverside;

public class CreateRoomRequest extends Request {
    private final Room room;

    public CreateRoomRequest(String sender, Room room) {
        super(sender);
        this.room = room;
    }
}
