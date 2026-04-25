package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.LocalTime;

public class HotelServicesController {

    @FXML private ComboBox<String> serviceTypeComboBox;
    @FXML private TextField dateField;
    @FXML private TextField timeField;
    @FXML private TextField detailsField;
    @FXML private Button bookServiceButton;
    @FXML private Button backButton;

    private Guest currentGuest;

    public void setGuest(Guest guest) {
        currentGuest = guest;
    }

    @FXML
    private void initialize() {
        serviceTypeComboBox.getItems().addAll("SPA", "GYM", "RESTAURANT", "ROOM_SERVICE");
    }

    @FXML
    private void handleBookService() {
        try {
            if (serviceTypeComboBox.getValue() == null || dateField.getText().isEmpty() || timeField.getText().isEmpty() || detailsField.getText().isEmpty()) {
                throw new EmptyFieldException("Please fill in all fields!");
            }

            LocalDate date = LocalDate.parse(dateField.getText());

            if (date.isBefore(LocalDate.now())) {
                throw new InvalidDateException("Cannot book a service in the past!");
            }

            LocalTime time = LocalTime.parse(timeField.getText());
            String details = detailsField.getText();
            HotelService.ServiceType serviceType = HotelService.ServiceType.valueOf(serviceTypeComboBox.getValue());

            HotelService service = new HotelService(currentGuest, serviceType, date, time, details);

            if (currentGuest.getBalance() < service.getCost()) {
                throw new InsufficientBalanceException("Insufficient balance! Service costs $" + service.getCost() + " | Your balance: $" + currentGuest.getBalance());
            }

            HotelDatabase.services.add(service);
            currentGuest.setBalance(currentGuest.getBalance() - service.getCost());

            System.out.println("========== SERVICE BOOKING CONFIRMATION ==========");
            System.out.println("Service: " + service.getServiceType());
            System.out.println("Date: " + service.getDate());
            System.out.println("Time: " + service.getTime());
            System.out.println("Details: " + service.getDetails());
            System.out.println("Cost: $" + service.getCost());
            System.out.println("Remaining Balance: $" + currentGuest.getBalance());
            System.out.println("==================================================");

        } catch (EmptyFieldException | ServiceUnavailableException | InvalidTimeSlotException | InsufficientBalanceException | InvalidDateException e) {
            System.out.println("Booking Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/guestDashboard.fxml"));
        Parent root = loader.load();
        GuestDashboardController controller = loader.getController();
        controller.setGuest(currentGuest);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }
}