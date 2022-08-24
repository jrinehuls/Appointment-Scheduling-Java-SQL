package controller;

import dataAccess.DBCountries;
import dataAccess.DBCustomers;
import dataAccess.DBDivisions;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Countries;
import model.Customers;
import model.Divisions;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
/** This class is for adding customers to the database */
public class AddCustomerScreenController implements Initializable {
    /** A field for getting name input */
    public TextField custNameTextField;
    /** A field for getting address input */
    public TextField addressTextField;
    /** A field for getting postal code input */
    public TextField postalCodeTextField;
    /** A field for getting phone input */
    public TextField phoneTextField;
    /** A Label to display errors for the name field */
    public Label custNameLabel;
    /** A Label to display errors for the address field */
    public Label addressLabel;
    /** A Label to display errors for the postal code field */
    public Label postalCodeLabel;
    /** A Label to display errors for the phone field */
    public Label phoneLabel;
    /** A combo box for storing and selecting countries */
    public ComboBox<Countries> countriesCB;
    /** A combo box for storing and selecting divisions */
    public ComboBox<Divisions> divisionsCB;

    int divisionId = 0;
    ObservableList<Countries> countries = DBCountries.getAllCountries();
    ObservableList<Divisions> usDivisions = DBDivisions.getAllUsDivisions();
    ObservableList<Divisions> ukDivisions = DBDivisions.getAllUkDivisions();
    ObservableList<Divisions> caDivisions = DBDivisions.getAllCaDivisions();

    /** initialize determines the initial state of the scene when it gets called. <br>
     The countries combo box is set with countries from the database.
     @param url points to a uniform resource locator (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/net/URL.html"></a>)
     @param resourceBundle contains locale-specific objects (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html"></a>) */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        countriesCB.setItems(countries);

    }
    /** This method sets what is available in the divisions combo box. <br>
     * The division combo box is populated with options based on the selection from the countries combo box.
     * @param actionEvent calls method when a selection is made from the countries combo box. */
    @FXML
    public void setDivisionCombo(ActionEvent actionEvent) {
        int countryId = countriesCB.getValue().getId();
        if(countryId == 1){
            divisionsCB.setItems(usDivisions);
        }
        if(countryId == 2){
            divisionsCB.setItems(ukDivisions);
        }
        if(countryId == 3){
            divisionsCB.setItems(caDivisions);
        }
    }
    /** Sets the value of the divisionId variable to division ID of the selected division.
     * @param actionEvent calls method when a selection is made from the divisions combo box.*/
    @FXML
    public void setDivisionId(ActionEvent actionEvent){
        try {
            divisionId = divisionsCB.getValue().getDivisionId();
        } catch (NullPointerException e) {
            divisionId = 0;
        }
    }
    /** Adds a new customer to the database. <br>
     * Variables are assigned values based on user input from text fields and combo boxes. Input is validated for various conditions<br>
     * including length and whether the user entered data. Labels display to let the user know what to correct. If all criteria is<br>
     * met, the customer is added to the database and the user is directed back to the main screen.
     * @param actionEvent calls method when the save button is pressed. */
    @FXML
    public void addCustomer(ActionEvent actionEvent) throws IOException, SQLException {

        String name = custNameTextField.getText();
        String address = addressTextField.getText();
        String postalCode = postalCodeTextField.getText();
        String phone = phoneTextField.getText();

        custNameLabel.setText(null);
        addressLabel.setText(null);
        postalCodeLabel.setText(null);
        phoneLabel.setText(null);

        if(name.isEmpty()){
            custNameLabel.setText("This field cannot be left blank.");
        }
        else if(custNameTextField.getText().length() > 50) {
            custNameLabel.setText("That text is too long.");
        }

        if(address.isEmpty()){
            addressLabel.setText("This field cannot be left blank.");
        }
        else if(addressTextField.getText().length() > 100) {
            custNameLabel.setText("That text is too long.");
        }

        if(postalCode.isEmpty()){
            postalCodeLabel.setText("This field cannot be left blank.");
        }
        else if(postalCodeTextField.getText().length() > 50) {
            custNameLabel.setText("That text is too long.");
        }

        if(phone.isEmpty()){
            phoneLabel.setText("This field cannot be left blank.");
        }
        else if(phoneTextField.getText().length() > 50) {
            custNameLabel.setText("That text is too long.");
        }

        if(!name.isEmpty() && name.length() <= 50 && !address.isEmpty() && address.length() <= 100 && !postalCode.isEmpty() &&
           postalCode.length() <= 50 && !phone.isEmpty() && phone.length() <= 50 && divisionId != 0){
            Customers customer = new Customers(-1, name, address, postalCode, phone, divisionId);
            DBCustomers.addCustomer(customer);
            toMain(actionEvent);
        }

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
