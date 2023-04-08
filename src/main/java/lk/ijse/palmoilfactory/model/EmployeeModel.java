package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeModel {
    public static List<String> getSchIDs() throws SQLException, ClassNotFoundException {
        List<String> schIds = new ArrayList<>();

        String sql = "SELECT schId FROM schedule";

        ResultSet resultSet= CrudUtil.execute(sql);

        while(resultSet.next()) {
            schIds.add(resultSet.getString(1));
        }
        return schIds;
    }

}
