package com.mycompany.uniproject;
import java.time.LocalDate;
import java.util.ArrayList;

public class HotelDatabase {
    public static ArrayList<Room> rooms = new ArrayList<>();
    public static ArrayList<Guest> guests = new ArrayList<>();
    public static ArrayList<RoomType> roomTypes = new ArrayList<>();
    public static ArrayList<Amenity> amenities = new ArrayList<>();
    public static ArrayList<Receptionist> receptionists = new ArrayList<>();
    public static ArrayList<Admin> admins = new ArrayList<>();
    public static ArrayList<Invoice> invoices = new ArrayList<>();
    public static ArrayList<Reservation> reservations = new ArrayList<>();
    public static ArrayList<HotelService> services = new ArrayList<>();

    public static void populateAmenities() {
        Amenity wifi = new Amenity(1, "wifi");
        Amenity TV = new Amenity(2, "Tv");
        Amenity Mini_bar = new Amenity(3, "mini bar");
        Amenity pool = new Amenity(4, "Swimming Pool");
        Amenity gym = new Amenity(5, "Gym");
        Amenity spa = new Amenity(6, "Spa");
        Amenity cinema = new Amenity(7,"Cinema");
        Amenity alley = new Amenity(8, "Bowling Alley");
        Amenity closet = new Amenity(9,"Walk-in Closet");
        Amenity bed = new Amenity(10,"Master Bed");
        Amenity bed_2 = new Amenity(10,"Normal Bed");
        Amenity parking = new Amenity(11 ,"Private Parking");
        amenities.add(wifi);
        amenities.add(TV);
        amenities.add(Mini_bar);
        amenities.add(pool);
        amenities.add(gym);
        amenities.add(spa);
        amenities.add(cinema);
        amenities.add(alley);
        amenities.add(closet);
        amenities.add(bed);
        amenities.add(bed_2);
        amenities.add(parking);
    }

    public static void populateRoomtype() {
        RoomType singleRoom = new RoomType(100, "single room", 120, 1);
        RoomType doubleRoom = new RoomType(101, "double room", 180, 2);
        RoomType suiteRoom = new RoomType(102, "suite room", 700, 8);
        RoomType penthouseRoom = new RoomType(103, "penthouse", 2500, 12);
        RoomType familyRoom = new RoomType(104, "family", 300, 6);
        roomTypes.add(singleRoom);
        roomTypes.add(doubleRoom);
        roomTypes.add(suiteRoom);
        roomTypes.add(penthouseRoom);
        roomTypes.add(familyRoom);
    }

    public static void populateRooms() {
        Room room100 = new Room(100, roomTypes.get(0));
        Room room101 = new Room(101, roomTypes.get(0));
        Room room102 = new Room(102, roomTypes.get(1));
        Room room103 = new Room(103, roomTypes.get(1));
        Room room104 = new Room(104, roomTypes.get(2));
        Room room105 = new Room(105, roomTypes.get(3));
        Room room106 = new Room(106, roomTypes.get(4));
        rooms.add(room100);
        rooms.add(room101);
        rooms.add(room102);
        rooms.add(room103);
        rooms.add(room104);
        rooms.add(room105);
        rooms.add(room106);
        room100.addAmenity(amenities.get(0));
        room100.addAmenity(amenities.get(1));
        room100.addAmenity(amenities.get(2));
        room100.addAmenity(amenities.get(10));
        room101.addAmenity(amenities.get(0));
        room101.addAmenity(amenities.get(1));
        room101.addAmenity(amenities.get(2));
        room101.addAmenity(amenities.get(10));
        room102.addAmenity(amenities.get(0));
        room102.addAmenity(amenities.get(1));
        room102.addAmenity(amenities.get(1));
        room102.addAmenity(amenities.get(2));
        room102.addAmenity(amenities.get(10));
        room102.addAmenity(amenities.get(10));
        room103.addAmenity(amenities.get(0));
        room103.addAmenity(amenities.get(1));
        room103.addAmenity(amenities.get(1));
        room103.addAmenity(amenities.get(2));
        room103.addAmenity(amenities.get(10));
        room103.addAmenity(amenities.get(10));
        room104.addAmenity(amenities.get(0));
        room104.addAmenity(amenities.get(1));
        room104.addAmenity(amenities.get(2));
        room104.addAmenity(amenities.get(9));
        room104.addAmenity(amenities.get(11));
        room105.addAmenity(amenities.get(0));
        room105.addAmenity(amenities.get(1));
        room105.addAmenity(amenities.get(1));
        room105.addAmenity(amenities.get(2));
        room105.addAmenity(amenities.get(9));
        room105.addAmenity(amenities.get(4));
        room105.addAmenity(amenities.get(5));
        room105.addAmenity(amenities.get(11));
        room106.addAmenity(amenities.get(0));
        room106.addAmenity(amenities.get(1));
        room106.addAmenity(amenities.get(2));
        room106.addAmenity(amenities.get(10));
        room106.addAmenity(amenities.get(10));
        room106.addAmenity(amenities.get(9));

    }

    public static void populateStaff() {
        Admin admin1 = new Admin("mohamed raouf", "eng.raouf123");
        Admin admin2 = new Admin("yousef sameh", "yousef.ali321");
        Receptionist receptionist1 = new Receptionist("saja yousef", "eng.saja521");
        Receptionist receptionist2 = new Receptionist("Omar abdelrahman", "omar.rhman5213");
        Receptionist receptionist3 = new Receptionist("Ali Mohamed", "ali.123456");
        admins.add(admin1);
        admins.add(admin2);
        receptionists.add(receptionist1);
        receptionists.add(receptionist2);
        receptionists.add(receptionist3);
    }

    public static void populateGuest() {
        RoomPreferences roomPreferences1 = new RoomPreferences(roomTypes.get(2), 5, false, true);
        Guest guest1 = new Guest("Saeb alrayyes", "SAEB_312", LocalDate.of(2007, 4, 13), 10000, "1 Elsarayat St., Abbaseya, 11517 Cairo, Egypt", Gender.MALE, roomPreferences1, 0123210323);
        Guest guest2 = new Guest("Abdellah Mohamed", "aboody5213", LocalDate.of(2007, 12, 21), 14000, "Cairo, Egypt", Gender.MALE, null, 1109876520L);
        Guest guest3 = new Guest("Yazan ammar", "yazan1234", LocalDate.of(2007, 10, 8), 8000, "Cairo, Egypt", Gender.MALE, null, 1109876520L);
        Guest guest4 = new Guest("mariam Ali", "mariam12345", LocalDate.of(1995, 6, 20),3000 , "Alexandria, Egypt", Gender.FEMALE, null, 1112345678L);
        guests.add(guest1);
        guests.add(guest2);
        guests.add(guest3);
        guests.add(guest4);
    }
}
