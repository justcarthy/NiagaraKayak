package com.niagarakayak.niagarakayakapp.service.twitter;

import com.niagarakayak.niagarakayakapp.model.Tweet;

public interface TwitterService {
    final String TWITTER_USER_TIMELINE_URL = "https://api.twitter.com/1.1/statuses/home_timeline.json";

    Tweet getLastTweet();
}
