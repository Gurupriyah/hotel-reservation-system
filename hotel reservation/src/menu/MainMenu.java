package menu;

import api.*;
import model.FreeRoom;
import model.IRoom;
import model.Reservation;

import java.text.*;
import java.util.*;

public class MainMenu {

    private static final Scanner sc = new Scanner(System.in);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public static void main(String[] args) {
        boolean start = true;

        while (start) {
            System.out.println("\n------ Welcome to Eden Inn ------");
            System.out.println("\n-----------------------------------------------------");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin Menu");
            System.out.println("5. Exit");
            System.out.println("-----------------------------------------------------");
            System.out.print("Please select a number for the menu option: ");

            String serviceNo = sc.nextLine();

            switch (serviceNo) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    seeMyReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.start();
                    break;
                case "5":
                    start = false;
                    System.out.println("Thank you for choosing Eden Inn. We look forward to welcoming you again.");
                    break;
                default:
                    System.out.println("Please select a valid service.");

            }
        }
    }

    private static void createAccount() {
        String firstName;
        while (true) {
            System.out.print("Enter first name: ");
            firstName = sc.nextLine().trim();
            if (!firstName.isEmpty()) break;
            System.out.println("First name cannot be empty.");
        }
        String lastName;
        while (true) {
            System.out.print("Enter last name: ");
            lastName = sc.nextLine().trim();
            if (!lastName.isEmpty()) break;
            System.out.println("Last name cannot be empty.");
        }
        while (true) {
            try {
                System.out.print("Enter email: ");
                String email = sc.nextLine().trim();


                HotelResource.createACustomer(email, firstName, lastName);

                System.out.println("Account created successfully!");
                break;

            } catch (IllegalArgumentException e) {

                System.out.println("Please enter a valid email.");
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }

    }


    private static void seeMyReservations() {
        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        try {
            Collection<Reservation> reservations = HotelResource.getCustomersReservations(email);
            if (reservations.isEmpty()) {
                System.out.println("No reservations found ");
            } else {
                System.out.println("Your reservations:");
                for (Reservation reservation : reservations) {
                    System.out.println(reservation);
                }

            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void findAndReserveRoom() {

        Date checkIn = null;
        Date checkOut = null;
        dateFormat.setLenient(false);
        // Get valid check-in and check-out dates
        while (true) {
            try {
                System.out.print("Enter check-in date (dd/MM/yyyy): ");
                checkIn = dateFormat.parse(sc.nextLine());

                System.out.print("Enter check-out date (dd/MM/yyyy): ");
                checkOut = dateFormat.parse(sc.nextLine());

                Date today = dateFormat.parse(dateFormat.format(new Date()));

                if (checkIn.before(today)) {
                    throw new IllegalArgumentException("Check-in date cannot be in the past.");
                }

                if (!checkOut.after(checkIn)) {
                    throw new IllegalArgumentException(
                            "Check-out date must be after check-in date."
                    );
                }
                break;
            } catch (ParseException e) {
                System.out.println("Invalid date format. Please use dd/MM/yyyy.");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        // Ask user for room type preference
        String filterChoice = "";
        while (true) {
            System.out.print("Do you want to see (1) Paid rooms or (2) Free rooms? Enter 1 or 2: ");
            filterChoice = sc.nextLine().trim();
            if (filterChoice.equals("1") || filterChoice.equals("2")) break;
            System.out.println("Invalid choice. Please enter 1 or 2.");
        }
        // Find available rooms based on user preference
        Collection<IRoom> rooms = HotelResource.findARoom(checkIn, checkOut);
        List<IRoom> filteredRooms = new ArrayList<>();

        for (IRoom room : rooms) {
            if (filterChoice.equals("1") && room.getRoomPrice() > 0) {
                filteredRooms.add(room);
            } else if (filterChoice.equals("2") && room instanceof FreeRoom) {
                filteredRooms.add(room);
            }
        }
        // If no rooms available, suggest alternative dates
        if (filteredRooms.isEmpty()) {
            Calendar calendar = Calendar.getInstance();

            calendar.setTime(checkIn);
            calendar.add(Calendar.DATE, 7);
            Date newCheckIn = calendar.getTime();

            calendar.setTime(checkOut);
            calendar.add(Calendar.DATE, 7);
            Date newCheckOut = calendar.getTime();

            Collection<IRoom> alternativeRooms = HotelResource.findARoom(newCheckIn, newCheckOut);
            List<IRoom> alternativeFilteredRooms = new ArrayList<>();
            for (IRoom room : alternativeRooms) {
                if (filterChoice.equals("1") && room.getRoomPrice() > 0) {
                    alternativeFilteredRooms.add(room);
                } else if (filterChoice.equals("2") && room instanceof FreeRoom) {
                    alternativeFilteredRooms.add(room);
                }
            }
            if (alternativeFilteredRooms.isEmpty()) {
                System.out.println("No rooms available for these dates.");
                return;
            }
            System.out.println("No rooms available for the selected dates.");
            System.out.println("However, rooms are available if you shift your stay by 7 days:");
            System.out.println("New dates: " +
                            dateFormat.format(newCheckIn) + " → " +
                            dateFormat.format(newCheckOut));
            alternativeFilteredRooms.forEach(System.out::println);
            System.out.println("would you like to book a room for these new dates? (y/n): ");
            if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
                return;
            }
            checkIn = newCheckIn;
            checkOut = newCheckOut;
            filteredRooms = alternativeFilteredRooms;

            }
            //Display available rooms and proceed with booking
            System.out.println("Available rooms:");
            filteredRooms.forEach(System.out::println);
            // Prompt user to book a room
            while (true) {
                System.out.print("Would you like to book a room? (y/n): ");
                String choice = sc.nextLine().trim();

                if (choice.equalsIgnoreCase("y")) {
                    break;
                } else if (choice.equalsIgnoreCase("n")) {
                    return;
                } else {
                    System.out.println("Invalid input. Please enter 'y' or 'n'.");
                }
            }

            // Get user email and validate account
            String email;
            while (true) {
                System.out.print("Enter your email: ");
                email = sc.nextLine().trim();

                if (HotelResource.getCustomer(email) == null) {
                    System.out.println("No account found with this email.");
                    boolean createAccountChoice;
                    while (true) {
                        System.out.print("Would you like to create an account? (y/n): ");
                        String choice = sc.nextLine().trim();

                        if (choice.equalsIgnoreCase("y")) {
                            createAccountChoice = true;
                            break;
                        } else if (choice.equalsIgnoreCase("n")) {
                            createAccountChoice = false;
                            return;
                        } else {
                            System.out.println("Invalid input. Please enter 'y' or 'n'.");
                        }
                    }
                    if (createAccountChoice) {
                        createAccount();
                    } else {
                        System.out.println("You need an account to book a room. Booking cancelled.");
                        return;
                    }
                } else {
                    break;
                }
            }
            // Select room to book

            IRoom selectedRoom = null;

            while (true) {
                try {
                    System.out.print("Enter room number: ");
                    String roomNumber = sc.nextLine();

                    if (roomNumber == null || roomNumber.trim().isEmpty()) {
                        throw new IllegalArgumentException("Room number cannot be empty.");
                    }

                    selectedRoom = HotelResource.getRoom(roomNumber);

                    if (selectedRoom == null || !filteredRooms.contains(selectedRoom)) {
                        throw new IllegalArgumentException("Invalid room number. Please select from available rooms.");
                    }

                    break;

                } catch (IllegalArgumentException e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
            try {
                Reservation reservation = HotelResource.bookARoom(email, selectedRoom, checkIn, checkOut);
                System.out.println("Reservation successful!");
                System.out.println(reservation);
            } catch (IllegalArgumentException e) {
                System.out.println("Booking failed: " + e.getMessage());


                }


            }
        }





