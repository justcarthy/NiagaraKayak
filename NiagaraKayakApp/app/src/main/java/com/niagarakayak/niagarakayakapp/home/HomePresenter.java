package com.niagarakayak.niagarakayakapp.home;

import android.support.annotation.NonNull;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.niagarakayak.niagarakayakapp.model.Weather;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterService;
import com.niagarakayak.niagarakayakapp.service.weather.OpenWeatherAPIService;
import com.niagarakayak.niagarakayakapp.service.weather.WeatherService;
import com.niagarakayak.niagarakayakapp.util.HomeUtils;
import com.niagarakayak.niagarakayakapp.util.WeatherUtils;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.util.Calendar;
import java.util.Date;

import static android.support.design.widget.Snackbar.*;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.LENGTH_LONGER;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.ERROR_COLOR;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.WEATHER_COLOR;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mHomeView;
    private final TwitterService mTwitterAPI;
    private final OpenWeatherAPIService mWeatherService;
    private final boolean isConnected;

    public HomePresenter(@NonNull TwitterAPIService twitterAPIService,
                         @NonNull OpenWeatherAPIService openWeatherAPIService,
                         @NonNull HomeContract.View mHomeView, boolean isConnected) {
        this.mTwitterAPI = twitterAPIService;
        this.mWeatherService = openWeatherAPIService;
        this.mHomeView = mHomeView;
        this.isConnected = isConnected;
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {
        checkInternet();
    }

    @Override
    public void checkInternet() {
        if (isConnected) {
            loadTweetCard();
        } else {
            mHomeView.showSnackbarWithMessage("No internet connection found!", LENGTH_LONG, ERROR_COLOR);
            loadErrorTweetCard();
        }
    }

    private void loadWeatherBar(String city) {
        mWeatherService.fetchWeather(new WeatherService.WeatherServiceRequest(city,
                new WeatherService.WeatherCallback() {
            @Override
            public void onFailure() {
                mHomeView.showSnackbarWithMessage("Couldn't fetch the weather!'", LENGTH_LONG, ERROR_COLOR);
            }

            @Override
            public void onSuccess(Weather weather) {
                mHomeView.showSnackbarWithMessage(WeatherUtils.getWeatherString(weather), LENGTH_LONGER, WEATHER_COLOR);
            }
        }));
    }

    @Override
    public void loadTweetCard() {
        mTwitterAPI.loadLastTweet(new TwitterService.TwitterCallback() {
            @Override
            public void onFailure(TwitterException e) {
                mHomeView.showSnackbarWithMessage("Failed to fetch most recent tweet", LENGTH_SHORT, ERROR_COLOR);
                loadErrorTweetCard();
            }

            @Override
            public void onSuccess(Status lastTweet) {
                String tweetText = lastTweet.getText();
                Date tweetDate = lastTweet.getCreatedAt();
                mHomeView.setTweetLabel("Last tweet from ");
                mHomeView.setTweetHandle("@" + lastTweet.getUser().getScreenName());
                mHomeView.setTweetDescription(tweetText);
                mHomeView.setTweetDate(tweetDate.toString());
                LatLng coords = HomeUtils.getLocationFromTweet(tweetText);
                loadWeatherBar("St.Catharines");
                loadMapCard(tweetDate, coords);
            }
        });
    }

    public void loadErrorTweetCard() {
        mHomeView.setTweetLabel("Error");
        mHomeView.setTweetDescription("Please reconnect to see most recent status.");
    }

    @Override
    public void loadMapCard(Date tweetDate, LatLng coords) {
        Calendar tweetCal = Calendar.getInstance();
        Calendar todaysCal = Calendar.getInstance();
        tweetCal.setTime(tweetDate);
        todaysCal.setTime(new Date());

        String mapsLabelText = "Here's where we were last";

        if (tweetCal.get(Calendar.DAY_OF_YEAR) == todaysCal.get(Calendar.DAY_OF_YEAR)) {
            // The tweet was today
            mapsLabelText = "Here's where we are for today";
        }

        mHomeView.setMapsLabel(mapsLabelText);
        mHomeView.showMapsLabel();
        mHomeView.showMapsCardWithCoords(coords);
    }
}