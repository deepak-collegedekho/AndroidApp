package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
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
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.listener.DashBoardItemListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 28/11/16.
 */

public class PrepareDashboard extends BaseFragment {
    private static String PARAM1 = "param1";

    private int mSelectedSubMenuPosition = 0;
    protected  static int EXAM_TAB_POSITION =0;
    private DashBoardItemListener mListener;
    private ArrayList<ProfileExam> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ProfileExam mExamDetail;
    private ViewPager mExamTabPager  = null;
    private PagerTabStrip mPagerHeader = null;
    private View mExamsTabLayout;

    private ImageView mLeftButton;
    private ImageView mRightButton;

    public static PrepareDashboard newInstance() {
         return new PrepareDashboard();
    }

    public static PrepareDashboard newInstance(ArrayList<ProfileExam> examList) {
        PrepareDashboard fragment = new PrepareDashboard();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM1, examList);
        fragment.setArguments(args);
        return fragment;
    }

    public void PrepareDashboard() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.mExamDetailList = args.getParcelableArrayList(PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);
        this.mPagerHeader        =   (PagerTabStrip) rootView.findViewById(R.id.exam_pager_header);
        this.mExamTabPager       =   (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        this.mExamsTabLayout     =   rootView.findViewById(R.id.exams_tab_layout);
        View widgetLayout        =   rootView.findViewById(R.id.include_layout_home_widget);
        TextView emptyLayout     =   (TextView)rootView.findViewById(R.id.empty_layout);

        updateUserProfile(rootView, MainActivity.mProfile);
        if (MainActivity.mProfile != null ) {
            this.mExamDetailList = MainActivity.mProfile.getYearly_exams();
            if (this.mExamDetailList != null && this.mExamDetailList.size() > 0) {
                this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
                this.mExamTabPager.setAdapter(this.mDetailsAdapter);
                if (this.mPagerHeader != null) {
                    ((ViewPager.LayoutParams) this.mPagerHeader.getLayoutParams()).isDecor = true;
                }
                this.mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);
            }
        }

        if(this.mExamDetailList != null && !this.mExamDetailList.isEmpty()) {
            this.mExamTabPager.setVisibility(View.VISIBLE);
            this.mExamsTabLayout.setVisibility(View.VISIBLE);
            widgetLayout.setVisibility(View.VISIBLE);
            emptyLayout.setVisibility(View.GONE);
            int pagerPosition = this.mExamTabPager.getCurrentItem();
            if(this.mExamDetailList.size() > pagerPosition)
                this.mExamDetail = this.mExamDetailList.get(pagerPosition);
        }else{
            this.mExamTabPager.setVisibility(View.GONE);
            this.mExamsTabLayout.setVisibility(View.GONE);
            widgetLayout.setVisibility(View.GONE);
            emptyLayout.setVisibility(View.VISIBLE);
            emptyLayout.setText("You don't have any syllabus for exams");
            this.mExamDetail = new ProfileExam();
            this.mExamDetail.setId(0);
        }
        this.mUpdateSubMenuItem(rootView);

        this.mExamTabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("","");
            }

            @Override
            public void onPageSelected(int position) {
                PrepareDashboard.EXAM_TAB_POSITION = position;
                PrepareDashboard.this.mExamTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("","");
            }
        });

        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.profile_image).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_step_by_step).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_psychometric_test).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_psychometric_report).setOnClickListener(this);
        ImageView tuteImage = (ImageView)rootView.findViewById(R.id.home_tute_image);
        tuteImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_tute7));
        tuteImage.setOnClickListener(this);

        rootView.findViewById(R.id.include_image_layout).findViewById(R.id.profile_image_edit_button).setOnClickListener(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        String psychometricResults = sharedPreferences.getString("psychometric_report", null);

        if (MainActivity.mProfile != null && MainActivity.mProfile.getPsychometric_given() == 1 && psychometricResults != null) {
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.GONE);
            rootView.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.VISIBLE);
        } else {
            rootView.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.GONE);
        }

        this.mInitializeExamTabNavButtons(rootView);

        return rootView;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            if (context instanceof MainActivity) {
                this.mListener = (DashBoardItemListener)context;
            }
        } catch (ClassCastException e){
            throw  new ClassCastException(context.toString()
                    +"must implement OnHomeItemSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
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

        CircularImageView mProfileImage = (CircularImageView)view.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
        String image = profile.getImage();
        if (image != null && ! image.isEmpty()) {
            mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
            mProfileImage.setVisibility(View.VISIBLE);
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
                if(isAdded())
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.PREPARE_HOME_TUTE), true).apply();
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

    @Override
    public void onResume() {
        super.onResume();

        this.mExamDetailList = MainActivity.mProfile.getYearly_exams();

        if (getView()!= null)
            this.mInitializeExamTabNavButtons(getView());
    }

    private void mSubMenuItemClickListener(){
        if(this.mExamDetail != null) {
            getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID, "" + this.mExamDetail.getId()).apply();
           if (this.mSelectedSubMenuPosition == 1) {
                this.mHomeWidgetSelected(Constants.WIDGET_SYLLABUS, Constants.BASE_URL + "yearly-exams/" + this.mExamDetail.getId() + "/syllabus/", null);
            } else if (this.mSelectedSubMenuPosition == 2) {
                this.mHomeWidgetSelected(Constants.TAG_MY_ALERTS, Constants.BASE_URL + "exam-alerts/", this.mExamDetail.getExam_tag());
            }
        }
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
                    int currentPosition = PrepareDashboard.this.mExamTabPager.getCurrentItem();
                    if (currentPosition > 0)
                        PrepareDashboard.this.mExamTabPager.setCurrentItem(currentPosition - 1);
                    else
                    {
                        PrepareDashboard.this.mLeftButton.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        PrepareDashboard.this.mLeftButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
                    }
                }
            });

            this.mRightButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int currentPosition = PrepareDashboard.this.mExamTabPager.getCurrentItem();
                    if (PrepareDashboard.this.mExamDetailList.size() - 1 > currentPosition)
                        PrepareDashboard.this.mExamTabPager.setCurrentItem(currentPosition + 1);
                    else
                    {
                        PrepareDashboard.this.mRightButton.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
                        PrepareDashboard.this.mRightButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
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

    private void mHomeWidgetSelected(String requestType, String url, String tag){
        if(this.mListener != null)
            this.mListener.onHomeItemSelected(requestType, url,tag);
    }


    private void mExamTabSelected(int position) {
        if(this.mListener != null && this.mExamDetailList != null && this.mExamDetailList.size() >position) {
            this.mExamDetail = this.mExamDetailList.get(position);
        }
    }

    public void updateExamsList(ArrayList<ProfileExam> examsList){
        if(examsList == null)
            return;
        this.mExamDetailList=examsList;
        this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
        this.mExamTabPager.setAdapter(this.mDetailsAdapter);
        if(this.mExamDetailList != null && !this.mExamDetailList.isEmpty()){
            this.mExamDetail = this.mExamDetailList.get(this.mExamTabPager.getCurrentItem());
        }
    }


    private void mUpdateSubMenuItem(View view) {
        if (view == null) return;

        TextView firstSubMenuTV = (TextView) view.findViewById(R.id.home_widget_textview_first);
        TextView secondSubMenuTV = (TextView) view.findViewById(R.id.home_widget_textview_second);

        ImageView firstSubMenuIV = (ImageView) view.findViewById(R.id.home_widget_image_first);
        ImageView secondSubMenuIV = (ImageView) view.findViewById(R.id.home_widget_image_second);

        boolean IsPrepareTuteCompleted = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).getBoolean(getString(R.string.PREPARE_HOME_TUTE), false);
         if (!IsPrepareTuteCompleted) {
            view.findViewById(R.id.home_tute_image).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.home_tute_image).setVisibility(View.GONE);
        }
        view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);

        firstSubMenuIV.setImageResource(R.drawable.ic_syllabus);
        secondSubMenuIV.setImageResource(R.drawable.ic_important_dates);
        firstSubMenuTV.setText(getString(R.string.syllabus_title));
        firstSubMenuTV.setContentDescription("Click to see syllabus for exam");
        secondSubMenuTV.setText(getString(R.string.important_dates));
        secondSubMenuTV.setContentDescription("Click to see Important Dates for exam");

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
    public void show() {

    }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }

}
