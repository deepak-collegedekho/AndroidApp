package com.collegedekho.app.adapter;

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
import com.collegedekho.app.fragment.TabFragment;

import java.util.ArrayList;

public class HomePagerAdapter extends FragmentStatePagerAdapter {

    private MainActivity mActivity;
    private FeedFragment mFeedFragment = null;
    private TabFragment mTabFragment = null;
    private CollegesDashboard mCollegesDashboard = null;
    private InteractionDashboard mInteractionDashboard = null;
    private PrepareDashboard mPrepareDashboard = null;
    private int selectedTab;

    public HomePagerAdapter(FragmentManager fm, MainActivity activity) {
        super(fm);
        this.mActivity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        ArrayList<ProfileExam> list = new ArrayList<>();
        switch (position) {
            case 0:
                this.mFeedFragment = FeedFragment.newInstance();
                fragment = this.mFeedFragment;
                break;
            case 1:
                if(MainActivity.mProfile != null && MainActivity.mProfile.getYearly_exams() != null) {
                    list.addAll(MainActivity.mProfile.getYearly_exams());
                }
                this.mCollegesDashboard = CollegesDashboard.newInstance(1, list);
                fragment = this.mCollegesDashboard;
                break;
            case 2:
                if(MainActivity.mProfile != null && MainActivity.mProfile.getYearly_exams() != null) {
                    list.addAll(MainActivity.mProfile.getYearly_exams());
                }
                this.mInteractionDashboard = InteractionDashboard.newInstance(2, list);
                fragment = this.mInteractionDashboard;
                break;
            case 3:
                if(MainActivity.mProfile != null && MainActivity.mProfile.getYearly_exams() != null) {
                    list.addAll(MainActivity.mProfile.getYearly_exams());
                }
                this.mPrepareDashboard = PrepareDashboard.newInstance(3, list);
                fragment = this.mPrepareDashboard;
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
        if(this.mFeedFragment != null)
            this.mFeedFragment.updateList(mFeedList, nextUrl);
    }

    public void feedListRefreshed(ArrayList mFeedList, String nextUrl) {
        if(this.mFeedFragment != null)
            this.mFeedFragment.feedRefreshed(mFeedList, nextUrl);
    }

    public void feedListNextLoaded(ArrayList mFeedList, String nextUrl) {
        if(this.mFeedFragment != null)
            this.mFeedFragment.updateList(mFeedList, nextUrl);
    }

    public void updateUserProfile() {
        if(this.mCollegesDashboard != null)
            this.mCollegesDashboard.updateUserInfo();
    }

    public void updateUserYearlyExam(boolean update) {
        if(this.mCollegesDashboard != null)
            this.mCollegesDashboard.updateCollegeCountFromVolley(update);
    }

    public int getSelectedTab() {
        if(this.mCollegesDashboard != null)
            return this.mCollegesDashboard.getSelectedTab();

        return -1;
    }

    public void setSelectedTab(int selectedTab) {
        if(this.mCollegesDashboard != null)
            this.mCollegesDashboard.setSelectedTab(selectedTab);
    }

    public void updateExamsList(ArrayList<ProfileExam> yearly_exams) {
        if(this.mCollegesDashboard != null)
            this.mCollegesDashboard.updateExamsList(yearly_exams);
    }

    public void updateExamSummary(ExamSummary updateExamSummary) {
        if(this.mCollegesDashboard != null)
            this.mCollegesDashboard.updateExamSummary(updateExamSummary);
    }
}
