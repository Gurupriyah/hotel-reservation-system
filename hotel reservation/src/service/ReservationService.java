package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.*;


public class ReservationService {

    private static final Map<String,IRoom> rooms = new HashMap<>();
    private static final Set<Reservation> reservations = new HashSet<>();

    public static void addRoom (IRoom room){
        rooms.put(room.getRoomNumber(),room );
    }
    public static IRoom getARoom(String roomId){
        return rooms.get(roomId);
    }
    public static Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().equals(room)) {

                if (isOverlapping(checkInDate, checkOutDate, reservation.getCheckInDate(), reservation.getCheckOutDate())) {
                    throw new IllegalArgumentException("Room is already booked for the selected dates.");
                }
            }
        }

        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);reservations.add(reservation);
        return reservation;
    }

    public static Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        Collection<IRoom> availableRooms = new ArrayList<>();

        for (IRoom room : rooms.values()) {
            boolean isAvailable = true;

            for (Reservation reservation : reservations) {
                if (reservation.getRoom().equals(room)) {
                    if (isOverlapping(
                            checkInDate,
                            checkOutDate,
                            reservation.getCheckInDate(),
                            reservation.getCheckOutDate()
                    )) {
                        isAvailable = false;
                        break;
                    }
                }
            }

            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        return availableRooms;
    }

    public static Collection<Reservation> getCustomerReservation(Customer customer){
        Collection<Reservation> customerReservations = new ArrayList<>();

        for (Reservation reservation: reservations){
            if (reservation.getCustomer().equals(customer)){
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
    public static void printAllReservation(){
        for (Reservation reservation: reservations){
            System.out.println(reservation);
        }

    }
    public static Collection<IRoom> getAllRooms() {
        return rooms.values();
    }

    private static boolean isOverlapping(Date newCheckIn, Date newCheckOut, Date existingCheckIn, Date existingCheckOut){
        return newCheckIn.before(existingCheckOut) && newCheckOut.after(existingCheckIn);
    }
    public static Map<Date[], Collection<IRoom>> findRecommendedRooms(
            Date checkInDate,
            Date checkOutDate
    ) {
        Calendar cal = Calendar.getInstance();

        cal.setTime(checkInDate);
        cal.add(Calendar.DATE, 7);
        Date newCheckIn = cal.getTime();

        cal.setTime(checkOutDate);
        cal.add(Calendar.DATE, 7);
        Date newCheckOut = cal.getTime();

        Collection<IRoom> availableRooms =
                findRooms(newCheckIn, newCheckOut);

        Map<Date[], Collection<IRoom>> recommendation = new HashMap<>();
        recommendation.put(new Date[]{newCheckIn, newCheckOut}, availableRooms);

        return recommendation;
    }

}
