package com.niagarakayak.niagarakayakapp.add_reservations;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import com.niagarakayak.niagarakayakapp.add_reservations.steps.Step1Fragment;
import com.niagarakayak.niagarakayakapp.add_reservations.steps.Step2Fragment;

public class StepPagerAdapter extends FragmentStatePagerAdapter {

    static final int STEP_ONE = 0;
    static final int STEP_TWO = 1;

    public StepPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case STEP_ONE: {
                return Step1Fragment.newInstance();
            }

            case STEP_TWO: {
                return Step2Fragment.newInstance();
            }

            default:
                return Step1Fragment.newInstance();
        }
    }

    public int getCount() {
        return 2;
    }
}
