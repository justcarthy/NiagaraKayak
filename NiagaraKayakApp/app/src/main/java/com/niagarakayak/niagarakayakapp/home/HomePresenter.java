package com.niagarakayak.niagarakayakapp.home;

import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterAPIService;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterService;
import twitter4j.Status;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mHomeView;
    private final TwitterService mTwitterAPI;
    private final boolean isConnected;

    public HomePresenter(@NonNull TwitterAPIService twitterAPIService, @NonNull HomeContract.View mHomeView,
                         boolean hasInternet) {
        this.mTwitterAPI = twitterAPIService;
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
            mHomeView.showSnackbarWithMessage("No internet connection found!", Snackbar.LENGTH_LONG);
            loadErrorTweetCard();
        }
    }

    @Override
    public void loadTweetCard() {
        mTwitterAPI.loadLastTweet(new TwitterService.TwitterCallback() {
            @Override
            public void onFailure(Exception e) {
                mHomeView.showSnackbarWithMessage("Failed to load most recent tweet", Snackbar.LENGTH_SHORT);
                loadErrorTweetCard();
            }

            @Override
            public void onSuccess(Status lastTweet) {
                mHomeView.setTweetLabel("Recent tweet from ");
                mHomeView.setTweetHandle("@" + lastTweet.getUser().getScreenName());
                mHomeView.setTweetDescription(lastTweet.getText());
                // TODO: Show location on Map.
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
