package controller;

import dataAccess.DBAppointments;
import dataAccess.DBContacts;
import dataAccess.DBCustomers;
import dataAccess.DBUsers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointments;
import model.Contacts;
import model.Customers;
import model.Users;
import utility.UsefulMethods;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
/** This class is for adding appointments to the database. */
public class AddAppointmentScreenController implements Initializable {
    /** A Label to display errors for the date picker */
    public Label dateLabel;
    /** A Label to display errors for the title field */
    public Label titleLabel;
    /** A Label to display errors for the location field */
    public Label locationLabel;
    /** A Label to display errors for the description field */
    public Label descriptionLabel;
    /** A Label to display errors for the times selected */
    public Label timeLabel;
    /** A field for getting title input */
    public TextField titleTextField;
    /** A field for getting location input */
    public TextField locationTextField;
    /** A field for getting description input */
    public TextField descriptionTextField;
    /** A field for getting type input */
    public TextField typeTextField;
    /** A combo box for storing and selecting customers */
    public ComboBox<Customers> customerCB;
    /** A combo box for storing and selecting users */
    public ComboBox<Users> userCB;
    /** A combo box for storing and selecting contacts */
    public ComboBox<Contacts> contactCB;
    /** A combo box for storing and selecting start times */
    public ComboBox<String> startTimeCB;
    /** A combo box for storing and selecting end times */
    public ComboBox<String> endTimeCB;
    /** A date picker for selecting dates */
    public DatePicker datePicker;
    /** A label to show invalid input for a combination of date and times. */
    public Label ldtLabel;
    /** A Label to display errors for the type field */
    public Label typeLabel;
    /** A Label to display errors for the customer combo box */
    public Label customerLabel;
    /** A Label to display errors for the contact combo box */
    public Label contactLabel;
    /** A Label to display errors for the user combo box */
    public Label userLabel;

    ObservableList<Customers> customers = DBCustomers.getAllCustomers();
    ObservableList<Users> users = DBUsers.getAllUsers();
    ObservableList<Contacts> contacts = DBContacts.getAllContacts();
    ObservableList<String> startTimes = FXCollections.observableArrayList();
    ObservableList<String> endTimes = FXCollections.observableArrayList();

    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
    LocalDateTime ldtPopulate = LocalDateTime.of(2022,8,18,8,0);
    LocalDateTime startDateAndTime;
    LocalDateTime endDateAndTime;

    private int customerId = 0;
    private int userId = 0;
    private int contactId = 0;
    private LocalDate ld;
    private LocalTime startLt;
    private LocalTime endLt;

    /** initialize determines the initial state of the scene when it gets called.
     Two for loops are used to populate the start and end time combo boxes. The times are converted from eastern to local time as they are added.<br>
     The customers, users, and contacts combo boxes are set with the customers, users, and contacts from the database.
     @param url points to a uniform resource locator (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/net/URL.html"></a>)
     @param resourceBundle contains locale-specific objects (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html"></a>) */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        for(int i = 0; i < 56; i++){
            startTimes.add(dtf.format(UsefulMethods.easternToLocalTime(ldtPopulate)));
            ldtPopulate = ldtPopulate.plusMinutes(15);
        }

        startTimeCB.setItems(startTimes);
        ldtPopulate = LocalDateTime.of(2022,8,18,8,0);

        for(int i = 0; i < 56; i++){
            ldtPopulate = ldtPopulate.plusMinutes(15);
            endTimes.add(dtf.format(UsefulMethods.easternToLocalTime(ldtPopulate)));
        }

        endTimeCB.setItems(endTimes);

