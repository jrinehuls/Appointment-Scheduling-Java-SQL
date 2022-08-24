package main;

import utility.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Locale;
/** This is the main class. it contains the methods to launch the application.
 *  <p><b>
 *  The Javadoc folder can be found inside the C195Project folder.
 *  </b></p>
 *  <p><b>
 * First lambda expression located in the controller package, MainScreenController class, deleteCustomer method.<br>
 * Second lambda expression located in the controller package, LoginScreenController class, onClick method.
 * </b></p>
 * */
public class Main extends Application {

    /** This is the start method. It stets the first stage and scene.
     @param stage holds the GUI. */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/LoginScreen.fxml"));
        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 800,600));
        stage.show();
    }

    /** This is the main method. it launches the application.
     It also establishes and closes a connection to the database.
     @param args is for arguments in the constructor. */
    public static void main(String[] args){
        //Locale.setDefault(new Locale("fr"));
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }

}
