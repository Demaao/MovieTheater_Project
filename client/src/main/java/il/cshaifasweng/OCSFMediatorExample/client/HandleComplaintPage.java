package il.cshaifasweng.OCSFMediatorExample.client;

import com.sun.javafx.menu.MenuItemBase;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HandleComplaintPage {
    public TableColumn statusColumn;
    @FXML
    private TextArea answerText;

    @FXML
    private Button chancelPurchaseBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    public TableColumn productNOColumn;

    @FXML
    public TableColumn priceColumn;

    @FXML
    public TableColumn methodColumn;

    @FXML
    public TableColumn paymentDetailsColumn;

    @FXML
    public TableView<Complaint> complaintTable;

    @FXML
    public TableColumn typeColumn;

    @FXML
    public TableColumn dateColumn;

    @FXML
    private Button hidePurchases;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    public TableView<Purchase> purchaseHistoryTable;

    @FXML
    private AnchorPane purchaseHistory;

    @FXML
    private Button seeCustomerPurchasesBtn;

    @FXML
    private Button submitAnswerBtn;

    @FXML
    private Button cancelPurchaseBtn;

    public static Complaint selectedComplaint;

    public static void setSelectedComplaint(Complaint complaint) {
        selectedComplaint = complaint;
    }

    private ObservableList<Complaint> complaintObservableList = FXCollections.observableArrayList();

    public void initialize() {
        EventBus.getDefault().register(this);
        requestPurchasesFromServer();
        ObservableList<Complaint> items = complaintTable.getItems();
        items.clear();
        complaintObservableList.add(selectedComplaint);
        statusColumn.setCellFactory(new Callback<TableColumn<Complaint, Boolean>, TableCell<Complaint, Boolean>>() {
            @Override
            public TableCell<Complaint, Boolean> call(TableColumn<Complaint, Boolean> col) {
                return new TableCell<Complaint, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                            setText(null);
                        } else {
                            Complaint complaint = (Complaint) getTableRow().getItem();
                            if (!complaint.getStatus()) {
                                setText("Received");
                            } else {
                                setText("Closed");
                            }}}};}});
        complaintTable.setItems(complaintObservableList);
        purchaseHistoryTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                cancelPurchaseBtn.setDisable(false);
            }});
    }

    @Subscribe
    public void onUpdateComplaintsEvent(UpdateComplaintsEvent event) {
        Platform.runLater(() -> {
            for(Complaint complaint : event.getComplaints()) {
                if (Objects.equals(complaint.getId(), selectedComplaint.getId())) {
                   setSelectedComplaint(complaint);
                }
            }
            ObservableList<Complaint> items = complaintTable.getItems();
            items.clear();
            complaintObservableList.add(selectedComplaint);
            statusColumn.setCellFactory(new Callback<TableColumn<Complaint, Boolean>, TableCell<Complaint, Boolean>>() {
                @Override
                public TableCell<Complaint, Boolean> call(TableColumn<Complaint, Boolean> col) {
                    return new TableCell<Complaint, Boolean>() {
                        @Override
                        protected void updateItem(Boolean item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                                setText(null);
                            } else {
                                Complaint complaint = (Complaint) getTableRow().getItem();
                                if (!complaint.getStatus()) {
                                    setText("Received");
                                } else {
                                    setText("Closed");
                                }}}};}});
            complaintTable.setItems(complaintObservableList);
        });
    }

    private int refundFlag = 0;
    @FXML
    void cancelPurchase(ActionEvent event) {
        if (selectedComplaint.getStatus()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The complaint has been resolved and closed!");
            alert.show();
        } else {
            Purchase selectedItem = purchaseHistoryTable.getSelectionModel().getSelectedItem();
            NewMessage msg = new NewMessage(selectedItem, "returnProduct");
            try {
                refundFlag = 1;
                SimpleClient.getClient().sendToServer(msg);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void hidePurchases(ActionEvent event) {
        purchaseHistory.setVisible(false);
    }

    @FXML
    void showPurchaseHistory(ActionEvent event) {
        purchaseHistory.setVisible(true);
    }

    @FXML
    void submitAnswer(ActionEvent event) {
        if (selectedComplaint.getStatus()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("The complaint has been resolved and closed!");
            alert.show();
        } else {
            if (answerText.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Response is empty!");
                alert.show();
            } else {
                selectedComplaint.setResponse(answerText.getText());
                if(refundFlag == 0){
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Submit Response");
                alert.setHeaderText(null);
                alert.setContentText("You have not refunded any purchase!\nDo you wish proceed?");
                ButtonType yesButton = new ButtonType("Yes");
                ButtonType cancelButton = new ButtonType("No");
                alert.getButtonTypes().setAll(yesButton, cancelButton);
                alert.showAndWait().ifPresent(response -> {
                    if (response == yesButton) {
                        try {
                            NewMessage msg = new NewMessage(selectedComplaint, "answerComplaint");
                            SimpleClient.getClient().sendToServer(msg);
                            answerText.clear();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }}});}
            else {
                try {
                    NewMessage msg = new NewMessage(selectedComplaint, "answerComplaint");
                    SimpleClient.getClient().sendToServer(msg);
                    answerText.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }}}}
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToCustomerServiceWorkerPage() throws IOException {
        App.switchScreen("CustomerServiceWorkerPage");
    }


    @FXML
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
    }

    @FXML
    private void requestLogoutFromServer() {
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    private ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();
    private List<Purchase> purchases = new ArrayList<>();

    @Subscribe
    public void handleUpdatePurchasesEvent(UpdatePurchasesEvent event) {
        Platform.runLater(() -> {
            purchaseList.clear(); // Clear existing items
            ObservableList<Purchase> items = purchaseHistoryTable.getItems();
            items.clear();
            purchases.clear();
            List<Purchase> allPurchases = event.getPurchases();
            allPurchases. removeIf(purchase -> purchase.getCustomer().getId() != selectedComplaint.getCustomerID());
            for(Purchase purchase : allPurchases) {
                if(purchase instanceof Card || purchase instanceof HomeMoviePurchase || purchase.getProductType().equals("Movie Ticket")) {
                    purchases.add(purchase);
                }
            }
            purchaseList.addAll(purchases);
            purchaseHistoryTable.setItems(purchaseList);
        });
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

    @Subscribe
    public void onCardEvent(UpdateCardsEvent event) {}
}
