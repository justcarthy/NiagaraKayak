package com.niagarakayak.niagarakayakapp.reservations;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.View;
import com.niagarakayak.niagarakayakapp.model.Reservation;
import com.niagarakayak.niagarakayakapp.service.reservation.ReservationAPIService;
import com.niagarakayak.niagarakayakapp.service.reservation.ReservationService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor;

import java.util.ArrayList;

public class ReservationsPresenter implements ReservationsContract.Presenter {

    private final String email;
    private ReservationsContract.View mReservationsView;
    private ReservationAPIService reservationAPIService;

    public ReservationsPresenter(String email, ReservationAPIService reservationAPIService, ReservationsContract.View mReservationsView) {
        this.mReservationsView = mReservationsView;
        this.reservationAPIService = reservationAPIService;
        this.email = email;
        mReservationsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadReservations();
    }

    @Override
    public void loadReservations() {
        reservationAPIService.getAllReservations(new ReservationService.ReservationCallback() {
            @Override
            public void onFailure(Exception e) {
                e.printStackTrace();
                ActivityUtils.showSnackbarWithMessage(((Fragment) mReservationsView).getView(), "Couldn't load reservations", Snackbar.LENGTH_LONG, SnackbarColor.ERROR_COLOR);
            }

            @Override
            public void onSuccess(ArrayList<Reservation> reservations) {
                if (reservations.size() > 0) {
                    mReservationsView.showReservations(reservations);
                }
            }
        }, email);
    }
}
