package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.Duration;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.model.StockModel;
import lk.ijse.palmoilfactory.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.time.LocalDateTime.*;
import static java.time.format.DateTimeFormatter.*;

public class StockDetailsFormController implements Initializable {
    @FXML
    private JFXTextField txtStockId;

    @FXML
    private JFXTextField txtFFBInput;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTime;
    @FXML
    private JFXComboBox<String> cmbSupplierId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupplierIds();
        Platform.runLater(() -> txtStockId.requestFocus());

        lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     yyyy-MM-dd")));

        loadTime();

    }

    private void loadTime() {
        lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     hh:mm:ss a")));
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     hh:mm:ss a")))));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void loadSupplierIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> iDs = SupplierModel.getIDs();

            for (String id : iDs) {
                obList.add(id);
            }
            cmbSupplierId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
    }
    @FXML
    void btnAddStockOnAction(ActionEvent event) {
        if(txtStockId.getText().isEmpty() || txtFFBInput.getText().isEmpty() ){
            new Alert(Alert.AlertType.WARNING,"Please Input data to Add Stock").show();
        }else {
            String stockId = txtStockId.getText();
            int ffbInput = Integer.parseInt(txtFFBInput.getText());
            lblDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     yyyy-MM-dd")));
            lblTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("     hh:mm:ss ")));
            String supId = String.valueOf(cmbSupplierId.getSelectionModel().getSelectedItem());

            boolean isAdded;

            try {
               isAdded = StockModel.addStock(stockId, ffbInput, lblDate.getText(), lblTime.getText(),supId);

                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Stock Added").show();

                    txtStockId.clear();
                    txtFFBInput.clear();
                    cmbSupplierId.getItems().clear();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Stock Not Added Please Try Again").show();
                }
            } catch (SQLException e) {
                System.out.println(e);
                // new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            } catch (ClassNotFoundException e) {
                System.out.println(e);
                //  new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void btnSearchStockOnAction(ActionEvent event) {
        if(txtStockId.getText().isEmpty() && txtFFBInput.getText().isEmpty() ){
            new Alert(Alert.AlertType.WARNING,"Please Input Stock ID to search Stock").show();
        }else {
            String stockId = txtStockId.getText();
            try {
                Stock stock = StockModel.searchStock(stockId);
                if (stock != null) {
                    txtFFBInput.setText(String.valueOf(stock.getFfbInput()));
                    lblDate.setText(stock.getDate());
                    lblTime.setText(stock.getTime());
                    cmbSupplierId.setValue(StockModel.searchByStockIdSupId(stockId));

                } else {
                    new Alert(Alert.AlertType.WARNING, "Stock Not Found Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void txtStockIdOnAction(ActionEvent event) {
        btnSearchStockOnAction(event);
    }

    @FXML
    void btnUpdateStockOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
       if (txtStockId.getText().isEmpty() || txtFFBInput.getText().isEmpty() || lblDate.getText().isEmpty() || lblTime.getText().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Input Stock ID and Search Stock is exist").show();
        } else {
            String stockId = txtStockId.getText();
            int ffbInput = Integer.parseInt(txtFFBInput.getText());
            String date = lblDate.getText();
            String time = lblTime.getText();
            String supId;
            if(cmbSupplierId.getSelectionModel().isEmpty()){
                supId = StockModel.searchByStockIdSupId(stockId);
            }else {
                supId=cmbSupplierId.getSelectionModel().getSelectedItem();
            }

            boolean isUpdated;

            try {
                isUpdated = StockModel.updateStock(stockId, ffbInput, date, time, supId);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Stock Updated").show();
                    txtStockId.clear();
                    txtFFBInput.clear();
                    cmbSupplierId.getItems().clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Stock Not Updated Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void btnDeleteStockOnAction(ActionEvent event) {
        if(txtStockId.getText().isEmpty() || txtFFBInput.getText().isEmpty() || lblDate.getText().isEmpty() || lblTime.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Stock ID and Search Stock is exist").show();
        }else {
            String stockId = txtStockId.getText();
            try {

                boolean isDeleted = StockModel.deleteStock(stockId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Stock Deleted Successfully").show();
                    txtStockId.clear();
                    txtFFBInput.clear();
                    cmbSupplierId.getItems().clear();

                } else {
                    new Alert(Alert.AlertType.WARNING, "Delete Fail").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
            }
        }
    }

}
