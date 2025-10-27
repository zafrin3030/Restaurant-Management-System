package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Customer;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UpdateController implements Initializable {

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
    }

    @FXML
    void goToCustomerDashboard(MouseEvent event) {
        HelloApplication.changeScene("customer~dashboard");
    }

    @FXML
    void saveEvent(ActionEvent event) {
       String fullName = fullNameField.getText();
       String userName = userNameField.getText();
       String phoneNum = phoneNumField.getText();
       String address = addressArea.getText();
       String gender = genderField.getText();

       try {
           Connection con = ConnectionSingleton.getConnection();
           String query = "UPDATE customers SET full_name = ? , username = ? , phone = ? , address = ? , gender = ? WHERE id = ?";
           PreparedStatement ps = con.prepareStatement(query);
           ps.setString(1, fullName);
           ps.setString(2, userName);
           ps.setString(3, phoneNum);
           ps.setString(4, address);
           ps.setString(5, gender);
           ps.setString(6, String.valueOf(CustomerLoginController.loggedInCustomer.getId()));
           int rows = ps.executeUpdate();

           if (rows > 0) {
               CustomerLoginController.loggedInCustomer.setFullName(fullName);
               CustomerLoginController.loggedInCustomer.setUsername(userName);
               CustomerLoginController.loggedInCustomer.setPhoneNumber(phoneNum);
               CustomerLoginController.loggedInCustomer.setAddress(address);
               CustomerLoginController.loggedInCustomer.setGender(gender);

               CustomerLoginController.loggedInUsername = fullName;

               ProfileEditController.successMessage = " Profile successfully updated ";
               HelloApplication.changeScene("profile~edit");
           }
       } catch (SQLException e){
           System.out.println("Failed to save customer details");
           e.printStackTrace();
       }
    }

    @FXML
    void goToPasswordUpdate(MouseEvent event) {
        HelloApplication.changeScene("password~update");
    }
}
