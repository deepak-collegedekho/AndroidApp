package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.ExamDetail;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 14/12/15.
 */
public class ExamDetailFragment extends BaseFragment{

    private final String TAG = "Exam Detail Fragment";
    private static String PARAM1 = "param1";
    private static final String EXAM_LIST = "user_education_list";
    private ArrayList<ExamDetail> mExamDetailList;


    public ExamDetailFragment() {
        //  empty constructor
    }

    public static ExamDetailFragment newInstance(ArrayList<ExamDetail> examDetailList) {
        ExamDetailFragment fragment = new ExamDetailFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(EXAM_LIST, examDetailList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mExamDetailList = getArguments().getParcelableArrayList(EXAM_LIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_exam_detail, container, false);
        return rootView;
    }
}
