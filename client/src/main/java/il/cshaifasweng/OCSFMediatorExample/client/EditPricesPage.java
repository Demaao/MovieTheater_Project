package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.ChangePriceRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.Cinema;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EditPricesPage {
    @FXML
    private Button addMovieBtn;

    @FXML
    private Label cardPriceLabel;

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button editCardPriceBtn;

    @FXML
    private Button editLinkTicketPriceBtn;

    @FXML
    private Button editPricesBtn;

    @FXML
    private Button editScreenigBtn;

    @FXML
    private Button editTicketPriceBtn;

    @FXML
    private Button homePageBtn;

    @FXML
    private Label linkTicketPriceLabel;

    @FXML
    private Button logOutBtn;

    @FXML
    private TextField newCardPriceText;

    @FXML
    private TextField newLinkTicketPriceText;

    @FXML
    private TextField newTicketPriceText;

    @FXML
    private Button removeMovieBtn;

    @FXML
    private Button saveCardPriceBtn;

    @FXML
    private Button saveLinkTicketPriceBtn;

    @FXML
    private Button saveTicketPriceBtn;

    @FXML
    private Button cancelCardBtn;

    @FXML
    private Button cancelLinkTicketBtn;

    @FXML
    private Button cancelTicketBtn;

    @FXML
    private Label ticketPriceLabel;

    @FXML
    private AnchorPane requestAnchor;

    public TableColumn requestNOColumn;
    public TableColumn productColumn;
    public TableColumn dateColumn;
    public TableColumn newPriceColumn;
    public TableColumn statusColumn;
    public TableColumn currentPriceColumn;

    @FXML
    private TableView<ChangePriceRequest> priceUpdateRequestTable;

    public void showRequestAnchor(ActionEvent actionEvent) {
        requestAnchor.setVisible(true);
    }

    public void hideRequestAnchor(ActionEvent actionEvent) {
        requestAnchor.setVisible(false);
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

    @FXML
    private void switchToAddMoviePage() throws IOException {
        App.switchScreen("AddMoviePage");
    }

    @FXML
    private void switchToRemoveMoviePage() throws IOException {
        App.switchScreen("RemoveMoviePage");
    }

    @FXML
    private void switchToEditPricesPage() throws IOException {
        App.switchScreen("EditPricesPage");
    }

    @FXML
    private void switchToEditScreeningPage() throws IOException {
        App.switchScreen("EditScreeningPage");
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
    private void switchToContentManagerPage() throws IOException {
        App.switchScreen("ContentManagerPage");
    }

    public void editTicketPrice(ActionEvent actionEvent) {
        editTicketPriceBtn.setVisible(false);
        newTicketPriceText.setVisible(true);
        saveTicketPriceBtn.setVisible(true);
        cancelTicketBtn.setVisible(true);
    }

    public void editLinkTicketPrice(ActionEvent actionEvent) {
        editLinkTicketPriceBtn.setVisible(false);
        newLinkTicketPriceText.setVisible(true);
        saveLinkTicketPriceBtn.setVisible(true);
        cancelLinkTicketBtn.setVisible(true);
    }

    public void editCardPrice(ActionEvent actionEvent) {
        editCardPriceBtn.setVisible(false);
        newCardPriceText.setVisible(true);
        saveCardPriceBtn.setVisible(true);
        cancelCardBtn.setVisible(true);
    }

    public void saveCardPrice(ActionEvent actionEvent) {
        String priceText = newCardPriceText.getText();
        try {
            double price = Double.parseDouble(priceText);
            // Check if the price is non-negative
            if (price >= 0) {
                ChangePriceRequest request = new ChangePriceRequest(cinema.getCardPrice(), price, "Card");
                NewMessage message = new NewMessage(request, "changePriceRequest");
                SimpleClient.getClient().sendToServer(message);
                editCardPriceBtn.setVisible(true);
                newCardPriceText.clear();
                newCardPriceText.setVisible(false);
                saveCardPriceBtn.setVisible(false);
                cancelCardBtn.setVisible(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Price");
                alert.setHeaderText(null);
                alert.setContentText("Price cannot be negative.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input. Please enter a numerical value.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveLinkTicketPrice(ActionEvent actionEvent) {
        String priceText = newLinkTicketPriceText.getText();
        try {
            double price = Double.parseDouble(priceText);
            // Check if the price is non-negative
            if (price >= 0) {
                ChangePriceRequest request = new ChangePriceRequest(cinema.getLinkTicketPrice(),price, "Link Ticket");
                NewMessage message = new NewMessage(request, "changePriceRequest");
                SimpleClient.getClient().sendToServer(message);
                newLinkTicketPriceText.clear();
                editLinkTicketPriceBtn.setVisible(true);
                newLinkTicketPriceText.setVisible(false);
                saveLinkTicketPriceBtn.setVisible(false);
                cancelLinkTicketBtn.setVisible(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Price");
                alert.setHeaderText(null);
                alert.setContentText("Price cannot be negative.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input. Please enter a numerical value.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveTicketPrice(ActionEvent actionEvent) {
        String priceText = newTicketPriceText.getText();
        try {
            double price = Double.parseDouble(priceText);
            // Check if the price is non-negative
            if (price >= 0) {
                ChangePriceRequest request = new ChangePriceRequest(cinema.getTicketPrice(), price, "Ticket");
                NewMessage message = new NewMessage(request, "changePriceRequest");
                SimpleClient.getClient().sendToServer(message);
                newTicketPriceText.clear();
                editTicketPriceBtn.setVisible(true);
                newTicketPriceText.setVisible(false);
                saveTicketPriceBtn.setVisible(false);
                cancelTicketBtn.setVisible(false);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Price");
                alert.setHeaderText(null);
                alert.setContentText("Price cannot be negative.");
                alert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input. Please enter a numerical value.");
            alert.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void cancelTicket(ActionEvent actionEvent) {
        newTicketPriceText.clear();
        editTicketPriceBtn.setVisible(true);
        newTicketPriceText.setVisible(false);
        saveTicketPriceBtn.setVisible(false);
        cancelTicketBtn.setVisible(false);
    }

    public void cancelLinkTicket(ActionEvent actionEvent) {
        newLinkTicketPriceText.clear();
        editLinkTicketPriceBtn.setVisible(true);
        newLinkTicketPriceText.setVisible(false);
        saveLinkTicketPriceBtn.setVisible(false);
        cancelLinkTicketBtn.setVisible(false);
    }

    public void cancelCard(ActionEvent actionEvent) {
        newCardPriceText.clear();
        editCardPriceBtn.setVisible(true);
        newCardPriceText.setVisible(false);
        saveCardPriceBtn.setVisible(false);
        cancelCardBtn.setVisible(false);
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

    private Cinema cinema = new Cinema();

  public void initialize() {
      EventBus.getDefault().register(this);
      requestCinemaFromServer();
      requestRequestsFromServer();
      DecimalFormat decimalFormat = new DecimalFormat("#.##");

      double ticketValue = cinema.getTicketPrice();
      String strValue = decimalFormat.format(ticketValue);
      ticketPriceLabel.setText(strValue);

      double linkTicketValue = cinema.getLinkTicketPrice();
      String strValue2 = decimalFormat.format(linkTicketValue);
      linkTicketPriceLabel.setText(strValue2);

      double cardValue = cinema.getCardPrice();
      String strValue3 = decimalFormat.format(cardValue);
      cardPriceLabel.setText(strValue3);
  }

    @Subscribe
    public void onCinemaEvent(UpdateCinemaEvent event) {
        Platform.runLater(() -> {
      //  System.out.println("got here");
          //  System.out.println("Im here");
            DecimalFormat decimalFormat = new DecimalFormat("#.##");
            Cinema cinema = event.getCinema();
            this.cinema = cinema;
            double ticketValue = cinema.getTicketPrice();
           // System.out.println(ticketValue);
            String strValue = decimalFormat.format(ticketValue);
            ticketPriceLabel.setText(strValue);

            double linkTicketValue = cinema.getLinkTicketPrice();
            String strValue2 = decimalFormat.format(linkTicketValue);
            linkTicketPriceLabel.setText(strValue2);

            double cardValue = cinema.getCardPrice();
            String strValue3 = decimalFormat.format(cardValue);
            cardPriceLabel.setText(strValue3);

        });
    }

    @FXML
    private void requestCinemaFromServer() {
        try {
            NewMessage message = new NewMessage("cinema");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

}
