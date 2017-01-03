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

    public ReservationsPresenter(String email, ReservationLocalDataService reservationLocalDataService, ReservationAPIService reservationAPIService,
                                 ReservationsContract.View mReservationsView) {
        this.mReservationsView = mReservationsView;
        this.reservationLocalDataService = reservationLocalDataService;
        this.reservationAPIService = reservationAPIService;
        this.email = email;
        mReservationsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadReservationsFromServerAndLocal();
    }

    @Override
    public void loadReservationsFromServerAndLocal() {
        getRemoteReservations(new LoadReservationsCallback() {
            @Override
            public void onSuccess(final ArrayList<Reservation> remoteReservations) {
                reservationLocalDataService.readLocalReservations(new DataService.ReadCallback() {
                    @Override
                    public void onFailure(Exception e) {
                        ActivityUtils.showSnackbarWithMessage(((Fragment) mReservationsView).getView(), "Couldn't fetch reservations from the phone", Snackbar.LENGTH_LONG, SnackbarColor.ERROR_COLOR);
                    }

                    @Override
                    public void onSuccess(ArrayList<Reservation> localReservations) {
                        syncDbWithRemote(remoteReservations, localReservations);
                    }
                });
            }
        });

    }

    private void getRemoteReservations(final LoadReservationsCallback callback) {
        reservationAPIService.getAllReservations(new ReservationService.ReservationCallback() {
            @Override
            public void onFailure(Exception e) {
                ActivityUtils.showSnackbarWithMessage(((Fragment) mReservationsView).getView(), "Couldn't fetch updates, showing reservations on phone", Snackbar.LENGTH_LONG, SnackbarColor.ERROR_COLOR);
                loadReservationsFromDatabase();
                mReservationsView.setRefreshing(false);
            }

            @Override
            public void onSuccess(ArrayList<Reservation> reservations) {
                callback.onSuccess(reservations);
            }
        }, email);
    }

    private void syncDbWithRemote(ArrayList<Reservation> remoteReservations, ArrayList<Reservation> localReservations) {
        for (Reservation remoteReservation : remoteReservations) {
            // If the local database doesn't have the remote reservation, add it to the database.
            if (!localReservations.contains(remoteReservation)) {
                reservationLocalDataService.addReservationLocal(new DataService.InsertCallback() {
                    @Override
                    public void onFailure(Exception e) {}

                    @Override
                    public void onSuccess() {
                    }
                }, remoteReservation);
            } else {
                // If it does contain it, it means it was confirmed.
                // So if it's not confirmed, confirm it.
                int reservationIndex = localReservations.indexOf(remoteReservation);
                Reservation localReservation = localReservations.get(reservationIndex);
                if (!localReservation.isConfirmed()) {
                    reservationLocalDataService.confirmReservationLocal(localReservation.getReservationID());
                }
            }
        }

        // Now that we are done syncing, show them to the user.
        loadReservationsFromDatabase();
    }

    private void loadReservationsFromDatabase() {
        reservationLocalDataService.readLocalReservations(new DataService.ReadCallback() {
            @Override
            public void onFailure(Exception e) {
                ActivityUtils.showSnackbarWithMessage(((Fragment) mReservationsView).getView(), "Couldn't load reservations from phone", Snackbar.LENGTH_LONG, SnackbarColor.ERROR_COLOR);
                mReservationsView.setRefreshing(false);
            }

            @Override
            public void onSuccess(ArrayList<Reservation> reservationsFromDb) {
                showReservations(reservationsFromDb);
            }
        });
    }

    private void showReservations(ArrayList<Reservation> reservations) {
        mReservationsView.showReservations(reservations);
        mReservationsView.setRefreshing(false);
    }


    private interface LoadReservationsCallback {
        void onSuccess(ArrayList<Reservation> remoteReservations);
    }

}