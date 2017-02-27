package com.collegedekho.app.adapter;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.network.MySingleton;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * Created by sureshsaini on 30/9/16.
 */
public class NewsStackAdapter extends ArrayAdapter<News>{


    private List<News> mNewsList;
    private Context mContext;
    private ImageLoader imageLoader;

    public NewsStackAdapter(Context context, int card_news_grid_view, List<News> newsList){
        super(context,card_news_grid_view, newsList);
        this.mNewsList = newsList;
        this.mContext = context;
        this.imageLoader = MySingleton.getInstance(context).getImageLoader();
   }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View itemView = convertView;
        if (itemView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = layoutInflater.inflate(R.layout.card_news_grid_view, null);
        }
        TextView newsTitle = (TextView) itemView.findViewById(R.id.textview_news_title);
        NetworkImageView newsImage = (NetworkImageView) itemView.findViewById(R.id.image_news_collapsed);
        newsImage.setDefaultImageResId(R.drawable.ic_default_image);
        newsImage.setErrorImageResId(R.drawable.ic_default_image);
        News news = this.mNewsList.get(position);

        // set news title
        try {
            String newsName= new String(news.title.getBytes("ISO-8859-1"),"UTF-8");
            newsTitle.setText(newsName);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            newsTitle.setText(news.title);
        }

        // load news image from server
        if (news.image != null && !news.image.isEmpty())
            newsImage.setImageUrl(news.image, imageLoader);

        return itemView;
    }
}
