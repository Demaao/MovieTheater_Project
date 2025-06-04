package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Card;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PersonalMessagesPage {

    @FXML
    private Button ChargebackBtn;

    @FXML
    private Button MycomplaintsBtn;

    @FXML
    private TableColumn NOColumn;

    @FXML
    private Button OKBtn;

    @FXML
    private Button bigCardsBtn;

    @FXML
    private Button bigMoviesBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private TableColumn dateColumn1;

    @FXML
    private Button hidePurchases;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private AnchorPane messageAnchor;

    @FXML
    private TableColumn messageColumn;

    @FXML
    private Label messageLabel;

    @FXML
    private TableView<Notification> messageTable;

    @FXML
    private Button personalAreaBtn;

    @FXML
    private TableColumn statusColumn;

    @FXML
    private TableColumn subjectColumn;

    @FXML
    private Label titleLabel;

    public void initialize() {
        EventBus.getDefault().register(this);
        requestNotificationsFromServer();
        messageTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                displayMessageDetails(newValue);
            }
        });
    }

    private void displayMessageDetails(Notification notification) {
        String message = "Message NO. " + notification.getId() + "\n\nSubject: " + notification.getSubject()
                + "\n\nReceived at: " + notification.getTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
                + "\n\nMessage: " + notification.getMessage();
        messageLabel.setText(message);
        // If there are other UI components in messageAnchor for showing details, update them here as well
        messageAnchor.setVisible(true);
        try {
            NewMessage msg = new NewMessage(notification, "readNotification");
            SimpleClient.getClient().sendToServer(msg);
            PersonalAreaPage.counter--;
        } catch (IOException e) {
            e.printStackTrace();
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


    private List<Notification> notifications = new ArrayList<>();
    private List<Notification> allNotifications = new ArrayList<>();

    private ObservableList<Notification> notificationObservableList = FXCollections.observableArrayList();

    @Subscribe
    public void onPersonalMessageEvent(UpdatePersonalMessageEvent event) {
        Platform.runLater(() -> {
            if(PersonalAreaPage.loggedInCustomer != null) {
                notifications.clear();
                ObservableList<Notification> items = messageTable.getItems();
                items.clear();
                List<Notification> allNotifications = event.getNotifications();
                for (Notification notification : allNotifications) {
                    if (notification.getCustomer().getId() == PersonalAreaPage.loggedInCustomer.getId()) {
                        notifications.add(notification);
                    }
                }
                dateColumn1.setCellFactory(new Callback<TableColumn<Notification, LocalDateTime>, TableCell<Notification, LocalDateTime>>() {
                    @Override
                    public TableCell<Notification, LocalDateTime> call(TableColumn<Notification, LocalDateTime> col) {
                        return new TableCell<Notification, LocalDateTime>() {
                            private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                            @Override
                            protected void updateItem(LocalDateTime item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty || item == null) {
                                    setText(null);
                                } else {
                                    setText(item.format(formatter));
                                }}};}});
                notificationObservableList.addAll(notifications);
                messageTable.setItems(notificationObservableList);
            }});
    }

    @FXML
    void hidePurchases(ActionEvent event) {
        messageAnchor.setVisible(false);
    }

    @FXML
    private void switchToHomePage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("CardsPage");
    }

    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        App.switchScreen("PersonalAreaPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("MoviesPage");
    }


    @FXML
    private void switchToPersonalArea() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("PersonalAreaPage");
    }

}
