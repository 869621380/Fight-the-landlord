package com.example.fightthelandlord;

import com.example.fightthelandlord.Controllers.gameWindowController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.IOException;

public class test extends Application {
    public static void main(String[]args) throws Exception {
        launch(args);

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        gamePage game=new gamePage();
        //没有连接成功直接结束
        //if(game.getConnectStatus()) return;
        game.gameStart();
    }

}
