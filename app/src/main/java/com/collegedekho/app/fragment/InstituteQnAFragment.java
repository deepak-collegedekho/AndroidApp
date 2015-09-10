package com.collegedekho.app.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;


public class InstituteQnAFragment extends Fragment implements TextWatcher, AdapterView.OnItemClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnQuestionAskedListener mListener;
    private TextInputLayout til1;
    private TextInputLayout til2;
    private ArrayList<QnAQuestions> mQnAQuestions;
    private TextView mEmptyTextView;
    private TextView mAskExpertButton;
    private TextView mAskExpertCancelButton;
    private TextView mAskExpertSubmitButton;
    private LinearLayout mInstituteQnAAskContainer;
    private LinearLayout mInstituteQnAQuestionListContainer;
    private LinearLayout mInstituteQnAAskContainerButtonsControllers;
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
            this.mQnAQuestions = new ArrayList<QnAQuestions>();
            this.mInstitute = "";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_qna, container, false);

        this.mInstituteQnAAskContainer = (LinearLayout) rootView.findViewById(R.id.institute_qna_ask_question_container);
        this.mInstituteQnAAskContainerButtonsControllers = (LinearLayout) rootView.findViewById(R.id.institute_qna_question_controls_buttons);
        this.mInstituteQnAQuestionListContainer = (LinearLayout) rootView.findViewById(R.id.institute_qna_question_recycle_view_container);
        this.mAskExpertButton = (TextView) rootView.findViewById(R.id.institute_qna_button_ask_expert);
        this.mAskExpertCancelButton= (TextView) rootView.findViewById(R.id.institute_qna_button_ask_cancel);
        this.mAskExpertSubmitButton= (TextView) rootView.findViewById(R.id.institute_qna_button_ask_submit);
        this.mEmptyTextView = (TextView) rootView.findViewById(android.R.id.empty);

        this.mQnAQuestionsListAdapter = new QnAQuestionsListAdapter(getActivity(), this.mQnAQuestions);

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

        this.mAskExpertCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAskExpertCancelButtonPressed();
            }
        });

        this.mAskExpertSubmitButton.setOnClickListener(new View.OnClickListener() {
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

    public QnAQuestions validateData(View rootView) {
        View check = rootView.findViewById(R.id.institute_qna_question_title);
        QnAQuestions q = new QnAQuestions();
        q.setInstitute(this.mInstitute);
        q.setTitle(((EditText) check).getText().toString());
        boolean valid = q.getTitle().length() > 0;
        ((EditText) check).addTextChangedListener(this);
        til1 = (TextInputLayout) check.getParent();
        if (!valid)
            til1.setError("Question title cannot be empty.");
        else
            til1.setErrorEnabled(false);
        check = rootView.findViewById(R.id.institute_qna_question_desc);
        ((EditText) check).addTextChangedListener(this);
        til2 = (TextInputLayout) check.getParent();
        q.setDesc(((EditText) check).getText().toString());
        valid &= q.getDesc().length() > 0;
        if (!valid)
            til2.setError("Question text cannot be empty.");
        else
            til2.setErrorEnabled(false);
        if (valid)
            return q;
        return null;
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
            if (q != null) {
                mListener.onQuestionAsked(q);
            }
        }

        this.mInstituteQnAAskContainer.setVisibility(View.GONE);
        this.mInstituteQnAQuestionListContainer.setVisibility(View.VISIBLE);

        this.mAskExpertButton.setVisibility(View.VISIBLE);
        this.mAskExpertButton.setEnabled(true);
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
        }
    }

    @Override
    public void onAttach(Activity activity) {
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
            til2.setErrorEnabled(false);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //    currentCourseIndex = position;
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
    }

    //adds added answer to the current list
    public void instituteQnAAnswerUpdated(QnAAnswers qnaAnswer)
    {

    }

    //adds questions to the current list
    public void instituteQnAUpdated(ArrayList<QnAQuestions> qnaQuestionList)
    {
        this.mQnAQuestions = qnaQuestionList;

        if (this.mQnAQuestions.size() == 0)
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
        if (this.mQnAQuestions.size() == 0)
            this.mEmptyTextView.setVisibility(View.GONE);

        if (this.mInstituteQnAAskContainer.getVisibility() == View.VISIBLE)
        {
            this.mInstituteQnAQuestionListContainer.setVisibility(View.VISIBLE);
            this.mInstituteQnAAskContainer.setVisibility(View.GONE);
        }

        this.mInstituteQnAQuestionListContainer.requestFocus();

        mQnAQuestions.add(mQnAQuestions.size(), ques);
        mQnAQuestionsListAdapter.notifyItemInserted(mQnAQuestions.size() - 1);
        mQnAQuestionsListAdapter.notifyDataSetChanged();

        this.mQuestionsListView.scrollToPosition(mQnAQuestions.size() - 1);
    }

}
