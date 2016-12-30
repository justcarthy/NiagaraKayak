package com.niagarakayak.niagarakayakapp.model;

/**
 * This class represents a reservation.
 */

public class Reservation {

    private String email;
    private String reservationID;
    private String date;
    private String time;
    private String location;
    private int adults;
    private int children;
    private int hours;
    private int singleKayaks;
    private int tandemKayaks;
    private boolean confirmed;

    public Reservation() {}

    public Reservation(String reservationID,String email,String date,String time,int hours,int singleKayaks,int tandemKayaks,
                       String location, int adults, int children, boolean confirmed) {
        this.reservationID = reservationID;
        this.date = date;
        this.email = email;
        this.time = time;
        this.hours = hours;
        this.singleKayaks = singleKayaks;
        this.tandemKayaks = tandemKayaks;
        this.adults = adults;
        this.children = children;
        this.location = location;
        this.confirmed = confirmed;
    }

    public boolean isConfirmed() { return confirmed; }

    public void setConfirmed(boolean confirmed) { this.confirmed = confirmed; }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getReservationHours() {
        return hours;
    }

    public void setReservationHours(int reservationHours) {
        this.hours = reservationHours;
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

    public String getReservationTime() {
        return time;
    }

    public void setReservationTime(String reservationTime) {
        this.time = reservationTime;
    }

    public void setReservationID(String reservationID){
        this.reservationID = reservationID;
    }

    public String getReservationID(){
        return reservationID;
    }

    public void setLocation(String location){ this.location = location; }

    public String getLocation() { return location ;}

    public void setAdults(int adults){ this.adults = adults; }

    public int getAdults(){ return adults; }

    public int getChildren() { return children;}

    public void setChildren(int children) { this.children = children;    }

    public int getHours() { return hours;}

    public void setHours(int hours) { this.hours = hours;    }

    public String getTime() { return time;    }

    public void setTime(String time) { this.time = time;    }
}
