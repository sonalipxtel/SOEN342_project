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

        try {
            this.connection = DriverManager
                    .getConnection("jdbc:sqlite:/C:\\SQLite\\sqlite-tools-win-x64-3450100\\flightsdb.db");
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
                // Retrieve data from the result set
                String number = rs.getString("number");
                String source = rs.getString("source");
                String destination = rs.getString("destination");
                // Create the Flight object
                flight = new Flight(number, new Airport(source), new Airport(destination));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flight;
    }

}
