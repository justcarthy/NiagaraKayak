package com.niagarakayak.niagarakayakapp.intro;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.niagarakayak.niagarakayakapp.R;

public class EmailSlide extends IntroSlide implements ISlideBackgroundColorHolder{

    private View container;
    private EditText input;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.intro_email_layout, container, false);
        this.container = root;
        this.input = (EditText) root.findViewById(R.id.intro_email_text);
        return root;
    }

    @Override
    boolean isInputValid() {
       return super.isInputValid() && input.getText().toString().contains("@");
    }

    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#47a1b3");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (container != null) {
            container.setBackgroundColor(backgroundColor);
        }
    }

}
