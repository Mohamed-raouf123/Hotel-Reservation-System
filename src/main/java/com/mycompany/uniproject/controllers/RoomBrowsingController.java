package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RoomBrowsingController {
    @FXML private ListView<String> roomsListView;
    @FXML private Button bookRoomButton;
    @FXML private Button backButton;

    private List<Room> availableRooms = new ArrayList<>();
    private Guest currentGuest;

    @FXML
    private void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            if (currentGuest != null) {
                Platform.runLater(() -> {
                    roomsListView.getItems().clear();
                    availableRooms.clear();
                    loadRooms();
                });
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setGuest(Guest guest) {
        currentGuest = guest;
        loadRooms();
    }

    public void loadRooms() {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            if (HotelDatabase.rooms.get(i).isAvailable()) {
                roomsListView.getItems().add(HotelDatabase.rooms.get(i).toString());
                availableRooms.add(HotelDatabase.rooms.get(i));
            }
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

    @FXML
    private void handleBookRoom() {
        int selectedIndex = roomsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            System.out.println("Please choose a room first!");
            return;
        }
        Room selectedRoom = availableRooms.get(selectedIndex);
        TextInputDialog dialog = new TextInputDialog("YYYY-MM-DD");
        dialog.setTitle("Check-in Date");
        dialog.setHeaderText("Enter check-in date:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            LocalDate checkIn = LocalDate.parse(result.get());
            if (checkIn.isBefore(LocalDate.now())) {
                System.out.println("Check-in date cannot be in the past!");
                return;
            }
            TextInputDialog dialog2 = new TextInputDialog("YYYY-MM-DD");
            dialog2.setTitle("Check-out Date");
            dialog2.setHeaderText("Enter check-out date:");
            Optional<String> result2 = dialog2.showAndWait();
            if (result2.isPresent()) {
                LocalDate checkout = LocalDate.parse(result2.get());
                try {
                    Reservation reservation = new Reservation(currentGuest, selectedRoom, checkIn, checkout);
                    HotelDatabase.reservations.add(reservation);
                    currentGuest.getReservations().add(reservation);
                    System.out.println("Reservation made successfully!");
                } catch (InvalidDateException e) {
                    System.out.println("Date Error: " + e.getMessage());
                } catch (RoomAlreadyBookedException e) {
                    System.out.println("Booking Error: " + e.getMessage());
                }
            }
        }
    }
}
