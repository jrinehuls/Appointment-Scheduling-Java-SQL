package controller;

import dataAccess.DBAppointments;
import dataAccess.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import utility.Confirmation;
import utility.UsefulMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
/** This class is the central hub of this project. It displays tables for customer and appointments. <br>
 * from here, customers and appointments can be added modified, and deleted. Also, special reports can be accessed.*/
public class MainScreenController implements Initializable {

//----------------------------Customers stuff---------------------------------------------------------------------
    /** TableView for customers. */
    public TableView<Customers> customersTable;
    /** Column for the customer IDs in the customers table. */
    public TableColumn<Customers, Integer> custCustIdCol;
    /** Column for the customer names in the customers table. */
    public TableColumn<Customers, String> custCustNameCol;
    /** Column for the customer addresses in the customers table. */
    public TableColumn<Customers, String> custAddressCol;
    /** Column for the customer postal codes in the customers table. */
    public TableColumn<Customers, String> custPostalCodeCol;
    /** Column for the customer phone numbers in the customers table. */
    public TableColumn<Customers, String> custPhoneCol;
    /** Column for the customer first level divisions in the customers table. */
    public TableColumn<Customers, Integer> custDivisionIdCol;
//----------------------------Appointments stuff---------------------------------------------------------------------
    /**TableView for appointments. */
    public TableView<Appointments> appointmentsTable;
    /** Column for the appointment IDs in the appointments table. */
    public TableColumn<Appointments, Integer> appAppIdCol;
    /** Column for the appointment titles in the appointments table. */
    public TableColumn<Appointments, String> appTitleCol;
    /** Column for the appointment descriptions in the appointments table. */
    public TableColumn<Appointments, String> appDescriptionCol;
    /** Column for the appointment locations in the appointments table. */
    public TableColumn<Appointments, String> appLocationCol;
    /** Column for the appointment types in the appointments table. */
    public TableColumn<Appointments, String> appTypeCol;
    /** Column for the appointment start dates and times in the appointments table. */
    public TableColumn<Appointments, LocalDateTime> appStartCol;
    /** Column for the appointment end dates and times in the appointments table. */
    public TableColumn<Appointments, LocalDateTime> appEndCol;
    /** Column for the customer IDs in the appointments table. */
    public TableColumn<Appointments, Integer> appCustIdCol;
    /** Column for the user IDs in the appointments table. */
    public TableColumn<Appointments, Integer> appUserIdCol;
    /** Column for the contact IDs in the appointments table. */
    public TableColumn<Appointments, Integer> appContactIdCol;
    /** Groups radio buttons together. */
    public ToggleGroup toggleGroup;
    /** Radio button to select appointments for this week. */
    public RadioButton weekAppointmentsRadio;
    /** Radio button to select appointments for this month. */
    public RadioButton monthAppointmentsRadio;
    /** Radio button to select all appointments. */
    public RadioButton allAppointmentsRadio;


    /** initialize determines the initial state of the scene when it gets called.
     All customers are added to the customers table and all appointments are added to the appointments table.<br>
     @param url points to a uniform resource locator (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/net/URL.html"></a>)
     @param resourceBundle contains locale-specific objects (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html"></a>) */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//----------------------------Customers stuff---------------------------------------------------------------------
        ObservableList<Customers> customers = DBCustomers.getAllCustomers();
        customersTable.setItems(customers);

        custCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custCustNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        custAddressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        custPostalCodeCol.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custPhoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custDivisionIdCol.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
//----------------------------Appointments stuff---------------------------------------------------------------------
        ObservableList<Appointments> appointments = DBAppointments.getAllAppointments();
        appointmentsTable.setItems(appointments);

        appAppIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        appDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        appLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        appTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        appStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        appEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        appCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appContactIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

    }
    /** This method pulls up the Add Customer form.
     * @param actionEvent called when the add customer button is clicked.*/
    public void addCustomerScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }
    /** This method pulls up the Modify Customer form. Gets the selected customer, if one is selected, and<br>
     * Passes it to the ModifyCustomerScreenController to modify selected customer.
     * @param actionEvent called when the modify customer button is clicked.*/
    public void modifyCustomerScene(ActionEvent actionEvent) throws IOException {
//----------------------<To pass selected customer to modify customer scene>--------------------------------------------
        Customers customer = customersTable.getSelectionModel().getSelectedItem();
        if(customer != null) {
            ModifyCustomerScreenController.receiveCustomer(customer);
//----------------------</To pass selected customer to modify customer scene>-------------------------------------------
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomerScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        }
    }
    /**This method deletes customers. It gets the highlighted customer from the table and saves the name to a variable<br>
     * Calls the boolean return method "isDeletable" from the usefulMethods class to make sure the selection is not null<br>
     * This method calls the deleteCustomer method from DBCustomers which deletes appointments associated to that customer.<br>
     * The user confirms to delete the customer and associated appointments and the customer is deleted. The table reflects the remaining customers.<br>
     * <p><b>Lambda expression - The functional interface "Confirmation" has its confirmation method overridden<br>
     * to accepts a name parameter. The "confirmed" variable is assigned to the Confirmation interface instance. nameyPoo<br>
     * is the argument for the lambda expression. The instance calls the confirmation method with the customer's name as an argument<br>
     * A pop-up shows confirmation that the customer has been deleted.
     * </b></p>
     * getSelectedRadio is called to show the updated appointments table based on the selected radio button. It refreshes, basically.
     * @param actionEvent calls method when delete appointment button is pressed*/
    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customers customer = customersTable.getSelectionModel().getSelectedItem();
        final String name = customer.getName();
        if(UsefulMethods.isDeletable(customer)){
            DBCustomers.deleteCustomer(customer);
            customersTable.setItems(DBCustomers.getAllCustomers());
            //--------------------Here is a lambda expression-------------------
            Confirmation confirmed = (nameyPoo) -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Customer Deleted");
                alert.setHeaderText(nameyPoo + " has been deleted.");
                alert.setContentText("");
                alert.showAndWait();
            };
            confirmed.confirmation(name);
        }
        getSelectedRadio(actionEvent);
    }
    /** This method pulls up the Add appointment form.
     * @param actionEvent called when the add appointment button is clicked.*/
    public void addAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    /** This method pulls up the Modify Appointment form. Gets the selected appointment, if one is selected, and<br>
     * Passes it to the ModifyAppointmentScreenController to modify selected appointment.
     * @param actionEvent called when the modify appointment button is clicked.*/
    public void modifyAppointmentScreen(ActionEvent actionEvent) throws IOException {
//----------------------<To pass selected customer to modify customer scene>--------------------------------------------
        Appointments appointment = appointmentsTable.getSelectionModel().getSelectedItem();
        if(appointment != null) {
            ModifyAppointmentScreenController.receiveAppointment(appointment);
//----------------------</To pass selected customer to modify customer scene>-------------------------------------------
            Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointmentScreen.fxml"));
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setTitle("Modify Customer");
            stage.setScene(scene);
            stage.show();
        }
    }
    /**This method deletes appointments. It gets the highlighted appointment from the table and saves the id and type to variables<br>
     * Calls the boolean return method "isDeletable" from the usefulMethods class to make sure the selection is not null<br>
     * A pop-up is shown to confirm deletion. If ok is clicked. The appointment is removed from the database. The table is repopulated<br>
     * with appointments and a confirmation pop-up is displayed.
     * @param actionEvent calls method when delete appointment button is pressed*/
    public void deleteAppointment(ActionEvent actionEvent) throws SQLException {
        Appointments appointment = appointmentsTable.getSelectionModel().getSelectedItem();
        final int id = appointment.getAppointmentId();
        final String type = appointment.getType();
        if(UsefulMethods.isDeletable(appointment)){
            DBAppointments.deleteAppointment(appointment);
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
            UsefulMethods.confirmation(id, type);
        }
    }
    /** This method updates the appointments table. What is shown is determined by the radio button being selected<br>
     * appointments can be shown for the week, the month, or all of them.
     * @param actionEvent called when radio buttons are selected.*/
    public void getSelectedRadio(ActionEvent actionEvent) {
        if(weekAppointmentsRadio.isSelected()) {
            appointmentsTable.setItems(DBAppointments.getWeekAppointments());
        }
        else if(monthAppointmentsRadio.isSelected()){
            appointmentsTable.setItems(DBAppointments.getMonthAppointments());
        }
        else {
            appointmentsTable.setItems(DBAppointments.getAllAppointments());
        }
    }
    /** This method pulls up the Reports form. This form shows reports not seen in the Main Screen<br>
     * @param actionEvent called when the modify customer button is clicked.*/
    public void toReportsScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportsScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Reports Screen");
        stage.setScene(scene);
        stage.show();
    }
}
