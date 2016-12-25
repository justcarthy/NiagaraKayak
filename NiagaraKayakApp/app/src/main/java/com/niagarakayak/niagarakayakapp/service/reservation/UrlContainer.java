package com.niagarakayak.niagarakayakapp.service.reservation;

/**
 * Created by bala on 21/12/16.
 */

public class UrlContainer {

    private static final String allReservationsUrl = "www.niagarakayak.com/kayakAPI/reservation.php/?api=%s&ALL=true";

    private static final String reservationUrl = "http://www.niagarakayak.com/kayakAPI/" +
            "reservation.php/?api=%s&customer=%s";
    private static final String postURL = "http://www.niagarakayak.com/kayakAPI/reservation.php/" +
            "?api=%s&type=POST&Email=%s&Date=%s&Time=%s&Hours=%d&Single=%d&Tandem=%d" +
            "&location=%s&adults=%d&children=%d";
    //apikey , email , date , time , hours , single , tandem;


    public static String getAllReservationsUrl() {
        return allReservationsUrl;
    }
    public static String getReservationUrl(){
        return reservationUrl;
    }
    public static String getPostURL(){
        return postURL;
    }
}
