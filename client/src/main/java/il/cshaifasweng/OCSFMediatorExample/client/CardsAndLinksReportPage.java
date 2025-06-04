package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.DailyReportData;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;

public class CardsAndLinksReportPage {

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private ComboBox<String> monthComboBox;

    @FXML
    private TableView<DailyReportData> cardsAndLinksReportTable;

    @FXML
    private TableColumn<DailyReportData, LocalDate> dateColumn;

    @FXML
    private TableColumn<DailyReportData, Integer> cardsSoldColumn;

    @FXML
    private TableColumn<DailyReportData, Integer> linksSoldColumn;

    @FXML
    private TableColumn<DailyReportData, Double> cardRevenueColumn;

    @FXML
    private TableColumn<DailyReportData, Double> linkRevenueColumn;

    @FXML
    private TableColumn<DailyReportData, Double> totalRevenueColumn;

    public void initialize() {
        // Register with EventBus to receive the response from the server
        EventBus.getDefault().register(this);

        // Populate ComboBox with month names
        ObservableList<String> months = FXCollections.observableArrayList();
        for (Month month : Month.values()) {
            months.add(month.name());
        }
        monthComboBox.setItems(months);
        monthComboBox.setValue(String.valueOf(Month.SEPTEMBER));

        // Set the columns for the TableView
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        cardsSoldColumn.setCellValueFactory(new PropertyValueFactory<>("cardsSold"));
        linksSoldColumn.setCellValueFactory(new PropertyValueFactory<>("linksSold"));
        cardRevenueColumn.setCellValueFactory(new PropertyValueFactory<>("cardRevenue"));
        linkRevenueColumn.setCellValueFactory(new PropertyValueFactory<>("linkRevenue"));
        totalRevenueColumn.setCellValueFactory(new PropertyValueFactory<>("totalRevenue"));
        // When a month is selected, request the report from the server
        requestMonthlyReport();
        monthComboBox.setOnAction(event -> requestMonthlyReport());
    }
    private void requestMonthlyReport() {
        String selectedMonth = monthComboBox.getSelectionModel().getSelectedItem();
        if (selectedMonth != null) {
            // Convert selected month name to month number
            int monthNumber = Month.valueOf(selectedMonth).getValue();
            int year = 2024;  // Hardcoded year, could be modified

            // Create and send the request to the server
            NewMessage requestMessage = new NewMessage("generateMonthlyReport", year, monthNumber);

            // Check if the client is connected before sending the message
            if (SimpleClient.getClient().isConnected()) {
                try {
                    SimpleClient.getClient().sendToServer(requestMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Client is not connected to the server.");
                // Optionally, you can try to reconnect the client here
                try {
                    SimpleClient.getClient().openConnection();
                    SimpleClient.getClient().sendToServer(requestMessage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            System.out.println("No month selected!");
        }
    }

    @Subscribe
    public void onMonthlyReportReceived(UpdateMonthlyReportEvent event) {
        Map<LocalDate, DailyReportData> reportData = event.getReportData();

        // Ensure updates are run on the JavaFX Application thread
        Platform.runLater(() -> populateTable(reportData));
    }

    private void populateTable(Map<LocalDate, DailyReportData> reportData) {
        ObservableList<DailyReportData> tableData = FXCollections.observableArrayList(reportData.values());

        cardsAndLinksReportTable.setItems(tableData);
     /*   if(cardsAndLinksReportTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("No data found");
            alert.show();
        }*/
    }

    @Subscribe
    public void handleUpdatePurchasesEvent(UpdatePurchasesEvent event) {
        requestMonthlyReport();
    }

    // Navigation methods go here
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
}
