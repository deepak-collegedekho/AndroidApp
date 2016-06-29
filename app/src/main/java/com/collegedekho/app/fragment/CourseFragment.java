package com.collegedekho.app.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CourseListAdapter;
import com.collegedekho.app.entities.InstituteCourse;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CourseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CourseFragment extends BaseFragment {
    private static final String ARG_PARAM1 = "param1";

    private ArrayList<InstituteCourse> courses;
    private CourseListAdapter mAdapter;

    public CourseFragment() {
        // Required empty public constructor
    }

    public static CourseFragment newInstance(ArrayList<InstituteCourse> courses) {
        Log.e("CI-CF", "newInstance: course's level is : " + courses.get(0).level);
        CourseFragment fragment = new CourseFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_PARAM1, courses);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            courses = getArguments().getParcelableArrayList(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        RecyclerView rview = (RecyclerView) inflater.inflate(R.layout.fragment_course, container, false);
        this.mAdapter = new CourseListAdapter(getActivity(), courses);
        rview.setAdapter(mAdapter);
        rview.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rview.setHasFixedSize(true);
        return rview;
    }

    public void updateAdapter() {
        Log.e("CI-CF", "updateAdapter for level :: " + courses.get(0).level);
        this.mAdapter.updateAdapter();

    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}
