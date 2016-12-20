package com.niagarakayak.niagarakayakapp.service.twitter;

import android.os.AsyncTask;
import android.util.Log;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterAPIService implements TwitterService {

    private Twitter twitter;

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
        private TwitterException exception;
        private TwitterCallback callback;

        @Override
        protected twitter4j.Status doInBackground(TwitterCallback... callbacks) {
            this.callback = callbacks[0];
            try {
                return twitter.getUserTimeline().get(0);
            } catch (TwitterException e) {
                this.exception = e;
                Log.d("TWITTER API ERROR", e.getMessage());
                return null;
            }
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
