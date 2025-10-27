package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.models.Review;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class CustomerReviewController implements Initializable {

    @FXML
    private TableColumn<Review, String> nameCol;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea reviewArea;

    @FXML
    private TableColumn<Review, String> reviewCol;

    @FXML
    private ComboBox<String> reviewStar;

    @FXML
    private TableView<Review> reviewTable;

    @FXML
    private TableColumn<Review, String> starCol;

    private ObservableList<Review> reviewList = FXCollections.observableArrayList();
    private Review selectedReview = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameField.setText(CustomerLoginController.loggedInUsername);
        reviewStar.setItems(FXCollections.observableArrayList("*" , "**" , "***" , "****" , "*****"));
        nameCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCustomer_name()));
        reviewCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getComments()));
        starCol.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getRating()));
        reviewTable.setItems(reviewList);
        loadReview();

        reviewTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedReview = newValue;
                nameField.setText(selectedReview.getCustomer_name());
                reviewArea.setText(selectedReview.getComments());
                reviewStar.setValue(selectedReview.getRating());
            }
        });
    }


    @FXML
    void goToOrderHistory(MouseEvent event) {
        HelloApplication.changeScene("customer~order~history");
    }

    @FXML
    void submitEvent(ActionEvent event) {
         String customerName = nameField.getText();
         String comment = reviewArea.getText();
         String rating = reviewStar.getSelectionModel().getSelectedItem();

         try {
             Connection con = ConnectionSingleton.getConnection();
             if(selectedReview == null){
                 String query = "INSERT INTO reviews (customer_name, comments, rating) VALUES (?, ?, ?)";
                 PreparedStatement ps = con.prepareStatement(query);
                 ps.setString(1, customerName);
                 ps.setString(2, comment);
                 ps.setString(3, rating);
                 ps.executeUpdate();
             } else {
                 String query = "UPDATE reviews SET comments=?, rating=? WHERE customer_name=?";
                 PreparedStatement ps = con.prepareStatement(query);
                 ps.setString(1, comment);
                 ps.setString(2, rating);
                 ps.setString(3, customerName);
                 ps.executeUpdate();
                 selectedReview = null;
             }
         } catch (SQLException e){
             System.out.println("Failed to load review");
             e.printStackTrace();
         }
         loadReview();
         clearFields();
    }

    @FXML
    void deleteEvent(MouseEvent event) {
         Review selectedReview = reviewTable.getSelectionModel().getSelectedItem();
         if(selectedReview != null){
             try {
                 Connection con = ConnectionSingleton.getConnection();
                 String query = "DELETE FROM reviews WHERE customer_name = ?";
                 PreparedStatement ps = con.prepareStatement(query);
                 ps.setString(1, selectedReview.getCustomer_name());
                 ps.executeUpdate();
                 loadReview();
             } catch (SQLException e){
                 System.out.println("Failed to load review");
                 e.printStackTrace();
             }
             clearFields();
         }
    }

    private void loadReview() {
        reviewList.clear();

        try {
            Connection con = ConnectionSingleton.getConnection();
            String sql = "SELECT * FROM reviews WHERE customer_name = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, nameField.getText());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                reviewList.add(new Review(
                        rs.getString("customer_name"),
                        rs.getString("comments"),
                        rs.getString("rating")
                ));

            }
        } catch (SQLException e){
            System.out.println("Failed to load review");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        reviewArea.clear();
        reviewStar.getSelectionModel().clearSelection();
        selectedReview = null;
    }

}
