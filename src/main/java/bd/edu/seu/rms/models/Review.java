package bd.edu.seu.rms.models;

public class Review {
    private String customer_name;
    private String comments;
    private String rating;

    public Review() {
    }

    public Review(String customer_name, String comments, String rating) {
        this.customer_name = customer_name;
        this.comments = comments;
        this.rating = rating;
    }


    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
