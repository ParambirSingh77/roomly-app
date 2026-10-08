package com.example.roomly;

import java.util.ArrayList;

// Keeps the sample data in memory so the screens have something to show.
// This is temporary. A later stage replaces it with the API and a local database.
public class BookingStore {

    public static final String[] ROOMS = {
            "Meeting Room 1", "Meeting Room 2", "Meeting Room 3", "Meeting Room 4",
            "Meeting Room 5", "Seminar Room 1", "Seminar Room 2"
    };

    public static final String[] DATES = {"Fri 9 Oct", "Mon 12 Oct", "Tue 13 Oct"};

    public static final String[] TIMES = {
            "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM",
            "1:00 PM", "2:00 PM", "3:00 PM", "4:00 PM"
    };

    private static final ArrayList<Booking> bookings = new ArrayList<>();
    private static int nextId = 4;
    private static String currentUser = "";

    // Sample bookings
    static {
        bookings.add(new Booking(1, "Meeting Room 2", "Fri 9 Oct", "10:00 AM", "Sam", Booking.CONFIRMED));
        bookings.add(new Booking(2, "Seminar Room 1", "Fri 9 Oct", "11:00 AM", "Priya", Booking.CONFIRMED));
        bookings.add(new Booking(3, "Meeting Room 4", "Mon 12 Oct", "2:00 PM", "Alex", Booking.CONFIRMED));
    }

    public static String getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String user) {
        currentUser = user;
    }

    public static void logout() {
        currentUser = "";
    }

    // True if the room already has a booking at that date and time
    public static boolean isTaken(String room, String date, String time) {
        for (Booking b : bookings) {
            if (b.getRoom().equals(room) && b.getDate().equals(date) && b.getTime().equals(time)) {
                return true;
            }
        }
        return false;
    }

    // Rooms that are free at that date and time
    public static ArrayList<String> getFreeRooms(String date, String time) {
        ArrayList<String> free = new ArrayList<>();
        for (String room : ROOMS) {
            if (!isTaken(room, date, time)) {
                free.add(room);
            }
        }
        return free;
    }

    // An employee request starts as pending until a manager approves it
    public static void addRequest(String room, String date, String time) {
        bookings.add(new Booking(nextId, room, date, time, currentUser, Booking.PENDING));
        nextId++;
    }

    // Bookings that belong to the logged in user
    public static ArrayList<Booking> getMyBookings() {
        ArrayList<Booking> mine = new ArrayList<>();
        for (Booking b : bookings) {
            if (b.getUser().equalsIgnoreCase(currentUser)) {
                mine.add(b);
            }
        }
        return mine;
    }

    // A pending request is removed straight away.
    // A confirmed booking needs a manager to approve the cancellation.
    public static void cancel(Booking booking) {
        if (booking.getStatus().equals(Booking.CONFIRMED)) {
            booking.setStatus(Booking.CANCEL_REQUESTED);
        } else {
            bookings.remove(booking);
        }
    }
}
