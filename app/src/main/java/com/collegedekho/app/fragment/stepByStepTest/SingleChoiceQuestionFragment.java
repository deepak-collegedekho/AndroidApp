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
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.entities.StepByStepChoice;
import com.collegedekho.app.entities.StepByStepQuestion;
import com.collegedekho.app.resource.MySingleton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SingleChoiceQuestionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SingleChoiceQuestionFragment extends StepByStepFragment implements AdapterView.OnItemClickListener {
    public static final int TYPE_PRIMARY = 1;
    public static final int TYPE_SECONDARY = 2;
    private static final String ARG_QUESTION = "question";
    private static final String ARG_TYPE = "type";
    private StepByStepQuestion pQuestion;
    private boolean mIsRequired;
    private boolean mIsSkippable;
    private ChoiceListAdapter mChoiceListAdapter;
    private boolean mAnswered;
    private ArrayList<StepByStepChoice> mChoiceHashMap;
    private static int SELECTED_INDEX = -1;
    private boolean mIsAnswerDeemedForSecondary = false;

    public SingleChoiceQuestionFragment() {
        // Required empty public constructor
    }

    public static SingleChoiceQuestionFragment newInstance(StepByStepQuestion question) {
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
            if(pQuestion!=null) {
                mIsRequired = pQuestion.isRequired();
                mIsSkippable = pQuestion.is_skippable();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_single_choice_question, container, false);
        ((TextView) rootView.findViewById(R.id.institute_qna_question_title)).setText(pQuestion.getText());
        ListView choiceList = (ListView) rootView.findViewById(R.id.single_choice_list);
        choiceList.setOnItemClickListener(this);
        choiceList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

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
        return this.mAnswered;
    }

    @Override
    public void updateAndSetAnswer()
    {
        this.mIsAnswerDeemedForSecondary = false;

        StepByStepChoice key = (StepByStepChoice) this.mChoiceListAdapter.getItem(SingleChoiceQuestionFragment.this.SELECTED_INDEX);

        super.setAnswer(pQuestion.getName(), key.getId());
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
        private ArrayList<StepByStepChoice> mChoiceList;
        private Context mContext;
        private LayoutInflater inflater;
        public int mSelectedPosition = -1;

        public ChoiceListAdapter(Activity activity, ArrayList<StepByStepChoice> choiceList)
        {
            this.mContext = activity;
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
            StepByStepChoice sbsChoice;

            if (inflater == null)
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView == null)
                convertView = inflater.inflate(R.layout.item_qtype_single, null);

            //get StepByStepChoice object
            sbsChoice = mChoiceList.get(position);

            //set name
            TextView choiceText = (TextView) convertView.findViewById(R.id.single_choice_text);
            choiceText.setText(sbsChoice.getName());

            //set image_new
            ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            NetworkImageView optionImage = (NetworkImageView) convertView.findViewById(R.id.single_option_image);

            optionImage.setDefaultImageResId(R.drawable.ic_default_image);
            if (sbsChoice.getImage() != null && !sbsChoice.getImage().isEmpty())
            {
                if (optionImage.getVisibility() == View.GONE)
                    optionImage.setVisibility(View.VISIBLE);
                optionImage.setImageUrl(sbsChoice.getImage(), imageLoader);
            }
            else
                optionImage.setVisibility(View.GONE);

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

    @Override
    public void onResume() {
        super.onResume();
    }
}