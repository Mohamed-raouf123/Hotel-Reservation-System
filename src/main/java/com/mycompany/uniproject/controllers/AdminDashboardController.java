package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.Optional;

public class AdminDashboardController {
    @FXML private Button viewGuestsButton;
    @FXML private Button viewRoomsButton;
    @FXML private Button viewReservationsButton;
    @FXML private Button addRoomButton;
    @FXML private Button deleteRoomButton;
    @FXML private Button logoutButton;
    @FXML private ListView<String> dataListView;

    private AdminReceptionist currentAdmin;

    public void setAdmin(AdminReceptionist admin) {
        currentAdmin = admin;
    }

    @FXML
    private void handleViewGuests() {
        dataListView.getItems().clear();
        for (int i = 0; i < HotelDatabase.guests.size(); i++) {
            dataListView.getItems().add(HotelDatabase.guests.get(i).toString());
        }
    }

    @FXML
    private void handleViewRooms() {
        dataListView.getItems().clear();
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            dataListView.getItems().add(HotelDatabase.rooms.get(i).toString());
        }
    }

    @FXML
    private void handleViewReservations() {
        dataListView.getItems().clear();
        for (int i = 0; i < HotelDatabase.reservations.size(); i++) {
            dataListView.getItems().add(HotelDatabase.reservations.get(i).toString());
        }
    }

    @FXML
    private void handleLogout() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/mycompany/uniproject/fxml/login.fxml"));
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void handleAddRoom() {
        TextInputDialog dialog = new TextInputDialog("Room Number");
        dialog.setTitle("Room number");
        dialog.setHeaderText("Enter room number:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int roomnumber = Integer.parseInt(result.get());
            TextInputDialog dialog2 = new TextInputDialog("Room Type Index");
            dialog2.setTitle("Room Type Index");
            dialog2.setHeaderText("Enter room type index (0=Single, 1=Double, 2=Suite):");
            Optional<String> result2 = dialog2.showAndWait();
            if (result2.isPresent()) {
                int index = Integer.parseInt(result2.get());
                RoomType roomType = HotelDatabase.roomTypes.get(index);
                ((Admin) currentAdmin).createRoom(roomnumber, roomType);
                Room newRoom = HotelDatabase.rooms.get(HotelDatabase.rooms.size() - 1);

                while (true) {
                    TextInputDialog amenityDialog = new TextInputDialog("-1");
                    amenityDialog.setTitle("Add Amenity");
                    amenityDialog.setHeaderText("Enter amenity index to add:\n0=WiFi, 1=TV, 2=Mini-bar, 3=Pool, 4=Gym, 5=Spa\n-1 when done");
                    Optional<String> amenityResult = amenityDialog.showAndWait();
                    if (!amenityResult.isPresent()) break;
                    int amenityIndex = Integer.parseInt(amenityResult.get());
                    if (amenityIndex == -1) break;
                    if (amenityIndex >= 0 && amenityIndex < HotelDatabase.amenities.size()) {
                        newRoom.addAmenity(HotelDatabase.amenities.get(amenityIndex));
                        System.out.println("Amenity added: " + HotelDatabase.amenities.get(amenityIndex).getName());
                    }
                }
                System.out.println("Room " + roomnumber + " added with " + newRoom.getAmenities().size() + " amenities");
            }
        }
    }

    @FXML
    private void handleDeleteRoom() {
        TextInputDialog dialog = new TextInputDialog("Room Number");
        dialog.setTitle("Room number");
        dialog.setHeaderText("Enter room number:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int roomnumber = Integer.parseInt(result.get());
            ((Admin) currentAdmin).deleteRoom(roomnumber);
            System.out.println("Room deleted successfully");
        }
    }
}
