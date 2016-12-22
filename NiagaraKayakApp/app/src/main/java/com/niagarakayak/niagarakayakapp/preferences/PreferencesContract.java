package com.niagarakayak.niagarakayakapp.preferences;


import android.view.View;

import com.niagarakayak.niagarakayakapp.BasePresenter;
import com.niagarakayak.niagarakayakapp.BaseView;

/**
 * Created by justin on 21/12/16.
 */

public interface PreferencesContract {

    interface Presenter extends BasePresenter, android.view.View.OnClickListener {
        void loadSettings();
        boolean checkInput();
        void saveSettings();

        @Override
        void onClick(android.view.View v);

        @Override
        void start();
    }

    interface View extends BaseView<Presenter> {
        void setName(String name);
        void setEmail(String email);
        void setPhone(String phone);
        String getNameText();
        String getEmailText();
        String getPhoneText();
        void showToast(String message);
    }


}
