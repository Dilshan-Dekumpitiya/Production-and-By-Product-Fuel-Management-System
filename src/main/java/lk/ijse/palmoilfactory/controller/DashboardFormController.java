package lk.ijse.palmoilfactory.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class DashboardFormController implements Initializable {
    public Pane dashboardContext;
    public AnchorPane dashboardDetailsPane;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateTimeInit();

    }

    private void dateTimeInit() {
        lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     hh:mm:ss a")));

        lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     yyyy-MM-dd")));

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     hh:mm:ss a")))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        Timeline timeline2 = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     yyyy-MM-dd")))));
        timeline2.setCycleCount(Animation.INDEFINITE);
        timeline2.play();
    }

    @FXML
    void btnDashboardOnAction(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader= new FXMLLoader(LoginFormController.class.getResource("/view/dashboard-view-form.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        thisStage.setScene(scene);
        thisStage.setMaximized(true);
    }

    public void btnSupplierDetailOnAction(ActionEvent actionEvent) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/supplier-details-form.fxml"));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);
    }

    @FXML
    void btnStockDetailsOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/stock-details-form.fxml"));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);
    }

    @FXML
    void btnSteamOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/steam-details-form.fxml"));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);
    }

    @FXML
    void btnOilProductionOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/oil-production-form.fxml"));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);
    }

    @FXML
    void btnByProductFuelOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/by-product-fuel-form.fxml"));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);
    }

    @FXML
    void btnEmployeeOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/employee-details-form.fxml"));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);
    }

    @FXML
    void btnOrderDetailsOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/order-details-form.fxml"));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);
    }

    @FXML
    void btnGetReportOnAction(ActionEvent event) throws IOException {
        Parent load = FXMLLoader.load(getClass().getResource("/view/get-report-form.fxml"));
        dashboardDetailsPane.getChildren().clear();
        dashboardDetailsPane.getChildren().add(load);
    }

    @FXML
    void btnLogoutOnAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) dashboardContext.getScene().getWindow();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/login-view-form.fxml"))));

        stage.centerOnScreen();
        stage.show();
    }
}
