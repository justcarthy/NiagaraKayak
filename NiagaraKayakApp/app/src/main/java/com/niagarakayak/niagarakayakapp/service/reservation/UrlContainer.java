package com.niagarakayak.niagarakayakapp.service.reservation;

/**
 * Created by bala on 21/12/16.
 */

public class UrlContainer {
    private static final String reservationUrl = "http://www.niagarakayak/kayakAPI/reservation.php/?api=%s&customer=%s";

    public static String getReservationUrl(){
        return reservationUrl;
    }
}
