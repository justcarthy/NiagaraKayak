package com.niagarakayak.niagarakayakapp.util;

import com.google.android.gms.maps.model.LatLng;

public class MapUtils {
    private static final String[] LOCATIONS = {
        "charles daley park", "queenston"
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
        return getLocation(tweet);
    }

    public static LatLng getLocation(String location) {
        location = location.toLowerCase().replace("-", " ");

        for (int i = 0; i < LOCATIONS.length; i++) {
            if (location.contains(LOCATIONS[i])) {
                return LOCATION_COORDS[i];
            }
        }

        return new LatLng(0, 0);
    }

    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
