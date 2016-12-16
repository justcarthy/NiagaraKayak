package com.niagarakayak.niagarakayakapp.Model;

/**
 * Object which represents a reservation.
 */

public class Reservation {
    private String name;
    // Store date as strings for simplicity
    private String date;
    private String email;
    private String phone;
    private String reservationTime;
    private int reservationHours;
    private int singleKayaks;
    private int tandemKayaks;
    private String launchArea;
    private int numAdults;
    private int numChildren;


    public Reservation() {}

    public Reservation(String name, String date, String email, String phone, String reservationTime, int reservationHours,
                       int singleKayaks, int tandemKayaks, String launchArea, int numAdults, int numChildren) {
        this.name = name;
        this.date = date;
        this.email= email;
        this.phone = phone;
        this.reservationTime = reservationTime;
        this.reservationHours = reservationHours;
        this.singleKayaks = singleKayaks;
        this.tandemKayaks = tandemKayaks;
        this.launchArea = launchArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReservationHours() {
        return reservationHours;
    }

    public void setReservationHours(int reservationHours) {
        this.reservationHours = reservationHours;
    }

    public int getSingleKayaks() {
        return singleKayaks;
    }

    public void setSingleKayaks(int singleKayaks) {
        this.singleKayaks = singleKayaks;
    }

    public int getTandemKayaks() {
        return tandemKayaks;
    }

    public void setTandemKayaks(int tandemKayaks) {
        this.tandemKayaks = tandemKayaks;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLaunchArea() {
        return launchArea;
    }

    public void setLaunchArea(String launchArea) {
        this.launchArea = launchArea;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getNumAdults() {
        return numAdults;
    }

    public void setNumAdults(int numAdults) {
        this.numAdults = numAdults;
    }

    public int getNumChildren() {
        return numChildren;
    }

    public void setNumChildren(int numChildren) {
        this.numChildren = numChildren;
    }
}
