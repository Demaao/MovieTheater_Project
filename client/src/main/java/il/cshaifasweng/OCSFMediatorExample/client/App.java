package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;
import il.cshaifasweng.OCSFMediatorExample.entities.HomeMovie;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javafx.stage.StageStyle;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import static java.lang.Thread.sleep;

public class App extends Application {

    private static Scene scene;
    static SimpleClient client;
    private static Stage appStage;

    public static AbstractClient getClient() {
        return client;
    }

    @Override
    public void start(Stage stage) throws IOException {
    	EventBus.getDefault().register(this);
    	//client = SimpleClient.getClient();
    	//client.openConnection();
        scene = new Scene(loadFXML("HostPage"), 900, 650);
        appStage = stage;
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
        PersonalAreaPage.logOutCustomer();
        try {
            NewMessage message = new NewMessage("logOut", LoginPage.employee1);
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();

        }
    	EventBus.getDefault().unregister(this);
		super.stop();
	}

   public static void setContent(String pageName) throws IOException {
        Parent root = loadFXML(pageName);
        scene = new Scene(root);
        appStage.setScene(scene);
        appStage.show();
    }

    public static void switchScreen(String screenName) {
        switch (screenName) {
            case "MoviesList":
                Platform.runLater(() -> {
                    try {
                        setContent("MoviesList");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "secondary":
                Platform.runLater(() -> {
                    try {
                        setContent("secondary");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "updateList":
                Platform.runLater(() -> {
                    try {
                        setContent("updateList");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "HostPage":
                Platform.runLater(() -> {
                    try {
                        setContent("HostPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "HomePage":
                Platform.runLater(() -> {
                    try {
                        setContent("HomePage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "LoginPage":
                Platform.runLater(() -> {
                    try {
                        setContent("LoginPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ComplaintPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ComplaintPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ChargebackPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ChargebackPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ChargebackPolicyPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ChargebackPolicyPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "CardsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("CardsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "CheckTicketsInCardPage":
                Platform.runLater(() -> {
                    try {
                        setContent("CheckTicketsInCardPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PurchaseProductsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("PurchaseProductsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PurchaseLink":
                Platform.runLater(() -> {
                    try {
                        setContent("PurchaseLink");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PaymentPage":
                Platform.runLater(() -> {
                    try {
                        setContent("PaymentPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PaymentLink":
                Platform.runLater(() -> {
                    try {
                        setContent("PaymentLink");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "MoviesPage":
                Platform.runLater(() -> {
                    try {
                        setContent("MoviesPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "FiledComplaintsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("FiledComplaintsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "CustomerServiceWorkerPage":
                Platform.runLater(() -> {
                    try {
                        setContent("CustomerServiceWorkerPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "HandleComplaintPage":
                Platform.runLater(() -> {
                    try {
                        setContent("HandleComplaintPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ContentManagerPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ContentManagerPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "AddMoviePage":
                Platform.runLater(() -> {
                    try {
                        setContent("AddMoviePage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "RemoveMoviePage":
                Platform.runLater(() -> {
                    try {
                        setContent("RemoveMoviePage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "EditPricesPage":
                Platform.runLater(() -> {
                    try {
                        setContent("EditPricesPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "EditScreeningPage":
                Platform.runLater(() -> {
                    try {
                        setContent("EditScreeningPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "HeadManagerPage":
                Platform.runLater(() -> {
                    try {
                        setContent("HeadManagerPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "TicketsReportPage":
                Platform.runLater(() -> {
                    try {
                        setContent("TicketsReportPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "CardsAndLinksReportPage":
                Platform.runLater(() -> {
                    try {
                        setContent("CardsAndLinksReportPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ComplaintsReportPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ComplaintsReportPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ConfirmPricesUpdatesPage":
                Platform.runLater(() -> {
                    try {
                        setContent("ConfirmPricesUpdatesPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "BranchManagerPage":
                Platform.runLater(() -> {
                    try {
                        setContent("BranchManagerPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "MovieDetailsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("MovieDetailsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "MovieLinkDetailsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("MovieLinkDetailsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PersonalAreaPage":
                Platform.runLater(() -> {
                    try {
                        setContent("PersonalAreaPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PersonalDetailsPage":
                Platform.runLater(() -> {
                    try {
                        setContent("PersonalDetailsPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PersonalMessagesPage":
                Platform.runLater(() -> {
                    try {
                        setContent("PersonalMessagesPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "ChooseSeating":
                Platform.runLater(() -> {
                    try {
                        setContent("ChooseSeating");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PaymentTickets":
                Platform.runLater(() -> {
                    try {
                        setContent("PaymentTickets");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "PersonalLinkPage":
                Platform.runLater(() -> {
                    try {
                        setContent("PersonalLinkPage");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;

        }
    }

    public static int loginDeniedCounter = 0;

    @Subscribe
    public void onWarningEvent(WarningEvent event) {
            Platform.runLater(() -> {
                Alert alert;
                if (event.getWarning().getMessage().equals("Logged out successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.setHeaderText("Logged Out");
                    alert.show();
                }
                else   if (event.getWarning().getMessage().equals("Movie removed successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else   if (event.getWarning().getMessage().equals("Movie added successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                }  else   if (event.getWarning().getMessage().equals("Screening removed successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else   if (event.getWarning().getMessage().equals("Screening added successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else   if (event.getWarning().getMessage().equals("Screening time updated successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else   if (event.getWarning().getMessage().equals("Complaint submitted successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                }  else   if (event.getWarning().getMessage().equals("Complaint answered successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                }   else   if (event.getWarning().getMessage().equals("Request sent successfully!")) {
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else if (event.getWarning().getMessage().startsWith("You have")){
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else if(event.getWarning().getMessage().startsWith("Time remaining for Complaint NO.")){
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else if(event.getWarning().getMessage().equals("Request denied successfully!")){
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else if(event.getWarning().getMessage().equals("Request confirmed successfully!")){
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                } else if(event.getWarning().getMessage().equals("Payment completed successfully!")){
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                  //  alert.show();
                } else if(event.getWarning().getMessage().equals("Purchase refunded successfully!")){
                    alert = new Alert(Alert.AlertType.INFORMATION, event.getWarning().getMessage());
                    alert.show();
                }
                else if (loginDeniedCounter >= 5) {
                    Stage dialogStage = new Stage();
                    dialogStage.initStyle(StageStyle.UNDECORATED); // No window decorations
                    dialogStage.initModality(Modality.APPLICATION_MODAL);

                    Label label = new Label("Too many incorrect login attempts!\n Try again in 10 seconds");
                    StackPane pane = new StackPane(label);
                    pane.setPrefSize(350, 190); // Set your preferred size
                    pane.setStyle("-fx-background-color: white; -fx-border-color:  lightgray; " +
                            "-fx-border-width: 1px; -fx-font-size: 15; ");

                    Scene scene = new Scene(pane);
                    dialogStage.setScene(scene);
                    dialogStage.centerOnScreen(); // Center dialog on screen
                    dialogStage.show();

                    final int[] countdown = {10};

                    Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event1 -> {
                        countdown[0]--;
                        label.setText("Too many incorrect login attempts!\nTry again in " + countdown[0] + " seconds");

                        if (countdown[0] <= 0) {
                            dialogStage.close();
                        }
                    }));
                    timeline.setCycleCount(10); // Repeat the KeyFrame 10 times (once per second)
                    timeline.play();

                    // Automatically close the dialog after 30 seconds
                    PauseTransition delay = new PauseTransition(Duration.seconds(10));
                    delay.setOnFinished(event1 -> dialogStage.close());
                    delay.play();
                }
                else {
                    alert = new Alert(Alert.AlertType.WARNING, event.getWarning().getMessage());
                    alert.show();
                }
            });
    }

    @Subscribe
    public void onMessageEvent(MessageEvent event) {
        if(event.getMessage().equals("Log out")) {
            Platform.runLater(() -> {
                LoginPage.employee1 = null;
                try {
                    AddMoviePage.switchToHomePage();
                } catch (IOException e) {
                }
            });
        }
    }

    @Subscribe
    public void onMovieUpdateEvent(NewMessage event){
        if(Objects.equals(event.getMessage(), "movieNotAvailable") && (MovieDetailsPage.movieDetailsPage == 1)
        && ((int)event.getObject() == MovieDetailsPage.selectedMovie.getId())) {
            if (PaymentTickets.getRequest() != null) {
                if(PaymentTickets.getRequest().getScreening().getBranch().getName().equals(event.getBranchName()) || event.getBranchName().equals("All")) {
                    MovieDetailsPage.movieDetailsPage = 0;
                    MovieDetailsPage.setTicketPrice = 0;
                    try {
                        SimpleClient.getClient().sendToServer(new NewMessage(PaymentTickets.getRequest(), "UndoSaveSeatsInHall"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Sorry, the movie is no longer available.");
                    alert.show();
                    try {
                        switchScreen("MoviesPage");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return;
                }
            } if(ChooseSeating.screening != null){
                if (ChooseSeating.screening.getBranch().getName().equals(event.getBranchName()) || event.getBranchName().equals("All")){
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Sorry, the movie is no longer available.");
                        alert.show();
                        try {
                            MovieDetailsPage.movieDetailsPage = 0;
                            MovieDetailsPage.setTicketPrice = 0;
                            switchScreen("MoviesPage");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            } else if (MovieDetailsPage.selectedMovie instanceof HomeMovie || event.getBranchName().equals("All")){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Sorry, the movie is no longer available.");
                alert.show();
                try {
                    MovieDetailsPage.movieDetailsPage = 0;
                    PurchaseLink.setLinkPrice = 0;
                    switchScreen("MoviesPage");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

	public static void main(String[] args) {
        launch();
    }
}