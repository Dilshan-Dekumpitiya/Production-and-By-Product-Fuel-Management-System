package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LoginFormController {

    public AnchorPane loginContext;

    public JFXTextField txtUsername;

    public JFXTextField txtPassword;


    @FXML
    void btnForgotPasswordOnAction(ActionEvent event) {

    }

    @FXML
    void btnLoginOnAction(ActionEvent event) throws IOException {

        Stage stage = (Stage) loginContext.getScene().getWindow();
        stage.close();
        
        Stage stage2 = new Stage();
        stage2.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/dashboard-view-form.fxml"))));
        stage2.setTitle("Dashboard");

       /* int width = (int) Screen.getPrimary().getBounds().getWidth();
        int height = (int) Screen.getPrimary().getBounds().getHeight();

        stage2.setMaximized(true);

        stage2.setWidth(width);
        stage2.setHeight(height);*/
        stage2.setMaximized(true);
        stage2.show();

    }
    @FXML
    void btnNeedSignUpOnAction(ActionEvent event) {

    }

    @FXML
    void btnShowPasswordOnAction(ActionEvent event) {
        //txtPassword.textProperty().bindBidirectional(txtPasswordText.textProperty());
    }
}
