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
import com.niagarakayak.niagarakayakapp.home.HomeActivity;
import com.niagarakayak.niagarakayakapp.reservations.ReservationActivity;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;

public class PreferencesActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private String[] mDrawerTitles;
    private DrawerLayout mDrawer;
    private PreferencesViewFragment preferencesViewFragment;
    private DrawerLayout.DrawerListener mDrawerToggle;
    private SharedPreferences prefs;
    View headerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        setContentView(R.layout.activity_preferences);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setToolbarTitle("Preferences");
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = mNavigationView.inflateHeaderView(R.layout.nav_header);
        mDrawerTitles = getResources().getStringArray(R.array.menu_titles);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (savedInstanceState == null) {
            preferencesViewFragment = PreferencesViewFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), preferencesViewFragment, R.id.contentView);

        } else {
            preferencesViewFragment = (PreferencesViewFragment) getSupportFragmentManager().findFragmentById(R.id.contentView);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        new PreferencesPresenter(prefs, preferencesViewFragment);
        setNavHeaderText(prefs.getString("name", ""), prefs.getString("email", ""));
        setupDrawer();
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
                // Do nothing, we are already here
                break;
            }

            case R.id.nav_reservations: {
                Intent i = new Intent(this, ReservationActivity.class);
                startActivity(i);
                break;
            }
        }

        mNavigationView.getMenu().getItem(2).setChecked(true);
        mDrawer.closeDrawers();
    }


    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
}
