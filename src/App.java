import flights.*;
import users.*;

public class App {

    public static void main(String[] args) throws Exception {

        // USE CASE 1
        // TESTING USER
        User user_1 = new User(001);
        Flight flight_1 = user_1.getFlightDetails("AC305");

        if (flight_1 != null) {
            System.out.println(flight_1);
        } else {
            System.out.println("Flight not found.");
        }

        User user_2 = new User(002);
        Flight flight_2 = user_2.getFlightDetails("Montréal-Pierre Elliott Trudeau International Airport",
                "Vancouver International Airport");

        if (flight_2 != null) {
            System.out.println(flight_2);
        } else {
            System.out.println("Flight not found.");
        }

        // // TESTING REGISTERED USER
        RegisteredUser registeredUser_1 = new RegisteredUser(1, "j_sivalingam");
        FlightDetails flightDetails_1 = registeredUser_1.getFlightDetails("AC405");

        if (flightDetails_1 != null) {
            System.out.println(flightDetails_1);
        } else {
            System.out.println("Flight details not found.");
        }

        RegisteredUser registeredUser_2 = new RegisteredUser(2, "s_patel");
        FlightDetails flightDetails_2 = registeredUser_2.getFlightDetails("AC405");

        if (flightDetails_2 != null) {
            System.out.println(flightDetails_2);
        } else {
            System.out.println("Flight details not found.");
        }

        // // TESTING ADMINISTRATOR
        Administrator administrator_1 = new Administrator(1, "je_sivalingam",
                Type.AIRPORT);
        PrivateFlight privateFlight_1 = administrator_1.getPrivateFlight("AC1004",
                "je_sivalingam");

        if (privateFlight_1 != null) {
            System.out.println(privateFlight_1);
        } else {
            System.out.println("Flight details not found.");
        }

        Administrator administrator_2 = new Administrator(2, "so_patel",
                Type.AIRLINE);
        PrivateFlight privateFlight_2 = administrator_2.getPrivateFlight(
                "Montréal-Pierre Elliott Trudeau International Airport",
                "Fort Lauderdale-Hollywood International Airport",
                "so_patel");

        if (privateFlight_2 != null) {
            System.out.println(privateFlight_2);
        } else {
            System.out.println("Flight details not found.");
        }

        // USE CASE 2
        // TESTING FLIGHT REGISTERING

        /* Testing registerFlight(...) */
        Administrator administrator_4 = new Administrator(5, "jenisha_sivalingam",
                Type.AIRLINE);

        administrator_4.registerFlight("AC567", "Montréal-Pierre Elliott Trudeau International Airport",
                "John F. Kennedy International Airport", "Air_Canada", "Airbus_A220-300", "2024-03-20 09:30:00",
                "2024-03-20 12:30:00", administrator_4.getAdminType());

        // /* Testing registerPrivateFlight(...) */
        // Administrator administrator_5 = new Administrator(5, "je_sivalingam",
        // Type.AIRPORT);
        // Administrator.registerPrivateFlight("AC223", YUL, JFK, AC, airbus,
        // "2024-03-20 09:30:00", "2024-03-20 12:30:00",
        // administrator_5.getAdminType());

        // USE CASE 3
        // System administrators can enter records on airports.
        // Administrator administrator_3 = new Administrator(4, "sonali_patel",
        // Type.SYSTEM);
        // administrator_3.addAirport("John F. Kennedy International Airport", "JFK");

    }
}
