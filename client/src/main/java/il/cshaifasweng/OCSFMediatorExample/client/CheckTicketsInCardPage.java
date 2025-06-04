package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Card;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CheckTicketsInCardPage {
    public TableColumn cardNOColumn;
    public TableColumn dateColumn;
    public TableColumn ticketsAmountColumn;
    @FXML
    private TableView<Card> CardsTable;

    @FXML
    private TableView<Card> allCardsTable;

    @FXML
    private TextField IDNumText;

    @FXML TextField cardNumText;

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
    private Button showCardsBtn;

    @FXML
    private Button signUpBtn;

    @FXML
    private Button okBtn;

    @FXML
    private Button okBtn2;

    @FXML
    private Button enterBtn;

    @FXML
    private Button forgotCardNumBtn;

    @FXML
    private Label cardNumLabel;

    @FXML
    private Label IDNumLabel;

    @FXML
    private Label forgotCardNumLabel;

    public void initialize() {
        PersonalAreaPage.inPersonalAreaFlag = 3;
        EventBus.getDefault().register(this);
        requestCardsFromServer();
    }

    private void requestCardsFromServer() {
        try {
            NewMessage message = new NewMessage("cards");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Card> cards = new ArrayList<>();
    private List<Card> allCards = new ArrayList<>();

    private ObservableList<Card> cardObservableList = FXCollections.observableArrayList();

    @Subscribe
    public void onCardEvent(UpdateCardsEvent event) {
        Platform.runLater(() -> {
            cards.clear();
            ObservableList<Card> items = CardsTable.getItems();
            items.clear();
            allCards = event.getCards();
            CardsTable.getItems().clear();
            for (Card card : allCards) {
                if (!cardNumText.getText().isEmpty() && card.getId() == Integer.parseInt(cardNumText.getText())) {
                    cards.add(card);
                    break;
                } else if(!IDNumText.getText().isEmpty() && card.getCustomer().getId() == Integer.parseInt(IDNumText.getText())) {
                    cards.add(card);
                }
            }
            cardObservableList.addAll(cards);
            dateColumn.setCellFactory(new Callback<TableColumn<Card, LocalDateTime>, TableCell<Card, LocalDateTime>>() {
                @Override
                public TableCell<Card, LocalDateTime> call(TableColumn<Card, LocalDateTime> col) {
                    return new TableCell<Card, LocalDateTime>() {
                        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.format(formatter));
                            }}};}});
            CardsTable.setItems(cardObservableList);
            if(PersonalAreaPage.inPersonalAreaFlag == 3 && x > 0){
                CardsTable.setPrefHeight(236);
                CardsTable.setVisible(true);
                okBtn2.setVisible(true);
            }
        });
    }

    @FXML
    void showCardsTable(ActionEvent event) {
        if(cardNumText.getText().isEmpty() || !cardNumText.getText().matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid card number");
            alert.showAndWait();
            return;
        } else {
            cards.clear();
            CardsTable.getItems().clear();
            for (Card card : allCards) {
                if (!cardNumText.getText().isEmpty() && card.getId() == Integer.parseInt(cardNumText.getText())) {
                    cards.add(card);
                    break;
                }
            }
            cardObservableList.addAll(cards);
            dateColumn.setCellFactory(new Callback<TableColumn<Card, LocalDateTime>, TableCell<Card, LocalDateTime>>() {
                @Override
                public TableCell<Card, LocalDateTime> call(TableColumn<Card, LocalDateTime> col) {
                    return new TableCell<Card, LocalDateTime>() {
                        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.format(formatter));
                            }}};}});
            CardsTable.setItems(cardObservableList);
        }
        if(CardsTable.getItems().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("No card found!");
                alert.showAndWait();
        } else {
                CardsTable.setVisible(true);
                okBtn.setVisible(true);
                forgotCardNumLabel.setVisible(false);
                forgotCardNumBtn.setVisible(false);
        }
    }

    private int x = 0;

    @FXML
    void showAllCardsTable(ActionEvent event) {
        x++;
        if(x > 1 && PersonalAreaPage.loggedInCustomer != null){
            CardsTable.getItems().clear();
            PersonalAreaPage.logOutCustomer();
        }
        if(IDNumText.getText().isEmpty() || IDNumText.getText().length() != 9 || !IDNumText.getText().matches("\\d+")){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid ID number");
            alert.showAndWait();
            return;
        }  else {
                try {
                    int id = Integer.parseInt(IDNumText.getText());
                    NewMessage message = new NewMessage("loginCustomer", id);
                    SimpleClient.getClient().sendToServer(message);
                    PersonalAreaPage.inPersonalAreaFlag = 3;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
            cards.clear();
            CardsTable.getItems().clear();
            for (Card card : allCards) {
               if (!IDNumText.getText().isEmpty() && card.getCustomer().getId() == Integer.parseInt(IDNumText.getText())) {
                    cards.add(card);
                }
            }
            cardObservableList.addAll(cards);
            dateColumn.setCellFactory(new Callback<TableColumn<Card, LocalDateTime>, TableCell<Card, LocalDateTime>>() {
                @Override
                public TableCell<Card, LocalDateTime> call(TableColumn<Card, LocalDateTime> col) {
                    return new TableCell<Card, LocalDateTime>() {
                        private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

                        @Override
                        protected void updateItem(LocalDateTime item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || item == null) {
                                setText(null);
                            } else {
                                setText(item.format(formatter));
                            }}};}});
            CardsTable.setItems(cardObservableList);
        } if (CardsTable.getItems().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No matching customer found!");
            alert.showAndWait();
        } else {
            CardsTable.setPrefHeight(236);
            CardsTable.setVisible(true);
            okBtn2.setVisible(true);*/
        }
    }

    @Subscribe
    public void onLoginCustomerEvent(UpdateLoginCustomerEvent event){
        Platform.runLater(() -> {
            if(PersonalAreaPage.inPersonalAreaFlag == 3){
                PersonalAreaPage.loggedInCustomer = event.getCustomer();
                requestCardsFromServer();
            }
        });
    }

    @FXML
    void askForIDNum(ActionEvent event) {
        IDNumLabel.setVisible(true);
        IDNumText.setVisible(true);
        enterBtn.setVisible(true);

        forgotCardNumBtn.setVisible(false);
        forgotCardNumLabel.setVisible(false);

        cardNumLabel.setVisible(false);
        cardNumText.setVisible(false);
        showCardsBtn.setVisible(false);
    }


    @FXML
    private void switchToCardsPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("CardsPage");
    }


    @FXML
    private void switchToHostPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("HostPage");
    }

    @FXML
    private void switchToHomePage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("ChargebackPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("MoviesPage");
    }
    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        PersonalAreaPage.logOutCustomer();
        App.switchScreen("PersonalAreaPage");
    }
}

