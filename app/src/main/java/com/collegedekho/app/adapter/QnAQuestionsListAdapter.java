package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;
import com.collegedekho.app.fragment.profileBuilding.CourseSelectionFragment;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.CircularImageView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * @author harshvardhan
 *         Created: 09/08/15
 */
public class QnAQuestionsListAdapter extends RecyclerView.Adapter {

    private static final String TAG = "QnAQuestionsListAdapter";
    private ArrayList<QnAQuestions> mQnAQuestions;
    private Context mContext;
    private int lastPosition = -1;
    private int mViewType;
    private final ImageLoader mImageLoader;

    public QnAQuestionsListAdapter(Context context, ArrayList<QnAQuestions> qnaQuestions, int viewType) {
        this.mQnAQuestions = qnaQuestions;
        this.mContext = context;
        this.mViewType = viewType;
        this.mImageLoader = MySingleton.getInstance(context).getImageLoader();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int viewID = R.layout.card_qna_grid_view;
        if (this.mViewType == Constants.VIEW_INTO_LIST)
            viewID = R.layout.card_qna_list_view;
        View rootView = LayoutInflater.from(this.mContext).inflate(viewID, parent, false);
        try {
            return new QnAQuestionHolder(rootView, (QnAQuestionsListFragment.OnQnAQuestionSelectedListener) this.mContext);
        } catch (ClassCastException e) {
            throw new ClassCastException(this.mContext.toString()
                    + " must implement OnQnAQuestionSelectedListener");
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        super.onBindViewHolder(holder, position, payloads);
        onBindView(holder, position);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
      onBindView(holder, position);
    }

    private void  onBindView(RecyclerView.ViewHolder holder, int position){
        QnAQuestions qnaQuestion = mQnAQuestions.get(position);
        String description = "";

        QnAQuestionHolder qnAQuestionHolder = (QnAQuestionHolder) holder;
        qnAQuestionHolder.questionHeading.setText(qnaQuestion.getTitle());
        description = description + qnaQuestion.getTitle();
        if (qnaQuestion.getUser().equalsIgnoreCase("Anonymous user")){
            qnAQuestionHolder.userName.setText(qnaQuestion.getUser());
        } else {
            qnAQuestionHolder.userName.setText(qnaQuestion.getUser());
            description = qnaQuestion.getUser() + " asked " + description + " click to see detail";
        }
        qnAQuestionHolder.userRole.setText(qnaQuestion.getUser_role());
        qnAQuestionHolder.userProfileImage.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.ic_profile_default_vector));
        qnAQuestionHolder.userProfileImage.setDefaultImageResId(R.drawable.ic_profile_default_vector);
        qnAQuestionHolder.userProfileImage.setImageUrl(qnaQuestion.getUser_image(),mImageLoader);
        qnAQuestionHolder.mContainer.setContentDescription(description);
        qnAQuestionHolder.questionVotes.setText(String.valueOf(qnaQuestion.getUpvotes()-qnaQuestion.getDownvotes()));
        qnAQuestionHolder.answerCount.setText(String.valueOf(qnaQuestion.getAnswers_count()) + "\n" + "Answer");

        if(qnaQuestion.getIs_study_abroad()==1)
        {
            qnAQuestionHolder.studyAbroad.setVisibility(View.VISIBLE);
        }
        else
        {
            qnAQuestionHolder.studyAbroad.setVisibility(View.GONE);
        }
        qnAQuestionHolder.likeButton.setSelected(qnaQuestion.getCurrent_user_vote_type() == 0);
        qnAQuestionHolder.likeButton.setClickable(true);
        qnAQuestionHolder.likeContainer.setClickable(true);
        qnAQuestionHolder.likeButton.setVisibility(View.VISIBLE);
        qnAQuestionHolder.likeProgressBar.setVisibility(View.GONE);
        if (qnaQuestion.getCurrent_user_vote_type() == Constants.LIKE_THING) {
            qnAQuestionHolder.likeButton.setColorFilter(ContextCompat.getColor(this.mContext, R.color.like_green_selected));
            qnAQuestionHolder.likeContainer.setContentDescription("Already up voted Question");
            qnAQuestionHolder.likeButton.setContentDescription("Already up voted Question");

        } else if(qnaQuestion.getCurrent_user_vote_type() == Constants.DISLIKE_THING) {
            qnAQuestionHolder.likeButton.setColorFilter(ContextCompat.getColor(this.mContext, R.color.dislike_red_selected));
            qnAQuestionHolder.likeContainer.setContentDescription("Already down voted this question");
            qnAQuestionHolder.likeButton.setContentDescription("Already down voted this question");

        } else{
            qnAQuestionHolder.likeButton.setColorFilter(ContextCompat.getColor(this.mContext, R.color.subheading_color));
            qnAQuestionHolder.likeContainer.setContentDescription("up vote this question");
            qnAQuestionHolder.likeButton.setContentDescription("up vote this question");
        }

