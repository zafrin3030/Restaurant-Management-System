package bd.edu.seu.rms.models;

public class Order {
    private int id;
    private int customerId;
    private String foodName;
    private int quantity;
    private double price;
    private double totalPrice;
    private String date;
    private String paymentMethod;
    private String specialNote;

    public Order() {

    }


    public Order(int id,  int customerId , String foodName, int quantity, double price, double totalPrice, String date, String paymentMethod, String specialNote) {
        this.id = id;
        this.customerId = customerId;
        this.foodName = foodName;
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = totalPrice;
        this.date = date;
        this.paymentMethod = paymentMethod;
        this.specialNote = specialNote;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }
}
