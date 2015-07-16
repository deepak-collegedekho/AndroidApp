package com.collegedekho.app.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CoursePagerAdapter;
import com.collegedekho.app.entities.InstituteCourse;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteCoursesFragment extends Fragment {
    private static final String ARG_COURSES = "courses";
    private static final String ARG_COURSE_COUNT = "course_count";

    private ArrayList<ArrayList<InstituteCourse>> mCourses;
    private CoursePagerAdapter adapter;

    public InstituteCoursesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param courses Parameter 1.
     * @return A new instance of fragment InstituteOverviewFragment.
     */
    public static InstituteCoursesFragment newInstance(ArrayList<ArrayList<InstituteCourse>> courses) {
        InstituteCoursesFragment fragment = new InstituteCoursesFragment();
        Bundle args = new Bundle();
        for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
            args.putParcelableArrayList(InstituteCourse.CourseLevel.values()[i].name(), courses.get(i));
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCourses = new ArrayList<>();
            for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
                ArrayList<InstituteCourse> a = getArguments().getParcelableArrayList(InstituteCourse.CourseLevel.values()[i].name());
                mCourses.add(a);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_courses, container, false);
        ViewPager mPager = (ViewPager) rootView.findViewById(R.id.pager_courses);
        adapter = new CoursePagerAdapter(getChildFragmentManager(), mCourses);
        mPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.course_tab_layout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.text_subhead_blue));
        tabLayout.setupWithViewPager(mPager);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
            outState.putParcelableArrayList(InstituteCourse.CourseLevel.values()[i].name(), mCourses.get(i));
        }
    }
}