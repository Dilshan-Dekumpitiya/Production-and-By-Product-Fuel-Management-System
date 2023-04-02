package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.db.DBConnection;
import lk.ijse.palmoilfactory.dto.Supplier;
import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierModel {
    public static boolean addSupplier(String supId,String supName,String supAddress,String supContact) throws SQLException, ClassNotFoundException {
        String sql="INSERT INTO supplier(supId,name,address,contact)"+"VALUES(?,?,?,?)";

        return CrudUtil.execute(sql, supId, supName, supAddress, supContact);
    }

    public static Supplier searchSupplier(String id) throws SQLException, ClassNotFoundException {
       // String sql="SELECT * FROM supplier WHERE supId='"+id+"'";
        ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM supplier WHERE supId='"+id+"'");
        if(resultSet.next()){
            return new Supplier(resultSet.getString("supId"),resultSet.getString("name"),resultSet.getString("address"),resultSet.getString("contact"));
           // return CrudUtil.execute(sql,id);
        }
        return null;
    }

    public static boolean updateSupplier(Supplier supplier) throws SQLException, ClassNotFoundException {
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement("UPDATE supplier SET name = ?, address = ?, contact = ? WHERE supId = ?");

        preparedStatement.setString(1, supplier.getSupName());
        preparedStatement.setString(2, supplier.getSupAddress());
        preparedStatement.setString(3, supplier.getSupContact());
        preparedStatement.setString(4, supplier.getSupId());

        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows>0;

    }

    public static boolean deleteSupplier(String supId) throws SQLException, ClassNotFoundException {
        String sql="DELETE FROM supplier WHERE supId=? ";
        PreparedStatement preparedStatement = DBConnection.getInstance().getConnection().prepareStatement(sql);
        preparedStatement.setString(1,supId);
        return preparedStatement.executeUpdate()>0;
    }

    public static List<String> getIDs() throws SQLException, ClassNotFoundException {
        Connection con = DBConnection.getInstance().getConnection();

        List<String> ids = new ArrayList<>();

        String sql = "SELECT supId FROM supplier";
        ResultSet resultSet = con.createStatement().executeQuery(sql);
        while(resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;
    }
}
