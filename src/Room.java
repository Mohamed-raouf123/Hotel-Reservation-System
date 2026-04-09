import java.util.ArrayList;

public class Room<amenities> {
    //Attributes
    private int rn;
    private boolean isAvailable;
    private RoomType roomType;
    private ArrayList<Amenity> amenities;
    //constructor
    public Room(int rn, RoomType roomtype){
        this.rn = rn;
        this.roomType=roomtype;
        this.isAvailable= true;
        this.amenities = new ArrayList<Amenity>();
    }
    //Setter methods

    public void setAmenities(ArrayList<Amenity> amenities) {
        this.amenities = amenities;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public void setRn(int rn) {
        if(rn <= 0){throw new IllegalArgumentException("Room number cannot be negative OR Room number is unavailable");
        }
        this.rn = rn;
    }

    public void setRoomType(RoomType roomType) {
        if(roomType == null){throw new IllegalArgumentException("RoomType cant be null");
        }
        this.roomType = roomType;
    }
    //Getter method

    public ArrayList<Amenity> getAmenities() {
        return amenities;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public int getRn() {
        return rn;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    //Method to add/update amenities
    public void addAmenity(Amenity amenity ) {
        if(amenity == null){throw new IllegalArgumentException("amenity cannot be null");
        }
        amenities.add(amenity);
    }

    //Method to remove unwanted amenities
    public void removeAmenity(int id){
        for(int i = 0;i<amenities.size();i++){
            if (amenities.get(i).getId() == id){
                amenities.remove(i);
            }
            break;
        }
    }
    //Method to read Room info
    @Override
    public String toString() {
        return "ROOM{" +
                "Room number=" + rn +
                ", Is it availiabe='" + isAvailable + "'" +
                ", RoomType=" + roomType +
                ", Amenities availiabe=" + amenities +
                "}";
    }
}
