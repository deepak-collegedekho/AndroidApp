package com.collegedekho.app.display.feedViews;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.widget.FadeInImageView;

/**
 * Created by harshvardhan on 02/02/17.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {

    public CardView feedCard;
    public FadeInImageView feedIcon;
    public TextView feedTitle;
    public TextView feedTime;
    public FadeInImageView feedImage;
    public TextView feedDescription;
    public FeedFragment.onFeedInteractionListener mListener;

    public FeedViewHolder(View itemView, FeedFragment.onFeedInteractionListener listener) {
        super(itemView);

        this.feedCard = (CardView) itemView.findViewById(R.id.def_feed_card);
        this.feedIcon = (FadeInImageView) itemView.findViewById(R.id.def_feed_icon);
        this.feedTitle = (TextView) itemView.findViewById(R.id.def_feed_title);
        this.feedTime = (TextView) itemView.findViewById(R.id.def_feed_time);
        this.feedImage = (FadeInImageView) itemView.findViewById(R.id.def_feed_image);
        this.feedDescription = (TextView) itemView.findViewById(R.id.def_feed_desc);

        this.mListener = listener;
    }
}
