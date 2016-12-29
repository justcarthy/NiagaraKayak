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

public class Step2Fragment extends Fragment {
    private TextInputEditText adultText;
    private TextInputEditText childText;
    private AutoCompleteTextView launchText;
    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }

        View root = inflater.inflate(R.layout.fragment_step2, container, false);

        adultText = (TextInputEditText) root.findViewById(R.id.adult_text);
        childText = (TextInputEditText) root.findViewById(R.id.child_text);
        launchText = (AutoCompleteTextView) root.findViewById(R.id.launch_text);

        String[] launchOptions = getResources().getStringArray(R.array.launch_points_array);
        ArrayAdapter<String> optionsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, launchOptions);
        launchText.setAdapter(optionsAdapter);
        launchText.setOnTouchListener(new View.OnTouchListener() {
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
            adultText.setText(mBundle.getString("adult"));
            childText.setText(mBundle.getString("child"));
            launchText.setText(mBundle.getString("launch"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("adult", adultText.getText().toString());
        outState.putString("child", childText.getText().toString());
        outState.putString("launch", launchText.getText().toString());
    }

    public static Step2Fragment newInstance() {
        Bundle args = new Bundle();
        Step2Fragment fragment = new Step2Fragment();
        fragment.setArguments(args);
        return fragment;
    }
}
