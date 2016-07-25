package com.collegedekho.app.adapter;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author harshvardhan
 *         Created: 17/08/15
 */
public class MyFBCommentsListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "QnAQuestionsListAdapter";
    private ArrayList<MyFutureBuddyComment> mMyFBCommentList;
    private Context mContext;
    private volatile SimpleDateFormat mSDF;
    private final ImageLoader mImageLoader;
    public boolean IS_UP_SCROLLING = true;



    public MyFBCommentsListAdapter(Context context, ArrayList<MyFutureBuddyComment> myFBCommentList) {
        this.mMyFBCommentList = myFBCommentList;
        this.mContext = context;
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.my_fb_comment_card, parent, false);
        try {
            return new MyFBCommentsHolder(rootView, (MyFutureBuddiesFragment.OnMyFBInteractionListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnMyFBInteractionListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyFutureBuddyComment myFBComment = mMyFBCommentList.get(position);
        MyFBCommentsHolder qnaAnswerHolder = (MyFBCommentsHolder) holder;
        String simpleDate = "";
        String shortDate = "";
        String time = "";

        //set date
        try
        {
            if (myFBComment.getAdded_on() != null)
            {
                this.mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                this.mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));

                Date date = this.mSDF.parse(myFBComment.getAdded_on());

                //Get Full date
                this.mSDF.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
                this.mSDF.applyPattern("MMMM d, yyyy KK:mm a");
                simpleDate = this.mSDF.format(date);

                //Get Time
                this.mSDF.applyLocalizedPattern("HH:mm");
                time = this.mSDF.format(date);

                //Get Date
                this.mSDF.applyLocalizedPattern("yyyy-MM-dd");
                shortDate = this.mSDF.format(date);

                if(position == 0)
                {
                    qnaAnswerHolder.date.setVisibility(View.VISIBLE);
                    qnaAnswerHolder.date.setText(String.valueOf(shortDate));
                }
                if(position >0 )
                {
                    Date prevDate = this.mSDF.parse(mMyFBCommentList.get(position -1).getAdded_on());
                    String prevShortDate = this.mSDF.format(prevDate);

                    if(!prevShortDate.equalsIgnoreCase(shortDate))
                    {
                        qnaAnswerHolder.date.setVisibility(View.VISIBLE);
                        qnaAnswerHolder.date.setText(String.valueOf(shortDate));
                    }
                    else
                    {
                        qnaAnswerHolder.date.setVisibility(View.GONE);
                    }
                }
            }
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + myFBComment.getAdded_on());
        }

        qnaAnswerHolder.dateAddedOn.setText(simpleDate);
        qnaAnswerHolder.time.setText(String.valueOf(time));

        //set comment
        qnaAnswerHolder.commentText.setText(myFBComment.getComment());

        if ((myFBComment.getToken()).equals(MainActivity.mProfile.getToken()))
        {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                qnaAnswerHolder.myFbCard.setBackgroundResource(R.drawable.ic_chat_inline);
            }
            else{
                qnaAnswerHolder.myFbCard.setCardBackgroundColor(this.mContext.getResources().getColor(R.color.self_comment_card_background));

            }
            qnaAnswerHolder.myFbCardLayout.setGravity(Gravity.RIGHT);
            int left = Utils.getPadding(mContext,10);
            int right = Utils.getPadding(mContext,10);
            int bottom = Utils.getPadding(mContext,25);
            qnaAnswerHolder.myFbCardLayout.setPadding(left,0,right,0);
            qnaAnswerHolder.time.setPadding(left,0,right,bottom);
            qnaAnswerHolder.time.setTextColor(this.mContext.getResources().getColor(R.color.chat_time_blue));

            qnaAnswerHolder.mUserImageSelf.setVisibility(View.VISIBLE);
            qnaAnswerHolder.mUserImageOther.setVisibility(View.GONE);

            if (myFBComment.isCommentSent())
                qnaAnswerHolder.mSentNotifier.setVisibility(View.VISIBLE);

            qnaAnswerHolder.userName.setVisibility(View.VISIBLE);
            qnaAnswerHolder.userName.setText(myFBComment.getUser());
            qnaAnswerHolder.setIsRecyclable(false);

            updateUserImage(myFBComment.getUser_image(), qnaAnswerHolder.mUserImageSelf);
            qnaAnswerHolder.mUserImageOther.setPadding(left,0,right,0);
        }
        else
        {
            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                qnaAnswerHolder.myFbCard.setBackgroundResource(R.drawable.ic_chat_inline_others);
            }
            else{
                qnaAnswerHolder.myFbCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.comment_card_background));
           }
            int left = Utils.getPadding(mContext,10);
            int right = Utils.getPadding(mContext,10);
            int top = Utils.getPadding(mContext,20);
            int bottom = Utils.getPadding(mContext,5);
            qnaAnswerHolder.userName.setPadding(left,0,right,0);
            qnaAnswerHolder.time.setPadding(left,0,right,bottom);
            qnaAnswerHolder.time.setTextColor(mContext.getResources().getColor(R.color.chat_time_gray));
            qnaAnswerHolder.userName.setText(myFBComment.getUser());

            qnaAnswerHolder.mUserImageSelf.setVisibility(View.GONE);
            qnaAnswerHolder.mUserImageOther.setVisibility(View.VISIBLE);

            updateUserImage(myFBComment.getUser_image(), qnaAnswerHolder.mUserImageOther);
            qnaAnswerHolder.mUserImageOther.setPadding(left,top,right,0);
        }
    }

    public void updateUserImage(String image, CircularImageView imageView){
        imageView.setDefaultImageResId(R.drawable.ic_profile_default);
        imageView.setErrorImageResId(R.drawable.ic_profile_default);

        if (image != null && !image.isEmpty())
            imageView.setImageUrl(image, mImageLoader);
    }

    @Override
    public int getItemCount() {
        return mMyFBCommentList.size();
    }

    private class MyFBCommentsHolder extends RecyclerView.ViewHolder{
        TextView commentText;
        TextView userName;
        TextView dateAddedOn;
        TextView time;
        TextView date;
        LinearLayout myFbCardLayout;
        CardView myFbCard;
        ImageView mSentNotifier;
        CircularImageView mUserImageOther;
        CircularImageView mUserImageSelf;

        MyFutureBuddiesFragment.OnMyFBInteractionListener mListener;

        public MyFBCommentsHolder(View itemView, MyFutureBuddiesFragment.OnMyFBInteractionListener listener)
        {
            super(itemView);
            this.commentText = (TextView) itemView.findViewById(R.id.my_fb_comment_text);
            this.userName = (TextView) itemView.findViewById(R.id.my_fb_comment_user_name);
            this.dateAddedOn = (TextView) itemView.findViewById(R.id.my_fb_comment_date_added_on);
            this.myFbCardLayout = (LinearLayout) itemView.findViewById(R.id.my_fb_comment_content);
            this.myFbCard = (CardView) itemView.findViewById(R.id.my_fb_card);
            this.mSentNotifier = (ImageView) itemView.findViewById(R.id.my_fb_comment_sent_notifier);
            this.time = (TextView) itemView.findViewById(R.id.my_fb_comment_time);
            this.date = (TextView) itemView.findViewById(R.id.my_fb_comment_date);
            this.mUserImageOther = (CircularImageView) itemView.findViewById(R.id.my_fb_comment_user_image);
            this.mUserImageSelf = (CircularImageView) itemView.findViewById(R.id.my_fb_comment_user_image_self);

            this.mListener = listener;
        }
    }
}