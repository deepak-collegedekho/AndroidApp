package com.collegedekho.app.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamStreamAdapter;
import com.collegedekho.app.adapter.ProfileBuildingExamAdapter;
import com.collegedekho.app.adapter.SubLevelAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.entities.SubLevel;
import com.collegedekho.app.listener.ExamFragmentListener;
import com.collegedekho.app.listener.ExamOnQueryListener;
import com.collegedekho.app.listener.ExamSearchCloseListener;
import com.collegedekho.app.listener.InstituteCountListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.AnalyticsUtils;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
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
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.collegedekho.app.activity.MainActivity.REQUEST_CHECK_SETTINGS;
import static com.collegedekho.app.activity.MainActivity.currentFragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileBuildingFragment.OnUserEducationInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileBuildingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileBuildingFragment extends BaseFragment implements ExamFragmentListener, InstituteCountListener
{
    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    private static final String TAG = "ProfileBuildingFragment";
    private OnUserEducationInteractionListener mListener;
    private RecyclerView mStreamRecyclerView;
    private View mRootView ;
    private SubLevelAdapter subLevelAdapter ;
    private AlertDialog subLevelDialog ;
    private List<ProfileSpinnerItem> mStreamList = null;
    private ExamStreamAdapter mStreamAdapter;
    private ProfileBuildingExamAdapter mExamAdapter;
    private SearchView mExamSearchView;
    private ArrayList<Exam> mAllExamList = new ArrayList<>();
    private ArrayList<Exam> mStreamExamList = new ArrayList<>();
    private ExamOnQueryListener cExamQueryListener;
    private boolean  isStreamSelected;
    private Animation animationFromTop;
    private Animation animationFromBottom;
    private int mUserSubLevelID = 0;
    private int mLastSelectedCurrentLevelID = 0; // if last selected current level  and stream are same then present exam list be used
    private String mEventCategory = "";
    private String mEventAction = "";
    private HashMap<String, Object> mEventValue = new HashMap<>();
    private String mInstituteCount = "";
    private ArrayList<SubLevel> mSubLevelList;
    private LocationRequest locationRequest;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private TextView mSkipButton;

    public ProfileBuildingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment ProfileBuildingFragment.
     */
    public static ProfileBuildingFragment newInstance() {
        return new ProfileBuildingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return  mRootView = inflater.inflate(R.layout.fragment_profile_building, container, false);
    }

    @Override
    public void onStart() {
        createLocationRequest();
        super.onStart();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationFromTop = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_top);
        animationFromTop.setDuration(Constants.ANIM_SHORTEST_DURATION);
        animationFromBottom = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_bottom);
        animationFromBottom.setDuration(Constants.ANIM_SHORTEST_DURATION);

        this.mSkipButton = ((TextView) mRootView.findViewById(R.id.user_education_skip_Text_View));
        mStreamRecyclerView = (RecyclerView)view.findViewById(R.id.user_education_recycler_view);
        mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mExamSearchView = (SearchView) view.findViewById(R.id.user_exam_search_view);
        TickerView mInstituteCountTicker1 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker1);
        TickerView mInstituteCountTicker2 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker2);
        TickerView mInstituteCountTicker3 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker3);
        TickerView mInstituteCountTicker4 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker4);
        TickerView mInstituteCountTicker5 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker5);
        TickerView mInstituteCountTickerPlus = (TickerView) view.findViewById(R.id.institute_count_ticker_plus);
        mInstituteCountTicker1.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker2.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker3.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker4.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker5.setCharacterList(NUMBER_LIST);
        mInstituteCountTickerPlus.setCharacterList(NUMBER_LIST);
        mInstituteCount = "20430";
        mInstituteCountTicker1.setText("0");
        mInstituteCountTicker2.setText("3");
        mInstituteCountTicker3.setText("4");
        mInstituteCountTicker4.setText("0");
        mInstituteCountTicker5.setText("2");
        mInstituteCountTickerPlus.setText("+");

        if(MainActivity.mProfile != null && MainActivity.mProfile.getApp_flow() == Constants.APP_OLD_FLOW){
            mRootView.findViewById(R.id.institute_count_ticker_layout).setVisibility(View.GONE);
            mRootView.findViewById(R.id.profile_building_college_text).setVisibility(View.GONE);
        }
        /// load profile completion Ui Screen
        mLoadUserProfileCompletedUI();
        view.findViewById(R.id.user_education_radio_button_school).setOnClickListener(this);
        view.findViewById(R.id.user_education_radio_button_college).setOnClickListener(this);
        view.findViewById(R.id.user_education_radio_button_pg).setOnClickListener(this);
        view.findViewById(R.id.user_education_next_button).setOnClickListener(this);
        view.findViewById(R.id.user_education_level_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_stream_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_exams_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_skip_button).setOnClickListener(this);
      /*  view.findViewById(R.id.go_to_recommended).setOnClickListener(this);
        view.findViewById(R.id.go_to_dash_board).setOnClickListener(this);
        view.findViewById(R.id.go_to_profile).setOnClickListener(this);*/
        view.findViewById(R.id.user_exam_search_container).setOnClickListener(this);
        mExamSearchView.setOnSearchClickListener(this);
    }

    private void mLoadUserProfileCompletedUI(){

        if(MainActivity.mProfile == null)
            return;
        Profile profile = MainActivity.mProfile;
        mLastSelectedCurrentLevelID = profile.getCurrent_level_id();
        mUserSubLevelID = profile.getCurrent_sublevel_id();

        // if current education selected  then show next layout
        if(mLastSelectedCurrentLevelID >= 1 && mUserSubLevelID >= 1) {

            if(isAdded()) {
                int instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).
                        getInt(getString(R.string.pref_institute_count), 0);
                setInstituteCount(String.valueOf(instituteCount));
            }

            // show recyclerView For stream or exams list
            mStreamRecyclerView.setVisibility(View.VISIBLE);

            // hide education level radio button
            mRootView.findViewById(R.id.user_education_radio_group).setVisibility(View.GONE);

            // show current level education layout
            View  educationLayout = mRootView.findViewById(R.id.user_education_education_layout);
            educationLayout.setVisibility(View.VISIBLE);
            educationLayout.startAnimation(animationFromTop);

            // set current level education
            TextView  currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);
            currentLevelTxtView.setVisibility(View.VISIBLE);
            int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
            if (currentLevelId == ProfileMacro.LEVEL_TWELFTH || currentLevelId == ProfileMacro.LEVEL_TENTH) {
                currentLevelTxtView.setText(getString(R.string.school));
            } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                currentLevelTxtView.setText(getString(R.string.college));
            } else {
                currentLevelTxtView.setText(getString(R.string.pg_college));
            }

            if(profile.getCurrent_stream_id() >= 1){

                isStreamSelected = true;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(mListener != null){
                            mListener.onRequestForUserExams();
                        }
                    }
                },300);
                mRootView.findViewById(R.id.user_education_next_button).setAlpha(1);
                // show mDeviceProfile' current stream Layout
                mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.user_education_stream_layout).startAnimation(animationFromTop);

                // set mDeviceProfile's current stream
                TextView  currentStreamTxtView = (TextView)mRootView.findViewById(R.id.user_education_stream);
                currentStreamTxtView.setVisibility(View.VISIBLE);
                currentStreamTxtView.setText(MainActivity.mProfile.getCurrent_stream_name());

                // change heading for mDeviceProfile exams selection
                ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
                mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.VISIBLE);

                if(mExamAdapter == null) {
                    mExamAdapter = new ProfileBuildingExamAdapter(getActivity(),this, mStreamExamList);
                    mStreamRecyclerView.setAdapter(mExamAdapter);
                }else{
                    mStreamRecyclerView.setAdapter(mExamAdapter);
                    mExamAdapter.updateExamsList(mStreamExamList);
                }

                cExamQueryListener = new ExamOnQueryListener(mStreamExamList,this,mRootView.findViewById(R.id.user_education_next_button_layout));
                mExamSearchView.setOnQueryTextListener(cExamQueryListener);
                mExamSearchView.setQuery("", false);
                mExamSearchView.setOnCloseListener(new ExamSearchCloseListener(null,mRootView.findViewById(R.id.user_education_next_button_layout)));


                mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.GONE);
                mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
                TextView emptyText = (TextView) mRootView.findViewById(R.id.empty) ;
                emptyText.setVisibility(View.VISIBLE);
                emptyText.setText(getString(R.string.loading_exams));
                mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
                this.mSkipButton.setText(getString(R.string.not_preparing));

            }else {

                // show next button with animation
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


                //  set heading text according to  stream screen
                ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));
                this.mSkipButton.setText(getString(R.string.Skip));

                if(MainActivity.mProfile != null){

                    if(mStreamList == null){
                        int streamType = 1 ; //  0 for college and 1 for school
                        int currentLevelID = MainActivity.mProfile.getCurrent_level_id();
                        if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                            streamType = 0;
                        } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
                            streamType = 0;
                        }
                        mListener.onRequestForLevelStreams(streamType);
                    }else {
                        int userStreamId = MainActivity.mProfile.getCurrent_stream_id();
                        if (mStreamList != null) {
                            int count = mStreamList.size();
                            for (int i = 0; i < count; i++) {
                                ProfileSpinnerItem streamOj = mStreamList.get(i);
                                if (streamOj == null) continue;
                                if (streamOj.getId() == userStreamId)
                                    streamOj.setSelected(true);
                                else
                                    streamOj.setSelected(false);
                            }
                        }
                        if (mStreamAdapter == null) {
                            mStreamAdapter = new ExamStreamAdapter(this, getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
                            mStreamRecyclerView.setAdapter(mStreamAdapter);
                        } else {
                            mStreamRecyclerView.setAdapter(mStreamAdapter);
                            mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
                        }
                    }
                }

            }
        }
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
        if (mainActivity != null) {
            currentFragment = this;
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
    public void onPause() {
        super.onPause();
        // this code to remove focus from search view
        if (mExamSearchView != null && !mExamSearchView.isIconified()) {
            mExamSearchView.setIconified(true);
            mExamSearchView.setQuery("", false);
            mExamSearchView.onActionViewCollapsed();
        }
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch(view.getId())
        {
            case R.id.user_education_skip_button:
                mUserEducationSkip();
                break;
            case R.id.user_education_next_button:
                mNextStepSelected();
                break;
            case R.id.user_education_radio_button_school:
                mRequestForSubLevels(ProfileMacro.LEVEL_TWELFTH);
                break;
            case R.id.user_education_radio_button_college:
                mRequestForSubLevels(ProfileMacro.LEVEL_UNDER_GRADUATE);
                break;
            case R.id.user_education_radio_button_pg:
                mRequestForSubLevels(ProfileMacro.LEVEL_POST_GRADUATE);
                break;
            case R.id.user_education_level_edit_btn:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_PROFILE_EDIT);
                this.mEventValue.put("editing_what", "current_education_edit");
                mEditCurrentEducationLevel();
                break;
            case R.id.user_education_stream_edit_btn:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_PROFILE_EDIT);
                this.mEventValue.put("editing_what", "current_stream_edit");
                mEditCurrentStream();
                break;
            case R.id.user_education_exams_edit_btn:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_PROFILE_EDIT);
                this.mEventValue.put("editing_what", "exams_edit");
                mEditUserExams();
                break;
          /*  case R.id.go_to_recommended:
            case R.id.go_to_dash_board:
            case R.id.go_to_profile:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
                mTakeMeToHome(Integer.parseInt(view.getTag().toString()));
                break;*/
            case R.id.user_exam_search_view:
            case R.id.user_exam_search_container:
                mExamSearchView.onActionViewExpanded();
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SEARCH);
                this.mEventValue.put("searching_what", "exams");
                break;
            default:
                break;
        }

        if (!this.mEventAction.isEmpty() && this.mEventAction != ""){
            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
            this.mResetEventVariables();
        }
    }
    private void setInstituteCount(final String count) {

        if(MainActivity.mProfile != null && MainActivity.mProfile.getApp_flow() == Constants.APP_OLD_FLOW){
            return;
        }
        if(!mInstituteCount.equalsIgnoreCase(count)) {
            mInstituteCount = count;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isAdded()){
                        if( count.length() ==1) {
                            updateTickerValue(count, "0","0","0","0");
                        }else if(count.length() ==2){
                            updateTickerValue("0",count.substring(0,1),"0","0","0");
                        }else if(count.length() ==3){
                            updateTickerValue("0",count.substring(1,2),count.substring(0,1),"0","0");
                        }else if(count.length() ==4){
                            updateTickerValue("0",count.substring(2,3),count.substring(1,2),count.substring(0,1),"0");
                        }else if(count.length() ==5){
                            updateTickerValue("0",count.substring(3,4),count.substring(2,3), count.substring(1,2),count.substring(0,1));
                        }
                        if(isAdded()) {
                            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.institute_count);
                            mp.start();
                        }
                    }

                }
            }, 200);
        }
    }

    protected void updateTickerValue(String value1,String value2, String value3, String value4, String value5 ) {
        if(mRootView != null) {
            TickerView mInstituteCountTicker1 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker1);
            TickerView mInstituteCountTicker2 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker2);
            TickerView mInstituteCountTicker3 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker3);
            TickerView mInstituteCountTicker4 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker4);
            TickerView mInstituteCountTicker5 = (TickerView) mRootView.findViewById(R.id.institute_count_ticker5);

            mInstituteCountTicker1.setText(value1);
            mInstituteCountTicker2.setText(value2);
            mInstituteCountTicker3.setText(value3);
            mInstituteCountTicker4.setText(value4);
            mInstituteCountTicker5.setText(value5);
        }
    }

    private void mRequestForSubLevels(int level) {
        if(NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED){
            Utils.DisplayToast(getContext(), getString(R.string.no_internet));
            return;
        }
        mListener.onRequestForSubLevels(level);
    }

    public void mSubLevelsResponseCompleted(ArrayList<SubLevel> subLevelsList){
        this.mSubLevelList = subLevelsList;
        if(this.mSubLevelList != null && !this.mSubLevelList.isEmpty()){
            int instituteCount = this.mSubLevelList.get(0).getInstitutes_count();
            if(isAdded()) {
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit().
                        putInt(getString(R.string.pref_institute_count), instituteCount).apply();
            }
            setInstituteCount(String.valueOf(instituteCount));
        }
        mAskForUserSubLevel(subLevelsList);
    }

    private void mAskForUserSubLevel(ArrayList<SubLevel> subLevels) {

        subLevelAdapter = new SubLevelAdapter(getActivity(),this,subLevels);
        subLevelDialog =  new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.please_select_sub_level_year))
                .setCancelable(false)
                .setSingleChoiceItems(subLevelAdapter, -1,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                onSubLevelSelected(which);
                            }
                        }).show();
    }

    public void onSubLevelSelected(int position){
        // set user's sub level base
        mUserSubLevelID = (int)subLevelAdapter.getItemId(position);
        // Now show Next Button
        mAnimateFooterButtons();
        // dismiss dialog
        if(subLevelDialog != null && subLevelDialog.isShowing())
            subLevelDialog.dismiss();
        // check if fragment is added to activity then show dialog to ask marks
        // otherwise set default marks and request for streams
        mNextStepSelected();
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
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        mClearUserExams();

        if(this.mListener ==  null) {
            return;
        }

        this.mListener.onSkipSelectedInProfileBuilding();

        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);
        this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);

        if (this.mSkipButton.getText() == getString(R.string.skip))
        {
            this.mEventValue.put("action_what", getString(R.string.skip));
            this.mEventValue.put("action_where", "current_stream");
        }
        if (this.mSkipButton.getText() == getString(R.string.not_preparing))
        {
            this.mEventValue.put("action_what", getString(R.string.not_preparing));
            this.mEventValue.put("action_where", "exam_selection");
        }

        if (!this.mEventAction.isEmpty() && !this.mEventAction.equals(""))
        {
            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, mEventValue, this.getActivity());
            this.mResetEventVariables();
        }
    }

    private void mNextStepSelected() {
        // check internet connection
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        if(!isStreamSelected) {
            View educationLevelView = mRootView.findViewById(R.id.user_education_radio_group);
            if (educationLevelView.getVisibility() == View.VISIBLE) {
                setUserEducationLevel();
            } else{
                checkForLocationPermission();
            }
        }else{
            setUserEducationExams();
        }
    }

    private void setUserEducationLevel() {
        isStreamSelected = false;
        RadioGroup radioGroupEducation = (RadioGroup)mRootView.findViewById(R.id.user_education_radio_group);
        int selectedRadioButton = radioGroupEducation.getCheckedRadioButtonId();

        if(selectedRadioButton <= 1){
            mListener.displayMessage(R.string.please_select_your_level);
            return;
        }
        // setting default  current education level school
        int currentLevelID = ProfileMacro.LEVEL_TWELFTH;
        int checkedRadioGroupIndex = radioGroupEducation.indexOfChild(mRootView.findViewById(selectedRadioButton));
        if(checkedRadioGroupIndex  == 1){
            currentLevelID =  ProfileMacro.LEVEL_UNDER_GRADUATE;
        }else if(checkedRadioGroupIndex == 2){
            currentLevelID =  ProfileMacro.LEVEL_POST_GRADUATE;
        }

        // setting default preferred level ug
        int streamType = 1 ; //  0 for college and 1 for school
        int preferredLevelId = ProfileMacro.LEVEL_UNDER_GRADUATE;
        if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
            streamType = 0;
            preferredLevelId = ProfileMacro.LEVEL_POST_GRADUATE;
        } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
            streamType = 0;
            preferredLevelId = ProfileMacro.LEVEL_PHD;
        }

        // set mDeviceProfile' current  and preferred level locally
        if (MainActivity.mProfile != null) {
            mLastSelectedCurrentLevelID = MainActivity.mProfile.getCurrent_level_id();
            MainActivity.mProfile.setCurrent_sublevel_id(mUserSubLevelID);
            MainActivity.mProfile.setCurrent_level_id(currentLevelID);
            MainActivity.mProfile.setPreferred_level(preferredLevelId);
        }
        // request for streams
        this.mListener.onRequestForLevelStreams(streamType);

        // send Events
        this.mEventAction = getString(R.string.ACTION_CURRENT_LEVEL_SELECTED);
        String name = MainActivity.mProfile.getName();
        if (name != null && !name.isEmpty())
            this.mEventValue.put(getString(R.string.USER_NAME), name);
        String phone = MainActivity.mProfile.getPhone_no();
        if (phone != null && !phone.isEmpty())
            this.mEventValue.put(getString(R.string.USER_PHONE),phone);
        this.mEventValue.put(getString(R.string.USER_CURRENT_LEVEL_ID), currentLevelID);
        this.mEventValue.put(getString(R.string.USER_CURRENT_SUBLEVEL), mUserSubLevelID);
        this.mEventValue.put(getString(R.string.USER_PREFERRED_LEVEL_ID), preferredLevelId);
        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);
        AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, mEventValue, this.getActivity());
        this.mResetEventVariables();
    }



    public void checkForLocationPermission() {
        if(mStreamList != null) {
            int count = mStreamList.size();
            boolean isStreamSelected = false;
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem objItem = mStreamList.get(i);
                if (!objItem.isSelected()) continue;
                isStreamSelected = true;
                break;
            }
            if (!isStreamSelected) {
                mListener.displayMessage(R.string.please_select_your_stream);
                return;
            }
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
            builder.show();
        }else {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if(mLastLocation == null && Constants.IS_LOCATION_SERVICES_ENABLED){
                checkLocationSettings();
            }else{
                mRequestForLocationUpdate();
            }
        }
    }
    public void setUserEducationStream() {

        // check user has been selected a stream
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

        // show user' current stream Layout
        mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_stream_layout).startAnimation(animationFromTop);

        // set user's current stream
        TextView  currentStreamTxtView = (TextView)mRootView.findViewById(R.id.user_education_stream);
        currentStreamTxtView.setVisibility(View.VISIBLE);
        currentStreamTxtView.setText(currentStreamName);

        // change heading for mDeviceProfile exams selection
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.VISIBLE);

        cExamQueryListener = new ExamOnQueryListener(mStreamExamList,this,mRootView.findViewById(R.id.user_education_next_button_layout));
        mExamSearchView.setOnQueryTextListener(cExamQueryListener);
        mExamSearchView.setQuery("", false);
        mExamSearchView.setOnCloseListener(new ExamSearchCloseListener(null,mRootView.findViewById(R.id.user_education_next_button_layout)));

        this.mSkipButton.setText(getString(R.string.not_preparing));

        if(mExamAdapter == null) {
            mExamAdapter = new ProfileBuildingExamAdapter(getActivity(), this, mStreamExamList);
            mStreamRecyclerView.setAdapter(mExamAdapter);
        }else{
            mStreamRecyclerView.setAdapter(mExamAdapter);
            mExamAdapter.updateExamsList(mStreamExamList);
        }

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

        // send Events
        this.mEventAction = MainActivity.getResourceString(R.string.ACTION_CURRENT_STREAM_SELECTED);
        String name = MainActivity.mProfile.getName();
        if (name != null && !name.isEmpty())
            this.mEventValue.put(MainActivity.getResourceString(R.string.USER_NAME), name);
        String phone = MainActivity.mProfile.getPhone_no();
        if (phone != null && !phone.isEmpty())
            this.mEventValue.put(MainActivity.getResourceString(R.string.USER_PHONE), name);
        this.mEventValue.put(MainActivity.getResourceString(R.string.USER_CURRENT_STREAM_ID), String.valueOf(currentStreamId));
        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);
        AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, mEventValue, this.getActivity());
        this.mResetEventVariables();
    }

    private void setUserEducationExams() {

        if(this.mListener == null || this.mExamAdapter == null)
            return;

        // if mDeviceProfile does not have any exam and want to further proceed
        // then next button works like "Not Preparing Button"
        if(this.mAllExamList != null && this.mAllExamList.size() <=0){
            mUserEducationSkip();
            return;
        }

        boolean isExamSelected = false;
        StringBuilder selectedExamsBuffer = new StringBuilder();
        selectedExamsBuffer.append("[");
        int firstTime =0;
        if(mAllExamList != null && !mAllExamList.isEmpty()) {
            for (Exam exam:mAllExamList) {
                if(exam == null || !exam.isSelected())continue;

                ArrayList<ExamDetail> detailList = exam.getExam_details();
                if(detailList == null || detailList.isEmpty()) continue;

                for (ExamDetail examDetailObj:detailList) {
                    if(examDetailObj == null || !examDetailObj.isSelected())continue;

                    if(firstTime != 0)
                        selectedExamsBuffer.append(",");
                    firstTime++;

                    selectedExamsBuffer.append("{\"id\":").append(examDetailObj.getId())
                            .append(",").append("\"score\":").append(examDetailObj.getScore());

                    if(examDetailObj.isResult_out()){
                        //TODO :: set  to exam status as given or preparing base on
                        // TODO::  result out date  and exam's date
                        //String date = examDetailObj.getExam_date();
                        selectedExamsBuffer.append(",").append("\"status\":").append(1);
                    }else{
                        selectedExamsBuffer.append(",").append("\"status\":").append(2);
                    }
                    selectedExamsBuffer.append("}");

                    isExamSelected = true;

                }
            }
        }
        selectedExamsBuffer.append("]");

        if(!isExamSelected){
            mListener.displayMessage(R.string.SELECT_ONE_EXAM);
            return;
        }

        HashMap<String, String> userParams = new HashMap<>();
        userParams.put("yearly_exams", selectedExamsBuffer.toString());
        this.mListener.onUserExamSelected(userParams);

        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);
        this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_EXAM_SELECTED);
        try
        {
            //Events
            this.mEventValue.put(MainActivity.getResourceString(R.string.USER_EXAM_SELECTED), selectedExamsBuffer.toString());//examDetail.get(MainActivity.getResourceString(R.string.EXAM_ID)) + "#" + examDetail.get(MainActivity.getResourceString(R.string.SCORE)) + "#" + examDetail.get(MainActivity.getResourceString(R.string.STATUS)));
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());
            this.mEventValue.clear();
        }catch(Exception e){
            Log.e(TAG,"exception in sending events");
        }
        this.mResetEventVariables();
    }


    /**
     * @param searchResults
     */
    @Override
    public void updateQueryExamList(ArrayList<Exam> searchResults) {
        if(searchResults != null && searchResults.size() >0){
            TextView emptyText = (TextView)mRootView.findViewById(R.id.empty);
            emptyText.setVisibility(View.GONE);
            emptyText.setText(R.string.no_exam_found);
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
        }else{
            TextView emptyText = (TextView)mRootView.findViewById(R.id.empty);
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(R.string.no_exam_found_in_search);

            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
        }

        if(mExamAdapter != null){
            mExamAdapter.setShowAllExams(true);
            mExamAdapter.updateExamsList(searchResults);
        }
    }


    private void mEditCurrentEducationLevel(){
        isStreamSelected = false;
        mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
       // mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_radio_group).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.currently_studying_at));
        mStreamRecyclerView.setVisibility(View.GONE);


        int checkedRadioButtonID = ((RadioGroup)mRootView.findViewById(R.id.user_education_radio_group)).getCheckedRadioButtonId();
        if( checkedRadioButtonID == -1 && MainActivity.mProfile != null){

            int levelId = MainActivity.mProfile.getCurrent_level_id();
            if(levelId == ProfileMacro.LEVEL_TENTH || levelId == ProfileMacro.LEVEL_TWELFTH){
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_school)).setChecked(true);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_college)).setChecked(false);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_pg)).setChecked(false);
            }else if(levelId == ProfileMacro.LEVEL_UNDER_GRADUATE){
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_school)).setChecked(false);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_college)).setChecked(true);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_pg)).setChecked(false);
            }
            else if(levelId == ProfileMacro.LEVEL_POST_GRADUATE){
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_school)).setChecked(false);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_college)).setChecked(false);
                ((RadioButton)mRootView.findViewById(R.id.user_education_radio_button_pg)).setChecked(true);

            }
        }
        if(this.mSubLevelList != null && !this.mSubLevelList.isEmpty()) {
            int instituteCount = this.mSubLevelList.get(0).getInstitutes_count();
            setInstituteCount(String.valueOf(instituteCount));
        }
    }

    private void mEditCurrentStream(){
        isStreamSelected = false;
        mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
       // mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);

        ((TextView) mRootView.findViewById(R.id.user_education_skip_Text_View)).setText(getString(R.string.skip));
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));

        mStreamRecyclerView.setVisibility(View.VISIBLE);

        this.mSkipButton.setText("Skip");

        // set selected true stream if user has selected stream earlier
        if(MainActivity.mProfile != null) {

            if (mStreamList == null) {

                int streamType = 1 ; //  0 for college and 1 for school
                int currentLevelID = MainActivity.mProfile.getCurrent_level_id();
                if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                    streamType = 0;
                } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
                    streamType = 0;
                }
                this.mListener.onRequestForLevelStreams(streamType);
            } else {
                int userStreamId = MainActivity.mProfile.getCurrent_stream_id();
                if (mStreamList != null) {
                    int count = mStreamList.size();
                    for (int i = 0; i < count; i++) {
                        ProfileSpinnerItem streamOj = mStreamList.get(i);
                        if (streamOj == null) continue;
                        if (streamOj.getId() == userStreamId) {
                            streamOj.setSelected(true);
                            // update institute count for that stream
                            setInstituteCount(String.valueOf(streamOj.getInstitutes_count()));
                        } else {
                            streamOj.setSelected(false);
                        }
                    }
                }
                if(mStreamAdapter == null) {
                    mStreamAdapter = new ExamStreamAdapter(this,getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
                    mStreamRecyclerView.setAdapter(mStreamAdapter);
                }else {
                    mStreamRecyclerView.setAdapter(mStreamAdapter);
                    mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
                }
            }
        }

    }

    private void mEditUserExams(){
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
       // mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
        mStreamRecyclerView.setVisibility(View.VISIBLE);
        updateExamsLayouts(this.mAllExamList);
    }


   /* public void onExamSubmittedSuccessfully() {
        mStreamRecyclerView.setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.VISIBLE);
      //  mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.VISIBLE);

        mRootView.findViewById(R.id.user_education_exams_layout).startAnimation(animationFromTop);

        TextView  userExamSTxtView = (TextView) mRootView.findViewById(R.id.user_education_exams);

        if(MainActivity.mProfile != null) {
            ArrayList<ProfileExam>  userExamList = MainActivity.mProfile.getYearly_exams();
            StringBuffer examsNameBuffer = new StringBuffer();
            if(userExamList != null && userExamList.size() >=1) {
                int count = userExamList.size();
                if(count >= 2)
                    count = 2;
                for (int i = 0; i < count; i++) {
                    ProfileExam exam = userExamList.get(i);
                    if(exam == null)
                        continue;
                    String examName = exam.getExam_short_name();
                    if(examName == null || examName.isEmpty()){
                        examName = exam.getExam_name();
                    }
                    if(i==0) {
                        examsNameBuffer.append(examName);
                    } else {
                        examsNameBuffer.append(", ").append(examName);
                    }
                }
                if(count < userExamList.size())
                    examsNameBuffer.append(".....");

                userExamSTxtView.setText(examsNameBuffer.toString());
            }
            else
            {
                mClearUserExams();
                userExamSTxtView.setText("Not Set");
            }

            if (mAllExamList != null && mAllExamList.isEmpty())
            {
                mClearUserExams();
                userExamSTxtView.setText("Not Set");
            }
        }
    }*/

    private void mClearUserExams()
    {
        if(this.mListener ==  null)
            return;

        if(MainActivity.mProfile != null) {
            ArrayList<ProfileExam> userExamList = MainActivity.mProfile.getYearly_exams();
            if(userExamList != null) {
                int size =userExamList.size();
                if (size >= 1) {
                    HashMap<String, String> userParams = new HashMap<>();
                    userParams.put("yearly_exams", "[]");
                    this.mListener.requestForProfile(userParams);
                    MainActivity.mProfile.setYearly_exams(new ArrayList<ProfileExam>());
                }
            }
        }
    }

    public void profileUpdatedSuccessfully(){
        //if user is selected stream and  after that level response comes
        // then it will reset user stream id
        int currentStreamId =0;
        boolean checkStream = false;
        if(mStreamList != null && !mStreamList.isEmpty()){
            int count = mStreamList.size();
            for (int i = 0; i < count; i++) {
                ProfileSpinnerItem objItem = mStreamList.get(i);
                if(!objItem.isSelected()) continue;
                currentStreamId = objItem.getId();
                checkStream = true;
                break;
            }
            if(checkStream && MainActivity.mProfile != null){
                MainActivity.mProfile.setCurrent_stream_id(currentStreamId);
            }
        }
    }

    public void updateExamsList(ArrayList<Exam> examList){
        if(this.mAllExamList == null) {
            this.mAllExamList = new ArrayList<>();
        }else {
            this.mAllExamList.clear();
        }
        this.mAllExamList.addAll(examList);
        if(isAdded()) {
            updateExamsLayouts(mAllExamList);
        }
    }

    private void updateExamsLayouts(ArrayList<Exam> mAllExamList){

        if(isAdded()) {
            getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit()
                    .putInt(getString(R.string.pref_institute_count), Integer.parseInt(mInstituteCount)).apply();
        }
        if(mAllExamList == null || mAllExamList.isEmpty()){
            mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
            mStreamRecyclerView.setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
            TextView emptyText = (TextView) mRootView.findViewById(R.id.empty) ;
            emptyText.setVisibility(View.VISIBLE);
            emptyText.setText(getString(R.string.no_exam_found));
            return;

        }else{
            mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
        }

        if (this.mStreamExamList == null)
            this.mStreamList = new ArrayList<>();
        else
            this.mStreamExamList.clear();


        // add all stream Exam on the top
        int examCount = mAllExamList.size();
        int i=0;
        while(i < examCount) {
            Exam exam = mAllExamList.get(i);
            if(exam == null || exam.getExam_type() == ProfileMacro.STREAM_EXAM){
                i++;
                continue;
            }else {
                examCount--;
                mAllExamList.remove(i);
                mAllExamList.add(exam);
            }
        }

        this.mStreamExamList.addAll(mAllExamList);

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
            mExamAdapter.setShowAllExams(true);
            mExamAdapter.updateExamsList(mStreamExamList);
        }
    }
    public void hideNavigationIcon(){
        mRootView.findViewById(R.id.navigation_cd_icon).setVisibility(View.GONE);
    }

    private void mResetEventVariables()
    {
        this.mEventAction = "";
        this.mEventValue.clear();
    }


    public void mLevelStreamResponseCompleted(ArrayList<ProfileSpinnerItem> streamList) {
        // request to update user level info on server
        updateUserEducationLevel();

        if(mRootView == null)return;
        //  show next layout to select current stream
        // set heading to acco. current stream screen
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));

        // hide current level radio groups
        mRootView.findViewById(R.id.user_education_radio_group).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);

        // show current education layout
        mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_education_layout).startAnimation(animationFromTop);

        // set current level education
        TextView  currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);
        currentLevelTxtView.setVisibility(View.VISIBLE);
        int currentLevelId = MainActivity.mProfile.getCurrent_level_id();
        if (currentLevelId == ProfileMacro.LEVEL_TWELFTH
                ||  currentLevelId == ProfileMacro.LEVEL_TENTH) {
            currentLevelTxtView.setText(getString(R.string.school));
        } else if (currentLevelId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
            currentLevelTxtView.setText(getString(R.string.college));
        } else {
            currentLevelTxtView.setText(getString(R.string.pg_college));
        }

        if (this.mSkipButton == null)
            this.mSkipButton = ((TextView) mRootView.findViewById(R.id.user_education_skip_Text_View));
        this.mSkipButton.setText(getString(R.string.Skip));
        // show streams for current level
        mStreamRecyclerView.setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);

        this.mStreamList = streamList;
        if(MainActivity.mProfile != null){

            int userStreamId = MainActivity.mProfile.getCurrent_stream_id();
            if(mStreamList != null){
                int count = mStreamList.size();
                for (int i = 0; i <  count; i++) {
                    ProfileSpinnerItem streamOj = mStreamList.get(i);
                    if(streamOj == null )continue;
                    if(streamOj.getId() == userStreamId) {
                        streamOj.setSelected(true);
                        // update institute count for this stream
                        setInstituteCount(String.valueOf(streamOj.getInstitutes_count()));
                    }else {
                        streamOj.setSelected(false);
                    }
                }
            }
        }
        if (mStreamAdapter == null) {
            mStreamAdapter = new ExamStreamAdapter(this,getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
            mStreamRecyclerView.setAdapter(mStreamAdapter);
        } else {
            mStreamRecyclerView.setAdapter(mStreamAdapter);
            mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
        }
    }

    private void updateUserEducationLevel() {
        HashMap<String, String> params = new HashMap<>();
        params.put("current_level_id", String.valueOf(MainActivity.mProfile.getCurrent_level_id()));
        params.put("current_sublevel_id", String.valueOf(mUserSubLevelID));
        params.put("preferred_level", String.valueOf(MainActivity.mProfile.getPreferred_level()));
        params.put("current_score_type", String.valueOf(ProfileMacro.PERCENTAGE));

        // set location coordinates
        if (getActivity() != null){
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                mLastLocation = LocationServices.FusedLocationApi.getLastLocation(getGoogleApiClient());
                if (mLastLocation != null) {
                    params.put("latitude", String.valueOf(mLastLocation.getLatitude()));
                    params.put("longitude", String.valueOf(mLastLocation.getLongitude()));
                }
            }
         }
        // save user's profile data on server
        if(this.mListener != null)
            this.mListener.requestForProfile(params);
    }

    private GoogleApiClient getGoogleApiClient(){
        if(mGoogleApiClient == null && getActivity() != null) {
            mGoogleApiClient = ((MainActivity)getActivity()).getGoogleClient();
        }
        return mGoogleApiClient;
    }

    @Override
    public void updateInstituteCountOnStreamSelection(int instituteCount) {
        setInstituteCount(String.valueOf(instituteCount));
    }

    @Override
    public void updateInstituteCountOnExamSelection() {
        int instituteCount = 0;
        boolean isAnyExamSelected = false;
        if(mAllExamList != null && !mAllExamList.isEmpty()){
            for (Exam exam :mAllExamList ) {
                if(exam == null )continue;
                ArrayList<ExamDetail> examDetailList = exam.getExam_details();
                if(examDetailList != null) {
                    int count = examDetailList.size();
                    for (int i = 0; i < count; i++) {
                        ExamDetail examDetailObj = examDetailList.get(i);
                        if (examDetailObj == null) continue;
                        if (examDetailObj.isSelected()) {
                            isAnyExamSelected = true;
                            if (instituteCount < examDetailObj.getInstitutes_count())
                                instituteCount = examDetailObj.getInstitutes_count();
                        }
                    }
                }
            }
        }
        if(!isAnyExamSelected){
            if(isAdded()) {
                instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).
                        getInt(getString(R.string.pref_institute_count), instituteCount);
            }
        }
        if(instituteCount > 0){
            setInstituteCount(String.valueOf(instituteCount));
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

                LocationServices.FusedLocationApi.removeLocationUpdates(getGoogleApiClient(), locationListener);
            }
            if(mListener != null)
                mListener.onRequestForLocationUpdate(params);
        }else{
            setUserEducationStream();
        }

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
                        LocationServices.FusedLocationApi.requestLocationUpdates(getGoogleApiClient(), locationRequest, locationListener);
                        ((MainActivity)getActivity()).showProgressDialog(Constants.TAG_USER_EXAMS_SUBMISSION,Constants.THEME_TRANSPARENT);
                }else{
                    if(mListener != null)
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


    private void createLocationRequest() {
        locationRequest = LocationRequest.create()
                .setFastestInterval(5 * 1000)
                .setInterval(30 * 1000)
                .setPriority(LocationRequest.PRIORITY_LOW_POWER);

    }
    private void checkLocationSettings() {
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);
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
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);
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
        void onRequestForSubLevels(int level);
        void onRequestForLevelStreams(int level);
        void onSkipSelectedInProfileBuilding();
        void onUserExamSelected(HashMap<String, String> examJson);
        void displayMessage(int messageId);
        void requestForProfile(HashMap<String, String> params);
        void onRequestForLocationUpdate(HashMap<String, String> params);
        void onRequestForUserExams();
    }

}
