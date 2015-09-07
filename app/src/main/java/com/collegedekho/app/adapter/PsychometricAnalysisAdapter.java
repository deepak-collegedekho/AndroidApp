package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.PsychometricPrimaryQuestion;
import com.collegedekho.app.entities.PsychometricQuestion;
import com.collegedekho.app.fragment.pyschometricTest.DropDownQuestionFragment;
import com.collegedekho.app.fragment.pyschometricTest.MultipleChoiceQuestionFragment;
import com.collegedekho.app.fragment.pyschometricTest.PsychometricQuestionFragment;
import com.collegedekho.app.fragment.pyschometricTest.SingleChoiceQuestionFragment;
import com.collegedekho.app.fragment.pyschometricTest.RangeQuestionFragment;
import com.collegedekho.app.fragment.pyschometricTest.TextQuestionFragment;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 29/07/15.
 */
public class PsychometricAnalysisAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "PsychometricAnalysisAdapter";
    private ArrayList<PsychometricQuestion> mPQuestion;
    private Context mContext;
    private PsychometricQuestionFragment mCurrentQuestionFragment;
    private PsychometricQuestionFragment mLastQuestionFragment;

    public PsychometricAnalysisAdapter(FragmentManager fm, Context context, ArrayList<PsychometricQuestion> pQuestions) {
        super(fm);

        this.mContext = context;
        this.mPQuestion = pQuestions;

        if(this.mPQuestion != null && this.mPQuestion.size() > 0)
            this.mCurrentQuestionFragment = this.mReturnFragmentofType(this.mPQuestion.get(0), (this.mPQuestion.get(0).getType()));
    }

    public PsychometricAnalysisAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        this.mLastQuestionFragment = this.mCurrentQuestionFragment;

        return this.mReturnFragmentofType(this.mPQuestion.get(position), (this.mPQuestion.get(position).getType()));
    }

    private PsychometricQuestionFragment mReturnFragmentofType(PsychometricQuestion question, String type)
    {
        switch (type)
        {
            case Constants.QTYPE_SINGLE:
                this.mCurrentQuestionFragment = SingleChoiceQuestionFragment.newInstance(question);
                break;
            case Constants.QTYPE_DROPDOWN:
                this.mCurrentQuestionFragment = DropDownQuestionFragment.newInstance(question);
                break;
            case Constants.QTYPE_INPUT:
                this.mCurrentQuestionFragment = TextQuestionFragment.newInstance(question);
                break;
            case Constants.QTYPE_RANGE:
                this.mCurrentQuestionFragment = RangeQuestionFragment.newInstance(question);
                break;
            case Constants.QTYPE_MULTIPLE:
                this.mCurrentQuestionFragment = MultipleChoiceQuestionFragment.newInstance(question, 2);
                break;
            default:
                this.mCurrentQuestionFragment = null;
        }

        return mCurrentQuestionFragment;
    }

    @Override
    public int getCount() {
        return mPQuestion.size();
    }

    public PsychometricQuestionFragment getCurrentQuestionFragment() {
        return mCurrentQuestionFragment;
    }

    public PsychometricQuestionFragment getLastQuestionFragment() {
        return mLastQuestionFragment;
    }

    public void addSecondary(int index, PsychometricQuestion ques)
    {
        this.mPQuestion.add(index, ques);

        this.notifyDataSetChanged();
    }
}