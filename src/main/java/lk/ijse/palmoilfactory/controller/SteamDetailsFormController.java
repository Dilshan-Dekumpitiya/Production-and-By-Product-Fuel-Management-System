package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import lk.ijse.palmoilfactory.model.SteamModel;
import lk.ijse.palmoilfactory.model.StockModel;
import lk.ijse.palmoilfactory.model.SupplierModel;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SteamDetailsFormController implements Initializable {
    @FXML
    private JFXTextField txtFruitOutput;

    @FXML
    private JFXTextField txtStockDate;

    @FXML
    private JFXTextField txtStockTime;

    @FXML
    private JFXComboBox<String> cmbStockId;

    @FXML
    private JFXTextField txtEmptyBunchOutput;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadStockIds();
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

            double fruitOutput=ffbinput*0.3;
            txtFruitOutput.setText(String.valueOf(fruitOutput));
            double emptyBunchoutput=ffbinput*0.7;
            txtEmptyBunchOutput.setText(String.valueOf(emptyBunchoutput));

            String date= StockModel.searchByStockIdDate(stockId);
            txtStockDate.setText(date);

            String time = StockModel.searchByStockIdTime(stockId);
            txtStockTime.setText(time);

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }
    }
}
