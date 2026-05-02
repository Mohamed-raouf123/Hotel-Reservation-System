package com.mycompany.uniproject;

import java.sql.*;
import java.time.LocalDate;

public class DatabaseManager {

    static final String DB_URL = "jdbc:sqlite:hotel.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void init() {
        createTables();
        loadAll();
        for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) saveRoomType(HotelDatabase.roomTypes.get(i));
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) saveAmenity(HotelDatabase.amenities.get(i));
    }

    static void createTables() {
        try {
            Connection conn = getConnection();
            Statement stmt = conn.createStatement();

            stmt.execute("CREATE TABLE IF NOT EXISTS room_types (id INTEGER PRIMARY KEY, name TEXT, price REAL, capacity INTEGER, max_count INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS amenities (id INTEGER PRIMARY KEY, name TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS rooms (room_number INTEGER PRIMARY KEY, type_id INTEGER, is_available INTEGER DEFAULT 1)");
            stmt.execute("CREATE TABLE IF NOT EXISTS room_amenities (room_number INTEGER, amenity_id INTEGER, PRIMARY KEY(room_number, amenity_id))");
            stmt.execute("CREATE TABLE IF NOT EXISTS guests (username TEXT PRIMARY KEY, password TEXT, dob TEXT, balance REAL, address TEXT, gender TEXT, phone INTEGER, pref_room_type_id INTEGER, pref_floor INTEGER, smoking INTEGER, sea_view INTEGER)");
            stmt.execute("CREATE TABLE IF NOT EXISTS admins (username TEXT PRIMARY KEY, password TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS receptionists (username TEXT PRIMARY KEY, password TEXT)");
            stmt.execute("CREATE TABLE IF NOT EXISTS reservations (id INTEGER PRIMARY KEY, guest_username TEXT, room_number INTEGER, check_in TEXT, check_out TEXT, status TEXT)");

            conn.close();
        } catch (SQLException e) {
            System.out.println("Error creating tables: " + e.getMessage());
        }
    }

    static void loadAll() {
        loadRoomTypes();
        loadAmenities();
        loadRooms();
        loadGuests();
        loadAdmins();
        loadReceptionists();
        loadReservations();
    }

    static void loadRoomTypes() {
        try {
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM room_types");
            while (rs.next()) {
                int id = rs.getInt("id");
                boolean found = false;
                for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
                    if (HotelDatabase.roomTypes.get(i).getId() == id) { found = true; break; }
                }
                if (!found) {
                    HotelDatabase.roomTypes.add(new RoomType(id, rs.getString("name"), rs.getDouble("price"), rs.getInt("capacity"), rs.getInt("max_count")));
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("loadRoomTypes error: " + e.getMessage());
        }
    }

    static void loadAmenities() {
        try {
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM amenities");
            while (rs.next()) {
                int id = rs.getInt("id");
                boolean found = false;
                for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
                    if (HotelDatabase.amenities.get(i).getId() == id) { found = true; break; }
                }
                if (!found) {
                    HotelDatabase.amenities.add(new Amenity(id, rs.getString("name")));
                }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("loadAmenities error: " + e.getMessage());
        }
    }

    static void loadRooms() {
        try {
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM rooms");
            while (rs.next()) {
                int roomNum = rs.getInt("room_number");
                int typeId = rs.getInt("type_id");
                boolean available = rs.getInt("is_available") == 1;

                RoomType rt = null;
                for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
                    if (HotelDatabase.roomTypes.get(i).getId() == typeId) { rt = HotelDatabase.roomTypes.get(i); break; }
                }
                if (rt == null) continue;

                Room room = new Room(roomNum, rt);
                room.setAvailable(available);

                PreparedStatement ps = conn.prepareStatement("SELECT amenity_id FROM room_amenities WHERE room_number = ?");
                ps.setInt(1, roomNum);
                ResultSet ars = ps.executeQuery();
                while (ars.next()) {
                    int aid = ars.getInt("amenity_id");
                    for (int i = 0; i < HotelDatabase.amenities.size(); i++) {
                        if (HotelDatabase.amenities.get(i).getId() == aid) { room.addAmenity(HotelDatabase.amenities.get(i)); break; }
                    }
                }

                boolean found = false;
                for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
                    if (HotelDatabase.rooms.get(i).getRoomnumber() == roomNum) { found = true; break; }
                }
                if (!found) HotelDatabase.rooms.add(room);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("loadRooms error: " + e.getMessage());
        }
    }

    static void loadGuests() {
        try {
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM guests");
            while (rs.next()) {
                String username = rs.getString("username");
                boolean found = false;
                for (int i = 0; i < HotelDatabase.guests.size(); i++) {
                    if (HotelDatabase.guests.get(i).getUsername().equals(username)) { found = true; break; }
                }
                if (found) continue;

                RoomType prefType = null;
                int prefTypeId = rs.getInt("pref_room_type_id");
                for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) {
                    if (HotelDatabase.roomTypes.get(i).getId() == prefTypeId) { prefType = HotelDatabase.roomTypes.get(i); break; }
                }

                RoomPreferences prefs = new RoomPreferences(prefType, rs.getInt("pref_floor"), rs.getInt("smoking") == 1, rs.getInt("sea_view") == 1);
                Guest g = new Guest(username, rs.getString("password"), LocalDate.parse(rs.getString("dob")), rs.getDouble("balance"), rs.getString("address"), Gender.valueOf(rs.getString("gender")), prefs, rs.getLong("phone"));
                HotelDatabase.guests.add(g);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("loadGuests error: " + e.getMessage());
        }
    }

    static void loadAdmins() {
        try {
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM admins");
            while (rs.next()) {
                String username = rs.getString("username");
                boolean found = false;
                for (int i = 0; i < HotelDatabase.admins.size(); i++) {
                    if (HotelDatabase.admins.get(i).getUsername().equals(username)) { found = true; break; }
                }
                if (!found) HotelDatabase.admins.add(new Admin(username, rs.getString("password")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("loadAdmins error: " + e.getMessage());
        }
    }

    static void loadReceptionists() {
        try {
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM receptionists");
            while (rs.next()) {
                String username = rs.getString("username");
                boolean found = false;
                for (int i = 0; i < HotelDatabase.receptionists.size(); i++) {
                    if (HotelDatabase.receptionists.get(i).getUsername().equals(username)) { found = true; break; }
                }
                if (!found) HotelDatabase.receptionists.add(new Receptionist(username, rs.getString("password")));
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("loadReceptionists error: " + e.getMessage());
        }
    }

    static void loadReservations() {
        try {
            Connection conn = getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM reservations");
            while (rs.next()) {
                int id = rs.getInt("id");
                boolean found = false;
                for (int i = 0; i < HotelDatabase.reservations.size(); i++) {
                    if (HotelDatabase.reservations.get(i).getReservationId() == id) { found = true; break; }
                }
                if (found) continue;

                Guest guest = null;
                String guestUsername = rs.getString("guest_username");
                for (int i = 0; i < HotelDatabase.guests.size(); i++) {
                    if (HotelDatabase.guests.get(i).getUsername().equals(guestUsername)) { guest = HotelDatabase.guests.get(i); break; }
                }

                Room room = null;
                int roomNumber = rs.getInt("room_number");
                for (int i = 0; i < HotelDatabase.rooms.size(); i++) {
                    if (HotelDatabase.rooms.get(i).getRoomnumber() == roomNumber) { room = HotelDatabase.rooms.get(i); break; }
                }

                if (guest == null || room == null) continue;

                room.setAvailable(true);
                Reservation res = new Reservation(guest, room, LocalDate.parse(rs.getString("check_in")), LocalDate.parse(rs.getString("check_out")));
                res.setStatus(ReservationStatus.valueOf(rs.getString("status")));
                res.setReservationId(id);

                if (res.getStatus() == ReservationStatus.CANCELLED || res.getStatus() == ReservationStatus.COMPLETED) {
                    room.setAvailable(true);
                }

                HotelDatabase.reservations.add(res);
                guest.getReservations().add(res);
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("loadReservations error: " + e.getMessage());
        }
    }

    public static void saveGuest(Guest g) {
        int prefTypeId = -1;
        int prefFloor = 0;
        int smoking = 0;
        int seaView = 0;
        if (g.getRoomPreferences() != null) {
            RoomPreferences prefs = g.getRoomPreferences();
            if (prefs.getPreferredRoomType() != null) prefTypeId = prefs.getPreferredRoomType().getId();
            prefFloor = prefs.getPreferredFloor();
            smoking = prefs.isSmokingAllowed() ? 1 : 0;
            seaView = prefs.isSeaView() ? 1 : 0;
        }
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT OR REPLACE INTO guests VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, g.getUsername());
            ps.setString(2, g.getPassword());
            ps.setString(3, g.getDateOfBirth().toString());
            ps.setDouble(4, g.getBalance());
            ps.setString(5, g.getAddress());
            ps.setString(6, g.getGender().name());
            ps.setLong(7, g.getPhone());
            ps.setInt(8, prefTypeId);
            ps.setInt(9, prefFloor);
            ps.setInt(10, smoking);
            ps.setInt(11, seaView);
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("saveGuest error: " + e.getMessage());
        }
    }

    public static void saveRoom(Room r) {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT OR REPLACE INTO rooms VALUES (?, ?, ?)");
            ps.setInt(1, r.getRoomnumber());
            ps.setInt(2, r.getRoomType().getId());
            ps.setInt(3, r.isAvailable() ? 1 : 0);
            ps.executeUpdate();

            conn.createStatement().execute("DELETE FROM room_amenities WHERE room_number = " + r.getRoomnumber());
            for (int i = 0; i < r.getAmenities().size(); i++) {
                PreparedStatement ps2 = conn.prepareStatement("INSERT OR IGNORE INTO room_amenities VALUES (?, ?)");
                ps2.setInt(1, r.getRoomnumber());
                ps2.setInt(2, r.getAmenities().get(i).getId());
                ps2.executeUpdate();
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println("saveRoom error: " + e.getMessage());
        }
    }

    public static void saveReservation(Reservation res) {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT OR REPLACE INTO reservations (id, guest_username, room_number, check_in, check_out, status) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, res.getReservationId());
            ps.setString(2, res.getGuest().getUsername());
            ps.setInt(3, res.getRoom().getRoomnumber());
            ps.setString(4, res.getCheckInDate().toString());
            ps.setString(5, res.getCheckOutDate().toString());
            ps.setString(6, res.getStatus().name());
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("saveReservation error: " + e.getMessage());
        }
    }

    public static void saveRoomType(RoomType rt) {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT OR REPLACE INTO room_types VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, rt.getId());
            ps.setString(2, rt.getName());
            ps.setDouble(3, rt.getPricePerNight());
            ps.setInt(4, rt.getCapacity());
            ps.setInt(5, rt.getMaxCount());
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("saveRoomType error: " + e.getMessage());
        }
    }

    public static void saveAmenity(Amenity a) {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT OR REPLACE INTO amenities VALUES (?, ?)");
            ps.setInt(1, a.getId());
            ps.setString(2, a.getName());
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("saveAmenity error: " + e.getMessage());
        }
    }

    public static void saveAdmin(Admin a) {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT OR REPLACE INTO admins VALUES (?, ?)");
            ps.setString(1, a.getUsername());
            ps.setString(2, a.getPassword());
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("saveAdmin error: " + e.getMessage());
        }
    }

    public static void saveReceptionist(Receptionist r) {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT OR REPLACE INTO receptionists VALUES (?, ?)");
            ps.setString(1, r.getUsername());
            ps.setString(2, r.getPassword());
            ps.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.out.println("saveReceptionist error: " + e.getMessage());
        }
    }

    public static void saveAll() {
        for (int i = 0; i < HotelDatabase.guests.size(); i++) saveGuest(HotelDatabase.guests.get(i));
        for (int i = 0; i < HotelDatabase.rooms.size(); i++) saveRoom(HotelDatabase.rooms.get(i));
        for (int i = 0; i < HotelDatabase.reservations.size(); i++) saveReservation(HotelDatabase.reservations.get(i));
        for (int i = 0; i < HotelDatabase.roomTypes.size(); i++) saveRoomType(HotelDatabase.roomTypes.get(i));
        for (int i = 0; i < HotelDatabase.amenities.size(); i++) saveAmenity(HotelDatabase.amenities.get(i));
        for (int i = 0; i < HotelDatabase.admins.size(); i++) saveAdmin(HotelDatabase.admins.get(i));
        for (int i = 0; i < HotelDatabase.receptionists.size(); i++) saveReceptionist(HotelDatabase.receptionists.get(i));
    }

    public static void deleteRoom(int roomNumber) {
        try {
            Connection conn = getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM rooms WHERE room_number = ?");
            ps.setInt(1, roomNumber);
            ps.executeUpdate();
            conn.createStatement().execute("DELETE FROM room_amenities WHERE room_number = " + roomNumber);
            conn.close();
        } catch (SQLException e) {
            System.out.println("deleteRoom error: " + e.getMessage());
        }
    }
}