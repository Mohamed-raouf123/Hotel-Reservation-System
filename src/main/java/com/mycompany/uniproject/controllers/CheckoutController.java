package com.mycompany.uniproject.controllers;

import com.mycompany.uniproject.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Optional;

public class CheckoutController {
    @FXML private Button backButton;
    @FXML private Button confirmPaymentButton;
    @FXML private ComboBox<String> paymentMethodComboBox;
    @FXML private Label invoiceLabel;

    private Guest currentGuest;

    public void setGuest(Guest guest) {
        currentGuest = guest;
    }

    @FXML
    private void initialize() {
        paymentMethodComboBox.getItems().addAll("CASH", "CREDIT_CARD", "ONLINE");
    }

    @FXML
    private void handleConfirmPayment() {
        if (paymentMethodComboBox.getValue() == null) {
            AlertHelper.warning("Please select a payment method!");
            return;
        }
        Invoice invoice = currentGuest.checkout();
        if (invoice == null) {
            AlertHelper.warning("No active reservations to checkout.");
            return;
        }
        PaymentMethod paymentMethod = PaymentMethod.valueOf(paymentMethodComboBox.getValue());
        if (paymentMethod == PaymentMethod.CREDIT_CARD) {
            try {
                TextInputDialog cardDialog = new TextInputDialog("1234 5678 9012 3456");
                cardDialog.setTitle("Credit Card");
                cardDialog.setHeaderText("Enter card number:");
                Optional<String> cardResult = cardDialog.showAndWait();
                if (!cardResult.isPresent()) return;

                String cardNumber = cardResult.get().replaceAll(" ", "");
                if (cardNumber.length() != 16) {
                    throw new InvalidCardException("Card number must be 16 digits!");
                }

                TextInputDialog expiryDialog = new TextInputDialog("MM/YY");
                expiryDialog.setTitle("Credit Card");
                expiryDialog.setHeaderText("Enter expiry date:");
                Optional<String> expiryResult = expiryDialog.showAndWait();
                if (!expiryResult.isPresent()) return;

                TextInputDialog cvvDialog = new TextInputDialog("123");
                cvvDialog.setTitle("Credit Card");
                cvvDialog.setHeaderText("Enter CVV:");
                Optional<String> cvvResult = cvvDialog.showAndWait();
                if (!cvvResult.isPresent()) return;

                if (cvvResult.get().length() != 3) {
                    throw new InvalidCardException("CVV must be 3 digits!");
                }

                AlertHelper.info("Card ending in " + cardNumber.substring(12) + " accepted.");

            } catch (InvalidCardException e) {
                AlertHelper.error("Card Error: " + e.getMessage());
                return;
            }
        }
        invoice.processPayment(paymentMethod);
        try {
            currentGuest.payInvoice(invoice);
        } catch (InsufficientBalanceException e) {
            AlertHelper.error("Payment Error: " + e.getMessage());
            return;
        }
        invoiceLabel.setText(invoice.toString());
        AlertHelper.success("Payment successful!\nTotal: $" + invoice.getTotalAmount() + "\nRemaining balance: $" + currentGuest.getBalance());
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
