package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.fragment.ExamDetailFragment;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 14/12/15.
 */
public class ExamDetailAdapter extends FragmentStatePagerAdapter {

    ArrayList<ExamDetail> mExamDetailList;
    private ExamDetailFragment mExamFragment[];
    private int count;

    public ExamDetailAdapter(FragmentManager fm, ArrayList<ExamDetail> examDetailList) {
        super(fm);
        if(examDetailList != null){
            count = examDetailList.size();
            this.mExamFragment =  new ExamDetailFragment[count];
        }
        this.mExamDetailList = examDetailList;
    }

    @Override
    public Fragment getItem(int position) {
        this.mExamFragment[position] = ExamDetailFragment.newInstance(mExamDetailList);
        return mExamFragment[position];
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        ExamDetail examDetailObj =  mExamDetailList.get(position);
        if(examDetailObj != null){
            String title = examDetailObj.getExam_short_name().trim();
            if(examDetailObj.getYear().trim() != null){
                title = title + "  " + examDetailObj.getYear().trim();
            }
            return title;
        }
        return "Exam";
    }

}
