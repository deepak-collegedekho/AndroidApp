package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.fragment.CourseFragment;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public class CoursePagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<String> titles;
    ArrayList<ArrayList<InstituteCourse>> mCourse;
    private CourseFragment courseFragment[];
    private int count;
    public CoursePagerAdapter(FragmentManager fm, ArrayList<ArrayList<InstituteCourse>> course) {
        super(fm);
        mCourse = new ArrayList<>();
        titles = new ArrayList<>();
        for (int i = 0; i < course.size(); i++) {
            if (course.get(i).size() > 0) {
                mCourse.add(course.get(i));
                count++;
                titles.add(InstituteCourse.CourseLevel.values()[i].name().replace("_", " "));
            }
        }
        courseFragment = new CourseFragment[count];
    }

    @Override
    public Fragment getItem(int position) {

            courseFragment[position] = CourseFragment.newInstance(mCourse.get(position));
             return courseFragment[position];
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void updateAdapter(int position , int tabPosition)
    {
            if(courseFragment[tabPosition] != null)
            {
                courseFragment[tabPosition].updateAdapter(position);
            }
    }
}
