package users;

import flights.*;

public class RegisteredUser extends User {

    private String u_name;

    public RegisteredUser(int u_id, String u_name) {
        super(u_id);
        this.u_name = u_name;
    }

    public String getU_name() {
        return u_name;
    }

    // public FlightDetails getFlightDetails(String f_number) {
    // return flightDetails;

    // }

}
