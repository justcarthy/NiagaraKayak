package com.niagarakayak.niagarakayakapp.add_reservations.steps;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import com.niagarakayak.niagarakayakapp.R;

public class Step1Fragment extends Fragment {

    private TextInputEditText dateText;
    private TextInputEditText timeText;
    private AutoCompleteTextView hoursText;

    private View.OnClickListener listener;

    public Step1Fragment() {

    }

    public Step1Fragment(View.OnClickListener listener) {
        this.listener = listener;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step1, container, false);
        dateText =  (TextInputEditText) root.findViewById(R.id.date_text);
        timeText = (TextInputEditText) root.findViewById(R.id.time_text);
        hoursText = (AutoCompleteTextView) root.findViewById(R.id.hours_text);
        dateText.setOnClickListener(listener);
        timeText.setOnClickListener(listener);
        String[] hoursOptions = getResources().getStringArray(R.array.hour_options);
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, hoursOptions);
        hoursText.setAdapter(optionsAdapter);
        hoursText.setOnClickListener(listener);
        hoursText.setKeyListener(null);
        hoursText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((AutoCompleteTextView) v).showDropDown();
                return false;
            }
        });
        return root;
    }

    public String getDateText() {
        return dateText.getText().toString();
    }

    public String getTimeText() {
        return timeText.getText().toString();
    }

    public String getHoursText() {
        return hoursText.getText().toString();
    }

    public void setDateText(String date) {
        dateText.setText(date);
    }

    public void setHoursText(String hours) {
        hoursText.setText(hours);
    }

    public void setTimeText(String time) {
        timeText.setText(time);
    }


    public String getHoursOptionSelected() {
        return hoursText.getText().toString();
    }
}
