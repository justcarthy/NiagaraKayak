package com.niagarakayak.niagarakayakapp.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Weather;
import com.niagarakayak.niagarakayakapp.preferences.PreferencesActivity;
import com.niagarakayak.niagarakayakapp.preferences.PreferencesPresenter;
import com.niagarakayak.niagarakayakapp.preferences.PreferencesViewFragment;
import com.niagarakayak.niagarakayakapp.reservations.ReservationActivity;
import com.niagarakayak.niagarakayakapp.reservations.ReservationsPresenter;
import com.niagarakayak.niagarakayakapp.reservations.ReservationsViewFragment;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.service.weather.OpenWeatherAPIService;
import com.niagarakayak.niagarakayakapp.service.weather.WeatherService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.SnackbarUtils;
import com.niagarakayak.niagarakayakapp.util.WeatherUtils;

import static android.support.design.widget.Snackbar.LENGTH_LONG;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.ERROR_COLOR;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.WEATHER_COLOR;


public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private String[] mDrawerTitles;
    private CharSequence mTitle;

    private ActionBarDrawerToggle mDrawerToggle;

    private HomeViewFragment homeViewFragment;
    private SharedPreferences prefs;

    private TwitterAPIService twitterAPIService;
    private OpenWeatherAPIService openWeatherAPIService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        if (!checkSettings(prefs)) {
            // TODO: run intro here
        }

        setContentView(R.layout.activity_home);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setToolbarTitle("Home");
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = mNavigationView.inflateHeaderView(R.layout.nav_header);
        mDrawerTitles = getResources().getStringArray(R.array.menu_titles);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        // The HomePresenter needs the twitter api service.
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
            // Show the weather bar once.
            loadWeatherBar("St.Catharines");

        } else {
            homeViewFragment = (HomeViewFragment) getSupportFragmentManager().findFragmentById(R.id.contentView);
        }

        new HomePresenter(twitterAPIService, openWeatherAPIService, homeViewFragment, isConnectedOrConnecting());
        setupDrawer();
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
                        ActivityUtils.showSnackbarWithMessage(homeViewFragment.getView(), WeatherUtils.getWeatherString(weather), LENGTH_LONGER, WEATHER_COLOR);
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
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
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

    private void selectDrawerItem(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home: {
                // Do nothing, we are already here
                break;
            }

            case R.id.nav_preferences: {
                // Preferences
                Intent i = new Intent(this, PreferencesActivity.class);
                startActivity(i);
                break;
            }

            case R.id.nav_reservations: {
                // Reservations
                Intent i = new Intent(this, ReservationActivity.class);
                startActivity(i);
                break;
            }
        }

        mNavigationView.getMenu().getItem(0).setChecked(true);
        mDrawer.closeDrawers();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.access_drawer_open,  R.string.accesss_drawer_close);
    }

    private boolean isConnectedOrConnecting() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {
            return activeNetwork.isConnectedOrConnecting();
        }

        return false;
    }

    public void setToolbarTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}
