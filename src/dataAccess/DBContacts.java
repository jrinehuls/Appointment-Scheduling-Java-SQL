package dataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Contacts;
import utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** This class handles SQL queries regarding contacts. */
public class DBContacts {

    /** This method returns all contacts in the database in an ObservableList.
     * An SQL query returns all contacts and columns for contacts. A new contact instance is created for each<br>
     * contact record using a while loop. The contacts are added to an ObservableList.
     * @return ObservableList of contacts */
    public static ObservableList<Contacts> getAllContacts() {
        ObservableList<Contacts> contactsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts;";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int contactId = rs.getInt("Contact_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");

                Contacts contact = new Contacts(contactId, contactName, contactEmail);
                contactsList.add(contact);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return contactsList;
    }

    /** This method returns one selected contact from the database.
     * An SQL query returns all contacts and contact columns. A while loop iterates through all contacts to find a match. A match is made<br>
     * when the contact ID of the appointment matches the contact ID of a contact in the database. That contact is returned.
     * @param appointment The appointment for which to find its associated contact
     * @return the contact associated with the selected appointment.
     * */
    public static Contacts getSelectedContact(Appointments appointment) {
        String sql = "SELECT * FROM contacts;";
        Contacts contact = null;
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int contactId = rs.getInt("Contact_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String contactName = rs.getString("Contact_Name");
                String contactEmail = rs.getString("Email");
                if(contactId == appointment.getContactId()) {
                    contact = new Contacts(contactId, contactName, contactEmail);
                    break;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return contact;
    }
}
