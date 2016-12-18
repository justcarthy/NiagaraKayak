package com.niagarakayak.niagarakayakapp.home;

import android.graphics.drawable.Drawable;
import com.niagarakayak.niagarakayakapp.BasePresenter;

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        void setTweetLabel(String label);
        void setTweetImage(Drawable image);
        void setTweetDescription(String description);
        void setMapFragment();
        void showErrorToast(String message);
    }

    interface Presenter extends BasePresenter {
        void checkInternet();
        void loadTweetCard();
        void loadMapCard();
    }

}
