package lk.ijse.palmoilfactory.model;

import lk.ijse.palmoilfactory.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SteamModel {
    public static List<String> getStockIDs() throws SQLException, ClassNotFoundException {
        List<String> ids = new ArrayList<>();

        String sql = "SELECT stockId FROM ffbstock";

        ResultSet resultSet= CrudUtil.execute(sql);

        while(resultSet.next()) {
            ids.add(resultSet.getString(1));
        }
        return ids;

    }
}
