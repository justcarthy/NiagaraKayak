package com.niagarakayak.niagarakayakapp.service.reservation;

/**
 * Created by bala on 21/12/16.
 */

public class UrlContainer {

    private static final String reservationUrl = "http://www.niagarakayak.com/kayakAPI/" +
            "GetReservations.php/?api=%s&customer=%s";
    private static final String makeReservationURL = "http://www.niagarakayak.com/kayakAPI/MakeReservation.php/" +
            "?api=%s&ReservationID=%s&Email=%s&Date=%s&Time=%s&Hours=%d&Single=%d&Tandem=%d" +
            "&location=%s&adults=%d&children=%d";

    private static final String postCustomerURL = "http://www.niagarakayak.com/kayakAPI/customer.php/?type=POST";
    private static final String PostCustomerURLparam = "api=%s&Email=%s&Name=%s&Phone=%s";

    private static final String updateCustomerURL = "http://www.niagarakayak.com/kayakAPI/customer.php/?type=UPDATE";
    private static final String updateCustomerURLparam = "api=%s&Email=%s&NameUP=%s&PhoneUP=%s";

    /**
     *
     * @return URL to update customer record
     */
    public static String getUpdateCustomerURL() {return updateCustomerURL;}

    /**
     *
     * @return parameter string to be passed for updating record
     */
    public static String getUpdateCustomerURLparam() {return updateCustomerURLparam;}

    /**
     *
     * @return  URL to fetch reservations for a customer
     */
    public static String getReservationUrl(){
        return reservationUrl;
    }


    /**
     *
     * @return URL to call script to send email to Buisness owner
     */
    public static String getmakeReservationURL(){
        return makeReservationURL;
    }

    /**
     *
     * @return parameter to be supplied to post customers to the table
     */
    public static String getPostCustomerURLparam() {return PostCustomerURLparam; }

    /**
     *
     * @return url for posting customer to the table
     */
    public static String getPostCustomerURL() {return postCustomerURL; }
}
