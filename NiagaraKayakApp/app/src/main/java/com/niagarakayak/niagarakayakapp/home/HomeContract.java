package com.niagarakayak.niagarakayakapp.home;

import android.graphics.drawable.Drawable;
import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        void setTweetLabel(String label);
        void setTweetHandle(String handle);
        void setTweetImage(Drawable image);
        void setTweetDescription(String description);
        void showMapsCard();
        void showMapsLabel();
        void showSnackbarWithMessage(String message, int length, SnackbarUtils.SnackbarColor color);
    }

    interface Presenter extends BasePresenter {
        void checkInternet();
        void loadTweetCard();
        void loadMapCard();
    }

}
