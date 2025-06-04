package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.HomeMoviePurchase;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.time.LocalDateTime;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class PersonalLinkPage {

    @FXML
    private Button OKBtn;

    @FXML
    private TableView<HomeMoviePurchase> linksTable;
    @FXML
    private TableColumn<HomeMoviePurchase, String> movieNameColumn;
    @FXML
    private TableColumn<HomeMoviePurchase, LocalDateTime> startTimeColumn;
    @FXML
    private TableColumn<HomeMoviePurchase, LocalDateTime> endTimeColumn;

    @FXML
    private TableColumn<HomeMoviePurchase, String> movieLinkColumn;

    private ObservableList<HomeMoviePurchase> homeMoviePurchasesList = FXCollections.observableArrayList();

    public void initialize() {
        EventBus.getDefault().register(this);
        requestHomeMoviePurchasesFromServer();
        setupTableColumns();
        startLinkUpdater();  //to switch the link to available on the time
    }


    private void startLinkUpdater() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            updateLinksVisibility();  //refresh the link in the table
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    @Subscribe
    public void handleHomeMoviePurchasesUpdate(UpdateHomeMoviePurchasesEvent event) {
        homeMoviePurchasesList.clear();
        homeMoviePurchasesList.addAll(event.getHomeMoviePurchases());
        linksTable.setItems(homeMoviePurchasesList);
    }


    private void updateLinksVisibility() {
        linksTable.refresh();
    }



    private void setupTableColumns() {
        movieNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMovieTitle()));
        movieLinkColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLink()));

        movieLinkColumn.setCellFactory(col -> new TableCell<HomeMoviePurchase, String>() {
            private final Hyperlink hyperlink = new Hyperlink();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null || getTableRow().getItem() == null) {
                    setGraphic(null);
                    setText(null);
                    hyperlink.setOnAction(null);
                } else {
                    HomeMoviePurchase purchase = getTableRow().getItem();
                    LocalDateTime now = LocalDateTime.now();
                    LocalDateTime startTime = purchase.getAvailabilityStartTime();
                    LocalDateTime endTime = purchase.getAvailabilityEndTime();

                    hyperlink.setText(item);
                    if (now.isAfter(startTime) && now.isBefore(endTime)) {
                        hyperlink.setDisable(false);
                        hyperlink.setOnAction(e -> openWebpage(item));
                    } else {
                        hyperlink.setDisable(true);
                        hyperlink.setOnAction(null);
                    }

                    setGraphic(hyperlink);
                    setText(null);
                }
            }

        });


        startTimeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAvailabilityStartTime()));
        endTimeColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAvailabilityEndTime()));
    }

    private void openWebpage(String url) {
        try {
            Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
            if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
                desktop.browse(new URI(url));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void requestHomeMoviePurchasesFromServer() {
        if (PersonalAreaPage.loggedInCustomer != null) {
            NewMessage message = new NewMessage("fetchHomeMoviePurchases", PersonalAreaPage.loggedInCustomer.getId());
            try {
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("User is not logged in or ID is null. Redirecting to login page...");
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
    private void switchToPersonalAreaPage() throws IOException {
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

