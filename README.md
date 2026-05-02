Hotel Reservation System

CSE241 — Object-Oriented Computer Programming
Faculty of Engineering, Ain Shams University — 2nd Semester 2025/2026

A JavaFX desktop application for managing hotel operations: guest reservations, staff workflows, and live communication between guests and receptionists.

# Team

- Mohamed Raouf
- Saeb Alrayyes
- Omar Abdelrahman
- Yousef Sameh
- Saja Yousef

# Features

 Guest Features
- Account registration with validation (age, password strength, duplicate username)
- Login with credential checking
- Browse available rooms with multi-criteria filtering (type, price, amenities)
- Make and cancel reservations
- View active reservations on the dashboard
- Book hotel services (spa, gym, restaurant, room service)
- Checkout and pay invoices via cash, credit card, or online
- Live chat with the receptionist

 Receptionist Features
- View all guests and rooms
- Create reservations on a guest's behalf
- Process check-ins and check-outs
- Live chat with guests

 Admin Features
- Full CRUD on rooms, room types, and amenities
- Capacity-aware room creation (each type has a maximum count)
- View all guests, rooms, and reservations

 Technology Stack

- Language: Java 17
- GUI: JavaFX with FXML and CSS
- Build: Apache Maven
- Concurrency: JavaFX Timeline, threads, Platform.runLater
- Networking: Java Sockets (ServerSocket / Socket)

 How to Run

1. Clone this repository
2. Open the project in IntelliJ IDEA or NetBeans as a Maven project
3. Let Maven download dependencies
4. Run the Testmain class

 Sample Login Credentials

Guests:
- Saeb alrayyes / SAEB_312
- Abdellah Mohamed / aboody5213
- Yazan ammar / yazan1234
- mariam Ali / mariam12345

Admins:
- mohamed raouf / eng.raouf123
- yousef sameh / yousef.ali321

Receptionists:
- saja yousef / eng.saja521
- Omar abdelrahman / omar.rhman5213
- Ali Mohamed / ali.123456

# Project Structure


src/main/java/com/mycompany/uniproject/
├── Testmain.java                 — JavaFX entry point
├── HotelDatabase.java            — In-memory data store
├── ChatServer.java               — Socket server for live chat
├── controllers/                  — All FXML controllers
│   ├── AlertHelper.java
│   ├── LoginController.java
│   ├── GuestDashboardController.java
│   ├── RoomBrowsingController.java
│   └── ... (12 controllers total)
├── Guest.java, Admin.java, Receptionist.java
├── Room.java, RoomType.java, Amenity.java
├── Reservation.java, Invoice.java, HotelService.java
└── *.java exception classes

src/main/resources/com/mycompany/uniproject/
├── fxml/                         — All screen layouts
├── images/                       — Icon
└── style.css                     — Application stylesheet


## Demo Video


See `Hotel_Reservation_System_Report.docx` in this repository for the full project report.
EADME.md…]()
