package com.niagarakayak.niagarakayakapp.home;

import android.graphics.drawable.Drawable;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;

import java.util.Date;

public interface HomeContract {

    interface View extends BaseView<Presenter> {
        void setTweetLabel(String label);
        void setTweetHandle(String handle);
        void setTweetImage(Drawable image);
        void setTweetDescription(String description);
        void setTweetDate(String stringDate);
        void showMapsCardWithCoords(final LatLng coords);
        void showMapsLabel();
        void showRetryButton(boolean show);
        void setMapsLabel(String label);
    }

    interface Presenter extends BasePresenter {
        void checkInternet();
        void loadTweetCard();
        void loadMapCard(Date tweetDate, LatLng coords);
        boolean hasTwitterLoaded();
    }

}
