package com.niagarakayak.niagarakayakapp.sign_up;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import com.github.paolorotolo.appintro.AppIntro2;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;
import com.niagarakayak.niagarakayakapp.model.Customer;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerAPIService;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;

import java.util.ArrayList;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;

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

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SignUpSlide finalSlide = (SignUpSlide) mPagerAdapter.getItem(pager.getCurrentItem());
                finalSlide.handleDone(new SignUpSlide.OnDoneCallback() {
                    @Override
                    public void onDone() {
                        CustomerAPIService customerAPIService = new CustomerAPIService(getString(R.string.NK_API_KEY));
                        customerAPIService.postCustomer(nameSlide.getInput(), emailSlide.getInput(), phoneSlide.getInput(), new CustomerService.CustomerCallback() {
                            @Override
                            public void onFailure(Exception ex) {
                                // The customer exists exception should never be thrown, since we already verify on the email slide.
                                ActivityUtils.showSnackbarWithMessage(findViewById(android.R.id.content),
                                        "Couldn't create account, server issues. Try again later.",
                                        LENGTH_LONGER, SnackbarUtils.SnackbarColor.ERROR_COLOR);
                            }

                            @Override
                            public void onSuccess() {
                                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor editor = prefs.edit();

                                editor.putString("name", nameSlide.getInput());
                                editor.putString("email", emailSlide.getInput());
                                editor.putString("phone", phoneSlide.getInput());
                                editor.commit();

                                Intent i = new Intent(SignUpActivity.this, HomeActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }

                            @Override
                            public void onSuccess(ArrayList<Customer> customers) {
                                //d
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
    }
}