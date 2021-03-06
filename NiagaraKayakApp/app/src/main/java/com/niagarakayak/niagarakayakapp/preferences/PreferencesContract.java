package com.niagarakayak.niagarakayakapp.preferences;


import android.view.View;

import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;


public interface PreferencesContract {

    interface Presenter extends BasePresenter, android.view.View.OnClickListener {
        void loadSettings();
        boolean validInput();
        void saveSettings();

        @Override
        void onClick(android.view.View v);
    }

    interface View extends BaseView<Presenter> {
        void setName(String name);
        void setEmail(String email);
        void setPhone(String phone);
        String getNameText();
        String getEmailText();
        String getPhoneText();
        void goHome();
        void showToast(String message);
    }


}
