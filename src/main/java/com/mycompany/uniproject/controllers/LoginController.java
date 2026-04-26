package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.Guest;
import com.mycompany.uniproject.HotelDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    @FXML private Button registerButton;
    @FXML private Button staffLoginButton;

    @FXML
    private void handleLogin() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            AlertHelper.warning("Please enter both username and password.");
            return;
        }

        for (int i = 0; i < HotelDatabase.guests.size(); i++) {
            Guest g = HotelDatabase.guests.get(i);
            if (g.getUsername().equals(username) && g.login(username, password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/guestDashboard.fxml"));
                Parent root = loader.load();
                GuestDashboardController controller = loader.getController();
                controller.setGuest(g);
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
                stage.setScene(scene);
                return;
            }
        }
        AlertHelper.error("Invalid username or password.");
    }

    @FXML
    private void handleRegister() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/uniproject/fxml/register.fxml"));
        Stage stage = (Stage) registerButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void handleStaffLogin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/uniproject/fxml/staffLogin.fxml"));
        Stage stage = (Stage) staffLoginButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
