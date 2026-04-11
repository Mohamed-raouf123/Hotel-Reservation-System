package com.mycompany.uniproject;
public class RoomType {
    //Attributes
    private int id;
    private String name;
    private double price;
    private int capacity;
    //constructor
    public RoomType(int i,String n,double p, int c){
        id = i;
        name = n;
        price = p;
        capacity = c;
    }
//Setter Method
    public void setCapacity(int capacity) {
        if(capacity < 0){throw new IllegalArgumentException("Price cannot be negative");
        }
        this.capacity = capacity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        if(price < 0){throw new IllegalArgumentException("Capacity cannot be negative");
        }
        this.price = price;
    }
    //Getter method
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPricePerNight() {
        return price;
    }
    //Method to print out Room info
    @Override
    public String toString() {
        return "RoomType{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", price=" + price +
                ", capacity=" + capacity +
                "}";
    }
}