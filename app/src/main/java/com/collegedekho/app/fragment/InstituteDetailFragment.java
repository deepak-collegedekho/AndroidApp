package com.collegedekho.app.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.InstitutePagerAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.QnAQuestions;
import com.collegedekho.app.resource.Constants;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteDetailFragment extends BaseFragment {
    private static final String ARG_INSTITUTE = "param1";
    ArrayList<ArrayList<InstituteCourse>> courses;
    private Institute mInstitute;
    private InstitutePagerAdapter mPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mPager;

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
            mInstitute = getArguments().getParcelable(ARG_INSTITUTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_institute_detail, container, false);
        if (mInstitute.getBanner() != null) {
            ImageLoader imageLoader = MySingleton.getInstance(getActivity()).getImageLoader();
            NetworkImageView imageView = ((NetworkImageView) rootView.findViewById(R.id.image_college_banner));
            imageView.setErrorImageResId(R.drawable.default_banner);
            imageView.setDefaultImageResId(R.drawable.default_banner);
            imageView.setImageUrl(mInstitute.getBanner(), imageLoader);
        } else {
            ((NetworkImageView) rootView.findViewById(R.id.image_college_banner)).setDefaultImageResId(R.drawable.default_banner);
        }

        mPagerAdapter = new InstitutePagerAdapter(getChildFragmentManager(), mInstitute);

        mPager = (ViewPager) rootView.findViewById(R.id.college_detail_pager);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(3);

        tabLayout = (TabLayout) rootView.findViewById(R.id.college_tabs_layout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.text_subhead_blue));
        tabLayout.setupWithViewPager(mPager);

        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

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

               mPagerAdapter.updateCourseFragment();
    }

    public void instituteQnAQuestionAdded(QnAQuestions ques)
    {
        mPagerAdapter.questionAdded(ques);

    }

    public void updateInstituteQnAQuestions(String response)
    {
        new LoadQnAQuestionAsyncTask().execute(response);
    }

    public void updateInstituteShortlist()
    {
        mPagerAdapter.updateShortListButton();
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
                    mCourses = JSON.std.listOfFrom(InstituteCourse.class, ma.extractResults(response));
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mPagerAdapter.setCourses(courses);
            if (getView() != null) {
                tabLayout.setupWithViewPager(mPager);
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
            mPagerAdapter.setQnAQuestions(mQnAQuestions);
            if (getView() != null) {
                tabLayout.setupWithViewPager(mPager);
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
}
