package com.example.fightthelandlord.Controllers;
import com.example.fightthelandlord.Controllers.LoginWindowControllers;
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
import java.net.URL;

import java.io.IOException;
import java.sql.*;

public class RegisterWindowControllers {
    private Stage stage;
    public gamePage page;
    public void setRegisterWindowStage(Stage stage) {
        this.stage = stage;
        //System.out.println("Stage set: " + stage);
    }

    @FXML
    private ImageView registerBackgroundImageView;
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
        Image registerBackgroundImage = new Image(getClass().getResourceAsStream("/images/registerBackground.png"));
        registerBackgroundImageView.setImage(registerBackgroundImage);
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
    public void handleCancelButtonClick()throws IOException {//这里加多一个窗口关闭
        Stage newStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/LoginWindow.fxml"));
        Parent root = fxmlLoader.load();
        LoginWindowControllers controller = fxmlLoader.<LoginWindowControllers>getController();
        // 传递 Stage
        controller.setLoginWindowStage(newStage);
        controller.page = this.page;
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("LoginWindow");
        newStage.show();
        stage.hide();
    }
    @FXML
    private void handleConfirmButtonClick()throws IOException {//点击确认按钮传输回密码账号框的字符串

        String username = registerAccountInputTextField.getText();
        String password = registerAccountInputPasswordField.getText();
        registerAccount(username, password);
    }

    private void registerAccount(String username, String password) throws IOException {
        if (password.matches("[A-Za-z0-9]+")) {// 密码有效，进行注册逻辑，密码只能设置英文大小写和数字
            //-----里面是字符串传输去数据库以
            // 注册账户逻辑，下面是测试的
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/LOGIN_USER", "root", "root");
                PreparedStatement statement = connection.prepareStatement("INSERT INTO USER VALUES (?,?)");
                statement.setString(1, username);
                statement.setString(2, password);
                int rows = statement.executeUpdate();
                if (rows > 0) {
                    System.out.println("Insert successful");
                }

            }catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println("Username: " + username);
            System.out.println("Password: " + password);
            //弹出注册成功窗口和关闭注册窗口
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("注册成功喵");
            alert.setHeaderText(null);
            alert.setContentText("注册成功");

            //关闭注册窗口，进入登录窗口
            Stage newStage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/fightthelandlord/LoginWindow.fxml"));
            Parent root = fxmlLoader.load();
            LoginWindowControllers controller = fxmlLoader.<LoginWindowControllers>getController();
            // 传递 Stage
            controller.setLoginWindowStage(newStage);
            controller.page = this.page;
            Scene scene = new Scene(root);
            newStage.setScene(scene);
            newStage.setTitle("LoginWindow");
            newStage.show();
            stage.hide();

            // 更改对话框样式
            //alert.getDialogPane().getStylesheets().add(getClass().getResource("registerSuccess.css").toExternalForm());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("注册失败，密码设置范围为英文大小写与数字");
            alert.setHeaderText(null);
            alert.setContentText("注册失败");

            // 更改对话框样式
            //alert.getDialogPane().getStylesheets().add(getClass().getResource("registerFail.css").toExternalForm());
            alert.showAndWait();
        }

    }
}
