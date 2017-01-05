package com.niagarakayak.niagarakayakapp.preferences;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.contact.ContactActivity;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;
import com.niagarakayak.niagarakayakapp.login_activity.LoginActivity;
import com.niagarakayak.niagarakayakapp.reservations.ReservationActivity;
import com.niagarakayak.niagarakayakapp.service.customers.CustomerAPIService;
import com.niagarakayak.niagarakayakapp.service.database.ReservationLocalDataService;
import com.niagarakayak.niagarakayakapp.service.database.ReservationReaderContract;
import com.niagarakayak.niagarakayakapp.service.database.ReservationReaderHelper;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;

public class PreferencesActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private SharedPreferences prefs;
    private View headerLayout;

    private ReservationReaderHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_preferences);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolbarTitle("Preferences");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        PreferencesViewFragment preferencesViewFragment;
        if (savedInstanceState == null) {
            preferencesViewFragment = PreferencesViewFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), preferencesViewFragment, R.id.contentView);

        } else {
            preferencesViewFragment = (PreferencesViewFragment) getSupportFragmentManager().findFragmentById(R.id.contentView);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        new PreferencesPresenter(prefs, preferencesViewFragment, new CustomerAPIService(getString(R.string.NK_API_KEY)));
        setNavHeaderText(prefs.getString("name", ""), prefs.getString("email", ""));

        dbHelper = new ReservationReaderHelper(this);

        setupDrawer();
    }

    private void setNavHeaderText(String username, String email) {
        TextView navUserText = (TextView) headerLayout.findViewById(R.id.nav_user_name);
        TextView navEmailText = (TextView) headerLayout.findViewById(R.id.nav_user_email);

        navUserText.setText(username);
        navEmailText.setText(email);
    }

    private void setupDrawer() {
        DrawerLayout.DrawerListener mDrawerToggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(mDrawerToggle);
        navigationView.setNavigationItemSelectedListener(
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
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.access_drawer_open,  R.string.accesss_drawer_close);
    }

    private void selectDrawerItem(MenuItem item) {

        int itemClicked = 2;
        switch (item.getItemId()) {
            case R.id.nav_home: {
                Intent i = new Intent(this, HomeActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                itemClicked = 0;
                startActivity(i);
                break;
            }

            case R.id.nav_reservations: {
                Intent i = new Intent(this, ReservationActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                itemClicked = 1;
                startActivity(i);
                break;
            }

            case R.id.nav_preferences: {
                // Do nothing, we are already here
                break;
            }

            case R.id.nav_contact: {
                Intent i = new Intent(this, ContactActivity.class);
                itemClicked = 3;
                navigationView.getMenu().getItem(itemClicked).setChecked(true);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                break;
            }

            case R.id.nav_sign_out: {
                dbHelper.reset(dbHelper.getWritableDatabase(), ReservationReaderContract.ReservationEntry.RESERVATION_TABLE);
                ActivityUtils.clearSharedPrefs(prefs);
                Intent i = new Intent(this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            }
        }

        navigationView.getMenu().getItem(itemClicked).setChecked(true);
        drawerLayout.closeDrawers();
    }


    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
