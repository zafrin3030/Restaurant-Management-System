package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Customer;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerListController  implements Initializable {

    @FXML
    private TableColumn<Customer, String> addressCol;

    @FXML
    private TableView<Customer> customerListTable;

    @FXML
    private TableColumn<Customer, String> fullNameCol;

    @FXML
    private TableColumn<Customer, String> genderCol;

    @FXML
    private TableColumn<Customer, Number> idCol;

    @FXML
    private TableColumn<Customer, String> phoneCol;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Customer, String> usernameCol;

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
          idCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));
          fullNameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFullName()));
          usernameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getUsername()));
          phoneCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPhoneNumber()));
          addressCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getAddress()));
          genderCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getGender()));
          loadCustomers();

    }

    @FXML
    void goToAdminDashboard(MouseEvent event) {
        HelloApplication.changeScene("dashboard~admin");
    }

    @FXML
    void searchKeyValue(KeyEvent event) {
          String searchValue = searchField.getText().toLowerCase().trim();
          ObservableList<Customer> customers = FXCollections.observableArrayList();

          for (Customer customer : customerList) {
              String gender = customer.getGender().toLowerCase().trim();
              String address = customer.getAddress().toLowerCase().trim();
              if (address.contains(searchValue) || gender.contains(searchValue)) {
              customers.add(customer);
              }
          }
          customerListTable.setItems(customers);
    }

    private void loadCustomers() {
        try {
            Connection con = ConnectionSingleton.getConnection();
            String query = "select * from customers";
            ResultSet rs = con.createStatement().executeQuery(query);
            while (rs.next()) {
                customerList.add(new Customer(
                        rs.getInt("id"),
                        rs.getString("full_name"),
                        rs.getString("username"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("gender"),
                        rs.getString("password")
                ));
            }
            customerListTable.setItems(customerList);
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

}
