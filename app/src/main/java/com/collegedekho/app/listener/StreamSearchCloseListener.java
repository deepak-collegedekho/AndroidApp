package com.collegedekho.app.listener;

import android.support.v7.widget.SearchView;
import android.view.View;

/**
 * Created by root on 21/6/16.
 */
public class StreamSearchCloseListener implements SearchView.OnCloseListener {

    View hint;

    public StreamSearchCloseListener(View hint){
        this.hint = hint;
    }

    @Override
    public boolean onClose() {
        hint.setVisibility(View.VISIBLE);
        return false;
    }
}
