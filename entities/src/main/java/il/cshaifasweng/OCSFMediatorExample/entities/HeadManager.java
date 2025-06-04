package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class HeadManager extends Employee implements Serializable {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "headManager")
    private List<Branch> branches;

    public HeadManager() {
        super(); // Call to the superclass's default constructor
    }

    // Parameterized constructor
    public HeadManager(int id, String fullName,String username, String password, String position,
                       boolean isOnline, String phoneNumber, String email, String branchName, List<Branch> branches) {
        super(id, fullName, password, username, position, isOnline, phoneNumber, email, branchName); // Initialize inherited fields
        this.branches = branches;
    }

    public void requestReport() {
        // Implementation
    }

    public void decideChangePrice() {
        // Implementation
    }

    public List<Branch> getBranches() {
        return branches;
    }
    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
