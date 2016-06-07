package com.collegedekho.app.listener;

import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;

/**
 * Created by harshvardhan on 24/05/16.
 */

public class CustomTouchDelegate extends TouchDelegate {
    private View mDelegateView;

    /**
     * Constructor
     *
     * @param bounds       Bounds in local coordinates of the containing view that should be mapped to
     *                     the delegate view
     * @param delegateView The view that should receive motion events
     */
    public CustomTouchDelegate(Rect bounds, View delegateView) {
        super(bounds, delegateView);
        mDelegateView = delegateView;
    }

    public CustomTouchDelegate(View delegateView) {
        super(new Rect(), delegateView);
        mDelegateView = delegateView;
    }

    public boolean onTouchEvent(MotionEvent event) {
        return mDelegateView.dispatchTouchEvent(event);
    }
}
