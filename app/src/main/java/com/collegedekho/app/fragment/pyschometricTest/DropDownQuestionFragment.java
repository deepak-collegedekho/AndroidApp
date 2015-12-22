package com.collegedekho.app.fragment.pyschometricTest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.PsychometricQuestion;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public class DropDownQuestionFragment extends PsychometricQuestionFragment implements AdapterView.OnItemSelectedListener{
    public static final int TYPE_PRIMARY = 1;
    public static final int TYPE_SECONDARY = 2;
    private static final String ARG_QUESTION = "question";
    private static final String ARG_TYPE = "type";
    private PsychometricQuestion pQuestion;
    private volatile boolean mIsRequired;
    private ChoiceListAdapter mChoiceListAdapter;
    private boolean mAnswered;
    private HashMap<String, String> mChoiceHashMap;
    private int mSelectedPosition = -1;


    public DropDownQuestionFragment() {
        // Required empty public constructor
    }

    public static DropDownQuestionFragment newInstance(PsychometricQuestion question) {
        DropDownQuestionFragment fragment = new DropDownQuestionFragment();
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
            mIsRequired = pQuestion.isRequired();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_dropdown_question, container, false);
        ((TextView) rootView.findViewById(R.id.institute_qna_question_title)).setText(pQuestion.getText());

        Spinner dropdownList = (Spinner) rootView.findViewById(R.id.dropdown_choice_list);
        dropdownList.setOnItemSelectedListener(this);

        this.mChoiceHashMap = (HashMap) pQuestion.getChoiceMap();
        Collection c = this.mChoiceHashMap.keySet();
        ArrayList<String> valList = new ArrayList<>();

        for (Object str : c)
            valList.add(str.toString());

        this.mChoiceListAdapter = new ChoiceListAdapter(getActivity().getBaseContext(), R.layout.item_qtype_dropdown, R.id.spinner_options, valList);
        dropdownList.setAdapter(this.mChoiceListAdapter);
        //ArrayAdapter<String> spinnerArrayAdapter;

        //spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, pQuestion.getChoices());
        //spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //dropdownList.setAdapter(spinnerArrayAdapter);
        /*if (mType == TYPE_PRIMARY) {
            if (pQuestion.type.equals(Constants.QTYPE_RANGE))
                rootView.findViewById(R.id.answer_list).setVisibility(View.GONE);
        }*/
        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(getActivity().getBaseContext(), ((ArrayList<String>) this.pQuestion.getChoices()).get(position), Toast.LENGTH_LONG).show();
        this.mAnswered = true;
        this.mSelectedPosition = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //Toast.makeText(getActivity().getBaseContext(), "Not Selected", Toast.LENGTH_LONG).show();
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
    public boolean isAnswered()
    {
        return this.mAnswered;
    }

    @Override
    public boolean isAnswerDeemedForSecondary() {
        return false;
    }

    @Override
    public void updateAndSetAnswer()
    {
        String key = (String) this.mChoiceListAdapter.getItem(this.mSelectedPosition);
        String value = this.mChoiceHashMap.get(key);

        super.setAnswer((String) pQuestion.getField().get(0), value);
    }

    private class ChoiceListAdapter extends ArrayAdapter
    {
        private ArrayList<String> mChoiceList;
        private Activity mActivity;
        private Context mContext;
        private int mSpinnerRowTextView;
        private int mRowLayout;
        private LayoutInflater inflater;

        public ChoiceListAdapter(Context context, int resource, int textViewResourceId, ArrayList<String> objects) {
            super(context, resource, textViewResourceId, objects);
            this.mContext = context;
            this.mRowLayout = resource;
            this.mSpinnerRowTextView = textViewResourceId;
            this.mChoiceList = (ArrayList<String>) objects;
        }

        public ChoiceListAdapter(Context context, int resource, List objects, ArrayList<String> mChoiceList, Context mContext, int mRowLayout) {
            super(context, resource, objects);
            this.mChoiceList = mChoiceList;
            this.mContext = mContext;
            this.mRowLayout = mRowLayout;
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
                convertView = inflater.inflate(R.layout.item_qtype_dropdown, null);

            TextView tv = ((TextView) convertView.findViewById(R.id.spinner_options));
            tv.setText(this.mChoiceList.get(position));

            return convertView;
        }
    }
}
