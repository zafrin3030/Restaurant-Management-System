package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

public class CustomerRegistrationController {

    @FXML
    private ToggleGroup genderGroup;

    @FXML
    private TextArea addressArea;

    @FXML
    private Label confirmPasswordError;

    @FXML
    private PasswordField confirmPasswordField;

    @FXML
    private TextField fullNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField phnNumberField;

    @FXML
    private TextField usernameField;

    @FXML
    void goToCustomerLoginEvent(MouseEvent event) {
        HelloApplication.changeScene("customerlogin");
    }

    @FXML
    void registerEvent(ActionEvent event) {
         String fullName = fullNameField.getText();
         String username = usernameField.getText();
         String phnNumber = phnNumberField.getText();
         String address = addressArea.getText();
         RadioButton selectedGenderButton = (RadioButton) genderGroup.getSelectedToggle();
         String gender = selectedGenderButton.getText();
         String password = passwordField.getText();
         String confirmPassword = confirmPasswordField.getText();

         if (fullName.isEmpty() || username.isEmpty() || phnNumber.isEmpty() || address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
             showAlert(Alert.AlertType.ERROR, "Error", "Please fill all the fields");
             return;
         }

         if (!password.equals(confirmPassword)) {
             showAlert(Alert.AlertType.ERROR, "Password Mismatch", "Passwords do not match");
             return;
         }

         try {
             Connection con = ConnectionSingleton.getConnection();
             String query = " INSERT INTO customers (full_name, username, phone, address, gender , password) VALUES (?, ?, ?, ?, ? , ?)";
             PreparedStatement ps = con.prepareStatement(query);
             ps.setString(1, fullName);
             ps.setString(2, username);
             ps.setString(3, phnNumber);
             ps.setString(4, address);
             ps.setString(5, gender);
             ps.setString(6, password);
             ps.executeUpdate();
             clearFields();

         } catch (SQLIntegrityConstraintViolationException e){
             showAlert(Alert.AlertType.ERROR, "Duplicate", "Username already exists");
             return;
         } catch (SQLException ex) {
             System.out.println("Failed to add customer");
             ex.printStackTrace();
         }
    }

    private void clearFields() {
        fullNameField.clear();
        usernameField.clear();
        phnNumberField.clear();
        addressArea.clear();
        genderGroup.selectToggle(null);
        passwordField.clear();
        confirmPasswordField.clear();
    }

   private void showAlert(Alert.AlertType alertType, String title, String contentText) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.showAndWait();
   }
}
