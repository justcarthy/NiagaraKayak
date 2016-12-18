package com.niagarakayak.niagarakayakapp.home;

import android.support.annotation.NonNull;
import com.niagarakayak.niagarakayakapp.model.Tweet;
import com.niagarakayak.niagarakayakapp.service.twitter.TwitterService;

import java.util.Objects;

public class HomePresenter implements HomeContract.Presenter {

    private final HomeContract.View mHomeView;
    private final TwitterService mTwitterAPI;

    public HomePresenter(@NonNull TwitterService twitterService, @NonNull HomeContract.View mHomeView) {
        this.mTwitterAPI = twitterService;
        this.mHomeView = mHomeView;
        mHomeView.setPresenter(this);
    }

    @Override
    public void start() {
        checkInternet();
    }

    @Override
    public void checkInternet() {
        //TODO: Handle the case where there is no internet!

        // TODO: Check before you call this!
        loadTweetCard();
    }

    @Override
    public void loadTweetCard() {
        // TODO: Actually implement this
        Tweet tweet = mTwitterAPI.getLastTweet();
        Objects.requireNonNull(tweet);
        mHomeView.setTweetDescription(tweet.getText());
        mHomeView.setTweetLabel("Latest tweet from " + tweet.getUserHandle());
    }

    @Override
    public void loadMapCard() {
    }
}
