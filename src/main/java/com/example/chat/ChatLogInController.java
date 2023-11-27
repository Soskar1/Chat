package com.example.chat;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class ChatLogInController {
    @FXML private TextField nicknameTextField;

    public void connect() throws IOException {
        String nickname = nicknameTextField.getText();
        if (nickname.isEmpty())
            return;

        Socket socket = new Socket(Settings.IP, Settings.PORT);

        User user = new User(nickname, socket);
        enterMessenger(user);
    }

    private void enterMessenger(User user) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("messenger.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        MessengerController messenger = fxmlLoader.getController();
        messenger.initialize(user);

        Scene scene = new Scene(root);

        stage.setTitle("Messenger");
        stage.setScene(scene);

        stage.setOnHidden(e -> {
            messenger.shutdown();
            Platform.exit();
        });

        stage.show();
        stage.setResizable(false);

        close();
    }

    private void close() {
        Stage stage = (Stage) nicknameTextField.getScene().getWindow();
        stage.close();
    }
}