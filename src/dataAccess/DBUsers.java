package dataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;
import model.Users;
import utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBUsers {

    public static ObservableList<Users> getAllUsers() {
        ObservableList<Users> usersList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM users;";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");

                Users user = new Users(userId, userName, password);
                usersList.add(user);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return usersList;
    }
}
