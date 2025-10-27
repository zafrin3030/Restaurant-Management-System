package bd.edu.seu.rms.models;

public class Customer {
    private int id;
    private String fullName;
    private String username;
    private String phoneNumber;
    private String address;
    private String gender;
    private String password;

    public Customer() {
    }

    public Customer(int id, String fullName, String username, String phoneNumber, String address, String gender, String password) {
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
