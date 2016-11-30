package com.collegedekho.app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.fragment.CalendarFragment;

import java.util.Calendar;

/**
 * Created by Bashir on 14/12/15.
 */
public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_PAGES=1;
    private String[] monthNames = {"","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
    private CalendarItemDetailsAdapter.OnItemStateChangeListener mListener;
    private Fragment fragment;

    public CalendarPagerAdapter(FragmentManager fm, int pageCount, CalendarItemDetailsAdapter.OnItemStateChangeListener listener)
    {
        super(fm);
        this.NUM_PAGES=pageCount;
        this.mListener=listener;
    }

    @Override
    public Fragment getItem(int position) {
        fragment= CalendarFragment.newInstance(mListener);
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

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
    public void setNumberOfPages(int number){
        NUM_PAGES=number;
        notifyDataSetChanged();
    }
    public void update(){
        if (fragment!=null && fragment instanceof CalendarFragment){
            ((CalendarFragment)fragment).updateData();
        }
    }
}
