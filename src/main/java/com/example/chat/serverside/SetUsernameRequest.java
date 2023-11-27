package com.example.chat.serverside;

public class SetUsernameRequest extends Request {
    private final String newNickname;

    public SetUsernameRequest(String sender, String newNickname) {
        super(sender);
        this.newNickname = newNickname;
    }

    public String getNewNickname() {
        return newNickname;
    }
}
