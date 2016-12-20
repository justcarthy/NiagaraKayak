package com.niagarakayak.niagarakayakapp.home;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import com.niagarakayak.niagarakayakapp.R;
import com.niagarakayak.niagarakayakapp.model.Weather;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterService;
import com.niagarakayak.niagarakayakapp.service.weather.OpenWeatherAPIService;
import com.niagarakayak.niagarakayakapp.service.weather.WeatherService;
import com.niagarakayak.niagarakayakapp.util.HomeUtils;
import com.niagarakayak.niagarakayakapp.util.WeatherUtils;
import twitter4j.Status;
import twitter4j.TwitterException;

import static android.support.design.widget.Snackbar.*;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.ERROR_COLOR;
import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.WEATHER_COLOR;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mHomeView;
    private final TwitterService mTwitterAPI;
    private final OpenWeatherAPIService mWeatherService;
    private final boolean isConnected;

    public HomePresenter(@NonNull TwitterAPIService twitterAPIService, @NonNull OpenWeatherAPIService openWeatherAPIService,
                         @NonNull HomeContract.View mHomeView, boolean hasInternet) {
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
                float temp = weather.temperature.getTemp();
                mHomeView.showSnackbarWithMessage(weather.currentCondition.getCondition(), LENGTH_LONG, WEATHER_COLOR);
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

                mHomeView.setTweetLabel("Recent tweet from ");
                mHomeView.setTweetHandle("@" + lastTweet.getUser().getScreenName());
                mHomeView.setTweetDescription(tweetText);

                loadMapCard();
            }
        });
    }

    public void loadErrorTweetCard() {
        mHomeView.setTweetLabel("Error");
        mHomeView.setTweetDescription("St.Catharines");
    }

    @Override
    public void loadMapCard() {
        mHomeView.showMapsLabel();
        mHomeView.showMapsCard();
    }

    public void loadErrorMapCard() {
        // TODO: Implement.
    }
}
