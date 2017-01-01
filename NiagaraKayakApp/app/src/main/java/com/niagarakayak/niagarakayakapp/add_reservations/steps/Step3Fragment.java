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

public class Step3Fragment extends Fragment {
    private TextInputEditText adultText;
    private TextInputEditText childText;
    private TextInputEditText singleText;
    private TextInputEditText tandemText;
    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }

        View root = inflater.inflate(R.layout.fragment_step3, container, false);

        adultText = (TextInputEditText) root.findViewById(R.id.adult_text);
        childText = (TextInputEditText) root.findViewById(R.id.child_text);
        singleText = (TextInputEditText) root.findViewById(R.id.single_text);
        tandemText = (TextInputEditText) root.findViewById(R.id.tandem_text);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundle != null) {
            adultText.setText(mBundle.getString("adult"));
            childText.setText(mBundle.getString("child"));
            singleText.setText(mBundle.getString("single"));
            tandemText.setText(mBundle.getString("tandem"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("adult", adultText.getText().toString());
        outState.putString("child", childText.getText().toString());
        outState.putString("single", singleText.getText().toString());
        outState.putString("tandem", tandemText.getText().toString());
    }

    public static Step3Fragment newInstance() {
        Bundle args = new Bundle();
        Step3Fragment fragment = new Step3Fragment();
        fragment.setArguments(args);
        return fragment;
    }
}
