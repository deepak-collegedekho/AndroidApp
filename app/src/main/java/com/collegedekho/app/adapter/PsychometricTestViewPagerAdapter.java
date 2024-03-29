package com.collegedekho.app.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.collegedekho.app.entities.PsychometricTestQuestion;
import com.collegedekho.app.fragment.PsychometricTestFragment;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

/**
 * Created by Bashir on 17/12/15.
 */
public class PsychometricTestViewPagerAdapter extends FragmentStatePagerAdapter{
    private int NUM_PAGES = 1;
    ArrayList<PsychometricTestQuestion> questionsList;
    PsychometricTestFragment.OnNextPageListener listener;
    public PsychometricTestViewPagerAdapter(FragmentManager fm, int pageCount, ArrayList<PsychometricTestQuestion> questionsList, PsychometricTestFragment.OnNextPageListener listener) {
        super(fm);
        this.questionsList=questionsList;
        this.NUM_PAGES = pageCount;
        this.listener=listener;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = PsychometricTestFragment.newInstance(questionsList,listener);
        Bundle b = new Bundle();
        b.putInt("id", position);
        b.putParcelableArrayList("psychometric_questions_list",questionsList);
        fragment.setArguments(b);
        return fragment;
    }
    @Override
    public Parcelable saveState() {
        return null;
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
