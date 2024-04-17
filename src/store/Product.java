package store;

public abstract class Product {
    protected String name;
    protected String brand;
    protected double price;
    protected int quantity;
    protected String size;
    protected String color;
    protected Gender gender;
    protected Material material;

    public Product(String name, String brand, double price, int quantity, String size, String color, Material material, Gender gender) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
        this.material = material;
        this.gender = gender;
    }

    public abstract void displayDetails();
    //getters and setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material) {
        this.material = material;
    }
}