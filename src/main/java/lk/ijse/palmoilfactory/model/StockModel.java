package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Stock;
import lk.ijse.palmoilfactory.dto.Supplier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StockModel {
    public static boolean addStock(Stock stock) throws SQLException, ClassNotFoundException {

        String sql="INSERT INTO ffbstock(stockId,ffbInput,date,time,supId)"+"VALUES(?,?,?,?,?)";

        Connection connection = DBConnection.getInstance().getConnection(); //Singleton use

        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,stock.getStockId() );
        preparedStatement.setInt(2, stock.getFfbInput());
        preparedStatement.setString(3, stock.getDate());
        preparedStatement.setString(4,stock.getTime());
        preparedStatement.setString(5,stock.getSupId());

        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows>0;
    }

    public static Stock searchStock(String stockId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM ffbstock WHERE stockId='"+stockId+"'");
        if(resultSet.next()){
            return new Stock(resultSet.getString("stockId"),resultSet.getInt("ffbInput"),resultSet.getString("date"),resultSet.getString("time"),resultSet.getString("supId"));
        }
        return null;
    }

    public static boolean updateStock(Stock stock) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE ffbstock SET ffbInput = ?, date = ?, time = ? , supId = ? WHERE stockId = ?");

        preparedStatement.setInt(1, stock.getFfbInput());
        preparedStatement.setString(2, stock.getDate());
        preparedStatement.setString(3, stock.getTime());
        preparedStatement.setString(4, stock.getSupId());
        preparedStatement.setString(5, stock.getStockId());

        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows>0;
    }

    public static String searchByStockId(String stockId) throws SQLException, ClassNotFoundException {
        String sql="SELECT supId FROM ffbstock WHERE stockId = ?";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,stockId);
        ResultSet resultSet = preparedStatement.executeQuery();
        if(resultSet.next()){
            return resultSet.getString("supId");
        }
        return null;
    }

    public static boolean deleteStock(String stockId) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM ffbstock WHERE stockId=? ";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,stockId);
        return preparedStatement.executeUpdate()>0;
    }
}
