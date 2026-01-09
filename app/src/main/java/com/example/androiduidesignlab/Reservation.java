package com.example.androiduidesignlab;

public class Reservation {
    private long id;
    private String username;
    private String date;
    private String time;
    private int guests;
    private String reservationDetails;

    public Reservation(String username, String date, String time, int guests, String reservationDetails) {
        this.username = username;
        this.date = date;
        this.time = time;
        this.guests = guests;
        this.reservationDetails = reservationDetails;
    }

    public Reservation(long id, String username, String date, String time, int guests, String reservationDetails) {
        this.id = id;
        this.username = username;
        this.date = date;
        this.time = time;
        this.guests = guests;
        this.reservationDetails = reservationDetails;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getTime() { return time; }
    public void setTime(String time) { this.time = time; }

    public int getGuests() { return guests; }
    public void setGuests(int guests) { this.guests = guests; }

    public String getReservationDetails() { return reservationDetails; }
    public void setReservationDetails(String reservationDetails) { this.reservationDetails = reservationDetails; }

    public String getFullDescription() {
        String details = (reservationDetails == null || reservationDetails.isEmpty()) ? "" : ": " + reservationDetails;
        return String.format("Reservation for %d on %s at %s%s", guests, date, time, details);
    }
}
