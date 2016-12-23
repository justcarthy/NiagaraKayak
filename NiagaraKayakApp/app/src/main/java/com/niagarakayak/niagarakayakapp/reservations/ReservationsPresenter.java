package com.niagarakayak.niagarakayakapp.reservations;

import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public class ReservationsPresenter implements ReservationsContract.Presenter {

    private ReservationsContract.View mReservationsView;

    public ReservationsPresenter(ReservationsContract.View mReservationsView) {
        this.mReservationsView = mReservationsView;
        mReservationsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadReservations();
    }

    @Override
    public void loadReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation("1", "adamsandler@adamsandler.com", "2015-09-20", "8 PM", 12, 1, 0, "Charles Daley Park", 1, 0));
        mReservationsView.showReservations(reservations);
    }
}
