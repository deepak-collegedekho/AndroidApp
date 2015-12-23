package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 27/11/15.
 */
public class ProfileFragment extends  BaseFragment
        {

    private final String TAG = "profile Frgament";
    private static String PARAM1 = "param1";

    private OnTabSelectListener mListener;
    private ArrayList<ExamDetail> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ExamDetail mExamDetail; // detail is needs in tabs to get id of exams
    private ExamSummary mExamSummary;  // exam summary gives info about the colleges of user
    private ViewPager mExamTabPager  = null;
    private boolean isFistTime = false;



    public static ProfileFragment newInstance(ArrayList<ExamDetail> examList) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1, examList);
        fragment.setArguments(args);
        return fragment;
    }

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.mExamDetailList = args.getParcelableArrayList(PARAM1);
        }
        this.isFistTime = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView mProfileName    =   (TextView)rootView.findViewById(R.id.user_name);
        mExamTabPager = (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        CircularImageView mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        if(MainActivity.user != null)
        {
            String name = MainActivity.user.getName();
            if(name.contains("Anonymous User"))
            {
                if(MainActivity.user.profileData[0] != null)
                {
                    mProfileName.setText(MainActivity.user.profileData[0]);
                    mProfileName.setVisibility(View.VISIBLE);
                }else {
                    mProfileName.setText("");
                    mProfileName.setVisibility(View.GONE);
                }
            }else {
                mProfileName.setText(name);
                mProfileName.setVisibility(View.VISIBLE);
            }
            String image = MainActivity.user.getImage();
            if (image != null && ! image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());

        }

        if(this.mExamDetailList != null && this.mExamDetailList.size() > 0) {

            rootView.findViewById(R.id.profile_syllabus_statusLL).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.important_date_layout_RL).setVisibility(View.VISIBLE);
            mExamTabPager.setVisibility(View.VISIBLE);
            this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
            mExamTabPager.setAdapter(this.mDetailsAdapter);

            mExamTabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.e("","");
                }

                @Override
                public void onPageSelected(int position) {
                    EXAM_TAB_POSITION =position;
                    mExamTabSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.e("","");
                }
            });

            rootView.findViewById(R.id.check_gesture).setOnTouchListener(onSwipeTouchListener);
            rootView.findViewById(R.id.include_layout_profile_widget).setOnTouchListener(onSwipeTouchListener);
            if(this.isFistTime) {
                this.isFistTime = false;
                int currentPosition = mExamTabPager.getCurrentItem();
                mExamTabSelected(currentPosition);
            }
            mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);

        }else{

            rootView.findViewById(R.id.prep_buddies).setVisibility(View.GONE);
            rootView.findViewById(R.id.profile_syllabus_statusLL).setVisibility(View.GONE);
            rootView.findViewById(R.id.important_date_layout_RL).setVisibility(View.GONE);
        }

        rootView.findViewById(R.id.backup_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.wishList_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.recommended_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.important_date_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.profile_syllabus_statusLL).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Constants.READY_TO_CLOSE = true;
        getActivity().findViewById(R.id.bottom_tab_layout).setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Constants.READY_TO_CLOSE = false;

        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
            mainActivity.mUpdateTabMenuItem(-1);
        }

        getActivity().findViewById(R.id.bottom_tab_layout).setVisibility(View.VISIBLE);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
                this.mListener = (OnTabSelectListener) context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnTabSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
       // super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId())
        {
            case R.id.profile_syllabus_statusLL:
                if(this.mExamDetail != null)
                this.mProfileWidgetSelected(Constants.WIDGET_SYLLABUS, Constants.BASE_URL + "yearly-exams/"+ this.mExamDetail.getId()+"/syllabus/",null);
                break;
            case R.id.backup_colleges_layout_RL:
               if(this.mExamDetail != null)
                   mProfileWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes", this.mExamDetail.getExam_tag());
               else
               mProfileWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes",null);
               break;
            case R.id.wishList_colleges_layout_RL:
                mProfileWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/shortlistedinstitutes", null);
                break;
            case R.id.recommended_colleges_layout_RL:
                if(this.mExamDetail != null)
                    mProfileWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/",this.mExamDetail.getExam_tag());
                    else
                    mProfileWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/",null);
                break;
            case R.id.important_date_layout_RL:

                if(this.mExamDetail != null)
                this.mProfileWidgetSelected(Constants.WIDGET_TEST_CALENDAR, Constants.BASE_URL+"yearly-exams/"+mExamDetail.getId()+"/calendar/", null);
                  break;
            default:
                break;
        }
    }


    private void mProfileWidgetSelected(String requestType, String url, String examTag)
    {
        if(mListener != null)
            mListener.onHomeItemSelected(requestType, url, examTag);
    }

    private void mExamTabSelected(int position) {
        if(this.mListener != null && this.mExamDetailList != null && this.mExamDetailList.size() >position)
        {
           this.mExamDetail = this.mExamDetailList.get(position);
           this.mListener.onExamTabSelected(mExamDetail);
        }
    }

    public void updateExamSummary(ExamSummary examSummary) {
        this.mExamSummary = examSummary;
        View view = getView();
        if(view == null || this.mExamSummary == null)
            return;

        TextView backup_countTV = (TextView)view.findViewById(R.id.backup_colleges_count);
        TextView wishList_countTV =  (TextView)view.findViewById(R.id.wishList_colleges_count);
        TextView recommended_countTV =  (TextView)view.findViewById(R.id.recommended_colleges_count);
        TextView important_dateTV =  (TextView)view.findViewById(R.id.important_dates_count);
        TextView covered_syllabus =  (TextView)view.findViewById(R.id.covered_syllabus);
        CircularProgressBar profileCompleted =  (CircularProgressBar) view.findViewById(R.id.profile_image_circular_progressbar);

        backup_countTV.setText(""+this.mExamSummary.getBackup_count());
        wishList_countTV.setText(""+this.mExamSummary.getShortlist_count());
        recommended_countTV.setText(""+this.mExamSummary.getRecommended_count());
        important_dateTV.setText(""+this.mExamSummary.getNext_important_date());
        covered_syllabus.setText(""+this.mExamSummary.getSyllabus_covered()+"%");

        //TODO:: showing progress as a profile circle
        if(this.mExamSummary.getSyllabus_covered() ==0)
         profileCompleted.setProgress(100);
        else
         profileCompleted.setProgress(this.mExamSummary.getSyllabus_covered());
    }

            OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(getActivity()) {
                @Override
                public void onSwipeLeft() {
                    int currentPosition = mExamTabPager.getCurrentItem();
                    if (mExamDetailList.size()-1 >= currentPosition)
                        mExamTabPager.setCurrentItem(currentPosition + 1);
                }

                @Override
                public void onSwipeRight() {
                    super.onSwipeRight();

                    int currentPosition = mExamTabPager.getCurrentItem();
                    if (currentPosition > 0)
                        mExamTabPager.setCurrentItem(currentPosition - 1);
                }

            };

            /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public  interface OnTabSelectListener {

        void onExamTabSelected(ExamDetail tabPosition);

        void onHomeItemSelected(String requestType, String url, String examTag);
    }

}
