package com.collegedekho.app.listener;

import android.view.View;

import com.collegedekho.app.entities.News;
import com.collegedekho.app.fragment.BaseFragment;

/**
 * Created by sureshsaini on 30/12/15.
 */
public interface OnNewsSelectListener extends BaseFragment.BaseListener {
    void onNewsSelected(News news, boolean flag, View view);

    @Override
    void onEndReached(String next, int type);
}
