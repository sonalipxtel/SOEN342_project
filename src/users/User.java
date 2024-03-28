package users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import flights.*;

public class User {

    private int u_id;
    private Connection connection;

    public User(int u_id) {
        this.u_id = u_id;

        // Connect to the database
        try {
            this.connection = DriverManager
                    .getConnection("jdbc:sqlite:/Users/noahburns/Downloads/SOEN342_project/src/Database/flightsdb.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public Flight getFlightDetails(String f_number) {
        Flight flight = null;
        String query = "SELECT number, source, destination FROM flights WHERE number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, f_number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                // Retrieve data
                String number = rs.getString("number");
                String source = rs.getString("source");
                String destination = rs.getString("destination");

                // Fetch airport details for source
                Airport sourceAirport = getAirportDetails(source);

                // Fetch airport details for destination
                Airport destAirport = getAirportDetails(destination);

                // Create the Flight object
                flight = new Flight(number, sourceAirport, destAirport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    public Flight getFlightDetails(String source, String destination) {
        Flight flight = null;
        String query = "SELECT number FROM flights WHERE source = ? AND destination = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String flightNumber = rs.getString("number");
                // Fetch airport details for source
                Airport sourceAirport = getAirportDetails(source);

                // Fetch airport details for destination
                Airport destAirport = getAirportDetails(destination);

                // Create the Flight object
                flight = new Flight(flightNumber, sourceAirport, destAirport);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

    public Airport getAirportDetails(String airport) throws SQLException {
        String query = "SELECT ap_name, ap_code FROM airports WHERE ap_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, airport);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                // Retrieve data
                String ap_name = rs.getString("ap_name");
                String ap_code = rs.getString("ap_code");

                // Create and return the Airport object
                return new Airport(ap_name, ap_code);
            }
        }
        return null;
    }
}
