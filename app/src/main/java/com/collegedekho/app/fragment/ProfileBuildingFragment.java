package com.collegedekho.app.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
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
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamStreamAdapter;
import com.collegedekho.app.adapter.ProfileBuildingExamAdapter;
import com.collegedekho.app.adapter.SubLevelAdapter;
import com.collegedekho.app.animation.AnimationLoader;
import com.collegedekho.app.crop.Crop;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.entities.SubLevel;
import com.collegedekho.app.listener.ExamFragmentListener;
import com.collegedekho.app.listener.ExamInstituteCountListener;
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
import com.collegedekho.app.widget.NumberPicker;
import com.fasterxml.jackson.jr.ob.JSON;
import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileBuildingFragment.OnUserEducationInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileBuildingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileBuildingFragment extends BaseFragment implements ProfileFragmentListener,
        ExamFragmentListener, ExamInstituteCountListener
{

    private static final char[] NUMBER_LIST = TickerUtils.getDefaultNumberList();
    protected static final Random RANDOM = new Random(System.currentTimeMillis());
    private static final String TAG = "user_education_fragment";
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
    private Uri mImageCaptureUri;
    private int mUserSubLevelID = 0;
    private int mUserCurrentMarks = 0;
    private int mLastSelectedCurrentLevelID = 0; // if last selected current level  and stream are same then present exam list be used
    private String mEventCategory = "";
    private String mEventAction = "";
    private HashMap<String, Object> mEventValue = new HashMap<>();;
    final String[] marks_arrays = {"30-40%", "40-50%","50-60%","60-70%", "70-80%", "80-90%", "90-100%",};
    private String mInstituteCount = "";

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        animationFromTop = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_top);
        animationFromTop.setDuration(Constants.ANIM_SHORTEST_DURATION);
        animationFromBottom = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_bottom);
        animationFromBottom.setDuration(Constants.ANIM_SHORTEST_DURATION);

        TickerView mInstituteCountTicker = (TickerView) mRootView.findViewById(R.id.institute_count_ticker);
        mInstituteCountTicker.setCharacterList(NUMBER_LIST);
        mInstituteCountTicker.setText("20149");
        mInstituteCountTicker.setGravity(Gravity.CENTER);

        if(MainActivity.mProfile != null){

            mUserSubLevelID = MainActivity.mProfile.getCurrent_sublevel_id();
            mUserCurrentMarks = MainActivity.mProfile.getCurrent_score();
            // update profile image  e
            updateProfileImage();

            String userName = MainActivity.mProfile.getName();
            if(userName != null && !userName.isEmpty()){
                if(!userName.equalsIgnoreCase(getString(R.string.ANONYMOUS_USER))) {
                    view.findViewById(R.id.user_education_edit_name_til).setVisibility(View.GONE);
                    view.findViewById(R.id.user_education_name_layout).setVisibility(View.VISIBLE);
                    ((TextView)view.findViewById(R.id.user_name)).setText(userName);
                    mRootView.findViewById(R.id.user_education_name_layout).setContentDescription("Your name is " + userName);
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
                mRootView.findViewById(R.id.user_education_phone_layout).setContentDescription("your phone number is " + userPhoneNumber);
            }else {
                view.findViewById(R.id.user_education_edit_phone_til).setVisibility(View.VISIBLE);
                view.findViewById(R.id.user_education_phone_layout).setVisibility(View.GONE);
            }
        }

        mStreamRecyclerView = (RecyclerView)view.findViewById(R.id.user_education_recycler_view);
        mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mExamSearchView = (SearchView) view.findViewById(R.id.user_exam_search_view);
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
        view.findViewById(R.id.user_profile_image_update).setOnClickListener(this);
        view.findViewById(R.id.go_to_recommended).setOnClickListener(this);
        view.findViewById(R.id.go_to_dash_board).setOnClickListener(this);
        view.findViewById(R.id.go_to_profile).setOnClickListener(this);
        view.findViewById(R.id.user_exam_search_container).setOnClickListener(this);
        view.findViewById(R.id.user_profile_image_update).setContentDescription("click to upload your photograph");
        mExamSearchView.setOnSearchClickListener(this);
    }

    private void mLoadUserProfileCompletedUI(){

        if(MainActivity.mProfile == null)
            return;
        Profile profile = MainActivity.mProfile;

        // if current education selected  then show next layout
        if(profile.getCurrent_level_id() >= 1 && profile.getCurrent_sublevel_id() >= 1) {

            if(isAdded()) {
                int instituteCount = getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).
                        getInt(getString(R.string.pref_institute_count), 0);
                setInstituteCount(String.valueOf(instituteCount));
            }
            // set mDeviceProfile profile completion progress
            CircularProgressBar profileCompleted =  (CircularProgressBar) mRootView.findViewById(R.id.user_profile_progress);
            profileCompleted.setProgress(0);
            profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);

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
                ((TextView) mRootView.findViewById(R.id.user_education_skip_Text_View)).setText(getString(R.string.not_preparing));

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

                // show streams list to choose current stream based on current level
                try {
                    mStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class,
                            ProfileMacro.getStreamJson(MainActivity.mProfile.getCurrent_level_id()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(MainActivity.mProfile != null){

                    int userStreamId = MainActivity.mProfile.getCurrent_stream_id();
                    if(mStreamList != null){
                        int count = mStreamList.size();
                        for (int i = 0; i <  count; i++) {
                            ProfileSpinnerItem streamOj = mStreamList.get(i);
                            if(streamOj == null )continue;
                            if(streamOj.getId() == userStreamId)
                                streamOj.setSelected(true);
                            else
                                streamOj.setSelected(false);
                        }
                    }

                }


                ((TextView) mRootView.findViewById(R.id.user_education_skip_Text_View)).setText(getString(R.string.Skip));
                if (mStreamAdapter == null) {
                    mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
                    mStreamRecyclerView.setAdapter(mStreamAdapter);
                } else {
                    mStreamRecyclerView.setAdapter(mStreamAdapter);
                    mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
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
        if (mainActivity != null)
            mainActivity.currentFragment = this;
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

            if( mRootView != null){
                (mRootView.findViewById(R.id.user_education_name_edit_text)).requestFocus();
            }
        }

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
            case R.id.go_to_recommended:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
                this.mEventValue.put("action_what", "go_to_recommended");
                mTakeMeToRecommended();
                break;
            case R.id.go_to_dash_board:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
                this.mEventValue.put("action_what", "go_to_dash_board");
                mTakeMeToDashBoard();
                break;
            case R.id.go_to_profile:
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
                this.mEventValue.put("action_what", "go_to_profile");
                mTakeMeToProfile();
                break;
            case R.id.user_exam_search_view:
            case R.id.user_exam_search_container:
                mExamSearchView.onActionViewExpanded();
                this.mEventAction = MainActivity.getResourceString(R.string.ACTION_SEARCH);
                this.mEventValue.put("searching_what", "exams");
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
    private void setInstituteCount(final String count) {
        if(!mInstituteCount.equalsIgnoreCase(count)) {
            mInstituteCount = count;
            if (mRootView != null) {
                final TickerView mInstituteCountTicker = (TickerView) mRootView.findViewById(R.id.institute_count_ticker);
                mInstituteCountTicker.setCharacterList(NUMBER_LIST);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (isAdded() && mInstituteCountTicker != null) {
                            mInstituteCountTicker.setText(String.valueOf(count));
                            MediaPlayer mp = MediaPlayer.create(getActivity().getApplicationContext(), R.raw.institute_count);
                            mp.start();
                        }
                    }
                }, 200);
            }
        }
    }

    protected String getRandomNumber(int digits) {
        final int digitsInPowerOf10 = (int) Math.pow(10, digits);
        return Integer.toString(RANDOM.nextInt(digitsInPowerOf10) +
                digitsInPowerOf10 * (RANDOM.nextInt(8) + 1));
    }

    private void mRequestForSubLevels(int level) {
        if(NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED){
            Utils.DisplayToast(getContext(), getString(R.string.no_internet));
            return;
        }
        mListener.onRequestForSubLevels(level);
    }

    public void mSubLevelsResponseCompleted(ArrayList<SubLevel> subLevelsList){
        if(subLevelsList != null && !subLevelsList.isEmpty()){
            int instituteCount = subLevelsList.get(0).getInstitutes_count();
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
        // set mDeviceProfile's sub level base
        mUserSubLevelID = (int)subLevelAdapter.getItemId(position);
        //mUserSubLevelID = ProfileMacro.getSubLevel(which, userLevel);
        // Now show Next Button
        mAnimateFooterButtons();
        // dismiss dialog
        if(subLevelDialog != null && subLevelDialog.isShowing())
            subLevelDialog.dismiss();
        // check if fragment is added to activity then show dialog to ask marks
        // otherwise set default marks and request for streams
        if(isAdded()) {
            mShowDialogForMarks();
        }else{
            mUserCurrentMarks = mGetMarks(-1);
            mNextStepSelected();
        }
    }

    private void mShowDialogForMarks() {

        View marksDialogView = getActivity().getLayoutInflater().inflate(R.layout.layout_marks_picker, null, false);

        final NumberPicker mMarksPicker = (NumberPicker) marksDialogView.findViewById(R.id.marks_number_picker);
        mMarksPicker.setMaxValue(marks_arrays.length-1);
        mMarksPicker.setMinValue(0);
        mMarksPicker.setSaveFromParentEnabled(false);
        mMarksPicker.setSaveEnabled(true);
        mMarksPicker.setWrapSelectorWheel(false);
        mMarksPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        mMarksPicker.setDisplayedValues(marks_arrays);

        AlertDialog marksDialog = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.please_enter_your_current_marks))
                .setCancelable(false)
                .setView(marksDialogView)
                .setPositiveButton("Done", null)
                .create();
        marksDialog.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(final DialogInterface dialog) {

                Button b = ((AlertDialog)dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        int marksPosition = mMarksPicker.getValue();
                        mUserCurrentMarks = mGetMarks(marksPosition);
                        mNextStepSelected();
                        dialog.dismiss();
                    }
                });
            }
        });
        marksDialog.show();

        marksDialogView.startAnimation(AnimationLoader.getInAnimation(getActivity()));

    }
    private int mGetMarks(int position){
        if(position == 0 ){
            return 35;
        }else if(position == 1 ){
            return 45;
        }else if(position == 2 ){
            return 55;
        }else if(position == 3 ){
            return 65;
        }else if(position == 4 ){
            return 75;
        }else if(position == 5 ){
            return 85;
        }else if(position == 6 ){
            return 95;
        }
        return 50;
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

        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);

        if(this.mListener ==  null)
            return;

        mClearUserExams();
        this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_ACTION);
        this.mEventValue.put("action_what", "skip");
        this.mEventValue.put("action_where", "current_stream");

        this.mListener.onSkipSelectedInProfileBuilding();
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
                setUserEducationStream();
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
        int streamType = 1 ; //  0 for college and 1 for school
        int checkedRadioGroupIndex = radioGroupEducation.indexOfChild(mRootView.findViewById(selectedRadioButton));
        if(checkedRadioGroupIndex  == 1){
            streamType = 0;
            currentLevelID =  ProfileMacro.LEVEL_UNDER_GRADUATE;
        }else if(checkedRadioGroupIndex == 2){
            streamType = 0;
            currentLevelID =  ProfileMacro.LEVEL_POST_GRADUATE;
        }
        // request for streams
        this.mListener.onRequestForLevelStreams(streamType);

        // setting default preferred level ug
        int preferredLevelId = ProfileMacro.LEVEL_UNDER_GRADUATE;
        if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
            preferredLevelId = ProfileMacro.LEVEL_POST_GRADUATE;
        } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
            preferredLevelId = ProfileMacro.LEVEL_PHD;
        }

        // set mDeviceProfile' current  and preferred level locally
        if (MainActivity.mProfile != null) {
            mLastSelectedCurrentLevelID = MainActivity.mProfile.getCurrent_level_id();
            MainActivity.mProfile.setCurrent_sublevel_id(mUserSubLevelID);
            MainActivity.mProfile.setCurrent_level_id(currentLevelID);
            MainActivity.mProfile.setPreferred_level(preferredLevelId);
        }

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



    private void setUserEducationStream() {

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

        ((TextView) mRootView.findViewById(R.id.user_education_skip_Text_View)).setText(getString(R.string.not_preparing));

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

        // check mDeviceProfile's name and phone Number
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        if(!isExamSelected){
            mListener.displayMessage(R.string.SELECT_ONE_EXAM);
            return;
        }

        userParams.put("yearly_exams", selectedExamsBuffer.toString());
        this.mListener.onUserExamSelected(userParams);

        this.mEventCategory = MainActivity.getResourceString(R.string.CATEGORY_PREFERENCE);
        this.mEventAction = MainActivity.getResourceString(R.string.ACTION_USER_EXAM_SELECTED);
        // for (int n = 0; n < parentArray.length(); n++) {
        try
        {
            // JSONObject examDetail = (JSONObject) parentArray.get(n);
            this.mEventValue.put(MainActivity.getResourceString(R.string.USER_EXAM_SELECTED), selectedExamsBuffer.toString());//examDetail.get(MainActivity.getResourceString(R.string.EXAM_ID)) + "#" + examDetail.get(MainActivity.getResourceString(R.string.SCORE)) + "#" + examDetail.get(MainActivity.getResourceString(R.string.STATUS)));

            //Events
            AnalyticsUtils.SendAppEvent(this.mEventCategory, this.mEventAction, this.mEventValue, this.getActivity());

            this.mEventValue.clear();
        }
        catch(Exception e)
        {

        }
        // }

        this.mResetEventVariables();
    }


    /**
     *
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
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
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
    }

    private void mEditCurrentStream(){
        isStreamSelected = false;
        mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_skip_Text_View)).setText("Skip");
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));
        mStreamRecyclerView.setVisibility(View.VISIBLE);

        if(mStreamList == null){

            try {
                mStreamList = JSON.std.listOfFrom(ProfileSpinnerItem.class,
                        ProfileMacro.getStreamJson(MainActivity.mProfile.getCurrent_level_id()));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        // set selected true stream if mDeviceProfile has same stream
        if(MainActivity.mProfile != null){

            int userStreamId = MainActivity.mProfile.getCurrent_stream_id();
            if(mStreamList != null){
                int count = mStreamList.size();
                for (int i = 0; i <  count; i++) {
                    ProfileSpinnerItem streamOj = mStreamList.get(i);
                    if(streamOj == null )continue;
                    if(streamOj.getId() == userStreamId)
                        streamOj.setSelected(true);
                    else
                        streamOj.setSelected(false);
                }
            }
        }

        if(mStreamAdapter == null) {
            mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
            mStreamRecyclerView.setAdapter(mStreamAdapter);
        }else {
            mStreamRecyclerView.setAdapter(mStreamAdapter);
            mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
        }
    }

    private void mEditUserExams(){
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
        mStreamRecyclerView.setVisibility(View.VISIBLE);
        updateExamsLayouts(this.mAllExamList);
    }


    public void onExamSubmittedSuccessfully() {
        mStreamRecyclerView.setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.VISIBLE);

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
    }

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

    private boolean getUserNameAndPhone(HashMap<String, String> profileParams){

        View nameView = mRootView.findViewById(R.id.user_education_edit_name_til);
        if(nameView.getVisibility() == View.VISIBLE){
            String userName = ((EditText) mRootView.findViewById(R.id.user_education_name_edit_text)).getText().toString().trim();
            if(!userName.isEmpty()) {
                if (!Utils.isValidName(userName)) {
                    mListener.displayMessage(R.string.NAME_INVALID);
                    return false;
                }
                profileParams.put(getString(R.string.USER_NAME),userName);

                // hide name EditText
                ((TextView) mRootView.findViewById(R.id.user_name)).setText(userName);
                mRootView.findViewById(R.id.user_education_name_layout).setContentDescription("Your name is " + userName);
                mRootView.findViewById(R.id.user_education_name_layout).setVisibility(View.VISIBLE);
                nameView.setVisibility(View.GONE);
                if(MainActivity.mProfile != null)
                    MainActivity.mProfile.setName(userName);
            }
        }else{
            if(MainActivity.mProfile != null)
                profileParams.put(getString(R.string.USER_NAME),MainActivity.mProfile.getName());
        }

        View phoneView = mRootView.findViewById(R.id.user_education_edit_phone_til);
        if(phoneView.getVisibility() == View.VISIBLE){
            String userPhoneNumber = ((EditText) mRootView.findViewById(R.id.user_education_phone_edit_text)).getText().toString().trim();
            if (!userPhoneNumber.trim().isEmpty()) {
                if (userPhoneNumber.length() <= 9 || !Utils.isValidPhone(userPhoneNumber)) {
                    mListener.displayMessage(R.string.PHONE_INVALID);
                    return false;
                }
                profileParams.put(getString(R.string.USER_PHONE),userPhoneNumber);
                ((TextView) mRootView.findViewById(R.id.user_phone)).setText(userPhoneNumber);
                mRootView.findViewById(R.id.user_education_phone_layout).setContentDescription("your phone number is " + userPhoneNumber);
                mRootView.findViewById(R.id.user_education_phone_layout).setVisibility(View.VISIBLE);
                phoneView.setVisibility(View.GONE);
                if(MainActivity.mProfile != null)
                    MainActivity.mProfile.setPhone_no(userPhoneNumber);
            }
        }else{
            if(MainActivity.mProfile != null)
                profileParams.put(getString(R.string.USER_PHONE),MainActivity.mProfile.getPhone_no());
        }
        return true;
    }



    private void mRequestForImageCapture() {

        // Determine Uri of camera image to save.
        File imageFile = new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        mImageCaptureUri = Uri.fromFile(imageFile);

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

            CircularProgressBar profileCompleted =  (CircularProgressBar) mRootView.findViewById(R.id.user_profile_progress);
            profileCompleted.setProgress(0);
            profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);

        }
    }

    public void profileUpdatedSuccessfully(){
        if( mRootView == null || MainActivity.mProfile == null)
            return;
        Profile profile = MainActivity.mProfile;
        String name = profile.getName();

        if (name != null && !name.isEmpty() && !name.toLowerCase().contains(Constants.ANONYMOUS_USER.toLowerCase())){
            ((TextView) mRootView.findViewById(R.id.user_name)).setText(name);
            mRootView.findViewById(R.id.user_education_name_layout).setContentDescription("Your name is " + name);
            mRootView.findViewById(R.id.user_education_name_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_edit_name_til).setVisibility(View.GONE);
        }

        String phone = profile.getPhone_no();
        if (phone != null && !phone.isEmpty() && !phone.equalsIgnoreCase("null")) {
            ((TextView) mRootView.findViewById(R.id.user_phone)).setText(phone);
            mRootView.findViewById(R.id.user_education_phone_layout).setContentDescription("your phone number is " + phone);
            mRootView.findViewById(R.id.user_education_phone_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_edit_phone_til).setVisibility(View.GONE);
        }

        CircularProgressBar profileCompleted =  (CircularProgressBar) mRootView.findViewById(R.id.user_profile_progress);
        profileCompleted.setProgress(0);
        profileCompleted.setProgressWithAnimation(MainActivity.mProfile.getProgress(), 2000);

    }

    public void updateExamsList(ArrayList<Exam> examList){
        // update user stream ID
        updateUserStreamId();
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
        for (int i = 0; i < examCount; i++) {
            Exam exam = mAllExamList.get(i);
            if(exam == null || exam.getExam_type() == ProfileMacro.OTHER_EXAM)continue;
            //mStreamExamList.add(exam);
            mAllExamList.remove(i);
            mAllExamList.add(0, exam);
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


    private void mTakeMeToRecommended(){
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        if(mListener == null)
            return;
        // check user's name and phone Number
        // if name or phone is given and have any issue then
        // the it will show a toast
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        mRootView.findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        mListener.OnTakeMeToRecommended();
    }

    private void mTakeMeToDashBoard(){
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        if(mListener == null)
            return;
        // check mDeviceProfile's name and phone Number
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        if(userParams.size() >= 1)
            this.mListener.requestForProfile(userParams);


        mRootView.findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        mListener.OnTakeMeToDashBoard();
    }

    private void mTakeMeToProfile(){
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }

        if(mListener == null)
            return;

        // check mDeviceProfile's name and phone Number
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        if(userParams.size() >= 1)
            this.mListener.requestForProfile(userParams);

        mRootView.findViewById(R.id.user_education_top_layout).setVisibility(View.GONE);
        mListener.OnTakeMeToProfile();
    }

    private void mResetEventVariables()
    {
        this.mEventAction = "";
        this.mEventValue.clear();
    }


    public void mLevelStreamResponseCompleted(ArrayList<ProfileSpinnerItem> streamList) {
         // update user level on server
        updateUserEducationLevel();
        // set Institute Count
        if(streamList != null && !streamList.isEmpty()) {
            int instituteCount = streamList.get(0).getInstitutes_count();
            if(isAdded()) {
                getActivity().getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE).edit()
                        .putInt(getString(R.string.pref_institute_count), instituteCount).apply();
            }
            setInstituteCount(String.valueOf(instituteCount));
        }
        //  show next layout to select current stream
        // set heading to acco. current stream screen
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));

        // hide current level radio groups
        mRootView.findViewById(R.id.user_education_radio_group).setVisibility(View.GONE);

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

        ((TextView) mRootView.findViewById(R.id.user_education_skip_Text_View)).setText(getString(R.string.Skip));
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
                    if(streamOj.getId() == userStreamId)
                        streamOj.setSelected(true);
                    else
                        streamOj.setSelected(false);
                }
            }
        }
        if (mStreamAdapter == null) {
            mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);
            mStreamRecyclerView.setAdapter(mStreamAdapter);
        } else {
            mStreamRecyclerView.setAdapter(mStreamAdapter);
            mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
        }
    }

    private void updateUserEducationLevel(){
        HashMap<String, String> params = new HashMap<>();
        params.put("current_level_id", String.valueOf(MainActivity.mProfile.getCurrent_level_id()));
        params.put("current_sublevel_id", String.valueOf(mUserSubLevelID));
        params.put("preferred_level", String.valueOf(MainActivity.mProfile.getPreferred_level()));
        params.put("current_score", String.valueOf( mUserCurrentMarks));
        params.put("current_score_type", String.valueOf(ProfileMacro.PERCENTAGE));

        // check mDeviceProfile's name and phone Number
        if(!getUserNameAndPhone(params)){
            return;
        }
        // save mDeviceProfile profile data on server
        this.mListener.requestForProfile(params);
    }
    private void updateUserStreamId() {
        HashMap<String, String> params = new HashMap<>();
        // check mDeviceProfile's name and phone Number
        if(!getUserNameAndPhone(params)){
            return;
        }
        // save user's current stream id on server
        params.put("current_stream_id", String.valueOf(MainActivity.mProfile.getCurrent_stream_id()));
        this.mListener.requestForProfile(params);
    }

    /**
     * This method is used to update name and phone number
     * when user select option to go to recommended colleges
     */
    public void updateUserNamePhoneNumber(){
        HashMap<String, String> userParams = new HashMap<>();
        if(!getUserNameAndPhone(userParams)){
            return;
        }

        if(userParams.size() >= 1 && mListener != null){
            if(NetworkUtils.getConnectivityStatus() != Constants.TYPE_NOT_CONNECTED) {
                mListener.requestForProfile(userParams);
            }
        }
    }

    @Override
    public void updateInstituteCount() {
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
        void onRequestForUserExams();
        void OnTakeMeToRecommended();
        void OnTakeMeToDashBoard();
        void OnTakeMeToProfile();
        void requestToUploadProfileImage(byte[] fileByteArray);
    }

}
