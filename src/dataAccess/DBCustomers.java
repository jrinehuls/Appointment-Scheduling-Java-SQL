package dataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointments;
import model.Customers;
import utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** This class handles SQL queries regarding customers. */
public class DBCustomers {

    /** This method returns all customers in the database in an ObservableList.
     * An SQL query returns all customers and columns for customers. A new customer instance is created for each<br>
     * customer record using a while loop. The customers are added to an ObservableList.
     * @return ObservableList of customers */
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> customersList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customers;";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");

                Customers customer = new Customers(customerId, name, address, postalCode, phone, divisionId);
                customersList.add(customer);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return customersList;
    }

    /** This method returns one selected customer from the database.
     * An SQL query returns all customers and customer columns. A while loop iterates through all customers to find a match. A match is made<br>
     * when the customer ID of the appointment matches the customer ID of a customer in the database. That customer is returned.
     * @param appointment The appointment for which to find its associated customer
     * @return the customer associated with the selected appointment.
     * */
    public static Customers getSelectedCustomer(Appointments appointment) {
        String sql = "SELECT * FROM customers;";
        Customers customer = null;
        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                String name = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");
                if(customerId == appointment.getCustomerId()) {
                    customer = new Customers(customerId, name, address, postalCode, phone, divisionId);
                    break;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return customer;
    }

    /** This method adds a customer to the database.
     * A customer is added to the database with an INSERT statement. The values for the parameter indices are set using the<br>
     * associated getter methods of the Customers class.
     * @param customer the customer to be added
     * @return the number of rows affected by the insertion*/
    public static int addCustomer(Customers customer) throws SQLException {
        String sql = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES(?, ?, ?, ?, ?);";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method modifies a customer to the database.
     * A customer is modified in the database with an UPDATE statement. The values for the parameter indices are set using the<br>
     * associated getter methods of the Customers class.
     * @param customer the customer to be modified
     * @return the number of rows affected by the insertion*/
    public static int modifyCustomer(Customers customer) throws SQLException {
        String sql = "UPDATE customers Set Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Division_ID = ? WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setString(1, customer.getName());
        ps.setString(2, customer.getAddress());
        ps.setString(3, customer.getPostalCode());
        ps.setString(4, customer.getPhone());
        ps.setInt(5, customer.getDivisionId());
        ps.setInt(6, customer.getCustomerId());
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** This method deletes a customer and any associated appointments from the database.
     * A customer and their appointments are deleted from the database with DELETE statements. The values for the parameter indices<br>
     * are set using the getCustomerId method of the Customers class.
     * @param customer the customer to be deleted
     * @return the number of rows affected by the insertion*/
    public static int deleteCustomer(Customers customer) throws SQLException {
        String sql = "DELETE FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ps.setInt(1, customer.getCustomerId());
        String sql2 = "DELETE FROM customers WHERE Customer_ID = ?";
        PreparedStatement ps2 = JDBC.getConnection().prepareStatement(sql2);
        ps2.setInt(1, customer.getCustomerId());
        int rowsAffected = ps2.executeUpdate();
        return rowsAffected;
    }
}
