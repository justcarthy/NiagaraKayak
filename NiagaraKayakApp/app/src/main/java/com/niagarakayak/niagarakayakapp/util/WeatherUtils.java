package com.niagarakayak.niagarakayakapp.util;

import com.niagarakayak.niagarakayakapp.model.Weather;

public class WeatherUtils {
    public static String getCelciusString(float farenheit) {
        return Math.round((farenheit - 273.15)) + "C";
    }

    public static Boolean severeConditions(Weather weather){
        int conditionID = weather.currentCondition.getWeatherId();
        float temp = weather.temperature.getTemp();
        return (conditionID < 800 ||
                conditionID == 900 ||
                conditionID == 901 ||
                conditionID == 902 ||
                conditionID == 906 ||
                conditionID >  957 ||
                temp < 285);
    }

    public static Boolean mildConditions(Weather weather){
        int conditionID = weather.currentCondition.getWeatherId();
        float temp = weather.temperature.getTemp();
        return (conditionID == 903 ||
                conditionID == 905 ||
                conditionID == 955 ||
                conditionID == 956 ||
                conditionID == 95);
    }



}
