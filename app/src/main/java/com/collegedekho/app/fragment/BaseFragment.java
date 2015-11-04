package com.collegedekho.app.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.News;

/**
 * Created by root on 16/9/15.
 */
public class BaseFragment extends Fragment implements View.OnClickListener{

    protected static final String ARG_TITLE = "title";
    protected static final String ARG_NEXT = "next";
    protected String TAG ="BaseFragment";
    protected int pastVisiblesItems, visibleItemCount, totalItemCount;
    protected boolean loading = false;
    protected LinearLayout progressBarLL;
    protected LinearLayoutManager layoutManager;
    protected String mNextUrl;
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
                if(mNextUrl != null) {
                    progressBarLL.setVisibility(View.VISIBLE);
                }
                listener.onEndReached(mNextUrl,listType);
            }
            /*if(dy < 0)
            {
                mProgressBarLL.setVisibility(View.GONE);
            }*/
        }

    };

    @Override
    public void onClick(View v) {
    }

    public void updateNews(News news) {
            }
    public void updateArticle(Articles article) {
    }

    public interface BaseListener{
        void onEndReached(String next, int type);
        void onFilterButtonClicked();
        void onFilterApplied();
    }

}
