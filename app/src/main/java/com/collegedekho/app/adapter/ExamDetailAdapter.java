package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.fragment.ExamDetailFragment;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 14/12/15.
 */
public class ExamDetailAdapter extends FragmentStatePagerAdapter {

    ArrayList<ProfileExam> mExamDetailList;
    private ExamDetailFragment mExamFragment[];
    private int count;

    public ExamDetailAdapter(FragmentManager fm, ArrayList<ProfileExam> examDetailList) {
        super(fm);
        if(examDetailList != null){
            count = examDetailList.size();
            this.mExamFragment =  new ExamDetailFragment[count];
        }
        this.mExamDetailList = examDetailList;
    }

    @Override
    public Fragment getItem(int position) {
        this.mExamFragment[position] = ExamDetailFragment.newInstance(mExamDetailList.get(position));
        return mExamFragment[position];
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ProfileExam examDetailObj =  mExamDetailList.get(position);
        if(examDetailObj != null){
            String title = examDetailObj.getExam_short_name().trim();
            //if(examDetailObj.getYear() != null && examDetailObj.getYear()){
               if(title == null || title.isEmpty()){
                   title = examDetailObj.getExam_name();
               }
                title = title + "  " + examDetailObj.getYear();
            //}
            return title;
        }
        return "Exam";
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
