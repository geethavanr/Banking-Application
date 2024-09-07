package coe528.project;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;

public class ManagerUI {
    private ListView<String> listView;
    private Bank bank;
    private Stage managerStage;

    public ManagerUI(Bank bank) {
        this.bank = bank;
        listView = new ListView<>();
        updateCustomerList();
    }

    public void createScene() {
        managerStage = new Stage();

        Label titleLabel = new Label("Manager Options");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        Button createButton = new Button("Create Customer");
        createButton.setOnAction(event -> showCreateCustomerPage());

        Button deleteButton = new Button("Delete Customer");
        deleteButton.setOnAction(event -> showDeleteCustomerPage());

        Button logoutButton = new Button("Log Out");
        logoutButton.setOnAction(event -> {
            managerStage.close();
            // Call the initial login screen method here
        });

        HBox buttonsBox = new HBox(10);
        buttonsBox.getChildren().addAll(createButton, deleteButton, logoutButton);
        buttonsBox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(titleLabel, buttonsBox, listView);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Scene scene = new Scene(layout, 500, 400);
        managerStage.setScene(scene);
        managerStage.setTitle("Manager UI");
        managerStage.show();
    }

    private void showCreateCustomerPage() {
        Stage createStage = new Stage();

        Label titleLabel = new Label("Create Customer");
        titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        Label usernameLabel = new Label("Username:");
        TextField usernameField = new TextField();

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();

        Button createButton = new Button("Create");
        createButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Please enter username and password.");
            } else if (bank.customerExists(username)) {
                showAlert("Error", "Customer with username already exists.");
            } else {
                bank.addCustomer(username, password);
                showAlert("Success", "Customer created successfully.");
                updateCustomerList();
                createStage.close();
            }
        });

        VBox createLayout = new VBox(10);
        createLayout.getChildren().addAll(titleLabel, usernameLabel, usernameField, passwordLabel, passwordField, createButton);
        createLayout.setAlignment(Pos.CENTER);
        createLayout.setPadding(new Insets(20));

        Scene createScene = new Scene(createLayout, 300, 200);
        createStage.setScene(createScene);
        createStage.setTitle("Create Customer");
        createStage.show();
    }

    private void showDeleteCustomerPage() {
        String selectedCustomer = listView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showAlert("Error", "Please select a customer to delete.");
            return;
        }

        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirm Delete");
        confirmationAlert.setHeaderText("Are you sure you want to delete this customer?");
        confirmationAlert.setContentText("Customer: " + selectedCustomer);

        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == javafx.scene.control.ButtonType.OK) {
                if (bank.deleteCustomer(selectedCustomer)) {
                    showAlert("Success", "Customer deleted successfully.");
                    updateCustomerList();
                } else {
                    showAlert("Error", "Unable to delete customer.");
                }
            }
        });
    }

    private void updateCustomerList() {
        listView.getItems().clear();
        listView.getItems().addAll(bank.getAllCustomers());
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
