package com.collegedekho.app.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.SegmentedGroup;
import com.collegedekho.app.widget.spinner.MaterialSpinner;
import com.facebook.login.LoginManager;
import com.fasterxml.jackson.jr.ob.JSON;
import com.truecaller.android.sdk.TrueButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sureshsaini on 6/1/16.
 */
public class ProfileEditFragmentNew extends BaseFragment {

    private static final String PARAM1 = "param1" ;
    private final String TAG = "Profile Edit fragment" ;
    private ViewPager profilePager;
    private int currentPosition =0;
    private ProfilePagerAdapter profileAdapter;
    private static Profile mProfile;
    private static List<ProfileSpinnerItem> mPreferredStatesList;
    private static List<ProfileSpinnerItem> mPreferredCitiesList;
    private static List<ProfileSpinnerItem> mPreferredDegreesList;
    private static List<ProfileSpinnerItem> mPreferredSpecializationList;
    private static List<ProfileSpinnerItem> mCurrentSpecializationList;
    private static int mCurrentSelectedSpecializationId;
    private static int mPreferredSelectedSpecializationId;

    private static ProfileUpdateListener mListener;
    private View mCurrentView;

    public ProfileEditFragmentNew() {
        // required empty constructor
    }

    public static ProfileEditFragmentNew newInstance(Profile profile) {
        ProfileEditFragmentNew fragment = new ProfileEditFragmentNew();
        Bundle args = new Bundle();
        args.putParcelable(PARAM1, profile);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle != null){
            mProfile = bundle.getParcelable(PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedIstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_edit_new, container, false);
        rootView.findViewById(R.id.profile_save_button).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profilePager=(ViewPager)view.findViewById(R.id.profile_view_pager);
        profilePager.setOffscreenPageLimit(2);
        if(profileAdapter == null) {
            ArrayList<String> titleList = new ArrayList<>();
            titleList.add("Info");
            titleList.add("Education");
            titleList.add("Preferred");
            titleList.add("Exams");
            titleList.add("Others");
            profileAdapter = new ProfilePagerAdapter(getChildFragmentManager(), titleList.size(), titleList);
        }

        profilePager.setAdapter(profileAdapter);
        profilePager.setOffscreenPageLimit(4);
        profilePager.setCurrentItem(currentPosition);

        profilePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mListener == null)
                            return;
                        int currentPosition = profilePager.getCurrentItem();
                        int streamId = 0 ;
                        int spinnerId = 0 ;
                        MaterialSpinner currentSpinner =  null;
                        List<ProfileSpinnerItem> spinnerList = null;

