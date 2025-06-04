package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Card;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PurchaseProductsPage {

    public TableView<Card> purchaseCardsTable;
    public TableColumn NOColumn;
    public TableColumn detailsColumn;
    public TableColumn priceColumn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button cancelBtn;

    @FXML
    private Menu cardsMenue;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Menu chargebackMenue;

    @FXML
    private Menu comlaintsMenue;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private Menu moviesMenue;

    @FXML
    private Button nextBtn;

    @FXML
    private Button signUpBtn;

    private ObservableList<Card> cardObservableList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        purchaseCardsTable.getItems().clear();
        cardObservableList.addAll(CardsPage.cards);
        purchaseCardsTable.setItems(cardObservableList);
        NOColumn.setCellFactory(col -> new TableCell<Card, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    // Use the index to get the card and create custom text
                    int index = getIndex();
                    if (index >= 0 && index < getTableView().getItems().size()) {
                        setText(String.valueOf(index+1));
                    } else {
                        setText(null);
                    }}}});
        detailsColumn.setCellFactory(col -> new TableCell<Card, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    // Use the index to get the card and create custom text
                    int index = getIndex();
                    if (index >= 0 && index < getTableView().getItems().size()) {
                        Card card = getTableView().getItems().get(index);
                        String customText = "Tickets Amount: 20";
                        setText(customText);
                    } else {
                        setText(null);
                    }}}});
        if(CardsPage.cards.isEmpty())
            totalPriceLabel.setText(null);
        else {
            double totalPrice = CardsPage.cards.size() * CardsPage.cards.get(0).getPricePaid();
            totalPriceLabel.setText(totalPrice + "$");
        }
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToPaymentPage() throws IOException {
        App.switchScreen("PaymentPage");
    }


    @FXML
    private void switchToCardsPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("CardsPage");
    }


    @FXML
    private void switchToHostPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        CardsPage.cards.clear();
        App.switchScreen("PersonalAreaPage");
    }

    @Subscribe
    public void onCardEvent(UpdateCardsEvent event){}
}

