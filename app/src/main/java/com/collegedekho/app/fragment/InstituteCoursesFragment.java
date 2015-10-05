package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CoursePagerAdapter;
import com.collegedekho.app.entities.InstituteCourse;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteCoursesFragment extends BaseFragment {
    private static final String ARG_COURSES = "courses";
    private static final String ARG_COURSE_COUNT = "course_count";

    private ArrayList<ArrayList<InstituteCourse>> mCourses;
    private CoursePagerAdapter mAdapter;
    private int mCourseCount;
    private static TabLayout mTabLayout;

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
            this.mCourses = new ArrayList<>();
            for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
                ArrayList<InstituteCourse> a = getArguments().getParcelableArrayList(InstituteCourse.CourseLevel.values()[i].name());
                this.mCourseCount += a.size();
                this.mCourses.add(a);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_courses, container, false);
        if (mCourseCount < 1) {
            ((TextView) rootView.findViewById(R.id.course_tab_title)).setText("Loading Course...");
        } else {
            init(rootView);
        }
        return rootView;
    }

    private void init(View rootView) {
        ViewPager mPager = (ViewPager) rootView.findViewById(R.id.pager_courses);
        this.mAdapter = new CoursePagerAdapter(getChildFragmentManager(), mCourses);
        mPager.setAdapter(mAdapter);
        mTabLayout = (TabLayout) rootView.findViewById(R.id.course_tab_layout);
        mTabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.text_subhead_blue));
        mTabLayout.setupWithViewPager(mPager);
        mTabLayout.setVisibility(View.VISIBLE);
        ((TextView) rootView.findViewById(R.id.course_tab_title)).setText("Courses Offered");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
            outState.putParcelableArrayList(InstituteCourse.CourseLevel.values()[i].name(), mCourses.get(i));
        }
    }

    public void updateData(int courseCount) {
        this.mCourseCount = courseCount;
        View rootView = getView();
        if (rootView != null) {
            if (courseCount > 0) {
                init(rootView);
            } else if (courseCount < 1) {
                ((TextView) rootView.findViewById(R.id.course_tab_title)).setText("Courses info not available");
            }
        }
    }
    public void updateAdapter(int position ,int tabPosition)
    {
        mAdapter.updateAdapter(position , tabPosition);
    }
    public static int getTabposition()
    {
        return mTabLayout.getSelectedTabPosition();
    }

}