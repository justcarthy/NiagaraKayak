package com.niagarakayak.niagarakayakapp.home;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.View;
import com.google.android.gms.maps.model.LatLng;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterService;
import com.niagarakayak.niagarakayakapp.util.ActivityUtils;
import com.niagarakayak.niagarakayakapp.util.MapUtils;
import twitter4j.Status;

import java.util.Calendar;
import java.util.Date;

import static android.support.design.widget.Snackbar.*;

import static com.niagarakayak.niagarakayakapp.util.SnackbarUtils.SnackbarColor.ERROR_COLOR;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mHomeView;
    private final TwitterService mTwitterAPI;
    private final boolean isConnected;
    private boolean twitterLoaded;

    public HomePresenter(@NonNull TwitterAPIService twitterAPIService,
                         @NonNull HomeContract.View mHomeView, boolean isConnected) {
        this.mTwitterAPI = twitterAPIService;
        this.mHomeView = mHomeView;
        this.isConnected = isConnected;
        mHomeView.setPresenter(this);
        twitterLoaded = false;
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
            ActivityUtils.showSnackbarWithMessage(((Fragment) mHomeView).getView(),
                    "No internet connection found!", LENGTH_LONG, ERROR_COLOR);
            loadErrorTweetCard();
            mHomeView.showRetryButton(true);
        }
    }

    @Override
    public void loadTweetCard() {
        mTwitterAPI.loadLastTweet(new TwitterService.TwitterCallback() {
            @Override
            public void onFailure(Exception e) {
                ActivityUtils.showSnackbarWithMessage(((Fragment) mHomeView).getView(),
                        "Failed to fetch most recent tweet", LENGTH_SHORT, ERROR_COLOR);
                loadErrorTweetCard();
            }

            @Override
            public void onSuccess(Status lastTweet) {
                twitterLoaded = true;
                String tweetText = lastTweet.getText();
                Date tweetDate = lastTweet.getCreatedAt();
                mHomeView.setTweetLabel("Last tweet from ");
                mHomeView.setTweetHandle("@" + lastTweet.getUser().getScreenName());
                mHomeView.setTweetDescription(tweetText);
                mHomeView.setTweetDate(tweetDate.toString());
                LatLng coords = MapUtils.getLocationFromTweet(tweetText);
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
        int dayOfTweet = tweetCal.get(Calendar.DAY_OF_YEAR);
        int today = todaysCal.get(Calendar.DAY_OF_YEAR);
        mHomeView.setMapsLabel(dayOfTweet == today ? "Here's where are for today" : "Here's where we were last");
        mHomeView.showMapsLabel();
        mHomeView.showMapsCardWithCoords(coords);
        mHomeView.showRetryButton(false);
    }

    @Override
    public boolean hasTwitterLoaded() {
        return twitterLoaded;
    }
}