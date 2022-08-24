package dataAccess;

import utility.JDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Countries;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** This class handles SQL queries regarding countries. */
public class DBCountries {

    /** This method returns all countries in the database in an ObservableList.
     * An SQL query returns all countries and columns for countries. A new country instance is created for each<br>
     * country record using a while loop. The countries are added to an ObservableList.
     * @return ObservableList of countries */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countriesList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM countries;";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("Country_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String countryName = rs.getString("Country");

                Countries country = new Countries(countryId, countryName);
                countriesList.add(country);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return countriesList;
    }

    /** This method returns one selected country from the database.
     * An SQL query returns the country that has the division with the division ID of the parameter passed into the constructor and<br>
     * columns for division ID, country ID, and country name. A while loop iterates through the single field and a new country instance is<br>
     * created. the country ID and name are passed into the constructor of the new country, and it is returned.
     * @param divisionID The division ID for which to find its associated country
     * @return the country associated with the divisionID parameter
     * */
    public static Countries getSelectedCountry(int divisionID) {
        Countries country = null;
        String sql = "SELECT Division_ID, countries.Country_ID, Country\n" +
                "FROM first_level_divisions, countries\n" +
                "WHERE first_level_divisions.Country_ID = countries.Country_ID and Division_ID =" + divisionID + ";";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int countryId = rs.getInt("Country_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String countryName = rs.getString("Country");

                country = new Countries(countryId, countryName);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return country;
    }

}
