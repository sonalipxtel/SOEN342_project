import flights.Flight;
import users.User;

public class App {

    public static void main(String[] args) throws Exception {
        User user = new User(001);

        // Call the getFlightDetails method with a flight number
        String flightNumber = "AC305";
        Flight flight = user.getFlightDetails(flightNumber);

        // Check if flight details were found
        if (flight != null) {
            // Print flight details
            System.out.println("Flight Number: " + flight.getF_number());
            System.out.println("Source: " + flight.getSource().getAp_name());
            System.out.println("Destination: " + flight.getDestination().getAp_name());
        } else {
            System.out.println("Flight not found.");
        }
    }
}
