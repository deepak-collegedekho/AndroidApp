package com.collegedekho.app.listener;

import android.support.v7.widget.SearchView;
import android.view.View;

/**
 * Created by root on 21/6/16.
 */
public class ExamSearchCloseListener implements SearchView.OnCloseListener{

    View mHintView;
    View mNextView;

    public ExamSearchCloseListener(View hint, View nextView){
        this.mHintView = hint;
        this.mNextView = nextView;
    }


    @Override
    public boolean onClose() {
        if(mHintView != null)
        mHintView.setVisibility(View.VISIBLE);
        if(mNextView != null)
        mNextView.setVisibility(View.VISIBLE);
        return false;
    }
}
