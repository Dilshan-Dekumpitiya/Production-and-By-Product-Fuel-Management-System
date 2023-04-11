package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.dto.tm.SupplierTM;
import lk.ijse.palmoilfactory.model.SupplierModel;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierDetailsFormController implements Initializable {

    @FXML
    private JFXTextField txtSupplierId;

    @FXML
    private JFXTextField txtSupplierAddress;

    @FXML
    private JFXTextField txtSupplierName;

    @FXML
    private JFXTextField txtSupplierContact;

    @FXML
    private TableColumn<?, ?> colSupId;

    @FXML
    private TableColumn<?, ?> colSupName;

    @FXML
    private TableColumn<?, ?> colSupAddress;

    @FXML
    private TableColumn<?, ?> colSupContact;

    @FXML
    private TableColumn<?, ?> colSupAction;

    @FXML
    private TableView<SupplierTM> tblSupplier;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtSupplierId.requestFocus());
        setCellValueFactory(); //To show table data
        getAllSupplierToTable(); //To get all supplier details to table(Not show)
    }

    void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId"));
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("supAddress"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("supContact"));
        colSupAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    void getAllSupplierToTable() {
        try {
            ObservableList<SupplierTM> obList = FXCollections.observableArrayList();
            List<Supplier> supList = SupplierModel.getAll();
            for(Supplier supplier : supList) {
                Button btn=new Button("Delete");
                SupplierTM tm=new SupplierTM(
                        supplier.getSupId(),
                        supplier.getSupName(),
                        supplier.getSupAddress(),
                        supplier.getSupContact(),btn);
                obList.add(tm);

            }
            tblSupplier.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    @FXML
    void btnAddSupplierOnAction(ActionEvent event) {
        if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Data to Add Supplier").show();
        }else {
            String supId = txtSupplierId.getText();
            String supName = txtSupplierName.getText();
            String supAddress = txtSupplierAddress.getText();
            String supContact = txtSupplierContact.getText();

            boolean isAdded;

            try {
                isAdded = SupplierModel.addSupplier(supId, supName, supAddress, supContact);
                if (isAdded) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added").show();
                    txtSupplierName.clear();
                    txtSupplierId.clear();
                    txtSupplierAddress.clear();
                    txtSupplierContact.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Supplier Not Added Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void btnSearchSupplierOnAction(ActionEvent event) {
        if(txtSupplierId.getText().isEmpty() && txtSupplierName.getText().isEmpty() && txtSupplierAddress.getText().isEmpty() && txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Supplier ID to Search Supplier ").show();
        }else {
            String id = txtSupplierId.getText();
            try {
                Supplier supplier = SupplierModel.searchSupplier(id);

                if (supplier != null) {
                    txtSupplierId.setText(supplier.getSupId());
                    txtSupplierName.setText(supplier.getSupName());
                    txtSupplierAddress.setText(supplier.getSupAddress());
                    txtSupplierContact.setText(supplier.getSupContact());

                } else {
                    new Alert(Alert.AlertType.WARNING, "Supplier Not Found Please Try Again").show();
                }

            } catch (SQLException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();

            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.WARNING, "OOPSSS!! something happened!!!").show();
            }
        }
    }

    @FXML
    void txtSupplierIdOnAction(ActionEvent event) {
        btnSearchSupplierOnAction(event);
    }

    @FXML
    void btnDeleteSupplierOnAction(ActionEvent event) {
        if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Supplier ID and Search Supplier is exist").show();
        }else {
            String supId = txtSupplierId.getText();
            try {

                boolean isDeleted = SupplierModel.deleteSupplier(supId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted Successfully").show();
                    txtSupplierName.clear();
                    txtSupplierId.clear();
                    txtSupplierAddress.clear();
                    txtSupplierContact.clear();
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
    void btnUpdateSupplierOnAction(ActionEvent event) {
        if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.CONFIRMATION,"Please Input Supplier ID and Search Supplier is exist").show();
        }else {
            String supId = txtSupplierId.getText();
            String supName = txtSupplierName.getText();
            String supAddress = txtSupplierAddress.getText();
            String supContact = txtSupplierContact.getText();

            boolean isUpdated;

            try {
                isUpdated = SupplierModel.updateSupplier(supId, supName, supAddress, supContact);
                if (isUpdated) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated").show();
                    txtSupplierName.clear();
                    txtSupplierId.clear();
                    txtSupplierAddress.clear();
                    txtSupplierContact.clear();
                } else {
                    new Alert(Alert.AlertType.WARNING, "Supplier Not Updated Please Try Again").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            } catch (ClassNotFoundException e) {
                new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
        }
    }
}
