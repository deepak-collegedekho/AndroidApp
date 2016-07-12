package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sureshsaini on 6/12/15.
 */
public class TabFragment extends  BaseFragment{
    private final String TAG ="Tab Fragment";
    private static String PARAM1 = "param1";
    private static String PARAM2 = "param2";

    private int selectedTabPosition = 0;
    private int selectedSubMenuPosition = 0;
    private  OnHomeItemSelectListener mListener;
    private ArrayList<ProfileExam> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ProfileExam mExamDetail;
    private ExamSummary mExamSummary;
    private ViewPager mExamTabPager  = null;
    private PagerTabStrip mPagerHeader = null;
    private boolean isFistTime = false;
    private boolean IS_COLLEGE_TUTE_COMPLETED = true;
    private boolean IS_PREPARE_TUTE_COMPLETED = true;
    private int i = 0 ;
    private int examId;
    private View mExamsTabLayout;

    TextView mRecommendedCountTV;
    TextView mTrendingCountTV;
    TextView mShortlistCountTV;
    TextView mExploreCountTV;

    public static TabFragment newInstance(int tabPosoition,ArrayList<ProfileExam> examList) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM1, tabPosoition);
        args.putParcelableArrayList(PARAM2, examList);
        fragment.setArguments(args);
        return fragment;
    }

    public TabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.selectedTabPosition = args.getInt(PARAM1);
            this.mExamDetailList = args.getParcelableArrayList(PARAM2);
        }
        this.isFistTime = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        mRecommendedCountTV =  (TextView)rootView.findViewById(R.id.recommended_count);
        mTrendingCountTV = (TextView)rootView.findViewById(R.id.trending_count);
        mShortlistCountTV = (TextView)rootView.findViewById(R.id.shortlist_count);
        mExploreCountTV = (TextView)rootView.findViewById(R.id.explore_count);
        mPagerHeader    = (PagerTabStrip) rootView.findViewById(R.id.exam_pager_header);
        mExamTabPager       = (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        mExamsTabLayout     =rootView.findViewById(R.id.exams_tab_layout);
        TextView mProfileName = (TextView) rootView.findViewById(R.id.user_name);
        TextView mProfileNumber = (TextView) rootView.findViewById(R.id.user_phone);
        CircularImageView mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);

        IS_COLLEGE_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
        IS_PREPARE_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean("prepare_tute", false);
//        if(IS_COLLEGE_TUTE_COMPLETED){
//            getActivity().invalidateOptionsMenu();
//        } else {
//            getActivity().invalidateOptionsMenu();
//        }

        rootView.findViewById(R.id.prepare_tour_guide_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                IS_PREPARE_TUTE_COMPLETED = true;
//                        getActivity().invalidateOptionsMenu();
                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean("prepare_tute", true).apply();
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);
                updateCollegeCount(selectedTabPosition);
                TabFragment.this.getActivity().invalidateOptionsMenu();
            }
        });

        rootView.findViewById(R.id.prep_buddy_tour_guide_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IS_COLLEGE_TUTE_COMPLETED){
                    if(i ==0){
                        i++;
                        v.setBackgroundResource(R.drawable.ic_profile_tute2);
                    } else if(i ==1) {
                        i++;
                        v.setBackgroundResource(R.drawable.ic_profile_tute3);
                    } else {
                        v.setVisibility(View.GONE);
                        IS_COLLEGE_TUTE_COMPLETED = true;
//                        getActivity().invalidateOptionsMenu();
                        getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), true).apply();
                        View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                        bottomMenu.animate().translationY(0);
                        bottomMenu.setVisibility(View.VISIBLE);
                        updateCollegeCount(selectedTabPosition);
                        TabFragment.this.getActivity().invalidateOptionsMenu();
                    }
                }
