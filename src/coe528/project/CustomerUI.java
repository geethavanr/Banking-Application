package coe528.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomerUI {
    private Customer customer;
    private Stage customerStage;

    public CustomerUI(Customer customer) {
        this.customer = customer;
    }

    public void createScene() {
        customerStage = new Stage();

        Label titleLabel = new Label("Customer Options");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Label balanceLabel = new Label("Balance: $" + customer.getBalance());
        Label levelLabel = new Label("Level: " + customer.getLevel());

        Label depositLabel = new Label("Enter deposit amount:");
        TextField depositField = new TextField();

        Label withdrawLabel = new Label("Enter withdrawal amount:");
        TextField withdrawField = new TextField();

        Label purchaseLabel = new Label("Enter purchase amount:");
        TextField purchaseField = new TextField();

        Button depositButton = new Button("Deposit");
        depositButton.setOnAction(event -> {
            try {
                double amount = Double.parseDouble(depositField.getText());
                customer.deposit(amount);
                showAlert("Success", "Deposit successful.");
                updateBalanceAndLevelLabels();
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid deposit amount.");
            }
        });

        Button withdrawButton = new Button("Withdraw");
        withdrawButton.setOnAction(event -> {
            try {
                double amount = Double.parseDouble(withdrawField.getText());
                if (amount > customer.getBalance()) {
                    showAlert("Error", "Withdrawal amount cannot exceed the current balance.");
                } else {
                    customer.withdraw(amount);
                    showAlert("Success", "Withdrawal successful.");
                    updateBalanceAndLevelLabels();
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid withdrawal amount.");
            }
        });
        Button purchaseButton = new Button("Make Purchase");
        purchaseButton.setOnAction(event -> {
            try {
                double amount = Double.parseDouble(purchaseField.getText());
                double fee = 0;
                if (customer.getLevel().equals("Silver")) {
                    fee = 20;
                } else if (customer.getLevel().equals("Gold")) {
                    fee = 10;
                }

                if (amount + fee > customer.getBalance()) {
                    showAlert("Error", "Purchase amount and fee cannot exceed the current balance.");
                } else if (amount < 50) {
                    showAlert("Error", "Purchase amount must be at least $50.");
                } else {
                    customer.makeOnlinePurchase(amount);
                    showAlert("Success", "Purchase successful.");
                    updateBalanceAndLevelLabels();
                }
            } catch (NumberFormatException e) {
                showAlert("Error", "Please enter a valid purchase amount.");
            }
        });

        Button logoutButton = new Button("Log Out");
        logoutButton.setOnAction(event -> {
            customerStage.close();
            // Call the initial login screen method here
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, balanceLabel, levelLabel, depositLabel, depositField,
                depositButton, withdrawLabel, withdrawField, withdrawButton,
                purchaseLabel, purchaseField, purchaseButton, logoutButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 400, 500);
        customerStage.setScene(scene);
        customerStage.setTitle("Customer UI");
        customerStage.show();
    }

    private void updateBalanceAndLevelLabels() {
        Label balanceLabel = (Label) customerStage.getScene().getRoot().getChildrenUnmodifiable().get(1);
        balanceLabel.setText("Balance: $" + customer.getBalance());

        Label levelLabel = (Label) customerStage.getScene().getRoot().getChildrenUnmodifiable().get(2);
        levelLabel.setText("Level: " + customer.getLevel());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
