package dataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;
import model.Users;
import utility.JDBC;
import utility.UsefulMethods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** This class handles SQL queries regarding appointments. */
public class DBAppointments {

    /** This method returns all appointments in the database in an ObservableList.
     * An SQL query returns all appointments and columns for appointments. A new appointment instance is created for each<br>
     * appointment record using a while loop. The appointments are added to an ObservableList.
     * @return ObservableList of appointments */
    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments;";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTimeStamp = rs.getTimestamp("Start");
                Timestamp endTimeStamp = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                LocalDateTime start = startTimeStamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();

                Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentsList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

//----------------------Overloaded method for getting all appointments for a specific customerId------------------------
    /** This method returns all appointments in the database that are associated to a particular customer in an ObservableList.
     * An SQL query returns all appointments and columns for appointments where the customer ID of the appointment matched that of<br>
     * the customerID parameter. A new appointment instance is created for each appointment record using a while loop. The appointments<br>
     * are added to an ObservableList.
     * @param customerId the ID of the customer whose appointments are returned
     * @return ObservableList of appointments */
    public static ObservableList<Appointments> getAllAppointments(int customerId) {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments Where Customer_ID = ?;";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTimeStamp = rs.getTimestamp("Start");
                Timestamp endTimeStamp = rs.getTimestamp("End");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                LocalDateTime start = startTimeStamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();

                Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentsList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }
//----------------------Overloaded method for getting all appointments for a specific user---------------------------
    /** This method returns all appointments in the database that are associated to a particular user in an ObservableList.
     * An SQL query returns all appointments and columns for appointments where the user ID of the appointment matched that of<br>
     * the user ID of the user parameter. A new appointment instance is created for each appointment record using a while loop. The appointments<br>
     * are added to an ObservableList.
     * @param user the user whose appointments are returned
     * @return ObservableList of appointments */
    public static ObservableList<Appointments> getAllAppointments(Users user) throws SQLException {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments Where User_ID = ?;";

        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, user.getUserId());
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentId = rs.getInt("Appointment_ID"); // could use column number starting from 1 ex: rs.getInt(1);
            String title = rs.getString("Title");
            String description = rs.getString("Description");
            String location = rs.getString("Location");
            String type = rs.getString("Type");
            Timestamp startTimeStamp = rs.getTimestamp("Start");
            Timestamp endTimeStamp = rs.getTimestamp("End");
            int customerId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            int contactId = rs.getInt("Contact_ID");

            LocalDateTime start = startTimeStamp.toLocalDateTime();
            LocalDateTime end = endTimeStamp.toLocalDateTime();

            Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
            appointmentsList.add(appointment);
        }

        return appointmentsList;
    }
