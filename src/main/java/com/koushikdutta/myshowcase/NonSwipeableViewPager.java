package com.koushikdutta.myshowcase;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NonSwipeableViewPager extends ViewPager {

    public NonSwipeableViewPager(Context context) {
        super(context);
    }

    public NonSwipeableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        if (!allowSwiping)
            return false;
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Never allow swiping to switch between pages
        if (!allowSwiping)
            return false;
        return super.onTouchEvent(event);
    }

    private boolean allowSwiping;

    public void allowSwiping(boolean allowSwiping) {
        this.allowSwiping = allowSwiping;
    }
}