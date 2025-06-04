package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class CustomerServiceWorker extends Employee implements Serializable {
    public CustomerServiceWorker() {
        super(); // Call to the superclass's default constructor
    }

    // Parameterized constructor
    public CustomerServiceWorker(int id, String fullName,String username, String password, String position,
                          boolean isOnline, String phoneNumber, String email, String branchName) {
        super(id, fullName, password, username, position, isOnline, phoneNumber, email, branchName); // Initialize inherited fields
    }


    public String reply() {
        // Implementation
        return "Reply";
    }

    public String refund() {
        // Implementation
        return "Refund";
    }
}
