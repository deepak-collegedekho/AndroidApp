package com.collegedekho.app.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamDetailAdapter;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.listener.DashBoardItemListener;
import com.collegedekho.app.network.ApiEndPonits;
import com.collegedekho.app.network.MySingleton;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by harshvardhan on 28/11/16.
 */

public class PrepareDashboard extends BaseFragment {
    private static String PARAM1 = "param1";

    private int mSelectedSubMenuPosition = 0;
    protected  static int EXAM_TAB_POSITION =0;
    private DashBoardItemListener mListener;
    private ArrayList<ProfileExam> mExamDetailList = new ArrayList<>();
    private ExamDetailAdapter mDetailsAdapter;
    private ProfileExam mExamDetail;
    private ViewPager mExamTabPager  = null;
    private PagerTabStrip mPagerHeader = null;
    private View mExamsTabLayout;

    private ImageView mLeftButton;
    private ImageView mRightButton;
    private TextView mEmptyLayout;
    private View mPrepareWidget;
    private View mEmptyParent;

    private LocationRequest mLocationRequest;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;

    public static PrepareDashboard newInstance() {
         return new PrepareDashboard();
    }

    public void PrepareDashboard() { }

    @Override
    public void onStart() {
        super.onStart();
        createLocationRequest();
        getGoogleApiClient();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_prepare_dashboard, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mPagerHeader     =  (PagerTabStrip) view.findViewById(R.id.exam_pager_header);
        this.mExamTabPager    =  (ViewPager) view.findViewById(R.id.exam_detail_pager);
        this.mExamsTabLayout  =  view.findViewById(R.id.exams_tab_layout);
        this.mPrepareWidget   =  view.findViewById(R.id.include_layout_home_widget);
        this.mEmptyParent   =  view.findViewById(R.id.exam_selection_parent);
        this.mEmptyLayout     =  (TextView)view.findViewById(R.id.empty_layout);
        this.mLeftButton      =  (ImageView) view.findViewById(R.id.exam_left_nav);
        this.mRightButton     =  (ImageView) view.findViewById(R.id.exam_right_nav);
        this.mDetailsAdapter  = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
        this.mExamTabPager.setAdapter(this.mDetailsAdapter);

        if(mPagerHeader.getLayoutParams() != null)
        ((ViewPager.LayoutParams) this.mPagerHeader.getLayoutParams()).isDecor = true;

        this. updateUserProfile(view);

        this.mUpdateSubMenuItem(view);

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

        this.mLeftButton.setOnClickListener(this);
        this.mRightButton.setOnClickListener(this);
        view.findViewById(R.id.home_widget_first).setOnClickListener(this);
        view.findViewById(R.id.home_widget_second).setOnClickListener(this);
        view.findViewById(R.id.profile_image).setOnClickListener(this);
        view.findViewById(R.id.btn_tab_step_by_step).setOnClickListener(this);
        view.findViewById(R.id.btn_tab_psychometric_test).setOnClickListener(this);
        view.findViewById(R.id.btn_tab_psychometric_report).setOnClickListener(this);
        view.findViewById(R.id.exam_selection_button).setOnClickListener(this);
        ImageView tuteImage = (ImageView)view.findViewById(R.id.home_tute_image);
        tuteImage.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_tute7));
        tuteImage.setOnClickListener(this);
        view.findViewById(R.id.include_image_layout).findViewById(R.id.profile_image_edit_button).setOnClickListener(this);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        String psychometricResults = sharedPreferences.getString("psychometric_report", null);

        if (MainActivity.mProfile != null && MainActivity.mProfile.getPsychometric_given() == 1
                && psychometricResults != null) {
            view.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.GONE);
            view.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.VISIBLE);
        } else {
            view.findViewById(R.id.btn_tab_psychometric_test).setVisibility(View.VISIBLE);
            view.findViewById(R.id.btn_tab_psychometric_report).setVisibility(View.GONE);
        }
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
            case R.id.exam_selection_button:
                this.checkForLocationPermission();
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
            case R.id.exam_left_nav:
                this.moveToLeftExam();
                break;
            case R.id.exam_right_nav:
                this.moveToRightExam();
                break;
            case R.id.home_tute_image:
                view.setVisibility(View.GONE);
                if(getActivity() != null) {
                    ((MainActivity) getActivity()).mShowFabCounselor();
                    getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putBoolean(getString(R.string.PREPARE_HOME_TUTE), true).apply();
                }break;
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
        updateExamsList();
    }

    private void updateUserProfile(View view){
        if (MainActivity.mProfile == null)
            return;
        Profile profile = MainActivity.mProfile;

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


    private void mSubMenuItemClickListener(){
        if(this.mExamDetail != null) {
            getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().putString(Constants.SELECTED_EXAM_ID, "" + this.mExamDetail.getId()).apply();
           if (this.mSelectedSubMenuPosition == 1) {
                this.mHomeWidgetSelected(Constants.WIDGET_SYLLABUS, ApiEndPonits.BASE_URL + "yearly-exams/" + this.mExamDetail.getId() + "/syllabus/", null);
            } else if (this.mSelectedSubMenuPosition == 2) {
                this.mHomeWidgetSelected(Constants.TAG_MY_ALERTS, ApiEndPonits.BASE_URL + "exam-alerts/", this.mExamDetail.getExam_tag());
            }
        }
    }

    private void moveToLeftExam(){
        int currentPosition = PrepareDashboard.this.mExamTabPager.getCurrentItem();
        if (currentPosition > 0)
            PrepareDashboard.this.mExamTabPager.setCurrentItem(currentPosition - 1);
        else
        {
            PrepareDashboard.this.mLeftButton.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            PrepareDashboard.this.mLeftButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
        }
    }

    private void moveToRightExam(){
        int currentPosition = PrepareDashboard.this.mExamTabPager.getCurrentItem();
        if (PrepareDashboard.this.mExamDetailList.size() - 1 > currentPosition)
            PrepareDashboard.this.mExamTabPager.setCurrentItem(currentPosition + 1);
        else
        {
            PrepareDashboard.this.mRightButton.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS);
            PrepareDashboard.this.mRightButton.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.shake));
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

    public void updateExamsList(){

        if (MainActivity.mProfile != null ) {
            ArrayList<ProfileExam> examList = MainActivity.mProfile.getYearly_exams();
            this.mExamDetailList.clear();
            if(examList != null) {
                this.mExamDetailList.addAll(examList);
            }
        }

        if(!this.mExamDetailList.isEmpty()) {
            this.mExamTabPager.setVisibility(View.VISIBLE);
            this.mExamsTabLayout.setVisibility(View.VISIBLE);
            this.mPrepareWidget.setVisibility(View.VISIBLE);
            this.mEmptyParent.setVisibility(View.GONE);
            this.mEmptyLayout.setVisibility(View.GONE);
            this.mLeftButton.setVisibility(View.VISIBLE);
            this.mRightButton.setVisibility(View.VISIBLE);
            this.mLeftButton.setHapticFeedbackEnabled(true);
            this.mRightButton.setHapticFeedbackEnabled(true);
            this.mExamTabPager.setCurrentItem(EXAM_TAB_POSITION);
            int pagerPosition = this.mExamTabPager.getCurrentItem();
            if(this.mExamDetailList.size() > pagerPosition)
                this.mExamDetail = this.mExamDetailList.get(pagerPosition);
            this.mDetailsAdapter  = new ExamDetailAdapter(getChildFragmentManager(), this.mExamDetailList);
            this.mExamTabPager.setAdapter(this.mDetailsAdapter);

        }else{
            this.mExamTabPager.setVisibility(View.GONE);
            this.mExamsTabLayout.setVisibility(View.GONE);
            this.mPrepareWidget.setVisibility(View.GONE);
            this.mEmptyLayout.setVisibility(View.GONE);
            this.mEmptyLayout.setVisibility(View.VISIBLE);
            this.mLeftButton.setVisibility(View.GONE);
            this.mRightButton.setVisibility(View.GONE);
            this.mEmptyLayout.setText("You don't have any exams selected.");
            this.mExamDetail = new ProfileExam();
            this.mExamDetail.setId(0);
        }
    }


    private void mUpdateSubMenuItem(View view) {
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

    @Override
    public String getEntity() {
        return null;
    }

    private void checkForLocationPermission() {

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.location_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                                    Manifest.permission.ACCESS_FINE_LOCATION}, Constants.RC_HANDLE_LOCATION);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            requestForYearlyExams();
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            if (getActivity() != null && !getActivity().isFinishing()) {
                builder.show();
            }
        } else {
            if (mGoogleApiClient == null) {
                requestForYearlyExams();
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation == null && Constants.IS_LOCATION_SERVICES_ENABLED) {
                checkLocationSettings();
            } else {
                mRequestForLocationUpdate();
            }
        }
    }

    public void requestForYearlyExams(){
        EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_YEARY_EXAMS, null, null));

    }

    private GoogleApiClient getGoogleApiClient(){
        if(mGoogleApiClient == null && getActivity() != null) {
            mGoogleApiClient = ((MainActivity)getActivity()).getGoogleClient();
        }
        return mGoogleApiClient;
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            // check fragment is added when we get location update
            if(!isAdded())
                return;

            mLastLocation = location;
            mRequestForLocationUpdate();
        }

    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case Activity.RESULT_OK:
                if(getActivity() != null &&
                        ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    LocationServices.FusedLocationApi.requestLocationUpdates(getGoogleApiClient(), mLocationRequest, (com.google.android.gms.location.LocationListener) locationListener);
                    ((MainActivity)getActivity()).showProgressDialog(Constants.TAG_USER_EXAMS_SUBMISSION,Constants.THEME_TRANSPARENT);
                }else{
                    requestForYearlyExams();
                }
                break;
            case Activity.RESULT_CANCELED:
                requestForYearlyExams();
                break;
        }
    }

    public void askForLocationSetting(){
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(getGoogleApiClient());
            if (mLastLocation == null && Constants.IS_LOCATION_SERVICES_ENABLED) {
                checkLocationSettings();
            } else {
                mRequestForLocationUpdate();
            }
        }
    }

    private void mRequestForLocationUpdate(){

        HashMap<String, String> params = new HashMap<>();
        if (mLastLocation != null) {
            params.put("latitude", String.valueOf(mLastLocation.getLatitude()));
            params.put("longitude", String.valueOf(mLastLocation.getLongitude()));
        }
        if(params.size() > 0) {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if(getGoogleApiClient() != null) {
                    if(getGoogleApiClient().isConnected()) {
                        LocationServices.FusedLocationApi.removeLocationUpdates(getGoogleApiClient(), locationListener);
                    }
                }
            }
            EventBus.getDefault().post(new Event(AllEvents.ACTION_ON_LOCATION_UPDATE,params, null));
        }else{
            requestForYearlyExams();
        }

    }

    private void createLocationRequest() {
        mLocationRequest = LocationRequest.create()
                .setFastestInterval(5 * 1000)
                .setInterval(30 * 1000)
                .setPriority(LocationRequest.PRIORITY_LOW_POWER);

    }
    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);

        final PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(getGoogleApiClient(), builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {

            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state =result.getLocationSettingsStates();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location
                        // requests here.
                        Log.e(TAG, "location settings are satisfied");
                        requestForYearlyExams();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the user
                        // a dialog.
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            status.startResolutionForResult(getActivity(), MainActivity.REQUEST_CHECK_LOCATION_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        Log.e(TAG, "location settings are not satisfied");
                        requestForYearlyExams();
                        break;
                }
            }
        });
    }

}
