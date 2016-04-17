package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;

import java.util.ArrayList;

/**
 * Created by sureshsaini on 27/11/15.
 */
public class ProfileFragment extends BaseFragment
        {

    private final String TAG = "profile Frgament";
    private static String PARAM1 = "param1";
    private ArrayList<ExamDetail> mExamDetailList;
    private ExamDetailAdapter mDetailsAdapter;
    private TextView mProfileName;
    private TextView mStreamName;
    private CircularImageView mProfileImage;
    private OnTabSelectListener mListener;
    private ExamDetail mExamDetail; // detail is needs in tabs to get id of exams
    private ExamSummary mExamSummary;  // exam summary gives info about the colleges of user
    private ViewPager mExamTabPager  = null;
    private boolean IS_TUTE_COMPLETED = true;
    private int i = 0;
    private static boolean IS_COMING_FROM_ON_CREATE = false;
    private static String SavedExamSummary = "saved_exam_summary";

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

        Utils.RegisterBroadcastReceiver(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        IS_TUTE_COMPLETED = getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PROFILE_SCREEN_TUTE), false);

        mProfileName  = (TextView)rootView.findViewById(R.id.user_name);
        mStreamName   = (TextView)rootView.findViewById(R.id.user_profile_stream);
        mExamTabPager = (ViewPager) rootView.findViewById(R.id.exam_detail_pager);
        mProfileImage = (CircularImageView)rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        if(this.mExamDetailList != null && this.mExamDetailList.size() > 0) {

            rootView.findViewById(R.id.profile_syllabus_statusLL).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.important_date_layout_RL).setVisibility(View.VISIBLE);
            this.mExamTabPager.setVisibility(View.VISIBLE);
            this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
            this.mExamTabPager.setAdapter(this.mDetailsAdapter);

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
            rootView.findViewById(R.id.pager_strip).setVisibility(View.VISIBLE);

        }else{

            if(this.mListener != null)
            {
                this.mExamDetail = new ExamDetail();
                this.mExamDetail.setId("0");
                this.mListener.onExamTabSelected(this.mExamDetail);
            }

            rootView.findViewById(R.id.pager_strip).setVisibility(View.GONE);
            rootView.findViewById(R.id.prep_buddies).setVisibility(View.GONE);
            rootView.findViewById(R.id.profile_syllabus_statusLL).setVisibility(View.GONE);
            rootView.findViewById(R.id.important_date_layout_RL).setVisibility(View.GONE);
            rootView.findViewById(R.id.backup_colleges_layout_RL).setVisibility(View.GONE);
        }

        rootView.findViewById(R.id.profile_guide_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(i ==0){
                    i++;
                    rootView.findViewById(R.id.profile_guide_image).setBackgroundResource(R.drawable.ic_profile_tute2);
                }else if(i ==1) {
                    i++;
                    //v.setBackgroundResource(R.drawable.ic_profile_tute3);
                    rootView.findViewById(R.id.profile_guide_image).setBackgroundResource(R.drawable.ic_profile_tute3);
                }
                else if(i ==2) {
                    i++;
                    v.setBackgroundResource(R.drawable.ic_profile_tute4);
                }
                else if(i ==3) {
                    i++;
                    v.setBackgroundResource(R.drawable.ic_profile_tute5);
                }else {
                    v.setVisibility(View.GONE);
                    IS_TUTE_COMPLETED = true;
                    //getActivity().findViewById(R.id.bottom_tab_layout).setVisibility(View.VISIBLE);
                    View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
                    bottomMenu.animate().translationY(0);
                    bottomMenu.setVisibility(View.VISIBLE);
                    getActivity().invalidateOptionsMenu();
                    getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putBoolean(MainActivity.getResourceString(R.string.PROFILE_SCREEN_TUTE), true).apply();
                }
            }
        });

        rootView.findViewById(R.id.backup_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.wishList_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.recommended_colleges_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.important_date_layout_RL).setOnClickListener(this);
        rootView.findViewById(R.id.profile_syllabus_statusLL).setOnClickListener(this);

