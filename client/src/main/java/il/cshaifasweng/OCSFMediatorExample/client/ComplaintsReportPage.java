package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import javafx.event.ActionEvent;
import java.io.IOException;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;


public class ComplaintsReportPage {

    @FXML
    private Button cardAndLinkReportBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button complaintsReportBtn;

    @FXML
    private BarChart<String,Number> complaintsReportGraph;

    @FXML
    private Button confirmPricesBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button logOutBtn;


    @FXML
    private ComboBox<String> monthComboBox;


    @FXML
    private ComboBox<String> branchComboBox;

    @FXML
    private Label chooseBranchText;

    private int choosenMonth ;

    private String choosenBranch ;


    @FXML
    private void switchToHeadManagerPage() throws IOException {
        if(LoginPage.employee1.getPosition().equals("Head Manager"))
            App.switchScreen("HeadManagerPage");
        else
            App.switchScreen("BranchManagerPage");
    }

    public void initialize() {
        EventBus.getDefault().register(this);

        if(LoginPage.employee1.getPosition().equals("Branch Manager")){
            chooseBranchText.setVisible(false);
            branchComboBox.setVisible(false);
            branchComboBox.setDisable(true);
            cardAndLinkReportBtn.setVisible(false);
            confirmPricesBtn.setVisible(false);
            complaintsReportBtn.setLayoutY(300);
            logOutBtn.setLayoutY(350);
           // int id = LoginPage.employee1.getId();
         //   requestBranchFromServer(id);
            choosenBranch = LoginPage.employee1.getBranchName();
        }
        else {
            choosenBranch = "All";
            chooseBranchText.setVisible(true);
            branchComboBox.setVisible(true);
            branchComboBox.setDisable(false);
        }


        monthComboBox.getItems().addAll("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        monthComboBox.setValue("September");
        branchComboBox.getItems().addAll("All", "Haifa Cinema", "Tel Aviv Cinema",
                "Eilat Cinema", "Karmiel Cinema", "Jerusalem Cinema");
       // branchComboBox.setValue("All");

        complaintsReportGraph.setData(FXCollections.observableArrayList());

        choosenMonth = 9;
        requestComplaintFromServer();
        monthComboBox.setOnAction(event -> requestComplaintFromServer());
        branchComboBox.setOnAction(event -> requestComplaintFromServer());
    }

    private void requestComplaintFromServer() {
        alertShown.set(false);
        try {
            if(LoginPage.employee1.getPosition().equals("Branch Manager"))
                choosenBranch = LoginPage.employee1.getBranchName();
            else if (branchComboBox.getValue() == null)
                choosenBranch = "All";
            else
                choosenBranch = branchComboBox.getValue();
            choosenMonth = getMonthNumber(monthComboBox.getValue());
            NewMessage message = new NewMessage("dataForComplaintsReport",choosenMonth,choosenBranch);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AtomicBoolean alertShown = new AtomicBoolean(false);
    @Subscribe
    public void onUpdateComplaintReportEvent(UpdateComplaintReportEvent event) {
        complaintsReportGraph.getData().clear();
        complaintsReportGraph.setAnimated(false);

        Map<Integer, Integer> complaintsByDay = event.getComplaintReportEvent();


        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Complaints per Day");

        int daysInMonth = getDaysInMonth(choosenMonth, 2024);

        boolean noData = complaintsByDay.isEmpty(); // Flag to check if there is no data

        for (int day = 1; day <= daysInMonth; day++) {
            Integer complaintsCount = complaintsByDay.get(day);
            if (complaintsCount == null) {
                complaintsCount = 0;
            }
            series.getData().add(new XYChart.Data<>(String.valueOf(day), complaintsCount));
        }

        complaintsReportGraph.getData().add(series);
        complaintsReportGraph.layout();
        complaintsReportGraph.setAnimated(true);
        if(noData) {
            if (alertShown.compareAndSet(false, true)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("NO DATA");
                alert.setHeaderText(null);
                alert.setContentText("There is no complaint submitted in that Month and branch.");
                alert.show();
                // Ensure alert is only shown once
                //showAlert(Alert.AlertType.INFORMATION, "NO DATA", "There is no complaint submitted in that Month and branch.");
            }
        }
    }

    // Method to show alerts
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Subscribe
    public void onUpdateComplaintsEvent(UpdateComplaintsEvent event) {
        requestComplaintFromServer();
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
        alertShown.set(false);
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    @FXML
    void changeBranch(ActionEvent event) {
        alertShown.set(false);
        if(event.getSource() == branchComboBox) {
            String newChosenBranch = branchComboBox.getValue();
            if(!newChosenBranch.equals(choosenBranch)) {
                choosenBranch = newChosenBranch;
                choosenMonth = getMonthNumber(monthComboBox.getValue());
                requestComplaintFromServer();
            }
        }
    }

    @FXML
    void changeMonth(ActionEvent event) {
        alertShown.set(false);
        if(event.getSource() == monthComboBox) {
            String newChosenMonth = monthComboBox.getValue();
            if(!newChosenMonth.equals(choosenMonth)) {
                choosenMonth = getMonthNumber(newChosenMonth);
                choosenBranch = branchComboBox.getValue();
                requestComplaintFromServer();
            }
        }
    }

    private int getMonthNumber(String month) {
        switch (month) {
            case "January": return 1;
            case "February": return 2;
            case "March": return 3;
            case "April": return 4;
            case "May": return 5;
            case "June": return 6;
            case "July": return 7;
            case "August": return 8;
            case "September": return 9;
            case "October": return 10;
            case "November": return 11;
            case "December": return 12;
            default: return 9; // Default to septemper if something goes wrong
        }}

    private int getDaysInMonth(int month, int year) {
        switch (month) {
            case 2: // February
                return (year % 4 == 0) ? 29 : 28; // Leap year check
            case 4: case 6: case 9: case 11:
                return 30;
            default:
                return 31;
        }
    }

    public void cleanup() {
        EventBus.getDefault().unregister(this);  // Unregister from EventBus when done
    }

}
