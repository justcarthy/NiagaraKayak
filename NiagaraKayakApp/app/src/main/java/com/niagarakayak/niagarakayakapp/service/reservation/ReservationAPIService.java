package com.niagarakayak.niagarakayakapp.service.reservation;

import android.os.AsyncTask;
import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public class ReservationAPIService implements ReservationService {
    @Override
    public void getAllReservations(ReservationCallback callback) {
        new getAllReservationsTask().execute(callback);
    }


    private class getAllReservationsTask extends AsyncTask<ReservationCallback, Void, ArrayList<Reservation>> {
        private Exception exception;
        private ReservationCallback callback;

        @Override
        protected ArrayList<Reservation> doInBackground(ReservationCallback... params) {
            this.callback = params[0];

            // Request goes here

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Reservation> reservations) {
            if (exception != null) {
                callback.onFailure();
            } else {
                callback.onSuccess(reservations);
            }
        }
    }
}
