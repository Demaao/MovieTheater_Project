package il.cshaifasweng.OCSFMediatorExample.client;

import com.sun.javafx.menu.MenuItemBase;
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

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class CustomerServiceWorkerPage {
    public TableView<Complaint> complaintTable;
    public TableColumn complaintNOColumn;
    public TableColumn subjectColumn;
    public TableColumn idColumn;
    public TableColumn dateColumn;
    public TableColumn statusColumn;
    public TableColumn complaintColumn;
    private ObservableList<Complaint> complaintObservableList = FXCollections. observableArrayList();
    private List<Complaint> complaints = new ArrayList<>();

    @FXML
    Button handleComplaintBtn;

    @FXML
    private Label customerServiceWorkerNameLabel;

    @FXML
    private void switchToCustomerServiceWorkerPage() throws IOException {
        App.switchScreen("CustomerServiceWorkerPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    void switchToHostPage(ActionEvent event) {
        App.switchScreen("HostPage");
    }

    @FXML
    void switchToHandleComplaintPage(ActionEvent event) {
        Complaint complaint = complaintTable.getSelectionModel().getSelectedItem();
        HandleComplaintPage.setSelectedComplaint(complaint);
        App.switchScreen("HandleComplaintPage");
    }

    public void initialize() {
        EventBus.getDefault().register(this);
        handleComplaintBtn.setDisable(true);
        customerServiceWorkerNameLabel.setText(LoginPage.employee1.getFullName());
        requestComplaintFromServer();
        complaintTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
              handleComplaintBtn.setDisable(false);
            }});
    }

    public static AtomicBoolean alertShown = new AtomicBoolean(false);

    @Subscribe
    public void onUpdateComplaintsEvent(UpdateComplaintsEvent event) {
        Platform.runLater(() -> {
            complaints.clear();
            ObservableList<Complaint> items = complaintTable.getItems();
            items.clear();
            complaints = event.getComplaints();
            complaintObservableList.clear();
            complaintObservableList.addAll(complaints);
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
            dateColumn.setCellFactory(new Callback<TableColumn<Complaint, LocalDateTime>, TableCell<Complaint, LocalDateTime>>() {
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
            complaintTable.setItems(complaintObservableList);
            if(alertShown.compareAndSet(false, true)){
            for(Complaint complaint: complaints) {
                if (!complaint.getStatus()) {
                    LocalDateTime submissionTime = complaint.getSubmissionDate();
                    LocalDateTime deadline = submissionTime.plusHours(24);
                    LocalDateTime currentTime = LocalDateTime.now();

                    long hoursLeft = java.time.Duration.between(currentTime, deadline).toHours();
                    long minutesLeft = java.time.Duration.between(currentTime, deadline).toMinutesPart();

                    if (hoursLeft < 2 && LoginPage.employee1!=null && LoginPage.employee1.getPosition().equals("Customer Service Worker")) {
                        String timeLeftMessage = String.format(
                                "Time remaining for Complaint NO. %d: %d hours and %d minutes.\nImmediate action is needed.",
                                complaint.getId(),
                                hoursLeft,
                                minutesLeft);
                   /*     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText(timeLeftMessage);
                        alert.show();*/
                        EventBus.getDefault().post(new WarningEvent(new Warning(timeLeftMessage)));
                    }}}} });
    //    alertShown.set(false);
    }

    private void requestComplaintFromServer() {
        try {
            NewMessage message = new NewMessage("complaintsList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void requestLogoutFromServer() {
        alertShown.set(false);
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }}
}
