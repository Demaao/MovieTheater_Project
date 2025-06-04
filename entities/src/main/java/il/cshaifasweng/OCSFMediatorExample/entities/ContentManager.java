package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.List;

@Entity
public class ContentManager extends Employee implements Serializable {

    public ContentManager() {
        super(); // Call to the superclass's default constructor
    }

    // Parameterized constructor
    public ContentManager(int id, String fullName,String username, String password, String position,
                       boolean isOnline, String phoneNumber, String email, String branchName) {
        super(id, fullName, password, username, position, isOnline, phoneNumber, email, branchName); // Initialize inherited fields
    }
    public void updatePrice() {
        // Implementation
    }

    public void addMovie() {
        // Implementation
    }

    public void deleteMovie() {
        // Implementation
    }

    public void updateScreeningTime() {
        // Implementation
    }
}
