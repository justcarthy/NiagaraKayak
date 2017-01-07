package com.niagarakayak.niagarakayakapp.service.reservation;

import android.os.AsyncTask;

import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * This class contains methods and AsyncTasks to fetch reservations from the PHP server.
 */

public class ReservationAPIService implements ReservationService {
    private final String APIKey;
    private String Email;  //email for getAllReservations
    private String postURL;

    public ReservationAPIService(String APIKey) {
        this.APIKey = APIKey;
    }

    @Override
    public void getAllReservations(ReservationCallback callback, String Email) {
        this.Email = Email;
        new getReservationsTask().execute(callback);
    }

    @Override
    public void postReservation(PostCallback callback, Reservation reservation) {
        String postURL = UrlContainer.getmakeReservationURL();
        //apikey,reservationID,email,date,time,hours,single,tandem,location,adults,children;
        String reservationID = reservation.getReservationID();
        String email = reservation.getEmail();
        String date = reservation.getDate();
        String time = reservation.getReservationTime();
        int hours = reservation.getReservationHours();
        int single = reservation.getSingleKayaks();
        int tandem = reservation.getTandemKayaks();
        int adults = reservation.getAdults();
        int children = reservation.getChildren();
        String location = reservation.getLocation();
        postURL = String.format(postURL, APIKey, reservationID, email, date, time, hours, single,
                tandem, location, adults, children);
        postURL = postURL.replace(" ", "%20");
        this.postURL = postURL;
        new PostReservationTask().execute(callback);
    }

    /**
     * This method fetches reservations for a given email.
     * @return Reservation for the given Email,null if unauthorized
     */
    private ArrayList<Reservation> executeFetchReservations() throws Exception {
        String url = UrlContainer.getReservationUrl();
        url = String.format(url, APIKey, this.Email);
        HttpURLConnection httpConnection = (HttpURLConnection)new URL(url).openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setUseCaches(false);
        httpConnection.connect();

            int response_code = httpConnection.getResponseCode();
            switch(response_code) {
                case HttpURLConnection.HTTP_OK:
                    String json = getJSONString(httpConnection); //resources are closed
                    return JSONReservationParser.getReservations(json);
                case HttpURLConnection.HTTP_UNAUTHORIZED:
                case HttpURLConnection.HTTP_UNAVAILABLE:
                case HttpURLConnection.HTTP_BAD_GATEWAY:
                case HttpURLConnection.HTTP_BAD_REQUEST:
                case HttpURLConnection.HTTP_CLIENT_TIMEOUT:
                case HttpURLConnection.HTTP_RESET:
                case HttpURLConnection.HTTP_USE_PROXY:
                case HttpURLConnection.HTTP_CONFLICT:
                case HttpURLConnection.HTTP_BAD_METHOD:
                case HttpURLConnection.HTTP_REQ_TOO_LONG:
                case HttpURLConnection.HTTP_UNSUPPORTED_TYPE:
                case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
                case HttpURLConnection.HTTP_FORBIDDEN:
                case HttpURLConnection.HTTP_NOT_FOUND:
                case HttpURLConnection.HTTP_INTERNAL_ERROR:
                case HttpURLConnection.HTTP_GONE:
                case HttpURLConnection.HTTP_NO_CONTENT:
                case HttpURLConnection.HTTP_NOT_ACCEPTABLE:
                    throw new Exception();
            }
        return null;
    }

    /**
     * Method gets the json object and closes all resources.
     * @param httpConnection connection to extract the JSON object from
     * @return JSON object as string
     * @throws Exception throws IOException
     */
    private String getJSONString(HttpURLConnection httpConnection) throws IOException {
        StringBuffer buffer = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));

        String line = null;

        while ((line = in.readLine()) != null ) {
            buffer.append(line + "\r\n");
        }

        in.close();
        httpConnection.disconnect();
        return buffer.toString();
    }

    /**
     * This method posts a reservation using the postURL.
     * @throws Exception
     */
    private void executePostReservation() throws Exception {
        HttpURLConnection httpConnection = (HttpURLConnection) new URL(this.postURL).openConnection();
        httpConnection.setRequestMethod("GET");
        httpConnection.setUseCaches(false);
        httpConnection.connect();

        int response_code = httpConnection.getResponseCode();

        switch(response_code) {
            // on successful post return OK
            case HttpURLConnection.HTTP_OK:
                  break;
            //api key not valid
            case HttpURLConnection.HTTP_UNAUTHORIZED:
                throw new Exception("Not authorized");
            //invalid url
            case HttpURLConnection.HTTP_BAD_REQUEST:
                throw new Exception("Bad request");
            //server error
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                throw new Exception("Internal error");
        }

        httpConnection.disconnect(); //close resources
    }

    private class getReservationsTask extends AsyncTask<ReservationCallback, Void, ArrayList<Reservation>> {
        private ReservationCallback callback;
        private Exception exception;

        @Override
        protected ArrayList<Reservation> doInBackground(ReservationCallback... params) {
            this.callback = params[0];
            try {
                return executeFetchReservations();
            } catch (Exception e) {
                this.exception = e;
            }

            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Reservation> reservations) {
            if(exception != null || reservations == null) {
                callback.onFailure(exception);
            } else {
                callback.onSuccess(reservations);
            }
        }
    }

    private class PostReservationTask extends AsyncTask<PostCallback,Void,Void>{
        private PostCallback callback;
        private Exception exception;

        @Override
        protected Void doInBackground(PostCallback... params) {
            this.callback = params[0];
            try {
                 ReservationAPIService.this.executePostReservation();
            } catch (Exception e) {
                this.exception = e;
            }
           return null;
        }

        @Override
        protected void onPostExecute(Void a) {
            if(exception != null) {
                callback.onFailure(exception);
            } else {
                callback.onSuccess();
            }
        }
    }



}
