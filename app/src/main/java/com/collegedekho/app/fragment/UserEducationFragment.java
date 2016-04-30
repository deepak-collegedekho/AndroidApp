package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.entities.UserEducationStreams;
import com.collegedekho.app.entities.UserEducationSublevels;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.NumberPicker;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UserEducationFragment.OnUserEducationInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserEducationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserEducationFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER_EDUCATION_LIST = "user_education_list";
    private int selectedValue=0;
    // TODO: Rename and change types of parameters
    private String mLevelID;
    private String mSubLevelID = "";
    private String mStreamID = "";
    private String mMarks = "";
    private boolean isUserPreparing;
    private OnUserEducationInteractionListener mListener;
    private RelativeLayout.LayoutParams layoutParams;
    private RadioGroup examRadioGroup;
    private TextView btnSubmit,preparingMsg;
    private ArrayList<UserEducation> mUserEducationList;
    private ArrayList<UserEducationSublevels> mUserExamSubLevelsList;
    private ArrayList<ArrayList<UserEducationStreams>> mUserStreamLists;
    private NumberPicker mExamPicker;
    private NumberPicker mStreamPicker;
    private NumberPicker mMarksPicker;

    private String[] exam_arrays ;
    private String[] stream_arrays ;
    final String[] marks_arrays = {"30-40%", "40-50%","50-60%","60-70%", "70-80%", "80-90%", "90-100%",};

    private boolean IS_TUTE_COMPLETED = true;
    private boolean isEditMode=false;
    public UserEducationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userEducationList Parameter 1.
     * @return A new instance of fragment UserEducationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserEducationFragment newInstance(ArrayList<UserEducation> userEducationList) {
        UserEducationFragment fragment = new UserEducationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(USER_EDUCATION_LIST, userEducationList);
        fragment.setArguments(args);

        return fragment;
    }

    public static UserEducationFragment newEditableInstance(ArrayList<UserEducation> userEducationList) {
        UserEducationFragment fragment = new UserEducationFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(USER_EDUCATION_LIST, userEducationList);
        args.putBoolean("is_edit_mode",true);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mUserEducationList = getArguments().getParcelableArrayList(USER_EDUCATION_LIST);
            this.isEditMode=getArguments().getBoolean("is_edit_mode");
            makeArraysForPicker();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_user_education, container, false);
        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.EDUCATION_SCREEN_TUTE), false);

        TextView cdTextView = (TextView) rootView.findViewById(R.id.user_cd_recommendation_text);
        Spanned text = Html.fromHtml("GET <b><font color='#ff8d00'>C</font><font color='#1f2560'>D</font></b> <br>RECOMMEDATIONS");
        cdTextView.setText(text);
        btnSubmit=(TextView)rootView.findViewById(R.id.education_submit_button);
        preparingMsg=(TextView)rootView.findViewById(R.id.preparing_msg);
        examRadioGroup=(RadioGroup)rootView.findViewById(R.id.exam_rd_group);
        examRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                 switch (checkedId){
                     case R.id.rd_btn_yes:
                         mPreparingForExam(0);
                         break;

                     case R.id.rd_btn_no:
                         mPreparingForExam(1);
                         break;
                 }
            }
        });
        mMarksPicker = (NumberPicker) rootView.findViewById(R.id.marks_number_picker);
        mMarksPicker.setMaxValue(marks_arrays.length-1);
        mMarksPicker.setMinValue(0);
        mMarksPicker.setSaveFromParentEnabled(false);
        mMarksPicker.setSaveEnabled(true);
        mMarksPicker.setWrapSelectorWheel(false);
        mMarksPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mMarksPicker.setDisplayedValues(marks_arrays);

        mExamPicker = (NumberPicker) rootView.findViewById(R.id.exam_number_picker);
        mExamPicker.setMaxValue(exam_arrays.length-1);
        mExamPicker.setMinValue(0);
        mExamPicker.setSaveFromParentEnabled(false);
        mExamPicker.setSaveEnabled(true);
        mExamPicker.setWrapSelectorWheel(false);
        mExamPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mExamPicker.setDisplayedValues(exam_arrays);

        mStreamPicker = (NumberPicker) rootView.findViewById(R.id.stream_number_picker);
        mStreamPicker.setMaxValue(stream_arrays.length-1);
        mStreamPicker.setMinValue(0);
        mStreamPicker.setSaveFromParentEnabled(false);
        mStreamPicker.setSaveEnabled(true);
        mStreamPicker.setWrapSelectorWheel(false);
        mStreamPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mStreamPicker.setDisplayedValues(stream_arrays);

        mExamPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(android.widget.NumberPicker picker, int oldVal, int newVal) {
                mUpdateStreamPicker(newVal);
                selectedValue=newVal;
                mStreamPicker.setValue(0);
                mMarksPicker.setValue(0);
            }

        });

        rootView.findViewById(R.id.is_preparing_for_exam).setOnClickListener(this);
        rootView.findViewById(R.id.is_not_preparing_for_exam).setOnClickListener(this);
        rootView.findViewById(R.id.education_submit_button).setOnClickListener(this);
        if(isEditMode){
            rootView.findViewById(R.id.education_tour_guide_image).setVisibility(View.GONE);
            IS_TUTE_COMPLETED = true;
            getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.EDUCATION_SCREEN_TUTE), true).apply();
            setForEditEducation();
        }else {
            rootView.findViewById(R.id.education_tour_guide_image).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    v.setVisibility(View.GONE);
                    IS_TUTE_COMPLETED = true;
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.EDUCATION_SCREEN_TUTE), true).apply();
                    return false;
                }
            });
        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnUserEducationInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnUserEducationInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        mUpdateStreamPicker(0);
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.currentFragment = this;

        View view =  getView();
        if(view != null ){
            if(!IS_TUTE_COMPLETED) {
                view.findViewById(R.id.education_tour_guide_image).setVisibility(View.VISIBLE);
            }else {
                view.findViewById(R.id.education_tour_guide_image).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId())
        {
            case R.id.is_preparing_for_exam:
                 mPreparingForExam(0);
                break;
            case R.id.is_not_preparing_for_exam:
                mPreparingForExam(1);
                break;
            case R.id.education_submit_button:
                mUserPreparingForExam();
                break;
            default:
                break;
        }
    }

    private void mPreparingForExam(int position)
    {
        View view = getView();
        if(view != null) {
            view.findViewById(R.id.education_submit_button).setVisibility(View.VISIBLE);
            if (position == 0) {
                isUserPreparing = true;
                view.findViewById(R.id.is_preparing_for_exam).setSelected(true);
                view.findViewById(R.id.is_not_preparing_for_exam).setSelected(false);
                view.findViewById(R.id.first_step_user_image).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.second_step_user_image).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.help_us_to_understand_uTV)).setTextColor(getResources().getColor(R.color.primary_orange));
            }
            else{
                isUserPreparing = false;
                view.findViewById(R.id.is_preparing_for_exam).setSelected(false);
                view.findViewById(R.id.is_not_preparing_for_exam).setSelected(true);
                view.findViewById(R.id.first_step_user_image).setVisibility(View.INVISIBLE);
                view.findViewById(R.id.second_step_user_image).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.help_us_to_understand_uTV)).setTextColor(getResources().getColor(R.color.primary_orange));

            }
        }
    }


    private void makeArraysForPicker(){
        if(this.mUserEducationList == null)return;
        int count = this.mUserEducationList.size();

        this.mUserExamSubLevelsList = new ArrayList<>();
        this.mUserStreamLists = new ArrayList<>();
        for (int i = 0; i < count ; i++) {
            this.mUserExamSubLevelsList.addAll(this.mUserEducationList.get(i).getSublevels());
            int sublevelCount = this.mUserEducationList.get(i).getSublevels().size();
            for (int j = 0; j <sublevelCount ; j++)
                this.mUserStreamLists.add(this.mUserEducationList.get(i).getStreams());
        }

        // make Exam string arrays for picker
        int examsCount = this.mUserExamSubLevelsList.size();
        this.exam_arrays = new String[examsCount];
        for (int i = 0; i < examsCount ; i++)
            this.exam_arrays[i]  = mUserExamSubLevelsList.get(i).getName();

        // make stream String arrays for picker
        if (mUserStreamLists.size() == 0)
            return;

        ArrayList<UserEducationStreams> tempStreamList = mUserStreamLists.get(0);
        if(tempStreamList == null) return;
        int streamCount = tempStreamList.size();
        this.stream_arrays = new String[streamCount];
        for (int i = 0; i < streamCount ; i++)
            this.stream_arrays[i]  = tempStreamList.get(i).getName();

    }

    private void mUpdateStreamPicker(int position) {

        if(this.mUserStreamLists == null || position >= this.mUserStreamLists.size())
            return;

        // make stream String arrays for picker
        ArrayList<UserEducationStreams> tempStreamList = mUserStreamLists.get(position);
        if(tempStreamList == null) return;
        int streamCount = tempStreamList.size();
        this.stream_arrays = new String[streamCount];
        for (int i = 0; i < streamCount ; i++)
            this.stream_arrays[i]  = tempStreamList.get(i).getName();

        mStreamPicker.setDisplayedValues(null);
        mStreamPicker.setMaxValue(stream_arrays.length-1);
        mStreamPicker.setMinValue(0);
        mStreamPicker.setWrapSelectorWheel(false);
        mStreamPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mStreamPicker.setDisplayedValues(stream_arrays);

    }

    private void mUserPreparingForExam() {
        if (this.mListener != null) {
                HashMap<String, String> map = new HashMap<>();
                int examPosition = mExamPicker.getValue();
                int streamPosition = mStreamPicker.getValue();
                int marksPosition = mMarksPicker.getValue();
                ArrayList<UserEducationStreams> tempStreamList = mUserStreamLists.get(examPosition);

                UserEducationFragment.this.mSubLevelID = "" + mUserExamSubLevelsList.get(examPosition).getId();
                UserEducationFragment.this.mStreamID = "" + tempStreamList.get(streamPosition).getId();
                UserEducationFragment.this.mMarks = this.mGetMarks(marksPosition);
                map.put("sublevel", UserEducationFragment.this.mSubLevelID);
                map.put("stream", UserEducationFragment.this.mStreamID);
                map.put("marks", UserEducationFragment.this.mMarks);

            if(isUserPreparing) {
                if(isEditMode) {
                    map.put("is_preparing", MainActivity.user.getIs_preparing());
                    mListener.onSubmitEditedEducation(map);
                }else {
                    map.put("is_preparing", "1");
                    mListener.onEducationSelected(map);
                }
            }else{
                if (isEditMode){
                    map.put("is_preparing", MainActivity.user.getIs_preparing());
                    mListener.onSubmitEditedEducation(map);
                }else {
                    map.put("is_preparing", "0");
                    mUserNotPreparingForExam(map);
                }
//                displayAlert(getActivity());
            }
        }
    }

   private String mGetMarks(int position){
       if(position == 0 ){
          return "35";
       }else if(position == 1 ){
          return "45";
       }else if(position == 2 ){
          return "55";
       }else if(position == 3 ){
           return "65";
       }else if(position == 4 ){
           return "75";
       }else if(position == 5 ){
          return "85";
       }else if(position == 6 ){
          return "95";
       }
       return "45";
   }


    private void mUserNotPreparingForExam(HashMap<String, String> map) {
        if(this.mListener !=  null){
            this.mListener.onUserNotPreparingSelected(map);
        }

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
    public interface OnUserEducationInteractionListener {
        void onEducationSelected(HashMap<String, String> map);
        void onUserNotPreparingSelected(HashMap<String, String> map);
        void onStepByStep();
        void onIknowWhatIWant();
        void onPsychometricTest();
        void onSubmitEditedEducation(HashMap<String, String> map);
    }

    private void displayAlert(final Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        View dialogView = inflater.inflate(R.layout.not_preparing_dialog_layout, null);
        dialogBuilder.setView(dialogView);

        Button btn_step=(Button)dialogView.findViewById(R.id.btn_step_by_step);
        Button btn_iknow=(Button)dialogView.findViewById(R.id.btn_i_know);
        Button btn_psychometric=(Button)dialogView.findViewById(R.id.btn_psychometric_test);

        final AlertDialog alertDialog = dialogBuilder.create();
        btn_step.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    alertDialog.dismiss();
                mListener.onStepByStep();
            }
        });
        btn_iknow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mListener.onIknowWhatIWant();
            }
        });

        btn_psychometric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                mListener.onPsychometricTest();
            }
        });

        alertDialog.show();
    }
    public void setForEditEducation(){
        btnSubmit.setVisibility(View.VISIBLE);
        examRadioGroup.check(R.id.rd_btn_yes);
        examRadioGroup.setVisibility(View.GONE);
        preparingMsg.setVisibility(View.GONE);
    }
}
