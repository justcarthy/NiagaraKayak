package com.niagarakayak.niagarakayakapp.util;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fm, Fragment frag, int frameId) {
        Objects.requireNonNull(fm);
        Objects.requireNonNull(frag);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(frameId, frag);
        transaction.commit();
    }

    public static void showSnackbarWithMessage(View view, String message, int length, SnackbarUtils.SnackbarColor snackbarColor) {
        Snackbar sb = Snackbar.make(view, message, length);
        sb.getView().setBackgroundColor(SnackbarUtils.getBackgroundColor(view.getContext(), snackbarColor));
        sb.show();
    }

    public static boolean isConnectedOrConnecting(AppCompatActivity activity) {
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

}
