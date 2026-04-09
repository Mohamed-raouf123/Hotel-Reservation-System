public class Amenity {
    //attributes
    private int id;
    private String name;
    //constructor
   public Amenity(int num,String n){
        id = num;
        name = n;
    }
    //Setter method
    public void setId(int id){
        if (id < 0) throw new IllegalArgumentException("ID cannot be negative");
        this.id = id;
    }
    public void setName(String name){
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Amenity name cannot be empty");
        }
        this.name = name;
    }
    //Getter method
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    //Method to print Amenities info
    @Override
    public String toString() {
        return "Amenities{" +
                "id=" + id +
                ", name='" + name+ "'" +
                "}";
    }
}