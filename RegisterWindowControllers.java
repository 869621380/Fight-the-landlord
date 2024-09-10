package com.example.fightthelandlord.Controllers;
//import com.example.fightthelandlord.LoginWindow;
//import com.example.fightthelandlord.RegisterWindow;
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
public class RegisterWindowControllers {

    @FXML
    private ImageView RegisterWindowBackgroundImageView;
    @FXML
    private ImageView registerAccountImageView;
    @FXML
    private ImageView registerPasswordImageView;
    @FXML
    private Label registerAccountLabel;
    @FXML
    private Label registerPasswordLabel;

    @FXML
    private ImageView registerAccountInputImageView;
    @FXML
    private ImageView registerPasswordInputImageView;
    @FXML
    private TextField registerAccountInputTextField;
    @FXML
    private PasswordField registerAccountInputPasswordField;

    @FXML
    private ImageView cancelButtonImageView;
    @FXML
    private ImageView confirmButtonImageView;
    @FXML
    private Label cancelButtonLabel;
    @FXML
    private Label confirmButtonLabel;

    public void initialize() {
        Image registerBackgroundImage = new Image(getClass().getResourceAsStream("/images/ab4f616b-6685-4a4f-8a82-10afd2.png"));
        registerAccountImageView.setImage(registerBackgroundImage);
        //注册界面的账号密码标签背景框
        Image registerAccountImage = new Image(getClass().getResourceAsStream("/images/Button2.png"));
        registerAccountImageView.setImage(registerAccountImage);
        Image registerPasswordImage = new Image(getClass().getResourceAsStream("/images/Button2.png"));
        registerPasswordImageView.setImage(registerPasswordImage);

        //注册界面的账号密码输入框的背景框
        Image registerAccountInputImage = new Image(getClass().getResourceAsStream("/images/textbox1.png"));
        registerAccountInputImageView.setImage(registerAccountInputImage);
        Image registerPasswordInputImage = new Image(getClass().getResourceAsStream("/images/textbox1.png"));
        registerPasswordInputImageView.setImage(registerPasswordInputImage);
        //loginAccountInputTextField.setPromptText("Username");
        //loginAccountInputPasswordField.setPromptText("Password");

        //注册界面的取消注册、确认注册按钮
        Image cancelButtonImage = new Image(getClass().getResourceAsStream("/images/Button2.png"));
        cancelButtonImageView.setImage(cancelButtonImage);
        Image confirmButtonImage = new Image(getClass().getResourceAsStream("/images/Button2.png"));
        confirmButtonImageView.setImage(confirmButtonImage);
    }
    @FXML
    public void handleCancelButtonClick()throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/LoginWindow.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("LoginWindow");
        stage.show();
    }

//    @FXML
//    public void handleConfirmButtonClick()throws IOException {
//
//
//    }


}