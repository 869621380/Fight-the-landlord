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
    public boolean testGame() throws IOException, InterruptedException {
        gamePage game=new gamePage();
        if(game.getConnectStatus()){
            game.gameStart();
            return true;
        }
        else {
            System.out.println("Connect Error");
            return false;
        }
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        /**
         * scene对象可获取，controller用于和前端界面交换数据
         */
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("gameWindow.fxml"));
        //   加载 FXML 文件并返回根节点（Parent）。
        Parent root = fxmlLoader.load();
        //   通过 fxmlLoader.getController() 获取 GameWindowController 的实例。
        controller = fxmlLoader.getController();
        Scene scene = new Scene(root, 1200, 800);

        /* ******************************************* */

        /* 用于固定窗口大小，设置标题  */
        primaryStage.setTitle("gameWindow");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setMaxWidth(scene.getWidth());
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.setMaxHeight(scene.getHeight());

        primaryStage.show();//  窗口输出
        game=new gamePage();
        //if(!game.getConnectStatus()){
           // primaryStage.close();
       // }
        game.gameStart();
        controller.setHandCard(game.getDeck());


    }
    private gameWindowController controller;
    gamePage game;
}
