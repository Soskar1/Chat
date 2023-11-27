package com.example.chat.network.requests;

import java.util.ArrayList;

public class GetUsersRequest extends Request {
    public ArrayList<String> nicknames;

    public GetUsersRequest(String sender) {
        super(sender);
    }
}
