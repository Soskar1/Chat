package com.example.chat;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class RoomCreatorController {
    @FXML private ListView<String> usersToInviteListView;
    @FXML private ListView<String> allUsersListView;
    private User userToIgnore;

    public void initialize(User user) {
        userToIgnore = user;


    }

    private void getUsersFromServer() throws IOException {
        //GetAllUsersClientRequest request = new GetAllUsersClientRequest(userToIgnore.getNickname());
        //ObjectOutputStream out = new ObjectOutputStream(userToIgnore.getOutputStream());
        //out.writeObject(request);
        //out.flush();
        //out.close();


    }
}
