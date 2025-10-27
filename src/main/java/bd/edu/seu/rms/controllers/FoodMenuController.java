package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
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


public class FoodMenuController implements Initializable {
    @FXML
    private ChoiceBox<String> categoryBox;

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField costPriceField;

    @FXML
    private TextField quantityField;

    @FXML
    private TableColumn<Food, Number> availableQuantityCol;

    @FXML
    private TableColumn<Food, String> categoryCol;

    @FXML
    private TableView<Food> foodTable;

    @FXML
    private TableColumn<Food, String> nameCol;

    @FXML
    private TableColumn<Food, Number> priceCol;

    @FXML
    private TableColumn<Food, Number> costPriceCol;

    @FXML
    private TextField searchField;

    private ObservableList<Food> foodList = FXCollections.observableArrayList();
    private Food selectedFood = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryBox.setItems(FXCollections.observableArrayList("Drinks", "Meals", "Snacks", "Desserts", "Specials"));
        nameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getFoodName()));
        categoryCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCategory()));
        priceCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getPrice()));
        costPriceCol.setCellValueFactory(c -> new SimpleDoubleProperty(c.getValue().getCostPrice()));
        availableQuantityCol.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getAvailableQuantity()));

        availableQuantityCol.setCellFactory(column -> new TableCell<Food, Number>(){
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setStyle("-fx-border-color: #000000");
                } else {
                    setText(String.valueOf(item.intValue()));
                    if (item.intValue() <=10){
                        setStyle("-fx-text-fill: red; -fx-border-color: #000000");
                    } else {
                        setStyle("-fx-border-color: #000000");
                    }
                }
            }
        });

        foodTable.setItems(foodList);
        loadFood();

        foodTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedFood = newValue;
                nameField.setText(newValue.getFoodName());
                categoryBox.getSelectionModel().select(newValue.getCategory());
                priceField.setText(String.valueOf(newValue.getPrice()));
                costPriceField.setText(String.valueOf(newValue.getCostPrice()));
                quantityField.setText(String.valueOf(newValue.getAvailableQuantity()));
            }
        });
    }

    @FXML
    void goToAdminDashboard(ActionEvent event) {
        HelloApplication.changeScene("dashboard~admin");
    }

    @FXML
    void saveFoodEvent(ActionEvent event) {
         String foodName = nameField.getText();
         String category = categoryBox.getSelectionModel().getSelectedItem();
         double price = Double.parseDouble(priceField.getText());
         double costPrice = Double.parseDouble(costPriceField.getText());
         int availableQuantity = Integer.parseInt(quantityField.getText());

         try {
             Connection con = ConnectionSingleton.getConnection();
             if (selectedFood == null) {
                 String query = "INSERT INTO food (food_name, category, price, cost_price, available_quantity) VALUES (?,?,?,?,?)";
                 PreparedStatement ps = con.prepareStatement(query);
                 ps.setString(1, foodName);
                 ps.setString(2, category);
                 ps.setDouble(3, price);
                 ps.setDouble(4, costPrice);
                 ps.setInt(5, availableQuantity);
                 ps.executeUpdate();
             } else {
                 String query = "UPDATE food SET food_name = ?, category = ?, price = ?, cost_price = ? , available_quantity = ? WHERE id = ? ";
                 PreparedStatement ps = con.prepareStatement(query);
                 ps.setString(1, foodName);
                 ps.setString(2, category);
                 ps.setDouble(3, price);
                 ps.setDouble(4, costPrice);
                 ps.setInt(5, availableQuantity);
                 ps.setInt(6, selectedFood.getId());
                 ps.executeUpdate();
                 selectedFood = null;
             }
         } catch (SQLException e){
             System.out.println("Failed to save food");
             e.printStackTrace();
         }
         clearTable();
         loadFood();
    }

    @FXML
    void deleteEvent(MouseEvent event) {
         Food selectedFood = foodTable.getSelectionModel().getSelectedItem();
         if (selectedFood != null) {
             try {
                 Connection con = ConnectionSingleton.getConnection();
                 String query = "DELETE FROM food WHERE id = ?";
                 PreparedStatement ps = con.prepareStatement(query);
                 ps.setInt(1, selectedFood.getId());
                 ps.executeUpdate();
                 loadFood();
             } catch (SQLException e){
                 System.out.println("Failed to delete food");
                 e.printStackTrace();
             }
             clearTable();
         }
    }

    private void loadFood() {
        foodList.clear();
        try {
            Connection con = ConnectionSingleton.getConnection();
            ResultSet rs = con.createStatement().executeQuery("select * from food");
            while (rs.next()) {
                foodList.add(new Food(
                        rs.getInt("id"),
                        rs.getString("food_name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getDouble("cost_price"),
                        rs.getInt("available_quantity")
                ));
            }
        } catch (SQLException e){
            System.out.println("Failed to load food");
            e.printStackTrace();
        }
    }

    private void clearTable() {
        nameField.clear();
        categoryBox.getSelectionModel().clearSelection();
        priceField.clear();
        costPriceField.clear();
        quantityField.clear();
    }

    @FXML
    void searchKeyValue(KeyEvent event) {
        String searchValue = searchField.getText();
        ObservableList<Food> foods = FXCollections.observableArrayList();

        for (Food food : foodList) {
            if (food.getFoodName().toLowerCase().contains(searchValue.toLowerCase()) || food.getCategory().toLowerCase().contains(searchValue.toLowerCase()) || String.valueOf(food.getPrice()).toLowerCase().contains(searchValue.toLowerCase()) || String.valueOf(food.getCostPrice()).toLowerCase().contains(searchValue.toLowerCase()) ||String.valueOf(food.getAvailableQuantity()).toLowerCase().contains(searchValue.toLowerCase()) ) {
                foods.add(food);
            }
        }
        foodTable.setItems(foods);
    }

}
