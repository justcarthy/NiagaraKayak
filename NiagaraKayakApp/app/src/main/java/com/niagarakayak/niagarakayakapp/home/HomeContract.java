package com.niagarakayak.niagarakayakapp.home;

import android.graphics.drawable.Drawable;
import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        void setTweetLabel(String label);
        void setTweetHandle(String handle);
        void setTweetImage(Drawable image);
        void setTweetDescription(String description);
        void setMapFragment();
        void showSnackbarWithMessage(String message, int length);
    }

    interface Presenter extends BasePresenter {
        void checkInternet();
        void loadTweetCard();
        void loadMapCard();
    }

}
