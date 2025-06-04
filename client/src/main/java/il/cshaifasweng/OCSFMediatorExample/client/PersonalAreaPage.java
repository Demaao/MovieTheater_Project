package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PersonalAreaPage {

    @FXML
    private TextField IDNumText;

    @FXML
    private Button enterBtn;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label menuMsg;

    @FXML
    private Button orderBtn, messagesBtn, MycomplaintBtn, moviesLinksBtn;

    public static Customer loggedInCustomer;

    public void initialize() {
        inPersonalAreaFlag = 1;
        EventBus.getDefault().register(this);
        if (loggedInCustomer != null) {
            welcomeLabel.setText("Welcome " + loggedInCustomer.getName());
            IDNumText.setVisible(false);
            enterBtn.setVisible(false);

            // Set the buttons to be visible now that the ID has been validated and customer is logged in
            orderBtn.setVisible(true);
            messagesBtn.setVisible(true);
            MycomplaintBtn.setVisible(true);
            moviesLinksBtn.setVisible(true);
            menuMsg.setVisible(true);
        }
    }

    private void requestNotificationsFromServer() {
        try {
            NewMessage message = new NewMessage("notifications");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AtomicBoolean alertShown = new AtomicBoolean(false);
    public static int counter = 0;
    public static int inPersonalAreaFlag = 1;

    @Subscribe
    public void onPersonalMessageEvent(UpdatePersonalMessageEvent event) {
        Platform.runLater(() -> {
            if(PersonalAreaPage.loggedInCustomer != null && inPersonalAreaFlag == 1) {
                int x = 0;
                List<Notification> notifications = event.getNotifications();
                for (Notification notification : notifications) {
                    if (notification.getCustomer().getId() == PersonalAreaPage.loggedInCustomer.getId()
                            && notification.getStatus().equals("Unread")) {
                            x++;
                        }}
                if(x > counter && alertShown.compareAndSet(false, true)){
                if (x > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Notifications");
                    alert.setHeaderText(null);
                    alert.setContentText("You have " + x + " unread notifications");
                    alert.showAndWait();
                    counter = x;
                }}
            }});
    }

    @Subscribe
    public void onMovieEvent(UpdateMoviesEvent event) {
        Platform.runLater(() -> {
            requestNotificationsFromServer();
            alertShown.set(false);
           });
    }

    @Subscribe
    public void onPurchaseEvent(UpdatePurchasesEvent event) {
        Platform.runLater(() -> {
            requestNotificationsFromServer();
            alertShown.set(false);
        });
    }

    @FXML
    private void showPurchasesTable(ActionEvent event) {
        String idNum = IDNumText.getText();
        if (idNum.isEmpty() || idNum.length() != 9 || !idNum.matches("\\d+")) {
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please enter a valid 9-digit ID number.");
                alert.show();
            });
        } else {
            try {
                int id = Integer.parseInt(idNum);
                NewMessage message = new NewMessage("loginCustomer", id);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }}
    }

    @Subscribe
    public void handleCustomerLogin(UpdateLoginCustomerEvent event) {
        Platform.runLater(() -> {
            if(inPersonalAreaFlag == 1) {
                loggedInCustomer = event.getCustomer();
                welcomeLabel.setText("Welcome " + loggedInCustomer.getName());

                IDNumText.setVisible(false);
                enterBtn.setVisible(false);

                orderBtn.setVisible(true);
                messagesBtn.setVisible(true);
                MycomplaintBtn.setVisible(true);
                moviesLinksBtn.setVisible(true);
                menuMsg.setVisible(true);
                requestNotificationsFromServer();
            }
        });
    }

    public static void logOutCustomer() {
        if (loggedInCustomer != null) {
            alertShown.set(false);
            counter = 0;
            inPersonalAreaFlag = 2;
            try {
                NewMessage message = new NewMessage(loggedInCustomer, "logOutCustomer");
                SimpleClient.getClient().sendToServer(message);
                loggedInCustomer = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    public void switchToPersonalDetailsPage() throws IOException {
        App.switchScreen("PersonalDetailsPage");
        /*
        if (loggedInCustomer != null) {
            int customerId = loggedInCustomer.getId();
            NewMessage message = new NewMessage("fetchPurchases", customerId);
            try {
                SimpleClient.getClient().sendToServer(message);
                App.switchScreen("PersonalDetailsPage");
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to connect to server.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "No customer is logged in.");
            alert.showAndWait();
        } */
    }

    @FXML
    private void switchToHostPage() throws IOException {
        logOutCustomer();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        logOutCustomer();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        logOutCustomer();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        logOutCustomer();
        App.switchScreen("LoginPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        logOutCustomer();
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        logOutCustomer();
        inPersonalAreaFlag = 0;
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        logOutCustomer();
        App.switchScreen("CardsPage");
    }

    @FXML
    private void switchToMessages() throws IOException {
        App.switchScreen("PersonalMessagesPage");
    }

    @FXML
    public void switchToFiledComplaintsPage() throws IOException {
        App.switchScreen("FiledComplaintsPage");
    }

    @FXML
    public void switchToPersonalLinkPage() throws IOException {
        App.switchScreen("PersonalLinkPage");
    }

    @FXML
    private void switchToMoviesLinks() throws IOException {
        logOutCustomer();
        App.switchScreen("MoviesLinksPage");
    }

    @FXML
    private void switchToPersonalArea() throws IOException {
        logOutCustomer();
        App.switchScreen("PersonalAreaPage");
    }
}
