package com.collegedekho.app.fragment;

import android.animation.ObjectAnimator;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.widget.CircularImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by sureshsaini on 17/5/16.
 */
public class ProfileFragment extends BaseFragment {

    private static String TAG ="Profile Fragment";
    private static String PARAM1  = "param1";
    public static Profile mProfile ;
    private UserProfileListener mListener;

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
        if(args != null){
            this.mProfile = args.getParcelable(PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        rootView.findViewById(R.id.profile_edit_btn).setOnClickListener(this);
        rootView.findViewById(R.id.profile_image_update_btn).setOnClickListener(this);
        rootView.findViewById(R.id.profile_show_more_info).setOnClickListener(this);
        rootView.findViewById(R.id.profile_show_more_education).setOnClickListener(this);
        rootView.findViewById(R.id.profile_show_more_preferences).setOnClickListener(this);
        rootView.findViewById(R.id.profile_show_more_exams).setOnClickListener(this);
        rootView.findViewById(R.id.profile_show_more_other_info).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateUserProfileImage();
        updateUserProfile();
    }

    public void updateUserProfileImage(){
        View view = getView();
        if (mProfile == null || view == null)
            return;
        CircularImageView mProfileImage = (CircularImageView) view.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        String image = mProfile.getImage();
        if (image != null && !image.isEmpty())
            mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
        else{
            if(MainActivity.user != null) {
                image = MainActivity.user.getImage();
                if (image != null && !image.isEmpty())
                    mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
            }
        }
    }

    public void updateUserProfile() {
        View view = getView();
        if (mProfile == null || view == null)
            return;


        String name = mProfile.getName();
        if(name != null && !name.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_user_name)).setText(name);
        }

        // update current basic info

        int infoProgressStatus =0;
        String email = mProfile.getEmail();
        if (email != null && !email.isEmpty() && !email.contains("@anonymouscollegedekho.com")) {
            ((TextView)view.findViewById(R.id.profile_info_email)).setText(": "+email);
            infoProgressStatus +=18;
        }else{
            ((TextView)view.findViewById(R.id.profile_info_email)).setText(": na");
        }

