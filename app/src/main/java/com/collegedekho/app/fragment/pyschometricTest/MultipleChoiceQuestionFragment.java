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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
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
 * Use the {@link MultipleChoiceQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MultipleChoiceQuestionFragment extends PsychometricQuestionFragment implements AdapterView.OnItemClickListener{
    public static final int TYPE_PRIMARY = 1;
    public static final int TYPE_SECONDARY = 2;
    private static final String ARG_QUESTION = "question";
    private static final String ARG_TYPE = "type";
    private PsychometricQuestion pQuestion;
    private int mType;
    private boolean mIsRequired;
    private ChoiceListAdapter mChoiceListAdapter;
    private boolean mAnswered;
    private HashMap<String, String> mChoiceHashMap;
    private HashMap<Integer, Integer> mAnswers;
    private boolean mIsAnswerDeemedForSecondary = false;
    private boolean[] mIsChecked;

    public MultipleChoiceQuestionFragment() {
        // Required empty public constructor
    }

    public static MultipleChoiceQuestionFragment newInstance(PsychometricQuestion question, int type) {
        MultipleChoiceQuestionFragment fragment = new MultipleChoiceQuestionFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_QUESTION, question);
        args.putInt(ARG_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.pQuestion = getArguments().getParcelable(ARG_QUESTION);
            this.mType = getArguments().getInt(ARG_TYPE);
            this.mIsRequired = this.pQuestion.isRequired();
            this.mAnswers = new HashMap<Integer, Integer>();
            this.mIsChecked = new boolean[this.pQuestion.getChoiceMap().size()];
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_multiple_choice_question, container, false);
        ((TextView) rootView.findViewById(R.id.institute_qna_question_title)).setText(pQuestion.getText());
        ListView choiceList = (ListView) rootView.findViewById(R.id.multiple_choice_list);
        choiceList.setOnItemClickListener(this);
        //choiceList.setItemsCanFocus(true);

        this.mChoiceHashMap = (HashMap) this.pQuestion.getChoiceMap();
        Collection c = this.mChoiceHashMap.keySet();
        ArrayList<String> valList = new ArrayList<>();

        for (Object str : c)
            valList.add(str.toString());

        this.mChoiceListAdapter = new ChoiceListAdapter( getActivity(), valList );
        choiceList.setAdapter(this.mChoiceListAdapter);
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
    public void updateAndSetAnswer()
    {
        //Remove duplicate entries and relax
        /*HashSet hs = new HashSet();
        hs.addAll(this.mAnswers);

        this.mAnswers.clear();
        this.mAnswers.addAll(hs);*/

        Collection values = this.mAnswers.values();
        Object[] s = values.toArray();
        ArrayList<String> answers = new ArrayList<String>();
        String answerString = "[";
        for (int i = 0; i < values.size(); i++)
        {
            if (values.contains(1)) {
                this.mIsAnswerDeemedForSecondary = true;
            }

            answers.add(String.valueOf(s[i]));
        }
        //ArrayList<String> answerStrings = ArrayList;
        super.setAnswer(pQuestion.getField().get(0), answers.toString());
    }

    @Override
    public boolean isAnswerDeemedForSecondary()
    {
        return this.mIsAnswerDeemedForSecondary;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        this.mAnswered = true;

        String key = (String) this.mChoiceListAdapter.getItem(position);
        int value = Integer.parseInt(this.mChoiceHashMap.get(key));

        this.mAnswers.put(position, value);
    }

    private class ChoiceListAdapter extends BaseAdapter
    {
        private ArrayList<String> mChoiceList;
        private Activity mActivity;
        private LayoutInflater inflater;

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
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            if (inflater == null)
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.item_qtype_multiple, null);

            //TextView choiceText = (TextView) convertView.findViewById(R.id.multiple_choice_text);
            //choiceText.setText(mChoiceList.get(position));

            CheckBox choice = (CheckBox) convertView.findViewById(R.id.multiple_choice_checkbox);
            choice.setTag(position);
            choice.setText(mChoiceList.get(position));
            choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    mIsChecked[position] = isChecked;

                    if (mIsChecked[position]) {
                        String key = (String) mChoiceListAdapter.getItem(Integer.parseInt(buttonView.getTag().toString()));
                        int value = Integer.parseInt(mChoiceHashMap.get(key));

                        mAnswered = true;
                        mAnswers.put(position, value);
                    }
                    else
                    {
                        if (mAnswers.containsKey(position))
                            mAnswers.remove(position);
                    }
                }
            });

            choice.setChecked(mIsChecked[position]);

            return convertView;
        }
    }
}