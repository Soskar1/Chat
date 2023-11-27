package com.example.chat;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class MessengerController {
    private static User user;

    public void initialize(User user) throws IOException {
        MessengerController.user = user;
        sendDataToServer(user.getNickname());
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

    public static void sendDataToServer(Object data) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(user.getOutputStream());
        out.writeObject(data);
        out.flush();
        out.close();
    }

    public void shutdown() {
        user.disconnect();
    }
}
