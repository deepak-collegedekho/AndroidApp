package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.Placements;
import com.collegedekho.app.entities.QnAAnswers;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.fragment.InstituteAboutFragment;
import com.collegedekho.app.fragment.InstituteCoursesFragment;
import com.collegedekho.app.fragment.InstituteInfrastructureFragment;
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
    private static final int COURSES_POSITION = 1;
    private static final int PLACEMENT_POSITION = 2;
    private static final int INFRA_POSITION = 3;

    private Institute mInstitute;
    private Placements p;
    private int count = 4;
    private InstituteCoursesFragment f;
    private InstituteOverviewFragment o;
    private InstituteQnAFragment q;
    private ArrayList<ArrayList<InstituteCourse>> mCourses;
    private ArrayList<QnAQuestions> mQnAQuestions;

    public InstitutePagerAdapter(FragmentManager fragmentManager, Institute institute) {
        super(fragmentManager);
        this.mInstitute = institute;
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
                this.o = InstituteOverviewFragment.newInstance(this.mInstitute);
                return this.o;
            case COURSES_POSITION:
                this.f = InstituteCoursesFragment.newInstance(this.mCourses, this.mInstitute);
                return this.f;
            case PLACEMENT_POSITION:
                InstitutePlacementFragment frag = InstitutePlacementFragment.newInstance(this.p, this.mInstitute);
                return frag;
            case INFRA_POSITION:
                InstituteInfrastructureFragment instituteInfrastructureFragment = InstituteInfrastructureFragment.newInstance(this.mInstitute);
                return instituteInfrastructureFragment;
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
            case COURSES_POSITION:
                return "COURSES";
            case PLACEMENT_POSITION:
                return "PLACEMENTS";
            case INFRA_POSITION:
                return "INFRASTRUCTURE";
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
    public void updateCourseFragment()
    {
        if(f!= null)
        {
            f.updateAdapter();
        }
    }
}
