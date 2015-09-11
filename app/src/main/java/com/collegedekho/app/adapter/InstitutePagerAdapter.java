package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.Placements;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.fragment.InstituteAboutFragment;
import com.collegedekho.app.fragment.InstituteCoursesFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstitutePlacementFragment;
import com.collegedekho.app.fragment.InstituteQnAFragment;

import java.util.ArrayList;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public class InstitutePagerAdapter extends FragmentStatePagerAdapter {
    private static final int OVERVIEW_POSITION = 0;
    private static final int ABOUT_POSITION = 1;
    private static final int COURSES_POSITION = 2;
    private static final int PLACEMENT_POSITION = 3;
    private static final int QNA_POSITION = 4;

    Institute mInstitute;
    Placements p;
    int count = 5;
    InstituteCoursesFragment f;
    InstituteOverviewFragment o;
    InstituteQnAFragment q;
    private ArrayList<ArrayList<InstituteCourse>> mCourses;
    private ArrayList<QnAQuestions> mQnAQuestions;

    public InstitutePagerAdapter(FragmentManager fragmentManager, Institute institute) {
        super(fragmentManager);
        mInstitute = institute;
        p = new Placements();
        p.about = mInstitute.getPlacement();
        p.highestSalary = mInstitute.getMax_salary();
        p.averageSalary = mInstitute.getAvg_salary();
        mCourses = new ArrayList<>();
        for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
            mCourses.add(new ArrayList<InstituteCourse>());
        }
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case OVERVIEW_POSITION:
                o = InstituteOverviewFragment.newInstance(mInstitute);
                return o;
            case ABOUT_POSITION:
                return InstituteAboutFragment.newInstance(mInstitute.getShort_name(), mInstitute.getDescription());
            case COURSES_POSITION:
                f = InstituteCoursesFragment.newInstance(mCourses);
                return f;
            case PLACEMENT_POSITION:
                return InstitutePlacementFragment.newInstance(p);
            case QNA_POSITION:
                q = InstituteQnAFragment.newInstance(mQnAQuestions, mInstitute.getName());
                return q;
        }
        return null;
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case OVERVIEW_POSITION:
                return "OVERVIEW";
            case ABOUT_POSITION:
                return "ABOUT";
            case COURSES_POSITION:
                return "COURSES";
            case PLACEMENT_POSITION:
                return "PLACEMENTS";
            case QNA_POSITION:
                return "Q & A";
        }
        return super.getPageTitle(position);
    }

    public void setCourses(ArrayList<ArrayList<InstituteCourse>> courses) {
        int count = 0;
        for (int i = 0; i < courses.size(); i++) {
            mCourses.get(i).addAll(courses.get(i));
            count += courses.get(i).size();
        }
        if (f != null)
            f.updateData(count);
    }

    public void setQnAQuestions(ArrayList<QnAQuestions> qnaQuestions)
    {
        if (q != null)
        {
            this.mQnAQuestions = qnaQuestions;
            q.instituteQnAUpdated(qnaQuestions);
        }
        this.mQnAQuestions = qnaQuestions;
    }

    public void updateShortListButton() {
        if (o != null)
            o.updateShortListButton();
    }

    public void questionAdded(QnAQuestions ques)
    {
        if (q != null)
            q.questionAdded(ques);
    }

    public void answerAdded(QnAAnswers ans)
    {

    }
    public void updateCourseFragment(int position , int tabPosition)
    {
        if(f!= null)
        {
            f.updateAdapter(position , tabPosition);
        }
    }
}
