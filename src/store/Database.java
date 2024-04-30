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
    }

    // Initialize the database or data files like XML or JSON
    public void initializeDatabase() {
        try {
            // Load XML file
            File xmlFile = new File("clothinginventory.xml");
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
                   //Product product = new Product(name, brand, color, size, material, gender, price, quantity);
                    //Product product = (name, brand, price,quantity, size, color, material, gender)
                    
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
    public void updateProductone(String productId, Product newData) {
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

    public void createProduct(Product productData) {
	    productList.add(productData);
    }

    public void deleteProduct(String productId) {
        productList.removeIf(product -> product.getName().equals(productId));
    }
<<<<<<< HEAD

    public List<Product> getProductList() {
        return productList; //Getter for Product List
=======
    //I don't know why this created twice
    /* 
    public void updateProduct(String productId, Product newData) {
    	boolean found = false;
    	for (Product product : productList) {
    		if (product.getName().equals(productId)) {
    			product.setName(newData.getName());
                product.setBrand(newData.getBrand());
                product.setPrice(newData.getPrice());
                product.setQuantity(newData.getQuantity());
                product.setSize(newData.getSize());
                product.setColor(newData.getColor());
                product.setMaterial(newData.getMaterial());
                product.setGender(newData.getGender());
                found = true;
                break;
    		}
    	}
    	if (!found) {
    		System.out.println("Product not found: " + productId);
    	}
    }
    */
    public List<Product> listProducts() {
    	return productList;
    }
    
    
    public void processOrder(String productId, int quantity) {
    	for (Product product : productList) {
    		if (product.getName().equals(productId)) {
    			
    			if (product.getQuantity() >= quantity) {
    				
    				product.setQuantity(product.getQuantity() - quantity);
    				System.out.println("Order processed successfully for " + quantity + " " + productId);
    			} else {
    				System.out.println("Insufficient quantity in stock for " + productId);
    			}
    			return;
    		}
    	}
    	System.out.println("Product not found: " + productId);
>>>>>>> branch 'main' of https://github.com/CWill22/K-FinalProject
    }
}
   

