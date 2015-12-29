package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
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
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class QnAQuestionsListFragment extends BaseFragment {
    public static final String TITLE = "QnA";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<QnAQuestions> mQnAQuestionsList;
    private TextView mEmptyTextView;
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
            mQnAQuestionsList = getArguments().getParcelableArrayList(ARG_PARAM1);
            mNextUrl = getArguments().getString(ARG_NEXT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_qna_questions_list, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);
        progressBarLL = (LinearLayout) rootView.findViewById(R.id.progressBarLL);


        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.institute_questions_list);

        if(this.mViewType == Constants.VIEW_INTO_GRID) {
            layoutManager = new GridLayoutManager(getActivity(), 2);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        }
        else {
            layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
        }
        this.mAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestionsList, this.mViewType);
        recyclerView.setAdapter(this.mAdapter);
        //recyclerView.addOnScrollListener(scrollListener);
        updateViewTypeIcon(rootView, this.mViewType);

        if (mQnAQuestionsList.size() == 0)
        {
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText("Couldn't find related questions for you. Like and Shortlist college");
            recyclerView.setVisibility(View.GONE);
            rootView.findViewById(R.id.question_ask_button).setVisibility(View.GONE);
        }
        else
        {
            this.mEmptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.question_ask_button).setVisibility(View.VISIBLE);
        }

        rootView.findViewById(R.id.view_into_grid).setOnClickListener(this);
        rootView.findViewById(R.id.view_into_list).setOnClickListener(this);
        rootView.findViewById(R.id.question_ask_button).setOnClickListener(this);
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
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnArticleSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, true));
                   // recyclerView.addOnScrollListener(scrollListener);
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
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                    //recyclerView1.addOnScrollListener(scrollListener);
                }
                break;
            case R.id.question_ask_button:
                if(rootView != null) {
                    if ((rootView.findViewById(R.id.qna_ask_layout)).getVisibility() == View.GONE) {
                        (rootView.findViewById(R.id.qna_ask_layout)).setVisibility(View.VISIBLE);
                        rootView.findViewById(R.id.question_ask_button).setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.question_ask_cross:
                if(rootView != null)  {
                    rootView.findViewById(R.id.question_ask_button).setVisibility(View.VISIBLE);
                    (rootView.findViewById(R.id.qna_ask_layout)).setVisibility(View.GONE);
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

    public void updateList(List<QnAQuestions> institutes, String next) {
        this.progressBarLL.setVisibility(View.GONE);
        this.mQnAQuestionsList.addAll(institutes);
        this.mAdapter.notifyDataSetChanged();
        this.loading = false;
        this.mNextUrl = next;
    }

    public QnAQuestions validateData(View rootView)
    {
        EditText check = (EditText)rootView.findViewById(R.id.institute_qna_question_title);
              String title =  check.getText().toString();
        if (title == null || title.length() <= 0) {
            Utils.DisplayToast(getActivity(), "Question title cannot be empty.");
             return null;
        }
        EditText check1 = (EditText)rootView.findViewById(R.id.institute_qna_question_desc);

        String desc =  check1.getText().toString();
        if (desc == null || desc.length() <= 0) {
            Utils.DisplayToast(getActivity(), "Question text cannot be empty.");
             return null;
        }

        QnAQuestions q = new QnAQuestions();
        q.setTitle(title);
        q.setDesc(desc);

        return q;
    }


    public void onAskExpertSubmitButtonPressed()
    {
        View rootView = getView();
        if (rootView != null) {
            QnAQuestions q = validateData(rootView);
            if (q != null && mListener != null)
            {

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

            this.mQnAQuestionsList.add(mQnAQuestionsList.size(), ques);
            this.mAdapter.notifyItemInserted(mQnAQuestionsList.size() - 1);
            this.mAdapter.notifyDataSetChanged();

            View view = getView();
            if(view != null) {
               ((RecyclerView) view.findViewById(R.id.institute_questions_list)).scrollToPosition(mQnAQuestionsList.size() - 1);

                view.findViewById(R.id.qna_ask_layout).setVisibility(View.GONE);
                view.findViewById(R.id.question_ask_button).setVisibility(View.VISIBLE);
            }


    }

    public interface OnQnAQuestionSelectedListener extends BaseListener{
        void onQnAQuestionSelected(QnAQuestions qnaQuestion);
        void onQuestionAsked(QnAQuestions qnaQuestion);
        @Override
        void onEndReached(String next, int type);
        void onQnAQuestionVote(int position, int voteType);
    }

}
