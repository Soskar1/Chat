package com.example.chat;

import com.example.chat.network.Client;
import com.example.chat.network.Room;
import com.example.chat.network.ServerListener;
import com.example.chat.network.requests.CreateRoomRequest;
import com.example.chat.network.requests.GetUsersRequest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessengerController implements Initializable {
    @FXML private ListView<Room> roomListView;
    private RoomCreatorController roomCreatorController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomListView.setCellFactory(param -> new ListCell<Room>() {
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
            if (roomCreatorController != null) {
                roomCreatorController.setUsers(serverResponse.nicknames);
            }
        } else if (object instanceof CreateRoomRequest serverResponse) {
            Platform.runLater(() -> roomListView.getItems().add(serverResponse.room));
            System.out.println("CREATE_ROOM_REQUEST from " + serverResponse.getSender());
        }
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
}
