package com.collegedekho.app.fragment.stepByStepTest;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.StepByStepAdapter;
import com.collegedekho.app.display.NonSwipeableViewPager;
import com.collegedekho.app.display.ZoomPageTransformer;
import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.listener.PsychometricAnalysisPageListener;
import com.collegedekho.app.resource.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnStepByStepFragmentListener} interface
 * to handle interaction events.
 * Use the {@link StepByStepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StepByStepFragment extends BaseFragment implements PsychometricAnalysisPageListener{
    private static final String QUESTION_LIST = "question_list";
    private static JSONObject mAnswersMap = new JSONObject();
    private ViewPager mViewPager;
    private FloatingActionButton mNextButton;
    private StepByStepAdapter mQuestionAdapter;
    private int mQuestionSetCount = 1;
    private boolean mIsFinished = false;
    private ArrayList<StepByStepQuestion> mStepByStepQuestions;
    private OnStepByStepFragmentListener mListener;

    public StepByStepFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param stepByStepQuestions Parameter 1.
     * @return A new instance of fragment StepByStepFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StepByStepFragment newInstance(ArrayList<StepByStepQuestion> stepByStepQuestions) {
        StepByStepFragment fragment = new StepByStepFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(QUESTION_LIST, stepByStepQuestions);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            ArrayList<StepByStepQuestion> stepByStepQuestions = getArguments().getParcelableArrayList(QUESTION_LIST);
            // sometimes question was null
            mStepByStepQuestions = new ArrayList<>();
            if(stepByStepQuestions != null) {
                for (int i = 0; i < stepByStepQuestions.size(); i++) {
                   StepByStepQuestion que = stepByStepQuestions.get(i);
                    if(que == null )continue;
                    mStepByStepQuestions.add(que);
                }
            }
            if (MainActivity.mProfile.getCurrent_stream_id() > 0)
                mQuestionSetCount = 2;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_step_by_step, container, false);

        this.mViewPager = (NonSwipeableViewPager) rootView.findViewById(R.id.sbs_pager);
        this.mViewPager.setHapticFeedbackEnabled(true);
        this.mViewPager.setPageTransformer(true, new ZoomPageTransformer());

        this.mQuestionAdapter = new StepByStepAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext(), this.mStepByStepQuestions);
        this.mViewPager.setAdapter(this.mQuestionAdapter);

        this.mNextButton = (FloatingActionButton) rootView.findViewById(R.id.sbs_button_next);

        this.mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean carryOnToNext = false;
                int currentIndex;
                boolean isRequired;
                boolean isSkippable;
                boolean isAnswered;
                StepByStepFragment inContextFragment;

                inContextFragment = StepByStepFragment.this.mQuestionAdapter.getFragment(StepByStepFragment.this.mViewPager.getCurrentItem());

                if (StepByStepFragment.this.mIsFinished) {
                    inContextFragment.updateAndSetAnswer();
                    StepByStepFragment.this.mFinishTest();
                    return;
                }

                currentIndex = StepByStepFragment.this.mViewPager.getCurrentItem();

                //if on last question in the questions list
                //check the number of iteration for the questionlist
                //if it is 1 or 2. ic_call_vector for next set of questions
                //if it is 3. prepare the post data, send it back and be done with this.

                isRequired = inContextFragment.isRequired();

                isSkippable = inContextFragment.isSkippable();

                isAnswered = inContextFragment.isAnswered();

                if (isAnswered) {
                    inContextFragment.updateAndSetAnswer();
                    carryOnToNext = true;
                } else {
                    //if not Answered and is not required then carry on to next question
                    if (!isRequired || isSkippable) {
                        //skipBy = ((mStepByStepQuestions.get(currentIndex)).getSecondary()).size() + 1;
                        carryOnToNext = true;
                    } else {
                        try{
                            ((MainActivity) StepByStepFragment.this.getActivity()).speakMessageForAccessibility("This is a necessary question. Please answer it and then click next on the lower right corner");
                        } catch (Exception e){}
                    }
                }

                if (carryOnToNext) {
                    String answerValue = "";
                    String questionSetCount = "";

                    //if in_school or graduate case
                    if (StepByStepQuestion.getCurrentLevel() == StepByStepQuestion.CurrentLevels.IN_SCHOOL)
                    {
                        // if question set count equal to three
                        if (StepByStepFragment.this.mQuestionSetCount == 3 && StepByStepFragment.this.mViewPager.getCurrentItem() == StepByStepFragment.this.mQuestionAdapter.getCount() - 2)
                        {
                            //StepByStepFragment.this.mNextButton.setText("Finish");
                            StepByStepFragment.this.mIsFinished = true;
                            StepByStepFragment.this.mNextButton.setImageDrawable(StepByStepFragment.this.getContext().getResources().getDrawable(R.drawable.ic_checked));
                            StepByStepFragment.this.mNextButton.setContentDescription("Click to submit test");

                        }

                        // if not on the last question yet for the set
                        if (StepByStepFragment.this.mViewPager.getCurrentItem() < StepByStepFragment.this.mQuestionAdapter.getCount() - 1)
                        {
                            // question set count should not matter
                            StepByStepFragment.this.mSetCurrentItem();

                        }
                        // if on the last question for the question set
                        else if (StepByStepFragment.this.mViewPager.getCurrentItem() == StepByStepFragment.this.mQuestionAdapter.getCount() - 1)
                        {
                            if (StepByStepFragment.this.mQuestionSetCount < 3)
                            {
                                try {
                                    answerValue = String.valueOf(StepByStepFragment.mAnswersMap.get(StepByStepFragment.this.mStepByStepQuestions.get(currentIndex).getName()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                questionSetCount = StepByStepFragment.this.mGetQuestionSetCountIncrementedString();
                                String tag = Constants.TAG_LOAD_STEP_BY_STEP + "#" + StepByStepQuestion.CurrentLevels.IN_SCHOOL + "#" + String.valueOf(StepByStepFragment.this.mQuestionSetCount);
                                String url = Constants.BASE_URL + "step-by-step/" +  answerValue + "/ug-ques-" + questionSetCount;
                                StepByStepFragment.this.mNextButton.setEnabled(false);
                                StepByStepFragment.this.mGetQuestions(tag, url);
                            }
                        }
                    }
                    // if post graduate case
                    if (StepByStepQuestion.getCurrentLevel() == StepByStepQuestion.CurrentLevels.POSTGRADUATE_COLLEGE  || StepByStepQuestion.getCurrentLevel() == StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE)
                    {
                        // if question set count equal to two
                        if (StepByStepFragment.this.mQuestionSetCount == 2 && StepByStepFragment.this.mViewPager.getCurrentItem() == StepByStepFragment.this.mQuestionAdapter.getCount() - 2)
                        {
                            //StepByStepFragment.this.mNextButton.setText("Finish");
                            StepByStepFragment.this.mIsFinished = true;
                            StepByStepFragment.this.mNextButton.setImageDrawable(StepByStepFragment.this.getContext().getResources().getDrawable(R.drawable.ic_checked));
                            StepByStepFragment.this.mNextButton.setContentDescription("Click to submit and move to next question");
                        }

                        // if not on the last question yet for the set
                        if (StepByStepFragment.this.mViewPager.getCurrentItem() < StepByStepFragment.this.mQuestionAdapter.getCount() - 1)
                        {
                            // question set count should not matter
                            StepByStepFragment.this.mSetCurrentItem();
                        }
                        // if on the last question for the question set
                        else if (StepByStepFragment.this.mViewPager.getCurrentItem() == StepByStepFragment.this.mQuestionAdapter.getCount() - 1)
                        {
                            if (StepByStepFragment.this.mQuestionSetCount < 2)
                            {
                                try {
                                    answerValue = String.valueOf(StepByStepFragment.mAnswersMap.get(StepByStepFragment.this.mStepByStepQuestions.get(currentIndex).getName()));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                                questionSetCount = StepByStepFragment.this.mGetQuestionSetCountIncrementedString();
                                String tag = Constants.TAG_LOAD_STEP_BY_STEP + "#" + StepByStepQuestion.CurrentLevels.GRADUATE_COLLEGE + "#" + String.valueOf(StepByStepFragment.this.mQuestionSetCount);
                                String url = Constants.BASE_URL + "step-by-step/" +  answerValue + "/pg-ques-" + questionSetCount;
                                StepByStepFragment.this.mNextButton.setEnabled(false);
                                //StepByStepFragment.this.mNextButton.setText("Loading questions..");
                                StepByStepFragment.this.mGetQuestions(tag, url);
                            }
                        }
                    }
                } else {
                    StepByStepFragment.this.mViewPager.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                    StepByStepFragment.this.mViewPager.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
                }
            }
        });

        return rootView;
    }


    public void updateQuestionSet(ArrayList<StepByStepQuestion> stepByStepQuestion)
    {
        this.mNextButton.setEnabled(true);

        if(mQuestionAdapter == null){
            this.mQuestionAdapter = new StepByStepAdapter(getActivity().getSupportFragmentManager(), getActivity().getApplicationContext(), this.mStepByStepQuestions);
            this.mViewPager.setAdapter(this.mQuestionAdapter);

        }else {
            if(stepByStepQuestion != null) {
                for (int i = 0; i < stepByStepQuestion.size(); i++) {
                    StepByStepQuestion que = stepByStepQuestion.get(i);
                    if(que == null )continue;
                    this.mStepByStepQuestions.add(que);
                }
            }
            //this.mStepByStepQuestions.addAll(stepByStepQuestion);
            this.mQuestionAdapter.notifyDataSetChanged();
        }

        this.mSetCurrentItem();
    }

    private void mSetCurrentItem()
    {
        this.mViewPager.setCurrentItem(this.mViewPager.getCurrentItem() == this.mQuestionAdapter.getCount() - 1 ? this.mQuestionAdapter.getCount() - 1 : this.mViewPager.getCurrentItem() + 1);
    }

    private String mGetQuestionSetCountIncrementedString()
    {
        switch (this.mQuestionSetCount)
        {
            case 1:
                this.mQuestionSetCount = 2;
                return "two";
            case 2:
                this.mQuestionSetCount = 3;
                return "three";
            default:
                return "";
        }
    }

    private void mFinishTest()
    {
        if (this.mListener != null) {
            int index = StepByStepQuestion.CurrentLevels.valueOf(StepByStepQuestion.getCurrentLevel().toString()).ordinal();

            index = index  + 1;

            try {
                StepByStepFragment.mAnswersMap.put("user_type", index);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            this.mListener.onSBSTestFinish(Constants.BASE_URL + "step-by-step/answers/", StepByStepFragment.mAnswersMap);
        }

    }

    private void mGetQuestions(String tag, String url) {
        if (this.mListener != null) {
            this.mListener.onFetchQuestions(tag, url);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnStepByStepFragmentListener) {
            this.mListener = (OnStepByStepFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnStepByStepFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public boolean isSkippable() {
        return false;
    }

    @Override
    public boolean isRequired() {
        return false;
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
    public String type() {
        return null;
    }

    @Override
    public boolean isAnswered() {
        return false;
    }

    @Override
    public void updateAndSetAnswer() {

    }

    @Override
    public boolean isAnswerDeemedForSecondary() {
        return false;
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnStepByStepFragmentListener {
        // TODO: Update argument type and name
        void onFetchQuestions(String tag, String url);
        void onSBSTestFinish(String url, JSONObject answerObject);
    }

    public void setAnswer(String key, Object value)
    {
        try
        {
            StepByStepFragment.mAnswersMap.put(key, value);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
