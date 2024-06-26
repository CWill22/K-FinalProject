package store;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UIManager extends JFrame {
    

    private static final long serialVersionUID = 1L; //Serial version ID
    private JPanel loginPanel; //Creates the loginPanel
    private JTextField usernameField; //Creates a field for usernames
    private JPasswordField passwordField; //Creates a field for passwords
    private Database database;
    private HashMap<String, String> userMap; //Creates a map to store users
    private List<OrderItem> orderItems;

    public UIManager() {
    	    	
    	orderItems = new ArrayList<>(); // Initialize the list of order items
    	
        setTitle("Mizzou Clothing Management System"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(866, 500);
        getContentPane().setLayout(new BorderLayout());
        
        database = new Database();
        userMap = new HashMap<>(); // Initialize the user map

        // Create the login panel
        createLoginPanel();

        // Add login panel to the frame
        getContentPane().add(loginPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridLayout(0, 1)); //Creates the loginPanel
        usernameField = new JTextField(); //Creates the field for users to input a user name
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField = new JPasswordField(); //Creates the field for users to input a password
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        //Add labels for username and password
        JLabel label = new JLabel("Username: ");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(label);
        loginPanel.add(usernameField); //Adds the user name Field to the loginPanel
        JLabel label_1 = new JLabel("Password: ");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(label_1);
        loginPanel.add(passwordField);//Add the password field to the loginPanel
        JLabel spacingLabel = new JLabel(" ");
        loginPanel.add(spacingLabel);
        loginPanel.add(loginButton);
        loginPanel.add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	// Retrieve the username and password entered by the user
            	String username = usernameField.getText();
            	String password = new String(passwordField.getPassword());

            	// Perform authentication
            	boolean authenticated = authenticate(username, password);
            	if (authenticated) {
            		// If authentication succeeds, switch to main panel
            		switchToMainPanel();
            	} else {
            		// If authentication fails, show error message 
            		JOptionPane.showMessageDialog(UIManager.this, "Invalid username or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            	}
            }
        });

        registerButton.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		// Retrieve the username and password entered by the user
        		String username = usernameField.getText();
        		String password = new String(passwordField.getPassword());

        		// Check if the username is available
        		if (createAccount(username, password)) {
        			// Add the new user to the list
        			JOptionPane.showMessageDialog(UIManager.this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
        		} else {
        			// Show error message if the username is not available
        			JOptionPane.showMessageDialog(UIManager.this, "Username already exists", "Registration Failed", JOptionPane.ERROR_MESSAGE);
        		}
        	}

			private boolean createAccount(String username, String password) {
				// Check if the username is available
	        	if (isUsernameAvailable(username)) {
	        		// Add the new user to the list
	        		userMap.put(username, password);
	        		
	        		saveUserMapToFile();
	        		return true;
	        	} else {
	        		return false;
	        		
	        	}
			}
        });
        
      
        
        // Add a button to delete an account
        JButton deleteButton = new JButton("Delete Account");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(UIManager.this, "Enter username to delete:");
                // Check if the username is not null or empty
                if (username != null && !username.isEmpty()) {
                    deleteAccount(username);
                }
            }
        });
        loginPanel.add(deleteButton);
    }
    
    
    // Method to authenticate a user based on username and password
    private boolean authenticate(String username, String password) {
        String storedPassword = userMap.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    // Method to check if a username is available
    private boolean isUsernameAvailable(String username) {
        return !userMap.containsKey(username);
    }
    
    // Method to delete an account based on the username
    private void deleteAccount(String username) {
        if (userMap.containsKey(username)) {
            userMap.remove(username);
            saveUserMapToFile(); // Save updated user map to file
            JOptionPane.showMessageDialog(UIManager.this, "Account deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(UIManager.this, "Username not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to switch to the main panel, which contains the main functionality like List Products, Add Product, etc.
    private void switchToMainPanel() {
        // Remove login panel
        getContentPane().removeAll();
        
        // Create and add main panel
        JPanel mainPanel = createMainPanel();
        getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Repaint the frame
        revalidate();
        repaint();
    }
    
    // Method to create the main panel with buttons for different actions
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(5, 1));

        // Add buttons to main panel
        mainPanel.add(createButton("List Products", this::openListProductsFrame));
        mainPanel.add(createButton("Add Product", this::openAddProductFrame));
        mainPanel.add(createButton("Update Product", this::openUpdateProductFrame));
        mainPanel.add(createButton("Process Order", this::openProcessOrderFrame));
        mainPanel.add(createButton("Logout", this::logout));

        return mainPanel;
    }

    // Method to create a button with the specified text and action listener
    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

    // Method to open a frame for adding a new product
    private void openAddProductFrame(ActionEvent e) {
        JFrame addProductFrame = new JFrame("Add Product");
       
        // Create text fields for each attribute of the product
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField quantityField = new JTextField();
        JComboBox<Brands> brandComboBox = new JComboBox<>(Brands.values());
        JComboBox<Size> sizeComboBox = new JComboBox<>(Size.values());
        JComboBox<Color> colorComboBox = new JComboBox<>(Color.values());
        JComboBox<Material> materialComboBox = new JComboBox<>(Material.values());
        JComboBox<Gender> genderComboBox = new JComboBox<>(Gender.values());
        JComboBox<String> productTypeComboBox = new JComboBox<>();
        // Add product types to the combo box
        productTypeComboBox.addItem("Crewneck");
        productTypeComboBox.addItem("TShirt");
        productTypeComboBox.addItem("Short");
        productTypeComboBox.addItem("Sock");
        productTypeComboBox.addItem("Sweatshirt");
        productTypeComboBox.addItem("Sweatpant");

        // Add labels for each field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel brandLabel = new JLabel("Brand:");
        brandLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel sizeLabel = new JLabel("Size:");
        sizeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel colorLabel = new JLabel("Color:");
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel materialLabel = new JLabel("Material:");
        materialLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel typeLabel = new JLabel("Type:");
        typeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        

        // Create the 'Add' button
        JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retrieve values from text fields and combo boxes
                String name = nameField.getText();
                Brands brand = (Brands) brandComboBox.getSelectedItem();
                double price;
                int quantity;
                Size size = (Size) sizeComboBox.getSelectedItem();
                Color color = (Color) colorComboBox.getSelectedItem();
                Material material = (Material) materialComboBox.getSelectedItem();
                Gender gender = (Gender) genderComboBox.getSelectedItem();
                
                // Validate and parse price
                try {
                    price = Double.parseDouble(priceField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(UIManager.this, "Invalid price format. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Stop processing further
                }

                // Validate and parse quantity
                try {
                    quantity = Integer.parseInt(quantityField.getText());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(UIManager.this, "Invalid quantity format. Please enter a valid integer.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Stop processing further
                }

             // Determine the selected product type
                String productType = (String) productTypeComboBox.getSelectedItem();

                // Create an instance of the appropriate subclass based on the selected product type
                Product newProduct = null;
                switch (productType) {
                    case "Crewneck":
                        newProduct = new Crewneck(name, brand, price, quantity, size, color, material, gender);
                        break;
                    case "TShirt":
                        newProduct = new TShirt(name, brand, price, quantity, size, color, material, gender);
                        break;
                    case "Short":
                        newProduct = new Short(name, brand, price, quantity, size, color, material, gender);
                        break;
                    case "Sock":
                        newProduct = new Sock(name, brand, price, quantity, size, color, material, gender);
                        break;
                    case "Sweatshirt":
                        newProduct = new Sweatshirt(name, brand, price, quantity, size, color, material, gender);
                        break;
                    case "Sweatpant":
                        newProduct = new Sweatpant(name, brand, price, quantity, size, color, material, gender);
                        break;
                    default:
                    	JOptionPane.showMessageDialog(UIManager.this, "Invalid product type selected.", "Error", JOptionPane.ERROR_MESSAGE);
                        break;
                }
                
                // Add the new product to your database or list
                database.insertProduct(newProduct);
            }
        });

        // Create a panel to hold the components
        JPanel panel = new JPanel(new GridLayout(10, 2));
        // Add components to the panel
        panel.add(typeLabel);
        panel.add(productTypeComboBox);
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(brandLabel);
        panel.add(brandComboBox);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(sizeLabel);
        panel.add(sizeComboBox);
        panel.add(colorLabel);
        panel.add(colorComboBox);
        panel.add(materialLabel);
        panel.add(materialComboBox);
        panel.add(genderLabel);
        panel.add(genderComboBox);
        panel.add(addButton); // Add the 'Add' button

        // Add the panel to the frame
        addProductFrame.add(panel);
        
        addProductFrame.setSize(getScreenWidth(), getScreenHeight());
        addProductFrame.setVisible(true);
    }

    private void openListProductsFrame(ActionEvent e) {
        
    	// Retrieve the list of products from the database
    	List<Product> products = database.getProductList();
        
        DefaultTableModel model = new DefaultTableModel();
        
        // Add columns to the table model
        model.addColumn("Name");
        model.addColumn("Brand");
        model.addColumn("Color");
        model.addColumn("Size");
        model.addColumn("Material");
        model.addColumn("Gender");
        model.addColumn("Price");
        model.addColumn("Quantity");
        
        for (Product product : products) {
        	model.addRow(new Object[] {
        			product.getName(),
        			product.getBrand(),
        			product.getColor(),
        			product.getSize(),
        			product.getMaterial(),
        			product.getGender(),
        			product.getPrice(),
        			product.getQuantity()
        	});
        }
        
        // Create a JTable with the table model
        JTable table = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
     // Create a button to delete a product
        JButton deleteButton = new JButton("Delete Product");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the name of the product in the selected row
                    String productName = (String) table.getValueAt(selectedRow, 0);
                    // Remove the product from the database
                    database.removeProduct(productName);
                    // Remove the selected row from the table
                    model.removeRow(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a product to delete.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Create a panel to hold the table and delete button
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(deleteButton, BorderLayout.SOUTH);
        
        
        // Create a frame to display the list of products
    	JFrame listProductsFrame = new JFrame("List Products");
    	listProductsFrame.getContentPane().add(panel, BorderLayout.CENTER);
        listProductsFrame.setSize(1024, 768);
        listProductsFrame.setVisible(true);
    }

    private void openUpdateProductFrame(ActionEvent e) {
        JFrame updateProductFrame = new JFrame("Update Product");
        
        // Create a table model with columns for product attributes
        DefaultTableModel model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

			// Override the isCellEditable method to prevent cell editing
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };
        // Add columns to the table model
        model.addColumn("Name");
        model.addColumn("Brand");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Size");
        model.addColumn("Color");
        model.addColumn("Material");
        model.addColumn("Gender");
        
        // Retrieve the list of products from the database
        List<Product> products = database.getProductList();
        
        // Add each product to the table model
        for (Product product : products) {
            model.addRow(new Object[] {
                product.getName(),
                product.getBrand(),
                product.getPrice(),
                product.getQuantity(),
                product.getSize(),
                product.getColor(),
                product.getMaterial(),
                product.getGender()
            });
        }
        
        // Create a JTable with the table model
        JTable productTable = new JTable(model);
        
        // Create a scroll pane to hold the table
        JScrollPane scrollPane = new JScrollPane(productTable);
        
        // Create an 'Update' button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    // Retrieve the selected product from the table model
                    Product selectedProduct = products.get(selectedRow);
                    
                    // Show a dialog for updating the selected product
                    boolean updated = showUpdateProductDialog(selectedProduct);
                    if (updated) {
                        // Update the product in the database
                        database.updateProduct(selectedProduct.getName(), selectedProduct);
                        
                        // Refresh the table model
                        model.setValueAt(selectedProduct.getName(), selectedRow, 0);
                        model.setValueAt(selectedProduct.getBrand(), selectedRow, 1);
                        model.setValueAt(selectedProduct.getPrice(), selectedRow, 2);
                        model.setValueAt(selectedProduct.getQuantity(), selectedRow, 3);
                        model.setValueAt(selectedProduct.getSize(), selectedRow, 4);
                        model.setValueAt(selectedProduct.getColor(), selectedRow, 5);
                        model.setValueAt(selectedProduct.getMaterial(), selectedRow, 6);
                        model.setValueAt(selectedProduct.getGender(), selectedRow, 7);
                    }
                } else {
                    JOptionPane.showMessageDialog(updateProductFrame, "Please select a product to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Add components to the frame
        updateProductFrame.add(scrollPane, BorderLayout.CENTER);
        updateProductFrame.add(updateButton, BorderLayout.SOUTH);
        
        // Set frame properties and make it visible
        updateProductFrame.setSize(800, 600); // Set a preferred size
        updateProductFrame.setLocationRelativeTo(null); // Center the frame on the screen
        updateProductFrame.setVisible(true);
    }

    // Method to show a dialog for updating product details
    private boolean showUpdateProductDialog(Product product) {
        // Create text fields and combo boxes for each attribute of the product
        JTextField nameField = new JTextField(product.getName());
        JTextField priceField = new JTextField(String.valueOf(product.getPrice()));
        JTextField quantityField = new JTextField(String.valueOf(product.getQuantity()));
        JComboBox<Brands> brandComboBox = new JComboBox<>(Brands.values());
        brandComboBox.setSelectedItem(product.getBrand());
        JComboBox<Size> sizeComboBox = new JComboBox<>(Size.values());
        sizeComboBox.setSelectedItem(product.getSize());
        JComboBox<Color> colorComboBox = new JComboBox<>(Color.values());
        colorComboBox.setSelectedItem(product.getColor());
        JComboBox<Material> materialComboBox = new JComboBox<>(Material.values());
        materialComboBox.setSelectedItem(product.getMaterial());
        JComboBox<Gender> genderComboBox = new JComboBox<>(Gender.values());
        genderComboBox.setSelectedItem(product.getGender());
        
        // Create labels for each field
        JLabel nameLabel = new JLabel("Name:");
        JLabel brandLabel = new JLabel("Brand:");
        JLabel priceLabel = new JLabel("Price:");
        JLabel quantityLabel = new JLabel("Quantity:");
        JLabel sizeLabel = new JLabel("Size:");
        JLabel colorLabel = new JLabel("Color:");
        JLabel materialLabel = new JLabel("Material:");
        JLabel genderLabel = new JLabel("Gender:");
        
        // Create a panel to hold the components
        JPanel panel = new JPanel(new GridLayout(9, 2));
        
        // Add components to the panel
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(brandLabel);
        panel.add(brandComboBox);
        panel.add(priceLabel);
        panel.add(priceField);
        panel.add(quantityLabel);
        panel.add(quantityField);
        panel.add(sizeLabel);
        panel.add(sizeComboBox);
        panel.add(colorLabel);
        panel.add(colorComboBox);
        panel.add(materialLabel);
        panel.add(materialComboBox);
        panel.add(genderLabel);
        panel.add(genderComboBox);
        
        // Show the dialog
        int result = JOptionPane.showConfirmDialog(null, panel, "Update Product", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            // Update the product with the new values
            product.setName(nameField.getText());
            product.setBrand((Brands) brandComboBox.getSelectedItem());
            product.setPrice(Double.parseDouble(priceField.getText()));
            product.setQuantity(Integer.parseInt(quantityField.getText()));
            product.setSize((Size) sizeComboBox.getSelectedItem());
            product.setColor((Color) colorComboBox.getSelectedItem());
            product.setMaterial((Material) materialComboBox.getSelectedItem());
            product.setGender((Gender) genderComboBox.getSelectedItem());
            
            return true; // Return true indicating successful update
        }
        
        return false; // Return false indicating update canceled
    }

    private void openProcessOrderFrame(ActionEvent e) {
    	/// Create a frame to display the process order panel
        JFrame processOrderFrame = new JFrame("Process Order");
        JPanel panel = new JPanel(new BorderLayout());

        // Get the list of products from the database
        List<Product> products = database.getProductList();

        // Create a table model to display product information
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Brand");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Size");
        model.addColumn("Color");
        model.addColumn("Material");
        model.addColumn("Gender");
        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getName(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getSize(),
                    product.getColor(),
                    product.getMaterial(),
                    product.getGender()
            });
        }

        // Create a table with the product information
        JTable table = new JTable(model);

        // Set the table to non-editable
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.setDefaultEditor(table.getColumnClass(i), null);
        }
        
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a button to process the order
        JButton processButton = new JButton("Process Order");
        JLabel totalCostLabel = new JLabel("Total Cost: $0.0");
        processButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get the quantity of the selected product
                    int quantity = (int) table.getValueAt(selectedRow, 3);
                    // If quantity is greater than 0, decrease it by 1 and update the table
                    if (quantity > 0) {
                        table.setValueAt(quantity - 1, selectedRow, 3);
                        // Update the quantity in the database
                        String productName = (String) table.getValueAt(selectedRow, 0);
                        database.processOrder(productName, 1);
                        
                        orderItems.add(new OrderItem(productName, 1));
                        
                        double totalCost = calculateTotalOrderCost(orderItems);
                        totalCostLabel.setText("Total Cost: $" + totalCost);
                        System.out.println("Total Order Cost: " + totalCost);
                    } else {
                        JOptionPane.showMessageDialog(processOrderFrame, "Product is currently out of stock.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(processOrderFrame, "Please select a product to order.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components to the panel
        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(processButton, BorderLayout.SOUTH);
        panel.add(totalCostLabel, BorderLayout.NORTH);

        // Add the panel to the frame
        processOrderFrame.getContentPane().add(panel);

        // Set frame properties
        processOrderFrame.setSize(600, 300);
        processOrderFrame.setLocationRelativeTo(null);
        processOrderFrame.setVisible(true);
    }
        
    private void logout(ActionEvent e) {
    	// Remove login panel
        getContentPane().removeAll();
        
        // Create and add main panel
        createLoginPanel();
        getContentPane().add(loginPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
        
    }
    
    // Get the screen width
    private int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    // Get the screen height
    private int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
    }
    
 // Method to calculate the total cost of an order based on selected products
 	public double calculateTotalOrderCost(List<OrderItem> orderItems) {
 		double totalCost = 0.0;
 		
 		for (OrderItem orderItem : orderItems) {
 			String productId = orderItem.getProductId();
 			int quantitiy = orderItem.getQuantity();
 			
 			// Find the product in the inventory
 			Product product = getProductById(productId);
 			
 			if (product != null) {
 				// Calculate the subtotal for the order item
 				double subtotal = product.getPrice() * quantitiy;
 				totalCost += subtotal;
 			} else {
 				System.out.println("Product not found: " + productId);
 			}
 		}
 		
 		return totalCost;
 	}
 	
 	// Helper method to retrieve a product by its ID
 	private Product getProductById(String productId) {
 		List<Product> products = database.getProductList();
 		for (Product product : products) {
 			if (product.getName().equals(productId)) {
 				return product;
 			}
 		}
 		return null; // Product not found
 	}
 	
 // Class representing an item in an order
 	public class OrderItem {
 		private String productId;
 		private int quantity;
 		// Constructor
 		public OrderItem(String productId, int quantity) {
 			this.productId = productId;
 			this.quantity = quantity;
 		}
 		
 		// Getters
 		public String getProductId() {
 			return productId;
 		}
 		
 
 		public int getQuantity() {
 			return quantity;
 		}
 	}
    
    private void saveUserMapToFile() {
        try {
        	// Create an output stream to write objects to a file
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("userMap.dat"));

            // Write the user map object to the file
            outputStream.writeObject(userMap);

            // Close the output stream
            outputStream.close();

            // Inform user
            System.out.println("User map saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing user map file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}