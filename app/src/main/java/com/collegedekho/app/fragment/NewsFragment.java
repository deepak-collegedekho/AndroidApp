package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.NewsListAdapter;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.listener.OnNewsSelectListener;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNewsSelectListener} interface
 * to handle interaction events.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends BaseFragment {
    private static final String ARG_NEWS = "news";

    public static final String TITLE = "News";
    private ArrayList<News> mNewsList;
    private String mTitle;
    private NewsListAdapter mAdapter;
    private int mViewType = Constants.VIEW_INTO_LIST;
    private News mNews;
    private int selectedNewsPosition = -1;

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
            listType = Constants.NEWS_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_news, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBarLL = (LinearLayout) view.findViewById(R.id.progressBarLL);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.news_list_recyclerView);

        view.findViewById(R.id.view_into_grid).setOnClickListener(this);
        view.findViewById(R.id.view_into_list).setOnClickListener(this);
        view.findViewById(R.id.news_detail_layout).setOnClickListener(this);

        if (this.mViewType == Constants.VIEW_INTO_GRID) {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            view.findViewById(R.id.news_detail_scrollView).setVisibility(View.VISIBLE);
            int padd = Utils.getPadding(getContext(), 60);
            progressBarLL.setGravity(Gravity.END);
            progressBarLL.setPadding(0, 0, 0, padd);
        } else {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            view.findViewById(R.id.news_detail_scrollView).setVisibility(View.GONE);
            progressBarLL.setGravity(Gravity.CENTER);
            progressBarLL.setPadding(0, 0, 0, 0);
        }
        recyclerView.setLayoutManager(layoutManager);
        updateViewTypeIcon(view, this.mViewType);
        if (this.mAdapter == null)
            this.mAdapter = new NewsListAdapter(getActivity(), new ArrayList<News>(), mViewType);
        recyclerView.setAdapter(this.mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(scrollListener);

        mUpdateNewsListAdapter(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity)
                listener = (OnNewsSelectListener) context;
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
        super.listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mMainActivity = (MainActivity) this.getActivity();
        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            MainActivity mMainActivity = (MainActivity) this.getActivity();
            if (mMainActivity != null)
                mMainActivity.currentFragment = this;
        }
    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.view_into_grid:
                View rootView = getView();
                if (rootView != null && mViewType != Constants.VIEW_INTO_GRID) {
                    this.mViewType = Constants.VIEW_INTO_GRID;
                    rootView.findViewById(R.id.news_detail_scrollView).setVisibility(View.VISIBLE);
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.news_list_recyclerView);
                    recyclerView.setVisibility(View.VISIBLE);
                    super.layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    this.mAdapter = new NewsListAdapter(getActivity(), this.mNewsList, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.setHasFixedSize(true);
                    int padd = Utils.getPadding(getContext(), 60);
                    super.progressBarLL.setGravity(Gravity.END);
                    super.progressBarLL.setPadding(0, 0, 0, padd);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    this.mUpdateNewsDetail(rootView, this.mNewsList.get(0));
                    recyclerView.addOnScrollListener(scrollListener);
                }
                break;
            case R.id.view_into_list:
                View rootView1 = getView();
                if (rootView1 != null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    rootView1.findViewById(R.id.news_detail_scrollView).setVisibility(View.GONE);
                    RecyclerView recyclerView1 = (RecyclerView) rootView1.findViewById(R.id.news_list_recyclerView);
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView1.setLayoutManager(layoutManager);
                    this.mAdapter = new NewsListAdapter(getActivity(), this.mNewsList, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.setHasFixedSize(true);
                    progressBarLL.setGravity(Gravity.CENTER);
                    progressBarLL.setPadding(0, 0, 0, 0);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                    recyclerView1.addOnScrollListener(scrollListener);
                }
                break;
            case R.id.news_detail_layout:
                if (getView() != null && getActivity() != null) {
                    ((MainActivity) getActivity()).onNewsSelected(this.mNews, true, getView().findViewById(R.id.news_college_banner));
                }
                break;
            default:
                break;
        }

        updateViewTypeIcon(getView(), this.mViewType);
    }

    public void updateNews(News news) {
        mUpdateNewsDetail(getView(), news);
    }

    public void updateNewsList(ArrayList<News> newslist, String next) {
        super.progressBarLL.setVisibility(View.GONE);
        this.mNewsList = newslist;
        this.mUpdateNewsListAdapter(getView());
        super.loading = false;
        super.mNextUrl = next;
    }


    @Override
    public void onPause() {
        super.onPause();
        super.loading = false;
    }

    private void mUpdateNewsListAdapter(View view) {
        if (view == null) return;

        if (this.mNewsList == null || this.mNewsList.size() <= 0) {
            view.findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
            view.findViewById(R.id.news_detail_scrollView).setVisibility(View.GONE);
            view.findViewById(R.id.view_into_grid_list).setVisibility(View.GONE);
            view.findViewById(R.id.news_list_recyclerView).setVisibility(View.GONE);
        } else {
            view.findViewById(android.R.id.empty).setVisibility(View.GONE);
            view.findViewById(R.id.view_into_grid_list).setVisibility(View.VISIBLE);

            if (this.mViewType == Constants.VIEW_INTO_GRID) {
                view.findViewById(R.id.news_detail_scrollView).setVisibility(View.VISIBLE);
                view.findViewById(R.id.news_list_recyclerView).setVisibility(View.VISIBLE);
                if (this.mNewsList != null && !this.mNewsList.isEmpty()) {
                    if (this.selectedNewsPosition != -1 && this.selectedNewsPosition < this.mNewsList.size())
                        mUpdateNewsDetail(view, this.mNewsList.get(this.selectedNewsPosition));
                    else
                        mUpdateNewsDetail(view, this.mNewsList.get(0));
                }
            } else {
                this.mAdapter.updateNewsAdapter(this.mNewsList);
                this.mAdapter.notifyDataSetChanged();
            }
        }


    }

    /**
     * This method is used to display detail of News
     * @param view view
     * @param news news
     */
    private void mUpdateNewsDetail(View view, News news) {
        if (view == null || news == null) return;
        this.mNews = news;

        // set title of news
        try {
            String response = new String(news.title.getBytes("ISO-8859-1"), "UTF-8");
            ((TextView) view.findViewById(R.id.news_title)).setText(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ((TextView) view.findViewById(R.id.news_title)).setText(news.title);
        }

        // set content of news
        try {
            String response = new String(news.content.getBytes("ISO-8859-1"), "UTF-8");
            ((TextView) view.findViewById(R.id.news_content)).setText(Html.fromHtml(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ((TextView) view.findViewById(R.id.news_content)).setText(Html.fromHtml(news.content));
        }

        // load news image from server
        if (news.image != null && !news.image.isEmpty()) {
            ((NetworkImageView) view.findViewById(R.id.news_college_banner)).setImageUrl(news.image, MySingleton.getInstance(getActivity()).getImageLoader());
            view.findViewById(R.id.news_college_banner).setVisibility(View.VISIBLE);
        } else
            view.findViewById(R.id.news_college_banner).setVisibility(View.GONE);

        // set published date of news
        String d = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(news.published_on);
            sdf.applyPattern("MMMM d, yyyy KK:mm a");
            d = sdf.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + news.published_on);
        }
        ((TextView) view.findViewById(R.id.news_pubdate)).setText(d);

        ArrayList<News> newList = new ArrayList<>();
        int count = this.mNewsList.size();
        for (int i = 0; i < count; i++) {
            News n = this.mNewsList.get(i);
            if (n.getId() == news.getId()) {
                this.selectedNewsPosition = i;
                continue;
            }
            newList.add(n);
        }
        view.findViewById(R.id.news_detail_scrollView).scrollTo(0, 0);
        this.mAdapter.updateNewsAdapter(newList);

    }
}