module com.example.chat {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.chat;
    opens com.example.chat to javafx.fxml;
    exports com.example.chat.network;
    opens com.example.chat.network to javafx.fxml;
    exports com.example.chat.network.requests;
    opens com.example.chat.network.requests to javafx.fxml;
}