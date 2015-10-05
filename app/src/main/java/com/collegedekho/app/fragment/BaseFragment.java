package com.collegedekho.app.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.LinearLayout;

/**
 * Created by root on 16/9/15.
 */
public class BaseFragment extends Fragment {

    protected static final String ARG_TITLE = "title";
    protected static final String ARG_NEXT = "next";
    protected String TAG ="BaseFragment";
    protected int pastVisiblesItems, visibleItemCount, totalItemCount;
    protected boolean loading = false;
    protected LinearLayout progressBarLL;
    protected LinearLayoutManager layoutManager;
    protected String nextUrl;
    protected BaseListener listener;
    protected  int listType;


    RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            visibleItemCount = layoutManager.getChildCount();
            totalItemCount = layoutManager.getItemCount();
            pastVisiblesItems = layoutManager.findFirstVisibleItemPosition();

            if ((!loading) && ((visibleItemCount + pastVisiblesItems) >= totalItemCount)) {
                loading = true;
                if(nextUrl != null) {
                    progressBarLL.setVisibility(View.VISIBLE);
                }
                listener.onEndReached(nextUrl,listType);
            }
            /*if(dy < 0)
            {
                mProgressBarLL.setVisibility(View.GONE);
            }*/
        }

    };
    public interface BaseListener{
        void onEndReached(String next, int type);
        void onFilterButtonClicked();
    }
   /* @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (sDisableExitAnimation) {
            Animation a = new Animation() {};
            a.setDuration(0);
            return a;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }*/
}
