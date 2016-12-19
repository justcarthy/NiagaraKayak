package com.niagarakayak.niagarakayakapp.service.twitter;


import twitter4j.Status;

public interface TwitterService {
    void loadLastTweet(TwitterAPIService.TwitterCallback callback);

    interface TwitterCallback {
        void onFailure(Exception e);
        void onSuccess(Status tweet);
    }
}
