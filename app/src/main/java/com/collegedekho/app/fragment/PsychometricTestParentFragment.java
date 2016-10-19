package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.PsychometricTestViewPagerAdapter;
import com.collegedekho.app.entities.PsychometricTestQuestion;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Bashir on 17/12/15.
 */
public class PsychometricTestParentFragment extends BaseFragment implements PsychometricTestFragment.OnNextPageListener {

    private PsychometricTestViewPagerAdapter mAdapter;
    private ViewPager mPager;
    private TextView btnSubmit;
    private int numPages = 1;
    private ArrayList<PsychometricTestQuestion> mQuestionsList;
    private OnPsychometricTestSubmitListener mListener;
    int preState = -1;
    View appBar;

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
        appBar=getActivity().findViewById(R.id.app_bar_layout);
        if(appBar!=null) {
            preState = appBar.getVisibility();
            if (preState == View.GONE) {
                appBar.setVisibility(View.VISIBLE);
            }
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
        if(preState==View.GONE){
            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) getActivity().findViewById(R.id.main_container).getLayoutParams();
            params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            getActivity().findViewById(R.id.main_container).setLayoutParams(params);
        }
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
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity)
                this.mListener = (OnPsychometricTestSubmitListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnPsychometricTestSubmitListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
        if(appBar!=null) {
            if (preState == View.GONE) {
                appBar.setVisibility(View.GONE);
                CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                getActivity().findViewById(R.id.main_container).setLayoutParams(params);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.currentFragment = this;
    }


    @Override
    public void gotoNext() {
        if (mPager.getCurrentItem() != (numPages-1)) {
            mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }else {
            btnSubmit.setVisibility(View.VISIBLE);
        }
    }


    private void submitPsychometricTest() {
        ArrayList<HashMap<String,Integer>>mapArrayList=new ArrayList<>();
        HashMap<String, Integer> questionResponse = new HashMap<>();
        JSONObject object=new JSONObject();
        for (PsychometricTestQuestion question : mQuestionsList) {
            questionResponse.put(question.getId(), Integer.valueOf(question.getAnswer()));
        }
        try {
            mapArrayList.add(questionResponse);
            object.putOpt("questions",new JSONArray(mapArrayList));
            mListener.onSubmitPsychometricTest(object);
        }catch (Exception e){
            e.printStackTrace();
        }
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_submit:
                submitPsychometricTest();
                break;
        }
    }


    public interface OnPsychometricTestSubmitListener {
        void onSubmitPsychometricTest(JSONObject object);

    }

}
