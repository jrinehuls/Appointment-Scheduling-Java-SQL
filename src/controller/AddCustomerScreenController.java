package controller;

import dataAccess.DBCountries;
import dataAccess.DBCustomers;
import dataAccess.DBDivisions;
import javafx.collections.FXCollections;
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

public class AddCustomerScreenController implements Initializable {

    public TextField custNameTextField;
    public TextField addressTextField;
    public TextField postalCodeTextField;
    public TextField phoneTextField;
    public Label custNameLabel;
    public Label addressLabel;
    public Label postalCodeLabel;
    public Label phoneLabel;
    public ComboBox<Countries> countriesCB;
    public ComboBox<Divisions> divisionsCB;

    int divisionId = 0;
    ObservableList<Countries> countries = DBCountries.getAllCountries();
    ObservableList<Divisions> usDivisions = DBDivisions.getAllUsDivisions();
    ObservableList<Divisions> ukDivisions = DBDivisions.getAllUkDivisions();
    ObservableList<Divisions> caDivisions = DBDivisions.getAllCaDivisions();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        custNameLabel.setText(null);
        addressLabel.setText(null);
        postalCodeLabel.setText(null);
        phoneLabel.setText(null);

        countriesCB.setItems(countries);

    }

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

    @FXML
    public void setDivisionId(ActionEvent actionEvent){
        try {
            divisionId = divisionsCB.getValue().getDivisionId();
        } catch (NullPointerException e) {
            divisionId = 0;
        }
    }

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
        if(address.isEmpty()){
            addressLabel.setText("This field cannot be left blank.");
        }
        if(postalCode.isEmpty()){
            postalCodeLabel.setText("This field cannot be left blank.");
        }
        if(phone.isEmpty()){
            phoneLabel.setText("This field cannot be left blank.");
        }

        if(!name.isEmpty() && !address.isEmpty() && !postalCode.isEmpty() && !phone.isEmpty() && divisionId != 0){
            Customers customer = new Customers(-1, name, address, postalCode, phone, divisionId);
            DBCustomers.addCustomer(customer);
            toMain(actionEvent);
        }

    }

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
