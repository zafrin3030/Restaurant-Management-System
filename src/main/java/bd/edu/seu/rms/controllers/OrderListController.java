package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Order;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class OrderListController implements Initializable {

    @FXML
    private TableColumn<Order, Number> customerIdCol;

    @FXML
    private TableColumn<Order, String> foodNameCol;

    @FXML
    private TableColumn<Order, Number> idCol;

    @FXML
    private TableColumn<Order, String> noteCol;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> paymentCol;

    @FXML
    private TableColumn<Order, Number> quantityCol;

    private ObservableList<Order> orders = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            idCol.setCellValueFactory(c-> new SimpleIntegerProperty(c.getValue().getId()));
            customerIdCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getCustomerId()));
            foodNameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFoodName()));
            quantityCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()));
            paymentCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPaymentMethod()));
            noteCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSpecialNote()));
            orderTable.setItems(orders);
            loadOrders();
    }

    @FXML
    void goToAdminDashboard(ActionEvent event) {
        HelloApplication.changeScene("dashboard~admin");
    }

    private void loadOrders() {
        orders.clear();
        try {
            Connection con = ConnectionSingleton.getConnection();
            String query = "select * from orders";
            ResultSet rs = con.createStatement().executeQuery(query);
            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("food_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("total_price"),
                        rs.getString("order_date"),
                        rs.getString("payment_method"),
                        rs.getString("special_note")
                ));
            }
        } catch(SQLException e){
            System.out.println("Failed to load orders: ");
            e.printStackTrace();
        }
    }
}
