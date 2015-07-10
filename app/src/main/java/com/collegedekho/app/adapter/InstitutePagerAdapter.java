package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.fragment.CollegeAboutFragment;
import com.collegedekho.app.fragment.CollegeCoursesFragment;
import com.collegedekho.app.fragment.CollegeOverviewFragment;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public class InstitutePagerAdapter extends FragmentPagerAdapter {

    private static final int OVERVIEW_POSITION = 0;
    private static final int ABOUT_POSITION = 1;
    private static final int COURSES_POSITION = 2;
    private static final int PLACEMENT_POSITION = 3;

    Institute mInstitute;
    int count = 2;
    private ArrayList<ArrayList<InstituteCourse>> mCourses;

    public InstitutePagerAdapter(FragmentManager fragmentManager, Institute institute) {
        super(fragmentManager);
        mInstitute = institute;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case OVERVIEW_POSITION:
                return CollegeOverviewFragment.newInstance(mInstitute);
            case ABOUT_POSITION:
                return CollegeAboutFragment.newInstance(mInstitute.getShort_name(), mInstitute.getDescription());
            case COURSES_POSITION:
                return CollegeCoursesFragment.newInstance(mCourses);
            case PLACEMENT_POSITION:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case OVERVIEW_POSITION:
                return "OVERVIEW";
            case ABOUT_POSITION:
                return "ABOUT";
            case COURSES_POSITION:
                return "COURSES";
            case PLACEMENT_POSITION:
                break;
        }
        return super.getPageTitle(position);
    }

    public void setCourses(ArrayList<ArrayList<InstituteCourse>> courses) {
        mCourses = courses;
        count = 3;
        notifyDataSetChanged();
    }

}
