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
        setSize(866, 500);
        getContentPane().setLayout(new BorderLayout());

        // Create the login panel
        createLoginPanel();

        // Add login panel to the frame
        getContentPane().add(loginPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void createLoginPanel() {
        loginPanel = new JPanel(new GridLayout(0, 1));
        usernameField = new JTextField();
        usernameField.setHorizontalAlignment(SwingConstants.CENTER);
        passwordField = new JPasswordField();
        passwordField.setHorizontalAlignment(SwingConstants.CENTER);
        JButton loginButton = new JButton("Login");

        JLabel label = new JLabel("Username: ");
        label.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(label);
        loginPanel.add(usernameField);
        JLabel label_1 = new JLabel("Password: ");
        label_1.setHorizontalAlignment(SwingConstants.CENTER);
        loginPanel.add(label_1);
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
        mainPanel.add(createButton("Add Product", this::openAddProductFrame));
        mainPanel.add(createButton("List Products", this::openListProductsFrame));
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
        addProductFrame.setSize(800, 600);
        addProductFrame.setVisible(true);
    }

    private void openListProductsFrame(ActionEvent e) {
        JFrame listProductsFrame = new JFrame("List Products");
        // Add components for displaying a list of products
        // Example: JList, JTable, etc.
        listProductsFrame.setSize(800, 600);
        listProductsFrame.setVisible(true);
    }

    private void openUpdateProductFrame(ActionEvent e) {
        JFrame updateProductFrame = new JFrame("Update Product");
        // Add components for updating an existing product
        // Example: JTextFields, JLabels, JButtons, etc.
        // Example: updateProductFrame.add(new JLabel("Product ID"));
        // Example: updateProductFrame.add(new JTextField());
        updateProductFrame.setSize(800, 600);
        updateProductFrame.setVisible(true);
    }

    private void openProcessOrderFrame(ActionEvent e) {
        JFrame processOrderFrame = new JFrame("Process Order");
        // Add components for processing an order
        // Example: JTextFields, JLabels, JButtons, etc.
        // Example: processOrderFrame.add(new JLabel("Customer Name"));
        // Example: processOrderFrame.add(new JTextField());
        processOrderFrame.setSize(800, 600);
        processOrderFrame.setVisible(true);
    }

    private void logout(ActionEvent e) {
        dispose();
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
