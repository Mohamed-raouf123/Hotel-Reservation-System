package com.mycompany.uniproject;

import java.time.LocalDate;
import java.time.LocalTime;

public class HotelService {

    public enum ServiceType {
        SPA, GYM, RESTAURANT, ROOM_SERVICE
    }

    private static int idCounter = 2000;
    private int serviceId;
    private ServiceType serviceType;
    private Guest guest;
    private LocalDate date;
    private LocalTime time;
    private double cost;
    private String details;

    public HotelService(Guest guest, ServiceType serviceType, LocalDate date, LocalTime time, String details) throws ServiceUnavailableException, InvalidTimeSlotException {
        if (serviceType == null) {
            throw new ServiceUnavailableException("Service type cannot be null!");
        }
        if (time.getHour() < 8 || time.getHour() > 22) {
            throw new InvalidTimeSlotException("Services are only available between 8AM and 10PM!");
        }
        this.serviceId = ++idCounter;
        this.guest = guest;
        this.serviceType = serviceType;
        this.date = date;
        this.time = time;
        this.details = details;
        this.cost = calculateCost(serviceType);
    }

    private double calculateCost(ServiceType type) {
        switch (type) {
            case SPA: return 150.0;
            case GYM: return 50.0;
            case RESTAURANT: return 100.0;
            case ROOM_SERVICE: return 75.0;
            default: return 0.0;
        }
    }

    public int getServiceId() { return serviceId; }
    public ServiceType getServiceType() { return serviceType; }
    public Guest getGuest() { return guest; }
    public LocalDate getDate() { return date; }
    public LocalTime getTime() { return time; }
    public double getCost() { return cost; }
    public String getDetails() { return details; }

    @Override
    public String toString() {
        return "Service #" + serviceId + " | Type: " + serviceType + " | Date: " + date + " | Time: " + time + " | Cost: $" + cost + " | Details: " + details;
    }
}