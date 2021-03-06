package com.niagarakayak.niagarakayakapp.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.contact.ContactActivity;
import com.niagarakayak.niagarakayakapp.login_activity.LoginActivity;
import com.niagarakayak.niagarakayakapp.model.Weather;
import com.niagarakayak.niagarakayakapp.preferences.PreferencesActivity;
import com.niagarakayak.niagarakayakapp.reservations.ReservationActivity;
import com.niagarakayak.niagarakayakapp.service.database.ReservationReaderContract.ReservationEntry;
import com.niagarakayak.niagarakayakapp.service.database.ReservationReaderHelper;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.service.weather.OpenWeatherAPIService;
import com.niagarakayak.niagarakayakapp.service.weather.WeatherService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.WeatherUtils;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static com.niagarakayak.niagarakayakapp.util.ActivityUtils.isConnectedOrConnecting;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.ERROR_COLOR;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.WEATHER_COLOR;

public class HomeActivity extends AppCompatActivity {
    private View headerLayout;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private NavigationView navigationView;

    private ActionBarDrawerToggle drawerToggle;

    private HomeViewFragment homeViewFragment;
    private SharedPreferences prefs;

    private TwitterAPIService twitterAPIService;
    private OpenWeatherAPIService openWeatherAPIService;

    private ReservationReaderHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!checkSettings(prefs)) {
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

        setContentView(R.layout.activity_home);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setToolbarTitle("Home");
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        headerLayout = navigationView.inflateHeaderView(R.layout.nav_header);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        twitterAPIService = new TwitterAPIService(
                getString(R.string.TWITTER_CONSUMER_KEY),
                getString(R.string.TWITTER_CONSUMER_SECRET),
                getString(R.string.TWITTER_ACCESS_TOKEN),
                getString(R.string.TWITTER_ACCESS_TOKEN_SECRET)
        );

        openWeatherAPIService = new OpenWeatherAPIService(
                getString(R.string.OPEN_WEATHER_API_KEY)
        );


        if (savedInstanceState == null) {
            homeViewFragment = HomeViewFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), homeViewFragment, R.id.contentView);
            if (isConnectedOrConnecting(this)) {
                loadWeatherBar("St.Catharines");
            }
        } else {
            homeViewFragment = (HomeViewFragment) getSupportFragmentManager().findFragmentById(R.id.contentView);
        }

        setNavHeaderText(prefs.getString("name", ""), prefs.getString("email", ""));
        new HomePresenter(twitterAPIService, homeViewFragment, isConnectedOrConnecting(this));

        dbHelper = new ReservationReaderHelper(this);

        setupDrawer();
    }

    private void setNavHeaderText(String username, String email) {
        TextView navUserText = (TextView) headerLayout.findViewById(R.id.nav_user_name);
        TextView navEmailText = (TextView) headerLayout.findViewById(R.id.nav_user_email);

        navUserText.setText(username);
        navEmailText.setText(email);
    }

    private void loadWeatherBar(String city) {
        openWeatherAPIService.fetchWeather(new WeatherService.WeatherServiceRequest(city,
                new WeatherService.WeatherCallback() {
                    @Override
                    public void onFailure() {
                        ActivityUtils.showSnackbarWithMessage(homeViewFragment.getView(), "Couldn't fetch the weather!'", LENGTH_LONG, ERROR_COLOR);
                    }

                    @Override
                    public void onSuccess(Weather weather) {
                        if (homeViewFragment != null) {
                            ActivityUtils.showSnackbarWithMessage(homeViewFragment.getView(), WeatherUtils.getWeatherString(weather), LENGTH_LONGER, WEATHER_COLOR);
                        }
                    }
                }));
    }

    private boolean checkSettings(SharedPreferences prefs) {
        if (prefs.getString("name", null) == null
                || prefs.getString("email", null) == null
                || prefs.getString("phone", null) == null) {
            return false;
        }

        return true;
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private void setupDrawer() {
        drawerToggle = setupDrawerToggle();
        drawerLayout.addDrawerListener(drawerToggle);
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

        int itemClicked = 0;
        switch (item.getItemId()) {
            case R.id.nav_home: {
                // Do nothing, we are already here
                break;
            }

            case R.id.nav_reservations: {
                // Reservations
                Intent i = new Intent(this, ReservationActivity.class);
                itemClicked = 1;
                navigationView.getMenu().getItem(itemClicked).setChecked(true);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
                break;
            }

            case R.id.nav_preferences: {
                // Preferences
                Intent i = new Intent(this, PreferencesActivity.class);
                itemClicked = 2;
                navigationView.getMenu().getItem(itemClicked).setChecked(true);
                i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(i);
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
                dbHelper.reset(dbHelper.getWritableDatabase(), ReservationEntry.RESERVATION_TABLE);
                ActivityUtils.clearSharedPrefs(prefs);
                Intent i = new Intent(this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;
            }
        }

        navigationView.getMenu().getItem(itemClicked).setChecked(false);
        drawerLayout.closeDrawers();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.access_drawer_open,  R.string.accesss_drawer_close);
    }



    public void setToolbarTitle(CharSequence title) {
        getSupportActionBar().setTitle(title);
    }
}
