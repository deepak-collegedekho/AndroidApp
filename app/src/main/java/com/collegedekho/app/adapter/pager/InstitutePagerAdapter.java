package com.collegedekho.app.adapter.pager;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Facility;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.MyFutureBuddy;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.Placements;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.fragment.InstituteArticleFragment;
import com.collegedekho.app.fragment.InstituteCoursesFragment;
import com.collegedekho.app.fragment.InstituteInfrastructureFragment;
import com.collegedekho.app.fragment.InstituteNewsFragment;
import com.collegedekho.app.fragment.InstituteOverviewFragment;
import com.collegedekho.app.fragment.InstitutePlacementFragment;
import com.collegedekho.app.fragment.InstituteVideosFragment;
import com.collegedekho.app.fragment.MyFutureBuddiesFragment;
import com.collegedekho.app.fragment.QnAQuestionsListFragment;
import com.collegedekho.app.resource.Constants;
import com.crashlytics.android.Crashlytics;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mayank Gautam
 *         Created: 09/07/15
 */
public class InstitutePagerAdapter extends FragmentStatePagerAdapter {
    private Institute mInstitute;
    private Placements p;
    private int count = 9;
    private boolean showPlacementFrag = true;
    private boolean showInfraFrag = true;
    private boolean showOverViewFrag = true;
    private boolean showCoursesFrag = true;
    private boolean showVideosFrag = true;
    private boolean showArticlesFrag = true;
    private boolean showNewsFrag = true;
    private boolean showQnaFrag = true;
    private boolean showMyFbFrag = true;
    private int positionOverview = 0;
    public int positionCourse = 0;
    private int positionPlacement = 0;
    private int positionInfra = 0;
    public int positionNews = 0;
    public int positionArticle = 0;
    private int positionVideo = 0;
    public int positionQna = 0;
    public int positionMyFb = 0;
    private InstituteCoursesFragment mCourseFragment;
    private InstituteOverviewFragment mOverViewFragment;
    private InstituteNewsFragment mNewsFragment;
    private InstituteArticleFragment mArticleFragment;
    private MyFutureBuddiesFragment mFbFragment;
    private QnAQuestionsListFragment mQnaFragment;
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
        String placement = this.mInstitute.getPlacement();
        if(placement == null || placement.length()==0)
        {
            count-=1;
            showPlacementFrag = false;
        }
        ArrayList<Facility> facility = this.mInstitute.getFacilities();

        if(facility == null || facility.size()<1)
        {
            count-=1;
            showInfraFrag = false;
        }
    }

    @Override
    public Fragment getItem(int position) {
        if(position<count)
        {
           if(showOverViewFrag || positionOverview == position)
           {
               this.mOverViewFragment = InstituteOverviewFragment.newInstance(this.mInstitute);
               showOverViewFrag = false;
               positionOverview = position;
               return this.mOverViewFragment;
           }
           else if (showCoursesFrag || position == positionCourse)
           {
               this.mCourseFragment = InstituteCoursesFragment.newInstance(this.mCourses, this.mInstitute);
               showCoursesFrag = false;
               positionCourse = position;
               return this.mCourseFragment;
           }
           else if (showPlacementFrag || position == positionPlacement)
           {
               InstitutePlacementFragment frag = InstitutePlacementFragment.newInstance(this.p, this.mInstitute);
               showPlacementFrag = false;
               positionPlacement = position;
               return frag;
           }
           else if (showInfraFrag || position == positionInfra)
           {
               InstituteInfrastructureFragment frag = InstituteInfrastructureFragment.newInstance(this.mInstitute);
               showInfraFrag = false;
               positionInfra = position;
               return frag;
           }
           else if (showQnaFrag || position == positionQna)
           {
               this.mQnaFragment = QnAQuestionsListFragment.newInstance(mInstitute.getInstituteQuestions(),null, Constants.INSTITUTE_QNA_LIST_TYPE);
               showQnaFrag = false;
               positionQna = position;
               return this.mQnaFragment;
           }
           else if (showNewsFrag || position == positionNews)
           {
               this.mNewsFragment = InstituteNewsFragment.newInstance(new ArrayList<News>(),"", null);
               showNewsFrag = false;
               positionNews = position;
               return this.mNewsFragment;
           }
           else if (showArticlesFrag || position == positionArticle)
           {
               this.mArticleFragment = InstituteArticleFragment.newInstance(new ArrayList<Articles>(),"", null);
               showArticlesFrag = false;
               positionArticle = position;
               return this.mArticleFragment;
           }
           else if (showVideosFrag || position == positionVideo)
           {
               InstituteVideosFragment videosFragment = InstituteVideosFragment.newInstance(this.mInstitute.getVideos());
               showVideosFrag = false;
               positionVideo = position;
               return videosFragment;
           }
           else if(showMyFbFrag || position == positionMyFb)
           {
               this.mFbFragment = MyFutureBuddiesFragment.newInstance(mInstitute.getInstituteMyFutureBuddy(),0,false);
               showMyFbFrag = false;
               positionMyFb = position;
               return  mFbFragment;
           }
        }
        return null;
    }
    @Override
    public Parcelable saveState() {
        Bundle bundle = (Bundle) super.saveState();
        bundle.putParcelableArray("institute", null); // Never maintain any states from the base class, just null it out
        return bundle;
    }

        @Override
    public int getCount() {
        return count;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(positionOverview == position)
        {
            return "OVERVIEW";
        }
        else if(positionCourse == position)
        {
            return "COURSES";
        }
        else if(positionPlacement == position)
        {
            return "PLACEMENTS";
        }
        else if(positionVideo == position)
        {
            return "VIDEOS";
        }
        else if(positionArticle == position)
        {
            return "ARTICLES";
        }
        else if(positionInfra == position)
        {
            return "INFRASTRUCTURE";
        }
        else if(positionNews == position)
        {
            return "NEWS";
        }
        else if(positionQna == position)
        {
            return "Questions";
        }
        else if(positionMyFb == position)
        {
            return "My Future Buddies";
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
    public void updateInstituteMyFB(MyFutureBuddy mBuddy)
    {
        if(mFbFragment != null)
            mFbFragment.updateMyFBFromNotification(mBuddy);
    }

   public void updateNews(News news) {
        if(mNewsFragment != null)
            mNewsFragment.updateNews(news);
    }

    public void updateArticle(Articles article) {
        if(mArticleFragment != null)
            mArticleFragment.updateArticle(article);
    }

    public void updateQnas(ArrayList<QnAQuestions> qnasList, String next){
        if(mQnaFragment !=null)
            mQnaFragment.updateList(qnasList, next);
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
