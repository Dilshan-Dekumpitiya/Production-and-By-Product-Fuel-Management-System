package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import lk.ijse.palmoilfactory.dto.Employee;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.model.EmployeeModel;
import lk.ijse.palmoilfactory.model.StockModel;
import lk.ijse.palmoilfactory.model.SupplierModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeeDetailsFormController implements Initializable {

    @FXML
    private Pane empdetailsPane;

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
        if(txtEmployeeId.getText().isEmpty() || txtEmployeeName.getText().isEmpty() || txtEmployeeAddress.getText().isEmpty() || txtEmployeeContact.getText().isEmpty() || txtEmployeeSalary.getText().isEmpty() || cmbEmployeetype.getSelectionModel().isEmpty() || cmbEmployeeSchId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Data to Add Employee").show();
        }else {
            String empId = txtEmployeeId.getText();
            String empName = txtEmployeeName.getText();
            String empAddress = txtEmployeeAddress.getText();
            String empContact = txtEmployeeContact.getText();
            double empSalary = Double.parseDouble(txtEmployeeSalary.getText());
            String empType = cmbEmployeetype.getSelectionModel().getSelectedItem();
            String empSchId = cmbEmployeeSchId.getSelectionModel().getSelectedItem();

            boolean isAdded;

            try {
                isAdded = EmployeeModel.addEmployee(empId, empName, empAddress, empContact,empSalary,empType,empSchId);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Added").show();
                    txtEmployeeId.clear();
                    txtEmployeeName.clear();
                    txtEmployeeAddress.clear();
                    txtEmployeeContact.clear();
                    txtEmployeeSalary.clear();
                    cmbEmployeetype.getItems().clear();
                    cmbEmployeeSchId.getItems().clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Employee Not Added Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void btnSearchEmployeeOnAction(ActionEvent event) {
       if(txtEmployeeId.getText().isEmpty() ){
            new Alert(Alert.AlertType.WARNING,"Please Input Employee ID to search Employee").show();
        }else {
            String empId = txtEmployeeId.getText();
            try {
                Employee employee = EmployeeModel.searchEmployee(empId);
                if (employee != null) {
                    txtEmployeeName.setText(String.valueOf(employee.getEmpName()));
                    txtEmployeeAddress.setText(employee.getEmpAddress());
                    txtEmployeeContact.setText(employee.getEmpContact());
                    txtEmployeeSalary.setText(String.valueOf(employee.getEmpSalary()));
                    cmbEmployeetype.setValue(EmployeeModel.searchByempIdEmployeeType(empId));
                    cmbEmployeeSchId.setValue(EmployeeModel.searchByempIdEmployeeSchId(empId));

                } else {
                    new Alert(Alert.AlertType.WARNING, "Employee Not Found Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void txtEmployeeIdOnAction(ActionEvent event) {
        btnSearchEmployeeOnAction(event);
    }

    @FXML
    void btnUpdateEmployeeOnAction(ActionEvent event) {
        if (txtEmployeeId.getText().isEmpty() || txtEmployeeName.getText().isEmpty() || txtEmployeeAddress.getText().isEmpty() || txtEmployeeContact.getText().isEmpty() || txtEmployeeSalary.getText().isEmpty() || cmbEmployeetype.getSelectionModel().isEmpty() || cmbEmployeeSchId.getSelectionModel().isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please Input Employee ID and Search Employee is exist").show();
        } else {
            String empId = txtEmployeeId.getText();
            String empName = txtEmployeeName.getText();
            String empAddress = txtEmployeeAddress.getText();
            String empContact = txtEmployeeContact.getText();
            double empSalary = Double.parseDouble(txtEmployeeSalary.getText());
            String empType = null;
            String empSchId = null;

            if(cmbEmployeetype.getSelectionModel().isEmpty() || cmbEmployeeSchId.getSelectionModel().isEmpty()){
                try {
                    empType = EmployeeModel.searchByempIdEmployeeType(empId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                    empSchId=EmployeeModel.searchByempIdEmployeeSchId(empId);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }else {
                empType=cmbEmployeetype.getSelectionModel().getSelectedItem();
                empSchId=cmbEmployeeSchId.getSelectionModel().getSelectedItem();
            }

            boolean isUpdated;

            try {
                isUpdated = EmployeeModel.updateEmployee(empId, empName, empAddress, empContact, empSalary,empType,empSchId);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Updated").show();
                    txtEmployeeId.clear();
                    txtEmployeeName.clear();
                    txtEmployeeAddress.clear();
                    txtEmployeeContact.clear();
                    txtEmployeeSalary.clear();
                    cmbEmployeetype.getItems().clear();
                    cmbEmployeeSchId.getItems().clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Employee Not Updated Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void btnDeleteEmployeeOnAction(ActionEvent event) {
        if(txtEmployeeId.getText().isEmpty() || txtEmployeeName.getText().isEmpty() || txtEmployeeAddress.getText().isEmpty() || txtEmployeeContact.getText().isEmpty() || txtEmployeeSalary.getText().isEmpty() || cmbEmployeetype.getSelectionModel().isEmpty() || cmbEmployeeSchId.getSelectionModel().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Employee ID and Search Employee is exist").show();
        }else {
            String empId = txtEmployeeId.getText();
            try {

                boolean isDeleted = EmployeeModel.deleteEmployee(empId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Employee Deleted Successfully").show();
                    txtEmployeeId.clear();
                    txtEmployeeName.clear();
                    txtEmployeeAddress.clear();
                    txtEmployeeContact.clear();
                    txtEmployeeSalary.clear();
                    cmbEmployeetype.getItems().clear();
                    cmbEmployeeSchId.getItems().clear();

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

    @FXML
    void btnEditScheduleDetailsOnAction(ActionEvent event) throws IOException {
        TranslateTransition transition = new TranslateTransition();
        Parent load = FXMLLoader.load(getClass().getResource("/view/schedule-details-form.fxml"));
        transition.setNode(load);
        transition.setDuration(Duration.seconds(0.5));

        transition.setToX(390);

        empdetailsPane.getChildren().add(load);

        transition.play();

    }

}
