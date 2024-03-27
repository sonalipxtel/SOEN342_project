import flights.*;
import users.*;
import java.util.Scanner;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("\nWelcome to the Flight Management System");
            System.out.println("1. User operations");
            System.out.println("2. Registered user operations");
            System.out.println("3. Administrator operations");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    userOperations();
                    break;
                case 2:
                    registeredUserOperations();
                    break;
                case 3:
                    administratorOperations();
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void userOperations() {
        System.out.println("User Operations:");
        System.out.println("1. Get flight details by flight number");
        System.out.println("2. Get flight details by airports");
        System.out.print("Select an operation: ");
        int operation = scanner.nextInt();
        scanner.nextLine(); // consume the rest of the line

        User user = new User(001); 

        switch (operation) {
            case 1:
                System.out.print("Enter flight number: ");
                String flightNumber = scanner.nextLine();
                Flight flight = user.getFlightDetails(flightNumber);
                if (flight != null) {
                    System.out.println(flight);
                } else {
                    System.out.println("Flight not found.");
                }
                break;
            case 2:
                System.out.print("Enter source airport name: ");
                String source = scanner.nextLine();
                System.out.print("Enter destination airport name: ");
                String destination = scanner.nextLine();
                Flight flightByAirports = user.getFlightDetails(source, destination);
                if (flightByAirports != null) {
                    System.out.println(flightByAirports);
                } else {
                    System.out.println("Flight not found.");
                }
                break;
            default:
                System.out.println("Invalid operation selected.");
        }
    }

    private static void registeredUserOperations() {
        System.out.println("Registered User Operations:");
        System.out.println("1. Get detailed flight information by flight number");
        System.out.println("2. Get detailed flight information by source and destination");
        System.out.print("Select an operation: ");
        int operation = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        RegisteredUser registeredUser = new RegisteredUser(1, "j_sivalingam");

        switch (operation) {
            case 1:
                System.out.print("Enter flight number: ");
                String flightNumber = scanner.nextLine();
                FlightDetails flightDetails = registeredUser.getFlightDetails(flightNumber);
                if (flightDetails != null) {
                    System.out.println(flightDetails);
                } else {
                    System.out.println("Detailed flight information not found.");
                }
                break;
            case 2:
                System.out.print("Enter source airport name: ");
                String source = scanner.nextLine();
                System.out.print("Enter destination airport name: ");
                String destination = scanner.nextLine();
                FlightDetails flightDetailsByAirports = registeredUser.getFlightDetails(source, destination);
                if (flightDetailsByAirports != null) {
                    System.out.println(flightDetailsByAirports);
                } else {
                    System.out.println("Detailed flight information not found.");
                }
                break;
            default:
                System.out.println("Invalid operation selected.");
        }
    }

    private static void administratorOperations() {
        System.out.println("Administrator Operations:");
        System.out.println("1. Get private flight details by flight number");
        System.out.println("2. Get private flight details by source and destination");
        System.out.println("3. Add a new airport");
        System.out.print("Select an operation: ");
        int operation = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Administrator admin = new Administrator(2, "so_patel", Type.AIRPORT);

        switch (operation) {
            case 1:
                System.out.print("Enter private flight number: ");
                String flightNumber = scanner.nextLine();
                PrivateFlight privateFlight = admin.getPrivateFlight(flightNumber, admin.getU_name());

                if (privateFlight != null) {
                    System.out.println(privateFlight);
                } else {
                    System.out.println("Private flight details not found.");
                }
                break;
            case 2:
                System.out.print("Enter source airport name: ");
                String source = scanner.nextLine();
                System.out.print("Enter destination airport name: ");
                String destination = scanner.nextLine();
                PrivateFlight privateFlightByAirports = admin.getPrivateFlight(source, destination, admin.getU_name());

                if (privateFlightByAirports != null) {
                    System.out.println(privateFlightByAirports);
                } else {
                    System.out.println("Private flight details not found.");
                }
                break;
            case 3:
                if (admin.getAdminType() == Type.SYSTEM) {
                    System.out.print("Enter new airport name: ");
                    String ap_name = scanner.nextLine();
                    System.out.print("Enter new airport code: ");
                    String ap_code = scanner.nextLine();
                    admin.addAirport(ap_name, ap_code); // Adding a new Airport
                } else {
                    System.out.println("Only SYSTEM administrators can add new airports.");
                }
                break;
            default:
                System.out.println("Invalid operation selected.");
        }
    }

}
