package com.niagarakayak.niagarakayakapp.reservations;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.add_reservations.AddReservationsActivity;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;
import com.niagarakayak.niagarakayakapp.preferences.PreferencesActivity;
import com.niagarakayak.niagarakayakapp.service.database.ReservationLocalDataService;
import com.niagarakayak.niagarakayakapp.service.reservation.ReservationAPIService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;

public class ReservationActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private String[] mDrawerTitles;
    private DrawerLayout mDrawer;
    private ReservationsViewFragment reservationsViewFragment;
    private DrawerLayout.DrawerListener mDrawerToggle;
    private SharedPreferences prefs;
    private View headerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_reservation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setToolbarTitle("Reservations");
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = mNavigationView.inflateHeaderView(R.layout.nav_header);
        mDrawerTitles = getResources().getStringArray(R.array.menu_titles);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (savedInstanceState == null) {
            reservationsViewFragment = ReservationsViewFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), reservationsViewFragment, R.id.contentView);

        } else {
            reservationsViewFragment = (ReservationsViewFragment) getSupportFragmentManager().findFragmentById(R.id.contentView);
        }

        ReservationAPIService reservationAPIService = new ReservationAPIService(getString(R.string.NK_API_KEY));
        ReservationLocalDataService reservationLocalDataService = new ReservationLocalDataService(this);

        setNavHeaderText(prefs.getString("name", ""), prefs.getString("email", ""));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        new ReservationsPresenter(prefs.getString("email", ""), reservationLocalDataService, reservationAPIService, reservationsViewFragment);
        setupDrawer();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.reservation_menu, menu);
        MenuItem addReservationButton = menu.getItem(0);
        addReservationButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent i = new Intent(ReservationActivity.this, AddReservationsActivity.class);
                startActivity(i);
                return true;
            }
        });
        return true;
    }

    private void setNavHeaderText(String username, String email) {
        TextView navUserText = (TextView) headerLayout.findViewById(R.id.nav_user_name);
        TextView navEmailText = (TextView) headerLayout.findViewById(R.id.nav_user_email);

        navUserText.setText(username);
        navEmailText.setText(email);
    }


    private void setupDrawer() {
        mDrawerToggle = setupDrawerToggle();
        mDrawer.addDrawerListener(mDrawerToggle);
        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem item) {
                        selectDrawerItem(item);
                        return true;
                    }
                }
        );
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.access_drawer_open,  R.string.accesss_drawer_close);
    }

    private void selectDrawerItem(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home: {
                Intent i = new Intent(this, HomeActivity.class);
                startActivity(i);
                break;
            }

            case R.id.nav_preferences: {
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
                break;
            }

            case R.id.nav_reservations: {
                // Do nothing, we are already here
                break;
            }
        }

        mNavigationView.getMenu().getItem(1).setChecked(true);
        mDrawer.closeDrawers();
    }


    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
