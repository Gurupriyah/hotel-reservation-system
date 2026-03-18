package model;
import java.util.regex.Pattern;

public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(String firstName,String lastName, String email){
        this.firstName=firstName;
        this.lastName=lastName;

        if (Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",email)){
            this.email=email;
        }else{
            throw new IllegalArgumentException("Invalid email address");
        }
    }
    // getter
    public final String getFirstName() {
        return firstName;
    }
    public final String getLastName() {
        return lastName;
    }
    public final String getEmail() {
        return email;
    }



    @Override
    public final String toString(){
        return ("Name: "+ firstName+" "+ lastName+" ;Email: "+email);
    }
}
