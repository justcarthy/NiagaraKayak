package com.niagarakayak.niagarakayakapp.preferences;

import android.content.SharedPreferences;
import android.view.View;
import com.niagarakayak.niagarakayakapp.sign_up.EmailSlide;

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

        mPrefsView.setName(nameSetting);
        mPrefsView.setEmail(emailSetting);
        mPrefsView.setPhone(phoneSetting);
    }

    @Override
    public boolean validInput() {
        String name = mPrefsView.getNameText();
        String email = mPrefsView.getEmailText();
        String phone = mPrefsView.getPhoneText();

        if (name.isEmpty()|| email.isEmpty()|| phone.isEmpty()) {
            mPrefsView.showToast("Fields cannot be blank");
            return false;
        }

        if (!EmailSlide.validEmail(email)) {
            mPrefsView.showToast("Invalid email");
            return false;
        }


        return true;
    }

    @Override
    public void saveSettings() {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", mPrefsView.getNameText());
        editor.putString("email", mPrefsView.getEmailText());
        editor.putString("phone", mPrefsView.getPhoneText());
        editor.commit();
        mPrefsView.showToast("Saved!");
    }

    @Override
    public void onClick(View v) {
        if(validInput()) {
            saveSettings();
            mPrefsView.goHome();
        }
    }
}
