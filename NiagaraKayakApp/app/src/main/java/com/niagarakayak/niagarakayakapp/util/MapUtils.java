package com.niagarakayak.niagarakayakapp.util;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapUtils {
    private static final String[] LOCATIONS = {
        "charles daley park", "queenston", "niagara on the lake"
    };

    private static final LatLng[] LOCATION_COORDS = {
            // Charles daley
            new LatLng(43.1815012, -79.3236707),
            // Queenston
            new LatLng(43.165492, -79.052310),
            // Niagara-on-the-lake
            new LatLng(43.255049, -79.062405)
    };

    public static LatLng getLocationFromTweet(String tweetString) {
        // We only have a set of defined points to deal with:
        // Logic is simple, check if the tweet text contains
        // Any of the launch point words, return the first match.
        return getLocation(tweetString);
    }

    public static LatLng getLocation(String locationString) {
        locationString = locationString.toLowerCase().replace("-", " ");

        for (int i = 0; i < LOCATIONS.length; i++) {
            if (locationString.contains(LOCATIONS[i])) {
                return LOCATION_COORDS[i];
            }
        }

        return new LatLng(0, 0);
    }

    public static Marker addMarkerToMap(GoogleMap map, LatLng coords, String title, String snippet) {
        Marker markerToAdd = map.addMarker(new MarkerOptions().position(coords));
        markerToAdd.setTitle(title);
        markerToAdd.setSnippet(snippet);
        return markerToAdd;
    }

}
