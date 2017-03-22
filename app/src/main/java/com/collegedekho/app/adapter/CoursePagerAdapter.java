package com.collegedekho.app.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.fragment.CourseFragment;
import com.crashlytics.android.Crashlytics;

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
        Log.e("CI-CPA", "getItem :: position is : " + position);
        courseFragment[position] = CourseFragment.newInstance(mCourse.get(position));
        return courseFragment[position];
    }
    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    public void updateAdapter(int tabPosition)
    {
        Log.e("CI-CPA", "Step 1 :: updateAdapter :: position is : " + tabPosition);

        if(courseFragment[tabPosition] != null)
        {
            Log.e("CI-CPA", "Step 2 :: updateAdapter :: position is : " + tabPosition);

            courseFragment[tabPosition].updateAdapter();
        }
    }

    @Override
    public void finishUpdate(ViewGroup container) {
        try{
            super.finishUpdate(container);
        } catch (NullPointerException nullPointerException){
            Crashlytics.logException(nullPointerException);
            System.out.println("Catch the NullPointerException in FragmentPagerAdapter.finishUpdate");
        }
    }
}
