package com.niagarakayak.niagarakayakapp.home;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.service.weather.OpenWeatherAPIService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;


public class HomeActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private String[] mDrawerTitles;
    private CharSequence mTitle;

    private ActionBarDrawerToggle mDrawerToggle;
    private HomeViewFragment homeFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setToolbarTitle("Home");
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerLayout = mNavigationView.inflateHeaderView(R.layout.nav_header);
        mDrawerTitles = getResources().getStringArray(R.array.menu_titles);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        homeFragment = (HomeViewFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentView);
        if (homeFragment == null) {
            homeFragment = HomeViewFragment.newInstance();
            ActivityUtils.addFragmentToActivty(getSupportFragmentManager(), homeFragment, R.id.contentView);
        }

        boolean connectionStatus = isConnectedOrConnecting();

        TwitterAPIService twitterAPIService = new TwitterAPIService(
                getString(R.string.TWITTER_CONSUMER_KEY),
                getString(R.string.TWITTER_CONSUMER_SECRET),
                getString(R.string.TWITTER_ACCESS_TOKEN),
                getString(R.string.TWITTER_ACCESS_TOKEN_SECRET)
        );

        OpenWeatherAPIService openWeatherAPIService = new OpenWeatherAPIService(
                getString(R.string.OPEN_WEATHER_API_KEY)
        );

        // Set presenter
        new HomePresenter(twitterAPIService, openWeatherAPIService, homeFragment, connectionStatus);
        setupDrawer();
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
        // TODO: Implement when we make more activities.
        setToolbarTitle(item.getTitle());
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

        if (activeNetwork!= null) {
            return activeNetwork.isConnectedOrConnecting();
        }

        return false;
    }

    public void setToolbarTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}
