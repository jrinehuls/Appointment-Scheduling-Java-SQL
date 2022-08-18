package dataAccess;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Divisions;
import utility.JDBC;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBDivisions {

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
