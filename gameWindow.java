package org.example.fight_the_landlord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.fight_the_landlord.Controllers.gameWindowController;

public class gameWindow extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameWindow.fxml"));

        //   加载 FXML 文件并返回根节点（Parent）。
        Parent root = fxmlLoader.load();

        //   通过 fxmlLoader.getController() 获取 GameWindowController 的实例。
        gameWindowController controller = fxmlLoader.getController();


        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setTitle("gameWindow");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
