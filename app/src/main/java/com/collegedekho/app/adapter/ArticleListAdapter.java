package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.fragment.ArticleListFragment;
import com.collegedekho.app.resource.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by root on 10/9/15.
 */
public class ArticleListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "ArticleListAdapter";
    private final SimpleDateFormat sdf;
    private ArrayList<Articles> mArticles;
    private Context mContext;
    private ImageLoader imageLoader;
    private int type;

    public ArticleListAdapter(Context context, ArrayList<Articles> articles, int type) {
        mArticles = articles;
        mContext = context;
        this.type = type;
        imageLoader = MySingleton.getInstance(context).getImageLoader();
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = null;
        if (type == Constants.TYPE_ARTCLES) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.card_article, parent, false);
        } else if (type == Constants.TYPE_SIMILARLAR_ARTICLES) {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.card_similar_article, parent, false);
        }
        try {
            return new ArticleHolder(rootView, (ArticleListFragment.OnArticleSelectedListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Articles a = mArticles.get(position);
        ArticleHolder articleHolder = (ArticleHolder) holder;
        articleHolder.articleTitle.setText(a.title);

        if (a.image != null && !a.image.isEmpty())
            articleHolder.articleImage.setImageUrl(a.image, imageLoader);

        if (type == Constants.TYPE_ARTCLES) {
            articleHolder.articleContent.setText(a.content);
            String d = "";
            try {
                sdf.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
                Date date = sdf.parse(a.published_on);
                sdf.applyPattern("MMMM d, yyyy KK:mm a");
                d = sdf.format(date);
            } catch (ParseException e) {
                Log.e(TAG, "Date format unknown: " + a.published_on);
            }
            articleHolder.articlePubDate.setText(d);

        }

    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView articleTitle;
        TextView articlePubDate;
        TextView articleContent;
        NetworkImageView articleImage;
        ArticleListFragment.OnArticleSelectedListener mListener;

        public ArticleHolder(View itemView, ArticleListFragment.OnArticleSelectedListener listener) {
            super(itemView);

            if (type == Constants.TYPE_ARTCLES) {
                articlePubDate = (TextView) itemView.findViewById(R.id.textview_article_pubdate);
                articleContent = (TextView) itemView.findViewById(R.id.textview_article_content);
            }
            articleTitle = (TextView) itemView.findViewById(R.id.textview_article_title);
            articleImage = (NetworkImageView) itemView.findViewById(R.id.image_article_collapsed);
            articleImage.setDefaultImageResId(R.drawable.ic_default_image);
            articleImage.setErrorImageResId(R.drawable.ic_default_image);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onArticleSelected(mArticles.get(getAdapterPosition()), true);
        }
    }
}