module org.example.fight_the_landlord {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.desktop;
    requires java.sql;


    opens com.example.fightthelandlord to javafx.fxml;
    exports com.example.fightthelandlord;
    exports com.example.fightthelandlord.Controllers;
    opens com.example.fightthelandlord.Controllers to javafx.fxml;
}