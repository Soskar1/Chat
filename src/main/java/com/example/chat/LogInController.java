package com.example.chat;

import com.example.chat.network.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class LogInController {
    @FXML private TextField nicknameTextField;

    public void connect() throws IOException {
        String nickname = nicknameTextField.getText();
        if (nickname.isEmpty())
            return;

        Socket socket = new Socket(Settings.IP, Settings.PORT);
        User user = new User(nickname, socket);
        Client.setUser(user);
        Client.sendDataToServer(nickname);

        enterMessenger();
    }

    private void enterMessenger() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Messenger.class.getResource("messenger.fxml"));
        Parent root = fxmlLoader.load();

        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setTitle("Messenger. " + nicknameTextField.getText());
        stage.setScene(scene);

        stage.setOnHidden(e -> {
            try {
                Client.disconnect();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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