package com.collegedekho.app.fragment;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstitutePagerAdapter;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.GameEntity;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.entities.QnAQuestions;
import com.fasterxml.jackson.jr.ob.JSON;

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
    public static final int Videos = 4;
    public static final int QnA = 1;
    public static final int News = 2;
    public static final int Articles = 3;

    private ArrayList<ArrayList<InstituteCourse>> courses;
    ArrayList<News> mInstituteNewsList;
    ArrayList<Articles> mInstituteArticleList;
    private Institute mInstitute;
    private InstitutePagerAdapter mDetailsAdapter;
    private ViewPager mDetailsPager;
    private ArrayList<GameEntity> mData = new ArrayList<>(4);
    private String nextArticleUrl;
    private String nextNewsUrl;


    public InstituteDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param institute Parameter 1.
     * @return A new instance of fragment InstituteOverviewFragment.
     */
    public static InstituteDetailFragment newInstance(Institute institute) {
        InstituteDetailFragment fragment = new InstituteDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_INSTITUTE, institute);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mInstitute = getArguments().getParcelable(ARG_INSTITUTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_detail, container, false);
        if(this.mDetailsAdapter == null) {
            this.mDetailsAdapter = new InstitutePagerAdapter(getChildFragmentManager(), this.mInstitute);
        }
        else{
            this.mDetailsAdapter.updateInstitutiesList(this.mInstitute);
        }
        this.mDetailsPager = (ViewPager) rootView.findViewById(R.id.college_detail_pager);
        this.mDetailsPager.setAdapter(this.mDetailsAdapter);
        //this.mDetailsPager.setPageTransformer(true, new DepthPageTransformer());
        this.mDetailsPager.setOffscreenPageLimit(5);

        //this.mFooterAdapter = new FooterPagerAdapter(this.mInstitute, this.getContext());

        //this.mFooterPager = (ViewPager) rootView.findViewById(R.id.college_extras_pager);
        //this.mFooterPager.setAdapter(this.mFooterAdapter);
        //this.mFooterPager.setPageMargin(this.getResources().getDisplayMetrics().widthPixels /-7);

/*
        this.mData.add(new GameEntity(R.drawable.ic_carousel_video, R.string.videos_title));
        this.mData.add(new GameEntity(R.drawable.ic_carousel_qna, R.string.qna_title));
        this.mData.add(new GameEntity(R.drawable.ic_carousel_news, R.string.news_title));
        this.mData.add(new GameEntity(R.drawable.ic_carousel_articles, R.string.article_title));
*/

        /*this.mData.add(new GameEntity(R.drawable.image_1, R.string.videos_title));
        this.mData.add(new GameEntity(R.drawable.image_2, R.string.qna_title));
        this.mData.add(new GameEntity(R.drawable.image_3, R.string.news_title));
        this.mData.add(new GameEntity(R.drawable.image_4, R.string.article_title));*/

/*
        this.mAdapter = new CoverFlowAdapter(this.getContext());
        this.mAdapter.setData(this.mData);
*/

        //this.tabLayout = (TabLayout) rootView.findViewById(R.id.college_tabs_layout);
        //this.tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.light_grey_background));
        //this.tabLayout.setupWithViewPager(this.mDetailsPager);

        //this.mDetailsPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));
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

        return rootView;
    }

    public void updateCourses(String response ) { new LoadCoursesAsyncTask().execute(response);  }

    public void updateAppliedCourses(String response) {
      if(response != null) {
           try {
               JSONObject responseObj = new JSONObject(response);
               String instituteId = responseObj.getString(MainActivity.getResourceString(R.string.APPLY_COURSE));
               for (int i = 0; i < courses.size() ; i++) {
                   ArrayList<InstituteCourse> instituteCourse = courses.get(i);
                   int count = instituteCourse.size();
                   for (int j = 0; j < count; j++) {
                       long id = instituteCourse.get(j).getId();
                       long tempId = Long.parseLong(instituteId);

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

    public void instituteQnAQuestionAdded(QnAQuestions ques)
    {
       // mDetailsAdapter.questionAdded(ques);
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

                if (ma != null)
                {
                    mCourses = JSON.std.listOfFrom(InstituteCourse.class, ma.extractResults(response));

                    courses = new ArrayList<>();
                    for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++)
                    {
                        courses.add(new ArrayList<InstituteCourse>());
                    }
                    if(mCourses != null && !mCourses.isEmpty()) {
                        for (InstituteCourse course : mCourses) {
                            courses.get(course.level).add(course);
                        }
                    }
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mDetailsAdapter.setCourses(courses);
            if (getView() != null) {
                //tabLayout.setupWithViewPager(mDetailsPager);
            }
        }
    }

    /**
     * Get the current view position from the ViewPager by
     * extending SimpleOnPageChangeListener class and adding your method
     */
    public class FooterPagerAdapter extends PagerAdapter implements View.OnClickListener{

        private Institute mInstitute;
        private Context mContext;
        private InstituteDetailFragment.OnInstituteFooterItemSelected mListener;

        public FooterPagerAdapter(Institute institute, Context context) {
            this.mInstitute = institute;
            this.mContext = context;
            this.mListener = (InstituteDetailFragment.OnInstituteFooterItemSelected) context;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            CardView cardView = (CardView) LayoutInflater.from(this.mContext).inflate(R.layout.card_blue_item, null, false);
            cardView.setOnClickListener(this);

            TextView component = (TextView) cardView.findViewById(R.id.blue_item_text);

            switch (position)
            {
                case InstituteDetailFragment.Videos:
                {
                    component.setText("Videos");
                    component.setTag(this.mInstitute.getVideos());
                    cardView.setTag(InstituteDetailFragment.Videos);
                    break;
                }
                case InstituteDetailFragment.QnA:
                {
                    component.setText("QnA");
                    component.setTag(this.mInstitute.getId());
                    cardView.setTag(InstituteDetailFragment.QnA);
                    break;
                }
                case InstituteDetailFragment.News:
                {
                    component.setText("News");
                    component.setTag(this.mInstitute.getId());
                    cardView.setTag(InstituteDetailFragment.News);
                    break;
                }
                case InstituteDetailFragment.Articles:
                {
                    component.setText("Articles");
                    component.setTag(this.mInstitute.getId());
                    cardView.setTag(InstituteDetailFragment.Articles);
                    break;
                }
                default:
                    break;
            }

            container.addView(cardView);

            return cardView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((CardView) object);
        }

        @Override
        public void onClick(View v) {
            TextView component = (TextView) v.findViewById(R.id.blue_item_text);

            switch ((int) v.getTag())
            {
                case InstituteDetailFragment.Videos:
                {
                    this.mListener.onFooterVideosSelected((ArrayList<String>) component.getTag());
                    break;
                }
                default:
                {
                    this.mListener.OnFooterOtherItemsSelected((int) v.getTag(), (int) component.getTag());
                    break;
                }
            }
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

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }

    public interface OnInstituteFooterItemSelected{
        void onFooterVideosSelected(ArrayList<String> videos);

        void OnFooterOtherItemsSelected(int type, int instituteID);
    }
}
