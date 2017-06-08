package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.pager.InstitutePagerAdapter;
import com.collegedekho.app.display.CustomViewPager;
import com.collegedekho.app.display.DepthPageTransformer;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.MyFutureBuddy;
import com.collegedekho.app.entities.MyFutureBuddyComment;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.network.ApiEndPonits;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.widget.fab.FloatingActionButton;
import com.collegedekho.app.widget.fab.FloatingActionMenu;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.collegedekho.app.activity.MainActivity.mProfile;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteDetailFragment extends BaseFragment {
    private static final String ARG_INSTITUTE = "param1";
    private static final String ARG_FROM_WISHLIST = "param2";
    //public static final int QnA = 1;
    public static final int News = 2;
    public static final int Articles = 3;
    //public static final int Videos = 4;

    private ArrayList<ArrayList<InstituteCourse>> courses;
    private ArrayList<News> mInstituteNewsList;
    private ArrayList<Articles> mInstituteArticleList;
    private ArrayList<QnAQuestions> mInstituteQnaList;
    private Institute mInstitute;
    private InstitutePagerAdapter mDetailsAdapter;
    private CustomViewPager mDetailsPager;
    private String nextArticleUrl;
    private String nextNewsUrl;
    private String nextQnaUrl;
    private OnInstituteDetailListener mListener;
    private FloatingActionMenu mFloatingMenu;
    private Constants.CDRecommendedInstituteType mInstituteType;
    private FloatingActionButton mFabApply;
    private int fabMargin;
    private MyFutureBuddy mFBuddy;

    public InstituteDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @param institute Parameter 1.
     * @param institutesType type of institute
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
     return inflater.inflate(R.layout.fragment_institute_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateInstituteDetail(view);
    }

    public boolean updateFabCounselorButton(){
        if (this.mInstituteType == Constants.CDRecommendedInstituteType.SHORTLIST)
        {
            return  true;
        }
        return false;
    }
    private void updateInstituteDetail(View rootView){
        if(rootView == null) return;
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
                if (this.mInstitute.getL3_number() != null && !this.mInstitute.getL3_number().isEmpty())
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
                    String contactNumber =  InstituteDetailFragment.this.mInstitute.getL3_number();
                    if(MainActivity.mProfile != null) {
                        String contact = mProfile.getCounselor_contact_no();
                        if (contact != null && !contact.isEmpty()) {
                          contactNumber = contact;
                        }
                    }
                    Uri number = Uri.parse("tel:" +contactNumber);
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

        MyFutureBuddy mFutureBuddy = new MyFutureBuddy();
        mFutureBuddy.setResource_uri("https://www.collegedekho.com/api/1/personalize/forums/"+this.mInstitute.getForum_id()+"/");
        mFutureBuddy.setInstitute_name(this.mInstitute.getName());
        mFutureBuddy.setInstitute_logo(this.mInstitute.getLogo());
        mFutureBuddy.setInstitute_id(this.mInstitute.getId());
        mFutureBuddy.setState_name(this.mInstitute.getState_name());
        mFutureBuddy.setCity_name(this.mInstitute.getCity_name());
        mFutureBuddy.setFutureBuddiesCommentsSet(null);
        mFutureBuddy.setComments_count(0);
        mFutureBuddy.setShortListed(this.mInstitute.getIs_shortlisted() == 1 ? true : false);

        mInstitute.setInstituteMyFutureBuddy(mFutureBuddy);

        if(this.mDetailsAdapter == null) {
            this.mDetailsAdapter = new InstitutePagerAdapter(getChildFragmentManager(), this.mInstitute);
        }else{
            this.mDetailsAdapter.updateInstitutiesList(this.mInstitute);
        }

        requestForCourseUpdate();

        this.mDetailsPager = (CustomViewPager) rootView.findViewById(R.id.college_detail_pager);
        this.mDetailsPager.setAdapter(this.mDetailsAdapter);
        this.mDetailsPager.setPageTransformer(true, new DepthPageTransformer());
        this.mDetailsPager.setOffscreenPageLimit(4);
        this.mDetailsPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(getActivity() != null){
                    ((MainActivity)getActivity()).mSetCounselorMenuVisibility();
                }
                if(position == mDetailsAdapter.positionCourse)
                    mDetailsAdapter.setCourses(courses);
                else if(position == mDetailsAdapter.positionNews)
                {
                    mDetailsAdapter.updateInstituteNews(mInstituteNewsList, nextNewsUrl);
                }
                else if(position == mDetailsAdapter.positionArticle)
                    mDetailsAdapter.updateInstituteArticles(mInstituteArticleList, nextArticleUrl);
                else if(position == mDetailsAdapter.positionMyFb && false)
                    mDetailsAdapter.updateInstituteMyFB(mFBuddy);
                else if(position == mDetailsAdapter.positionQna)
                    mDetailsAdapter.updateQnas(mInstituteQnaList,nextQnaUrl);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        ((ViewPager.LayoutParams) (rootView.findViewById(R.id.pager_header)).getLayoutParams()).isDecor = true;

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

        //android-app://com.collegedekho.app/https/www.collegedekho.com/fragment_institute_list/institutes/3633/
        Uri app_uri_val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_INSTITUTE_LIST  + "/institutes/" + this.mInstitute.getId());
        Uri web_uri_val = Uri.parse(ApiEndPonits.IP + "/colleges/" + this.mInstitute.getUri_slug());

        AnalyticsUtils.AppIndexingView("CollegeDekho - Colleges - " + this.mInstitute.getUri_slug(), web_uri_val, app_uri_val, (MainActivity) this.getActivity(), true);

//        if(getActivity() != null && getActivity() instanceof MainActivity) {
//            MainActivity mainActivity = (MainActivity) getActivity();
//            mainActivity.mIndexingUrl = "/colleges/" + mInstitute.getUri_slug();
//            mainActivity.mIndexingTitle = mInstitute.getName();
//            mainActivity.mIndexingDescription = mInstitute.getName() + " " + mInstitute.getAcronym() + " " + mInstitute.getShort_name();
//            mainActivity.appIndexingTask();
//        }
    }

    @Override
    public void onStop() {
        super.onStop();

        Uri app_uri_val = Uri.parse(Constants.BASE_APP_URI.toString() + Constants.TAG_FRAGMENT_INSTITUTE_LIST  + "/institutes/" + this.mInstitute.getId());
        Uri web_uri_val = Uri.parse(ApiEndPonits.IP + "/colleges/" + this.mInstitute.getUri_slug());

        AnalyticsUtils.AppIndexingView("CollegeDekho - Colleges - " + this.mInstitute.getUri_slug(), web_uri_val, app_uri_val, (MainActivity) this.getActivity(), false);
    }

    @Override
    public String getEntity() {
        return null;
    }

    public void updateCourses(String response ) { new LoadCoursesAsyncTask().execute(response);  }

    public void updateAppliedCourses(String response) {
      if(response != null) {
           try {
               JSONObject responseObj = new JSONObject(response);
               String instituteCourseId = responseObj.getString(getString(R.string.APPLY_COURSE));
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

    public void updateQnasList(ArrayList<QnAQuestions> qnaQuestionList, String next)
    {
        mInstitute.setInstituteQuestions(qnaQuestionList);
        this.mInstituteQnaList = qnaQuestionList;
        this.nextQnaUrl = next;
        mDetailsAdapter.updateQnas(this.mInstituteQnaList,this.nextQnaUrl);
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

    public void updateInstitutedFromNotification(Institute institute, Constants.CDRecommendedInstituteType instituteType){
        this.mInstitute = institute;
        this.mInstituteType = instituteType;
        updateInstituteDetail(getView());
    }

    public void updateInstituteMyBuddy(MyFutureBuddy mFBuddy)
    {
        this.mFBuddy = mFBuddy;
        mDetailsAdapter.updateInstituteMyFB(this.mFBuddy);
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
            //if (getView() != null) {
                //tabLayout.setupWithViewPager(mDetailsPager);
            //
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
