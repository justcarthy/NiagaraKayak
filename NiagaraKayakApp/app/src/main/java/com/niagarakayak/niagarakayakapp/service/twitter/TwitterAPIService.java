package com.niagarakayak.niagarakayakapp.service.twitter;

import android.util.Base64;
import com.niagarakayak.niagarakayakapp.model.Tweet;
import okhttp3.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;

import static android.net.Uri.encode;

public class TwitterAPIService implements TwitterService {

    OkHttpClient client;

    private String CONSUMER_API_KEY;

    // Just get the latest tweet for now..
    private final int mCount = 1;

    private String mNonce, mSignatureString;
    private long mTimeStamp;
    private String accessTokenString;
    private Request request;


    public TwitterAPIService(String consumer, String accessToken) {
        this.CONSUMER_API_KEY = consumer;
        this.accessTokenString = accessToken;
        this.mNonce = nonceGenerator();
        this.mTimeStamp  = System.currentTimeMillis() / 1000L;

        client = new OkHttpClient();
        String header = createHeader();
        mSignatureString = generateSignature(createSignatureBaseString(mCount, mNonce, mTimeStamp,
                CONSUMER_API_KEY, accessTokenString),
                consumer, accessTokenString);
        request = new Request.Builder()
                .header("Authorization", header)
                .url(TWITTER_USER_TIMELINE_URL)
                .build();
    }

    private String  nonceGenerator (){
        SecureRandom random = new SecureRandom();
        String s = java.util.UUID.randomUUID().toString().replace("-","");
        return s.substring(0,32);
    }

    private String createSignatureBaseString(int mCount, String mNonce, long mTimeStamp,
                                             String CONSUMER_API_KEY, String accessTokenString) {
        StringBuilder builder = new StringBuilder();

        builder.append("count=");
        builder.append(mCount);
        builder.append("&oauth_consumer_key=");
        builder.append(encode(CONSUMER_API_KEY));
        builder.append("&oauth_nonce=");
        builder.append(mNonce);
        builder.append("&oauth_signature_method=HMAC-SHA1&oauth_timestamp=");
        builder.append(mTimeStamp);
        builder.append("&oauth_token=");
        builder.append(encode(accessTokenString));
        builder.append("&oauth_version=");
        builder.append(encode("1.0"));
        builder.append("&since_id=1");

        String intermediateString = encode(builder.toString());

        builder = new StringBuilder();
        builder.append("GET&");
        builder.append(encode(TWITTER_USER_TIMELINE_URL));
        builder.append("&");
        builder.append(intermediateString);

        return builder.toString();
    }

    private String createHeader(){
        StringBuilder builder = new StringBuilder();
        builder.append("OAuth oauth_consumer_key=\"");
        builder.append(encode(CONSUMER_API_KEY));
        builder.append("\", oauth_nonce=\"");
        builder.append(mNonce);
        builder.append("\", oauth_signature=\"");
        builder.append(encode(mSignatureString));
        builder.append("\", oauth_signature_method=\"HMAC-SHA1\", ");
        builder.append("oauth_timestamp=\"");
        builder.append(mTimeStamp);
        builder.append("\", oauth_token=\"");
        builder.append(encode(accessTokenString));
        builder.append("\", oauth_version=\"");
        builder.append(encode("1.0"));
        builder.append("\"");

        return builder.toString();
    }

    private String generateSignature(String signatueBaseStr, String oAuthConsumerSecret, String oAuthTokenSecret) {
        byte[] byteHMAC = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec spec;
            if (null == oAuthTokenSecret) {
                String signingKey = encode(oAuthConsumerSecret) + '&';
                spec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
            } else {
                String signingKey = encode(oAuthConsumerSecret) + '&' + encode(oAuthTokenSecret);
                spec = new SecretKeySpec(signingKey.getBytes(), "HmacSHA1");
            }
            mac.init(spec);
            byteHMAC = mac.doFinal(signatueBaseStr.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        String s = "";
        try {
            s = new String(Base64.encode(byteHMAC, Base64.NO_WRAP), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        };

        return s;

    }

    @Override
    public Tweet getLastTweet() {
        final Tweet tweet = new Tweet();

        //TODO: Make the call and return the tweet.

        return tweet;
    }
}
