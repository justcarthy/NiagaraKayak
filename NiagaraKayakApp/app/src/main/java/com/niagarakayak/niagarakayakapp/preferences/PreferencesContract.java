package com.niagarakayak.niagarakayakapp.preferences;


import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;

/**
 * Created by justin on 21/12/16.
 */

public interface PreferencesContract {

    interface Presenter extends BasePresenter {
        void loadSettings();
        void saveSettings();
    }

    interface View extends BaseView<Presenter> {
        void setName();
        void setEmail();
        void setPhone();
        void getNameText();
        void getEmailText();
        void getPhoneText();
    }


}
