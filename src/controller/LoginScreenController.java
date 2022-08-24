package controller;

import dataAccess.DBAppointments;
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
import utility.Checking;
import utility.UsefulMethods;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.TimeZone;
/** This class is responsible for allowing authorized users to log in to the program. <br>
 * Language can be translated into French for this screen via a resource bundle if the user's system is default to French.<br> */
public class LoginScreenController implements Initializable {
    /** Text field for entering username. */
    public TextField userNameField;
    /** Password field for entering password. */
    public PasswordField passwordField;
    /** Label for warning if invalid log in info provided. */
    public Label warningLabel;
    /** Label to display users time zone. */
    public Label zoneLabel;
    /** Button to click to log in after entering username and password. */
    public Button loginButton;
    /** Label to show that the adjacent text field is for entering username. */
    public Label userNameLabel;
    /** Label to show that the adjacent password field is for entering password. */
    public Label passwordLabel;
    /** Label for title of screen */
    public Label titleLabel;

    TimeZone timeZone = TimeZone.getDefault();
    ResourceBundle rb = ResourceBundle.getBundle("controller/Nat", Locale.getDefault());
    DateTimeFormatter format = DateTimeFormatter.ofPattern("HH:mm");

    /** initialize determines the initial state of the scene when it gets called.
     Label and button text are set and determined by the resource bundle.<br>
     @param url points to a uniform resource locator (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/net/URL.html"></a>)
     @param resourceBundle contains locale-specific objects (source: <a href="https://docs.oracle.com/javase/8/docs/api/java/util/ResourceBundle.html"></a>) */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        warningLabel.setText(null);
        zoneLabel.setText(timeZone.getID());
        userNameLabel.setText(rb.getString("name"));
        passwordLabel.setText(rb.getString("pass"));
        titleLabel.setText(rb.getString("title"));
        loginButton.setText(rb.getString("button"));

    }
    /** This method determines whether a user's login is valid when the button is clicked. <br>
     * <p><b>Lambda expression - The functional interface "Checking" has its meetsTwoConditions method overridden<br>
     * to accepts two boolean parameters. The "checking" variable is assigned to the Checking interface instance. first and second<br>
     * are the arguments for the lambda expression. The instance calls the meetsTwoCondition method with the userName and password arguments<br>
     * A for loop loops through all users in the database checks for and finds a match when meetTwoConditions returns true.
     * </b></p>
     * If no matches, the warning label displays a message that login is invalid. If a match is found, toMainScreen gets called<br>
     * and the user is directed to the Main Screen. The isUpcoming method is called to display a pop-up for if there is an appointment<br>
     * upcoming within the next 15 minutes. PrintWriter and FileWriter write log in attempts to a text file.
     * @param actionEvent calls method when log in button is pressed */
    @FXML
    public void onClick(ActionEvent actionEvent) throws SQLException, IOException {
        FileWriter file = new FileWriter("login_activity.txt", true);
        PrintWriter writer = new PrintWriter(file);
        String userName = userNameField.getText();
        String password = passwordField.getText();
        ObservableList<Users> userList = DBUsers.getAllUsers();

        Checking checking = (first, second) -> first && second;

        for(Users user : userList){
            boolean userMatch = user.getUserName().equals(userName);
            boolean passwordMatch = user.getPassword().equals(password);
            if(checking.meetsTwoConditions(userMatch, passwordMatch)){
                toMainScreen();
                writer.println("User " + userName + " successfully logged in on " + LocalDate.now() + " at " + LocalTime.now().format(format));
                writer.close();
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                stage.close();
                UsefulMethods.isUpcoming(DBAppointments.getAllAppointments(user));
            }
        }
        warningLabel.setText(rb.getString("warning"));
        writer.println("User " + userName + " gave invalid log-in on " + LocalDate.now() + " at " + LocalTime.now().format(format));
        writer.close();
    }

    /** This method sends users to the MainScreen if there is a successful log-in. */
    public void toMainScreen() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Main Screen");
        stage.setScene(scene);
        stage.show();
    }

}

