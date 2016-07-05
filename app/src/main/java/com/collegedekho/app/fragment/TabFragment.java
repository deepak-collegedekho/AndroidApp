package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import java.util.ArrayList;

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
    private ArrayList<ExamDetail> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ExamDetail mExamDetail;
    private ExamSummary mExamSummary;
    private ViewPager mExamTabPager  = null;
    private PagerTabStrip mPagerHeader = null;
    private boolean isFistTime = false;
    private boolean IS_TUTE_COMPLETED = true;
    private int i = 0 ;
    private View mExamsTabLayout;

    TextView recommended_countTV;
    TextView trending_countTV;
    TextView shortlist_countTV;
    TextView mExplore_countTV;

    public static TabFragment newInstance(int tabPosoition,ArrayList<ExamDetail> examList) {
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

        recommended_countTV =  (TextView)rootView.findViewById(R.id.recommended_count);
        trending_countTV    = (TextView)rootView.findViewById(R.id.trending_count);
        shortlist_countTV   = (TextView)rootView.findViewById(R.id.shortlist_count);
        mExplore_countTV = (TextView)rootView.findViewById(R.id.explore_count);
        mPagerHeader    = (PagerTabStrip) rootView.findViewById(R.id.exam_pager_header);
        mExamTabPager       = (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        mExamsTabLayout     =rootView.findViewById(R.id.exams_tab_layout);
        TextView mProfileName = (TextView) rootView.findViewById(R.id.user_name);
        TextView mProfileNumber = (TextView) rootView.findViewById(R.id.user_phone);
        CircularImageView mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);

        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
        rootView.findViewById(R.id.prep_buddy_tour_guide_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!IS_TUTE_COMPLETED){
                    if(i ==0){
                        i++;
                        v.setBackgroundResource(R.drawable.ic_profile_tute2);
                    } else if(i ==1) {
                        i++;
                        v.setBackgroundResource(R.drawable.ic_profile_tute3);
                    } else if(i ==2) {
                        i++;
                        v.setBackgroundResource(R.drawable.ic_profile_tute4);
                    } else if(i ==3) {
                        i++;
                        v.setBackgroundResource(R.drawable.ic_profile_tute5);
                    } else {
                        v.setVisibility(View.GONE);
                        IS_TUTE_COMPLETED = true;
                        getActivity().invalidateOptionsMenu();
                        getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), true).apply();
                        View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                        bottomMenu.animate().translationY(0);
                        bottomMenu.setVisibility(View.VISIBLE);
                        updateCollegeCount(selectedTabPosition);
                    }
                } else {
                    v.setVisibility(View.GONE);
                    View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                    bottomMenu.animate().translationY(0);
                    bottomMenu.setVisibility(View.VISIBLE);
                    updateCollegeCount(selectedTabPosition);
                }
            }
        });


        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
        if(MainActivity.mProfile != null) {

            String name = MainActivity.mProfile.getName();
            String phone = MainActivity.mProfile.getPhone_no();

            if (name == null || name.isEmpty() || name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())) {
                mProfileName.setText("Name : Anonymous User");
                mProfileName.setVisibility(View.VISIBLE);
            } else {
                String userName = name.substring(0, 1).toUpperCase() + name.substring(1);
                mProfileName.setText("Name : "+userName);
                mProfileName.setVisibility(View.VISIBLE);
            }

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
        }
        if(mExamDetailList == null || mExamDetailList.isEmpty()){
            if(MainActivity.user != null)
                mExamDetailList = MainActivity.user.getUser_exams();
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
                    TabFragment.this.updateCollegeCount(selectedTabPosition);
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
                this.mExamDetail = new ExamDetail();
                this.mExamDetail.setId("0");
                this.mListener.onExamTabSelected(this.mExamDetail);
            }
        }

        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_fourth).setOnClickListener(this);
        rootView.findViewById(R.id.profile_image).setOnClickListener(this);

        if (MainActivity.mProfile.getPsychometric_given() == 1)
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.GONE);

        rootView.findViewById(R.id.btn_tab_psychometric_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTabPsychometricTest();
            }
        });

        if (MainActivity.mProfile.getStep_by_step_given() == 1)
            rootView.findViewById(R.id.btn_tab_step_by_step).setVisibility(View.GONE);

        rootView.findViewById(R.id.btn_tab_step_by_step).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onTabStepByStep();
            }
        });

        return rootView;
    }

    public void updateExamsList(ArrayList<ExamDetail> examsList){
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
            if (MainActivity.user.getUser_exams() != null && MainActivity.user.getUser_exams().size()>0) {
                mExamsTabLayout.setVisibility(View.VISIBLE);
                this.mExamDetailList = MainActivity.user.getUser_exams();
                this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
                mExamTabPager.setAdapter(this.mDetailsAdapter);
                mExamTabPager.invalidate();
                mainActivity.mUpdateTabMenuItem(this.selectedTabPosition,0);
                updateCollegeCount(selectedTabPosition);
                if(mPagerHeader != null)
                    ((ViewPager.LayoutParams) mPagerHeader.getLayoutParams()).isDecor = true;
                if (selectedTabPosition < mExamDetailList.size())
                    mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);
            }else {
                mainActivity.mUpdateTabMenuItem(this.selectedTabPosition,0);
                mExamsTabLayout.setVisibility(View.GONE);
            }
        }
        if(this.mExamDetailList != null && !this.mExamDetailList.isEmpty()) {
            if (this.mListener != null && this.mExamDetailList != null && this.mExamDetailList.size() > 0) {
                this.mExamDetail = this.mExamDetailList.get(mExamTabPager.getCurrentItem());
                this.mListener.onExamTabSelected(mExamDetail);
            }
        }else{

            if(this.mListener != null)
            {
                this.mExamDetail = new ExamDetail();
                this.mExamDetail.setId("0");
                this.mListener.onExamTabSelected(this.mExamDetail);
            }
        }
