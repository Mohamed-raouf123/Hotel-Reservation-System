package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.Guest;
import com.mycompany.uniproject.Reservation;
import com.mycompany.uniproject.ReservationStatus;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class GuestDashboardController {
    @FXML private Button browseRoomsButton;
    @FXML private Button myReservationsButton;
    @FXML private Button checkoutButton;
    @FXML private Button logoutButton;
    @FXML private Button chatButton;
    @FXML private Label welcomeLabel;
    @FXML private Label balanceLabel;
    @FXML private Label reservationsLabel;
    @FXML private Button hotelServicesButton;
    @FXML private ListView<String> activeReservationsListView;

    private Guest currentGuest;

    public void setGuest(Guest guest) {
        currentGuest = guest;
        welcomeLabel.setText("Welcome, " + guest.getUsername());
        balanceLabel.setText("Balance: $" + guest.getBalance());
        loadActiveReservations();
    }

    private void loadActiveReservations() {
        if (activeReservationsListView == null) return;
        activeReservationsListView.getItems().clear();
        for (Reservation r : currentGuest.getReservations()) {
            if (r.getStatus() == ReservationStatus.PENDING || r.getStatus() == ReservationStatus.CONFIRMED) {
                activeReservationsListView.getItems().add(r.toString());
            }
        }
        if (activeReservationsListView.getItems().isEmpty()) {
            activeReservationsListView.getItems().add("No active reservations");
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
    private void handleBrowseRooms() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/roomBrowsing.fxml"));
        Parent root = loader.load();
        RoomBrowsingController controller = loader.getController();
        controller.setGuest(currentGuest);
        Stage stage = (Stage) browseRoomsButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void handleMyReservations() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/reservationManagement.fxml"));
        Parent root = loader.load();
        ReservationManagementController controller = loader.getController();
        controller.setGuest(currentGuest);
        Stage stage = (Stage) browseRoomsButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void handleCheckout() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/checkout.fxml"));
        Parent root = loader.load();
        CheckoutController controller = loader.getController();
        controller.setGuest(currentGuest);
        Stage stage = (Stage) browseRoomsButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void handleChat() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/chat.fxml"));
        Parent root = loader.load();
        ChatController controller = loader.getController();
        controller.setGuest(currentGuest);
        Stage stage = (Stage) chatButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void handleHotelServices() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/hotelServices.fxml"));
        Parent root = loader.load();
        HotelServicesController controller = loader.getController();
        controller.setGuest(currentGuest);
        Stage stage = (Stage) hotelServicesButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }
}
