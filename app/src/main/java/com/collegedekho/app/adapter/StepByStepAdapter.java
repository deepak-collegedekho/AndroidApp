package com.collegedekho.app.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.fragment.stepByStepTest.MultipleChoiceQuestionFragment;
import com.collegedekho.app.fragment.stepByStepTest.RangeQuestionFragment;
import com.collegedekho.app.fragment.stepByStepTest.SingleChoiceQuestionFragment;
import com.collegedekho.app.fragment.stepByStepTest.StepByStepFragment;
import com.collegedekho.app.resource.Constants;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 18/12/15.
 */
public class StepByStepAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = "StepByStepAdapter";
    private ArrayList<StepByStepQuestion> mPQuestion;
    private Context mContext;
    private StepByStepFragment mCurrentQuestionFragment;
    private StepByStepFragment mLastQuestionFragment;
    private ArrayList<StepByStepFragment> mStepByStepFragmentsArray;


    public StepByStepAdapter(FragmentManager fm, Context context, ArrayList<StepByStepQuestion> pQuestions) {
        super(fm);

        this.mContext = context;
        this.mPQuestion = pQuestions;
        this.mStepByStepFragmentsArray = new ArrayList<StepByStepFragment>();
    }

    public StepByStepAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public StepByStepFragment getItem(int position)
    {
        this.mLastQuestionFragment = this.mCurrentQuestionFragment;

        return this.mReturnFragmentofType(this.mPQuestion.get(position), (this.mPQuestion.get(position).getType()));
    }

    private StepByStepFragment mReturnFragmentofType(StepByStepQuestion question, String type)
    {
        switch (type)
        {
            case Constants.QTYPE_SINGLE:
                this.mCurrentQuestionFragment = SingleChoiceQuestionFragment.newInstance(question);
                break;
            case Constants.QTYPE_MULTIPLE:
                this.mCurrentQuestionFragment = MultipleChoiceQuestionFragment.newInstance(question);
                break;
            case Constants.QTYPE_RANGE:
                this.mCurrentQuestionFragment = RangeQuestionFragment.newInstance(question);
                break;
            default:
                this.mCurrentQuestionFragment = null;
                break;
        }

        if (this.mCurrentQuestionFragment != null)
            this.mStepByStepFragmentsArray.add(this.mCurrentQuestionFragment);

        return mCurrentQuestionFragment;
    }

    @Override
    public int getCount() {
        return mPQuestion.size();
    }

    public StepByStepFragment getFragment(int position) {
        if (position >= this.getCount()) {
            return null;
        }

        return this.mStepByStepFragmentsArray.get(position);
    }
}