package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.NewsListAdapter;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.resource.TypeFaceTypes;
import com.collegedekho.app.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNewsSelectedListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment {
    private static final String ARG_NEWS = "news";

    public static final String TITLE = "News";
    private ArrayList<News> mNews;
    private String mTitle;
    private NewsListAdapter mAdapter;

    public NewsFragment() {
        // Required empty public constructor
    }

    public static NewsFragment newInstance(ArrayList<News> news, String title, String next) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NEWS, news);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mNews = getArguments().getParcelableArrayList(ARG_NEWS);
            this.mTitle = getArguments().getString(ARG_TITLE);
            this.mNextUrl = getArguments().getString(ARG_NEXT);
            this.listType = Constants.NEWS_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        if (mNews == null || mNews.size() <= 0) {
            (rootView.findViewById(android.R.id.empty)).setVisibility(View.VISIBLE);
            return rootView;
        }

        (rootView.findViewById(android.R.id.empty)).setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.news_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new NewsListAdapter(getActivity(), new ArrayList<News>(), Constants.TYPE_SIMILARLAR_NEWS);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.addOnScrollListener(scrollListener);
        mUpdateNewsDetail(rootView, mNews.get(0));
        return rootView;
    }

    /**
     * This method update news details
     * @param news
     */
    private void mUpdateNewsDetail(View view, News news)
    {
         if(view == null || news == null)return;

        ((TextView) view.findViewById(R.id.news_title)).setText(Html.fromHtml(news.title));
        //((TextView) view.findViewById(R.id.news_title)).setTypeface(Utils.getTypeFace(getActivity(), TypeFaceTypes.GOTHAMBOOK));
        ((TextView) view.findViewById(R.id.news_content)).setText(Html.fromHtml(news.content));
        //((TextView) view.findViewById(R.id.news_content)).setTypeface(Utils.getTypeFace(getActivity(), TypeFaceTypes.DROID_SERIF_BOLD));

        if (news.image != null && !news.image.isEmpty())
            ((NetworkImageView) view.findViewById(R.id.news_college_banner)).setImageUrl(news.image, MySingleton.getInstance(getActivity()).getImageLoader());
        else
            view.findViewById(R.id.news_college_banner).setVisibility(View.GONE);
        String d = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(news.published_on);
            sdf.applyPattern("MMMM d, yyyy KK:mm a");
            d = sdf.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + news.published_on);
//                Utils.sendException(t, TAG, "DateFormatUnknown", r.getAddedOn());
        }
        ((TextView) view.findViewById(R.id.news_pubdate)).setText(d);
        ArrayList<News> newList = new ArrayList<>();
        for (News n :mNews ) {
            if(n.getId() == news.getId())continue;
            newList.add(n);
        }
        view.findViewById(R.id.news_detail_scrollView).scrollTo(0, 0);
         mAdapter.updateNewsAdapter(newList);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof  MainActivity)
                listener = (OnNewsSelectedListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNewsSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_NEWS, this.mNews);
        outState.putString(ARG_TITLE, this.mTitle);
        outState.putString(ARG_NEXT, this.mNextUrl);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;

    }
    public void updateNews(News news)
    {

        View view = getView();
        mUpdateNewsDetail(getView(), news);
    }
    public void updateList(List<News> news, String next) {
       // progressBarLL.setVisibility(View.GONE);
        this.mNews.addAll(news);
        mAdapter.notifyDataSetChanged();
        loading = false;
        mNextUrl = next;
    }
    public interface OnNewsSelectedListener extends  BaseListener{
        void onNewsSelected(News news);

        @Override
        void onEndReached(String next, int type);
    }
}
