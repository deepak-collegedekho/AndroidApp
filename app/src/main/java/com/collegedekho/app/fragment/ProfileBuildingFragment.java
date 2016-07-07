package com.collegedekho.app.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputFilter;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamStreamAdapter;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.listener.ExamFragmentListener;
import com.collegedekho.app.listener.ExamOnQueryListener;
import com.collegedekho.app.listener.ExamSearchCloseListener;
import com.collegedekho.app.listener.ProfileFragmentListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.CircularProgressBar;
import com.collegedekho.app.widget.GridSpacingItemDecoration;
import com.fasterxml.jackson.jr.ob.JSON;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileBuildingFragment.OnUserEducationInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileBuildingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileBuildingFragment extends BaseFragment implements ProfileFragmentListener, ExamFragmentListener{

    private static final String TAG = "user_education_fragment";
    private OnUserEducationInteractionListener mListener;
    private RecyclerView mStreamRecyclerView;
    private View mRootView ;
    private List<ProfileSpinnerItem> mStreamList = null;
    private ExamStreamAdapter mStreamAdapter;
    private ExamsAdapter mExamAdapter;
    private SearchView mExamSearchView;
    private ArrayList<Exam> mAllExamList = new ArrayList<>();
    private ArrayList<Exam> mStreamExamList = new ArrayList<>();
    private ExamOnQueryListener cExamQueryListener;
    private boolean  isStreamSelected;
    private Animation animationFromTop;
    private Animation animationFromBottom;
    private File uploadTempImageFile;
    private Uri mImageCaptureUri;
    private int mUserSubLevelID = 0;
    private int mUserCurrentMarks = 0;
    private int mLastSelectedCurrentLevelID = 0; // if last selected current level  and stream are same then present exam list be used
    private String mEventCategory = "";
    private String mEventAction = "";
    private static HashMap<String, Object> mEventValue;

    public ProfileBuildingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ProfileBuildingFragment.
     */
    public static ProfileBuildingFragment newInstance() {
        ProfileBuildingFragment fragment = new ProfileBuildingFragment();
        ProfileBuildingFragment.mEventValue = new HashMap<String, Object>();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        animationFromTop = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_top);
        animationFromTop.setDuration(Constants.ANIM_SHORTEST_DURATION);

        animationFromBottom = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_bottom);
        animationFromBottom.setDuration(Constants.ANIM_SHORTEST_DURATION);

        mRootView = inflater.inflate(R.layout.fragment_profile_building, container, false);

        /*View nextView = mRootView.findViewById(R.id.user_education_next_button);
        if (nextView.getAlpha() != 1 && !isResumed())
            nextView.setX(10000);
        }*/
        mRootView.findViewById(R.id.layout_container).setOnClickListener(this);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(MainActivity.mProfile != null){
            CircularImageView mProfileImage = (CircularImageView) view.findViewById(R.id.profile_image);

            mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
            mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

            String image = MainActivity.mProfile.getImage();
            if (image != null && !image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());

            String userName = MainActivity.mProfile.getName();
            if(userName != null && !userName.isEmpty()){
                if(!userName.equalsIgnoreCase(getString(R.string.ANONYMOUS_USER))) {
                view.findViewById(R.id.user_education_edit_name_til).setVisibility(View.GONE);
                view.findViewById(R.id.user_education_name_layout).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.user_name)).setText(userName);
            }else {
                view.findViewById(R.id.user_education_edit_name_til).setVisibility(View.VISIBLE);
                view.findViewById(R.id.user_education_name_layout).setVisibility(View.GONE);
            }
            }

            String userPhoneNumber = MainActivity.mProfile.getPhone_no();
            if(userPhoneNumber != null && !userPhoneNumber.isEmpty()) {
                view.findViewById(R.id.user_education_edit_phone_til).setVisibility(View.GONE);
                view.findViewById(R.id.user_education_phone_layout).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.user_phone)).setText(userPhoneNumber);
            }else {
                view.findViewById(R.id.user_education_edit_phone_til).setVisibility(View.VISIBLE);
                view.findViewById(R.id.user_education_phone_layout).setVisibility(View.GONE);
            }
        }

        mStreamRecyclerView = (RecyclerView)view.findViewById(R.id.user_education_recycler_view);
        mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mStreamRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));

        mExamSearchView = (SearchView) view.findViewById(R.id.user_exam_search_view);

        // if current education selected  then show next layout
        if(MainActivity.mProfile != null){
            if(MainActivity.mProfile.getCurrent_level_id() >= 1
                    && MainActivity.mProfile.getCurrent_sublevel_id() >= 1) {

                final View nextView = mRootView.findViewById(R.id.user_education_next_button);
                if (nextView.getAlpha() != 1) {

                    nextView.setVisibility(View.VISIBLE);
                    nextView.setAlpha(0);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            nextView.animate()
                                    .x(mRootView.getWidth()- nextView.getWidth() - nextView.getPaddingRight())
                                    .alpha(1f)
                                    .setDuration(Constants.ANIM_AVERAGE_DURATION);
                        }
                    }, Constants.ANIM_SHORT_DURATION);


                    View skipView = mRootView.findViewById(R.id.user_education_skip_button);
                    skipView.setVisibility(View.VISIBLE);
                    skipView.setAlpha(0f);

                    skipView.animate()
                            .alpha(1f)
                            .x(skipView.getWidth() +mRootView.findViewById(R.id.user_education_next_button_layout).getPaddingLeft())
                            .setStartDelay(Constants.ANIM_SHORT_DURATION)
                            .setDuration(Constants.ANIM_AVERAGE_DURATION);

                }

                mRootView.findViewById(R.id.user_education_radio_group).setVisibility(View.GONE);

                //  set heading text according to  stream screen
                ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));
                mStreamRecyclerView.setVisibility(View.VISIBLE);

                // show current level education layout
                View  educationLayout = mRootView.findViewById(R.id.user_education_education_layout);
                educationLayout.setVisibility(View.VISIBLE);
                educationLayout.startAnimation(animationFromTop);

                // set current level education
                TextView  currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);
                currentLevelTxtView.setVisibility(View.VISIBLE);
                int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
                if (currentLevelId == ProfileMacro.LEVEL_TWELFTH || currentLevelId == ProfileMacro.LEVEL_TENTH) {
                    currentLevelTxtView.setText(" School");
                } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                    currentLevelTxtView.setText(" College");
                } else {
                    currentLevelTxtView.setText(" PG College");
                }

                // show streams list to choose current stream based on current level
                try {
                    mStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class,
                            ProfileMacro.getStreamJson(MainActivity.mProfile.getCurrent_level_id()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mStreamAdapter == null) {
                    mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
                    mStreamRecyclerView.setAdapter(mStreamAdapter);
                } else {
                    mStreamRecyclerView.setAdapter(mStreamAdapter);
                    mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
                }
            }
        }

        view.findViewById(R.id.user_education_show_all_exams).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(!view.isSelected()){

                            // add all stream Exam on the top
                            mStreamExamList.clear();
                            mStreamExamList.addAll(mAllExamList);
                            mExamAdapter.setShowAllExams(true);
                            mExamAdapter.updateExamsList(mStreamExamList);
                            view.setSelected(true);
                            ((TextView)view).setText(getString(R.string.show_less));
                        }else{
                            if(mExamAdapter != null){
                                // add all stream Exam on the top
                                mStreamExamList.clear();
                                int examCount = mAllExamList.size();
                                for (int i = 0; i < examCount; i++) {
                                    Exam exam = mAllExamList.get(i);
                                    if(exam == null || exam.getExam_type() == ProfileMacro.OTHER_EXAM)continue;
                                    mStreamExamList.add(exam);
                                }
                                mExamAdapter.setShowAllExams(false);
                                mExamAdapter.updateExamsList(mStreamExamList);
                            }
                            view.setSelected(false);
                            ((TextView)view).setText(getString(R.string.show_more));
                        }
                        if(mStreamExamList != null && mStreamExamList.size() >0){
                            mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
                            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
                        }else{
                            TextView emptyText = (TextView) mRootView.findViewById(R.id.empty) ;
                            emptyText.setVisibility(View.VISIBLE);
                            emptyText.setText(getString(R.string.no_exam_found));
                            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
                        }
                    }
                });

        view.findViewById(R.id.user_education_radio_button_school).setOnClickListener(this);
        view.findViewById(R.id.user_education_radio_button_college).setOnClickListener(this);
        view.findViewById(R.id.user_education_radio_button_pg).setOnClickListener(this);
        view.findViewById(R.id.user_education_next_button).setOnClickListener(this);
        view.findViewById(R.id.user_education_level_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_stream_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_exams_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_skip_button).setOnClickListener(this);
        view.findViewById(R.id.user_profile_image_update).setOnClickListener(this);
        view.findViewById(R.id.go_to_recommended).setOnClickListener(this);
        view.findViewById(R.id.go_to_dash_board).setOnClickListener(this);
        view.findViewById(R.id.go_to_profile).setOnClickListener(this);
        view.findViewById(R.id.user_exam_search_container).setOnClickListener(this);
        view.findViewById(R.id.user_education_no_exam_skip_button).setOnClickListener(this);
        mExamSearchView.setOnSearchClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (OnUserEducationInteractionListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnUserEducationInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);

        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.currentFragment = this;
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch(view.getId())
        {
            case R.id.user_profile_image_update:
                mRequestForImageCapture();
                break;
            case R.id.user_education_skip_button:
                mUserEducationSkip();
                break;
            case R.id.user_education_next_button:
                mSelectedNextButton();
                break;
            case R.id.user_education_radio_button_school:
                mAskForUserSubLevel(ProfileMacro.SUB_LEVEL_SCHOOL, ProfileMacro.LEVEL_TWELFTH);
                break;
            case R.id.user_education_radio_button_college:
                mAskForUserSubLevel(ProfileMacro.SUB_LEVEL_COLLEGE,  ProfileMacro.LEVEL_UNDER_GRADUATE);
                break;
            case R.id.user_education_radio_button_pg:
                mAskForUserSubLevel(ProfileMacro.SUB_LEVEL_PG,  ProfileMacro.LEVEL_POST_GRADUATE);
                break;
            case R.id.user_education_level_edit_btn:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_PROFILE_EDIT);
                ProfileBuildingFragment.mEventValue.put("editing_what", "current_education_edit");
                mEditCurrentEducationLevel();
                break;
            case R.id.user_education_stream_edit_btn:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_PROFILE_EDIT);
                ProfileBuildingFragment.mEventValue.put("editing_what", "current_stream_edit");
                mEditCurrentStream();
                break;
            case R.id.user_education_exams_edit_btn:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_PROFILE_EDIT);
                ProfileBuildingFragment.mEventValue.put("editing_what", "exams_edit");
                mEditUserExams();
                break;
            case R.id.go_to_recommended:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
                ProfileBuildingFragment.mEventValue.put("action_what", "go_to_recommended");
                mTakeMeToRecommended();
                break;
            case R.id.go_to_dash_board:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
                ProfileBuildingFragment.mEventValue.put("action_what", "go_to_dash_board");
                mTakeMeToDashBoard();
                break;
            case R.id.go_to_profile:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
                ProfileBuildingFragment.mEventValue.put("action_what", "go_to_profile");
                mTakeMeToProfile();
                break;
            case R.id.user_exam_search_view:
            case R.id.user_exam_search_container:
                mExamSearchView.onActionViewExpanded();
                getView().findViewById(R.id.user_exam_search_hint).setVisibility(View.GONE);
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SEARCH);
                ProfileBuildingFragment.mEventValue.put("searching_what", "exams");
            case R.id.user_education_no_exam_skip_button:

                mTakeMeToDashBoard();
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SEARCH);
                ProfileBuildingFragment.mEventValue.put("skip when there is no exam", "skip to go dashboard");
                break;
            default:
                break;
        }

        if (!this.mEventAction.isEmpty() && this.mEventAction != "")
        {
            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
            this.mResetEventVariables();
        }
    }


    private void mAskForUserSubLevel(CharSequence subLevel[], final int userLevel) {

        new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.please_select_sub_level_year))
                .setCancelable(false)
                .setSingleChoiceItems(subLevel, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // set user's sub level base
                                mUserSubLevelID = ProfileMacro.getSubLevel(which, userLevel);
                                // Now show Next Button
                                mAnimateFooterButtons();
                                // dismiss dialog
                                dialog.dismiss();
                                mShowDialogForMarks();
                            }
                        }).show();

    }

    private void mShowDialogForMarks() {

        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        input.setSingleLine();
        input.setHint("Your Marks");
        input.setLayoutParams(lp);
        InputFilter[] FilterArray = new InputFilter[1];
        FilterArray[0] = new InputFilter.LengthFilter(4);
        input.setFilters(FilterArray);

       AlertDialog marksDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.please_enter_your_current_marks))
                .setCancelable(false)
                .setView(input)
                .setPositiveButton("Done", null)
               .create();

        marksDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        String marks = input.getText().toString();
                        if(marks == null || marks.isEmpty()){
                            Utils.DisplayToast(getContext(),getString(R.string.please_enter_your_marks));
                            return;
                        }
                        try {
                            mUserCurrentMarks =Integer.parseInt(marks);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        dialog.dismiss();

                    }
                });
            }
        });
        marksDialog.show();

    }

    private void mAnimateFooterButtons()
    {
        View nextView = mRootView.findViewById(R.id.user_education_next_button);
        if (nextView.getAlpha() != 1)
        {
            Runnable animEnd = new Runnable() {
                @Override
                public void run() {
                    mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
                }
            };

            nextView.animate()
                    .x(mRootView.getWidth() - nextView.getWidth() - nextView.getPaddingRight())
                    .alpha(1)
                    .withEndAction(animEnd)
                    .setDuration(Constants.ANIM_AVERAGE_DURATION);

        }
    }

    private void mUserEducationSkip() {
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);


        if(this.mListener ==  null)
            return;

        if(MainActivity.mProfile != null) {
            int size = MainActivity.mProfile.getYearly_exams().size();
            if(size >= 1) {
                JSONObject parentJsonObject  = new JSONObject();
                JSONArray parentArray = new JSONArray();
                try {
                    parentJsonObject.put(MainActivity.getResourceString(R.string.RESULTS), parentArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                mListener.onRemoveUserExams(parentJsonObject);
            }
        }
        this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
        ProfileBuildingFragment.mEventValue.put("action_what", "skip");
        ProfileBuildingFragment.mEventValue.put("action_where", "current_stream");

        this.mListener.onSkipSelectedInProfileBuilding();

        if (!this.mEventAction.isEmpty() && this.mEventAction != "")
        {
            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
            this.mResetEventVariables();
        }
    }

    private void mSelectedNextButton() {
        if(this.mListener ==  null)
            return;

        // check internet connection
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        // check which layout is visible to do action
        View radioGroupEducation = mRootView.findViewById(R.id.user_education_radio_group);
        if(radioGroupEducation.getVisibility() == View.VISIBLE) {
            isStreamSelected = false;
            int selectedRadioButton = ((RadioGroup) radioGroupEducation).getCheckedRadioButtonId();

            if(selectedRadioButton <= 1){
                mListener.displayMessage(R.string.please_select_your_level);
                return;
            }
            // setting default  current education level school
            int currentLevelID = ProfileMacro.LEVEL_TWELFTH;
            try {
                currentLevelID = Integer.parseInt(mRootView.findViewById(selectedRadioButton).getTag().toString());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            // setting default preferred level college
            int preferredLevelId = ProfileMacro.LEVEL_UNDER_GRADUATE;

            if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                preferredLevelId = ProfileMacro.LEVEL_POST_GRADUATE;
            } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
                preferredLevelId = ProfileMacro.LEVEL_PHD;
            }

            // set user' current  and preferred level locally
            if (MainActivity.mProfile != null) {
                mLastSelectedCurrentLevelID = MainActivity.mProfile.getCurrent_level_id();
                MainActivity.mProfile.setCurrent_sublevel_id(mUserSubLevelID);
                MainActivity.mProfile.setCurrent_level_id(currentLevelID);
                MainActivity.mProfile.setPreferred_level(preferredLevelId);
            }


            HashMap<String, String> params = new HashMap<>();
            params.put("current_level_id", "" + currentLevelID);
            params.put("current_sublevel_id", "" + mUserSubLevelID);
            params.put("preferred_level", "" + preferredLevelId);
            params.put("current_score", "" + mUserCurrentMarks);

            // check user's name and phone Number
            if(!getUserNameAndPhone(params)){
                return;
            }

            // save user profile data on server
            this.mListener.requestForProfile(params, Request.Method.POST);


            this.mEventAction = MainActivity.getResourceString(R.string.ACTION_CURRENT_LEVEL_SELECTED);

            if (MainActivity.mProfile.getName() != "")
                ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_NAME), MainActivity.mProfile.getName());
            if (MainActivity.mProfile.getPhone_no() != "")
                ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_PHONE), MainActivity.mProfile.getPhone_no());

            ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_CURRENT_LEVEL_ID), currentLevelID);
            ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_CURRENT_SUBLEVEL), mUserSubLevelID);
            ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_PREFERRED_LEVEL_ID), preferredLevelId);

            this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);

            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());

            this.mResetEventVariables();

            //  show next layout to select current stream
            // set heading to acco. current stream screen
            ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));

            // hide current level radio groups
            radioGroupEducation.setVisibility(View.GONE);

            // show current education layout
            mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_education_layout).startAnimation(animationFromTop);

            // set current level education
            TextView  currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);
            currentLevelTxtView.setVisibility(View.VISIBLE);
            int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
            if (currentLevelId == ProfileMacro.LEVEL_TWELFTH
                    ||  currentLevelId == ProfileMacro.LEVEL_TENTH) {
                currentLevelTxtView.setText(" School");
            } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                currentLevelTxtView.setText(" College");
            } else {
                currentLevelTxtView.setText(" PG College");
            }

            // show streams for current level
            mStreamRecyclerView.setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
            try {
                mStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class,
                        ProfileMacro.getStreamJson(currentLevelID));
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (mStreamAdapter == null) {
                mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
                mStreamRecyclerView.setAdapter(mStreamAdapter);
            } else {
                mStreamRecyclerView.setAdapter(mStreamAdapter);
                mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
            }
        }else if( !isStreamSelected && mStreamRecyclerView.getVisibility() == View.VISIBLE){

            HashMap<String, String> params = new HashMap<>();
            // check user's name and phone Number
            if(!getUserNameAndPhone(params)){
                return;
            }

            int currentStreamId  = 0;
            String currentStreamName ="";
            int count = mStreamList.size();
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem objItem = mStreamList.get(i);
                if(!objItem.isSelected()) continue;
                currentStreamId = objItem.getId();
                currentStreamName = objItem.getName();
                isStreamSelected = true;
                break;
            }

            // check user has selected current  stream
            if(!isStreamSelected){
                mListener.displayMessage(R.string.please_select_your_stream);
                return;
            }

            // clear old exam list if preferred level
            // and stream are not same
            if(MainActivity.mProfile != null && mStreamExamList != null && !mStreamExamList.isEmpty()){

                if(mLastSelectedCurrentLevelID != MainActivity.mProfile.getCurrent_level_id() ||
                        currentStreamId != MainActivity.mProfile.getCurrent_stream_id()){
                    // clear last exam list
                    if (mStreamExamList != null)
                        mStreamExamList.clear();

                    MainActivity.mProfile.setCurrent_stream_id(currentStreamId);
                    MainActivity.mProfile.setCurrent_stream_name(currentStreamName);
                    // request for exams based on preferred level nad current stream
                    this.mListener.onRequestForUserExams();
                }else{
                    MainActivity.mProfile.setCurrent_stream_id(currentStreamId);
                    MainActivity.mProfile.setCurrent_stream_name(currentStreamName);
                }

            }else {
                // clear last exam list
                if (mStreamExamList != null)
                    mStreamExamList.clear();

                // save in profile data in locally
                MainActivity.mProfile.setCurrent_stream_id(currentStreamId);
                MainActivity.mProfile.setCurrent_stream_name(currentStreamName);

                // request for exams based on preferred level nad current stream
                this.mListener.onRequestForUserExams();
            }

            // set user'current stream locally
            if(MainActivity.mProfile != null){
                // save this id in local to check last selected level was same
                mLastSelectedCurrentLevelID = MainActivity.mProfile.getCurrent_level_id();
            }

            // save user's current stream id on server
            params.put("current_stream_id",""+currentStreamId);
            this.mListener.requestForProfile(params, Request.Method.POST);



            // show user' current stream Layout
            mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_stream_layout).startAnimation(animationFromTop);

            // set user's current stream
            TextView  currentStreamTxtView = (TextView)mRootView.findViewById(R.id.user_education_stream);
            currentStreamTxtView.setVisibility(View.VISIBLE);
            currentStreamTxtView.setText(currentStreamName);

            // change heading for user exams selection
            ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
            mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.VISIBLE);
            /*final CheckBox showALL = (CheckBox)mRootView.findViewById(R.id.user_education_show_all_exams);
            showALL.setVisibility(View.VISIBLE);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    showALL.setChecked(false);
                }
            },200);*/
            TextView textView = (TextView)mRootView.findViewById(R.id.user_education_show_all_exams);
            textView.setSelected(false);
            textView.setVisibility(View.VISIBLE);
            textView.setText("Show More...");


            if(mExamAdapter == null) {
                mExamAdapter = new ExamsAdapter(getActivity(), mStreamExamList);
                mStreamRecyclerView.setAdapter(mExamAdapter);
            }else{
                mStreamRecyclerView.setAdapter(mExamAdapter);
                mExamAdapter.updateExamsList(mStreamExamList);
            }

            cExamQueryListener = new ExamOnQueryListener(mStreamExamList,this);
            this.mExamSearchView.setOnQueryTextListener(cExamQueryListener);

            mExamSearchView.setOnCloseListener(new ExamSearchCloseListener(mRootView.findViewById(R.id.user_exam_search_hint)));

            if(mStreamExamList != null && mStreamExamList.size() >0){
                mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
                mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.VISIBLE);
            }else{
                mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.GONE);
                mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
                TextView emptyText = (TextView) mRootView.findViewById(R.id.empty) ;
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText(getString(R.string.loading_exams));
                mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
            }

            this.mEventAction = MainActivity.getResourceString(R.string.ACTION_CURRENT_STREAM_SELECTED);

            if (MainActivity.mProfile.getName() != "")
                ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_NAME), MainActivity.mProfile.getName());
            if (MainActivity.mProfile.getPhone_no() != "")
                ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_PHONE), MainActivity.mProfile.getPhone_no());

            ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_CURRENT_STREAM_ID), String.valueOf(currentStreamId));

            this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);
            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());

            this.mResetEventVariables();

        }else{
            onUserExamsSelected();
        }
    }

    /**
     *
     * @param searchResults
     */
    @Override
    public void updateQueryExamList(ArrayList<Exam> searchResults) {
        if(searchResults != null && searchResults.size() >0){
            mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
        }else{
            mRootView.findViewById(R.id.empty).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
        }

        if(mExamAdapter != null){
            mExamAdapter.updateExamsList(searchResults);
        }
    }


    private void mEditCurrentEducationLevel(){
        mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_show_all_exams).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_no_exam_skip_button).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_radio_group).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.currently_studying_at));

        mStreamRecyclerView.setVisibility(View.GONE);
    }

    private void mEditCurrentStream(){
        isStreamSelected = false;
        mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_show_all_exams).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_no_exam_skip_button).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));
        mStreamRecyclerView.setVisibility(View.VISIBLE);

        if(mStreamAdapter == null) {
            mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
            mStreamRecyclerView.setAdapter(mStreamAdapter);
        }else {
            mStreamRecyclerView.setAdapter(mStreamAdapter);
            mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
        }

        try{
            mStreamExamList.clear();
            int examCount = mAllExamList.size();
            for (int i = 0; i < examCount; i++) {
                Exam exam = mAllExamList.get(i);
                if(exam == null || exam.getExam_type() == ProfileMacro.OTHER_EXAM)continue;
                mStreamExamList.add(exam);
            }
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    private void mEditUserExams(){
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_show_all_exams).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
        mStreamRecyclerView.setVisibility(View.VISIBLE);
    }


    public void onExamSubmittedSuccessfully() {
        mStreamRecyclerView.setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.VISIBLE);

        mRootView.findViewById(R.id.user_education_exams_layout).startAnimation(animationFromTop);

        if(MainActivity.user != null) {
            ArrayList<ExamDetail>  userExamList = MainActivity.user.getUser_exams();
            StringBuffer examsNameBuffer = new StringBuffer();
            if(userExamList != null) {
                int count = userExamList.size();
                if(count >= 2)
                    count = 2;
                for (int i = 0; i < count; i++) {
                    if(i==0)
                        examsNameBuffer.append(userExamList.get(i).getExam_name());
                    else
                        examsNameBuffer.append(", ").append(userExamList.get(i).getExam_name());

                }
                if(count < userExamList.size())
                    examsNameBuffer.append(".....");
            }
            TextView  userExamSTxtView = (TextView)mRootView.findViewById(R.id.user_education_exams);
            userExamSTxtView.setText(examsNameBuffer.toString());
        }
    }

    private boolean getUserNameAndPhone(HashMap<String, String> profileParams){

        View nameView = mRootView.findViewById(R.id.user_education_edit_name_til);
        if(nameView.getVisibility() == View.VISIBLE){
            String userName = ((EditText) mRootView.findViewById(R.id.user_education_name_edit_text)).getText().toString();
            if(userName != null && !userName.isEmpty()) {
                if (!Utils.isValidName(userName)) {
                    mListener.displayMessage(R.string.NAME_INVALID);
                    return false;
                }
                profileParams.put("name",userName);

                // hide name EditText
                ((TextView) mRootView.findViewById(R.id.user_name)).setText(userName);
                mRootView.findViewById(R.id.user_education_name_layout).setVisibility(View.VISIBLE);
                nameView.setVisibility(View.GONE);
                if(MainActivity.mProfile != null)
                    MainActivity.mProfile.setName(userName);
            }
        }

        View phoneView = mRootView.findViewById(R.id.user_education_edit_phone_til);
        if(phoneView.getVisibility() == View.VISIBLE){
            String userPhoneNumber = ((EditText) mRootView.findViewById(R.id.user_education_phone_edit_text)).getText().toString();
            if (userPhoneNumber != null && !userPhoneNumber.trim().isEmpty()) {
                if (userPhoneNumber.length() <= 9 || !Utils.isValidPhone(userPhoneNumber)) {
                    mListener.displayMessage(R.string.PHONE_INVALID);
                    return false;
                }
                profileParams.put("phone_no",userPhoneNumber);
                ((TextView) mRootView.findViewById(R.id.user_phone)).setText(userPhoneNumber);
                mRootView.findViewById(R.id.user_education_phone_layout).setVisibility(View.VISIBLE);
                phoneView.setVisibility(View.GONE);
                if(MainActivity.mProfile != null)
                    MainActivity.mProfile.setPhone_no(userPhoneNumber);
            }
        }
        return true;
    }


    private void onUserExamsSelected() {

        if(this.mListener == null || mExamAdapter == null)
            return;

        boolean isExamSelected = false;
        JSONObject parentJsonObject=new JSONObject();
        JSONArray parentArray=new JSONArray();
        ArrayList<Exam> adapterExamList = mExamAdapter.getExamsList();
        if(adapterExamList != null && !adapterExamList.isEmpty()) {
            for (Exam exam:adapterExamList) {
                if(exam == null || !exam.isSelected())continue;

                ArrayList<ExamDetail> detailList = exam.getExam_details();
                if(detailList == null && detailList.isEmpty()) continue;

                for (ExamDetail examDetailObj:detailList) {
                    if(examDetailObj == null || !examDetailObj.isSelected())continue;
                    JSONObject examHash = new JSONObject();
                    try {
                        examHash.putOpt(MainActivity.getResourceString(R.string.EXAM_ID),examDetailObj.getId());
                        examHash.putOpt(MainActivity.getResourceString(R.string.SCORE),examDetailObj.getScore());
                        examHash.putOpt(MainActivity.getResourceString(R.string.STATUS),examDetailObj.getStatus());
                        parentArray.put(examHash);
                        isExamSelected = true;
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        try {
            parentJsonObject.put(MainActivity.getResourceString(R.string.RESULTS),parentArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // check user's name and phone Number
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        if(userParams.size() >= 1)
            this.mListener.requestForProfile(userParams, Request.Method.POST);

        if(!isExamSelected){
            mListener.displayMessage(R.string.SELECT_ONE_EXAM);
            return;
        }


        this.mListener.onUserExamSelected(parentJsonObject);

        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);

        this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_EXAM_SELECTED);

        for (int n = 0; n < parentArray.length(); n++) {
            try
            {
                JSONObject examDetail = (JSONObject) parentArray.get(n);
                ProfileBuildingFragment.mEventValue.put(MainActivity.getResourceString(R.string.USER_EXAM_SELECTED), examDetail.get(MainActivity.getResourceString(R.string.EXAM_ID)) + "#" + examDetail.get(MainActivity.getResourceString(R.string.SCORE)) + "#" + examDetail.get(MainActivity.getResourceString(R.string.STATUS)));

                //Events
                AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());

                ProfileBuildingFragment.mEventValue.clear();
            }
            catch(Exception e)
            {

            }
        }

        this.mResetEventVariables();
    }


    private void mRequestForImageCapture() {

        // Determine Uri of camera image to save.

        uploadTempImageFile = new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        mImageCaptureUri = Uri.fromFile(uploadTempImageFile);

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

        getActivity().startActivityForResult(chooserIntent, Constants.REQUEST_PICK_IMAGE);
    }

    @Override
    public void requestForCropProfileImage(Intent data) {

        if (data != null) {
            // Get the Image from data
            Uri filePath = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            // Get the cursor
            Cursor cursor = getActivity().getContentResolver().query(filePath,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();

            File sourceFile =  new File(imgDecodableString);
            FileChannel source = null;
            FileChannel destination = null;
            try {
                source = new FileInputStream(sourceFile).getChannel();
                destination = new FileOutputStream(uploadTempImageFile).getChannel();
                if (destination != null && source != null) {
                    try {
                        destination.transferFrom(source, 0, source.size());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (source != null) {
                    try {
                        source.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (destination != null) {
                    try {
                        destination.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            mImageCaptureUri = Uri.fromFile(uploadTempImageFile);
        }

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");
        List<ResolveInfo> list = getActivity().getPackageManager().queryIntentActivities(intent, 0);
        int size = list.size();

        if (size <= 0) {
            uploadUserProfileImage();
        } else {
            intent.setData(mImageCaptureUri);

            intent.putExtra("outputX", 200);
            intent.putExtra("outputY", 200);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);
            intent.putExtra("return-data", true);

            Intent i = new Intent(intent);
            ResolveInfo res = list.get(0);
            i.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            getActivity().startActivityForResult(i, Constants.REQUEST_CROP_IMAGE);
        }
    }



    @Override
    public void uploadUserProfileImage() {

        if(MainActivity.user == null)
            return;

        final File imageFile = new File(mImageCaptureUri.getPath());
        final boolean  processCount[] = new boolean[1];

        Ion.with(getActivity().getApplicationContext())
                .load("PUT",Constants.BASE_URL+"upload-image/")
                .uploadProgressHandler(new ProgressCallback() {
                    @Override
                    public void onProgress(long uploaded, long total) {
                        // Displays the progress bar for the first time.
                        System.out.println("UPLOADED " + uploaded + "TOTAL `" + total);
                        MainActivity activity = (MainActivity) getActivity();
                        if(activity != null && !processCount[0] ){
                            processCount[0] =true;
                            activity.showProgressDialog("Uploading Image");
                        }
                    }
                })
                .setTimeout(60 * 60 * 1000)
                .setHeader("Authorization","Token "+MainActivity.user.getToken())
                .setMultipartFile("image", "application/json", imageFile)
                .asJsonObject()
                // run a callback on completion
                .setCallback(new FutureCallback<JsonObject>() {
                    @Override
                    public void onCompleted(Exception e, JsonObject result) {
                        // When the loop is finished, updates the notification
                        if (e != null) {
                            Toast.makeText(getActivity().getApplicationContext(), "Error uploading file", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if(mListener != null){
                            mListener.onProfileImageUploaded();
                        }

                        MainActivity activity = (MainActivity) getActivity();
                        if(activity != null){
                            activity.hideProgressDialog();
                        }

                        if (imageFile.exists()) imageFile.delete();

                        System.out.println("UPLOAD RESULT" + result.toString());


                    }
                });
    }


    public void profileImageUploadedSuccesfully(){
        if( mRootView == null || MainActivity.mProfile == null)
            return;
        Profile profile = MainActivity.mProfile;
        String name = profile.getName();

        if (name != null && !name.isEmpty() && !name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())){
            ((TextView) mRootView.findViewById(R.id.user_name)).setText(name);
            mRootView.findViewById(R.id.user_education_name_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_edit_name_til).setVisibility(View.GONE);
        }

        String phone = profile.getPhone_no();
        if (phone != null && !phone.isEmpty() && !phone.equalsIgnoreCase("null")) {
            ((TextView) mRootView.findViewById(R.id.user_phone)).setText(phone);
            mRootView.findViewById(R.id.user_education_phone_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_edit_phone_til).setVisibility(View.GONE);
        }

        String image = profile.getImage();
        if (image != null && ! image.isEmpty())
            ((CircularImageView) mRootView.findViewById(R.id.profile_image)).setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());

        CircularProgressBar profileCompleted =  (CircularProgressBar) mRootView.findViewById(R.id.user_profile_progress);
        profileCompleted.setProgress(MainActivity.mProfile.getProgress());

    }

    public void updateExamsList(ArrayList<Exam> examList){
        this.mAllExamList.clear();
        this.mAllExamList.addAll(examList);

        if(mAllExamList == null || mAllExamList.isEmpty()){
            mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_show_all_exams).setVisibility(View.GONE);
            mStreamRecyclerView.setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_no_exam_skip_button).setVisibility(View.VISIBLE);
            TextView emptyText = (TextView) mRootView.findViewById(R.id.empty) ;
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_exam_found));
            return;

        }else{
            mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_show_all_exams).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_no_exam_skip_button).setVisibility(View.GONE);

        }


        // add all stream Exam on the top
        int examCount = mAllExamList.size();
        for (int i = 0; i < examCount; i++) {
            Exam exam = mAllExamList.get(i);
            if(exam == null || exam.getExam_type() == ProfileMacro.OTHER_EXAM)continue;
            mStreamExamList.add(exam);
            mAllExamList.remove(i);
            mAllExamList.add(0, exam);
        }

        if(mStreamExamList != null && mStreamExamList.size() >0){
            mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
        }else{
            TextView emptyText = (TextView) mRootView.findViewById(R.id.empty) ;
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_exam_found));
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
        }

        // show searchView for exam
        View examSearchView = mRootView.findViewById(R.id.user_exam_search_container);
        if(examSearchView.getVisibility() == View.GONE) {
            examSearchView.setVisibility(View.VISIBLE);
            examSearchView.startAnimation(animationFromTop);
        }

        // show  next button
        View nextButtonLayout = mRootView.findViewById(R.id.user_education_next_button_layout);
        if(nextButtonLayout.getVisibility() == View.GONE){
            nextButtonLayout.setVisibility(View.VISIBLE);
            nextButtonLayout.startAnimation(animationFromBottom);
        }

        if(mExamAdapter != null){
            mExamAdapter.updateExamsList(mStreamExamList);
        }
    }

    public void hideNavigationIcon(){
        mRootView.findViewById(R.id.navigation_cd_icon).setVisibility(View.GONE);
    }


    private void mTakeMeToRecommended(){
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        if(mListener == null)
            return;
        // check user's name and phone Number
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        if(userParams.size() >= 1)
            this.mListener.requestForProfile(userParams, Request.Method.POST);


        mRootView.findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        mListener.OnTakeMeToRecommended();
    }

    private void mTakeMeToDashBoard(){
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        if(mListener == null)
            return;
        // check user's name and phone Number
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        if(userParams.size() >= 1)
            this.mListener.requestForProfile(userParams, Request.Method.POST);


        mRootView.findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        mListener.OnTakeMeToDashBoard();
    }

    private void mTakeMeToProfile(){
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        if(mListener == null)
            return;

        // check user's name and phone Number
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        if(userParams.size() >= 1)
            this.mListener.requestForProfile(userParams, Request.Method.POST);

        mRootView.findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        mListener.OnTakeMeToProfile();
    }

    private void mResetEventVariables()
    {
        this.mEventAction = "";
        ProfileBuildingFragment.mEventValue.clear();
    }


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
    public interface OnUserEducationInteractionListener {
        void onSkipSelectedInProfileBuilding();
        void onUserExamSelected(JSONObject examJson);
        void onRemoveUserExams(JSONObject examJson);
        void displayMessage(int messageId);
        void requestForProfile(HashMap<String, String> params, int method);
        void onRequestForUserExams();
        void  onRequestForLevelStreams(int levelId, int levelType);
        void OnTakeMeToRecommended();
        void OnTakeMeToDashBoard();
        void OnTakeMeToProfile();
        void onProfileImageUploaded();
    }

}
