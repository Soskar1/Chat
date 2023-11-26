package com.example.chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MessengerController {
    private User user;

    public void initialize(User user) {
        this.user = user;
    }

    public void createRoom() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("room-creator.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        RoomCreatorController controller = fxmlLoader.getController();
        controller.initialize(user);

        Scene scene = new Scene(root);

        stage.setTitle("Messenger. Room Creator");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
    }
}
