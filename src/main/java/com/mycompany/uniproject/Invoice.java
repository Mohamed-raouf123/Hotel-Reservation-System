package com.mycompany.uniproject;
import java.time.LocalDate;
import java.util.List;

public class Invoice {
    private static int invoiceCounter = 5000;
    private int invoiceId;
    private List<Reservation> reservations;
    private double totalAmount;
    private boolean paid;
    private LocalDate issueDate;
    private PaymentMethod paymentMethod;
    private LocalDate paymentDate;

    public Invoice(List<Reservation> reservations) {
        this.invoiceId = ++invoiceCounter;
        this.reservations = reservations;
        this.issueDate = LocalDate.now();
        this.paid = false;
        this.totalAmount = reservations.stream().mapToDouble(Reservation::calculateTotal).sum();
    }

    public void generateInvoice() { System.out.println("Invoice generated for amount: $" + totalAmount); }
    public void processPayment(PaymentMethod method) { this.paymentMethod = method; this.paymentDate = LocalDate.now(); System.out.println("Payment of $" + totalAmount + " processed via " + method); }
    public void pay(double amount) {
        if (amount >= totalAmount) { this.paid = true; System.out.println("Invoice #" + invoiceId + " paid in full. Amount: $" + totalAmount);
            for (Reservation r : reservations) {
            r.setStatus(ReservationStatus.COMPLETED);
        } }
        else { System.out.println("Insufficient amount. Total due: $" + totalAmount); }
    }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    public PaymentMethod getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(PaymentMethod paymentMethod) { this.paymentMethod = paymentMethod; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate paymentDate) { this.paymentDate = paymentDate; }
    public int getInvoiceId() { return invoiceId; }
    public boolean isPaid() { return paid; }
    public LocalDate getIssueDate() { return issueDate; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("========== INVOICE #").append(invoiceId).append(" ==========\n");
        sb.append("Issue Date : ").append(issueDate).append("\n");
        sb.append("-------------------------------------------\n");
        for (Reservation r : reservations) { sb.append("  ").append(r).append("\n"); }
        sb.append("-------------------------------------------\n");
        sb.append("TOTAL      : $").append(totalAmount).append("\n");
        sb.append("STATUS     : ").append(paid ? "PAID" : "UNPAID").append("\n");
        sb.append("===========================================");
        return sb.toString();
    }
}
