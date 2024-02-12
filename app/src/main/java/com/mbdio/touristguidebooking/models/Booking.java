package com.mbdio.touristguidebooking.models;


import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.IdentityHashMap;

public class Booking {
    private String id;
    private String date;
    private BookingStatus status;
    private String touristId;
    @Exclude
    private Tourist tourist;

    public Booking() {
    }

    public Booking(String id, String date, BookingStatus status, String touristID, Tourist tourist) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.touristId = touristId;
        this.setTourist( tourist);
    }

    public Booking(String date, BookingStatus status, String touristId) {
        this.date = date;
        this.status = status;
        this.touristId = touristId;
    }

    public Booking(Booking book) {
        this.id = book.id;
        this.date = book.date;
        this.status = book.status;
        this.touristId = book.touristId;
        this.tourist = book.tourist;
    }


    public Tourist getTourist() {
        return tourist;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public String getTouristId() {
        return touristId;
    }

    public void setTouristId(String touristId) {
        this.touristId = touristId;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
