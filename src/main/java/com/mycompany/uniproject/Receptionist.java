package com.mycompany.uniproject;

public class Receptionist extends AdminReceptionist {

    public Receptionist(String username, String password) {
        super(username, password, Role.RECEPTIONIST);
    }

    public void checkIn(int num) {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            if (HotelDatabase.rooms.get(i).getRoomnumber() == num) {
                HotelDatabase.rooms.get(i).bookRoom();
            }
        }
    }

    public void checkOut(int n) {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            if (HotelDatabase.rooms.get(i).getRoomnumber() == n) {
                HotelDatabase.rooms.get(i).releaseRoom();
            }
        }
    }

    @Override
    public void makeBehaviour() {
        System.out.println("Receptionist is handling guest check-ins and check-outs");
    }
}
