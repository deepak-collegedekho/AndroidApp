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
import com.collegedekho.app.adapter.ArticleListAdapter;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnArticleSelectedListener} interface
 * to handle interaction events.
 * Use the {@link ArticleListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleListFragment extends BaseFragment {

    private static final String ARG_ARTICLE = "article";
    public static final String TITLE = "Articles";

    private ArrayList<Articles> mArticles;
    private String mTitle;
    private ArticleListAdapter mAdapter;
    public ArticleListFragment() {
        // Required empty public constructor
    }

    public static ArticleListFragment newInstance(ArrayList<Articles> articles, String title, String next) {
        ArticleListFragment fragment = new ArticleListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ARTICLE, articles);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticles = getArguments().getParcelableArrayList(ARG_ARTICLE);
            mTitle = getArguments().getString(ARG_TITLE);
            nextUrl  = getArguments().getString(ARG_NEXT);
            listType = Constants.ARTICLES_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_articles_list, container, false);
        ((TextView) rootView.findViewById(R.id.textview_page_title)).setText(mTitle);
        progressBarLL     =   (LinearLayout)rootView.findViewById(R.id.progressBarLL);
        if (mArticles.size() == 0)
            ((TextView) rootView.findViewById(android.R.id.empty)).setText("This list is empty.");
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.articles_list);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ArticleListAdapter(getActivity(), mArticles , Constants.TYPE_ARTCLES);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(scrollListener);
        return rootView;
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (OnArticleSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnArticleSelectedListener");
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
    public void updateList(List<Articles> articles, String next) {
        progressBarLL.setVisibility(View.GONE);
        this.mArticles.addAll(articles);
        mAdapter.notifyDataSetChanged();
        loading = false;
        nextUrl = next;
    }

    public interface OnArticleSelectedListener extends BaseListener{
        void onArticleSelected(Articles article ,boolean flag);
    }



}
