package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;

import java.util.ArrayList;

/**
 * @author harshvardhan
 *         Created: 09/08/15
 */
public class QnAQuestionsListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "QnAQuestionsListAdapter";
    private ArrayList<QnAQuestions> mQnAQuestions;
    private Context mContext;
    private int lastPosition = -1;

    public QnAQuestionsListAdapter(Context context, ArrayList<QnAQuestions> qnaQuestions) {
        this.mQnAQuestions = qnaQuestions;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(mContext).inflate(R.layout.card_qna_question, parent, false);
        try {
            return new QnAQuestionHolder(rootView, (QnAQuestionsListFragment.OnQnAQuestionSelectedListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnQnAQuestionSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        QnAQuestions qnaQuestion = mQnAQuestions.get(position);

        QnAQuestionHolder qnAQuestionHolder = (QnAQuestionHolder) holder;

        qnAQuestionHolder.questionHeading.setText(qnaQuestion.getTitle());
        qnAQuestionHolder.questionViews.setText(String.valueOf(qnaQuestion.getView_count()) + " Views");
        qnAQuestionHolder.questionVotes.setText(String.valueOf(qnaQuestion.getUpvotes() - qnaQuestion.getDownvotes()));
        qnAQuestionHolder.answerCount.setText(String.valueOf(qnaQuestion.getAnswers_count()));

        ArrayList<String> tags = qnaQuestion.getTags();

        if(tags != null)
            for(int i = 0; i < tags.size(); i++)
            {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);

                tv.setText(tags.get(i));

                qnAQuestionHolder.tagsContainer.addView(tv);
            }
        this.setAnimation(qnAQuestionHolder.container, position);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    @Override
    public int getItemCount() {
        return mQnAQuestions.size();
    }

    private class QnAQuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView questionHeading;
        TextView questionViews;
        TextView questionVotes;
        TextView answerCount;
        LinearLayout tagsContainer;
        QnAQuestionsListFragment.OnQnAQuestionSelectedListener mListener;
        CardView container;

        public QnAQuestionHolder(View itemView, QnAQuestionsListFragment.OnQnAQuestionSelectedListener listener) {
            super(itemView);

            this.questionHeading = (TextView) itemView.findViewById(R.id.question_heading);
            this.questionViews = (TextView) itemView.findViewById(R.id.question_views);
            this.questionVotes = (TextView) itemView.findViewById(R.id.vote_count);
            this.answerCount = (TextView) itemView.findViewById(R.id.answer_count);
            this.tagsContainer = (LinearLayout) itemView.findViewById(R.id.tags_container);
            this.container = (CardView) itemView.findViewById(R.id.card_qna_container);

            this.mListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onQnAQuestionSelected(mQnAQuestions.get(getAdapterPosition()));
        }
    }
}
