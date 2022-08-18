package controller;

import dataAccess.DBUsers;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Users;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class LoginScreenController implements Initializable {

    public PasswordField passwordField;
    public TextField userNameField;
    public Label warningLabel;
    public Label zoneLabel;
    public Button loginButton;
    public Label userNameLabel;
    public Label passwordLabel;
    public Label titleLabel;

    TimeZone timeZone = TimeZone.getDefault();
    ResourceBundle rb = ResourceBundle.getBundle("controller/Nat", Locale.getDefault());


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        warningLabel.setText(null);
        zoneLabel.setText(timeZone.getID());
        userNameLabel.setText(rb.getString("name"));
        passwordLabel.setText(rb.getString("pass"));
        titleLabel.setText(rb.getString("title"));
        loginButton.setText(rb.getString("button"));

    }

    @FXML
    public void onClick(ActionEvent actionEvent) {

        String userName = userNameField.getText();
        String password = passwordField.getText();
        ObservableList<Users> userList = DBUsers.getAllUsers();

        for(Users user : userList){
            boolean userMatch = user.getUserName().equals(userName);
            boolean passwordMatch = user.getPassword().equals(password);

            if(userMatch && passwordMatch){
                try {
                    toMainScreen(actionEvent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                stage.close();
                //break;
            }
            else {
                warningLabel.setText(rb.getString("warning"));
            }

        }

    }

    public void toMainScreen(ActionEvent actionEvent) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = new Stage();
        //Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

}

