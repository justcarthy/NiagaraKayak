package com.niagarakayak.niagarakayakapp.add_reservations;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;

import java.util.Objects;

public class AddReservationsActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private AddReservationsViewFragment addReservationsViewFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_add_reservations);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setToolbarTitle("Add Reservation");

        if (savedInstanceState == null) {
            addReservationsViewFragment = AddReservationsViewFragment.newInstance();
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            transaction.add(R.id.contentView, addReservationsViewFragment);
            transaction.commit();
        } else {
            addReservationsViewFragment = (AddReservationsViewFragment) getFragmentManager().findFragmentById(R.id.contentView);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        new AddReservationsPresenter(addReservationsViewFragment);
    }

    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
