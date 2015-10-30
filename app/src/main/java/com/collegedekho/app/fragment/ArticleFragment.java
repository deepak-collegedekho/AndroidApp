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
import com.collegedekho.app.entities.News;
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
    private ArrayList<Articles> mArticles;
    private String mTitle;
    private ArticleListAdapter mAdapter;

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
            this.mArticles = getArguments().getParcelableArrayList(ARG_ARCTICLE);
            this.mTitle = getArguments().getString(ARG_TITLE);
            this.mNextUrl = getArguments().getString(ARG_NEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);

        if (mArticles == null || mArticles.size() <= 0) {
            (rootView.findViewById(android.R.id.empty)).setVisibility(View.VISIBLE);
            return rootView;
        }

        (rootView.findViewById(android.R.id.empty)).setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.article_list_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new ArticleListAdapter(getActivity(), new ArrayList<Articles>(), Constants.TYPE_SIMILARLAR_ARTICLES);
        recyclerView.setAdapter(mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // recyclerView.addOnScrollListener(scrollListener);
        mUpdateArticleDetail(rootView, mArticles.get(0));
        return rootView;
    }

    /**
     * This method update article details
     * @param article
     */
    private void mUpdateArticleDetail(View view,  Articles article)
    {
        if(view == null || article == null)return;

        ((TextView) view.findViewById(R.id.article_title)).setText(article.title);
        ((TextView) view.findViewById(R.id.article_content)).setText(Html.fromHtml(article.content));
        if (article.image != null && !article.image.isEmpty())
            ((NetworkImageView) view.findViewById(R.id.article_college_banner)).setImageUrl(article.image, MySingleton.getInstance(getActivity()).getImageLoader());
        else
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
        for (Articles n :mArticles ) {
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
        outState.putParcelableArrayList(ARG_ARCTICLE, this.mArticles);
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
    public void updateArticle(Articles article)
    {
        mUpdateArticleDetail(getView(), article);
    }
    public void updateList(List<Articles> article, String next) {
        // progressBarLL.setVisibility(View.GONE);
        this.mArticles.addAll(article);
        mAdapter.notifyDataSetChanged();
        loading = false;
        mNextUrl = next;
    }
    public interface OnArticleSelectedListener extends  BaseListener{
        void onArticleSelected(Articles article);
            @Override
        void onEndReached(String next, int type);
    }
}
