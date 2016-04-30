package com.collegedekho.app.display;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by harshvardhan on 26/04/16.
 */
public class CustomViewPager extends ViewPager {
    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        try {
            super.draw(canvas);
        } catch (NullPointerException e) {
            Log.d("CustomViewPager", "Null Pointer Exception skipped");
        }
    }
}
