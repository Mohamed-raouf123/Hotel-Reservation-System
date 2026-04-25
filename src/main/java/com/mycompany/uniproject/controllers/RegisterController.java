package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.time.LocalDate;

public class RegisterController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private TextField dobField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private ComboBox<String> genderField;
    @FXML private Button registerButton;
    @FXML private Button backToLoginButton;

    @FXML
    private void initialize() {
        genderField.getItems().addAll("MALE", "FEMALE");
    }

    @FXML
    private void handleRegister() {
        try {
            String username = usernameField.getText();
            String password = passwordField.getText();
            String address = addressField.getText();
            String gender = genderField.getValue();

            if (username.isEmpty() || password.isEmpty() || address.isEmpty() || gender == null) {
                throw new EmptyFieldException("All fields must be filled!");
            }

            for (Guest g : HotelDatabase.guests) {
                if (g.getUsername().equals(username)) {
                    throw new DuplicateUsernameException("Username '" + username + "' is already taken!");
                }
            }

            LocalDate dob = LocalDate.parse(dobField.getText());
            long phone = Long.parseLong(phoneField.getText());

            int age = LocalDate.now().getYear() - dob.getYear();
            if (age < 18) {
                throw new InvalidAgeException("Guest must be at least 18 years old!");
            }

            Guest newGuest = new Guest(username, password, dob, 0.0, address, Gender.valueOf(gender), null, phone);
            HotelDatabase.guests.add(newGuest);
            newGuest.register();

        } catch (EmptyFieldException | DuplicateUsernameException | InvalidAgeException e) {
            System.out.println("Registration Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleBackToLogin() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/uniproject/fxml/login.fxml"));
        Stage stage = (Stage) backToLoginButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
