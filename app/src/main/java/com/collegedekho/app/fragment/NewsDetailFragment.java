package com.collegedekho.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.News;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsDetailFragment extends Fragment {
    private static final String ARG_NEWS = "short_name";

    private News mNews;


    public NewsDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param news A News object to be displayed.
     * @return A new instance of fragment InstituteOverviewFragment.
     */
    public static NewsDetailFragment newInstance(News news) {
        NewsDetailFragment fragment = new NewsDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NEWS, news);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mNews = getArguments().getParcelable(ARG_NEWS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_about, container, false);
        ((TextView) rootView.findViewById(R.id.textview_news_title)).setText(mNews.title);
        ((TextView) rootView.findViewById(R.id.textview_news_content)).setText(Html.fromHtml(mNews.content));
        ((TextView) rootView.findViewById(R.id.textview_news_pubdate)).setText(mNews.published_on);
        ((NetworkImageView) rootView.findViewById(R.id.image_news_expanded)).setImageUrl(mNews.image, MySingleton.getInstance(getActivity()).getImageLoader());
        return rootView;
    }


}
