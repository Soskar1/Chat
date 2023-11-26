package com.example.chat;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ChatLogInController {
    @FXML private TextField nicknameTextField;

    public void connect() throws IOException {
        if (nicknameTextField.getText().isEmpty())
            return;

        Socket socket = new Socket("localhost", 7777);
        User user = new User(nicknameTextField.getText(), socket);
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
        stage.show();
        stage.setResizable(false);

        close();
    }

    private void close() {
        Stage stage = (Stage) nicknameTextField.getScene().getWindow();
        stage.close();
    }
}