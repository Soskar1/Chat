package com.example.chat.serverside;

import java.util.ArrayList;

public class Room {
    private final ArrayList<String> users;
    private final String name;

    public Room(String name, ArrayList<String> users) {
        this.name = name;
        this.users = users;
    }
}
