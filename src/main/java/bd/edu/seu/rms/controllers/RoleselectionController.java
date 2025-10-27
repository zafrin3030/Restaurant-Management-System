package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;


public class RoleselectionController {

    @FXML
    void onAdminLoginClicked(ActionEvent event)  {
        HelloApplication.changeScene("AdminLogin");



    }

    @FXML
    void onCustomerLoginClicked(ActionEvent event) {
        HelloApplication.changeScene("CustomerLogin");

    }

}
