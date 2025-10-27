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
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoginController {

    @FXML
    private PasswordField adminPasswordField;

    @FXML
    private TextField loginInput;

    public static String loggedInAdminName;

    @FXML
    void handleLogoutClick(MouseEvent event) {
        HelloApplication.changeScene("roleselection~view");

    }

    @FXML
    void goToAdminDashboard(ActionEvent event) {
          String loginText = loginInput.getText();
          String passwordText = adminPasswordField.getText();

          if(loginText.isEmpty() || passwordText.isEmpty()) {
              showAlert("Login Error", "Please enter your username/email and password.");
              return;
          }
          try{
              Connection con = ConnectionSingleton.getConnection();
              String query = "SELECT * FROM admin WHERE (username = ? OR email = ? ) AND password = ?";
              PreparedStatement ps = con.prepareStatement(query);
              ps.setString(1, loginText);
              ps.setString(2, loginText);
              ps.setString(3, passwordText);
              ResultSet rs = ps.executeQuery();
              if(rs.next()) {
                  loggedInAdminName = rs.getString("name");
                  HelloApplication.changeScene("dashboard~admin");
              } else {
                  showAlert("Login Failed" , "Invalid username/email or password.");
              }
          } catch (SQLException e){
              System.out.println("Failed to load database");
              e.printStackTrace();
          }
    }

    private void showAlert(String title , String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
