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
    private static final int LENGTH_LONGER = 10000;

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
                int UniSad  =   0x1F61E;
                int UniRain =   0x2614;
                int UniCloud=   0x2601;
                int UniWind =   0x1F343;
                int UniHappy=   0x1F604;
                int UniSun  =   0x1F305;
                if(WeatherUtils.severeConditions(weather)) {
                    mHomeView.showSnackbarWithMessage("Not the best day outside for kayaking "
                            + HomeUtils.getEmojiByUnicode(UniSad) + " "
                            + HomeUtils.getEmojiByUnicode(UniRain) + " "
                            + "\nMaybe tomorrow?", LENGTH_LONGER, WEATHER_COLOR);

                }
                else if(WeatherUtils.mildConditions(weather)){
                    mHomeView.showSnackbarWithMessage("It's a little windy, \nbut that's no reason to stay inside"
                            + HomeUtils.getEmojiByUnicode(UniCloud) + " "
                            + HomeUtils.getEmojiByUnicode(UniWind) + " ",
                            LENGTH_LONGER, WEATHER_COLOR);
                }
                else{
                    mHomeView.showSnackbarWithMessage("Perfect weather outside today! \nCome kayaking!"
                                    + HomeUtils.getEmojiByUnicode(UniHappy) + " "
                                    + HomeUtils.getEmojiByUnicode(UniSun) + " ",
                            LENGTH_LONGER, WEATHER_COLOR);
                }
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
        mHomeView.setTweetDescription("Please Reconnect to see most recent status.");
    }

    @Override
    public void loadMapCard() {
        mHomeView.showMapsLabel();
        mHomeView.showMapsCard();

        loadWeatherBar("St.Catharines");
    }

    public void loadErrorMapCard() {
        // TODO: Implement.
    }


}
