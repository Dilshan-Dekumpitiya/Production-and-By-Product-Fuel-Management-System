package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;


public class OilProductionModel {

    public static String getUpdatedOilqty() throws SQLException, ClassNotFoundException {

        String sql="SELECT totalQty from totaloilqty";
        ResultSet resultSet= CrudUtil.execute(sql);

        if(resultSet.next()) {
            return (resultSet.getString(1));
        }
        return "-1";
    }

    public static boolean subtractionOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE totaloilqty SET totalQty = (totalQty - ?) ";

        return CrudUtil.execute(sql,qty);

    }

    public static void subtractionOilQty(double qty) throws SQLException, ClassNotFoundException {

        String sql = "UPDATE totaloilqty SET totalQty = (totalQty - ?) ";

         CrudUtil.execute(sql,qty);

    }

    public static void addOilQtyTototalOil(double qty) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE totaloilqty SET totalQty = (totalQty + ?) ";

        CrudUtil.execute(sql,qty);
    }

}