//                else {
//                    v.setVisibility(View.GONE);
//                    View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
//                    bottomMenu.animate().translationY(0);
//                    bottomMenu.setVisibility(View.VISIBLE);
//                    updateCollegeCount(selectedTabPosition);
//                }
            }
        });

        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        IS_COLLEGE_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
        if(MainActivity.mProfile != null) {

            String name = MainActivity.mProfile.getName();
            if (name == null || name.isEmpty() || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
                mProfileName.setText("Name : Anonymous User");
                mProfileName.setVisibility(View.VISIBLE);
            } else {
                String userName = name.substring(0, 1).toUpperCase() + name.substring(1);
                mProfileName.setText("Name : "+userName);
                mProfileName.setVisibility(View.VISIBLE);
            }

            String phone = MainActivity.mProfile.getPhone_no();
            if (phone == null || phone.isEmpty() || phone == "null") {
                mProfileNumber.setText("Phone : Not Set");
                mProfileNumber.setVisibility(View.VISIBLE);
            } else {
                mProfileNumber.setText("Phone : " + phone);
                mProfileNumber.setVisibility(View.VISIBLE);
            }

            String image = MainActivity.mProfile.getImage();
            if (image != null && ! image.isEmpty()) {
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
                mProfileImage.setVisibility(View.VISIBLE);
            }
            CircularProgressBar profileCompleted =  (CircularProgressBar) rootView.findViewById(R.id.user_profile_progress);
            profileCompleted.setProgress(0);
            profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);
        }

        if(mExamDetailList == null || mExamDetailList.isEmpty()){
            if(MainActivity.mProfile != null)
                mExamDetailList = MainActivity.mProfile.getYearly_exams();
        }

        if(this.mExamDetailList != null && this.mExamDetailList.size() > 0) {
            this.mExamTabPager.setVisibility(View.VISIBLE);
            this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
            this.mExamTabPager.setAdapter(this.mDetailsAdapter);
            ((ViewPager.LayoutParams) mPagerHeader.getLayoutParams()).isDecor = true;

            this.mExamTabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.e("","");
                }

                @Override
                public void onPageSelected(int position) {
                    TabFragment.this.EXAM_TAB_POSITION = position;
                    TabFragment.this.mExamTabSelected(position);

                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.e("","");
                }
           });

           rootView.findViewById(R.id.exam_swipe_listener_layout).setOnTouchListener(this.onSwipeTouchListener);
           rootView.findViewById(R.id.include_layout_home_widget).setOnTouchListener(this.onSwipeTouchListener);

            if(this.isFistTime) {
                this.isFistTime = false;
                int currentPosition = this.mExamTabPager.getCurrentItem();
                this.mExamTabSelected(currentPosition);
            }
            this.mExamTabPager.setCurrentItem(this.EXAM_TAB_POSITION);
        }else{

            if(this.mListener != null)
            {
                this.mExamDetail = new ProfileExam();
                this.mExamDetail.setId(0);
            }
        }
//        rootView.findViewById(R.id.prep_buddy_tour_guide_image).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_fourth).setOnClickListener(this);
        rootView.findViewById(R.id.profile_image).setOnClickListener(this);
        rootView.findViewById(R.id.include_image_layout).findViewById(R.id.profile_image_edit_button).setOnClickListener(this);

        String psychometricResults = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getString("psychometric_report", null);

        if (MainActivity.mProfile != null && MainActivity.mProfile.getPsychometric_given() == 1 && psychometricResults != null) {
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.GONE);
            rootView.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.VISIBLE);
        } else {
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.GONE);
        }

        rootView.findViewById(R.id.btn_tab_psychometric_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTabPsychometricTest();
            }
        });

        rootView.findViewById(R.id.btn_tab_psychometric_report).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTabPsychometricReport();
            }
        });

