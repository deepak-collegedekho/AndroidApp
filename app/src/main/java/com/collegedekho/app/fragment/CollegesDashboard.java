package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.listener.DashBoardItemListener;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.network.ApiEndPonits;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 28/11/16.
 */

public class CollegesDashboard extends BaseFragment {
    private static String PARAM1 = "param1";

    private DashBoardItemListener mListener;
    private ArrayList<ProfileExam> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ProfileExam mExamDetail;
    private volatile ExamSummary mExamSummary;
    private ViewPager mExamTabPager  = null;
    private PagerTabStrip mPagerHeader = null;
    private boolean isFistTime = false;
    protected  static int EXAM_TAB_POSITION =0;
    private View mExamsTabLayout;
    private ImageView mLeftButton;
    private ImageView mRightButton;

    private TickerView mRecommendedCountTV;
    private TickerView mTrendingCountTV;
    private TickerView mShortlistCountTV;
    private TickerView mExploreCountTV;
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private View mExamSwipeListener;
    private View mHomeWidgetLayout;

    public static CollegesDashboard newInstance() {
          return new CollegesDashboard();
    }

    public static CollegesDashboard newInstance(ArrayList<ProfileExam> examList) {
        CollegesDashboard fragment = new CollegesDashboard();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1, examList);
        fragment.setArguments(args);
        return fragment;
    }
    public void CollegesDashboard(){ }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.mExamDetailList = args.getParcelableArrayList(PARAM1);
        }
        this.isFistTime = true;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        this.mRecommendedCountTV     =   (TickerView) rootView.findViewById(R.id.recommended_count);
        this.mTrendingCountTV        =   (TickerView)rootView.findViewById(R.id.trending_count);
        this.mShortlistCountTV       =   (TickerView)rootView.findViewById(R.id.shortlist_count);
        this.mExploreCountTV         =   (TickerView)rootView.findViewById(R.id.explore_count);
        this.mRecommendedCountTV.setCharacterList(NUMBER_LIST);
        this.mTrendingCountTV.setCharacterList(NUMBER_LIST);
        this.mShortlistCountTV.setCharacterList(NUMBER_LIST);
        this.mExploreCountTV.setCharacterList(NUMBER_LIST);

        this.mRecommendedCountTV.setText("0");
        this.mTrendingCountTV.setText("0");
        this.mShortlistCountTV.setText("0");
        this.mExploreCountTV.setText("0");

        this.mUpdateSubMenuItem(rootView);

        this.mPagerHeader            =   (PagerTabStrip) rootView.findViewById(R.id.exam_pager_header);
        this.mExamTabPager           =   (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        this.mExamsTabLayout         =   rootView.findViewById(R.id.exams_tab_layout);
        CircularImageView mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        ImageView tuteImage = (ImageView)rootView.findViewById(R.id.home_tute_image);
        tuteImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_tute5));
        //tuteImage.setImageBitmap(BitMapResize.decodeSampledBitmapFromResource(getResources(), tuteImage.getId(), tuteImage.getWidth(), tuteImage.getHeight()));

        tuteImage.setOnClickListener(this);

        if(MainActivity.mProfile != null) {

            updateUserProfile(rootView, MainActivity.mProfile);
            String image = MainActivity.mProfile.getImage();
            if (image != null && ! image.isEmpty()) {
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
                mProfileImage.setVisibility(View.VISIBLE);
            }
            if(this.mExamDetailList == null || this.mExamDetailList.isEmpty()){
                this.mExamDetailList = MainActivity.mProfile.getYearly_exams();
            }
        }
        if(this.mExamDetailList != null && this.mExamDetailList.size() > 0) {
            this.mExamTabPager.setVisibility(View.VISIBLE);
            this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
            this.mExamTabPager.setAdapter(this.mDetailsAdapter);
            ((ViewPager.LayoutParams) this.mPagerHeader.getLayoutParams()).isDecor = true;

            this.mExamTabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    Log.e("","");
                }

                @Override
                public void onPageSelected(int position) {
                    CollegesDashboard.EXAM_TAB_POSITION = position;
                    CollegesDashboard.this.mExamTabSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    Log.e("","");
                }
            });

            this.mExamSwipeListener = rootView.findViewById(R.id.exam_swipe_listener_layout);
            this.mHomeWidgetLayout = rootView.findViewById(R.id.include_layout_home_widget);

            this.mExamSwipeListener .setOnTouchListener(this.onSwipeTouchListener);
            this.mHomeWidgetLayout.setOnTouchListener(this.onSwipeTouchListener);

            if(this.isFistTime) {
                this.isFistTime = false;
                int currentPosition = this.mExamTabPager.getCurrentItem();
                this.mExamTabSelected(currentPosition);
            }
            this.mExamTabPager.setCurrentItem(CollegesDashboard.EXAM_TAB_POSITION);
            this.mInitializeExamTabNavButtons(rootView);
        }else{

            if(this.mListener != null)
            {
                this.mExamDetail = new ProfileExam();
                this.mExamDetail.setId(0);
            }
        }
        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_fourth).setOnClickListener(this);
        rootView.findViewById(R.id.profile_image).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_step_by_step).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_psychometric_test).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_psychometric_report).setOnClickListener(this);

        rootView.findViewById(R.id.include_image_layout).findViewById(R.id.profile_image_edit_button).setOnClickListener(this);

        String psychometricResults = sharedPreferences.getString("psychometric_report", null);

        if (MainActivity.mProfile != null && MainActivity.mProfile.getPsychometric_given() == 1 && psychometricResults != null) {
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.GONE);
            rootView.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.VISIBLE);
        } else {
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.GONE);
        }

        return rootView;
    }

    private void mInitializeExamTabNavButtons(View view)
    {
        if (this.mExamDetailList != null && this.mExamDetailList.size() > 1)
        {
            this.mLeftButton = (ImageView) view.findViewById(R.id.exam_left_nav);
            this.mRightButton = (ImageView) view.findViewById(R.id.exam_right_nav);

            this.mLeftButton.setVisibility(View.VISIBLE);
            this.mRightButton.setVisibility(View.VISIBLE);

            this.mLeftButton.setHapticFeedbackEnabled(true);
            this.mRightButton.setHapticFeedbackEnabled(true);

            this.mLeftButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPosition = CollegesDashboard.this.mExamTabPager.getCurrentItem();
                    if (currentPosition > 0)
                        CollegesDashboard.this.mExamTabPager.setCurrentItem(currentPosition - 1);
                    else
                    {
                        CollegesDashboard.this.mLeftButton.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        CollegesDashboard.this.mLeftButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
                    }
                }
            });

            this.mRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPosition = CollegesDashboard.this.mExamTabPager.getCurrentItem();
                    if (CollegesDashboard.this.mExamDetailList.size() - 1 > currentPosition)
                        CollegesDashboard.this.mExamTabPager.setCurrentItem(currentPosition + 1);
                    else
                    {
                        CollegesDashboard.this.mRightButton.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        CollegesDashboard.this.mRightButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
                    }
                }
            });
        }
        else
        {
            if (this.mLeftButton != null && this.mRightButton != null)
            {
                this.mLeftButton.setVisibility(View.GONE);
                this.mRightButton.setVisibility(View.GONE);
            }
        }
    }

    private void mExamTabSelected(int position) {
        if(this.mExamDetailList != null && this.mExamDetailList.size() >position) {
            this.mExamDetail = this.mExamDetailList.get(position);
        }
        requestToUpdateExamSummary();
    }
    private void requestToUpdateExamSummary(){
        if(mListener != null && this.mExamDetail != null) {
            this.mListener.onExamTabSelected(this.mExamDetail);
        }
    }

    public void updateUserYearlyExamSummary(ExamSummary examSummary){
        if(examSummary == null)return;
        this.mExamSummary = examSummary;
        updateCollegeCount();
    }

    private void updateCollegeCount() {
            if(this.mExamSummary == null)return;

            this.mRecommendedCountTV.setVisibility(View.GONE);
            this.mTrendingCountTV.setVisibility(View.VISIBLE);
            this.mShortlistCountTV.setVisibility(View.VISIBLE);
            this.mExploreCountTV.setVisibility(View.VISIBLE);


            int recommended = (this.mExamSummary != null) ? this.mExamSummary.getRecommended_count() : 0;
            int shortlist = (this.mExamSummary != null) ? this.mExamSummary.getShortlist_count() : 0;
            int trending = (this.mExamSummary != null) ? this.mExamSummary.getBuzzlist_count() : 0;
            int explore = (this.mExamSummary != null) ? this.mExamSummary.getBackup_count() : 0;

            setInstituteCount(this.mRecommendedCountTV, recommended);
            this.mRecommendedCountTV.setContentDescription(String.valueOf(recommended));

            setInstituteCount(this.mShortlistCountTV, shortlist);
            this.mShortlistCountTV.setContentDescription(String.valueOf(shortlist));

            setInstituteCount(this.mTrendingCountTV, trending);
            this.mTrendingCountTV.setContentDescription(String.valueOf(trending));

            setInstituteCount(this.mExploreCountTV, explore);
            this.mExploreCountTV.setContentDescription(String.valueOf(explore));

    }

    private void setInstituteCount(final TickerView view,final int count){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(view != null)
                    view.setText(String.valueOf(count));
            }
        }, 200);
    }



    private void mHomeWidgetSelected(String requestType, String url, String tag){
        if(this.mListener != null)
            this.mListener.onHomeItemSelected(requestType, url,tag);
    }

    private OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(getActivity()) {
        @Override
        public void onSwipeLeft() {
            int currentPosition = CollegesDashboard.this.mExamTabPager.getCurrentItem();
            if (CollegesDashboard.this.mExamDetailList.size()-1 >= currentPosition)
                CollegesDashboard.this.mExamTabPager.setCurrentItem(currentPosition + 1);
        }

        @Override
        public void onSwipeRight() {
            super.onSwipeRight();

            int currentPosition = CollegesDashboard.this.mExamTabPager.getCurrentItem();
            if (currentPosition > 0)
                CollegesDashboard.this.mExamTabPager.setCurrentItem(currentPosition - 1);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity)
            {
                this.mListener = (DashBoardItemListener) context;
            }
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


    public void updateUserInfo() {
        updateUserProfile(getView(), MainActivity.mProfile);
    }

    private void updateUserProfile(View view, Profile profile){
        if (view == null || profile == null)
            return;

        TextView mProfileName = (TextView) view.findViewById(R.id.user_name);
        TextView mProfileNumber = (TextView) view.findViewById(R.id.user_phone);
        mProfileName.setOnClickListener(this);
        mProfileNumber.setOnClickListener(this);
        mProfileName.setVisibility(View.VISIBLE);
        mProfileNumber.setVisibility(View.VISIBLE);

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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.user_name:
            case R.id.user_phone:
            case R.id.profile_image:
            case R.id.profile_image_edit_button:
                this.mListener.requestForProfileFragment();
                break;
            case R.id.btn_tab_psychometric_test:
                this.mListener.onPsychometricTestSelected();
                break;
            case R.id.btn_tab_psychometric_report:
                this.mListener.onTabPsychometricReport();
                break;
            case R.id.btn_tab_step_by_step:
                this.mListener.onTabStepByStep();
                break;
            case R.id.home_tute_image:
                view.setVisibility(View.GONE);
                if(getActivity() != null) {
                    ((MainActivity) getActivity()).mShowFabCounselor();
                    getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.INSTITUTES_HOME_TUTE), true).apply();
                }
                updateCollegeCount();
                    break;
            default:
                try {
                    int pos = Integer.parseInt((String) view.getTag());
                    this.mSubMenuItemClickListener(pos);
                } catch (Exception e) {
                    e.printStackTrace();
                }
        }
    }

    private void mSubMenuItemClickListener(int position){

//            if(position == 1) {
//                if(this.mExamDetail != null)
//                    this.mHomeWidgetSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, ApiEndPonits.API_RECOMMENDED_INSTITUTES, this.mExamDetail.getExam_tag());
//                else
//                    this.mHomeWidgetSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, ApiEndPonits.API_RECOMMENDED_INSTITUTES,null);
//            } else
            if(position == 1) {
                this.mHomeWidgetSelected(Constants.WIDGET_SHORTLIST_INSTITUTES, ApiEndPonits.API_SHORTLISTED_INSTITUTES, null);
            } else if(position == 2) {
                if(this.mExamDetail != null)
                    this.mHomeWidgetSelected(Constants.CARD_BUZZLIST_INSTITUTES, ApiEndPonits.API_BUZZLIST_INSTITUTES, this.mExamDetail.getExam_tag());
                else
                    this.mHomeWidgetSelected(Constants.CARD_BUZZLIST_INSTITUTES,  ApiEndPonits.API_BUZZLIST_INSTITUTES,null);
            } else if(position == 3) {
                if(this.mExamDetail != null)
                    this.mHomeWidgetSelected(Constants.WIDGET_INSTITUTES, ApiEndPonits.API_PERSONALIZE_INSTITUTES, this.mExamDetail.getExam_tag());
                else
                    this.mHomeWidgetSelected(Constants.WIDGET_INSTITUTES, ApiEndPonits.API_PERSONALIZE_INSTITUTES,null);
            }

    }


    public void updateExamsList(ArrayList<ProfileExam> examsList){
        if(this.mExamDetailList == null)
            return;
        this.mExamDetailList=examsList;
        this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
        this.mExamTabPager.setAdapter(this.mDetailsAdapter);
        if(this.mExamDetailList != null && !this.mExamDetailList.isEmpty()){
            this.mExamDetail = this.mExamDetailList.get(this.mExamTabPager.getCurrentItem());
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mExamDetailList = MainActivity.mProfile.getYearly_exams();
        if (this.mExamDetailList != null && !this.mExamDetailList.isEmpty()) {
            this.mExamsTabLayout.setVisibility(View.VISIBLE);
            this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
            this.mExamTabPager.setAdapter(this.mDetailsAdapter);
            this.mExamTabPager.invalidate();
            if (this.mPagerHeader != null)
                ((ViewPager.LayoutParams) this.mPagerHeader.getLayoutParams()).isDecor = true;
            this.mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);
        } else {
            this.mExamsTabLayout.setVisibility(View.GONE);
        }

        if(this.mExamDetailList != null && !this.mExamDetailList.isEmpty()) {
            if (getView()!= null)
                this.mInitializeExamTabNavButtons(getView());
            int pagerPosition = this.mExamTabPager.getCurrentItem();
            if(this.mExamDetailList.size() > pagerPosition)
                this.mExamDetail = this.mExamDetailList.get(pagerPosition);
        }else{
            if(getView() != null)
                this.mInitializeExamTabNavButtons(getView());
            this.mExamDetail = new ProfileExam();
            this.mExamDetail.setId(0);
        }
        requestToUpdateExamSummary();

    }

