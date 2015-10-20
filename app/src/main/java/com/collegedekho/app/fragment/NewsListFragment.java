package com.collegedekho.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.NewsListAdapter;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNewsSelectedListener} interface
 * to handle interaction events.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends BaseFragment {
    private static final String ARG_NEWS = "news";

    public static final String TITLE = "News";
    private ArrayList<News> mNews;
    private String mTitle;
    private NewsListAdapter mAdapter;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance(ArrayList<News> news, String title, String next) {
        NewsListFragment fragment = new NewsListFragment();
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
            this.nextUrl  = getArguments().getString(ARG_NEXT);
            this.listType = Constants.NEWS_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
        ((TextView) rootView.findViewById(R.id.textview_page_title)).setText(mTitle);
        progressBarLL     =   (LinearLayout)rootView.findViewById(R.id.progressBarLL);
        if (mNews.size() == 0)
            ((TextView) rootView.findViewById(android.R.id.empty)).setText("This list is empty.");
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.news_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new NewsListAdapter(getActivity(), mNews, Constants.TYPE_NEWS);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(scrollListener);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnNewsSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnNewsSelectedListener");
        }
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
    public void updateList(List<News> news, String next) {
        progressBarLL.setVisibility(View.GONE);
        this.mNews.addAll(news);
        mAdapter.notifyDataSetChanged();
        loading = false;
        nextUrl = next;
    }
    public interface OnNewsSelectedListener extends  BaseListener{

        void onNewsSelected(News news ,boolean flag);

        @Override
        void onEndReached(String next, int type);
    }
}
