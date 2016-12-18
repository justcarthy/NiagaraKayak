package com.niagarakayak.niagarakayakapp.home;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ActionBar actionBar;
    private ResideMenu resideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getSupportActionBar();
        resideMenu = new ResideMenu(this);

        HomeViewFragment homeFragment = (HomeViewFragment) getSupportFragmentManager()
                .findFragmentById(R.id.contentView);

        if (homeFragment == null) {
            homeFragment = HomeViewFragment.newInstance();
            ActivityUtils.addFragmentToActivty(getSupportFragmentManager(), homeFragment, R.id.contentView);
        }

        boolean isConnectedOrConnecting = false;
        if (isConnectedOrConnecting()) {
            isConnectedOrConnecting = true;
        }

        TwitterAPIService twitterAPIService = new TwitterAPIService(
                getString(R.string.TWITTER_CONSUMER_KEY),
                getString(R.string.TWITTER_CONSUMER_SECRET),
                getString(R.string.TWITTER_ACCESS_TOKEN),
                getString(R.string.TWITTER_ACCESS_TOKEN_SECRET)
        );

        //Create the presenter
        new HomePresenter(twitterAPIService, homeFragment, isConnectedOrConnecting);
        setUpResideMenu();
        setUpActionBar();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                Toast.makeText(HomeActivity.this, "Menu item tapped", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            if (!resideMenu.isOpened()) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            } else {
                resideMenu.closeMenu();
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUpResideMenu() {
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        String titles[] = { "Home", "Reservations", "Preferences" };
        int icon[] = { R.drawable.icon_home, R.drawable.icon_calendar, R.drawable.icon_profile };

        for (int i = 0; i < titles.length; i++){
            ResideMenuItem item = new ResideMenuItem(this, icon[i], titles[i]);
            item.setOnClickListener(this);
            resideMenu.addMenuItem(item, ResideMenu.DIRECTION_LEFT);
        }
    }

    private void setUpActionBar() {
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_24dp);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    private boolean isConnectedOrConnecting() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork!= null) {
            return activeNetwork.isConnectedOrConnecting();
        }

        return false;
    }
}
