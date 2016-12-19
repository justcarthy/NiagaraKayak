package com.niagarakayak.niagarakayakapp.home;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import com.niagarakayak.niagarakayakapp.model.Weather;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterService;
import com.niagarakayak.niagarakayakapp.service.weather.OpenWeatherAPIService;
import com.niagarakayak.niagarakayakapp.service.weather.WeatherService;
import twitter4j.Status;

import static android.support.design.widget.Snackbar.*;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.*;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mHomeView;
    private final TwitterService mTwitterAPI;
    private final OpenWeatherAPIService mWeatherService;
    private final boolean isConnected;

    public HomePresenter(@NonNull TwitterAPIService twitterAPIService,
                         OpenWeatherAPIService openWeatherAPIService, @NonNull HomeContract.View mHomeView,
                         boolean hasInternet) {
        this.mTwitterAPI = twitterAPIService;
        this.mWeatherService = openWeatherAPIService;
        this.mHomeView = mHomeView;
        this.isConnected = hasInternet;
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
            mHomeView.showSnackbarWithMessage("No internet connection found!", LENGTH_LONG, WEATHER_COLOR);
            loadErrorTweetCard();
        }
    }

    private void loadWeatherBar() {
        // TODO: Get Niagara Kayaks location from Google Maps.
        mWeatherService.fetchWeather(new WeatherService.WeatherServiceRequest("St.Catharines",
                new WeatherService.WeatherCallback() {
            @Override
            public void onFailure() {
                mHomeView.showSnackbarWithMessage("Couldn't fetch the weather", LENGTH_SHORT, ERROR_COLOR);
            }

            @Override
            public void onSuccess(Weather weather) {
                // TODO: Display the weather better here
                float temp = weather.temperature.getTemp();
                mHomeView.showSnackbarWithMessage(weather.currentCondition.getCondition(), LENGTH_SHORT, WEATHER_COLOR);
            }
        }));
    }

    @Override
    public void loadTweetCard() {
        mTwitterAPI.loadLastTweet(new TwitterService.TwitterCallback() {
            @Override
            public void onFailure(Exception e) {
                mHomeView.showSnackbarWithMessage("Failed to load most recent tweet", LENGTH_SHORT, ERROR_COLOR);
                loadErrorTweetCard();
            }

            @Override
            public void onSuccess(Status lastTweet) {
                mHomeView.setTweetLabel("Recent tweet from ");
                mHomeView.setTweetHandle("@" + lastTweet.getUser().getScreenName());
                mHomeView.setTweetDescription(lastTweet.getText());

                loadWeatherBar();
                loadMapCard();
            }
        });
    }

    public void loadErrorTweetCard() {
        mHomeView.setTweetLabel("Error");
        mHomeView.setTweetDescription("Failed to recent Tweet");
    }

    @Override
    public void loadMapCard() {
    }

    public void loadErrorMapCard() {
        // TODO: Implement.
    }
}
