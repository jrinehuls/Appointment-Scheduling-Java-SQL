package controller;

import dataAccess.DBAppointments;
import dataAccess.DBContacts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.ResourceBundle;
/** This class is for showing various reports on the data in the database. */
public class ReportsScreenController implements Initializable {

//--------------------------------------FXML components for month and type total----------------------------------------
    /** A combo box for storing and selecting months */
    public ComboBox<String> monthCB;
    /** A combo box for storing and selecting types */
    public ComboBox<String> typeCB;
    /** A label to display the total appointments of selected type and month */
    public Label totalLabel;
//--------------------------------------FXML components for contact appointments----------------------------------------
    /** TableView for appointments. */
    public TableView<Appointments> appointmentsTable;
    /** Column for the appointment IDs in the appointments table. */
    public TableColumn<Appointments, Integer> apptIdCol;
    /** Column for the titles in the appointments table. */
    public TableColumn<Appointments, String> titleCol;
    /** Column for the descriptions in the appointments table. */
    public TableColumn<Appointments, String> descriptionCol;
    /** Column for the types in the appointments table. */
    public TableColumn<Appointments, String> typeCol;
    /** Column for the start times in the appointments table. */
    public TableColumn<Appointments, LocalDateTime> startCol;
    /** Column for the end times in the appointments table. */
    public TableColumn<Appointments, LocalDateTime> endCol;
    /** Column for the customer IDs in the appointments table. */
    public TableColumn<Appointments, Integer> custIdCol;
    /** A combo box for storing and selecting contacts */
    public ComboBox<Contacts> contactsCB;
//--------------------------------------FXML components for outdated appointments---------------------------------------
    /** TableView for outdated appointments. */
    public TableView<Appointments> oldApptsTable;
    /** Column for the appointment IDs in the outdated appointments table. */
    public TableColumn<Appointments, Integer> oldApptIdCol;
    /** Column for the titles in the outdated appointments table. */
    public TableColumn<Appointments, String> oldTitleCol;
    /** Column for the descriptions in the outdated appointments table. */
    public TableColumn<Appointments, String> oldDescriptionCol;
    /** Column for the locations in the outdated appointments table. */
    public TableColumn<Appointments, String> oldLocationCol;
    /** Column for the types in the outdated appointments table. */
    public TableColumn<Appointments, String> oldTypeCol;
    /** Column for the start dates in the outdated appointments table. */
    public TableColumn<Appointments, LocalDateTime> oldStartCol;
    /** Column for the end dates in the outdated appointments table. */
    public TableColumn<Appointments, LocalDateTime> oldEndCol;
    /** Column for the customer IDs in the outdated appointments table. */
    public TableColumn<Appointments, Integer> oldCustIdCol;
    /** Column for the user IDs in the outdated appointments table. */
    public TableColumn<Appointments, Integer> oldUserIdCol;
    /** Column for the contact IDs in the outdated appointments table. */
    public TableColumn<Appointments, Integer> oldContIdCol;

    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<String> types = DBAppointments.getAllTypes();

    /** initialize determines the initial state of the scene when it gets called.
     For month and type count, the month and type combo boxes are populated with data. For appointments by contact table, the columns are<br>
     assigned to which variable they will be storing for appointments. The table is populated from the database. For outdated appointments<br>
     table, the columns are assigned to which variable they will be storing for appointments. The table is populated from the database.<br>
     using a for loop and checking if the appointment ended before the current local date and time. If so, it is added to the table.
     @param url points to a uniform resource locator (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/net/URL.html"></a>)
     @param resourceBundle contains locale-specific objects (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html"></a>) */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//------------------------------------------------For month and type count----------------------------------------------
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");

        monthCB.setItems(months);
        typeCB.setItems(types);
//---------------------------------------For appointments by contact table----------------------------------------------
        apptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        typeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        custIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        contactsCB.setItems(DBContacts.getAllContacts());
//----------------------------------------For outdated appointments table-----------------------------------------------
        oldApptIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        oldTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        oldDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        oldLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
        oldTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
        oldStartCol.setCellValueFactory(new PropertyValueFactory<>("start"));
        oldEndCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        oldCustIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        oldUserIdCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
        oldContIdCol.setCellValueFactory(new PropertyValueFactory<>("contactId"));

        ObservableList<Appointments> oldAppointments = FXCollections.observableArrayList();
        ObservableList<Appointments> allAppointments = DBAppointments.getAllAppointments();
        for(Appointments appointment : allAppointments) {
            if(appointment.getEnd().isBefore(LocalDateTime.now())){
                oldAppointments.add(appointment);
            }
        }
        oldApptsTable.setItems(oldAppointments);

    }
    /** Calculates total appointments of a certain type during a specified month and displays it in a label.
     * @param actionEvent calls method when a selection is made from the type or month combo box. */
    @FXML
    public void calcTotal(ActionEvent actionEvent) {
        String month = monthCB.getValue();
        String type = typeCB.getValue();
        int count = DBAppointments.monthAndTypeMatches(month, type);
        totalLabel.setText(String.valueOf(count));
    }

    /** Sets appointments in table based on contact selected from the contacts combo box.
     * stores selected contact to the contact variable which is passed into the constructor of the overloaded getAllAppointments<br>
     * method. The returned appointments are stored in an observable list and set to the table.
     * @param actionEvent calls method when a selection is made from the contacts combo box.
     */
    @FXML
    public void selectContactAppointments(ActionEvent actionEvent) {
        Contacts contact = contactsCB.getValue();
        ObservableList<Appointments> appointments = DBAppointments.getAllAppointments(contact);
        appointmentsTable.setItems(appointments);
    }
    /** This method sends users to the main screen.
     * @param actionEvent calls method when cancel button is pressed */
    @FXML
    public void toMain(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

}
