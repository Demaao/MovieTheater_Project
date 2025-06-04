package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
public class Complaint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String complaintDescription;

    @Column(nullable = false)
    private LocalDateTime submissionDate;

    @Column(nullable = false)
    private Long customerID;

    @Column(nullable = true)
    private Long workerID;

    @Column(nullable = false)
    private boolean status;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = true)
    private String response;

    // Default Constructor (Required by JPA)
    public Complaint() {
    }

    // Constructor
    public Complaint(String name, Long customerID, String phoneNumber, String subject, String email, String complaintDescription) {
        this.complaintDescription = complaintDescription;
        this.submissionDate = LocalDateTime.now();
        this.customerID = customerID;
        this.workerID = null;
        this.status = false;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
        this.name = name;
        this.response = null;
        this.email = email;
    }

    public Complaint(String name, Long customerID, String phoneNumber, String subject, String email,
                     String complaintDescription, LocalDateTime submissionDate,
                     String response, boolean status) {
        this.complaintDescription = complaintDescription;
        this.submissionDate = submissionDate;
        this.customerID = customerID;
        this.workerID = null;
        this.status = status;
        this.phoneNumber = phoneNumber;
        this.subject = subject;
        this.name = name;
        this.response = response;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComplaintDescription() {
        return complaintDescription;
    }

    public void setComplaintDescription(String complaintDescription) {
        this.complaintDescription = complaintDescription;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getWorkerID() {
        return workerID;
    }

    public void setWorkerID(Long workerID) {
        this.workerID = workerID;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    // Additional methods
    public String getDetails() {
        return String.format("Complaint: %s, Date: %s, Status: %s, Phone: %s, Subject: %s, Name: %s, Email: %s, Response: %s",
                complaintDescription, submissionDate.toString(), status ? "Resolved" : "Unresolved",
                phoneNumber, subject, name != null ? name : "N/A", email, response != null ? response : "N/A");
    }

    public void updateStatus(boolean newStatus) {
        this.status = newStatus;
    }
}
