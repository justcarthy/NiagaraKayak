package com.niagarakayak.niagarakayakapp.service.reservation;

import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public interface ReservationService {

    static final String APIKey ="F6FCED2D6429CEB07C4B9CDAAA1425435935EA537434A5F753FF40F78F446A53";

    /**
     *
     * @param callback callback to use
     * @param Email email address of client to fetch reservations for
     */
    void getAllReservations(ReservationCallback callback,String Email);
    void postReservation(PostCallback callback,Reservation reservation);

    interface ReservationCallback {
        void onFailure();
        void onSuccess(ArrayList<Reservation> reservations);
    }

    interface PostCallback{
        void onFailure(int responseCode,String message);
        void onSuccess();
    }

}