//    private void mUpdateSubMenuItem(){
//        final View view = getView();
//        if(view ==   null)  return;
//
//        TextView firstSubMenuTV       = (TextView)view.findViewById(R.id.home_widget_textview_first);
//        TextView secondSubMenuTV   = (TextView)view.findViewById(R.id.home_widget_textview_second);
//        TextView thirdSubMenuTV    = (TextView)view.findViewById(R.id.home_widget_textview_third);
//        TextView fourthSubMenuTV          = (TextView)view.findViewById(R.id.home_widget_textview_fourth);
//
//        ImageView firstSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_first);
//        ImageView secondSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_second);
//        ImageView thirdSubMenuIV      = (ImageView)view.findViewById(R.id.home_widget_image_third);
//        ImageView fourthSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_fourth);
//
//
//        boolean  isTuteCompleted = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getString(R.string.INSTITUTES_HOME_TUTE), false);
//            if(!isTuteCompleted) {
//                view.findViewById(R.id.home_tute_image).setVisibility(View.VISIBLE);
//            } else {
//                view.findViewById(R.id.home_tute_image).setVisibility(View.GONE);
//            }
//
//            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
//            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT, 0, .5f);
//            ll.setLayoutParams(lp);
//
//            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);
//            ll2.setLayoutParams(lp);
//
//            LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) (getView().findViewById(R.id.home_widget_third)).getLayoutParams();
//            int marginLeftInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40 , getResources().getDisplayMetrics());
//            int marginRightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15 , getResources().getDisplayMetrics());
//            lp2.setMargins(marginLeftInDp,0,marginRightInDp,0);
//            getView().findViewById(R.id.home_widget_third).setLayoutParams(lp2);
//
//            firstSubMenuIV.setImageResource(R.drawable.ic_institute_reco);
//            secondSubMenuIV.setImageResource(R.drawable.ic_shortlist);
//            thirdSubMenuIV.setImageResource(R.drawable.ic_trending);
//            fourthSubMenuIV.setImageResource(R.drawable.ic_search_college_widget);
//            firstSubMenuIV.setVisibility(View.GONE);
//
//            firstSubMenuTV.setText(getString(R.string.TAG_TAB_RECOMMENDED_COLLEGS));
//            firstSubMenuTV.setVisibility(View.GONE);
//            firstSubMenuTV.setContentDescription("Click to Explore recommended Institutes");
//            secondSubMenuTV.setText(getString(R.string.TAG_TAB_SHORTLISTED_COLLEGS));
//            secondSubMenuTV.setContentDescription("Click to Explore shortlisted Institutes");
//            thirdSubMenuTV.setText(getString(R.string.TAG_TAB_FEATURED_COLLEGS));
//            thirdSubMenuTV.setContentDescription("Click to Explore popular Institutes");
//            fourthSubMenuTV.setText(getString(R.string.TAG_TAB_EXPLORE_COLLEGS));
//            fourthSubMenuTV.setContentDescription("Click to Explore all Institutes");
//
//        try {
//            if(ll2.getChildAt(0)!=null)
//            ll2.getChildAt(0).setVisibility(View.VISIBLE);
//            if(ll2.getChildAt(1)!=null)
//            ll2.getChildAt(1).setVisibility(View.VISIBLE);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        this.mToggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
//
//        updateCollegeCount();
//    }

    private void mToggleView(LinearLayout linearLayout1, LinearLayout linearLayout2, int visibility)
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

    @Override
    public String getEntity() {
        return null;
    }

    public void updateDataForExam() {
        this.mExamTabSelected(EXAM_TAB_POSITION);
    }


    private void mUpdateSubMenuItem(View view){
        if(view ==   null)    return;

        TextView firstSubMenuTV     = (TextView)view.findViewById(R.id.home_widget_textview_first);
        TextView secondSubMenuTV    = (TextView)view.findViewById(R.id.home_widget_textview_second);
        TextView thirdSubMenuTV    = (TextView)view.findViewById(R.id.home_widget_textview_third);
        ImageView firstSubMenuIV    = (ImageView)view.findViewById(R.id.home_widget_image_first);
        ImageView secondSubMenuIV   = (ImageView)view.findViewById(R.id.home_widget_image_second);
        ImageView thirdSubMenuIV   = (ImageView)view.findViewById(R.id.home_widget_image_third);


        view.findViewById(R.id.home_widget_fourth).setVisibility(View.GONE);

        LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) view.findViewById(R.id.home_widget_third).getLayoutParams();
        int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45 , getResources().getDisplayMetrics());
        lp2.setMargins(marginInDp,0,marginInDp,0);
        view.findViewById(R.id.home_widget_third).setLayoutParams(lp2);

        firstSubMenuIV.setImageResource(R.drawable.ic_shortlist);
        secondSubMenuIV.setImageResource(R.drawable.ic_trending);
        thirdSubMenuIV.setImageResource(R.drawable.ic_search_college);
        firstSubMenuTV.setText(getString(R.string.TAG_TAB_SHORTLISTED_COLLEGS));
//        firstSubMenuTV.setContentDescription("Click to chat with your Future mates");
        secondSubMenuTV.setText(getString(R.string.TAG_TAB_FEATURED_COLLEGS));
//        secondSubMenuTV.setContentDescription("Click to ask questions");
        thirdSubMenuTV.setText(getString(R.string.TAG_TAB_EXPLORE_COLLEGS));
//        thirdSubMenuTV.setContentDescription("Click to chat with your Counselor");

//        this.mToggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);

        updateCollegeCount();
    }

}
