package com.example.chat;

import com.example.chat.serverside.Client;
import com.example.chat.serverside.GetAllUsersRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomCreatorController implements Initializable {
    @FXML private ListView<String> usersToInviteListView;
    @FXML private ListView<String> allUsersListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getUsersFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    private void getUsersFromServer() throws IOException, ClassNotFoundException {
        GetAllUsersRequest request = new GetAllUsersRequest(Client.getNickname());
        Client.sendDataToServer(request);

        // wait for response
        ObjectInputStream in = new ObjectInputStream(Client.getInputStream());
        String[] nicknames = (String[])in.readObject();
        allUsersListView.getItems().addAll(nicknames);
        in.close();
    }

    private void disconnect() {
        try {
            Client.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
