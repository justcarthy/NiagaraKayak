package com.niagarakayak.niagarakayakapp.util;

public class TimeUtils {

    public static String get24HrTime(String[] splitTimeText) {
        String[] arr = splitTimeText[0].split(":");
        int hours = Integer.parseInt(arr[0]);
        String minutes = arr[1];

        if (splitTimeText[1].equals("PM")) {
            hours+=12;
        }

        return "" + hours + ":" + minutes;
    }

    public static String get12HrTime(String twentyFourHourTime) {
        String[] arr = twentyFourHourTime.split(":");
        int hours = Integer.parseInt(arr[0]);
        String minutes = arr[1];
        String amOrPm = hours > 12 ? "PM" : "AM";
        hours -= hours > 12 ? 12 : 0;
        return "" + hours + ":" + minutes + " " + amOrPm;
    }

}
