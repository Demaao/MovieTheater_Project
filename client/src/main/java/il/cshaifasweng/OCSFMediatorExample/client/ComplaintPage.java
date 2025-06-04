package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.HomeMovie;
import il.cshaifasweng.OCSFMediatorExample.entities.Movie;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;  // Import the Complaint class
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class ComplaintPage {
    @FXML
    private TextField IDNumText;

    @FXML
    private Menu cardsMenue;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Menu chargebackMenue;

    @FXML
    private Menu comlaintsMenue;

    @FXML
    private TextArea complaintText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField fullNameText;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Menu moviesMenue;

    @FXML
    private TextField phoneText;

    @FXML
    private Button signUpBtn;

    @FXML
    private Button submitComplaintBtn;

    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        subjectComboBox.getItems().addAll("Website",
                "Movie link", "Screening", "Purchases", "Haifa Cinema", "Tel Aviv Cinema",
                "Eilat Cinema", "Karmiel Cinema", "Jerusalem Cinema", "Other");

    }


    @FXML
    void submitComplaint(ActionEvent event) {
        String fullName = fullNameText.getText();
        String email = emailText.getText();
        String phoneNumber = phoneText.getText();
        String idNumber = IDNumText.getText();
        String complaintDescription = complaintText.getText();
        String selectedSubject = subjectComboBox.getValue();

        // Validate required fields
        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || idNumber.isEmpty() || complaintDescription.isEmpty()) {
            showAlert(AlertType.ERROR, "Form Error!", "Please fill in all fields.");
            return;
        }

        // Validate ID number
        if (idNumber.length() != 9 || !idNumber.matches("\\d+")) {
            showAlert(AlertType.ERROR, "Invalid ID", "Please enter a valid 9-digit ID number.");
            return;
        }

        // Validate phone number
        if (!phoneNumber.matches("\\d+")) {
            showAlert(AlertType.ERROR, "Invalid Phone Number", "Please enter a valid phone number. Only digits are allowed.");
            return;
        }

        // Validate subject selection
        if (selectedSubject == null) {
            showAlert(AlertType.ERROR, "Subject Missing", "Please select a subject for your complaint.");
            return;
        }

        // Validate email format
        if (!isValidEmail(email)) {
            showAlert(AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        Complaint  complaint = new Complaint(fullName, Long.parseLong(idNumber), phoneNumber, selectedSubject, email, complaintDescription);

        try {
            NewMessage msg = new NewMessage(complaint, "submitComplaint");
            SimpleClient.getClient().sendToServer(msg);
           // showAlert(AlertType.INFORMATION, "Success", "Complaint submitted successfully!");
            clearForm();
        } catch (IOException e) {
            // Log the exception for debugging
            e.printStackTrace();
            showAlert(AlertType.ERROR, "Server Error", "Failed to submit complaint. Please try again later.");
        }

        clearForm();
    }

    // Example of email validation
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    // Method to clear the form fields
    private void clearForm() {
        fullNameText.clear();
        emailText.clear();
        phoneText.clear();
        IDNumText.clear();
        complaintText.clear();
        subjectComboBox.setValue(null);
    }

    // Method to show alerts
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /////////////////////////////////////////////////////////////////////
    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        App.switchScreen("MoviesPage");
    }
    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        App.switchScreen("PersonalAreaPage");
    }
/*
    @FXML
    public void switchToFiledComplaintsPage() throws IOException {
        App.switchScreen("FiledComplaintsPage");
    } */

    @Subscribe
    public void onUpdateComplaintsEvent(UpdateComplaintsEvent event) {}
}


