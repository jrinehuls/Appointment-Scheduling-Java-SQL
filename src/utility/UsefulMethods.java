package utility;

import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Appointments;
import model.Customers;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
/** A class for miscellaneous methods that are used in the program and reduces and de-clutters code in other classes. */
public abstract class UsefulMethods {
    /** Converts eastern time to a local date and time.
     * The local zone ID is stored in the zoneId variable. The ldt passed in is set as "America/New_York" and is stored as a ZonedDateTime<br>
     * The local zone of the same instant in time is stored in another ZonedDateTime variable. That ZonedDateTime is converted to a<br>
     * LocalDateTime and is returned.
     * @param ldt A LocalDateTime object of the eastern time zone.
     * @return A LocalDateTime object of the local time zone. */
    public static LocalDateTime easternToLocalTime(LocalDateTime ldt) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime targetZdt = ldt.atZone(ZoneId.of("America/New_York"));
        ZonedDateTime localZdt = targetZdt.withZoneSameInstant(zoneId);
        LocalDateTime newLdt = localZdt.toLocalDateTime();
        return newLdt;
    }

    /** Checks to see if a customer is deletable based on if it is selected and if the user agrees to delete.
     * First, a check is done to see if a customer is even selected, then a confirmation box is displayed confirming whether<br>
     * wants to delete the customer.
     * @param customer The customer to be deleted.
     * @return true if the customer meets the criteria and false otherwise. */
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

    /** Checks to see if an appointment is deletable based on if it is selected and if the user agrees to delete.
     * First, a check is done to see if an appointment is even selected, then a confirmation box is displayed confirming whether<br>
     * wants to delete the appointment.
     * @param appointment The customer to be deleted.
     * @return true if the customer meets the criteria and false otherwise. */
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

    /** Shows an information box displaying that the appointment was deleted.
     * @param type The type of appointment.
     * @param appointmentId The ID of the appointment. */
    public static void confirmation(int appointmentId, String type){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Appointment Deleted");
        alert.setHeaderText("Appointment of ID: " + appointmentId + " and type: " + type +" has been deleted.");
        alert.setContentText("");
        alert.showAndWait();
    }

    /** Shows an information box displaying that there is a scheduling conflict. */
    public static void overlapNotification() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Scheduling conflict");
        alert.setHeaderText("Please choose another date, time, and/or customer.");
        alert.setContentText("This appointment can't be saved due to a conflict in this customer's schedule.");
        alert.showAndWait();
    }

    /** Determines if an appointment is upcoming within 15 minutes and displays an information box.
     * Loops through the ObservableList of appointments to compare the start time of each to the current time. <br>
     * If any are within 15 minutes, the content text is changed to reflect which appointment, by ID, is upcoming.
     * @param appointments An observable list of appointments. */
    public static void isUpcoming(ObservableList<Appointments> appointments) {
        LocalDateTime startTime;
        LocalDateTime currentTime;
        long timeDifference;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Checking Schedule");
        alert.setHeaderText("Checking for upcoming appointments.");
        alert.setContentText("There are no upcoming appointments.");
        for (Appointments appointment : appointments) {
            startTime = appointment.getStart();
            currentTime = LocalDateTime.now();
            timeDifference = ChronoUnit.SECONDS.between(currentTime, startTime);
            if(timeDifference > 0 && timeDifference <= 900){
                alert.setContentText("Appointment with ID: " + appointment.getAppointmentId() + " is coming up at: " + appointment.getStart());
            }
        }
        alert.showAndWait();
    }
    /** Calculates the difference in hours between UTC and local time.
     * The hours at both UTC and local time are stored in variables. The difference can be of two values since they can be on the same day as each other<br>
     * or one calendar day apart. Since a -4 time zone could be +20 if they're on different days, the +20 needs to be converted to -4. Logical checks are used<br>
     * to check and convert if needed. The time difference of the zones is returned.
     * @return The sign and difference in hours of local zone and UTC. */
    public static int hourDifference(){
        ZoneId localZoneId = ZoneId.systemDefault();
        ZoneId utcZoneId = ZoneId.of("UTC");
        int localHour = LocalDateTime.ofInstant(Instant.now(), localZoneId).getHour();
        int utcHour = LocalDateTime.ofInstant(Instant.now(), utcZoneId).getHour();
        if(Math.abs(localHour - utcHour) >= 12 && localHour - utcHour > 0) {
            return (localHour - utcHour) - 24;
        }
        else if(Math.abs(localHour - utcHour) >= 12 && localHour - utcHour < 0) {
            return (localHour - utcHour) + 24;
        }
        else {
            return localHour - utcHour;
        }
    }

}
