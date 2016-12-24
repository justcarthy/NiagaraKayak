package com.niagarakayak.niagarakayakapp.preferences;

import android.content.SharedPreferences;
import android.util.Log;
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
        String nameSetting = prefs.getString("name", "");
        String emailSetting = prefs.getString("email", "");
        String phoneSetting = prefs.getString("phone", "");

        Log.d("PreferencesPresenter", "loadSettings: nameSetting: " + nameSetting);
        Log.d("PreferencesPresenter", "loadSettings: emailSetting: " + emailSetting);
        Log.d("PreferencesPresenter", "loadSettings: phoneSetting: " + phoneSetting);

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
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", mPrefsView.getNameText());
        editor.putString("email", mPrefsView.getEmailText());
        editor.putString("phone", mPrefsView.getPhoneText());
        editor.commit();
    }

    @Override
    public void onClick(View v) {
        if(checkInput()) {
            saveSettings();
        }
    }
}
