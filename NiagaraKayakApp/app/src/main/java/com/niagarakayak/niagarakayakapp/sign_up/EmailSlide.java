package com.niagarakayak.niagarakayakapp.sign_up;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Customer;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerAPIService;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;

public class EmailSlide extends SignUpSlide {

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private CustomerAPIService customerAPIService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRoot = inflater.inflate(R.layout.fragment_intro_email_layout, container, false);
        this.input = (EditText) mRoot.findViewById(R.id.intro_email_text);
        customerAPIService = new CustomerAPIService(getString(R.string.NK_API_KEY));
        setIsAllowedToContinue(false);
        return mRoot;
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#47a1b3");
    }

    public static boolean validEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    @Override
    public boolean isValid() {
        return super.isValid() && validEmail(getInput());
    }

    @Override
    public boolean isPolicyRespected() {
        return super.isPolicyRespected();
    }

    @Override
    void handleContinue(final GoToNextPageCallback callback) {
        if (isValid()) {
            customerAPIService.checkEmailFree(getInput(), new CustomerService.CustomerCallback() {
                @Override
                public void onFailure(Exception ex) {
                    if (ex instanceof CustomerService.CustomerExistsException) {
                        ActivityUtils.showSnackbarWithMessage(getView(), "Email is already taken",
                                LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
                    }
                }

                @Override
                public void onSuccess() {
                    sendVerificationEmail(callback);
                }

                @Override
                public void onSuccess(ArrayList<Customer> customers) {}
            });
        } else {
            onIllegalyRequestedContinue();
        }
    }

    @Override
    void onIllegalyRequestedContinue() {
        ActivityUtils.showSnackbarWithMessage(getView(), "Please enter a valid email!", LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
    }

    private void sendVerificationEmail(final GoToNextPageCallback callback) {
        final String email = getInput();

        customerAPIService.sendVerificationEmail(email, new CustomerService.CustomerCallback() {
            @Override
            public void onFailure(Exception ex) {
                ActivityUtils.showSnackbarWithMessage(getView(), "Unable to send email. Try again later.",
                        LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
            }

            @Override
            public void onSuccess() {
                callback.nextPage();
            }

            @Override
            public void onSuccess(ArrayList<Customer> customers) {}
        });
    }
}