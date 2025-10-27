package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Employee;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeFormController implements Initializable {

    @FXML
    private TextArea addressArea;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phnNumField;

    @FXML
    private TextField salaryField;

    @FXML
    private ComboBox<String> employeePositionCombo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       employeePositionCombo.setItems(FXCollections.observableArrayList("Chef" , "Delivery Boy"));
       Employee emp = EmployeeAssignController.getSelectedEmployee();
       if (emp != null) {
           nameField.setText(emp.getName());
           employeePositionCombo.setValue(emp.getPosition());
           addressArea.setText(emp.getAddress());
           phnNumField.setText(emp.getPhone());
           salaryField.setText(String.valueOf(emp.getSalary()));
       }
    }

    @FXML
    void backToEmployeeEvent(ActionEvent event) {
        HelloApplication.changeScene("employee~assign");
    }

    @FXML
    void saveEvent(ActionEvent event) {
          String name = nameField.getText();
          String position = employeePositionCombo.getValue();
          String address = addressArea.getText();
          String phone = phnNumField.getText();
          double salary = Double.parseDouble(salaryField.getText());

          try{
              Connection con = ConnectionSingleton.getConnection();
              if(EmployeeAssignController.getSelectedEmployee() == null){
                  String query = "INSERT INTO employees (name, position, address, phone, salary) VALUES(?,?,?,?,?)";
                  PreparedStatement ps = con.prepareStatement(query);
                  ps.setString(1, name);
                  ps.setString(2, position);
                  ps.setString(3, address);
                  ps.setString(4, phone);
                  ps.setDouble(5, salary);
                  ps.executeUpdate();
              } else {
                  String query = "UPDATE employees SET name =? , position =? , address =? , phone =? , salary =? WHERE id =?";
                  PreparedStatement ps = con.prepareStatement(query);
                  ps.setString(1, name);
                  ps.setString(2, position);
                  ps.setString(3, address);
                  ps.setString(4, phone);
                  ps.setDouble(5, salary);
                  ps.setInt(6, EmployeeAssignController.getSelectedEmployee().getId());
                  ps.executeUpdate();
              }
              HelloApplication.changeScene("employee~assign");
          } catch (SQLException e){
              System.out.println("Failed to connect to database");
              e.printStackTrace();
          }
    }

}

