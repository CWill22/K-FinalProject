package store;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.List;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;

public class UIManager extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel loginPanel; //Creates the loginPanel
    private JTextField usernameField; //Creates a field for usernames
    private JPasswordField passwordField; //Creates a field for passwords
    private Database database;
    private HashMap<String, String> userMap;

    public UIManager() {
    	    	
    	initializeUserMapFile();
    	loadUserMapFromFile();
        setTitle("Mizzou Clothing Management System"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(866, 500);
        getContentPane().setLayout(new BorderLayout());
        
        database = new Database();
        userMap = new HashMap<>();

        // Create the login panel
        createLoginPanel();

        // Add login panel to the frame
        getContentPane().add(loginPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridLayout(0, 1));
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
        		String username = usernameField.getText();
        		String password = new String(passwordField.getPassword());

        		// Check if the username is available
        		if (isUsernameAvailable(username)) {
        			// Add the new user to the list
        			userMap.put(username, password);
        			
        			saveUserMapToFile();
        			
        			JOptionPane.showMessageDialog(UIManager.this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
        		} else {
        			// Show error message if the username is not available
        			JOptionPane.showMessageDialog(UIManager.this, "Username already exists", "Registration Failed", JOptionPane.ERROR_MESSAGE);
        		}
        	}
        });
        
        // Add a button to delete an account
        JButton deleteButton = new JButton("Delete Account");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = JOptionPane.showInputDialog(UIManager.this, "Enter username to delete:");
                if (username != null && !username.isEmpty()) {
                    deleteAccount(username);
                }
            }
        });
        loginPanel.add(deleteButton);
    }
    
    
    private boolean authenticate(String username, String password) {
        String storedPassword = userMap.get(username);
        return storedPassword != null && storedPassword.equals(password);
    }

    private boolean isUsernameAvailable(String username) {
        return !userMap.containsKey(username);
    }
    
    private void deleteAccount(String username) {
        if (userMap.containsKey(username)) {
            userMap.remove(username);
            saveUserMapToFile(); // Save updated user map to file
            JOptionPane.showMessageDialog(UIManager.this, "Account deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(UIManager.this, "Username not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

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

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.addActionListener(actionListener);
        return button;
    }

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
        
    	List<Product> products = database.getProductList();
        
        DefaultTableModel model = new DefaultTableModel();
        
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
        
        JTable table = new JTable(model);
        
        JScrollPane scrollPane = new JScrollPane(table);
        
    	
    	JFrame listProductsFrame = new JFrame("List Products");
    	listProductsFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        listProductsFrame.setSize(1024, 768);
        listProductsFrame.setVisible(true);
    }

    private void openUpdateProductFrame(ActionEvent e) {
        JFrame updateProductFrame = new JFrame("Update Product");

        // Create a table to display products
        List<Product> products = database.getProductList();
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Brand");
        model.addColumn("Price");
        model.addColumn("Quantity");
        model.addColumn("Size");
        model.addColumn("Color");
        model.addColumn("Gender");
        model.addColumn("Material");

        for (Product product : products) {
            model.addRow(new Object[]{
                    product.getName(),
                    product.getBrand(),
                    product.getPrice(),
                    product.getQuantity(),
                    product.getSize(),
                    product.getColor(),
                    product.getGender(),
                    product.getMaterial()
            });
        }

        JTable productTable = new JTable(model);

        // Create a scroll pane to hold the table
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Add the scroll pane to the frame
        updateProductFrame.add(scrollPane);

        // Create an 'Update' button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if (selectedRow != -1) {
                    updateSelectedProduct(selectedRow, model);
                } else {
                    JOptionPane.showMessageDialog(updateProductFrame, "Please select a product to update.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add the 'Update' button to the frame
        updateProductFrame.add(updateButton, BorderLayout.SOUTH);

        // Set frame properties and make it visible
        updateProductFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the frame
        updateProductFrame.setSize(getScreenWidth(), getScreenHeight());
        updateProductFrame.setVisible(true);
    }

    private void updateSelectedProduct(int row, DefaultTableModel model) {
        String productName = (String) model.getValueAt(row, 0);
        Product originalProduct = getProductByName(productName);

        if (originalProduct != null) {
            Brands brand = (Brands) model.getValueAt(row, 1);
            double price = (double) model.getValueAt(row, 2);
            int quantity = (int) model.getValueAt(row, 3);
            Size size = (Size) model.getValueAt(row, 4);
            Color color = (Color) model.getValueAt(row, 5);
            Gender gender = (Gender) model.getValueAt(row, 6);
            Material material = (Material) model.getValueAt(row, 7);

            Product updatedProduct = null;
            if (originalProduct instanceof Crewneck) {
                updatedProduct = new Crewneck(productName, brand, price, quantity, size, color, material, gender);
            } else if (originalProduct instanceof TShirt) {
                updatedProduct = new TShirt(productName, brand, price, quantity, size, color, material, gender);
            }
            // Add similar conditions for other product types

            if (updatedProduct != null) {
                database.updateProduct(productName, updatedProduct);
                JOptionPane.showMessageDialog(null, "Product updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Failed to update product: Unsupported product type", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Failed to update product: Product not found", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to retrieve a product from the database by its name
    private Product getProductByName(String productName) {
        List<Product> productList = database.getProductList();
        for (Product product : productList) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null; // Product not found
    }

        
    private void openProcessOrderFrame(ActionEvent e) {
        JFrame processOrderFrame = new JFrame("Process Order");
        // Add components for processing an order
        // Example: JTextFields, JLabels, JButtons, etc.
        // Example: processOrderFrame.add(new JLabel("Customer Name"));
        // Example: processOrderFrame.add(new JTextField());
        processOrderFrame.setSize(getScreenWidth(), getScreenHeight());
        processOrderFrame.setVisible(true);
    }

    private void logout(ActionEvent e) {
    	// Remove login panel
        getContentPane().removeAll();
        
        // Create and add main panel
        createLoginPanel();
        getContentPane().add(loginPanel, BorderLayout.CENTER);

        // Repaint the frame
        //revalidate();
        //repaint();
    	
    	
    	
    	
    	
    	//dispose();
    	
    	
    

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
    
    private static void initializeUserMapFile() {
        File userMapFile = new File("userMap.dat");
        try {
            if (!userMapFile.exists()) {
                userMapFile.createNewFile();
                System.out.println("User map file created successfully.");
            }
        } catch (IOException e) {
            System.err.println("Error creating user map file: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
	private void loadUserMapFromFile() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("userMap.dat"))) {
            userMap = (HashMap<String, String>) inputStream.readObject();
            System.out.println("User map loaded successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("File not found: userMap.dat");
        } catch (IOException e) {
            System.err.println("Error reading user map file: " + e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
        	System.err.println("Class not found: " + e.getMessage());
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UIManager();
            }
        });
    }
}
