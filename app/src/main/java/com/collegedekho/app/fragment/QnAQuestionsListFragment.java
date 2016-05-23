package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.QnAQuestionsListAdapter;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import java.util.ArrayList;

public class QnAQuestionsListFragment extends BaseFragment {
    public static final String TITLE = "QnA";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<QnAQuestions> mQnAQuestionsList;
    private TextView mEmptyTextView;
    private TextView mAskButton;
    private QnAQuestionsListAdapter mAdapter;
    private int mViewType = Constants.VIEW_INTO_LIST;
    private OnQnAQuestionSelectedListener mListener;

    public static QnAQuestionsListFragment newInstance(ArrayList<QnAQuestions> qnaQuestions, String next)
    {
        QnAQuestionsListFragment fragment = new QnAQuestionsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, qnaQuestions);
        args.putString(ARG_NEXT, next);
        fragment.setArguments(args);

        return fragment;
    }

    public QnAQuestionsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mQnAQuestionsList = getArguments().getParcelableArrayList(this.ARG_PARAM1);
            this.mNextUrl = getArguments().getString(this.ARG_NEXT);
            this.listType = Constants.QNA_LIST_TYPE;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_qna_questions_list, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        this.mAskButton = (TextView) rootView.findViewById(R.id.question_ask_button);
        this.progressBarLL = (LinearLayout) rootView.findViewById(R.id.progressBarLL);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.institute_questions_list);
        if(this.mViewType == Constants.VIEW_INTO_GRID)
            this.layoutManager = new GridLayoutManager(getActivity(), 2);
        else
            this.layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(this.layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        this.mAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestionsList, this.mViewType);
        recyclerView.setAdapter(this.mAdapter);
        recyclerView.addOnScrollListener(this.scrollListener);
        updateViewTypeIcon(rootView, this.mViewType);

        if (mQnAQuestionsList.size() == 0)
        {
            this.mAskButton.setEnabled(false);
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText("Couldn't find related questions for you. Like and Shortlist college");
            recyclerView.setVisibility(View.GONE);
            this.mToggleAskButtonVisiblity(View.GONE);
        }else {
            this.mEmptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            this.mToggleAskButtonVisiblity(View.VISIBLE);
            this.mAskButton.setEnabled(true);
        }

        this.mAskButton.setOnClickListener(this);
        rootView.findViewById(R.id.view_into_grid).setOnClickListener(this);
        rootView.findViewById(R.id.view_into_list).setOnClickListener(this);
        rootView.findViewById(R.id.question_ask_cross).setOnClickListener(this);
        rootView.findViewById(R.id.institute_qna_button_ask_submit).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof  MainActivity)
                mListener = (OnQnAQuestionSelectedListener)context;
            listener = mListener;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
        mListener = null;
        System.gc();
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        View rootView = getView();
        switch (view.getId())
        {
            case R.id.view_into_grid:
                if(rootView != null && mViewType != Constants.VIEW_INTO_GRID) {
                    this.mViewType = Constants.VIEW_INTO_GRID;
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.institute_questions_list);
                    layoutManager = new GridLayoutManager(getActivity(), 2);
                    recyclerView.setLayoutManager(layoutManager);
                    this.mAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestionsList, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.addOnScrollListener(scrollListener);
                }

                break;
            case R.id.view_into_list:
                if(rootView!= null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    RecyclerView recyclerView1 = (RecyclerView) rootView.findViewById(R.id.institute_questions_list);
                    layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                    recyclerView1.setLayoutManager(layoutManager);
                    this.mAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestionsList, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.addOnScrollListener(scrollListener);
                }
                break;
            case R.id.question_ask_button:
                if(rootView != null) {
                    if ((rootView.findViewById(R.id.qna_ask_layout)).getVisibility() == View.GONE) {
                        (rootView.findViewById(R.id.qna_ask_layout)).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.institute_qna_button_ask_submit).setEnabled(true);
                        rootView.findViewById(R.id.dummy_view).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.dummy_view).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        this.mToggleAskButtonVisiblity(View.GONE);
                    }
                }
                break;
            case R.id.question_ask_cross:
                if(rootView != null)  {
                    this.mToggleAskButtonVisiblity(View.VISIBLE);
                    (rootView.findViewById(R.id.qna_ask_layout)).setVisibility(View.GONE);
                    rootView.findViewById(R.id.dummy_view).setVisibility(View.GONE);
                }
                break;
            case R.id.institute_qna_button_ask_submit:
                onAskExpertSubmitButtonPressed();
                break;
            default:
                break;
        }
        updateViewTypeIcon(getView(), this.mViewType);
    }

    public void updateList(ArrayList<QnAQuestions> qnaQuestionList, String next) {
        this.progressBarLL.setVisibility(View.GONE);
        this.mQnAQuestionsList = qnaQuestionList;
        this.mAdapter.updateAdapter(qnaQuestionList);
        this.loading = false;
        this.mNextUrl = next;
    }

    @Override
    public void onPause() {
        super.onPause();
        loading=false;
    }

    private void mToggleAskButtonVisiblity(int visibility)
    {
        if (visibility == View.GONE)
            this.mAskButton.animate().translationY(this.mAskButton.getHeight());
        else if (visibility == View.VISIBLE)
            this.mAskButton.animate().translationY(0);

        this.mAskButton.setVisibility(visibility);
    }

    public QnAQuestions validateData(View rootView)
    {
        EditText check = (EditText)rootView.findViewById(R.id.institute_qna_question_title);
        String title =  check.getText().toString();
        if (title == null || title.trim().length() <= 0) {
            mListener.displayMessage(R.string.QUESTION_TITLE_EMPTY);
             return null;
        }

        EditText check1 = (EditText)rootView.findViewById(R.id.institute_qna_question_desc);
        String desc =  check1.getText().toString();
        if (desc == null || desc.trim().length() <= 0) {
            mListener.displayMessage(R.string.QUESTION_TEXT_EMPTY);
            return null;
        }

        QnAQuestions q = new QnAQuestions();
        q.setTitle(title);
        q.setDesc(desc);

        return q;
    }


    public void onAskExpertSubmitButtonPressed()
    {
        View view = getView();
        if (view != null) {
            QnAQuestions q = validateData(view);
            if (q != null && mListener != null)
            {
                ((EditText)view.findViewById(R.id.institute_qna_question_title)).setText("");
                ((EditText)view.findViewById(R.id.institute_qna_question_desc)).setText("");
                view.findViewById(R.id.institute_qna_button_ask_submit).setEnabled(false);
                this.mListener.onQuestionAsked(q);
            }
        }
    }

    public void updateLikeButtons(int position) {
        mAdapter.updateLikeButtons(position);
    }
    public void instituteQnAQuestionAdded(QnAQuestions ques)
    {
        if(this.mQnAQuestionsList == null)
            this.mQnAQuestionsList = new ArrayList<>();

            this.mQnAQuestionsList.add(0, ques);
            this.mAdapter.notifyItemInserted(0);
            this.mAdapter.notifyDataSetChanged();

            View view = getView();
            if(view != null) {
               ((RecyclerView) view.findViewById(R.id.institute_questions_list)).scrollToPosition(0);
                view.findViewById(R.id.qna_ask_layout).setVisibility(View.GONE);
                view.findViewById(R.id.dummy_view).setVisibility(View.GONE);
                this.mToggleAskButtonVisiblity(View.VISIBLE);
            }


    }

    public interface OnQnAQuestionSelectedListener extends BaseListener{
        void onQnAQuestionSelected(QnAQuestions qnaQuestion);
        void onQuestionAsked(QnAQuestions qnaQuestion);
        @Override
        void onEndReached(String next, int type);
        void onQnAQuestionVote(int position, int voteType);
        void displayMessage(int messageId);
    }

}
