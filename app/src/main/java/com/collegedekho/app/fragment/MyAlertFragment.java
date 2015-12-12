package com.collegedekho.app.fragment;

import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.entities.UserEducationStreams;
import com.collegedekho.app.entities.UserEducationSublevels;
import com.collegedekho.app.widget.NumberPicker;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created by sureshsaini on 8/12/15.
 */
public class MyAlertFragment extends  BaseFragment {

    private final String TAG = "MyAlert Frgament";
    private static String PARAM1 = "param1";
    private static final String USER_EDUCATION_LIST = "user_education_list";

    private ArrayList<UserEducation> mUserEducationList;
    private ArrayList<UserEducationSublevels> mUserExamSubLevelsList;
    private ArrayList<ArrayList<UserEducationStreams>> mUserStreamLists;
    private NumberPicker mExamPicker;
    private NumberPicker mStreamPicker;
    private NumberPicker mMarksPicker;

    private String[] exam_arrays ={"Schooling in 10th", "Schooling in 12th", "UG first year", "UG second year", "UG third year", "UG fourth year"};
    private String[] stream_arrays=  {"Engineering", "Management", "Design"};
    final String[] marks_arrays = {"30-40%", "40-50%","50-60%","60-70%", "70-80%", "80-90%", "90-100%",};

    public MyAlertFragment() {
        //  empty constructor
    }

    public static MyAlertFragment newInstance(ArrayList<UserEducation> userEducationList) {
        MyAlertFragment fragment = new MyAlertFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(USER_EDUCATION_LIST, userEducationList);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mUserEducationList = getArguments().getParcelableArrayList(USER_EDUCATION_LIST);
            makeArraysForPicker();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_number_picker, container, false);

        mMarksPicker = (NumberPicker) rootView.findViewById(R.id.marks_number_picker);
        mMarksPicker.setMaxValue(marks_arrays.length-1);
        mMarksPicker.setMinValue(0);
        mMarksPicker.setWrapSelectorWheel(false);
        mMarksPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mMarksPicker.setDisplayedValues(marks_arrays);

        mExamPicker = (NumberPicker) rootView.findViewById(R.id.exam_number_picker);
        mExamPicker.setMaxValue(exam_arrays.length-1);
        mExamPicker.setMinValue(0);
        mExamPicker.setWrapSelectorWheel(false);
        mExamPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mExamPicker.setDisplayedValues(exam_arrays);

        mStreamPicker = (NumberPicker) rootView.findViewById(R.id.stream_number_picker);
        mStreamPicker.setMaxValue(stream_arrays.length-1);
        mStreamPicker.setMinValue(0);
        mStreamPicker.setWrapSelectorWheel(false);
        mStreamPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mStreamPicker.setDisplayedValues(stream_arrays);

       /* mExamPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                mUpdateStreamPicker(newVal);

                mStreamPicker.setValue(0);
                mMarksPicker.setValue(0);
                setNumberPickerTextColor(picker,newVal, R.color.primary_orange);
            }
        });
        mMarksPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {


                int examPosition = mExamPicker.getValue();
                int streamPosition = mExamPicker.getValue();
                ArrayList<UserEducationStreams> tempStreamList = mUserStreamLists.get(examPosition);

                mUserExamSubLevelsList.get(examPosition).getId();
                tempStreamList.get(streamPosition).getId();


            }
        });*/

        return rootView;

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



    public static void setNumberPickerTextColor(NumberPicker numberPicker, int position, int color){
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(position);
            if(child instanceof EditText && position == count){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass().getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    numberPicker.invalidate();
                    ((EditText)child).setTextColor(color);

                }catch(Exception e){
                    e.printStackTrace();
                }

            }
        }
    }
}
