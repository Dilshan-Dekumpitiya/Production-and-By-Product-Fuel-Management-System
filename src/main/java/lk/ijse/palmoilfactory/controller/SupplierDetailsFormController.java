package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.dto.tm.SupplierTM;
import lk.ijse.palmoilfactory.model.SupplierModel;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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

    private ObservableList<SupplierTM> obList = FXCollections.observableArrayList();

    @FXML
    private JFXButton btnSaveSupplier;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> txtSupplierId.requestFocus());
        setCellValueFactory(); //To show table data
        getAllSupplierToTable(); //To get all supplier details to table(Not show)

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> { //Add ActionListener to selected column and display text field values
            //Check select value is not null
            if(null!=newValue) { //newValue!=null --> Get more time to compare (newValue object compare)
                btnSaveSupplier.setText("Update Supplier");
                setDataToTextFields(newValue); //Set data to text field of selected row data of table
            }
        });
    }

    private void setDataToTextFields(SupplierTM supplierTM) {
        txtSupplierId.setText(supplierTM.getSupId());
        txtSupplierName.setText(supplierTM.getSupName());
        txtSupplierAddress.setText(supplierTM.getSupAddress());
        txtSupplierContact.setText(supplierTM.getSupContact());
    }

    void setCellValueFactory() {
        colSupId.setCellValueFactory(new PropertyValueFactory<>("supId")); //SupplierTM class attributes names
        colSupName.setCellValueFactory(new PropertyValueFactory<>("supName"));
        colSupAddress.setCellValueFactory(new PropertyValueFactory<>("supAddress"));
        colSupContact.setCellValueFactory(new PropertyValueFactory<>("supContact"));
        colSupAction.setCellValueFactory(new PropertyValueFactory<>("btn"));
    }

    void getAllSupplierToTable() {
        try {

            List<Supplier> supList = SupplierModel.getAll();
            for(Supplier supplier : supList) {
                JFXButton btnDel=new JFXButton("Delete");
                btnDel.setAlignment(Pos.CENTER);
                btnDel.setStyle("-fx-background-color: #686de0; ");
                btnDel.setCursor(Cursor.HAND);

                SupplierTM tm=new SupplierTM(
                        supplier.getSupId(),
                        supplier.getSupName(),
                        supplier.getSupAddress(),
                        supplier.getSupContact(),btnDel);

                obList.add(tm);

                setDeleteButtonTableOnAction(btnDel);

            }

            tblSupplier.setItems(obList);


        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    private void setDeleteButtonTableOnAction(JFXButton btnDel) {
        btnDel.setOnAction((e) -> {
            ButtonType yes = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> buttonType = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete?", yes, no).showAndWait();

            if (buttonType.get() == yes) {
                txtSupplierId.setText(tblSupplier.getSelectionModel().getSelectedItem().getSupId());
                int index = tblSupplier.getSelectionModel().getSelectedIndex();
                obList.remove(index);
                btnSearchSupplierOnAction(e);
                btnDeleteSupplierOnAction(e);
                tblSupplier.refresh();

            }

        });
    }

    @FXML
    void btnSaveSupplierOnAction(ActionEvent event) {
        if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Data to Add Supplier").show();
        }else {
            String supId = txtSupplierId.getText();
            String supName = txtSupplierName.getText();
            String supAddress = txtSupplierAddress.getText();
            String supContact = txtSupplierContact.getText();

            boolean isAdded;

            if(btnSaveSupplier.getText().equalsIgnoreCase("Save Supplier")){
                try {
                    isAdded = SupplierModel.addSupplier(supId, supName, supAddress, supContact);
                    if (isAdded) {
                        new Alert(Alert.AlertType.CONFIRMATION, "Supplier Added").show();
                        clearFields();

                    } else {
                        new Alert(Alert.AlertType.WARNING, "Supplier Not Added Please Try Again").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
                } catch (ClassNotFoundException e) {
                    new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
                }

            }else{
                if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
                    new Alert(Alert.AlertType.CONFIRMATION,"Please Input Supplier ID and Search Supplier is exist").show();
                }else {
                    String suppId = txtSupplierId.getText();
                    String suppName = txtSupplierName.getText();
                    String suppAddress = txtSupplierAddress.getText();
                    String suppContact = txtSupplierContact.getText();

                    boolean isUpdated;

                    try {
                        isUpdated = SupplierModel.updateSupplier(suppId, suppName, suppAddress, suppContact);
                        if (isUpdated) {
                            new Alert(Alert.AlertType.CONFIRMATION, "Supplier Updated").show();
                            clearFields();

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

        txtSupplierId.setText(tblSupplier.getSelectionModel().getSelectedItem().getSupId());
        if(txtSupplierId.getText().isEmpty() || txtSupplierName.getText().isEmpty() || txtSupplierAddress.getText().isEmpty() || txtSupplierContact.getText().isEmpty()){
            new Alert(Alert.AlertType.WARNING,"Please Input Supplier ID and Search Supplier is exist").show();
        }else {
            String supId = txtSupplierId.getText();
            try {

                boolean isDeleted = SupplierModel.deleteSupplier(supId);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "Supplier Deleted Successfully").show();
                    clearFields();

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

    private void clearFields(){
        txtSupplierName.clear();
        txtSupplierId.clear();
        txtSupplierAddress.clear();
        txtSupplierContact.clear();
    }
    @FXML
    void btnClearOnAction(ActionEvent event) {
        clearFields();
        btnSaveSupplier.setText("Save Supplier");
    }
}
