package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;

import org.greenrobot.eventbus.EventBus;

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
    private LayoutInflater mLayoutInflater;

    public MyFBCommentsListAdapter(Context context, ArrayList<MyFutureBuddyComment> myFBCommentList) {
        this.mMyFBCommentList = myFBCommentList;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
        this.mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
    }

    public void setmMyFBCommentList(ArrayList<MyFutureBuddyComment> myFBCommentList){
        this.mMyFBCommentList = myFBCommentList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = mLayoutInflater.inflate(R.layout.my_fb_comment_card, parent, false);
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
        final MyFBCommentsHolder myFBCommentsHolder = (MyFBCommentsHolder) holder;
        String simpleDate = "";
        String shortDate = "";
        String time = "";
        //set date
        try
        {
            String commentAddedDate = myFBComment.getAdded_on();
            if (commentAddedDate != null)
            {
                this.mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                this.mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
                Date date = this.mSDF.parse(commentAddedDate);

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

                if(position == 0) {
                    myFBCommentsHolder.date.setVisibility(View.VISIBLE);
                    myFBCommentsHolder.date.setText(String.valueOf(shortDate));
                }
                if(position >0 ) {
                    String prevCommentDate = mMyFBCommentList.get(position -1).getAdded_on();
                    if(prevCommentDate != null) {
                        Date prevDate = this.mSDF.parse(prevCommentDate);
                        String prevShortDate = this.mSDF.format(prevDate);

                        if (!prevShortDate.equalsIgnoreCase(shortDate)) {
                            myFBCommentsHolder.date.setVisibility(View.VISIBLE);
                            myFBCommentsHolder.date.setText(String.valueOf(shortDate));
                        } else {
                            myFBCommentsHolder.date.setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + myFBComment.getAdded_on());
        }

        myFBCommentsHolder.dateAddedOn.setText(simpleDate);
        myFBCommentsHolder.time.setText(String.valueOf(time));

        // set Chat description
        String commentToken = myFBComment.getToken();
        if (commentToken == null || commentToken.isEmpty()){
            int commentType = myFBComment.getIs_user();
            if(commentType == Constants.USER_COMMENT){
                setMyOwnComment(myFBCommentsHolder, myFBComment);
            }else{
                setOtherUsersComment(myFBCommentsHolder, myFBComment);
            }
        }else if(commentToken.equals(MainActivity.mProfile.getToken())) {
            setMyOwnComment(myFBCommentsHolder, myFBComment);
        } else {
            setOtherUsersComment(myFBCommentsHolder, myFBComment);
        }

    }

    private void setMyOwnComment(MyFBCommentsHolder myFBCommentsHolder, MyFutureBuddyComment myFBComment){

        myFBCommentsHolder.myFbCard.setCardBackgroundColor(this.mContext.getResources().getColor(R.color.self_comment_card_background));

            /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                myFBCommentsHolder.myFbCard.getBackground().setAlpha(0);
                myFBCommentsHolder.myFbCommentContainer.setBackgroundResource(R.drawable.chat_bg);
            } else {
                myFBCommentsHolder.myFbCard.getBackground().setAlpha(1);
                myFBCommentsHolder.myFbCard.setCardBackgroundColor(this.mContext.getResources().getColor(R.color.self_comment_card_background));
            }*/

        myFBCommentsHolder.myFbCardLayout.setGravity(Gravity.RIGHT);
        int left = Utils.getPadding(mContext,10);
        int right = Utils.getPadding(mContext,10);
        int top = Utils.getPadding(mContext,5);
        int bottom = Utils.getPadding(mContext,10);

        //myFBCommentsHolder.myFbCardLayout.setPadding(left,0,right,0);
        myFBCommentsHolder.time.setPadding(left,0,right,bottom);
        myFBCommentsHolder.time.setTextColor(this.mContext.getResources().getColor(R.color.chat_time_gray));

        myFBCommentsHolder.mUserImageSelf.setVisibility(View.VISIBLE);
        myFBCommentsHolder.mUserImageOther.setVisibility(View.GONE);
        myFBCommentsHolder.mUserImageSelf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event(AllEvents.ACTION_USER_DP_CLICK,null, null));
            }
        });

        if (myFBComment.isCommentSent())
            myFBCommentsHolder.mSentNotifier.setVisibility(View.VISIBLE);

        myFBCommentsHolder.userName.setPadding(left,top,right,0);
        myFBCommentsHolder.userName.setVisibility(View.VISIBLE);
        myFBCommentsHolder.userName.setText(myFBComment.getUser());
        myFBCommentsHolder.setIsRecyclable(false);

        updateUserImage(myFBComment.getUser_image(), myFBCommentsHolder.mUserImageSelf);
        myFBCommentsHolder.mUserImageOther.setPadding(left,0,right,0);

        String description = myFBComment.getComment();
        //set comment
        myFBCommentsHolder.commentText.setText(Html.fromHtml(description));
        description = "You said " + description;
        myFBCommentsHolder.mContainer.setContentDescription(description);
    }

    private void setOtherUsersComment(MyFBCommentsHolder myFBCommentsHolder, MyFutureBuddyComment myFBComment){

        myFBCommentsHolder.myFbCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.comment_card_background));

            /*if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                myFBCommentsHolder.myFbCard.setBackgroundResource(R.drawable.chat_bg);
            } else {
                myFBCommentsHolder.myFbCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.comment_card_background));
            }*/

        int left = Utils.getPadding(mContext,10);
        int right = Utils.getPadding(mContext,10);
        int top = Utils.getPadding(mContext,5);
        int bottom = Utils.getPadding(mContext,10);

        myFBCommentsHolder.mUserImageOther.setPadding(0,top,0,0);
        myFBCommentsHolder.mUserImageSelf.setPadding(0,top,0,0);
        myFBCommentsHolder.userName.setPadding(left,top,right,0);
        myFBCommentsHolder.time.setPadding(left,0,right,bottom);
        myFBCommentsHolder.time.setTextColor(mContext.getResources().getColor(R.color.chat_time_gray));
        myFBCommentsHolder.userName.setText(myFBComment.getUser());

        myFBCommentsHolder.mUserImageSelf.setVisibility(View.GONE);
        myFBCommentsHolder.mUserImageOther.setVisibility(View.VISIBLE);
        myFBCommentsHolder.mSentNotifier.setVisibility(View.GONE);
        updateUserImage(myFBComment.getUser_image(), myFBCommentsHolder.mUserImageOther);
        myFBCommentsHolder.mUserImageOther.setPadding(left,top,right,0);
        myFBCommentsHolder.mUserImageOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new Event(AllEvents.ACTION_USER_DP_CLICK,null, null));
            }
        });


        String description = myFBComment.getComment();
        //set comment
        myFBCommentsHolder.commentText.setText(Html.fromHtml(description));
        description = myFBComment.getUser() + " " + description;
        myFBCommentsHolder.mContainer.setContentDescription(description);
    }

    private void updateUserImage(String image, CircularImageView imageView){
        imageView.setDefaultImageResId(R.drawable.ic_profile_default);
        imageView.setErrorImageResId(R.drawable.ic_profile_default);

        if (image != null && !image.isEmpty()) {
            imageView.setImageUrl(image, mImageLoader);
        }else{
            imageView.setImageUrl(null, mImageLoader);
        }
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
        View mContainer;

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
            this.mContainer = itemView;

            this.mListener = listener;
        }
    }
}