package com.niagarakayak.niagarakayakapp.intro;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.niagarakayak.niagarakayakapp.R;

public class VerificationSlide extends Fragment implements ISlideBackgroundColorHolder {
    private View container;
    private EditText input;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step2, container, false);
        root.findViewById(R.id.date_text);
        this.container = root;
        input = (EditText) root.findViewById(R.id.intro_verify_text);
        return root;
    }

    // TODO: Write code to verify here.


    @Override
    public int getDefaultBackgroundColor() {
        return Color.parseColor("#64b1c1");
    }

    @Override
    public void setBackgroundColor(@ColorInt int backgroundColor) {
        if (container != null) {
            container.setBackgroundColor(backgroundColor);
        }
    }
}
