package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.CircularImageView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * @author harshvardhan
 *         Created: 09/08/15
 */
public class QnAQuestionsListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "QnAQuestionsListAdapter";
    private ArrayList<QnAQuestions> mQnAQuestions;
    private Context mContext;
    private int lastPosition = -1;
    private ImageLoader mImageLoader;
    private SimpleDateFormat mSDF;
    private int mViewType;

    public QnAQuestionsListAdapter(Context context, ArrayList<QnAQuestions> qnaQuestions, int viewType) {
        this.mQnAQuestions = qnaQuestions;
        this.mContext = context;
        this.mImageLoader = MySingleton.getInstance(mContext).getImageLoader();
        this.mViewType = viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewID = R.layout.card_qna_grid_view;
        if (this.mViewType == Constants.VIEW_INTO_LIST)
            viewID = R.layout.card_qna_list_view;
        View rootView = LayoutInflater.from(mContext).inflate(viewID, parent, false);
        try {
            return new QnAQuestionHolder(rootView, (QnAQuestionsListFragment.OnQnAQuestionSelectedListener) mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(mContext.toString()
                    + " must implement OnQnAQuestionSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        QnAQuestions qnaQuestion = mQnAQuestions.get(position);

        QnAQuestionHolder qnAQuestionHolder = (QnAQuestionHolder) holder;
        qnAQuestionHolder.questionHeading.setText(qnaQuestion.getTitle());
        qnAQuestionHolder.userName.setText(qnaQuestion.getUser());
        qnAQuestionHolder.questionVotes.setText(String.valueOf(qnaQuestion.getUpvotes()));
        qnAQuestionHolder.answerCount.setText(String.valueOf(qnaQuestion.getAnswers_count()) + "\n" + "Answer");

        qnAQuestionHolder.likeButton.setSelected(qnaQuestion.getCurrent_user_vote_type() == 0);
        qnAQuestionHolder.likeButton.setClickable(true);
        qnAQuestionHolder.likeButton.setVisibility(View.VISIBLE);
        qnAQuestionHolder.likeProgressBar.setVisibility(View.GONE);
        if (qnAQuestionHolder.likeButton.isSelected())
            qnAQuestionHolder.likeButton.setColorFilter(ContextCompat.getColor(this.mContext, R.color.like_green_selected));
        else
            qnAQuestionHolder.likeButton.setColorFilter(ContextCompat.getColor(this.mContext, R.color.subheading_color));

        if (qnaQuestion.getAdded_on() != null) {
            Date date = null;
            String simpleDate;
            String time;
            try {

                this.mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                this.mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));

                date = this.mSDF.parse(qnaQuestion.getAdded_on());
                //Get Full date
                this.mSDF.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
                mSDF.applyPattern("MMM d, yyyy");
                simpleDate = mSDF.format(date);

                //Get Time
                mSDF.applyLocalizedPattern("HH:mm a");
                time = mSDF.format(date);
                qnAQuestionHolder.addedOn.setText(time + "\n" + simpleDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        ArrayList<String> tags = qnaQuestion.getTags();

        /*if(tags != null)
            for(int i = 0; i < tags.size(); i++)
            {
                TextView tv = (TextView) LayoutInflater.from(mContext).inflate(R.layout.item_tag, null);

                tv.setText(tags.get(i));

                qnAQuestionHolder.tagsContainer.addView(tv);
            }*/
        // this.setAnimation(qnAQuestionHolder.container, position);
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * Here is the key method to apply the animation
     */
    private void setAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(this.mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }


    public void updateLikeButtons(int position) {
        this.notifyItemChanged(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mQnAQuestions.size();
    }

    private class QnAQuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView questionHeading;
        TextView userName;
        CircularImageView userProfileImage;
        TextView questionVotes;
        TextView answerCount;
        TextView addedOn;
        ImageView likeButton;
        ProgressBar likeProgressBar;
        QnAQuestionsListFragment.OnQnAQuestionSelectedListener mListener;
        CardView container;

        public QnAQuestionHolder(View itemView, QnAQuestionsListFragment.OnQnAQuestionSelectedListener listener) {
            super(itemView);
            this.userProfileImage = (CircularImageView) itemView.findViewById(R.id.card_qna_profile_image);
            this.questionHeading = (TextView) itemView.findViewById(R.id.card_qna_question_heading);
            this.userName = (TextView) itemView.findViewById(R.id.card_qna_user_name);
            this.questionVotes = (TextView) itemView.findViewById(R.id.card_item_like_count);
            this.answerCount = (TextView) itemView.findViewById(R.id.card_qna_answer_count);
            this.addedOn = (TextView) itemView.findViewById(R.id.card_qna_added_on);

            this.likeButton = (ImageView) itemView.findViewById(R.id.card_item_button_like);
            this.likeProgressBar = (ProgressBar) itemView.findViewById(R.id.card_item_like_progressBar);
            this.likeButton.setOnClickListener(this);
            this.mListener = listener;
           itemView.findViewById(R.id.card_item_like_layout).setOnClickListener(this);
           itemView.findViewById(R.id.layout_item_expand).setOnClickListener(this);
           itemView.findViewById(R.id.qna_card_view).setOnClickListener(this);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {

                case R.id.card_item_button_like:
                case R.id.card_item_like_layout:
                   if (!v.isSelected()) {
                        likeButton.setVisibility(View.GONE);
                        likeProgressBar.setVisibility(View.VISIBLE);
                        likeButton.setClickable(false);
                        mListener.onQnAQuestionVote(getAdapterPosition(), Constants.LIKE_THING);

                    } else {
                        likeButton.setVisibility(View.GONE);
                        likeProgressBar.setVisibility(View.VISIBLE);
                        likeButton.setClickable(false);
                        mListener.onQnAQuestionVote(getAdapterPosition(), Constants.DISLIKE_THING);

                    }
                    break;
                case R.id.layout_item_expand:
                case R.id.qna_card_view:
                    mListener.onQnAQuestionSelected(mQnAQuestions.get(getAdapterPosition()));
                    break;
            }
        }
    }

}
