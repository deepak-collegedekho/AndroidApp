package com.collegedekho.app.fragment.pyschometricTest;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.PsychometricQuestion;
import com.collegedekho.app.listener.PsychometricAnalysisPageListener;

public class TextQuestionFragment extends PsychometricQuestionFragment implements PsychometricAnalysisPageListener
{
    private static final String ARG_QUESTION = "question";
    private PsychometricQuestion pQuestion;
    private boolean mIsRequired;
    EditText mAnswertext;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    public static TextQuestionFragment newInstance(PsychometricQuestion question) {
        TextQuestionFragment fragment = new TextQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, question);
        fragment.setArguments(args);
        return fragment;
    }

    public TextQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pQuestion = getArguments().getParcelable(ARG_QUESTION);
            mIsRequired = pQuestion.isRequired();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_text_question, container, false);
        ((TextView) rootView.findViewById(R.id.institute_qna_question_title)).setText(pQuestion.getText());

        this.mAnswertext = (EditText) rootView.findViewById(R.id.answerText);
        this.mAnswertext.requestFocus();

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(this.mAnswertext, InputMethodManager.SHOW_IMPLICIT);

        //mAnswertext.setHint(pQuestion.text);

        //mAnswertext.

        /*if (mType == TYPE_PRIMARY) {
            if (pQuestion.type.equals(Constants.QTYPE_RANGE))
                rootView.findViewById(R.id.answer_list).setVisibility(View.GONE);
        }*/

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnStepByStepFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStepByStepFragmentListener");
        }
    }*/

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean isRequired() {
        return this.mIsRequired;
    }

    @Override
    public boolean hasSecondary() {
        return false;
    }

    @Override
    public boolean isSecondary() {
        return false;
    }

    @Override
    public Fragment getFragmentInstance() {
        return null;
    }

    @Override
    public boolean isAnswered() {
        return this.mAnswertext.getText().length() > 0 ? true : false;
    }

    @Override
    public boolean isAnswerDeemedForSecondary() {
        return super.isAnswerDeemedForSecondary();
    }

    @Override
    public void updateAndSetAnswer() {
        super.setAnswer((String) pQuestion.getField().get(0), this.mAnswertext.getText().toString());
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
