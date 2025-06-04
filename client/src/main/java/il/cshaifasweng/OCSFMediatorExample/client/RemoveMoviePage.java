package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class RemoveMoviePage {
    @FXML
    private TableView<Movie> movieTable;

    @FXML
    TableColumn screeningTypeColumn;

    @FXML
    TableColumn screeningColumn;

    @FXML
    Label selectCinemaLabel;

    @FXML
    ComboBox<String> chooseCinemaBox;

    @FXML
    Button removeBtn;

    private ObservableList<Movie> movies = FXCollections.observableArrayList();

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

    @FXML
    private void requestLogoutFromServer() {
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();}
    }

  public void initialize() {
        EventBus.getDefault().register(this);
        requestMoviesFromServer();
        requestPurchasesFromServer();
        screeningTypeColumn.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {
          @Override
          public TableCell<Movie, String> call(TableColumn<Movie, String> col) {
              return new TableCell<Movie, String>() {
                  @Override
                  protected void updateItem(String item, boolean empty) {
                      super.updateItem(item, empty);
                      if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                          setText(null);
                      } else {
                          Movie movie = (Movie) getTableRow().getItem();
                          if (movie instanceof HomeMovie) {
                              setText("Link");
                          } else {
                              setText("Cinema");
                          }}}};}});
      screeningColumn.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {
          @Override
          public TableCell<Movie, String> call(TableColumn<Movie, String> col) {
              return new TableCell<Movie, String>() {
                  @Override
                  protected void updateItem(String item, boolean empty) {
                      super.updateItem(item, empty);
                      if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                          setText(null);
                      } else {
                          Movie movie = (Movie) getTableRow().getItem();
                          if (movie instanceof HomeMovie) {
                              setText("-");
                          } else {
                              List<Branch> branches = movie.getBranches();
                              Set<String> availableCinemas = branches.stream()
                                      .map(branch -> branch.getLocation())
                                      .collect(Collectors.toSet());
                              setText(availableCinemas.toString());
                          }}}};}});
      movieTable.setItems(movies);
      movieTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
          if (newSelection != null) {
              if (newSelection instanceof HomeMovie) {
                  selectCinemaLabel.setVisible(false);
                  chooseCinemaBox.setVisible(false);
                  removeBtn.setDisable(false);
              } else {
                  selectCinemaLabel.setVisible(true);
                  chooseCinemaBox.setVisible(true);
                  List<Branch> branches = newSelection.getBranches();
                  Set<String> availableCinemas = branches.stream()
                          .map(branch -> branch.getName())
                          .collect(Collectors.toSet());
                  chooseCinemaBox.getItems().clear();
                  chooseCinemaBox.getItems().add("All");
                  chooseCinemaBox.getItems().addAll(availableCinemas);
                  availableCinemas.clear();
                  removeBtn.setDisable(true);
                  chooseCinemaBox.setOnAction(event -> removeBtn.setDisable(false));}}});
    }

    @Subscribe
    public void onUpdateMoviesEvent(UpdateMoviesEvent event) {
        Platform.runLater(() -> {
            List<Movie> movieList = event.getMovies();
            movieList.removeIf(movie -> movie instanceof SoonMovie);
            ObservableList<Movie> items = movieTable.getItems();
            items.clear();
            movies.addAll(movieList);
            screeningTypeColumn.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {
                @Override
                public TableCell<Movie, String> call(TableColumn<Movie, String> col) {
                    return new TableCell<Movie, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                                setText(null);
                            } else {
                                Movie movie = (Movie) getTableRow().getItem();
                                if (movie instanceof HomeMovie) {
                                    setText("Link");
                                } else {
                                    setText("Cinema");
                                }}}};}});
            screeningColumn.setCellFactory(new Callback<TableColumn<Movie, String>, TableCell<Movie, String>>() {
                @Override
                public TableCell<Movie, String> call(TableColumn<Movie, String> col) {
                    return new TableCell<Movie, String>() {
                        @Override
                        protected void updateItem(String item, boolean empty) {
                            super.updateItem(item, empty);
                            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                                setText(null);
                            } else {
                                Movie movie = (Movie) getTableRow().getItem();
                                if (movie instanceof HomeMovie) {
                                    setText("-");
                                } else {
                                    List<Branch> branches = movie.getBranches();
                                    Set<String> availableCinemasLocation = branches.stream()
                                            .map(branch -> branch.getLocation())
                                            .collect(Collectors.toSet());
                                    setText(availableCinemasLocation.toString());
                                   Set<String> availableCinemas = branches.stream()
                                            .map(branch -> branch.getName())
                                            .collect(Collectors.toSet());
                                    chooseCinemaBox.getItems().clear();
                                    chooseCinemaBox.getItems().add("All");
                                    chooseCinemaBox.getItems().addAll(availableCinemas);
                                    availableCinemas.clear();
                                }}}};}});
            movieTable.setItems(movies);});
    }

    @FXML
    public void removeMovie(){
        Platform.runLater(() -> {
            Movie movie = movieTable.getSelectionModel().getSelectedItem();
            for (Purchase purchase: purchases){
                if ((!(movie instanceof HomeMovie) && chooseCinemaBox.getValue().equals("All"))
                || (!(movie instanceof HomeMovie) && chooseCinemaBox.getValue().equals(purchase.getBranchName()))){
                    String[] lines = purchase.getPurchaseDescription().split("\n");
                    String date = "";
                    String time = "";
                    String movieTitle = "";
                    for (String line : lines) {
                        if (line.startsWith("Movie:")) {
                            movieTitle = line.split(": ")[1].trim();
                        }
                        if (line.startsWith("Date:")) {
                            date = line.split(": ")[1].trim();
                        }
                        if (line.startsWith("Time:")) {
                            time = line.split(": ")[1].trim();
                            break;
                        }
                    }
                    LocalDate date1 = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
                    // Parse the time string into a LocalTime
                    LocalTime time1 = LocalTime.parse(time, DateTimeFormatter.ISO_LOCAL_TIME);
                    LocalDateTime dateTime = LocalDateTime.of(date1, time1);
                    if(movie.getEngtitle().equals(movieTitle) && dateTime.isAfter(LocalDateTime.now())){
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("Remove Movie");
                        alert.setHeaderText(null);
                        alert.setContentText("Cannot remove movie! Tickets for this movie have already been sold");
                        alert.show();
                        return;
                    }
                }
            }
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Remove Movie");
            alert.setHeaderText(movie.getEngtitle());
            alert.setContentText("Are you sure you want to remove this movie?");
            ButtonType yesButton = new ButtonType("Yes");
            ButtonType cancelButton = new ButtonType("Cancel");
            alert.getButtonTypes().setAll(yesButton, cancelButton);
            alert.showAndWait().ifPresent(response -> {
                if (response == yesButton) {
                    NewMessage message;
                    if (movie instanceof HomeMovie) {
                        HomeMovie homeMovie = (HomeMovie) movie;
                        message = new NewMessage(homeMovie,"removeHomeMovie");}
                    else { message = new NewMessage(movie, "removeCinemaMovie", chooseCinemaBox.getValue());}
                    try {
                        SimpleClient.getClient().sendToServer(message);
                        selectCinemaLabel.setVisible(false);
                        chooseCinemaBox.setVisible(false);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }}});});
    }

    private void requestMoviesFromServer() {
        try {
            NewMessage message = new NewMessage("moviesList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onUpdateScreeningEvent(UpdateScreeningTimesEvent event) {}

    @FXML
    private void requestPurchasesFromServer() {
        try {
            NewMessage message = new NewMessage("fetchPurchases");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Purchase> purchases = new ArrayList<>();
    @Subscribe
    public void handleUpdatePurchasesEvent(UpdatePurchasesEvent event) {
        Platform.runLater(() -> {
            purchases = event.getPurchases();
            purchases.removeIf(purchase -> purchase instanceof  Card);
            purchases.removeIf(purchase -> purchase instanceof HomeMoviePurchase);
            purchases.removeIf(purchase -> purchase.getProductType().equals("Movie Tickets"));
            purchases.removeIf(purchase -> purchase.getProductType().equals("Movie Card"));
            purchases.removeIf(purchase -> purchase.getProductType().equals("Movie Link"));

        });
    }

}
