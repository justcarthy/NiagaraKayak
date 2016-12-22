package com.niagarakayak.niagarakayakapp.preferences;

import android.content.SharedPreferences;

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
    }

    @Override
    public void saveSettings() {

    }
}
