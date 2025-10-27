package bd.edu.seu.rms.models;

public class CartItem {
    private Food food;
    private int quantity;

    public CartItem() {
    }

    public CartItem(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return quantity * food.getPrice();
    }

    @Override
    public String toString() {
      return food.getFoodName() + " -Quantity: " + quantity + " -Price: " + getTotalPrice();
    }
}
