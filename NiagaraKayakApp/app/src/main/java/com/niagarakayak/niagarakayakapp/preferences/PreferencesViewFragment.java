package com.niagarakayak.niagarakayakapp.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;

public class PreferencesViewFragment extends Fragment implements PreferencesContract.View {

    private PreferencesContract.Presenter mPresenter;

    private TextInputEditText nameText;
    private TextInputEditText phoneText;
    private TextInputEditText emailText;
    private Button saveButton;

    private Bundle mBundle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }

        View root  = inflater.inflate(R.layout.fragment_preferences, container, false);
        nameText = (TextInputEditText) root.findViewById(R.id.name);
        phoneText = (TextInputEditText) root.findViewById(R.id.phone);
        emailText = (TextInputEditText) root.findViewById(R.id.email);
        saveButton = (Button) root.findViewById(R.id.btn_save);
        saveButton.setOnClickListener(mPresenter);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mBundle != null) {
            nameText.setText(mBundle.getString("name"));
            phoneText.setText(mBundle.getString("phone"));
            emailText.setText(mBundle.getString("email"));
        } else {
            mPresenter.start();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("name", nameText.getText().toString());
        outState.putString("phone", phoneText.getText().toString());
        outState.putString("email", emailText.getText().toString());
    }

    @Override
    public void setPresenter(PreferencesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setName(String name) {
        nameText.setText(name);
    }

    @Override
    public void setEmail(String email) {
        emailText.setText(email);
    }

    @Override
    public void setPhone(String phone) {
        phoneText.setText(phone);
    }

    @Override
    public String getNameText() {
        return nameText.getText().toString();
    }

    @Override
    public String getEmailText() {
        return emailText.getText().toString();
    }

    @Override
    public String getPhoneText() {
        return phoneText.getText().toString();
    }

    @Override
    public void goHome() {
        Intent i = new Intent(this.getContext(), HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    @Override
    public void showToast(String message) {
        Toast.makeText(this.getContext(), message, Toast.LENGTH_LONG).show();
    }

    public static PreferencesViewFragment newInstance() {
        Bundle args = new Bundle();
        PreferencesViewFragment preferencesViewFragment = new PreferencesViewFragment();
        preferencesViewFragment.setArguments(args);
        return preferencesViewFragment;
    }



}
