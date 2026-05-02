package com.mycompany.uniproject;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Reservation {
    private static int idCounter = 1000;
    private int reservationId;
    private Guest guest;
    private Room room;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private ReservationStatus status;

    public Reservation(Guest guest, Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        if (checkOutDate.isBefore(checkInDate) || checkOutDate.isEqual(checkInDate)) {
            throw new InvalidDateException("Check-out date must be after check-in date!");
        }
        if (!room.isAvailable()) {
            throw new RoomAlreadyBookedException("Room #" + room.getRoomnumber() + " is already booked!");
        }
        this.reservationId = ++idCounter;
        room.setAvailable(false);
        this.guest = guest;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.status = ReservationStatus.PENDING;
    }

    public void confirmReservation() {
        this.status = ReservationStatus.CONFIRMED;
        System.out.println("Reservation confirmed.");
    }

    public void cancelReservation() {
        room.setAvailable(true);
        this.status = ReservationStatus.CANCELLED;
        System.out.println("Reservation cancelled.");
    }

    public double calculateTotal() {
        long nights = ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        return nights * room.getRoomType().getPricePerNight();
    }

    public Guest getGuest() { return guest; }
    public void setGuest(Guest guest) { this.guest = guest; }
    public int getReservationId() { return reservationId; }
    public void setReservationId(int id) { this.reservationId = id; } // FIX: needed for DB loading
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public LocalDate getCheckInDate() { return checkInDate; }
    public void setCheckInDate(LocalDate checkInDate) { this.checkInDate = checkInDate; }
    public LocalDate getCheckOutDate() { return checkOutDate; }
    public void setCheckOutDate(LocalDate checkOutDate) { this.checkOutDate = checkOutDate; }
    public ReservationStatus getStatus() { return status; }
    public void setStatus(ReservationStatus status) { this.status = status; }

    @Override
    public String toString() {
        return "Reservation #" + reservationId + " | Guest: " + guest.getUsername() +
                " | Room: " + room.getRoomnumber() + " | Check-in: " + checkInDate +
                " | Check-out: " + checkOutDate + " | Total: $" + calculateTotal() +
                " | Status: " + status;
    }
}