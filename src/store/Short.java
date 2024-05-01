package store;

public class Short extends Product {

    public Short(String name, Brands brand, double price, int quantity, Size size, Color color, Material material, Gender gender) {
        super(name, brand, price, quantity, size, color, material, gender);
    }

    @Override
    public void displayDetails() {
        System.out.println("Name: " + getName());
        System.out.println("Brand: " + getBrand());
        System.out.println("Price: $" + getPrice());
        System.out.println("Quantity: " + getQuantity());
        System.out.println("Size: " + getSize());
        System.out.println("Color: " + getColor());
        System.out.println("Material: " + getMaterial());
        System.out.println("Gender: " + getGender());
    }
}

