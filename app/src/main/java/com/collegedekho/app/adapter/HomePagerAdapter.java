package com.collegedekho.app.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.fragment.CollegesDashboard;
import com.collegedekho.app.fragment.FeedFragment;
import com.collegedekho.app.fragment.InteractionDashboard;
import com.collegedekho.app.fragment.PrepareDashboard;

import java.util.ArrayList;

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

    public void feedListRefreshed(ArrayList mFeedList, String nextUrl) {
        if(this.mFeedFragment != null && this.mFeedFragment.isAdded()) {
            this.mFeedFragment.feedRefreshed(mFeedList, nextUrl);
        }
    }

    public void feedListNextLoaded(ArrayList mFeedList, String nextUrl) {
        if(this.mFeedFragment != null && this.mFeedFragment.isAdded())
            this.mFeedFragment.updateList(mFeedList, nextUrl);
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
}
