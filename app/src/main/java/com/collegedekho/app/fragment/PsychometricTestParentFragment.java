package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.PsychometricTestViewPagerAdapter;
import com.collegedekho.app.entities.PsychometricTestQuestion;

import java.util.ArrayList;

/**
 * Created by Bashir on 17/12/15.
 */
public class PsychometricTestParentFragment extends BaseFragment implements PsychometricTestFragment.OnNextPageListener {

    private PsychometricTestViewPagerAdapter mAdapter;
    private ViewPager mPager;
    private TextView btnSubmit;
    private int numPages = 1;
    private ArrayList<PsychometricTestQuestion> mQuestionsList;

    public static PsychometricTestParentFragment newInstance(ArrayList<PsychometricTestQuestion> questionsList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList("questions_list", questionsList);
        PsychometricTestParentFragment fragment = new PsychometricTestParentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mQuestionsList = bundle.getParcelableArrayList("questions_list");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.psychometric_test_parent_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPager = (ViewPager) view.findViewById(R.id.psychometric_test_viewpager);
        btnSubmit = (TextView) view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);
        btnSubmit.setVisibility(View.GONE);
        initPsychometricTest();
        mAdapter = new PsychometricTestViewPagerAdapter(getChildFragmentManager(), numPages, mQuestionsList, this);
        mPager.setAdapter(mAdapter);
    }

    private void initPsychometricTest() {
        if (mQuestionsList == null || mQuestionsList.isEmpty()) {
            return;
        }
        int size = mQuestionsList.size();
        int pages = size / 4;
        if (size % 4 == 0) {
            numPages = pages;
        } else {
            numPages = pages + 1;
        }

    }

    @Override
    public void gotoNext() {
        if (mPager.getCurrentItem() != numPages) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }else {
            btnSubmit.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                Log.e("DEBUG","Submit Test Data ...");
                break;
        }
    }
}
