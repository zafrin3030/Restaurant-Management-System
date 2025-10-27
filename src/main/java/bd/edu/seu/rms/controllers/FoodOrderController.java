package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.CartItem;
import bd.edu.seu.rms.models.Food;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class FoodOrderController implements Initializable {

    @FXML
    private TextField availableQuantityField;

    @FXML
    private ListView<CartItem> cartListView;

    @FXML
    private TableColumn<Food, String> categoryCol;

    @FXML
    private TableColumn<Food, String> foodNameCol;

    @FXML
    private TextField foodNameField;

    @FXML
    private TableView<Food> foodTableView;

    @FXML
    private ToggleGroup paymentGroup;

    @FXML
    private TextField phoneField;

    @FXML
    private TableColumn<Food, Number> priceCol;

    @FXML
    private TextField priceField;

    @FXML
    private TableColumn<Food, Number> quantityCol;

    @FXML
    private Label quantityError;

    @FXML
    private Spinner<Integer> quantitySpinner;

    @FXML
    private TextField searchField;

    @FXML
    private TextArea specialNoteArea;

    @FXML
    private ToggleGroup specialNoteGroup;

    @FXML
    private RadioButton specialRadioButton;

    @FXML
    private Label totalPriceLabel;

    private ObservableList<Food> foodList = FXCollections.observableArrayList();
    private ObservableList<CartItem> cartItems = FXCollections.observableArrayList();
    private double totalPrice = 0.0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        foodNameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFoodName()));
        categoryCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory()));
        priceCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrice()));
        quantityCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getAvailableQuantity()));
       cartListView.setItems(cartItems);
        foodTableView.setItems(foodList);
        quantitySpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
        loadMenu();

        foodTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
               foodNameField.setText(newValue.getFoodName());
               priceField.setText(String.valueOf(newValue.getPrice()));
               availableQuantityField.setText(String.valueOf(newValue.getAvailableQuantity()));
            }
        });
    }

    @FXML
    void addToCartEvent(ActionEvent event) {
           Food selectedFood = foodTableView.getSelectionModel().getSelectedItem();
           int quantity = quantitySpinner.getValue();

           if(selectedFood == null) {
               quantityError.setText("Please select a food to add to cart");
               return;
           }
           if(quantity <= 0 || quantity > selectedFood.getAvailableQuantity()) {
               quantityError.setText("Invalid quantity selected!");
               return;
           }
           quantityError.setText("");
           cartItems.add(new CartItem(selectedFood, quantity));
           totalPrice += selectedFood.getPrice() * quantity;
           totalPriceLabel.setText(String.valueOf(totalPrice));
           quantitySpinner.getValueFactory().setValue(0);
    }

    @FXML
    void deleteEvent(MouseEvent event) {
         CartItem selectedItem = cartListView.getSelectionModel().getSelectedItem();
         if(selectedItem != null) {
             cartItems.remove(selectedItem);
             totalPrice -= selectedItem.getTotalPrice();
             totalPriceLabel.setText(String.valueOf(totalPrice));
         } else {
             showAlert("No food selected!");
         }
    }

    @FXML
    void exitEvent(ActionEvent event) {
        HelloApplication.changeScene("customer~dashboard");
    }

    @FXML
    void orderDoneEvent(ActionEvent event) {
         if (cartItems.isEmpty()) {
             showAlert("Cart is empty!");
             return;
         }

         if (phoneField.getText().isEmpty()) {
             showAlert("Please enter a phone number!");
             return;
         }

           RadioButton paymentRadio = (RadioButton) paymentGroup.getSelectedToggle();
           if(paymentRadio == null){
             showAlert("Please select a payment!");
             return;
         }

         RadioButton noteRadio = (RadioButton) specialNoteGroup.getSelectedToggle();
         String note;
         if(noteRadio != null && noteRadio == specialRadioButton){
             if(specialNoteArea.getText().isEmpty()){
                 showAlert("Please enter a special note!");
                 return;
             } else {
                 note = specialNoteArea.getText();
             }
         } else {
             note = "regular";
         }

         String paymentMethod = paymentRadio.getText();

         try {
             Connection con = ConnectionSingleton.getConnection();
             String query = "insert into orders (customer_id, food_name , quantity, price, total_price, order_date, payment_method, special_note) values (?,?,?,?,?,CURDATE(),?,?)";
             PreparedStatement ps = con.prepareStatement(query);
             for (CartItem item : cartItems) {
                 ps.setInt(1, CustomerLoginController.loggedInCustomer.getId());
                 ps.setString(2, item.getFood().getFoodName());
                 ps.setInt(3, item.getQuantity());
                 ps.setDouble(4, item.getFood().getPrice());
                 ps.setDouble(5, item.getTotalPrice());
                 ps.setString(6, paymentMethod);
                 ps.setString(7, note);
                 ps.addBatch();


                 PreparedStatement ps2 = con.prepareStatement("update food set available_quantity = available_quantity - ? where id = ?");
                 ps2.setInt(1, item.getQuantity());
                 ps2.setInt(2, item.getFood().getId());
                 ps2.executeUpdate();

             }
             ps.executeBatch();
             cartItems.clear();
             totalPrice = 0.0;
             totalPriceLabel.setText("0.0");
             specialNoteArea.clear();
             phoneField.clear();
             loadMenu();
         } catch (SQLException e){
             System.out.println("Failed to add to cart");
             e.printStackTrace();
         }
    }

    private void loadMenu(){
        foodList.clear();
        try {
            Connection con = ConnectionSingleton.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from food");
            while(rs.next()){
                foodList.add(new Food(rs.getInt("id"),
                        rs.getString("food_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getDouble("cost_price"),
                        rs.getInt("available_quantity")
                ));
            }
        } catch (SQLException e){
            System.out.println("Failed to load menu");
            e.printStackTrace();
        }
    }

    @FXML
    void searchKeyValue(KeyEvent event) {
         String searchValue = searchField.getText();
         ObservableList<Food> foods = FXCollections.observableArrayList();

         for(Food food : foodList){
             if(food.getFoodName().toLowerCase().contains(searchValue.toLowerCase()) || food.getCategory().toLowerCase().contains(searchValue.toLowerCase()) || String.valueOf(food.getPrice()).toLowerCase().contains(searchValue.toLowerCase()) || String.valueOf(food.getAvailableQuantity()).toLowerCase().contains(searchValue.toLowerCase())){
                 foods.add(food);
             }
         }
         foodTableView.setItems(foods);
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
