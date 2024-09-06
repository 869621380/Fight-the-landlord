package com.example.fightthelandlord;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.example.fightthelandlord.Controllers.gameWindowController;

/**
 *       gameWindow仅用于前端自己调试，后端所需仅看标注段落
 */
public class gameWindow extends Application {

    public static void main(String[] args) {
        launch(args);
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
        gameWindowController controller = fxmlLoader.getController();
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


    }

}
