/*
package com.collegedekho.app.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.QnAAnswersListAdapter;
import com.collegedekho.app.animation.AnimationUtil;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.network.ApiEndPonits;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class  QnAQuestionDetailFragment extends BaseFragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private final String TAG = "QuestionDetail Fragment";
    private QnAQuestions mQnAQuestion;
    private ArrayList<QnAAnswers> mQnAAnswersSet;
    private OnQnAAnswerInteractionListener mListener;
    private ImageView mUpVoteButton;
    private ImageView mDownVoteButton;
    private QnAAnswersListAdapter mQnAAnswersListAdapter;
    private TextView mVoteCounts;
    private RecyclerView mAnswersListView;
    private TextView mEmptyTextView;
    private volatile SimpleDateFormat mSDF;
    private View mAnswerQuestionFAB;
    private EditText mAnswerETV;
    private int mQnAPosition;

    public QnAQuestionDetailFragment() {
        // Required empty public constructor
    }

    public static QnAQuestionDetailFragment newInstance(QnAQuestions qnaQuestionAnswers, int position) {
        QnAQuestionDetailFragment fragment = new QnAQuestionDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, qnaQuestionAnswers);
        args.putInt(ARG_PARAM2, position);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mQnAQuestion = getArguments().getParcelable(ARG_PARAM1);
            this.mQnAPosition = getArguments().getInt(ARG_PARAM2);
            if(this.mQnAQuestion!=null) {
                this.mQnAAnswersSet = mQnAQuestion.getAnswer_set();
            }
            mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_qna_question_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateQnaDetail(view);
    }

    private void updateQnaDetail(final View rootView){
        if(rootView == null) return;
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        TextView questionUser = (TextView) rootView.findViewById(R.id.question_user);
        TextView questionAskedDate = (TextView) rootView.findViewById(R.id.question_time);
        TextView answersCount = (TextView) rootView.findViewById(R.id.answers_count);
        String simpleDate = "";
        try {
            if(mQnAQuestion != null && mSDF != null) {
                mSDF.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
                if(this.mQnAQuestion.getAdded_on() !=  null
                        && !this.mQnAQuestion.getAdded_on().isEmpty()) {
                    Date date = mSDF.parse(this.mQnAQuestion.getAdded_on());
                    mSDF.applyPattern("MMMM d, yyyy KK:mm a");
                    simpleDate = mSDF.format(date);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if ( this.mQnAAnswersSet != null && this.mQnAAnswersSet.size() == 0)
            (this.mEmptyTextView).setText(getString(R.string.be_the_first_one_to_answer));

        LinearLayout tagsContainer = (LinearLayout) rootView.findViewById(R.id.tags_container);
        questionUser.setText(this.mQnAQuestion.getUser());
        answersCount.setText(String.valueOf(this.mQnAQuestion.getAnswers_count()));
        questionAskedDate.setText(simpleDate);
        ArrayList<String> tags = this.mQnAQuestion.getTags();

        if(tags != null && tags.size() > 0)
            for(int i = 0; i < tags.size(); i++) {
                TextView tv = (TextView) LayoutInflater.from(this.getContext()).inflate(R.layout.item_tag, null);
                tv.setText(tags.get(i));
                tagsContainer.addView(tv);
            }
        else
            tagsContainer.setVisibility(View.GONE);

        ((TextView) rootView.findViewById(R.id.qna_title)).setText(this.mQnAQuestion.getTitle());
        ((TextView) rootView.findViewById(R.id.qna_description)).setText(this.mQnAQuestion.getDesc());
        this.mVoteCounts = ((TextView) rootView.findViewById(R.id.qna_votes_Count));
        this.mVoteCounts.setText(String.valueOf(this.mQnAQuestion.getUpvotes() - this.mQnAQuestion.getDownvotes()));

        this.mUpVoteButton = (ImageView) rootView.findViewById(R.id.qna_button_upvote);
        this.mDownVoteButton = (ImageView) rootView.findViewById(R.id.qna_button_downvote);

        this.mUpVoteButton.setSelected(this.mQnAQuestion.getCurrent_user_vote_type() == Constants.LIKE_THING);
        this.mDownVoteButton.setSelected(this.mQnAQuestion.getCurrent_user_vote_type() == Constants.DISLIKE_THING);

        mAnswerQuestionFAB = rootView.findViewById(R.id.answer_reply);
        mAnswerQuestionFAB.setOnClickListener(this);
        mAnswerQuestionFAB.setContentDescription("Answer this question");

        mAnswerETV = ((EditText) rootView.findViewById(R.id.qna_answer_edittext));
        (rootView.findViewById(R.id.answer_question_cross)).setOnClickListener(this);
        (rootView.findViewById(R.id.qna_answer_submit_button)).setOnClickListener(this);

        this.mUpVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
                    mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                    return;
                }
                if (v.isSelected()) {
                    mListener.displayMessage(R.string.ALREADY_UP_VOTED);
                    return;
                }
                mUpVoteButton.setClickable(false);
                if (mQnAQuestion.getCurrent_user_vote_type() == Constants.DISLIKE_THING) {
                    mListener.onQnAQuestionVoteFromDetail(Constants.NOT_INTERESTED_THING, mQnAQuestion.getIndex());
                } else {
                    mListener.onQnAQuestionVoteFromDetail(Constants.LIKE_THING, mQnAQuestion.getIndex());
                }

            }
        });

        this.mDownVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
                    mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                    return;
                }
                if (v.isSelected()) {
                    mListener.displayMessage(R.string.ALREADY_DOWN_VOTED);
                    return;
                }
                mDownVoteButton.setClickable(false);
                if (mQnAQuestion.getCurrent_user_vote_type() == Constants.LIKE_THING) {
                    mListener.onQnAQuestionVoteFromDetail(Constants.NOT_INTERESTED_THING, mQnAQuestion.getIndex());
                } else {
                    mListener.onQnAQuestionVoteFromDetail(Constants.DISLIKE_THING, mQnAQuestion.getIndex());
                }
            }
        });

        this.mQnAAnswersListAdapter = new QnAAnswersListAdapter(getActivity(), mQnAAnswersSet, mQnAPosition);
        this.mAnswersListView = (RecyclerView) rootView.findViewById(R.id.qna_answers_list);
        this.mAnswersListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mAnswersListView.setAdapter(this.mQnAAnswersListAdapter);
        this.mAnswersListView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View view) {
        View rootView = getView();
        switch (view.getId()) {
            case R.id.answer_reply:
                if (rootView != null) {
                    View answerLayout = rootView.findViewById(R.id.qna_answer_layout);
                    if ( answerLayout.getVisibility()== View.INVISIBLE) {
                        AnimationUtil.circularReveal(answerLayout,true);
                        this.mToggleAnswerButtonVisibility(View.GONE, View.GONE);
                    }
                }
                break;
            case R.id.answer_question_cross:
                if (rootView != null) {
                    this.mToggleAnswerButtonVisibility(View.VISIBLE, View.VISIBLE);
                    AnimationUtil.circularReveal(rootView.findViewById(R.id.qna_answer_layout), false);
                }
                break;
            case R.id.qna_answer_submit_button: {
                if (NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
                    mListener.displayMessage(R.string.INTERNET_CONNECTION_ERROR);
                    return;
                }
                String value = mAnswerETV.getText().toString();
                if (value.trim().equals("")) {
                    mListener.displayMessage(R.string.ENTER_YOUR_ANSWER);
                } else {
                    if(rootView != null)
                        (rootView.findViewById(R.id.qna_answer_layout)).setVisibility(View.INVISIBLE);

                    this.mToggleAnswerButtonVisibility(View.VISIBLE, View.VISIBLE);
                    mListener.onQnAAnswerSubmitted(mQnAQuestion.getResource_uri(), mAnswerETV.getText().toString().trim(), mQnAQuestion.getIndex(), mQnAAnswersSet.size());
                    mAnswerETV.setText("");
                }
                break;
            }
            default:
                break;
        }
    }

    private void mToggleAnswerButtonVisibility(int askQuesButtonVisibility, int toolbarVisibility)
    {
        //Putting whole thing in try/catch for I am not putting MainActivity null check and
        // hide and show methods are showing NPE warning

        if (askQuesButtonVisibility == View.GONE) {
            this.mAnswerQuestionFAB.animate()
                    .translationY(this.mAnswerQuestionFAB.getHeight());

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAnswerETV.requestFocus();
                    //showing soft keyboard on ask button click
                    InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);

                }
            }, 200);
            mAnswerQuestionFAB.setVisibility(askQuesButtonVisibility);
        } else if (askQuesButtonVisibility == View.VISIBLE) {

            mAnswerQuestionFAB.setVisibility(askQuesButtonVisibility);
            this.mAnswerQuestionFAB.animate().translationY(0);
        }

        if(getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (actionBar != null) {
                if (toolbarVisibility == View.GONE)
                    actionBar.hide();
                else if (toolbarVisibility == View.VISIBLE)
                    actionBar.show();
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String resourceURI = this.mQnAQuestion.getResource_uri();
        String[] resourceURIArray = resourceURI.split("/");

        Uri app_uri_val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_QNA_QUESTION_LIST + "/personalize/qna/" + resourceURIArray[resourceURIArray.length - 1]);
        Uri web_uri_val = Uri.parse(ApiEndPonits.IP + "/qna/" + this.mQnAQuestion.getUri_slug());

        AnalyticsUtils.AppIndexingView("CollegeDekho - QnA - " + this.mQnAQuestion.getDesc(), web_uri_val, app_uri_val, (MainActivity) this.getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();
        String resourceURI = this.mQnAQuestion.getResource_uri();
        String[] resourceURIArray = resourceURI.split("/");

        Uri app_uri_val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_QNA_QUESTION_LIST + "/personalize/qna/" + resourceURIArray[resourceURIArray.length - 1]);
        Uri web_uri_val = Uri.parse(ApiEndPonits.IP + "/qna/" + this.mQnAQuestion.getUri_slug());

        AnalyticsUtils.AppIndexingView("CollegeDekho - QnA - " + this.mQnAQuestion.getDesc(), web_uri_val, app_uri_val, (MainActivity) this.getActivity(), false);
    }


    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnQnAAnswerInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnQnAAnswerInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        System.gc();
    }

    //Called on when response is received from server on vote up or down for either question or answer
    public void onVotingFeedback(int questionIndex, int answerIndex, int voteType)
    {
        if (answerIndex < 0)
        {
            //update question vote
            int totalVotes = Integer.parseInt(this.mVoteCounts.getText().toString());

            if (voteType == Constants.LIKE_THING)
            {
                totalVotes++;
                this.mUpVoteButton.setSelected(true);
                this.mDownVoteButton.setSelected(false);
                this.mQnAQuestion.setUpvotes(mQnAQuestion.getUpvotes()+1);
            }
            else if(voteType == Constants.DISLIKE_THING)
            {
                totalVotes--;
                this.mUpVoteButton.setSelected(false);
                this.mDownVoteButton.setSelected(true);
                this.mQnAQuestion.setDownvotes(mQnAQuestion.getDownvotes()+1);
            }else {
                if(mQnAQuestion.getCurrent_user_vote_type()== Constants.LIKE_THING){
                    this.mQnAQuestion.setUpvotes(mQnAQuestion.getUpvotes()-1);
                    totalVotes--;
                }else{
                    this.mQnAQuestion.setDownvotes(mQnAQuestion.getDownvotes()-1);
                    totalVotes++;
                }
            }

            this.mUpVoteButton.setClickable(true);
            this.mDownVoteButton.setClickable(true);
            this.mQnAQuestion.setCurrent_user_vote_type(voteType);
            this.mVoteCounts.setText(String.valueOf(totalVotes));
            this.mUpVoteButton.setSelected(voteType == Constants.LIKE_THING);
            this.mDownVoteButton.setSelected(voteType == Constants.DISLIKE_THING);
        }
        else
        {
            //update answer vote
            QnAAnswers qnaAns = mQnAAnswersSet.get(answerIndex);
            if (voteType == Constants.LIKE_THING)
                qnaAns.setUpvotes(qnaAns.getUpvotes() + 1);
            else if(voteType == Constants.DISLIKE_THING)
                qnaAns.setDownvotes(qnaAns.getDownvotes() + 1);
            else{
                if(qnaAns.getCurrent_user_vote_type()== Constants.LIKE_THING){
                    qnaAns.setUpvotes(qnaAns.getUpvotes()-1);
                }else{
                    qnaAns.setDownvotes(qnaAns.getDownvotes()-1);
                }
            }
            qnaAns.setCurrent_user_vote_type(voteType);
            this.mQnAAnswersSet.remove(answerIndex);
            this.mQnAAnswersSet.add(answerIndex, qnaAns);
            this.mQnAAnswersListAdapter.notifyItemChanged(answerIndex);
            this.mQnAAnswersListAdapter.notifyDataSetChanged();
        }
    }

    public void answerAdded(QnAAnswers answer)
    {
        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);

        this.mQnAAnswersSet.add(this.mQnAAnswersSet.size(), answer);
        this.mQnAAnswersListAdapter.notifyItemInserted(this.mQnAAnswersSet.size() - 1);
        this.mQnAAnswersListAdapter.notifyDataSetChanged();

        this.mAnswersListView.scrollToPosition(this.mQnAAnswersSet.size() - 1);
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }


    public interface OnQnAAnswerInteractionListener {
        void onQnAQuestionVoteFromDetail(int voteType, int position);
        void onQnAAnswerVote(int voteType, int answerPosition, int questionPosition);
        void onQnAAnswerSubmitted(String questionURI, String answerText, int questionIndex, int answerIndex);
        void displayMessage(int messageId);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) this.getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
            //  answer dialog layout was adjusting UI while soft keyboard is open
            // so to prevent this added window soft input mode
             mainActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(getActivity() != null) {
                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
    }

    public void updateQnaDetailFromNotification(QnAQuestions qnAQuestions){
        if(qnAQuestions == null )return;
        this.mQnAQuestion = qnAQuestions;
        if(this.mQnAQuestion!=null) {
            this.mQnAAnswersSet = mQnAQuestion.getAnswer_set();
        }
        updateQnaDetail(getView());
    }
}
*/
