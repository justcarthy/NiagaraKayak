package com.niagarakayak.niagarakayakapp.service.twitter;

import android.os.AsyncTask;
import android.util.Log;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * This class contains methods and AsyncTasks to fetch reservations from the Twitter API.
 */

public class TwitterAPIService implements TwitterService {

    /**
     * Twitter object to interact with the Twitter API.
     */
    private Twitter twitter;

    /**
     * Constructor for Twitter API service.
     * You can find all credentials on the Twitter developer console.
     * @param consumer              Consumer key
     * @param consumerSecret        Consumer secret
     * @param accessToken           Access token
     * @param accessTokenSecret     Access token secret
     */
    public TwitterAPIService(String consumer, String consumerSecret, String accessToken, String accessTokenSecret) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(consumer)
                .setOAuthConsumerSecret(consumerSecret)
                .setOAuthAccessToken(accessToken)
                .setOAuthAccessTokenSecret(accessTokenSecret);

        TwitterFactory tf = new TwitterFactory(cb.build());
        twitter = tf.getInstance();
    }

    @Override
    public void loadLastTweet(TwitterCallback callback) {
        new getLastTweetTask().execute(callback);
    }

    private class getLastTweetTask extends AsyncTask<TwitterCallback, Void, Status> {
        private Exception exception;
        private TwitterCallback callback;

        @Override
        protected twitter4j.Status doInBackground(TwitterCallback... callbacks) {
            this.callback = callbacks[0];
            try {
                return twitter.getUserTimeline().get(0);
            } catch (Exception e) {
                this.exception = e;
            }

            return null;
        }

        @Override
        protected void onPostExecute(twitter4j.Status lastTweet) {
            if (exception != null || lastTweet == null) {
                callback.onFailure(exception);
            } else {
                callback.onSuccess(lastTweet);
            }
        }
    }
}
