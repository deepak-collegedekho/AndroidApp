package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.display.ZoomPageTransformer;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ExamSummary;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
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
    private ViewPager mExamTabPager  = null;
    private boolean isFistTime = false;
    private boolean IS_TUTE_COMPLETED = true;
private View mExamsTabLayout;
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

        this.mExamTabPager = (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        mExamsTabLayout=rootView.findViewById(R.id.exams_tab_layout);
        //this.mExamTabPager.setPageTransformer(true, new ZoomPageTransformer());
        TextView mProfileName = (TextView) rootView.findViewById(R.id.user_name);
        TextView mStreamName = (TextView) rootView.findViewById(R.id.user_stream);
        CircularImageView mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);

        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
        if(MainActivity.user != null) {

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
            if (image != null && ! image.isEmpty()) {
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
                mProfileImage.setVisibility(View.VISIBLE);
            }
            String streamName = MainActivity.user.getStream_name();
            if(streamName != null && !streamName.isEmpty()){
                mStreamName.setVisibility(View.VISIBLE);
                mStreamName.setText(streamName);
            }
            else{
                mStreamName.setVisibility(View.GONE);
            }
        }

        if(this.mExamDetailList != null && this.mExamDetailList.size() > 0) {
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

           rootView.findViewById(R.id.exam_swipe_listener_layout).setOnTouchListener(onSwipeTouchListener);
           rootView.findViewById(R.id.include_layout_home_widget).setOnTouchListener(onSwipeTouchListener);

            if(this.isFistTime) {
                this.isFistTime = false;
                int currentPosition = mExamTabPager.getCurrentItem();
                mExamTabSelected(currentPosition);
            }
            mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);
        }
        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_fourth).setOnClickListener(this);

        rootView.findViewById(R.id.prep_buddy_tour_guide_image).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                v.setVisibility(View.GONE);
                IS_TUTE_COMPLETED = true;
                getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(Constants.PREP_BUDDY_SCREEN_TUTE, true).apply();

                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);


                return true;
            }
        });


        return rootView;
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
                mainActivity.mUpdateTabMenuItem(this.selectedTabPosition);
                if (selectedTabPosition < mExamDetailList.size())
                    mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);
            }else {
                mExamsTabLayout.setVisibility(View.GONE);
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

    }

    @Override
    public void onPause() {
        super.onPause();

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
    public void onClick(View view) {
        super.onClick(view);
        try{
        this.selectedSubMenuPosition = Integer.parseInt((String)view.getTag());
        }catch (Exception e){
            e.printStackTrace();
        }

        this.mSubMenuItemClickListener();
        this.mUpdateSubMenuItem();
    }

    private void mExamTabSelected(int position) {
        if(this.mListener != null && this.mExamDetailList != null && this.mExamDetailList.size() >position) {
            this.mExamDetail = this.mExamDetailList.get(position);
            this.mListener.onExamTabSelected(mExamDetail);
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
            IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(Constants.PREP_BUDDY_SCREEN_TUTE, false);
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
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1.0f);
            ll.setLayoutParams(lp);

            //this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_test_calendar);
            secondSubMenuIV.setImageResource(R.drawable.ic_syllabus);
            thirdSubMenuIV.setImageResource(R.drawable.ic_challenges);
            fourthSubMenuIV.setImageResource(R.drawable.ic_prep_path);

            firstSubMenuTV.setText("Test Calendar");
            secondSubMenuTV.setText("Syllabus");
            thirdSubMenuTV.setText("Challenges");
            fourthSubMenuTV.setText("Prep Path");

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);

        }else   if(this.selectedTabPosition == 2){
            if(view != null ){
                view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);

                View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);
            }

            LinearLayout ll = (LinearLayout)view.findViewById(R.id.home_widget_first_layout);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, .5f);
            ll.setLayoutParams(lp);

            //this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.GONE);

            firstSubMenuIV.setImageResource(R.drawable.ic_institute);
            secondSubMenuIV.setImageResource(R.drawable.ic_news);
            thirdSubMenuIV.setImageResource(R.drawable.ic_article);
            fourthSubMenuIV.setImageResource(R.drawable.ic_qna);

            firstSubMenuTV.setText("Institutes");
            secondSubMenuTV.setText("News");
            thirdSubMenuTV.setText("Article");
            fourthSubMenuTV.setText("Qna");

            this.mtoggleView(ll, (LinearLayout) view.findViewById(R.id.home_widget_second_layout), View.VISIBLE);
        }else   if(this.selectedTabPosition == 3){

        }else   if(this.selectedTabPosition == 4){
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

            if(selectedSubMenuPosition == 2) {
                if (this.mExamDetail != null) {
                    this.mHomeWidgetSelected(Constants.WIDGET_SYLLABUS , Constants.BASE_URL + "yearly-exams/" + mExamDetail.getId() + "/syllabus/", null);
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID,  mExamDetail.getId()).commit();

                }
            } else if(selectedSubMenuPosition == 1) {
                if (this.mExamDetail != null) {
                    this.mHomeWidgetSelected(Constants.WIDGET_TEST_CALENDAR, Constants.BASE_URL + "yearly-exams/" + mExamDetail.getId() + "/calendar/", null);
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID, mExamDetail.getId()).commit();
                }
            }else
                Toast.makeText(getActivity().getApplicationContext(), "Coming soon..", Toast.LENGTH_LONG).show();
        }
        else if(selectedTabPosition == 2){
             if(selectedSubMenuPosition == 1){
                 if(this.mExamDetail != null)
                 this.mHomeWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL+"personalize/institutes/",this.mExamDetail.getExam_tag());
                 else
                 this.mHomeWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL+"personalize/institutes/", null);
             }else  if(selectedSubMenuPosition == 2){
                 this.mHomeWidgetSelected(Constants.WIDGET_NEWS, Constants.BASE_URL+"personalize/news", null);
             }else  if(selectedSubMenuPosition == 3){
                 this.mHomeWidgetSelected(Constants.WIDGET_ARTICES, Constants.BASE_URL+"personalize/articles", null);
             }else  if(selectedSubMenuPosition == 4){
                 this.mHomeWidgetSelected(Constants.TAG_LOAD_QNA_QUESTIONS, Constants.BASE_URL+"personalize/qna", null);
             }
        }
    }

    public void updateTabFragment(int tabPosition){
        this.selectedTabPosition = tabPosition;
        mUpdateSubMenuItem();
    }

    public void updateExamSummary(ExamSummary examSummary) {
        View view = getView();

        if(view == null || examSummary == null)
            return;

         CircularProgressBar profileCompleted =  (CircularProgressBar) view.findViewById(R.id.profile_image_circular_progressbar);

        //TODO:: showing progress as a profile circle
        //if(examSummary.getSyllabus_covered() ==0)
            profileCompleted.setProgress(100);
        //else
           // profileCompleted.setProgress(examSummary.getSyllabus_covered());
    }

    public void updateExamsList(ArrayList<ExamDetail>examsList){
        this.mExamDetailList=examsList;
        this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
        mExamTabPager.setAdapter(this.mDetailsAdapter);
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
    }

}