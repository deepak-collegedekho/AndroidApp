package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.listener.OnSwipeTouchListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import java.util.ArrayList;

/**
 * Created by harshvardhan on 28/11/16.
 */

public class InteractionDashboard extends BaseFragment {
    private static String PARAM1 = "param1";
    private static String PARAM2 = "param2";

    private int mSelectedSubMenuPosition = 0;
    private InteractionDashboard.OnHomeItemSelectListener mListener;
    private ArrayList<ProfileExam> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private ProfileExam mExamDetail;
    private ViewPager mExamTabPager = null;
    private PagerTabStrip mPagerHeader = null;
    private View mExamsTabLayout;
    protected  static int EXAM_TAB_POSITION =0;

    public static InteractionDashboard newInstance() {
        return new InteractionDashboard();
    }

    public static InteractionDashboard newInstance(int tabPosoition, ArrayList<ProfileExam> examList) {
        InteractionDashboard fragment = new InteractionDashboard();
        Bundle args = new Bundle();
        args.putInt(PARAM1, tabPosoition);
        args.putParcelableArrayList(PARAM2, examList);
        fragment.setArguments(args);
        fragment.setArguments(args);
        return fragment;
    }

    public void InteractionDashboard() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            this.mExamDetailList = args.getParcelableArrayList(PARAM2);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tab, container, false);

        this.mPagerHeader = (PagerTabStrip) rootView.findViewById(R.id.exam_pager_header);
        this.mExamTabPager = (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        this.mExamsTabLayout = rootView.findViewById(R.id.exams_tab_layout);

        rootView.findViewById(R.id.prep_buddy_tour_guide_image).setOnClickListener(this);

        updateUserProfile(rootView, MainActivity.mProfile);
        if (MainActivity.mProfile != null) {
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

        if (this.mExamDetailList != null && !this.mExamDetailList.isEmpty()) {
            this.mExamTabPager.setVisibility(View.VISIBLE);
            this.mExamsTabLayout.setVisibility(View.VISIBLE);
            int pagerPosition = this.mExamTabPager.getCurrentItem();
            if (this.mExamDetailList.size() > pagerPosition)
                this.mExamDetail = this.mExamDetailList.get(pagerPosition);
        } else {
            this.mExamTabPager.setVisibility(View.GONE);
            this.mExamsTabLayout.setVisibility(View.GONE);
            this.mExamDetail = new ProfileExam();
            this.mExamDetail.setId(0);
        }
        this.mUpdateSubMenuItem(rootView);

        this.mExamTabPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("", "");
            }

            @Override
            public void onPageSelected(int position) {
                InteractionDashboard.EXAM_TAB_POSITION = position;
                InteractionDashboard.this.mExamTabSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("", "");
            }
        });

        rootView.findViewById(R.id.home_widget_first).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_second).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_third).setOnClickListener(this);
        rootView.findViewById(R.id.home_widget_fourth).setOnClickListener(this);
        rootView.findViewById(R.id.profile_image).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_step_by_step).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_psychometric_test).setOnClickListener(this);
        rootView.findViewById(R.id.btn_tab_psychometric_report).setOnClickListener(this);

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

        return rootView;
    }

    private void mExamTabSelected(int position) {
        if (this.mListener != null && this.mExamDetailList != null && this.mExamDetailList.size() > position) {
            this.mExamDetail = this.mExamDetailList.get(position);
        }
    }

    private void mHomeWidgetSelected(String requestType, String url, String tag) {
        if (this.mListener != null)
            this.mListener.onHomeItemSelected(requestType, url, tag);
    }

    OnSwipeTouchListener onSwipeTouchListener = new OnSwipeTouchListener(getActivity()) {
        @Override
        public void onSwipeLeft() {
            int currentPosition = InteractionDashboard.this.mExamTabPager.getCurrentItem();
            if (InteractionDashboard.this.mExamDetailList.size() - 1 >= currentPosition)
                InteractionDashboard.this.mExamTabPager.setCurrentItem(currentPosition + 1);
        }

        @Override
        public void onSwipeRight() {
            super.onSwipeRight();

            int currentPosition = InteractionDashboard.this.mExamTabPager.getCurrentItem();
            if (currentPosition > 0)
                InteractionDashboard.this.mExamTabPager.setCurrentItem(currentPosition - 1);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof MainActivity) {
                this.mListener = (OnHomeItemSelectListener) context;
                this.setUserVisibleHint(true);
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + "must implement OnHomeItemSelectListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    private void updateUserProfile(View view, Profile profile) {
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

        CircularImageView mProfileImage = (CircularImageView) view.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);
        String image = profile.getImage();
        if (image != null && !image.isEmpty()) {
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
        switch (view.getId()) {
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
            case R.id.prep_buddy_tour_guide_image:
                view.setVisibility(View.GONE);
                if(isAdded())
                    getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.PREP_BUDDY_HOME_TUTE), true).apply();

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

    private void mSubMenuItemClickListener() {
        if (this.mSelectedSubMenuPosition == 1) {
            this.mHomeWidgetSelected(Constants.WIDGET_FORUMS, Constants.BASE_URL + "personalize/forums/", null);
        } else if (this.mSelectedSubMenuPosition == 2) {
            this.mHomeWidgetSelected(Constants.TAG_LOAD_QNA_QUESTIONS, Constants.BASE_URL + "personalize/qna/", null);
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


    private void mUpdateSubMenuItem(View view){
        if(view ==   null)    return;

        TextView firstSubMenuTV       = (TextView)view.findViewById(R.id.home_widget_textview_first);
        TextView secondSubMenuTV   = (TextView)view.findViewById(R.id.home_widget_textview_second);
        ImageView firstSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_first);
        ImageView secondSubMenuIV     = (ImageView)view.findViewById(R.id.home_widget_image_second);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        boolean IS_COLLEGE_TUTE_COMPLETED = sharedPreferences.getBoolean(getString(R.string.PREP_BUDDY_HOME_TUTE), false);
        if(!IS_COLLEGE_TUTE_COMPLETED) {
            getActivity().invalidateOptionsMenu();
            view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.prep_buddy_tour_guide_image).setVisibility(View.GONE);
        }
        view.findViewById(R.id.home_widget_second_layout).setVisibility(View.GONE);

        firstSubMenuIV.setImageResource(R.drawable.ic_chat_bubble_widget);
        secondSubMenuIV.setImageResource(R.drawable.ic_qna);
        firstSubMenuTV.setText("Future Buddies");
        firstSubMenuTV.setContentDescription("Click to chat with your Future mates");
        secondSubMenuTV.setText("Q & A");
        secondSubMenuTV.setContentDescription("Click to ask questions");


    }

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
    public void show() {  }

    @Override
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {  }

    public interface OnHomeItemSelectListener {

        void onExamTabSelected(ProfileExam tabPosition);
        void onHomeItemSelected(String requestType, String url,String examTag);
        void requestForProfileFragment();
        void onTabStepByStep();
        void onPsychometricTestSelected();
        void onTabPsychometricReport();
    }
}
