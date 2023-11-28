package com.example.chat.network;

import java.io.Serializable;
import java.util.ArrayList;

public class Room implements Serializable {
    private final ArrayList<String> users;
    private final String name;
    private StringBuilder content;
    private int id;

    public Room(String name, ArrayList<String> users) {
        content = new StringBuilder();
        this.name = name;
        this.users = users;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content.toString();
    }

    public void setContent(String content) {
        this.content = new StringBuilder(content);
    }

    public void appendContent(String content) {
        this.content.append(content);
    }
}
