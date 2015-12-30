package com.collegedekho.app.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;

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
    protected  static int EXAM_TAB_POSITION =0;


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

                if(mNextUrl != null && progressBarLL != null)
                    progressBarLL.setVisibility(View.VISIBLE);

                if(listener != null)
                    listener.onEndReached(mNextUrl,listType);
            }
            /*if(dy < 0)
            {
                mProgressBarLL.setVisibility(View.GONE);
            }*/
        }

    };

    protected void updateViewTypeIcon(View view, int type)
    {
        if(view == null)return;
        if(type == Constants.VIEW_INTO_GRID) {
            ((ImageView) view.findViewById(R.id.view_into_grid_image)).setColorFilter(getResources().getColor(R.color.primaryColor));
            ((ImageView) view.findViewById(R.id.view_into_list_image)).setColorFilter(getResources().getColor(R.color.bg_category_item_unselected));
        }
        else
        {
            ((ImageView) view.findViewById(R.id.view_into_grid_image)).setColorFilter(getResources().getColor(R.color.bg_category_item_unselected));
            ((ImageView) view.findViewById(R.id.view_into_list_image)).setColorFilter(getResources().getColor(R.color.primary_color));
        }
    }


    @Override
    public void onClick(View v) {
    }

    public void updateNews(News news) {}
    public void updateArticle(Articles article) {}
    public void updateLikeButtons(int position) {}
    public void instituteQnAQuestionAdded(QnAQuestions q){}

    public void updateExamSummary(ExamSummary examSummary) {

    }

    public interface BaseListener{
        void onEndReached(String next, int type);

        void onFilterButtonClicked();

        void onFilterApplied();

        void onInstituteLikedDisliked(int position, int liked);
    }

}
