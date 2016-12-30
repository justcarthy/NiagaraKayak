package com.niagarakayak.niagarakayakapp.reservations;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.niagarakayak.niagarakayakapp.model.Reservation;
import com.niagarakayak.niagarakayakapp.service.database.DataService;
import com.niagarakayak.niagarakayakapp.service.database.ReservationLocalDataService;
import com.niagarakayak.niagarakayakapp.service.reservation.ReservationAPIService;
import com.niagarakayak.niagarakayakapp.service.reservation.ReservationService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor;

import java.util.ArrayList;

public class ReservationsPresenter implements ReservationsContract.Presenter {

    private final String email;
    private ReservationsContract.View mReservationsView;
    private ReservationAPIService reservationAPIService;
    private ReservationLocalDataService reservationLocalDataService;

    public ReservationsPresenter(String email, ReservationLocalDataService reservationLocalDataService, ReservationAPIService reservationAPIService, ReservationsContract.View mReservationsView) {
        this.mReservationsView = mReservationsView;
        this.reservationLocalDataService = reservationLocalDataService;
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
        getRemoteReservations(new RemoteReservationsCallback() {
            @Override
            public void onSuccess(final ArrayList<Reservation> remoteReservations) {
                ArrayList<Reservation> toUpdate = new ArrayList<>();

                reservationLocalDataService.readLocalReservations(new DataService.ReadCallback() {
                    @Override
                    public void onFailure(Exception e) {}

                    @Override
                    public void onSuccess(ArrayList<Reservation> localReservations) {
                        for (Reservation localReservation : localReservations) {
                            for (Reservation remoteReservation : remoteReservations) {
                                if (localReservation.getReservationID().equals(remoteReservation.getReservationID())) {
                                    reservationLocalDataService.confirmReservationLocal(localReservation.getReservationID());
                                }
                            }
                        }

                        displayReservationsFromDatabase();
                    }
                });
            }
        });

    }


    private void getRemoteReservations(final RemoteReservationsCallback callback) {
        reservationAPIService.getAllReservations(new ReservationService.ReservationCallback() {
            @Override
            public void onFailure(Exception e) {
                ActivityUtils.showSnackbarWithMessage(((Fragment) mReservationsView).getView(), "Couldn't fetch reservations from server", Snackbar.LENGTH_LONG, SnackbarColor.ERROR_COLOR);
            }

            @Override
            public void onSuccess(ArrayList<Reservation> reservations) {
                callback.onSuccess(reservations);
            }
        }, email);
    }

    private void displayReservationsFromDatabase() {
        reservationLocalDataService.readLocalReservations(new DataService.ReadCallback() {
            @Override
            public void onFailure(Exception e) {
                ActivityUtils.showSnackbarWithMessage(((Fragment) mReservationsView).getView(), "Couldn't load reservations from phone", Snackbar.LENGTH_LONG, SnackbarColor.ERROR_COLOR);
            }

            @Override
            public void onSuccess(ArrayList<Reservation> reservations) {
                ActivityUtils.showSnackbarWithMessage(((Fragment) mReservationsView).getView(), "Successfully loaded reservations", Snackbar.LENGTH_LONG, SnackbarColor.SUCCESS_COLOR);
                mReservationsView.showReservations(reservations);
            }
        });
    }


    private interface RemoteReservationsCallback {
        public void onSuccess(ArrayList<Reservation> remoteReservations);
    }

}
