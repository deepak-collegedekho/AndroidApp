package com.collegedekho.app.adapter;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.fragment.TestFragment;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public class TestPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<String> mTestList;
    private TestFragment testFragment;

    public TestPagerAdapter(FragmentManager fragmentManager, ArrayList<String> testList) {
        super(fragmentManager);
        this.mTestList = testList;
        this.testFragment = new TestFragment();
    }

    @Override
    public Fragment getItem(int position) {

        testFragment= TestFragment.newInstance(this.mTestList.get(position));
        return testFragment;
    }
    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public int getCount() {
        return mTestList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "TEST"+position;
    }

}