//----------------------Overloaded method for getting all appointments for a specific contact---------------------------
    /** This method returns all appointments in the database that are associated to a particular contact in an ObservableList.
     * An SQL query returns all appointments and columns for appointments where the contact ID of the appointment matched that of<br>
     * the contact ID of the contact parameter. A new appointment instance is created for each appointment record using a while loop. The appointments<br>
     * are added to an ObservableList.
     * @param contact the contact whose appointments are returned
     * @return ObservableList of appointments */
    public static ObservableList<Appointments> getAllAppointments(Contacts contact) {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments Where Contact_ID = ?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, contact.getContactId());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTimeStamp = rs.getTimestamp("Start");
                Timestamp endTimeStamp = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                LocalDateTime start = startTimeStamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();

                Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentsList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    /** This method returns all appointments in the database for the current month in an ObservableList.
     * An SQL query returns all appointments and columns for appointments where the month of the appointment matches that of<br>
     * the current month. An hour interval is added or subtracted using the hourDifference method of the UsefulMethods class<br>
     * so that the beginning and end of the month in local time lines up with that of UTC time. A new appointment instance is<br>
     * created for each appointment record using a while loop. The appointments are added to an ObservableList.
     * @return ObservableList of appointments */
    public static ObservableList<Appointments> getMonthAppointments() {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments\n" +
                "WHERE month(Start) = month(date_add(current_timestamp(), interval ? hour)) \n" +
                "and year(Start) = year(date_add(current_timestamp(), interval ? hour))";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, UsefulMethods.hourDifference());
            ps.setInt(2, UsefulMethods.hourDifference());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTimeStamp = rs.getTimestamp("Start");
                Timestamp endTimeStamp = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                LocalDateTime start = startTimeStamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();

                Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentsList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }
    /** This method returns all appointments in the database for the current week in an ObservableList.
     * An SQL query returns all appointments and columns for appointments where the week of the appointment matches that of<br>
     * the current week. An hour interval is added or subtracted using the hourDifference method of the UsefulMethods class<br>
     * so that the beginning and end of the week in local time lines up with that of UTC time. A new appointment instance is<br>
     * created for each appointment record using a while loop. The appointments are added to an ObservableList.
     * @return ObservableList of appointments */
    public static ObservableList<Appointments> getWeekAppointments() {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments\n" +
                "WHERE week(Start) = week(date_add(current_timestamp(), interval ? hour)) \n" +
                "and year(Start) = year(date_add(current_timestamp(), interval ? hour))";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, UsefulMethods.hourDifference());
            ps.setInt(2, UsefulMethods.hourDifference());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int appointmentId = rs.getInt("Appointment_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startTimeStamp = rs.getTimestamp("Start");
                Timestamp endTimeStamp = rs.getTimestamp("End");
                int customerId = rs.getInt("Customer_ID");
                int userId = rs.getInt("User_ID");
                int contactId = rs.getInt("Contact_ID");

                LocalDateTime start = startTimeStamp.toLocalDateTime();
                LocalDateTime end = endTimeStamp.toLocalDateTime();

                Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentsList.add(appointment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointmentsList;
    }

    /** This method adds an appointment to the database.
     * An appointment is added to the database with an INSERT statement. The values for the parameter indices are set using the<br>
     * associated getter methods of the Appointments class.
     * @param appointment the appointment to be added
     * @return the number of rows affected by the insertion*/
    public static int addAppointment(Appointments appointment) throws SQLException {
        String sql = "INSERT INTO appointments (Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        ps.setInt(7, appointment.getCustomerId());
        ps.setInt(8, appointment.getUserId());
        ps.setInt(9, appointment.getContactId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method modifies an appointment to the database.
     * An appointment is modified in the database with an UPDATE statement. The values for the parameter indices are set using the<br>
     * associated getter methods of the Appointments class.
     * @param appointment the appointment to be modified
     * @return the number of rows affected by the insertion*/
    public static int modifyAppointment(Appointments appointment) throws SQLException {
        String sql = "UPDATE appointments Set Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ?\n" +
                " WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, appointment.getTitle());
        ps.setString(2, appointment.getDescription());
        ps.setString(3, appointment.getLocation());
        ps.setString(4, appointment.getType());
        ps.setTimestamp(5, Timestamp.valueOf(appointment.getStart()));
        ps.setTimestamp(6, Timestamp.valueOf(appointment.getEnd()));
        ps.setInt(7, appointment.getCustomerId());
        ps.setInt(8, appointment.getUserId());
        ps.setInt(9, appointment.getContactId());
        ps.setInt(10, appointment.getAppointmentId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method deletes an appointment from the database.
     * An appointment is deleted from the database with DELETE statements. The value for the parameter index<br>
     * is set using the getAppointmentId method of the Appointments class.
     * @param appointment the appointment to be deleted
     * @return the number of rows affected by the insertion*/
    public static int deleteAppointment(Appointments appointment) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, appointment.getAppointmentId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method returns all distinct appointment types in the database in an ObservableList.
     * An SQL query returns all distinct appointment types. A new string instance is created for each<br>
     * appointment type record using a while loop. The types are added to an ObservableList.
     * @return ObservableList of types */
    public static ObservableList<String> getAllTypes() {
        ObservableList<String> types = FXCollections.observableArrayList();
        String sql = "SELECT Distinct Type FROM appointments;";
    try {
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String type = rs.getString("Type");
            types.add(type);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

        return types;
    }

    /** This method returns a number of appointment that match the month and type arguments.
     * An SQL query is executed to find instances of appointments that match both the type and month parameters and calls this<br>
     * column "Count". A while loop iterates through this one record and assigns that value to the count variable which is returned.
     * @param month the month to be searched
     * @param type the type to be searched
     * @return the number of appointment that match the month and type arguments*/
    public static int monthAndTypeMatches(String month, String type) {
        int count = -1;
        String sql = "SELECT count(Start) AS Count\n" +
                "FROM appointments\n" +
                "WHERE monthname(Start) = ? && type = ?;";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, month);
            ps.setString(2, type);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                count = rs.getInt("Count");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}
