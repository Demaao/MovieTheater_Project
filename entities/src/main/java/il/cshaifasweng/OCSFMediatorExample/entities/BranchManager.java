package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class BranchManager extends Employee implements Serializable {

    @OneToOne(mappedBy = "branchManager", cascade = CascadeType.ALL)  // Correct reference to the owning field
    private Branch branch;

    public BranchManager() {
        super(); // Call to the superclass's default constructor
    }

    // Parameterized constructor
    public BranchManager(int id, String fullName,String username, String password, String position,
                       boolean isOnline, String phoneNumber, String email, String branchName, Branch branch) {
        super(id, fullName, password, username, position, isOnline, phoneNumber, email, branchName); // Initialize inherited fields
        this.branch = branch;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

   /* public Report showReport() {
        // Implementation
        return new Report();
    } */
}
