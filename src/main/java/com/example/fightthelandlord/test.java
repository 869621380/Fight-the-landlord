package com.example.fightthelandlord;

import javafx.application.Application;
import javafx.stage.Stage;

public class test extends Application {
    public static void main(String[]args) throws Exception {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        gamePage game=new gamePage();
        //没有连接成功直接结束
        if(!game.getConnectStatus()) return;
        game.selectRoom();
    }

}
