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


    private static final String checkCustomerEmailURL = "http://www.niagarakayak.com/kayakAPI/customer.php/?type=CHECKFREE";
    private static final String checkCustomerEmailURLparam = "api=%s&Email=%s";

    private static final String  CustomerInfoURL= "http://www.niagarakayak.com/kayakAPI/customer.php/?type=GET";
    private static final String  CustomerInfoURLparam = "api=%s&Email=%s";


    private static final String sendVerificationURL = "http://www.niagarakayak.com/kayakAPI/sverification.php/";
    private static final String sendVerificationURLparam = "api=%s&Email=%s";


    private static final String VerificationURL = "http://www.niagarakayak.com/kayakAPI/verify.php/";
    private static final String VerificationURLparam = "api=%s&Email=%s&Code=%s";


    /**
     *
     * @return URL for script to get customer information
     */
    public static String getCustomerInfoURL() {return CustomerInfoURL;}

    /**
     *
     * @return paramters to pass to url to get customer information
     */
    public static String getCustomerInfoURLparam() {return CustomerInfoURLparam;}



    /**
     *
     * @return URL to check if email is available for customer to use for registration
     */
    public static String getCheckCustomerEmailURL() {
        return checkCustomerEmailURL;
    }

    /**
     *
     * @return parameter to supply for checking customer Email URL
     */
    public static String getCheckCustomerEmailURLparam() {
        return checkCustomerEmailURLparam;
    }

    /**
     * @return URL to send verification code to customer
     */
    public static String getSendVerificationURL() {
        return sendVerificationURL;
    }

    /**
     * @return parameters to be supplied for sending verification code URL
     */
    public static String getSendVerificationURLparam() {
        return sendVerificationURLparam;
    }

    /**
     * @return URL to verify the code provided by the customer
     */
    public static String getVerificationURL() {
        return VerificationURL;
    }

    /**
     * @return param to be supplied with URL used to do verification
     */
    public static String getVerificationURLparam() {
        return VerificationURLparam;
    }

    /**
     * @return URL to update customer record
     */
    public static String getUpdateCustomerURL() {
        return updateCustomerURL;
    }

    /**
     * @return parameter string to be passed for updating record
     */
    public static String getUpdateCustomerURLparam() {
        return updateCustomerURLparam;
    }

    /**
     * @return URL to fetch reservations for a customer
     */
    public static String getReservationUrl() {
        return reservationUrl;
    }


    /**
     * @return URL to call script to send email to Buisness owner
     */
    public static String getmakeReservationURL() {
        return makeReservationURL;
    }

    /**
     * @return parameter to be supplied to post customers to the table
     */
    public static String getPostCustomerURLparam() {
        return PostCustomerURLparam;
    }

    /**
     * @return url for posting customer to the table
     */
    public static String getPostCustomerURL() {
        return postCustomerURL;
    }
}
