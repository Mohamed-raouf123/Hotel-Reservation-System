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

public class ReservationManagementController {
    @FXML private ListView<String> reservationsListView;
    @FXML private Button makeReservationButton;
    @FXML private Button cancelReservationButton;
    @FXML private Button backButton;

    private Guest currentGuest;

    @FXML
    private void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> {
            if (currentGuest != null) {
                Platform.runLater(() -> {
                    reservationsListView.getItems().clear();
                    loadReservations();
                });
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public void setGuest(Guest guest) {
        currentGuest = guest;
        loadReservations();
    }

    public void loadReservations() {
        for (int i = 0; i < currentGuest.getReservations().size(); i++) {
            reservationsListView.getItems().add(currentGuest.getReservations().get(i).toString());
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
    private void handleMakeReservation() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/mycompany/uniproject/fxml/roomBrowsing.fxml"));
        Parent root = loader.load();
        RoomBrowsingController controller = loader.getController();
        controller.setGuest(currentGuest);
        Stage stage = (Stage) backButton.getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());
        stage.setScene(scene);
    }

    @FXML
    private void handleCancelReservation() {
        int selectedIndex = reservationsListView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            System.out.println("A ERROR HAS OCCURED PLEASE CHOOSE A RESERVATION");
            return;
        }
        Reservation reservation = currentGuest.getReservations().get(selectedIndex);
        currentGuest.cancelReservation(reservation.getReservationId());
        System.out.println("Cancel Reservation clicked!");
        reservationsListView.getItems().clear();
        loadReservations();
    }
}
