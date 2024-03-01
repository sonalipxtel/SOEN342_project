package users;

import flights.*;

public class User {

    private int u_id;

    public User(int u_id) {
        this.u_id = u_id;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public Flight getFlightDetails(String f_number) {
        Airport source = new Airport("ABC", "DEF");
        Airport destination = new Airport("GHI", "JKL");
        // Create a Flight object
        Flight flight = new Flight(f_number, source, destination);
        return flight;

    }

}
