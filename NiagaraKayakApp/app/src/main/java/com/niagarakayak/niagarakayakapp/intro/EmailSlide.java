package com.niagarakayak.niagarakayakapp.intro;

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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailSlide extends Fragment implements ISlideBackgroundColorHolder{

    private View container;
    private EditText input;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_intro_email_layout, container, false);
        this.container = root;
        this.input = (EditText) root.findViewById(R.id.intro_email_text);
        return root;
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

    boolean isInputValid() {
        return input != null && TextUtils.isEmpty(input.getText()) && validEmail(input.getText().toString());
    }

    public static boolean validEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }

    String getInput() {
        if (input != null) {
            return input.getText().toString();
        }

        return "";
    }

}