package com.collegedekho.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.resource.DetectHtml;

import org.greenrobot.eventbus.EventBus;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Created by sureshsaini on 24/1/17.
 */

public class QnAAnswerListAdapterNew extends RecyclerView.Adapter {

    private static final String TAG = "QnAQuestionsListAdapter";
    private final int QUESTION_COUNT =0, QUESTION = 1, ANSWER = 2;
    private ArrayList<? super Object> mQnAQuestionAnswers;
    private volatile SimpleDateFormat mSDF;

    public QnAAnswerListAdapterNew(Context context, ArrayList qnaQuestionAnswers) {
        mQnAQuestionAnswers = qnaQuestionAnswers;
        mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
        mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    @Override
    public int getItemViewType(int position) {
        if(mQnAQuestionAnswers.get(position) instanceof String){
            return  QUESTION_COUNT;
        }else if(mQnAQuestionAnswers.get(position) instanceof QnAAnswers){
          return  ANSWER;
        }else if(mQnAQuestionAnswers.get(position) instanceof QnAQuestions){
            return  QUESTION;
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case ANSWER:
                View v1 = inflater.inflate(R.layout.layout_question_answer, parent, false);
                viewHolder = new QnAAnswerHolder(v1);
                break;
            case QUESTION:
                View v2 = inflater.inflate(R.layout.layout_qna_question, parent, false);
                viewHolder = new QnAQuestionHolder(v2);
                break;
            default:
                View v = inflater.inflate(R.layout.layout_qna_answer_count, parent, false);
                viewHolder = new QnAAnswerCountHolder(v);
                break;
        }
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewHolder.getItemViewType()) {
            case ANSWER:
                QnAAnswerHolder  viewHolder1 = (QnAAnswerHolder) viewHolder;
                configureViewHolder1(viewHolder1, position);
                break;
            case QUESTION:
                QnAQuestionHolder  viewHolder2 = (QnAQuestionHolder) viewHolder;
                configureViewHolder2(viewHolder2, position);
                break;
            default:
                QnAAnswerCountHolder  viewHolder3 = (QnAAnswerCountHolder) viewHolder;
                configureViewHolder3(viewHolder3, position);
                break;
        }
    }

    private void configureViewHolder1(QnAAnswerHolder viewHolder1, int position) {
        QnAAnswers qnaAnswer =(QnAAnswers) mQnAQuestionAnswers.get(position);
        QnAAnswerHolder qnaAnswerHolder = viewHolder1;
        String simpleDate = "";
        try {
            mSDF.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
            Date date = mSDF.parse(qnaAnswer.getAdded_on());
            mSDF.applyPattern("MMMM d, yyyy KK:mm a");
            simpleDate = mSDF.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + qnaAnswer.getAdded_on());
        }

        String description = qnaAnswer.getAnswer_text();
        if (DetectHtml.isHtml(description)) {
            Spanned result;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                result = Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY);
            } else {
                result = Html.fromHtml(description);
            }
            description = result.toString();
        }
        qnaAnswerHolder.answerText.setText(description);
        String userId = qnaAnswer.getUser_id();
        if (userId != null && userId.equalsIgnoreCase(MainActivity.mProfile.getId())) {
            qnaAnswerHolder.askedByUser.setText("Me");
        } else {
            qnaAnswerHolder.askedByUser.setText(qnaAnswer.getUser() +" - "+simpleDate);
        }
    }

    private void configureViewHolder2(QnAQuestionHolder viewHolder2, int position) {
        QnAQuestions qnAQuestions =(QnAQuestions) mQnAQuestionAnswers.get(position);
        QnAQuestionHolder qnaAnswerHolder = viewHolder2;
        if(qnAQuestions == null)
            return;
        qnaAnswerHolder.questionText.setText(qnAQuestions.getDesc());
    }

    private void configureViewHolder3(QnAAnswerCountHolder viewHolder3, int position) {

        String  answerMsg  =(String) mQnAQuestionAnswers.get(position);
        if(position != 0){
            viewHolder3.answerCountText.setText(answerMsg);
            viewHolder3.answerCountText.setBackgroundColor(Color.WHITE);
            viewHolder3.answerCountText.setTypeface(Typeface.DEFAULT_BOLD);
        }else {
            viewHolder3.answerCountText.setText(answerMsg);
        }
    }


    @Override
    public int getItemCount() {
        return mQnAQuestionAnswers.size();
    }


    private class QnAAnswerHolder extends RecyclerView.ViewHolder {
        CardView answerCard;
        TextView answerText;
        TextView askedByUser;
        public QnAAnswerHolder(View itemView) {
            super(itemView);
            answerCard = (CardView) itemView.findViewById(R.id.card_qna_answer);
            answerText = (TextView) itemView.findViewById(R.id.qna_answer_text);
            askedByUser = (TextView) itemView.findViewById(R.id.asked_by_user);
        }
    }

    private class QnAQuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView questionText;
        TextView askedByUser;
        View questionDivider;

        public QnAQuestionHolder(View itemView) {
            super(itemView);
            questionText = (TextView) itemView.findViewById(R.id.qna_question_text);
            questionDivider = itemView.findViewById(R.id.question_divider);
            questionDivider.setVisibility(View.GONE);
            askedByUser = (TextView) itemView.findViewById(R.id.asked_by_user);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
           EventBus.getDefault().post(new Event(AllEvents.ACTION_SIMILAR_QUESTION_CLICK, mQnAQuestionAnswers.get(getAdapterPosition()), null));
        }
    }
    private class QnAAnswerCountHolder extends RecyclerView.ViewHolder {
        TextView answerCountText;

        public QnAAnswerCountHolder(View itemView) {
            super(itemView);
            answerCountText  =(TextView)itemView;
        }
    }

}