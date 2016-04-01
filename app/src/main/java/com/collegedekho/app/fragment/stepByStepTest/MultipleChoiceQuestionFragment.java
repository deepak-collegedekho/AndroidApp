package com.collegedekho.app.fragment.stepByStepTest;

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

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.StepByStepChoice;
import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.resource.MySingleton;

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
    public static final int TYPE_PRIMARY = 1;
    public static final int TYPE_SECONDARY = 2;
    private static final String ARG_QUESTION = "question";
    private static final String ARG_TYPE = "type";
    private StepByStepQuestion pQuestion;
    private int mType;
    private boolean mIsRequired;
    private ChoiceListAdapter mChoiceListAdapter;
    private boolean mAnswered;
    private ArrayList<StepByStepChoice> mChoiceHashMap;
    private HashMap<Integer, Integer> mAnswers;
    private boolean mIsAnswerDeemedForSecondary = false;
    private boolean[] mIsChecked;

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
            if(pQuestion!=null){
                this.mIsRequired = this.pQuestion.isRequired();
                this.mIsChecked = new boolean[this.pQuestion.getChoices().size()];
            }
            this.mAnswers = new HashMap<Integer, Integer>();
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

        this.mChoiceHashMap = pQuestion.getChoices();
        this.mChoiceHashMap.addAll(pQuestion.getOther_choices());

        this.mChoiceListAdapter = new ChoiceListAdapter( getActivity(), this.mChoiceHashMap );
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
        if (this.mAnswers.size() == 0)
            return false;
        else
            return true;
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
        JSONArray answerList = new JSONArray();
        String answerString = "[";
        for (int i = 0; i < values.size(); i++)
        {
            if (values.contains(1)) {
                this.mIsAnswerDeemedForSecondary = true;
            }

            answers.add(String.valueOf(s[i]));
            answerList.put(String.valueOf(s[i]));
        }
        //ArrayList<String> answerStrings = ArrayList;
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
        this.mAnswered = true;

        this.mAnswers.put(position, this.mChoiceListAdapter.getItem(position).getId());
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

            //TextView choiceText = (TextView) convertView.findViewById(R.id.multiple_choice_text);
            //choiceText.setText(mChoiceList.get(position));

            sbsChoice = mChoiceList.get(position);

            //set name
            final TextView choiceText = (TextView) convertView.findViewById(R.id.multiple_choice_text);
            choiceText.setText(sbsChoice.getName());

            final CheckBox choice = (CheckBox) convertView.findViewById(R.id.multiple_choice_checkbox);
            choice.setTag(position);
            //choice.setText(sbsChoice.getName());
            choice.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    StepByStepChoice tempSbsChoice;
                    mIsChecked[position] = isChecked;

                    if (mIsChecked[position]) {
                        tempSbsChoice = mChoiceListAdapter.getItem(Integer.parseInt(buttonView.getTag().toString()));

                        mAnswered = true;
                        mAnswers.put(position, tempSbsChoice.getId());
                    }
                    else
                    {
                        if (mAnswers.containsKey(position))
                            mAnswers.remove(position);
                    }
                }
            });

            choice.setChecked(mIsChecked[position]);

            choiceText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choice.performClick();
                }
            });

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

            optionImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    choice.performClick();
                }
            });

            return convertView;
        }
    }
}