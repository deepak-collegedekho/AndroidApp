package com.collegedekho.app.fragment;


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
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.NewsListAdapter;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.DividerItemDecoration;

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
public class NewsDetailFragment extends Fragment {
    private static final String ARG_NEWS = "short_name";
    private static final String TAG = "NewsDetailFragment";
    private static final String ARG_NEWSLIST = "similar_news";

    private News mNews;
    private ArrayList<News> mNewsList;
    private NewsListAdapter mAdapter;
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
            mNews = getArguments().getParcelable(ARG_NEWS);
            mNewsList = getArguments().getParcelableArrayList(ARG_NEWSLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        showNewsUpdate();
        return rootView;
    }

    public void updateNews(News news) {
        this.mNews = news;
        showNewsUpdate();
    }
    private void showNewsUpdate()
    {
        if(rootView == null)
        {
            return;
        }
        ((TextView) rootView.findViewById(R.id.textview_news_title)).setText(mNews.title);
        ((TextView) rootView.findViewById(R.id.textview_news_content)).setText(Html.fromHtml(mNews.content));
        if (mNews.image != null && !mNews.image.isEmpty())
            ((NetworkImageView) rootView.findViewById(R.id.image_news_expanded)).setImageUrl(mNews.image, MySingleton.getInstance(getActivity()).getImageLoader());
        else
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

        ArrayList<News> similarNews =  new ArrayList<News>();
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
        if(similarNews.size() <=0)
        {

            ((TextView) rootView.findViewById(R.id.similar_newsTV)).setVisibility(View.GONE);
            ((View) rootView.findViewById(R.id.divider_similar_news)).setVisibility(View.GONE);
            ((RecyclerView) rootView.findViewById(R.id.related_news_list)).setVisibility(View.GONE);
        }
        else {
            ScrollView scrollVIew = (ScrollView) rootView.findViewById(R.id.parentScrollView);
            scrollVIew.scrollTo(0, 0);
            scrollVIew.smoothScrollTo(0, 0);

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.related_news_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            mAdapter = new NewsListAdapter(getActivity(), similarNews, Constants.TYPE_SIMILARLAR_NEWS);
            recyclerView.setAdapter(mAdapter);
            //recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.HORIZONTAL_LIST));
            recyclerView.setHasFixedSize(true);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }
}
