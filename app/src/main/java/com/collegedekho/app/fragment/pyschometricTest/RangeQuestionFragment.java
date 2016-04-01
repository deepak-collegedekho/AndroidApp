package com.collegedekho.app.fragment.pyschometricTest;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.PsychometricQuestion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link RangeQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RangeQuestionFragment extends PsychometricQuestionFragment {
    private static final String ARG_QUESTION = "question";

    private PsychometricQuestion pQuestion;
    private boolean mIsRequired;
    private String mType;
    private boolean mAnswered;
    private HashMap<String, String> mChoiceHashMap;
    private int mMin;
    private int mMax;
    private int mProgress;

    public RangeQuestionFragment() {
        // Required empty public constructor
    }

    public static RangeQuestionFragment newInstance(PsychometricQuestion question) {
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
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_range_question, container, false);
        ((TextView) rootView.findViewById(R.id.institute_qna_question_title)).setText(pQuestion.getText());

        this.mChoiceHashMap = (HashMap) this.pQuestion.getChoiceMap();
        Collection c = this.mChoiceHashMap.keySet();
        ArrayList<Integer> valList = new ArrayList<>();

        for (Object str : c)
            valList.add(Integer.parseInt(str.toString()));

        this.mMin = valList.get(1);
        this.mMax = valList.get(0);

        if (this.mMax < this.mMin)
        {
            this.mMax = this.mMax ^ this.mMin;
            this.mMin = this.mMax ^ this.mMin;
            this.mMax = this.mMax ^ this.mMin;
        }

        final TextView seekBarValue = (TextView) rootView.findViewById(R.id.range_value);

        SeekBar seekBar = (SeekBar) rootView.findViewById(R.id.range_seekbar);
        seekBar.setProgress((this.mMin + this.mMax) >> 1);
        seekBar.incrementProgressBy(this.mMax/10);
        seekBar.setMax(this.mMax);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser)
            {
                mAnswered = true;

                progress = progress / 10;
                progress = progress * 10;
                mProgress = progress;
                seekBarValue.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        //mAnswertext.setHint(pQuestion.text);

        //mAnswertext.

        /*if (mType == TYPE_PRIMARY) {
            if (pQuestion.type.equals(Constants.QTYPE_RANGE))
                rootView.findViewById(R.id.answer_list).setVisibility(View.GONE);
        }*/

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
        return !pQuestion.isSecondary();
    }

    @Override
    public boolean isSecondary() {
        return pQuestion.isSecondary();
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
        super.setAnswer(pQuestion.getField().get(0), this.mMin);
        super.setAnswer(pQuestion.getField().get(1), this.mProgress);
    }
}
