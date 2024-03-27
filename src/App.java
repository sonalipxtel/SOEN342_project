import flights.*;
import users.*;

public class App {

    public static void main(String[] args) throws Exception {

        // USE CASE 1
        // // TESTING NON-REGISTERED USER
        User user_1 = new User(001);
        Flight flight_1 = user_1.getFlightDetails("AC305");

        if (flight_1 != null) {
            System.out.println(flight_1);
        } else {
            System.out.println("Flight not found.");
        }

        System.out.println();

        User user_2 = new User(002);
        Flight flight_2 = user_2.getFlightDetails("Montréal-Pierre Elliott Trudeau International Airport",
                "Vancouver International Airport");

        if (flight_2 != null) {
            System.out.println(flight_2);
        } else {
            System.out.println("Flight not found.");
        }

        System.out.println();

        // // TESTING REGISTERED USER
        RegisteredUser registeredUser_1 = new RegisteredUser(1, "j_sivalingam");
        FlightDetails flightDetails_1 = registeredUser_1.getFlightDetails("AC405");

        if (flightDetails_1 != null) {
            System.out.println(flightDetails_1);
        } else {
            System.out.println("Flight details not found.");
        }

        System.out.println();

        RegisteredUser registeredUser_2 = new RegisteredUser(2, "s_patel");
        FlightDetails flightDetails_2 = registeredUser_2.getFlightDetails("AC405");

        if (flightDetails_2 != null) {
            System.out.println(flightDetails_2);
        } else {
            System.out.println("Flight details not found.");
        }

        System.out.println();

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

        System.out.println();

        // USE CASE 2
        // Testing registerFlight
        Administrator administrator_5 = new Administrator(5, "jenisha_sivalingam",
                Type.AIRLINE);

        administrator_5.registerFlight("AC567", "Montréal-Pierre Elliott Trudeau International Airport",
                "John F. Kennedy International Airport", "Air_Canada", "Airbus_A220-300", "2024-03-20 09:30:00",
                "2024-03-20 12:30:00", administrator_5.getAdminType());

        System.out.println();

        // Testing registerPrivateFlight
        Administrator administrator_3 = new Administrator(3, "no_burns",
                Type.AIRPORT);
        administrator_3.registerPrivateFlight("AC223", "Montréal-Pierre Elliott Trudeau International Airport",
                "John F. Kennedy International Airport", "Air_Canada", "Airbus_A319-100",
                "2024-05-25 10:30:00", "2024-05-26 11:45:00", administrator_3.getAdminType());

        System.out.println();

        // USE CASE 3
        // System administrators can enter records on airports.
        Administrator administrator_4 = new Administrator(4, "sonali_patel",
                Type.SYSTEM);
        administrator_4.addAirport("John F. Kennedy International Airport", "JFK");

    }
}
