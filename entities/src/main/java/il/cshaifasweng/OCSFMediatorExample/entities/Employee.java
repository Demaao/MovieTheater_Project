package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(name = "employees")
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Employee implements Serializable{
    @Id
    protected int id;
    protected String fullName;
    protected String password;
    protected String username;
    protected String position;
    protected boolean isOnline;
    protected String phoneNumber;
    protected String email;
    protected String branchName;

    public Employee(int id, String fullName, String password, String username, String position,
                    boolean isOnline, String phoneNumber, String email, String branchName) {
        this.id = id;
        this.fullName = fullName;
        this.password = password;
        this.username = username;
        this.position = position;
        this.isOnline = isOnline;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.branchName = branchName;
    }

    public Employee() {

    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public String getPosition() {
        return position;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getBranchName() {
        return branchName;
    }
    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

   /* public int login(String username, String password) {
        if (username.equals(this.username) && password.equals(this.password)) {
            setIsOnline(true);
            return 1;
        }
        return 0;
    }

    public void logout() {
        setIsOnline(false);
    }
    */


}
