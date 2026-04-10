import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
public class Guest {

    // ── Attributes ──────────────────────────────────────────────────────────
    private String username;
    private String password;       // validated
    private LocalDate dateOfBirth;
    private double balance;
    private String address;
    private Gender gender;         // enum: MALE, FEMALE
    private RoomPreferences roomPreferences;

    private List<Reservation> reservations;
    private boolean loggedIn;

    // ── Constructor ──────────────────────────────────────────────────────────
    public Guest(String username, String password, LocalDate dateOfBirth,
                 double balance, String address, Gender gender,
                 RoomPreferences roomPreferences) {
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException(
                "Password must be at least 8 characters and contain a digit.");
        }
        this.username = username;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.balance = balance;
        this.address = address;
        this.gender = gender;
        this.roomPreferences = roomPreferences;
        this.reservations = new ArrayList<>();
        this.loggedIn = false;
    }

    // ── Password validation ──────────────────────────────────────────────────
    private boolean isValidPassword(String password) {
        if (password == null || password.length() < 8) return false;
        return password.chars().anyMatch(Character::isDigit);
    }

    // ── Behaviors ────────────────────────────────────────────────────────────

    public void register() {
        System.out.println("Guest '" + username + "' registered successfully.");
    }

    public boolean login(String inputUsername, String inputPassword) {
        if (this.username.equals(inputUsername) && this.password.equals(inputPassword)) {
            this.loggedIn = true;
            System.out.println("Welcome back, " + username + "! Login successful.");
            return true;
        }
        System.out.println("Login failed. Invalid credentials.");
        return false;
    }

    public List<Room> viewAvailableRooms(List<Room> allRooms) {
        requireLogin();
        List<Room> available = new ArrayList<>();
        for (Room r : allRooms) {
            if (r.isAvailable()) available.add(r);
        }
        System.out.println("\n--- Available Rooms ---");
        available.forEach(System.out::println);
        return available;
    }

    public Reservation makeReservation(Room room, LocalDate checkIn, LocalDate checkOut) {
        requireLogin();
        if (!room.isAvailable()) {
            System.out.println("Room #" + room.getRoomnumber() + " is not available.");
            return null;
        }
        Reservation res = new Reservation(room, checkIn, checkOut);
        reservations.add(res);
        System.out.println("Reservation confirmed: " + res);
        return res;
    }

    public void viewReservations() {
        requireLogin();
        if (reservations.isEmpty()) {
            System.out.println("No reservations found.");
            return;
        }
        System.out.println("\n--- Reservations for " + username + " ---");
        reservations.forEach(System.out::println);
    }

    public void cancelReservation(int reservationId) {
        requireLogin();
        for (Reservation res : reservations) {
            if (res.getReservationId() == reservationId && res.isActive()) {
                res.cancel();
                System.out.println("Reservation #" + reservationId + " has been cancelled.");
                return;
            }
        }
        System.out.println("Reservation #" + reservationId + " not found or already cancelled.");
    }

    public Invoice checkout() {
        requireLogin();
        List<Reservation> active = new ArrayList<>();
        for (Reservation r : reservations) {
            if (r.isActive()) active.add(r);
        }
        if (active.isEmpty()) {
            System.out.println("No active reservations to check out from.");
            return null;
        }
        Invoice invoice = new Invoice(active);
        System.out.println("\nCheckout complete. Invoice generated:");
        System.out.println(invoice);
        return invoice;
    }

    public void payInvoice(Invoice invoice) {
        requireLogin();
        if (invoice == null) {
            System.out.println("No invoice to pay.");
            return;
        }
        if (balance >= invoice.getTotalAmount()) {
            balance -= invoice.getTotalAmount();
            invoice.pay(invoice.getTotalAmount());
            System.out.println("Remaining balance: $" + balance);
        } else {
            System.out.println("Insufficient balance. Due: $" + invoice.getTotalAmount()
                               + " | Your balance: $" + balance);
        }
    }

    // ── Helper ───────────────────────────────────────────────────────────────
    private void requireLogin() {
        if (!loggedIn) throw new IllegalStateException("Guest must be logged in first.");
    }

    // ── Getters & Setters ────────────────────────────────────────────────────
    public String getUsername() { return username; }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public double getBalance() { return balance; }
    public String getAddress() { return address; }
    public Gender getGender() { return gender; }
    public RoomPreferences getRoomPreferences() { return roomPreferences; }
    public List<Reservation> getReservations() { return reservations; }

    public void setAddress(String address) { this.address = address; }
    public void setBalance(double balance) { this.balance = balance; }
    public void setRoomPreferences(RoomPreferences roomPreferences) {
        this.roomPreferences = roomPreferences;
    }

    @Override
    public String toString() {
        return "Guest{username='" + username + "', gender=" + gender +
               ", dob=" + dateOfBirth + ", balance=$" + balance + "}";
    }
}
