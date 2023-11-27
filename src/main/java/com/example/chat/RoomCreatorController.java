package com.example.chat;

import com.example.chat.network.Client;
import com.example.chat.network.requests.CreateRoomRequest;
import com.example.chat.network.requests.GetUsersRequest;
import com.example.chat.network.Room;
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
    }

    public void setUsers(ArrayList<String> nicknames) {
        allUsersListView.getItems().addAll(nicknames);
    }

    private void disconnect() {
        try {
            Client.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}