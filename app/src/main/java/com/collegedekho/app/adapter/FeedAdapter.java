package com.collegedekho.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.display.feedViews.FeedViewHolder;
import com.collegedekho.app.display.feedViews.ProfileViewHolder;
import com.collegedekho.app.display.feedViews.RecoFeedViewHolder;
import com.collegedekho.app.entities.Feed;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.Constants;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by harshvardhan on 16/11/16.
 */

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int mCardHeight;
    private ArrayList<Feed> mFeedList;
    private final Context mContext;
    private final ImageLoader mImageLoader;
    private FeedFragment.onFeedInteractionListener mListener;
    // Allows to remember the last item shown on screen
    public int lastPosition = -1;

    public static final int DEFAULT = 0;
   // public static final int FEED_QNA = 1;
    public static final int PROFILE_COMPLETION = 2;
    public static final int RECOMMENDED_INSTITUTES = 3;

    public FeedAdapter(Context context, ArrayList<Feed> feedList)
    {
        this.mFeedList = feedList;
        this.mContext = context;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();

        //Logic to draw card of width lesser than the device width, suggesting user about another cards.
        WindowManager wm = (WindowManager) this.mContext.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        int height;
        display.getSize(size);

        height = size.y;

        // 2/4 of the screen's height.
        //this.mCardHeight = ((height >> 2) + ((height >> 2) >> 1));
        this.mCardHeight = ((height >> 2) + (height >> 2));

        this.setHasStableIds(true);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(this.mContext);
        View view;
        switch (viewType) {
            case FeedAdapter.PROFILE_COMPLETION:
                view = inflater.inflate(R.layout.layout_profile_completion, parent, false);
                viewHolder = new ProfileViewHolder(view, this.mContext);
                break;
          /*  case FeedAdapter.FEED_QNA:
                view = inflater.inflate(R.layout.layout_feed_question, parent, false);
                viewHolder = new QnaViewHolder(view);
                break;*/
            case FeedAdapter.RECOMMENDED_INSTITUTES:
                view = inflater.inflate(R.layout.feed_reco_ui, parent, false);
                //view.setMinimumHeight(this.mCardHeight);
                viewHolder = new RecoFeedViewHolder(view, this.mContext);
                break;
            default:
                view = inflater.inflate(R.layout.feed_default_ui, parent, false);
                viewHolder = new FeedViewHolder(view, (FeedFragment.onFeedInteractionListener) this.mContext);
                break;
        }
        return viewHolder;
    }

    @Override
    public long getItemId(int position) {
        Feed feed = this.mFeedList.get(position);
        if (feed != null && feed.getId() != 0)
            return feed.getId();
        else
            return super.getItemId(position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        this.mOnBind(holder);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        this.mOnBind(holder);
    }

    private void mOnBind(RecyclerView.ViewHolder holder)
    {
        Feed feed = this.mFeedList.get(holder.getAdapterPosition());
        switch (feed.getScreen())
        {
            case Constants.PROFILE_COMPLETION_OTP:
                this.setProfileCompletionInfo((ProfileViewHolder) holder);
                break;
           /* case Constants.TAG_FRAGMENT_QNA_QUESTION_LIST:
                this.setQnaFeedDetail(feed,(QnaViewHolder) holder);
                break;*/
            case Constants.RECOMMENDED_INSTITUTE_FEED_LIST:
                this.setRecoFeedViewHolder(feed, (RecoFeedViewHolder) holder);
                break;
            default:
                this.setDefaultViewHolder(feed, (FeedViewHolder) holder);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        Feed feed = this.mFeedList.get(position);
        switch(feed.getScreen())
        {
            case Constants.PROFILE_COMPLETION_OTP:
                return FeedAdapter.PROFILE_COMPLETION;
           /* case Constants.TAG_FRAGMENT_QNA_QUESTION_LIST:
                return FeedAdapter.FEED_QNA;*/
            case Constants.RECOMMENDED_INSTITUTE_FEED_LIST:
                return FeedAdapter.RECOMMENDED_INSTITUTES;
            default:
                return FeedAdapter.DEFAULT;
        }
    }


    /**
     * Here is the key method to apply the animation
     */
    private void mSetAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(this.mContext, R.anim.fade_in);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }
/*
    public void not(ArrayList<Feed> feedList){
        this.mFeedList = feedList;
        notifyDataSetChanged();
    }*/

    private void setDefaultViewHolder(final Feed feed, final FeedViewHolder feedViewHolder)
    {
        //setting title
        String title = feed.getTitle();
        try {
            title = new String(title.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            title = feed.getTitle();
        }
        if (title != null && !title.isEmpty())
        {
            feedViewHolder.feedTitle.setText(title);
            feedViewHolder.feedTitle.setVisibility(View.VISIBLE);
            //setting feed title color
            if (feed.getTitle_color() != null && !feed.getTitle_color().isEmpty())
                feedViewHolder.feedTitle.setTextColor(Color.parseColor(feed.getTitle_color()));
            else
                feedViewHolder.feedTitle.setTextColor(this.mContext.getResources().getColor(R.color.heading_color));
        }
        else
            feedViewHolder.feedTitle.setVisibility(View.GONE);

        //setting time
        String time = feed.getFeed_time();
        try {
            time = new String(time.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            time = feed.getFeed_time();
        }
        if (time != null && !time.isEmpty())
        {
            feedViewHolder.feedTime.setText(time);
            feedViewHolder.feedTime.setVisibility(View.VISIBLE);

            //setting feed time color
            if (feed.getTime_color() != null && !feed.getTime_color().isEmpty())
                feedViewHolder.feedTime.setTextColor(Color.parseColor(feed.getTime_color()));
            else
                feedViewHolder.feedTime.setTextColor(this.mContext.getResources().getColor(R.color.text_light_grey));
        }
        else
            feedViewHolder.feedTime.setVisibility(View.GONE);

        //setting icon
        String iconURL = feed.getIcon();
        if (iconURL != null && !iconURL.isEmpty())
        {
            feedViewHolder.feedIcon.setImageUrl(feed.getIcon(), this.mImageLoader);
            feedViewHolder.feedIcon.setVisibility(View.VISIBLE);
        }
        else
            feedViewHolder.feedIcon.setVisibility(View.GONE);

        //setting image
        String imageURL = feed.getImage();
        if (imageURL != null && !imageURL.isEmpty())
        {
            feedViewHolder.feedImage.setImageUrl(feed.getImage(), this.mImageLoader);
            feedViewHolder.feedImage.setVisibility(View.VISIBLE);
        }
        else
            feedViewHolder.feedImage.setVisibility(View.GONE);

        //setting description
        String description = feed.getDescription();
        try {
            description = new String(description.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            description = feed.getDescription();
        }
        if (description != null && !description.isEmpty())
        {
            feedViewHolder.feedDescription.setText(description);
            feedViewHolder.feedDescription.setVisibility(View.VISIBLE);

            //setting feed description color
            if (feed.getDescription_color() != null && !feed.getDescription_color().isEmpty())
                feedViewHolder.feedDescription.setTextColor(Color.parseColor(feed.getDescription_color()));
            else
                feedViewHolder.feedDescription.setTextColor(this.mContext.getResources().getColor(R.color.subheading_color));
        }
        else
            feedViewHolder.feedDescription.setVisibility(View.GONE);

        //setting card background color
        if (feed.getFeed_background_color() != null && !feed.getFeed_background_color().isEmpty())
            feedViewHolder.feedCard.setBackgroundColor(Color.parseColor(feed.getFeed_background_color()));
        else
            feedViewHolder.feedCard.setBackgroundColor(this.mContext.getResources().getColor(R.color.card_bg));

        //send feed selected on click
        feedViewHolder.feedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedViewHolder.mListener.onFeedSelected(feed);
            }
        });

        //share
        final String feedShareURL = feed.getWeb_resource_uri();

        if (feedShareURL != null && !feedShareURL.isEmpty())
        {
            feedViewHolder.feedShare.setVisibility(View.VISIBLE);
            feedViewHolder.feedShareIcon.setVisibility(View.VISIBLE);

            final String finalTitle = title;
            final String type = feed.getScreen();
            feedViewHolder.feedShareIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FeedAdapter.this.mShareClick(type, finalTitle, feedShareURL);
                }
            });
            feedViewHolder.feedShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FeedAdapter.this.mShareClick(type, finalTitle, feedShareURL);
                }
            });
        }
        else
        {
            feedViewHolder.feedShare.setVisibility(View.GONE);
            feedViewHolder.feedShareIcon.setVisibility(View.GONE);
        }

        this.mSetAnimation(feedViewHolder.feedCard, feedViewHolder.getAdapterPosition());
    }

    private void mShareClick(String type, String title, String feedShareURL)
    {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            i.putExtra(Intent.EXTRA_SUBJECT, "CollegeDekho App");
            String shareableText;

            switch (type)
            {
                case Constants.TAG_FRAGMENT_NEWS_LIST:
                    shareableText = "Please see this news I found at CollegeDekho..\n";
                    break;
                case Constants.TAG_FRAGMENT_ARTICLES_LIST:
                    shareableText = "I found this Article at CollegeDekho..Please read..\n";
                    break;
                case Constants.TAG_FRAGMENT_QNA_QUESTION_LIST:
                    shareableText = "Hey I found this really awesome QnA at CollegeDekho..\n";
                    break;
                default:
                    shareableText = "Please check this Information..\n";
                    break;
            }
            shareableText = shareableText + "\n" + title + "\n";
            shareableText = shareableText + "\n"+ feedShareURL + "\n\n";
            shareableText = shareableText + "CollegeDekho \nDiscover.Prepare.Achieve";
            i.putExtra(Intent.EXTRA_TEXT, shareableText);
            this.mContext.startActivity(Intent.createChooser(i, "Share"));
            //Toast.makeText(mContext, shareableText, Toast.LENGTH_LONG).show();
        } catch(Exception e) {
            //e.toString();
        }
    }

    private void setRecoFeedViewHolder(final Feed feed, final RecoFeedViewHolder feedViewHolder)
    {
        //feedViewHolder.feedCard.setLayoutParams(new CardView.LayoutParams(CardView.LayoutParams.MATCH_PARENT, this.mCardHeight));
        feedViewHolder.recoFeedInstitutesUpdate(feed.getResult(), feedViewHolder.getAdapterPosition());

        this.mSetAnimation(feedViewHolder.feedCard, feedViewHolder.getAdapterPosition());
    }

    private void setProfileCompletionInfo(ProfileViewHolder viewHolder) {
        viewHolder.updateProfileCompletionBar();
    }

/*
    public void setQnaFeedDetail(final Feed feed , QnaViewHolder viewHolder) {
        viewHolder.questionText.setText(feed.getDescription());
        //setting time
        String time = feed.getFeed_time();
        try {
            time = new String(time.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            time = feed.getFeed_time();
        }
        if (time != null && !time.isEmpty())
        {
            viewHolder.askedByUser.setText(feed.getTitle()+" on "+time);
        }
    }*/
    @Override
    public int getItemCount() {
        return this.mFeedList.size();
    }

}