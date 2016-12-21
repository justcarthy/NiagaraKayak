package com.niagarakayak.niagarakayakapp.preferences;

import android.graphics.drawable.Drawable;

import com.google.android.gms.maps.model.LatLng;
import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;
import com.niagarakayak.niagarakayakapp.home.HomeContract;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;

import java.util.Date;

/**
 * Created by justin on 21/12/16.
 */

public interface PreferencesContract {

    interface Presenter extends BasePresenter {
        void loadSettings();
    }

    interface View extends BaseView<HomeContract.Presenter> {
        void setName();
        void setEmail();
        void setPhone();
        void getNameText();
        void getEmailText();
        void getPhoneText();
    }


}
