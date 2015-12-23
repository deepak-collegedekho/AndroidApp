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
import com.collegedekho.app.adapter.CoverFlowAdapter;
import com.collegedekho.app.entities.Articles;
import com.collegedekho.app.entities.GameEntity;
import com.collegedekho.app.entities.News;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstitutePagerAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.QnAQuestions;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

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

    ArrayList<ArrayList<InstituteCourse>> courses;
    ArrayList<News> mInstituteNewsList;
    ArrayList<Articles> mInstituteArticleList;
    private Institute mInstitute;
    private InstitutePagerAdapter mDetailsAdapter;
    private FooterPagerAdapter mFo54oterAdapter;
    private ViewPager mDetailsPager;
    private ViewPager mFooterPager;
    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private ArrayList<GameEntity> mData = new ArrayList<>(4);
    private InstituteDetailFragment.OnInstituteFooterItemSelected mListener;
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
            this.mListener = (InstituteDetailFragment.OnInstituteFooterItemSelected) this.getActivity();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_detail, container, false);
        /*if (mInstitute.getBanner() != null) {
            ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            NetworkImageView imageView = ((NetworkImageView) rootView.findViewById(R.id.image_college_banner));
            imageView.setErrorImageResId(R.drawable.default_banner);
            imageView.setDefaultImageResId(R.drawable.default_banner);
            imageView.setImageUrl(this.mInstitute.getBanner(), imageLoader);
        } else {
            ((NetworkImageView) rootView.findViewById(R.id.image_college_banner)).setDefaultImageResId(R.drawable.default_banner);
        }*/

        this.mDetailsAdapter = new InstitutePagerAdapter(getChildFragmentManager(), this.mInstitute);

        this.mDetailsPager = (ViewPager) rootView.findViewById(R.id.college_detail_pager);
        this.mDetailsPager.setAdapter(this.mDetailsAdapter);
        this.mDetailsPager.setOffscreenPageLimit(3);

        //this.mFooterAdapter = new FooterPagerAdapter(this.mInstitute, this.getContext());

        //this.mFooterPager = (ViewPager) rootView.findViewById(R.id.college_extras_pager);
        //this.mFooterPager.setAdapter(this.mFooterAdapter);
        //this.mFooterPager.setPageMargin(this.getResources().getDisplayMetrics().widthPixels /-7);

        this.mData.add(new GameEntity(R.drawable.ic_carousel_video, R.string.videos_title));
        this.mData.add(new GameEntity(R.drawable.ic_carousel_qna, R.string.qna_title));
        this.mData.add(new GameEntity(R.drawable.ic_carousel_news, R.string.news_title));
        this.mData.add(new GameEntity(R.drawable.ic_carousel_articles, R.string.article_title));

        /*this.mData.add(new GameEntity(R.drawable.image_1, R.string.videos_title));
        this.mData.add(new GameEntity(R.drawable.image_2, R.string.qna_title));
        this.mData.add(new GameEntity(R.drawable.image_3, R.string.news_title));
        this.mData.add(new GameEntity(R.drawable.image_4, R.string.article_title));*/

        this.mAdapter = new CoverFlowAdapter(this.getContext());
        this.mAdapter.setData(this.mData);
/*
        this.mCoverFlow = (FeatureCoverFlow) rootView.findViewById(R.id.coverflow);
        this.mCoverFlow.setAdapter(this.mAdapter);

        this.mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //int itemPosition = (int) view.getTag(R.integer.carousal_item_position);
                switch (position)
                {
                    case InstituteDetailFragment.Videos:
                    {
                        InstituteDetailFragment.this.mListener.onFooterVideosSelected((InstituteDetailFragment.this.mInstitute.getVideos()));
                        break;
                    }
                    default:
                    {
                        InstituteDetailFragment.this.mListener.OnFooterOtherItemsSelected(position, InstituteDetailFragment.this.mInstitute.getId());
                        break;
                    }
                }
            }
        });

        this.mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
            }

            @Override
            public void onScrolling() {
            }
        });
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
               String instituteId = responseObj.getString(Constants.APPLY_COURSE);
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

       this.mDetailsAdapter.updateCourseFragment();
    }

    public void instituteQnAQuestionAdded(QnAQuestions ques)
    {
        mDetailsAdapter.questionAdded(ques);
    }


    public void cancleAppliedRequest(int position, int tabPosition)
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
        String mResponse;
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
            mDetailsAdapter.setQnAQuestions(mQnAQuestions);
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
