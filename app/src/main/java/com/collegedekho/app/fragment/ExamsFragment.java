package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.widget.GridSpacingItemDecoration;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 30/11/15.
 */
public class ExamsFragment extends BaseFragment {

    private final String TAG = "Exam Frgament";
    private static String PARAM1 = "param1";

    private ArrayList<Exam> mExamList ;
    public ExamsFragment() {
        // required empty Constructor
    }

    public static ExamsFragment newInstance(ArrayList<Exam> examList){
        ExamsFragment fragment = new ExamsFragment();
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
            this.mExamList = args.getParcelableArrayList(PARAM1);
        }
        // for demo
        this.mExamList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Exam obj = new Exam();
            obj.setmExamName("exam"+i);
            this.mExamList.add(obj);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_exams, container, false);
        ((TextView)rootView.findViewById(R.id.points_test_view)).setText("YOU  HAVE EARNED FOR SHARING YOUR DETAIL");
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.exams_recycle_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 5, false));
        recyclerView.setHasFixedSize(true);

        ExamsAdapter mAdapter = new ExamsAdapter(getActivity(), this.mExamList);
        recyclerView.setAdapter(mAdapter);

        return rootView;
    }

}
