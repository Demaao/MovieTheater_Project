package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Card;
import il.cshaifasweng.OCSFMediatorExample.entities.Customer;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class PaymentPage {

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

    private  double totalPrice;

    @FXML
    private void initialize() {
        if(CardsPage.cards.isEmpty())
            totalPriceLabel.setText(null);
        else {
            totalPrice = CardsPage.cards.size() * CardsPage.cards.get(0).getPricePaid();
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

        // Display error message based on the number of errors
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

        List<Purchase> purchases = new ArrayList<>(CardsPage.cards);

        Customer customer = new Customer(Integer.parseInt(IDNumText.getText()),fullNameText.getText(), emailText.getText(),
                phoneText.getText(), purchases, false);

        LocalDateTime time = LocalDateTime.now();
        for(Card card : CardsPage.cards){
            card.setPurchaseDate(time);
            card.setCustomer(customer);
        }

        if(CardsPage.cards.size() > 1){
            Purchase purchase = new Purchase("Movie Card", time, "Credit Card",
                    totalPrice, customer, null, CardsPage.cards.size(), CardsPage.cards.size() + " cinema cards were ordered containing 20 tickets each, which allows access to movie screenings at all our branches based on available seating.");
            purchases.add(purchase);
        } else {
            Purchase purchase = new Purchase("Movie Card", time, "Credit Card",
                    totalPrice, customer, null, CardsPage.cards.size(), "A cinema card was ordered containing 20 tickets, which allows access to movie screenings at all our branches based on available seating.");
            purchases.add(purchase);
        }


        NewMessage msg = new NewMessage(purchases, "purchaseCards");
        SimpleClient.getClient().sendToServer(msg);

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
                "\nProduct Type: Card\nAmount: " +CardsPage.cards.size()+"\nTotal Price: " +totalPrice + "\n\nNote: You can see your purchase details in the personal area.");
        alert.show();

        fullNameText.clear();
        IDNumText.clear();
        phoneText.clear();
        emailText.clear();
        creditCardTxt.clear();
        totalPriceLabel.setText(null);

        CardsPage.cards.clear();

        switchToCardsPage();
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
    private void switchToCardsPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("CardsPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("ChargebackPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("PersonalAreaPage");
    }

    @FXML
    private void switchPurchaseProductsPage() throws IOException {
        App.switchScreen("PurchaseProductsPage");
    }

    @Subscribe
    public void onCardEvent(UpdateCardsEvent event) {}

    @Subscribe
    public void handleUpdatePurchasesEvent(UpdatePurchasesEvent event) {}
}
