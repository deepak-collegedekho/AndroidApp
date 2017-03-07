package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
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
import com.collegedekho.app.adapter.ArticleListAdapter;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.listener.OnArticleSelectListener;
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
 * {@link OnArticleSelectListener} interface
 * to handle interaction events.
 * Use the {@link ArticleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteArticleFragment extends BaseFragment {
    private static final String ARG_ARCTICLE = "article";

    public static final String TITLE = "Article";
    private ArrayList<Articles> mArticlesList;
    private String mTitle;
    private ArticleListAdapter mAdapter;
    private int mViewType = Constants.VIEW_INTO_LIST;
    private Articles mArticle;
    private int selectedArticlePosition = -1;

    public InstituteArticleFragment() {
        // Required empty public constructor
    }

    public static InstituteArticleFragment newInstance(ArrayList<Articles> article, String title, String next) {
        InstituteArticleFragment fragment = new InstituteArticleFragment();
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
            listType = Constants.ARTICLES_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_article, container, false);
        progressBarLL = (LinearLayout)rootView.findViewById(R.id.progressBarLL);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.article_list_recyclerView);


        (rootView).findViewById(R.id.view_into_grid).setOnClickListener(this);
        (rootView).findViewById(R.id.view_into_list).setOnClickListener(this);
        (rootView).findViewById(R.id.article_detail_layout).setOnClickListener(this);

        if(mViewType == Constants.VIEW_INTO_GRID) {
            layoutManager =new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
            rootView.findViewById(R.id.article_detail_scrollView).setVisibility(View.VISIBLE);
            progressBarLL.setGravity(Gravity.RIGHT);
            int padd = Utils.getPadding(getContext(), 60);
            progressBarLL.setPadding(0,0,0, padd);
        }
        else {
            rootView.findViewById(R.id.article_detail_scrollView).setVisibility(View.GONE);
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            progressBarLL.setGravity(Gravity.CENTER);
            progressBarLL.setPadding(0,0,0, 0);
        }
        recyclerView.setLayoutManager(layoutManager);
        updateViewTypeIcon(rootView, this.mViewType);
        this.mAdapter = new ArticleListAdapter(getActivity(), new ArrayList<Articles>(), mViewType);
        recyclerView.setAdapter(this.mAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnScrollListener(scrollListener);

        mUpdateArticleListAdapter(rootView);
        return rootView;
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof  MainActivity)
                listener = (OnArticleSelectListener)context;
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
    public void onPause() {
        super.onPause();
        loading=false;
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

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
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                    recyclerView.setLayoutManager(layoutManager);
                    this.mAdapter = new ArticleListAdapter(getActivity(), this.mArticlesList, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.setHasFixedSize(true);
                    progressBarLL.setGravity(Gravity.RIGHT);
                    int padd = Utils.getPadding(getContext(), 60);
                    progressBarLL.setPadding(0, 0, 0, padd);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.addOnScrollListener(scrollListener);
                    this.mUpdateArticleDetail(rootView, this.mArticlesList.get(0));
                }
                break;
            case R.id.view_into_list:
                View rootView1 = getView();
                if(rootView1 != null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    rootView1.findViewById(R.id.article_detail_scrollView).setVisibility(View.GONE);
                    RecyclerView recyclerView1 = (RecyclerView) rootView1.findViewById(R.id.article_list_recyclerView);
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView1.setLayoutManager(layoutManager);
                    this.mAdapter = new ArticleListAdapter(getActivity(), this.mArticlesList, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.setHasFixedSize(true);
                    progressBarLL.setGravity(Gravity.CENTER);
                    progressBarLL.setPadding(0, 0, 0, 0);
                    recyclerView1.addOnScrollListener(scrollListener);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                }
                break;
            case R.id.article_detail_layout:

                (( MainActivity)getActivity()).onArticleSelected(this.mArticle, true, null);
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

    public void updateArticleList(ArrayList<Articles> articlelist, String next) {
        progressBarLL.setVisibility(View.GONE);
        this.mArticlesList = articlelist;
        mUpdateArticleListAdapter(getView());
        loading = false;
        mNextUrl = next;
    }


    private void mUpdateArticleListAdapter(View view){
        if(view == null)return;
        if (mArticlesList == null || mArticlesList.size() <= 0) {
            view.findViewById(android.R.id.empty).setVisibility(View.VISIBLE);
            view.findViewById(R.id.article_list_recyclerView).setVisibility(View.GONE);
            view.findViewById(R.id.article_detail_scrollView).setVisibility(View.GONE);
            view.findViewById(R.id.view_into_grid_list).setVisibility(View.GONE);
        }else{
            view.findViewById(android.R.id.empty).setVisibility(View.GONE);
            view.findViewById(R.id.article_list_recyclerView).setVisibility(View.VISIBLE);
            view.findViewById(R.id.view_into_grid_list).setVisibility(View.VISIBLE);

            if(this.mViewType == Constants.VIEW_INTO_GRID)
            {
                view.findViewById(R.id.article_detail_scrollView).setVisibility(View.VISIBLE);
                if(mArticlesList != null && !mArticlesList.isEmpty()) {
                    if (selectedArticlePosition != -1 && selectedArticlePosition < mArticlesList.size())
                        mUpdateArticleDetail(view, mArticlesList.get(selectedArticlePosition));
                    else
                        mUpdateArticleDetail(view, mArticlesList.get(0));
                }
            }
            else{
                this.mAdapter.updateArticleAdapter(this.mArticlesList);
                this.mAdapter.notifyDataSetChanged();

            }
        }
    }
    /**
     * This method update article details
     * @param article article which has info to display
     */
    private void mUpdateArticleDetail(View view,  Articles article)
    {
        if(view == null || article == null)return;
        this.mArticle = article;
        try {
            String response= new String(mArticle.getTitle().getBytes("ISO-8859-1"),"UTF-8");
            ((TextView) view.findViewById(R.id.article_title)).setText(response);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            ((TextView) view.findViewById(R.id.article_title)).setText(mArticle.getTitle());
        }

        ((TextView) view.findViewById(R.id.article_content)).setText(Html.fromHtml(article.getContent()));

        if (article.getImage() != null && !article.getImage().isEmpty()) {
            ((NetworkImageView) view.findViewById(R.id.article_college_banner)).setImageUrl(article.getImage(), MySingleton.getInstance(getActivity()).getImageLoader());
            view.findViewById(R.id.article_college_banner).setVisibility(View.VISIBLE);
        }else
            view.findViewById(R.id.article_college_banner).setVisibility(View.GONE);

        String d = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(article.getPublished_on());
            sdf.applyPattern("MMMM d, yyyy KK:mm a");
            d = sdf.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + article.getPublished_on());
        }

        ((TextView) view.findViewById(R.id.article_pubdate)).setText(d);
        ArrayList<Articles> articleList = new ArrayList<>();
        int count = this.mArticlesList.size();
        for (int i=0 ; i<count ; i++ ) {
            Articles a = mArticlesList.get(i);
            if(a.getId() == article.getId()) {
                selectedArticlePosition = i;
                continue;
            }
            articleList.add(a);
        }
        view.findViewById(R.id.article_detail_scrollView).scrollTo(0, 0);
        mAdapter.updateArticleAdapter(articleList);

    }

}
