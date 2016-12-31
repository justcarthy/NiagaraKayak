package com.niagarakayak.niagarakayakapp.service.database;


import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public interface DataService {

    void addReservationLocal(InsertCallback callback, Reservation reservation);
    void readLocalReservations(ReadCallback callback);

    interface InsertCallback {
        void onFailure(Exception e);
        void onSuccess();
    }

    interface ReadCallback {
        void onFailure(Exception e);
        void onSuccess(ArrayList<Reservation> reservations);
    }

}
