package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import lk.ijse.palmoilfactory.model.EmployeeModel;
import lk.ijse.palmoilfactory.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeDetailsFormController implements Initializable {
    @FXML
    private JFXTextField txtEmployeeId;

    @FXML
    private JFXTextField txtEmployeeAddress;

    @FXML
    private JFXTextField txtEmployeeName;

    @FXML
    private JFXTextField txtEmployeeContact;

    @FXML
    private JFXTextField txtEmployeeSalary;

    @FXML
    private JFXComboBox<String> cmbEmployeetype;

    @FXML
    private JFXComboBox<String> cmbEmployeeSchId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtEmployeeId.requestFocus());
        loadSchIds();
        loadEmpType();
    }

    private void loadEmpType() {
        ArrayList<String> empTypeList=new ArrayList<> ();
        empTypeList.add("Oil Production");
        empTypeList.add("By Production");
        empTypeList.add("Other");

        ObservableList<String> dataSet = FXCollections.observableArrayList(empTypeList);
        cmbEmployeetype.setItems(dataSet);

    }

    private void loadSchIds() {
        try {
            ObservableList<String> obList = FXCollections.observableArrayList();
            List<String> schIDs = EmployeeModel.getSchIDs();

            for (String schId : schIDs) {
                obList.add(schId);
            }
            cmbEmployeeSchId.setItems(obList);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
        }
    }

    @FXML
    void btnAddEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnSearchEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void txtEmployeeIdOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) {

    }

    @FXML
    void btnEditScheduleDetailsOnAction(ActionEvent event) {

    }

}
