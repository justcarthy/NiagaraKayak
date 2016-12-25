package com.niagarakayak.niagarakayakapp.intro;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class IntroSlide extends Fragment {

    private View container;
    private EditText input;

    boolean isInputValid() {
        return input != null && !TextUtils.isEmpty(input.getText());
    }

    String getInput() {
        if (input != null) {
            return input.getText().toString();
        }

        return "";
    }


}
