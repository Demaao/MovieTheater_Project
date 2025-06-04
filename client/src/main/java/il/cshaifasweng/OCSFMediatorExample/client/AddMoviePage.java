package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class AddMoviePage {
    @FXML
    private Label hallLabel;

    @FXML
    private TableView<Movie> movieTable;

    @FXML
    TableColumn screeningTypeColumn;

    @FXML
    TableColumn screeningColumn;

    @FXML
    private TextField actors;

    @FXML
    private Button addBtn;

    @FXML
    private DatePicker addDateText;

    @FXML
    private TextField addHoursText;

    @FXML
    private Button addMovieBtn;

    @FXML
    private Button addScreeningBtn;

    @FXML
    private Button addTimeBtn;

    @FXML
    private Button changeHostBtn;

    @FXML
    private ComboBox<String> cinemaComboBox;

    @FXML
    private Label cinemaLabel;

    @FXML
    private Label cinemaLabel1;

    @FXML
    private Label contentManagerNameLabel;

    @FXML
    private TextArea description;

    @FXML
    private TextField director;

    @FXML
    private Button editPricesBtn;

    @FXML
    private Button editScreenigBtn;

    @FXML
    private TextField engName;

    @FXML
    private TextField genre;

    @FXML
    private TextField hebName;

    @FXML
    private Button homePageBtn;

    @FXML
    private TextField imagePath;

    @FXML
    private Label linkLabel;

    @FXML
    private Label linkLabel1;

    @FXML
    private Label linkLabel2;

    @FXML
    private TextField linkText;

    @FXML
    private Button logOutBtn;

    @FXML
    private TextField movieLength;

    @FXML
    private Button removeMovieBtn;

    @FXML
    private TableView<Screening> screeningTimeTableCinema;

    @FXML
    private TableView<Screening> screeningTimeTableLink;

    private ObservableList<Screening> screeningsForTableLink = FXCollections.observableArrayList();

    private ObservableList<Screening> screeningsForTableCinema = FXCollections.observableArrayList();

    private List<Screening> movieCinemaScreenings = new ArrayList<>();

    private List<LocalDateTime> movieCinemaDateTimes = new ArrayList<>();

    private List<Hall> movieCinemaHalls = new ArrayList<>();

    private List<Screening> movieLinkScreenings = new ArrayList<>();

    private List<Branch> movieBranches = new ArrayList<>();

    private ObservableList<Movie> movies = FXCollections.observableArrayList();

    private ObservableList<Screening> allScreenings = FXCollections.observableArrayList();

    @FXML
    private ComboBox<Integer> hallComboBox;

    @FXML
    private AnchorPane screenings;

    @FXML
    private TextField year;

    @FXML
    private Spinner<String> screeningTypeSpinner;

    private int flag = 0;

    private ObservableList<String> ScreeningType =
            FXCollections.observableArrayList("In The Cinema", "Link");

    @FXML
    public void initialize() {
        EventBus.getDefault().register(this);
        requestMoviesFromServer();
        requestScreeningTimes();

        importImageBtn.setOnAction(event -> importImage());

        SpinnerValueFactory<String> valueFactory2 =
                new SpinnerValueFactory.ListSpinnerValueFactory<String>(ScreeningType);
        screeningTypeSpinner.setValueFactory(valueFactory2);

        screeningTypeSpinner.setOnMouseClicked(event -> {      // מאזין לבחירת ז'אנר
            changeScreeningType();
        });

        cinemaComboBox.getItems().addAll("Haifa Cinema", "Tel Aviv Cinema", "Eilat Cinema", "Karmiel Cinema", "Jerusalem Cinema");

        cinemaComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            updateHallComboBox(newSelection); // ×§×¨××× ×××ª××× ×©××¢××× ×ª ××ª ×ª×××ª ×××××¨× ×©× ×××××××ª
        });

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
    }

    private byte[] imageData;

    @FXML
    private void importImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                // Convert the image to a byte array
                imageData = Files.readAllBytes(selectedFile.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void changeScreeningType() {
        if (Objects.equals(screeningTypeSpinner.getValue(), "In The Cinema")) {
            cinemaLabel.setVisible(true);
            cinemaComboBox.setVisible(true);
            hallComboBox.setVisible(true);
            hallLabel.setVisible(true);
            screeningTimeTableCinema.setVisible(true);
            linkLabel.setVisible(false);
            linkText.setVisible(false);
            screeningTimeTableLink.setVisible(false);
        } else if (Objects.equals(screeningTypeSpinner.getValue(), "Link")) {
            cinemaLabel.setVisible(false);
            cinemaComboBox.setVisible(false);
            screeningTimeTableCinema.setVisible(false);
            hallComboBox.setVisible(false);
            hallLabel.setVisible(false);
            linkLabel.setVisible(true);
            linkText.setVisible(true);
            screeningTimeTableLink.setVisible(true);
        }
    }

    @FXML
    void showScreening(ActionEvent event) {
        screenings.setVisible(true);
        changeScreeningType();
    }

    @FXML
    void hideScreening(ActionEvent actionEvent) {
        screenings.setVisible(false);
        addHoursText.clear();
        addDateText.setValue(null);
    }

    @FXML
    private TableColumn<Screening, String> dateColumn;

    @FXML
    private TableColumn<Screening, String> hourColumn;

    @FXML
    private TableColumn<Screening, String> dateColumnCinema;

    @FXML
    private TableColumn<Screening, String> hourColumnCinema;

    @FXML
    private TableColumn<Screening, String> cinemaColumn;

    @FXML
    private Button importImageBtn;

    @FXML
    void AddMovie(ActionEvent event) throws IOException {
        String screeningType = screeningTypeSpinner.getValue();

        String movieNameEng = engName.getText();
        String movieNameHeb = hebName.getText();
        String directorName = director.getText();
        String mainActors = actors.getText();
        String movieGenre = genre.getText();
        String descriptionText = description.getText();

        //   String image = imagePath.getText();  //////////////////////////////////////////
        String length = movieLength.getText();

        String link = linkText.getText();
        ComboBox<String> cinemas = cinemaComboBox;
        String hours = addHoursText.getText();
        LocalDate date = addDateText.getValue();
        TableView<?> screeningCinema = screeningTimeTableCinema;
        TableView<?> screeningLink = screeningTimeTableLink;

        List<String> errorMessages = new ArrayList<>();
        // Reset field styles before validation
        resetFieldStyles();
        // Validate each field and track errors
        if (movieNameEng.isEmpty()) {
            highlightFieldError(engName);
            errorMessages.add("Please enter a movie name(ENG).");
        }
        if (movieNameHeb.isEmpty()) {
            highlightFieldError(hebName);
            errorMessages.add("Please enter a movie name(HEB).");
        }
        if (directorName.isEmpty()) {
            highlightFieldError(director);
            errorMessages.add("Please enter a director name.");
        }
        if (mainActors.isEmpty()) {
            highlightFieldError(actors);
            errorMessages.add("Please the main actor(s).");
        }
        if (movieGenre.isEmpty()) {
            highlightFieldError(genre);
            errorMessages.add("Please enter a movie genre.");
        }
        if (descriptionText.isEmpty()) {
            highlightFieldError(description);
            errorMessages.add("Please enter the movie description.");
        }
        if (imageData == null) {
            highlightFieldError(importImageBtn);
            errorMessages.add("Please choose an image.");
        }
        if (length.isEmpty() || !validateMovieLength()) {
            highlightFieldError(movieLength);
            errorMessages.add("Please enter a valid movie length in this format %dh %dm.");
        }
        if(year.getText().isEmpty() || !validateReleaseYear()) {
            highlightFieldError(year);
            errorMessages.add("Please enter a valid release year.");
        }
        if(screeningTypeSpinner.getValue().equals("In The Cinema")) {
            if(screeningTimeTableCinema.getItems().isEmpty() && cinemas.getValue() == null) {
                highlightFieldError(addScreeningBtn);
                highlightFieldError(cinemaComboBox);
                errorMessages.add("Please choose a cinema.");
            }
            if(screeningTimeTableCinema.getItems().isEmpty() && (hours.isEmpty() || !validateHour())) {
                highlightFieldError(addScreeningBtn);
                highlightFieldError(addHoursText);
                errorMessages.add("Please enter a valid hour in this format HH:MM.");
            }
            if(screeningTimeTableCinema.getItems().isEmpty() && date == null) {
                highlightFieldError(addScreeningBtn);
                highlightFieldError(addDateText);
                errorMessages.add("Please choose a date.");
            }
        }
        if(screeningTypeSpinner.getValue().equals("Link")) {
            if(screeningTimeTableLink.getItems().isEmpty() && link.isEmpty()) {
                highlightFieldError(addScreeningBtn);
                highlightFieldError(linkText);
                errorMessages.add("Please enter a link.");
            }
            if(screeningTimeTableLink.getItems().isEmpty() && (hours.isEmpty() || !validateHour())) {
                highlightFieldError(addScreeningBtn);
                highlightFieldError(addHoursText);
                errorMessages.add("Please enter a valid hour in this format HH:MM.");
            }
            if(screeningTimeTableLink.getItems().isEmpty() && date == null) {
                highlightFieldError(addScreeningBtn);
                highlightFieldError(addDateText);
                errorMessages.add("Please choose a date.");
            }
        }

        // Display error message based on the number of errors
        if (!errorMessages.isEmpty()) {
            String alertMessage;
            if (errorMessages.size() == 1) {
                alertMessage = errorMessages.get(0);
            } else {
                alertMessage = "Multiple errors detected. Please review the highlighted fields and correct the issues.";
            }

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validation Errors");
                alert.setHeaderText(null);
                alert.setContentText(alertMessage);
                alert.show();
            });
            return; // Stop processing if validation fails
        }

        resetFieldStyles();

        // byte[] image1 = loadImageFromFile(imageData);

        int movieYear =  Integer.parseInt(year.getText());

        if(screeningType.equals("In The Cinema")) {
            Movie movie = new Movie(30, movieNameEng, movieNameHeb, directorName, movieYear, imageData, movieGenre, descriptionText, mainActors, length);
            NewMessage msg = new NewMessage(movie, "addCinemaMovie", movieBranches, movieCinemaDateTimes, movieCinemaHalls); ////////////////////////add halls
            SimpleClient.getClient().sendToServer(msg);
        } else if(screeningType.equals("Link")) {
            HomeMovie homeMovie = new HomeMovie(30, movieNameEng, movieNameHeb, directorName, movieYear, imageData, link, movieGenre, descriptionText, mainActors, length);
            for(Screening x: movieLinkScreenings){
                homeMovie.addScreening(x.getScreeningTime(), x.getBranch(), null); /////////////////////////////////////////
            }
            NewMessage msg = new NewMessage(homeMovie, "addHomeMovie");
            SimpleClient.getClient().sendToServer(msg);
        }
        engName.clear();
        hebName.clear();
        director.clear();
        actors.clear();
        genre.clear();
        description.clear();
        //  imagePath.clear();
        imageData = null;
        linkText.clear();
        year.clear();
        movieLength.clear();
        addHoursText.clear();
        screeningTimeTableLink.getItems().clear();
        screeningTimeTableCinema.getItems().clear();
        addDateText.setValue(null);
      //  cinemaComboBox.setValue(null);
        hallComboBox.setValue(null);
        addScreeningBtn.getStyleClass().remove("error");
    }


    private void updateHallComboBox(String branchName) {
        List<Integer> halls = getHallsByBranch(branchName);
        hallComboBox.setItems(FXCollections.observableArrayList(halls));
        hallComboBox.getSelectionModel().clearSelection(); // × ××§×× ××××¨× ×§××××ª
    }



    @FXML
    void addTime(ActionEvent event) {

        addHoursText.getStyleClass().remove("error");
        addScreeningBtn.getStyleClass().remove("error");
        addDateText.getStyleClass().remove("error");
        cinemaComboBox.getStyleClass().remove("error");
        hallComboBox.getStyleClass().remove("error");

        if (!validateHour()) {
            highlightFieldError(addHoursText);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please enter a valid hour in this format HH:MM.");
                alert.show();
            });
        } else if (addDateText.getValue() == null) {
            highlightFieldError(addDateText);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please choose a date.");
                alert.show();
            });
        } else if (screeningTypeSpinner.getValue().equals("In The Cinema") && cinemaComboBox.getValue() == null) {
            highlightFieldError(cinemaComboBox);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please choose a cinema.");
                alert.show();
            });
        } else if (screeningTypeSpinner.getValue().equals("In The Cinema") && hallComboBox.getValue() == null) {
            highlightFieldError(hallComboBox);
            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Please choose a hall.");
                alert.show();
            });
        } else {
            String hour1 = addHoursText.getText();
            String[] parts = hour1.split(":");
            String hours = parts[0];
            String minutes = parts[1];
            LocalDate date1 = addDateText.getValue();


            String cinema = cinemaComboBox.getValue();
            LocalDateTime dateTime1 = LocalDateTime.of(date1.getYear(), date1.getMonth(), date1.getDayOfMonth(), Integer.parseInt(hours), Integer.parseInt(minutes));


            if (screeningTypeSpinner.getValue().equals("In The Cinema")) {
                int selectedHallId = hallComboBox.getValue(); //save it
                if (!isScreeningTimeOverlapping(dateTime1, selectedHallId, cinema)) {
                    Branch branch = new Branch();
                    branch.setName(cinema);

                    Hall hall = new Hall();
                    hall.setId(selectedHallId);

                    Screening screening = new Screening(dateTime1, null, branch,hall);

                    movieBranches.add(branch);
                    movieCinemaDateTimes.add(dateTime1);
                    movieCinemaHalls.add(hall);
                    movieCinemaScreenings.add(screening);
                    screeningsForTableCinema.add(screening);

                    // Set up the date column
                    dateColumnCinema.setCellValueFactory(cellData -> {
                        LocalDateTime dateTime = cellData.getValue().getScreeningTime();
                        // Extract and format the date
                        String date = dateTime.toLocalDate().toString();
                        return new SimpleStringProperty(date);
                    });

                    // Set up the hour column
                    hourColumnCinema.setCellValueFactory(cellData -> {
                        LocalDateTime dateTime = cellData.getValue().getScreeningTime();
                        // Extract and format the hour
                        String hour = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                        return new SimpleStringProperty(hour);
                    });

                    cinemaColumn.setCellValueFactory(cellData -> {
                        Branch branch1 = cellData.getValue().getBranch();
                        // Extract and format the hour
                        String cinema1 = branch1.getName();
                        return new SimpleStringProperty(cinema1);
                    });

                    // Connect the table to the data list
                    screeningTimeTableCinema.setItems(screeningsForTableCinema);

                    addHoursText.clear();
                    addDateText.setValue(null);
                    //cinemaComboBox.setValue(null);
                    hallComboBox.setValue(null);

                } else {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setContentText("Screening time overlaps with another screening in the selected hall.");
                        alert.show();
                    });}

            } else if (screeningTypeSpinner.getValue().equals("Link")) {
                Screening screening = new Screening(dateTime1, null, null);
                movieLinkScreenings.add(screening);
                screeningsForTableLink.add(screening);

                // Set up the date column
                dateColumn.setCellValueFactory(cellData -> {
                    LocalDateTime dateTime = cellData.getValue().getScreeningTime();
                    // Extract and format the date
                    String date = dateTime.toLocalDate().toString();
                    return new SimpleStringProperty(date);
                });

                // Set up the hour column
                hourColumn.setCellValueFactory(cellData -> {
                    LocalDateTime dateTime = cellData.getValue().getScreeningTime();
                    // Extract and format the hour
                    String hour = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"));
                    return new SimpleStringProperty(hour);
                });
                // Connect the table to the data list
                screeningTimeTableLink.setItems(screeningsForTableLink);

                addHoursText.clear();
                addDateText.setValue(null);
                //cinemaComboBox.setValue(null);
                hallComboBox.setValue(null);
            }

            addHoursText.getStyleClass().remove("error");
            addScreeningBtn.getStyleClass().remove("error");
            addDateText.getStyleClass().remove("error");
            cinemaComboBox.getStyleClass().remove("error");
            hallComboBox.getStyleClass().remove("error");
        }
    }


    private List<Integer> getHallsByBranch(String branchName) {

        if (branchName.equals("Haifa Cinema")) {
            return List.of(2, 3 ,5 ,23,28);
        } else if (branchName.equals("Tel Aviv Cinema")) {
            return List.of(6,9,15,19,20);
        }
        else if (branchName.equals("Eilat Cinema")) {
            return List.of(12,13,14,24,25,29);
        }
        else if (branchName.equals("Karmiel Cinema")) {
            return List.of(8,10,11,17,18,21);
        }
        else if (branchName.equals("Jerusalem Cinema")) {
            return List.of(1,4,7,16,22,26,27);
        }
        else {
            return List.of();
        }
    }

    private boolean isScreeningTimeOverlapping(LocalDateTime plannedTime, int hallId, String cinemaName) {
        final int SCREENING_DURATION = 150; // Duration of a screening in minutes

        allScreenings.removeIf(screening -> screening.getHall()==null);

        boolean isOverlapping = allScreenings.stream()
                .filter(screening -> {
                    boolean sameHall = screening.getHall().getId() == hallId;
                    boolean sameCinema = screening.getBranch().getName().equals(cinemaName);
                    return sameHall && sameCinema;
                })
                .anyMatch(screening -> {
                    LocalDateTime existingStart = screening.getScreeningTime();
                    LocalDateTime existingEnd = existingStart.plusMinutes(SCREENING_DURATION);
                    // Check overlap
                    boolean overlap = !plannedTime.plusMinutes(SCREENING_DURATION).isBefore(existingStart) && !plannedTime.isAfter(existingEnd);
                    return overlap;
                });

        boolean isOverlapping2 = movieCinemaScreenings.stream()
                .filter(screening -> {
                    boolean sameHall = screening.getHall().getId() == hallId;
                    boolean sameCinema = screening.getBranch().getName().equals(cinemaName);
                    return sameHall && sameCinema;
                })
                .anyMatch(screening -> {
                    LocalDateTime existingStart = screening.getScreeningTime();
                    LocalDateTime existingEnd = existingStart.plusMinutes(SCREENING_DURATION);
                    // Check overlap
                    boolean overlap = !plannedTime.plusMinutes(SCREENING_DURATION).isBefore(existingStart) && !plannedTime.isAfter(existingEnd);
                    return overlap;
                });
        return isOverlapping || isOverlapping2;
       // return isOverlapping;
    }



    // Method to get screenings for a specific branch and hall
    private List<Screening> getScreeningsForBranchAndHall(int branchId, int hallId) {
        List<Screening> filteredScreenings = allScreenings.stream()
                .filter(screening -> screening.getBranch() != null &&
                        screening.getHall() != null &&
                        screening.getBranch().getId() == branchId &&
                        screening.getHall().getId() == hallId)
                .collect(Collectors.toList());
        return filteredScreenings;
    }




    public void requestScreeningTimes() {
        try {
            NewMessage requestMessage = new NewMessage("allScreeningTimesRequest");
            SimpleClient.getClient().sendToServer(requestMessage);
        } catch (IOException e) {
            System.err.println("Failed to send screening times request: " + e.getMessage());
        }
    }

    @Subscribe
    public void onScreeningTimesReceived(UpdateScreeningTimesEvent event) {
        Platform.runLater(() -> {
            allScreenings.clear();
            allScreenings.addAll(event.getScreenings());
        });
    }


    private void resetFieldStyles() {
        Platform.runLater(() -> {
            engName.getStyleClass().remove("error");
            hebName.getStyleClass().remove("error");
            director.getStyleClass().remove("error");
            actors.getStyleClass().remove("error");
            genre.getStyleClass().remove("error");
            description.getStyleClass().remove("error");
            // imagePath.getStyleClass().remove("error");
            importImageBtn.getStyleClass().remove("error");
            linkText.getStyleClass().remove("error");
            year.getStyleClass().remove("error");
            movieLength.getStyleClass().remove("error");
            cinemaComboBox.getStyleClass().remove("error");
            addHoursText.getStyleClass().remove("error");
            addScreeningBtn.getStyleClass().remove("error");
            addDateText.getStyleClass().remove("error");
        });
    }

    private void highlightFieldError(Control control) {
        Platform.runLater(() -> {
            if (!control.getStyleClass().contains("error")) {
                control.getStyleClass().add("error");
            }});
    }

    private boolean validateMovieLength() {
        String length = movieLength.getText().trim();
        // The regex enforces the format "Xh Ym" where X is one or more digits and Y is 0 to 59
        String regex = "^\\d+h \\d+m$"; // Both parts must be present, enforce single space

        if (!Pattern.matches(regex, length)) {
            return false;
        }

        // Split the input into parts and parse
        String[] parts = length.split(" ");
        int hours = 0;
        int minutes = 0;

        try {
            if (parts[0].endsWith("h")) {
                hours = Integer.parseInt(parts[0].substring(0, parts[0].length() - 1));
            }

            if (parts[1].endsWith("m")) {
                minutes = Integer.parseInt(parts[1].substring(0, parts[1].length() - 1));
                if (minutes < 0 || minutes >= 60) {
                    return false; // Invalid minutes range
                }
            }
        } catch (NumberFormatException e) {
            return false;
        }

        // Return true if both parts are valid
        return true;
    }

    private boolean validateReleaseYear() {
        String movieyear = year.getText().trim(); // Assume movieYear is a TextField or similar component

        // Regex to check if the year is a four-digit number
        String regex = "^\\d{4}$";

        if (!Pattern.matches(regex, movieyear)) {
            return false; // Not a valid four-digit number
        }

        // Convert the year to an integer
        try {
            int releaseYear = Integer.parseInt(movieyear);

            // Define a plausible range for the release year
            int currentYear = java.time.Year.now().getValue();
            if (releaseYear >= 1888 && releaseYear <= currentYear + 5) {
                return true; // The year is within a plausible range
            }
        } catch (NumberFormatException e) {
            return false; // In case parsing the year fails, though unlikely given the regex
        }

        return false; // Year was not within the plausible range
    }

    private boolean validateHour() {
        String time = addHoursText.getText().trim(); // Assume hourField is a TextField or similar component

        // Regex to check if the input is a valid time in 24-hour format HH:MM
        String regex = "^(?:[01]\\d|2[0-3]):[0-5]\\d$";

        // Check the format using regex
        return Pattern.matches(regex, time);
    }

    private static byte[] loadImageFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        try (InputStream inputStream = new FileInputStream(file)) {
            return inputStream.readAllBytes();
        }
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
    public static void switchToHomePage() throws IOException {
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
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        Platform.runLater(() -> {
            LoginPage.employee1 = null;
            try {
                switchToHomePage();
            }
            catch (IOException e) {}
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
                                }}}};}});
            movieTable.setItems(movies);});
    }

    @Subscribe
    public void onUpdateHomeMoviesEvent(UpdateHomeMoviesEvent event) {}
}