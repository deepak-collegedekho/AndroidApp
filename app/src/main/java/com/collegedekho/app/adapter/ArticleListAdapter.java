package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.listener.OnArticleSelectListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;

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
    private int mViewType;
    // Allows to remember the last item shown on screen
    private int mArticleChangedPosition = -1;
    private boolean mArticleStreamChanged;

    public ArticleListAdapter(Context context, ArrayList<Articles> articles, int viewType) {
        this.mArticles = articles;
        this.mContext = context;
        this.mViewType = viewType;
        this.imageLoader = MySingleton.getInstance(context).getImageLoader();
        this.sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        this.sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.card_article_grid_view;

        if(this.mViewType == Constants.VIEW_INTO_LIST)
            layoutID = R.layout.card_article_list_view;

        View  rootView = LayoutInflater.from(this.mContext).inflate( layoutID,parent, false);
        try {
            return new ArticleHolder(rootView, (OnArticleSelectListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {


        Articles articles = mArticles.get(position);
        ArticleHolder articleHolder = (ArticleHolder) holder;
        articleHolder.articleTitle.setText(Html.fromHtml(articles.title));

        if (articles.image != null && !articles.image.isEmpty())
            articleHolder.articleImage.setImageUrl(articles.image, imageLoader);

        if (mViewType == Constants.VIEW_INTO_LIST) {
            articleHolder.articleContent.setText(Html.fromHtml(articles.content));
            String d = "";
            try {
                sdf.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
                Date date = sdf.parse(articles.published_on);
                sdf.applyPattern("MMMM d, yyyy KK:mm a");
                d = sdf.format(date);
            } catch (ParseException e) {

                Log.e(TAG, "Date format unknown: " + articles.published_on);

            }
            articleHolder.articlePubDate.setText(d);

            if (position == 0 || this.mArticleChangedPosition == position)
            {
                if (position == 0)
                    articleHolder.streamTypeHeader.setText(articles.getStream() + " Articles");
                else
                    articleHolder.streamTypeHeader.setText("Other Articles");

                articleHolder.streamTypeHeader.setVisibility(View.VISIBLE);
                articleHolder.setIsRecyclable(false);
            }
            else if (articles.getStream() == null && !this.mArticleStreamChanged) {
                articleHolder.streamTypeHeader.setText("Other News");
                articleHolder.streamTypeHeader.setVisibility(View.VISIBLE);
                articleHolder.setIsRecyclable(false);

                this.mArticleStreamChanged = true;
                this.mArticleChangedPosition = position;
            }

        }

    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }


    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public void updateArticleAdapter(ArrayList<Articles> articleList){
        if(this.mArticles == null)return;
        mArticles = articleList;
        notifyDataSetChanged();
    }

    class ArticleHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView articleTitle;
        TextView articlePubDate;
        TextView articleContent;
        NetworkImageView articleImage;
        OnArticleSelectListener mListener;
        RelativeLayout container;
        TextView streamTypeHeader;

        public ArticleHolder(View itemView, OnArticleSelectListener listener) {
            super(itemView);

            if (mViewType == Constants.VIEW_INTO_LIST) {
                articlePubDate = (TextView) itemView.findViewById(R.id.textview_article_pubdate);
                articleContent = (TextView) itemView.findViewById(R.id.textview_article_content);
                streamTypeHeader = (TextView) itemView.findViewById(R.id.card_article_heading);
                container = (RelativeLayout) itemView.findViewById(R.id.card_article_container);
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
            boolean flag = false;
            if(mViewType == Constants.VIEW_INTO_LIST)
                flag = true;
            if (getAdapterPosition() < mArticles.size())
                mListener.onArticleSelected(mArticles.get(getAdapterPosition()), flag);
        }
    }
}