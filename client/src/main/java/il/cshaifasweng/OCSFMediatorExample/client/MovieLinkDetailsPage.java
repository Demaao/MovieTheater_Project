package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.bytebuddy.asm.Advice;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MovieLinkDetailsPage {

    //   public static Movie selectedMovie;
    @FXML
    private Button cancelBtn;
    @FXML
    private Label movieTitleLabel;

    @FXML
    private ImageView moviePosterImageView;

    @FXML
    private TextArea movieDescriptionTextArea;

    @FXML
    private Label directorLabel;

    @FXML
    private Label actorsLabel;

    @FXML
    private Label yearLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label lengthLabel;

    @FXML
    private Label hebtitle;

    @FXML
    private Label engtitle;

    @FXML
    private ComboBox<String> cinemaComboBox;

    @FXML
    private DatePicker chooseDatePicker;

    @FXML
    private ComboBox<String> timeComboBox;

    @FXML
    private Label bookNowLabel;
    @FXML
    private Button purchaseCardBtn;



    @FXML
    public void initialize() {
        if (MovieDetailsPage.selectedMovie != null) {
            EventBus.getDefault().register(this);

            movieTitleLabel.setText(MovieDetailsPage.selectedMovie.getHebtitle());
            moviePosterImageView.setImage(new Image(new ByteArrayInputStream(MovieDetailsPage.selectedMovie.getImageData())));
            movieDescriptionTextArea.setText(MovieDetailsPage.selectedMovie.getDescription());
            directorLabel.setText("Director : " + MovieDetailsPage.selectedMovie.getDirector());
            actorsLabel.setText("Actors : " + MovieDetailsPage.selectedMovie.getMainActors());
            yearLabel.setText("Year : " + MovieDetailsPage.selectedMovie.getYear());
            genreLabel.setText("Genre : " + MovieDetailsPage.selectedMovie.getGenre());
            lengthLabel.setText("Length : " + MovieDetailsPage.selectedMovie.getLength());
            hebtitle.setText(MovieDetailsPage.selectedMovie.getHebtitle());
            engtitle.setText(MovieDetailsPage.selectedMovie.getEngtitle());

            requestScreeningTimesFromServer();

            chooseDatePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                    }
                }
            });

            if (MovieDetailsPage.selectedMovie instanceof HomeMovie) {
                cinemaComboBox.setVisible(false);
                chooseDatePicker.setVisible(true);
                timeComboBox.setVisible(true);

                chooseDatePicker.setLayoutX(230.0);
                timeComboBox.setLayoutX(430.0);

                chooseDatePicker.setOnAction(event -> updateAvailableTimes(MovieDetailsPage.selectedMovie.getScreenings()));

            }
            else if(MovieDetailsPage.selectedMovie instanceof SoonMovie) {
                cinemaComboBox.setVisible(false);
                chooseDatePicker.setVisible(false);
                timeComboBox.setVisible(false);
                bookNowLabel.setVisible(false);
                lengthLabel.setVisible(false);
            }
            else {
                cinemaComboBox.setVisible(true);
                chooseDatePicker.setVisible(true);
                timeComboBox.setVisible(true);

                cinemaComboBox.setLayoutX(230.0);
                chooseDatePicker.setLayoutX(430.0);
                timeComboBox.setLayoutX(630.0);

                cinemaComboBox.setOnAction(event -> updateAvailableDays(null));
                chooseDatePicker.setOnAction(event -> updateAvailableTimes(null));
            }
        }
    }

    private void requestScreeningTimesFromServer() {
        try {
            NewMessage message = new NewMessage("screeningTimesRequest");
            message.setMovie(MovieDetailsPage.selectedMovie);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onUpdateScreeningTimes(UpdateScreeningTimesEvent event) {
        List<Screening> screenings = event.getScreenings();
        updateUIWithScreeningTimes(screenings);
        updateAvailableDays(screenings);
        updateAvailableTimes(screenings);
    }

    private void updateUIWithScreeningTimes(List<Screening> screenings) {
        cinemaComboBox.getItems().clear();
        timeComboBox.getItems().clear();
        chooseDatePicker.setValue(null);

        if (MovieDetailsPage.selectedMovie instanceof HomeMovie) {
            updateAvailableDays(screenings);
        } else {

            Set<String> availableCinemas = screenings.stream()
                    .filter(screening -> screening.getBranch() != null)
                    .map(screening -> screening.getBranch().getName())
                    .collect(Collectors.toSet());


            cinemaComboBox.getItems().addAll(availableCinemas);

            cinemaComboBox.setOnAction(event -> updateAvailableDays(screenings));
        }
    }


    private void updateAvailableDays(List<Screening> screenings) {
        timeComboBox.getItems().clear();
        chooseDatePicker.setValue(null);

        if (screenings == null || screenings.isEmpty()) {
            screenings = MovieDetailsPage.selectedMovie.getScreenings();
        }

        if (MovieDetailsPage.selectedMovie instanceof HomeMovie) {

            Set<LocalDate> availableDays = screenings.stream()
                    .filter(screening -> screening.getBranch() == null)
                    .map(screening -> screening.getScreeningTime().toLocalDate())
                    .collect(Collectors.toSet());

            chooseDatePicker.setDayCellFactory(picker -> new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (!availableDays.contains(date) || date.isBefore(LocalDate.now())) {
                        setDisable(true);
                    }
                }
            });

        } else {
            String selectedCinema = cinemaComboBox.getValue();
            if (selectedCinema != null) {
                Set<LocalDate> availableDays = screenings.stream()
                        .filter(screening -> screening.getBranch() != null)
                        .filter(screening -> screening.getBranch().getName().equals(selectedCinema))
                        .map(screening -> screening.getScreeningTime().toLocalDate())
                        .collect(Collectors.toSet());

                chooseDatePicker.setDayCellFactory(picker -> new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);
                        if (!availableDays.contains(date) || date.isBefore(LocalDate.now())) {
                            setDisable(true);
                        }
                    }
                });
            }
        }

        List<Screening> finalScreenings = screenings;
        chooseDatePicker.setOnAction(event -> updateAvailableTimes(finalScreenings));
    }

    private void updateAvailableTimes(List<Screening> screenings) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        timeComboBox.getItems().clear();
        timeComboBox.setPromptText("Select Time");

        LocalDate selectedDay = chooseDatePicker.getValue();

        if (screenings == null || screenings.isEmpty()) {
            screenings = MovieDetailsPage.selectedMovie.getScreenings();
        }

        if (MovieDetailsPage.selectedMovie instanceof HomeMovie) {
            // Handle HomeMovie screenings
            if (selectedDay != null) {
                List<LocalTime> availableTimes = screenings.stream()
                        .filter(screening -> screening.getBranch() == null) // Filter for home movies
                        .filter(screening -> screening.getScreeningTime().toLocalDate().equals(selectedDay))
                        .map(screening -> screening.getScreeningTime().toLocalTime())
                        .collect(Collectors.toList());

                timeComboBox.getItems().addAll(
                        availableTimes.stream()
                                .map(time -> time.format(timeFormatter))
                                .collect(Collectors.toList())
                );
            }
        } else {
            // Handle Cinema screenings
            String selectedCinema = cinemaComboBox.getValue();

            if (selectedCinema != null && selectedDay != null) {
                List<LocalTime> availableTimes = screenings.stream()
                        .filter(screening -> screening.getBranch() != null)
                        .filter(screening -> screening.getBranch().getName().equals(selectedCinema))
                        .filter(screening -> screening.getScreeningTime().toLocalDate().equals(selectedDay))
                        .map(screening -> screening.getScreeningTime().toLocalTime())
                        .collect(Collectors.toList());

                timeComboBox.getItems().addAll(
                        availableTimes.stream()
                                .map(time -> time.format(timeFormatter))
                                .collect(Collectors.toList())
                );
            }
        }
    }

    // static int movieDetailsPage;

    @FXML
    private void switchToHomePage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("HostPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("CardsPage");
    }

    public static HomeMoviePurchase homeMoviePurchase = new HomeMoviePurchase();

    @FXML
    private void switchToPurchaseProductsPage() throws IOException {
        Platform.runLater(() -> {
            if(chooseDatePicker.getValue() == null || timeComboBox.getValue() == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please select date and time!");
                alert.show();
            }
            else {
                LocalDate date = chooseDatePicker.getValue();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                LocalTime time = LocalTime.parse(timeComboBox.getValue(), formatter);
                LocalDateTime dateTime = LocalDateTime.of(date, time).withSecond(0).withNano(0);
                Screening screening = new Screening(dateTime, MovieDetailsPage.selectedMovie, null);
                // Split the movie length into hours and minutes
                String[] parts = MovieDetailsPage.selectedMovie.getLength().split(" ");
                int hours = Integer.parseInt(parts[0].replace("h", ""));
                int minutes = Integer.parseInt(parts[1].replace("m", ""));
                // Add hours and minutes to the start time
                LocalDateTime endTime = dateTime.plusHours(hours).plusMinutes(minutes);
                homeMoviePurchase = new HomeMoviePurchase("Link", null, "Credit Card", 100, null, "Movie link for \"" + MovieDetailsPage.selectedMovie.getEngtitle() + "\"." + "\nScreening: " + screening.getScreeningTime().format(formatter1),
                        (HomeMovie) MovieDetailsPage.selectedMovie, dateTime, endTime, screening, ((HomeMovie) MovieDetailsPage.selectedMovie).getLink(), ((HomeMovie) MovieDetailsPage.selectedMovie).getEngtitle());
                homeMoviePurchase.setScreening(screening);
                ((HomeMovie) MovieDetailsPage.selectedMovie).addHomeMoviePurchase(homeMoviePurchase);
                App.switchScreen("PurchaseLink");
            }});
    }

    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        MovieDetailsPage.movieDetailsPage = 0;
        App.switchScreen("PersonalAreaPage");
    }

    @Subscribe
    public void onRequestEvent(UpdateRequestEvent event) {}
}