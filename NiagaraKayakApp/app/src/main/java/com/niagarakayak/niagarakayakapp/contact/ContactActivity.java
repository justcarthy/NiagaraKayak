package com.niagarakayak.niagarakayakapp.contact;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.home.HomeActivity;
import com.niagarakayak.niagarakayakapp.login_activity.LoginActivity;
import com.niagarakayak.niagarakayakapp.preferences.PreferencesActivity;
import com.niagarakayak.niagarakayakapp.reservations.ReservationActivity;
import com.niagarakayak.niagarakayakapp.service.database.ReservationReaderContract;
import com.niagarakayak.niagarakayakapp.service.database.ReservationReaderHelper;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private View headerLayout;
    private DrawerLayout drawer;
    private ReservationReaderHelper dbHelper;
    private SharedPreferences prefs;

    private RelativeLayout telephoneContainer, webContainer, emailContainer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        telephoneContainer = (RelativeLayout) findViewById(R.id.telephone_container);
        webContainer = (RelativeLayout) findViewById(R.id.web_container);
        emailContainer = (RelativeLayout) findViewById(R.id.email_container);

        setSupportActionBar(toolbar);
        setToolbarTitle("Contact");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);

        setNavHeaderText(prefs.getString("name", ""), prefs.getString("email", ""));

        dbHelper = new ReservationReaderHelper(this);

        setupDrawer();

        telephoneContainer.setOnClickListener(this);
        webContainer.setOnClickListener(this);
        emailContainer.setOnClickListener(this);

    }

    private void setupDrawer() {
        DrawerLayout.DrawerListener mDrawerToggle = setupDrawerToggle();
        drawer.addDrawerListener(mDrawerToggle);
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

    private void selectDrawerItem(MenuItem item) {
        int itemClicked = 1;
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
                Intent i = new Intent(this, PreferencesActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                itemClicked = 2;
                startActivity(i);
                break;
            }

            case R.id.nav_contact: {
                // Do nothing, we are already here
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
        drawer.closeDrawers();
    }

    private DrawerLayout.DrawerListener setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawer, toolbar, R.string.access_drawer_open,  R.string.accesss_drawer_close);
    }

    private void setNavHeaderText(String username, String email) {
        TextView navUserText = (TextView) headerLayout.findViewById(R.id.nav_user_name);
        TextView navEmailText = (TextView) headerLayout.findViewById(R.id.nav_user_email);

        navUserText.setText(username);
        navEmailText.setText(email);
    }

    private void setToolbarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.telephone_container:
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "289-654-2562", null));
                startActivity(dialIntent);
                break;
            case R.id.email_container:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:support@niagarakayak.com"));
                startActivity(emailIntent);
                break;
            case R.id.web_container:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.niagarakayak.com"));
                startActivity(browserIntent);
                break;
        }
    }
}
