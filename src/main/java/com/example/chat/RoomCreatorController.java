package com.example.chat;

import com.example.chat.serverside.Client;
import com.example.chat.serverside.GetUsersRequest;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RoomCreatorController implements Initializable {
    @FXML private ListView<String> usersToInviteListView;
    @FXML private ListView<String> allUsersListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getUsersFromServer();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    private void getUsersFromServer() throws IOException, ClassNotFoundException, InterruptedException {
        GetUsersRequest request = new GetUsersRequest(Client.getNickname());
        Client.sendDataToServer(request);

        RequestResponse response = new RequestResponse();
        Thread thread = new Thread(response);
        thread.start();
        thread.join();
        allUsersListView.getItems().addAll(response.nicknames);
    }

    private void disconnect() {
        try {
            Client.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class RequestResponse implements Runnable {
        public String[] nicknames;

        @Override
        public void run() {
            int size = 0;
            try {
                size = (int) Client.getDataFromServer();
                nicknames = new String[size];
                for (int i = 0; i < size; ++i) {
                    nicknames[i] = (String)Client.getDataFromServer();
                }
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
