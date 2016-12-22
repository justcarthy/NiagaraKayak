package com.niagarakayak.niagarakayakapp.util;

import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import java.util.Objects;

public class ActivityUtils {

    public static void addFragmentToActivity(FragmentManager fm, Fragment frag, int frameId) {
        Objects.requireNonNull(fm);
        Objects.requireNonNull(frag);
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.add(frameId, frag);
        transaction.commit();
    }

    public static void replaceFragmentInActivity(FragmentManager fm, Fragment replacement, int frameId) {
        Objects.requireNonNull(fm);
        Objects.requireNonNull(replacement);

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(frameId, replacement);
        transaction.commit();
    }

    public static void showSnackbarWithMessage(View view, String message, int length, SnackbarUtils.SnackbarColor snackbarColor) {
        Snackbar sb = Snackbar.make(view, message, length);
        sb.getView().setBackgroundColor(SnackbarUtils.getBackgroundColor(view.getContext(), snackbarColor));
        sb.show();
    }

}
