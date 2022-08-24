package model;


import javafx.collections.ObservableList;

import java.time.LocalDateTime;
/** This class defines appointments. */
public class Appointments {

    private int appointmentId;
    private String title;
    private String description;
    private String location;
    private String type;
    private LocalDateTime start;
    private LocalDateTime end;
    private int customerId;
    private int userId;
    private int contactId;

    /** A constructor for new appointments.
     * @param appointmentId appointmentId for the appointment
     * @param type type of appointment
     * @param title title of the appointment
     * @param description description of the appointment
     * @param location location of the appointment
     * @param start start time of the appointment
     * @param end end time of the appointment
     * @param customerId customerId for the appointment
     * @param userId userId for the appointment
     * @param contactId contactId for the appointment */
    public Appointments(int appointmentId, String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId){
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type =type;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /** Sets the appointmentId to a variable.
     * @param appointmentId appointmentId for the appointment. */
    public void setAppointmentId(int appointmentId){
        this.appointmentId = appointmentId;
    }
    /** Gets the appointmentId.
     * @return appointmentId */
    public int getAppointmentId() {
        return appointmentId;
    }
    /** Sets the title to a variable.
     * @param title title for the appointment. */
    public void setTitle(String title){
        this.title = title;
    }
    /** Gets the title.
     * @return title */
    public String getTitle() {
        return title;
    }
    /** Sets the description to a variable.
     * @param description description for the appointment. */
    public void setDescription(String description){
        this.description = description;
    }
    /** Gets the description.
     * @return description */
    public String getDescription() {
        return description;
    }
    /** Sets the location to a variable.
     * @param location location for the appointment. */
    public void setLocation(String location){
        this.location = location;
    }
    /** Gets the location.
     * @return location */
    public String getLocation() {
        return location;
    }
    /** Sets the type to a variable.
     * @param type type for the appointment. */
    public void setType(String type){
        this.type = type;
    }
    /** Gets the type.
     * @return type */
    public String getType() {
        return type;
    }
    /** Sets the start time to a variable.
     * @param start start time for the appointment. */
    public void setStart(LocalDateTime start){
        this.start = start;
    }
    /** Gets the start time.
     * @return start time */
    public LocalDateTime getStart() {
        return start;
    }
    /** Sets the end time to a variable.
     * @param end end time for the appointment. */
    public void setEnd(LocalDateTime end){
        this.end = end;
    }
    /** Gets the end time.
     * @return end time */
    public LocalDateTime getEnd() {
        return end;
    }
    /** Sets the customerId time to a variable.
     * @param customerId customerId for the appointment. */
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    /** Gets the customerID.
     * @return customerID */
    public int getCustomerId() {
        return customerId;
    }
    /** Sets the userId time to a variable.
     * @param userId userId for the appointment. */
    public void setUserId(int userId){
        this.userId = userId;
    }
    /** Gets the userID.
     * @return userID */
    public int getUserId() {
        return userId;
    }
    /** Sets the contactId to a variable.
     * @param contactId The contactId for the appointment. */
    public void setContactId(int contactId){
        this.contactId = contactId;
    }
    /** Gets the contactID.
     * @return contactID */
    public int getContactId() {
        return contactId;
    }

    /** Checks to see if an appointment overlaps with any in the observable list.
     * A series of logical checks is used to compare start and end times. If there is an overlap, true is returned, otherwise false is.
     * @param appointments An ObservableList of appointments
     * @return true of there is an overlap and false if not */
    public boolean overlaps(ObservableList<Appointments> appointments){
        for(Appointments oldAppointment : appointments) {
            if(appointmentId == oldAppointment.getAppointmentId()) {
                continue;
            }
            if(!start.isBefore(oldAppointment.getStart()) && start.isBefore(oldAppointment.getEnd())) {
                return true;
            }
            else if(end.isAfter(oldAppointment.getStart()) && !end.isAfter(oldAppointment.getEnd())) {
                return true;
            }
            else if(!start.isAfter(oldAppointment.getStart()) && !end.isBefore(oldAppointment.getEnd())) {
                return true;
            }

        }
        return false;
    }

}
