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
import com.collegedekho.app.MySingleton;
import com.collegedekho.app.R;
import com.collegedekho.app.adapter.InstitutePagerAdapter;
import com.collegedekho.app.entities.Institute;
import com.collegedekho.app.entities.InstituteCourse;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InstituteDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InstituteDetailFragment extends Fragment {
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
        mPager = (ViewPager) rootView.findViewById(R.id.college_detail_pager);
        mPagerAdapter = new InstitutePagerAdapter(getChildFragmentManager(), mInstitute);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOffscreenPageLimit(3);
        tabLayout = (TabLayout) rootView.findViewById(R.id.college_tabs_layout);
        tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.text_subhead_blue));
        tabLayout.setupWithViewPager(mPager);
        mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        return rootView;
    }


    public void updateCourses(List<InstituteCourse> response) {
        new RandomTask(response).execute();
    }

    public void updateInstituteShortlist() {
        mPagerAdapter.updateShortListButton();
    }

    class RandomTask extends AsyncTask<Void, Void, Void> {

        List<InstituteCourse> mCourses;
        RandomTask(List<InstituteCourse> response) {
            mCourses = response;
        }


        @Override
        protected Void doInBackground(Void... params) {
            courses = new ArrayList<>();
            for (int i = 0; i < InstituteCourse.CourseLevel.values().length; i++) {
                courses.add(new ArrayList<InstituteCourse>());
            }
            for (InstituteCourse course : mCourses) {
                courses.get(course.level).add(course);
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
}
