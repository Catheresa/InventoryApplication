module com.example.pa {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.controller to javafx.fxml;
    exports com.example.controller;
}