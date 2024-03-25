package flights;

import java.sql.*;

public class Airline {
    private String al_name;
    private String al_code;

    // JDBC variables for opening, closing, and managing connection
    private static Connection connection;
    private static Statement statement;
    private static ResultSet resultSet;

    public Airline(String al_name, String al_code) {
        this.al_name = al_name;
        this.al_code = al_code;

        try {
            // Connect to the database
            connection = DriverManager.getConnection("jdbc:sqlite:/C:\\SQLite\\sqlite-tools-win-x64-3450100\\flightsdb.db");
            addAirline();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getAl_name() {
        return al_name;
    }

    public void setAl_name(String al_name) {
        this.al_name = al_name;
    }

    public String getAl_code() {
        return al_code;
    }

    public void setAl_code(String al_code) {
        this.al_code = al_code;
    }

    public String toString() {
        return "Airline Name: " + al_name + ", Airline Code: " + al_code;
    }

    private void addAirline() throws SQLException {
        statement = connection.createStatement();
        String query = "INSERT INTO airlines (al_name, al_code) VALUES ('" + al_name + "', '" + al_code + "')";
        statement.executeUpdate(query);
        System.out.println("Airline added to the database successfully.");
    }

    public static void registerFlight(String f_number, Airport source, Airport destination, Airline airline, Aircraft aircraft, String scheduledDep, String scheduledArr) {
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
        String query = "SELECT COUNT(*) FROM aircrafts WHERE registrationNumber = ? AND isAvailable = 1";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, aircraftRegistrationNumber);
        resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count > 0;
        }
        return false;
    }

}
