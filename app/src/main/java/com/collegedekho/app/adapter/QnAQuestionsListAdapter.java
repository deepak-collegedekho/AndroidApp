package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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

        public QnAQuestionHolder(View itemView, QnAQuestionsListFragment.OnQnAQuestionSelectedListener listener) {
            super(itemView);

            questionHeading = (TextView) itemView.findViewById(R.id.question_heading);
            questionViews = (TextView) itemView.findViewById(R.id.question_views);
            questionVotes = (TextView) itemView.findViewById(R.id.vote_count);
            answerCount = (TextView) itemView.findViewById(R.id.answer_count);
            tagsContainer = (LinearLayout) itemView.findViewById(R.id.tags_container);

            mListener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onQnAQuestionSelected(mQnAQuestions.get(getAdapterPosition()));
        }
    }
}