//        if (MainActivity.mProfile.getStep_by_step_given() == 1)
//            rootView.findViewById(R.id.btn_tab_step_by_step).setVisibility(View.GONE);

        rootView.findViewById(R.id.btn_tab_step_by_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTabStepByStep();
            }
        });

        return rootView;
    }

    public void updateExamsList(ArrayList<ProfileExam> examsList){
        if(mExamDetailList == null)
            return;
        this.mExamDetailList=examsList;
        this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
        mExamTabPager.setAdapter(this.mDetailsAdapter);
    }


    @Override
    public void onResume() {
        super.onResume();
        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
            if (MainActivity.mProfile.getYearly_exams() != null && MainActivity.mProfile.getYearly_exams().size()>0) {
                mExamsTabLayout.setVisibility(View.VISIBLE);
                this.mExamDetailList = MainActivity.mProfile.getYearly_exams();
                this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
                mExamTabPager.setAdapter(this.mDetailsAdapter);
                mExamTabPager.invalidate();
                mainActivity.mUpdateTabMenuItem(this.selectedTabPosition,0);
                if(mPagerHeader != null)
                    ((ViewPager.LayoutParams) mPagerHeader.getLayoutParams()).isDecor = true;
                if (selectedTabPosition < mExamDetailList.size())
                    mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);
            }else {
                if(selectedTabPosition == 3)
                    selectedTabPosition =1;
                mainActivity.mUpdateTabMenuItem(this.selectedTabPosition,0);
                mExamsTabLayout.setVisibility(View.GONE);
            }
        }
        if(this.mExamDetailList != null && !this.mExamDetailList.isEmpty()) {
            if (this.mListener != null && this.mExamDetailList != null && this.mExamDetailList.size() > 0) {
                this.mExamDetail = this.mExamDetailList.get(mExamTabPager.getCurrentItem());
            }
        }else{

            if(this.mListener != null)
            {
                this.mExamDetail = new ProfileExam();
                this.mExamDetail.setId(0);
            }
        }
/*
        if (mExamDetailList.size() >= EXAM_TAB_POSITION)
            mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);*/

        this.mUpdateSubMenuItem();
//        if(IS_COLLEGE_TUTE_COMPLETED)
//        {
//            View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
//            bottomMenu.animate().translationY(0);
//            bottomMenu.setVisibility(View.VISIBLE);
//        }
    }

    @Override
    public void onPause() {
        super.onPause();
        final MainActivity mainActivity = (MainActivity)getActivity();
        if(mainActivity.currentBottomItem != null){
            mainActivity.mUpdateTabMenuItem(-2,0);
//            mainActivity.currentBottomItem.animate().translationYBy(10f).setDuration(0).start();
//            mainActivity.currentBottomItem = null;

        }
        View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
        bottomMenu.animate().translationY(bottomMenu.getHeight());
        bottomMenu.setVisibility(View.GONE);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
       try{
            if (context instanceof MainActivity)
                this.mListener = (OnHomeItemSelectListener)context;
        }
        catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnHomeItemSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }


    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

//    private void mIsTuteCompleted(View v){
//        if(!IS_COLLEGE_TUTE_COMPLETED){
//            if(i ==0){
//                i++;
//                v.setBackgroundResource(R.drawable.ic_profile_tute2);
//            } else if(i ==1) {
//                i++;
//                v.setBackgroundResource(R.drawable.ic_profile_tute3);
//            } else {
//                v.setVisibility(View.GONE);
//                IS_COLLEGE_TUTE_COMPLETED = true;
//                getActivity().invalidateOptionsMenu();
//                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), true).apply();
//                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
//                bottomMenu.animate().translationY(0);
//                bottomMenu.setVisibility(View.VISIBLE);
//                updateCollegeCount(selectedTabPosition);
//            }
//        } else {
//            v.setVisibility(View.GONE);
//            View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
//            bottomMenu.animate().translationY(0);
//            bottomMenu.setVisibility(View.VISIBLE);
//            updateCollegeCount(selectedTabPosition);
//        }
//    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view.getId() == R.id.profile_image || view.getId() == R.id.profile_image_edit_button) {
            if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
                ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
                return;
            }
            if(mListener != null)
                mListener.requestForProfileFragment();

        }
