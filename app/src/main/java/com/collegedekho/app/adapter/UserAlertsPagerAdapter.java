package com.collegedekho.app.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.collegedekho.app.entities.MyAlertDate;
import com.collegedekho.app.entities.MyAlertDateDescription;
import com.collegedekho.app.fragment.UserAlertDetailsFragment;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

/**
 * Created by Bashir on 14/12/15.
 */
public class UserAlertsPagerAdapter extends FragmentStatePagerAdapter {
    private int NUM_PAGES=1;
    private ArrayList<MyAlertDate> alertDatesList;
    private String[] monthNames = {"","January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

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
    public Parcelable saveState() {
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        MyAlertDate alertDate =  alertDatesList.get(position);
        StringBuilder builder=new StringBuilder();
        if(alertDate != null){
            builder.append(monthNames[alertDate.getMonth()]);
            builder.append(" ");
            builder.append(alertDate.getYear());
        }
            return builder.toString();
    }
    @Override
    public int getCount() {
        return NUM_PAGES;
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