/*
        if (savedInstanceState != null)
        {
            try {
                ExamSummary examSummary = JSON.std.beanFrom(ExamSummary.class, savedInstanceState.get(ProfileFragment.SavedExamSummary).toString());

                if (examSummary != null)
                    this.updateExamSummary(examSummary);

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
*/
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        Constants.READY_TO_CLOSE = true;
        View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
        bottomMenu.animate().translationY(bottomMenu.getHeight());
        bottomMenu.setVisibility(View.GONE);
    }

    @Override
    public void onDestroy() {
        Utils.UnregisterReceiver(this.getActivity());
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            getActivity().invalidateOptionsMenu();
        }catch (Exception e){

        }
        View view =  getView();
        if(view != null ){
            IS_TUTE_COMPLETED= getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).getBoolean(MainActivity.getResourceString(R.string.PROFILE_SCREEN_TUTE), false);
            View bottomMenu = getActivity().findViewById(R.id.bottom_tab_layout);
            if(!IS_TUTE_COMPLETED) {

                bottomMenu.animate().translationY(bottomMenu.getHeight());
                bottomMenu.setVisibility(View.GONE);

                view.findViewById(R.id.profile_guide_image).setVisibility(View.VISIBLE);
            }else {
                bottomMenu.animate().translationY(0);
                bottomMenu.setVisibility(View.VISIBLE);

                view.findViewById(R.id.profile_guide_image).setVisibility(View.GONE);
            }
        }

        Constants.READY_TO_CLOSE = false;

        if(MainActivity.user != null)
        {
            String name = MainActivity.user.getName();
            if(name!=null && name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase()))
            {
                if(MainActivity.user.profileData[0] != null && !MainActivity.user.profileData[0].equalsIgnoreCase(Constants.ANONYMOUS_USER))
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

            String streamName = MainActivity.user.getStream_name();
            if(streamName != null && !streamName.isEmpty()){
                mStreamName.setVisibility(View.VISIBLE);
                mStreamName.setText(streamName);
            }
            else{
                mStreamName.setVisibility(View.GONE);
            }
        }

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
            mainActivity.mUpdateTabMenuItem(-1);
        }

//        if (Utils.isScreenGotOff() == true)
//        {
//            Utils.setScreenGotOff(false);
//            return;
//        }

        updateExamSummaryHandler.postDelayed(updateExamSummaryRunnable,300);
        if(((MainActivity)getActivity()).isReloadProfile && this.mListener!=null){
            ((MainActivity)getActivity()).isReloadProfile=false;
            this.mExamDetailList=MainActivity.user.getUser_exams();
//            mListener.onReloadProfile();
//            getActivity().getSupportFragmentManager().beginTransaction().detach(this).attach(this).commit();
            updateUserProfile(this.mExamDetailList);
        }
    }

