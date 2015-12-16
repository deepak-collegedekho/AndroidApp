package com.collegedekho.app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.fragment.CalendarFragment;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Bashir on 14/12/15.
 */
public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_PAGES=1;
    ArrayList<Chapters> chapterList;
    public CalendarPagerAdapter(FragmentManager fm, int pageCount)
    {
        super(fm);
        this.NUM_PAGES=pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment= CalendarFragment.newInstance(chapterList);
        Bundle b=new Bundle();
        b.putInt("id", position);
        fragment.setArguments(b);
        return  fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
