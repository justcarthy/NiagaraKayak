package com.niagarakayak.niagarakayakapp.sign_up;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.github.paolorotolo.appintro.AppIntro2;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;

public class SignUpActivity extends AppIntro2 {
    private NameSlide nameSlide;
    private EmailSlide emailSlide;
    private PhoneSlide phoneSlide;
    private VerificationSlide verificationSlide;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        nameSlide = new NameSlide();
        emailSlide = new EmailSlide();
        phoneSlide = new PhoneSlide();
        verificationSlide = new VerificationSlide();

        addSlide(nameSlide);
        addSlide(emailSlide);
        addSlide(verificationSlide);
        addSlide(phoneSlide);

        setFadeAnimation();
        setColorTransitionsEnabled(true);

        // Don't let the user swipe through it.
        setSwipeLock(true);

        // Hide Skip button.
        showSkipButton(false);

        // Turn vibration on and set intensity.
        setVibrate(true);
        setVibrateIntensity(30);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpSlide currentSlide = (SignUpSlide) mPagerAdapter.getItem(pager.getCurrentItem());
                currentSlide.handleContinue(new SignUpSlide.GoToNextPageCallback() {
                    @Override
                    public void nextPage() {
                        pager.setCurrentItem(pager.getCurrentItem() + 1);
                    }
                });
            }
        });
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        SignUpSlide slide = (SignUpSlide) currentFragment;

        if (slide.isValid()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
            SharedPreferences.Editor editor = prefs.edit();

            editor.putString("name", nameSlide.getInput());
            editor.putString("email", emailSlide.getInput());
            editor.putString("phone", phoneSlide.getInput());
            editor.commit();

            Intent i = new Intent(this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            slide.input.setHint("Phone is empty!");
        }
    }
}