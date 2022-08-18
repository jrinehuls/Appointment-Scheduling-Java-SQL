package model;

public class Divisions {

    private int divisionId;
    private String divisionName;
    private int countryId;

    public Divisions(int divisionId, String divisionName, int countryId){
        this.divisionId = divisionId;
        this.divisionName = divisionName;
        this.countryId = countryId;
    }

    public void setDivisionId(int divisionId){
        this.divisionId = divisionId;
    }

    public int getDivisionId(){
        return divisionId;
    }

    public void setDivisionName(String divisionName) {
        this.divisionName =divisionName;
    }

    public String getDivisionName() {
        return divisionName;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryId() {
        return countryId;
    }

    @Override
    public String toString(){
        return getDivisionId() + ".  " + getDivisionName();
    }
}
