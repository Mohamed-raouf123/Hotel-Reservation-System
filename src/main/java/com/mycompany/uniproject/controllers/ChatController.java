package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.Guest;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.net.Socket;

public class ChatController {
    @FXML private TextArea chatArea;
    @FXML private TextField messageField;
    @FXML private Button sendButton;
    @FXML private Button backButton;

    private Guest currentGuest;
    private Socket socket;
    private PrintWriter out;

    public void setGuest(Guest guest) {
        currentGuest = guest;
        connectToServer();
    }

    private void connectToServer() {
        new Thread(() -> {
            try {
                socket = new Socket("localhost", 5000);
                out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                while ((message = in.readLine()) != null) {
                    final String msg = message;
                    Platform.runLater(() -> chatArea.appendText(msg + "\n"));
                }
            } catch (Exception e) {
                Platform.runLater(() -> chatArea.appendText("Connection lost.\n"));
            }
        }).start();
    }

    @FXML
    private void handleSend() {
        String message = messageField.getText();
        if (message.isEmpty()) return;
        if (out == null) {
            AlertHelper.warning("Not connected yet. Please wait a moment.");
            return;
        }
        out.println(message);
        chatArea.appendText("You: " + message + "\n");
        messageField.clear();
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