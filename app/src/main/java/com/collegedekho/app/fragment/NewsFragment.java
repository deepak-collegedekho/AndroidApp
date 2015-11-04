package com.collegedekho.app.fragment;

import android.content.Context;
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
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.NewsListAdapter;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
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
 * Activities that contain this fragment must implement the
 * {@link OnNewsSelectedListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment  {
    private static final String ARG_NEWS = "news";

    public static final String TITLE = "News";
    private ArrayList<News> mNewsList;
    private String mTitle;
    private NewsListAdapter mAdapter;
    private int mViewType = Constants.VIEW_INTO_GRID;
    private News mNews;

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
            this.mNewsList = getArguments().getParcelableArrayList(ARG_NEWS);
            this.mTitle = getArguments().getString(ARG_TITLE);
            this.mNextUrl = getArguments().getString(ARG_NEXT);
            
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);

        if (this.mNewsList == null || this.mNewsList.size() <= 0) {
            (rootView.findViewById(android.R.id.empty)).setVisibility(View.VISIBLE);
            return rootView;
        }

        (rootView.findViewById(android.R.id.empty)).setVisibility(View.GONE);
        (rootView).findViewById(R.id.view_into_grid).setOnClickListener(this);
        (rootView).findViewById(R.id.view_into_list).setOnClickListener(this);
        (rootView).findViewById(R.id.news_detail_layout).setOnClickListener(this);
       RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.news_list_recyclerView);
        if(mViewType == Constants.VIEW_INTO_GRID) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            rootView.findViewById(R.id.news_detail_scrollView).setVisibility(View.VISIBLE);
        }
        else {

            rootView.findViewById(R.id.news_detail_scrollView).setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }
        this.mAdapter = new NewsListAdapter(getActivity(), new ArrayList<News>(), mViewType);
        recyclerView.setAdapter(this.mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mUpdateNewsDetail(rootView, this.mNewsList.get(0));

        return rootView;
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
        outState.putParcelableArrayList(ARG_NEWS, this.mNewsList);
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.view_into_grid:
                View rootView = getView();
                if(rootView != null && mViewType != Constants.VIEW_INTO_GRID) {
                    this.mViewType = Constants.VIEW_INTO_GRID;
                    rootView.findViewById(R.id.news_detail_scrollView).setVisibility(View.VISIBLE);
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.news_list_recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    this.mAdapter = new NewsListAdapter(getActivity(), this.mNewsList, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    this.mUpdateNewsDetail(rootView, this.mNewsList.get(0));
                }
                break;
            case R.id.view_into_list:
                View rootView1 = getView();
                if(rootView1 != null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    rootView1.findViewById(R.id.news_detail_scrollView).setVisibility(View.GONE);
                    RecyclerView recyclerView1 = (RecyclerView) rootView1.findViewById(R.id.news_list_recyclerView);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    this.mAdapter = new NewsListAdapter(getActivity(), this.mNewsList, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                }
                break;
            case R.id.news_detail_layout:

                (( MainActivity)getActivity()).onNewsSelected(this.mNews, true);
                break;
            default:
                break;
        }
    }

    /**
     * This method is used to display detail of News
     * @param view
     * @param news
     */
    private void mUpdateNewsDetail(View view, News news)
    {
        if(view == null || news == null)return;
        mNews= news;
        ((TextView) view.findViewById(R.id.news_title)).setText(Html.fromHtml(news.title));
        ((TextView) view.findViewById(R.id.news_content)).setText(Html.fromHtml(news.content));
        //((TextView) view.findViewById(R.id.news_title)).setTypeface(Utils.getTypeFace(getActivity(), TypeFaceTypes.GOTHAMBOOK));
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
        for (News n : this.mNewsList) {
            if(n.getId() == news.getId())continue;
            newList.add(n);
        }
        view.findViewById(R.id.news_detail_scrollView).scrollTo(0, 0);

        mAdapter.updateNewsAdapter(newList);

    }


    public void updateNews(News news)
    {
        mUpdateNewsDetail(getView(), news);
    }
    public void updateList(List<News> news, String next) {
       // progressBarLL.setVisibility(View.GONE);
        this.mNewsList.addAll(news);
        this.mAdapter.notifyDataSetChanged();
        loading = false;
        mNextUrl = next;
    }
    public interface OnNewsSelectedListener extends  BaseListener{
        void onNewsSelected(News news, boolean flag);

        @Override
        void onEndReached(String next, int type);
    }
}