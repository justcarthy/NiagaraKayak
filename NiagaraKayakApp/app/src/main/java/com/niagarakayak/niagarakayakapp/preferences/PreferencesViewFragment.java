package com.niagarakayak.niagarakayakapp.preferences;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.niagarakayak.niagarakayakapp.R;

/**
 * Created by justin on 21/12/16.
 */

public class PreferencesViewFragment extends Fragment implements PreferencesContract.View {

    private PreferencesContract.Presenter mPresenter;

    private EditText nameText;
    private EditText phoneText;
    private EditText emailText;
    private Button saveButton;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root  = inflater.inflate(R.layout.fragment_preferences, container, false);
        nameText = (EditText) root.findViewById(R.id.name);
        phoneText = (EditText) root.findViewById(R.id.phone);
        emailText = (EditText) root.findViewById(R.id.email);
        saveButton = (Button) root.findViewById(R.id.btn_save);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void setPresenter(PreferencesContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void setName() {

    }

    @Override
    public void setEmail() {

    }

    @Override
    public void setPhone() {

    }

    @Override
    public void getNameText() {

    }

    @Override
    public void getEmailText() {

    }

    @Override
    public void getPhoneText() {

    }

    public static PreferencesViewFragment newInstance() {
        Bundle args = new Bundle();
        PreferencesViewFragment preferencesViewFragment = new PreferencesViewFragment();
        preferencesViewFragment.setArguments(args);
        return preferencesViewFragment;
    }
}
