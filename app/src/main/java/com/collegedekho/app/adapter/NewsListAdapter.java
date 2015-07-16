package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.fragment.NewsListFragment;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 07/07/15
 */
public class NewsListAdapter extends RecyclerView.Adapter {

    private ArrayList<News> mNews;
    private Context mContext;
    private ImageLoader imageLoader;

    public NewsListAdapter(Context context, ArrayList<News> news) {
        mNews = news;
        mContext = context;
        imageLoader = MySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.item_institute_list, parent, false);
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
        newsHolder.newsContent.setText(n.content);
        newsHolder.newsPubDate.setText(n.published_on);
        newsHolder.newsImage.setImageUrl(n.image, imageLoader);

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

        public NewsHolder(View itemView, NewsListFragment.OnNewsSelectedListener listener) {
            super(itemView);
            newsTitle = (TextView) itemView.findViewById(R.id.textview_news_title);
            newsPubDate = (TextView) itemView.findViewById(R.id.textview_news_pubdate);
            newsContent = (TextView) itemView.findViewById(R.id.textview_news_content);
            newsImage = (NetworkImageView) itemView.findViewById(R.id.image_news_collapsed);
            mListener = listener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onNewsSelected(mNews.get(getAdapterPosition()));
        }
    }
}
