package com.example.chat;

import com.example.chat.network.Client;
import com.example.chat.network.Room;
import com.example.chat.network.ServerListener;
import com.example.chat.network.requests.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessengerController implements Initializable {
    @FXML private TextArea roomContentTextArea;
    @FXML private TextField messageTextField;
    @FXML private ListView<Room> roomListView;
    private RoomCreatorController roomCreatorController;

    private Room currentRoom = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GetRoomsRequest request = new GetRoomsRequest();
        try {
            Client.sendDataToServer(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        roomListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        messageTextField.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    sendMessage();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        try {
            ServerListener roomListener = new ServerListener(this::handleServerData);
            Thread thread = new Thread(roomListener);
            thread.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void handleServerData(Object object) {
        if (object instanceof GetUsersRequest serverResponse) {
            if (roomCreatorController != null)
                roomCreatorController.setUsers(serverResponse.nicknames);
        } else if (object instanceof CreateRoomRequest serverResponse) {
            Platform.runLater(() -> roomListView.getItems().add(serverResponse.room));
        } else if (object instanceof GetRoomContentRequest serverResponse) {
            Platform.runLater(() -> roomContentTextArea.setText(serverResponse.content));
        } else if (object instanceof SendMessageRequest serverResponse) {
            if (currentRoom.getId() == serverResponse.room.getId())
                Platform.runLater(() -> roomContentTextArea.appendText(serverResponse.message));
        } else if (object instanceof GetRoomsRequest serverResponse) {
            if (!serverResponse.rooms.isEmpty())
                Platform.runLater(() -> roomListView.getItems().addAll(serverResponse.rooms));
        }
    }

    public void selectRoom() throws IOException {
        var rooms = roomListView.getSelectionModel().getSelectedItems();
        if (rooms.isEmpty()) {
            return;
        }

        currentRoom = rooms.get(0);
        messageTextField.setDisable(false);

        GetRoomContentRequest request = new GetRoomContentRequest(currentRoom);
        Client.sendDataToServer(request);
    }

    public void createRoom() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Messenger.class.getResource("room-creator.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        roomCreatorController = fxmlLoader.getController();

        Scene scene = new Scene(root);

        stage.setTitle("Messenger. Room Creator");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }

    private void sendMessage() throws IOException {
        String message = messageTextField.getText();
        SendMessageRequest request = new SendMessageRequest(currentRoom, message);
        Client.sendDataToServer(request);

        messageTextField.clear();
    }
}
