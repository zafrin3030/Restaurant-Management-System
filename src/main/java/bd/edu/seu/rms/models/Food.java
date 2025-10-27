package bd.edu.seu.rms.models;

public class Food {
    private int id;
    private String foodName;
    private String category;
    private double price;
    private double costPrice;
    private int availableQuantity;

    public Food() {
    }

    public Food(int id , String foodName, String category, double price, double costPrice,  int availableQuantity) {
        this.id = id;
        this.foodName = foodName;
        this.category = category;
        this.price = price;
        this.costPrice = costPrice;
        this.availableQuantity = availableQuantity;
    }

    public double getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(double costPrice) {
        this.costPrice = costPrice;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }
}
