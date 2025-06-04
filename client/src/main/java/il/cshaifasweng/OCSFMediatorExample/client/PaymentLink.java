package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Customer;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentLink {

    @FXML
    private TextField IDNumText;

    @FXML
    private TextField creditCardTxt;

    @FXML
    private TextField emailText;

    @FXML
    private TextField fullNameText;

    @FXML
    private Button payBtn;

    @FXML
    private TextField phoneText;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button backBtn; // Added for the Back button

    private  double totalPrice;

    @FXML
    private void initialize() {
        if(MovieLinkDetailsPage.homeMoviePurchase == null)
            totalPriceLabel.setText(null);
        else {
            totalPrice = MovieLinkDetailsPage.homeMoviePurchase.getPricePaid();
            totalPriceLabel.setText(totalPrice + "$");
        }
    }

    @FXML
    void payForProduct(ActionEvent event) throws IOException {
        List<String> errorMessages = new ArrayList<>();

        // Reset field styles before validation
        resetFieldStyles();

        // Validate each field and track errors
        if (fullNameText.getText().isEmpty() || !validateFullName()) {
            highlightFieldError(fullNameText);
            errorMessages.add("Invalid full name. Please use only letters and spaces.");
        }

        if (IDNumText.getText().isEmpty() || !validateIDNumber()) {
            highlightFieldError(IDNumText);
            errorMessages.add("Invalid ID number. Please enter a 9-digit number.");
        }

        if (phoneText.getText().isEmpty() || !validatePhoneNumber()) {
            highlightFieldError(phoneText);
            errorMessages.add("Invalid phone number. Please enter a 10-digit number.");
        }

        if (emailText.getText().isEmpty() || !validateEmail()) {
            highlightFieldError(emailText);
            errorMessages.add("Invalid email format.");
        }

        if (creditCardTxt.getText().isEmpty() || !validateCreditCard()) {
            highlightFieldError(creditCardTxt);
            errorMessages.add("Invalid credit card number. Please enter a 16-digit number.");
        }

        // If there are validation errors, stop processing
        /*
        if (!errorMessages.isEmpty()) {
            String alertMessage = String.join("\n", errorMessages);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Errors");
                alert.setHeaderText(null);
                alert.setContentText(alertMessage);
                alert.show();
            });
            return; // Stop processing if validation fails
        }*/
        if (!errorMessages.isEmpty()) {
            String alertMessage;
            if (errorMessages.size() == 1) {
                alertMessage = errorMessages.get(0);
            } else {
                alertMessage = "Multiple errors detected. Please review the highlighted fields and correct the issues.";
            }

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Errors");
                alert.setHeaderText(null);
                alert.setContentText(alertMessage);
                alert.show();
            });
            return; // Stop processing if validation fails
        }


        // Gather data from input fields
        int customerId = Integer.parseInt(IDNumText.getText());
        String fullName = fullNameText.getText();
        String email = emailText.getText();
        String phone = phoneText.getText();
        String creditCard = creditCardTxt.getText();

        // Create a Customer object to send to the server, i will check if it works with IsLoggedIn=False
        Customer customer = new Customer(customerId, fullName, email, phone, new ArrayList<>(), false);
        LocalDateTime time = LocalDateTime.now();
        MovieLinkDetailsPage.homeMoviePurchase.setCustomer(customer);
        MovieLinkDetailsPage.homeMoviePurchase.setPurchaseDate(time);

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(MovieLinkDetailsPage.homeMoviePurchase);

        DateTimeFormatter timeDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        Purchase purchase = new Purchase("Movie Link", time, "Credit Card",
                totalPrice, customer, "Movie link was ordered for the movie: " + MovieLinkDetailsPage.homeMoviePurchase.getHomeMovie().getEngtitle() + ". Viewing is limited to the screening time you selected: " +
                MovieLinkDetailsPage.homeMoviePurchase.getScreening().getScreeningTime().format(timeDateFormatter));

        purchases.add(purchase);
        // Send purchase and customer data to the server
        NewMessage message = new NewMessage(purchases,"processPayment");
     //   message.setCustomer(customer);  // Set the customer data in the message
     //   message.setObject(MovieLinkDetailsPage.homeMoviePurchase);  // Send the purchase details

        try {
            SimpleClient.getClient().sendToServer(message);  // Send to the server
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Formatting the date
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = time.toLocalDate().format(dateFormatter);

        // Formatting the time
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String time2 = time.toLocalTime().format(timeFormatter);


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Purchase Receipt");
        alert.setHeaderText("Purchase completed successfully!");
        alert.setContentText("Date: " + date + ",   "+  "Time: " + time2 +
                "\nProduct Type: Movie Link\n" +
                "Movie Title: " +  MovieLinkDetailsPage.homeMoviePurchase.getHomeMovie().getEngtitle() +
                "\nLink: "+  MovieLinkDetailsPage.homeMoviePurchase.getHomeMovie().getLink()+
                "\nScreening: " +  MovieLinkDetailsPage.homeMoviePurchase.getScreening().getScreeningTime().format(timeDateFormatter) +
                "\nTotal Price: " +totalPrice + "\n\nNote: You can see your purchase details in the personal area.");
        alert.show();

        fullNameText.clear();
        IDNumText.clear();
        phoneText.clear();
        emailText.clear();
        creditCardTxt.clear();
        totalPriceLabel.setText(null);

        PurchaseLink.setLinkPrice = 0;

        CardsPage.cards.clear();

        switchToMoviesPage();

        MovieDetailsPage.movieDetailsPage = 0;
    }

    private void resetFieldStyles() {
        fullNameText.getStyleClass().remove("error");
        IDNumText.getStyleClass().remove("error");
        phoneText.getStyleClass().remove("error");
        emailText.getStyleClass().remove("error");
        creditCardTxt.getStyleClass().remove("error");
    }

    private boolean validateFullName() {
        String fullName = fullNameText.getText().trim();
        return Pattern.matches("[a-zA-Z\\s]+", fullName);
    }

    private boolean validateIDNumber() {
        String idNumber = IDNumText.getText().trim();
        return Pattern.matches("\\d{9}", idNumber);
    }

    private boolean validatePhoneNumber() {
        String phoneNumber = phoneText.getText().trim();
        return Pattern.matches("\\d{10}", phoneNumber);
    }

    private boolean validateEmail() {
        String email = emailText.getText().trim();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        return Pattern.matches(emailRegex, email);
    }

    private boolean validateCreditCard() {
        String creditCard = creditCardTxt.getText().trim();
        return Pattern.matches("\\d{16}", creditCard);
    }

    private void highlightFieldError(TextField field) {
        field.getStyleClass().add("error");
    }

    @FXML
    private void switchToPreviousPage(ActionEvent event) throws IOException {
        App.switchScreen("PurchaseLink"); // in case he wants to come back
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        PurchaseLink.setLinkPrice = 0;
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("CardsPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        PurchaseLink.setLinkPrice = 0;
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        PurchaseLink.setLinkPrice = 0;
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        PurchaseLink.setLinkPrice = 0;
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        PurchaseLink.setLinkPrice = 0;
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        PurchaseLink.setLinkPrice = 0;
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("ChargebackPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        PurchaseLink.setLinkPrice = 0;
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        PurchaseLink.setLinkPrice = 0;
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("PersonalAreaPage");
    }
}
