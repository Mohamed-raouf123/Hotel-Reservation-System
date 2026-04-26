package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.HotelDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class StaffLoginController {
    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    @FXML private Button backButton;

    @FXML
    private void handleStaffLogin() throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.isEmpty() || password.isEmpty()) {
            AlertHelper.warning("Please enter both username and password.");
            return;
        }
        for (int i = 0; i < HotelDatabase.admins.size(); i++) {
            if (HotelDatabase.admins.get(i).getUsername().equals(username) && HotelDatabase.admins.get(i).getPassword().equals(password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/adminDashboard.fxml"));
                Parent root = loader.load();
                AdminDashboardController controller = loader.getController();
                controller.setAdmin(HotelDatabase.admins.get(i));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
                stage.setScene(scene);
                return;
            }
        }
        for (int i = 0; i < HotelDatabase.receptionists.size(); i++) {
            if (HotelDatabase.receptionists.get(i).getUsername().equals(username) && HotelDatabase.receptionists.get(i).getPassword().equals(password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/receptionistDashboard.fxml"));
                Parent root = loader.load();
                ReceptionistDashboardController controller = loader.getController();
                controller.setReceptionist(HotelDatabase.receptionists.get(i));
                Stage stage = (Stage) loginButton.getScene().getWindow();
                Scene scene = new Scene(root, 800, 600);
                scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
                stage.setScene(scene);
                return;
            }
        }
        AlertHelper.error("Invalid staff credentials.");
    }

    @FXML
    private void handleBack() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/uniproject/fxml/login.fxml"));
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
