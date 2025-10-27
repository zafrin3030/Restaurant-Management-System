package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CustomerPasswordChangeController {

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField usernameField;

    @FXML
    void goToCustomerLogin(MouseEvent event) {
         HelloApplication.changeScene("customerlogin");
    }

    @FXML
    void newPasswordSaveEvent(ActionEvent event) {
         String username = usernameField.getText();
         String newPassword = newPasswordField.getText();
         String confirmPassword = confirmPasswordField.getText();

         if(username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
             showAlert("Please fill all the fields");
             return;
         }

         if(!newPassword.equals(confirmPassword)) {
             showAlert("Passwords do not match");
             return;
         }

         try {
             Connection con = ConnectionSingleton.getConnection();
             String query = "UPDATE customers SET password=? WHERE username=?";
             PreparedStatement ps = con.prepareStatement(query);
             ps.setString(1, newPassword);
             ps.setString(2, username);
             int rows = ps.executeUpdate();

             if(rows > 0) {
                 HelloApplication.changeScene("customerlogin");
             } else {
                 showAlert("Username not found");
             }
         } catch (SQLException e){
             e.printStackTrace();
         }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Reset Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
