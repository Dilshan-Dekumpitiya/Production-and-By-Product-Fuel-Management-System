package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.palmoilfactory.dto.Orders;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.dto.tm.OrderTM;
import lk.ijse.palmoilfactory.dto.tm.SupplierTM;
import lk.ijse.palmoilfactory.model.OrderModel;
import lk.ijse.palmoilfactory.model.SupplierModel;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {
    @FXML
    private JFXTextField txtQty;

    @FXML
    private JFXTextField txtPrice;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOilQuantityOnHand;

    @FXML
    private TableView<OrderTM> tblOrderDetails;

    @FXML
    private TableColumn<?, ?> colOrderId;

    @FXML
    private TableColumn<?, ?> colOrderDate;

    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colPrice;

    @FXML
    private DatePicker dtpckrOrdersDate;

    private ObservableList<OrderTM> obList = FXCollections.observableArrayList();

    private String text="";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setOrderDate();
        generateNextOrderId();
        setCellValueFactory();
        getOrderDetailToTable(text);

        dtpckrOrdersDate.setOnAction(actionEvent -> { //Add action listener to dtpckrOrdersDate to search and display table
            tblOrderDetails.getItems().clear();
            text= String.valueOf(dtpckrOrdersDate.getValue());
            getOrderDetailToTable(text);
        });


    }

    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextOrderId() {
        try {
            String nextId = OrderModel.generateNextOrderId();
            lblOrderId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId")); //OrderTM class attributes names
        colOrderDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    private void getOrderDetailToTable(String text) {
        try {
            List<Orders> orderList = OrderModel.getAll();
            for(Orders orders : orderList) {
                if (orders.getOrderDate().contains(text) ){  //Check pass text contains of the supName

                    OrderTM tm=new OrderTM(
                            orders.getOrderId(),
                            orders.getOrderDate(),
                            orders.getQuantity(),
                            orders.getPrice());

                    obList.add(tm);

                }
            }
            tblOrderDetails.setItems(obList);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

    }

    @FXML
    void txtPriceOnAction(ActionEvent event) {

    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }

    @FXML
    void btnClearOnAction(ActionEvent event) {

    }

}
