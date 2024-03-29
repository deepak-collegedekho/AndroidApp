package com.collegedekho.app.fragment.stepByStepTest;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.StepByStepChoice;
import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.network.MySingleton;

import org.json.JSONArray;

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
public class MultipleChoiceQuestionFragment extends StepByStepFragment implements AdapterView.OnItemClickListener{

    private static final String ARG_QUESTION = "question";
    private static final String ARG_TYPE = "type";
    private StepByStepQuestion pQuestion;
    private ChoiceListAdapter mChoiceListAdapter;
    private ArrayList<StepByStepChoice> mChoiceHashMap;
    private HashMap<Integer, Integer> mAnswers;
    private boolean mIsAnswerDeemedForSecondary = false;

    public MultipleChoiceQuestionFragment() {
        // Required empty public constructor
    }

    public static MultipleChoiceQuestionFragment newInstance(StepByStepQuestion question) {
        MultipleChoiceQuestionFragment fragment = new MultipleChoiceQuestionFragment();
        Bundle args = new Bundle();

        args.putParcelable(ARG_QUESTION, question);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.pQuestion = getArguments().getParcelable(ARG_QUESTION);
            this.mAnswers = new HashMap<>();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_multiple_choice_question, container, false);
        if(pQuestion ==  null)
            return  rootView;


        ((TextView) rootView.findViewById(R.id.institute_qna_question_title)).setText(pQuestion.getText());
        (rootView.findViewById(R.id.institute_qna_question_title)).setContentDescription(pQuestion.getText() + ". Please select a choice from below and then click on the lower right corner to move ahead");
        ListView choiceList = (ListView) rootView.findViewById(R.id.multiple_choice_list);
        choiceList.setOnItemClickListener(this);
        //choiceList.setItemsCanFocus(true);

        this.mChoiceHashMap = pQuestion.getChoices();
        if(this.mChoiceHashMap == null)
            this.mChoiceHashMap = new ArrayList<>();
        this.mChoiceHashMap.addAll(pQuestion.getOther_choices());

        this.mChoiceListAdapter = new ChoiceListAdapter( getActivity(), this.mChoiceHashMap );
        choiceList.setAdapter(this.mChoiceListAdapter);

        return rootView;
    }

    @Override
    public void onAttach(Context activity) {
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
        return this.mAnswers.size() != 0;
    }

    @Override
    public void updateAndSetAnswer()
    {
        Collection values = this.mAnswers.values();
        Object[] s = values.toArray();
        ArrayList<String> answers = new ArrayList<String>();
        JSONArray answerList = new JSONArray();
        for (int i = 0; i < values.size(); i++)
        {
            if (values.contains(1)) {
                this.mIsAnswerDeemedForSecondary = true;
            }

            answers.add(String.valueOf(s[i]));
            answerList.put(String.valueOf(s[i]));
        }
        super.setAnswer(pQuestion.getName(), answerList);
    }

    @Override
    public boolean isAnswerDeemedForSecondary()
    {
        return this.mIsAnswerDeemedForSecondary;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        StepByStepChoice sbsChoice =  mChoiceHashMap.get(position);
        if(sbsChoice == null )
            return;
        //this.mAnswered = true;

        if(sbsChoice.isSelected()){
            sbsChoice.setSelected(false);
            if (mAnswers.containsKey(position))
                mAnswers.remove(position);
        }else{
            sbsChoice.setSelected(true);
            this.mAnswers.put(position, sbsChoice.getId());
        }
        this.mChoiceListAdapter.notifyDataSetChanged();
    }

    private class ChoiceListAdapter extends BaseAdapter
    {
        private ArrayList<StepByStepChoice> mChoiceList;
        private Context mContext;
        private LayoutInflater inflater;

        public ChoiceListAdapter(Context context, ArrayList<StepByStepChoice> choiceList)
        {
            this.mContext = context;
            this.mChoiceList = choiceList;
        }

        @Override
        public int getCount() {
            return mChoiceList.size();
        }

        @Override
        public StepByStepChoice getItem(int position) {
            return mChoiceList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent)
        {
            StepByStepChoice sbsChoice;

            if (inflater == null)
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.item_qtype_multiple, null);

            convertView.setTag(position);
            sbsChoice = mChoiceList.get(position);

            //set name
            TextView choiceText = (TextView) convertView.findViewById(R.id.multiple_choice_text);
            choiceText.setText(sbsChoice.getName());

            CheckBox choice = (CheckBox) convertView.findViewById(R.id.multiple_choice_checkbox);
            if(sbsChoice.isSelected())
                choice.setChecked(true);
           else
                choice.setChecked(false);

            //set image_new
            ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            NetworkImageView optionImage = (NetworkImageView) convertView.findViewById(R.id.multiple_option_image);

            optionImage.setDefaultImageResId(R.drawable.ic_default_image);
            if (sbsChoice.getImage() != null && !sbsChoice.getImage().isEmpty())
            {
                if (optionImage.getVisibility() == View.GONE)
                    optionImage.setVisibility(View.VISIBLE);
                optionImage.setImageUrl(sbsChoice.getImage(), imageLoader);
            }
            else
                optionImage.setVisibility(View.GONE);
            return convertView;
        }
    }
}