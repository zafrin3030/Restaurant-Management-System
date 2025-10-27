package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Employee;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EmployeeAssignController implements Initializable {

    @FXML
    private TableColumn<Employee, String> addressCol;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, String> nameCol;

    @FXML
    private TableColumn<Employee, String> phoneCol;

    @FXML
    private TableColumn<Employee, String> positionCol;

    @FXML
    private TableColumn<Employee, Number> salaryCol;

    private ObservableList<Employee> employees;

    public static Employee selectedEmployee;

    public static Employee getSelectedEmployee() {
        return selectedEmployee;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getName()));
        positionCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPosition()));
        addressCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress()));
        phoneCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhone()));
        salaryCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getSalary()));
        loadEmployees();
    }

    private void loadEmployees() {
        employees = FXCollections.observableArrayList();
        try{
            Connection con = ConnectionSingleton.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from employees");
            while(rs.next()){
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("position"),
                        rs.getString("address"),
                        rs.getString("phone"),
                        rs.getDouble("salary")
                ) );
            }
        employeeTable.setItems(employees);
        } catch (SQLException e){
            System.out.println("Failed to load employees");
            e.printStackTrace();

        }
    }

    @FXML
    void goToAdminDashboard(ActionEvent event) {
        HelloApplication.changeScene("dashboard~admin");
    }

    @FXML
    void newEmployeeAssignEvent(ActionEvent event) {
        selectedEmployee = null;
        HelloApplication.changeScene("employee~form");
    }

    @FXML
    void editEvent(ActionEvent event) {
        selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
        if(selectedEmployee != null){
            HelloApplication.changeScene("employee~form");
        }
    }

    @FXML
    void deleteEvent(ActionEvent event) {
         Employee selected = employeeTable.getSelectionModel().getSelectedItem();
         if(selected != null){
             try {
                 Connection con = ConnectionSingleton.getConnection();
                 String query = "delete from employees where id = ?";
                 PreparedStatement ps = con.prepareStatement(query);
                 ps.setInt(1, selected.getId());
                 ps.executeUpdate();
                 loadEmployees();
             } catch (SQLException e){
                 e.printStackTrace();
             }
         }
    }
}
