package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Customer;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CustomerLoginController {

    @FXML
    private TextField customerNameField;

    @FXML
    private PasswordField customerPasswordField;

    public static String loggedInUsername = "";
    public static Customer loggedInCustomer;

    @FXML
    void goToCustomerDashboard(ActionEvent event) {
         String username = customerNameField.getText();
         String password = customerPasswordField.getText();

         if (username.isEmpty() || password.isEmpty()) {
             showAlert("Please enter both username and password");
             return;
         }

         try {
             Connection con = ConnectionSingleton.getConnection();
             String query = "SELECT * FROM customers WHERE username=? AND password=?";
             PreparedStatement ps = con.prepareStatement(query);
             ps.setString(1, username);
             ps.setString(2, password);
             ResultSet rs = ps.executeQuery();
             if (rs.next()) {
                 loggedInCustomer = new Customer (
                         rs.getInt("id"),
                         rs.getString("full_name"),
                         rs.getString("username"),
                         rs.getString("phone"),
                         rs.getString("address"),
                         rs.getString("gender"),
                         rs.getString("password")
                 );
                 loggedInUsername = loggedInCustomer.getFullName();
                 HelloApplication.changeScene("customer~dashboard");
             } else {
                 showAlert("Invalid username or password");
             }
         } catch (SQLException e){
             System.out.println("Failed to get customer details");
             e.printStackTrace();
         }
    }

    @FXML
    void goToPasswordChangeEvent(MouseEvent event) {
           HelloApplication.changeScene("customerpassword~change");
    }

    @FXML
    void goToRegistrationEvent(ActionEvent event) {
           HelloApplication.changeScene("customer~registration");
    }

    @FXML
    void handleLogoutClick(MouseEvent event) {
        HelloApplication.changeScene("roleselection~view");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Login Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
