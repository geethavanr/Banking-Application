package coe528.project;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;


public class Bank {
    private List<Customer> customers;
    private Map<String, User> users;
    private double balance;

    public Bank() {
        this.customers = new ArrayList<>();
        this.users = new HashMap<>();
        this.balance = 100.0; // Starting balance for customers
        // Initialize with default manager
        users.put("admin", new Manager("admin", "admin"));
    }

    public boolean addCustomer(String username, String password) {
        if (!users.containsKey(username)) {
            Customer customer = new Customer(username, password);
            customers.add(customer);
            users.put(username, customer);
            customer.saveToFile(); // Save customer data to file
            return true;
        }
        return false;
    }

public boolean deleteCustomer(String username) {
    User user = users.get(username);
    if (user instanceof Customer) {
        Customer customer = (Customer) user;
        customers.remove(customer);
        users.remove(username);
        File file = new File(username + ".txt");
        if (file.exists()) {
            file.delete(); // Delete corresponding file
        }
        return true;
    }
    return false;
}


    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    public void onlinePurchase(double amount) {
        if (balance >= amount) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds for online purchase.");
        }
    }

    public double getBalance() {
        return balance;
    }

    public User authenticateUser(String username, String password) {
        User user = users.get(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public Customer authenticateCustomer(String username, String password) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

    public List<String> getAllCustomers() {
        List<String> customerNames = new ArrayList<>();
        for (User user : users.values()) {
            if (user instanceof Customer) {
                customerNames.add(user.getUsername());
            }
        }
        return customerNames;
    }

    public boolean customerExists(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public Customer getCustomer(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null; // Return null if customer is not found
    }
}
