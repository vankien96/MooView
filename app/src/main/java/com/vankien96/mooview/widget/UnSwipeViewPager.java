package com.vankien96.mooview.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Admin on 12/12/2017.
 */

public class UnSwipeViewPager extends ViewPager {

    private boolean enable;

    public UnSwipeViewPager(Context context) {
        super(context);
    }

    public UnSwipeViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.enable) {
            return super.onTouchEvent(ev);
        }
        return false;
    }

    @Override
    public boolean performClick() {
        super.performClick();
        return true;
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item, false);
    }
}
