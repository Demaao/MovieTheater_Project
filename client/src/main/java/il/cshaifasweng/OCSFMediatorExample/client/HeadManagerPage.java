package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ChangePriceRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Warning;
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

public class HeadManagerPage {

    @FXML
    private Button cardsAndLinksReportsBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button complaintsReportsBtn;

    @FXML
    private Button confirmPricesBtn;

    @FXML
    private Label headManagerNameLabel;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private Button ticketsReportsBtn;
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
    private void switchToTicketsReportPage() throws IOException{
        App.switchScreen("TicketsReportPage");
    }

    @FXML
    private void switchToCardsAndLinksReportPage() throws IOException{
        App.switchScreen("CardsAndLinksReportPage");
    }

    @FXML
    private void switchToComplaintsReportPage() throws IOException{
        App.switchScreen("ComplaintsReportPage");
    }

    @FXML
    private void switchToConfirmPricesUpdatesPage() throws IOException{
        App.switchScreen("ConfirmPricesUpdatesPage");
    }

    @FXML
    private void requestLogoutFromServer() {
        counter = 0;///////////////////////////////////////////////////
        EventBus.getDefault().unregister(this);
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public void initialize() {
        headManagerNameLabel.setText(LoginPage.employee1.getFullName());
        EventBus.getDefault().register(this);
        requestRequestsFromServer();
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
    public static int counter = 0;

    @Subscribe
    public void onRequestEvent(UpdateRequestEvent event) {
        Platform.runLater(() -> {
            requests.clear();
            requests = event.getRequests();
            int x = 0;
            for(ChangePriceRequest request: requests) {
                if (request.getStatus().equals("Received")) { // Assuming getStatus() returns false if not responded
                    x++;
                }
            }
            if (x > counter && x > 0 && LoginPage.employee1!=null && LoginPage.employee1.getPosition().equals("Head Manager")) {
           /*     Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("You have " + x + " unanswered change price requests");
                alert.show();*/
                EventBus.getDefault().post(new WarningEvent(new Warning("You have " + x + " unanswered change price requests")));
                counter = x;
            }
        });}
}
