package il.cshaifasweng.OCSFMediatorExample.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

public class NewMessage implements Serializable {
    Object object;
    String message;
    String userName;
    String password;
    Employee employee;
    Movie movie;
    String branchName;
    List<Branch> branches;
    List<LocalDateTime> dateTimes;
    List<Hall> halls;
    int id;


    // New fields for year and month
    private int year;
    private int month;

    // Constructor for monthly report data (Map<LocalDate, DailyReportData>)
    public NewMessage(String message, Map<LocalDate, DailyReportData> reportData) {
        this.message = message;
        this.object = reportData; // Store report data in the object field
    }

    // Constructor for year and month
    public NewMessage(String message, int year, int month) {
        this.message = message;
        this.year = year;
        this.month = month;
    }

    public NewMessage(Object object, String message) {
        this.object = object;
        this.message = message;
    }

    public NewMessage(Object object, String message, String branchName) {////////////////////
        this.object = object;
        this.message = message;
        this.branchName = branchName;
    }

    public NewMessage(Object object, String message, List<Branch> branches, List<LocalDateTime> dateTimes, List<Hall> halls) {////////////////////
        this.object = object;
        this.message = message;
        this.branches = branches;
        this.dateTimes = dateTimes;
        this.halls = halls;
    }

    public NewMessage(String message) {
        this.message = message;
    }

    public NewMessage(String message, String username, String password) {
        this.message = message;;
        this.userName = username;
        this.password = password;
    }
    public NewMessage(String message, Employee employee) {
        this.message = message;
        this.employee = employee;
    }
    public NewMessage(String message, int id) {
        this.message = message;
        this.id = id;
    }

    public NewMessage(Object object, String message, int id) {
        this.object = object;
        this.message = message;
        this.id = id;
    }

    public NewMessage( String message, String branchName) {////////////////////
        this.message = message;
        this.branchName = branchName;
    }
    public NewMessage(String message, int month, String branchName) {
        this.message = message;
        this.month = month;
        this.branchName = branchName;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getUserName(){
        return userName;
    }
    public String getPassword(){
        return password;
    }
    public String getBranchName(){ return branchName; }

    public Movie getMovie(){
        return movie;
    }

    public Object getObject() {
        return object;
    }
    public String getMessage() {
        return message;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setMovie(Movie movie) { this.movie = movie; }

    public List<Branch> getBranches() {return branches;}
    public List<LocalDateTime> getDateTimes() {return dateTimes;}
    public List<Hall> getHalls() {return halls;}

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }



    // Getters for year and month
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }



//    public LocalDate getSelectedDate() { return selectedDate; }
//    public void setSelectedDate(LocalDate date) { this.selectedDate = date; }
//
//
//    public LocalTime getSelectedTime() { return selectedTime; }
//    public void setSelectedTime(LocalTime time) { this.selectedTime = time; }
//
//    public int getSelectedMovieId() {
//        return selectedMovieId;
//    }
//
//    public void setSelectedMovieId(int selectedMovieId) {
//        this.selectedMovieId = selectedMovieId;
//    }
//    public String getSelectedBranchName() {
//        return selectedBranchName;
//
//    }
//    public void setSelectedBranchName(String selectedBranchName) {
//        this.selectedBranchName = selectedBranchName;
//    }
}