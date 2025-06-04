package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ChangePriceRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.Complaint;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
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
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConfirmPricesUpdatesPage {
    public TableColumn requestNOColumn;
    public TableColumn productColumn;
    public TableColumn dateColumn;
    public TableColumn newPriceColumn;
    public TableColumn statusColumn;
    public TableColumn currentPriceColumn;
    @FXML
    private Button changeHostBtn;

    @FXML
    private Button confirmBtn;

    @FXML
    private Button denyBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private TableView<ChangePriceRequest> priceUpdateRequestTable;

    private ChangePriceRequest request;

    @FXML
    void confirmPriceUpdate(ActionEvent event) throws IOException {
        if (request.getStatus().equals("Denied") || request.getStatus().equals("Confirmed")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("This request has already been " + request.getStatus() + ".");
            alert.showAndWait();
        } else {
            NewMessage message = new NewMessage(request, "confirmPriceUpdate");
            SimpleClient.getClient().sendToServer(message);
            HeadManagerPage.counter--;
        }
    }

    @FXML
    void denyPriceUpdate(ActionEvent event) throws IOException {
        if (request.getStatus().equals("Denied") || request.getStatus().equals("Confirmed")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("This request has already been " + request.getStatus() + ".");
            alert.showAndWait();
        } else {
            NewMessage message = new NewMessage(request, "denyPriceUpdate");
            SimpleClient.getClient().sendToServer(message);
            HeadManagerPage.counter--;
        }
    }

    @FXML
    private void switchToHeadManagerPage() throws IOException {
        App.switchScreen("HeadManagerPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToTicketsReportPage() throws IOException {
        App.switchScreen("TicketsReportPage");
    }

    @FXML
    private void switchToCardsAndLinksReportPage() throws IOException {
        App.switchScreen("CardsAndLinksReportPage");
    }

    @FXML
    private void switchToComplaintsReportPage() throws IOException {
        App.switchScreen("ComplaintsReportPage");
    }

    @FXML
    private void switchToConfirmPricesUpdatesPage() throws IOException {
        App.switchScreen("ConfirmPricesUpdatesPage");
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


    public void initialize() {
        EventBus.getDefault().register(this);
        requestRequestsFromServer();
        priceUpdateRequestTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                request = newSelection;
                confirmBtn.setDisable(false);
                denyBtn.setDisable(false);
            }
        });
    }

    private void requestRequestsFromServer() {
        try {
            NewMessage message = new NewMessage("requestsList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<ChangePriceRequest> requests = new ArrayList<>();
    private ObservableList<ChangePriceRequest> requestObservableList = FXCollections.observableArrayList();

    @Subscribe
    public void onRequestEvent(UpdateRequestEvent event) {
        Platform.runLater(() -> {
            requests.clear();
            ObservableList<ChangePriceRequest> items = priceUpdateRequestTable.getItems();
            items.clear();
            requests = event.getRequests();
            requestObservableList.addAll(requests);
            dateColumn.setCellFactory(new Callback<TableColumn<ChangePriceRequest, LocalDateTime>, TableCell<ChangePriceRequest, LocalDateTime>>() {
                @Override
                public TableCell<ChangePriceRequest, LocalDateTime> call(TableColumn<ChangePriceRequest, LocalDateTime> col) {
                    return new TableCell<ChangePriceRequest, LocalDateTime>() {
                        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.format(formatter));
                            }
                        }
                    };
                }
            });
            priceUpdateRequestTable.setItems(requestObservableList);
        });
    }

    @Subscribe
    public void onCinemaEvent(UpdateCinemaEvent event) {
    }

}
