package com.niagarakayak.niagarakayakapp.Utility;

import android.content.Context;
import com.niagarakayak.niagarakayakapp.Model.Reservation;
import com.niagarakayak.niagarakayakapp.R;

/**
 * This class is for generating URLs to send GET/POST requests to the WordPress API.
 */

public class ApiUrlHelper {

    // The base URL
    private static final String BASE_URL = "http://niagarakayak.com/api/";

    //The API "nonce" for sending post requests
    private String API_NONCE;

    /**
     * The constructor, needs context to get API key.
     * @param context - Context object from Activity / Fragment.
     */
    public ApiUrlHelper(Context context) {
        // The API nonce is stored in the res/strings.xml.
        API_NONCE = context.getString(R.string.API_NONCE);
    }

    /**
     * Get base url
     * @return  Base URL
     */
    public static String getApiBaseUrl() {
        return BASE_URL;
    }

    /**
     * GET method URL for getting all reservations.
     * @return  url for GET method
     */
    public static String getReservationsUrl() {
        return BASE_URL + "get_posts";
    }

    /**
     * GET method URL for getting a specific reservation
     * @param id    ID for URL
     * @return  url for getting a specific reservation
     */
    public static String getReservationUrl(int id)  {
        return BASE_URL + "get_post?id=" + id;
    }

    /**
     * POST method url for creating a new reservation within the WP database
     * @param reservation - Reservation object
     * @return  url for posting a reservation
     */
    public String postReservationUrl(Reservation reservation) {
        String url = BASE_URL +
                "create_post/?" +
                "nonce=" + API_NONCE + "&" +
                "title=" + "name$" + reservation.getName() +
                            "date$" + reservation.getDate() +
                            "email$" + reservation.getEmail() +
                            "phone$" + reservation.getPhone() +
                            "time$" + reservation.getReservationTime() +
                            "hour$" + reservation.getReservationHours() +
                            "single_kayak$" + reservation.getSingleKayaks() +
                            "tandem_kayak$" + reservation.getTandemKayaks() +
                            "num_adults$" + reservation.getNumAdults() +
                            "num_children$" + reservation.getNumChildren() +
                "&status=publish";

        // Replace spaces with specific space character code used frequently in URLs
        url.replace(" ", "%20");
        return url;
    }

}
