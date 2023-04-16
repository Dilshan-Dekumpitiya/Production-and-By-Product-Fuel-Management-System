package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {

    public static boolean addStock(String stockId, int ffbInput, String date, String time ,String supId) throws SQLException, ClassNotFoundException {

        String sql="INSERT INTO ffbstock(stockId,ffbInput,date,time,supId)"+"VALUES(?,?,?,?,?)";

        return CrudUtil.execute(sql,stockId,ffbInput,date,time,supId);
    }

    public static Stock searchStock(String stockId) throws SQLException, ClassNotFoundException {

        String sql="SELECT * FROM ffbstock WHERE stockId=?";

        ResultSet resultSet = CrudUtil.execute(sql, stockId);

        if(resultSet.next()) {
            String  stkId = resultSet.getString(1);
            int ffbInput = resultSet.getInt(2);
            String date = resultSet.getString(3);
            String time = resultSet.getString(4);
            String supId = resultSet.getString(5);

            return new Stock(stkId,ffbInput, date,time,supId);
        }
        return null;
    }

    public static boolean updateStock(String stockId, int ffbInput, String date, String time, String supId) throws SQLException, ClassNotFoundException {

        String sql="UPDATE ffbstock SET ffbInput = ?, date = ?, time = ? , supId = ? WHERE stockId = ?";

        return CrudUtil.execute(sql,ffbInput,date,time,supId,stockId);

    }

    public static boolean deleteStock(String stockId) throws SQLException, ClassNotFoundException {

        String sql="DELETE FROM ffbstock WHERE stockId=? ";

        return CrudUtil.execute(sql,stockId);
    }

    public static String searchByStockIdSupId(String stockId) throws SQLException, ClassNotFoundException {

        String sql="SELECT supId FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("supId");
        }
        return null;
    }

    public static int searchByStockIdFFBInput(String stockId) throws SQLException, ClassNotFoundException {

        String sql="SELECT ffbInput from ffbstock WHERE stockId = ? ";

        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getInt("ffbInput");
        }
        return 0;
    }

    public static String searchByStockIdDate(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT date FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("date");
        }
        return null;

    }

    public static String searchByStockIdTime(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT time FROM ffbstock WHERE stockId = ?";

        ResultSet resultSet=CrudUtil.execute(sql,stockId);

        if(resultSet.next()){
            return resultSet.getString("time");
        }
        return null;

    }

    public static double getTotalFFBInput() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(ffbInput) AS total FROM ffbstock";

        ResultSet resultSet=CrudUtil.execute(sql);

        if(resultSet.next()){
            return Double.parseDouble(resultSet.getString("total"));
        }
        return -1;

    }
}
