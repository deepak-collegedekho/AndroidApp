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
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.NewsListAdapter;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnNewsSelectedListener} interface
 * to handle interaction events.
 * Use the {@link NewsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsListFragment extends Fragment {
    private static final String ARG_NEWS = "news";
    private static final String ARG_TITLE = "title";

    private ArrayList<News> mNews;
    private ArrayList<News> similarNews;
    private String mTitle;

    public NewsListFragment() {
        // Required empty public constructor
    }

    public static NewsListFragment newInstance(ArrayList<News> news, String title) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NEWS, news);
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNews = getArguments().getParcelableArrayList(ARG_NEWS);
            mTitle = getArguments().getString(ARG_TITLE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_list, container, false);
        ((TextView) rootView.findViewById(R.id.textview_page_title)).setText(mTitle);
        if (mNews.size() == 0)
            ((TextView) rootView.findViewById(android.R.id.empty)).setText("This list is empty.");
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.news_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new NewsListAdapter(getActivity(), mNews , Constants.TYPE_NEWS));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnNewsSelectedListener {
        void onNewsSelected(News news ,boolean flag);
    }

}
