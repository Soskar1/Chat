module com.example.chat {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.chat;
    opens com.example.chat to javafx.fxml;
    exports com.example.chat.serverside;
    opens com.example.chat.serverside to javafx.fxml;
}