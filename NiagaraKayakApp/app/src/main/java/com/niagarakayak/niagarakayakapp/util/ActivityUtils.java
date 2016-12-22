package com.niagarakayak.niagarakayakapp.util;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

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

}
