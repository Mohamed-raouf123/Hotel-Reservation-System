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

    public static void populateAmenities() {
        Amenity wifi = new Amenity(1 , "wifi");
        Amenity TV = new Amenity(2 ,"Tv");
        Amenity Mini_bar = new Amenity(3 , "mini bar");
        amenities.add(wifi);
        amenities.add(TV);
        amenities.add(Mini_bar);
    }

    public static void populateRoomtype(){
        RoomType singleRoom = new RoomType(100 ,"singleroom", 56.99, 1);
        RoomType doubleRoom = new RoomType(101, "doubleroom" , 113.98, 4);
        RoomType suiteRoom = new RoomType(102 , "suiteroom" ,200, 8);
        roomTypes.add(singleRoom);
        roomTypes.add(doubleRoom);
        roomTypes.add(suiteRoom);
    }
    public static void populateRooms(){
        Room room100 = new Room(100, roomTypes.get(0));
        Room room101 = new Room ( 101 , roomTypes.get(1));
        Room room102 = new Room (102 , roomTypes.get(2));
        rooms.add(room100);
        rooms.add(room101);
        rooms.add(room102);
        room100.addAmenity(amenities.get(0));
        room101.addAmenity(amenities.get(0));
        room101.addAmenity(amenities.get(1));
        room102.addAmenity(amenities.get(0));
        room102.addAmenity(amenities.get(1));
        room102.addAmenity(amenities.get(2));

    }
    public static void main(String[] args){
        HotelDatabase.populateRoomtype();
        HotelDatabase.populateAmenities();
        HotelDatabase.populateRooms();
        for(int i = 0; i<HotelDatabase.rooms.size();i++){
            System.out.println(rooms.get(i));
        }
    }

}
