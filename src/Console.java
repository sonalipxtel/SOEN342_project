import flights.*;
import users.*;
import java.util.Scanner;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

public class Console {

    private static final Scanner scanner = new Scanner(System.in);
    private static final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private static final ReadLock readLock = rwLock.readLock();
    private static final WriteLock writeLock = rwLock.writeLock();

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
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option, please try again.");
            }
        }
    }

    private static void userOperations() {
        readLock.lock(); // Acquire lock
        try {
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

        } finally {
            readLock.unlock(); // Release lock
        }
    }

    private static void registeredUserOperations() {
        readLock.lock();
        System.out.print("\nEnter your username: ");
        String username = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Enter your user ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        try {
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

        } finally {
            readLock.unlock(); // Release lock
        }
    }

    private static void administratorOperations() {
        writeLock.lock();
        System.out.print("\nEnter your username: ");
        String username = scanner.nextLine();
        scanner.nextLine();
        System.out.print("Enter your user ID: ");
        int userID = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter your admin type (SYSTEM, AIRPORT, AIRLINE): ");
        String typeStr = scanner.next(); // Get admin type

        try {
            System.out.println("\nAdministrator Operations:");
            System.out.println("1. Get private flight details by flight number");
            System.out.println("2. Get private flight details by source and destination");
            System.out.println("3. Add a new airport");
            System.out.print("Select an operation: ");
            int operation = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            Administrator admin = new Administrator(userID, username, Type.valueOf(typeStr.toUpperCase()));

            switch (operation) {
                case 1:
                    System.out.print("\nEnter private flight number: ");
                    String flightNumber = scanner.nextLine();
                    PrivateFlight privateFlight = admin.getPrivateFlight(flightNumber, username);

                    if (privateFlight != null) {
                        System.out.println(privateFlight);
                    } else {
                        System.out.println("\nPrivate flight details not found.");
                    }
                    break;
                case 2:
                    System.out.print("\nEnter source airport name: ");
                    String source = scanner.nextLine();
                    System.out.print("Enter destination airport name: ");
                    String destination = scanner.nextLine();
                    PrivateFlight privateFlightByAirports = admin.getPrivateFlight(source, destination,
                            admin.getU_name());

                    if (privateFlightByAirports != null) {
                        System.out.println(privateFlightByAirports);
                    } else {
                        System.out.println("\nPrivate flight details not found.");
                    }
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
                    break;
                default:
                    System.out.println("\nInvalid operation selected.");
            }
        } finally {
            writeLock.unlock(); // Release lock
        }
    }

}
