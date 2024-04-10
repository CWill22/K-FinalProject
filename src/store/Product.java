package store;

public abstract class Product {
    private String name;
    private String brand;
    private double price;
    private int quantity;
    private String size;
    private String color;

    public Product(String name, String brand, double price, int quantity, SizeEnum size, ColorEnum color) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.quantity = quantity;
        this.size = size;
        this.color = color;
    }

    public abstract void displayDetails();

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public SizeEnum getSize() {
        return size;
    }

    public ColorEnum getColor() {
        return color;
    }
}
