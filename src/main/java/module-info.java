module com.example.chat {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.example.chat;
    opens com.example.chat to javafx.fxml;
}