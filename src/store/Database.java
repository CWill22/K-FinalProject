package store;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory; // Add this import statement

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;




public class Database {
    private List<Product> productList; // Database to store products

    // Constructor to initialize the database
    public Database() {
        this.productList = new ArrayList<>();
        initializeDatabase() ; // Initialize the database
    }

    // Initialize the database or data files like XML or JSON
    public void initializeDatabase() {
        try {
        	System.out.println("Loading database...");
            // Load XML file
            File xmlFile = new File("src/store/clothingInventory.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            // Extract product information from XML and add to the database
            NodeList nodeList = doc.getElementsByTagName("Product");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();
                    String type = element.getElementsByTagName("Type").item(0).getTextContent();
                    String brand = element.getElementsByTagName("Brand").item(0).getTextContent();
                    String color = element.getElementsByTagName("Color").item(0).getTextContent();
                    String size = element.getElementsByTagName("Size").item(0).getTextContent();
                    String material = element.getElementsByTagName("Material").item(0).getTextContent();
                    String gender = element.getElementsByTagName("Gender").item(0).getTextContent();
                    double price = Double.parseDouble(element.getElementsByTagName("Price").item(0).getTextContent());
                    int quantity = Integer.parseInt(element.getElementsByTagName("QuantityInStock").item(0).getTextContent());

                    // Create a new product, using the subclass contructer for its type,  and add it to the store
                  
                    if(type.equals("Crewneck")) {
                        Product product = new Crewneck(name, Brands.valueOf(brand), price, quantity, Size.valueOf(size), Color.valueOf(color),
                        Material.valueOf(material), Gender.valueOf(gender));
						productList.add(product);
                    }
                    else if(type.equals("TShirt")) {
                    	Product product = new TShirt(name, Brands.valueOf(brand), price, quantity, Size.valueOf(size), Color.valueOf(color),
                                Material.valueOf(material), Gender.valueOf(gender));
						productList.add(product);
                    }
                    else if(type.equals("Short")) {
                    	Product product = new Short(name, Brands.valueOf(brand), price, quantity, Size.valueOf(size), Color.valueOf(color),
                                Material.valueOf(material), Gender.valueOf(gender));
						productList.add(product);
                    }
                    else if(type.equals("Sock")) {
                    	Product product = new Sock(name, Brands.valueOf(brand), price, quantity, Size.valueOf(size), Color.valueOf(color),
                                Material.valueOf(material), Gender.valueOf(gender));
                        productList.add(product);	
                    }
                    else if(type.equals("Sweatshirt")) {
                    	Product product = new Sweatshirt(name, Brands.valueOf(brand), price, quantity, Size.valueOf(size), Color.valueOf(color),
                                Material.valueOf(material), Gender.valueOf(gender));
                        productList.add(product);
                    }
                    else if(type.equals("Sweatpant")) {
                    	Product product = new Sock(name, Brands.valueOf(brand), price, quantity, Size.valueOf(size), Color.valueOf(color),
                                Material.valueOf(material), Gender.valueOf(gender));
                        productList.add(product);
                    }
                    
                    
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Adds a new product record to the database or data file
    public void insertProduct(Product productData) {
        productList.add(productData);
    }

    // Updates an existing product’s data
    public void updateProduct(String productId, Product newData) {
        for (Product product : productList) {
            if (product.getName().equals(productId)) {
                // Update the product's data
                product.setName(newData.getName());
                product.setBrand(newData.getBrand());
                product.setPrice(newData.getPrice());
                product.setQuantity(newData.getQuantity());
                product.setSize(newData.getSize());
                product.setColor(newData.getColor());
                product.setMaterial(newData.getMaterial());
                product.setGender(newData.getGender());
                break; // Exit loop after updating the product
            }
        }
    }

    // Safely deletes a product record from the storage system
    public void removeProduct(String productId) {
        productList.removeIf(product -> product.getName().equals(productId));
    }

    // Retrieves products based on specific search criteria
    public List<Product> findProducts(Product filterCriteria) {
        List<Product> foundProducts = new ArrayList<>();
        for (Product product : productList) {
            // Check if the product matches the filter criteria
            if (matchesCriteria(product, filterCriteria)) {
                foundProducts.add(product);
            }
        }
        return foundProducts;
    }

    // Helper method to check if a product matches the filter criteria
    private boolean matchesCriteria(Product product, Product filterCriteria) {
        return product.getBrand().equals(filterCriteria.getBrand()) &&
                product.getColor().equals(filterCriteria.getColor()) &&
                product.getSize().equals(filterCriteria.getSize()) &&
                product.getMaterial().equals(filterCriteria.getMaterial()) &&
                product.getGender().equals(filterCriteria.getGender());
    }

    // Method to create a new product and add it to the inventory
    public void createProduct(Product productData) {
    	if (productData != null) {
    		// Add the product to the inventory
    		productList.add(productData);
    		System.out.println("Product '" + productData.getName() + "' added successfully.");
    	} else {
    		System.out.println("Null product data provided. Cannot add product.");
    	}
    }

    public void deleteProduct(String productId) {
        productList.removeIf(product -> product.getName().equals(productId));
    }

    // Method to list all the products in the inventory
    public List<Product> listProducts() {
    	// Check if the inventory is empty
    	if (productList.isEmpty()) {
            System.out.println("No products available in inventory.");
        }
    	// Return the list of products
    	return productList;
    }
    
    // Method to process an order for a specified product and quantity
    public void processOrder(String productId, int quantity) {
    	// Iterate through the products in the inventory
    	for (Product product : productList) {
    		// Check productId matches
    		if (product.getName().equals(productId)) {
    			// check if quantity is in stock 
    			if (product.getQuantity() >= quantity) {
    				
    				product.setQuantity(product.getQuantity() - quantity);
    				System.out.println("Order processed successfully for " + quantity + " " + productId);
    			} else {
    				System.out.println("Insufficient quantity in stock for " + productId);
    			}
    			return;
    		}
    	}
    	// If the product is not found in the inventory
    	System.out.println("Product not found: " + productId);
    }

    // Method to retrieve the list of products in the inventory
	public List<Product> getProductList() {
		return productList;
	}
	
}
   

