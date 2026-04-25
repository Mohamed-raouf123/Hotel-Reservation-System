package com.mycompany.uniproject;

public class Admin extends AdminReceptionist {

    public Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }

    public void createRoom(int rn, RoomType roomtype) {
        Room room = new Room(rn, roomtype);
        HotelDatabase.rooms.add(room);
    }

    public void createRoomType(int i, String n, double p, int c) {
        RoomType typies = new RoomType(i, n, p, c);
        HotelDatabase.roomTypes.add(typies);
    }

    public void createAmenity(int num, String n) {
        Amenity amenities = new Amenity(num, n);
        HotelDatabase.amenities.add(amenities);
    }

    public void deleteRoom(int id) {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            if (HotelDatabase.rooms.get(i).getRoomnumber() == id) {
                HotelDatabase.rooms.remove(i);
            }
        }
    }

    public void deleteroomType(int id) {
        for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
            if (HotelDatabase.roomTypes.get(i).getId() == id) {
                HotelDatabase.roomTypes.remove(i);
            }
        }
    }

    public void deleteAmenities(String name) {
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            if (HotelDatabase.amenities.get(i).getName().equals(name)) {
                HotelDatabase.amenities.remove(i);
            }
        }
    }

    public void updateRoom(int num, RoomType newRoomType) {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
            if (HotelDatabase.rooms.get(i).getRoomnumber() == num) {
                HotelDatabase.rooms.get(i).setRoomType(newRoomType);
            }
        }
    }

    public void updateAmenities(int num, Amenity newAmenity) {
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
            if (HotelDatabase.amenities.get(i).getId() == num) {
                HotelDatabase.amenities.remove(i);
                HotelDatabase.amenities.add(newAmenity);
            }
        }
    }

    public void updateroomType(int num, RoomType newRoomType) {
        for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
            if (HotelDatabase.roomTypes.get(i).getId() == num) {
                HotelDatabase.roomTypes.remove(i);
                HotelDatabase.roomTypes.add(newRoomType);
            }
        }
    }

    public void ViewAll() {
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) { System.out.println(HotelDatabase.rooms.get(i)); }
        for (int i = 0; i < HotelDatabase.guests.size(); i++) { System.out.println(HotelDatabase.guests.get(i)); }
        for (int i = 0; i < HotelDatabase.reservations.size(); i++) { System.out.println(HotelDatabase.reservations.get(i)); }
    }

    @Override
    public void makeBehaviour() {
        System.out.println("Admin is managing the system");
    }
}
