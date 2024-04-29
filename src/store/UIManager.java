package store;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UIManager extends JFrame {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private JPanel loginPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UIManager() {
        setTitle("Mizzou Clothing Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLayout(new BorderLayout());

        // Create the login panel
        createLoginPanel();

        // Add login panel to the frame
        add(loginPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridLayout(3, 2));
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginPanel.add(new JLabel("Username:"));
        loginPanel.add(usernameField);
        loginPanel.add(new JLabel("Password:"));
        loginPanel.add(passwordField);
        loginPanel.add(new JLabel()); // Placeholder for empty cell
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

    private boolean authenticate(String username, String password) {
        // Replace with your authentication logic
        return "admin".equals(username) && "password".equals(password);
    }

    private void switchToMainPanel() {
        // Remove login panel
        getContentPane().removeAll();
        
        // Create and add main panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // Repaint the frame
        revalidate();
        repaint();
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridLayout(5, 1));

        JButton addButton = new JButton("Add Product");
        JButton listButton = new JButton("List Products");
        JButton updateButton = new JButton("Update Product");
        JButton processOrderButton = new JButton("Process Order");
        JButton logoutButton = new JButton("Logout");
        
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openAddProductFrame();
            }
        });

        listButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openListProductsFrame();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUpdateProductFrame();
            }
        });

        processOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openProcessOrderFrame();
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logout();
                dispose();
            }
        });

        mainPanel.add(addButton);
        mainPanel.add(listButton);
        mainPanel.add(updateButton);
        mainPanel.add(processOrderButton);
        mainPanel.add(logoutButton);

        // Add action listeners for main panel buttons

        return mainPanel;
    }
    
    private void openAddProductFrame() {
        JFrame addProductFrame = new JFrame("Add Product");
        // Add components for adding a new product
        // Example: JTextFields, JLabels, JButtons, etc.
        // Example: addProductFrame.add(new JLabel("Product Name"));
        // Example: addProductFrame.add(new JTextField());
        addProductFrame.setSize(300, 200);
        addProductFrame.setVisible(true);
    }

    private void openListProductsFrame() {
        JFrame listProductsFrame = new JFrame("List Products");
        // Add components for displaying a list of products
        // Example: JList, JTable, etc.
        listProductsFrame.setSize(400, 300);
        listProductsFrame.setVisible(true);
    }

    private void openUpdateProductFrame() {
        JFrame updateProductFrame = new JFrame("Update Product");
        // Add components for updating an existing product
        // Example: JTextFields, JLabels, JButtons, etc.
        // Example: updateProductFrame.add(new JLabel("Product ID"));
        // Example: updateProductFrame.add(new JTextField());
        updateProductFrame.setSize(300, 200);
        updateProductFrame.setVisible(true);
    }

    private void openProcessOrderFrame() {
        JFrame processOrderFrame = new JFrame("Process Order");
        // Add components for processing an order
        // Example: JTextFields, JLabels, JButtons, etc.
        // Example: processOrderFrame.add(new JLabel("Customer Name"));
        // Example: processOrderFrame.add(new JTextField());
        processOrderFrame.setSize(300, 200);
        processOrderFrame.setVisible(true);
    }

    private void logout() {
        // Implementation for logging out
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