//        else if(view.getId() == R.id.prep_buddy_tour_guide_image){
//            mIsTuteCompleted(view);
//        }

        if (view.getId() == R.id.btn_tab_psychometric_test)
            mListener.onTabPsychometricTest();
        if (view.getId() == R.id.btn_tab_step_by_step)
            mListener.onTabStepByStep();
        else {
            try {
                this.selectedSubMenuPosition = Integer.parseInt((String) view.getTag());
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.mSubMenuItemClickListener();
        }
    }

    private void mExamTabSelected(int position) {
        if(this.mListener != null && this.mExamDetailList != null && this.mExamDetailList.size() >position) {
            this.mExamDetail = this.mExamDetailList.get(position);
            this.examId = mExamDetail.getId();
            updateCollegeCount(selectedTabPosition);
        }
    }

    private void mHomeWidgetSelected(String requestType, String url, String tag)
    {
        if(mListener != null)
            mListener.onHomeItemSelected(requestType, url,tag);
    }

    private void mUpdateSubMenuItem(){
        final View view = getView();
        if(view ==   null)
            return;

        TextView firstSubMenuTV       = (TextView)view.findViewById(R.id.home_widget_textview_first);
        TextView secondSubMenuTV   = (TextView)view.findViewById(R.id.home_widget_textview_second);
        TextView thirdSubMenuTV    = (TextView)view.findViewById(R.id.home_widget_textview_third);
        TextView fourthSubMenuTV          = (TextView)view.findViewById(R.id.home_widget_textview_fourth);

        ImageView firstSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_first);
        ImageView secondSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_second);
        ImageView thirdSubMenuIV      = (ImageView)view.findViewById(R.id.home_widget_image_third);
        ImageView fourthSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_fourth);

        if(this.selectedTabPosition == 1){
            IS_COLLEGE_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
            if(view != null ){
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                if(!IS_COLLEGE_TUTE_COMPLETED) {
                    getActivity().invalidateOptionsMenu();
                    view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.VISIBLE);
                    bottomMenu.animate().translationY(bottomMenu.getHeight());
                    bottomMenu.setVisibility(View.GONE);
                } else {
                    view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
                    bottomMenu.animate().translationY(0);
                    bottomMenu.setVisibility(View.VISIBLE);
                }
            }

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, .5f);
            ll.setLayoutParams(lp);

            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);
            ll2.setLayoutParams(lp);

            LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) ((RelativeLayout) getView().findViewById(R.id.home_widget_third)).getLayoutParams();;
            int marginLeftInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40 , getResources().getDisplayMetrics());
            int marginRightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15 , getResources().getDisplayMetrics());
            lp2.setMargins(marginLeftInDp,0,marginRightInDp,0);
            getView().findViewById(R.id.home_widget_third).setLayoutParams(lp2);

            //this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_institute);
            secondSubMenuIV.setImageResource(R.drawable.ic_shortlist);
            thirdSubMenuIV.setImageResource(R.drawable.ic_trending);
            fourthSubMenuIV.setImageResource(R.drawable.ic_search_college_widget);

            firstSubMenuTV.setText(MainActivity.getResourceString(R.string.TAG_TAB_RECOMMENDED_COLLEGS));
            firstSubMenuTV.setContentDescription("Select to Explore recommended Institutes");
            secondSubMenuTV.setText(MainActivity.getResourceString(R.string.TAG_TAB_SHORTLISTED_COLLEGS));
            secondSubMenuTV.setContentDescription("Select to Explore shortlisted Institutes");
            thirdSubMenuTV.setText(MainActivity.getResourceString(R.string.TAG_TAB_FEATURED_COLLEGS));
            thirdSubMenuTV.setContentDescription("Select to Explore popular Institutes");
            fourthSubMenuTV.setText(MainActivity.getResourceString(R.string.TAG_TAB_EXPLORE_COLLEGS));
            fourthSubMenuTV.setContentDescription("Select to Explore all Institutes");

            ll2.getChildAt(0).setVisibility(View.VISIBLE);
            ll2.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);

        }else if(this.selectedTabPosition == 2){
//            IS_COLLEGE_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
//            if(view != null ){
//                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
//                if(!IS_COLLEGE_TUTE_COMPLETED) {
//                    view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.VISIBLE);
//                    bottomMenu.animate().translationY(bottomMenu.getHeight());
//                    bottomMenu.setVisibility(View.GONE);
//                } else {
//                    view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
//                    bottomMenu.animate().translationY(0);
//                    bottomMenu.setVisibility(View.VISIBLE);
//                }
//            }

            if(view != null ){
                view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
                view.findViewById(R.id.prepare_tour_guide_image).setVisibility(View.GONE);
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);
            }


            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);
            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);
            ll2.setLayoutParams(lp);
            //this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_chat_bubble_widget);
            secondSubMenuIV.setImageResource(R.drawable.ic_qna);

            firstSubMenuTV.setText("Future Buddies");
            firstSubMenuTV.setContentDescription("Your Future mates");
            secondSubMenuTV.setText("Q & A");
            secondSubMenuTV.setContentDescription("FAQ's");

            ll2.getChildAt(0).setVisibility(View.VISIBLE);
            ll2.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);

        }else  if(this.selectedTabPosition == 3){
            IS_PREPARE_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean("prepare_tute", false);
            if(view != null ){
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                if(!IS_PREPARE_TUTE_COMPLETED) {
                    getActivity().invalidateOptionsMenu();
                    view.findViewById(R.id.prepare_tour_guide_image).setVisibility(View.VISIBLE);
                    bottomMenu.animate().translationY(bottomMenu.getHeight());
                    bottomMenu.setVisibility(View.GONE);
                } else {
                    view.findViewById(R.id.prepare_tour_guide_image).setVisibility(View.GONE);
                    bottomMenu.animate().translationY(0);
                    bottomMenu.setVisibility(View.VISIBLE);
                }
            }

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);

            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);

            LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) ((RelativeLayout) getView().findViewById(R.id.home_widget_third)).getLayoutParams();;
            int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45 , getResources().getDisplayMetrics());
            lp2.setMargins(marginInDp,0,marginInDp,0);
            getView().findViewById(R.id.home_widget_third).setLayoutParams(lp2);

