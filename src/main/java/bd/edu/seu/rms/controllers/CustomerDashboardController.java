package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class CustomerDashboardController implements Initializable {

    @FXML
    private Label fullnameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            fullnameLabel.setText(CustomerLoginController.loggedInUsername);
    }

    @FXML
    void goToEditProfile(MouseEvent event) {
       HelloApplication.changeScene("profile~edit");
    }

    @FXML
    void goToFoodOrder(MouseEvent event) {
        HelloApplication.changeScene("food~order");
    }

    @FXML
    void goToLoginEvent(MouseEvent event) {
        HelloApplication.changeScene("customerlogin");
    }

    @FXML
    void goToOrderHistory(MouseEvent event) {
          HelloApplication.changeScene("customer~order~history");
    }

}
