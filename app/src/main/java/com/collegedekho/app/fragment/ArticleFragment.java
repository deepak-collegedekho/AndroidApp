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
import com.collegedekho.app.adapter.ArticleListAdapter;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;

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
 * {@link OnArticleSelectedListener} interface
 * to handle interaction events.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ArticleFragment extends BaseFragment {
    private static final String ARG_ARCTICLE = "article";

    public static final String TITLE = "Article";
    private ArrayList<Articles> mArticlesList;
    private String mTitle;
    private ArticleListAdapter mAdapter;
    private int mViewType = Constants.VIEW_INTO_GRID;
    private Articles mArticle;

    public ArticleFragment() {
        // Required empty public constructor
    }

    public static ArticleFragment newInstance(ArrayList<Articles> article, String title, String next) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ARCTICLE, article);
        args.putString(ARG_TITLE, title);
        args.putString(ARG_NEXT, next);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mArticlesList = getArguments().getParcelableArrayList(ARG_ARCTICLE);
            this.mTitle = getArguments().getString(ARG_TITLE);
            this.mNextUrl = getArguments().getString(ARG_NEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        if (mArticlesList == null || mArticlesList.size() <= 0) {
            (rootView.findViewById(android.R.id.empty)).setVisibility(View.VISIBLE);
            return rootView;
        }

        (rootView.findViewById(android.R.id.empty)).setVisibility(View.GONE);
        (rootView).findViewById(R.id.view_into_grid).setOnClickListener(this);
        (rootView).findViewById(R.id.view_into_list).setOnClickListener(this);
        (rootView).findViewById(R.id.article_detail_layout).setOnClickListener(this);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.article_list_recyclerView);
        if(mViewType == Constants.VIEW_INTO_GRID) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            rootView.findViewById(R.id.article_detail_scrollView).setVisibility(View.VISIBLE);
        }
        else {

            rootView.findViewById(R.id.article_detail_scrollView).setVisibility(View.GONE);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        }

        updateViewTypeIcon(rootView, this.mViewType);
        this.mAdapter = new ArticleListAdapter(getActivity(), new ArrayList<Articles>(), mViewType);
        recyclerView.setAdapter(this.mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        mUpdateArticleDetail(rootView, this.mArticlesList.get(0));
        // recyclerView.addOnScrollListener(scrollListener);
        return rootView;
    }

    /**
     * This method update article details
     * @param article
     */
    private void mUpdateArticleDetail(View view,  Articles article)
    {
        if(view == null || article == null)return;
        this.mArticle = article;
        ((TextView) view.findViewById(R.id.article_title)).setText(article.title);
        ((TextView) view.findViewById(R.id.article_content)).setText(Html.fromHtml(article.content));
        if (article.image != null && !article.image.isEmpty()) {
            ((NetworkImageView) view.findViewById(R.id.article_college_banner)).setImageUrl(article.image, MySingleton.getInstance(getActivity()).getImageLoader());
            view.findViewById(R.id.article_college_banner).setVisibility(View.VISIBLE);
        }else
            view.findViewById(R.id.article_college_banner).setVisibility(View.GONE);
        String d = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(article.published_on);
            sdf.applyPattern("MMMM d, yyyy KK:mm a");
            d = sdf.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + article.published_on);
//                Utils.sendException(t, TAG, "DateFormatUnknown", r.getAddedOn());
        }
        ((TextView) view.findViewById(R.id.article_pubdate)).setText(d);
        ArrayList<Articles> newList = new ArrayList<>();
        for (Articles n : mArticlesList) {
            if(n.getId() == article.getId())continue;
            newList.add(n);
        }
        view.findViewById(R.id.article_detail_scrollView).scrollTo(0, 0);
        mAdapter.updateArticleAdapter(newList);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof  MainActivity)
                listener = (OnArticleSelectedListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(ARG_ARCTICLE, this.mArticlesList);
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
                    rootView.findViewById(R.id.article_detail_scrollView).setVisibility(View.VISIBLE);
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.article_list_recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                    this.mAdapter = new ArticleListAdapter(getActivity(), this.mArticlesList, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    this.mUpdateArticleDetail(rootView, this.mArticlesList.get(0));
                }
                break;
            case R.id.view_into_list:
                View rootView1 = getView();
                if(rootView1 != null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    rootView1.findViewById(R.id.article_detail_scrollView).setVisibility(View.GONE);
                    RecyclerView recyclerView1 = (RecyclerView) rootView1.findViewById(R.id.article_list_recyclerView);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    this.mAdapter = new ArticleListAdapter(getActivity(), this.mArticlesList, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                }
                break;
            case R.id.article_detail_layout:

                (( MainActivity)getActivity()).onArticleSelected(this.mArticle, true);
                break;
            default:
                break;

        }
        updateViewTypeIcon(getView(), this.mViewType);
    }
    public void updateArticle(Articles article)
    {
        mUpdateArticleDetail(getView(), article);
    }
    public void updateList(List<Articles> article, String next) {
        // progressBarLL.setVisibility(View.GONE);
        this.mArticlesList.addAll(article);
        mAdapter.notifyDataSetChanged();
        loading = false;
        mNextUrl = next;
    }
    public interface OnArticleSelectedListener extends  BaseListener{
        void onArticleSelected(Articles article, boolean flag);
            @Override
        void onEndReached(String next, int type);
    }
}
