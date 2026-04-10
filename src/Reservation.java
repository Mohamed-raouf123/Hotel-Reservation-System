import java.time.LocalDate;

public class Reservation {
    private static int idCounter = 1000;

    private int reservationId;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private boolean active;

    public Reservation(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.reservationId = ++idCounter;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.active = true;
        room.setAvailable(false);
    }

    public void cancel() {
        this.active = false;
        room.setAvailable(true);
    }

    public double calculateTotal() {
        long nights = java.time.temporal.ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * room.getRoomType().getPricePerNight();
    }

    // Getters
    public int getReservationId() { return reservationId; }
    public Room getRoom() { return room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return "Reservation #" + reservationId +
               " | Room: " + room.getRoomnumber() +
               " | Check-in: " + checkInDate +
               " | Check-out: " + checkOutDate +
               " | Total: $" + calculateTotal() +
               " | Active: " + active;
    }
}
