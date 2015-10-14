package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.QnAAnswersListAdapter;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class QnAQuestionsAndAnswersFragment extends BaseFragment{
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

    public static QnAQuestionsAndAnswersFragment newInstance(QnAQuestions qnaQuestionAnswers) {
        QnAQuestionsAndAnswersFragment fragment = new QnAQuestionsAndAnswersFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, qnaQuestionAnswers);
        fragment.setArguments(args);
        return fragment;
    }

    public QnAQuestionsAndAnswersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mQnAQuestion = getArguments().getParcelable(ARG_PARAM1);
            mQnAAnswersSet = mQnAQuestion.getAnswer_set();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_qna_questions_answers, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        if (mQnAAnswersSet.size() == 0)
            (this.mEmptyTextView).setText("Be the first one to answer.");

        ((TextView) rootView.findViewById(R.id.qna_title)).setText(mQnAQuestion.getTitle());
        ((TextView) rootView.findViewById(R.id.qna_description)).setText(mQnAQuestion.getDesc());
        mVoteCounts = ((TextView) rootView.findViewById(R.id.qna_votes_Count));
        mVoteCounts.setText(String.valueOf(mQnAQuestion.getUpvotes() - mQnAQuestion.getDownvotes()));

        this.mUpvoteButton = (ImageView) rootView.findViewById(R.id.qna_button_upvote);
        this.mDownvoteButton = (ImageView) rootView.findViewById(R.id.qna_button_downvote);

        this.mUpvoteButton.setSelected(mQnAQuestion.getCurrent_user_vote_type() == Constants.LIKE_THING);
        this.mDownvoteButton.setSelected(mQnAQuestion.getCurrent_user_vote_type() == Constants.DISLIKE_THING);

        ((FloatingActionButton) rootView.findViewById(R.id.answer_reply)).setOnClickListener(new View.OnClickListener() {
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
                if (v.isSelected())
                    Toast.makeText(getActivity(), "You can't upvote the upvoted", Toast.LENGTH_SHORT).show();
                else
                    mListener.onQnAQuestionVote(mQnAQuestion.getResource_uri(), Constants.LIKE_THING, mQnAQuestion.getIndex());
            }
        });

        this.mDownvoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.isSelected())
                    Toast.makeText(getActivity(), "You can't downvote the downvoted", Toast.LENGTH_SHORT).show();
                else
                    mListener.onQnAQuestionVote(mQnAQuestion.getResource_uri(), Constants.DISLIKE_THING, mQnAQuestion.getIndex());
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
    public void onAttach(Activity activity) {
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
        if(answerIndex < 0)
        {
            //update question vote
            //Log.e(QnAQuestionsAndAnswersFragment.class.getName(), String.valueOf(questionIndex));
            int totalVotes = Integer.parseInt(this.mVoteCounts.getText().toString());

            if (voteType == Constants.LIKE_THING)
            {
                totalVotes++;
                this.mUpvoteButton.setSelected(true);
                this.mDownvoteButton.setSelected(false);
            }
            else
            {
                totalVotes--;
                this.mUpvoteButton.setSelected(false);
                this.mDownvoteButton.setSelected(true);
            }

            this.mVoteCounts.setText(String.valueOf(totalVotes));
        }
        else
        {
            //update answer vote
            //Log.e(QnAQuestionsAndAnswersFragment.class.getName(), String.valueOf(questionIndex) + " and " + String.valueOf(answerIndex));
            QnAAnswers qnaAns = mQnAAnswersSet.get(answerIndex);

            if (voteType == Constants.LIKE_THING)
            {
                if (qnaAns.getCurrent_user_vote_type() == Constants.DISLIKE_THING)
                    qnaAns.setDownvotes(qnaAns.getDownvotes() - 1);

                qnaAns.setCurrent_user_vote_type(Constants.LIKE_THING);
                qnaAns.setUpvotes(qnaAns.getUpvotes() + 1);
            }
            else
            {
                if (qnaAns.getCurrent_user_vote_type() == Constants.LIKE_THING)
                    qnaAns.setUpvotes(qnaAns.getUpvotes() - 1);

                qnaAns.setCurrent_user_vote_type(Constants.DISLIKE_THING);
                qnaAns.setDownvotes(qnaAns.getDownvotes() + 1);
            }

            mQnAAnswersSet.remove(answerIndex);
            mQnAAnswersSet.add(answerIndex, qnaAns);

            mQnAAnswersListAdapter.notifyItemChanged(answerIndex);
            mQnAAnswersListAdapter.notifyDataSetChanged();
        }
    }

    public void answerAdded(QnAAnswers answer)
    {
        if (this.mEmptyTextView.getVisibility() == View.VISIBLE)
            this.mEmptyTextView.setVisibility(View.GONE);

        mQnAAnswersSet.add(mQnAAnswersSet.size(), answer);
        mQnAAnswersListAdapter.notifyItemInserted(mQnAAnswersSet.size() - 1);
        mQnAAnswersListAdapter.notifyDataSetChanged();

        this.mAnswersListView.scrollToPosition(mQnAAnswersSet.size() - 1);
    }

    public interface OnQnAAnswerInteractionListener {
        void onQnAQuestionVote(String resourceURI, int voteType, int position);
        void onQnAAnswerVote(String resourceURI, int voteType, int answerPosition, int questionPosition);
        void onQnAAnswerSubmitted(String questionURI, String answerText, int questionIndex, int answerIndex);
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }
}
