package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Order;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerOrderHistoryController implements Initializable {

    @FXML
    private TableColumn<Order, String> dateCol;

    @FXML
    private TableColumn<Order, String> foodNameCol;

    @FXML
    private TableColumn<Order, String> noteCol;

    @FXML
    private TableView<Order> orderTable;

    @FXML
    private TableColumn<Order, String> paymentCol;

    @FXML
    private TableColumn<Order, Number> priceCol;

    @FXML
    private TableColumn<Order, Number> quantityCol;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Order, Number> totalCol;

    @FXML
    private TextField totalOrderField;

    public ObservableList<Order> orderList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
         foodNameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFoodName()));
         quantityCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getQuantity()));
         priceCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrice()));
         totalCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getTotalPrice()));
         dateCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDate()));
         paymentCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getPaymentMethod()));
         noteCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getSpecialNote()));
         orderTable.setItems(orderList);
         loadCustomerOrderHistory();
    }

    @FXML
    void backToCustomerDashboard(ActionEvent event) {
        HelloApplication.changeScene("customer~dashboard");
    }

    @FXML
    void goToReviewWriteEvent(ActionEvent event) {
         HelloApplication.changeScene("customer~review");
    }

    private void loadCustomerOrderHistory() {
        orderList.clear();
        try{
            Connection con = ConnectionSingleton.getConnection();
            String query = "select * from orders where customer_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, CustomerLoginController.loggedInCustomer.getId());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getInt("customer_id"),
                        rs.getString("food_name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getDouble("total_price"),
                        rs.getDate("order_date").toString(),
                        rs.getString("payment_method"),
                        rs.getString("special_note")
                );
                orderList.add(order);
            }
            totalOrderField.setText(orderList.size()+"");
        } catch (SQLException e){
            System.out.println("Failed to load customer order history");
            e.printStackTrace();
        }
    }

    @FXML
    void searchKeyValue(KeyEvent event) {
            String search = searchField.getText().toLowerCase().trim();
            ObservableList<Order> filtered = FXCollections.observableArrayList();
            for (Order o : orderList) {
                String price = String.valueOf(o.getPrice());
                String totalPrice = String.valueOf(o.getTotalPrice());
                String date = o.getDate().toLowerCase();
                String payment = o.getPaymentMethod().toLowerCase();
                String note = o.getSpecialNote().toLowerCase();
                String food = o.getFoodName().toLowerCase();
                if (food.contains(search)
                        || price.contains(search)
                        || totalPrice.contains(search)
                        || date.contains(search)
                        || payment.contains(search)
                        || note.contains(search)) {
                    filtered.add(o);
                }
            }
            orderTable.setItems(filtered);
    }
}