public void updateUserProfile(ArrayList<ExamDetail> userExamsList){
    this.mExamDetailList=userExamsList;
    View rootView=getView();
    if(rootView==null){
        return;
    }
    if(this.mExamDetailList != null && this.mExamDetailList.size() > 0) {
        rootView.findViewById(R.id.profile_syllabus_statusLL).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.important_date_layout_RL).setVisibility(View.VISIBLE);
        this.mExamTabPager.setVisibility(View.VISIBLE);
        this.mDetailsAdapter = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
        this.mExamTabPager.setAdapter(this.mDetailsAdapter);
        rootView.findViewById(R.id.check_gesture).setOnTouchListener(onSwipeTouchListener);
        rootView.findViewById(R.id.include_layout_profile_widget).setOnTouchListener(onSwipeTouchListener);
        rootView.findViewById(R.id.pager_strip).setVisibility(View.VISIBLE);

    }else{

        if(this.mListener != null)
        {
            this.mExamDetail = new ExamDetail();
            this.mExamDetail.setId("0");
            this.mListener.onExamTabSelected(this.mExamDetail);
        }

        rootView.findViewById(R.id.pager_strip).setVisibility(View.GONE);
        rootView.findViewById(R.id.prep_buddies).setVisibility(View.GONE);
        rootView.findViewById(R.id.profile_syllabus_statusLL).setVisibility(View.GONE);
        rootView.findViewById(R.id.important_date_layout_RL).setVisibility(View.GONE);
        rootView.findViewById(R.id.backup_colleges_layout_RL).setVisibility(View.GONE);
    }

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
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId())
        {
            case R.id.profile_syllabus_statusLL:
                if(this.mExamDetail != null)
                    this.mProfileWidgetSelected(Constants.WIDGET_SYLLABUS, Constants.BASE_URL + "yearly-exams/"+ this.mExamDetail.getId()+"/syllabus/",null);
                this.getActivity().getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID,  mExamDetail.getId()).commit();
                break;
            case R.id.backup_colleges_layout_RL:
               if(this.mExamDetail != null)
                   this.mProfileWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/", this.mExamDetail.getExam_tag());
               else
                   this.mProfileWidgetSelected(Constants.WIDGET_INSTITUTES, Constants.BASE_URL + "personalize/institutes/",null);
               break;
            case R.id.wishList_colleges_layout_RL:
                this.mProfileWidgetSelected(Constants.WIDGET_SHORTLIST_INSTITUTES, Constants.BASE_URL + "personalize/shortlistedinstitutes", null);
                break;
            case R.id.recommended_colleges_layout_RL:
                if(this.mExamDetail != null)
                    this.mProfileWidgetSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/", this.mExamDetail.getExam_tag());
                else
                    this.mProfileWidgetSelected(Constants.WIDGET_RECOMMENDED_INSTITUTES, Constants.BASE_URL + "personalize/recommended-institutes/",null);
                break;
            case R.id.important_date_layout_RL:
                if(this.mExamDetail != null)
                    this.mProfileWidgetSelected(Constants.TAG_MY_ALERTS, Constants.BASE_URL + "exam-alerts/", null);
                break;
            default:
                break;
        }
    }

    private void mProfileWidgetSelected(String requestType, String url, String examTag)
    {
        if(this.mListener != null)
            this.mListener.onHomeItemSelected(requestType, url, examTag);
        Utils.UnregisterReceiver(this.getActivity());
    }

    private void mExamTabSelected(int position) {
        if(this.mListener != null ) {
           if(this.mExamDetailList != null && this.mExamDetailList.size() >position) {
                this.mExamDetail = this.mExamDetailList.get(position);
                this.mListener.onExamTabSelected(this.mExamDetail);
            }else if (MainActivity.user.getIs_preparing().equals("0")) {
                this.mListener.onExamTabSelected(this.mExamDetail);
            }
        }
    }

    @Override
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

        Utils.SetCounterAnimation(covered_syllabus, this.mExamSummary.getSyllabus_covered(), "" , "%", Constants.ANIM_SHORT_DURATION);
        covered_syllabus.setContentDescription(String.valueOf(this.mExamSummary.getSyllabus_covered())+ "percent syllabus completed");
        Utils.SetCounterAnimation(recommended_countTV, this.mExamSummary.getRecommended_count(), "" , "", Constants.ANIM_SHORT_DURATION);
        recommended_countTV.setContentDescription(String.valueOf(this.mExamSummary.getRecommended_count())+" colleges recommended by college dekho");
        Utils.SetCounterAnimation(wishList_countTV, this.mExamSummary.getShortlist_count(), "" , "", Constants.ANIM_SHORT_DURATION);
        wishList_countTV.setContentDescription(String.valueOf(this.mExamSummary.getShortlist_count())+ "colleges in your wishlist");
        Utils.SetCounterAnimation(backup_countTV, this.mExamSummary.getBackup_count(), "" , "", Constants.ANIM_SHORT_DURATION);
        backup_countTV.setContentDescription(String.valueOf("You are eligible for "+this.mExamSummary.getBackup_count())+ " colleges");
        important_dateTV.setText(""+this.mExamSummary.getNext_important_date());

        //TODO:: showing progress as a profile circle
        //if(this.mExamSummary.getSyllabus_covered() ==0)
         profileCompleted.setProgress(100);
        //else
         //profileCompleted.setProgress(this.mExamSummary.getSyllabus_covered());
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
    public interface OnTabSelectListener {
        void onExamTabSelected(ExamDetail tabPosition);
        void onHomeItemSelected(String requestType, String url, String examTag);
    }

    Handler updateExamSummaryHandler=new Handler();
    Runnable updateExamSummaryRunnable = new Runnable() {
        @Override
        public void run() {
            int currentPosition = mExamTabPager.getCurrentItem();
            mExamTabSelected(currentPosition);
        }
    };

}
