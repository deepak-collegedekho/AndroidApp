package com.collegedekho.app.fragment.profileBuilding;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamStreamAdapter;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
/**
 * Created by sureshsaini on 3/5/17.
 */

public class PrefStreamSelectionFragment  extends BaseProfileBuildingFragment {
        private static final String TAG = StreamSelectionFragment.class.getSimpleName();
        private static final String PARAM1 = "PARAM1";
        private View mRootView;
        private String mEventCategory = "";
        private String mEventAction = "";
        private HashMap<String, Object> mEventValue = new HashMap<>();
        private RecyclerView mStreamRecyclerView;
        private ExamStreamAdapter mStreamAdapter;
        private ArrayList<ProfileSpinnerItem> mStreamList = new ArrayList<>();
        private LocationRequest mLocationRequest;
        private Location mLastLocation;
        private GoogleApiClient mGoogleApiClient;

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param streamList
         * @return A new instance of fragment LevelSelectionFragment.
         */
        public static PrefStreamSelectionFragment newInstance(ArrayList<ProfileSpinnerItem> streamList) {
            PrefStreamSelectionFragment fragment = new PrefStreamSelectionFragment();
            if (streamList != null) {
                Bundle args = new Bundle();
                args.putParcelableArrayList(PARAM1, streamList);
                fragment.setArguments(args);
            }
            return fragment;
        }

