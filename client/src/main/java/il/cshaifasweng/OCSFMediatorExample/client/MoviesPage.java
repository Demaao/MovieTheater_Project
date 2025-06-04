package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoviesPage {
    @FXML
    private Label NowInCinema;
    @FXML
    private Label WatchFromHome;

    @FXML
    private ImageView cinemaImageView1;
    @FXML
    private ImageView cinemaImageView2;
    @FXML
    private ImageView cinemaImageView3;
    @FXML
    private ImageView cinemaImageView4;
    @FXML
    private Label cinemaLabel1;
    @FXML
    private Label cinemaLabel2;
    @FXML
    private Label cinemaLabel3;
    @FXML
    private Label cinemaLabel4;
    @FXML
    private ImageView soonImageView1;
    @FXML
    private ImageView soonImageView2;
    @FXML
    private ImageView soonImageView3;

    @FXML
    private Label soonLabel1;
    @FXML
    private Label soonLabel2;
    @FXML
    private Label soonLabel3;

    @FXML
    private ImageView homeImageView1;
    @FXML
    private ImageView homeImageView2;
    @FXML
    private ImageView homeImageView3;
    @FXML
    private ImageView homeImageView4;

    @FXML
    private Label homeLabel1;
    @FXML
    private Label homeLabel2;
    @FXML
    private Label homeLabel3;
    @FXML
    private Label homeLabel4;

    @FXML
    private ComboBox<String> searchByNameBox;
    @FXML
    private ComboBox<String> searchByGenreBox;
    @FXML
    private ComboBox<String> searchByCinemaBox;

    @FXML
    private DatePicker searchByDatePicker;

    private List<Movie> movies = new ArrayList<>();
    private List<SoonMovie> soonMovies;
    private List<HomeMovie> homeMovies = new ArrayList<>();

    private List<Movie> filteredCinemaMovies = movies;
    private List<HomeMovie> filteredHomeMovies = homeMovies;

    private int cinemaCurrentIndex = 0;
    private int homeCurrentIndex = 0;

    private int filteredCinemaCurrentIndex = 0;
    private int filteredHomeCurrentIndex = 0;

    public void initialize() {
        EventBus.getDefault().register(this);
        requestMoviesFromServer();
        requestSoonMoviesFromServer();
        requestHomeMoviesFromServer();

        // הוספת אפשרויות לתיבת הז'אנרים
        searchByGenreBox.getItems().addAll("All", "Action", "Adventure", "Comedy", "Drama", "Documentary");

        searchByCinemaBox.getItems().addAll("All", "Haifa Cinema", "Tel Aviv Cinema", "Eilat Cinema", "Karmiel Cinema", "Jerusalem Cinema");

        searchByDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);  // If the date is before today it will not be available for selection
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        });

        searchByNameBox.setOnAction(event -> {        // מאזין לבחירת שם סרט
            String selectedMovieName = searchByNameBox.getValue();
            if (selectedMovieName != null) {
                Movie selectedMovie = findMovieByName(selectedMovieName);
                if (selectedMovie != null) {
                    openMovieDetailsPage(selectedMovie);
                }
            }
        });

        searchByGenreBox.setOnAction(event -> {      // מאזין לבחירת ז'אנר
            filterMovies();
        });

        searchByCinemaBox.setOnAction(event -> {     //מאיזין לבחירת בית קלנוע
            filterMovies();
        });

        searchByDatePicker.setOnAction(event -> {
            filterMovies();
        });

        cinemaImageView1.setOnMouseClicked(event -> openMovieDetailsPage(filteredCinemaMovies.get(filteredCinemaCurrentIndex)));
        cinemaLabel1.setOnMouseClicked(event -> openMovieDetailsPage(filteredCinemaMovies.get(filteredCinemaCurrentIndex)));


        cinemaImageView2.setOnMouseClicked(event -> openMovieDetailsPage(filteredCinemaMovies.get(filteredCinemaCurrentIndex + 1)));
        cinemaLabel2.setOnMouseClicked(event -> openMovieDetailsPage(filteredCinemaMovies.get(filteredCinemaCurrentIndex + 1)));

        cinemaImageView3.setOnMouseClicked(event -> openMovieDetailsPage(filteredCinemaMovies.get(filteredCinemaCurrentIndex + 2)));
        cinemaLabel3.setOnMouseClicked(event -> openMovieDetailsPage(filteredCinemaMovies.get(filteredCinemaCurrentIndex + 2)));

        cinemaImageView4.setOnMouseClicked(event -> openMovieDetailsPage(filteredCinemaMovies.get(filteredCinemaCurrentIndex + 3)));
        cinemaLabel4.setOnMouseClicked(event -> openMovieDetailsPage(filteredCinemaMovies.get(filteredCinemaCurrentIndex + 3)));

        homeImageView1.setOnMouseClicked(event -> openMovieDetailsPage(filteredHomeMovies.get(filteredHomeCurrentIndex)));
        homeLabel1.setOnMouseClicked(event -> openMovieDetailsPage(filteredHomeMovies.get(filteredHomeCurrentIndex)));

        homeImageView2.setOnMouseClicked(event -> openMovieDetailsPage(filteredHomeMovies.get(filteredHomeCurrentIndex + 1)));
        homeLabel2.setOnMouseClicked(event -> openMovieDetailsPage(filteredHomeMovies.get(filteredHomeCurrentIndex + 1)));

        homeImageView3.setOnMouseClicked(event -> openMovieDetailsPage(filteredHomeMovies.get(filteredHomeCurrentIndex + 2)));
        homeLabel3.setOnMouseClicked(event -> openMovieDetailsPage(filteredHomeMovies.get(filteredHomeCurrentIndex + 2)));

        homeImageView4.setOnMouseClicked(event -> openMovieDetailsPage(filteredHomeMovies.get(filteredHomeCurrentIndex + 3)));
        homeLabel4.setOnMouseClicked(event -> openMovieDetailsPage(filteredHomeMovies.get(filteredHomeCurrentIndex + 3)));


        soonImageView1.setOnMouseClicked(event -> openMovieDetailsPage(soonMovies.get(0)));
        soonLabel1.setOnMouseClicked(event -> openMovieDetailsPage(soonMovies.get(0)));

        soonImageView2.setOnMouseClicked(event -> openMovieDetailsPage(soonMovies.get(1)));
        soonLabel2.setOnMouseClicked(event -> openMovieDetailsPage(soonMovies.get(1)));

        soonImageView3.setOnMouseClicked(event -> openMovieDetailsPage(soonMovies.get(2)));
        soonLabel3.setOnMouseClicked(event -> openMovieDetailsPage(soonMovies.get(2)));

    }

    @Subscribe
    public void onUpdateMoviesEvent(UpdateMoviesEvent event) {
        Platform.runLater(() -> {
            this.filteredCinemaMovies = event.getMovies();
            this.movies = event.getMovies();
            filterMovies();
            //updateMovies(event.getMovies());
        });
    }

    @Subscribe
    public void onUpdateSoonMoviesEvent(UpdateSoonMoviesEvent event) {
        Platform.runLater(() -> {
            this.soonMovies = event.getSoonMovies();
            updateSoonMovies();
        });
    }

    @Subscribe
    public void onUpdateHomeMoviesEvent(UpdateHomeMoviesEvent event) {
        Platform.runLater(() -> {
            this.filteredHomeMovies = event.getHomeMovies();
            this.homeMovies = event.getHomeMovies();
            filterMovies();
          //  updateHomeMovies();
        });
    }

    private void requestMoviesFromServer() {
        try {
            NewMessage message = new NewMessage("moviesList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestSoonMoviesFromServer() {
        try {
            NewMessage message = new NewMessage("soonMoviesList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void requestHomeMoviesFromServer() {
        try {
            NewMessage message = new NewMessage("homeMoviesList");
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateHomeMovies() {
        if (homeMovies != null && homeMovies.size() >= 4) {
            homeImageView1.setImage(new Image(new ByteArrayInputStream(homeMovies.get(homeCurrentIndex).getImageData())));
            homeLabel1.setText(homeMovies.get(homeCurrentIndex).getHebtitle());

            homeImageView2.setImage(new Image(new ByteArrayInputStream(homeMovies.get(homeCurrentIndex + 1).getImageData())));
            homeLabel2.setText(homeMovies.get(homeCurrentIndex + 1).getHebtitle());

            homeImageView3.setImage(new Image(new ByteArrayInputStream(homeMovies.get(homeCurrentIndex + 2).getImageData())));
            homeLabel3.setText(homeMovies.get(homeCurrentIndex + 2).getHebtitle());

            homeImageView4.setImage(new Image(new ByteArrayInputStream(homeMovies.get(homeCurrentIndex + 3).getImageData())));
            homeLabel4.setText(homeMovies.get(homeCurrentIndex + 3).getHebtitle());

            populateSearchByNameBox();
        }
    }

    private void updateImages() {
        if (movies != null && movies.size() >= 10) {
            cinemaImageView1.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex).getImageData())));
            cinemaLabel1.setText(movies.get(cinemaCurrentIndex).getHebtitle());

            cinemaImageView2.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 1).getImageData())));
            cinemaLabel2.setText(movies.get(cinemaCurrentIndex + 1).getHebtitle());

            cinemaImageView3.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 2).getImageData())));
            cinemaLabel3.setText(movies.get(cinemaCurrentIndex + 2).getHebtitle());

            cinemaImageView4.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 3).getImageData())));
            cinemaLabel4.setText(movies.get(cinemaCurrentIndex + 3).getHebtitle());
        }
    }

    private void updateMovieView(ImageView imageView, Label label, Movie movie) {
        imageView.setImage(new Image(new ByteArrayInputStream(movie.getImageData())));
        label.setText(movie.getHebtitle());
    }

    public void updateMovies(List<Movie> movies) {
        int flag = 0;
        this.movies.clear();
        for (Movie movie : movies){
            for(Branch branch : movie.getBranches()){
                if(branch.getName().equals("Haifa Cinema") || branch.getName().equals("Tel Aviv Cinema") ||
                        branch.getName().equals("Eilat Cinema") || branch.getName().equals("Karmiel Cinema")
                        || branch.getName().equals("Jerusalem Cinema") ){
                    flag = 1;
                }
            }
            if(flag == 1){
                this.movies.add(movie);
                flag = 0;
            }
        }
        filteredCinemaMovies = this.movies;
        cinemaCurrentIndex = 0;
        filteredCinemaCurrentIndex = 0;
      //  updateImages();
        if (movies != null && movies.size() >= 10) {
            cinemaImageView1.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex).getImageData())));
            cinemaLabel1.setText(movies.get(cinemaCurrentIndex).getHebtitle());

            cinemaImageView2.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 1).getImageData())));
            cinemaLabel2.setText(movies.get(cinemaCurrentIndex + 1).getHebtitle());

            cinemaImageView3.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 2).getImageData())));
            cinemaLabel3.setText(movies.get(cinemaCurrentIndex + 2).getHebtitle());

            cinemaImageView4.setImage(new Image(new ByteArrayInputStream(movies.get(cinemaCurrentIndex + 3).getImageData())));
            cinemaLabel4.setText(movies.get(cinemaCurrentIndex + 3).getHebtitle());
        }
        populateSearchByNameBox(); //to add the movies name to the search box
    }

    @FXML
    private void handleLeftButton() {
        if (filteredCinemaMovies != null && filteredCinemaMovies.size() > 0) {
            if (filteredCinemaCurrentIndex > 0) {
                filteredCinemaCurrentIndex--;
                updateFilteredCinemaMovies();
            }
        }
    }

    @FXML
    private void handleRightButton() {
        if (filteredCinemaMovies != null && filteredCinemaMovies.size() > 0) {
            if (filteredCinemaCurrentIndex < filteredCinemaMovies.size() - 4) {
                filteredCinemaCurrentIndex++;
                updateFilteredCinemaMovies();
            }
        }
    }

    @FXML
    private void handleHomeLeftButton() {
        if (filteredHomeMovies != null && filteredHomeMovies.size() > 0) {
            if (filteredHomeCurrentIndex > 0) {
                filteredHomeCurrentIndex--;
                updateFilteredHomeMovies();
            }
        }
    }

    @FXML
    private void handleHomeRightButton() {
        if (filteredHomeMovies != null && filteredHomeMovies.size() > 0) {
            if (filteredHomeCurrentIndex < filteredHomeMovies.size() - 4) {
                filteredHomeCurrentIndex++;
                updateFilteredHomeMovies();
            }
        }
    }

    private void updateSoonMovies() {
        if (soonMovies != null && soonMovies.size() == 3) {
            soonImageView1.setImage(new Image(new ByteArrayInputStream(soonMovies.get(0).getImageData())));
            soonLabel1.setText(soonMovies.get(0).getHebtitle());

            soonImageView2.setImage(new Image(new ByteArrayInputStream(soonMovies.get(1).getImageData())));
            soonLabel2.setText(soonMovies.get(1).getHebtitle());

            soonImageView3.setImage(new Image(new ByteArrayInputStream(soonMovies.get(2).getImageData())));
            soonLabel3.setText(soonMovies.get(2).getHebtitle());

            populateSearchByNameBox();
        }
    }

    // we call this method after the movies updated to add options to  searchByNameBox
    private void populateSearchByNameBox() {
        if (movies != null) {
            searchByNameBox.getItems().clear();// Clear existing items
            for (Movie movie : movies) {
                searchByNameBox.getItems().add(movie.getEngtitle());
            }
        }
        if (homeMovies != null) {
            for (HomeMovie movie : homeMovies) {
                searchByNameBox.getItems().add(movie.getEngtitle());
            }
        }
        if (soonMovies != null) {
            for (SoonMovie movie : soonMovies) {
                searchByNameBox.getItems().add(movie.getEngtitle());
            }
        }
    }

    private void updateFilteredCinemaMovies() {
        clearCinemaDisplay();
        if (!filteredCinemaMovies.isEmpty()) {
            for (int i = 0; i < Math.min(filteredCinemaMovies.size(), 4); i++) {
                int displayIndex = filteredCinemaCurrentIndex + i;
                if (displayIndex < filteredCinemaMovies.size()) {
                    updateMovieView(getCinemaImageView(i), getCinemaLabel(i), filteredCinemaMovies.get(displayIndex));
                }
            }
        }
    }

    private void updateFilteredHomeMovies() {
        clearHomeDisplay();
        if (!filteredHomeMovies.isEmpty()) {
            for (int i = 0; i < Math.min(filteredHomeMovies.size(), 4); i++) {
                int displayIndex = filteredHomeCurrentIndex + i;
                if (displayIndex < filteredHomeMovies.size()) {
                    updateMovieView(getHomeImageView(i), getHomeLabel(i), filteredHomeMovies.get(displayIndex));
                }
            }
        }
    }

    private ImageView getCinemaImageView(int index) {
        switch (index) {
            case 0: return cinemaImageView1;
            case 1: return cinemaImageView2;
            case 2: return cinemaImageView3;
            case 3: return cinemaImageView4;
            default: return null;
        }
    }

    private Label getCinemaLabel(int index) {
        switch (index) {
            case 0: return cinemaLabel1;
            case 1: return cinemaLabel2;
            case 2: return cinemaLabel3;
            case 3: return cinemaLabel4;
            default: return null;
        }
    }

    private ImageView getHomeImageView(int index) {
        switch (index) {
            case 0: return homeImageView1;
            case 1: return homeImageView2;
            case 2: return homeImageView3;
            case 3: return homeImageView4;
            default: return null;
        }
    }

    private Label getHomeLabel(int index) {
        switch (index) {
            case 0: return homeLabel1;
            case 1: return homeLabel2;
            case 2: return homeLabel3;
            case 3: return homeLabel4;
            default: return null;
        }
    }

    private void clearCinemaDisplay() {
        cinemaImageView1.setImage(null);
        cinemaLabel1.setText("");
        cinemaImageView2.setImage(null);
        cinemaLabel2.setText("");
        cinemaImageView3.setImage(null);
        cinemaLabel3.setText("");
        cinemaImageView4.setImage(null);
        cinemaLabel4.setText("");
    }

    private void clearHomeDisplay() {
        homeImageView1.setImage(null);
        homeLabel1.setText("");
        homeImageView2.setImage(null);
        homeLabel2.setText("");
        homeImageView3.setImage(null);
        homeLabel3.setText("");
        homeImageView4.setImage(null);
        homeLabel4.setText("");
    }

    private Movie findMovieByName(String movieName) {   //if we want to search by name
        for (Movie movie : movies) {
            if (movie.getEngtitle().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        for(HomeMovie movie : homeMovies) {
            if (movie.getEngtitle().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        for(SoonMovie movie : soonMovies) {
            if (movie.getEngtitle().equalsIgnoreCase(movieName)) {
                return movie;
            }
        }
        return null;
    }

    private void openMovieDetailsPage(Movie movie) {
        MovieDetailsPage.setSelectedMovie(movie);  // שמור את הסרט שנבחר
        MovieDetailsPage.movieDetailsPage = 1;
        if (movie instanceof HomeMovie) {
            App.switchScreen("MovieLinkDetailsPage");  // Navigate to HomeMovieDetailsPage
        }
        else {
            //MovieDetailsPage.setSelectedMovie(movie);
            App.switchScreen("MovieDetailsPage");  // Navigate to MovieDetailsPage
        }
        //App.switchScreen("MovieDetailsPage");  // מעבר לדף
    }

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    @FXML
    private void switchToComplaintPage() throws IOException {
        App.switchScreen("ComplaintPage");
    }

    @FXML
    private void switchToLoginPage() throws IOException {
        App.switchScreen("LoginPage");
    }

    @FXML
    private void switchToHostPage() throws IOException {
        App.switchScreen("HostPage");
    }

    @FXML
    public void switchToMoviesPage() throws IOException {
        App.switchScreen("MoviesPage");
    }

    @FXML
    private void switchToChargebackPage() throws IOException {
        App.switchScreen("ChargebackPage");
    }

    @FXML
    private void switchToCardsPage() throws IOException {
        App.switchScreen("CardsPage");
    }
    @FXML
    private void  switchToPersonalAreaPage() throws IOException {
        App.switchScreen("PersonalAreaPage");
    }

    private void filterMovies() {
        LocalDate selectedDate = searchByDatePicker.getValue();
        String selectedCinema = searchByCinemaBox.getValue();
        String selectedGenre = searchByGenreBox.getValue();

        if (movies != null) {
            filteredCinemaMovies = movies.stream()
                    .filter(movie -> !(movie instanceof HomeMovie))
                    .filter(movie -> !(movie instanceof SoonMovie))
                    .filter(movie -> selectedGenre == null || selectedGenre.equals("All") ||
                            movie.getGenre().equalsIgnoreCase(selectedGenre))
                    .filter(movie -> selectedCinema == null || selectedCinema.equals("All") ||
                            movie.getBranches().stream().anyMatch(branch ->
                                    branch.getName().equalsIgnoreCase(selectedCinema)))
                    .filter(movie -> selectedDate == null || ((selectedCinema == null || selectedCinema.equals("All")) && movie.getScreenings().stream().anyMatch(screening ->
                                    screening.getScreeningTime().toLocalDate().equals(selectedDate) ) || movie.getScreenings().stream().anyMatch(screening ->
                            screening.getScreeningTime().toLocalDate().equals(selectedDate) && screening.getBranch().getName().equalsIgnoreCase(selectedCinema))))
                    .collect(Collectors.toList());

            NowInCinema.setText(buildCinemaLabel(selectedDate, selectedCinema, selectedGenre));
        }

        if (homeMovies != null) {
            filteredHomeMovies = homeMovies.stream()
                    .filter(movie -> selectedGenre == null || selectedGenre.equals("All") ||
                            movie.getGenre().equalsIgnoreCase(selectedGenre))
                    .filter(movie -> selectedDate == null || movie.getScreenings().stream().anyMatch(screening ->
                            screening.getScreeningTime().toLocalDate().equals(selectedDate)))
                    .collect(Collectors.toList());

            WatchFromHome.setText(buildHomeLabel(selectedDate, selectedGenre));
        }

        filteredCinemaCurrentIndex = 0;
        filteredHomeCurrentIndex = 0;

        updateFilteredCinemaMovies();
        updateFilteredHomeMovies();
        populateSearchByNameBox();
    }

    private String buildCinemaLabel(LocalDate date, String cinema, String genre) {
        StringBuilder label;
        if (genre != null && !genre.equals("All")) {
            label = new StringBuilder(genre + " movies");
        }
        else{
            label = new StringBuilder("Movies");
        }
        if (cinema != null && !cinema.equals("All")) {
            label.append(" in ").append(cinema);
        }
        else {
            label.append(" in the cinema");
        }
        if (date != null) {
            label.append(" on ").append(date);
        }
        return label.toString();
    }

    private String buildHomeLabel(LocalDate date, String genre) {
        StringBuilder label;
        if (genre != null && !genre.equals("All")) {
            label = new StringBuilder(genre + " movies to watch from home");
        }
        else{
            label = new StringBuilder("Watch from home");
        }
        if (date != null) {
            label.append(" on ").append(date);
        }
        return label.toString();
    }

    @Subscribe
    public void onUpdateScreeningEvent(UpdateScreeningTimesEvent event) {}
}