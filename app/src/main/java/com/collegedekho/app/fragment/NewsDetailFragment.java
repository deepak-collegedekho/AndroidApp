
package com.collegedekho.app.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.NewsListAdapter;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class NewsDetailFragment extends BaseFragment {
    private static final String ARG_NEWS = "short_name";
    private static final String TAG = "NewsDetailFragment";
    private static final String ARG_NEWSLIST = "similar_news";

    private News mNews;
    private ArrayList<News> mNewsList;
    private View rootView;

    public NewsDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param news A News object to be displayed.
     * @return A new instance of fragment InstituteOverviewFragment.
     */

    public static NewsDetailFragment newInstance(News news , List<News> newsList) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWS, news);
        args.putParcelableArrayList(ARG_NEWSLIST, (ArrayList<News>)newsList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           this.mNews = getArguments().getParcelable(ARG_NEWS);
           this.mNewsList = getArguments().getParcelableArrayList(ARG_NEWSLIST);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        this.setUserVisibleHint(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news_detail, container, false);
        showNewsUpdate();
        return rootView;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    public void updateNews(News news) {
        this.mNews = news;
        showNewsUpdate();
    }

    private void showNewsUpdate()
    {
        if(rootView == null) return;
        try {
            String response= new String(mNews.title.getBytes("ISO-8859-1"),"UTF-8");
            ((TextView) rootView.findViewById(R.id.textview_news_title)).setText(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ((TextView) rootView.findViewById(R.id.textview_news_title)).setText(mNews.title);
        }

        if(mNews.getNews_source()==2)
            ((TextView) rootView.findViewById(R.id.textview_news_content)).setText(Html.fromHtml(mNews.content));
        else
            Utils.renderHtml(getActivity(), (LinearLayout)rootView.findViewById(R.id.news_content_layout), mNews.content);


        if (mNews.image != null && !mNews.image.isEmpty()) {
            NetworkImageView newsImageView = (NetworkImageView) rootView.findViewById(R.id.image_news_expanded);
            newsImageView.setImageUrl(mNews.image, MySingleton.getInstance(getActivity()).getImageLoader());
            newsImageView.setDefaultImageResId(R.drawable.ic_default_image);
            newsImageView.setErrorImageResId(R.drawable.ic_default_image);
        } else
            rootView.findViewById(R.id.image_news_expanded).setVisibility(View.GONE);
        String d = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(mNews.published_on);
            sdf.applyPattern("MMMM d, yyyy KK:mm a");
            d = sdf.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + mNews.published_on);
//                Utils.sendException(t, TAG, "DateFormatUnknown", r.getAddedOn());
        }
        ((TextView) rootView.findViewById(R.id.textview_news_pubdate)).setText(d);

        ArrayList<News> similarNews =  new ArrayList<>();
        ArrayList<String> relatedNewsIds = mNews.getSimilarNewsIds();
        if (relatedNewsIds != null)
        {
            for (int i = 0; i < relatedNewsIds.size(); i++) {
                for (int j = 0; j < mNewsList.size(); j++) {
                    News tempObj = mNewsList.get(j);
                    if (tempObj == null) continue;
                    if (relatedNewsIds.get(i).equalsIgnoreCase(Integer.toString(tempObj.getId())))
                    {
                        similarNews.add(tempObj);
                        break;
                    }
                }
            }
        }
        if(similarNews.size() <= 0 ) {

           rootView.findViewById(R.id.similar_newsTV).setVisibility(View.GONE);
           rootView.findViewById(R.id.divider_similar_news).setVisibility(View.GONE);
           rootView.findViewById(R.id.related_news_list).setVisibility(View.GONE);
        }
        else {
            ScrollView scrollVIew = (ScrollView) rootView.findViewById(R.id.parentScrollView);
            scrollVIew.scrollTo(0, 0);
            scrollVIew.smoothScrollTo(0, 0);

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.related_news_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            NewsListAdapter mAdapter = new NewsListAdapter(getActivity(), similarNews, Constants.VIEW_INTO_GRID);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());

           /* StackView stackView = (StackView)rootView.findViewById(R.id.similar_news_stack);
            NewsStackAdapter adapt = new NewsStackAdapter(getActivity(), R.layout.card_news_grid_view, similarNews);
            stackView.setAdapter(adapt);*/
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser) {
            MainActivity mMainActivity = (MainActivity) this.getActivity();
            if (mMainActivity != null)
                mMainActivity.currentFragment = this;
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        Uri val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_NEWS_LIST + "/personalize/news/" + this.mNews.getId());
        AnalyticsUtils.AppIndexingView("CollegeDekho - News - " + this.mNews.title, val, val, (MainActivity) this.getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();
        Uri val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_NEWS_LIST + "/personalize/news/" + this.mNews.getId());
        AnalyticsUtils.AppIndexingView("CollegeDekho - News - " + this.mNews.title, val, val, (MainActivity) this.getActivity(), false);
    }
}


