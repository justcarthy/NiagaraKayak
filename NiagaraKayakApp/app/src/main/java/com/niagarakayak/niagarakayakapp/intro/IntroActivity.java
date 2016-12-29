package com.niagarakayak.niagarakayakapp.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;
import com.github.paolorotolo.appintro.AppIntro2;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;

public class IntroActivity extends AppIntro2 {
    private NameSlide nameSlide;
    private EmailSlide emailSlide;
    private PhoneSlide phoneSlide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameSlide = new NameSlide();
        emailSlide = new EmailSlide();
        phoneSlide = new PhoneSlide();

        addSlide(nameSlide);
        addSlide(emailSlide);
        addSlide(phoneSlide);

        setColorTransitionsEnabled(true);

        // Hide Skip/Done button.
        showSkipButton(false);

        // Turn vibration on and set intensity.
        setVibrate(true);
        setVibrateIntensity(30);
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
//        super.onDonePressed(currentFragment);

        boolean isNameEmpty = nameSlide.isInputValid();
        boolean isEmailEmpty = emailSlide.isInputValid();
        boolean isPhoneEmpty = phoneSlide.isInputValid();

        // First check input
        if (!invalidInput(isNameEmpty, isEmailEmpty, isPhoneEmpty)) {
            // If it's good, save it in shared preferences
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("name", nameSlide.getInput());
            editor.putString("email", emailSlide.getInput());
            editor.putString("phone", phoneSlide.getInput());

            editor.commit();

            // Close this activity, Start the home activity!
            Intent i = new Intent(this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }
    }

    private boolean invalidInput(boolean nameStatus, boolean emailStatus, boolean phoneStatus) {
        if (nameStatus) {
            Toast.makeText(IntroActivity.this, "You left the name field blank! Please tell us your name!", Toast.LENGTH_LONG).show();
            return true;
        }

        if (emailStatus) {
            Toast.makeText(IntroActivity.this, "Invalid email! Please check the email field again", Toast.LENGTH_LONG).show();
            return true;
        }
        
        if (phoneStatus) {
            Toast.makeText(IntroActivity.this, "You left the phone field blank! Please tell us your number!", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
}
