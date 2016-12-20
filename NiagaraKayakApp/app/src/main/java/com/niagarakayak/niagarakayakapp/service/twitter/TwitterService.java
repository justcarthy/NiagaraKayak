package com.niagarakayak.niagarakayakapp.service.twitter;


import twitter4j.Status;
import twitter4j.TwitterException;

public interface TwitterService {
    void loadLastTweet(TwitterAPIService.TwitterCallback callback);

    interface TwitterCallback {
        void onFailure(TwitterException e);
        void onSuccess(Status tweet);
    }
}
