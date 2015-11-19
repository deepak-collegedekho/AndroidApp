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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteDetailFragment extends BaseFragment {
    private static final String ARG_INSTITUTE = "param1";
    public static final int Videos = 0;
    public static final int QnA = 1;
    public static final int News = 2;
    public static final int Articles = 3;

    ArrayList<ArrayList<InstituteCourse>> courses;
    private Institute mInstitute;
    private InstitutePagerAdapter mDetailsAdapter;
    private FooterPagerAdapter mFooterAdapter;
    //private TabLayout tabLayout;
    private ViewPager mDetailsPager;
    private ViewPager mFooterPager;

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
        View rootView = inflater.inflate(R.layout.fragment_institute_detail_new, container, false);
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

        this.mFooterAdapter = new FooterPagerAdapter(this.mInstitute, this.getContext());

        this.mFooterPager = (ViewPager) rootView.findViewById(R.id.college_extras_pager);
        this.mFooterPager.setAdapter(this.mFooterAdapter);
        this.mFooterPager.setPageMargin(this.getResources().getDisplayMetrics().widthPixels /-7);

        //this.tabLayout = (TabLayout) rootView.findViewById(R.id.college_tabs_layout);
        //this.tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.light_grey_background));
        //this.tabLayout.setupWithViewPager(this.mDetailsPager);

        //this.mDetailsPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(this.tabLayout));

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

    public void updateInstituteQnAQuestions(String response)
    {
        new LoadQnAQuestionAsyncTask().execute(response);
    }

    public void updateInstituteShortlist()
    {
        mDetailsAdapter.updateShortListButton();
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
