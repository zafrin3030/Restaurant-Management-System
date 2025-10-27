package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardAdminController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          loginAdminName.setText(AdminLoginController.loggedInAdminName);
    }

    @FXML
    private Label loginAdminName;

    @FXML
    void handleLogoutClick(MouseEvent event) {
        HelloApplication.changeScene("roleselection~view");
    }
    @FXML
    void manageEmployeeEvent(ActionEvent event) {
        HelloApplication.changeScene("employee~assign");
    }
    @FXML
    void goToRevenueDashboard(ActionEvent event) {
        HelloApplication.changeScene("revenue~dashboard");
    }
    @FXML
    void goToFoodMenu(ActionEvent event) {
        HelloApplication.changeScene("food~menu");
    }
    @FXML
    void goToOrderList(ActionEvent event) {
        HelloApplication.changeScene("order~list");
    }
    @FXML
    void goToSeeReview(ActionEvent event) {
        HelloApplication.changeScene("review~show");
    }
    @FXML
    void goToCustomerList(MouseEvent event) {
        HelloApplication.changeScene("customer~list");
    }



}
