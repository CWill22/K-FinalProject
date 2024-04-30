package store;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    public UIManager() {
    	  
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

        //Add labels for username and password
        JLabel label = new JLabel("Username: ");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(label);
        loginPanel.add(usernameField); //Adds the user name Field to the loginPanel
        JLabel label_1 = new JLabel("Password: ");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(label_1);
        loginPanel.add(passwordField); //Add the pssword field to the loginPanel
        loginPanel.add(loginButton);

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
    }

    private boolean authenticate(String username, String password) { //Set the user name and password here
        // Replace with authentication logic
        return "admin".equals(username) && "password".equals(password);
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
        // Add components for adding a new product
        // Example: JTextFields, JLabels, JButtons, etc.
        // Example: addProductFrame.add(new JLabel("Product Name"));
        // Example: addProductFrame.add(new JTextField());
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
        
        updateProductFrame.setSize(getScreenWidth(), getScreenHeight());
        updateProductFrame.setVisible(true);
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
