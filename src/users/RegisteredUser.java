package users;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import flights.*;

public class RegisteredUser extends User {

    private String u_name;
    private Connection connection;

    public RegisteredUser(int u_id, String u_name) {
        super(u_id);
        this.u_name = u_name;

        // Connect to the database
        try {
            this.connection = DriverManager
                    .getConnection("jdbc:sqlite:/C:\\SQLite\\sqlite-tools-win-x64-3450100\\flightsdb.db");

            // Checking if the username is valid
            if (!isValidUser(u_name)) {
                throw new IllegalArgumentException("Invalid username: " + u_name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidUser(String u_name) throws SQLException {
        String query = "SELECT * FROM registeredUsers WHERE u_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, u_name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if username exists, false otherwise
        }
    }

    public String getU_name() {
        return u_name;
    }

     // Get flight using flight number
    public FlightDetails getFlightDetails(String f_number) {
        FlightDetails flightdetails = null;
        String query = "SELECT number, source, destination, airline, aircraft, scheduled_dep, actual_dep, scheduled_arr, actual_arr FROM flights WHERE number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, f_number);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                // Retrieve data
                String number = rs.getString("number");
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                String airline = rs.getString("airline");
                String aircraft = rs.getString("aircraft");
                String scheduled_dep = rs.getString("scheduled_dep");
                String actual_dep = rs.getString("actual_dep");
                String scheduled_arr = rs.getString("scheduled_arr");
                String actual_arr = rs.getString("actual_arr");

                // Creating an airport object for 'source'
                Airport sourceAirport = getAirportDetails(source);

                // Creating an airport object for 'destination'
                Airport destAirport = getAirportDetails(destination);

                // Creating an airline object
                Airline usedAirline = getAirlineDetails(airline);

                // Creating an aircraft object
                Aircraft usedAircraft = getAircraftDetails(aircraft);

                // Create the Flight object
                flightdetails = new FlightDetails(number, sourceAirport, destAirport,
                        usedAirline, usedAircraft, scheduled_dep, actual_dep, scheduled_arr,
                        actual_arr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightdetails;

    }

     // Get flight using source/destination and user name
    public FlightDetails getFlightDetails(String source, String destination) {
        FlightDetails flightDetails = null;
        String query = "SELECT number, airline, aircraft, scheduled_dep, actual_dep, scheduled_arr, actual_arr FROM flights WHERE source = ? AND destination = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, source);
            pstmt.setString(2, destination);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Retrieve data
                String number = rs.getString("number");
                String airline = rs.getString("airline");
                String aircraft = rs.getString("aircraft");
                String scheduled_dep = rs.getString("scheduled_dep");
                String actual_dep = rs.getString("actual_dep");
                String scheduled_arr = rs.getString("scheduled_arr");
                String actual_arr = rs.getString("actual_arr");

                // Creating airport objects for source and destination
                Airport sourceAirport = getAirportDetails(source);
                Airport destAirport = getAirportDetails(destination);

                // Creating airline and aircraft objects
                Airline usedAirline = getAirlineDetails(airline);
                Aircraft usedAircraft = getAircraftDetails(aircraft);

                flightDetails = new FlightDetails(number, sourceAirport, destAirport,
                        usedAirline, usedAircraft, scheduled_dep, actual_dep, scheduled_arr,
                        actual_arr);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flightDetails;
    }

    public Airline getAirlineDetails(String airline) throws SQLException {
        String query = "SELECT al_name, al_code FROM airlines WHERE al_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, airline);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                // Retrieve data
                String al_name = rs.getString("al_name");
                String al_code = rs.getString("al_code");

                // Create and return the Airport object
                return new Airline(al_name, al_code);
            }
        }
        return null;
    }

    public Aircraft getAircraftDetails(String aircraft) throws SQLException {
        String query = "SELECT model, registrationNumber, status FROM aircrafts WHERE model = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, aircraft);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                // Retrieve data
                String model = rs.getString("model");
                String registrationNumber = rs.getString("registrationNumber");
                Status status = Status.valueOf(rs.getString("status"));

                // Create and return the Airport object
                return new Aircraft(model, registrationNumber, status);
            }
        }
        return null;
    }

}
