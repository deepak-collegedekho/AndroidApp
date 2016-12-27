package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.fragment.QnAQuestionDetailFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.DetectHtml;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.widget.CircularImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author harshvardhan
 *         Created: 12/08/15
 */
public class QnAAnswersListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "QnAQuestionsListAdapter";
    private final int mQnAPosition;
    private ArrayList<QnAAnswers> mQnAQuestionAnswers;
    private Context mContext;
    private volatile SimpleDateFormat mSDF;
    private ImageLoader mImageLoader;

    public QnAAnswersListAdapter(Context context, ArrayList<QnAAnswers> qnaQuestionAnswers, int mQnAPosition) {
        mQnAQuestionAnswers = qnaQuestionAnswers;
        mContext = context;
        this.mQnAPosition = mQnAPosition;
        mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        mImageLoader = MySingleton.getInstance(this.mContext).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_qna_answer, parent, false);
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_qna_answer, parent, false);
        try {
            return new QnAAnswerHolder(rootView, (QnAQuestionDetailFragment.OnQnAAnswerInteractionListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnQnAQuestionSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QnAAnswers qnaAnswer = mQnAQuestionAnswers.get(position);
        QnAAnswerHolder qnaAnswerHolder = (QnAAnswerHolder) holder;
        String simpleDate = "";
        String description = "";
        try {
            mSDF.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
            Date date = mSDF.parse(qnaAnswer.getAdded_on());
            mSDF.applyPattern("MMMM d, yyyy KK:mm a");
            simpleDate = mSDF.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + qnaAnswer.getAdded_on());
        }

         String answerText = qnaAnswer.getAnswer_text();
        if(DetectHtml.isHtml(answerText)){
            Spanned result;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml(answerText,Html.FROM_HTML_MODE_LEGACY);
            } else {
                result = Html.fromHtml(answerText);
            }
            answerText = result.toString();
        }
        description= description+answerText;
        qnaAnswerHolder.answerText.setText(answerText);
        qnaAnswerHolder.answerVotes.setText(String.valueOf(qnaAnswer.getUpvotes() - qnaAnswer.getDownvotes()));
        String userId = qnaAnswer.getUser_id();
        if (userId != null && userId.equalsIgnoreCase(MainActivity.mProfile.getId()))
        {
            qnaAnswerHolder.answerCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.self_comment_card_background));
            qnaAnswerHolder.userName.setText("Me");
            description = "you answered " + description;
        } else  {
            qnaAnswerHolder.userName.setText(qnaAnswer.getUser());
            qnaAnswerHolder.answerCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.comment_card_background));
            description = qnaAnswer.getUser() + " answered " + description;
        }

        qnaAnswerHolder.mContainer.setContentDescription(description);
        qnaAnswerHolder.dateAddedOn.setText(simpleDate);
        qnaAnswerHolder.answerUpvoteButton.setClickable(true);
        qnaAnswerHolder.answerDownvoteButton.setClickable(true);
        qnaAnswerHolder.answerUpvoteButton.setSelected(qnaAnswer.getCurrent_user_vote_type() == Constants.LIKE_THING);
        qnaAnswerHolder.answerDownvoteButton.setSelected(qnaAnswer.getCurrent_user_vote_type() == Constants.DISLIKE_THING);
        updateUserImage(qnaAnswer.getUser_image(), qnaAnswerHolder.userImage);
    }

    private void updateUserImage(String image, CircularImageView imageView){
        imageView.setDefaultImageResId(R.drawable.ic_profile_default);
        imageView.setErrorImageResId(R.drawable.ic_profile_default);

        if (image != null && !image.isEmpty()) {
            imageView.setImageUrl(image, mImageLoader);
        }else{
            imageView.setImageUrl(null,mImageLoader);
        }
    }

    @Override
    public int getItemCount() {
        return mQnAQuestionAnswers.size();
    }


    private class QnAAnswerHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView answerText;
        TextView answerVotes;
        TextView userName;
        TextView dateAddedOn;
        ImageButton answerUpvoteButton;
        ImageButton answerDownvoteButton;
        CardView answerCard;
        QnAQuestionDetailFragment.OnQnAAnswerInteractionListener mListener;
        View mContainer;
        CircularImageView userImage;

        public QnAAnswerHolder(View itemView, QnAQuestionDetailFragment.OnQnAAnswerInteractionListener listener) {
            super(itemView);

            answerCard = (CardView) itemView.findViewById(R.id.card_qna_answer);
            answerText = (TextView) itemView.findViewById(R.id.qna_answer_text);
            answerVotes = (TextView) itemView.findViewById(R.id.qna_answer_votes);
            userName = (TextView) itemView.findViewById(R.id.qna_answer_user_name);
            dateAddedOn = (TextView) itemView.findViewById(R.id.qna_answer_date_added_on);
            answerUpvoteButton = (ImageButton) itemView.findViewById(R.id.qna_answer_button_upvote);
            answerDownvoteButton = (ImageButton) itemView.findViewById(R.id.qna_answer_button_downvote);
            userImage = (CircularImageView) itemView.findViewById(R.id.user_image);
            mContainer = itemView;

            mListener = listener;

            answerUpvoteButton.setOnClickListener(this);
            answerDownvoteButton.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int connectivityStatus=new NetworkUtils(v.getContext(), null).getConnectivityStatus();
            switch (v.getId())
            {
                case R.id.qna_answer_button_downvote:
                    if (connectivityStatus != Constants.TYPE_NOT_CONNECTED) {
                        int position = getAdapterPosition();
                        if(position < mQnAQuestionAnswers.size()) {
                            QnAAnswers qnAAnswer = mQnAQuestionAnswers.get(position);
                            if (qnAAnswer.getCurrent_user_vote_type() == Constants.DISLIKE_THING) {
                                Toast.makeText(mContext, "You can't downvote the downvoted", Toast.LENGTH_SHORT).show();
                            }else{
                                answerDownvoteButton.setClickable(false);
                                if(qnAAnswer.getCurrent_user_vote_type() == Constants.LIKE_THING) {
                                    mListener.onQnAAnswerVote( Constants.NOT_INTERESTED_THING, position, mQnAPosition);
                                }else{
                                    mListener.onQnAAnswerVote( Constants.DISLIKE_THING, position, mQnAPosition);
                                }
                            }
                        }
                    }else {
                        this.mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);

                    }
                    break;

                case R.id.qna_answer_button_upvote:
                    if (connectivityStatus != Constants.TYPE_NOT_CONNECTED) {

                        int position = getAdapterPosition();
                        if(position < mQnAQuestionAnswers.size()) {

                            QnAAnswers qnAAnswer = mQnAQuestionAnswers.get(position);
                            if (qnAAnswer.getCurrent_user_vote_type() == Constants.LIKE_THING) {
                                Toast.makeText(mContext, "You can't upvote the upvoted", Toast.LENGTH_SHORT).show();
                            } else {
                                answerUpvoteButton.setClickable(false);
                                if (qnAAnswer.getCurrent_user_vote_type() == Constants.DISLIKE_THING) {
                                    mListener.onQnAAnswerVote(Constants.NOT_INTERESTED_THING, position, mQnAPosition);

                                } else {
                                    mListener.onQnAAnswerVote(Constants.LIKE_THING, position, mQnAPosition);
                                }
                            }
                        }
                    }else {
                        this.mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                    }
                    break;

                default:
                {
                    break;
                }
            }
        }
    }
}
