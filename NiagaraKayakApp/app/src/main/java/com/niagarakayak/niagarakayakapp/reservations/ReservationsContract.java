package com.niagarakayak.niagarakayakapp.reservations;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;
import com.niagarakayak.niagarakayakapp.model.Reservation;

import java.util.ArrayList;

public interface ReservationsContract {

    interface Presenter extends BasePresenter {
        void loadReservationsFromServerAndLocal();
    }

    interface View extends BaseView<Presenter> {
        void showReservations(ArrayList<Reservation> reservations);
        void setRefreshing(boolean refresh);
    }

}
