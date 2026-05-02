package com.mycompany.uniproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Testmain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        HotelDatabase.populateRoomtype();
        HotelDatabase.populateAmenities();
        HotelDatabase.populateGuest();
        HotelDatabase.populateStaff();
        HotelDatabase.populateRooms();
        DatabaseManager.init();
        ChatServer.startServer();

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/com/mycompany/uniproject/fxml/login.fxml")
        );

        Parent root = loader.load();
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/mycompany/uniproject/style.css").toExternalForm());

        try {
            Image icon = new Image(getClass().getResource("/com/mycompany/uniproject/images/icon.png").toExternalForm());
            stage.getIcons().add(icon);
        } catch (Exception e) {
            System.out.println("Icon not found: " + e.getMessage());
        }

        stage.setTitle("Hotel Reservation System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
