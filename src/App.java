import flights.Flight;
import flights.FlightDetails;
import users.RegisteredUser;
import users.User;

public class App {

    public static void main(String[] args) throws Exception {
        User user = new User(001);

        // Call the getFlightDetails method with a flight number
        String flightNumber = "AC305";
        Flight flight = user.getFlightDetails(flightNumber);

        // Check if flight details were found
        if (flight != null) {
            System.out.println(flight);
        } else {
            System.out.println("Flight not found.");
        }

        RegisteredUser registeredUser = new RegisteredUser(1, "j_sivalingam");

        // Call the getFlightDetails method
        FlightDetails flightDetails = registeredUser.getFlightDetails("AC405");

        // Check if flightDetails is not null
        if (flightDetails != null) {
            System.out.println(flightDetails);
        } else {
            System.out.println("Flight details not found.");
        }
    }
}
