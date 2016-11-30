package com.collegedekho.app.fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.crop.Crop;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.listener.ProfileFragmentListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;
import com.collegedekho.app.widget.CustomSwipeRefreshLayout;
import com.collegedekho.app.widget.spinner.MaterialSpinner;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by {sureshsaini} on {17/5/16.}
 */
public class ProfileFragment extends BaseFragment implements ProfileFragmentListener, CustomSwipeRefreshLayout.OnRefreshListener {

    private final static String TAG ="Profile Fragment";
    private final static String PARAM1  = "param1";
    public Profile mProfile ;
    private UserProfileListener mListener;
    private TextView mProfileName;
    private Uri mImageCaptureUri;
    private View mRootView;
    private Drawable mPlusDrawable;
    private Drawable mMinusDrawable;
    private static List<ProfileSpinnerItem> mPreferredStatesList;
    private static List<ProfileSpinnerItem> mPreferredCitiesList;
    private static List<ProfileSpinnerItem> mPreferredDegreesList;
    private RelativeLayout mUserImageLayout;
    private CustomSwipeRefreshLayout mSwipeRefreshLayout;

    public static ProfileFragment getInstance(Profile profile){
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM1, profile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.mProfile = args.getParcelable(PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_profile, container, false);

        this.mSwipeRefreshLayout = (CustomSwipeRefreshLayout) mRootView.findViewById(R.id.profile_swipe_refresh_container);
        this.mSwipeRefreshLayout.setOnRefreshListener(this);
//        this.mSwipeRefreshLayout.setEnabled(false);

        if(mProfile.getIs_verified() == ProfileMacro.NUMBER_VERIFIED)
            mRootView.findViewById(R.id.profile_login_button).setVisibility(View.GONE);
        else
            mRootView.findViewById(R.id.profile_login_button).setVisibility(View.VISIBLE);

        if(isAdded()) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                mPlusDrawable = VectorDrawableCompat.create(getActivity().getResources(), R.drawable.ic_add_inline_vector23dp, null);
                mMinusDrawable = VectorDrawableCompat.create(getActivity().getResources(), R.drawable.ic_minus_inline, null);
            } else {
                mPlusDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_add_inline_vector23dp);
                mMinusDrawable = ContextCompat.getDrawable(getActivity(), R.drawable.ic_minus_inline);
            }
        }

        mUserImageLayout = (RelativeLayout) mRootView.findViewById(R.id.user_profile_image_update);

        mUserImageLayout.findViewById(R.id.profile_image_edit_button).setVisibility(View.GONE);

        mProfileName = (TextView)mRootView.findViewById(R.id.profile_user_name);
        mRootView.findViewById(R.id.profile_login_button).setOnClickListener(this);
        mRootView.findViewById(R.id.user_profile_image_update).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_info_edit_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_education_edit_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_preferred_edit_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_exams_edit_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_other_info_edit_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_expand_info_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_expand_education_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_expand_preferred_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_expand_exams_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_expand_other_info_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_info_save_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_education_save_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_preferred_save_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_other_info_save_btn).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_to_dashboard).setOnClickListener(this);
        mRootView.findViewById(R.id.profile_to_recommended).setOnClickListener(this);

        mRootView.findViewById(R.id.profile_login_button).setContentDescription("click to login");
        mRootView.findViewById(R.id.user_profile_image_update).setContentDescription("click to change your profile picture");
        mRootView.findViewById(R.id.profile_info_edit_btn).setContentDescription("Click to edit your general profile information");
        mRootView.findViewById(R.id.profile_education_edit_btn).setContentDescription("Click to edit your current education information");
        mRootView.findViewById(R.id.profile_preferred_edit_btn).setContentDescription("Click to edit your profile preferences");
        mRootView.findViewById(R.id.profile_exams_edit_btn).setContentDescription("Click to edit the exam you are interested in");
        mRootView.findViewById(R.id.profile_other_info_edit_btn).setContentDescription("Click to edit miscellaneous profile information");
        mRootView.findViewById(R.id.profile_expand_info_btn).setContentDescription("Click to expand your general profile information");
        mRootView.findViewById(R.id.profile_expand_education_btn).setContentDescription("Click to expand your current education information");
        mRootView.findViewById(R.id.profile_expand_preferred_btn).setContentDescription("Click to expand your profile preferences");
        mRootView.findViewById(R.id.profile_expand_exams_btn).setContentDescription("Click to expand the exam you are interested in");
        mRootView.findViewById(R.id.profile_expand_other_info_btn).setContentDescription("Click to expand miscellaneous profile information");
        mRootView.findViewById(R.id.profile_info_save_btn).setContentDescription("Save info you entered");
        mRootView.findViewById(R.id.profile_education_save_btn).setContentDescription("Save info you entered");
        mRootView.findViewById(R.id.profile_preferred_save_btn).setContentDescription("Save info you entered");
        mRootView.findViewById(R.id.profile_other_info_save_btn).setContentDescription("Save info you entered");
        mRootView.findViewById(R.id.profile_to_dashboard).setContentDescription("Click to go to your Dashboard");
        mRootView.findViewById(R.id.profile_to_recommended).setContentDescription("Click to see recommended colleges");

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateProfileImage();
        updateUserProfile();
    }

    public void updateUserProfile() {
        mProfile = MainActivity.mProfile;
        if (mProfile == null && !isAdded())
            return;
        if(mUserImageLayout != null)
        setProfileProgressStatus(mUserImageLayout.findViewById(R.id.user_profile_progress), mProfile.getProgress());

        String name = mProfile.getName();
        if (name != null && !name.isEmpty()) {
            mProfileName.setText(name);
        }

        // update current basic info
        String email = mProfile.getEmail();
        if (email != null && !email.isEmpty() && !email.contains("@anonymouscollegedekho.com")) {
            ((TextView) mRootView.findViewById(R.id.profile_info_email)).setText(email);
        } else {
            email = Utils.getDeviceEmail(getActivity());
            ((TextView) mRootView.findViewById(R.id.profile_info_email)).setText(email);
        }

        String phone = mProfile.getPhone_no();
        if (phone != null && !phone.isEmpty()) {
            ((TextView) mRootView.findViewById(R.id.profile_info_phone)).setText(getString(R.string.country_no) + phone);
        } else {
            ((TextView) mRootView.findViewById(R.id.profile_info_phone)).setText(getString(R.string.na));
        }

        String city = mProfile.getCity_name();
        if (city != null && !city.isEmpty()) {
            ((TextView) mRootView.findViewById(R.id.profile_info_city)).setText(city);
        } else {
            ((TextView) mRootView.findViewById(R.id.profile_info_city)).setText(getString(R.string.na));
        }

        String state = mProfile.getState_name();
        if (state != null && !state.isEmpty()) {
            ((TextView) mRootView.findViewById(R.id.profile_info_state)).setText(state);
        } else {
            ((TextView) mRootView.findViewById(R.id.profile_info_state)).setText(getString(R.string.na));
        }

        String motherTongue = mProfile.getMother_tongue_name();
        if (motherTongue != null && !motherTongue.isEmpty()) {
            ((TextView) mRootView.findViewById(R.id.profile_info_mother_tongue)).setText(motherTongue);
        } else {
            ((TextView) mRootView.findViewById(R.id.profile_info_mother_tongue)).setText(getString(R.string.na));
        }

        String category = mProfile.getSocial_category_name();
        if (category != null && !category.isEmpty()) {
            ((TextView) mRootView.findViewById(R.id.profile_info_category)).setText(category);
        } else {
            ((TextView) mRootView.findViewById(R.id.profile_info_category)).setText(getString(R.string.na));
        }
        // set basic info progress
        setProfileProgressStatus(mRootView.findViewById(R.id.profile_info_progress), mProfile.getBasic_progress());

        //  set degree name school if mDeviceProfile is currently in school and does not have any degree
        //  else set mDeviceProfile's current holding degree name in current education

        int userCurrentStreamId = mProfile.getCurrent_stream_id();
        if (userCurrentStreamId == 33 || userCurrentStreamId == 34 || userCurrentStreamId == 35 || userCurrentStreamId == 36
                || userCurrentStreamId == 37 || userCurrentStreamId == 38 || userCurrentStreamId == 39) {
            ((TextView) mRootView.findViewById(R.id.profile_education_degree)).setText(getString(R.string.school));
        } else {
            String currentDegreeName = mProfile.getCurrent_degree_name();
            if (currentDegreeName != null && !currentDegreeName.isEmpty()) {
                ((TextView) mRootView.findViewById(R.id.profile_education_degree)).setText(currentDegreeName);
            } else {
                ((TextView) mRootView.findViewById(R.id.profile_education_degree)).setText(getString(R.string.na));
            }
        }

        // set mDeviceProfile's Passing Year
        int currentPassingYear = mProfile.getCurrent_passing_year();
        if(currentPassingYear >= 2000) {
            ((TextView) mRootView.findViewById(R.id.profile_education_year)).setText(String.valueOf(currentPassingYear));

        } else{
            // we will not set user current passing year and preferred year utill he
            // will not set,  discussed with Harsh and Shashank
           /* ((TextView) mRootView.findViewById(R.id.profile_education_year)).setText(String.valueOf(Utils.GetCurrentYear()));

            //TODO :: save it locally and send it to server when you have more than 5 unsynced keys
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("current_passing_year", String.valueOf(Utils.GetCurrentYear()));
            hashMap.put("preferred_year_of_admission", String.valueOf(String.valueOf(Utils.GetCurrentYear())));

            ((MainActivity) getActivity()).requestForProfile(hashMap);

            MainActivity.mProfile.setPreferred_year_of_admission(Utils.GetCurrentYear());

            MainActivity.mProfile.setCurrent_passing_year(Utils.GetCurrentYear());*/
        }

        // set DeviceProfile Stream Name in Current Education
        String currentStream = mProfile.getCurrent_stream_name();
        if (currentStream != null && !currentStream.isEmpty()) {
            ((TextView)mRootView.findViewById(R.id.profile_education_stream)).setText(currentStream);
        } else {
            ((TextView)mRootView.findViewById(R.id.profile_education_stream)).setText(getString(R.string.na));
        }

        // hide specialization if mDeviceProfile current
        // level is school else show his/her specialization
        userCurrentStreamId = mProfile.getCurrent_stream_id();
        if(userCurrentStreamId == 33 ||userCurrentStreamId == 34 || userCurrentStreamId == 35  || userCurrentStreamId == 36
                || userCurrentStreamId == 37 ||userCurrentStreamId == 38 || userCurrentStreamId == 39) {
            mRootView.findViewById(R.id.profile_education_specialization_layout).setVisibility(View.GONE);
        } else {
            mRootView.findViewById(R.id.profile_education_specialization_layout).setVisibility(View.GONE);

            String currentSpecialization = mProfile.getCurrent_specialization_name();
            if (currentSpecialization != null && !currentSpecialization.isEmpty()) {
                ((TextView)mRootView.findViewById(R.id.profile_education_specialization)).setText(currentSpecialization);
            } else {
                ((TextView)mRootView.findViewById(R.id.profile_education_specialization)).setText(getString(R.string.na));
            }
        }

        // set mDeviceProfile's current score which he/she achieved in last degree or in school
        int currentScore = mProfile.getCurrent_score();
        int scoreType = mProfile.getCurrent_score_type();
        if (scoreType <=  0) {
            ((TextView)mRootView.findViewById(R.id.profile_education_score)).setText(String.valueOf(currentScore));
        } else {
            ((TextView)mRootView.findViewById(R.id.profile_education_score)).setText(currentScore +" "+  ProfileMacro.getCurrentScoreTypeName(scoreType));
        }

        // set mDeviceProfile's education mode
        int currentMode = mProfile.getCurrent_mode();
        {
            ((TextView) mRootView.findViewById(R.id.profile_education_mode)).setText(ProfileMacro.getEducationModeName(currentMode));
        }

        setProfileProgressStatus(mRootView.findViewById(R.id.profile_education_progress), mProfile.getCurrent_education_progress());

        // set mDeviceProfile preferred info
        setPreferredEducationInfo(false);

        setProfileProgressStatus(mRootView.findViewById(R.id.profile_exams_progress), mProfile.getExams_progress());

        //  set DeviceProfile Exams Names
        mExpandUserExamsLayout(false);

        //  set DeviceProfile Others Info
        String fatherName = mProfile.getFathers_name();
        if (fatherName != null && !fatherName.isEmpty()) {
            ((TextView)mRootView.findViewById(R.id.profile_father_name)).setText(fatherName);
        } else {
            ((TextView)mRootView.findViewById(R.id.profile_father_name)).setText(getString(R.string.na));
        }


        String motherName = mProfile.getMothers_name();
        if (motherName != null && !motherName.isEmpty()) {
            ((TextView)mRootView.findViewById(R.id.profile_mother_name)).setText(motherName);
        } else {
            ((TextView)mRootView.findViewById(R.id.profile_mother_name)).setText(getString(R.string.na));
        }

        String coachingName = mProfile.getCoaching_institute();
        if (coachingName != null && !coachingName.isEmpty()) {
            ((TextView)mRootView.findViewById(R.id.profile_coaching_institute_name)).setText(coachingName);
        } else {
            ((TextView)mRootView.findViewById(R.id.profile_coaching_institute_name)).setText(getString(R.string.na));
        }
        setProfileProgressStatus(mRootView.findViewById(R.id.profile_other_progress), mProfile.getOthers_progress());

    }

    /**
     *  this method is to  update mDeviceProfile preferred education info
     *  on preference part of screen
     * @param isShowAllInfo flag to show all exams
     */
    public void setPreferredEducationInfo(boolean isShowAllInfo) {
        View view = getView();
        if (mProfile == null || view == null)
            return;

        String preferredStream = mProfile.getPreferred_stream_short_name();
        if (preferredStream != null && !preferredStream.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_preferences_stream)).setText(preferredStream);

        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_stream)).setText(getString(R.string.na));
        }

        String preferredSpecialization = mProfile.getPreferred_specialization_name();
        if (preferredSpecialization != null && !preferredSpecialization.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_preferences_specialization)).setText(preferredSpecialization);
        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_specialization)).setText(getString(R.string.na));

        }

        //set mDeviceProfile admission year
        int preferredYear = mProfile.getPreferred_year_of_admission();
        if(preferredYear >= 2000 ){
            ((TextView) view.findViewById(R.id.profile_preferences_year)).setText(String.valueOf(preferredYear));
        }else
        {
            //if nothing is set in profile, dig in current passing year from profile and set it as admission year
            int currentEducationPassingYear = MainActivity.mProfile.getCurrent_passing_year();

            //if current passing year is not set set current year as admission year
            if (currentEducationPassingYear <= 0)
                currentEducationPassingYear = Utils.GetCurrentYear();

            ((TextView) mRootView.findViewById(R.id.profile_preferences_year)).setText(String.valueOf(currentEducationPassingYear));

            //TODO :: save it locally and send it to server when you have more than 5 unsynced keys
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("preferred_year_of_admission", String.valueOf(currentEducationPassingYear));

            ((MainActivity) getActivity()).requestForProfile(hashMap);

            MainActivity.mProfile.setPreferred_year_of_admission(currentEducationPassingYear);
        }


        int preferredMode = mProfile.getPreferred_mode();
        ((TextView)view.findViewById(R.id.profile_preferences_mode)).setText(ProfileMacro.getEducationModeName(preferredMode));

        int feeRange = mProfile.getPreferred_fee_range_max();
        if(feeRange >= 1) {
            ((TextView) view.findViewById(R.id.profile_preferences_fee_range)).setText(ProfileMacro.getFeeRangeName(feeRange));
        }else
            ((TextView)view.findViewById(R.id.profile_preferences_fee_range)).setText(getString(R.string.na));

        int loanRequired = mProfile.getPreferred_loan_required();
        if(loanRequired >= 1) {
            int loanAmount = mProfile.getPreferred_loan_amount_needed();
            view.findViewById(R.id.profile_preferences_loan_required_layout).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.profile_preferences_loan_amount)).setText(ProfileMacro.getLoanRequiredAmount(loanAmount));
        }else {
            view.findViewById(R.id.profile_preferences_loan_required_layout).setVisibility(View.GONE);
        }

        // set degree names
        ArrayList<String> degreeNameList = mProfile.getPreferred_degrees_names();
        if(degreeNameList != null && !degreeNameList.isEmpty() ){
            int count = degreeNameList.size();
            if(!isShowAllInfo && count >3)
                count =3;

            StringBuilder degreesNameBuffer = new StringBuilder();
            for (int i = 0; i < count; i++) {
                if(i == 0){
                    degreesNameBuffer.append(degreeNameList.get(i));
                    continue;
                }
                degreesNameBuffer.append(", ").append(degreeNameList.get(i));
            }
            if(!isShowAllInfo && degreeNameList.size() > count) {
                ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(degreesNameBuffer.append("....").toString());

            }else{
                ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(degreesNameBuffer.toString());
            }
        }else
            ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(getString(R.string.na));

        // set City names
        ArrayList<String> cityNameList = mProfile.getPreferred_cities_names();
        if(cityNameList != null  && !cityNameList.isEmpty()){
            int count = cityNameList.size();
            if(!isShowAllInfo && count >4)
                count =4;

            StringBuilder cityNameBuffer = new StringBuilder();
            for (int i = 0; i < count; i++) {
                if(i == 0){
                    cityNameBuffer.append(cityNameList.get(i));
                    continue;
                }
                cityNameBuffer.append(", ").append(cityNameList.get(i));
            }
            if(!isShowAllInfo && cityNameList.size() > count) {
                ((TextView)view.findViewById(R.id.profile_preferences_location)).setText(cityNameBuffer.append("....").toString());
            }else{
                ((TextView)view.findViewById(R.id.profile_preferences_location)).setText(cityNameBuffer.toString());
            }
        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_location)).setText(getString(R.string.na));

        }
        setProfileProgressStatus(view.findViewById(R.id.profile_preferences_progress), mProfile.getPreference_progress());
    }
    /**
     * This method is used to set mDeviceProfile exams info
     * @param showAll boolean to show all exam' name on UI
     */

    private void mExpandUserExamsLayout(boolean showAll) {

        if (mProfile == null )
            return;

        ArrayList<ProfileExam> examsList = mProfile.getYearly_exams();
        if(examsList != null ){
            int count = examsList.size();
            if(!showAll && count >4)
                count =4;

            StringBuilder examsNameBuffer = new StringBuilder();
            for (int i = 0; i < count; i++) {
                ProfileExam exam = examsList.get(i);
                if(exam == null)continue;
                if(i == 0){
                    examsNameBuffer.append(exam.getExam_name());
                    continue;
                }
                examsNameBuffer.append(", ").append(exam.getExam_name()) ;
            }
            if(!showAll && examsList.size() > count) {
                ((TextView) mRootView.findViewById(R.id.profile_exams_name)).setText(examsNameBuffer.append("....").toString());
                mRootView.findViewById(R.id.profile_exams_name).setSelected(false);
                ((ImageView)mRootView.findViewById(R.id.profile_expand_exams_btn)).setImageDrawable(mPlusDrawable);
                mRootView.findViewById(R.id.profile_expand_exams_btn).setVisibility(View.VISIBLE);

            }else{
                mRootView.findViewById(R.id.profile_exams_name).setSelected(true);
                ((TextView)mRootView.findViewById(R.id.profile_exams_name)).setText(examsNameBuffer.toString());
                ((ImageView)mRootView.findViewById(R.id.profile_expand_exams_btn)).setImageDrawable(mMinusDrawable);
                if( examsList.size() <= 4) {
                    mRootView.findViewById(R.id.profile_expand_exams_btn).setVisibility(View.GONE);
                }else {
                    mRootView.findViewById(R.id.profile_expand_exams_btn).setVisibility(View.VISIBLE);
                }
            }
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

    @Override
    public void onRefresh() {
        Log.d("SWIPE :", " SUCCESS");
        if(mListener != null)
            this.mListener.onRefreshProfile();
        else
            this.mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        MainActivity  mainActivity = (MainActivity) getActivity();
        if (mainActivity != null) {
            mainActivity.currentFragment = this;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (UserProfileListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(TAG+ context.toString()
                    + " must implement UserProfileListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(final View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.profile_to_dashboard:
                mListener.toDashboard();
                break;
            case R.id.profile_to_recommended:
                mListener.toRecommended();
                break;
            case R.id.profile_login_button:
                mListener.onPostAnonymousLogin();
                break;
            case R.id.user_profile_image_update:
                // start loader for cursor
                checkMediaFilePermission();
                break;
            case R.id.profile_expand_info_btn:
                View view = mRootView.findViewById(R.id.profile_expanded_info_layout);
                if(view.getVisibility()== View.VISIBLE) {
                    ((ImageView)mRootView.findViewById(R.id.profile_expand_info_btn)).setImageDrawable(mPlusDrawable);
                    view.setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_info_btn).setContentDescription("Click to collapse your general profile information");
                }else {
                    ((ImageView)mRootView.findViewById(R.id.profile_expand_info_btn)).setImageDrawable(mMinusDrawable);
                    view.setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_expand_info_btn).setContentDescription("Click to expand your general profile information");
                }
                break;
            case R.id.profile_expand_education_btn:
                view = mRootView.findViewById(R.id.profile_expanded_education_layout);
                if(view.getVisibility()== View.VISIBLE) {
                    ((ImageView)mRootView.findViewById(R.id.profile_expand_education_btn)).setImageDrawable(mPlusDrawable);
                    view.setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_education_btn).setContentDescription("Click to collapse your current education information");
                }else {
                    ((ImageView)mRootView.findViewById(R.id.profile_expand_education_btn)).setImageDrawable(mMinusDrawable);
                    view.setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_expand_education_btn).setContentDescription("Click to expand your current education information");
                }
                break;
            case R.id.profile_expand_preferred_btn:
                view = mRootView.findViewById(R.id.profile_expanded_preferred_layout);
                if(view.getVisibility()== View.VISIBLE) {
                    ((ImageView)mRootView.findViewById(R.id.profile_expand_preferred_btn)).setImageDrawable(mPlusDrawable);
                    setPreferredEducationInfo(false);
                    view.setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_preferred_btn).setContentDescription("Click to collapse your profile preferences");
                   } else {
                    ((ImageView)mRootView.findViewById(R.id.profile_expand_preferred_btn)).setImageDrawable(mMinusDrawable);
                    view.setVisibility(View.VISIBLE);
                    setPreferredEducationInfo(true);
                    mRootView.findViewById(R.id.profile_expand_preferred_btn).setContentDescription("Click to expand your profile preferences");
                }
                break;

            case R.id.profile_expand_exams_btn:
                if(mRootView.findViewById(R.id.profile_exams_name).isSelected()) {
                    mExpandUserExamsLayout(false);
                    mRootView.findViewById(R.id.profile_expand_exams_btn).setContentDescription("Click to collapse the exam you are interested in");
                }else{
                    mExpandUserExamsLayout(true);
                    mRootView.findViewById(R.id.profile_expand_exams_btn).setContentDescription("Click to expand the exam you are interested in");
                }
                break;

            case R.id.profile_expand_other_info_btn:
                view = mRootView.findViewById(R.id.profile_expanded_other_info_layout);
                if(view.getVisibility()== View.VISIBLE) {
                    ((ImageView)mRootView.findViewById(R.id.profile_expand_other_info_btn)).setImageDrawable(mPlusDrawable);
                    view.setVisibility(View.GONE);
                    v.setScaleY(1f);
                    mRootView.findViewById(R.id.profile_expand_other_info_btn).setContentDescription("Click to collapse miscellaneous profile information");
                }else {
                    ((ImageView)mRootView.findViewById(R.id.profile_expand_other_info_btn)).setImageDrawable(mMinusDrawable);
                    view.setVisibility(View.VISIBLE);
                    v.setScaleY(-1f);
                    mRootView.findViewById(R.id.profile_expand_other_info_btn).setContentDescription("Click to expand miscellaneous profile information");
                }break;
            case R.id.profile_info_edit_btn:
                ((ImageView)mRootView.findViewById(R.id.profile_expand_info_btn)).setImageDrawable(mPlusDrawable);
                view = mRootView.findViewById(R.id.profile_info_edit_layout);
                if(view.getVisibility() != View.VISIBLE) {
                    mRootView.findViewById(R.id.profile_info_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expanded_info_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_info_btn).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_info_edit_layout).setVisibility(View.VISIBLE);
                    loadUserInfoEditLayout();
                    mRootView.findViewById(R.id.profile_info_edit_btn).setContentDescription("Click to stop editing your general profile information");
                }else{
                    mRootView.findViewById(R.id.profile_info_layout).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_expanded_info_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_info_btn).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_info_edit_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_info_edit_btn).setContentDescription("Click to edit your general profile information");
                }
                break;
            case R.id.profile_education_edit_btn:
                ((ImageView)mRootView.findViewById(R.id.profile_expand_education_btn)).setImageDrawable(mPlusDrawable);
                view = mRootView.findViewById(R.id.profile_education_edit_layout);
                if(view.getVisibility() != View.VISIBLE) {
                    mRootView.findViewById(R.id.profile_education_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expanded_education_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_education_btn).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_education_edit_layout).setVisibility(View.VISIBLE);
                    loadUserCurrentEducationEditLayout();
                    mRootView.findViewById(R.id.profile_education_edit_btn).setContentDescription("Click to stop editing your current education information");
                }else{
                    mRootView.findViewById(R.id.profile_education_layout).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_expanded_education_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_education_btn).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_education_edit_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_education_edit_btn).setContentDescription("Click to edit your current education information");
                }
                break;
            case R.id.profile_preferred_edit_btn:
                ((ImageView)mRootView.findViewById(R.id.profile_expand_preferred_btn)).setImageDrawable(mPlusDrawable);
                view = mRootView.findViewById(R.id.profile_preferred_edit_layout);
                if(view.getVisibility() != View.VISIBLE) {
                    mRootView.findViewById(R.id.profile_preferred_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expanded_preferred_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_preferred_btn).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_preferred_edit_layout).setVisibility(View.VISIBLE);
                    loadUserPreferredInfoEditLayout();
                    mRootView.findViewById(R.id.profile_preferred_edit_btn).setContentDescription("Click to stop editing your profile preferences");
                }else{
                    mRootView.findViewById(R.id.profile_preferred_layout).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_expanded_preferred_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_preferred_btn).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_preferred_edit_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_preferred_edit_btn).setContentDescription("Click to edit your profile preferences");
                }
                break;
            case R.id.profile_exams_edit_btn:
                mRequestForUserExamsUpdate();
                v.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        v.setClickable(true);
                    }
                }, 200);
                break;
            case R.id.profile_other_info_edit_btn:
                ((ImageView)mRootView.findViewById(R.id.profile_expand_exams_btn)).setImageDrawable(mPlusDrawable);
                view = mRootView.findViewById(R.id.profile_other_info_edit_layout);
                if(view.getVisibility() != View.VISIBLE) {
                    mRootView.findViewById(R.id.profile_other_info_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expanded_other_info_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_other_info_btn).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_other_info_edit_layout).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_other_info_edit_btn).setContentDescription("Click to stop editing miscellaneous profile information");
                }else{
                    mRootView.findViewById(R.id.profile_other_info_layout).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_expanded_other_info_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_expand_other_info_btn).setVisibility(View.VISIBLE);
                    mRootView.findViewById(R.id.profile_other_info_edit_layout).setVisibility(View.GONE);
                    mRootView.findViewById(R.id.profile_other_info_edit_btn).setContentDescription("Click to edit miscellaneous profile information");
                }
                loadUserOtherInfoEditLayout();
                break;
            case R.id.profile_info_save_btn:
                mRequestForUpdateInfo();
                break;
            case R.id.profile_education_save_btn:
                mRequestForUpdateCurrentEducation();
                break;
            case R.id.profile_preferred_save_btn:
                mRequestForUpdatePreferredEducation();
                break;
            case R.id.profile_other_info_save_btn:
                mRequestForUpdateOtherInfo();
                break;

            default:
                break;
        }
    }


    /**
     * This method is used to set mDeviceProfile profile info on
     * profile info  page and mDeviceProfile can edit his/her basic info
     *  on this page like name, phone, city, state.
     */
    private void loadUserInfoEditLayout(){
        if (mProfile == null || mRootView == null)
            return;

        String name = mProfile.getName();
        if(name != null && !name.isEmpty() &&  !name.equalsIgnoreCase(getResources().getString(R.string.ANONYMOUS_USER))){
            ((TextView)mRootView.findViewById(R.id.profile_edit_name)).setText(name);
        }

        if(mProfile.getIs_verified() == 1){
            mRootView.findViewById(R.id.profile_edit_phone_til).setVisibility(View.GONE);
        }else{
            mRootView.findViewById(R.id.profile_edit_phone_til).setVisibility(View.VISIBLE);
            String phone = mProfile.getPhone_no();
            if (phone != null && !phone.isEmpty()){
                ((TextView)mRootView.findViewById(R.id.profile_edit_phone)).setText(phone);
            }
        }
        // set mDeviceProfile city and state

        final MaterialSpinner stateSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_state);
        final MaterialSpinner citySpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_city);

        final int stateID = mProfile.getState_id();
        final int cityID = mProfile.getCity_id();

        try {

            final  List<ProfileSpinnerItem> statesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.PROFILE_CITIES);
            List<ProfileSpinnerItem> citiesList = null;

            if(stateID >= 1){
                int stateCount = statesList.size();
                for(int i=0 ; i< stateCount; i++){
                    ProfileSpinnerItem pObj = statesList.get(i);
                    if(pObj == null) continue;
                    if(stateID ==  pObj.getId()){
                        statesList.remove(i);
                        statesList.add(0,pObj);
                        citiesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getCitiJson(pObj.getId()));
                        int cityCount = citiesList.size();
                        for(int j =0 ; j <cityCount; j++){
                            ProfileSpinnerItem pCityObj = citiesList.get(j);
                            if(pCityObj == null) continue;
                            if(cityID ==  pCityObj.getId()) {
                                citiesList.remove(j);
                                citiesList.add(0, pCityObj);
                                break;
                            }
                        }
                        break;
                    }
                }
                stateSpinner.setItems(statesList, false);
                citySpinner.setItems(citiesList, false);
            }else {
                stateSpinner.setItems(statesList, true);
                stateSpinner.setText(getString(R.string.select_state));
            }

            if(citiesList == null){
                citiesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getCitiJson(0));
                citySpinner.setItems(citiesList, true);
                if(citiesList.size() > 1)
                    citySpinner.setText(getString(R.string.select_city));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        stateSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener(){

            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                try {
                    List<ProfileSpinnerItem> citiesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getCitiJson(view.getSelectedSpinnerItemId()));
                    citySpinner.setItems(citiesList, true);
                    if(citiesList.size() > 1)
                        citySpinner.setText(getString(R.string.select_city));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });

        // set DeviceProfile Social Category
        MaterialSpinner socialCategorySpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_category);
        int socialCategoryId = mProfile.getSocial_category();
        ArrayList<ProfileSpinnerItem> socialCategoryList = ProfileMacro.getSocialCategoryList();
        if(socialCategoryId >= 1) {
            int CategoryCount = socialCategoryList.size();
            for (int i = 0; i < CategoryCount; i++) {
                ProfileSpinnerItem pObj = socialCategoryList.get(i);
                if (pObj == null) continue;
                if (socialCategoryId == pObj.getId()) {
                    socialCategoryList.remove(i);
                    socialCategoryList.add(0, pObj);
                    break;
                }
            }
            socialCategorySpinner.setItems(socialCategoryList, false);
        }else{
            socialCategorySpinner.setItems(socialCategoryList, true);
            socialCategorySpinner.setText(getString(R.string.select_category));
        }

        // set mDeviceProfile Mother tongue
        MaterialSpinner motherTongueSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_mother_tongue);
        int motherTongueId = mProfile.getMother_tongue();
        ArrayList<ProfileSpinnerItem> motherTongueList = ProfileMacro.getMotherTongueList();
        if(motherTongueId >= 1) {
            int motherTongueCount = motherTongueList.size();
            for (int i = 0; i < motherTongueCount; i++) {
                ProfileSpinnerItem pObj = motherTongueList.get(i);
                if (pObj == null) continue;
                if (motherTongueId == pObj.getId()) {
                    motherTongueList.remove(i);
                    motherTongueList.add(0, pObj);
                    break;
                }
            }
            motherTongueSpinner.setItems(motherTongueList, false);
        }else{
            motherTongueSpinner.setItems(motherTongueList, true);
            motherTongueSpinner.setText(getString(R.string.select_category));
        }


    }
    /**
     *
     * This method is used to set mDeviceProfile current education info on
     * profile current education page and mDeviceProfile can edit his/her current education
     * info  on this page like name, degree , stream , city, state.
     */

    private void loadUserCurrentEducationEditLayout(){
        if (mProfile == null || mRootView == null)
            return;

        // set sub level
        MaterialSpinner currentSubLevelSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_current_sub_level);
        final MaterialSpinner currentStreamSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_current_stream);
        final MaterialSpinner currentSpecializationSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_current_specialization);
        final MaterialSpinner currentDegreeSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_current_degree);
        final MaterialSpinner currentPassingYearSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_current_passing_year);

        int userSubLevelId = mProfile.getCurrent_sublevel_id();
        int userStreamId = mProfile.getCurrent_stream_id();

        // hide current degree and specialization isf mDeviceProfile has current level is school
        if(userStreamId == 33|| userStreamId == 34 || userStreamId == 35
                || userStreamId == 36 || userStreamId == 37 || userStreamId == 38 || userStreamId == 39){
            currentSpecializationSpinner.setVisibility(View.GONE);
            currentDegreeSpinner.setVisibility(View.GONE);
        }else{
            currentSpecializationSpinner.setVisibility(View.VISIBLE);
            currentDegreeSpinner.setVisibility(View.VISIBLE);
        }

        try {

            List<ProfileSpinnerItem> currentSubLevelList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.SUB_LEVEL_JSON);
            List<ProfileSpinnerItem> currentStreamList = null;
            if(userSubLevelId >= 1){
                int subLevelCount = currentSubLevelList.size();
                boolean isStreamFound = false;
                for(int i=0 ; i< subLevelCount; i++){
                    ProfileSpinnerItem pObj = currentSubLevelList.get(i);
                    if(pObj == null) continue;
                    if(userSubLevelId ==  pObj.getId()){
                        currentSubLevelList.remove(i);
                        currentSubLevelList.add(0,pObj);
                        // request for degrees
                        mRequestForCurrentSubLevelDegreesList(userSubLevelId);

                        currentStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJsonForSubLevel(pObj.getId()));
                        int streamCount = currentStreamList.size();
                        for(int j =0 ; j < streamCount; j++){
                            ProfileSpinnerItem pStreamObj = currentStreamList.get(j);
                            if(pStreamObj == null) continue;
                            if(userStreamId ==  pStreamObj.getId()) {
                                currentStreamList.remove(j);
                                currentStreamList.add(0, pStreamObj);
                                isStreamFound = true;
                                if(mListener != null){
                                    mListener.requestForSpecialization(userStreamId, ProfileMacro.CURRENT_EDUCATION);
                                    currentSpecializationSpinner.setText(getString(R.string.loading));
                                    currentSpecializationSpinner.hideArrow();
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                currentSubLevelSpinner.setItems(currentSubLevelList, false);
                if(isStreamFound) {
                    currentStreamSpinner.setItems(currentStreamList, false);
                }else{
                    currentStreamSpinner.setItems(currentStreamList, true);
                    currentStreamSpinner.setText(getString(R.string.select_stream));
                }
            }
            else{
                currentSubLevelSpinner.setItems(currentSubLevelList, true);
                currentSubLevelSpinner.setText(getString(R.string.select_level));
            }


            if(currentStreamList == null){
                currentStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJsonForSubLevel(mProfile.getCurrent_sublevel_id()));
                currentStreamSpinner.setItems(currentStreamList, true);
                if(currentStreamList.size() > 1)
                    currentStreamSpinner.setText(getString(R.string.select_your_stream));

                if(mListener != null){
                    mListener.requestForSpecialization(userStreamId, ProfileMacro.CURRENT_EDUCATION);
                    currentSpecializationSpinner.setText(getString(R.string.loading));
                    currentSpecializationSpinner.hideArrow();
                }
            }

            currentSubLevelSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                    try {
                        int selectedId = view.getSelectedSpinnerItemId();
                        List<ProfileSpinnerItem> currentStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJsonForSubLevel(selectedId));
                        currentStreamSpinner.setItems(currentStreamList, true);
                        if(currentStreamList.size() > 1)
                            currentStreamSpinner.setText(getString(R.string.select_your_stream));

                        // hide specialization and degree layout if stream is is school level
                        if(selectedId == 7 || selectedId == 8 || selectedId == 9 || selectedId == 15){
                            currentSpecializationSpinner.setVisibility(View.GONE);
                            currentDegreeSpinner.setVisibility(View.GONE);
                        }
                        // request for degrees
                        mRequestForCurrentSubLevelDegreesList(selectedId);

                        // set passing year based on current sublevelID
                        //currentPassingYearSpinner.setSelectedIndex(ProfileMacro.ComputePassingYearIndexOnSelectedSubLevel(selectedId));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set DeviceProfile Current Specialization
        currentStreamSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(mListener == null)
                    return;
                int streamId = view.getSelectedSpinnerItemId();
                if(streamId == 33|| streamId == 34 || streamId == 35
                        || streamId == 36 || streamId == 37 || streamId == 38 || streamId == 39){
                    currentSpecializationSpinner.setVisibility(View.GONE);
                    currentDegreeSpinner.setVisibility(View.GONE);
                }else{
                    currentSpecializationSpinner.setVisibility(View.VISIBLE);
                    currentDegreeSpinner.setVisibility(View.VISIBLE);
                }
                mListener.requestForSpecialization(view.getSelectedSpinnerItemId(), ProfileMacro.CURRENT_EDUCATION);
                currentSpecializationSpinner.setText(getString(R.string.loading));
                currentSpecializationSpinner.hideArrow();
            }
        });



        // set mDeviceProfile current degree
        int userCurrentDegreeId = mProfile.getCurrent_degree_id();
        try {
            List<ProfileSpinnerItem> currentDegreeList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.DEGREE_JSON);

            if(userCurrentDegreeId >= 1){
                int degreeCount = currentDegreeList.size();
                for(int i=0 ; i< degreeCount; i++){
                    ProfileSpinnerItem pObj = currentDegreeList.get(i);
                    if(pObj == null) continue;
                    if(userCurrentDegreeId ==  pObj.getId()){
                        currentDegreeList.remove(i);
                        currentDegreeList.add(0,pObj);
                        break;
                    }
                }
                currentDegreeSpinner.setItems(currentDegreeList, false);
            }
            else{
                currentDegreeSpinner.setItems(currentDegreeList, true);
                currentDegreeSpinner.setText(getString(R.string.select_degree));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // set education mode
        int  currentEducationModeId = mProfile.getCurrent_mode();
        MaterialSpinner educationModeSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_current_mode);
        ArrayList<ProfileSpinnerItem> educationModeList = ProfileMacro.getEducationModeList();
        if(currentEducationModeId >= 1) {
            int educationModeCount = educationModeList.size();
            for (int i = 0; i < educationModeCount; i++) {
                ProfileSpinnerItem pObj = educationModeList.get(i);
                if (pObj == null) continue;
                if (currentEducationModeId == pObj.getId()) {
                    educationModeList.remove(i);
                    educationModeList.add(0, pObj);
                    break;
                }
            }
            educationModeSpinner.setItems(educationModeList, false);
        }else{
            educationModeSpinner.setItems(educationModeList, true);
            educationModeSpinner.setText(getString(R.string.select_education_mode));
        }

        // set Current Score

        int currentScore = mProfile.getCurrent_score();
        if(currentScore > 0){
            ((EditText)mRootView.findViewById(R.id.profile_edit_current_score)).setText(String.valueOf(currentScore));
        }

        // set Current Score type

        ArrayList<ProfileSpinnerItem> scoreTypeList = new ArrayList<>();
        for(int i= 1; i < 6; i++){
            ProfileSpinnerItem baseObject = new ProfileSpinnerItem();
            baseObject.setId(i);
            baseObject.setName(ProfileMacro.getCurrentScoreTypeName(i));
            scoreTypeList.add(baseObject);
        }

        scoreTypeList.add(0,scoreTypeList.remove(2));
        int currentScoreType = mProfile.getCurrent_score_type();
