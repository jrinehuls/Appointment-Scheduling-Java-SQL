package model;
/** This class defines countries. */
public class Countries {

    private int id;
    private String name;

    /** A constructor for countries.
     * @param id The country's ID
     * @param name The name of the country */
    public Countries(int id, String name){
        this.id = id;
        this.name = name;
    }
    /** Sets the ID of a country.
     * @param id id for the country. */
    public void setId(int id) {
        this.id = id;
    }
    /** Gets the ID of a country.
     * @return ID for the country. */
    public int getId(){
        return id;
    }
    /** Sets the name of a country.
     * @param name name for the country. */
    public void setName(String name) {
        this.name = name;
    }
    /** Gets the name of a country.
     * @return name for the country. */
    public String getName(){
        return name;
    }

    /** Returns a string for displaying in combo boxes.
     * @return country ID and country name to a string */
    @Override
    public String toString(){
        return getId() + ". " + getName();
    }

}