        public PrefStreamSelectionFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();
            if (args != null) {
                ArrayList streamList = args.getParcelableArrayList(PARAM1);
                if (streamList != null) {
                    mStreamList.clear();
                    mStreamList.addAll(streamList);
                }
            }
        }

        @Override
        public void onStart() {
            super.onStart();
            createLocationRequest();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return mRootView = inflater.inflate(R.layout.fragment_pref_stream_selection, container, false);
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            // set current level education
            TextView currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);
            currentLevelTxtView.setVisibility(View.VISIBLE);
            int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
            if (currentLevelId == ProfileMacro.LEVEL_TWELFTH || currentLevelId == ProfileMacro.LEVEL_TENTH) {
                currentLevelTxtView.setText(getString(R.string.school));
            } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                currentLevelTxtView.setText(getString(R.string.college));
            } else {
                currentLevelTxtView.setText(getString(R.string.pg_college));
            }

            super.initIntituesCountViews(view);
            int instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).
                    getInt(getString(R.string.pref_level_institute_count), 0);
            super.setInstituteCount(String.valueOf(instituteCount));

            this.checkUserAlreadySelectedStream();
            mStreamRecyclerView = (RecyclerView) view.findViewById(R.id.user_education_recycler_view);
            mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            mStreamAdapter = new ExamStreamAdapter(null, getActivity(), mStreamList);
            mStreamRecyclerView.setAdapter(mStreamAdapter);

            view.findViewById(R.id.user_education_skip_button).setOnClickListener(this);
            view.findViewById(R.id.user_education_level_edit_btn).setOnClickListener(this);
            view.findViewById(R.id.user_education_next_button).setOnClickListener(this);
        }

        @Override
        public void onResume() {
            super.onResume();
            MainActivity mainActivity = (MainActivity) getActivity();
            if (mainActivity != null) {
                MainActivity.currentFragment = this;
            }
            this.requestForLevelStreams();
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        }

        @Subscribe
        public void onEvent(Event event) {
            if (event != null) {
                switch (event.getTag()) {
                    case AllEvents.ACTION_CURRENT_STREAM_SELECTION:
                        super.setInstituteCount(event.getExtra());
                        this.showNextButton();
                        break;
                }
            }
        }

        private void checkUserAlreadySelectedStream() {
            if (MainActivity.mProfile == null) {
                return;
            }

            int userStreamId = MainActivity.mProfile.getCurrent_stream_id();
            int count = mStreamList.size();
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem streamOj = mStreamList.get(i);
                if (streamOj == null) continue;
                if (streamOj.getId() == userStreamId) {
                    streamOj.setSelected(true);
                    int instituteCount = streamOj.getInstitutes_count();
                    getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit()
                            .putInt(getString(R.string.pref_stream_institute_count), instituteCount).apply();
                    // update institute count for this stream
                    super.setInstituteCount(String.valueOf(instituteCount));
                    showNextButton();
                } else {
                    streamOj.setSelected(false);
                }
            }
        }


        private void requestForLevelStreams() {
            if (mStreamList.isEmpty()) {
                String streamType = "1"; //  0 for college and 1 for school
                if (MainActivity.mProfile != null) {
                    int currentLevelID = MainActivity.mProfile.getCurrent_level_id();
                    if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                        streamType = "0";
                    } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
                        streamType = "0";
                    }
                }
                EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_LEVEl_STREAMS, null, streamType));
            }
        }

        private void showNextButton() {
            mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
            final View nextView = mRootView.findViewById(R.id.user_education_next_button);
            if (nextView.getAlpha() != 1) {

                nextView.setVisibility(View.VISIBLE);
                nextView.setAlpha(0);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        nextView.animate()
                                .x(mRootView.getWidth() - nextView.getWidth() - nextView.getPaddingRight())
                                .alpha(1f)
                                .setDuration(Constants.ANIM_AVERAGE_DURATION);
                    }
                }, Constants.ANIM_SHORT_DURATION);


                View skipView = mRootView.findViewById(R.id.user_education_skip_button);
                skipView.setVisibility(View.VISIBLE);
                skipView.setAlpha(0f);

                skipView.animate()
                        .alpha(1f)
                        .x(skipView.getWidth() + mRootView.findViewById(R.id.user_education_next_button_layout).getPaddingLeft())
                        .setStartDelay(Constants.ANIM_SHORT_DURATION)
                        .setDuration(Constants.ANIM_AVERAGE_DURATION);

            }
        }

        @Override
        public void onClick(View view) {
            super.onClick(view);
            switch (view.getId()) {
                case R.id.user_education_skip_button:
                    this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
                    this.mEventAction = getString(R.string.ACTION_STREAM_SKIP_SELECTED);
                    EventBus.getDefault().post(new Event(AllEvents.ACTION_SKIP_STREAM_SELECTION, null, null));
                    break;
                case R.id.user_education_next_button:
                    this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
                    this.mEventAction = getString(R.string.ACTION_STREAM_NEXT_SELECTED);
                    checkForLocationPermission();
                    break;
                case R.id.user_education_level_edit_btn:
                    EventBus.getDefault().post(new Event(AllEvents.ACTION_LEVEL_EDIT_SELECTION, null, null));
                    break;
                default:
                    break;
            }
            if (!this.mEventAction.isEmpty() && this.mEventAction != "") {
                //Events
                AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
            }
        }

        private void checkForLocationPermission() {
            int count = mStreamList.size();
            boolean isStreamSelected = false;
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem objItem = mStreamList.get(i);
                if (!objItem.isSelected()) continue;
                isStreamSelected = true;
                break;
            }
            if (!isStreamSelected) {
                EventBus.getDefault().post(new Event(AllEvents.ACTION_PLEASE_SELECT_STREAM, null, null));
                return;
            }
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),
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
                                setUserEducationStream();
                            }
                        });
                // Create the AlertDialog object and return it
                builder.create();
                if (getActivity() != null && !getActivity().isFinishing()) {
                    builder.show();
                }
            } else {
                if (mGoogleApiClient == null) {
                    setUserEducationStream();
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

        public void setUserEducationStream() {

            this.requestForLevelStreams();
            if (mStreamList.isEmpty()) {
                return;
            }
            // check user has been selected a stream
            int currentStreamId = 0;
            String currentStreamName = "";
            int count = mStreamList.size();
            boolean isStreamSelected = false;
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem objItem = mStreamList.get(i);
                if (!objItem.isSelected()) continue;
                currentStreamId = objItem.getId();
                currentStreamName = objItem.getName();
                isStreamSelected = true;
                break;
            }
            if (!isStreamSelected) {
                EventBus.getDefault().post(new Event(AllEvents.ACTION_PLEASE_SELECT_STREAM, null, null));
                return;
            }
            showNextButton();

            MainActivity.mProfile.setCurrent_stream_id(currentStreamId);
            MainActivity.mProfile.setCurrent_stream_name(currentStreamName);

            EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_STREAM_YEARLY_EXAMS, null, null));

            // send Events
            this.mEventAction = getString(R.string.ACTION_CURRENT_STREAM_SELECTED);
            String name = MainActivity.mProfile.getName();
            if (name != null && !name.isEmpty())
                this.mEventValue.put(getString(R.string.USER_NAME), name);
            String phone = MainActivity.mProfile.getPhone_no();
            if (phone != null && !phone.isEmpty())
                this.mEventValue.put(getString(R.string.USER_PHONE), name);
            this.mEventValue.put(getString(R.string.USER_CURRENT_STREAM_ID), String.valueOf(currentStreamId));
            this.mEventCategory = getString(R.string.CATEGORY_PREFERENCE);
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, mEventValue, this.getActivity());
        }

        public void updateStreamList(ArrayList<ProfileSpinnerItem> streamList) {
            if (isAdded()) {
                this.mStreamList.clear();
                this.mStreamList.addAll(streamList);
                checkUserAlreadySelectedStream();
                if (mStreamAdapter == null) {
                    mStreamAdapter = new ExamStreamAdapter(null, getActivity(), mStreamList);
                    mStreamRecyclerView.setAdapter(mStreamAdapter);
                } else {
                    mStreamAdapter.updateStreamList(mStreamList);
                }
            }

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
                        LocationServices.FusedLocationApi.requestLocationUpdates(getGoogleApiClient(), mLocationRequest, locationListener);
                        ((MainActivity)getActivity()).showProgressDialog(Constants.TAG_USER_EXAMS_SUBMISSION,Constants.THEME_TRANSPARENT);
                    }else{
                        setUserEducationStream();
                    }
                    break;
                case Activity.RESULT_CANCELED:
                    setUserEducationStream();
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
                setUserEducationStream();
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
                            setUserEducationStream();
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied. But could be fixed by showing the user
                            // a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                status.startResolutionForResult(getActivity(), MainActivity.REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            Log.e(TAG, "location settings are not satisfied");
                            setUserEducationStream();
                            break;
                    }
                }
            });
        }

    }
