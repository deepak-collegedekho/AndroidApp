package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bowyer.app.fabtransitionlayout.BottomSheetLayout;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.QnAQuestionsListAdapter;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.GridSpacingItemDecoration;
import com.melnykov.fab.FloatingActionButton;

import java.util.ArrayList;

public class QnAQuestionsListFragment extends BaseFragment {
    public static final String TITLE = "QnA";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<QnAQuestions> mQnAQuestions;
    private TextView mEmptyTextView;
    private QnAQuestionsListAdapter mAdapter;
    private int mViewType = Constants.VIEW_INTO_LIST;
    private OnQnAQuestionSelectedListener mListener;

    public static QnAQuestionsListFragment newInstance(ArrayList<QnAQuestions> qnaQuestions)
    {
        QnAQuestionsListFragment fragment = new QnAQuestionsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, qnaQuestions);
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
            mQnAQuestions = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_qna_questions_list, container, false);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

       // BottomSheetLayout bottomSheetLayout = (BottomSheetLayout)rootView.findViewById(R.id.)
                (rootView).findViewById(R.id.view_into_grid).setOnClickListener(this);
        (rootView).findViewById(R.id.view_into_list).setOnClickListener(this);

        RecyclerView questionsListView = (RecyclerView) rootView.findViewById(R.id.institute_questions_list);

        if(this.mViewType == Constants.VIEW_INTO_GRID) {
            questionsListView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
            questionsListView.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        }
        else {
            questionsListView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            questionsListView.setItemAnimator(new DefaultItemAnimator());
        }
        this.mAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestions, this.mViewType);
        questionsListView.setAdapter(this.mAdapter);
        //questionsListView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        updateViewTypeIcon(rootView, this.mViewType);
        if (mQnAQuestions.size() == 0)
        {
            this.mEmptyTextView.setVisibility(View.VISIBLE);
            this.mEmptyTextView.setText("Couldn't find related questions for you. Like and Shortlist college");
            questionsListView.setVisibility(View.GONE);
        }
        else
        {
            this.mEmptyTextView.setVisibility(View.GONE);
            questionsListView.setVisibility(View.VISIBLE);
        }



        (rootView.findViewById(R.id.institute_qna_button_ask_submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAskExpertSubmitButtonPressed();
            }
        });

        (rootView.findViewById(R.id.qna_ask_question_fab_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((rootView.findViewById(R.id.qna_ask_layout)).getVisibility() == View.GONE)
                    (rootView.findViewById(R.id.qna_ask_layout)).setVisibility(View.VISIBLE);
                else
                    (rootView.findViewById(R.id.qna_ask_layout)).setVisibility(View.GONE);
            }
        });

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
        switch (view.getId())
        {
            case R.id.view_into_grid:
                View rootView = getView();
                if(rootView != null && mViewType != Constants.VIEW_INTO_GRID) {
                    this.mViewType = Constants.VIEW_INTO_GRID;
                    RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.institute_questions_list);
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
                    this.mAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestions, Constants.VIEW_INTO_GRID);
                    recyclerView.setAdapter(this.mAdapter);
                    recyclerView.setHasFixedSize(true);
                    recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 10, true));
                }

                break;
            case R.id.view_into_list:
                View rootView1 = getView();
                if(rootView1 != null && mViewType != Constants.VIEW_INTO_LIST) {
                    this.mViewType = Constants.VIEW_INTO_LIST;
                    RecyclerView recyclerView1 = (RecyclerView) rootView1.findViewById(R.id.institute_questions_list);
                    recyclerView1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    this.mAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestions, Constants.VIEW_INTO_LIST);
                    recyclerView1.setAdapter(this.mAdapter);
                    recyclerView1.setHasFixedSize(true);
                    recyclerView1.setItemAnimator(new DefaultItemAnimator());
                }
                break;
            default:
                break;
        }
        updateViewTypeIcon(getView(), this.mViewType);
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
    public void instituteQnAQuestionAdded(QnAQuestions ques)
    {
        if(this.mQnAQuestions == null)
            this.mQnAQuestions = new ArrayList<>();

            this.mQnAQuestions.add(mQnAQuestions.size(), ques);
            this.mAdapter.notifyItemInserted(mQnAQuestions.size() - 1);
            this.mAdapter.notifyDataSetChanged();

            View view = getView();
            if(view != null) {
               ((RecyclerView) view.findViewById(R.id.institute_questions_list)).scrollToPosition(mQnAQuestions.size() - 1);

                if ((view.findViewById(R.id.qna_ask_layout)).getVisibility() == View.GONE)
                    (view.findViewById(R.id.qna_ask_layout)).setVisibility(View.VISIBLE);
                else
                    (view.findViewById(R.id.qna_ask_layout)).setVisibility(View.GONE);
            }


    }

    public interface OnQnAQuestionSelectedListener {
        void onQnAQuestionSelected(QnAQuestions qnaQuestion);
        void onQuestionAsked(QnAQuestions qnaQuestion);
    }

}
