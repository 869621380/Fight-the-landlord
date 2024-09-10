package com.example.fightthelandlord.Controllers;
import com.example.fightthelandlord.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.Alert;

import java.io.IOException;
//上面这个是警告对话框

public class GameLobbyControllers {
    //Player player;
    //设置5个房间

    private String[] roomCounts = new String[5];
    private Player player;
    private Stage stage;


    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Stage getStage() {
        return stage;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setRoomCounts(String s) {
        String[] temp = s.split("");
        for (int i = 0; i < temp.length; i++) {
            roomCounts[i] = temp[i];
            System.out.println(roomCounts[i]);
        }
    }

    @FXML
    private StackPane lobbyMainStackPane;
    @FXML
    private AnchorPane lobbyMainAnchorPane;
    @FXML
    private ImageView lobbyBackgroundImageView;

    @FXML
    private ImageView roomButtonImageView1;
    @FXML
    private ImageView roomButtonImageView2;
    @FXML
    private ImageView roomButtonImageView3;
    @FXML
    private ImageView roomButtonImageView4;
    @FXML
    private ImageView roomButtonImageView5;

    @FXML
    private ImageView textButtonImageView1;
    @FXML
    private ImageView textButtonImageView2;
    @FXML
    private ImageView textButtonImageView3;
    @FXML
    private ImageView textButtonImageView4;
    @FXML
    private ImageView textButtonImageView5;

    @FXML
    private Label roomText1;
    @FXML
    private Label roomText2;
    @FXML
    private Label roomText3;
    @FXML
    private Label roomText4;
    @FXML
    private Label roomText5;

    @FXML
    private ImageView musicControlButtonImageView;
    @FXML
    private ImageView exitLobbyButtonImageView;
    @FXML
    private ImageView avatarImageView;

    public void initialize() {// 使用类加载器加载资源文件
        //背景图
        Image lobbyBackgroundImage = new Image(getClass().getResourceAsStream("/images/]W2RLDQ9~9N98K%0N5}J8%.png"));
        lobbyBackgroundImageView.setImage(lobbyBackgroundImage);
        //音乐开关图标
        Image musicControlButtonImage = new Image(getClass().getResourceAsStream("/images/Music-On-Click.png"));
        musicControlButtonImageView.setImage(musicControlButtonImage);
        //退出大厅返回标题图标
        Image exitLobbyButtonImage = new Image(getClass().getResourceAsStream("/images/Exit-Click.png"));
        exitLobbyButtonImageView.setImage(exitLobbyButtonImage);
        //头像
        Image avatarImage = new Image(getClass().getResourceAsStream("/images/UT_Item_StandingPainting_113_Max.png"));
        avatarImageView.setImage(avatarImage);


        //设置房间按钮图标
        Image roomButtonImage = new Image(getClass().getResourceAsStream("/images/Home-Click.png"));
        roomButtonImageView1.setImage(roomButtonImage);
        roomButtonImageView2.setImage(roomButtonImage);
        roomButtonImageView3.setImage(roomButtonImage);
        roomButtonImageView4.setImage(roomButtonImage);
        roomButtonImageView5.setImage(roomButtonImage);

        //设置文本底框图标
        Image textButtonImage = new Image(getClass().getResourceAsStream("/images/Click.png"));
        textButtonImageView1.setImage(textButtonImage);
        textButtonImageView2.setImage(textButtonImage);
        textButtonImageView3.setImage(textButtonImage);
        textButtonImageView4.setImage(textButtonImage);
        textButtonImageView5.setImage(textButtonImage);

    }

    @FXML
    public void handleEnterRoom1ButtonClick() throws IOException {
        if (Integer.parseInt(roomCounts[0]) == 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("房间人数限制");
            alert.setHeaderText(null); // 不显示头部文本
            alert.setContentText("房间人数已满，无法进入游戏准备房间。");
            alert.showAndWait(); // 显示对话框并等待用户关闭
        } else {
            player.setSelectRoom("0");
            stage.close();
        }
    }

    @FXML
    public void handleEnterRoom2ButtonClick() throws IOException {
        if (Integer.parseInt(roomCounts[1]) == 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("房间人数限制");
            alert.setHeaderText(null); // 不显示头部文本
            alert.setContentText("房间人数已满，无法进入游戏准备房间。");
            alert.showAndWait(); // 显示对话框并等待用户关闭
        } else {
            player.setSelectRoom("1");
            stage.close();
        }
    }

    @FXML
    public void handleEnterRoom3ButtonClick() throws IOException {
        if (Integer.parseInt(roomCounts[2]) == 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("房间人数限制");
            alert.setHeaderText(null); // 不显示头部文本
            alert.setContentText("房间人数已满，无法进入游戏准备房间。");
            alert.showAndWait(); // 显示对话框并等待用户关闭
        } else {
            player.setSelectRoom("2");
            stage.close();
        }
    }

    @FXML
    public void handleEnterRoom4ButtonClick() throws IOException {
        if (Integer.parseInt(roomCounts[3]) == 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("房间人数限制");
            alert.setHeaderText(null); // 不显示头部文本
            alert.setContentText("房间人数已满，无法进入游戏准备房间。");
            alert.showAndWait(); // 显示对话框并等待用户关闭
        } else {
            player.setSelectRoom("3");
            stage.close();
        }
    }

    @FXML
    public void handleEnterRoom5ButtonClick() throws IOException {
        if (Integer.parseInt(roomCounts[4]) == 3) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("房间人数限制");
            alert.setHeaderText(null); // 不显示头部文本
            alert.setContentText("房间人数已满，无法进入游戏准备房间。");
            alert.showAndWait(); // 显示对话框并等待用户关闭
        } else {
            player.setSelectRoom("4");
            stage.close();
        }
    }


    @FXML
    public void handleExitLobbyButtonClick() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/GameTitle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("GameTitle");
        stage.show();
    }


    public void refresh(String s) {
        System.out.println("refresh接收到："+s);
        setRoomCounts(s);
        // 设置对应Label的文本
        roomText1.setText("房间1：" + roomCounts[0] + " 人");
        roomText2.setText("房间2：" + roomCounts[1] + " 人");
        roomText3.setText("房间3：" + roomCounts[2] + " 人");
        roomText4.setText("房间4：" + roomCounts[3] + " 人");
        roomText5.setText("房间5：" + roomCounts[4] + " 人");
    }
}