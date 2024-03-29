package users;

import java.sql.*;
import flights.*;

public class Administrator extends User {

    private String u_name;
    private Type adminType;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public Administrator(int u_id, String u_name, Type adminType) {
        super(u_id);
        this.u_name = u_name;
        this.adminType = adminType;

        // Connect to the database
        try {
            this.connection = DriverManager
                    .getConnection("jdbc:sqlite:/Users/noahburns/Downloads/SOEN342_project-noah2.0/src/Database/flightsdb.db");

            // Checking if the username is valid
//            if (!isValidAdministrator(u_name)) {
//                throw new IllegalArgumentException("Invalid username: " + u_name);
//            }
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isValidAdministrator(String u_name) throws SQLException {
        String query = "SELECT * FROM administrators WHERE u_name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, u_name);
            ResultSet rs = pstmt.executeQuery();
            return rs.next(); // Returns true if username exists, false otherwise
        }
    }

    public String getU_name() {
        return u_name;
    }
    public Type getAdminType() {
        return adminType;
    }

    public void setAdminType(Type adminType) {
        this.adminType = adminType;
    }

    // USE CASE 1 - IMPLEMENTATION
    // Get private flight using flight number and user name
    public PrivateFlight getPrivateFlight(String f_number, String u_name) {
        PrivateFlight privateFlightDetails = null;
        // Adjusted SQL query to include city information
        String query = "SELECT pf.number, pf.source, pf.destination, pf.airline, pf.aircraft, pf.scheduled_dep, pf.actual_dep, pf.scheduled_arr, pf.actual_arr, " +
                "c.c_name, c.c_country, c.c_temperature " +
                "FROM privateflights AS pf " +
                "JOIN city AS c ON pf.city_id = c.c_id " + // Join with city using city_id from privateflights
                "JOIN administrators AS a ON ((pf.source = a.specific OR c.c_name = a.specific) AND a.u_name = ?) " +
                "WHERE pf.number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, u_name);
            pstmt.setString(2, f_number);
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
                String c_name = rs.getString("c_name");
                String c_country = rs.getString("c_country");
                int c_temperature = rs.getInt("c_temperature");

                // Assuming getAirportDetails takes the city name and retrieves the corresponding airport
                Airport sourceAirport = getAirportDetails(source);
                Airport destinationAirport = getAirportDetails(destination);

                // Creating airline and aircraft objects
                Airline usedAirline = getAirlineDetails(airline);
                Aircraft usedAircraft = getAircraftDetails(aircraft);

                // Creating a city object
                City city = new City(c_name, c_country, c_temperature); // Assuming City constructor takes a String for temperature

                // Create the PrivateFlight object with the new city information
                privateFlightDetails = new PrivateFlight(number, sourceAirport, destinationAirport,
                        usedAirline, usedAircraft, scheduled_dep, actual_dep,
                        scheduled_arr, actual_arr, city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return privateFlightDetails;
    }



    // Get private flight using source/destination and user name
    public PrivateFlight getPrivateFlight(String source, String destination, String u_name) {
        PrivateFlight privateFlightDetails = null;
        String query = "SELECT pf.number, pf.source, pf.destination, pf.airline, pf.aircraft, pf.scheduled_dep, pf.actual_dep, pf.scheduled_arr, pf.actual_arr, " +
                "c.c_name, c.c_country, c.c_temperature " +
                "FROM privateflights AS pf " +
                "JOIN city AS c ON pf.city_id = c.c_id " +
                "JOIN administrators AS a ON ((pf.source = a.specific OR c.c_name = a.specific) AND a.u_name = ?) " +
                "WHERE pf.source = ? AND pf.destination = ? AND c.c_name = ? ";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, u_name);
            pstmt.setString(2, source);
            pstmt.setString(3, destination);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                // Retrieve data
                String number = rs.getString("number");
                String f_source = rs.getString("source");
                String f_destination = rs.getString("destination");
                String airline = rs.getString("airline");
                String aircraft = rs.getString("aircraft");
                String scheduled_dep = rs.getString("scheduled_dep");
                String actual_dep = rs.getString("actual_dep");
                String scheduled_arr = rs.getString("scheduled_arr");
                String actual_arr = rs.getString("actual_arr");
                String c_name = rs.getString("c_name"); // City name
                String c_country = rs.getString("c_country"); // City country
                int c_temperature = rs.getInt("c_temperature"); // City temperature

                // Assuming getAirportDetails takes the city name and retrieves the corresponding airport
                Airport sourceAirport = getAirportDetails(f_source);
                Airport destinationAirport = getAirportDetails(f_destination);

                // Creating airline and aircraft objects
                Airline usedAirline = getAirlineDetails(airline);
                Aircraft usedAircraft = getAircraftDetails(aircraft);

                // Creating a city object
                City city = new City(c_name, c_country, c_temperature);

                // Create the PrivateFlight object with the new city information
                privateFlightDetails = new PrivateFlight(number, sourceAirport, destinationAirport,
                        usedAirline, usedAircraft, scheduled_dep, actual_dep,
                        scheduled_arr, actual_arr, city);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return privateFlightDetails;
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

    // USE CASE 2 IMPLEMENTATION
    // Method to register non-private flight
    public void registerFlight(String f_number, String source, String destination, String airline,
                               String aircraft,
                               String scheduled_dep, String scheduled_arr, Type adminType) {
        if (adminType == Type.AIRLINE) {
            try {
                // Check if scheduled departure and arrival times are unique to the airport
                boolean isUnique = checkUniqueTimes(source, scheduled_dep, scheduled_arr);

                if (isUnique) {
                    // Check if there is an available aircraft in the source airport
                    boolean isAircraftAvailable = checkAircraftAvailability(aircraft);

                    if (isAircraftAvailable) {
                        // Insert flight into the database
                        statement = connection.createStatement();
                        String query = "INSERT INTO flights (number, source, destination, airline, aircraft, scheduled_dep, scheduled_arr) "
                                +
                                "VALUES ('" + f_number + "', '" + source + "', '"
                                + destination + "', '" + airline + "', '"
                                + aircraft + "', '" + scheduled_dep + "', '" + scheduled_arr + "')";
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
        } else {
            System.out.println("Only AIRLINE administrators can register flights.");
        }
    }

    // Method to register private flight
    public void registerPrivateFlight(String f_number, String source, String destination, String airline,
                                      String aircraft, String scheduledDep, String scheduledArr, Type adminType) {
        if (adminType == Type.AIRPORT) {
            try {
                // Check if scheduled departure and arrival times are unique to the airport
                boolean isUnique = checkPrivateUniqueTimes(source, scheduledDep, scheduledArr);

                if (isUnique) {
                    // Check if there is an available aircraft in the source airport
                    boolean isAircraftAvailable = checkAircraftAvailability(aircraft);

                    if (isAircraftAvailable) {
                        // Insert flight into the database
                        statement = connection.createStatement();
                        String query = "INSERT INTO privateflights (number, source, destination, airline, aircraft, scheduled_dep, scheduled_arr) "
                                +
                                "VALUES ('" + f_number + "', '" + source + "', '"
                                + destination + "', '" + airline + "', '"
                                + aircraft + "', '" + scheduledDep + "', '" + scheduledArr + "')";
                        statement.executeUpdate(query);
                        System.out.println("Private Flight registered successfully.");
                    } else {
                        System.out.println("No available aircraft in the source airport.");
                    }
                } else {
                    System.out.println("Scheduled departure and arrival times are not unique to the airport.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Only AIRPORT administrators can register flights.");
        }
    }

    // Method to check if the time for the flight is unique to the airport
    public boolean checkUniqueTimes(String source, String scheduled_dep, String scheduled_arr)
            throws SQLException {
        String query = "SELECT COUNT(*) FROM flights WHERE source = ? " +
                "AND (scheduled_dep = ? OR scheduled_arr = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, source);
        preparedStatement.setString(2, scheduled_dep);
        preparedStatement.setString(3, scheduled_arr);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count == 0;
        }
        return false;
    }

    // Method to check if the time for the private flight is unique to the airport
    public boolean checkPrivateUniqueTimes(String source, String scheduled_dep, String scheduled_arr)
            throws SQLException {
        String query = "SELECT COUNT(*) FROM privateflights WHERE source = ? " +
                "AND (scheduled_dep = ? OR scheduled_arr = ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, source);
        preparedStatement.setString(2, scheduled_dep);
        preparedStatement.setString(3, scheduled_arr);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int count = resultSet.getInt(1);
            return count == 0;
        }
        return false;
    }

    // Method to check if the aircraft is available at the source airport
    public boolean checkAircraftAvailability(String model) throws SQLException {
        try {
            String query = "SELECT COUNT(*) FROM aircrafts WHERE model = ? AND status = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, model);
            preparedStatement.setString(2, "ON_LAND");
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
        return false;
    }

    // USE CASE 3 -IMPLEMENTATION
    // System administrators can enter records on airports.
    public void addAirport(String ap_name, String ap_code) {
        if (adminType == Type.SYSTEM) {
            try {
                // Insert airport record into the database
                statement = connection.createStatement();
                String query = "INSERT INTO airports (ap_name, ap_code) " +
                        "VALUES ('" + ap_name + "', '" + ap_code + "')";
                statement.executeUpdate(query);
                System.out.println("Airport added successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Only SYSTEM administrators can add airports.");
        }
    }

    // Close the connection to the database
    public void closeConnection() {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}