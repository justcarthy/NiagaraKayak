package com.niagarakayak.niagarakayakapp.service.reservation;

import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public interface ReservationService {

    /**
     *
     * @param callback callback to use
     * @param Email email address of client to fetch reservations for
     */
    void getAllReservations(ReservationCallback callback,String Email);
    String postReservationURL(Reservation reservation);

    interface ReservationCallback {
        void onFailure(Exception e);
        void onSuccess(ArrayList<Reservation> reservations);
    }

    interface PostCallback{
        void onFailure(int responseCode, String message);
        void onSuccess();
    }

}
