package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class MovieDetailsPage {

    public static Movie selectedMovie;
    @FXML
    private Button cancelBtn;
    private List<Screening> selectedScreening;

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
    private Button SBttn;

    public static void setSelectedMovie(Movie movie) {
        selectedMovie = movie;
    }


    @FXML
    public void initialize() {
        //    System.out.println("this shit is being invoked");//TODO
        if (selectedMovie != null) {
            EventBus.getDefault().register(this);
            requestCinemaFromServer();


            movieTitleLabel.setText(selectedMovie.getHebtitle());
            moviePosterImageView.setImage(new Image(new ByteArrayInputStream(selectedMovie.getImageData())));
            movieDescriptionTextArea.setText(selectedMovie.getDescription());
            directorLabel.setText("Director : " + selectedMovie.getDirector());
            actorsLabel.setText("Actors : " + selectedMovie.getMainActors());
            yearLabel.setText("Year : " + selectedMovie.getYear());
            genreLabel.setText("Genre : " + selectedMovie.getGenre());
            lengthLabel.setText("Length : " + selectedMovie.getLength());
            hebtitle.setText(selectedMovie.getHebtitle());
            engtitle.setText(selectedMovie.getEngtitle());

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

            if (selectedMovie instanceof HomeMovie) {
                cinemaComboBox.setVisible(false);
                chooseDatePicker.setVisible(true);
                timeComboBox.setVisible(true);

                chooseDatePicker.setLayoutX(230.0);
                timeComboBox.setLayoutX(430.0);

                chooseDatePicker.setOnAction(event -> updateAvailableTimes(selectedMovie.getScreenings()));
                System.out.println("screenings size1: " +  selectedMovie.getScreenings().size());

            }

            else if(selectedMovie instanceof SoonMovie) {
                cinemaComboBox.setVisible(false);
                chooseDatePicker.setVisible(false);
                timeComboBox.setVisible(false);
                bookNowLabel.setVisible(false);
                lengthLabel.setVisible(false);
                SBttn.setVisible(false);
                cancelBtn.setText("Back");
            }

            else {
                SBttn.setVisible(true);
                cancelBtn.setVisible(true);
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
            message.setMovie(selectedMovie);
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

    @Subscribe
    public void onUpdateScreeningEvent(UpdateScreeningTimesEvent event) {
        selectedScreening = event.getScreenings();

    }

    private void updateUIWithScreeningTimes(List<Screening> screenings) {
        cinemaComboBox.getItems().clear();
        timeComboBox.getItems().clear();
        chooseDatePicker.setValue(null);

        if (selectedMovie instanceof HomeMovie) {
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

    void setScreeningToHall() {
        LocalDate localDate = chooseDatePicker.getValue();
        String time = timeComboBox.getValue();
        LocalTime time1 = LocalTime.parse(time);
        LocalDateTime screeningTime = localDate.atTime(time1.minusHours(3));
        requestScreeningFromServer();
        // System.out.println("my sister 1  me6");
//        System.out.println("onUpdateScreeningEvent screenings size: " +  selectedScreening.size());
//        selectedScreening.forEach(e -> {if(e.getScreeningTime().equals(screeningTime) && e.getBranch().getName().equals(cinemaComboBox.getValue())){
//            System.out.println("selected screening branch: " + e.getBranch().getName() + " screening time: " + e.getScreeningTime() + " hall: " + e.getHall());
//            ChooseSeating.setScreening(e);
//        }}); System.out.println(e.getScreeningTime().toString()+"  "+ e.getScreeningHourAndMinute()+"  "+timeComboBox.getValue() + "  "+e.getBranch().getName() + "  "+ cinemaComboBox.getValue()) ;
        selectedMovie.getScreenings().forEach(e -> {
            if(e.getScreeningHourAndMinute().equals(timeComboBox.getValue()) &&e.getScreeningTime().toLocalDate().equals(chooseDatePicker.getValue())&& e.getBranch().getName().equals(cinemaComboBox.getValue())){
                ChooseSeating.setScreening(e);

            }

        });
    }


    private void requestScreeningFromServer() {
        try {
            NewMessage message = new NewMessage("screeningHallsRequest");
            message.setMovie(selectedMovie);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void updateAvailableDays(List<Screening> screenings) {
        timeComboBox.getItems().clear(); ///////////////////////////////////////////////
        chooseDatePicker.setValue(null); ///////////////////////////////////////////////

        if (screenings == null || screenings.isEmpty()) {
            screenings = selectedMovie.getScreenings();
        }

        if (selectedMovie instanceof HomeMovie) {

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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        timeComboBox.getItems().clear();
        timeComboBox.setPromptText("Select Time");

        LocalDate selectedDay = chooseDatePicker.getValue();

        if (screenings == null || screenings.isEmpty()) {
            screenings = selectedMovie.getScreenings();
        }

        if (selectedMovie instanceof HomeMovie) {
            // Handle HomeMovie screenings
            if (selectedDay != null) {
                List<LocalTime> availableTimes = screenings.stream()
                        .filter(screening -> screening.getBranch() == null) // Filter for home movies
                        .filter(screening -> screening.getScreeningTime().toLocalDate().equals(selectedDay))
                        .map(screening -> screening.getScreeningTime().toLocalTime())
                        .collect(Collectors.toList());

                timeComboBox.getItems().addAll(
                        availableTimes.stream().map(LocalTime::toString).collect(Collectors.toList())
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
                        availableTimes.stream().map(time -> time.format(formatter)).collect(Collectors.toList())
                );

            }
        }
    }

    private void updateUIWithHallDetails(Hall hall) {
        System.out.println("Hall ID: " + hall.getId());
    }

    static int movieDetailsPage;

    @FXML
    private void switchToHomePage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("HostPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("CardsPage");
    }
    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        movieDetailsPage = 0;
        App.switchScreen("PersonalAreaPage");
    }
    @FXML
    private void switchToPurchaseProductsPage() throws IOException {

        if (selectedMovie instanceof HomeMovie) {
            //System.out.println("Navigating to PurchaseLink");
            App.switchScreen("PurchaseLink");
        } else {

            App.switchScreen("PurchaseProductsPage");
        }

    }

    @FXML
    private void switchToChooseSeating() throws IOException, InterruptedException {
        if(chooseDatePicker.getValue() == null || timeComboBox.getValue() == null || cinemaComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select a cinema, date and time!");
            alert.show();
        }
        else {
            setScreeningToHall();
            App.switchScreen("ChooseSeating");
        }
    }

    private Cinema cinema = new Cinema();
    public static double ticketValue;
    public static int setTicketPrice = 0;

    @Subscribe
    public void onCinemaEvent(UpdateCinemaEvent event) {
        Platform.runLater(() -> {
            Cinema cinema = event.getCinema();
            this.cinema = cinema;
            if(setTicketPrice == 0 && MovieDetailsPage.movieDetailsPage == 1) {
                ticketValue = cinema.getTicketPrice();
                setTicketPrice = 1;
            }
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