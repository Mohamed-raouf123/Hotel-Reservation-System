package com.mycompany.uniproject;
import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;

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
        Amenity wifi = new Amenity(1, "wifi");
        Amenity TV = new Amenity(2, "Tv");
        Amenity Mini_bar = new Amenity(3, "mini bar");
        amenities.add(wifi);
        amenities.add(TV);
        amenities.add(Mini_bar);
    }

    public static void populateRoomtype() {
        RoomType singleRoom = new RoomType(100, "singleroom", 56.99, 1);
        RoomType doubleRoom = new RoomType(101, "doubleroom", 113.98, 4);
        RoomType suiteRoom = new RoomType(102, "suiteroom", 200, 8);
        roomTypes.add(singleRoom);
        roomTypes.add(doubleRoom);
        roomTypes.add(suiteRoom);
    }

    public static void populateRooms() {
        Room room100 = new Room(100, roomTypes.get(0));
        Room room101 = new Room(101, roomTypes.get(1));
        Room room102 = new Room(102, roomTypes.get(2));
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

    public static void populateStaff() {
        Admin admin1 = new Admin("mohamed raouf", "eng.raouf123");
        Admin admin2 = new Admin("yousef sameh", "yousef.ali321");
        Receptionist receptionist1 = new Receptionist("saja yousef", "eng.saja521");
        Receptionist receptionist2 = new Receptionist("Omar abdelrahman", "omar.rhman5213");
        admins.add(admin1);
        admins.add(admin2);
        receptionists.add(receptionist1);
        receptionists.add(receptionist2);
    }

    public static void populateGuest() {
        RoomPreferences roomPreferences1 = new RoomPreferences(roomTypes.get(2), 5, false, true);
        Guest guest1 = new Guest("Saeb alrayyes", "SAEB_312", LocalDate.of(2007, 4, 13), 1000, "1 Elsarayat St., Abbaseya, 11517 Cairo, Egypt", Gender.MALE, roomPreferences1, 0123210323);
        guests.add(guest1);
    }

    public static void main(String[] args) {
        MenuDisplay menu = new MenuDisplay();
        menu.showBanner();
        HotelDatabase.populateRoomtype();
        HotelDatabase.populateAmenities();
        HotelDatabase.populateRooms();
        HotelDatabase.populateStaff();
        HotelDatabase.populateGuest();
        Scanner input = new Scanner(System.in);
        System.out.println(" ----------------------------------------- " +
                "                 WELCOME TO AIN SHAMS GRAND HOTEL     " +
                "            ----------------------------------------- ");
        while (true) {
            System.out.println("WELCOME TO AIN SHAMS GRAND HOTEL SYSTEM  ");
            System.out.println(
                    "PLEASE IDENTIFY WHO IS ENTERING THE SYSTEM  ");
            System.out.println(
                    "ENTER 1 IF YOURE THE ADMIN");
            System.out.println(
                    "ENTER 2 IF YOURE THE RECEPTIONIST");
            System.out.println(
                    "ENTER 3 IF YOURE THE GUEST");
            System.out.println(
                    "ENTER 4 IF YOU WANT TO EXIT THE SYSTEM");
            int choice = input.nextInt();
            input.nextLine();
            if (choice == 1) {
                System.out.println("ENTER YOUR USERNAME");
                String EnteredUsername = input.nextLine();
                System.out.println("ENTER YOUR PASSWORD");
                String EnteredPassword = input.nextLine();
                boolean found = false;
                Admin loggedInAdmin = null;
                for (int i = 0; i < HotelDatabase.admins.size(); i++) {
                    AdminReceptionist admin = HotelDatabase.admins.get(i);
                    if (admin.getUsername().equals(EnteredUsername) && admin.getPassword().equals(EnteredPassword)) {
                        found = true;
                        loggedInAdmin = (Admin) admin;
                        System.out.println("WELCOME" + admin.getUsername());
                        break;
                    }

                    }
                if (!found) {
                    System.out.println("Invalid credentials, try again!");
                }
                else {
                    while (true){
                        System.out.println("WELCOME ADMIN");
                        System.out.println("WHAT WOULD YOU LIKE TO DO");
                        System.out.println("ENTER 1 IF YOU WOULD LIKE TO VIEW ALL ROOMS");
                        System.out.println("ENTER 2 IF YOU WOULD LIKE TO VIEW ALL GUESTS");
                        System.out.println("ENTER 3 IF YOU WOULD LIKE TO VIEW ALL RESERVATIONS");
                        System.out.println("ENTER 4 IF YOU WOULD LIKE TO ADD A ROOM");
                        System.out.println("ENTER 5 IF YOU WOULD LIKE TO DELETE A ROOM");
                        System.out.println("ENTER 5 IF YOU WOULD LIKE TO ADD A NEW AMENITY");
                        System.out.println("ENTER 6 IF YOU WOULD LIKE TO DELETE A AMENITY");
                        System.out.println("ENTER 7 IF YOU WOULD LIKE TO LOGOUT");
                        int adminChoice = input.nextInt();
                        input.nextLine();
                        if(adminChoice == 1){
                           for(int j = 0; j < HotelDatabase.rooms.size();j++){
                               System.out.println(HotelDatabase.rooms.get(j));
                           }
                                } else if (adminChoice == 2) {
                                    for (int j = 0 ; j < HotelDatabase.guests.size();j++){
                                        System.out.println(HotelDatabase.guests.get(j));
                                    }
                        } else if (adminChoice == 3) {
                            for (int j = 0 ; j < HotelDatabase.reservations.size(); j++){
                                System.out.println(HotelDatabase.reservations.get(j));
                            }

                        } else if (adminChoice == 4) {
                            for (int j = 0; j < HotelDatabase.roomTypes.size(); j++) {
                                System.out.println(j + " - " + HotelDatabase.roomTypes.get(j));
                            }
                            System.out.println("ENTER THE ROOM NUMBER");
                            int RoomNumber  = input.nextInt();
                            System.out.println("CHOOSE A ROOM TYPE (ENTER A NUMBER)");
                            int roomTypechoice = input.nextInt();
                            input.nextLine();
                            System.out.println(HotelDatabase.roomTypes.get(choice));
                            loggedInAdmin.createRoom(RoomNumber, HotelDatabase.roomTypes.get(roomTypechoice));

                        } else if (adminChoice  == 5) {
                            for (int j = 0 ; j<HotelDatabase.rooms.size();j++){
                                System.out.println(j + " - " + HotelDatabase.rooms.get(j));
                            }
                            System.out.println("ENTER THE NUMBER OF THE ROOM YOU WANT TO DELETE ");
                            int roomNumber = input.nextInt();
                            input.nextLine();
                            loggedInAdmin.deleteRoom(roomNumber);
                        }
                        else if (adminChoice == 6) {
                            System.out.println("ENTER AMENITY ID");
                            int Id =input.nextInt();
                            System.out.println("ENTER AMENITY NAME");
                            input.nextLine();
                            String name = input.nextLine();
                            loggedInAdmin.createAmenity(Id, name);
                        }
                        else if (adminChoice  == 7 ) {
                            for  ( int j = 0 ; j<HotelDatabase.amenities.size();j++){
                                System.out.println(j + " - " + HotelDatabase.amenities.get(j));
                            }
                            System.out.println("ENTER THE NAME OF THE AMENITY YOU WANT TO DELETE");
                            String amenity = input.nextLine();
                            loggedInAdmin.deleteAmenities(amenity);
                        }
                        else if (adminChoice == 8) {
                            break;
                        }

                    }
                }
                    } else if (choice == 2) {
                        System.out.println("ENTER YOUR USERNAME");
                        String enteredUsername = input.nextLine();
                        System.out.println("ENTER YOUR PASSWORD");
                        String enteredPassword = input.nextLine();
                        boolean found1 = false;
                        for (int i = 0; i < HotelDatabase.receptionists.size(); i++) {
                            AdminReceptionist receptionist = HotelDatabase.receptionists.get(i);
                            if (receptionist.getUsername().equals(enteredUsername) && receptionist.getPassword().equals(enteredPassword)) {
                                found1 = true;
                                System.out.println("WELCOME" + receptionist.getUsername());
                                while(true){
                                    System.out.println("WELCOME RECEPTIONIST");
                                    System.out.println("WHAT WOULD YOU LIKE TO DO");
                                    System.out.println("ENTER 1 IF YOU WOULD LIKE TO VIEW ALL AVAILABLE ROOMS");
                                    System.out.println("ENTER 2 IF YOU WOULD LIKE TO REGISTER A NEW GUEST");
                                    System.out.println("ENTER 3 IF YOU WOULD LIKE TO MAKE A NEW RESERVATION");
                                    System.out.println("ENTER 4 IF YOU WOULD LIKE TO CHECK IN A GUEST");
                                    System.out.println("ENTER 5 IF YOU WOULD LIKE TO CHECK OUT A GUEST");
                                    System.out.println("ENTER 6 IF YOU WOULD LIKE TO LOGOUT");
                                    int receptionistChoice = input.nextInt();
                                    input.nextLine();
                                    if(receptionistChoice == 1){
                                        for(int x =0 ; x< HotelDatabase.rooms.size();x++){
                                            if(HotelDatabase.rooms.get(x).isAvailable()){
                                                System.out.println(HotelDatabase.rooms.get(x));
                                            }
                                        }
                                    }
                                    else if (receptionistChoice == 2) {
                                        System.out.println("IF YOU WANT TO REGISTER A NEW GUEST YOU MUST PROVIDE THE FOLLOWING" +
                                                "1- USERNAME" +
                                                "2- PASSWORD" +
                                                "3- DATE OF BIRTH" +
                                                "4- BALANCE" +
                                                "5- ADDRESS" +
                                                "6- WHETHER ITS A MALE OR FEMALE" +
                                                "7-PHONE NUMBER");
                                        System.out.println("ENTER THE GUEST USERNAME");
                                        String userName = input.nextLine();
                                        System.out.println("ENTER THE GUEST PASSWORD");
                                        String passWord = input.nextLine();
                                        System.out.println("ENTER THE GUEST BIRTH YEAR");
                                        int year = input.nextInt();
                                        System.out.println("ENTER THE GUEST BIRTH MONTH");
                                        int month = input.nextInt();
                                        System.out.println("ENTER THE GUEST BIRTH DAY");
                                        int day = input.nextInt();
                                        input.nextLine();
                                        System.out.println("ENTER THE GUEST ENTIRE BIRTHDATE");
                                        LocalDate date = LocalDate.of(year,month,day);
                                        System.out.println("ENTER THE GUESTS BALANCE");
                                        double balance = input.nextDouble();
                                        input.nextLine();
                                        System.out.println("ENTER THE GUEST ADDRESS");
                                        String address = input.nextLine();
                                        System.out.println("ENTER GENDER (1 FOR MALE, 2 FOR FEMALE):");
                                        int genderChoice = input.nextInt();
                                        input.nextLine();
                                        Gender gender;
                                        if (genderChoice == 1) {
                                            gender = Gender.MALE;
                                        } else {
                                            gender = Gender.FEMALE;
                                        }
                                        System.out.println("ENTER THE GUEST PHONE NUMBER");
                                        long phone = input.nextLong();
                                        System.out.println("WHAT WOULD BE THE PREFERRED FLOOR NUMBER FOR THE GUEST");
                                        int floorNumber = input.nextInt();
                                        System.out.println("SMOKING ALLOWED? (1 FOR YES, 0 FOR NO):");
                                        int smokingChoice = input.nextInt();
                                        boolean smoking = smokingChoice == 1;
                                        System.out.println("SEA VIEW? (1 FOR YES, 0 FOR NO):");
                                        int seaViewChoice = input.nextInt();
                                        boolean seaView = seaViewChoice == 1;
                                        for(int x = 0 ; x < HotelDatabase.roomTypes.size() ; x++){
                                            System.out.println(HotelDatabase.roomTypes.get(x));
                                        }
                                        System.out.println("CHOOSE PREFERRED ROOM TYPE:");
                                        int prefChoice = input.nextInt();
                                        input.nextLine();
                                        RoomPreferences roomPreferences = new RoomPreferences(HotelDatabase.roomTypes.get(prefChoice), floorNumber,smoking,seaView);
                                        Guest newGuest = new Guest(userName,passWord,date,balance,address,gender,roomPreferences,phone);
                                        HotelDatabase.guests.add(newGuest);
                                        System.out.println("GUEST HAS BEEN ADDED SUCCESSFULLY");
                                    }
                                    else if (receptionistChoice == 3) {
                                        for (int j = 0; j < HotelDatabase.guests.size(); j++) {
                                            System.out.println(j + " - " + HotelDatabase.guests.get(j));
                                        }
                                        System.out.println("ENTER GUEST USERNAME:");
                                        String guestUsername = input.nextLine();
                                        Guest selectedGuest = null;
                                        for (int j = 0; j < HotelDatabase.guests.size(); j++) {
                                            if (HotelDatabase.guests.get(j).getUsername().equals(guestUsername)) {
                                                selectedGuest = HotelDatabase.guests.get(j);
                                                break;
                                            }
                                        }
                                        if (selectedGuest == null) {
                                            System.out.println("GUEST NOT FOUND!");
                                        } else {
                                            for (int j = 0; j < HotelDatabase.rooms.size(); j++) {
                                                if (HotelDatabase.rooms.get(j).isAvailable()) {
                                                    System.out.println(j + " - " + HotelDatabase.rooms.get(j));
                                                }
                                            }
                                            System.out.println("ENTER ROOM NUMBER:");
                                            int roomNumber = input.nextInt();
                                            Room selectedRoom = null;
                                            for (int j = 0; j < HotelDatabase.rooms.size(); j++) {
                                                if (HotelDatabase.rooms.get(j).getRoomnumber() == roomNumber) {
                                                    selectedRoom = HotelDatabase.rooms.get(j);
                                                    break;
                                                }
                                            }
                                            System.out.println("ENTER CHECK-IN YEAR:"); int ciYear = input.nextInt();
                                            System.out.println("ENTER CHECK-IN MONTH:"); int ciMonth = input.nextInt();
                                            System.out.println("ENTER CHECK-IN DAY:"); int ciDay = input.nextInt();
                                            System.out.println("ENTER CHECK-OUT YEAR:"); int coYear = input.nextInt();
                                            System.out.println("ENTER CHECK-OUT MONTH:"); int coMonth = input.nextInt();
                                            System.out.println("ENTER CHECK-OUT DAY:"); int coDay = input.nextInt();
                                            input.nextLine();
                                            LocalDate checkIn = LocalDate.of(ciYear, ciMonth, ciDay);
                                            LocalDate checkOut = LocalDate.of(coYear, coMonth, coDay);
                                            Reservation reservation = new Reservation(selectedGuest, selectedRoom, checkIn, checkOut);
                                            HotelDatabase.reservations.add(reservation);
                                            System.out.println("RESERVATION CREATED SUCCESSFULLY!");
                                        }
                                    }
                                    else if (receptionistChoice == 4) {
                                        for (int j = 0; j < HotelDatabase.reservations.size(); j++) {
                                            System.out.println(j + " - " + HotelDatabase.reservations.get(j));
                                        }
                                        System.out.println("ENTER RESERVATION ID:");
                                        int reservationId = input.nextInt();
                                        input.nextLine();
                                        for (int j = 0; j < HotelDatabase.reservations.size(); j++) {
                                            if (HotelDatabase.reservations.get(j).getReservationId() == reservationId) {
                                                HotelDatabase.reservations.get(j).confirmReservation();
                                                System.out.println("GUEST CHECKED IN SUCCESSFULLY!");
                                                break;
                                            }
                                        }
                                    }
                                    else if (receptionistChoice == 5) {
                                        for (int j = 0; j < HotelDatabase.reservations.size(); j++) {
                                            System.out.println(j + " - " + HotelDatabase.reservations.get(j));
                                        }
                                        System.out.println("ENTER RESERVATION ID:");
                                        int reservationId = input.nextInt();
                                        input.nextLine();
                                        for (int j = 0; j < HotelDatabase.reservations.size(); j++) {
                                            if (HotelDatabase.reservations.get(j).getReservationId() == reservationId) {
                                                HotelDatabase.reservations.get(j).cancelReservation();
                                                System.out.println("GUEST CHECKED OUT SUCCESSFULLY!");
                                                break;
                                            }
                                        }
                                    }
                                    else if (receptionistChoice == 6) {
                                        break;
                                    }
                                }
                                break;

                            }


                        }
                        if (!found1) {
                            System.out.println("Invalid credentials, try again!");
                        }

                    } else if (choice == 3) {while (true) {
                System.out.println("---------- GUEST MENU ----------");
                System.out.println("1. Login");
                System.out.println("2. Register");
                System.out.println("3. Back to main menu");
                int guestChoice = input.nextInt();
                input.nextLine();

                if (guestChoice == 1) {
                    System.out.println("ENTER YOUR USERNAME:");
                    String guestUsername = input.nextLine();
                    System.out.println("ENTER YOUR PASSWORD:");
                    String guestPassword = input.nextLine();
                    Guest loggedInGuest = null;
                    for (int i = 0; i < HotelDatabase.guests.size(); i++) {
                        if (HotelDatabase.guests.get(i).getUsername().equals(guestUsername) &&
                                HotelDatabase.guests.get(i).login(guestUsername, guestPassword)) {
                            loggedInGuest = HotelDatabase.guests.get(i);
                            break;
                        }
                    }
                    if (loggedInGuest == null) {
                        System.out.println("INVALID CREDENTIALS!");
                    } else {
                        while (true) {
                            System.out.println("---------- WELCOME " + loggedInGuest.getUsername() + " ----------");
                            System.out.println("1. View my reservations");
                            System.out.println("2. Cancel a reservation");
                            System.out.println("3. Checkout & pay invoice");
                            System.out.println("4. Logout");
                            int guestAction = input.nextInt();
                            input.nextLine();

                            if (guestAction == 1) {
                                loggedInGuest.viewReservations();
                            } else if (guestAction == 2) {
                                System.out.println("ENTER RESERVATION ID TO CANCEL:");
                                int resId = input.nextInt();
                                input.nextLine();
                                loggedInGuest.cancelReservation(resId);
                            } else if (guestAction == 3) {
                                Invoice invoice = loggedInGuest.checkout();
                                if (invoice != null) {
                                    System.out.println("CHOOSE PAYMENT METHOD:");
                                    System.out.println("1. CASH");
                                    System.out.println("2. CREDIT CARD");
                                    System.out.println("3. ONLINE");
                                    int payChoice = input.nextInt();
                                    input.nextLine();
                                    PaymentMethod method;
                                    if (payChoice == 1) method = PaymentMethod.CASH;
                                    else if (payChoice == 2) method = PaymentMethod.CREDIT_CARD;
                                    else method = PaymentMethod.ONLINE;
                                    invoice.processPayment(method);
                                    loggedInGuest.payInvoice(invoice);
                                }
                            } else if (guestAction == 4) {
                                System.out.println("GOODBYE " + loggedInGuest.getUsername() + "!");
                                break;
                            }
                        }
                    }
                } else if (guestChoice == 2) {
                    System.out.println("ENTER USERNAME:"); String username = input.next();
                    System.out.println("ENTER PASSWORD:"); String passWord = input.next();
                    System.out.println("ENTER BIRTH YEAR:"); int year = input.nextInt();
                    System.out.println("ENTER BIRTH MONTH:"); int month = input.nextInt();
                    System.out.println("ENTER BIRTH DAY:"); int day = input.nextInt();
                    input.nextLine();
                    LocalDate date = LocalDate.of(year, month, day);
                    System.out.println("ENTER BALANCE:"); double balance = input.nextDouble();
                    input.nextLine();
                    System.out.println("ENTER ADDRESS:"); String address = input.nextLine();
                    System.out.println("ENTER GENDER (1 FOR MALE, 2 FOR FEMALE):"); int genderChoice = input.nextInt();
                    input.nextLine();
                    Gender gender = genderChoice == 1 ? Gender.MALE : Gender.FEMALE;
                    System.out.println("ENTER PHONE NUMBER:"); long phone = input.nextLong();
                    System.out.println("ENTER PREFERRED FLOOR:"); int floorNumber = input.nextInt();
                    System.out.println("SMOKING ALLOWED? (1 FOR YES, 0 FOR NO):"); boolean smoking = input.nextInt() == 1;
                    System.out.println("SEA VIEW? (1 FOR YES, 0 FOR NO):"); boolean seaView = input.nextInt() == 1;
                    for (int x = 0; x < HotelDatabase.roomTypes.size(); x++) {
                        System.out.println(x + " - " + HotelDatabase.roomTypes.get(x));
                    }
                    System.out.println("CHOOSE PREFERRED ROOM TYPE:");
                    int prefChoice = input.nextInt();
                    input.nextLine();
                    RoomPreferences roomPreferences = new RoomPreferences(HotelDatabase.roomTypes.get(prefChoice), floorNumber, smoking, seaView);
                    Guest newGuest = new Guest(username, passWord, date, balance, address, gender, roomPreferences,phone);
                    HotelDatabase.guests.add(newGuest);
                    newGuest.register();
                } else if (guestChoice == 3) {
                    break;
                }
            }

            }
            else if (choice == 4){
                break;
            }
        }
        }
    }

