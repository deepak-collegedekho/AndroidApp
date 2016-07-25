package com.collegedekho.app.fragment.stepByStepTest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.StepByStepChoice;
import com.collegedekho.app.entities.StepByStepQuestion;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RangeQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RangeQuestionFragment extends StepByStepFragment {
    private static final String ARG_QUESTION = "question";

    private StepByStepQuestion pQuestion;
    private boolean mIsRequired;
    private boolean mIsSkippable;
    private String mType;
    private boolean mAnswered;
    private ArrayList<StepByStepChoice> mChoiceHashMap;
    private int mMin;
    private int mMax;
    private int mProgress;

    public RangeQuestionFragment() {
        // Required empty public constructor
    }

    public static RangeQuestionFragment newInstance(StepByStepQuestion question) {
        RangeQuestionFragment fragment = new RangeQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, question);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pQuestion = getArguments().getParcelable(ARG_QUESTION);
            if(pQuestion!=null) {
                mIsRequired = pQuestion.isRequired();
                mIsSkippable = pQuestion.is_skippable();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_range_question, container, false);

        ((TextView) rootView.findViewById(R.id.institute_qna_question_title)).setText(pQuestion.getText());

        this.mChoiceHashMap = this.pQuestion.getChoices();
/*
        Collection c = this.mChoiceHashMap.keySet();
        ArrayList<Integer> valList = new ArrayList<>();

        for (Object str : c)
            valList.add(Integer.parseInt(str.toString()));
*/

        this.mMin = Integer.parseInt(this.mChoiceHashMap.get(0).getName());
        this.mMax = Integer.parseInt(this.mChoiceHashMap.get(1).getName());

        if (this.mMax < this.mMin)
        {
            this.mMax = this.mMax ^ this.mMin;
            this.mMin = this.mMax ^ this.mMin;
            this.mMax = this.mMax ^ this.mMin;
        }

        TextView maxText = (TextView) rootView.findViewById(R.id.max_value);
        maxText.setText("MAX:" + String.valueOf(this.mMax));
        TextView minText = (TextView) rootView.findViewById(R.id.min_value);
        minText.setText("MIN:" + String.valueOf(this.mMin));

        final TextView seekBarValue = (TextView) rootView.findViewById(R.id.range_value);

        SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.range_seekbar);
        seekBar.setMax(this.mMax);
        seekBar.setProgress(100000);
        seekBarValue.setText(String.valueOf(100000));

        this.mProgress = 100000;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                int calculatedProgress = 0;
                mAnswered = true;

                //setting progress
                seekBar.setProgress(progress);

                /*--------------this logic is here to make the mDeviceProfile see only the interval figures in lakhs--------------*/

                //if progress is less than a lakh, show 0
                if (progress < 100000)
                {
                    seekBarValue.setText(calculatedProgress + "");
                }
                //if progress is more than or equal to a lakh,
                //show progress by this logic
                //get first digit of the progress and multiply it with a lakh
                //in case of 10 lakh: set 10 lakh if progress is equal to 10 lakh
                else
                {
                    int firstDigit = Integer.parseInt(Integer.toString(progress).substring(0, 1));
                    calculatedProgress = (firstDigit * 100000);

                    if (progress > calculatedProgress)
                    {
                        if (progress == mMax)
                            calculatedProgress = mMax;

                        seekBarValue.setText(calculatedProgress + "");
                    }
                }

                mProgress = calculatedProgress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        return rootView;
    }

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
        return pQuestion.isRequired();
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
    public String type() { return pQuestion.getType(); }

    @Override
    public boolean isAnswered() {
        return this.mAnswered;
    }

    @Override
    public boolean isAnswerDeemedForSecondary() {
        return super.isAnswerDeemedForSecondary();
    }

    @Override
    public void updateAndSetAnswer() {
        String[] tagList = pQuestion.getName().split("#");

        super.setAnswer(tagList[0], this.mMin);
        super.setAnswer(tagList[1], this.mProgress);
    }
}
