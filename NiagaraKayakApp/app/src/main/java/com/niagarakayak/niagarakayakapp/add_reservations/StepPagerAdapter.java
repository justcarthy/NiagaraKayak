package com.niagarakayak.niagarakayakapp.add_reservations;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import com.niagarakayak.niagarakayakapp.add_reservations.steps.Step1Fragment;
import com.niagarakayak.niagarakayakapp.add_reservations.steps.Step2Fragment;
import com.niagarakayak.niagarakayakapp.add_reservations.steps.Step3Fragment;

public class StepPagerAdapter extends FragmentStatePagerAdapter {

    static final int STEP_ONE = 0;
    static final int STEP_TWO = 1;
    static final int STEP_THREE = 2;

    private Fragment mCurrentFragment;

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

            case STEP_THREE: {
                return Step3Fragment.newInstance();
            }

            default:
                return Step1Fragment.newInstance();
        }
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = (Fragment) object;
        }
        super.setPrimaryItem(container, position, object);
    }

    public int getCount() {
        return 3;
    }
}