/*
        if (mExamDetailList.size() >= EXAM_TAB_POSITION)
            mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);*/

        this.mUpdateSubMenuItem();
        if(IS_TUTE_COMPLETED)
        {
            View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
            bottomMenu.animate().translationY(0);
            bottomMenu.setVisibility(View.VISIBLE);
        }

        updateCollegeCount(selectedTabPosition);
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

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view.getId() == R.id.profile_image) {
            ((MainActivity) getActivity()).displayProfileFrragment();
        }
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
            this.mListener.onExamTabSelected(mExamDetail);
            updateCollegeCount(selectedTabPosition);
        }else{
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
            IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
            if(view != null ){
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                if(!IS_TUTE_COMPLETED) {
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

            ll.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);

        }else   if(this.selectedTabPosition == 2){
            IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
            if(view != null ){
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                if(!IS_TUTE_COMPLETED) {
                    view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.VISIBLE);
                    bottomMenu.animate().translationY(bottomMenu.getHeight());
                    bottomMenu.setVisibility(View.GONE);
                } else {
                    view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
                    bottomMenu.animate().translationY(0);
                    bottomMenu.setVisibility(View.VISIBLE);
                }
            }

            if(view != null ){
                view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);

                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);
            }


            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);

            //this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_chat_bubble_widget);
            secondSubMenuIV.setImageResource(R.drawable.ic_qna);

            firstSubMenuTV.setText("Future Buddies");
            firstSubMenuTV.setContentDescription("Your Future mates");
            secondSubMenuTV.setText("Q & A");
            secondSubMenuTV.setContentDescription("FAQ's");

            ll.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);

        }else   if(this.selectedTabPosition == 3){
            IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
            if(view != null ){
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                if(!IS_TUTE_COMPLETED) {
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
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);

            //this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_test_calendar);
            secondSubMenuIV.setImageResource(R.drawable.ic_syllabus);

            firstSubMenuTV.setText("Test Calendar");
            firstSubMenuTV.setContentDescription("Test preparation calendar");
            secondSubMenuTV.setText("Syllabus");
            secondSubMenuTV.setContentDescription("Syllabus for exam");
            thirdSubMenuTV.setText("Important Dates");
            thirdSubMenuTV.setContentDescription("Important Dates");

            ll.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);

        }else   if(this.selectedTabPosition == 4){
            IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PREP_BUDDY_SCREEN_TUTE), false);
            if(view != null ){
                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                if(!IS_TUTE_COMPLETED) {
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
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 0.5f);
            ll.setLayoutParams(lp);

            //this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_news);
            secondSubMenuIV.setImageResource(R.drawable.ic_article);

            firstSubMenuTV.setText("News");
            firstSubMenuTV.setContentDescription("News");
            secondSubMenuTV.setText("Articles");
            secondSubMenuTV.setContentDescription("Articles");

            ll.getChildAt(1).setVisibility(View.VISIBLE);

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
            view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);
        }
        updateCollegeCount(selectedTabPosition);
    }

    private void updateCollegeCount(int selectedTabPosition) {

        if(selectedTabPosition == 1 && mListener != null ){
            recommended_countTV.setVisibility(View.VISIBLE);
            trending_countTV.setVisibility(View.VISIBLE);
            shortlist_countTV.setVisibility(View.VISIBLE);
            mExplore_countTV.setVisibility(View.VISIBLE);

            int recommended = (this.mExamSummary != null) ? this.mExamSummary.getRecommended_count() : 0;
            int shortlist = (this.mExamSummary != null) ? this.mExamSummary.getShortlist_count() : 0;
            int trending = (this.mExamSummary != null) ? this.mExamSummary.getBuzzlist_count() : 0;
            int explore = (this.mExamSummary != null) ? this.mExamSummary.getBackup_count() : 0;

            mListener.onExamTabSelected(this.mExamDetail);

            Utils.SetCounterAnimation(recommended_countTV, recommended, "" , "", Constants.ANIM_SHORT_DURATION);
            recommended_countTV.setContentDescription(String.valueOf(recommended));

            Utils.SetCounterAnimation(shortlist_countTV, shortlist, "" , "", Constants.ANIM_SHORT_DURATION);
            shortlist_countTV.setContentDescription(String.valueOf(shortlist));

            Utils.SetCounterAnimation(trending_countTV, trending, "" , "", Constants.ANIM_SHORT_DURATION);
            trending_countTV.setContentDescription(String.valueOf(trending));

            Utils.SetCounterAnimation(mExplore_countTV, explore, "" , "", Constants.ANIM_SHORT_DURATION);
            mExplore_countTV.setContentDescription(String.valueOf(explore));

//            important_dateTV.setText(""+this.mExamSummary.getNext_important_date());
        } else {
            recommended_countTV.setVisibility(View.GONE);
            trending_countTV.setVisibility(View.GONE);
            shortlist_countTV.setVisibility(View.GONE);
            mExplore_countTV.setVisibility(View.GONE);
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
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID, mExamDetail.getId()).apply();
                }
            } else if(selectedSubMenuPosition == 2) {
                if (this.mExamDetail != null) {
                    this.mHomeWidgetSelected(Constants.WIDGET_SYLLABUS , Constants.BASE_URL + "yearly-exams/" + mExamDetail.getId() + "/syllabus/", null);
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID,  mExamDetail.getId()).commit();
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

    public void updateTabFragment(int tabPosition){
        this.selectedTabPosition = tabPosition;
        mUpdateSubMenuItem();
    }

    public void updateExamSummary(ExamSummary examSummary) {
        View view = getView();
        this.mExamSummary = examSummary;
        if(view == null || examSummary == null)
            return;

         CircularProgressBar profileCompleted =  (CircularProgressBar) view.findViewById(R.id.profile_image_circular_progressbar);
//        updateCollegeCount(selectedTabPosition);
        //TODO:: showing progress as a profile circle
        //if(examSummary.getSyllabus_covered() ==0)
            profileCompleted.setProgress(100);
        //else
           // profileCompleted.setProgress(examSummary.getSyllabus_covered());
    }

    public void updateCollegeCountFromVolley(boolean update){
        if(update) {
            mExamSummary.getBackup_count();
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
    public  interface OnHomeItemSelectListener {

        void onExamTabSelected(ExamDetail tabPosition);
        void onHomeItemSelected(String requestType, String url,String examTag);
        void onTabStepByStep();
        void onTabPsychometricTest();
    }

}