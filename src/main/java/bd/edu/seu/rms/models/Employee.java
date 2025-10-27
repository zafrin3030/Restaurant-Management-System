package bd.edu.seu.rms.models;

public class Employee {
    private int id;
    private String name;
    private String position;
    private String address;
    private String phone;
    private double salary;

    public Employee() {
    }

    public Employee(int id, String name, String position, String address, String phone, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.address = address;
        this.phone = phone;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
