package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Employee;
import il.cshaifasweng.OCSFMediatorExample.entities.HeadManager;
import il.cshaifasweng.OCSFMediatorExample.entities.NewMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Objects;

public class LoginPage {

    @FXML
    private Button changeHostBtn;

    @FXML
    private Button changeHostBtn1;

    @FXML
    private Button homePageBtn;

    @FXML
    private Button loginBtn;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField userNameText;

    ////public Employee employee; ////////////////////
    static Employee employee1;

    @FXML
    private void switchToHomePage() throws IOException {
        App.switchScreen("HomePage");
    }

    public void initialize() {
        EventBus.getDefault().register(this);
    }

    @FXML
    private void requestEmployeeFromServer() {
        try { ///////////////////////////////////////////////////////////////
            NewMessage message = new NewMessage("login", userNameText.getText(), passwordText.getText());
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Subscribe
    public void onUpdateLoginEvent(UpdateLoginEvent event) {
        Platform.runLater(() -> {
            employee1 = event.getEmployee();
            try {
                switchToRightHomePage();
            }
            catch (IOException e) {}
        });
    }

    @FXML
    private void switchToRightHomePage() throws IOException {
        String position = employee1.getPosition();
        if(Objects.equals(position, "Head Manager")){
            App.switchScreen("HeadManagerPage");
        }
        else if(Objects.equals(position, "Branch Manager")){
            App.switchScreen("BranchManagerPage");
        }
        else if(Objects.equals(position, "Content Manager")){
            App.switchScreen("ContentManagerPage");
        }
        else if(Objects.equals(position, "Customer Service Worker")){
            App.switchScreen("CustomerServiceWorkerPage");
        }
    }

    @FXML
    void switchToHostPage(ActionEvent event) {
        App.switchScreen("HostPage");
    }

}