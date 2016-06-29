package com.collegedekho.app.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.R;
import com.collegedekho.app.adapter.CoursePagerAdapter;
import com.collegedekho.app.display.CustomViewPager;
import com.collegedekho.app.display.DepthPageTransformer;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.resource.MySingleton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteCoursesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteCoursesFragment extends BaseFragment {
    private static final String ARG_COURSES = "courses";
    private static final String ARG_INSTITUTE = "institute";
    private static final String ARG_COURSE_COUNT = "course_count";

    private ArrayList<ArrayList<InstituteCourse>> mCourses;
    private CoursePagerAdapter mAdapter;
    private int mCourseCount;
    private static TabLayout mTabLayout;
    private Institute mInstitute;
    private CustomViewPager mPager;

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
    public static InstituteCoursesFragment newInstance(ArrayList<ArrayList<InstituteCourse>> courses, Institute institute) {
        InstituteCoursesFragment fragment = new InstituteCoursesFragment();
        Bundle args = new Bundle();
        for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
            args.putParcelableArrayList(InstituteCourse.CourseLevel.values()[i].name(), courses.get(i));
        }
        args.putParcelable(ARG_INSTITUTE, institute);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mInstitute = getArguments().getParcelable(ARG_INSTITUTE);
            this.mCourses = new ArrayList<>();
            for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
                ArrayList<InstituteCourse> a = getArguments().getParcelableArrayList(InstituteCourse.CourseLevel.values()[i].name());
                if(a!=null) {
                    this.mCourseCount += a.size();
                    this.mCourses.add(a);
                }
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_courses, container, false);

        NetworkImageView imageView = ((NetworkImageView) rootView.findViewById(R.id.courses_image));
        imageView.setDefaultImageResId(R.drawable.default_banner);
        imageView.setErrorImageResId(R.drawable.default_banner);
        if (this.mInstitute.getImages().get("Student") != null) {
            ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            imageView.setImageUrl(this.mInstitute.getImages().get("Student"), imageLoader);
        }

        if (mCourseCount < 1) {
            ((TextView) rootView.findViewById(R.id.course_tab_title)).setText("Loading Course...");
        } else {
            (rootView.findViewById(R.id.course_tab_title)).setVisibility(View.GONE);
            init(rootView);
        }

        return rootView;
    }

    private void init(View rootView) {
        this.mPager = (CustomViewPager) rootView.findViewById(R.id.pager_courses);
        this.mAdapter = new CoursePagerAdapter(getChildFragmentManager(), mCourses);
        this.mPager.setAdapter(this.mAdapter);
        this.mPager.setPageTransformer(true, new DepthPageTransformer());

        (rootView.findViewById(R.id.course_tab_title)).setVisibility(View.GONE);


        this.mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("","");
            }
            @Override
            public void onPageSelected(int position) {
                Log.e("CI-ICF", "onPageSelected :: position is : " + position);
                InstituteCoursesFragment.this.mAdapter.updateAdapter(position);
                InstituteCoursesFragment.this.mPager.setCurrentItem(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("","");
            }
        });

        ((ViewPager.LayoutParams) (rootView.findViewById(R.id.pager_courses_header)).getLayoutParams()).isDecor = true;
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
                (rootView.findViewById(R.id.course_tab_title)).setVisibility(View.VISIBLE);
                ((TextView) rootView.findViewById(R.id.course_tab_title)).setText("Courses info not available");
            }
        }
    }

    public void updateAdapter()
    {
        Log.e("CI-ICF", "updateAdapter :: position is : " + this.mPager.getCurrentItem());
        this.mAdapter.updateAdapter(this.mPager.getCurrentItem());
    }

/*
    public static int getTabposition()
    {
        return mTabLayout.getSelectedTabPosition();
    }
*/

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }
}