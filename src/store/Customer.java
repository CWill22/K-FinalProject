package store;

public class Customer {
	public String username;
	private String password;
	
	//constructor for Customer (createAccount)
	public Customer(String username, String password) {
		this.username = username;
		this.password = password;
	}
	///createAccount method
	public Customer createAccount(String username, String password) {
		System.out.println("Creating account for Username: " + username);
        Customer customer = new Customer(username, password);
        return customer;
    }
	
	
	
	//getter and setter methods
	/*
	protected String getPassword() {
		return password;
	}
	protected void setPassword(String password) {
		this.password = password;
	}
	*/
	
	//validate password
	public boolean validateLoginCredentials(String username, String password) {
	//print username
		System.out.println("Trying to login to Username: " + this.username);
		return this.password.equals(password);
	}

	//deleteAccount method
	public void deleteAccount() {
		System.out.println("Deleting account for Username: " + this.username);
		this.username = null;
		this.password = null;
	}
	
}
