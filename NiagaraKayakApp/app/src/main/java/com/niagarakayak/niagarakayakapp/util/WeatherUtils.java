package com.niagarakayak.niagarakayakapp.util;

public class WeatherUtils {
    public static String getCelciusString(float farenheit) {
        return Math.round((farenheit - 273.15)) + "C";
    }

}
