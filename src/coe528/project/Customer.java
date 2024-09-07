package coe528.project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Represents a customer in the bank system.
 * This class is mutable as it allows changes to the customer's balance and level.
 *
 * Abstraction Function:
 * - Represents a bank customer with a unique username, password, level, and balance.
 *
 * Rep Invariant:
 * - Username, password, and level must not be null or empty strings.
 * - Balance must be non-negative.
 */
public class Customer extends User {
    private String level;
    private double balance;

    /**
     * Constructs a new Customer with the given username and password.
     * Initializes the level to "Silver" and the balance to 100.0.
     *
     * @param username The customer's username.
     * @param password The customer's password.
     */
    public Customer(String username, String password) {
        super(username, password, Role.CUSTOMER);
        this.level = "Silver"; // Default level
        this.balance = 100.0; // Starting balance for customers
    }

    /**
     * Gets the customer's level.
     *
     * @return The customer's level.
     */
    public String getLevel() {
        return level;
    }

    /**
     * Gets the customer's balance.
     *
     * @return The customer's balance.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Sets the customer's level.
     *
     * @param level The new level to set.
     */
    public void setLevel(String level) {
        this.level = level;
        saveToFile(); // Save updated data to file
    }

    /**
     * Sets the customer's balance.
     *
     * @param balance The new balance to set.
     */
    public void setBalance(double balance) {
        this.balance = balance;
        saveToFile(); // Save updated data to file
    }

    /**
     * Deposits the specified amount into the customer's account.
     * Updates the level based on the balance after deposit.
     *
     * @param amount The amount to deposit.
     */
    public void deposit(double amount) {
        balance += amount;
        updateLevel();
        saveToFile();
    }

    /**
     * Withdraws the specified amount from the customer's account if sufficient balance is available.
     * Updates the level based on the balance after withdrawal.
     *
     * @param amount The amount to withdraw.
     */
    public void withdraw(double amount) {
        if (balance >= amount) {
            balance -= amount;
            updateLevel();
            saveToFile();
        } else {
            System.out.println("Insufficient funds.");
        }
    }

    /**
     * Updates the customer's level based on their current balance.
     */
    private void updateLevel() {
        if (balance >= 20000) {
            level = "Platinum";
        } else if (balance >= 10000) {
            level = "Gold";
        } else {
            level = "Silver";
        }
    }

    /**
     * Makes an online purchase for the customer if conditions are met.
     * Deducts the purchase amount and applicable fee from the balance.
     * Updates the level based on the balance after the purchase.
     *
     * @param amount The amount of the purchase.
     * @return True if the purchase is successful, false otherwise.
     */
    public boolean makeOnlinePurchase(double amount) {
        double fee = 0;
        if (level.equals("Silver")) {
            fee = 20;
        } else if (level.equals("Gold")) {
            fee = 10;
        }

        if (balance >= amount + fee && amount >= 50) {
            balance -= (amount + fee);
            updateLevel();
            saveToFile();
            return true;
        } else {
            System.out.println("Insufficient funds or purchase amount below $50.");
            return false;
        }
    }

    /**
     * Saves the customer's data to a file with the username as the filename.
     */
    public void saveToFile() {
        File file = new File(getUsername() + ".txt");
        try (FileWriter writer = new FileWriter(file)) {
            writer.write("Username: " + getUsername() + "\n");
            writer.write("Password: " + getPassword() + "\n");
            writer.write("Level: " + level + "\n");
            writer.write("Balance: " + balance + "\n");
        } catch (IOException e) {
            System.out.println("Error saving customer data to file: " + e.getMessage());
        }
    }

    /**
     * Loads customer data from a file with the given filename.
     *
     * @param filename The name of the file to load customer data from.
     * @return The loaded Customer object, or null if loading fails.
     */
    public static Customer loadFromFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            String username = scanner.nextLine().split(": ")[1];
            String password = scanner.nextLine().split(": ")[1];
            String level = scanner.nextLine().split(": ")[1];
            double balance = Double.parseDouble(scanner.nextLine().split(": ")[1]);
            Customer customer = new Customer(username, password);
            customer.setLevel(level);
            customer.setBalance(balance);
            return customer;
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading customer data from file: " + e.getMessage());
        }
        return null;
    }

    /**
     * Returns a string representation of the Customer object.
     * The string contains the username, password, level, and balance of the customer.
     *
     * @return A string representation of the Customer object.
     */
    @Override
    public String toString() {
        return "Customer{" +
                "username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", level='" + level + '\'' +
                ", balance=" + balance +
                '}';
    }

    /**
     * Checks if the representation invariant holds for the customer.
     *
     * @return true if the representation invariant holds, false otherwise.
     */
    private boolean repOk() {
        return getUsername() != null && !getUsername().isEmpty()
                && getPassword() != null && !getPassword().isEmpty()
                && level != null && !level.isEmpty()
                && balance >= 0;
    }
}
