package com.niagarakayak.niagarakayakapp.util;

public class HomeUtils {

    public static String getLocationFromTweet(String tweet) {
       StringBuilder location = new StringBuilder();

        String[] arr = tweet.split(" ");

        int i = 1;

        while (!arr[i].toLowerCase().equals("from")) {
            location.append(arr[i]);
            i++;
        }

        return location.toString();
    }

    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }

}
