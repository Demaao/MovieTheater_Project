package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.time.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ChargebackPage {

    public TableColumn productNOColumn;
    public TableColumn typeColumn;
    public TableColumn dateColumn;
    public TableColumn priceColumn;
    public TableColumn methodColumn;
    public TableColumn paymentDetailsColumn;
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
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Menu moviesMenue;

    @FXML
    private TableView<Purchase> purchasesTable;

    @FXML
    private Button returnProductBtn;

    @FXML
    private Label selectProductText;

    @FXML
    private Button signUpBtn;

    private ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();
    private List<Purchase> purchases = new ArrayList<>();
    private List<Purchase> allPurchases = new ArrayList<>();

    public void initialize() {
        PersonalAreaPage.inPersonalAreaFlag = 0;
        EventBus.getDefault().register(this);
       // requestPurchasesFromServer();
        purchasesTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                returnProductBtn.setDisable(false);
            }});
    }

    private int x = 0;

    @FXML
    void showPurchasesTable(ActionEvent event) {
        x++;
        Platform.runLater(() -> {
        if(x > 1 && PersonalAreaPage.loggedInCustomer != null){
                purchasesTable.getItems().clear();
                PersonalAreaPage.logOutCustomer();
        }
        returnProductBtn.setDisable(true);
        if(IDNumText.getText().isEmpty() || IDNumText.getText().length() != 9 || !IDNumText.getText().matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid ID number");
            alert.show();
        } else {
            try {
                int id = Integer.parseInt(IDNumText.getText());
                NewMessage message = new NewMessage("loginCustomer", id);
                SimpleClient.getClient().sendToServer(message);
                PersonalAreaPage.inPersonalAreaFlag = 0;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }});
    }

    @Subscribe
    public void handleUpdatePurchasesEvent(UpdatePurchasesEvent event) {
        Platform.runLater(() -> {
            if (PersonalAreaPage.loggedInCustomer != null && PersonalAreaPage.inPersonalAreaFlag == 0) {
            purchaseList.clear(); // Clear existing items
            ObservableList<Purchase> items = purchasesTable.getItems();
            items.clear();
            purchases.clear();
            allPurchases = event.getPurchases();
            if(!IDNumText.getText().isEmpty()){
                for(Purchase purchase : allPurchases) {
                    if(purchase.getCustomer().getId() == Integer.parseInt(IDNumText.getText()) && (purchase instanceof Card || purchase instanceof HomeMoviePurchase || purchase.getProductType().equals("Movie Ticket"))) { // || purchase instanceof MovieTicket){
                        purchases.add(purchase);
                    }
                } dateColumn.setCellFactory(new Callback<TableColumn<Complaint, LocalDateTime>, TableCell<Complaint, LocalDateTime>>() {
                    @Override
                    public TableCell<Complaint, LocalDateTime> call(TableColumn<Complaint, LocalDateTime> col) {
                        return new TableCell<Complaint, LocalDateTime>() {
                            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                            @Override
                            protected void updateItem(LocalDateTime item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                } else {
                                    setText(item.format(formatter));
                                }}};}});
                purchaseList.addAll(purchases);
                purchasesTable.setItems(purchaseList);
                purchasesTable.setVisible(true);
                selectProductText.setVisible(true);
                returnProductBtn.setVisible(true);
            }}});
    }

    public void returnProduct(ActionEvent actionEvent) {
        Purchase selectedItem = purchasesTable.getSelectionModel().getSelectedItem();
        if (selectedItem instanceof Card) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(null);
            alert.setContentText("Cards cannot be returned!");
            alert.showAndWait();
        }
        else {
            double refundPercentage = 0;
            if(selectedItem instanceof HomeMoviePurchase){
                LocalDateTime screeningTime; // = ((HomeMoviePurchase) selectedItem).getScreening().getScreeningTime();
                if(((HomeMoviePurchase) selectedItem).getScreening() == null){
                    String time = ((HomeMoviePurchase) selectedItem).getPurchaseDescription();
                    String dateTimeString = time.split("\nScreening: ")[1];
                    DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    screeningTime = LocalDateTime.parse(dateTimeString, formatter1);
                }
                else
                    screeningTime = ((HomeMoviePurchase) selectedItem).getScreening().getScreeningTime();
                LocalDateTime currentTime = LocalDateTime.now();
                Duration duration = Duration.between(currentTime, screeningTime);
                // Get the difference in hours
                long hoursDifference = duration.toHours();

                // Determine refund based on hours difference
                if (hoursDifference >= 1) {
                    refundPercentage = 50.0;
                } else {
                    refundPercentage = 0.0;
                }
            }
            if(selectedItem.getProductType().equals("Movie Ticket")){
                String details = selectedItem.getPurchaseDescription();
                // Extract the date and time from the string
                String[] lines = details.split("\n");

                String dateStr = "";
                String timeStr = "";

                // Find and extract the date and time values
                for (String line : lines) {
                    if (line.startsWith("Date:")) {
                        dateStr = line.split(": ")[1].trim();
                    } else if (line.startsWith("Time:")) {
                        timeStr = line.split(": ")[1].trim();
                    }
                }

                // Parse the date and time
                LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                LocalTime time = LocalTime.parse(timeStr, DateTimeFormatter.ofPattern("HH:mm"));

                // Combine into a LocalDateTime
                LocalDateTime dateTime = LocalDateTime.of(date, time);

                LocalDateTime currentTime = LocalDateTime.now();
                Duration duration = Duration.between(currentTime, dateTime);
              //  double refundPercentage2;
                // Get the difference in hours
                long hoursDifference = duration.toHours();

                // Determine refund based on hours difference
                if (hoursDifference >= 3) {
                    refundPercentage = 100.0;
                } else if(hoursDifference >= 1){
                    refundPercentage = 50.0;
                } else{
                refundPercentage = 0.0;
                }
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Refund");
            if (refundPercentage > 0) {
                alert.setContentText("you are eligible for a " + refundPercentage + "% refund.\nAre you sure you want to return this product?");
                // The refund will be processed to the original payment method used for the purchase.
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType cancelButton = new ButtonType("No");
            alert.getButtonTypes().setAll(yesButton, cancelButton);
            alert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    try {
                        NewMessage msg = new NewMessage(selectedItem, "returnProduct");
                        SimpleClient.getClient().sendToServer(msg);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}});
            } else {
                alert.setContentText("No refund is available.");
                alert.setHeaderText(null);
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.show();
            }}
    }

    @FXML
    private void requestPurchasesFromServer() {
        try {
            NewMessage message = new NewMessage("fetchPurchases");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToChargebackPolicyPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ChargebackPolicyPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("CardsPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("MoviesPage");
    }
    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("PersonalAreaPage");
    }

    @Subscribe
    private void onCardsEvent(UpdateCardsEvent event){}

    @Subscribe
    public void onLoginCustomerEvent(UpdateLoginCustomerEvent event){
        Platform.runLater(() -> {
            if(PersonalAreaPage.inPersonalAreaFlag == 0){
            PersonalAreaPage.loggedInCustomer = event.getCustomer();
            requestPurchasesFromServer();
        }
    });
    }
}
