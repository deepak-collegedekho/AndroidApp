package com.collegedekho.app.resource;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;

import com.collegedekho.app.R;
import com.collegedekho.app.utils.Utils;

public class ScrollingFABBehavior extends CoordinatorLayout.Behavior<FloatingActionButton> {
    private int toolbarHeight;
    private int  mFabMenuMargin;

    public ScrollingFABBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = Utils.getToolbarHeight(context);
        this.mFabMenuMargin = context.getResources().getDimensionPixelSize(R.dimen.m60dp);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, FloatingActionButton fab, View dependency) {
        if (dependency instanceof AppBarLayout) {
                CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) fab.getLayoutParams();
               // int fabBottomMargin = lp.bottomMargin;
                int distanceToScroll = fab.getHeight() + mFabMenuMargin;
                float ratio = (float)dependency.getY()/(float)toolbarHeight;
                fab.setTranslationY(-distanceToScroll * ratio);
        }
        return true;
    }
}