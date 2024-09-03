module com.example.fightthelandlord {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.fightthelandlord to javafx.fxml;
    exports com.example.fightthelandlord;
}