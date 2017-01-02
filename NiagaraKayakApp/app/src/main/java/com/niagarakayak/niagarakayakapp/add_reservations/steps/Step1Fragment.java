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

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class Step1Fragment extends Fragment implements View.OnClickListener {

    private TextInputEditText dateText;
    private TextInputEditText timeText;
    private AutoCompleteTextView hoursText;
    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }

        View root = inflater.inflate(R.layout.fragment_step1, container, false);
        dateText = (TextInputEditText) root.findViewById(R.id.date_text);
        timeText = (TextInputEditText) root.findViewById(R.id.time_text);
        hoursText = (AutoCompleteTextView) root.findViewById(R.id.hours_text);
        dateText.setOnClickListener(this);
        timeText.setOnClickListener(this);
        String[] hoursOptions = getResources().getStringArray(R.array.hour_options);
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, hoursOptions);
        hoursText.setAdapter(optionsAdapter);
        hoursText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ((AutoCompleteTextView) v).showDropDown();
                return false;
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundle != null) {
            dateText.setText(mBundle.getString("date"));
            timeText.setText(mBundle.getString("time"));
            hoursText.setText(mBundle.getString("hour"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("date", dateText.getText().toString());
        outState.putString("time", timeText.getText().toString());
        outState.putString("hour", hoursText.getText().toString());
    }

    public static Step1Fragment newInstance() {
        Bundle args = new Bundle();
        Step1Fragment fragment = new Step1Fragment();
        fragment.setArguments(args);
        return fragment;
    }


    public void setDateText(String date) {
        dateText.setText(date);
    }

    public void setTimeText(String time) {
        timeText.setText(time);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_text: {
                Calendar now = Calendar.getInstance();
                DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String day = dayOfMonth < 10 ? "0"+dayOfMonth : ""+(dayOfMonth);
                        String month = monthOfYear < 10 ? "0"+(monthOfYear+1) : ""+(monthOfYear+1);
                        setDateText(year + "-" + month + "-" + day);
                    }
                }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
                dpd.show(getFragmentManager(), "datePicker");
                break;
            }

            case R.id.time_text: {
                TimePickerDialog tpd = TimePickerDialog.newInstance(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                        String amOrpm = hourOfDay < 12 ? "AM" : "PM";
                        String paddedMinute = minute < 10 ? "0"+minute : ""+minute;
                        String hour = hourOfDay > 12 ? ""+(hourOfDay-12) : ""+hourOfDay;
                        setTimeText(hour+ ":" + paddedMinute + " " + amOrpm);
                    }
                }, 12, 0, false);
                tpd.show(getChildFragmentManager(), "timePicker");
                break;
            }

        }
    }
}
