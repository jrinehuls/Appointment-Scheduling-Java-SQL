package controller;

import dataAccess.DBAppointments;
import dataAccess.DBCustomers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Customers;
import utility.UsefulMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {


//----------------------------Customers stuff---------------------------------------------------------------------
    public TableView<Customers> customersTable;
    public TableColumn<Customers, Integer> custCustIdCol;
    public TableColumn<Customers, String> custCustNameCol;
    public TableColumn<Customers, String> custAddressCol;
    public TableColumn<Customers, String> custPostalCodeCol;
    public TableColumn<Customers, String> custPhoneCol;
    public TableColumn<Customers, Integer> custDivisionIdCol;
//----------------------------Appointments stuff---------------------------------------------------------------------
    public TableView<Appointments> appointmentsTable;
    public TableColumn<Appointments, Integer> appAppIdCol;
    public TableColumn<Appointments, String> appTitleCol;
    public TableColumn<Appointments, String> appDescriptionCol;
    public TableColumn<Appointments, String> appLocationCol;
    public TableColumn<Appointments, String> appTypeCol;
    public TableColumn<Appointments, LocalDateTime> appStartCol; //This should not be a string
    public TableColumn<Appointments, LocalDateTime> appEndCol; //This should not be a string
    public TableColumn<Appointments, Integer> appCustIdCol;
    public TableColumn<Appointments, Integer> appUserIdCol;
    public TableColumn<Appointments, Integer> appContactIdCol;
    public ToggleGroup toggleGroup;

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

    public void addCustomerScene(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

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

    public void deleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customers customer = customersTable.getSelectionModel().getSelectedItem();
        String name = customer.getName();
        if(UsefulMethods.isDeletable(customer)){
            DBCustomers.deleteCustomer(customer);
            customersTable.setItems(DBCustomers.getAllCustomers());
            UsefulMethods.confirmation(name);
        }
    }

    public void addAppointmentScreen(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }
}
