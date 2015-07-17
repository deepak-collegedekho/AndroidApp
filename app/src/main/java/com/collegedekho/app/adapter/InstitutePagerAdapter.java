package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.Placements;
import com.collegedekho.app.fragment.InstituteAboutFragment;
import com.collegedekho.app.fragment.InstituteCoursesFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstitutePlacementFragment;
import com.collegedekho.app.fragment.InstituteQnAFragment;

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
    private static final int QNA_POSITION = 4;

    Institute mInstitute;
    Placements p;
    int count = 5;
    InstituteCoursesFragment f;
    private ArrayList<ArrayList<InstituteCourse>> mCourses;

    public InstitutePagerAdapter(FragmentManager fragmentManager, Institute institute) {
        super(fragmentManager);
        mInstitute = institute;
        p = new Placements();
        p.about = mInstitute.getPlacement();
        p.highestSalary = mInstitute.getMax_salary();
        p.averageSalary = mInstitute.getAvg_salary();
        mCourses = new ArrayList<>();
        for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
            mCourses.add(new ArrayList<InstituteCourse>());
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case OVERVIEW_POSITION:
                return InstituteOverviewFragment.newInstance(mInstitute);
            case ABOUT_POSITION:
                return InstituteAboutFragment.newInstance(mInstitute.getShort_name(), mInstitute.getDescription());
            case COURSES_POSITION:
                f = InstituteCoursesFragment.newInstance(mCourses);
                return f;
            case PLACEMENT_POSITION:
                return InstitutePlacementFragment.newInstance(p);
            case QNA_POSITION:
                return InstituteQnAFragment.newInstance();
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
                return "PLACEMENTS";
            case QNA_POSITION:
                return "Q & A";
        }
        return super.getPageTitle(position);
    }

    public void setCourses(ArrayList<ArrayList<InstituteCourse>> courses) {
        int count = 0;
        for (int i = 0; i < courses.size(); i++) {
            mCourses.get(i).addAll(courses.get(i));
            count += courses.get(i).size();
        }
        if (f != null)
            f.updateData(count);
    }

}
