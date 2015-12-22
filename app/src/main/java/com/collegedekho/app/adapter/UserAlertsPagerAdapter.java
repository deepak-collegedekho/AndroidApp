package com.collegedekho.app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.MyAlertDate;
import com.collegedekho.app.entities.MyAlertDateDescription;
import com.collegedekho.app.fragment.UserAlertDetailsFragment;

import java.util.ArrayList;

/**
 * Created by Bashir on 14/12/15.
 */
public class UserAlertsPagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_PAGES=1;
    private ArrayList<MyAlertDate> alertDatesList;
    public UserAlertsPagerAdapter(FragmentManager fm, int pageCount,ArrayList<MyAlertDate> alertDatesList)
    {
        super(fm);
        this.NUM_PAGES=pageCount;
        this.alertDatesList=alertDatesList;
        NUM_PAGES=alertDatesList.size();
    }

    @Override
    public Fragment getItem(int position) {
        MyAlertDate myAlertDate=alertDatesList.get(position);
        ArrayList<MyAlertDateDescription> dates=myAlertDate.getDates();
        Fragment fragment= UserAlertDetailsFragment.newInstance(dates,myAlertDate.getMonth(),myAlertDate.getYear());
        Bundle b=new Bundle();
        b.putInt("id", position);
        b.putInt("month",myAlertDate.getMonth()-1);
        b.putInt("year",myAlertDate.getYear());
        b.putParcelableArrayList("dates_list",dates);
        fragment.setArguments(b);
        return  fragment;
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }

}
