package com.collegedekho.app.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstitutePagerAdapter;
import com.collegedekho.app.display.CustomViewPager;
import com.collegedekho.app.display.DepthPageTransformer;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.fasterxml.jackson.jr.ob.JSON;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteDetailFragment extends BaseFragment {
    private static final String ARG_INSTITUTE = "param1";
    private static final String ARG_FROM_WISHLIST = "param2";
    public static final int Videos = 4;
    public static final int QnA = 1;
    public static final int News = 2;
    public static final int Articles = 3;

    private ArrayList<ArrayList<InstituteCourse>> courses;
    private ArrayList<News> mInstituteNewsList;
    private ArrayList<Articles> mInstituteArticleList;
    private Institute mInstitute;
    private InstitutePagerAdapter mDetailsAdapter;
    private CustomViewPager mDetailsPager;
    private String nextArticleUrl;
    private String nextNewsUrl;
    private OnInstituteDetailListener mListener;
    private FloatingActionMenu mFloatingMenu;
    private Constants.CDRecommendedInstituteType mInstituteType;
    private FloatingActionButton mFabApply;
    private int fabMargin;

    public InstituteDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param institute Parameter 1.
     * @param institutesType
     * @return A new instance of fragment InstituteOverviewFragment.
     */
    public static InstituteDetailFragment newInstance(Institute institute, Constants.CDRecommendedInstituteType institutesType) {
        InstituteDetailFragment fragment = new InstituteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INSTITUTE, institute);
        args.putSerializable(ARG_FROM_WISHLIST, institutesType);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mInstitute = getArguments().getParcelable(ARG_INSTITUTE);
            this.mInstituteType = (Constants.CDRecommendedInstituteType) getArguments().getSerializable(ARG_FROM_WISHLIST);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_detail, container, false);


        if (this.mInstituteType == Constants.CDRecommendedInstituteType.SHORTLIST)
        {
            Animation animation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.simple_grow);

            //  FAB margin needed for animation
            this.fabMargin = getResources().getDimensionPixelSize(R.dimen.fab_margin);

            this.mFloatingMenu = (FloatingActionMenu) rootView.findViewById(R.id.fab_menu);

            this.mFloatingMenu.startAnimation(animation);

            this.mFloatingMenu.setVisibility(View.VISIBLE);

            FloatingActionButton fabRemove = (FloatingActionButton) rootView.findViewById(R.id.remove_button);
            this.mFabApply = (FloatingActionButton) rootView.findViewById(R.id.apply_button);
            FloatingActionButton fabCall = (FloatingActionButton) rootView.findViewById(R.id.call_button);

            if (this.mInstitute.getGroups_exists() == 1)
            {
                //Show Call button number exists
                if (this.mInstitute.getL3_number() != null && !this.mInstitute.getL3_number().isEmpty() && this.mInstitute.getL3_number() != " ")
                    fabCall.setVisibility(View.VISIBLE);
                else
                    fabCall.setVisibility(View.GONE);

                //Show Apply Now if Partner and not yet applied
                if (!this.mInstitute.is_applied())
                    this.mFabApply.setVisibility(View.VISIBLE);
                else
                    this.mFabApply.setVisibility(View.GONE);
            }

            fabRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InstituteDetailFragment.this.mListener.OnInstituteRemovedFromDetail(InstituteDetailFragment.this.mInstitute);
                }
            });

            fabCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Call intent
                    Uri number = Uri.parse("tel:" + InstituteDetailFragment.this.mInstitute.getL3_number());
                    Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                    InstituteDetailFragment.this.getActivity().startActivity(callIntent);
                }
            });

            this.mFabApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InstituteDetailFragment.this.mListener.OnInstituteAppliedFromDetail(InstituteDetailFragment.this.mInstitute);
                }
            });
        }

        if(this.mDetailsAdapter == null) {
            this.mDetailsAdapter = new InstitutePagerAdapter(getChildFragmentManager(), this.mInstitute);
        }else{
            this.mDetailsAdapter.updateInstitutiesList(this.mInstitute);
        }

        requestForCourseUpdate();

        this.mDetailsPager = (CustomViewPager) rootView.findViewById(R.id.college_detail_pager);
        this.mDetailsPager.setAdapter(this.mDetailsAdapter);
        this.mDetailsPager.setPageTransformer(true, new DepthPageTransformer());

        this.mDetailsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1)
                    mDetailsAdapter.setCourses(courses);
                else if(position == 4)
                   mDetailsAdapter.updateInstituteNews(mInstituteNewsList, nextNewsUrl);
                else if(position == 5)
                    mDetailsAdapter.updateInstituteArticles(mInstituteArticleList, nextArticleUrl);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ((ViewPager.LayoutParams) ((PagerTabStrip) rootView.findViewById(R.id.pager_header)).getLayoutParams()).isDecor = true;

        return rootView;
    }

    public void OnInstituteRemoved()
    {
        this.getActivity().onBackPressed();
    }

    public void OnInstituteApplied()
    {
        this.mFabApply.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();

        Uri val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_INSTITUTE_LIST  + "/institutes/" + this.mInstitute.getId());

        AnalyticsUtils.AppIndexingView("CollegeDekho - Colleges - " + this.mInstitute.getUri_slug(), val, val, (MainActivity) this.getActivity(), true);
    }

    @Override
    public void onStop() {
        super.onStop();

        Uri val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_INSTITUTE_LIST  + "/institutes/" + this.mInstitute.getId());

        AnalyticsUtils.AppIndexingView("CollegeDekho - Colleges - " + this.mInstitute.getUri_slug(), val, val, (MainActivity) this.getActivity(), false);
    }

    @Override
    public void show() {
        if (this.mFloatingMenu != null)
            this.mFloatingMenu.animate().translationY(0).setInterpolator(new DecelerateInterpolator(3)).start();
    }

    @Override
    public void hide() {
        if (this.mFloatingMenu != null)
            this.mFloatingMenu.animate().translationY(this.mFloatingMenu.getHeight() + this.fabMargin).setInterpolator(new AccelerateInterpolator(3)).start();
    }

    public void updateCourses(String response ) { new LoadCoursesAsyncTask().execute(response);  }

    public void updateAppliedCourses(String response) {
      if(response != null) {
           try {
               JSONObject responseObj = new JSONObject(response);
               String instituteCourseId = responseObj.getString(MainActivity.getResourceString(R.string.APPLY_COURSE));
               for (int i = 0; i < courses.size() ; i++) {
                   ArrayList<InstituteCourse> instituteCourse = courses.get(i);
                   int count = instituteCourse.size();
                   for (int j = 0; j < count; j++) {
                       long id = instituteCourse.get(j).getId();
                       long tempId = Long.parseLong(instituteCourseId);

                       if(id ==  tempId)
                       {
                           instituteCourse.get(j).setIs_applied(1);
                           break;
                       }
                   }
               }
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }

       mDetailsAdapter.updateCourseFragment();
    }

    public void cancleAppliedRequest()
    {
        this.mDetailsAdapter.updateCourseFragment();
    }

    public void updateInstituteQnAQuestions(String response)
    {
        new LoadQnAQuestionAsyncTask().execute(response);
    }

    public void updateInstituteShortlist()
    {
        mDetailsAdapter.updateShortListButton();
    }

    public void updateInstituteNews(ArrayList<News> newsList, String next) {
        this.mInstituteNewsList = newsList;
        this.nextNewsUrl = next;
        mDetailsAdapter.updateInstituteNews( this.mInstituteNewsList, next);
    }

     public void updateNews(News news) {
        if(mDetailsAdapter != null)
            mDetailsAdapter.updateNews(news);
    }

    public void updateArticle(Articles article) {
        if(mDetailsAdapter != null)
            mDetailsAdapter.updateArticle(article);
    }

    public void updateInstituteArticle(ArrayList<Articles> articleList, String next) {
        this.mInstituteArticleList = articleList;
        this.nextArticleUrl = next;
        mDetailsAdapter.updateInstituteArticles( this.mInstituteArticleList, next);
    }

    private class LoadCoursesAsyncTask extends AsyncTask<String, Void, Void> {
        List<InstituteCourse> mCourses;
        @Override
        protected Void doInBackground(String... str) {
            String response = str[0];
            try
            {
                MainActivity ma = (MainActivity) getActivity();

                if (ma != null) {
                    mCourses = JSON.std.listOfFrom(InstituteCourse.class, ma.extractResults(response));

                    courses = new ArrayList<>();
                    for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
                        courses.add(new ArrayList<InstituteCourse>());
                    }
                    if (mCourses != null && !mCourses.isEmpty()) {
                        for (InstituteCourse course : mCourses) {
                            courses.get(course.level).add(course);
                        }
                    }
                    handleCourseUpdateResponse();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mDetailsAdapter.setCourses(courses);
        }
    }

    private void handleCourseUpdateResponse ()
    {
        int position = mDetailsPager.getCurrentItem();
        if (position == 1) {
            mDetailsAdapter.setCourses(courses);
        }
    }


    public class LoadQnAQuestionAsyncTask extends AsyncTask<String, Void, Void> {

        ArrayList<QnAQuestions> mQnAQuestions;
        @Override
        protected Void doInBackground(String... str)
        {
            MainActivity ma = (MainActivity) getActivity();
            final String response = str[0];

            if (ma != null)
                mQnAQuestions = ma.parseAndReturnQnAList(response);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
          //  mDetailsAdapter.setQnAQuestions(mQnAQuestions);
            if (getView() != null) {
                //tabLayout.setupWithViewPager(mDetailsPager);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity mMainActivity = (MainActivity) this.getActivity();
        if (mMainActivity != null) {
            mMainActivity.currentFragment = this;
            if (getArguments() != null) {
                Institute institute=getArguments().getParcelable(ARG_INSTITUTE);
                if(institute!=null) {
                    mMainActivity.setCurrentInstitute(institute);
                }
            }
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof  MainActivity)
                mListener = (OnInstituteDetailListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnInstituteSelectedListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;

    }
    public  interface  OnInstituteDetailListener{
        void requestForCoursesUpdate();
        void OnInstituteAppliedFromDetail(Institute institute);
        void OnInstituteRemovedFromDetail(Institute institute);
    }

    private void requestForCourseUpdate(){
        if(mListener != null)
            mListener.requestForCoursesUpdate();

    }
}
