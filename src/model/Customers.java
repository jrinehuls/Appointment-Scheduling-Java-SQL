package model;
/** This class defines customers */
public class Customers {
    private int customerId;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private int divisionId;

    /** A constructor for customers.
     * @param customerId the customer's unique ID
     * @param name the customer's name
     * @param address the customer's address
     * @param postalCode the customer's postal code
     * @param phone the customer's phone number
     * @param divisionId The ID of the division in which the customer resides */
    public Customers(int customerId, String name, String address, String postalCode, String phone, int divisionId) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.divisionId = divisionId;
    }

    /** Sets the ID of a customer.
     * @param customerId ID for the customer. */
    public void setCustomerId (int customerId) {
        this.customerId =  customerId;
    }
    /** Gets the ID of a customer.
     * @return ID for the customer. */
    public int getCustomerId() {
        return customerId;
    }
    /** Sets the name of a customer.
     * @param name name of the customer. */
    public void setName(String name) {
        this.name = name;
    }
    /** Gets the name of a customer.
     * @return name of the customer. */
    public String getName() {
        return name;
    }
    /** Sets the address of a customer.
     * @param address address of the customer. */
    public void setAddress(String address) {
        this.address = address;
    }
    /** Gets the address of a customer.
     * @return address of the customer. */
    public String getAddress() {
        return address;
    }
    /** Sets the postal code of a customer.
     * @param postalCode  postal code of the customer. */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    /** Gets the postal code of a customer.
     * @return postal code of the customer. */
    public String getPostalCode() {
        return postalCode;
    }
    /** Sets the phone number of a customer.
     * @param phone  phone number of the customer. */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /** Gets the phone number of a customer.
     * @return phone number of the customer. */
    public String getPhone() {
        return phone;
    }
    /** Sets the division ID of a customer.
     * @param divisionId  division ID of the customer. */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
    /** Gets the division ID of a customer.
     * @return division ID of the customer. */
    public int getDivisionId() {
        return divisionId;
    }

    /** Returns a string for displaying in combo boxes.
     * @return customer ID and name to a string */
    @Override
    public String toString(){
        return getCustomerId() + " " + getName();
    }

}
