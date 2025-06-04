package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class FiledComplaintsPage {
    public TableColumn responseColumn;
    public TableColumn statusColumn;
    public TableColumn subTimeColumn;
    public TableColumn subjectColumn;
    public TableColumn complaintNOColumn;
    public TableColumn complaintColumn;
    private ObservableList<Complaint> complaintObservableList = FXCollections. observableArrayList();
    private List<Complaint> complaints = new ArrayList<>();

    @FXML
    private Button ChargebackBtn;

    @FXML
    private TextField IDNumText;

    @FXML
    private Button OKBtn;

    @FXML
    private Button cardsBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private TableView<Complaint> complaintTable;

    @FXML
    private Button complaintsBtn;

    @FXML
    private Button enterBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Button moviesBtn;

    public void initialize() {
        EventBus.getDefault().register(this);
        requestComplaintFromServer();
    }

    @Subscribe
    public void onUpdateComplaintsEvent(UpdateComplaintsEvent event) {
        Platform.runLater(() -> {
            if(PersonalAreaPage.loggedInCustomer != null) {
                complaints.clear();
                ObservableList<Complaint> items = complaintTable.getItems();
                items.clear();
                for (Complaint complaint : event.getComplaints()) {
                    if (complaint.getCustomerID() == PersonalAreaPage.loggedInCustomer.getId()) {
                        this.complaints.add(complaint);
                    }
                }
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
                subTimeColumn.setCellFactory(new Callback<TableColumn<Complaint, LocalDateTime>, TableCell<Complaint, LocalDateTime>>() {
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
            }});
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