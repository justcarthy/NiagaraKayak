package com.niagarakayak.niagarakayakapp.preferences;

import android.content.SharedPreferences;
import android.view.View;
import com.niagarakayak.niagarakayakapp.model.Customer;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerAPIService;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerService;

import java.util.ArrayList;

public class PreferencesPresenter implements PreferencesContract.Presenter {

    private final PreferencesContract.View prefsView;
    private final SharedPreferences prefs;
    private final CustomerAPIService customerAPIService;
    private String nameSetting, emailSetting, phoneSetting;

    public PreferencesPresenter(SharedPreferences prefs, PreferencesContract.View prefsView, CustomerAPIService customerAPIService) {
        this.prefs = prefs;
        this.prefsView = prefsView;
        this.prefsView.setPresenter(this);
        this.customerAPIService = customerAPIService;
    }

    @Override
    public void start() {
        loadSettings();
    }

    @Override
    public void loadSettings() {
        nameSetting = prefs.getString("name", "");
        emailSetting = prefs.getString("email", "");
        phoneSetting = prefs.getString("phone", "");

        prefsView.setName(nameSetting);
        prefsView.setEmail(emailSetting);
        prefsView.setPhone(phoneSetting);
    }

    @Override
    public boolean validInput() {
        String name = prefsView.getNameText();
        String phone = prefsView.getPhoneText();

        if (name.isEmpty() || phone.isEmpty()) {
            prefsView.showToast("Fields cannot be blank");
            return false;
        }

        return true;
    }

    @Override
    public void saveSettings() {
        String inputName = prefsView.getNameText();
        String inputPhone = prefsView.getPhoneText();
        String inputEmail = prefsView.getEmailText();

        boolean nameChanged = !nameSetting.equals(inputName);
        boolean phoneChanged = !phoneSetting.equals(inputPhone);

        // If either name or phone changed, fire a request to update the customer table.
        if (nameChanged || phoneChanged) {
            customerAPIService.updateCustomer(inputEmail, inputName, inputPhone, new CustomerService.CustomerCallback() {
                @Override
                public void onFailure(Exception ex) {
                    prefsView.showToast("Unable to update. Server issues. Try again later.");
                }

                @Override
                public void onSuccess() {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("name", prefsView.getNameText());
                    editor.putString("phone", prefsView.getPhoneText());
                    editor.commit();
                    prefsView.showToast("Saved!");
                    prefsView.goHome();
                }

                @Override
                public void onSuccess(ArrayList<Customer> customers) {}
            });
        }
    }

    @Override
    public void onClick(View v) {
        if(validInput()) {
            saveSettings();
        }
    }
}
