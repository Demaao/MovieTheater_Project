package il.cshaifasweng.OCSFMediatorExample.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

public class HostPage {
    static int flag = 0;

    @FXML // fx:id="EnterIP"
    private Button EnterIP; // Value injected by FXMLLoader

    @FXML
    private Button homePageBtn;

    public void initialize() {
        if (flag == 0) {
            homePageBtn.setVisible(false);
        } else
            homePageBtn.setVisible(true);
    }

    @FXML // fx:id="HostIP"
    private TextField HostIP; // Value injected by FXMLLoader

    @FXML
    private TextField Port;
/*
    @FXML
    void enterHostIP(ActionEvent event) throws IOException {
        flag = 1;
        if (!HostIP.getText().startsWith("localhost")) {
            SimpleClient.client.closeConnection();
            SimpleClient.client.setHost(HostIP.getText());
            SimpleClient.client.openConnection();
        }
        try {
            switchToHomePage();
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }*/

    @FXML
    void enterHostIP(ActionEvent event) throws IOException {
        flag = 1;
        try{
            if (SimpleClient.client != null) {
                SimpleClient.client.closeConnection();
            }
            // Create a new client instance with the provided host address
            SimpleClient.client = new SimpleClient(HostIP.getText(), Integer.parseInt(Port.getText()));
            SimpleClient.client.openConnection(); // Connect to the server

        switchToHomePage();
    } catch (IOException e) {
        // Display an alert if the connection fails
        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to connect to server. Please check your IP address and port, and try again.");
        alert.setHeaderText("Connection Error");
        alert.showAndWait();
    } catch (NumberFormatException e) {
        // Handle cases where the port number isn't a valid integer
        Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid port number. Please enter a valid integer.");
        alert.setHeaderText("Input Error");
        alert.showAndWait();
    }
    }

    @FXML
    private void switchToHomePage() throws IOException {
        if(LoginPage.employee1 != null){
        String position = LoginPage.employee1.getPosition();
            if(Objects.equals(position, "Head Manager")){
                App.switchScreen("HeadManagerPage");
            } else if (Objects.equals(position, "Branch Manager")) {
                App.switchScreen("BranchManagerPage");
            } else if (Objects.equals(position, "Content Manager")) {
                App.switchScreen("ContentManagerPage");
            } else if(Objects.equals(position, "Customer Service Worker")) {
                App.switchScreen("CustomerServiceWorkerPage");
            }
        }
        else
            App.switchScreen("HomePage");
    }
    }