//        boolean isScoretypeSelected = false;
        if(currentScoreType == ProfileMacro.MARKS){
            scoreTypeList.add(0,scoreTypeList.remove(1));
        }else if(currentScoreType == ProfileMacro.GRADES){
            scoreTypeList.add(0,scoreTypeList.remove(2));
        //}else if(currentScoreType == ProfileMacro.PERCENTAGE){
            // scoreTypeList.add(0,scoreTypeList.remove(2));
        }else if(currentScoreType == ProfileMacro.RANK){
            scoreTypeList.add(0,scoreTypeList.remove(3));
        }else if(currentScoreType == ProfileMacro.PERCENTILE){
            scoreTypeList.add(0,scoreTypeList.remove(4));
        }


        MaterialSpinner currentScoreTypeSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_current_score_type);
//        if(isScoretypeSelected) {
//            currentScoreTypeSpinner.setItems(scoreTypeList, true);
//            if(mProfile.getCurrent_level_id() == ProfileMacro.LEVEL_TWELFTH || mProfile.getCurrent_level_id() == ProfileMacro.LEVEL_TENTH) {
//                currentScoreTypeSpinner.setText("Percentage");
//            } else {
//                currentScoreTypeSpinner.setText("Select Score Type");
//            }
//        }else{
        currentScoreTypeSpinner.setItems(scoreTypeList, false);
