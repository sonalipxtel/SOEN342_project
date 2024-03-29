import flights.*;
import users.*;
import java.util.Scanner;
import Locks.*;

public class App {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Lock lock = new Lock();


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
            scanner.nextLine();

            switch (choice) {
                case 1:
                    if (!lock.getLock("write")) {
                        lock.setLock("read", "locked");
                        try {
                            userOperations();
                        } finally {
                            lock.setLock("read", "unlocked");
                        }
                    } else {
                        System.out.println("\nAdministrator operations are currently in use. Please wait...");
                    }
                    break;
                case 2:
                    if (!lock.getLock("write")) {
                       lock.setLock("read", "locked");
                       try {
                            registeredUserOperations();
                        } finally {
                           lock.setLock("read", "unlocked");
                       }
                    } else {
                        System.out.println("\nAdministrator operations are currently in use. Please wait...");
                    }
                    break;
                case 3:
                    synchronized (Console.class) { // Ensure thread safety
                        if (!lock.getLock("write")) {
                            lock.setLock("write", "locked");
                            if (!lock.getLock("read")){
                                try {
                                    administratorOperations();
                                } finally {
                                    lock.setLock("write", "unlocked");
                                }
                            } else {
                                System.out.println("\nReader operations are currently in use. Please wait...");
                            }
                        } else {
                            System.out.println("\nAdministrator operations are currently in use. Please wait...");
                        }
                    }
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting the system...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void userOperations() {
        System.out.println("\nUser Operations:");
        System.out.println("1. Get flight details by flight number");
        System.out.println("2. Get flight details by airports");
        System.out.print("Select an operation: ");
        int operation = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        User user = new User(001);

        switch (operation) {
            case 1:
                System.out.print("\nEnter flight number: ");
                String flightNumber = scanner.nextLine();
                Flight flight = user.getFlightDetails(flightNumber);
                if (flight != null) {
                    System.out.println(flight);
                } else {
                    System.out.println("\nFlight not found.");
                }
                break;
            case 2:
                System.out.print("\nEnter source airport name: ");
                String source = scanner.nextLine();
                System.out.print("Enter destination airport name: ");
                String destination = scanner.nextLine();
                Flight flightByAirports = user.getFlightDetails(source, destination);
                if (flightByAirports != null) {
                    System.out.println(flightByAirports);
                } else {
                    System.out.println("\nFlight not found.");
                }
                break;
            default:
                System.out.println("\nInvalid operation selected.");
        }
    }

    private static void registeredUserOperations() {
        System.out.print("\nEnter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your user ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("\nRegistered User Operations:");
        System.out.println("1. Get detailed flight information by flight number");
        System.out.println("2. Get detailed flight information by source and destination");
        System.out.print("Select an operation: ");
        int operation = scanner.nextInt();
        scanner.nextLine();

        RegisteredUser registeredUser = new RegisteredUser(userID, username);

        switch (operation) {
            case 1:
                System.out.print("\nEnter flight number: ");
                String flightNumber = scanner.nextLine();
                FlightDetails flightDetails = registeredUser.getFlightDetails(flightNumber);
                if (flightDetails != null) {
                    System.out.println(flightDetails);
                } else {
                    System.out.println("\nDetailed flight information not found.");
                }
                break;
            case 2:
                System.out.print("\nEnter source airport name: ");
                String source = scanner.nextLine();
                System.out.print("Enter destination airport name: ");
                String destination = scanner.nextLine();
                FlightDetails flightDetailsByAirports = registeredUser.getFlightDetails(source, destination);
                if (flightDetailsByAirports != null) {
                    System.out.println(flightDetailsByAirports);
                } else {
                    System.out.println("\nDetailed flight information not found.");
                }
                break;
            default:
                System.out.println("\nInvalid operation selected.");
        }
    }

    private static void administratorOperations() {
        System.out.print("\nEnter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your user ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter your admin type (SYSTEM, AIRPORT, AIRLINE): ");
        String typeStr = scanner.nextLine(); // Get admin type

        System.out.println("\nAdministrator Operations:");
        System.out.println("1. Get private flight details by flight number");
        System.out.println("2. Get private flight details by source and destination");
        System.out.println("3. Add a new airport");
        System.out.println("4 Register a flight");
        System.out.println("5 Register a private flight");
        System.out.print("Select an operation: ");
        int operation = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Administrator admin = new Administrator(userID, username, Type.valueOf(typeStr.toUpperCase()));

        switch (operation) {
            case 1:
                if (admin.getAdminType() == Type.AIRPORT) {
                    System.out.print("\nEnter private flight number: ");
                    String flightNumber = scanner.nextLine();
                    PrivateFlight privateFlight = admin.getPrivateFlight(flightNumber, "no_burns");

                    if (privateFlight != null) {
                        System.out.println(privateFlight);
                    } else {
                        System.out.println("\nPrivate flight details not found.");
                    }
                } else {
                    System.out.println("\nOnly AIRPORT administrators can view private flight details.");
                }
                admin.closeConnection();
                break;
            case 2:
                if (admin.getAdminType() == Type.AIRPORT) {
                    System.out.print("\nEnter source airport name: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter destination airport name: ");
                    String destination = scanner.nextLine();
                    PrivateFlight privateFlightByAirports = admin.getPrivateFlight(source, destination,
                            "no_burns");

                    if (privateFlightByAirports != null) {
                        System.out.println(privateFlightByAirports);
                    } else {
                        System.out.println("\nPrivate flight details not found.");
                    }
                } else {
                    System.out.println("\nOnly AIRPORT administrators can view private flight details.");
                }
                admin.closeConnection();
                break;
            case 3:
                if (admin.getAdminType() == Type.SYSTEM) {
                    System.out.print("\nEnter new airport name: ");
                    String ap_name = scanner.nextLine();
                    System.out.print("Enter new airport code: ");
                    String ap_code = scanner.nextLine();
                    admin.addAirport(ap_name, ap_code); // Adding a new Airport
                } else {
                    System.out.println("\nOnly SYSTEM administrators can add new airports.");
                }
                admin.closeConnection();
                break;
            case 4:
                if (admin.getAdminType() == Type.AIRLINE) {
                    System.out.print("\nEnter flight number: ");
                    String f_number = scanner.nextLine();
                    System.out.print("\nEnter flight source: ");
                    String f_source = scanner.nextLine();
                    System.out.print("\nEnter flight destination: ");
                    String f_destination = scanner.nextLine();
                    System.out.print("\nEnter airline: ");
                    String f_airline = scanner.nextLine();
                    System.out.print("\nEnter aircaft model: ");
                    String f_aircraft = scanner.nextLine();
                    System.out.print("\nEnter departure time: ");
                    String f_dep = scanner.nextLine();
                    System.out.print("\nEnter arrival time: ");
                    String f_arr = scanner.nextLine();

                    admin.registerFlight(f_number, f_source, f_destination, f_airline, f_aircraft, f_dep, f_arr, admin.getAdminType());
                    admin.closeConnection();
                } else {
                    System.out.print("\nOnly AIRLINE administrators can register a flight");
                }
                admin.closeConnection();
                break;
            case 5:
                if (admin.getAdminType() == Type.AIRPORT) {
                    System.out.print("\nEnter flight number: ");
                    String f_number = scanner.nextLine();
                    System.out.print("\nEnter flight source: ");
                    String f_source = scanner.nextLine();
                    System.out.print("\nEnter flight destination: ");
                    String f_destination = scanner.nextLine();
                    System.out.print("\nEnter airline: ");
                    String f_airline = scanner.nextLine();
                    System.out.print("\nEnter aircaft model: ");
                    String f_aircraft = scanner.nextLine();
                    System.out.print("\nEnter departure time: ");
                    String f_dep = scanner.nextLine();
                    System.out.print("\nEnter arrival time: ");
                    String f_arr = scanner.nextLine();

                    admin.registerPrivateFlight(f_number, f_source, f_destination, f_airline, f_aircraft, f_dep, f_arr, admin.getAdminType());
                    admin.closeConnection();
                } else {
                    System.out.print("\nOnly AIRPORT administrators can register a private flight");
                }
                admin.closeConnection();
                break;
            default:
                System.out.println("\nInvalid operation selected.");
        }
    }
}
