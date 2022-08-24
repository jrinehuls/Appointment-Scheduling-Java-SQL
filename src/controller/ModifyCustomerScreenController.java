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
/** This class is for modifying customers in the database */
public class ModifyCustomerScreenController implements Initializable {
    /** A disabled label for displaying the customer ID */
    public TextField custIdTextField;
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

    private static Customers customer = null;
    private int divisionId = customer.getDivisionId();

    ObservableList<Countries> countries = DBCountries.getAllCountries();
    ObservableList<Divisions> usDivisions = DBDivisions.getAllUsDivisions();
    ObservableList<Divisions> ukDivisions = DBDivisions.getAllUkDivisions();
    ObservableList<Divisions> caDivisions = DBDivisions.getAllCaDivisions();

    /** This method receives an customer selected from the main screen.
     * @param modifiableCustomer is the received customer form the main screen */
    public static void receiveCustomer(Customers modifiableCustomer){
        customer = modifiableCustomer;
    }

    /** initialize determines the initial state of the scene when it gets called.<br>
     The combo boxes are set with data from the database. Selections for the combo boxes text in text fields are set based on the selected customer.
     @param url points to a uniform resource locator (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/net/URL.html"></a>)
     @param resourceBundle contains locale-specific objects (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html"></a>) */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        custIdTextField.setText(String.valueOf(customer.getCustomerId()));
        custNameTextField.setText(customer.getName());
        addressTextField.setText(customer.getAddress());
        postalCodeTextField.setText(customer.getPostalCode());
        phoneTextField.setText(customer.getPhone());

        countriesCB.setItems(countries);
        countriesCB.setValue(DBCountries.getSelectedCountry(divisionId));
        divisionsCB.setItems(getDivisionsList(divisionId));
        divisionsCB.setValue(DBDivisions.getSelectedDivision(divisionId));

    }
    /** This method returns a list of divisions based on divisionId argument passed in.
     * @param divisionId is the division ID of the selected customer.
     * @return returns the correct list of divisions based on that customer's division ID.*/
    public ObservableList<Divisions> getDivisionsList (int divisionId) {
        if(divisionId < 60){
            return usDivisions;
        }
        if(divisionId > 100){
            return ukDivisions;
        }
        else {
            return caDivisions;
        }
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
        divisionsCB.setValue(null);
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

    /** Modifies a customer in the database. <br>
     * Variables are assigned values based on user input from text fields and combo boxes. Input is validated for various conditions<br>
     * including length and whether the user entered data. Labels display to let the user know what to correct. If all criteria is<br>
     * met, the customer is updated in the database and the user is directed back to the main screen.
     * @param actionEvent calls method when the save button is pressed. */
    @FXML
    public void modifyCustomer(ActionEvent actionEvent) throws IOException, SQLException {

        int customerId = Integer.parseInt(custIdTextField.getText());
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
            Customers customer = new Customers(customerId, name, address, postalCode, phone, divisionId);
            DBCustomers.modifyCustomer(customer);
            toMain(actionEvent);
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
