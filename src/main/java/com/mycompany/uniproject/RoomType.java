package com.mycompany.uniproject;
public class RoomType {
    private int id;
    private String name;
    private double price;
    private int capacity;
    private int maxCount;

    public RoomType(int i, String n, double p, int c, int max) {
        id = i; name = n; price = p; capacity = c; maxCount = max;
    }

    public void setCapacity(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("Capacity cannot be negative");
        this.capacity = capacity;
    }
    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setPrice(double price) {
        if (price < 0) throw new IllegalArgumentException("Price cannot be negative");
        this.price = price;
    }
    public void setMaxCount(int maxCount) {
        if (maxCount < 0) throw new IllegalArgumentException("Max count cannot be negative");
        this.maxCount = maxCount;
    }
    public String getName() { return name; }
    public int getId() { return id; }
    public int getCapacity() { return capacity; }
    public double getPricePerNight() { return price; }
    public int getMaxCount() { return maxCount; }


    @Override
    public String toString() {
        return "Room Type: " + name + " | ID: " + id + " | Price: $" + price + "/night | Capacity: " + capacity + " | Max rooms: " + maxCount;
    }
}
