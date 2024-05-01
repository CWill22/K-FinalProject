package store;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UIManager extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel loginPanel; //Creates the loginPanel
    private JTextField usernameField; //Creates a field for usernames
    private JPasswordField passwordField; //Creates a field for passwords
    private Database database;
    private ArrayList<User> userList;

    public UIManager() {
    	  
    	this.userList = new ArrayList<> ();   	
        setTitle("Mizzou Clothing Management System"); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(866, 500);
        getContentPane().setLayout(new BorderLayout());
        
        database = new Database();

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
        loginPanel.add(passwordField); //Add the password field to the loginPanel
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
        			userList.add(new User(username, password));
        			JOptionPane.showMessageDialog(UIManager.this, "Registration successful", "Success", JOptionPane.INFORMATION_MESSAGE);
        		} else {
        			// Show error message if the username is not available
        			JOptionPane.showMessageDialog(UIManager.this, "Username already exists", "Registration Failed", JOptionPane.ERROR_MESSAGE);
        		}
        	}
        });
    }
    
    private boolean authenticate(String username, String password) {
    	for (User user : userList) {
    		if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
    			return true; //User is found
    		}
    	}
    	return false;
    }
    
    //Check if username is available
    private boolean isUsernameAvailable(String username) {
    	for (User user : userList) {
    		if(user.getUsername().equals(username)) {
    			return false; //Username already exists
    		}
    	}
    	return true; //Username available
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
        listProductsFrame.setSize(getScreenWidth(), getScreenHeight());
        listProductsFrame.setVisible(true);
    }

    private void openUpdateProductFrame(ActionEvent e) {
        JFrame updateProductFrame = new JFrame("Update Product");
        
     // Create a table to display products
        JTable productTable = new JTable();

        // Create a scroll pane to hold the table
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Add the scroll pane to the frame
        updateProductFrame.add(scrollPane);

        // Create an 'Update' button
        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row and column
                int selectedRow = productTable.getSelectedRow();
                int selectedColumn = productTable.getSelectedColumn();

                // Retrieve the selected product from the table model
                Product selectedProduct = (Product) productTable.getValueAt(selectedRow, selectedColumn);

                // Show a dialog for updating the selected product
                showUpdateProductDialog(selectedProduct);
            }
        });

        // Add the 'Update' button to the frame
        updateProductFrame.add(updateButton, BorderLayout.SOUTH);

        // Set frame properties and make it visible
        updateProductFrame.setSize(getScreenWidth(), getScreenHeight());
        updateProductFrame.setVisible(true);
    }

    // Method to show a dialog for updating product details
    private void showUpdateProductDialog(Product product) {
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
            
            // Update the product in the database
            // database.updateProduct(selectedProductId, product); // Assuming you have a database object
        }
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
        dispose();
    }
    
    // Get the screen width
    private int getScreenWidth() {
        return Toolkit.getDefaultToolkit().getScreenSize().width;
    }

    // Get the screen height
    private int getScreenHeight() {
        return Toolkit.getDefaultToolkit().getScreenSize().height;
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
