package com.niagarakayak.niagarakayakapp.util;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import com.niagarakayak.niagarakayakapp.R;

public class SnackbarUtils {
    public static enum SnackbarColor {
        ERROR_COLOR,
        SUCCESS_COLOR,
        WEATHER_COLOR
    }

    public static int getBackgroundColor(Context context, SnackbarColor color) {
        switch (color) {
            case ERROR_COLOR:
                return ContextCompat.getColor(context, R.color.error_snackbar);
            case SUCCESS_COLOR:
                return ContextCompat.getColor(context, R.color.success_snackbar);
            case WEATHER_COLOR:
                return ContextCompat.getColor(context, R.color.weather_snackbar);
            default:
                return ContextCompat.getColor(context, R.color.error_snackbar);
        }
    }



}