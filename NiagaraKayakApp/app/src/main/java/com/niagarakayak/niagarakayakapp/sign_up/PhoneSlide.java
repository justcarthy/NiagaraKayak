package com.niagarakayak.niagarakayakapp.sign_up;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerAPIService;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;

public class PhoneSlide extends SignUpSlide {


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRoot = inflater.inflate(R.layout.fragment_intro_phone_layout, container, false);
        this.input = (EditText) mRoot.findViewById(R.id.intro_phone_text);
        return mRoot;
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#66bed1");
    }

    @Override
    public boolean isPolicyRespected() {
        return isValid();
    }

    @Override
    void handleContinue(GoToNextPageCallback callback) {
        // Never gets called. The continue button is not visible on this slide.
    }

    @Override
    void onIllegalyRequestedContinue() {
        ActivityUtils.showSnackbarWithMessage(getView(),
                "Invalid phone!",
                LENGTH_LONGER, SnackbarUtils.SnackbarColor.ERROR_COLOR);
    }
}