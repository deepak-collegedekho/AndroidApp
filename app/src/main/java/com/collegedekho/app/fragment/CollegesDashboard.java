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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
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
    private static String PARAM2 = "param2";

    private int mSelectedTabPosition = 0;
    private int mSelectedSubMenuPosition = 0;
    private CollegesDashboard.OnHomeItemSelectListener mListener;
    private ArrayList<ProfileExam> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ProfileExam mExamDetail;
    private volatile ExamSummary mExamSummary;
    private ViewPager mExamTabPager  = null;
    private PagerTabStrip mPagerHeader = null;
    private boolean isFistTime = false;
    private boolean IS_COLLEGE_TUTE_COMPLETED = true;
    private boolean IS_PREPARE_TUTE_COMPLETED = true;
    private int i = 0 ;
    private int examId;
    private View mExamsTabLayout;

    private TickerView mRecommendedCountTV;
    private TickerView mTrendingCountTV;
    private TickerView mShortlistCountTV;
    private TickerView mExploreCountTV;
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private View mExamSwipeListener;
    private View mHomeWidgetLayout;

    public static CollegesDashboard newInstance() {

        Bundle args = new Bundle();

        CollegesDashboard fragment = new CollegesDashboard();
        fragment.setArguments(args);
        return fragment;
    }

    public static CollegesDashboard newInstance(int tabPosoition,ArrayList<ProfileExam> examList) {
        CollegesDashboard fragment = new CollegesDashboard();
        Bundle args = new Bundle();
        args.putInt(PARAM1, tabPosoition);
        args.putParcelableArrayList(PARAM2, examList);
        fragment.setArguments(args);
        fragment.setArguments(args);
        return fragment;
    }

    public void CollegesDashboard()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.mSelectedTabPosition = args.getInt(PARAM1);
            this.mExamDetailList = args.getParcelableArrayList(PARAM2);
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

        this.mPagerHeader            =   (PagerTabStrip) rootView.findViewById(R.id.exam_pager_header);
        this.mExamTabPager           =   (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        this.mExamsTabLayout         =   rootView.findViewById(R.id.exams_tab_layout);
        CircularImageView mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        this.IS_COLLEGE_TUTE_COMPLETED = sharedPreferences.getBoolean(getString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
        this.IS_PREPARE_TUTE_COMPLETED = sharedPreferences.getBoolean("prepare_tute", false);

        rootView.findViewById(R.id.prepare_tour_guide_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                CollegesDashboard.this.IS_PREPARE_TUTE_COMPLETED = true;
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean("prepare_tute", true).apply();
                updateCollegeCount(CollegesDashboard.this.mSelectedTabPosition);
                //TabFragment.this.getActivity().invalidateOptionsMenu();
            }
        });

        rootView.findViewById(R.id.prep_buddy_tour_guide_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!CollegesDashboard.this.IS_COLLEGE_TUTE_COMPLETED){
                    CollegesDashboard.this.IS_COLLEGE_TUTE_COMPLETED = true;
                    getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.PREP_BUDDY_SCREEN_TUTE), true).apply();
                    updateCollegeCount(CollegesDashboard.this.mSelectedTabPosition);
                }
            }
        });


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

    private void updateCollegeCount(int selectedTabPosition) {

        if(selectedTabPosition == 1 && this.mListener != null ){
            this.mRecommendedCountTV.setVisibility(View.VISIBLE);
            this.mTrendingCountTV.setVisibility(View.VISIBLE);
            this.mShortlistCountTV.setVisibility(View.VISIBLE);
            this.mExploreCountTV.setVisibility(View.VISIBLE);

            this.mListener.onExamTabSelected(this.mExamDetail);

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
        } else {
            this.mRecommendedCountTV.setVisibility(View.GONE);
            this.mTrendingCountTV.setVisibility(View.GONE);
            this.mShortlistCountTV.setVisibility(View.GONE);
            this.mExploreCountTV.setVisibility(View.GONE);
        }
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

    private void mExamTabSelected(int position) {
        if(this.mListener != null && this.mExamDetailList != null && this.mExamDetailList.size() >position) {
            this.mExamDetail = this.mExamDetailList.get(position);
            this.examId = this.mExamDetail.getId();
            updateCollegeCount(this.mSelectedTabPosition);
        }
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
                this.mListener = (OnHomeItemSelectListener)context;
                this.setUserVisibleHint(true);
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

    public void updateExamSummary(ExamSummary examSummary) {
        this.mExamSummary = examSummary;
    }

    public void updateCollegeCountFromVolley(boolean update){
        int updateId = Integer.parseInt(this.mExamSummary.getYearly_exam_id());
        if(this.examId == updateId && update){
            updateCollegeCount(this.mSelectedTabPosition);
        }
    }

    public void updateUserInfo() {
        updateUserProfile(getView(), MainActivity.mProfile);
    }

    private void updateUserProfile(View view, Profile profile){
        if (view == null || profile == null)
            return;

        TextView mProfileName = (TextView) view.findViewById(R.id.user_name);
        TextView mProfileNumber = (TextView) view.findViewById(R.id.user_phone);
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
            case R.id.profile_image:
            case R.id.profile_image_edit_button:
                if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
                    ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
                    return;
                }
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
            default:
                try {
                    this.mSelectedSubMenuPosition = Integer.parseInt((String) view.getTag());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.mSubMenuItemClickListener();
        }
    }

    private void mSubMenuItemClickListener(){
        if(this.mSelectedTabPosition == 1){

            if(this.mSelectedSubMenuPosition == 1) {
                if(this.mExamDetail != null)
                    this.mHomeWidgetSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/", this.mExamDetail.getExam_tag());
                else
                    this.mHomeWidgetSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/",null);
            } else if(this.mSelectedSubMenuPosition == 2) {
                this.mHomeWidgetSelected(Constants.WIDGET_SHORTLIST_INSTITUTES, Constants.BASE_URL + "personalize/shortlistedinstitutes", null);
            } else if(this.mSelectedSubMenuPosition == 3) {
                if(this.mExamDetail != null)
                    this.mHomeWidgetSelected(Constants.CARD_BUZZLIST_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/?action=2", this.mExamDetail.getExam_tag());
                else
                    this.mHomeWidgetSelected(Constants.CARD_BUZZLIST_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/?action=2",null);
            } else if(this.mSelectedSubMenuPosition == 4) {
                if(this.mExamDetail != null)
                    this.mHomeWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", this.mExamDetail.getExam_tag());
                else
                    this.mHomeWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/",null);
            }
        } else if(this.mSelectedTabPosition == 2){
            if(this.mSelectedSubMenuPosition == 1){
                this.mHomeWidgetSelected(Constants.WIDGET_FORUMS, Constants.BASE_URL + "personalize/forums/", null);
            }else if(this.mSelectedSubMenuPosition == 2){
                this.mHomeWidgetSelected(Constants.TAG_LOAD_QNA_QUESTIONS, Constants.BASE_URL+"personalize/qna/", null);
            }
        } else if (this.mSelectedTabPosition == 3){
            if(this.mExamDetail != null) {
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID, "" + this.mExamDetail.getId()).apply();
                if (this.mSelectedSubMenuPosition == 1) {
                    this.mHomeWidgetSelected(Constants.WIDGET_TEST_CALENDAR, Constants.BASE_URL + "yearly-exams/" + this.mExamDetail.getId() + "/calendar/", null);
                } else if (this.mSelectedSubMenuPosition == 2) {
                    this.mHomeWidgetSelected(Constants.WIDGET_SYLLABUS, Constants.BASE_URL + "yearly-exams/" + this.mExamDetail.getId() + "/syllabus/", null);
                } else if (this.mSelectedSubMenuPosition == 3) {
                    this.mHomeWidgetSelected(Constants.TAG_MY_ALERTS, Constants.BASE_URL + "exam-alerts/", this.mExamDetail.getExam_tag());
                }
            }
        } else if (this.mSelectedTabPosition == 4){
            if(this.mSelectedSubMenuPosition == 1){
                this.mHomeWidgetSelected(Constants.WIDGET_NEWS, Constants.BASE_URL+"personalize/news", null);
            }else  if(this.mSelectedSubMenuPosition == 2){
                this.mHomeWidgetSelected(Constants.WIDGET_ARTICES, Constants.BASE_URL+"personalize/articles", null);
            }
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        /*if (isVisibleToUser && this.getActivity() != null)
            ((MainActivity) this.getActivity()).mGetFeed(Constants.TAG_LOAD_FEED, Constants.BASE_URL + "feeds/");*/
    }

    public int getSelectedTab(){
        return this.mSelectedTabPosition;
    }

    public void setSelectedTab(int selectedTabPosition){
        this.mSelectedTabPosition = selectedTabPosition;
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

        /*this.mExamSwipeListener.bringToFront();
        this.mExamSwipeListener.invalidate();

        this.mHomeWidgetLayout.bringToFront();
        this.mHomeWidgetLayout.invalidate();*/

        if (MainActivity.mProfile.getYearly_exams() != null && MainActivity.mProfile.getYearly_exams().size() > 0) {
            this.mExamsTabLayout.setVisibility(View.VISIBLE);
            this.mExamDetailList = MainActivity.mProfile.getYearly_exams();
            this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
            this.mExamTabPager.setAdapter(this.mDetailsAdapter);
            this.mExamTabPager.invalidate();
            if (this.mExamDetailList != null && !this.mExamDetailList.isEmpty()) {
                this.mExamDetail = this.mExamDetailList.get(this.mExamTabPager.getCurrentItem());
            }
            if (this.mPagerHeader != null)
                ((ViewPager.LayoutParams) this.mPagerHeader.getLayoutParams()).isDecor = true;
            if (this.mExamDetailList != null && this.mSelectedTabPosition < this.mExamDetailList.size())
                this.mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);
        } else {
            if (this.mSelectedTabPosition == 3)
                this.mSelectedTabPosition = 1;
            this.mExamsTabLayout.setVisibility(View.GONE);
        }
        MainActivity mainActivity = (MainActivity)getActivity();
        if (mainActivity != null) {
            //mainActivity.currentFragment = this;
            mainActivity.mUpdateTabMenuItem(this.mSelectedTabPosition);
        }

        if(this.mExamDetailList != null && !this.mExamDetailList.isEmpty()) {
            int pagerPosition = this.mExamTabPager.getCurrentItem();
            if(this.mExamDetailList.size() > pagerPosition)
                this.mExamDetail = this.mExamDetailList.get(pagerPosition);
        }else{
            this.mExamDetail = new ProfileExam();
            this.mExamDetail.setId(0);
        }
        this.mUpdateSubMenuItem();
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

        if(this.mSelectedTabPosition == 1){
            this.IS_COLLEGE_TUTE_COMPLETED = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
            //View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
            if(!this.IS_COLLEGE_TUTE_COMPLETED) {
                getActivity().invalidateOptionsMenu();
                view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.VISIBLE);
                //bottomMenu.animate().translationY(bottomMenu.getHeight());
                //bottomMenu.setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
                //bottomMenu.animate().translationY(0);
                //bottomMenu.setVisibility(View.VISIBLE);
            }

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, .5f);
            ll.setLayoutParams(lp);

            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);
            ll2.setLayoutParams(lp);

            LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) (getView().findViewById(R.id.home_widget_third)).getLayoutParams();
            int marginLeftInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40 , getResources().getDisplayMetrics());
            int marginRightInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15 , getResources().getDisplayMetrics());
            lp2.setMargins(marginLeftInDp,0,marginRightInDp,0);
            getView().findViewById(R.id.home_widget_third).setLayoutParams(lp2);

            firstSubMenuIV.setImageResource(R.drawable.ic_institute_reco);
            secondSubMenuIV.setImageResource(R.drawable.ic_shortlist);
            thirdSubMenuIV.setImageResource(R.drawable.ic_trending);
            fourthSubMenuIV.setImageResource(R.drawable.ic_search_college_widget);

            firstSubMenuTV.setText(getString(R.string.TAG_TAB_RECOMMENDED_COLLEGS));
            firstSubMenuTV.setContentDescription("Click to Explore recommended Institutes");
            secondSubMenuTV.setText(getString(R.string.TAG_TAB_SHORTLISTED_COLLEGS));
            secondSubMenuTV.setContentDescription("Click to Explore shortlisted Institutes");
            thirdSubMenuTV.setText(getString(R.string.TAG_TAB_FEATURED_COLLEGS));
            thirdSubMenuTV.setContentDescription("Click to Explore popular Institutes");
            fourthSubMenuTV.setText(getString(R.string.TAG_TAB_EXPLORE_COLLEGS));
            fourthSubMenuTV.setContentDescription("Click to Explore all Institutes");

            ll2.getChildAt(0).setVisibility(View.VISIBLE);
            ll2.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);

        }else if(this.mSelectedTabPosition == 2){
            view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
            view.findViewById(R.id.prepare_tour_guide_image).setVisibility(View.GONE);
            //View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
            //bottomMenu.animate().translationY(0);
            //bottomMenu.setVisibility(View.VISIBLE);

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);
            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);
            ll2.setLayoutParams(lp);

            firstSubMenuIV.setImageResource(R.drawable.ic_chat_bubble_widget);
            secondSubMenuIV.setImageResource(R.drawable.ic_qna);

            firstSubMenuTV.setText("Future Buddies");
            firstSubMenuTV.setContentDescription("Click to chat with your Future mates");
            secondSubMenuTV.setText("Q & A");
            secondSubMenuTV.setContentDescription("Click to ask questions");

            ll2.getChildAt(0).setVisibility(View.VISIBLE);
            ll2.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);

        }else  if(this.mSelectedTabPosition == 3){
            this.IS_PREPARE_TUTE_COMPLETED = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean("prepare_tute", false);
            //View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
            if(!this.IS_PREPARE_TUTE_COMPLETED) {
                getActivity().invalidateOptionsMenu();
                view.findViewById(R.id.prepare_tour_guide_image).setVisibility(View.VISIBLE);
                //bottomMenu.animate().translationY(bottomMenu.getHeight());
                //bottomMenu.setVisibility(View.GONE);
            } else {
                view.findViewById(R.id.prepare_tour_guide_image).setVisibility(View.GONE);
                //bottomMenu.animate().translationY(0);
                //bottomMenu.setVisibility(View.VISIBLE);
            }

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);

            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);

            LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) (getView().findViewById(R.id.home_widget_third)).getLayoutParams();
            int marginInDp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 45 , getResources().getDisplayMetrics());
            lp2.setMargins(marginInDp,0,marginInDp,0);
            getView().findViewById(R.id.home_widget_third).setLayoutParams(lp2);

            firstSubMenuIV.setImageResource(R.drawable.ic_test_calendar);
            secondSubMenuIV.setImageResource(R.drawable.ic_syllabus);
            thirdSubMenuIV.setImageResource(R.drawable.ic_important_dates);

            firstSubMenuTV.setText(getString(R.string.test_calendar_title));
            firstSubMenuTV.setContentDescription("Click to see test preparation calendar");
            secondSubMenuTV.setText(getString(R.string.syllabus_title));
            secondSubMenuTV.setContentDescription("Click to see syllabus for exam");
            thirdSubMenuTV.setText(getString(R.string.important_dates));
            thirdSubMenuTV.setContentDescription("Click to see Important Dates for exam");

            ll2.getChildAt(0).setVisibility(View.VISIBLE);
            ll2.getChildAt(1).setVisibility(View.GONE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.VISIBLE);

        }else  if(this.mSelectedTabPosition == 4){

            view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
            view.findViewById(R.id.prepare_tour_guide_image).setVisibility(View.GONE);
            //View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
            //bottomMenu.animate().translationY(0);
            //bottomMenu.setVisibility(View.VISIBLE);

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);

            LinearLayout ll2 = (LinearLayout)view.findViewById(R.id.home_widget_second_layout);
            ll2.setLayoutParams(lp);

            firstSubMenuIV.setImageResource(R.drawable.ic_news);
            secondSubMenuIV.setImageResource(R.drawable.ic_article);

            firstSubMenuTV.setText(getString(R.string.news_title));
            firstSubMenuTV.setContentDescription("Click to read news");
            secondSubMenuTV.setText(getString(R.string.article_title));
            secondSubMenuTV.setContentDescription("Click to read articles");

            ll2.getChildAt(0).setVisibility(View.VISIBLE);
            ll2.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);
        }
        updateCollegeCount(this.mSelectedTabPosition);
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

    @Override
    public void onPause() {
        super.onPause();
        final MainActivity mainActivity = (MainActivity)getActivity();
        /*if(mainActivity.currentBottomItem != null){
            mainActivity.mUpdateTabMenuItem(-2);
        }*/
        //View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
        //bottomMenu.animate().translationY(bottomMenu.getHeight());
        //bottomMenu.setVisibility(View.GONE);

    }

    @Override
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }

    public interface OnHomeItemSelectListener {

        void onExamTabSelected(ProfileExam tabPosition);
        void onHomeItemSelected(String requestType, String url,String examTag);
        void requestForProfileFragment();
        void onTabStepByStep();
        void onPsychometricTestSelected();
        void onTabPsychometricReport();
    }
}