        customerCB.setItems(customers);
        userCB.setItems(users);
        contactCB.setItems(contacts);

    }
    /** This method sets the customer ID. The contact from the combo box calls its getCustomerId method.
     * @param actionEvent calls method a selection from the combo box is made. */
    @FXML
    public void customerId(ActionEvent actionEvent) {
        customerId = customerCB.getValue().getCustomerId();
    }
    /** This method sets the user ID. The user from the combo box calls its getUserId method.
     * @param actionEvent calls method a selection from the combo box is made. */
    @FXML
    public void userId(ActionEvent actionEvent) {
        userId = userCB.getValue().getUserId();
    }
    /** This method sets the contact ID. The contact from the combo box calls its getContactId method.
     * @param actionEvent calls method a selection from the combo box is made. */
    @FXML
    public void contactName(ActionEvent actionEvent) {
        contactId = contactCB.getValue().getContactId();
    }
    /** This method sets the start appointment time. The string from the combo box is parsed as a LocalTime object.
     * @param actionEvent calls method a selection from the combo box is made. */
    @FXML
    public void startTime(ActionEvent actionEvent) {
        String start = startTimeCB.getValue();
        startLt = LocalTime.parse(start, dtf);
    }
    /** This method sets the end appointment time. The string from the combo box is parsed as a LocalTime object.
     * @param actionEvent calls method a selection from the combo box is made. */
    @FXML
    public void endTime(ActionEvent actionEvent) {
        String end = endTimeCB.getValue();
        endLt = LocalTime.parse(end, dtf);
    }
    /** This method assigns the value of the date picker to the ld variable.
     * @param actionEvent calls method a selection from the date picker is made. */
    @FXML
    public void datePicked(ActionEvent actionEvent) {
        ld = datePicker.getValue();
    }

    /** Adds a new appointment to the database. <br>
     * Variables are assigned values based on user input from text fields, combo boxes, and a date picker. Input is validated <br>
     * for various conditions including length, whether the user entered data, if the date for the appointment is in the future, <br>
     * if the end time is after the start time, and if the customer already has an appointment that overlaps. A pop-up or labels<br>
     * display to let the user know what to correct. If all criteria is met, the appointment is added to the database and the user<br>
     * is directed back to the main screen.
     * @param actionEvent calls method when the save button is pressed. */
    @FXML
    public void addAppointment(ActionEvent actionEvent) throws SQLException, IOException {

        String title = titleTextField.getText();
        String description = descriptionTextField.getText();
        String location = locationTextField.getText();
        String type = typeTextField.getText();

        dateLabel.setText(null);
        titleLabel.setText(null);
        descriptionLabel.setText(null);
        locationLabel.setText(null);
        timeLabel.setText(null);
        ldtLabel.setText(null);
        typeLabel.setText(null);
        customerLabel.setText(null);
        contactLabel.setText(null);
        userLabel.setText(null);

        //---------------Validate customer ID------------------
        if(customerId ==0 ){
            customerLabel.setText("A customer ID must be selected.");
        }
        //---------------Validate contact----------------------
        if(contactId == 0){
            contactLabel.setText("A contact must be selected.");
        }
        //---------------Validate user-------------------------
        if(userId == 0){
            userLabel.setText("A user must be selected.");
        }
        //------------------Validate type----------------------
        if(type.isEmpty()){
            typeLabel.setText("An appointment type must be selected.");
        }
        else if(typeTextField.getText().length() > 50){
            typeLabel.setText("That text is too long.");
        }
        //------------------Validate date----------------------
        try {
            if (!LocalDate.now().isBefore(ld)) {
                dateLabel.setText("Appointments must be scheduled for a future date.");
            }
        } catch (NullPointerException e) {
            dateLabel.setText("Please select a date.");
        }
        //-----------------Validate title--------------------
        if(title.isEmpty()){
            titleLabel.setText("This field cannot be left blank.");
        }
        else if(titleTextField.getText().length() > 50) {
            titleLabel.setText("That text is too long.");
        }
        //-------------Validate description-------------------
        if(description.isEmpty()){
            descriptionLabel.setText("This field cannot be left blank.");
        }
        else if(descriptionTextField.getText().length() > 50) {
            descriptionLabel.setText("That text is too long.");
        }
        //--------------Validate location---------------------
        if(location.isEmpty()){
            locationLabel.setText("This field cannot be left blank.");
        }
        else if(locationTextField.getText().length() > 50) {
            locationLabel.setText("That text is too long.");
        }
        //---------------Validate times-------------------------
        try {
            if (!startLt.isBefore(endLt)) {
                timeLabel.setText("End time must be after start time.");
            }
        } catch (NullPointerException e) {
            timeLabel.setText("Start and end times must be selected.");
        }
        //-------------Validate LocalDateTimes-----------------------
        try {
            startDateAndTime = LocalDateTime.of(ld, startLt);
            endDateAndTime = LocalDateTime.of(ld, endLt);
        } catch (NullPointerException e) {
            ldtLabel.setText("Start time, end time, and date must all be selected.");
        }

//------------------------------Check conditions and add appointment to database----------------------------------------
        if(!title.isEmpty() && title.length() <= 50 && !description.isEmpty() && description.length() <= 50 && !location.isEmpty() && location.length() <= 50 && !type.isEmpty()
           && type.length() <= 50 && startDateAndTime != null && endDateAndTime != null && endDateAndTime.isAfter(startDateAndTime) && customerId > 0 && userId > 0 && contactId > 0
           && LocalDate.now().isBefore(ld)){
            ObservableList<Appointments> customerAppointments = DBAppointments.getAllAppointments(customerId);
            Appointments appointment = new Appointments(-1, title, description, location, type, startDateAndTime, endDateAndTime, customerId, userId, contactId);

            if(appointment.overlaps(customerAppointments)) {
                UsefulMethods.overlapNotification();
            }
            else {
                DBAppointments.addAppointment(appointment);
                toMain(actionEvent);
            }
        }

    }
    /** This method sends users to the main screen.
     * @param actionEvent calls method when cancel button is pressed*/
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
