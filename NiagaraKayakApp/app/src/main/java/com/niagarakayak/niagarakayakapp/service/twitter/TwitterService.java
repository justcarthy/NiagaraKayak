package com.niagarakayak.niagarakayakapp.service.twitter;


import twitter4j.Status;

public interface TwitterService {
    final String TWITTER_HOME_TIMELINE_URL = "https://api.twitter.com/1.1/statuses/user_timeline.json";

    void loadLastTweet(TwitterAPIService.TwitterCallback callback);

    interface TwitterCallback {
        void onFailure(Exception e);
        void onSuccess(Status tweet);
    }
}
