package com.example.fightthelandlord.Controllers;
import com.example.fightthelandlord.Controllers.RegisterWindowControllers;
import com.example.fightthelandlord.Controllers.GameTitleControllers;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.application.Application;
import java.net.URL;

import java.io.IOException;
import java.sql.*;


public class LoginWindowControllers {

    public gamePage page;
    public Player player;
    private Stage stage;
    public void setLoginWindowStage(Stage stage) {
        this.stage = stage;
        //System.out.println("Stage set: " + stage);
    }

    @FXML
    private ImageView loginBackgroundImageView;
    //密码账号的标签、密码账号文本框、登录注册三组，每组两个，分别设置文字+背景
    @FXML
    private ImageView loginAccountImageView;
    @FXML
    private ImageView loginPasswordImageView;
    @FXML
    private Label loginAccountLabel;
    @FXML
    private Label loginPasswordLabel;

    @FXML
    private ImageView loginAccountInputImageView;
    @FXML
    private ImageView loginPasswordInputImageView;
    @FXML
    private TextField loginAccountInputTextField;
    @FXML
    private PasswordField loginAccountInputPasswordField;

    @FXML
    private ImageView loginButtonImageView;
    @FXML
    private ImageView registerButtonImageView;
    @FXML
    private Label loginButtonLabel;
    @FXML
    private Label registerButtonLabel;

    @FXML
    private Button registerButton;
    @FXML
    private Button loginButton;

    public void initialize() {
        Image loginBackgroundImage = new Image(getClass().getResourceAsStream("/images/ab4f616b-6685-4a4f-8a82-10afd2.png"));
        loginBackgroundImageView.setImage(loginBackgroundImage);
        //登入界面的账号密码标签背景框
        Image loginAccountImage = new Image(getClass().getResourceAsStream("/images/Button2.png"));
        loginAccountImageView.setImage(loginAccountImage);
        Image loginPasswordImage = new Image(getClass().getResourceAsStream("/images/Button2.png"));
        loginPasswordImageView.setImage(loginPasswordImage);

        //登入界面的账号密码输入框的背景框
        Image loginAccountInputImage = new Image(getClass().getResourceAsStream("/images/textbox1.png"));
        loginAccountInputImageView.setImage(loginAccountInputImage);
        Image loginPasswordInputImage = new Image(getClass().getResourceAsStream("/images/textbox1.png"));
        loginPasswordInputImageView.setImage(loginPasswordInputImage);
        //loginAccountInputTextField.setPromptText("Username");
        //loginAccountInputPasswordField.setPromptText("Password");

        //登入界面的登入、注册按钮
        Image loginButtonImage = new Image(getClass().getResourceAsStream("/images/Button2.png"));
        loginButtonImageView.setImage(loginButtonImage);
        Image registerButtonImage = new Image(getClass().getResourceAsStream("/images/Button2.png"));
        registerButtonImageView.setImage(registerButtonImage);

    }
    //使用工具包的跳转页面方法，点击注册跳转注册页面

    @FXML
    public void handleLoginButtonClick()throws IOException{
        String username = loginAccountInputTextField.getText();
        String password = loginAccountInputPasswordField.getText();
        //验证密码账号
        checkAccount(username, password);
    }
    public void checkAccount(String username, String password)throws IOException{

        try{
            //连接数据库
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LOGIN_USER", "root", "root");
            //查询
            PreparedStatement statement = connection.prepareStatement("SELECT*FROM USER WHERE NAME = ? AND PASSWORD = ?");
            statement.setString(1,username);
            statement.setString(2,password);

            ResultSet resultSet = statement.executeQuery();

            if(resultSet.next()){
                Stage newStage = new Stage();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/GameTitle.fxml"));
                Parent root = fxmlLoader.load();
                GameTitleControllers controller = fxmlLoader.<GameTitleControllers>getController();
                page.firstController = controller;
                // 传递 Stage
                controller.setStage(newStage);
                Scene scene = new Scene(root);
                newStage.setScene(scene);
                newStage.setTitle("GameTitle");
                newStage.show();
                controller.setPage(page);
                controller.setPlayer(player);
                stage.hide();
            }else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("登录失败，请重新检查密码账号是否输入正确");
                alert.setHeaderText(null);
                alert.setContentText("登录失败");
//                alert.getDialogPane().getStylesheets().add(getClass().getResource("loginFail.css").toExternalForm());
//                URL imageUrl = getClass().getResource("/images/alertBackground.png");
//                if (imageUrl!= null) {
//                    // 将图片路径设置为对话框的图形
//                    alert.setGraphic(new javafx.scene.image.ImageView(new javafx.scene.image.Image(imageUrl.toExternalForm())));
//                }

                alert.showAndWait();

            }
            connection.close();
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegisterButtonClick()throws IOException {
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/RegisterWindow.fxml"));
        Parent root = fxmlLoader.load();
        RegisterWindowControllers controller = fxmlLoader.<RegisterWindowControllers>getController();
        // 传递 Stage
        controller.setRegisterWindowStage(newStage);
        controller.page = this.page;
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("RegisterWindow");
        newStage.show();
        stage.hide();
    }

}

