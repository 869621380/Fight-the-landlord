package com.example.fightthelandlord.Controllers;
import com.example.fightthelandlord.Player;
import com.example.fightthelandlord.gamePage;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;

import java.awt.*;
import java.io.IOException;

public class GameTitleControllers {
    public boolean inStartRoom = true;
    private Player player;
    private Stage stage;
    private GameLobbyControllers secondController;
    private gamePage page;

    public void setPage(gamePage page) {
        this.page = page;
    }

//    public void setSecondController(GameLobbyControllers secondController) {
//        this.secondController = secondController;
//    }

    @FXML
    private ImageView GameTitleBackgroundImageView;
    @FXML
    private Button startGamingButton;
    @FXML
    private ImageView startGamingBackgroundImageView;
    @FXML
    private ImageView startGamingTextImageView;

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public void initialize() {
        Image GameTitleBackgroundImage= new Image(getClass().getResourceAsStream("/images/PW~GHMB)V}.png"));
        GameTitleBackgroundImageView.setImage(GameTitleBackgroundImage);

        Image startGamingBackgroundImage= new Image(getClass().getResourceAsStream("/images/Idle@2x.png"));
        startGamingBackgroundImageView.setImage(startGamingBackgroundImage);

        Image startGamingTextImage= new Image(getClass().getResourceAsStream("/images/startGame2.png"));
        startGamingTextImageView.setImage(startGamingTextImage);

    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @FXML
    public void handleEnterGameLobbyButtonClick() throws IOException {

        System.out.println("Enter Game Lobby");
        player.setSelectRoom("-1");
        // 创建新的窗口并加载新的场景
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/GameLobby.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("GameLobby");
        secondController = fxmlLoader.getController();
        secondController.setPlayer(player);
        secondController.setStage(newStage);
        page.setSecondController(secondController);
        newStage.show();
        inStartRoom = false;
        // 关闭当前窗口
        stage.hide();
    }
}