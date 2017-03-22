package com.collegedekho.app.adapter;

import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;

import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.fragment.CollegesDashboard;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.fragment.InteractionDashboard;
import com.collegedekho.app.fragment.PrepareDashboard;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.HashMap;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private Activity mActivity;
    private FeedFragment mFeedFragment = null;
    private CollegesDashboard mCollegesDashboard = null;
    private InteractionDashboard mInteractionDashboard = null;
    private PrepareDashboard mPrepareDashboard = null;

    public HomePagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.mActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        ArrayList<ProfileExam> list = new ArrayList<>();
        switch (position) {
            case 0:
                fragment = this.mFeedFragment = FeedFragment.newInstance();
                break;
            case 1:
                if(MainActivity.mProfile != null && MainActivity.mProfile.getYearly_exams() != null) {
                    list.addAll(MainActivity.mProfile.getYearly_exams());
                }
                fragment = this.mCollegesDashboard = CollegesDashboard.newInstance(list);
                break;
            case 2:
                fragment = this.mInteractionDashboard = InteractionDashboard.newInstance();
                break;
            case 3:
                if(MainActivity.mProfile != null && MainActivity.mProfile.getYearly_exams() != null) {
                    list.addAll(MainActivity.mProfile.getYearly_exams());
                }
                fragment =  this.mPrepareDashboard = PrepareDashboard.newInstance(list);
                break;
            default:
                break;
        }
        Log.e(" fragment Object ", "fragment "+ fragment);
        return fragment;
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title="Hello";
        return title;
    }

    @Override
    public int getCount() {
        return 4;
    }

    public void feedListLoaded(ArrayList mFeedList, String nextUrl) {
        if(this.mFeedFragment != null && this.mFeedFragment.isAdded())
            this.mFeedFragment.updateList(mFeedList, nextUrl);
    }

    public void feedListRefreshed(ArrayList mFeedList, String nextUrl, boolean hasFailed) {
        if(this.mFeedFragment != null && this.mFeedFragment.isAdded()) {
            this.mFeedFragment.feedRefreshed(mFeedList, nextUrl, hasFailed);
        }
    }

    public void feedListNextLoaded(ArrayList mFeedList, String nextUrl) {
        if(this.mFeedFragment != null && this.mFeedFragment.isAdded())
            this.mFeedFragment.updateList(mFeedList, nextUrl);
    }

    public void removeProfileCompletionLayout() {
        if(this.mFeedFragment != null && this.mFeedFragment.isAdded())
            this.mFeedFragment.removeProfileCompletionLayout();
    }

    public void feedAction(String type, HashMap<String, String> dataMap) {
        if (this.mFeedFragment != null && this.mFeedFragment.isAdded())
            this.mFeedFragment.feedAction(type, dataMap);
        if (this.mCollegesDashboard != null && this.mCollegesDashboard.isAdded())
            this.mCollegesDashboard.updateDataForExam();
    }

    public void updateUserProfile() {
        if(this.mCollegesDashboard != null && this.mCollegesDashboard.isAdded())
            this.mCollegesDashboard.updateUserInfo();
    }

    public void updateUserYearlyExamSummary(ExamSummary examSummary) {
        if(this.mCollegesDashboard != null && this.mCollegesDashboard.isAdded())
            this.mCollegesDashboard.updateUserYearlyExamSummary(examSummary);
    }

    public void updateExamsList(ArrayList<ProfileExam> yearly_exams) {
        if(this.mCollegesDashboard != null && this.mCollegesDashboard.isAdded())
            this.mCollegesDashboard.updateExamsList(yearly_exams);
    }

    public void updateExamSummary(ExamSummary updateExamSummary) {
        if(this.mCollegesDashboard != null && this.mCollegesDashboard.isAdded())
            this.mCollegesDashboard.updateExamSummary(updateExamSummary);
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
