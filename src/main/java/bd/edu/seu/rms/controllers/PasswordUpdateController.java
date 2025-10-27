package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Customer;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class PasswordUpdateController implements Initializable {

    @FXML
    private Label changeMessage;

    @FXML
    private PasswordField confirmPassField;

    @FXML
    private PasswordField currentPassField;

    @FXML
    private Label loginFullName;

    @FXML
    private PasswordField newPassField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
           loginFullName.setText(CustomerLoginController.loggedInUsername);
    }

    @FXML
    void goToCustomerDashboard(MouseEvent event) {
        HelloApplication.changeScene("customer~dashboard");
    }

    @FXML
    void goToUpdate(MouseEvent event) {
         HelloApplication.changeScene("update~info");
    }

    @FXML
    void savePasswordEvent(ActionEvent event) {
          String currentPass = currentPassField.getText();
          String newPass = newPassField.getText();
          String confirmPass = confirmPassField.getText();

          if(currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
              showAlert("Please fill all the fields");
              return;
          }

          Customer loggedIn = CustomerLoginController.loggedInCustomer;
           if(!currentPass.equals(loggedIn.getPassword())) {
               showAlert("Current password is incorrect");
               return;
           }

           if(newPass.equals(currentPass)){
               showAlert("New password must be different");
               return;
           }

           if(!newPass.equals(confirmPass)) {
               showAlert("Passwords do not match");
               return;
           }

           try {
               Connection con = ConnectionSingleton.getConnection();
               String query = "UPDATE customers SET password = ? WHERE id = ?";
               PreparedStatement ps = con.prepareStatement(query);
               ps.setString(1, newPass);
               ps.setInt(2, loggedIn.getId());
               int rows = ps.executeUpdate();

               if(rows > 0) {
                   loggedIn.setPassword(newPass);
                   ProfileEditController.successMessage = "Password updated successfully";
                   HelloApplication.changeScene("profile~edit");
               }
           } catch (SQLException e){
               System.out.println("Failed to change password");
               e.printStackTrace();
           }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
