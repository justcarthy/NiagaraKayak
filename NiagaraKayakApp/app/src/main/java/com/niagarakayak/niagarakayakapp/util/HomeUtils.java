package com.niagarakayak.niagarakayakapp.util;

import com.google.android.gms.maps.model.LatLng;

public class HomeUtils {
    private static final String[] LOCATIONS = {
        "charles daley park", "niagara on the lake", "queenston"
    };

    private static final LatLng[] LOCATION_COORDS = {
            // Charles daley
            new LatLng(43.1815012, -79.3236707),
            // Queenston (Niagara on the lake)
            new LatLng(43.2016179,-79.122569)
    };

    public static LatLng getLocationFromTweet(String tweet) {
        // We only have a set of defined points to deal with:
        // Logic is simple, check if the tweet text contains
        // Any of the launch point words, return the first match.
        tweet = tweet.toLowerCase().replace("-", " ");

        for (int i = 0; i < LOCATIONS.length; i++) {
            if (tweet.contains(LOCATIONS[i])) {
                return LOCATION_COORDS[i];
            }
        }

        return null;
    }

    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
