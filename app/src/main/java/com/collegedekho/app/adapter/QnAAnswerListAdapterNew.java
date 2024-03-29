package com.collegedekho.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.DetectHtml;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;


/**
 * Created by sureshsaini on 24/1/17.
 */

public class QnAAnswerListAdapterNew extends RecyclerView.Adapter {

    private static final String TAG = "QnAQuestionsListAdapter";
    private final int QUESTION_COUNT =0, QUESTION = 1, ANSWER = 2;
    private ArrayList<? super Object> mQnAQuestionAnswers;
    private Context mContext;
    private final ImageLoader mImageLoader;

    public QnAAnswerListAdapterNew(Context context, ArrayList qnaQuestionAnswers) {
        this.mQnAQuestionAnswers = qnaQuestionAnswers;
        this.mContext = context;
        this.mImageLoader = MySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public int getItemViewType(int position) {
        if(this.mQnAQuestionAnswers.get(position) instanceof String){
            return  QUESTION_COUNT;
        }else if(this.mQnAQuestionAnswers.get(position) instanceof QnAAnswers){
          return  ANSWER;
        }else if(this.mQnAQuestionAnswers.get(position) instanceof QnAQuestions){
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
        QnAAnswers qnaAnswer =(QnAAnswers) this.mQnAQuestionAnswers.get(position);
        QnAAnswerHolder qnaAnswerHolder = viewHolder1;

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
        qnaAnswerHolder.userProfilePic.setDefaultImageResId(R.drawable.ic_profile_default_vector);
        qnaAnswerHolder.userProfilePic.setImageUrl(qnaAnswer.getUser_image(),mImageLoader);
        String userId = qnaAnswer.getUser_id();
        if (userId != null && userId.equalsIgnoreCase(MainActivity.mProfile.getId())) {
            qnaAnswerHolder.askedByUser.setText("Me");
        } else {
            qnaAnswerHolder.askedByUser.setText(qnaAnswer.getUser() +"  - "+qnaAnswer.getAdded_on()+" - "+qnaAnswer.getUser_role());
        }
    }

    private void configureViewHolder2(QnAQuestionHolder viewHolder2, int position) {
        QnAQuestions qnAQuestions =(QnAQuestions) this.mQnAQuestionAnswers.get(position);
        QnAQuestionHolder qnaQuestionHolder = viewHolder2;
        if(qnAQuestions == null)
            return;
        qnaQuestionHolder.questionText.setText(qnAQuestions.getDesc());
        qnaQuestionHolder.userProfilePic.setDefaultImageResId(R.drawable.ic_profile_default_vector);
        qnaQuestionHolder.userProfilePic.setImageUrl(qnAQuestions.getUser_image(),mImageLoader);
        qnaQuestionHolder.askedByUser.setText(qnAQuestions.getUser() +"  - "+qnAQuestions.getAdded_on()+" - "+qnAQuestions.getUser_role());

    }

    private void configureViewHolder3(QnAAnswerCountHolder viewHolder3, int position) {

        String  answerMsg  =(String) this.mQnAQuestionAnswers.get(position);
        int m15dp = Utils.getDimensionPixelSize(mContext, R.dimen.m15dp);
        int m8dp = Utils.getDimensionPixelSize(mContext, R.dimen.m8dp);

        if(position != 0){
            viewHolder3.answerCountText.setBackgroundColor(Color.WHITE);
            viewHolder3.answerCountText.setTypeface(Typeface.DEFAULT_BOLD);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(0, Utils.getDimensionPixelSize(mContext, R.dimen.m20dp),0,0);
            viewHolder3.answerCountText.setLayoutParams(params);
            viewHolder3.answerCountText.setTypeface(Typeface.DEFAULT_BOLD);
            viewHolder3.answerCountText.setPadding(m15dp,m15dp,m8dp,m8dp);
            viewHolder3.answerCountText.setText(answerMsg);
            viewHolder3.answerCountText.setOnClickListener(null);
        }else {

            viewHolder3.answerCountText.setBackgroundColor(mContext.getResources().getColor(R.color.transparent));
            viewHolder3.answerCountText.setTypeface(Typeface.DEFAULT);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            viewHolder3.answerCountText.setText(answerMsg);
            if (answerMsg != null && answerMsg.equalsIgnoreCase(mContext.getString(R.string.be_the_first_one_to_answer))) {

                params.setMargins(m15dp, m15dp,0,0);
                viewHolder3.answerCountText.setLayoutParams(params);
                viewHolder3.answerCountText.setPadding(0,0,0,0);
                viewHolder3.answerCountText.setBackground(mContext.getResources().getDrawable(R.drawable.border_bottom));
                viewHolder3.answerCountText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(new Event(AllEvents.ACTION_GIVE_ANSWER_FIRST_CLICK, null, null));
                    }
                });
            }else {
                params.setMargins(0, 0,0,0);
                viewHolder3.answerCountText.setLayoutParams(params);
                viewHolder3.answerCountText.setPadding(m15dp,m15dp,m8dp,m8dp);
                viewHolder3.answerCountText.setBackground(null);
                viewHolder3.answerCountText.setOnClickListener(null);
            }
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
        CircularImageView userProfilePic;
        public QnAAnswerHolder(View itemView) {
            super(itemView);
            answerCard = (CardView) itemView.findViewById(R.id.card_qna_answer);
            answerText = (TextView) itemView.findViewById(R.id.qna_answer_text);
            askedByUser = (TextView) itemView.findViewById(R.id.asked_by_user);
            userProfilePic = (CircularImageView) itemView.findViewById(R.id.image_user_profile_pic);
        }
    }

    private class QnAQuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView questionText;
        TextView askedByUser;
        View questionDivider;
        CircularImageView userProfilePic;

        public QnAQuestionHolder(View itemView) {
            super(itemView);
            questionText = (TextView) itemView.findViewById(R.id.qna_question_text);
            questionDivider = itemView.findViewById(R.id.question_divider);
            questionDivider.setVisibility(View.GONE);
            askedByUser = (TextView) itemView.findViewById(R.id.asked_by_user);
            itemView.setOnClickListener(this);
            userProfilePic = (CircularImageView) itemView.findViewById(R.id.image_user_profile_pic);
        }

        @Override
        public void onClick(View view) {
           EventBus.getDefault().post(new Event(AllEvents.ACTION_SIMILAR_QUESTION_CLICK, mQnAQuestionAnswers.get(getAdapterPosition()), null));
        }
    }
    private class QnAAnswerCountHolder extends RecyclerView.ViewHolder  {
        TextView answerCountText;

        public QnAAnswerCountHolder(View itemView) {
            super(itemView);
            answerCountText  =(TextView)itemView;
        }

    }

}