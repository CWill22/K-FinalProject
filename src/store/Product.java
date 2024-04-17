package store;

public abstract class Product {
    protected String name;
    protected Brands brand;
    protected double price;
    protected int quantity;
    protected Size size;
    protected Color color;
    protected Gender gender;
    protected Material material;

    public Product(String name, Brands brand, double price, int quantity, Size size, Color color, Material material, Gender gender) {
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

    // Getters and setters for the attributes
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brands getBrand() {
        return brand;
    }

    public void setBrand(Brands brand) {
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

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
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
