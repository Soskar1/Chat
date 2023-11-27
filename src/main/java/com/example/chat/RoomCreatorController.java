package com.example.chat;

import com.example.chat.serverside.GetAllUsersRequest;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RoomCreatorController {
    @FXML private ListView<String> usersToInviteListView;
    @FXML private ListView<String> allUsersListView;
    private User userToIgnore;

    public void initialize(User user) {
        userToIgnore = user;
    }

    private void getUsersFromServer() throws IOException, ClassNotFoundException {
        GetAllUsersRequest request = new GetAllUsersRequest(userToIgnore.getNickname());
        ObjectOutputStream out = new ObjectOutputStream(userToIgnore.getOutputStream());
        out.writeObject(request);
        out.flush();
        out.close();

        // wait for response
        ObjectInputStream in = new ObjectInputStream(userToIgnore.getInputStream());
        String[] nicknames = (String[])in.readObject();
        allUsersListView.getItems().addAll(nicknames);
        in.close();
    }
}
