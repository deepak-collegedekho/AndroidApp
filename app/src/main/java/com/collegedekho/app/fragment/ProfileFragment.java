package com.collegedekho.app.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.widget.CircularImageView;

import java.util.ArrayList;


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

    }

    public void updateUserProfile() {
        View view = getView();
        if (mProfile == null || view == null)
            return;


        // update current basic info
        String name = mProfile.getName();
        if(name != null && !name.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_user_name)).setText(name);
        }

        String email = mProfile.getEmail();
        if (email != null && !email.isEmpty() && !email.contains("@anonymouscollegedekho.com")) {
            ((TextView)view.findViewById(R.id.profile_info_email)).setText(": "+email);
        }else{
            ((TextView)view.findViewById(R.id.profile_info_email)).setText(": na");
        }

        String phone = mProfile.getPhone_no();
        if (phone != null && !phone.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_phone)).setText(": +91-"+phone);
        }else{
            ((TextView)view.findViewById(R.id.profile_info_phone)).setText(": na");
        }

        String city = mProfile.getCity_name();
        if (city != null && !city.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_city)).setText(": "+city);
        }else{
            ((TextView)view.findViewById(R.id.profile_info_city)).setText(": na");
        }

        String  state = mProfile.getState_name();
        if (state != null && !state.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_state)).setText(": "+state);
        }else{
            ((TextView)view.findViewById(R.id.profile_info_state)).setText(": na");
        }

        String motherTongue = mProfile.getMother_tongue_name();
        if (motherTongue != null && !motherTongue.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_mother_tongue)).setText(": "+motherTongue);
        }else{
            ((TextView)view.findViewById(R.id.profile_info_mother_tongue)).setText(": na");
        }

        String category = mProfile.getSocial_category_name();
        if (category != null && !category.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_category)).setText(": "+category);
        }else{
            ((TextView)view.findViewById(R.id.profile_info_category)).setText(": na");
        }

        // set user  current education info
        String currentDegreeName = mProfile.getCurrent_degree_name();
        if (currentDegreeName != null && !currentDegreeName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_education_degree)).setText(": "+currentDegreeName);
        }else{
            ((TextView)view.findViewById(R.id.profile_education_degree)).setText(": na");
        }

        int currentPassingYear = mProfile.getCurrent_passing_year();
        if(currentPassingYear >= 2000)
            ((TextView)view.findViewById(R.id.profile_education_year)).setText(": "+currentPassingYear);
        else
            ((TextView)view.findViewById(R.id.profile_education_year)).setText(": na");

        String currentStream = mProfile.getCurrent_stream_name();
        if (currentStream != null && !currentStream.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_education_stream)).setText(": "+currentStream);
        }else{
            ((TextView)view.findViewById(R.id.profile_education_stream)).setText(": na");
        }

        String currentSpecialization = mProfile.getCurrent_specialization_name();
        if (currentSpecialization != null && !currentSpecialization.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_education_specialization)).setText(": "+currentSpecialization);
        }else{
            ((TextView)view.findViewById(R.id.profile_education_specialization)).setText(": na");
        }

        int currentScore = mProfile.getCurrent_score();
        int scoreType = mProfile.getCurrent_score_type();
        if (scoreType <  0){
            ((TextView)view.findViewById(R.id.profile_education_score)).setText(": "+currentScore);
        }else{
            ((TextView)view.findViewById(R.id.profile_education_score)).setText(": "+ ProfileMacro.getCurrentScoreTypeName(scoreType));
        }

        int currentMode = mProfile.getCurrent_mode();
        ((TextView)view.findViewById(R.id.profile_education_mode)).setText(": "+ProfileMacro.getMotherTongueName(currentMode));


        // set user preferred info
        setPreferredEducationInfo(false);

        //  set User Exams Names
        setUserExamsInfo(false);

        //  set User Others Info
        String fatherName = mProfile.getFathers_name();
        if (fatherName != null && !fatherName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_father_name)).setText(": "+fatherName);
        }else{
            ((TextView)view.findViewById(R.id.profile_father_name)).setText(": ");
        }


        String motherName = mProfile.getMothers_name();
        if (motherName != null && !motherName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_mother_name)).setText(": "+motherName);
        }else{
            ((TextView)view.findViewById(R.id.profile_mother_name)).setText(": ");
        }

        String coachingName = mProfile.getCoaching_institute();
        if (coachingName != null && !coachingName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_coaching_institute_name)).setText(" : "+coachingName);
        }else{
            ((TextView)view.findViewById(R.id.profile_coaching_institute_name)).setText(": ");
        }

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

        String preferredStream = mProfile.getPreferred_stream_short_name();
        if (preferredStream != null && !preferredStream.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_preferences_stream)).setText(": "+preferredStream);
        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_stream)).setText("na");

        }

        String preferredSpecialization = mProfile.getPreferred_specialization_name();
        if (preferredSpecialization != null && !preferredSpecialization.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_preferences_specialization)).setText(": "+preferredSpecialization);
        }else{
            ((TextView)view.findViewById(R.id.profile_preferences_specialization)).setText("na");

        }

        int preferredYear = mProfile.getPreferred_year_of_admission();
        if(preferredYear >= 2000)
            ((TextView)view.findViewById(R.id.profile_preferences_year)).setText(": "+preferredYear);
        else
            ((TextView)view.findViewById(R.id.profile_preferences_year)).setText(": na");


        int preferredMode = mProfile.getPreferred_mode();
        ((TextView)view.findViewById(R.id.profile_preferences_mode)).setText(": "+ProfileMacro.getMotherTongueName(preferredMode));

        int feeRange = mProfile.getPreferred_fee_range_max();
        if(feeRange >= 1)
            ((TextView)view.findViewById(R.id.profile_preferences_fee_range)).setText(": "+ProfileMacro.getFeeRangeName(feeRange));
        else
            ((TextView)view.findViewById(R.id.profile_preferences_fee_range)).setText(": na");

        int loanRequired = mProfile.getPreferred_loan_required();
        if(loanRequired >= 1) {

            int loanAmount = mProfile.getPreferred_loan_amount_needed();
            view.findViewById(R.id.profile_preferences_loan_required_layout).setVisibility(View.VISIBLE);
            ((TextView) view.findViewById(R.id.profile_preferences_loan_amount)).setText(": " + ProfileMacro.getLoanRequiredAmount(loanAmount));
        }else {
            view.findViewById(R.id.profile_preferences_loan_required_layout).setVisibility(View.GONE);
        }



        // set degree names
        ArrayList<String> degreeNameList = mProfile.getPreferred_degrees_names();
        if(degreeNameList != null ){
            int count = degreeNameList.size();
            if(!isShowAllInfo && count >4)
                count =4;

            StringBuffer degreesNameBuffer = new StringBuffer("");
            for (int i = 0; i < count; i++) {
                if(i == 0){
                    degreesNameBuffer.append(degreeNameList.get(i));
                    continue;
                }
                degreesNameBuffer.append(", ").append(degreeNameList.get(i));
            }
            if(!isShowAllInfo && degreeNameList.size() > count) {
                ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(degreesNameBuffer.toString()+"....");
            }else{
                ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(degreesNameBuffer.toString());
            }
        }
        // set City names
        ArrayList<String> cityNameList = mProfile.getPreferred_cities_names();
        if(cityNameList != null ){
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
                ((TextView)view.findViewById(R.id.profile_preferences_location)).setText(cityNameBuffer.toString()+"....");
            }else{
                ((TextView)view.findViewById(R.id.profile_preferences_location)).setText(cityNameBuffer.toString());
            }
        }

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
            if( examsList.size() > count) {
                if(showAll)
                    ((TextView)view.findViewById(R.id.profile_exams_name)).setText(examsNameBuffer.toString()+"....");
                else
                    ((TextView)view.findViewById(R.id.profile_exams_name)).setText(examsNameBuffer.toString());

                view.findViewById(R.id.profile_show_more_exams).setVisibility(View.VISIBLE);
            }else{
                view.findViewById(R.id.profile_show_more_exams).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.profile_exams_name)).setText(examsNameBuffer.toString());
            }
        }
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
                    if(view.getVisibility()== View.VISIBLE)
                        view.setVisibility(View.GONE);
                    else
                        view.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.profile_show_more_education:
                rootView = getView();
                if(rootView != null){
                    View view = rootView.findViewById(R.id.profile_more_education);
                    if(view.getVisibility()== View.VISIBLE)
                        view.setVisibility(View.GONE);
                    else
                        view.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.profile_show_more_preferences:
                rootView = getView();
                if(rootView != null){
                    View view = rootView.findViewById(R.id.profile_more_preferences);
                    if(view.getVisibility()== View.VISIBLE)
                        view.setVisibility(View.GONE);
                    else
                        view.setVisibility(View.VISIBLE);

                }
                break;
            case R.id.profile_show_more_other_info:
                rootView = getView();
                if(rootView != null){
                    View view = rootView.findViewById(R.id.profile_more_other_info);
                    if(view.getVisibility()== View.VISIBLE)
                        view.setVisibility(View.GONE);
                    else
                        view.setVisibility(View.VISIBLE);

                    setPreferredEducationInfo(true);

                }
                break;

            case R.id.profile_show_more_exams:
                setUserExamsInfo(true);
                break;
            default:
                break;
        }
    }

    private void mOnProfileImageUpdate() {

    }

    private void mOnProfileEdited(){
        if(mListener != null)
            mListener.onUserProfileEdited(mProfile);
    }


    public interface  UserProfileListener{
        void onUserProfileEdited(Profile profile);
    }
}