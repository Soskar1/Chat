package com.example.chat.network;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    private final ArrayList<String> users;
    private final String name;

    public Room(String name, ArrayList<String> users) {
        this.name = name;
        this.users = users;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }
}
