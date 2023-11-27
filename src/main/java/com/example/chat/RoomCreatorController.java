package com.example.chat;

import com.example.chat.serverside.Client;
import com.example.chat.serverside.CreateRoomRequest;
import com.example.chat.serverside.GetUsersRequest;
import com.example.chat.serverside.Room;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class RoomCreatorController implements Initializable {
    @FXML private ListView<String> usersToInviteListView;
    @FXML private ListView<String> allUsersListView;
    @FXML private Button addButton;
    @FXML private Button removeButton;
    @FXML private Button createRoomButton;

    private String selectedUser = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getUsersFromServer();
        } catch (IOException | ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
            disconnect();
        }
    }

    public void selectUser() {
        selectedUser = allUsersListView.getSelectionModel().getSelectedItems().get(0);

        if (selectedUser != null)
            enableButtons();
        else
            disableButtons();
    }

    public void addUserToInviteList() {
        if (!usersToInviteListView.getItems().contains(selectedUser))
            usersToInviteListView.getItems().add(selectedUser);

        disableButtons();
        createRoomButton.setDisable(usersToInviteListView.getItems().isEmpty());
    }

    public void removeUserFromInviteList() {
        usersToInviteListView.getItems().remove(selectedUser);
        disableButtons();
        createRoomButton.setDisable(usersToInviteListView.getItems().isEmpty());
    }

    public void createRoom() throws IOException {
        ArrayList<String> users = new ArrayList<>();
        StringBuilder roomName = new StringBuilder(Client.getNickname() + ", ");

        users.add(Client.getNickname());
        var usersToInvite = usersToInviteListView.getItems();

        for (int i = 0; i < usersToInvite.size(); ++i) {
            String user = usersToInvite.get(i);
            users.add(user);
            roomName.append(user);

            if (i + 1 != usersToInvite.size()) {
                roomName.append(", ");
            }
        }

        Room room = new Room(roomName.toString(), users);
        CreateRoomRequest request = new CreateRoomRequest(Client.getNickname(), room);
        Client.sendDataToServer(request);

        Stage stage = (Stage) createRoomButton.getScene().getWindow();
        stage.close();
    }

    private void enableButtons() {
        addButton.setDisable(false);
        removeButton.setDisable(false);
    }

    private void disableButtons() {
        addButton.setDisable(true);
        removeButton.setDisable(true);
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
