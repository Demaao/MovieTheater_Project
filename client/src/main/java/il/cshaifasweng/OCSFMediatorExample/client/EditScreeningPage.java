package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EditScreeningPage {
    @FXML private DatePicker addDateText;
    @FXML private TextField addHoursText;

    @FXML private TableView<Movie> complaintTable;
    @FXML private TableColumn<Movie, String> nameEngColumn;
    @FXML private TableColumn<Movie, String> TypeColumn;
    @FXML private TableColumn<Movie, String> ScreeningTimes;
    @FXML private ComboBox<String> chooseMovieBox;
    @FXML private ComboBox<String> chooseCinemaBox;
    @FXML private ComboBox<String> removeComboBox;
    @FXML private ComboBox<String> editComboBox;
    @FXML private TextField editNewHoursText;
    @FXML private DatePicker editNewDateText;
    @FXML private Button editTimeBtn;


    private List<Movie> allMovies;
    private String savedMovieSelection;
    private String savedCinemaSelection;


    @FXML public void initialize() {
        EventBus.getDefault().register(this);
        requestMoviesFromServer();
        setupComboBoxes();
        setupDatePickers();
    }

    private void setupComboBoxes() {

        chooseMovieBox.setOnAction(event -> {
            updateCinemaOptions();
            updateTableBasedOnSelection();
            updateRemoveComboBox();
            resetDateTimeInputs();
            updateEditComboBox();
        });

        chooseCinemaBox.setOnAction(event -> {
            updateTableBasedOnCinemaSelection();
            updateEditComboBox();
        });
    }

    private void setupDatePickers() {
        final Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item.isBefore(LocalDate.now())) {
                    setDisable(true);
                }
            }
        };
        //if the date is before today then it cannot be selected
        addDateText.setDayCellFactory(dayCellFactory);
        editNewDateText.setDayCellFactory(dayCellFactory);
    }

    private void resetDateTimeInputs() {
        addDateText.setValue(null);
        addHoursText.clear();
    }

    @Subscribe
    public void onUpdateMoviesEvent(UpdateMoviesEvent event) {
        saveSelections();
        Platform.runLater(() -> {
            allMovies = event.getMovies().stream().filter(movie -> !(movie instanceof SoonMovie)).collect(Collectors.toList());
            chooseMovieBox.getItems().clear();
            chooseMovieBox.getItems().addAll(allMovies.stream().map(Movie::getEngtitle).collect(Collectors.toList()));
            restoreSelections();
            updateRemoveComboBox();
        });
    }

    private void updateCinemaOptions() {
        Movie selectedMovie = getSelectedMovie();
        if (selectedMovie != null && !(selectedMovie instanceof HomeMovie)) {
            chooseCinemaBox.setVisible(true);
            chooseCinemaBox.getItems().setAll(selectedMovie.getBranches().stream().map(Branch::getName).collect(Collectors.toList()));
        } else {
            chooseCinemaBox.setVisible(false);
        }
    }

    private void updateTableBasedOnCinemaSelection() {
        updateTable(getSelectedMovie(), chooseCinemaBox.getValue());
        updateRemoveComboBox();
    }

    private void updateTable(Movie movie, String cinemaName) {
        Platform.runLater(() -> {
            complaintTable.getItems().clear();
            if (movie != null) {
                List<Screening> screenings = new ArrayList<>(movie.getScreenings());
                if (cinemaName != null) {
                    screenings = screenings.stream().filter(s -> s.getBranch() != null && s.getBranch().getName().equals(cinemaName)).collect(Collectors.toList());
                }
                if (!screenings.isEmpty()) {
                    final List<Screening> finalScreenings = screenings;
                    complaintTable.getItems().add(movie);
                    nameEngColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEngtitle()));
                    TypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(movie instanceof HomeMovie ? "HOME MOVIE" : "CINEMA MOVIE"));
                    ScreeningTimes.setCellValueFactory(cellData -> new SimpleStringProperty(finalScreenings.stream().map(s -> s.getScreeningTime().toString()).collect(Collectors.joining("\n"))));
                    complaintTable.refresh();
                }
            }
        });
    }

    private void updateTableBasedOnSelection() {
        Movie selectedMovie = getSelectedMovie();
        String cinemaName = chooseCinemaBox.isVisible() ? chooseCinemaBox.getValue() : null;
        updateTable(selectedMovie, cinemaName);
    }

    @FXML
    void addTime(ActionEvent event) {
        saveSelections();

        try {
            Movie selectedMovie = getSelectedMovie();
            if (selectedMovie == null) {
                showAlert("Please select a movie.");
                return;
            }

            LocalDateTime dateTime;
            try {
                dateTime = LocalDateTime.of(addDateText.getValue(), LocalTime.parse(addHoursText.getText()));
            } catch (DateTimeParseException e) {
                showAlert("Invalid date or time format. Please use the correct format.");
                return;
            }

            // if it is a home movie, we will create a screening without the need for a branch or hall
            if (selectedMovie instanceof HomeMovie) {
                // Check if a  screenung time already exists at that tim
                boolean screeningExists = selectedMovie.getScreenings().stream()
                        .anyMatch(screening -> screening.getScreeningTime().equals(dateTime));

                if (screeningExists) {
                    showAlert("This screening time already exists. Please choose a different time.");
                    return;
                }

                // Create a newr time and add it to the movie
                ScreeningData data = new ScreeningData(selectedMovie.getId(), null, dateTime);
                NewMessage addMessage = new NewMessage(data, "addScreening");
                SimpleClient.getClient().sendToServer(addMessage);

                selectedMovie.addScreening(dateTime, null, null);

            } else {
                //if we have cinema movie
                String cinemaName = chooseCinemaBox.getValue();
                Branch selectedBranch = cinemaName != null ? getSelectedBranch(selectedMovie, cinemaName) : null;
                if (selectedBranch == null) {
                    showAlert("Please select a cinema branch.");
                    return;
                }

                Hall selectedHall = findExistingHall(selectedMovie, selectedBranch);
                if (selectedHall == null) {
                    showAlert("No hall found for the selected movie in this branch.");
                    return;
                }

                boolean isConflict = selectedMovie.getScreenings().stream()
                        .anyMatch(screening -> screening.getBranch().equals(selectedBranch)
                                && screening.getHall().equals(selectedHall)
                                && areTimesOverlapping(screening.getScreeningTime(), dateTime));

                if (isConflict) {
                    showAlert("This screening time conflicts with another screening. Please choose a different time.");
                    return;
                }

                ScreeningData data = new ScreeningData(selectedMovie.getId(), selectedBranch.getId(), selectedHall.getId(), dateTime);
                NewMessage addMessage = new NewMessage(data, "addScreening");
                SimpleClient.getClient().sendToServer(addMessage);

                selectedMovie.addScreening(dateTime, selectedBranch, selectedHall);
            }

            updateTable(selectedMovie, chooseCinemaBox.getValue());
            updateRemoveComboBox();

        } catch (Exception e) {
        } finally {
            restoreSelections();
        }
    }


    private Hall findExistingHall(Movie movie, Branch branch) {
        return movie.getScreenings().stream()
                .filter(screening -> screening.getBranch().equals(branch))
                .map(Screening::getHall) // Get the hall of each screening
                .findFirst() // Assuming all screenings for the same movie in the same branch are in the same hall
                .orElse(null);
    }



    private Branch getSelectedBranch(Movie movie, String branchName) {
        if (movie != null && branchName != null) {

            return movie.getBranches().stream()
                    .filter(branch -> branch.getName().equals(branchName))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private Movie getSelectedMovie() {
        String title = chooseMovieBox.getValue();
        return allMovies.stream().filter(m -> m.getEngtitle().equals(title)).findFirst().orElse(null);
    }
    private void saveSelections() {
        savedMovieSelection = chooseMovieBox.getValue();
        savedCinemaSelection = chooseCinemaBox.getValue();
    }

    private void restoreSelections() {
        chooseMovieBox.setValue(savedMovieSelection);
        if (savedCinemaSelection != null && chooseCinemaBox.getItems().contains(savedCinemaSelection)) {
            chooseCinemaBox.setValue(savedCinemaSelection);
        }
    }

    private void showAlert(String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Message");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
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


    @FXML
    void removeTime(ActionEvent event) {
        saveSelections();
        String cinemaName = chooseCinemaBox.getValue();

        try {
            Movie selectedMovie = getSelectedMovie();
            if (selectedMovie == null) {
                showAlert("No movie selected.");
                return;
            }
            // Retrieves the selected time string from the ComboBox and converts it to a LocalDateTime object
            String selectedTimeStr = removeComboBox.getValue();
            LocalDateTime selectedTime = LocalDateTime.parse(selectedTimeStr);
            Branch selectedBranch = cinemaName != null ? getSelectedBranch(selectedMovie, cinemaName) : null;

            if(!(selectedMovie instanceof HomeMovie) && cinemaName == null){
                showAlert("Please choose a cinema.");
                return;
            }

            // Handles removal based on the type of movie (Home or Cinema)
            if(selectedMovie instanceof HomeMovie){
                ScreeningData data = new ScreeningData(selectedMovie.getId(), null, selectedTime);
                NewMessage removeMessage = new NewMessage(data, "removeScreening");
                SimpleClient.getClient().sendToServer(removeMessage);
            } else {
                ScreeningData data = new ScreeningData(selectedMovie.getId(), selectedBranch != null ? selectedBranch.getId() : null, selectedTime);
                NewMessage removeMessage = new NewMessage(data, "removeScreening");
                SimpleClient.getClient().sendToServer(removeMessage);
            }
            // Removes the screening from the local data
            selectedMovie.getScreenings().removeIf(screening ->
                    screening.getScreeningTime().equals(selectedTime) && (screening.getBranch() == selectedBranch));
            updateTable(selectedMovie, cinemaName);

            updateRemoveComboBox();
            removeComboBox.setValue(null);


        } catch (Exception e) {
            showAlert("Invalid time format or selection.");
        } finally {
            restoreSelections();
        }
    }


    private void updateRemoveComboBox() {
        Platform.runLater(() -> {
            Movie selectedMovie = getSelectedMovie();
            String cinemaName = chooseCinemaBox.isVisible() ? chooseCinemaBox.getValue() : null;
            if (selectedMovie != null) {
                List<Screening> screenings = new ArrayList<>(selectedMovie.getScreenings());
                if (cinemaName != null) {
                    screenings = screenings.stream()
                            .filter(s -> s.getBranch() != null && s.getBranch().getName().equals(cinemaName))
                            .collect(Collectors.toList());
                }
                removeComboBox.getItems().clear();
                removeComboBox.getItems().addAll(screenings.stream()
                        .map(screening -> screening.getScreeningTime().toString())
                        .collect(Collectors.toList()));
            }
        });
    }


    private void resetEditFields() {
        editNewDateText.setValue(null);
        editNewHoursText.clear();
        editComboBox.getSelectionModel().clearSelection();
    }


    @FXML
    void editTime(ActionEvent event) {
        saveSelections();

        try {
            Movie selectedMovie = getSelectedMovie();
            if (selectedMovie == null) {
                showAlert("Please select a movie to edit its screening time.");
                return;
            }

            LocalDateTime newDateTime;
            try {
                newDateTime = LocalDateTime.of(editNewDateText.getValue(), LocalTime.parse(editNewHoursText.getText()));
            } catch (DateTimeParseException e) {
                showAlert("Invalid new date or time format. Please use the correct format.");
                return;
            }

            if (selectedMovie instanceof HomeMovie) {
                // The handling of home movies: there is no need to check branches and halls
                String selectedTimeStr = editComboBox.getValue();
                LocalDateTime selectedTime = LocalDateTime.parse(selectedTimeStr);

                Screening screeningToUpdate = selectedMovie.getScreenings().stream()
                        .filter(s -> s.getScreeningTime().equals(selectedTime))
                        .findFirst().orElse(null);

                if (screeningToUpdate == null) {
                    showAlert("Selected screening time does not exist. Please refresh and try again.");
                    return;
                }

                screeningToUpdate.setScreeningTime(newDateTime);
                ScreeningData updateData = new ScreeningData(selectedMovie.getId(), null, newDateTime); //  there is no need to check branches and halls
                NewMessage updateMessage = new NewMessage(updateData, "editScreening");
                SimpleClient.getClient().sendToServer(updateMessage);

            } else {
                //if we want to edit time for cinema movie
                String cinemaName = chooseCinemaBox.getValue();
                Branch selectedBranch = cinemaName != null ? getSelectedBranch(selectedMovie, cinemaName) : null;

                String selectedTimeStr = editComboBox.getValue();
                LocalDateTime selectedTime = LocalDateTime.parse(selectedTimeStr);

                boolean screeningExists = selectedMovie.getScreenings().stream()
                        .filter(screening -> screening.getBranch().equals(selectedBranch) && !screening.getScreeningTime().equals(selectedTime))
                        .anyMatch(screening -> areTimesOverlapping(screening.getScreeningTime(), newDateTime));

                if (screeningExists) {
                    showAlert("This new screening time conflicts with another existing screening. Choose a different time.");
                    return;
                }

                Screening screeningToUpdate = selectedMovie.getScreenings().stream()
                        .filter(s -> s.getScreeningTime().equals(selectedTime) && s.getBranch().equals(selectedBranch))
                        .findFirst().orElse(null);

                if (screeningToUpdate == null) {
                    showAlert("Selected screening time does not exist. Please refresh and try again.");
                    return;
                }

                screeningToUpdate.setScreeningTime(newDateTime);
                ScreeningData updateData = new ScreeningData(selectedMovie.getId(), selectedBranch != null ? selectedBranch.getId() : null, newDateTime);
                NewMessage updateMessage = new NewMessage(updateData, "editScreening");
                SimpleClient.getClient().sendToServer(updateMessage);
            }

            updateTable(selectedMovie, chooseCinemaBox.getValue());
            updateRemoveComboBox();
            resetEditFields();

            showAlert("Screening time updated successfully.");

        } catch (Exception e) {

        } finally {
            restoreSelections();
        }
    }

    // Helper method to check if two times overlap
    private boolean areTimesOverlapping(LocalDateTime existingTime, LocalDateTime newTime) {
        LocalDateTime endTime = existingTime.plusMinutes(150); // 2.5 hours in minutes
        return !newTime.plusMinutes(150).isBefore(existingTime) && !newTime.isAfter(endTime);
    }


    private void updateEditComboBox() {
        Movie selectedMovie = getSelectedMovie();
        String cinemaName = chooseCinemaBox.isVisible() ? chooseCinemaBox.getValue() : null;
        // Proceeds only if a movie is selected
        if (selectedMovie != null) {
            List<Screening> screenings = new ArrayList<>(selectedMovie.getScreenings());
            if (cinemaName != null) {
                screenings = screenings.stream()
                        .filter(s -> s.getBranch() != null && s.getBranch().getName().equals(cinemaName))
                        .collect(Collectors.toList());
            }
            editComboBox.getItems().clear();
            // Adds the filtered screenings to the removeComboBox, transforming each screening time to a string
            editComboBox.getItems().addAll(screenings.stream()
                    .map(screening -> screening.getScreeningTime().toString())
                    .collect(Collectors.toList()));
        }
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

    @Subscribe
    public void onUpdateHomeMoviesEvent(UpdateHomeMoviesEvent event) {}

    @Subscribe
    public void onUpdateScreeningEvent(UpdateScreeningTimesEvent event) {}
}