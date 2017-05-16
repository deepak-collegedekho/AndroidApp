package com.collegedekho.app.display.feedViews;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.widget.FadeInImageView;

/**
 * Created by harshvardhan on 02/02/17.
 */

public class FeedViewHolder extends RecyclerView.ViewHolder {

    public ImageView feedStudyAbroad;
    public CardView feedCard;
    public FadeInImageView feedIcon;
    public TextView feedTitle;
    public TextView feedTime;
    public FadeInImageView feedImage;
    public TextView feedDescription;
    public TextView feedShare;
    public ImageView feedShareIcon;
    public FeedFragment.onFeedInteractionListener mListener;

    public FeedViewHolder(View itemView, FeedFragment.onFeedInteractionListener listener) {
        super(itemView);

        this.feedCard = (CardView) itemView.findViewById(R.id.def_feed_card);
        this.feedIcon = (FadeInImageView) itemView.findViewById(R.id.def_feed_icon);
        this.feedStudyAbroad = (ImageView) itemView.findViewById(R.id.def_feed_study_abroad);
        this.feedTitle = (TextView) itemView.findViewById(R.id.def_feed_title);
        this.feedTime = (TextView) itemView.findViewById(R.id.def_feed_time);
        this.feedImage = (FadeInImageView) itemView.findViewById(R.id.def_feed_image);
        this.feedDescription = (TextView) itemView.findViewById(R.id.def_feed_desc);
        this.feedShare = (TextView) itemView.findViewById(R.id.def_feed_share);
        this.feedShareIcon = (ImageView) itemView.findViewById(R.id.def_feed_share_icon);

        this.mListener = listener;
    }
}
