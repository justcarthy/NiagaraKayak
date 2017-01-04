package com.niagarakayak.niagarakayakapp.sign_up;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerAPIService;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;

public class VerificationSlide extends SignUpSlide implements ISlideBackgroundColorHolder {

    private CustomerAPIService customerAPIService;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRoot = inflater.inflate(R.layout.fragment_intro_email_verification_layout, container, false);
        mRoot.findViewById(R.id.date_text);
        this.input = (EditText) mRoot.findViewById(R.id.intro_verify_text);
        customerAPIService = new CustomerAPIService(getString(R.string.NK_API_KEY));
        setIsAllowedToContinue(false);
        return mRoot;
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#64b1c1");
    }

    @Override
    void onIllegalyRequestedContinue() {
        ActivityUtils.showSnackbarWithMessage(getView(),
                "Please enter a valid verification code.",
                LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
    }

    @Override
    void handleContinue(final GoToNextPageCallback callback) {
        String email = ((EditText) getActivity().findViewById(R.id.intro_email_text)).getText().toString();
        String verificationCode = getInput();

        customerAPIService.verify(email, verificationCode, new CustomerService.CustomerCallback() {
            @Override
            public void onFailure(Exception ex) {
                if (ex instanceof CustomerService.InvalidValidationCode) {
                    ActivityUtils.showSnackbarWithMessage(getView(),
                            "Validation code is invalid or expired. Try again later.",
                            LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
                } else {
                    ActivityUtils.showSnackbarWithMessage(getView(),
                            "Server error. Failed to verify validation code. Try again later.",
                            LENGTH_LONGER, SnackbarColor.ERROR_COLOR);
                }
            }

            @Override
            public void onSuccess() {
                callback.nextPage();
            }
        });
    }
}
