package com.mycompany.uniproject;

import java.io.*;
import java.net.*;

public class ChatServer {

    private ServerSocket serverSocket;
    private Socket guestSocket;
    private Socket receptionistSocket;
    private PrintWriter guestOut;
    private PrintWriter receptionistOut;

    public void start() throws Exception {
        serverSocket = new ServerSocket(5000);
        System.out.println("Server started on port 5000...");

        while (true) {
            guestSocket = serverSocket.accept();
            guestOut = new PrintWriter(guestSocket.getOutputStream(), true);
            System.out.println("Guest connected!");

            receptionistSocket = serverSocket.accept();
            receptionistOut = new PrintWriter(receptionistSocket.getOutputStream(), true);
            System.out.println("Receptionist connected!");

            guestOut.println("CONNECTED");
            receptionistOut.println("CONNECTED");

            new Thread(() -> listenFrom(guestSocket, receptionistOut, "Guest")).start();
            new Thread(() -> listenFrom(receptionistSocket, guestOut, "Receptionist")).start();
        }
    }

    private void listenFrom(Socket socket, PrintWriter forwardTo, String sender) {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println(sender + ": " + message);
                forwardTo.println(sender + ": " + message);
            }
        } catch (Exception e) {
            System.out.println(sender + " disconnected.");
        }
    }

    public static void startServer() {
        new Thread(() -> {
            try {
                new ChatServer().start();
            } catch (Exception e) {
                System.out.println("Server error: " + e.getMessage());
            }
        }).start();
    }
}
