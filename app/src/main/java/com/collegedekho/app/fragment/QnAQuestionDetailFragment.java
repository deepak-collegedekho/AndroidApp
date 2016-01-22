package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.QnAAnswersListAdapter;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class QnAQuestionDetailFragment extends BaseFragment{
    private static final String ARG_PARAM1 = "param1";
    private QnAQuestions mQnAQuestion;
    private ArrayList<QnAAnswers> mQnAAnswersSet;
    private OnQnAAnswerInteractionListener mListener;
    private ImageView mUpvoteButton;
    private ImageView mDownvoteButton;
    private QnAAnswersListAdapter mQnAAnswersListAdapter;
    private TextView mVoteCounts;
    private RecyclerView mAnswersListView;
    private TextView mEmptyTextView;
    private volatile SimpleDateFormat mSDF;

    public static QnAQuestionDetailFragment newInstance(QnAQuestions qnaQuestionAnswers) {
        QnAQuestionDetailFragment fragment = new QnAQuestionDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, qnaQuestionAnswers);
        fragment.setArguments(args);
        return fragment;
    }

    public QnAQuestionDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQnAQuestion = getArguments().getParcelable(ARG_PARAM1);
            mQnAAnswersSet = mQnAQuestion.getAnswer_set();
            mSDF = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
            mSDF.setTimeZone(TimeZone.getTimeZone("UTC"));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LinearLayout tagsContainer;
        TextView questionUser;
        TextView questionAskedDate;
        TextView answersCount;
        // Inflate the layout for this fragment
        // final View rootView = inflater.inflate(R.layout.fragment_qna_questions_answers, container, false);
        final View rootView = inflater.inflate(R.layout.fragment_qna_question_detail, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        questionUser = (TextView) rootView.findViewById(R.id.question_user);
        questionAskedDate = (TextView) rootView.findViewById(R.id.question_time);
        answersCount = (TextView) rootView.findViewById(R.id.answers_count);
        String simpleDate = "";
        try {
            mSDF.applyLocalizedPattern("yyyy-MM-dd'T'HH:mm:ss");
            Date date = mSDF.parse(this.mQnAQuestion.getAdded_on());
            mSDF.applyPattern("MMMM d, yyyy KK:mm a");
            simpleDate = mSDF.format(date);
        } catch (ParseException e) {
            Log.e(TAG, "Date format unknown: " + this.mQnAQuestion.getAdded_on());
        }

        if (this.mQnAAnswersSet.size() == 0)
            (this.mEmptyTextView).setText("Be the first one to answer.");

        tagsContainer = (LinearLayout) rootView.findViewById(R.id.tags_container);
        questionUser.setText(this.mQnAQuestion.getUser());
        answersCount.setText(String.valueOf(this.mQnAQuestion.getAnswers_count()));
        questionAskedDate.setText(simpleDate);
        ArrayList<String> tags = this.mQnAQuestion.getTags();

        if(tags != null && tags.size() > 0)
            for(int i = 0; i < tags.size(); i++)
            {
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

        this.mUpvoteButton = (ImageView) rootView.findViewById(R.id.qna_button_upvote);
        this.mDownvoteButton = (ImageView) rootView.findViewById(R.id.qna_button_downvote);

        this.mUpvoteButton.setSelected(this.mQnAQuestion.getCurrent_user_vote_type() == Constants.LIKE_THING);
        this.mDownvoteButton.setSelected(this.mQnAQuestion.getCurrent_user_vote_type() == Constants.DISLIKE_THING);

        (rootView.findViewById(R.id.answer_reply)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());

                alert.setTitle("Enter your answer");

                // Set an EditText view to get user input
                final EditText input = (EditText) LayoutInflater.from(getActivity()).inflate(R.layout.item_text_edit, null);

                alert.setView(input);

                alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        if (value.equals("") || value.equals(" "))
                        {
                            Toast.makeText(getActivity(),"Please enter your answer", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            mListener.onQnAAnswerSubmitted(mQnAQuestion.getResource_uri(), input.getText().toString(), mQnAQuestion.getIndex(), mQnAAnswersSet.size());
                            input.setText("");
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Canceled.
                    }
                });

                alert.show();
            }
        });

        this.mUpvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    Toast.makeText(getActivity(), "You can't upvote the upvoted", Toast.LENGTH_SHORT).show();
                }else {
                    if(mQnAQuestion.getCurrent_user_vote_type()==Constants.DISLIKE_THING) {
                        mListener.onQnAQuestionVote(mQnAQuestion.getResource_uri()+ "upvote/", Constants.NOTINTERESTED_THING, mQnAQuestion.getIndex());
                    }else{
                        mListener.onQnAQuestionVote(mQnAQuestion.getResource_uri(), Constants.LIKE_THING, mQnAQuestion.getIndex());
                    }
                }
            }
        });

        this.mDownvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected()) {
                    Toast.makeText(getActivity(), "You can't downvote the downvoted", Toast.LENGTH_SHORT).show();
                }else {
                    if(mQnAQuestion.getCurrent_user_vote_type()==Constants.LIKE_THING) {
                        mListener.onQnAQuestionVote(mQnAQuestion.getResource_uri()+ "downvote/", Constants.NOTINTERESTED_THING, mQnAQuestion.getIndex());
                    }else {
                        mListener.onQnAQuestionVote(mQnAQuestion.getResource_uri(), Constants.DISLIKE_THING, mQnAQuestion.getIndex());
                    }
                }
            }
        });

        this.mQnAAnswersListAdapter = new QnAAnswersListAdapter(getActivity(), mQnAAnswersSet);

        this.mAnswersListView = (RecyclerView) rootView.findViewById(R.id.qna_answers_list);
        this.mAnswersListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mAnswersListView.setAdapter(this.mQnAAnswersListAdapter);
        this.mAnswersListView.setItemAnimator(new DefaultItemAnimator());
        //mAnswersListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));

        return rootView;
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
            //Log.e(QnAQuestionDetailFragment.class.getName(), String.valueOf(questionIndex));
            int totalVotes = Integer.parseInt(this.mVoteCounts.getText().toString());

            if (voteType == Constants.LIKE_THING)
            {
                totalVotes++;
                this.mUpvoteButton.setSelected(true);
                this.mDownvoteButton.setSelected(false);
//                this.mQnAQuestion.setCurrent_user_vote_type(Constants.LIKE_THING);
                this.mQnAQuestion.setUpvotes(mQnAQuestion.getUpvotes()+1);
            }
            else if(voteType == Constants.DISLIKE_THING)
            {
                totalVotes--;
                this.mUpvoteButton.setSelected(false);
                this.mDownvoteButton.setSelected(true);
//                this.mQnAQuestion.setCurrent_user_vote_type(Constants.DISLIKE_THING);
                this.mQnAQuestion.setDownvotes(mQnAQuestion.getDownvotes()+1);
            }else {
                if(mQnAQuestion.getCurrent_user_vote_type()== Constants.LIKE_THING){
                    totalVotes--;
                }else{
                    totalVotes++;
                }
            }

            this.mQnAQuestion.setCurrent_user_vote_type(voteType);
            this.mVoteCounts.setText(String.valueOf(totalVotes));
            this.mUpvoteButton.setSelected(voteType == Constants.LIKE_THING);
            this.mDownvoteButton.setSelected(voteType == Constants.DISLIKE_THING);
        }
        else
        {
            //update answer vote
            //Log.e(QnAQuestionDetailFragment.class.getName(), String.valueOf(questionIndex) + " and " + String.valueOf(answerIndex));
            QnAAnswers qnaAns = mQnAAnswersSet.get(answerIndex);

            if (voteType == Constants.LIKE_THING)
            {
//                if (qnaAns.getCurrent_user_vote_type() == Constants.DISLIKE_THING)
//                    qnaAns.setDownvotes(qnaAns.getDownvotes() - 1);

//                qnaAns.setCurrent_user_vote_type(Constants.LIKE_THING);
                qnaAns.setUpvotes(qnaAns.getUpvotes() + 1);
            }
            else if(voteType == Constants.DISLIKE_THING)
            {
//                if (qnaAns.getCurrent_user_vote_type() == Constants.LIKE_THING)
//                    qnaAns.setUpvotes(qnaAns.getUpvotes() - 1);

//                qnaAns.setCurrent_user_vote_type(Constants.DISLIKE_THING);
                qnaAns.setDownvotes(qnaAns.getDownvotes() + 1);
            }else {

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


    public interface OnQnAAnswerInteractionListener {
        void onQnAQuestionVote(String resourceURI, int voteType, int position);
        void onQnAAnswerVote(String resourceURI, int voteType, int answerPosition, int questionPosition);
        void onQnAAnswerSubmitted(String questionURI, String answerText, int questionIndex, int answerIndex);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) this.getActivity();

        if (mainActivity != null)
            mainActivity.currentFragment = this;
    }
}
