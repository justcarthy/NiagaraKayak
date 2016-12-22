package com.niagarakayak.niagarakayakapp.service.reservation;

import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public interface ReservationService {

    void getAllReservations(ReservationCallback callback);

    interface ReservationCallback {
        void onFailure();
        void onSuccess(ArrayList<Reservation> reservations);
    }

}
