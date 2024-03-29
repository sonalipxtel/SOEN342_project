package flights;

import java.sql.*;

public class Airport {
    private String ap_name;
    private String ap_code;


    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public Airport(String ap_name, String ap_code) {
        this.ap_name = ap_name;
        this.ap_code = ap_code;


        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:/Users/noahburns/Downloads/SOEN342_project-noah2.0/src/Database/flightsdb.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method used in User class
    public Airport(String ap_name) {

        this.ap_name = ap_name;
    }

    public String getAp_name() {

        return ap_name;
    }

    public void setAp_name(String ap_name) {

        this.ap_name = ap_name;
    }

    public String getAp_code() {

        return ap_code;
    }

    public void setAp_code(String ap_code) {
        this.ap_code = ap_code;
    }

    public String toString() {
        return "Airport Name: " + ap_name + ", Airport Code: " + ap_code;
    }

    public static void registerPrivateFlight(String f_number, Airport source, Airport destination, Airline airline, Aircraft aircraft, String scheduledDep, String scheduledArr) {
        try {
            // Check if scheduled departure and arrival times are unique to the airport
            boolean isUnique = checkUniqueTimes(source.getAp_code(), scheduledDep, scheduledArr);

            if (isUnique) {
                // Check if there is an available aircraft in the source airport
                boolean isAircraftAvailable = checkAircraftAvailability(aircraft);

                if (isAircraftAvailable) {
                    // Insert flight into the database
                    statement = connection.createStatement();
                    String query = "INSERT INTO flights (flightNumber, sourceAirportCode, scheduledDepartureTime, scheduledArrivalTime, aircraftRegistrationNumber) " +
                            "VALUES ('" + f_number + "', '" + source.getAp_code() + "', '" + scheduledDep + "', '" + scheduledArr + "', '" + aircraft.getRegistrationNumber() + "')";
                    statement.executeUpdate(query);
                    System.out.println("Flight registered successfully.");
                } else {
                    System.out.println("No available aircraft in the source airport.");
                }
            } else {
                System.out.println("Scheduled departure and arrival times are not unique to the airport.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkUniqueTimes(String ap_code, String scheduledDep, String scheduledArr) throws SQLException {
        String query = "SELECT COUNT(*) FROM flights WHERE sourceAirportCode = ? " +
                "AND (scheduledDepartureTime = ? OR scheduledArrivalTime = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, ap_code);
        preparedStatement.setString(2, scheduledDep);
        preparedStatement.setString(3, scheduledArr);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count == 0;
        }
        return false;
    }

    public static boolean checkAircraftAvailability(Aircraft aircraft) throws SQLException {
        String aircraftRegistrationNumber = aircraft.getRegistrationNumber();
        String query = "SELECT COUNT(*) FROM aircrafts WHERE registrationNumber = ? AND status = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, aircraftRegistrationNumber);
        preparedStatement.setString(2, Status.ON_LAND.name()); 
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
        return false;
    }


}
