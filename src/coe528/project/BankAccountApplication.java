package coe528.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BankAccountApplication extends Application {
    private Bank bank;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        bank = new Bank(); // Initialize the bank

        // Create initial login UI
        VBox loginLayout = new VBox(10);
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");

        Label messageLabel = new Label();

        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Authenticate user
            User authenticatedUser = bank.authenticateUser(username, password);

            if (authenticatedUser != null) {
                if (authenticatedUser instanceof Manager) {
                    new ManagerUI(bank).createScene(); // Display manager UI
                } else if (authenticatedUser instanceof Customer) {
                    Customer authenticatedCustomer = (Customer) authenticatedUser;
                    Customer customer = bank.getCustomer(authenticatedCustomer.getUsername()); // Retrieve full customer info
                    new CustomerUI(customer).createScene(); // Display customer UI with specific customer details
                }
            } else {
                messageLabel.setText("Invalid username or password");
            }
        });

        loginLayout.getChildren().addAll(new Label("Username:"), usernameField,
                new Label("Password:"), passwordField, loginButton, messageLabel);

        Scene loginScene = new Scene(loginLayout, 300, 200);
        primaryStage.setScene(loginScene);
        primaryStage.setTitle("Bank Account Application - Login");
        primaryStage.show();
    }
}
