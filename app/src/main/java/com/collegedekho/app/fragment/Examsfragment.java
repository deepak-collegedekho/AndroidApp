package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class Examsfragment extends BaseFragment {

    private final String TAG = "Exam Frgament";
    private static String PARAM1 = "param1";

    private ArrayList<String> mExamList ;
    public Examsfragment() {
        // required empty Constructor
    }

    public Examsfragment newInstance(ArrayList<String> examList){
        Examsfragment fragment = new Examsfragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null)
        {
            this.mExamList = args.getStringArrayList(PARAM1);
        }
        // for demo
        this.mExamList = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            this.mExamList.add("exam"+i);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = (View)inflater.inflate(R.layout.fragment_user_exams, container, false);
        ((TextView)rootView.findViewById(R.id.points_test_view)).setText("YOU  HAVE EARNED FOR SHARING YOUR DETAIL");
        return rootView;
    }
}
