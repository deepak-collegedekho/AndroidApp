package com.collegedekho.app.fragment.pyschometricTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
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
 * Use the {@link SingleChoiceQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleChoiceQuestionFragment extends PsychometricQuestionFragment implements AdapterView.OnItemClickListener {
    public static final int TYPE_PRIMARY = 1;
    public static final int TYPE_SECONDARY = 2;
    private static final String ARG_QUESTION = "question";
    private static final String ARG_TYPE = "type";
    private PsychometricQuestion pQuestion;
    private boolean mIsRequired;
    private ChoiceListAdapter mChoiceListAdapter;
    private boolean mAnswered;
    private HashMap<String, String> mChoiceHashMap;
    private static int SELECTED_INDEX = -1;
    private boolean mIsAnswerDeemedForSecondary = false;

    public SingleChoiceQuestionFragment() {
        // Required empty public constructor
    }

    public static SingleChoiceQuestionFragment newInstance(PsychometricQuestion question) {
        SingleChoiceQuestionFragment fragment = new SingleChoiceQuestionFragment();
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_choice_question, container, false);
        //RadioGroup radioGroup = (RadioGroup) rootView.findViewById(R.id.optionRadioGroup);
        ((TextView) rootView.findViewById(R.id.institute_qna_question_title)).setText(pQuestion.getText());
        ListView choiceList = (ListView) rootView.findViewById(R.id.single_choice_list);
        choiceList.setOnItemClickListener(this);
        choiceList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        this.mChoiceHashMap = (HashMap) pQuestion.getChoiceMap();
        Collection c = this.mChoiceHashMap.keySet();
        ArrayList<String> valList = new ArrayList<>();

        for (Object str : c)
            valList.add(str.toString());

        this.mChoiceListAdapter = new ChoiceListAdapter( getActivity(), valList );
        choiceList.setAdapter(this.mChoiceListAdapter);

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
    public void updateAndSetAnswer()
    {
        this.mIsAnswerDeemedForSecondary = false;

        String key = (String) this.mChoiceListAdapter.getItem(SingleChoiceQuestionFragment.this.SELECTED_INDEX);
        int value = Integer.parseInt(this.mChoiceHashMap.get(key));

        if (value != 1)
            this.mIsAnswerDeemedForSecondary = true;

        super.setAnswer(pQuestion.getField().get(0), value);
    }

    @Override
    public boolean isAnswerDeemedForSecondary() {
        return this.mIsAnswerDeemedForSecondary;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        this.mAnswered = true;

        SingleChoiceQuestionFragment.this.SELECTED_INDEX = position;

        this.mChoiceListAdapter.setSelectedIndex(position);
        this.mChoiceListAdapter.notifyDataSetChanged();


    }

    private class ChoiceListAdapter extends BaseAdapter
    {
        private ArrayList<String> mChoiceList;
        private Activity mActivity;
        private LayoutInflater inflater;
        public int mSelectedPosition = -1;

        public ChoiceListAdapter(Activity activity, ArrayList<String> choiceList)
        {
            this.mActivity = activity;
            this.mChoiceList = choiceList;
        }

        @Override
        public int getCount() {
            return mChoiceList.size();
        }

        @Override
        public Object getItem(int position) {
            return mChoiceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            if (inflater == null)
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.item_qtype_single, null);

            TextView choiceText = (TextView) convertView.findViewById(R.id.single_choice_text);
            choiceText.setText(mChoiceList.get(position));

            RadioButton choice = (RadioButton) convertView.findViewById(R.id.single_choice_radio);
            choice.setChecked(position == mSelectedPosition);

            if(mSelectedPosition == position)
                choice.setChecked(true);
            else
                choice.setChecked(false);

            choice.setTag(position);

            //Animation animation = AnimationUtils.loadAnimation(getActivity(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            //convertView.startAnimation(animation);
            //lastPosition = position;

            return convertView;
        }

        public void setSelectedIndex(int index){
            mSelectedPosition = index;
        }
    }
}