package lk.ijse.palmoilfactory.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.palmoilfactory.dto.Orders;
import lk.ijse.palmoilfactory.dto.tm.OrderTM;
import lk.ijse.palmoilfactory.model.OilProductionModel;
import lk.ijse.palmoilfactory.model.OrderModel;

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
        Platform.runLater(() -> txtQty.requestFocus());

        setOrderDate();
        generateNextOrderId();
        setCellValueFactory(); //To show table data
        getOrderDetailToTable(text);  //To get all orders details to table(Not show)

        String totaloil = OilProductionFormController.getUpdatedOilQuantity();
        lblOilQuantityOnHand.setText(totaloil);

        dtpckrOrdersDate.setOnAction(actionEvent -> { //Add action listener to dtpckrOrdersDate to search and display table
            tblOrderDetails.getItems().clear();
            text= String.valueOf(dtpckrOrdersDate.getValue());
            getOrderDetailToTable(text);
        });

    }

    /*private void calculateOilProductionqty() {
        try {
            String oilQty = OilProductionModel.getOilQtyOnHand();
            lblOilQuantityOnHand.setText(oilQty);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened!").show();
        }
    }*/

    private void setOrderDate() {
        lblOrderDate.setText(String.valueOf(LocalDate.now()));
    }

    private void generateNextOrderId() {
        try {
            String nextId = OrderModel.generateNextOrderId();
            lblOrderId.setText(nextId);
        } catch (SQLException | ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, "Something Happened!").show();
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
                if (orders.getOrderDate().contains(text) ){  //Check pass text contains of the order date

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
            new Alert(Alert.AlertType.ERROR, "Query error!").show();
        }
    }

    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {
        String orderId = lblOrderId.getText();
        String orderDate = lblOrderDate.getText();
        double qty = Double.parseDouble(txtQty.getText());
        double price = Double.parseDouble(txtPrice.getText());

        boolean isPlaced;

            try {

                isPlaced = OrderModel.placeOrder(orderId, orderDate, qty, price); //transaction --> autoCommit(false)
                if (isPlaced) {
                    tblOrderDetails.getItems().clear();
                    new Alert(Alert.AlertType.CONFIRMATION, "Order Added and Place Order").show();
                    clearFields();
                    txtQty.requestFocus();
                    generateNextOrderId();
                    OilProductionFormController.getUpdatedOilQuantity();

                    getOrderDetailToTable("");

                } else {
                    new Alert(Alert.AlertType.WARNING, "Order Not Added Please Try Again").show();
                }
            } catch (SQLException e) {
                   new Alert(Alert.AlertType.ERROR, "OOPSSS!! something happened!!!").show();
            }
    }

    private void clearFields() {
        txtQty.clear();
        txtPrice.clear();
    }

    @FXML
    void txtPriceOnAction(ActionEvent event) {
        btnPlaceOrderOnAction(event);
    }

    @FXML
    void txtQtyOnAction(ActionEvent event) {
        txtPrice.requestFocus();
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        txtQty.clear();
        txtPrice.clear();
        dtpckrOrdersDate.getEditor().clear();
        tblOrderDetails.getItems().clear();
        getOrderDetailToTable("");
        txtQty.requestFocus();
    }
}
