package com.example.androiduidesignlab;

public class Reservation {
    private long id;
    private String reservationDetails;

    public Reservation(String reservationDetails) {
        this.reservationDetails = reservationDetails;
    }

    public Reservation(long id, String reservationDetails) {
        this.id = id;
        this.reservationDetails = reservationDetails;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getReservationDetails() {
        return reservationDetails;
    }

    public void setReservationDetails(String reservationDetails) {
        this.reservationDetails = reservationDetails;
    }
}
