package com.niagarakayak.niagarakayakapp.util;

import com.niagarakayak.niagarakayakapp.model.Weather;

public class WeatherUtils {

    // Unicode symbols for different emojis
    private static final int UNI_SAD  =   0x1F61E;
    private static final int UNI_HAPPY =   0x1F604;
    private static final int UNI_SUN =   0x1F305;
    private static final int UNI_RAIN =   0x2614;
    private static final int UNI_CLOUD =   0x2601;
    private static final int UNI_WIND =   0x1F343;

    public enum WEATHER_TYPE {
        SEVERE,
        MILD,
        GOOD
    }

    /**
     * Helper method to return the type of weather
     * @param weather   Weather data
     * @return SEVERE, MILD, or GOOD based on conditions.
     */
    private static WEATHER_TYPE getWeatherType(Weather weather) {
        int conditionID = weather.currentCondition.getWeatherId();
        float temp = weather.temperature.getTemp();

        if (conditionID < 800 || temp < 285) {
            return WEATHER_TYPE.SEVERE;
        }

        switch (conditionID) {
            case 900:
            case 901:
            case 902:
            case 906:
            case 957:
                return WEATHER_TYPE.SEVERE;
            case 903:
            case 905:
            case 955:
            case 956:
            case 95:
                return WEATHER_TYPE.MILD;
            default:
                return WEATHER_TYPE.GOOD;
        }
    }

    /**
     * Gets the corresponding weather string based on the type of weather.
     * @param weather   Weather data
     * @return  String for Snackbar
     */
    public static String getWeatherString(Weather weather) {
        WEATHER_TYPE type = getWeatherType(weather);

        switch (type) {
            case SEVERE:
                return "Not the best day outside for kayaking "
                        + getEmojiByUnicode(UNI_SAD) + " "
                        + getEmojiByUnicode(UNI_RAIN) + " "
                        + "\nMaybe tomorrow?";
            case MILD:
                return "It's a little windy, \nbut that's no reason to stay inside!"
                        + getEmojiByUnicode(UNI_CLOUD) + " "
                        + getEmojiByUnicode(UNI_WIND) + " ";
            default:
                return "Perfect weather outside today! \nCome kayaking!"
                        + getEmojiByUnicode(UNI_HAPPY) + " "
                        + getEmojiByUnicode(UNI_SUN) + " ";
        }
    }

    public static String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
}
