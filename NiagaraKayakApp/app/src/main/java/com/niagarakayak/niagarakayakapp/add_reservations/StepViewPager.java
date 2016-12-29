package com.niagarakayak.niagarakayakapp.add_reservations;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Custom viewpager to enable and disable swiping programmatically.
 */
public class StepViewPager extends ViewPager {

    private boolean enabled;

    public StepViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.enabled = false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return this.enabled && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return this.enabled && super.onInterceptTouchEvent(ev);

    }

    public void setEnabled(boolean state) {
        this.enabled = state;
    }
}