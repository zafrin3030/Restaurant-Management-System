package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Customer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class ProfileEditController implements Initializable {

    @FXML
    private TextArea addressArea;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField genderField;

    @FXML
    private Label loginFullName;

    @FXML
    private TextField phoneNumField;

    @FXML
    private TextField userNameField;

    @FXML
    private Label saveMessage;

    public static String successMessage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
              loginFullName.setText(CustomerLoginController.loggedInUsername);
              Customer customer = CustomerLoginController.loggedInCustomer;
              if (customer != null) {
                  userNameField.setText(customer.getUsername());
                  fullNameField.setText(customer.getFullName());
                  phoneNumField.setText(customer.getPhoneNumber());
                  genderField.setText(customer.getGender());
                  addressArea.setText(customer.getAddress());

              }
              saveMessage.setText(successMessage);
              successMessage = "";
    }

    @FXML
    void goToCustomerDashboard(MouseEvent event) {
        HelloApplication.changeScene("customer~dashboard");
    }

    @FXML
    void goToPasswordUpdate(MouseEvent event) {
       HelloApplication.changeScene("password~update");
    }

    @FXML
    void goToUpdate(MouseEvent event) {
      HelloApplication.changeScene("update~info");
    }

}