//            if(mProfile.getCurrent_level_id() == ProfileMacro.LEVEL_TWELFTH || mProfile.getCurrent_level_id() == ProfileMacro.LEVEL_TENTH) {
//                currentScoreTypeSpinner.setText("Percentage");
//            }
//        }

        // set current passing year info about mDeviceProfile
        int currentPassingYear = mProfile.getCurrent_passing_year();
        ArrayList<ProfileSpinnerItem> passingYearList = ProfileMacro.getCurrentPassingYearList();
        if(currentPassingYear >= 1) {
            int yearCount = passingYearList.size();
            for (int i = 0; i < yearCount; i++) {
                ProfileSpinnerItem pObj = passingYearList.get(i);
                if (pObj == null) continue;
                if (String.valueOf(currentPassingYear).equals( pObj.getName())) {
                    passingYearList.remove(i);
                    passingYearList.add(0, pObj);
                    break;
                }
            }
            currentPassingYearSpinner.setItems(passingYearList, false);
        }else{
            currentPassingYearSpinner.setItems(passingYearList, false);

            try
            {
                //select current year as preferred year for passing the current level
                int indexForCurrentYear = ProfileMacro.GetYearIndexInPassingYearList(Utils.GetCurrentYear());

                if (indexForCurrentYear >= 0)
                    currentPassingYearSpinner.setSelectedIndex(indexForCurrentYear);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     *
     * This method is used to set mDeviceProfile Preferred education details on
     * profile current education page and mDeviceProfile can edit his/her Preferred education
     * info  on this page like degree, stream , specialization , city, state.
     */
    private void loadUserPreferredInfoEditLayout(){
        if(mRootView == null || mProfile == null)
            return;
        MaterialSpinner preferredLevelSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_level);
        final MaterialSpinner preferredStreamSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_stream);
        final MaterialSpinner preferredSpecializationSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_specialization);

        // set mDeviceProfile preferred level
        int preferredLevelId = mProfile.getPreferred_level();
        int preferredStreamId = mProfile.getPreferred_stream_id();
        List<ProfileSpinnerItem> streamList = null;
        try {
            List<ProfileSpinnerItem> levelList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.PREF_LEVEL_JSON);
            if(preferredLevelId >= 1){
                int levelCount = levelList.size();
                boolean isStreamFound = false;
                for(int i=0 ; i< levelCount; i++){
                    ProfileSpinnerItem pLevelObj = levelList.get(i);
                    if(pLevelObj == null) continue;
                    if(preferredLevelId ==  pLevelObj.getId()){
                        levelList.remove(i);
                        levelList.add(0,pLevelObj);
                        // request for degrees
                        if(mListener != null) {
                            mListener.requestForDegrees(preferredLevelId, ProfileMacro.PREFERRED_EDUCATION);
                        }

                        streamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(pLevelObj.getId()));
                        int streamCount = streamList.size();
                        for(int j =0 ; j < streamCount; j++){
                            ProfileSpinnerItem pStreamObj = streamList.get(j);
                            if(pStreamObj == null) continue;
                            if(preferredStreamId ==  pStreamObj.getId()) {
                                streamList.remove(j);
                                streamList.add(0, pStreamObj);
                                isStreamFound = true;
                                if (mListener != null) {
                                    mListener.requestForSpecialization(preferredStreamId, ProfileMacro.PREFERRED_EDUCATION );
                                    preferredSpecializationSpinner.setText(getString(R.string.loading));
                                    preferredSpecializationSpinner.hideArrow();
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                preferredLevelSpinner.setItems(levelList, false);
                if(isStreamFound) {
                    preferredStreamSpinner.setItems(streamList, false);
                }else {
                    preferredStreamSpinner.setItems(streamList, false);
                    preferredStreamSpinner.setText(getString(R.string.select_stream));
                }
            }
            else{
                preferredLevelSpinner.setItems(levelList, true);
                preferredLevelSpinner.setText(getString(R.string.select_level));
            }

            preferredLevelSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                    try {
                        int levelId = view.getSelectedSpinnerItemId();
                        List<ProfileSpinnerItem> currentStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(levelId));
                        preferredStreamSpinner.setItems(currentStreamList, false);
                        if(currentStreamList.size() > 1)
                            preferredStreamSpinner.setText(getString(R.string.select_stream));
                        if(mListener != null) {
                            mListener.requestForDegrees(levelId, ProfileMacro.PREFERRED_EDUCATION);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        // set Preferred Stream
        if(streamList == null) {
            try {
                List<ProfileSpinnerItem> allStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(1));
                List<ProfileSpinnerItem> streamList2 = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(7));
                List<ProfileSpinnerItem> streamList3 = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(8));
                allStreamList.addAll(streamList2);
                allStreamList.addAll(streamList3);

                if (preferredStreamId > 0) {
                    int streamCount = allStreamList.size();
                    boolean isStreamFound = false;
                    for (int j = 0; j < streamCount; j++) {
                        ProfileSpinnerItem pStreamObj = allStreamList.get(j);
                        if (pStreamObj == null) continue;
                        if (preferredStreamId == pStreamObj.getId()) {
                            allStreamList.remove(j);
                            allStreamList.add(0, pStreamObj);
                            isStreamFound = true;
                            if (mListener != null) {
                                mListener.requestForSpecialization(preferredStreamId, ProfileMacro.PREFERRED_EDUCATION );
                                preferredSpecializationSpinner.setText(getString(R.string.loading));
                                preferredSpecializationSpinner.hideArrow();
                            }
                            break;
                        }
                    }
                    if(isStreamFound) {
                        preferredStreamSpinner.setItems(allStreamList, false);
                    }
                    else{
                        preferredStreamSpinner.setItems(allStreamList, true);
                        preferredStreamSpinner.setText(getString(R.string.select_stream));
                    }
                } else {
                    preferredStreamSpinner.setItems(allStreamList, true);
                    preferredStreamSpinner.setText(getString(R.string.select_stream));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // set preferred Specialization
        preferredStreamSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                if(mListener == null)
                    return;
                mListener.requestForSpecialization(view.getSelectedSpinnerItemId(),ProfileMacro.PREFERRED_EDUCATION );
                preferredSpecializationSpinner.setText(R.string.loading);
                preferredSpecializationSpinner.hideArrow();
            }
        });


        // set preferred degree
        try {
            mPreferredDegreesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.DEGREE_JSON);

            ArrayList<Integer> degreesIdList = mProfile.getPreferred_degrees_ids();
            if(degreesIdList != null && mPreferredDegreesList != null && !degreesIdList.isEmpty()){
                for (int id :degreesIdList ) {
                    int count = mPreferredDegreesList.size();
                    for(int i =0 ; i < count ; i++){
                        ProfileSpinnerItem obj = mPreferredDegreesList.get(i);
                        if(obj == null)continue;
                        if( obj.getId() == id){
                            mPreferredDegreesList.add(0,mPreferredDegreesList.remove(i));
                            obj.setSelected(true);
                            break;
                        }
                    }
                }
            }

            MaterialSpinner preferredDegreeSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_degree);
            preferredDegreeSpinner.setMutliSelection(true);
            preferredDegreeSpinner.setFragmentListener(this);
            preferredDegreeSpinner.setItems(mPreferredDegreesList, true);
            if(degreesIdList != null && mPreferredDegreesList.size() > 1)
                preferredDegreeSpinner.setText("Degree("+degreesIdList.size()+")");



        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            mPreferredStatesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.PROFILE_CITIES);
            ArrayList<Integer> stateIdList = mProfile.getPreferred_states_ids();
            if(stateIdList != null && mPreferredStatesList != null && !stateIdList.isEmpty()){
                for (int id :stateIdList ) {
                    int count = mPreferredStatesList.size();
                    for(int i =0 ; i < count ; i++){
                        ProfileSpinnerItem obj = mPreferredStatesList.get(i);
                        if(obj == null)continue;
                        if( obj.getId() == id){
                            mPreferredStatesList.add(0,mPreferredStatesList.remove(i));
                            obj.setSelected(true);
                            break;
                        }
                    }
                }
            }
            MaterialSpinner preferredStateSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_state);
            preferredStateSpinner.setMutliSelection(true);
            preferredStateSpinner.setItems(mPreferredStatesList, true);
            preferredStateSpinner.setFragmentListener(this);
            if(stateIdList != null)
                preferredStateSpinner.setText("State("+stateIdList.size()+")");


            // set preferred Cities
            if(stateIdList != null && !stateIdList.isEmpty()){
                if(mPreferredCitiesList == null)
                    mPreferredCitiesList = new ArrayList<>();
                for (int stateId:stateIdList) {
                    mPreferredCitiesList.addAll(JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getCitiJson(stateId)));
                }
            }else{
                mPreferredCitiesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getCitiJson(0));
            }

            ArrayList<Integer> cityIdList = mProfile.getPreferred_cities_ids();
            if(cityIdList != null && mPreferredCitiesList != null && !cityIdList.isEmpty()){
                for (int id :cityIdList ) {
                    int count = mPreferredCitiesList.size();
                    for(int i =0 ; i < count ; i++){
                        ProfileSpinnerItem obj = mPreferredCitiesList.get(i);
                        if(obj == null)continue;
                        if( obj.getId() == id){
                            mPreferredCitiesList.add(0,mPreferredCitiesList.remove(i));
                            obj.setSelected(true);
                            break;
                        }
                    }
                }
            }

            final MaterialSpinner preferredCitySpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_city);
            preferredCitySpinner.setMutliSelection(true);
            preferredCitySpinner.setFragmentListener(this);
            preferredCitySpinner.setItems(mPreferredCitiesList, true);
            if(cityIdList != null && mPreferredCitiesList.size() > 1)
                preferredCitySpinner.setText("City("+cityIdList.size()+")");

        }catch (Exception e){
            e.printStackTrace();
        }

        // set Preferred Year
        int  preferredAdmissionYear = mProfile.getPreferred_year_of_admission();
        MaterialSpinner preferredYearSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_Admission_year);
        ArrayList<ProfileSpinnerItem> preferredAdmissionList = ProfileMacro.getPreferredAdmissionYearList();
        if(preferredAdmissionYear >= 1) {
            int yearCount = preferredAdmissionList.size();
            for (int i = 0; i < yearCount; i++) {
                ProfileSpinnerItem pObj = preferredAdmissionList.get(i);
                if (pObj == null) continue;
                if (preferredAdmissionYear == pObj.getId()) {
                    preferredAdmissionList.remove(i);
                    preferredAdmissionList.add(0, pObj);
                    break;
                }
            }
            preferredYearSpinner.setItems(preferredAdmissionList, false);
        }else{
            preferredYearSpinner.setItems(preferredAdmissionList, true);
            //preferredYearSpinner.setText("Preferred Year");

            try
            {
                //set current passing year, if set
                //else set current year as passing year if nothing is set in the profile
                int preferredCurrentPassingYear = MainActivity.mProfile.getCurrent_passing_year();

                if (preferredCurrentPassingYear > 0)
                    preferredYearSpinner.setSelectedIndex(ProfileMacro.GetYearIndexInAddmissionYearList(preferredCurrentPassingYear));
                else
                    preferredYearSpinner.setSelectedIndex(Utils.GetCurrentYear());
            }
            catch (Exception e)
            {
              e.printStackTrace();
            }
        }

        // set preferred education mode
        int  preferredEducationModeId = mProfile.getPreferred_mode();
        MaterialSpinner educationModeSpinner = (MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_mode);
        ArrayList<ProfileSpinnerItem> educationModeList = ProfileMacro.getEducationModeList();
        if(preferredEducationModeId >= 1) {
            int educationModeCount = educationModeList.size();
            for (int i = 0; i < educationModeCount; i++) {
                ProfileSpinnerItem pObj = educationModeList.get(i);
                if (pObj == null) continue;
                if (preferredEducationModeId == pObj.getId()) {
                    educationModeList.remove(i);
                    educationModeList.add(0, pObj);
                    break;
                }
            }
            educationModeSpinner.setItems(educationModeList, false);
        }else{
            educationModeSpinner.setItems(educationModeList, true);
            educationModeSpinner.setText(getString(R.string.select_education_mode));
        }


        // set preferred fee range
        int  maxFeeRange = mProfile.getPreferred_fee_range_max();
        MaterialSpinner feesRangeSpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_feee_range);
        ArrayList<ProfileSpinnerItem> feesRangeList = ProfileMacro.getFeesRangeList();
        if(maxFeeRange >= 10) {
            int feesRangeCount = feesRangeList.size();
            for (int i = feesRangeCount-1; i >= 0; i--) {
                ProfileSpinnerItem pObj = feesRangeList.get(i);
                if (pObj == null) continue;
                if (maxFeeRange >= pObj.getId()) {
                    feesRangeList.remove(i);
                    feesRangeList.add(0, pObj);
                    break;
                }
            }
            feesRangeSpinner.setItems(feesRangeList, false);
        }else{
            feesRangeSpinner.setItems(feesRangeList, true);
            feesRangeSpinner.setText(getString(R.string.select_fee_range));
        }

        // set preferred loan required
        //int  loanRequiredId = mProfile.getPreferred_loan_required();

        ArrayList<ProfileSpinnerItem> loanRequiredAmountList = new ArrayList<>();
        for (int i = 1; i < 6; i++) {
            ProfileSpinnerItem baseObject = new ProfileSpinnerItem();
            baseObject.setId(i);
            baseObject.setName(ProfileMacro.getLoanRequiredAmount(i));
            loanRequiredAmountList.add(baseObject);
        }
        int loanAmountNeededId = mProfile.getPreferred_loan_amount_needed();
       // boolean isLoanAmountSelected = false;
        //if (loanAmountNeededId == ProfileMacro.BELOW_ONE_LAKH) {}
       if (loanAmountNeededId == ProfileMacro.ONE_TO_THREE_LAKH) {
            loanRequiredAmountList.add(0, loanRequiredAmountList.remove(1));
        } else if (loanAmountNeededId == ProfileMacro.THREE_TO_FIVE_LAKH) {
            loanRequiredAmountList.add(0, loanRequiredAmountList.remove(2));
        } else if (loanAmountNeededId == ProfileMacro.ABOVE_FIVE_LAKH) {
            loanRequiredAmountList.add(0, loanRequiredAmountList.remove(3));
        } else if (loanAmountNeededId == ProfileMacro.TO_BE_DECIDED) {
            loanRequiredAmountList.add(0, loanRequiredAmountList.remove(4));
        } /*else {
            isLoanAmountSelected = true;
        }*/

      /*  MaterialSpinner loanAmountSpinner = (MaterialSpinner) mRootView.findViewById(R.id.profile_edit_loan_amount_needed);
        if(isLoanAmountSelected) {
            loanAmountSpinner.setItems(loanRequiredAmountList, true);
            loanAmountSpinner.setText("Select Score Type");
        }else{
            loanAmountSpinner.setItems(loanRequiredAmountList, false);
        }*/
    }


    private void loadUserOtherInfoEditLayout()
    {
        if(mRootView == null || mProfile == null)
            return;

        String fatherName = mProfile.getFathers_name();
        ((EditText)mRootView.findViewById(R.id.profile_edit_father_name)).setText(fatherName);

        String motherName = mProfile.getMothers_name();
        ((EditText)mRootView.findViewById(R.id.profile_edit_mother_name)).setText(motherName);

        String coachingInstitute = mProfile.getCoaching_institute();
        ((EditText)mRootView.findViewById(R.id.profile_edit_coaching_institute)).setText(coachingInstitute);
    }

    private void mRequestForUpdateInfo(){
        String userName = ((EditText) mRootView.findViewById(R.id.profile_edit_name)).getText().toString();
        if (userName.isEmpty() && mProfile != null){
            userName = mProfile.getName();
        }
        String userPhoneNumber ="";
        if(mProfile != null) {
            userPhoneNumber = mProfile.getPhone_no();
            if (mProfile.getIs_verified() != 1) {
                userPhoneNumber = ((EditText) mRootView.findViewById(R.id.profile_edit_phone)).getText().toString();
                if ( userPhoneNumber.length() > 0) {
                    if (userPhoneNumber.length() <= 9 || !Utils.isValidPhone(userPhoneNumber)) {
                        Utils.DisplayToast(getContext(), "Please enter a valid phone number.");
                        return;
                    }
                }
            }
        }
        String userStateIdValue = "";
        int userStateId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_state)).getSelectedSpinnerItemId();
        if (userStateId > 0) {
            userStateIdValue = userStateIdValue+userStateId ;
        }
        String userCityIdValue = "";
        int userCityId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_city)).getSelectedSpinnerItemId();
        if (userCityId > 0) {
            userCityIdValue = userCityIdValue +userCityId;
        }else{
            if(mProfile != null && mProfile.getCity_id() > 0)
                userCityIdValue = ""+mProfile.getCity_id();
        }
        String userSocialCategoryIdValue ="";
        int userSocialCategoryId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_category)).getSelectedSpinnerItemId();
        if (userSocialCategoryId > 0) {
            userSocialCategoryIdValue = userSocialCategoryIdValue+userSocialCategoryId;
        }
        String userMotherTongueIdValue = "";
        int userMotherTongueId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_mother_tongue)).getSelectedSpinnerItemId();
        if (userMotherTongueId > 0) {
            userMotherTongueIdValue = userMotherTongueIdValue+userMotherTongueId;
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("name", userName);
        params.put("phone_no", userPhoneNumber);
        params.put("state_id", userStateIdValue);
        params.put("city_id", userCityIdValue);
        params.put("social_category", userSocialCategoryIdValue);
        params.put("mother_tongue",userMotherTongueIdValue);
        mListener.onUserProfileEdited(params,0);
    }
    /**
     * This method is used to request to update mDeviceProfile current
     *  education info like education level , degree, state, city
     *
     */

    private void mRequestForUpdateCurrentEducation(){
        String currentSubLevelIdValue ="";
        int userCurrentSubLevelId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_sub_level)).getSelectedSpinnerItemId();
        if (userCurrentSubLevelId < 0) {
            Utils.DisplayToast(getContext(), "Please Select your SubLevel.");
            return;
        }else{
            currentSubLevelIdValue  +=  userCurrentSubLevelId;
        }
        String currentStreamIdValue ="";
        int userCurrentStreamId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_stream)).getSelectedSpinnerItemId();
        if (userCurrentStreamId < 0) {
            Utils.DisplayToast(getContext(), "Please Select your Stream.");
            return;
        }else{
            currentStreamIdValue += userCurrentStreamId;
        }
        String currentSpecializationIdValue ="";
        if(!(userCurrentStreamId == 33 ||userCurrentStreamId == 34 || userCurrentStreamId == 35 || userCurrentStreamId == 36
                || userCurrentStreamId == 37 || userCurrentStreamId == 38 || userCurrentStreamId == 39)){
            int userCurrentSpecializationId= ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_specialization)).getSelectedSpinnerItemId();
            if (userCurrentSpecializationId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Specialization.");
                return;
            }else{
                     currentSpecializationIdValue +=userCurrentSpecializationId ;
            }
        }
        String currentDegreeIdValue ="";
        if(!(userCurrentStreamId == 33 ||userCurrentStreamId == 34 ||  userCurrentStreamId == 35 || userCurrentStreamId == 36
                || userCurrentStreamId == 37 || userCurrentStreamId == 38 || userCurrentStreamId == 39) ){
            int currentDegreeId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_degree)).getSelectedSpinnerItemId();
            if (currentDegreeId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Degree.");
                return;
            }else{
                currentDegreeIdValue += currentDegreeId;
            }
        }
        String currentScore = ((EditText) mRootView.findViewById(R.id.profile_edit_current_score)).getText().toString();

        String currentScoreIdValue ="";
        int currentScoreId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_score_type)).getSelectedSpinnerItemId();
        if(currentScoreId > 0){
            currentScoreIdValue += currentScoreId;
        }
        String currentModeIdValue ="";
        int currentModeId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_mode)).getSelectedSpinnerItemId();
        if (currentModeId > 0) {
            currentModeIdValue += currentModeId;
        }

        String currentPassingYear = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_passing_year)).getSelectedSpinnerItemName();

        HashMap<String, String> params = new HashMap<>();
        params.put("current_sublevel_id",currentSubLevelIdValue);
        params.put("current_mode", currentModeIdValue);
        params.put("current_stream_id", currentStreamIdValue);
        params.put("current_degree_id", currentDegreeIdValue);
        params.put("current_specialization_id", currentSpecializationIdValue);
        params.put("current_score", currentScore);
        params.put("current_score_type", currentScoreIdValue);
        params.put("current_passing_year", currentPassingYear);

        //set preferred year of admission for mDeviceProfile if current passing year is equal to or greater than current year
        //because you can't travel back in time and take admission
        //and if can travel back in time why would you study there
        if (Integer.parseInt(currentPassingYear) >= Utils.GetCurrentYear())
            params.put("preferred_year_of_admission", currentPassingYear);

        mListener.onUserProfileEdited(params, 1);
    }

    /**
     * This method is used to request to update mDeviceProfile preferred
     *  education info like education level , degree, state, city
     *
     */

    private void mRequestForUpdatePreferredEducation(){

        String  preferredModeValue ="";
        int preferredMode = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_mode)).getSelectedSpinnerItemId();
        if (preferredMode > 0) {
            preferredModeValue += preferredMode;
        }
        String preferredYearValue = "";
        int preferredYear= ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_Admission_year)).getSelectedSpinnerItemId();
        if (preferredYear > 0) {
            preferredYearValue +=  preferredYear;
        }
        String preferredSubLevelValue ="";
        int preferredSubLevelId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_level)).getSelectedSpinnerItemId();
        if (preferredSubLevelId  > 0) {
            preferredSubLevelValue += preferredSubLevelId;
        }
        String preferredStreamIdValue ="";
        int preferredStreamId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_stream)).getSelectedSpinnerItemId();
       /* if (preferredStreamId > 0) {
            preferredStreamIdValue += preferredStreamId;
        }else{
            if(mProfile != null && mProfile.getPreferred_stream_id() > 0)
                preferredStreamIdValue = ""+mProfile.getPreferred_stream_id();
        }*/
        if (preferredStreamId < 0) {
            Utils.DisplayToast(getContext(), "Please Select your Stream.");
            return;
        }
        else{
            preferredStreamIdValue += preferredStreamId;
        }
        String preferredSpecializationIdvalue ="";
        int preferredSpecializationId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_specialization)).getSelectedSpinnerItemId();
        if (preferredSpecializationId  >0) {
            preferredSpecializationIdvalue +=preferredSpecializationId ;
        }
        else{
            if(mProfile != null && mProfile.getPreferred_specialization_id() > 0)
                preferredSpecializationIdvalue = ""+mProfile.getPreferred_specialization_id();
        }
       /* if (preferredSpecializationId < 0) {
            Utils.DisplayToast(getContext(), "Please Select your Specialization.");
            return;
        }else{
            preferredSpecializationIdvalue +=preferredSpecializationId ;
        }*/
        String feeRangeMaxValue ="";
        int feeRangeMax = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_feee_range)).getSelectedSpinnerItemId();
        if (feeRangeMax > 0) {
            feeRangeMaxValue += feeRangeMax ;
        }

       /* int loanRequiredButtonId = ((SegmentedGroup) mRootView.findViewById(R.id.profile_loan_required_group)).getCheckedRadioButtonId();
        RadioButton loanRequiredButton = (RadioButton) mRootView.findViewById(loanRequiredButtonId);
        String loanRequired = loanRequiredButton.getTag().toString();

        int userPreferredLoanId =0;
        try {
            if (Integer.parseInt(loanRequired ) >= 1) {
                userPreferredLoanId = ((MaterialSpinner) mRootView.findViewById(R.id.profile_edit_loan_amount_needed)).getSelectedSpinnerItemId();
                if (userPreferredLoanId < 0) {
                    Utils.DisplayToast(getContext(), "Please Select your Loan Amount.");
                    return;
                }
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
        }*/
        // get selected degree ids List
        StringBuilder degreeIds = new StringBuilder("[");
        if(mPreferredDegreesList != null ){
            boolean isFirstTime = true;
            int count = mPreferredDegreesList.size();
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem dObj = mPreferredDegreesList.get(i);
                if(dObj == null || !dObj.isSelected())continue;
                if(!isFirstTime)
                    degreeIds.append(",").append(dObj.getId());
                else {
                    isFirstTime = false;
                    degreeIds.append(dObj.getId());
                }
            }
        }
        degreeIds.append("]");
        // get selected State ids List
        StringBuilder stateIds = new StringBuilder("[");
        if(mPreferredStatesList != null ){
            int count = mPreferredStatesList.size();
            boolean isFirstTime = true;
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem dObj = mPreferredStatesList.get(i);
                if(dObj == null || !dObj.isSelected())continue;
                if(!isFirstTime)
                    stateIds.append(",").append(dObj.getId());
                else {
                    isFirstTime = false;
                    stateIds.append(dObj.getId());
                }
            }
        }
        stateIds.append("]");
        // get selected State ids List
        StringBuilder cityIds = new StringBuilder("[");
        if(mPreferredCitiesList != null ){
            boolean isFirstTime = true;
            int count = mPreferredCitiesList.size();
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem dObj = mPreferredCitiesList.get(i);
                if(dObj == null || !dObj.isSelected())continue;
                if(!isFirstTime)
                    cityIds.append(",").append(dObj.getId());
                else {
                    isFirstTime = false;
                    cityIds.append(dObj.getId());
                }
            }
        }
        cityIds.append("]");


        HashMap<String, String> params = new HashMap<>();
        params.put("preferred_year_of_admission", preferredYearValue);
        params.put("preferred_mode", preferredModeValue);
        params.put("preferred_fee_range_max",feeRangeMaxValue);
        params.put("preferred_level", preferredSubLevelValue);
        params.put("preferred_stream_id", preferredStreamIdValue);
        params.put("preferred_specialization_id", preferredSpecializationIdvalue);
        params.put("preferred_degrees_ids", "" + degreeIds.toString());
        params.put("preferred_states_ids", "" + stateIds.toString());
        params.put("preferred_cities_ids", "" + cityIds.toString());
        //params.put("preferred_loan_required", loanRequired);
        //params.put("preferred_loan_amount_needed", ""+userPreferredLoanId);
        mListener.onUserProfileEdited(params, 2);
    }

    /**
     * This method is used to request to update mDeviceProfile other info like
     * father name , mother name and coaching institute
     */
    private void mRequestForUpdateOtherInfo() {
        String fatherName = ((EditText) mRootView.findViewById(R.id.profile_edit_father_name)).getText().toString().trim();
        if (fatherName.isEmpty()){
            fatherName = mProfile.getFathers_name();
        }
        String motherName = ((EditText) mRootView.findViewById(R.id.profile_edit_mother_name)).getText().toString().trim();
        if (motherName.isEmpty()){
            motherName = mProfile.getMothers_name();
        }
        String coachingInstitute = ((EditText) mRootView.findViewById(R.id.profile_edit_coaching_institute)).getText().toString().trim();
        if (coachingInstitute.isEmpty()){
            coachingInstitute = mProfile.getCoaching_institute();
        }
        HashMap<String, String> params = new HashMap<>();
        if(fatherName != null)
        params.put("fathers_name", fatherName);
        if(motherName != null)
        params.put("mothers_name", motherName);
        if(coachingInstitute != null)
        params.put("coaching_institute", coachingInstitute);
        mListener.onUserProfileEdited(params, 4);
    }


    public void onDismissStatePopUpWindow(){

        if(mRootView == null || mPreferredStatesList == null)
            return;

        int selectedStateCount = 0;
        if(!mPreferredStatesList.isEmpty()) {
            mPreferredCitiesList.clear();
            for (ProfileSpinnerItem pObj : mPreferredStatesList) {
                try {
                    if(pObj.isSelected()) {
                        mPreferredCitiesList.addAll(JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getCitiJson(pObj.getId())));
                        selectedStateCount++;
                    } } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        ((MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_state)).setText("State("+selectedStateCount+")");
        MaterialSpinner preferredCitySpinner = (MaterialSpinner)mRootView. findViewById(R.id.profile_edit_preferred_city);
        preferredCitySpinner.setMutliSelection(true);
        preferredCitySpinner.setItems(mPreferredCitiesList, true);
        if(!mPreferredCitiesList.isEmpty()){
            int selectedCityCount = 0;
            for (ProfileSpinnerItem pObj : mPreferredCitiesList) {
                if(pObj.isSelected())
                    selectedCityCount++;
            }
            if(mPreferredCitiesList.size() > 1)
                preferredCitySpinner.setText("City("+selectedCityCount+")");
        }

    }

    public void onDismissCityPopUpWindow(){
        View view = getView();
        if(view == null)
            return;

        if(mPreferredCitiesList != null && !mPreferredCitiesList.isEmpty()){
            int selectedCityCount = 0;
            for (ProfileSpinnerItem pObj : mPreferredCitiesList) {
                if(pObj.isSelected())
                    selectedCityCount++;
            }
            ((MaterialSpinner)view. findViewById(R.id.profile_edit_preferred_city)).setText("City("+selectedCityCount+")");
        }
    }


    public void onDismissDegreePopUpWindow(){
        View view = getView();
        if(view == null)
            return;

        if(mPreferredDegreesList != null && !mPreferredDegreesList.isEmpty()){
            int selectedCityCount = 0;
            for (ProfileSpinnerItem pObj : mPreferredDegreesList) {
                if(pObj.isSelected())
                    selectedCityCount++;
            }
            ((MaterialSpinner)view. findViewById(R.id.profile_edit_preferred_degree)).setText("Degree("+selectedCityCount+")");
        }
    }



    public void setProfileProgressStatus(View progressbar, int progress)
    {
        if (progressbar instanceof ProgressBar)
        {
            ((ProgressBar)progressbar).setMax(100);
            ((ProgressBar) progressbar).setProgress(0);

            ObjectAnimator animation = ObjectAnimator.ofInt(progressbar, "progress", progress);
            animation.setDuration(1000);
            animation.setInterpolator(new AnticipateOvershootInterpolator());
            animation.start();
        }
        else if (progressbar instanceof CircularProgressBar)
        {
            ((CircularProgressBar) progressbar).setProgress(0);
            ((CircularProgressBar) progressbar).setProgressWithAnimation(progress, 2000);
        }
    }
    private void checkMediaFilePermission(){

        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.media_file_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    Constants.RC_HANDLE_STORAGE_PERM);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();

        }else {
            mRequestForImageCapture();
        }
    }


    public void mRequestForImageCapture() {

        // Determine Uri of camera image to save.
        File uploadTempImageFile = new File(Environment.getExternalStorageDirectory(),
                "ic_" + mProfile.getId() + "_profile.jpg");
        mImageCaptureUri = Uri.fromFile(uploadTempImageFile);

        // Camera.
        List<Intent> cameraIntents = new ArrayList<>();
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
            cameraIntents.add(intent);
        }
        // Filesystem.
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Chooser of filesystem options.
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
        getActivity().startActivityForResult(chooserIntent, Crop.REQUEST_PICK);
    }


    @Override
    public void requestForCropProfileImage(Intent result) {
        Uri source;
        if(result != null){
            source = result.getData();
        }else{
            source = mImageCaptureUri;
        }
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity());
    }


    @Override
    public void uploadUserProfileImage(Uri uri) {

        if(uri == null)
            return;
        // set bitmap to profile image
        if(getView() != null) {
            CircularImageView profileImage   = (CircularImageView) getView().findViewById(R.id.profile_image);
            profileImage.setImageURI(uri);
        }

        File imageFile = new File(uri.getPath());
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(imageFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Bitmap bm = BitmapFactory.decodeStream(fis);
        int width = bm.getWidth();
        int height = bm.getHeight();
        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio > 1) {
            width = 700;
            height = (int) (width / bitmapRatio);
        } else {
            height = 600;
            width = (int) (height * bitmapRatio);
        }

        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bm, width, height, true);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 100 , byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        // delete  temp created profile image
        deleteTempImageFile();
        // request to upload profile image file
        if(mListener != null) {
            mListener.requestToUploadProfileImage(byteArray);
        }
    }

    @Override
    public void deleteTempImageFile() {
        if(mImageCaptureUri != null) {
            File imageFile = new File(mImageCaptureUri.getPath());
            if(imageFile.exists()){
                imageFile.delete();
            }
        }
    }


    public void updateProfileImage(){
        if(mRootView != null && MainActivity.mProfile != null) {
            // set profile error image
            CircularImageView mProfileImage = (CircularImageView) mRootView.findViewById(R.id.profile_image);
            mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
            mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

            String image = MainActivity.mProfile.getImage();
            mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
        }
    }


    public void profileUpdatedSuccessfully(int viewPosition){
        updateUserProfile();
        if(viewPosition == 0){
            mRootView.findViewById(R.id.profile_info_layout).setVisibility(View.VISIBLE);
            ((ImageView)mRootView.findViewById(R.id.profile_expand_info_btn)).setImageDrawable(mPlusDrawable);
            mRootView.findViewById(R.id.profile_expand_info_btn).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.profile_info_edit_layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.profile_expanded_info_layout).setVisibility(View.GONE);
        }else if(viewPosition == 1){
            mRootView.findViewById(R.id.profile_education_layout).setVisibility(View.VISIBLE);
            ((ImageView)mRootView.findViewById(R.id.profile_expand_education_btn)).setImageDrawable(mPlusDrawable);
            mRootView.findViewById(R.id.profile_expand_education_btn).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.profile_education_edit_layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.profile_expanded_education_layout).setVisibility(View.GONE);
        }else if(viewPosition == 2){
            mRootView.findViewById(R.id.profile_preferred_layout).setVisibility(View.VISIBLE);
            ((ImageView)mRootView.findViewById(R.id.profile_expand_preferred_btn)).setImageDrawable(mPlusDrawable);
            mRootView.findViewById(R.id.profile_expand_preferred_btn).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.profile_preferred_edit_layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.profile_expanded_preferred_layout).setVisibility(View.GONE);
        }else if(viewPosition == 4){
            mRootView.findViewById(R.id.profile_other_info_layout).setVisibility(View.VISIBLE);
            ((ImageView)mRootView.findViewById(R.id.profile_expand_other_info_btn)).setImageDrawable(mPlusDrawable);
            mRootView.findViewById(R.id.profile_expand_other_info_btn).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.profile_other_info_edit_layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.profile_expanded_other_info_layout).setVisibility(View.GONE);
        }
    }


    private void mRequestForUserExamsUpdate() {
        if(mListener != null)
            mListener.onRequestForUserExams();
    }

    public void updateUserSpecializationList(String requestType, ArrayList<ProfileSpinnerItem> userSpecializationList) {

        if(mRootView == null || userSpecializationList == null)
            return;

        MaterialSpinner specializationSpinner;
        int specializationId ;
        if(requestType.equalsIgnoreCase(ProfileMacro.CURRENT_EDUCATION)){

            specializationSpinner = (MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_specialization);
            specializationId = mProfile.getCurrent_specialization_id();
        } else {
            specializationSpinner = (MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_specialization);
            specializationId = mProfile.getPreferred_specialization_id();
        }
        if(specializationSpinner == null)
            return;

        if(specializationId > 0){
            boolean isFound = false;
            int count = userSpecializationList.size();
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem pObj = userSpecializationList.get(i);
                if (pObj == null) continue;
                if (specializationId == pObj.getId()) {
                    userSpecializationList.remove(i);
                    userSpecializationList.add(0, pObj);
                    isFound = true;
                    break;
                }
            }
            if(isFound){
                specializationSpinner.setItems(userSpecializationList, false);
            }else {
                specializationSpinner.setItems(userSpecializationList, true);
                if(userSpecializationList.size() > 1)
                    specializationSpinner.setText(getString(R.string.specialization));
            }
        }else {
            specializationSpinner.setItems(userSpecializationList, true);
            if(userSpecializationList.size() > 1)
                specializationSpinner.setText(getString(R.string.specialization));
        }
    }

    private void mRequestForCurrentSubLevelDegreesList(int userSubLevelID){
        if(mListener == null)
            return;
        int levelId = -1;
        if(userSubLevelID == ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_1|| userSubLevelID == ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_2
                || userSubLevelID== ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_3 || userSubLevelID == ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_4){
            levelId = ProfileMacro.LEVEL_UNDER_GRADUATE;
        }else if(userSubLevelID == 13){
            levelId = ProfileMacro.LEVEL_CERTIFICATION;
        }
        else if(userSubLevelID == 14){
            levelId = ProfileMacro.LEVEL_DIPLOMA;
        }
        else if(userSubLevelID == ProfileMacro.CURRENT_SUB_LEVEL_PG_1 ||
                userSubLevelID == ProfileMacro.CURRENT_SUB_LEVEL_PG_2){
            levelId = ProfileMacro.LEVEL_POST_GRADUATE;
        }
        if(levelId != -1) {
            mListener.requestForDegrees(levelId, ProfileMacro.CURRENT_EDUCATION);
        }

    }

    public void mRefreshProfileOnResponse(Profile profile){
        if(profile != null){
            this.mProfile = profile;
            this.updateUserProfile();
        }
        this.mSwipeRefreshLayout.setRefreshing(false);
    }


    public void updateUserDegreesList(String requestType, ArrayList<ProfileSpinnerItem> userDegreesList) {
        if(mRootView == null || userDegreesList == null)
            return;

        if(requestType.equalsIgnoreCase(ProfileMacro.CURRENT_EDUCATION)){
            MaterialSpinner degreeSpinner =   (MaterialSpinner) mRootView.findViewById(R.id.profile_edit_current_degree);
            int degreeId = mProfile.getCurrent_degree_id();
            if(degreeId > 0){
                boolean isFound = false;
                int count = userDegreesList.size();
                for (int i = 0; i < count; i++) {
                    ProfileSpinnerItem pObj = userDegreesList.get(i);
                    if (pObj == null) continue;
                    if (degreeId == pObj.getId()) {
                        userDegreesList.remove(i);
                        userDegreesList.add(0, pObj);
                        isFound = true;
                        break;
                    }
                }
                if(isFound){
                    degreeSpinner.setItems(userDegreesList, false);
                }else {
                    degreeSpinner.setItems(userDegreesList, true);
                    if(userDegreesList.size() > 1)
                        degreeSpinner.setText(getString(R.string.select_degree));
                }
            }else {
                degreeSpinner.setItems(userDegreesList, true);
                if(userDegreesList.size() > 1)
                    degreeSpinner.setText(getString(R.string.select_degree));
            }

        } else {

            ArrayList<Integer> degreesIdList = mProfile.getPreferred_degrees_ids();
            int selectedCount =0;
            if(degreesIdList != null && !degreesIdList.isEmpty()){
                for (int id :degreesIdList ) {
                    int count = userDegreesList.size();
                    for(int i =0 ; i < count ; i++){
                        ProfileSpinnerItem obj = userDegreesList.get(i);
                        if(obj == null)continue;
                        if( obj.getId() == id){
                            selectedCount++;
                            userDegreesList.add(0,userDegreesList.remove(i));
                            obj.setSelected(true);
                            break;
                        }
                    }
                }
            }
            mPreferredDegreesList = userDegreesList;
            MaterialSpinner  preferredDegreeSpinner = (MaterialSpinner) mRootView.findViewById(R.id.profile_edit_preferred_degree);
            preferredDegreeSpinner.setMutliSelection(true);
            preferredDegreeSpinner.setFragmentListener(this);
            preferredDegreeSpinner.setItems(mPreferredDegreesList, true);
            if(degreesIdList != null && mPreferredDegreesList.size() > 1)
                preferredDegreeSpinner.setText("Degree("+selectedCount+")");
        }

    }

    public interface  UserProfileListener{
        void onUserProfileEdited(HashMap<String, String> params, int ViewPosition);
        void displayMessage(int messageId);
        void requestToUploadProfileImage(byte[] fileByteArray);
        void onPostAnonymousLogin();
        void onRequestForUserExams();
        void requestForSpecialization(int streamId, String requestType);
        void requestForDegrees(int levelId, String requestType);
        void toDashboard();
        void toRecommended();
        void onRefreshProfile();
    }
}
