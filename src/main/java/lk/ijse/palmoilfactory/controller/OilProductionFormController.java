package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lk.ijse.palmoilfactory.model.OilProductionModel;
import lk.ijse.palmoilfactory.model.SteamModel;
import lk.ijse.palmoilfactory.model.StockModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class OilProductionFormController implements Initializable {
    @FXML
    private JFXTextField txtTotalEBLiquidOutput;

    @FXML
    private JFXTextField txtStockDate;

    @FXML
    private JFXTextField txtStockTime;

    @FXML
    private JFXComboBox<String> cmbStockId;

    @FXML
    private JFXTextField txtTotalPressLiquid;

    @FXML
    private JFXTextField txtTotalOilOutput;

    @FXML
    private Label lblTotalOilQtyOnHand;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStockIds();

        calculateOilProductionqty();
    }

    private void calculateOilProductionqty() {
        try {
            String oilQty = OilProductionModel.getOilQtyOnHand();
            lblTotalOilQtyOnHand.setText(oilQty);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened!").show();
        }
    }

    private void loadStockIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> iDs = SteamModel.getStockIDs();

            for (String id : iDs) {
                obList.add(id);
            }
            cmbStockId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
    }

    @FXML
    void cmbStockIdOnAction(ActionEvent event) {
        String stockId = cmbStockId.getSelectionModel().getSelectedItem();
        try {

            int ffbinput = StockModel.searchByStockIdFFBInput(stockId);

            double totalPressLiquid=ffbinput*0.3*0.88;
            txtTotalPressLiquid.setText(String.valueOf(totalPressLiquid));
            double totalEBLiquidOutput=ffbinput*0.7*0.72;
            txtTotalEBLiquidOutput.setText(String.valueOf(totalEBLiquidOutput));

            String date= StockModel.searchByStockIdDate(stockId);
            txtStockDate.setText(date);

            String time = StockModel.searchByStockIdTime(stockId);
            txtStockTime.setText(time);

            String totalOilOutput = Double.toString(totalPressLiquid+totalEBLiquidOutput);
            txtTotalOilOutput.setText(totalOilOutput);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        if(cmbStockId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.ERROR, "No Stock ID Selected! Please Select Stock ID").show();
        }else {
            cmbStockId.getItems().clear();
            txtStockTime.clear();
            txtStockDate.clear();
            txtTotalEBLiquidOutput.clear();
            txtTotalPressLiquid.clear();
            txtTotalOilOutput.clear();
            loadStockIds();
        }
    }
}
