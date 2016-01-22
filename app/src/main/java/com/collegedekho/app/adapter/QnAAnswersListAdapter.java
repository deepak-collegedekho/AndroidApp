package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.fragment.QnAQuestionDetailFragment;
import com.collegedekho.app.resource.Constants;

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
    private ArrayList<QnAAnswers> mQnAQuestionAnswers;
    private Context mContext;
    private volatile SimpleDateFormat mSDF;

    public QnAAnswersListAdapter(Context context, ArrayList<QnAAnswers> qnaQuestionAnswers) {
        mQnAQuestionAnswers = qnaQuestionAnswers;
        mContext = context;
        mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_qna_answer, parent, false);
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_qna_answer_new_design, parent, false);
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
        try {
            mSDF.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
            Date date = mSDF.parse(qnaAnswer.getAdded_on());
            mSDF.applyPattern("MMMM d, yyyy KK:mm a");
            simpleDate = mSDF.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + qnaAnswer.getAdded_on());
        }

        qnaAnswerHolder.answerText.setText(qnaAnswer.getAnswer_text());
        qnaAnswerHolder.answerVotes.setText(String.valueOf(qnaAnswer.getUpvotes() - qnaAnswer.getDownvotes()));
        if (qnaAnswer.getUser() != MainActivity.user.getUsername())
        {
            qnaAnswerHolder.userName.setText(qnaAnswer.getUser());
            qnaAnswerHolder.answerCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.comment_card_background));
        }
        else
        {
            qnaAnswerHolder.answerCard.setCardBackgroundColor(mContext.getResources().getColor(R.color.self_comment_card_background));
            qnaAnswerHolder.userName.setText("Me");
        }

        qnaAnswerHolder.dateAddedOn.setText(simpleDate);

        qnaAnswerHolder.answerUpvoteButton.setSelected(qnaAnswer.getCurrent_user_vote_type() == Constants.LIKE_THING);
        qnaAnswerHolder.answerDownvoteButton.setSelected(qnaAnswer.getCurrent_user_vote_type() == Constants.DISLIKE_THING);
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

        public QnAAnswerHolder(View itemView, QnAQuestionDetailFragment.OnQnAAnswerInteractionListener listener) {
            super(itemView);

            answerCard = (CardView) itemView.findViewById(R.id.card_qna_answer);
            answerText = (TextView) itemView.findViewById(R.id.qna_answer_text);
            answerVotes = (TextView) itemView.findViewById(R.id.qna_answer_votes);
            userName = (TextView) itemView.findViewById(R.id.qna_answer_user_name);
            dateAddedOn = (TextView) itemView.findViewById(R.id.qna_answer_date_added_on);
            answerUpvoteButton = (ImageButton) itemView.findViewById(R.id.qna_answer_button_upvote);
            answerDownvoteButton = (ImageButton) itemView.findViewById(R.id.qna_answer_button_downvote);

            mListener = listener;

            answerUpvoteButton.setOnClickListener(this);
            answerDownvoteButton.setOnClickListener(this);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.qna_answer_button_downvote:
                {
                    if (v.isSelected())
                        Toast.makeText(mContext, "You can't downvote the downvoted", Toast.LENGTH_SHORT).show();
                    else
                        mListener.onQnAAnswerVote(mQnAQuestionAnswers.get(getAdapterPosition()).getResource_uri(), Constants.DISLIKE_THING, getAdapterPosition(), mQnAQuestionAnswers.get(getAdapterPosition()).getQuestionIndex());

                    break;
                }
                case R.id.qna_answer_button_upvote:
                {
                    if (v.isSelected())
                        Toast.makeText(mContext, "You can't upvote the upvoted", Toast.LENGTH_SHORT).show();
                    else
                        mListener.onQnAAnswerVote(mQnAQuestionAnswers.get(getAdapterPosition()).getResource_uri(), Constants.LIKE_THING, getAdapterPosition(), mQnAQuestionAnswers.get(getAdapterPosition()).getQuestionIndex());

                    break;
                }
                default:
                {
                    break;
                }
            }
        }
    }
}
