package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;

/**
 * Created by bashir on 11/12/15.
 */


public class NotPreparingFragment extends BaseFragment {
    View btn_iknow;
    View btn_psychometric;
    View btn_step_by_step;
    OnNotPreparingOptionsListener listener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView=(ViewGroup)inflater.inflate(R.layout.not_preparing_fragment_layout,container,false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btn_iknow=view.findViewById(R.id.btn_i_know);
        btn_psychometric =view.findViewById(R.id.btn_psychometric_test);
        btn_step_by_step =view.findViewById(R.id.btn_step_by_step);

        btn_iknow.setOnClickListener(this);
        btn_psychometric.setOnClickListener(this);
        btn_step_by_step.setOnClickListener(this);
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_i_know:
                listener.onIknowWhatIWant();
                break;

            case R.id.btn_psychometric_test:
                listener.onPsychometricTest();
                break;

            case R.id.btn_step_by_step:
                listener.onStepByStep();
                break;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
                this.listener = (OnNotPreparingOptionsListener)context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    + "must implement OnNotPreparingOptionsListener");
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

    public interface OnNotPreparingOptionsListener {
        void onIknowWhatIWant();
        void onStepByStep();
        void onPsychometricTest();
    }
}
