package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Exam;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bashir on 11/12/15.
 */


public class NotPreparingFragment extends BaseFragment {
    Button btn_iknow;
    Button btn_test;
    Button btn_dont_know;
    OnTestOptionsListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.not_preparing_fragment_layout,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_iknow=(Button)view.findViewById(R.id.btn_iknow);
        btn_test=(Button)view.findViewById(R.id.btn_psychometric_test);
        btn_dont_know=(Button)view.findViewById(R.id.btn_idontknow);
        btn_iknow.setOnClickListener(this);
        btn_test.setOnClickListener(this);
        btn_dont_know.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_iknow:
                listener.onIknowWhatIWant();
                break;

            case R.id.btn_psychometric_test:
                listener.onPsychometricTest();
                break;

            case R.id.btn_idontknow:
                listener.onIDontKnow();
                break;

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
                this.listener = (OnTestOptionsListener)context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnTestOptionsListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mainActivity = (MainActivity) getActivity();

        if (mainActivity != null)
            mainActivity.currentFragment = this;

    }

    public static NotPreparingFragment newInstance(){
        NotPreparingFragment fragment = new NotPreparingFragment();
        return fragment;
    }
    public interface OnTestOptionsListener {
        void onIknowWhatIWant();
        void onIDontKnow();
        void onPsychometricTest();
    }
}