        String phone = mProfile.getPhone_no();
        if (phone != null && !phone.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_phone)).setText(": +91-"+phone);
            infoProgressStatus +=18;
        }else{
            ((TextView)view.findViewById(R.id.profile_info_phone)).setText(": na");
        }

        String city = mProfile.getCity_name();
        if (city != null && !city.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_city)).setText(": "+city);
            infoProgressStatus +=16;
        }else{
            ((TextView)view.findViewById(R.id.profile_info_city)).setText(": na");
        }

        String  state = mProfile.getState_name();
        if (state != null && !state.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_state)).setText(": "+state);
            infoProgressStatus +=16;
        }else{
            ((TextView)view.findViewById(R.id.profile_info_state)).setText(": na");
        }

        String motherTongue = mProfile.getMother_tongue_name();
        if (motherTongue != null && !motherTongue.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_mother_tongue)).setText(": "+motherTongue);
            infoProgressStatus +=16;
        }else{
            ((TextView)view.findViewById(R.id.profile_info_mother_tongue)).setText(": na");
        }

        String category = mProfile.getSocial_category_name();
        if (category != null && !category.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_category)).setText(": "+category);
            infoProgressStatus +=16;
        }else{
            ((TextView)view.findViewById(R.id.profile_info_category)).setText(": na");
        }
        // set basic info progress
        setProfileProgressStatus((ProgressBar)view.findViewById(R.id.profile_info_progress), infoProgressStatus);


        // set user  current education info
        int currentEducationStatus = 0;
        String currentDegreeName = mProfile.getCurrent_degree_name();
        if (currentDegreeName != null && !currentDegreeName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_education_degree)).setText(": "+currentDegreeName);
            currentEducationStatus +=18;
        }else{
            ((TextView)view.findViewById(R.id.profile_education_degree)).setText(": na");
        }

        int currentPassingYear = mProfile.getCurrent_passing_year();

        if(currentPassingYear >= 2000) {
            ((TextView) view.findViewById(R.id.profile_education_year)).setText(": " + currentPassingYear);
            currentEducationStatus +=18;
        } else {
            ((TextView) view.findViewById(R.id.profile_education_year)).setText(": na");
        }
        String currentStream = mProfile.getCurrent_stream_name();
        if (currentStream != null && !currentStream.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_education_stream)).setText(": "+currentStream);
            currentEducationStatus +=17;
        }else{
            ((TextView)view.findViewById(R.id.profile_education_stream)).setText(": na");
        }

        String currentSpecialization = mProfile.getCurrent_specialization_name();
        if (currentSpecialization != null && !currentSpecialization.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_education_specialization)).setText(": "+currentSpecialization);
            currentEducationStatus +=17;
        }else{
            ((TextView)view.findViewById(R.id.profile_education_specialization)).setText(": na");
        }

        int currentScore = mProfile.getCurrent_score();
        int scoreType = mProfile.getCurrent_score_type();
        if (scoreType <  0){
            ((TextView)view.findViewById(R.id.profile_education_score)).setText(": "+currentScore);
            currentEducationStatus +=16;
        }else{
            ((TextView)view.findViewById(R.id.profile_education_score)).setText(": "+currentScore +" "+  ProfileMacro.getCurrentScoreTypeName(scoreType));
        }

        int currentMode = mProfile.getCurrent_mode();
        {
            currentEducationStatus +=14;
            ((TextView) view.findViewById(R.id.profile_education_mode)).setText(": " + ProfileMacro.getEducationMode(currentMode));
        }
        setProfileProgressStatus((ProgressBar)view.findViewById(R.id.profile_education_progress), currentEducationStatus);




        // set user preferred info
        setPreferredEducationInfo(false);

        //  set User Exams Names
        setUserExamsInfo(false);

        //  set User Others Info
        int otherInfoStatus =0;
        String fatherName = mProfile.getFathers_name();
        if (fatherName != null && !fatherName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_father_name)).setText(": "+fatherName);
            otherInfoStatus += 34;
        }else{
            ((TextView)view.findViewById(R.id.profile_father_name)).setText(": ");
        }


        String motherName = mProfile.getMothers_name();
        if (motherName != null && !motherName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_mother_name)).setText(": "+motherName);
            otherInfoStatus += 33;
        }else{
            ((TextView)view.findViewById(R.id.profile_mother_name)).setText(": ");
        }

        String coachingName = mProfile.getCoaching_institute();
        if (coachingName != null && !coachingName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_coaching_institute_name)).setText(" : "+coachingName);
            otherInfoStatus += 33;
        }else{
            ((TextView)view.findViewById(R.id.profile_coaching_institute_name)).setText(": ");
        }
        setProfileProgressStatus((ProgressBar)view.findViewById(R.id.profile_other_progress), otherInfoStatus);

    }

    /**
     *  this method is to  update user preferred education info
     *  on preference part of screen
     * @param isShowAllInfo
     */
    public void setPreferredEducationInfo(boolean isShowAllInfo) {
        View view = getView();
        if (mProfile == null || view == null)
            return;

        int preferredInfoStatus = 0;

        String preferredStream = mProfile.getPreferred_stream_short_name();
        if (preferredStream != null && !preferredStream.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_preferences_stream)).setText(": "+preferredStream);
             preferredInfoStatus += 15;

        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_stream)).setText("na");

        }

        String preferredSpecialization = mProfile.getPreferred_specialization_name();
        if (preferredSpecialization != null && !preferredSpecialization.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_preferences_specialization)).setText(": "+preferredSpecialization);
            preferredInfoStatus += 12;
        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_specialization)).setText("na");

        }

        int preferredYear = mProfile.getPreferred_year_of_admission();
        if(preferredYear >= 2000 ){
            ((TextView) view.findViewById(R.id.profile_preferences_year)).setText(": " + preferredYear);
            preferredInfoStatus += 13;
        }else
            ((TextView)view.findViewById(R.id.profile_preferences_year)).setText(": na");


        int preferredMode = mProfile.getPreferred_mode();
        ((TextView)view.findViewById(R.id.profile_preferences_mode)).setText(": "+ProfileMacro.getEducationMode(preferredMode));
        preferredInfoStatus += 10;

        int feeRange = mProfile.getPreferred_fee_range_max();
        if(feeRange >= 1) {
            ((TextView) view.findViewById(R.id.profile_preferences_fee_range)).setText(": " + ProfileMacro.getFeeRangeName(feeRange));
            preferredInfoStatus += 13;
        }else
            ((TextView)view.findViewById(R.id.profile_preferences_fee_range)).setText(": na");

        int loanRequired = mProfile.getPreferred_loan_required();
        if(loanRequired >= 1) {
            preferredInfoStatus += 12;
            int loanAmount = mProfile.getPreferred_loan_amount_needed();
            view.findViewById(R.id.profile_preferences_loan_required_layout).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.profile_preferences_loan_amount)).setText(": " + ProfileMacro.getLoanRequiredAmount(loanAmount));
        }else {
            view.findViewById(R.id.profile_preferences_loan_required_layout).setVisibility(View.GONE);
        }



        // set degree names
        ArrayList<String> degreeNameList = mProfile.getPreferred_degrees_names();
        if(degreeNameList != null && !degreeNameList.isEmpty() ){
            int count = degreeNameList.size();
            if(!isShowAllInfo && count >3)
                count =3;

            StringBuffer degreesNameBuffer = new StringBuffer("");
            for (int i = 0; i < count; i++) {
                if(i == 0){
                    degreesNameBuffer.append(degreeNameList.get(i));
                    continue;
                }
                degreesNameBuffer.append(", ").append(degreeNameList.get(i));
            }
            if(!isShowAllInfo && degreeNameList.size() > count) {
                    ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(": "+degreesNameBuffer.toString()+"....");

            }else{
                ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(": "+degreesNameBuffer.toString());
            }
            preferredInfoStatus += 12;
        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(": na");
        }
        // set City names
        ArrayList<String> cityNameList = mProfile.getPreferred_cities_names();
        if(cityNameList != null  && !cityNameList.isEmpty()){
            int count = cityNameList.size();
            if(!isShowAllInfo && count >4)
                count =4;

            StringBuffer cityNameBuffer = new StringBuffer("");
            for (int i = 0; i < count; i++) {
                if(i == 0){
                    cityNameBuffer.append(cityNameList.get(i));
                    continue;
                }
                cityNameBuffer.append(", ").append(cityNameList.get(i));
            }
            if(!isShowAllInfo && cityNameList.size() > count) {
                ((TextView)view.findViewById(R.id.profile_preferences_location)).setText(": "+cityNameBuffer.toString()+"....");
            }else{
                ((TextView)view.findViewById(R.id.profile_preferences_location)).setText(": "+cityNameBuffer.toString());
            }
            preferredInfoStatus += 12;
        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_location)).setText(": na ");

        }

        setProfileProgressStatus((ProgressBar)view.findViewById(R.id.profile_preferences_progress), preferredInfoStatus);

    }

    /**
     * This method is used to set user exams info
     * @param showAll
     */

    private void setUserExamsInfo(boolean showAll) {
        View view = getView();
        if (mProfile == null || view == null)
            return;

        ArrayList<ProfileExam> examsList = mProfile.getYearly_exams();
        if(examsList != null ){
            int count = examsList.size();
            if(!showAll && count >4)
                count =4;

            StringBuffer examsNameBuffer = new StringBuffer("");
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

                ((TextView) view.findViewById(R.id.profile_exams_name)).setText(": "+examsNameBuffer.toString() + "....");
                view.findViewById(R.id.profile_exams_name).setSelected(false);

            }else{
                view.findViewById(R.id.profile_exams_name).setSelected(true);
                ((TextView)view.findViewById(R.id.profile_exams_name)).setText(": "+examsNameBuffer.toString());
            }
        }
    }

    public void setProfileProgressStatus(ProgressBar  progressbar, int progress)
    {
        progressbar.setProgress(0);
        progressbar.setMax(100);
        progressbar.setProgress(50);

    }

    private void animateArrow(boolean shouldRotateUp, Drawable drawable) {
        int start = shouldRotateUp ? 0 : 10000;
        int end = shouldRotateUp ? 10000 : 0;
        ObjectAnimator animator = ObjectAnimator.ofInt(drawable, "level", start, end);
        animator.start();
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (UserProfileListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSignUpListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.profile_edit_btn:
                mOnProfileEdited();
                break;
            case R.id.profile_image_update_btn:
                mOnProfileImageUpdate();
                break;
            case R.id.profile_show_more_info:
                View rootView = getView();
                if(rootView != null){
                    View view = rootView.findViewById(R.id.profile_more_info);
                    if(view.getVisibility()== View.VISIBLE) {
                        view.setVisibility(View.GONE);
                        v.setScaleY(1f);
                    }else {
                        view.setVisibility(View.VISIBLE);
                        v.setScaleY(-1f);
                    }

                }
                break;
            case R.id.profile_show_more_education:
                rootView = getView();
                if(rootView != null){
                    View view = rootView.findViewById(R.id.profile_more_education);
                    if(view.getVisibility()== View.VISIBLE) {
                        view.setVisibility(View.GONE);
                        v.setScaleY(1f);
                    }else {
                        view.setVisibility(View.VISIBLE);
                        v.setScaleY(-1f);
                    }

                }
                break;
            case R.id.profile_show_more_preferences:
                rootView = getView();
                if(rootView != null){
                    View view = rootView.findViewById(R.id.profile_more_preferences);
                    if(view.getVisibility()== View.VISIBLE) {
                        setPreferredEducationInfo(false);
                        view.setVisibility(View.GONE);
                        v.setScaleY(1f);
                    } else {
                        view.setVisibility(View.VISIBLE);
                        setPreferredEducationInfo(true);
                        v.setScaleY(-1f);
                    }

                }
                break;
            case R.id.profile_show_more_other_info:
                rootView = getView();
                if(rootView != null){
                    View view = rootView.findViewById(R.id.profile_more_other_info);
                    if(view.getVisibility()== View.VISIBLE) {
                        view.setVisibility(View.GONE);
                        v.setScaleY(1f);
                    }else {
                        view.setVisibility(View.VISIBLE);
                        v.setScaleY(-1f);
                    }

                }
                break;

            case R.id.profile_show_more_exams:
                rootView = getView();
                if(rootView != null) {
                    if(rootView.findViewById(R.id.profile_exams_name).isSelected() == true) {
                        setUserExamsInfo(false);
                        v.setScaleY(1f);
                    }else{
                        setUserExamsInfo(true);
                        v.setScaleY(-1f);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void mOnProfileImageUpdate() {

        // Determine Uri of camera image to save.
        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "MyDir" + File.separator);
        root.mkdirs();
        final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        Uri outputFileUri = Uri.fromFile(sdImageMainDirectory);


        // Camera.
        List<Intent> cameraIntents = new ArrayList<Intent>();
        Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        PackageManager packageManager = getActivity().getPackageManager();
        List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        // Chooser of filesystem options.
        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
       // chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        getActivity().startActivityForResult(chooserIntent, Constants.PICK_IMAGE);
    }

    private void mOnProfileEdited(){
        if(mListener != null)
            mListener.onUserProfileEdited(mProfile);
    }



    public interface  UserProfileListener{
        void onUserProfileEdited(Profile profile);
    }
}
