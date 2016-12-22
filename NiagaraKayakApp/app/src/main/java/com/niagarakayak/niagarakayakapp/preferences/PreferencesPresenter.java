package com.niagarakayak.niagarakayakapp.preferences;

import android.content.SharedPreferences;
import android.view.View;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;

public class PreferencesPresenter implements PreferencesContract.Presenter {

    private final PreferencesContract.View mPrefsView;
    private final SharedPreferences prefs;

    public PreferencesPresenter(SharedPreferences prefs, PreferencesContract.View prefsView) {
        this.prefs = prefs;
        mPrefsView = prefsView;
        mPrefsView.setPresenter(this);
    }

    @Override
    public void start() {
        loadSettings();
    }

    @Override
    public void loadSettings() {
        // TODO: Implement

        String nameSetting = prefs.getString("name", null);
        String emailSetting = prefs.getString("email", null);
        String phoneSetting = prefs.getString("phone", null);
        mPrefsView.setName(nameSetting);
        mPrefsView.setEmail(emailSetting);
        mPrefsView.setPhone(phoneSetting);


    }

    @Override
    public boolean checkInput() {
        if(mPrefsView.getEmailText().isEmpty()||
                mPrefsView.getNameText().isEmpty()||
                mPrefsView.getEmailText().isEmpty()){
            mPrefsView.showToast("Fields cannot be blank");
            return false;
        }
        else return true;
    }

    @Override
    public void saveSettings() {
        prefs.edit().putString("name", mPrefsView.getNameText());
        prefs.edit().putString("email", mPrefsView.getEmailText());
        prefs.edit().putString("phone", mPrefsView.getPhoneText());
    }

    @Override
    public void onClick(View v) {
        if(checkInput()) {
            saveSettings();
        }
    }
}
