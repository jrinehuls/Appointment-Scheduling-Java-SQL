package model;

/** This class defines contacts. */
public class Contacts {

    private int contactId;
    private String contactName;
    private String contactEmail;

    /** A constructor for contacts
     * @param contactId contactId for the contact
     * @param contactName name of the contact
     * @param contactEmail contact's email address */
    public Contacts(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /** Sets the contactId to a variable.
     * @param contactId contactId for the contact. */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
    /** Gets the ID of a contact.
     * @return ID for the contact. */
    public int getContactId() {
        return contactId;
    }
    /** Sets the contact name to a variable.
     * @param contactName name for the contact. */
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    /** Gets the name of a contact.
     * @return name for the contact. */
    public String getContactName() {
        return contactName;
    }
    /** Sets the contact email to a variable.
     * @param contactEmail email for the contact. */
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    /** Gets the email of a contact.
     * @return email for the contact. */
    public String getContactEmail() {
        return contactEmail;
    }

    /** Returns a string for displaying in combo boxes.
     * @return contactId and name to a string */
    @Override
    public String toString(){
        return getContactId() + " " + getContactName();
    }


}
