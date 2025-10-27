package bd.edu.seu.rms.controllers;

import bd.edu.seu.rms.HelloApplication;
import bd.edu.seu.rms.utility.ConnectionSingleton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class RevenueDashboardController implements Initializable {

    @FXML
    private Label ordersLabel;

    @FXML
    private Label profitLabel;

    @FXML
    private Label revenueLabel;

    @FXML
    private BarChart<String, Number> barChart7days;

    @FXML
    private LineChart<String, Number> lineChart30Days;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        calculateTodayRevenue();

        XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
        barSeries.getData().add(new XYChart.Data<>("Sunday", 200000));
        barSeries.getData().add(new XYChart.Data<>("Monday", 300000));
        barSeries.getData().add(new XYChart.Data<>("Tuesday", 1100000));
        barSeries.getData().add(new XYChart.Data<>("Wednesday", 500000));
        barSeries.getData().add(new XYChart.Data<>("Thursday", 80000));
        barSeries.getData().add(new XYChart.Data<>("Friday", 400000));
        barSeries.getData().add(new XYChart.Data<>("Saturday", 1200000));

        barChart7days.getData().add(barSeries);

        XYChart.Series<String, Number> lineSeries = new XYChart.Series<>();
        lineSeries.getData().add(new XYChart.Data<>("5 Days", 200000));
        lineSeries.getData().add(new XYChart.Data<>("10 Days", 300000));
        lineSeries.getData().add(new XYChart.Data<>("15 Days", 100000));
        lineSeries.getData().add(new XYChart.Data<>("20 Days", 20000));
        lineSeries.getData().add(new XYChart.Data<>("25 Days", 500000));
        lineSeries.getData().add(new XYChart.Data<>("30 Days", 400000));

        lineChart30Days.getData().add(lineSeries);

    }

    @FXML
    void goToAdminDashboard(ActionEvent event) {
         HelloApplication.changeScene("dashboard~admin");
    }
    @FXML
    private void showBarchart() {
        barChart7days.setVisible(true);
        lineChart30Days.setVisible(false);
    }

    @FXML
    private void showLineChart() {
        lineChart30Days.setVisible(true);
        barChart7days.setVisible(false);
    }

    private void calculateTodayRevenue(){
        double totalRevenue = 0.0;
        double totalProfit = 0.0;
        int totalOrders = 0;

        try {
            Connection con = ConnectionSingleton.getConnection();
            String query = "select * from orders where order_date = CURDATE()";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String foodName = rs.getString("food_name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                double totalPrice = rs.getDouble("total_price");

                totalRevenue += totalPrice;
                totalOrders++;

                String q = " select cost_price from food where food_name = ?";
                PreparedStatement ps2 = con.prepareStatement(q);
                ps2.setString(1, foodName);
                ResultSet rs2 = ps2.executeQuery();

                if (rs2.next()) {
                    double costPrice = rs2.getDouble("cost_price");
                    totalProfit += (price - costPrice) * quantity;
                }
            }
            revenueLabel.setText(totalRevenue + "");
            ordersLabel.setText(String.valueOf(totalOrders));
            profitLabel.setText(totalProfit + "");

        } catch (SQLException e){
            System.out.println("Failed to calculate total revenue");
            e.printStackTrace();
        }
    }
}
