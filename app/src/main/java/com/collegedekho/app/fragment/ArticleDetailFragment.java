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
import com.collegedekho.app.R;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ArticleListAdapter;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.resource.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Suresh Saini on 10/9/15.
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment
 * Use the {@link ArticleDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
*/

public class ArticleDetailFragment extends BaseFragment {
    private static final String ARG_ARTICLE = "short_name";
    private static final String TAG = "ArticleDetailFragment";
    private static final String ARG_ARTICLELIST = "similar_articles";

    private Articles mArticle;
    private ArrayList<Articles> mArticlesList;
    private ArticleListAdapter mAdapter;
    private View rootView;


    public ArticleDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param article A Article object to be displayed.
     * @return A new instance of fragment ArticleFragment.
     */
    public static ArticleDetailFragment newInstance(Articles article , List<Articles> articlesList) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ARTICLE, article);
        args.putParcelableArrayList(ARG_ARTICLELIST, (ArrayList<Articles>)articlesList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mArticle = getArguments().getParcelable(ARG_ARTICLE);
            mArticlesList = getArguments().getParcelableArrayList(ARG_ARTICLELIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_article, container, false);
        showArticleUpdate();
        return rootView;
    }

    public void updateArticle(Articles article) {
        this.mArticle = article;
        showArticleUpdate();
    }
    private void showArticleUpdate()
    {
        if(rootView == null)
        {
            return;
        }
        ((TextView) rootView.findViewById(R.id.textview_article_title)).setText(mArticle.title);
        ((TextView) rootView.findViewById(R.id.textview_article_content)).setText(Html.fromHtml(mArticle.content));
        if (mArticle.image != null && !mArticle.image.isEmpty())
            ((NetworkImageView) rootView.findViewById(R.id.image_article_expanded)).setImageUrl(mArticle.image, MySingleton.getInstance(getActivity()).getImageLoader());
        else
            rootView.findViewById(R.id.image_article_expanded).setVisibility(View.GONE);
        String d = "";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(mArticle.published_on);
            sdf.applyPattern("MMMM d, yyyy KK:mm a");
            d = sdf.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + mArticle.published_on);
//                Utils.sendException(t, TAG, "DateFormatUnknown", r.getAddedOn());
        }
        ((TextView) rootView.findViewById(R.id.textview_article_pubdate)).setText(d);

        ArrayList<Articles> similarArticle =  new ArrayList<Articles>();
        ArrayList<String> similarArticlesIds = mArticle.getSimilarArticlesIds();
        if (similarArticlesIds != null)
        {
            for (int i = 0; i < similarArticlesIds.size(); i++) {
                for (int j = 0; j < mArticlesList.size(); j++) {
                    Articles tempObj = mArticlesList.get(j);
                    if (tempObj == null) continue;
                    if (similarArticlesIds.get(i).equalsIgnoreCase(Integer.toString(tempObj.getId())))
                    {
                        similarArticle.add(tempObj);
                        break;
                    }
                }
            }
        }
        if(similarArticle.size() <=0)
        {

            ((TextView) rootView.findViewById(R.id.similar_articleTV)).setVisibility(View.GONE);
            ((View) rootView.findViewById(R.id.divider_similar_article)).setVisibility(View.GONE);
            ((RecyclerView) rootView.findViewById(R.id.related_article_list)).setVisibility(View.GONE);
        }
        else {
            ScrollView scrollVIew = (ScrollView) rootView.findViewById(R.id.parentScrollView);
            scrollVIew.scrollTo(0, 0);
            scrollVIew.smoothScrollTo(0, 0);

            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.related_article_list);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            mAdapter = new ArticleListAdapter(getActivity(), similarArticle, Constants.TYPE_SIMILARLAR_ARTICLES);
            recyclerView.setAdapter(mAdapter);
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
