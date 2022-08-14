package controller;

import access.DBCountries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Countries;

import java.net.URL;
import java.util.ResourceBundle;

public class MainScreenController implements Initializable {


    public Label textLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void onClick(ActionEvent actionEvent) {
        textLabel.setText("The button was clicked.");

        ObservableList<Countries> clist = DBCountries.getAllCountries();
        for(Countries c : clist){
            System.out.println("Country ID: " + c.getId() + " Country Name: " + c.getName());
        }
    }
}

