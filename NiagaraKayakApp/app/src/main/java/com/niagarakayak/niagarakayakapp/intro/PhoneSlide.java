package com.niagarakayak.niagarakayakapp.intro;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.niagarakayak.niagarakayakapp.R;

public class PhoneSlide extends Fragment implements ISlideBackgroundColorHolder{

    private View container;
    private EditText input;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.intro_phone_layout, container, false);
        this.container = root;
        this.input = (EditText) root.findViewById(R.id.intro_phone_text);
        return root;
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#66bed1");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (container != null) {
            container.setBackgroundColor(backgroundColor);
        }
    }

    boolean isInputValid() {
        return input != null && TextUtils.isEmpty(input.getText());
    }

    String getInput() {
        if (input != null) {
            return input.getText().toString();
        }

        return "";
    }

}