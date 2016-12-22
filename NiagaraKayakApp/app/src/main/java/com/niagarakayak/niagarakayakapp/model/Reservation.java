package com.niagarakayak.niagarakayakapp.model;

/**
 * This class represents a reservation.
 */

public class Reservation {

    private String email;
    private String reservationID;
    private String date;
    private String time;
    private int hours;
    private int singleKayaks;
    private int tandemKayaks;

    public Reservation() {}

    public Reservation(String reservationID,String email,String date,String time,int hours,int singleKayaks,int tandemKayaks) {
        this.reservationID = reservationID;
        this.date = date;
        this.time = time;
        this.hours = hours;
        this.singleKayaks = singleKayaks;
        this.tandemKayaks = tandemKayaks;
    }

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
        return this.reservationID;
    }
}
