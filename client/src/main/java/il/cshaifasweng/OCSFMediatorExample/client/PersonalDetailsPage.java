package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Card;
import il.cshaifasweng.OCSFMediatorExample.entities.HomeMoviePurchase;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import il.cshaifasweng.OCSFMediatorExample.entities.Purchase;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.EventBus;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class PersonalDetailsPage {

    @FXML
    private Button ChargebackBtn, cardsBtn, changeHostBtn, complaintsBtn, enterBtn, homePageBtn, loginBtn, moviesBtn, OKBtn;

    @FXML
    private TableView<Purchase> purchasesTable;

    @FXML
    private TableColumn<Purchase, String> orderPriceColumn;
    @FXML
    private TableColumn<Purchase, String> orderDateColumn;
    @FXML
    private TableColumn<Purchase, String> orderDetailsColumn;
    @FXML
    private TableColumn<Purchase, String> paymentMethodColumn;

    private ObservableList<Purchase> purchaseList = FXCollections.observableArrayList();

    public void initialize() {
        // Register the controller to listen for events
        EventBus.getDefault().register(this);
        requestPurchasesFromServer();
        /*
        // Set up the table columns
        orderPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPricePaid())));
        orderDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getPurchaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
        ));

        // Set the value factory for orderDetailsColumn using purchaseDescription
        orderDetailsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPurchaseDescription()));

        // Adding text wrapping and padding for the order details column
        orderDetailsColumn.setCellFactory(column -> {
            return new TableCell<Purchase, String>() {
                private final Text text = new Text();
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        text.setText(item);
                        text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(10)); // Wrap text inside the column and add padding
                        setGraphic(text);
                        setPadding(new Insets(5, 10, 5, 10));
                    }}};});
        // Set the cell value factory for payment method
        paymentMethodColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentMethod()));
        // Set the items for the table
        purchasesTable.setItems(purchaseList); */
    }

    // Listener for the UpdatePurchasesEvent
    @Subscribe
    public void handleUpdatePurchasesEvent(UpdatePurchasesEvent event) {
        Platform.runLater(() -> {
            if(PersonalAreaPage.loggedInCustomer != null && PersonalAreaPage.inPersonalAreaFlag == 1) {
                purchaseList.clear(); // Clear existing items
                purchaseList.addAll(event.getPurchases()); // Add the new purchases
                purchaseList.removeIf(purchase -> purchase instanceof Card);
                purchaseList.removeIf(purchase -> purchase instanceof HomeMoviePurchase); /////////////////////////////
                purchaseList.removeIf(purchase -> purchase.getProductType().equals("Movie Ticket"));  ///////////////////////////////////////
                purchaseList.removeIf(purchase -> purchase.getCustomer().getId() != PersonalAreaPage.loggedInCustomer.getId());
                // Set up the table columns
                orderPriceColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue().getPricePaid())));
                orderDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                        cellData.getValue().getPurchaseDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                ));
                // Set the value factory for orderDetailsColumn using purchaseDescription
                orderDetailsColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPurchaseDescription()));
                // Adding text wrapping and padding for the order details column
                orderDetailsColumn.setCellFactory(column -> {
                    return new TableCell<Purchase, String>() {
                        private final Text text = new Text();

                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setGraphic(null);
                                setText(null);
                            } else {
                                text.setText(item);
                                text.wrappingWidthProperty().bind(getTableColumn().widthProperty().subtract(10)); // Wrap text inside the column and add padding
                                setGraphic(text);
                                setPadding(new Insets(5, 10, 5, 10));
                            }}};});
                // Set the cell value factory for payment method
                paymentMethodColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentMethod()));
                // Set the items for the table
                purchasesTable.setItems(purchaseList);
            }});
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

    @Subscribe
    public void onCardEvent(UpdateCardsEvent event) {}
}
