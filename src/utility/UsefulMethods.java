package utility;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointments;
import model.Customers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public abstract class UsefulMethods {

    public static LocalDateTime toEasternTime(LocalDateTime ldt){
        ZoneId zoneId = ZoneId.of("America/New_York");
        ZonedDateTime localZdt = ldt.atZone(ZoneId.systemDefault());
        ZonedDateTime targetZdt = localZdt.withZoneSameInstant(zoneId);
        LocalDateTime newLdt = targetZdt.toLocalDateTime();
        return newLdt;
    }

    public static boolean isDeletable(Customers customer) {
        if (customer != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Customer");
            alert.setHeaderText("DELETE?");
            alert.setContentText("This customer and all of their appointments will be deleted. \nAre you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
        else {
            return false;
        }
    }

    public static boolean isDeletable(Appointments appointment) {
        if (appointment != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Appointment");
            alert.setHeaderText("DELETE?");
            alert.setContentText("This appointment will be deleted. \nAre you sure?");
            Optional<ButtonType> result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
        else {
            return false;
        }
    }

    public static void confirmation(String name){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Customer Deleted");
        alert.setHeaderText(name + " has been deleted.");
        alert.setContentText("");
        alert.showAndWait();
    }

}
