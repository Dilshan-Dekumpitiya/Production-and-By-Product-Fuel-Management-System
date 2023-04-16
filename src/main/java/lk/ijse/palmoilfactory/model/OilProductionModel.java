package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class OilProductionModel {

    public static String getOilQtyOnHand() throws SQLException, ClassNotFoundException {

        String sql="SELECT totalQty from totaloilqty";
        ResultSet resultSet= CrudUtil.execute(sql);

        if(resultSet.next()) {
            return (resultSet.getString(1));
        }
        return "-1";
    }

    public static boolean updateQty(double qty) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE totaloilqty SET totalQty = (totalQty - ?) ";

        return CrudUtil.execute(sql,qty);

    }
}
