package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.PsychometricTestAdapter;
import com.collegedekho.app.entities.PsychometricTestQuestion;

import java.util.ArrayList;

/**
 * Created by {Bashir} on {11/12/15}.
 */
public class PsychometricTestFragment extends BaseFragment implements PsychometricTestAdapter.OnItemClickListener {

    private ArrayList<PsychometricTestQuestion> subList;
    private static PsychometricTestFragment.OnNextPageListener mNextListener;
    public static PsychometricTestFragment newInstance(ArrayList<PsychometricTestQuestion> questionsList,PsychometricTestFragment.OnNextPageListener listener) {
        Bundle args = new Bundle();
        mNextListener=listener;
        args.putParcelableArrayList("psychometric_questions_list", questionsList);
        PsychometricTestFragment fragment = new PsychometricTestFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {

            ArrayList<PsychometricTestQuestion> mQuestionList = bundle.getParcelableArrayList("psychometric_questions_list");
            int id=bundle.getInt("id");
            int start=id*4;
            int end=start+4;
            if(end>mQuestionList.size()){
                end=mQuestionList.size()-start;
            }
            subList = new ArrayList<>(mQuestionList.subList(start, end));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.psychometric_test_fragment, container, false);
        layoutManager = new LinearLayoutManager(getActivity());
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.psychometric_test_recycler);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        PsychometricTestAdapter adapter = new PsychometricTestAdapter(getActivity(), subList, this);
        recyclerView.setAdapter(adapter);
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
        switch (v.getId()) {
            case R.id.btn_submit:
//                submitPsychometricTest();
                break;
            default:
                break;
        }
    }


    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
    }

    @Override
    public void onItemClicked(int position) {
        mNextListener.gotoNext();
    }

    public interface OnNextPageListener{
        void gotoNext();
    }
}
