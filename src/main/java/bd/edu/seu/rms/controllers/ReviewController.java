package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ReviewController implements Initializable {

    @FXML
    private HBox reviewContainer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadReview();
    }

    private void loadReview() {
        reviewContainer.getChildren().clear();

        try {
            Connection con = ConnectionSingleton.getConnection();
            String query = "SELECT * FROM reviews ORDER BY id DESC";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String customerName = rs.getString("customer_name");
                String reviewText = rs.getString("comments");
                String rating = rs.getString("rating");

                HBox reviewCard = createReviewCard(customerName, reviewText, rating);

                reviewContainer.getChildren().add(reviewCard);
            }
        } catch (SQLException e){
            System.out.println("Failed to load review");
            e.printStackTrace();
        }
    }

    @FXML
    void goToAdminDashboard(MouseEvent event) {
        HelloApplication.changeScene("dashboard~admin");
    }

    private HBox createReviewCard(String customerName, String reviewText, String rating) {
        HBox reviewCard = new HBox();
        reviewCard.setSpacing(10);
        reviewCard.setPrefWidth(350);
        reviewCard.setPrefHeight(120);
        reviewCard.setMinWidth(180);


        reviewCard.setStyle("-fx-background-color:  #4a4a7a ; -fx-padding: 12; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #000000");
        VBox textContainer = new VBox();
        textContainer.setAlignment(Pos.CENTER);
        textContainer.setSpacing(10);

        Label customerNameLabel = new Label(customerName);
        customerNameLabel.setFont(Font.font("Arial", 16));
        customerNameLabel.setTextFill(Color.BLACK);

        Label ratingLabel = new Label("Rating: " + rating);
        ratingLabel.setFont(Font.font("Arial", 16));
        ratingLabel.setTextFill(Color.BLACK);

        Label commentsLabel = new Label(reviewText);
        commentsLabel.setWrapText(true);
        commentsLabel.setFont(Font.font("Arial", 18));
        commentsLabel.setMaxWidth(260);
        commentsLabel.setAlignment(Pos.CENTER);
        commentsLabel.setTextFill(Color.BLACK);
        VBox.setMargin(commentsLabel, new Insets(40,0,0,0));




        textContainer.getChildren().addAll(customerNameLabel, ratingLabel, commentsLabel);
        reviewCard.getChildren().addAll(textContainer);
        return reviewCard;
    }

}
