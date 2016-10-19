package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.ProfileExam;


/**
 * Created by {sureshsaini} on {14/12/15}.
 */
public class ExamDetailFragment extends BaseFragment{

    private final static  String TAG = "Exam Detail Fragment";
    private static final String EXAM_LIST = "user_education_list";

    public ExamDetailFragment() {
        //  empty constructor
    }

    public static ExamDetailFragment newInstance(ProfileExam examDetailList) {
        ExamDetailFragment fragment = new ExamDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXAM_LIST, examDetailList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_exam_detail, container, false);
    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }
}
