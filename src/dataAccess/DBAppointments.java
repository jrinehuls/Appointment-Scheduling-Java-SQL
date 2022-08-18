package dataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import utility.JDBC;
import utility.UsefulMethods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;


public class DBAppointments {

    public static ObservableList<Appointments> getAllAppointments() {
        ObservableList<Appointments> appointmentsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM appointments;";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
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

                //System.out.println(UsefulMethods.toEasternTime(start));

                Appointments appointment = new Appointments(appointmentId, title, description, location, type, start, end, customerId, userId, contactId);
                appointmentsList.add(appointment);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return appointmentsList;
    }
}
