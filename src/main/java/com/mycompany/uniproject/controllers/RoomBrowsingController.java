package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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

    @FXML private ComboBox<String> typeFilterComboBox;
    @FXML private TextField maxPriceField;
    @FXML private ListView<String> amenityFilterListView;
    @FXML private Button applyFiltersButton;
    @FXML private Button clearFiltersButton;

    private List<Room> availableRooms = new ArrayList<>();
    private Guest currentGuest;

    @FXML
    private void initialize() {
        if (typeFilterComboBox != null) {
            typeFilterComboBox.getItems().add("All");
            for (RoomType t : HotelDatabase.roomTypes) {
                typeFilterComboBox.getItems().add(t.getName());
            }
            typeFilterComboBox.setValue("All");
        }
        if (amenityFilterListView != null) {
            amenityFilterListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            for (Amenity a : HotelDatabase.amenities) {
                if (!amenityFilterListView.getItems().contains(a.getName())) {
                    amenityFilterListView.getItems().add(a.getName());
                }
            }
        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            if (currentGuest != null) {
                Platform.runLater(() -> loadRooms());
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
        roomsListView.getItems().clear();
        availableRooms.clear();

        String typeFilter = (typeFilterComboBox != null) ? typeFilterComboBox.getValue() : null;
        double maxPrice = parseMaxPrice();
        List<String> selectedAmenities = new ArrayList<>();
        if (amenityFilterListView != null) {
            ObservableList<String> selected = amenityFilterListView.getSelectionModel().getSelectedItems();
            if (selected != null) {
                for (String s : selected) {
                    if (s != null) selectedAmenities.add(s);
                }
            }
        }

        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            Room r = HotelDatabase.rooms.get(i);
            if (!r.isAvailable()) continue;

            if (typeFilter != null && !typeFilter.equals("All") && !r.getRoomType().getName().equals(typeFilter)) continue;
            if (r.getRoomType().getPricePerNight() > maxPrice) continue;

            if (!selectedAmenities.isEmpty()) {
                boolean hasAll = true;
                for (String selectedName : selectedAmenities) {
                    boolean has = false;
                    for (Amenity a : r.getAmenities()) {
                        if (a.getName().equals(selectedName)) {
                            has = true;
                            break;
                        }
                    }
                    if (!has) {
                        hasAll = false;
                        break;
                    }
                }
                if (!hasAll) continue;
            }

            roomsListView.getItems().add(r.toString());
            availableRooms.add(r);
        }

        if (roomsListView.getItems().isEmpty()) {
            roomsListView.getItems().add("No rooms match your filters.");
        }
    }

    private double parseMaxPrice() {
        if (maxPriceField == null || maxPriceField.getText() == null || maxPriceField.getText().isEmpty()) {
            return Double.MAX_VALUE;
        }
        try {
            return Double.parseDouble(maxPriceField.getText());
        } catch (NumberFormatException e) {
            return Double.MAX_VALUE;
        }
    }

    @FXML
    private void handleApplyFilters() {
        loadRooms();
    }

    @FXML
    private void handleClearFilters() {
        if (typeFilterComboBox != null) {
            typeFilterComboBox.getSelectionModel().select("All");
        }
        if (maxPriceField != null) {
            maxPriceField.setText("");
        }
        if (amenityFilterListView != null) {
            amenityFilterListView.getSelectionModel().clearSelection();
            amenityFilterListView.getFocusModel().focus(-1);
        }
        loadRooms();
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
        if (selectedIndex == -1 || selectedIndex >= availableRooms.size()) {
            AlertHelper.warning("Please select a room first!");
            return;
        }
        Room selectedRoom = availableRooms.get(selectedIndex);
        TextInputDialog dialog = new TextInputDialog("YYYY-MM-DD");
        dialog.setTitle("Check-in Date");
        dialog.setHeaderText("Enter check-in date:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                LocalDate checkIn = LocalDate.parse(result.get());
                if (checkIn.isBefore(LocalDate.now())) {
                    AlertHelper.error("Check-in date cannot be in the past!");
                    return;
                }
                TextInputDialog dialog2 = new TextInputDialog("YYYY-MM-DD");
                dialog2.setTitle("Check-out Date");
                dialog2.setHeaderText("Enter check-out date:");
                Optional<String> result2 = dialog2.showAndWait();
                if (result2.isPresent()) {
                    LocalDate checkout = LocalDate.parse(result2.get());
                    Reservation reservation = new Reservation(currentGuest, selectedRoom, checkIn, checkout);
                    HotelDatabase.reservations.add(reservation);
                    currentGuest.getReservations().add(reservation);
                    DatabaseManager.saveReservation(reservation);
                    DatabaseManager.saveRoom(selectedRoom);
                    AlertHelper.success("Reservation made successfully!\nRoom #" + selectedRoom.getRoomnumber() + "\nTotal: $" + reservation.calculateTotal());
                    loadRooms();
                }
            } catch (InvalidDateException e) {
                AlertHelper.error("Date Error: " + e.getMessage());
            } catch (RoomAlreadyBookedException e) {
                AlertHelper.error("Booking Error: " + e.getMessage());
            } catch (Exception e) {
                AlertHelper.error("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }
}
