package com.niagarakayak.niagarakayakapp.sign_up;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;

public class NameSlide extends SignUpSlide {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.mRoot = inflater.inflate(R.layout.fragment_intro_name_layout, container, false);
        this.input = (EditText) mRoot.findViewById(R.id.intro_name_text);
        setIsAllowedToContinue(false);
        return mRoot;
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#1F8094");
    }

    @Override
    void onIllegalyRequestedContinue() {
        ActivityUtils.showSnackbarWithMessage(getView(), "Name can't be empty!", LENGTH_LONGER, SnackbarUtils.SnackbarColor.ERROR_COLOR);
    }

    @Override
    void handleContinue(GoToNextPageCallback callback) {
       if (isValid()) {
           callback.nextPage();
       }
    }
}