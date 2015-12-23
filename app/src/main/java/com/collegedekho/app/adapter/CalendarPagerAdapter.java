package com.collegedekho.app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.ChapterDetails;
import com.collegedekho.app.entities.Chapters;
import com.collegedekho.app.entities.MyAlertDate;
import com.collegedekho.app.fragment.CalendarFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;

/**
 * Created by Bashir on 14/12/15.
 */
public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_PAGES=1;
    LinkedHashMap<String,String> mYearCalendar;
    private LinkedHashMap<String,ArrayList<ChapterDetails>> mChaptersDetailsList;
    private String[] monthNames = {"","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

    public CalendarPagerAdapter(FragmentManager fm, int pageCount,LinkedHashMap<String,String> yearCalendar,LinkedHashMap<String,ArrayList<ChapterDetails>> chaptersDetailsList)
    {
        super(fm);
        this.mChaptersDetailsList=chaptersDetailsList;
        this.mYearCalendar=yearCalendar;
        this.NUM_PAGES=pageCount;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment= CalendarFragment.newInstance(mYearCalendar,mChaptersDetailsList);
        Bundle b=new Bundle();
        b.putInt("id", position);
        fragment.setArguments(b);
        return  fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Calendar calendar=Calendar.getInstance();
        calendar.add(Calendar.MONTH,position);
        StringBuilder builder=new StringBuilder();
            builder.append(monthNames[calendar.get(Calendar.MONTH)+1]);
            builder.append(" ");
            builder.append(calendar.get(Calendar.YEAR));
        return builder.toString();
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
