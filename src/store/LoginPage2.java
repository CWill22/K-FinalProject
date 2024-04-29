package store;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginPage2 {

    JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private Customer customer;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    LoginPage2 window = new LoginPage2();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public LoginPage2() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 450, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[] { 145, 39, 11, 11, 83, 0 };
        gridBagLayout.rowHeights = new int[] { 29, 0, 0, 0, 0, 0, 0, 0 };
        gridBagLayout.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
        frame.getContentPane().setLayout(gridBagLayout);

        JLabel lblNewLabel = new JLabel("Log In");
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.anchor = GridBagConstraints.WEST;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel.gridx = 2;
        gbc_lblNewLabel.gridy = 0;
        frame.getContentPane().add(lblNewLabel, gbc_lblNewLabel);

        JLabel lblNewLabel_1 = new JLabel("Username");
        GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
        gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_1.gridx = 1;
        gbc_lblNewLabel_1.gridy = 1;
        frame.getContentPane().add(lblNewLabel_1, gbc_lblNewLabel_1);

        usernameField = new JTextField();
        GridBagConstraints gbc_usernameField = new GridBagConstraints();
        gbc_usernameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_usernameField.insets = new Insets(0, 0, 5, 5);
        gbc_usernameField.gridx = 2;
        gbc_usernameField.gridy = 1;
        frame.getContentPane().add(usernameField, gbc_usernameField);
        usernameField.setColumns(10);

        JLabel lblNewLabel_2 = new JLabel("Password");
        GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
        gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
        gbc_lblNewLabel_2.anchor = GridBagConstraints.EAST;
        gbc_lblNewLabel_2.gridx = 1;
        gbc_lblNewLabel_2.gridy = 2;
        frame.getContentPane().add(lblNewLabel_2, gbc_lblNewLabel_2);

        passwordField = new JPasswordField();
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
        gbc_passwordField.insets = new Insets(0, 0, 5, 5);
        gbc_passwordField.gridx = 2;
        gbc_passwordField.gridy = 2;
        frame.getContentPane().add(passwordField, gbc_passwordField);

        JButton btnNewButton = new JButton("Log In");
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	 String username = usernameField.getText();
                 String password = passwordField.getText();

                 //If the username or password is empty, print an error message and return
				if (username.isEmpty() || password.isEmpty()) {
					System.out.println("Username or password cannot be empty");
					return;
				}
                 // Check if the customer object already exists
                 if (customer == null) {
                     // Try to create a new Customer object with the provided credentials
                     customer = new Customer(username, password);
                 }

                 // Validate the login credentials
                 if (customer.validateLoginCredentials(username, password)) {
                     System.out.println("Login successful for Username: " + customer.username);
                 } else {
                     //customer = null;
                     System.out.println("Invalid login credentials");
                 }
            }
        });
        GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
        gbc_btnNewButton.insets = new Insets(0, 0, 5, 5);
        gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
        gbc_btnNewButton.gridx = 2;
        gbc_btnNewButton.gridy = 3;
        frame.getContentPane().add(btnNewButton, gbc_btnNewButton);

        JButton btnCreateAccount = new JButton("Create Account");
        btnCreateAccount.addActionListener(new ActionListener() {
        	 public void actionPerformed(ActionEvent e) {
        	        String username = usernameField.getText();
        	        String password = passwordField.getText();
        	        if (username.isEmpty() || password.isEmpty()) {
    					System.out.println("Username or password cannot be empty");
    					return;
    				}
        	        if (customer == null) {
        	            customer = new Customer();
        	        }
        	        customer = customer.createAccount(username, password);
        	        System.out.println("Account created for Username: " + customer.username);
        	    }
        });
        GridBagConstraints gbc_btnCreateAccount = new GridBagConstraints();
        gbc_btnCreateAccount.insets = new Insets(0, 0, 5, 5);
        gbc_btnCreateAccount.gridx = 2;
        gbc_btnCreateAccount.gridy = 5;
        frame.getContentPane().add(btnCreateAccount, gbc_btnCreateAccount);

        JButton btnDeleteAccount = new JButton("Delete Account");
        btnDeleteAccount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (customer != null) {
                    System.out.println("Deleting account for Username: " + customer.username);
                    customer.deleteAccount();
                    customer = null;
                } else {
                    System.out.println("No account to delete");
                }
            }
        });
        GridBagConstraints gbc_btnDeleteAccount = new GridBagConstraints();
        gbc_btnDeleteAccount.insets = new Insets(0, 0, 5, 5);
        gbc_btnDeleteAccount.gridx = 2;
        gbc_btnDeleteAccount.gridy = 6;
        frame.getContentPane().add(btnDeleteAccount, gbc_btnDeleteAccount);
    }
}