package com.niagarakayak.niagarakayakapp.reservations;

import android.graphics.drawable.Drawable;
import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;
import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public interface ReservationsContract {

    interface Presenter extends BasePresenter {
        void loadReservations();
    }

    interface View extends BaseView<Presenter> {
        void showReservations(ArrayList<Reservation> reservations);
    }

}