//            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_test_calendar);
            secondSubMenuIV.setImageResource(R.drawable.ic_syllabus);
            thirdSubMenuIV.setImageResource(R.drawable.ic_important_dates);

            firstSubMenuTV.setText("Test Calendar");
            firstSubMenuTV.setContentDescription("Test preparation calendar");
            secondSubMenuTV.setText("Syllabus");
            secondSubMenuTV.setContentDescription("Syllabus for exam");
            thirdSubMenuTV.setText("Important Dates");
            thirdSubMenuTV.setContentDescription("Important Dates");

            ll2.getChildAt(0).setVisibility(View.VISIBLE);
            ll2.getChildAt(1).setVisibility(View.GONE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.VISIBLE);

        }else  if(this.selectedTabPosition == 4){
//            IS_COLLEGE_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
//            if(view != null ){
//                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
//                if(!IS_COLLEGE_TUTE_COMPLETED) {
//                    view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.VISIBLE);
//                    bottomMenu.animate().translationY(bottomMenu.getHeight());
//                    bottomMenu.setVisibility(View.GONE);
//                } else {
//                    view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
//                    bottomMenu.animate().translationY(0);
//                    bottomMenu.setVisibility(View.VISIBLE);
//                }
//            }

            if(view != null ){
                view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
                view.findViewById(R.id.prepare_tour_guide_image).setVisibility(View.GONE);
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);
            }

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);

            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);
            ll2.setLayoutParams(lp);

            //this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_news);
            secondSubMenuIV.setImageResource(R.drawable.ic_article);

            firstSubMenuTV.setText("News");
            firstSubMenuTV.setContentDescription("News");
            secondSubMenuTV.setText("Articles");
            secondSubMenuTV.setContentDescription("Articles");

            ll2.getChildAt(0).setVisibility(View.VISIBLE);
            ll2.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);
        }
        updateCollegeCount(selectedTabPosition);
    }

    private void updateCollegeCount(int selectedTabPosition) {

        if(selectedTabPosition == 1 && mListener != null ){
            mRecommendedCountTV.setVisibility(View.VISIBLE);
            mTrendingCountTV.setVisibility(View.VISIBLE);
            mShortlistCountTV.setVisibility(View.VISIBLE);
            mExploreCountTV.setVisibility(View.VISIBLE);

            mListener.onExamTabSelected(this.mExamDetail);

            int recommended = (this.mExamSummary != null) ? this.mExamSummary.getRecommended_count() : 0;
            int shortlist = (this.mExamSummary != null) ? this.mExamSummary.getShortlist_count() : 0;
            int trending = (this.mExamSummary != null) ? this.mExamSummary.getBuzzlist_count() : 0;
            int explore = (this.mExamSummary != null) ? this.mExamSummary.getBackup_count() : 0;

            Utils.SetCounterAnimation(mRecommendedCountTV, recommended, "" , "", Constants.ANIM_SHORT_DURATION);
            mRecommendedCountTV.setContentDescription(String.valueOf(recommended));

            Utils.SetCounterAnimation(mShortlistCountTV, shortlist, "" , "", Constants.ANIM_SHORT_DURATION);
            mShortlistCountTV.setContentDescription(String.valueOf(shortlist));

            Utils.SetCounterAnimation(mTrendingCountTV, trending, "" , "", Constants.ANIM_SHORT_DURATION);
            mTrendingCountTV.setContentDescription(String.valueOf(trending));

            Utils.SetCounterAnimation(mExploreCountTV, explore, "" , "", Constants.ANIM_SHORT_DURATION);
            mExploreCountTV.setContentDescription(String.valueOf(explore));
        } else {
            mRecommendedCountTV.setVisibility(View.GONE);
            mTrendingCountTV.setVisibility(View.GONE);
            mShortlistCountTV.setVisibility(View.GONE);
            mExploreCountTV.setVisibility(View.GONE);
        }
    }

    private void mtoggleView(LinearLayout linearLayout1, LinearLayout linearLayout2, int visibility)
    {
        if (visibility == View.VISIBLE)
        {
            linearLayout1.animate().setDuration(Constants.ANIM_LONG_DURATION).alpha(1.0f);
            linearLayout1.animate().setDuration(Constants.ANIM_LONG_DURATION).translationY(0);
            linearLayout1.setVisibility(View.VISIBLE);

            linearLayout2.animate().setDuration(Constants.ANIM_LONG_DURATION).alpha(1.0f);
            linearLayout2.animate().setDuration(Constants.ANIM_LONG_DURATION).translationY(0);
            linearLayout2.setVisibility(View.VISIBLE);
        }
        else if (visibility == View.GONE)
        {
            linearLayout1.animate().setDuration(Constants.ANIM_LONG_DURATION).alpha(0.0f);
            linearLayout1.animate().setDuration(Constants.ANIM_LONG_DURATION).translationY(linearLayout1.getHeight());
            linearLayout1.setVisibility(View.GONE);

            linearLayout2.animate().setDuration(Constants.ANIM_LONG_DURATION).alpha(0.0f);
            linearLayout2.animate().setDuration(Constants.ANIM_LONG_DURATION).translationY(linearLayout2.getHeight());
            linearLayout2.setVisibility(View.GONE);
        }
    }

    private void mSubMenuItemClickListener(){
        if(selectedTabPosition == 1){

            if(selectedSubMenuPosition == 1) {
                if(this.mExamDetail != null)
                    this.mHomeWidgetSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/", this.mExamDetail.getExam_tag());
                else
                    this.mHomeWidgetSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/",null);
            } else if(selectedSubMenuPosition == 2) {
                this.mHomeWidgetSelected(Constants.WIDGET_SHORTLIST_INSTITUTES, Constants.BASE_URL + "personalize/shortlistedinstitutes", null);
            } else if(selectedSubMenuPosition == 3) {
                if(this.mExamDetail != null)
                    this.mHomeWidgetSelected(Constants.CARD_BUZZLIST_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/?action=2", this.mExamDetail.getExam_tag());
                else
                    this.mHomeWidgetSelected(Constants.CARD_BUZZLIST_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/?action=2",null);
            } else if(selectedSubMenuPosition == 4) {
                if(this.mExamDetail != null)
                    this.mHomeWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", this.mExamDetail.getExam_tag());//this.mExamDetail.getExam_tag());
                else
                    this.mHomeWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/",null);
            }
        } else if(selectedTabPosition == 2){
            if(selectedSubMenuPosition == 1){
                this.mHomeWidgetSelected(Constants.WIDGET_FORUMS, Constants.BASE_URL + "personalize/forums", null);
            }else if(selectedSubMenuPosition == 2){
                this.mHomeWidgetSelected(Constants.TAG_LOAD_QNA_QUESTIONS, Constants.BASE_URL+"personalize/qna", null);
            }
        } else if (selectedTabPosition == 3){
            if(selectedSubMenuPosition == 1){
                if (this.mExamDetail != null) {
                    this.mHomeWidgetSelected(Constants.WIDGET_TEST_CALENDAR, Constants.BASE_URL + "yearly-exams/" + mExamDetail.getId() + "/calendar/", null);
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID, ""+mExamDetail.getId()).apply();
                }
            } else if(selectedSubMenuPosition == 2) {
                if (this.mExamDetail != null) {
                    this.mHomeWidgetSelected(Constants.WIDGET_SYLLABUS , Constants.BASE_URL + "yearly-exams/" + mExamDetail.getId() + "/syllabus/", null);
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID,  ""+mExamDetail.getId()).commit();
                }
            } else if(selectedSubMenuPosition == 3) {
                if (this.mExamDetail != null) {
                    this.mHomeWidgetSelected(Constants.TAG_MY_ALERTS, Constants.BASE_URL + "exam-alerts/", null);
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID, ""+mExamDetail.getId()).commit();
                }
            }
        } else if (selectedTabPosition == 4){
            if(selectedSubMenuPosition == 1){
                this.mHomeWidgetSelected(Constants.WIDGET_NEWS, Constants.BASE_URL+"personalize/news", null);
            }else  if(selectedSubMenuPosition == 2){
                this.mHomeWidgetSelected(Constants.WIDGET_ARTICES, Constants.BASE_URL+"personalize/articles", null);
            }
        }
    }

    public int getSelectedTab(){
        return selectedTabPosition;
    }

    public void setSelectedTab(int selectedTabPosition){
        this.selectedTabPosition = selectedTabPosition;
    }

    public void updateTabFragment(int tabPosition){
        this.selectedTabPosition = tabPosition;
        mUpdateSubMenuItem();
    }

    public void updateExamSummary(ExamSummary examSummary) {
        View view = getView();
        this.mExamSummary = examSummary;
        if(view == null || examSummary == null)
            return;

        // updateCollegeCount(selectedTabPosition);
        //TODO:: showing progress as a profile circle
        //if(examSummary.getSyllabus_covered() ==0)
           // profileCompleted.setProgress(100);
        //else
           // profileCompleted.setProgress(examSummary.getSyllabus_covered());
    }

    public void updateCollegeCountFromVolley(boolean update){
        int updateId = Integer.parseInt(mExamSummary.getYearly_exam_id());
        if(examId == updateId && update){
                updateCollegeCount(selectedTabPosition);
        }
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

    public void updateUserInfo() {

        View view = getView();
        if (view == null || MainActivity.mProfile == null)
            return;

        TextView mProfileName = (TextView) view.findViewById(R.id.user_name);
        TextView mProfileNumber = (TextView) view.findViewById(R.id.user_phone);

        Profile profile = MainActivity.mProfile;
        String name = profile.getName();
        if (name == null || name.isEmpty() || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
            mProfileName.setText("Name : Anonymous User");
        } else {
            String userName = name.substring(0, 1).toUpperCase() + name.substring(1);
            mProfileName.setText("Name : " + userName);
        }

        String phone = profile.getPhone_no();
        if (phone == null || phone.isEmpty() || phone == "null") {
            mProfileNumber.setText("Phone : Not Set");
        } else {
            mProfileNumber.setText("Phone : " + phone);
        }

        CircularProgressBar profileCompleted = (CircularProgressBar) view.findViewById(R.id.user_profile_progress);
        profileCompleted.setProgress(0);
        profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);
    }


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
    public int getSelectedTabPosition(){
        return selectedTabPosition;
    }
    public  interface OnHomeItemSelectListener {

        void onExamTabSelected(ProfileExam tabPosition);
        void onHomeItemSelected(String requestType, String url,String examTag);
        void requestForProfileFragment();
        void onTabStepByStep();
        void onTabPsychometricTest();
        public void onTabPsychometricReport();
    }

}