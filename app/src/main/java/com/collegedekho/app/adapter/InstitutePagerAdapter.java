package com.collegedekho.app.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.Placements;
import com.collegedekho.app.fragment.InstituteArticleFragment;
import com.collegedekho.app.fragment.InstituteCoursesFragment;
import com.collegedekho.app.fragment.InstituteInfrastructureFragment;
import com.collegedekho.app.fragment.InstituteNewsFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstitutePlacementFragment;
import com.collegedekho.app.fragment.VideosFragment;

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
    private static final int NEWS_POSITION = 4;
    private static final int ARTICLE_POSITION = 5;
    private static final int VIDEOS_POSITION = 6;

    private Institute mInstitute;
    private Placements p;
    private int count = 7;
    private InstituteCoursesFragment mCourseFragment;
    private InstituteOverviewFragment mOverViewFragment;
    private InstituteNewsFragment mNewsFragment;
    private InstituteArticleFragment mArticleFragment;
    private ArrayList<ArrayList<InstituteCourse>> mCourses;

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
                this.mOverViewFragment = InstituteOverviewFragment.newInstance(this.mInstitute);
                return this.mOverViewFragment;
            case COURSES_POSITION:
                this.mCourseFragment = InstituteCoursesFragment.newInstance(this.mCourses, this.mInstitute);
                return this.mCourseFragment;
            case NEWS_POSITION:
                this.mNewsFragment = InstituteNewsFragment.newInstance(new ArrayList<News>(),"", null);
                return this.mNewsFragment;
            case ARTICLE_POSITION:
                this.mArticleFragment = InstituteArticleFragment.newInstance(new ArrayList<Articles>(),"", null);
                return this.mArticleFragment;
            case PLACEMENT_POSITION:
                InstitutePlacementFragment frag = InstitutePlacementFragment.newInstance(this.p, this.mInstitute);
                return frag;
            case INFRA_POSITION:
                InstituteInfrastructureFragment instituteInfrastructureFragment = InstituteInfrastructureFragment.newInstance(this.mInstitute);
                return instituteInfrastructureFragment;
            case VIDEOS_POSITION:
                VideosFragment videosFragment = VideosFragment.newInstance(this.mInstitute.getVideos());
                return videosFragment;
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
            case NEWS_POSITION:
                return "NEWS";
            case ARTICLE_POSITION:
                return "ARTICLES";
            case VIDEOS_POSITION:
                return "VIDEOS";
        }
        return super.getPageTitle(position);
    }
    public void updateInstitutiesList(Institute institute){
        this.mInstitute = institute;
    }

    public void setCourses(ArrayList<ArrayList<InstituteCourse>> courses) {
        if(courses == null)return;
        int count = 0;
        for (int i = 0; i < courses.size(); i++) {
            ArrayList<InstituteCourse> coursesList =  mCourses.get(i);
            if(coursesList == null)continue;
            coursesList.clear();
            coursesList.addAll(courses.get(i));
            count += courses.get(i).size();
        }
        if (mCourseFragment != null)
            mCourseFragment.updateData(count);
    }

    public void updateShortListButton() {
        if (mOverViewFragment != null)
            mOverViewFragment.updateShortListButton();
    }

    public void updateCourseFragment()
    {
        if(mCourseFragment != null)
            mCourseFragment.updateAdapter();
    }

    public void updateInstituteNews(ArrayList<News> newsList, String next) {
        if(mNewsFragment != null)
            mNewsFragment.updateNewsList(newsList, next);
    }
    public void updateInstituteArticles(ArrayList<Articles> artiles, String next) {
        if(mArticleFragment != null)
            mArticleFragment.updateArticleList(artiles, next);
    }

   public void updateNews(News news) {
        if(mNewsFragment != null)
            mNewsFragment.updateNews(news);
    }

    public void updateArticle(Articles article) {
        if(mArticleFragment != null)
            mArticleFragment.updateArticle(article);
    }
}
