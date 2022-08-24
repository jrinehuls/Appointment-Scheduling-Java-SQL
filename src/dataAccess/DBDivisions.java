package dataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Divisions;
import utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/** This class handles SQL queries regarding divisions. */
public class DBDivisions {

    /** This method returns all United States divisions in the database in an ObservableList.
     * An SQL query returns all divisions and division columns for divisions in the US. A new division instance is created for each<br>
     * division record using a while loop. The divisions are added to an ObservableList.
     * @return ObservableList of US first level divisions */
    public static ObservableList<Divisions> getAllUsDivisions() {
        ObservableList<Divisions> divisionsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions Where Country_ID = 1;";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("Division_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Divisions division = new Divisions(divisionId, divisionName, countryId);
                divisionsList.add(division);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return divisionsList;
    }

    /** This method returns all United Kingdom divisions in the database in an ObservableList.
     * An SQL query returns all divisions and division columns for divisions in the UK. A new division instance is created for each<br>
     * division record using a while loop. The divisions are added to an ObservableList.
     * @return ObservableList of UK first level divisions */
    public static ObservableList<Divisions> getAllUkDivisions() {
        ObservableList<Divisions> divisionsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions Where Country_ID = 2;";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("Division_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Divisions division = new Divisions(divisionId, divisionName, countryId);
                divisionsList.add(division);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return divisionsList;
    }

    /** This method returns all Canadian divisions in the database in an ObservableList.
     * An SQL query returns all divisions and division columns for divisions in Canada. A new division instance is created for each<br>
     * division record using a while loop. The divisions are added to an ObservableList.
     * @return ObservableList of Canadian first level divisions */
    public static ObservableList<Divisions> getAllCaDivisions() {
        ObservableList<Divisions> divisionsList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM first_level_divisions Where Country_ID = 3;";

        try{
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("Division_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                Divisions division = new Divisions(divisionId, divisionName, countryId);
                divisionsList.add(division);
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return divisionsList;
    }

    /** This method returns one selected division from the database.
     * An SQL query returns a division and all division columns. A while loop iterates through the query, which is only one division.<br>
     * a new division object is created and that division is returned.
     * @param divisionID The division ID of the division being searched
     * @return the division that has the ID of the parameter divisionID
     * */
    public static Divisions getSelectedDivision(int divisionID) {
        Divisions division = null;
        String sql = "SELECT * FROM first_level_divisions Where Division_ID = " + divisionID + ";";

        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                int divisionId = rs.getInt("Division_ID"); // could use column number starting from 1 ex: rs.getInt(1);
                String divisionName = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                division = new Divisions(divisionId, divisionName, countryId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return division;
    }
}