                        if(mCurrentView == null)
                            return;
                        if(currentPosition == 1) {
                            currentSpinner = (MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_current_specialization);
                            spinnerId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_current_stream)).getSelectedSpinnerItemId();
                            streamId =  mProfile.getCurrent_stream_id();
                            spinnerList = mCurrentSpecializationList;
                        } else if(currentPosition == 2) {
                            currentSpinner = (MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_preferred_specialization);
                            spinnerId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_preferred_stream)).getSelectedSpinnerItemId();
                            streamId = mProfile.getPreferred_stream_id();
                            spinnerList = mPreferredSpecializationList;
                        }

                        // set current specialization
                        if(spinnerId == streamId && spinnerList != null){
                            boolean found = false;
                            int specializationCount = spinnerList.size();
                            for (int i = 0; i < specializationCount; i++) {
                                ProfileSpinnerItem pObj = spinnerList.get(i);
                                if (pObj == null) continue;
                                if (streamId == pObj.getId()) {
                                    spinnerList.add(0, spinnerList.remove(i));
                                    found = true;
                                    currentSpinner.setItems(spinnerList, false);
                                    break;
                                }
                            }
                            if(!found) {
                                currentSpinner.setText("Loading...");
                                currentSpinner.hideArrow();
                                if(spinnerId > 0)
                                    mListener.requestForSpecialization(spinnerId);
                                else if(streamId > 0)
                                    mListener.requestForSpecialization(streamId);
                            }

                        }else if(streamId != spinnerId && spinnerId > 0 && spinnerList != null ) {
                            int specializationCount = spinnerList.size();
                            boolean found = false;
                            for (int i = 0; i < specializationCount; i++) {
                                ProfileSpinnerItem pObj = spinnerList.get(i);
                                if (pObj == null) continue;
                                if (spinnerId == pObj.getId()) {
                                    spinnerList.add(0, spinnerList.remove(i));
                                    found = true;
                                    currentSpinner.setItems(spinnerList, false);
                                    break;
                                }
                            }
                            if(!found) {
                                currentSpinner.setText("Loading...");
                                currentSpinner.hideArrow();
                                if(spinnerId > 0)
                                    mListener.requestForSpecialization(spinnerId);
                                else if(streamId > 0)
                                    mListener.requestForSpecialization(streamId);
                            }
                        }
                        else{
                            if(spinnerId > 0)
                                mListener.requestForSpecialization(spinnerId);
                            else if(streamId > 0)
                                mListener.requestForSpecialization(streamId);
                        }
                    }
                },400);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            currentPosition = profilePager.getCurrentItem();
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            MainActivity activity = (MainActivity) getActivity();
            activity.currentFragment=this;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if(context instanceof  MainActivity)
                mListener = (ProfileUpdateListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnArticleSelectedListener");
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
        switch(v.getId()) {
            case R.id.profile_save_button:
                updateUserProfile();
                break;

            default:
                break;

        }
    }
    private void updateUserProfile(){

        if(mCurrentView  == null || mListener == null) {
            return;
        }
        int currentPagePosition = profilePager.getCurrentItem();
        if(currentPagePosition == 0) {
            String userName = ((EditText) mCurrentView.findViewById(R.id.profile_edit_name)).getText().toString();
            if (userName == null || userName.trim().isEmpty()) {
                mListener.displayMessage(R.string.NAME_EMPTY);
                return;
            } else if (!Utils.isValidName(userName)) {
                mListener.displayMessage(R.string.NAME_INVALID);
                return;
            }
            String userPhoneNumber = ((EditText) mCurrentView.findViewById(R.id.profile_edit_phone)).getText().toString();
            if (userPhoneNumber == null || userPhoneNumber.trim().isEmpty()) {
                mListener.displayMessage(R.string.PHONE_EMPTY);
                return;
            } else if (userPhoneNumber.length() <= 9 || !Utils.isValidPhone(userPhoneNumber)) {
                mListener.displayMessage(R.string.PHONE_INVALID);
                return;
            }
            int userStateId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_state)).getSelectedSpinnerItemId();
            if (userStateId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your state.");
                return;
            }
            int userCityId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_city)).getSelectedSpinnerItemId();
            if (userCityId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your City.");
                return;
            }
            int motherTongueRadioButtonId = ((SegmentedGroup) mCurrentView.findViewById(R.id.profile_mother_tongue_group)).getCheckedRadioButtonId();
            RadioButton motherTonguebutton = (RadioButton) mCurrentView.findViewById(motherTongueRadioButtonId);
            String motherTongueId = motherTonguebutton.getTag().toString();

            int categoryButtonId = ((SegmentedGroup) mCurrentView.findViewById(R.id.profile_category_group)).getCheckedRadioButtonId();
            RadioButton categoryButton = (RadioButton) mCurrentView.findViewById(categoryButtonId);
            String categoryId = categoryButton.getTag().toString();


            HashMap<String, String> params = new HashMap<>();
            params.put("name", userName);
            params.put("phone_no", userPhoneNumber);
            params.put("state_id", "" + userStateId);
            params.put("city_id", "" + userCityId);
            params.put("mother_tongue", motherTongueId);
            params.put("social_category", categoryId);
            mListener.onProfileUpdated(params);
        }else if(currentPagePosition == 1) {

            int userCurrentSubLevelId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_current_sub_level)).getSelectedSpinnerItemId();
            if (userCurrentSubLevelId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Sub Level.");
                return;
            }
            int userCurrentStreamId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_current_stream)).getSelectedSpinnerItemId();
            if (userCurrentStreamId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Stream.");
                return;
            }
            int userCurrentSpecializationId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_current_specialization)).getSelectedSpinnerItemId();
            if (userCurrentSpecializationId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Specialization.");
                return;
            }
            int userCurrentDegreeId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_current_degree)).getSelectedSpinnerItemId();
            if (userCurrentDegreeId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Degree.");
                return;
            }
            String userCurrentScore = ((EditText) mCurrentView.findViewById(R.id.profile_edit_current_score)).getText().toString();

            int userCurrentScoreId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_current_score_type)).getSelectedSpinnerItemId();

            int currentModeButtonId = ((SegmentedGroup) mCurrentView.findViewById(R.id.profile_current_education_mode_group)).getCheckedRadioButtonId();
            RadioButton modeButton = (RadioButton) mCurrentView.findViewById(currentModeButtonId);
            String currentModeId = modeButton.getTag().toString();

            int currentPassingYearButtonId = ((SegmentedGroup) mCurrentView.findViewById(R.id.profile_current_passing_year_group)).getCheckedRadioButtonId();
            RadioButton passingButtonyear = (RadioButton) mCurrentView.findViewById(currentPassingYearButtonId);
            String passingYearId = passingButtonyear.getTag().toString();

            HashMap<String, String> params = new HashMap<>();
            params.put("current_sublevel_id", "" + userCurrentSubLevelId);
            params.put("current_mode", currentModeId);
            params.put("current_stream_id", "" + userCurrentStreamId);
            params.put("current_degree_id", "" + userCurrentDegreeId);
            params.put("current_specialization_id", "" + userCurrentSpecializationId);
            params.put("current_score", userCurrentScore);
            params.put("current_score_type", "" + userCurrentScoreId);
            params.put("current_passing_year", passingYearId);
            mListener.onProfileUpdated(params);

        }else if(currentPagePosition == 2){

            int preferredYearButtonId = ((SegmentedGroup) mCurrentView.findViewById(R.id.profile_preferred_admission_year_group)).getCheckedRadioButtonId();
            RadioButton yearButton = (RadioButton) mCurrentView.findViewById(preferredYearButtonId);
            String preferredYear = yearButton.getTag().toString();

            int preferredModeButtonId = ((SegmentedGroup) mCurrentView.findViewById(R.id.profile_preferred_mode_group)).getCheckedRadioButtonId();
            RadioButton modeButton = (RadioButton) mCurrentView.findViewById(preferredModeButtonId);
            String preferredMode = modeButton.getTag().toString();

            int feeRangeButtonId = ((SegmentedGroup) mCurrentView.findViewById(R.id.profile_fee_range_group)).getCheckedRadioButtonId();
            if(feeRangeButtonId <=0 ){
                Utils.DisplayToast(getContext(), "Please Select your fee range.");
                return;
            }
            RadioButton feeRangeButton = (RadioButton) mCurrentView.findViewById(feeRangeButtonId);
            String feeRangeMax = feeRangeButton.getTag().toString();

            int userPreferredSubLevelId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_preferred_level)).getSelectedSpinnerItemId();
            if (userPreferredSubLevelId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Sub Level.");
                return;
            }
            int userPreferredStreamId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_preferred_stream)).getSelectedSpinnerItemId();
            if (userPreferredStreamId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Stream.");
                return;
            }
            int userPreferredSpecializationId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_preferred_specialization)).getSelectedSpinnerItemId();
            if (userPreferredSpecializationId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Specialization.");
                return;
            }
           /* int userPreferredDegreeId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_preferred_degree)).getSelectedSpinnerItemId();
            if (userPreferredDegreeId < 0) {
                Utils.DisplayToast(getContext(), "Please Select your Degree.");
                return;
            }*/
            int loanRequiredButtonId = ((SegmentedGroup) mCurrentView.findViewById(R.id.profile_loan_required_group)).getCheckedRadioButtonId();
            RadioButton loanRequiredButton = (RadioButton) mCurrentView.findViewById(loanRequiredButtonId);
            String loanRequired = loanRequiredButton.getTag().toString();

            int userPreferredLoanId =0;
            try {
                if (Integer.parseInt(loanRequired ) >= 1) {
                    userPreferredLoanId = ((MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_loan_amount_needed)).getSelectedSpinnerItemId();
                    if (userPreferredLoanId < 0) {
                        Utils.DisplayToast(getContext(), "Please Select your Loan Amount.");
                        return;
                    }
                }
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
            // get selected degree ids List
            StringBuffer degreeIds = new StringBuffer("[");
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
            StringBuffer stateIds = new StringBuffer("[");
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
            StringBuffer cityIds = new StringBuffer("[");
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
            params.put("preferred_year_of_admission",  preferredYear);
            params.put("preferred_mode", preferredMode);
            params.put("preferred_fee_range_max", feeRangeMax);
            params.put("preferred_level", ""+ userPreferredSubLevelId);
            params.put("preferred_stream_id", "" + userPreferredStreamId);
            params.put("preferred_specialization_id", "" + userPreferredSpecializationId);
            params.put("preferred_degrees_ids", "" + degreeIds.toString());
            params.put("preferred_states_ids", "" + stateIds.toString());
            params.put("preferred_cities_ids", "" + cityIds.toString());
            params.put("preferred_loan_required", loanRequired);
            params.put("preferred_loan_amount_needed", ""+userPreferredLoanId);
            mListener.onProfileUpdated(params);
        }
        else if(currentPagePosition == 3) {
            LinearLayout layout = (LinearLayout)mCurrentView.findViewById(R.id.profile_edit_user_exams_list_layout);
            ArrayList<ProfileExam> profileExamList = mProfile.getYearly_exams();
            if(profileExamList == null)return;

            int count = profileExamList.size();
            JSONArray parentJsonArray = new JSONArray();
            for (int i = 0; i < count;  i++){
                ProfileExam profileExam = profileExamList.get(i);
                if(profileExam == null)continue;

                CardView layoutChild = (CardView)layout.getChildAt(i);
                if(layoutChild == null)continue;

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.putOpt(MainActivity.getResourceString(R.string.TAG_ID),profileExam.getId());
                    int examStatusSpinnerId = ((MaterialSpinner) layoutChild.findViewById(R.id.profile_exam_status)).getSelectedSpinnerItemId();
                    if(examStatusSpinnerId ==1){
                        jsonObject.putOpt(MainActivity.getResourceString(R.string.STATUS),ProfileMacro.EXAM_GIVEN);
                        String scoreText = ((EditText)layoutChild.findViewById(R.id.profile_exam_score)).getText().toString();
                        if(scoreText == null || scoreText.isEmpty()){
                            Utils.DisplayToast(getContext(), "Please enter exam score for "+profileExam.getExam_name());
                            return;
                        }
                        jsonObject.putOpt(MainActivity.getResourceString(R.string.SCORE),scoreText);
                    }else{
                        jsonObject.putOpt(MainActivity.getResourceString(R.string.STATUS),ProfileMacro.EXAM_PREPARING);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                parentJsonArray.put(jsonObject);

            }
            String examJsonString = parentJsonArray.toString();
            HashMap<String, String> params = new HashMap<>();
            params.put("yearly_exams", examJsonString);
            mListener.onProfileUpdated(params);

        }
        else if(currentPagePosition == 4) {
            String fatherName = ((EditText) mCurrentView.findViewById(R.id.profile_edit_father_name)).getText().toString();
            if (fatherName == null || fatherName.isEmpty()) {
                Utils.DisplayToast(getContext(), "Father name should not be empty");
                return;
            }
            String motherName = ((EditText) mCurrentView.findViewById(R.id.profile_edit_mother_name)).getText().toString();
            if (motherName == null || motherName.isEmpty()) {
                Utils.DisplayToast(getContext(), "Mother name should not be empty");
                return;
            }
            String coachingInstitute = ((EditText) mCurrentView.findViewById(R.id.profile_edit_coaching_institute)).getText().toString();
            if (coachingInstitute == null) {
                coachingInstitute = "";
            }
            HashMap<String, String> params = new HashMap<>();
            params.put("fathers_name", fatherName);
            params.put("mothers_name", motherName);
            params.put("coaching_institute", coachingInstitute);
            mListener.onProfileUpdated(params);
        }
    }

    public void onProfileSuccessfullyUpdated(Profile profile){
        if(profile != null)
            this.mProfile = profile;
    }

    public void onProfileExamsSuccessfullyUpdated(Profile profile) {

        this.mProfile = profile;

        LinearLayout parentLayout = (LinearLayout) mCurrentView.findViewById(R.id.profile_edit_user_exams_list_layout);
        parentLayout.removeAllViews();

        ArrayList<ProfileExam> userExamList = mProfile.getYearly_exams();
        if (userExamList == null || userExamList.isEmpty())
            return;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        for (final ProfileExam exam : userExamList) {
            final CardView cardView = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_profile_exams, null);
            cardView.setLayoutParams(layoutParams);
            ((TextView) cardView.findViewById(R.id.profile_exam_name)).setText(exam.getExam_name());

            ArrayList<ProfileSpinnerItem> examStatusList = new ArrayList<>();
            for (int i = 1; i < 3; i++) {
                ProfileSpinnerItem baseObject = new ProfileSpinnerItem();
                baseObject.setId(i);
                baseObject.setName(ProfileMacro.getExamStatusName(i));
                examStatusList.add(baseObject);
            }
            if (exam.getStatus() == ProfileMacro.EXAM_PREPARING) {
                cardView.findViewById(R.id.profile_exam_score).setVisibility(View.INVISIBLE);
                examStatusList.add(0, examStatusList.remove(1));
            } else {
                EditText scoreText = (EditText) cardView.findViewById(R.id.profile_exam_score);
                scoreText.setVisibility(View.VISIBLE);
                scoreText.setText("" + exam.getScore());
            }

            MaterialSpinner examStatusSpinner = (MaterialSpinner) cardView.findViewById(R.id.profile_exam_status);
            examStatusSpinner.setItems(examStatusList, false);
            examStatusSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    EditText scoreText = (EditText)cardView.findViewById(R.id.profile_exam_score);
                    if(view.getText().toString().equalsIgnoreCase("Given")){
                        scoreText.setVisibility(View.VISIBLE);
                        if(exam.getScore() != 0)
                            scoreText.setText(""+exam.getScore());
                    }else{
                        scoreText.setVisibility(View.INVISIBLE);
                    }
                }
            });
            parentLayout.addView(cardView);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    public void updateUserSpecializationList(ArrayList<ProfileSpinnerItem> userSpecializationList) {

        if(mCurrentView == null || userSpecializationList == null)
            return;
        int currentPosition = profilePager.getCurrentItem();
        MaterialSpinner specializationSpinner = null;
        int specializationId = 0;

        if(currentPosition == 1) {
            specializationSpinner = (MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_current_specialization);
            specializationId = mProfile.getCurrent_specialization_id();
            mCurrentSpecializationList = userSpecializationList;
        } else if(currentPosition == 2) {
            specializationSpinner = (MaterialSpinner) mCurrentView.findViewById(R.id.profile_edit_preferred_specialization);
            specializationId = mProfile.getPreferred_specialization_id();
            mPreferredSpecializationList = userSpecializationList;
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
                     specializationSpinner.setText("Specialization");
             }
         }else {
             specializationSpinner.setItems(userSpecializationList, true);
             if(userSpecializationList.size() > 1)
             specializationSpinner.setText("Specialization");
         }
    }


    public interface ProfileUpdateListener {
        void onProfileUpdated(HashMap<String, String> params);
        void displayMessage(int messageId);
        void onEditUserExams();
        void requestForSpecialization(int streamId);
    }


    class ProfilePagerAdapter extends FragmentStatePagerAdapter{
        private int NUM_PAGES=1;
        private ArrayList<String> titleList;
        private ProfileChildFragment profileChildragment;

        public ProfilePagerAdapter(FragmentManager fm, int pageCount, ArrayList<String> titleList) {
            super(fm);
            this.titleList=titleList;
            NUM_PAGES=pageCount;
        }

        @Override
        public Fragment getItem(int position) {

             profileChildragment = ProfileChildFragment.newInstance();

            Bundle b=new Bundle();
            b.putString("title",titleList.get(position));
            b.putInt("position",position);
            profileChildragment.setArguments(b);
            return  profileChildragment;
        }
        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            mCurrentView = ((ProfileChildFragment)object).getView();
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            StringBuilder builder=new StringBuilder();
            builder.append(titleList.get(position));
            builder.append(" ");
            return builder.toString();
        }

    }

    @SuppressLint("ValidFragment")
    public static class ProfileChildFragment extends BaseFragment {

        private View rootView;
        private int position;
        private CardView userInfoLayout, userEducationLayout, userPreferredLayout, userExamsLayout, userOtherInfoLayout;


        public ProfileChildFragment(){
            // required empty constructor
        }

        public static ProfileChildFragment newInstance() {
            ProfileChildFragment fragment = new ProfileChildFragment();
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle args = getArguments();
            if(args != null){
                position = args.getInt("position");
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            rootView  = inflater.inflate(R.layout.layout_profile_edit_new,container,false);

            rootView.findViewById(R.id.profile_loan_required_no).setOnClickListener(this);
            rootView.findViewById(R.id.profile_loan_required_yes).setOnClickListener(this);
            rootView.findViewById(R.id.profile_loan_required_maybe).setOnClickListener(this);
            rootView.findViewById(R.id.profile_exams_edit_btn).setOnClickListener(this);
            rootView.findViewById(R.id.profile_edit_fb_login).setOnClickListener(this);

            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            userInfoLayout = (CardView) view.findViewById(R.id.profile_edit_info_layout);
            userEducationLayout = (CardView) view.findViewById(R.id.profile_edit_education_layout);
            userPreferredLayout = (CardView) view.findViewById(R.id.profile_edit_preferences_layout);
            userExamsLayout = (CardView) view.findViewById(R.id.profile_edit_exam_layout);
            userOtherInfoLayout = (CardView) view.findViewById(R.id.profile_edit_other_info_layout);

            if(position == 0)
                loadUserInfoLayout();
            else if(position == 1)
                loadUserCurrentEducation();
            else if(position == 2)
                loadUserPreferredInfo();
            else if(position == 3)
                loadUserExamsInfo();
            else if(position == 4)
                loadUserOtherInfo();
        }

        @Override
        public void onClick(View v) {
            super.onClick(v);
            View rootView = getView();
            switch (v.getId()){
                case R.id.profile_loan_required_yes:
                    rootView.findViewById(R.id.profile_loan_amount_layout).setVisibility(View.VISIBLE);
                    final ScrollView scroll = (ScrollView)rootView.findViewById(R.id.profile_edit_scroll_view);
                    scroll.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll.scrollTo(0, scroll.getBottom());
                        }});
                    break;
                case R.id.profile_loan_required_no:
                    rootView.findViewById(R.id.profile_loan_amount_layout).setVisibility(View.GONE);
                    break;
                case R.id.profile_loan_required_maybe:
                    rootView.findViewById(R.id.profile_loan_amount_layout).setVisibility(View.VISIBLE);
                    final ScrollView scroll1 = (ScrollView)rootView.findViewById(R.id.profile_edit_scroll_view);
                    scroll1.post(new Runnable() {
                        @Override
                        public void run() {
                            scroll1.scrollTo(0, scroll1.getBottom());
                        }});
                    break;
                case R.id.profile_exams_edit_btn:
                    onEditExams();
                    break;
                case R.id.profile_edit_fb_login:
                    if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
                        ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
                        return;
                    }
                    LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email", "user_likes", "user_education_history"));

                    break;
                default:
                    break;
            }
        }

        private void onEditExams() {
            if(mListener != null)
                mListener.onEditUserExams();
        }

        @Override
        public void onResume() {
            super.onResume();
        }

        private void updatePagerView(int position){
            userInfoLayout.setVisibility(View.GONE);
            userEducationLayout.setVisibility(View.GONE);
            userPreferredLayout.setVisibility(View.GONE);
            userExamsLayout.setVisibility(View.GONE);
            userOtherInfoLayout.setVisibility(View.GONE);
            if(position == 0 ){
                userInfoLayout.setVisibility(View.VISIBLE);
            }else  if(position == 1 ){
                userEducationLayout.setVisibility(View.VISIBLE);
            }else  if(position == 2 ){
                userPreferredLayout.setVisibility(View.VISIBLE);
            }else  if(position == 3 ){
                userExamsLayout.setVisibility(View.VISIBLE);
            }else  if(position == 4 ){
                userOtherInfoLayout.setVisibility(View.VISIBLE);
            }
        }

        /**
         * This method is used to set user profile info on
         * profile info  page and user can edit his/her basic info
         *  on this page like name, phone, city, state.
         */

        private void loadUserInfoLayout(){
            updatePagerView(0);
            if (mProfile == null || rootView == null)
                return;

            String name = mProfile.getName();
            if(name != null && !name.isEmpty() &&  !name.equalsIgnoreCase(getResources().getString(R.string.ANONYMOUS_USER))){
                ((TextView)rootView.findViewById(R.id.profile_edit_name)).setText(name);
            }

            String phone = mProfile.getPhone_no();
            if (phone != null && !phone.isEmpty()){
                ((TextView)rootView.findViewById(R.id.profile_edit_phone)).setText(phone);
            }

            final MaterialSpinner stateSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_state);
            final MaterialSpinner citySpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_city);

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
                    stateSpinner.setText("Select State");
                }

                if(citiesList == null){
                    citiesList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getCitiJson(0));
                    citySpinner.setItems(citiesList, true);
                    if(citiesList.size() > 1)
                        citySpinner.setText("Select City");
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
                            citySpinner.setText("Select City");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            });
            // set Mother tongue
            int  motherTongueId = mProfile.getMother_tongue();
            if(motherTongueId == ProfileMacro.MOTHER_TONGUE_HINDI){
                ((RadioButton)rootView.findViewById(R.id.profile_mother_tongue_hindi)).setChecked(true);
            }
            else if(motherTongueId == ProfileMacro.MOTHER_TONGUE_ENGLISH){
                ((RadioButton)rootView.findViewById(R.id.profile_mother_tongue_english)).setChecked(true);
            }
            else {//if(motherTongueId == ProfileMacro.MOTHER_TONGUE_OTHER){
                ((RadioButton)rootView.findViewById(R.id.profile_mother_tongue_others)).setChecked(true);
            }
           /* else {
                ((RadioButton)rootView.findViewById(R.id.profile_mother_tongue_na)).setChecked(true);
            }*/


            // set user social category
            int  socialCategoryId = mProfile.getSocial_category();
            if(socialCategoryId == ProfileMacro.CATEGORY_GENERAL){
                ((RadioButton)rootView.findViewById(R.id.profile_category_general)).setChecked(true);
            }
            else if(socialCategoryId ==  ProfileMacro.CATEGORY_OBC){
                ((RadioButton)rootView.findViewById(R.id.profile_category_obc)).setChecked(true);
            }
            else if(socialCategoryId ==  ProfileMacro.CATEGORY_SC){
                ((RadioButton)rootView.findViewById(R.id.profile_category_sc)).setChecked(true);
            }
            else if(socialCategoryId ==  ProfileMacro.CATEGORY_ST){
                ((RadioButton)rootView.findViewById(R.id.profile_category_st)).setChecked(true);
            }
            else if(socialCategoryId ==  ProfileMacro.CATEGORY_OTHERS){
                ((RadioButton)rootView.findViewById(R.id.profile_category_other)).setChecked(true);
            }
            else if(socialCategoryId <= 0){
                ((RadioButton)rootView.findViewById(R.id.profile_category_na)).setChecked(true);
            }
        }

        /**
         *
         * This method is used to set user current education info on
         * profile current education page and user can edit his/her current education
         * info  on this page like name, degree , stream , city, state.
         */

        private void loadUserCurrentEducation(){
            updatePagerView(1);
            if (mProfile == null || rootView == null)
                return;

            // set sub level
            MaterialSpinner currentSubLevelSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_current_sub_level);
            final MaterialSpinner currentStreamSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_current_stream);
            final MaterialSpinner currentSpecializationSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_current_specialization);
            MaterialSpinner currentDegreeSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_current_degree);

            int userSubLevelId = mProfile.getCurrent_sublevel_id();
            int userStreamId = mProfile.getCurrent_stream_id();
            try {

                List<ProfileSpinnerItem> currentSubLevelList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.SUB_LEVEL_JSON);
                List<ProfileSpinnerItem> currentStreamList = null;
                if(userSubLevelId >= 1){
                    int subLevelCount = currentSubLevelList.size();
                    for(int i=0 ; i< subLevelCount; i++){
                        ProfileSpinnerItem pObj = currentSubLevelList.get(i);
                        if(pObj == null) continue;
                        if(userSubLevelId ==  pObj.getId()){
                            currentSubLevelList.remove(i);
                            currentSubLevelList.add(0,pObj);
                            currentStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(pObj.getId()));
                            int streamCount = currentStreamList.size();
                            for(int j =0 ; j < streamCount; j++){
                                ProfileSpinnerItem pStreamObj = currentStreamList.get(j);
                                if(pStreamObj == null) continue;
                                if(userStreamId ==  pStreamObj.getId()) {
                                    currentStreamList.remove(j);
                                    currentStreamList.add(0, pStreamObj);
                                    break;
                                }
                            }
                            break;
                        }
                    }

                    currentSubLevelSpinner.setItems(currentSubLevelList, false);
                    currentStreamSpinner.setItems(currentStreamList, false);
                }
                else{
                    currentSubLevelSpinner.setItems(currentSubLevelList, true);
                    currentSubLevelSpinner.setText("Select Your Level");
                }


                if(currentStreamList == null){
                    currentStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(2));
                    currentStreamSpinner.setItems(currentStreamList, true);
                    if(currentStreamList.size() > 1)
                        currentStreamSpinner.setText("Select your Stream");
                }

                currentSubLevelSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                        try {
                            List<ProfileSpinnerItem> currentStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(view.getSelectedSpinnerItemId()));
                            currentStreamSpinner.setItems(currentStreamList, false);
                            if(currentStreamList.size() > 1)
                                currentStreamSpinner.setText("Select your Stream");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

            // set User Current Specialization
            currentStreamSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                     if(mListener == null)
                         return;
                      mListener.requestForSpecialization(view.getSelectedSpinnerItemId());
                     currentSpecializationSpinner.setText("Loading...");
                     currentSpecializationSpinner.hideArrow();
                }
            });

            currentSpecializationSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    mCurrentSelectedSpecializationId = view.getSelectedSpinnerItemId();
                }
            });



            // set user current degree
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
                    currentDegreeSpinner.setText("Select Degree");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            // set education mode
            int  currentEducationModeId = mProfile.getCurrent_mode();
            if(currentEducationModeId == 1){
                ((RadioButton)rootView.findViewById(R.id.profile_current_education_full_type)).setChecked(true);
            }
            else if(currentEducationModeId == 2){
                ((RadioButton)rootView.findViewById(R.id.profile_current_education_part_type)).setChecked(true);
            }
            else if(currentEducationModeId == 4){
                ((RadioButton)rootView.findViewById(R.id.profile_current_education_distance)).setChecked(true);
            }
            else if(currentEducationModeId == 5){
                ((RadioButton)rootView.findViewById(R.id.profile_current_education_executive)).setChecked(true);
            }
            else if(currentEducationModeId == 6){
                ((RadioButton)rootView.findViewById(R.id.profile_current_education_online)).setChecked(true);
            }
            else{
                ((RadioButton)rootView.findViewById(R.id.profile_current_education_na)).setChecked(true);
            }

            // set Current Score

            int currentScore = mProfile.getCurrent_score();
            if(currentScore > 0){
                ((EditText)rootView.findViewById(R.id.profile_edit_current_score)).setText(""+currentScore);
            }

            // set Current Score type

            ArrayList<ProfileSpinnerItem> scoreTypeList = new ArrayList<>();
            for(int i= 1; i < 6; i++){
                ProfileSpinnerItem baseObject = new ProfileSpinnerItem();
                baseObject.setId(i);
                baseObject.setName(ProfileMacro.getCurrentScoreTypeName(i));
                scoreTypeList.add(baseObject);
            }

            int currentScoreType = mProfile.getCurrent_score_type();
            boolean isScoretypeSelected = false;
            if(currentScoreType == ProfileMacro.MARKS){

            }else if(currentScoreType == ProfileMacro.GRADES){
                scoreTypeList.add(0,scoreTypeList.remove(1));
            }else if(currentScoreType == ProfileMacro.PERCENTAGE){
                scoreTypeList.add(0,scoreTypeList.remove(2));
            }else if(currentScoreType == ProfileMacro.RANK){
                scoreTypeList.add(0,scoreTypeList.remove(3));
            }else if(currentScoreType == ProfileMacro.PERCENTILE){
                scoreTypeList.add(0,scoreTypeList.remove(4));
            }
            else{
                isScoretypeSelected = true;
            }

            MaterialSpinner currentScoreTypeSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_current_score_type);
            if(isScoretypeSelected) {
                currentScoreTypeSpinner.setItems(scoreTypeList, true);
                currentScoreTypeSpinner.setText("Select Score Type");
            }else{
                currentScoreTypeSpinner.setItems(scoreTypeList, false);
            }


            // set current passing year info about user
            int  currentPassingYear = mProfile.getCurrent_passing_year();
            if(currentPassingYear > 2000 && currentPassingYear  < 2015){
                ((RadioButton)rootView.findViewById(R.id.profile_current_passing_before_2015)).setChecked(true);
            }
            else if(currentPassingYear < 2015){
                ((RadioButton)rootView.findViewById(R.id.profile_current_passing_year_2015)).setChecked(true);
            }
            else if(currentPassingYear ==  2016){
                ((RadioButton)rootView.findViewById(R.id.profile_current_passing_year_2016)).setChecked(true);
            }
            else if(currentPassingYear == 2017){
                ((RadioButton)rootView.findViewById(R.id.profile_current_passing_year_2017)).setChecked(true);
            }
            else if(currentPassingYear > 2017){
                ((RadioButton)rootView.findViewById(R.id.profile_current_passing_year_after_2017)).setChecked(true);
            }
            else{
                ((RadioButton)rootView.findViewById(R.id.profile_current_passing_year_na)).setChecked(true);
            }

        }
        /**
         *
         * This method is used to set user Preferred education details on
         * profile current education page and user can edit his/her Preferred education
         * info  on this page like degree, stream , specialization , city, state.
         */

        private void loadUserPreferredInfo(){
            updatePagerView(2);
            if(rootView == null || mProfile == null)
                return;
            MaterialSpinner preferredLevelSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_preferred_level);
            final MaterialSpinner preferredStreamSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_preferred_stream);
            final MaterialSpinner preferredSpecializationSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_preferred_specialization);

            // set user preferred level
            int preferredLevelId = mProfile.getPreferred_level();
            int preferredStreamId = mProfile.getPreferred_stream_id();
            List<ProfileSpinnerItem> streamList = null;
            try {
                List<ProfileSpinnerItem> levelList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.SUB_LEVEL_JSON);
                if(preferredLevelId >= 1){
                    int levelCount = levelList.size();
                    for(int i=0 ; i< levelCount; i++){
                        ProfileSpinnerItem pLevelObj = levelList.get(i);
                        if(pLevelObj == null) continue;
                        if(preferredLevelId ==  pLevelObj.getId()){
                            levelList.remove(i);
                            levelList.add(0,pLevelObj);
                            streamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(pLevelObj.getId()));
                            int streamCount = streamList.size();
                            for(int j =0 ; j < streamCount; j++){
                                ProfileSpinnerItem pStreamObj = streamList.get(j);
                                if(pStreamObj == null) continue;
                                if(preferredStreamId ==  pStreamObj.getId()) {
                                    streamList.remove(j);
                                    streamList.add(0, pStreamObj);
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    preferredLevelSpinner.setItems(levelList, false);
                    preferredStreamSpinner.setItems(streamList, false);
                }
                else{
                    preferredLevelSpinner.setItems(levelList, true);
                    preferredLevelSpinner.setText("Select Your Level");
                }

                preferredLevelSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {

                        try {
                            List<ProfileSpinnerItem> currentStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class, ProfileMacro.getStreamJson(view.getSelectedSpinnerItemId()));
                            preferredStreamSpinner.setItems(currentStreamList, false);
                            if(currentStreamList.size() > 1)
                                preferredStreamSpinner.setText("Select your Stream");
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
                        for (int j = 0; j < streamCount; j++) {
                            ProfileSpinnerItem pStreamObj = allStreamList.get(j);
                            if (pStreamObj == null) continue;
                            if (preferredStreamId == pStreamObj.getId()) {
                                allStreamList.remove(j);
                                allStreamList.add(0, pStreamObj);
                            }
                        }
                        preferredStreamSpinner.setItems(allStreamList, false);
                    } else {
                        preferredStreamSpinner.setItems(allStreamList, true);
                        preferredStreamSpinner.setText("Select Stream");
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
                    mListener.requestForSpecialization(view.getSelectedSpinnerItemId());
                    preferredSpecializationSpinner.setText("Loading...");
                    preferredSpecializationSpinner.hideArrow();
                }
            });

            preferredSpecializationSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                @Override
                public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                    mPreferredSelectedSpecializationId = view.getSelectedSpinnerItemId();
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
                            if(obj == null)continue;;
                            if( obj.getId() == id){
                                mPreferredDegreesList.add(0,mPreferredDegreesList.remove(i));
                                 obj.setSelected(true);
                                break;
                            }
                        }
                    }
                }

                MaterialSpinner preferredDegreeSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_preferred_degree);
                preferredDegreeSpinner.setMutliSelection(true);
                preferredDegreeSpinner.setItems(mPreferredDegreesList, true);
                if(degreesIdList != null)
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
                            if(obj == null)continue;;
                            if( obj.getId() == id){
                                mPreferredStatesList.add(0,mPreferredStatesList.remove(i));
                                obj.setSelected(true);
                                break;
                            }
                        }
                    }
                }
                MaterialSpinner preferredStateSpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_preferred_state);
                preferredStateSpinner.setMutliSelection(true);
                preferredStateSpinner.setFragmentListener(this);
                preferredStateSpinner.setItems(mPreferredStatesList, true);
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

                final MaterialSpinner preferredCitySpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_preferred_city);
                preferredCitySpinner.setMutliSelection(true);
                preferredCitySpinner.setItems(mPreferredCitiesList, true);
                if(cityIdList != null)
                    preferredCitySpinner.setText("City("+cityIdList.size()+")");

            }catch (Exception e){
                e.printStackTrace();
            }

            // set Preferred Year
            int  preferredAdmissionYear = mProfile.getPreferred_year_of_admission();
            if(preferredAdmissionYear == 2017){
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_admission_year_2017)).setChecked(true);
            }
            else if(preferredAdmissionYear >= 2018){
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_admission_year_after_2017)).setChecked(true);
            }
            else{
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_admission_year_2016)).setChecked(true);
            }

            // set preferred education mode
            int  preferredEducationModeId = mProfile.getPreferred_mode();
           if(preferredEducationModeId == 1){
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_mode_full_type)).setChecked(true);
            }
            else if(preferredEducationModeId == 2){
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_mode_part_type)).setChecked(true);
            }
            else if(preferredEducationModeId == 4){
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_mode_distance)).setChecked(true);
            }
            else if(preferredEducationModeId == 5){
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_mode_executive)).setChecked(true);
            }else if(preferredEducationModeId == 6){
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_mode_online)).setChecked(true);
            }else{
                ((RadioButton)rootView.findViewById(R.id.profile_preferred_mode_na)).setChecked(true);
            }

            // set preferred fee range
            int  maxfeerangeId = mProfile.getPreferred_fee_range_max();
            if(maxfeerangeId == 1){
                ((RadioButton)rootView.findViewById(R.id.profile_fee_range_one_lac)).setChecked(true);
            }else  if(maxfeerangeId == 2){
                ((RadioButton)rootView.findViewById(R.id.profile_fee_range_two_lac)).setChecked(true);
            }else  if(maxfeerangeId == 3){
                ((RadioButton)rootView.findViewById(R.id.profile_fee_range_three_lac)).setChecked(true);
            }else  if(maxfeerangeId == 4){
                ((RadioButton)rootView.findViewById(R.id.profile_fee_range_four_lac)).setChecked(true);
            }else  if(maxfeerangeId == 5){
                ((RadioButton)rootView.findViewById(R.id.profile_fee_range_above_4)).setChecked(true);
            }

            // set preferred loan required
            int  loanRequiredId = mProfile.getPreferred_loan_required();
            if(loanRequiredId == 1){
                rootView.findViewById(R.id.profile_loan_amount_layout).setVisibility(View.VISIBLE);
                ((RadioButton)rootView.findViewById(R.id.profile_loan_required_yes)).setChecked(true);
            }
            else if(loanRequiredId == 2){
                rootView.findViewById(R.id.profile_loan_amount_layout).setVisibility(View.VISIBLE);
                ((RadioButton)rootView.findViewById(R.id.profile_loan_required_maybe)).setChecked(true);
            }
            else {
                rootView.findViewById(R.id.profile_loan_amount_layout).setVisibility(View.GONE);
                ((RadioButton)rootView.findViewById(R.id.profile_loan_required_no)).setChecked(true);
            }

            // set preferred required loan amount
            if(loanRequiredId >= 1)
                rootView.findViewById(R.id.profile_loan_amount_layout).setVisibility(View.VISIBLE);
            else
                rootView.findViewById(R.id.profile_loan_amount_layout).setVisibility(View.GONE);

                ArrayList<ProfileSpinnerItem> loanRequiredAmountList = new ArrayList<>();
                for (int i = 1; i < 6; i++) {
                    ProfileSpinnerItem baseObject = new ProfileSpinnerItem();
                    baseObject.setId(i);
                    baseObject.setName(ProfileMacro.getLoanRequiredAmount(i));
                    loanRequiredAmountList.add(baseObject);
                }
                int loanAmountNeededId = mProfile.getPreferred_loan_amount_needed();
                boolean isLoanAmountSelected = false;
                if (loanAmountNeededId == ProfileMacro.BELOW_ONE_LAKH) {

                } else if (loanAmountNeededId == ProfileMacro.ONE_TO_THREE_LAKH) {
                    loanRequiredAmountList.add(0, loanRequiredAmountList.remove(1));
                } else if (loanAmountNeededId == ProfileMacro.THREE_TO_FIVE_LAKH) {
                    loanRequiredAmountList.add(0, loanRequiredAmountList.remove(2));
                } else if (loanAmountNeededId == ProfileMacro.ABOVE_FIVE_LAKH) {
                    loanRequiredAmountList.add(0, loanRequiredAmountList.remove(3));
                } else if (loanAmountNeededId == ProfileMacro.TO_BE_DECIDED) {
                    loanRequiredAmountList.add(0, loanRequiredAmountList.remove(4));
                } else {
                    isLoanAmountSelected = true;
                }

                MaterialSpinner loanAmountSpinner = (MaterialSpinner) rootView.findViewById(R.id.profile_edit_loan_amount_needed);
                if(isLoanAmountSelected) {
                    loanAmountSpinner.setItems(loanRequiredAmountList, true);
                    loanAmountSpinner.setText("Select Score Type");
                }else{
                    loanAmountSpinner.setItems(loanRequiredAmountList, false);
                }
        }


        private void loadUserExamsInfo() {

            updatePagerView(3);
            if(rootView == null || mProfile == null)
                return;
            LinearLayout parentLayout = (LinearLayout) rootView.findViewById(R.id.profile_edit_user_exams_list_layout);
            parentLayout.removeAllViews();

            ArrayList<ProfileExam> userExamList = mProfile.getYearly_exams();
            if(userExamList == null || userExamList.isEmpty())
                return;


            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 5, 10, 5);
            for (final ProfileExam exam : userExamList) {
                final CardView cardView = (CardView) LayoutInflater.from(getActivity()).inflate(R.layout.layout_profile_exams, null);
                cardView.setLayoutParams(layoutParams);
                ((TextView) cardView.findViewById(R.id.profile_exam_name)).setText(exam.getExam_name());

                ArrayList<ProfileSpinnerItem> examStatusList = new ArrayList<>();
                for( int i=1 ; i < 3 ; i++) {
                    ProfileSpinnerItem baseObject = new ProfileSpinnerItem();
                    baseObject.setId(i);
                    baseObject.setName(ProfileMacro.getExamStatusName(i));
                    examStatusList.add(baseObject);
                }
                if(exam.getStatus() == ProfileMacro.EXAM_PREPARING){
                    cardView.findViewById(R.id.profile_exam_score).setVisibility(View.INVISIBLE);
                    examStatusList.add(0, examStatusList.remove(1));
                }else {
                    EditText scoreText = (EditText)cardView.findViewById(R.id.profile_exam_score);
                    scoreText.setVisibility(View.VISIBLE);
                    scoreText.setText(""+exam.getScore());
                }

                MaterialSpinner examStatusSpinner = (MaterialSpinner) cardView.findViewById(R.id.profile_exam_status);
                examStatusSpinner.setItems(examStatusList,false);
                examStatusSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(MaterialSpinner view, int position, long id, Object item) {
                        EditText scoreText = (EditText)cardView.findViewById(R.id.profile_exam_score);
                        if(view.getText().toString().equalsIgnoreCase("Given")){
                            scoreText.setVisibility(View.VISIBLE);
                            if(exam.getScore() != 0)
                            scoreText.setText(""+exam.getScore());
                        }else{
                            scoreText.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                parentLayout.addView(cardView);
            }
        }


        private void loadUserOtherInfo()
        {
            updatePagerView(4);
            if(rootView == null || mProfile == null)
                return;

            String fatherName = mProfile.getFathers_name();
            ((EditText)rootView.findViewById(R.id.profile_edit_father_name)).setText(fatherName);

            String motherName = mProfile.getMothers_name();
            ((EditText)rootView.findViewById(R.id.profile_edit_mother_name)).setText(motherName);

            String coachingInstitute = mProfile.getCoaching_institute();
            ((EditText)rootView.findViewById(R.id.profile_edit_coaching_institute)).setText(coachingInstitute);

            TrueButton trueButton =(TrueButton)rootView.findViewById(R.id.com_truecaller_android_sdk_truebutton);

            if (MainActivity.user != null && MainActivity.user.is_anony()){
                trueButton.setVisibility(View.VISIBLE);
                rootView.findViewById(R.id.profile_edit_fb_login).setVisibility(View.VISIBLE);
            }
            else{
                trueButton.setVisibility(View.GONE);
                rootView.findViewById(R.id.profile_edit_fb_login).setVisibility(View.GONE);
            }

            boolean usable = trueButton.isUsable();
            if (usable)
                trueButton.setTrueClient(MainActivity.mTrueClient);
            else
                trueButton.setVisibility(View.GONE);


        }

        public void onDismissStatePopUpWindow(){

            if(rootView == null || mPreferredStatesList == null)
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

            ((MaterialSpinner)rootView. findViewById(R.id.profile_edit_preferred_state)).setText("State("+selectedStateCount+")");
            MaterialSpinner preferredCitySpinner = (MaterialSpinner)rootView. findViewById(R.id.profile_edit_preferred_city);
            preferredCitySpinner.setMutliSelection(true);
            preferredCitySpinner.setItems(mPreferredCitiesList, true);
            if(!mPreferredCitiesList.isEmpty()){
                int selectedCityCount = 0;
                for (ProfileSpinnerItem pObj : mPreferredCitiesList) {
                    if(pObj.isSelected())
                        selectedCityCount++;
                }
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


        @Override
        public void show() {

        }

        @Override
        public void hide() {

        }

    }

}
