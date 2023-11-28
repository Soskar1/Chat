package com.example.chat.network.requests;

import com.example.chat.network.Room;

import java.util.ArrayList;

public class GetRoomsRequest extends Request {
    public ArrayList<Room> rooms = new ArrayList<>();
}
