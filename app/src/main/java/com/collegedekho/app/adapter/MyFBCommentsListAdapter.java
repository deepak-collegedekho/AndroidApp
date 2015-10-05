package com.collegedekho.app.adapter;

import android.content.Context;
import android.media.Image;
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

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;

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

    public MyFBCommentsListAdapter(Context context, ArrayList<MyFutureBuddyComment> myFBCommentList) {
        this.mMyFBCommentList = myFBCommentList;
        this.mContext = context;
        this.mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        this.mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_my_fb_comment, parent, false);
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
        try
        {
            if (myFBComment.getAdded_on() != null)
            {
                mSDF.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
                Date date = mSDF.parse(myFBComment.getAdded_on());
                mSDF.applyPattern("MMMM d, yyyy KK:mm a");
                simpleDate = mSDF.format(date);
            }
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + myFBComment.getAdded_on());
        }

        qnaAnswerHolder.commentText.setText(myFBComment.getComment());
        qnaAnswerHolder.dateAddedOn.setText(simpleDate);

        if ((myFBComment.getToken()).equals(MainActivity.user.getToken()))
        {
            //if it is my comment
            /*LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.weight = 1.0f;
            params.gravity = Gravity.RIGHT;

            qnaAnswerHolder.myFbCardLayout.setLayoutParams(params);*/

            qnaAnswerHolder.myFbCardLayout.setGravity(Gravity.RIGHT);
            qnaAnswerHolder.myFbCard.setCardBackgroundColor(this.mContext.getResources().getColor(R.color.self_comment_card_background));

            if (myFBComment.isCommentSent())
                qnaAnswerHolder.mSentNotifier.setVisibility(View.VISIBLE);

            qnaAnswerHolder.userName.setVisibility(View.GONE);
            qnaAnswerHolder.setIsRecyclable(false);
        }
        else
        {
            qnaAnswerHolder.myFbCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.comment_card_background));

            qnaAnswerHolder.userName.setText(myFBComment.getUser());
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
        LinearLayout myFbCardLayout;
        CardView myFbCard;
        ImageView mSentNotifier;

        MyFutureBuddiesFragment.OnMyFBInteractionListener mListener;

        public MyFBCommentsHolder(View itemView, MyFutureBuddiesFragment.OnMyFBInteractionListener listener)
        {
            super(itemView);

            this.commentText = (TextView) itemView.findViewById(R.id.my_fb_comment_text);
            this.userName = (TextView) itemView.findViewById(R.id.my_fb_comment_user_name);
            this.dateAddedOn = (TextView) itemView.findViewById(R.id.my_fb_comment_date_added_on);
            this.myFbCardLayout = (LinearLayout) itemView.findViewById(R.id.my_fb_card_layout);
            this.myFbCard = (CardView) itemView.findViewById(R.id.my_fb_card);
            this.mSentNotifier = (ImageView) itemView.findViewById(R.id.my_fb_comment_sent_notifier);

            this.mListener = listener;
        }
    }

}
