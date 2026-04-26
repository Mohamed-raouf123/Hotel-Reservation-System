package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.Optional;

public class ReceptionistDashboardController {
    @FXML private Button viewGuestsButton;
    @FXML private Button viewRoomsButton;
    @FXML private Button makeReservationButton;
    @FXML private Button checkInButton;
    @FXML private Button checkOutButton;
    @FXML private Button logoutButton;
    @FXML private Button chatButton;
    @FXML private ListView<String> dataListView;

    private Receptionist currentReceptionist;

    public void setReceptionist(Receptionist receptionist) {
        currentReceptionist = receptionist;
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
    private void handleMakeReservation() {
        TextInputDialog dialog = new TextInputDialog("Guest Username");
        dialog.setTitle("Make Reservation");
        dialog.setHeaderText("Enter guest username:");
        Optional<String> result = dialog.showAndWait();
        if (!result.isPresent()) return;

        String guestUsername = result.get();
        Guest selectedGuest = null;
        for (int i = 0; i < HotelDatabase.guests.size(); i++) {
            if (HotelDatabase.guests.get(i).getUsername().equals(guestUsername)) {
                selectedGuest = HotelDatabase.guests.get(i);
                break;
            }
        }
        if (selectedGuest == null) {
            AlertHelper.error("Guest '" + guestUsername + "' not found!");
            return;
        }
        final Guest finalGuest = selectedGuest;

        TextInputDialog dialog2 = new TextInputDialog("Room Number");
        dialog2.setTitle("Make Reservation");
        dialog2.setHeaderText("Enter room number:");
        Optional<String> result2 = dialog2.showAndWait();
        if (!result2.isPresent()) return;

        try {
            int roomNumber = Integer.parseInt(result2.get());
            Room selectedRoom = null;
            for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
                if (HotelDatabase.rooms.get(i).getRoomnumber() == roomNumber) {
                    selectedRoom = HotelDatabase.rooms.get(i);
                    break;
                }
            }
            if (selectedRoom == null) {
                AlertHelper.error("Room #" + roomNumber + " not found!");
                return;
            }
            final Room finalRoom = selectedRoom;

            TextInputDialog dialog3 = new TextInputDialog("YYYY-MM-DD");
            dialog3.setTitle("Check-in Date");
            dialog3.setHeaderText("Enter check-in date:");
            Optional<String> result3 = dialog3.showAndWait();
            if (!result3.isPresent()) return;

            LocalDate checkIn = LocalDate.parse(result3.get());
            if (checkIn.isBefore(LocalDate.now())) {
                AlertHelper.error("Check-in date cannot be in the past!");
                return;
            }
            TextInputDialog dialog4 = new TextInputDialog("YYYY-MM-DD");
            dialog4.setTitle("Check-out Date");
            dialog4.setHeaderText("Enter check-out date:");
            Optional<String> result4 = dialog4.showAndWait();
            if (!result4.isPresent()) return;

            LocalDate checkOut = LocalDate.parse(result4.get());
            try {
                Reservation reservation = new Reservation(finalGuest, finalRoom, checkIn, checkOut);
                HotelDatabase.reservations.add(reservation);
                finalGuest.getReservations().add(reservation);
                AlertHelper.success("Reservation #" + reservation.getReservationId() + " created for " + finalGuest.getUsername() + ".");
            } catch (InvalidDateException e) {
                AlertHelper.error("Date Error: " + e.getMessage());
            } catch (RoomAlreadyBookedException e) {
                AlertHelper.error("Booking Error: " + e.getMessage());
            }
        } catch (NumberFormatException e) {
            AlertHelper.error("Invalid number format!");
        } catch (Exception e) {
            AlertHelper.error("Invalid date format. Use YYYY-MM-DD.");
        }
    }

    @FXML
    private void handleCheckIn() {
        TextInputDialog dialog = new TextInputDialog("Reservation ID");
        dialog.setTitle("Check In");
        dialog.setHeaderText("Enter reservation ID:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int reservationId = Integer.parseInt(result.get());
                for (int i = 0; i < HotelDatabase.reservations.size(); i++) {
                    if (HotelDatabase.reservations.get(i).getReservationId() == reservationId) {
                        HotelDatabase.reservations.get(i).confirmReservation();
                        AlertHelper.success("Guest checked in successfully for reservation #" + reservationId + ".");
                        return;
                    }
                }
                AlertHelper.error("Reservation #" + reservationId + " not found!");
            } catch (NumberFormatException e) {
                AlertHelper.error("Invalid reservation ID!");
            }
        }
    }

    @FXML
    private void handleCheckOut() {
        TextInputDialog dialog = new TextInputDialog("Reservation ID");
        dialog.setTitle("Check Out");
        dialog.setHeaderText("Enter reservation ID:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            try {
                int reservationId = Integer.parseInt(result.get());
                for (int i = 0; i < HotelDatabase.reservations.size(); i++) {
                    if (HotelDatabase.reservations.get(i).getReservationId() == reservationId) {
                        Reservation r = HotelDatabase.reservations.get(i);
                        r.setStatus(ReservationStatus.COMPLETED);
                        r.getRoom().releaseRoom();
                        AlertHelper.success("Guest checked out for reservation #" + reservationId + ". Room #" + r.getRoom().getRoomnumber() + " is now available.");
                        return;
                    }
                }
                AlertHelper.error("Reservation #" + reservationId + " not found!");
            } catch (NumberFormatException e) {
                AlertHelper.error("Invalid reservation ID!");
            }
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
    private void handleChat() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/receptionistChat.fxml"));
        Parent root = loader.load();
        ReceptionistChatController controller = loader.getController();
        controller.setReceptionist(currentReceptionist);
        Stage stage = (Stage) chatButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
