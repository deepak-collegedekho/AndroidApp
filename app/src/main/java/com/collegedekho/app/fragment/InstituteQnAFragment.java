package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.QnAQuestionsListAdapter;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;


public class InstituteQnAFragment extends BaseFragment implements TextWatcher, AdapterView.OnItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnQuestionAskedListener mListener;
    private TextInputLayout til1;
    private TextInputLayout til2;
    private ArrayList<QnAQuestions> mQnAQuestions;
    private TextView mEmptyTextView;
    private TextView mAskExpertButton;
    private LinearLayout mInstituteQnAAskContainer;
    private LinearLayout mInstituteQnAQuestionListContainer;
    private QnAQuestionsListAdapter mQnAQuestionsListAdapter;
    private RecyclerView mQuestionsListView;
    private String mInstitute;


    public InstituteQnAFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InstituteQnAFragment.
     */
    public static InstituteQnAFragment newInstance(ArrayList<QnAQuestions> qnaQuestions, String instituteName) {
        InstituteQnAFragment fragment = new InstituteQnAFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, qnaQuestions);
        args.putString(ARG_PARAM2, instituteName);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mQnAQuestions = getArguments().getParcelableArrayList(ARG_PARAM1);
            this.mInstitute = getArguments().getString(ARG_PARAM2);
        }
        else
        {
            this.mQnAQuestions = new ArrayList<>();
            this.mInstitute = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_qna, container, false);

        this.mInstituteQnAAskContainer = (LinearLayout) rootView.findViewById(R.id.institute_qna_ask_question_container);
        this.mInstituteQnAQuestionListContainer = (LinearLayout) rootView.findViewById(R.id.institute_qna_question_recycle_view_container);
        this.mAskExpertButton = (TextView) rootView.findViewById(R.id.institute_qna_button_ask_expert);
        TextView mAskExpertCancelButton= (TextView) rootView.findViewById(R.id.institute_qna_button_ask_cancel);
        TextView mAskExpertSubmitButton= (TextView) rootView.findViewById(R.id.institute_qna_button_ask_submit);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        this.mQnAQuestionsListAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestions, Constants.VIEW_INTO_LIST);
        this.mQuestionsListView = (RecyclerView) rootView.findViewById(R.id.institute_qna_question_recycle_view);
        this.mQuestionsListView.setLayoutManager(new LinearLayoutManager(getActivity()));
        this.mQuestionsListView.setAdapter(this.mQnAQuestionsListAdapter);
        this.mQuestionsListView.setItemAnimator(new DefaultItemAnimator());

        this.mAskExpertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAskExpertButtonPressed();
            }
        });

        mAskExpertCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAskExpertCancelButtonPressed();
            }
        });

        mAskExpertSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAskExpertSubmitButtonPressed();
            }
        });

        if (this.mQnAQuestions == null || this.mQnAQuestions.size() == 0)
        {
            this.mInstituteQnAQuestionListContainer.setVisibility(View.GONE);
            this.mInstituteQnAAskContainer.setVisibility(View.GONE);
            this.mEmptyTextView.setText("No questions asked yet. Go ahead shoot one..");
            this.mAskExpertButton.setText("Ask an Expert Now");
            this.mAskExpertButton.setEnabled(true);
        }
        else
        {
            this.mInstituteQnAQuestionListContainer.setVisibility(View.VISIBLE);
            this.mAskExpertButton.setEnabled(true);
            this.mAskExpertButton.setText("Ask an Expert Now");

        }

        return rootView;
    }

    public QnAQuestions validateData(View rootView)
    {
        EditText check = (EditText)rootView.findViewById(R.id.institute_qna_question_title);
        til1 = (TextInputLayout) check.getParent();
        String title =  check.getText().toString();
        if (title == null || title.length() <= 0) {
            mListener.displayMessage(R.string.QUESTION_TITLE_EMPTY);
            til1.setError("Question title cannot be empty.");
            return null;
        }
        else
            til1.setErrorEnabled(false);

        EditText check1 = (EditText)rootView.findViewById(R.id.institute_qna_question_desc);
        til2 = (TextInputLayout) check1.getParent();
        String desc =  check1.getText().toString();
        if (desc == null || desc.length() <= 0) {
            mListener.displayMessage(R.string.QUESTION_TEXT_EMPTY);
                     til2.setError("Question text cannot be empty.");
            return null;
        }
        else
            til2.setErrorEnabled(false);

        QnAQuestions q = new QnAQuestions();
        q.setInstitute(this.mInstitute);
        q.setTitle(title);
        q.setDesc(desc);
        check.addTextChangedListener(this);
        check1.addTextChangedListener(this);

        return q;
    }

    public void onAskExpertCancelButtonPressed()
    {
        if (this.mQnAQuestions.size() == 0)
            this.mEmptyTextView.setVisibility(View.VISIBLE);
        else
            this.mInstituteQnAQuestionListContainer.setVisibility(View.VISIBLE);

        this.mInstituteQnAAskContainer.setVisibility(View.GONE);

        this.mAskExpertButton.setVisibility(View.VISIBLE);
        this.mAskExpertButton.setEnabled(true);
    }

    public void onAskExpertSubmitButtonPressed()
    {
        View rootView = getView();
        if (rootView != null) {
            QnAQuestions q = validateData(rootView);
            if (q != null)
            {
                mListener.onQuestionAsked(q);

                this.mInstituteQnAAskContainer.setVisibility(View.GONE);
                this.mInstituteQnAQuestionListContainer.setVisibility(View.VISIBLE);

                this.mAskExpertButton.setVisibility(View.VISIBLE);
                this.mAskExpertButton.setEnabled(true);
            }

        }


    }

    public void onAskExpertButtonPressed()
    {
        /*if (this.mInstituteQnALoader.isShowing()  && this.mInstituteQnALoader != null)
            this.mInstituteQnALoader.dismiss();*/
        if (this.mInstituteQnAAskContainer.getVisibility() == View.GONE)
        {
            this.mEmptyTextView.setVisibility(View.GONE);
            this.mInstituteQnAQuestionListContainer.setVisibility(View.GONE);
            this.mInstituteQnAAskContainer.setVisibility(View.VISIBLE);

            this.mAskExpertButton.setVisibility(View.GONE);
            this.mAskExpertButton.setEnabled(false);
            /* View view =getView();
            if(view != null) {
                ((EditText)view.findViewById(R.id.institute_qna_question_title)).setHint("");
                ((EditText)view.findViewById(R.id.institute_qna_question_desc)).setHint("");
            }*/
        }
    }

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
        try {
            mListener = (OnQuestionAskedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnQuestionAskedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (til1 != null) {
            til1.setErrorEnabled(false);
        }
        if (til2 != null) {
            til2.setErrorEnabled(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //    currentCourseIndex = position;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnQuestionAskedListener {
        void onQuestionAsked(QnAQuestions question);
        void displayMessage(int messageId);
    }

    //adds added answer to the current list
    public void instituteQnAAnswerUpdated(QnAAnswers qnaAnswer)
    {

    }

    //adds questions to the current list
    public void instituteQnAUpdated(ArrayList<QnAQuestions> qnaQuestionList)
    {
        this.mQnAQuestions = qnaQuestionList;

        if (this.mQnAQuestions == null || this.mQnAQuestions.size() == 0)
        {
            this.mInstituteQnAAskContainer.setVisibility(View.VISIBLE);
            this.mInstituteQnAQuestionListContainer.setVisibility(View.GONE);
        }
        else
        {
            this.mInstituteQnAAskContainer.setVisibility(View.GONE);
            this.mInstituteQnAQuestionListContainer.setVisibility(View.VISIBLE);
            this.mQnAQuestionsListAdapter.notifyDataSetChanged();
        }
        this.mAskExpertButton.setEnabled(true);
        this.mAskExpertButton.setText("Ask an Expert Now");

    }

    public void questionAdded(QnAQuestions ques)
    {
        if(this.mQnAQuestions == null)
            this.mQnAQuestions = new ArrayList<>();

       /* if (this.mQnAQuestions.size() == 0)
            this.mEmptyTextView.setVisibility(View.GONE);*/

        if (this.mInstituteQnAAskContainer.getVisibility() == View.VISIBLE)
        {
            this.mInstituteQnAQuestionListContainer.setVisibility(View.VISIBLE);
            this.mInstituteQnAAskContainer.setVisibility(View.GONE);
        }

        this.mInstituteQnAQuestionListContainer.requestFocus();

        this.mQnAQuestions.add(mQnAQuestions.size(), ques);
        this.mQnAQuestionsListAdapter.notifyItemInserted(mQnAQuestions.size() - 1);
        this.mQnAQuestionsListAdapter.notifyDataSetChanged();

        this.mQuestionsListView.scrollToPosition(mQnAQuestions.size() - 1);
    }



}
