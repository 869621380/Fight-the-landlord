package com.example.fightthelandlord.Controllers;
//import com.example.fightthelandlord.LoginWindow;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;



import java.io.IOException;

public class LoginWindowControllers {
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
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/GameTitle.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("GameTitle");
        stage.show();
    }

    @FXML
    public void handleRegisterButtonClick()throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/RegisterWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("RegisterWindow");
        stage.show();
    }




}