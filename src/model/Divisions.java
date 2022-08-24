package model;
/** This class defines divisions */
public class Divisions {

    private int divisionId;
    private String divisionName;
    private int countryId;

    /** A constructor for divisions.
     * @param divisionId The ID of the division
     * @param divisionName The name of the division
     * @param countryId The ID of the country this division belongs to */
    public Divisions(int divisionId, String divisionName, int countryId){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    /** Sets the ID of a division.
     * @param divisionId ID for the division. */
    public void setDivisionId(int divisionId){
        this.divisionId = divisionId;
    }
    /** Gets the ID of a division.
     * @return ID for the division. */
    public int getDivisionId(){
        return divisionId;
    }
    /** Sets the name of a division.
     * @param divisionName The name of the division. */
    public void setDivisionName(String divisionName) {
        this.divisionName =divisionName;
    }
    /** Gets the name of a division.
     * @return name of the division. */
    public String getDivisionName() {
        return divisionName;
    }
    /** Sets the ID of a country.
     * @param countryId The ID of the country. */
    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }
    /** Gets the ID of a country.
     * @return ID for the country. */
    public int getCountryId() {
        return countryId;
    }

    /** Returns a string for displaying in combo boxes.
     * @return division ID and name to a string */
    @Override
    public String toString(){
        return getDivisionId() + ".  " + getDivisionName();
    }
}
