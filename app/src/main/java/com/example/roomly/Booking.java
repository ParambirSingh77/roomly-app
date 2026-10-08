package com.example.roomly;

// One booking or booking request.
public class Booking {

    // The three statuses an employee can see
    public static final String CONFIRMED = "confirmed";
    public static final String PENDING = "pending";
    public static final String CANCEL_REQUESTED = "cancel_requested";

    private int id;
    private String room;
    private String date;
    private String time;
    private String user;
    private String status;

    public Booking(int id, String room, String date, String time, String user, String status) {
        this.id = id;
        this.room = room;
        this.date = date;
        this.time = time;
        this.user = user;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getRoom() {
        return room;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getUser() {
        return user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
