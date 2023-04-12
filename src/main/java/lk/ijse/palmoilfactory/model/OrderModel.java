package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.dto.Orders;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    public static String generateNextOrderId() throws SQLException, ClassNotFoundException {

        String sql = "SELECT orderId FROM Orders ORDER BY orderId DESC LIMIT 1";

        ResultSet resultSet = CrudUtil.execute(sql);
        if(resultSet.next()) {
            return splitOrderId(resultSet.getString(1));
        }
        return splitOrderId(null);
    }
    public static String splitOrderId(String currentOrderId) {
        if(currentOrderId != null) {
            String[] strings = currentOrderId.split("OD");
            int id = Integer.parseInt(strings[1]);
            id++;

            return "OD"+id;
        }
        return "OD1";
    }

    public static List<Orders> getAll() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders";

        List<Orders> orderData = new ArrayList<>();

        ResultSet resultSet = CrudUtil.execute(sql);
        while (resultSet.next()) {
            orderData.add(new Orders(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getDouble(3),
                    resultSet.getDouble(4)
            ));
        }
        return orderData;

    }
}
