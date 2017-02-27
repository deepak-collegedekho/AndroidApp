package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.listener.OnNewsSelectListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.network.MySingleton;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class NewsListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "NewsListAdapter";
    private final SimpleDateFormat sdf;
    private ArrayList<News> mNewsList;
    private Context mContext;
    private ImageLoader imageLoader;
    private int mViewType;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;
    private int mNewsChangedPosition = -1;
    private String mNewsStream = "";
    private boolean mNewsStreamChanged;

    public NewsListAdapter(Context context, ArrayList<News> news , int viewType) {
        this.mNewsList = news;
        this.mContext = context;
        this.mViewType = viewType;
        this.imageLoader = MySingleton.getInstance(context).getImageLoader();
        this.sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        this.sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        this.mNewsStream = MainActivity.mProfile.getCurrent_stream_name();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutID = R.layout.card_news_grid_view;

        if(this.mViewType == Constants.VIEW_INTO_LIST)
            layoutID = R.layout.card_news_list_view;

       View  rootView = LayoutInflater.from(this.mContext).inflate( layoutID,parent, false);
        try {
            return new NewsHolder(rootView, (OnNewsSelectListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnNewsSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        News news = this.mNewsList.get(position);
        NewsHolder newsHolder = (NewsHolder) holder;

        // set news title
        try {
            String newsName= new String(news.title.getBytes("ISO-8859-1"),"UTF-8");
            newsHolder.newsTitle.setText(newsName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            newsHolder.newsTitle.setText(news.title);
        }

        // load news image from server
        if (news.image != null && !news.image.isEmpty())
            newsHolder.newsImage.setImageUrl(news.image, imageLoader);

        ViewCompat.setTransitionName(newsHolder.newsImage, String.valueOf(position) + "_image");

        if(this.mViewType == Constants.VIEW_INTO_LIST) {

            // set news Content
            try {
                String newsContent= new String(news.content.getBytes("ISO-8859-1"),"UTF-8");
                newsHolder.newsContent.setText(Html.fromHtml(newsContent));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                newsHolder.newsContent.setText(Html.fromHtml(news.content));
            }

            // set news published date
            String d = "";
            try {
                sdf.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
                Date date = sdf.parse(news.published_on);
                sdf.applyPattern("MMMM d, yyyy KK:mm a");
                d = sdf.format(date);
            } catch (ParseException e) {
                Log.e(TAG, "Date format unknown: " + news.published_on);
            }
            newsHolder.newsPubDate.setText(d);

            if (position == 0 || this.mNewsChangedPosition == position)
            {
                if (position == 0)
                    newsHolder.streamTypeHeader.setText(MainActivity.mProfile.getCurrent_stream_name() == null?"News":MainActivity.mProfile.getCurrent_stream_name()+ " News");
                else
                    newsHolder.streamTypeHeader.setText("Other News");

                newsHolder.streamTypeHeader.setVisibility(View.VISIBLE);
                newsHolder.setIsRecyclable(false);
            }
            else if (news.getStream() == null && !this.mNewsStreamChanged) {
                newsHolder.streamTypeHeader.setText("Other News");
                newsHolder.streamTypeHeader.setVisibility(View.VISIBLE);
                newsHolder.setIsRecyclable(false);

                this.mNewsStreamChanged = true;
                this.mNewsChangedPosition = position;
            }

            //this.mSetAnimation(newsHolder.container, position);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }


    @Override
    public int getItemCount() {
        return this.mNewsList.size();
    }

    public void updateNewsAdapter(ArrayList<News> newsList){
        if(newsList == null)return;
        this.mNewsList = newsList;
        notifyDataSetChanged();
    }

    class NewsHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView newsTitle;
        TextView newsPubDate;
        TextView newsContent;
        NetworkImageView newsImage;
        OnNewsSelectListener mListener;
        TextView streamTypeHeader;

        public NewsHolder(View itemView, OnNewsSelectListener listener) {
            super(itemView);

            if(mViewType == Constants.VIEW_INTO_LIST) {
                newsPubDate = (TextView) itemView.findViewById(R.id.textview_news_pubdate);
                newsContent = (TextView) itemView.findViewById(R.id.textview_news_content);
                streamTypeHeader = (TextView) itemView.findViewById(R.id.card_news_heading);
            }
            newsTitle = (TextView) itemView.findViewById(R.id.textview_news_title);
            newsImage = (NetworkImageView) itemView.findViewById(R.id.image_news_collapsed);
            newsImage.setDefaultImageResId(R.drawable.ic_default_image);
            newsImage.setErrorImageResId(R.drawable.ic_default_image);
            mListener = listener;
            itemView.findViewById(R.id.card_news_container).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            boolean flag = false;
            if(mViewType == Constants.VIEW_INTO_LIST)
                flag = true;
            if (getAdapterPosition() < mNewsList.size())
                try {
                    mListener.onNewsSelected(mNewsList.get(getAdapterPosition()), flag, newsImage);
                }catch (Exception e){
                    e.printStackTrace();
                }
        }
    }
}
