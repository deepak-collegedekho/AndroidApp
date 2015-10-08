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
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.fragment.NewsListFragment;
import com.collegedekho.app.resource.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author Mayank Gautam
 *         Created: 07/07/15
 */
public class NewsListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "NewsListAdapter";
    private final SimpleDateFormat sdf;
    private ArrayList<News> mNews;
    private Context mContext;
    private ImageLoader imageLoader;
    private int type;

    public NewsListAdapter(Context context, ArrayList<News> news , int type) {
        mNews = news;
        mContext = context;
        this.type = type;
        imageLoader = MySingleton.getInstance(context).getImageLoader();
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = null;
        if(type == Constants.TYPE_NEWS) {
             rootView = LayoutInflater.from(mContext).inflate(R.layout.card_news, parent, false);
        }
        else if(type == Constants.TYPE_SIMILARLAR_NEWS)
        {
            rootView = LayoutInflater.from(mContext).inflate(R.layout.card_related_news, parent, false);
        }
        try {
            return new NewsHolder(rootView, (NewsListFragment.OnNewsSelectedListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnNewsSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        News n = mNews.get(position);
        NewsHolder newsHolder = (NewsHolder) holder;
        newsHolder.newsTitle.setText(n.title);

        if (n.image != null && !n.image.isEmpty())
            newsHolder.newsImage.setImageUrl(n.image, imageLoader);

        if(type == Constants.TYPE_NEWS) {
            newsHolder.newsContent.setText(n.content);
            String d = "";
            try {
                sdf.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
                Date date = sdf.parse(n.published_on);
                sdf.applyPattern("MMMM d, yyyy KK:mm a");
                d = sdf.format(date);
            } catch (ParseException e) {
                Log.e(TAG, "Date format unknown: " + n.published_on);
            }
            newsHolder.newsPubDate.setText(d);

        }

    }

    @Override
    public int getItemCount() {
        return mNews.size();
    }

    class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView newsTitle;
        TextView newsPubDate;
        TextView newsContent;
        NetworkImageView newsImage;
        NewsListFragment.OnNewsSelectedListener mListener;
      //  ArrayList<News> relatedNews;

        public NewsHolder(View itemView, NewsListFragment.OnNewsSelectedListener listener) {
            super(itemView);

            if(type == Constants.TYPE_NEWS) {
                newsPubDate = (TextView) itemView.findViewById(R.id.textview_news_pubdate);
                newsContent = (TextView) itemView.findViewById(R.id.textview_news_content);
            }
            newsTitle = (TextView) itemView.findViewById(R.id.textview_news_title);
            newsImage = (NetworkImageView) itemView.findViewById(R.id.image_news_collapsed);
            newsImage.setDefaultImageResId(R.drawable.ic_default_image);
            newsImage.setErrorImageResId(R.drawable.ic_default_image);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                mListener.onNewsSelected(mNews.get(getAdapterPosition()), true);
        }
    }
}
