package menu;

import api.*;
import model.*;
import model.IRoom;

import java.text.SimpleDateFormat;
import java.util.*;


public class AdminMenu {

    private static final Scanner sc = new Scanner(System.in);

    public static void start() {
        boolean start = true;

        while (start) {
            System.out.println("\n Admin Menu ");
            System.out.println("-----------------------------------------------------");
            System.out.println("1. See all customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a room");
            System.out.println("5. Test data");
            System.out.println("6. Back to Main Menu");
            System.out.println("-----------------------------------------------------");
            System.out.print("Please select a number for the menu option: ");

            String serviceNo = sc.nextLine();

            switch (serviceNo) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    testData();
                    break;
                case "6":
                    start = false;
                    break;
                default:
                    System.out.println("Please select a valid option.");
            }
        }

    }

    private static void seeAllCustomers() {
        Collection<Customer> customers = AdminResource.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Customer details: ");
            customers.forEach(System.out::println);
        }
    }

    private static void seeAllRooms() {
        Collection<IRoom> rooms = AdminResource.getAllRooms();
        if (rooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println("All Rooms:");
            rooms.forEach(System.out::println);
        }
    }

    private static void seeAllReservations() {
        System.out.println("All Reservations:");
        AdminResource.displayAllReservations();
    }

    private static void addRoom() {
        List<IRoom> roomsToAdd = new ArrayList<>();
        boolean adding = true;
        while (adding) {
            try {
                System.out.print("Enter room number: ");
                String roomNumber = sc.nextLine();
                if (roomNumber.isEmpty()) {
                    throw new IllegalArgumentException("Room number cannot be empty.");
                }

                double price = getPriceFromAdmin();
                RoomType roomType = getRoomTypeFromAdmin();

                IRoom room = new Room(roomNumber, price, roomType);
                roomsToAdd.add(room);

                System.out.print("Add another room? (y/n): ");
                if (!sc.nextLine().equalsIgnoreCase("y")) {
                    adding = false;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        AdminResource.addRoom(roomsToAdd);

    }
    private static RoomType getRoomTypeFromAdmin() {
        while (true) {
            System.out.println("Enter room type (1 for single bed, 2 for double bed, 3 for twin bed, 4 for triple bed, 5 for quad bed: ");

            String input = sc.nextLine().trim();

            switch (input) {
                case "1": return RoomType.SINGLE;
                case "2": return RoomType.DOUBLE;
                case "3": return RoomType.TWIN;
                case "4": return RoomType.TRIPLE;
                case "5": return RoomType.QUAD;
                default:
                    System.out.println("Invalid room type. Please try again.");
            }
        }
    }
    private static double getPriceFromAdmin() {
        while (true) {
            System.out.print("Enter price per night: ");

            try {
                double price = Double.parseDouble(sc.nextLine());
                if (price <= 0) {
                    System.out.println("Price must be greater than 0.");
                } else {
                    return price;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }
    private static void testData() {
        System.out.println("Populating test data...");

        try {
            HotelResource.createACustomer("guru@example.com", "Guru", "Priya");
            HotelResource.createACustomer("roh243@example.com", "Roh", "Roh");
            HotelResource.createACustomer("sha.06@example.com", "Sha", "Hari");
            System.out.println("Customers added.");
        } catch (IllegalArgumentException e) {
            System.out.println("Some customers already exist: " + e.getMessage());
        }

        List<IRoom> testRooms = new ArrayList<>();
        testRooms.add(new Room("101", 100.0, RoomType.SINGLE));
        testRooms.add(new Room("102", 150.0, RoomType.DOUBLE));
        testRooms.add(new FreeRoom("103",  RoomType.SINGLE));
        testRooms.add(new Room("201", 200.0, RoomType.DOUBLE));
        testRooms.add(new FreeRoom("202",  RoomType.TWIN));
        AdminResource.addRoom(testRooms);
        System.out.println("Rooms added.");

        try {
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
            HotelResource.bookARoom("guru@example.com", AdminResource.getAllRooms().iterator().next(),
                    df.parse("25/12/2026"), df.parse("27/12/2026"));
            HotelResource.bookARoom("roh243@example.com", AdminResource.getAllRooms().iterator().next(),
                    df.parse("28/12/2026"), df.parse("30/12/2026"));
            System.out.println("Reservations added.");
        } catch (Exception e) {
            System.out.println("Error creating reservations: " + e.getMessage());
        }

        System.out.println("Test data population completed!");
    }



}
