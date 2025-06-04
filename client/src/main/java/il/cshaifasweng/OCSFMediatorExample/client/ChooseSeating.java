package il.cshaifasweng.OCSFMediatorExample.client;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class ChooseSeating {
    private Hall hall;//An instance hall class, representing the specific hall where the screening is taking place.
    public static Screening screening;//A static variable representing the screening event, including details about the movie, time, and seat availability.
    private int counter = 0;//Tracks the number of seats currently selected by the user.
    private String pickedSeats;//A String that stores a description of the seats chosen by the user.

    @FXML // fx:id="gridSeats"
    private GridPane gridSeats;

    //s1-s42 represent individual seat icons that user can select
    @FXML
    private MaterialIconView s1;

    @FXML
    private MaterialIconView s2;

    @FXML
    private MaterialIconView s3;

    @FXML
    private MaterialIconView s4;

    @FXML
    private MaterialIconView s5;

    @FXML
    private MaterialIconView s6;

    @FXML
    private MaterialIconView s7;

    @FXML
    private MaterialIconView s8;

    @FXML
    private MaterialIconView s9;

    @FXML
    private MaterialIconView s10;

    @FXML
    private MaterialIconView s11;

    @FXML
    private MaterialIconView s12;

    @FXML
    private MaterialIconView s13;

    @FXML
    private MaterialIconView s14;

    @FXML
    private MaterialIconView s15;

    @FXML
    private MaterialIconView s16;

    @FXML
    private MaterialIconView s17;

    @FXML
    private MaterialIconView s18;

    @FXML
    private MaterialIconView s19;

    @FXML
    private MaterialIconView s20;

    @FXML
    private MaterialIconView s21;

    @FXML
    private MaterialIconView s22;

    @FXML
    private MaterialIconView s23;

    @FXML
    private MaterialIconView s24;

    @FXML
    private MaterialIconView s25;
    @FXML
    private MaterialIconView s26;

    @FXML
    private MaterialIconView s27;

    @FXML
    private MaterialIconView s28;

    @FXML
    private MaterialIconView s29;

    @FXML
    private MaterialIconView s30;

    @FXML
    private MaterialIconView s31;

    @FXML
    private MaterialIconView s32;

    @FXML
    private MaterialIconView s33;

    @FXML
    private MaterialIconView s34;

    @FXML
    private MaterialIconView s35;

    @FXML
    private MaterialIconView s36;

    @FXML
    private MaterialIconView s37;

    @FXML
    private MaterialIconView s38;

    @FXML
    private MaterialIconView s39;

    @FXML
    private MaterialIconView s40;

    @FXML
    private MaterialIconView s41;

    @FXML
    private MaterialIconView s42;

    @FXML
    private Button bookSeat;

    @FXML
    private Label availableSeats;

    @FXML
    private Label chosenNum;

    @FXML // fx:id="rowsGrid"
    private GridPane rowsGrid; // Value injected by FXMLLoader

    @FXML // fx:id="rowsGrid"
    private GridPane colsGrids; // Value injected by FXMLLoader

    @FXML   //back to screening page in case "back button was pressed
    private Button backToPrevious;

    @FXML // fx:id="movieInformation"
    private Label movieInformation; // Value injected by FXMLLoader

    @FXML
    private Label confirmation;


    @FXML // fx:id="warning"
    private Label warning; // Value injected by FXMLLoader

    @FXML
    void ChooseSeats(MouseEvent event) {
        confirmation.setVisible(false);
        if (((Node) event.getSource()).getStyle()
                .equals("-fx-fill:#dc48d5; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;")) {
            ((Node) event.getSource()).setStyle(
                    "-fx-fill:#ffcd81; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;");
            counter++;
            chosenNum.setText("Number of chosen seats: " +counter);
            warning.setVisible(false);

        } else if (((Node) event.getSource()).getStyle()
                .equals("-fx-fill:#e7cbe3; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;")) {
            warning.setVisible(true);


        } else if (((Node) event.getSource()).getStyle()
                .equals("-fx-fill:#ffcd81; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;")) {
            ((Node) event.getSource()).setStyle(
                    "-fx-fill:#dc48d5; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;");
            counter--;
            chosenNum.setText("Number of chosen seats: " + counter);
            warning.setVisible(false);
        }
    }

    public static int[] arr1;

    @FXML
    void bookChosenSeats() {
        int counter1 = 0;
        arr1 = new int[counter];   ///////////////////////////////////////////////////
        String[] arr2 = new String[counter];
        int seatcount = 0;
        pickedSeats = "";
        if (isOdd(hall.getColsNum())) {

            for (int k = 0; k < hall.getRows(); k++) {
                int i = 3 - (hall.getCols() - 1) / 2;
                int j = 3 + (hall.getCols() - 1) / 2;
                while (i <= j && seatcount < hall.getSeatsNum()) {
                    if (gridSeats.getChildren().get(i + k * 7).getStyle()
                            .equals("-fx-fill:#ffcd81; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;")) {
                        pickedSeats += Character.toString((char) 65 + k);
                        pickedSeats += (i + 1 - (3 - (hall.getCols() - 1) / 2)) + " ";
                        if (confirmation.isVisible()) {
                            screening.setTakenSeatAt(seatcount);
                            arr1[counter1] = seatcount;
                            arr2[counter1] = " " + Character.toString((char) 65 + k);
                            arr2[counter1] += Integer.toString(i + 1 - (3 - (hall.getCols() - 1) / 2));
                            counter1++;
                        }
                    }
                    seatcount++;
                    i++; // Increment i inside the while loop to avoid an infinite loop
                }
            }

        } else {
            for (int k = 0; k < hall.getRows(); k++) {
                int i = 3 - hall.getCols() / 2;
                int j = 3 + hall.getCols() / 2;
                while (i <= j && seatcount < hall.getSeatsNum()) {
                    if (i != 3) {
                        if (gridSeats.getChildren().get(i + k * 7).getStyle()
                                .equals("-fx-fill:#ffcd81; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;")) {
                            pickedSeats += Character.toString((char) 65 + k);

                            if (i < 3) {
                                pickedSeats += (i - (3 - (hall.getCols()) / 2) + 1) + " ";
                            }
                            if (i > 3) {
                                pickedSeats += (i - (3 - (hall.getCols()) / 2)) + " ";
                            }

                            if (confirmation.isVisible()) {
                                screening.setTakenSeatAt(seatcount);
                                arr1[counter1] = seatcount;
                                arr2[counter1] = "" + Character.toString((char) 65 + k);

                                if (i < 3) {
                                    arr2[counter1] += Integer.toString(i - (3 - (hall.getCols()) / 2) + 1);
                                }
                                if (i > 3) {
                                    arr2[counter1] += Integer.toString(i - (3 - (hall.getCols()) / 2));
                                }
                                counter1++;
                            }
                        }
                        seatcount++;
                    }
                    i++; // Increment i inside the while-loop
                }
            }

        }
        if(confirmation.isVisible() && !(confirmation.getText().equals("You must choose a seat in order to book."))) {
            BookingSeatsReq request = new BookingSeatsReq(screening,counter,arr1, arr2 );
            try {
                NewMessage msg = new NewMessage(request,  "SaveSeatsInHall");
                SimpleClient.getClient().sendToServer(msg);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }else if (counter!=0){
            confirmation.setVisible(true);
            confirmation.setText("You have selected the following seats: " + pickedSeats +
                    "\nTotal Price: " + MovieDetailsPage.ticketValue*counter+"$"
                    +"\nClick 'Book Seats' again to continue your purchase");
        } else {
            confirmation.setVisible(true);
            confirmation.setText("You must choose a seat in order to book.");
        }
    }


    public static  void setScreening(Screening screening1) {
        screening = screening1;
    }



    @FXML // This method is called by the FXMLLoader when initialization is complete
    public void initialize() {
        EventBus.getDefault().register(this);

        assert gridSeats != null : "fx:id=\"gridSeats\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s1 != null : "fx:id=\"s1\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s2 != null : "fx:id=\"s2\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s3 != null : "fx:id=\"s3\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s4 != null : "fx:id=\"s4\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s5 != null : "fx:id=\"s5\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s6 != null : "fx:id=\"s6\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s7 != null : "fx:id=\"s7\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s8 != null : "fx:id=\"s8\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s9 != null : "fx:id=\"s9\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s10 != null : "fx:id=\"s10\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s11 != null : "fx:id=\"s11\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s12 != null : "fx:id=\"s12\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s13 != null : "fx:id=\"s13\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s14 != null : "fx:id=\"s14\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s15 != null : "fx:id=\"s15\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s16 != null : "fx:id=\"s16\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s17 != null : "fx:id=\"s17\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s18 != null : "fx:id=\"s18\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s19 != null : "fx:id=\"s19\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s20 != null : "fx:id=\"s20\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s21 != null : "fx:id=\"s21\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s22 != null : "fx:id=\"s22\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s23 != null : "fx:id=\"s23\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s24 != null : "fx:id=\"s24\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s25 != null : "fx:id=\"s25\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s26 != null : "fx:id=\"s26\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s27 != null : "fx:id=\"s27\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s28 != null : "fx:id=\"s28\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s29 != null : "fx:id=\"s29\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s30 != null : "fx:id=\"s30\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s31 != null : "fx:id=\"s31\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s32 != null : "fx:id=\"s32\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s33 != null : "fx:id=\"s33\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s34 != null : "fx:id=\"s34\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s35 != null : "fx:id=\"s35\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s36 != null : "fx:id=\"s36\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s37 != null : "fx:id=\"s37\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s38 != null : "fx:id=\"s38\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s39 != null : "fx:id=\"s39\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s40 != null : "fx:id=\"s40\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s41 != null : "fx:id=\"s41\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert s42 != null : "fx:id=\"s42\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert colsGrids != null : "fx:id=\"colsGrid\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert rowsGrid != null : "fx:id=\"rowsGrid\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert bookSeat != null : "fx:id=\"bookSeat\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert movieInformation != null : "fx:id=\"movieInformation\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert warning != null : "fx:id=\"warning\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert availableSeats != null : "fx:id=\"availableSeats\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert chosenNum != null : "fx:id=\"chosenNum\" was not injected: check your FXML file 'SeatChoosing.fxml'.";
        assert backToPrevious != null : "fx:id=\"backButton\" was not injected: check your FXML file 'SeatChoosing.fxml'.";

        counter = 0;
        hall = screening.getHall();

        movieInformation.setText("Movie: " + screening.getMovie().getEngtitle()+ "\nDate: " + screening.getScreeningTime().toLocalDate()+ "\nTime: "+ screening.getScreeningTime().toLocalTime() +"\nBranch: "+screening.getBranch().getName()+ "\nCost per ticket: " + MovieDetailsPage.ticketValue);
        chosenNum.setText("Number of picked seats: " + counter);
        availableSeats.setText("Available seats: " + screening.getAvailableSeats());
        ShowSeatsInHall();

    }

    @Subscribe
    public void onUpdateScreeningEvent(UpdateScreeningTimesEvent event) {
        List<Screening> screenings = event.getScreenings();
        for(Screening screening1 : screenings) {
            if(screening1.getId() == screening.getId()) {
                screening = screening1;
                hall = screening.getHall();
                break;
            }}
        ShowSeatsInHall();
    }

    @FXML
    void backToPrevious(ActionEvent event) {
        MovieDetailsPage.setSelectedMovie(screening.getMovie());  // שמור את הסרט שנבחר
        MovieDetailsPage.movieDetailsPage = 1;
        MovieDetailsPage.setTicketPrice = 0;
        App.switchScreen("MovieDetailsPage");  // Navigate to MovieDetailsPage
    }

    private void ShowSeatsInHall() {
        int seatcount = 0;
        if (isOdd(hall.getCols())) {
            for (int i = 0; i < hall.getRows(); i++) {
                rowsGrid.getChildren().get(i).setVisible(true);
                int j = 3 - (hall.getCols() - 1) / 2, k = 3 + (hall.getCols() - 1) / 2;
                while (j <= k && seatcount < hall.getSeatsNum()) {
                    if (screening.getSeatStatus(seatcount)) {
                        gridSeats.getChildren().get(j + i * 7).setStyle("-fx-fill:#e7cbe3; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;");
                    } else {
                        gridSeats.getChildren().get(j + i * 7).setStyle("-fx-fill:#dc48d5; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;");
                    }
                    seatcount++;
                    j++;
                }
            }

            int k = 3 + hall.getCols() / 2;
            int j = 3 - hall.getCols() / 2;
            int i = 1;

            while (j <= k) {
                Label text = new Label(" ");
                text.setText(Integer.toString(i));
                text.setStyle("-fx-text-fill: WHITE; -fx-font-size: 12; -fx-alignment: center;");
                colsGrids.add(text, j, 0);
                i++;
                j++;
            }
        } else {
            for (int i = 0; i < hall.getRows(); i++) {
                rowsGrid.getChildren().get(i).setVisible(true);
                int j = 3 - hall.getCols() / 2, k = 3 + hall.getCols() / 2;
                while (j <= k && seatcount < hall.getSeatsNum()) {
                    if (j != 3) {
                        if (screening.getSeatStatus(seatcount)) {
                            gridSeats.getChildren().get(j + i * 7).setStyle("-fx-fill:#e7cbe3; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;");
                        } else {
                            gridSeats.getChildren().get(j + i * 7).setStyle("-fx-fill:#dc48d5; -fx-font-family: 'Material Icons'; -fx-font-size: 40.0;");
                        }
                        seatcount++;
                    }
                    j++;
                }}

            int k = 3 + hall.getCols() / 2;
            int j = 3 - hall.getCols() / 2;
            int i = 1;

            while (j <= k) {
                if (j != 3) {
                    Label text = new Label(" ");
                    text.setText(Integer.toString(i));
                    text.setStyle("-fx-text-fill: WHITE; -fx-font-size: 12; -fx-alignment: center;");
                    colsGrids.add(text, j, 0);
                    i++;
                }
                j++;
            }}
    }


    boolean isOdd(int num){
        return !(num % 2 == 0);
    }
}

