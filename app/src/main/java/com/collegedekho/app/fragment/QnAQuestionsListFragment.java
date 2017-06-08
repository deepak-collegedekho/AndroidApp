package com.collegedekho.app.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.QnAQuestionsListAdapter;
import com.collegedekho.app.animation.AnimationUtil;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QnAQuestionsListFragment extends BaseFragment {
    public static final String TITLE = "QnA";
    public static final String TAG = "Question List Fragment";

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private ArrayList<QnAQuestions> mQnAQuestionsList = new ArrayList<>();
    private TextView mEmptyTextView;
    private TextView mAskButton;
    private TextView mQuestionFragmentTitle;
    private QnAQuestionsListAdapter mAdapter;
    private int mViewType = Constants.VIEW_INTO_LIST;
    private OnQnAQuestionSelectedListener mListener;
    private EditText mQuestionTitle;
    private EditText mQuestionDesc;


    public static QnAQuestionsListFragment newInstance(ArrayList<QnAQuestions> qnaQuestions, String next){
        QnAQuestionsListFragment fragment = new QnAQuestionsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, qnaQuestions);
        args.putString(ARG_NEXT, next);
        args.putInt(ARG_PARAM2,Constants.QNA_LIST_TYPE);
        fragment.setArguments(args);
        return fragment;
    }

    public static QnAQuestionsListFragment newInstance(ArrayList<QnAQuestions> qnaQuestions, String next,int listType){
        QnAQuestionsListFragment fragment = new QnAQuestionsListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, qnaQuestions);
        args.putString(ARG_NEXT, next);
        args.putInt(ARG_PARAM2,listType);
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
            ArrayList<QnAQuestions> questionsList = getArguments().getParcelableArrayList(ARG_PARAM1);
            if(questionsList != null){
                this.mQnAQuestionsList.addAll(questionsList);
            }
            this.mNextUrl = getArguments().getString(ARG_NEXT);
            this.listType = getArguments().getInt(ARG_PARAM2);
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
        this.mQuestionTitle = (EditText)rootView.findViewById(R.id.institute_qna_question_title);
        this.mQuestionDesc = (EditText)rootView.findViewById(R.id.institute_qna_question_desc);
        this.mQuestionFragmentTitle = (TextView)rootView.findViewById(R.id.questions_page_title);

        if(listType == Constants.INSTITUTE_QNA_LIST_TYPE)
            this.mQuestionFragmentTitle.setVisibility(View.GONE);
        else
            this.mQuestionFragmentTitle.setVisibility(View.VISIBLE);

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
            this.mAskButton.setEnabled(true);
//            this.mEmptyTextView.setVisibility(View.VISIBLE);
//            this.mEmptyTextView.setText("Couldn't find related questions for you. Like and Shortlist college");
//            recyclerView.setVisibility(View.GONE);
//            this.mToggleAskButtonVisibility(View.GONE, View.VISIBLE);
        }else {
            this.mEmptyTextView.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            this.mToggleAskButtonVisibility(View.VISIBLE, View.VISIBLE);
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
        //just a caution for showing toolbar in case mToggleAskButtonVisiblity is not called
        if(getActivity() != null ) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if(actionBar != null)
                actionBar.show();
        }
        System.gc();
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null && listType == Constants.QNA_LIST_TYPE)
            mMainActivity.currentFragment = this;
    }


    @Override
    public String getEntity() {
        return null;
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
                    View askLayout = rootView.findViewById(R.id.qna_ask_layout);
                    if (askLayout.getVisibility() == View.INVISIBLE) {
                        AnimationUtil.circularReveal(askLayout, true);
                        rootView.findViewById(R.id.institute_qna_button_ask_submit).setEnabled(true);
                        mToggleAskButtonVisibility(View.GONE, View.GONE);
                    }
                }
                break;
            case R.id.question_ask_cross:
                if(rootView != null)  {
                    this.mToggleAskButtonVisibility(View.VISIBLE, View.VISIBLE);
                    AnimationUtil.circularReveal(rootView.findViewById(R.id.qna_ask_layout),false);
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

    private void mToggleAskButtonVisibility(final int askQuesButtonVisibility, int toolbarVisibility)
    {
        if (askQuesButtonVisibility == View.GONE) {
            this.mAskButton.animate()
                    .translationY(this.mAskButton.getHeight())
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAskButton.setVisibility(askQuesButtonVisibility);
                        }
                    });

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mQuestionTitle.requestFocus();
                    //showing soft keyboard on ask button click
                    InputMethodManager inputMethodManager=(InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }, 200);

        }
        else if (askQuesButtonVisibility == View.VISIBLE) {
            this.mAskButton.animate()
                    .setDuration(300)
                    .translationY(0)
                    .setListener(null);

            this.mAskButton.setVisibility(askQuesButtonVisibility);
        }

        if(getActivity() != null) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if(actionBar != null) {
                if (toolbarVisibility == View.GONE)
                    actionBar.hide();
                else if (toolbarVisibility == View.VISIBLE)
                    actionBar.show();
            }
        }
    }

    public QnAQuestions validateData() {
        String title =  this.mQuestionTitle.getText().toString();
        if (title.trim().length() <= 0) {
            mListener.displayMessage(R.string.QUESTION_TITLE_EMPTY);
            return null;
        } else {
            String[] words=title.split("\\s+");

            if (words.length == 1){
                QnAQuestionsListFragment.this.mQuestionTitle.setError(getString(R.string.QNA_TITLE_ERROR_SHORT_TITLE));
                QnAQuestionsListFragment.this.mQuestionTitle.setText(words[0]);
                QnAQuestionsListFragment.this.mQuestionTitle.requestFocus();
                return null;
            }
            else{
                Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
                Matcher m = p.matcher(title);
                boolean b = m.find();

                if (b) {
                    QnAQuestionsListFragment.this.mQuestionTitle.setError(getString(R.string.QNA_TITLE_ERROR_SPECIAL_CHARACTER));
                    QnAQuestionsListFragment.this.mQuestionTitle.requestFocus();
                    return null;
                }
            }
        }

        String desc =  this.mQuestionDesc.getText().toString().trim();
        if (desc.trim().length() <= 0) {
            QnAQuestionsListFragment.this.mQuestionTitle.setError(getString(R.string.QUESTION_TEXT_EMPTY));
            QnAQuestionsListFragment.this.mQuestionDesc.requestFocus();
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
            QnAQuestions q = validateData();
            if (q != null && mListener != null)
            {
                this.mQuestionTitle.setText("");
                this.mQuestionDesc.setText("");
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
                view.findViewById(R.id.qna_ask_layout).setVisibility(View.INVISIBLE);
               // view.findViewById(R.id.dummy_view).setVisibility(View.GONE);
                this.mToggleAskButtonVisibility(View.VISIBLE, View.VISIBLE);
            }
    }

    public interface OnQnAQuestionSelectedListener extends BaseListener{
        void onQnAQuestionSelected(QnAQuestions qnaQuestion, int qnaPosition);
        void onQuestionAsked(QnAQuestions qnaQuestion);
        @Override
        void onEndReached(String next, int type);
        void onQnAQuestionVote(int position, int voteType);
        void displayMessage(int messageId);
    }
}
