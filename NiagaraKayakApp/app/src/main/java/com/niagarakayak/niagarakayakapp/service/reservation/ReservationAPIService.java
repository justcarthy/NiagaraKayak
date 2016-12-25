package com.niagarakayak.niagarakayakapp.service.reservation;

import android.os.AsyncTask;

import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ReservationAPIService implements ReservationService {
    private final String APIKey;
    private String Email;  //email for getAllReservations

    public ReservationAPIService(String APIKey) {
        this.APIKey = APIKey;
    }

    @Override
    public void getAllReservations(ReservationCallback callback, String Email) {
        this.Email = Email;
        new getAllReservationsTask().execute(callback);
    }

    @Override
    public String postReservationURL(Reservation reservation){
        String postURL = UrlContainer.getPostURL();
        //apikey , email , date , time , hours , single , tandem;
        String email = reservation.getEmail();
        String date = reservation.getDate();
        String time = reservation.getReservationTime();
        int hours = reservation.getReservationHours();
        int single = reservation.getSingleKayaks();
        int tandem = reservation.getTandemKayaks();
        int adults = reservation.getAdults();
        int children = reservation.getChildren();
        String location = reservation.getLocation();
        postURL = String.format(postURL, APIKey, email, date, time, hours, single,
                tandem, location, adults, children);
        return postURL;
    }

    /**
     *
     * @return Reservation for the given Email,null if unauthorized
     */
    private ArrayList<Reservation> fetchReservations() throws Exception {
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
                    return ReservationParser.getReservations(json);
                case HttpURLConnection.HTTP_UNAUTHORIZED: //call failure
                    return null;
            }
        return null;
    }

    /**
     * method gets the json object and closes all resources
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


    private class getAllReservationsTask extends AsyncTask<ReservationCallback, Void, ArrayList<Reservation>> {
        private ReservationCallback callback;
        private Exception exception;

        @Override
        protected ArrayList<Reservation> doInBackground(ReservationCallback... params) {
            this.callback = params[0];
            try {
                return fetchReservations();
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


}
