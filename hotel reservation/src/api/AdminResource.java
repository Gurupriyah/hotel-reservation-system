package api;

import java.util.*;
import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {
    public static Customer getCustomer(String email){
        return CustomerService.getCustomer(email);
    }
    public static void addRoom(List<IRoom> rooms){
        for (IRoom room: rooms){
            ReservationService.addRoom(room);
        }
    }
    public static Collection<Customer> getAllCustomers(){
        return CustomerService.getAllCustomers();
    }
    public static Collection<IRoom> getAllRooms(){
        return ReservationService.getAllRooms();
    }
    public static void displayAllReservations(){
        ReservationService.printAllReservation();
    }

}
