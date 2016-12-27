package com.niagarakayak.niagarakayakapp.add_reservations;

import android.os.Parcelable;
import android.view.View;
import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;

public interface AddReservationsContract {

    interface View extends BaseView<Presenter>, android.view.View.OnClickListener {
        void showNextPage();
        void showToastWithMessage(String message);
        String getDateText();
        String getTimeText();
        String getHoursText();
        void onClick(android.view.View v);
    }

    interface Presenter extends BasePresenter {
        void handleOnContinue(int page);

        @Override
        void start();
    }

}
