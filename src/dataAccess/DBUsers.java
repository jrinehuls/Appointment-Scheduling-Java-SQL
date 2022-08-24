package dataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Users;
import utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** This class handles SQL queries regarding users. */
public class DBUsers {

    /** This method returns all users in the database in an ObservableList.
     * An SQL query returns all users and user columns. A new user instance is created for each user record using a while loop.<br>
     * The users are added to an ObservableList.
     * @return ObservableList of users */
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
    /** This method returns one selected user from the database.
     * An SQL query returns all users and user columns. A while loop iterates through all users to find a match. A match is made<br>
     * when the user ID of the appointment matches the user ID of a user in the database. That user is returned.
     * @param appointment The appointment for which to find its associated user
     * @return the user associated with the selected appointment.
     * */
    public static Users getSelectedUser(Appointments appointment) {
        String sql = "SELECT * FROM users;";
        Users user = null;
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int userId = rs.getInt("User_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String userName = rs.getString("User_Name");
                String password = rs.getString("Password");
                if(userId == appointment.getUserId()) {
                    user = new Users(userId, userName, password);
                    break;
                }

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
}
