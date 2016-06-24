package com.collegedekho.app.listener;

import android.view.View;

import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.fragment.BaseFragment;

/**
 * Created by sureshsaini on 30/12/15.
 */

public interface OnArticleSelectListener extends BaseFragment.BaseListener {
    void onArticleSelected(Articles article, boolean flag, View view);
    @Override
    void onEndReached(String next, int type);
}