package service;
import model.Customer;
import java.util.*;

public class CustomerService {

    private static final Map<String, Customer> customers =new HashMap<>();

    public static void addCustomer(String email,String firstName,String lastName){
        if (customers.containsKey(email)){
            throw new IllegalArgumentException("Customer with this email already exists");
        }
        Customer customer = new Customer(firstName, lastName, email);
        customers.put(email,customer);

    }
    public static Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);

    }
    public static Collection<Customer> getAllCustomers(){
        return customers.values();

    }
}