        if (qnaQuestion.getAdded_on() != null) {
            qnAQuestionHolder.addedOn.setText(qnaQuestion.getAdded_on());
        }
        this.mSetAnimation(qnAQuestionHolder.container, qnAQuestionHolder.getAdapterPosition());
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        holder.itemView.clearAnimation();
        super.onViewDetachedFromWindow(holder);
    }

    public void updateLikeButtons(int position) {
        this.notifyItemChanged(position);
        this.notifyDataSetChanged();
    }

    public void updateAdapter(ArrayList<QnAQuestions> qnaQuestionList) {
        if(qnaQuestionList == null)return;
        this.mQnAQuestions = qnaQuestionList;
        this.notifyDataSetChanged();
    }


    /**
     * Here is the key method to apply the animation
     */
    private void mSetAnimation(View viewToAnimate, int position)
    {
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition) {
            Animation animation = viewToAnimate.getAnimation();
            if(animation != null){
                animation.cancel();
            }
            viewToAnimate.setAnimation(null);
            if (this.mViewType == Constants.VIEW_INTO_GRID){
                if (position % 2 == 0)
                {
                    viewToAnimate.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.list_item_from_left));
                }else {
                    viewToAnimate.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.list_item_from_right));
                }
            } else {
                viewToAnimate.startAnimation(AnimationUtils.loadAnimation(this.mContext, R.anim.list_item_from_left));
            }
            lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return mQnAQuestions.size();
    }

    private class QnAQuestionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView questionHeading;
        TextView userName,userRole;
        CircularImageView userProfileImage;
        TextView questionVotes;
        TextView answerCount;
        TextView addedOn;
        ImageView likeButton;
        ProgressBar likeProgressBar;
        QnAQuestionsListFragment.OnQnAQuestionSelectedListener mListener;
        LinearLayout container;
        View likeContainer;
        View mContainer;
        ImageView studyAbroad;

        QnAQuestionHolder(View itemView, QnAQuestionsListFragment.OnQnAQuestionSelectedListener listener) {
            super(itemView);
            this.userProfileImage   = (CircularImageView) itemView.findViewById(R.id.image_user_profile_pic);
            this.questionHeading    = (TextView) itemView.findViewById(R.id.card_qna_question_heading);
            this.userName           = (TextView) itemView.findViewById(R.id.card_qna_user_name);
            this.userRole           = (TextView) itemView.findViewById(R.id.card_qna_user_role);
            this.questionVotes      = (TextView) itemView.findViewById(R.id.card_item_like_count);
            this.answerCount        = (TextView) itemView.findViewById(R.id.card_qna_answer_count);
            this.addedOn            = (TextView) itemView.findViewById(R.id.card_qna_added_on);
            this.likeButton         = (ImageView) itemView.findViewById(R.id.card_item_button_like);
            this.likeContainer      = itemView.findViewById(R.id.card_item_like_layout);
            this.likeProgressBar    = (ProgressBar) itemView.findViewById(R.id.card_item_like_progressBar);
            this.container = (LinearLayout) itemView.findViewById(R.id.qna_card_container);
            this.studyAbroad = (ImageView) itemView.findViewById(R.id.img_study_abroad);

            this.mListener = listener;
            this.container.setOnClickListener(this);
            this.likeButton.setOnClickListener(this);
            this.likeContainer.setOnClickListener(this);
            this.userProfileImage.setOnClickListener(this);
            itemView.setOnClickListener(this);
//            itemView.findViewById(R.id.layout_item_expand).setOnClickListener(this);
            mContainer = itemView;
        }

        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                case R.id.card_item_button_like:
                case R.id.card_item_like_layout:
                    if (NetworkUtils.getConnectivityStatus(mContext) != Constants.TYPE_NOT_CONNECTED) {
                        int position = getAdapterPosition();
                        if(position < mQnAQuestions.size()) {
                            QnAQuestions qnAQuestion = mQnAQuestions.get(position);

                            likeButton.setVisibility(View.GONE);
                            likeProgressBar.setVisibility(View.VISIBLE);
                            likeButton.setClickable(false);
                            likeContainer.setClickable(false);

                            if(qnAQuestion.getCurrent_user_vote_type() == Constants.LIKE_THING){
                                mListener.onQnAQuestionVote(position, Constants.NOT_INTERESTED_THING);

                            }else if(qnAQuestion.getCurrent_user_vote_type() == Constants.DISLIKE_THING){
                                mListener.onQnAQuestionVote(position, Constants.NOT_INTERESTED_THING);
                            }else{
                                if (!v.isSelected()) {
                                    mListener.onQnAQuestionVote(position, Constants.LIKE_THING);
                                } else {
                                    mListener.onQnAQuestionVote(position, Constants.DISLIKE_THING);
                                }
                            }
                        }
                    }else {
                        this.mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                    }
                    break;
                case R.id.qna_card_container: {
                    int qnaPosition = getAdapterPosition();
                    mListener.onQnAQuestionSelected(mQnAQuestions.get(qnaPosition), qnaPosition);
                }
                    break;
                case R.id.image_user_profile_pic:{
                    EventBus.getDefault().post(new Event(AllEvents.ACTION_USER_DP_CLICK,mQnAQuestions.get(getAdapterPosition()).getId() , null));
                    break;
                }
            }
        }
    }

}
