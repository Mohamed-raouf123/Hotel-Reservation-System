import java.util.*;

public abstract class AdminReceptionist {

    private String username;
    private String password;
    private Date birthDate;
    private int workingHours;
    private Role role;


    AdminReceptionist(String username, String password, Role role) {
        this.password = password;
        this.username = username;
        this.role = role;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setWorkingHours(int workingHours) {
        this.workingHours = workingHours;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getWorkingHours() {
        return workingHours;
    }

    public Role getRole() {
        return role;
    }

    public abstract void makeBehaviour();

}

class Receptionist extends AdminReceptionist {

    Receptionist(String username, String password) {
        super(username,password,Role.RECEPTIONIST);
    }

    void checkIn(int num) {
        for (int i = 0 ; i < HotelDatabase.rooms.size(); i++){
            if (HotelDatabase.rooms.get(i).getRn() == num){
                HotelDatabase.rooms.get(i).bookRoom();
            }
        }

    }

    void checkOut(int n) {
        for (int i = 0 ; i < HotelDatabase.rooms.size(); i++){
            if (HotelDatabase.rooms.get(i).getRn() == n){
                HotelDatabase.rooms.get(i).releaseRoom();
            }
        }
    }

    @Override
    public void makeBehaviour( ) {
        System.out.println("Receptionist is handling guest check-ins and check-outs");
    }
}

class Admin extends AdminReceptionist {

    Admin(String username, String password) {
        super(username, password, Role.ADMIN);
    }

    public void createRoom(int rn, RoomType roomtype){
        Room room = new Room(rn , roomtype);
        HotelDatabase.rooms.add(room);
    }

    public void createRoomType(int i,String n,double p, int c){
        RoomType typies = new RoomType(i, n , p, c);
        HotelDatabase.roomTypes.add(typies);
    }
    public void createAmenity(int num,String n){
        Amenity amenities = new Amenity(num , n);
        HotelDatabase.amenities.add(amenities);
    }

    public void deleteRoom (int id){
        for (int i = 0 ; i <HotelDatabase.rooms.size();i++){
            if(HotelDatabase.rooms.get(i).getRn() == id){
                HotelDatabase.rooms.remove(i);
            }
        }
    }

    public void deleteroomType(int id){
        for(int i = 0; i<HotelDatabase.roomTypes.size();i++){
            if(HotelDatabase.roomTypes.get(i).getId() == id){
                HotelDatabase.roomTypes.remove(i);
            }
        }
    }

    public void deleteAmenities(String name){
        for (int i = 0 ; i<HotelDatabase.amenities.size();i++){
            if (HotelDatabase.amenities.get(i).getName().equals(name)){
                HotelDatabase.amenities.remove(i);
            }
        }
    }

    public void updateRoom(int num, RoomType newRoomType){
        for (int i = 0 ; i < HotelDatabase.rooms.size(); i++){
            if(HotelDatabase.rooms.get(i).getRn() == num){
                HotelDatabase.rooms.get(i).setRoomType(newRoomType);
            }
        }
    }

    public void updateAmenities(int num, Amenity newAmenity){
        for (int i = 0 ; i < HotelDatabase.amenities.size(); i++){
            if(HotelDatabase.amenities.get(i).getId() == num){
                HotelDatabase.amenities.remove(i);
                HotelDatabase.amenities.add(newAmenity);
            }
        }
    }

    public void updateroomType(int num, RoomType newRoomType){
        for (int i = 0 ; i < HotelDatabase.roomTypes.size(); i++){
            if(HotelDatabase.roomTypes.get(i).getId() == num){
                HotelDatabase.roomTypes.remove(i);
                HotelDatabase.roomTypes.add(newRoomType);
            }
        }
    }

    public void ViewAll() {
        for (int i = 0 ; i < HotelDatabase.rooms.size();i++){
            System.out.println(HotelDatabase.rooms.get(i));
        }
        for(int i = 0 ; i<HotelDatabase.guests.size();i++){
            System.out.println(HotelDatabase.guests.get(i));
        }
        for(int i = 0 ; i<HotelDatabase.reservations.size();i++){
            System.out.println(HotelDatabase.reservations.get(i));
        }
    }

    @Override
    public void makeBehaviour( ) {
        System.out.println("Admin is managing the system");
    }
}
