package com.niagarakayak.niagarakayakapp.reservations;

public class ReservationsPresenter implements ReservationsContract.Presenter {

    private ReservationsContract.View mReservationsView;

    public ReservationsPresenter(ReservationsContract.View mReservationsView) {
        this.mReservationsView = mReservationsView;
    }

    @Override
    public void start() {
        loadReservations();
    }

    @Override
    public void loadReservations() {
    }
}
