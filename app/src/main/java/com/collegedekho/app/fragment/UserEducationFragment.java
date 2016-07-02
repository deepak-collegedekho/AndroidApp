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
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamStreamAdapter;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.listener.ExamOnQueryListener;
import com.collegedekho.app.listener.ExamSearchCloseListener;
import com.collegedekho.app.listener.ProfileFragmentListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
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
 * {@link UserEducationFragment.OnUserEducationInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UserEducationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserEducationFragment extends BaseFragment implements ProfileFragmentListener {

    private static final String TAG = "user_education_fragment";
    private OnUserEducationInteractionListener mListener;
    private RecyclerView mStreamRecyclerView;
    private View mRootView ;
    private List<ProfileSpinnerItem> mStreamList = null;
    private ExamStreamAdapter mStreamAdapter;
    private ExamsAdapter mExamAdapter;
    private SearchView mExamSearchView;
    private ArrayList<Exam> mExamList = new ArrayList<>();
    private ExamOnQueryListener cExamQueryListener;
    private boolean  isStreamSelected;
    private Animation animation;
    private File uploadTempImageFile;
    private Uri mImageCaptureUri;
    private int userSubLevelID = 0;

    public UserEducationFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment UserEducationFragment.
     */
    public static UserEducationFragment newInstance() {
        UserEducationFragment fragment = new UserEducationFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        animation = AnimationUtils.loadAnimation(this.getActivity(), R.anim.slide_from_top);
        animation.setDuration(Constants.ANIM_SHORTEST_DURATION);
        return  mRootView = inflater.inflate(R.layout.fragment_user_education, container, false);
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

            String userName = MainActivity.user.getName();
            if(userName != null && !userName.isEmpty()
                    && !userName.equalsIgnoreCase(getString(R.string.ANONYMOUS_USER))) {
                view.findViewById(R.id.user_education_edit_name_til).setVisibility(View.GONE);
                view.findViewById(R.id.user_education_name_layout).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.user_education_name)).setText(userName);
            }else {
                view.findViewById(R.id.user_education_edit_name_til).setVisibility(View.VISIBLE);
                view.findViewById(R.id.user_education_name_layout).setVisibility(View.GONE);
            }

            String userPhoneNumber = MainActivity.user.getPhone_no();
            if(userPhoneNumber != null && !userPhoneNumber.isEmpty()) {
                view.findViewById(R.id.user_education_edit_phone_til).setVisibility(View.GONE);
                view.findViewById(R.id.user_education_phone_layout).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.user_education_phone)).setText(userPhoneNumber);
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
            if(MainActivity.mProfile.getCurrent_level_id() >= 1) {

                this.mListener.onRequestForUserExams();

                View nextView = mRootView.findViewById(R.id.user_education_next_button);
                if (nextView.getAlpha() != 1) {
                    nextView.setVisibility(View.VISIBLE);
                    nextView.setAlpha(1);
                    mRootView.findViewById(R.id.user_education_skip_button).animate()
                            .setStartDelay(Constants.ANIM_SHORTEST_DURATION)
                            .x(mRootView.getX() + mRootView.getPaddingLeft())
                            .scaleXBy(-0.5f)
                            .scaleYBy(-0.5f)
                            .setDuration(Constants.ANIM_SHORT_DURATION);
                }

                mRootView.findViewById(R.id.user_education_radio_group).setVisibility(View.GONE);
                //  show next layout to select current stream
                ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));
                mStreamRecyclerView.setVisibility(View.VISIBLE);

                // set current level education
                mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.user_education_education_layout).startAnimation(animation);

                TextView  currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);

                currentLevelTxtView.setVisibility(View.VISIBLE);
                int currentEducationId = MainActivity.mProfile.getCurrent_level_id();
                if (currentEducationId == ProfileMacro.LEVEL_TWELFTH) {
                    currentLevelTxtView.setText(" School");
                } else if (currentEducationId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                    currentLevelTxtView.setText(" College");
                } else {
                    currentLevelTxtView.setText(" PG College");
                }

                // show streams for current level
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
        mExamSearchView.setOnSearchClickListener(this);
        //view.findViewById(R.id.user_exam_search_hint).setOnClickListener(this);

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
            case R.id.user_education_skip_button:
                mUserEducationSkip();
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
                mEditCurrentEducationLevel();
                break;
            case R.id.user_education_stream_edit_btn:
                mEditCurrentStream();
                break;
            case R.id.user_education_exams_edit_btn:
                mEditUserExams();
                break;
            case R.id.user_education_next_button:
                mSelectedNextButton();
                break;
            case R.id.go_to_recommended:
                mTakeMeToRecommended();
                break;
            case R.id.go_to_dash_board:
                mTakeMeToDashBoard();
                break;
            case R.id.go_to_profile:
                mTakeMeToProfile();
                break;
           case R.id.user_exam_search_view:
            //case R.id.user_exam_search_hint:
                if(!mExamSearchView.isActivated()){
                    getView().findViewById(R.id.user_exam_search_hint).setVisibility(View.GONE);
                } else {
                   getView().findViewById(R.id.user_exam_search_hint).setVisibility(View.VISIBLE);
                }
                break;

            default:
                break;
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
                                userSubLevelID = ProfileMacro.getSubLevel(which, userLevel);
                                dialog.dismiss();
                            }
                        }).show();

        // Now show Next Button
        mShowNextButton();
    }

    private void mShowNextButton() {
       if(mRootView.findViewById(R.id.user_education_radio_group).getVisibility() == View.VISIBLE) {
            View nextView = mRootView.findViewById(R.id.user_education_next_button);
            if (nextView.getAlpha() == 1)
                return;

            Runnable animEnd = new Runnable() {
                @Override
                public void run() {
                    mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
                }
            };

            nextView.setX(4000);

            nextView.animate()
                    .x(mRootView.getWidth() - nextView.getWidth() - nextView.getPaddingRight())
                    .alpha(1)
                    .withEndAction(animEnd)
                    .setDuration(Constants.ANIM_AVERAGE_DURATION);

            mRootView.findViewById(R.id.user_education_skip_button).animate()
                    .setStartDelay(Constants.ANIM_SHORTEST_DURATION)
                    .x(mRootView.getX() + mRootView.getPaddingLeft())
                    .scaleXBy(-0.5f)
                    .scaleYBy(-0.5f)
                    .setDuration(Constants.ANIM_SHORT_DURATION);
        }
    }

    private void mUserEducationSkip() {
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
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

        View radioGroupEducation = mRootView.findViewById(R.id.user_education_radio_group);
        if(radioGroupEducation.getVisibility() == View.VISIBLE) {
            this.mListener.onIknowWhatIWant();
         }
        else if( !isStreamSelected && mStreamRecyclerView.getVisibility() == View.VISIBLE){
            this.mListener.onSkipAfterSelectLevel();
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
                MainActivity.mProfile.setCurrent_sublevel_id(userSubLevelID);
                MainActivity.mProfile.setCurrent_level_id(currentLevelID);
                MainActivity.mProfile.setPreferred_level(preferredLevelId);
            }


            HashMap<String, String> params = new HashMap<>();
            params.put("current_level_id", "" + currentLevelID);
            params.put("current_sublevel_id", "" + userSubLevelID);
            params.put("preferred_level", "" + preferredLevelId);

            // check user's name and phone Number
            if(!getUserNameAndPhone(params)){
                return;
            }

            // save user profile data on server
            this.mListener.onRequestToUpdateUserProfile(params);
            this.mListener.onRequestForUserExams();

            //  show next layout to select current stream
            ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));
            radioGroupEducation.setVisibility(View.GONE);
            mStreamRecyclerView.setVisibility(View.VISIBLE);

            // set current level education
            mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_education_layout).startAnimation(animation);

            TextView  currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);

            currentLevelTxtView.setVisibility(View.VISIBLE);
            int currentEducationId = MainActivity.mProfile.getCurrent_level_id();
            if (currentEducationId == ProfileMacro.LEVEL_TWELFTH) {
                currentLevelTxtView.setText(" School");
            } else if (currentEducationId == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                currentLevelTxtView.setText(" College");
            } else {
                currentLevelTxtView.setText(" PG College");
            }

            // show streams for current level
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

            // check is stream selected
            if(!isStreamSelected){
                mListener.displayMessage(R.string.please_select_your_stream);
                return;
            }
            // set user'current stream locally
            if(MainActivity.mProfile != null){
                MainActivity.mProfile.setCurrent_stream_id(currentStreamId);
                MainActivity.mProfile.setCurrent_stream_name(currentStreamName);
            }

            // save user's current stream id on server
            params.put("current_stream_id",""+currentStreamId);

            this.mListener.onRequestToUpdateUserProfile(params);


            // show stream Layout
            mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_stream_layout).startAnimation(animation);

            TextView  currentStreamTxtView = (TextView)mRootView.findViewById(R.id.user_education_stream);
            currentStreamTxtView.setVisibility(View.VISIBLE);
            currentStreamTxtView.setText(currentStreamName);


            ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
           // mRootView.findViewById(R.id.profile_education_next_button).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.VISIBLE);

            if(mExamAdapter == null)
                mExamAdapter = new ExamsAdapter(getActivity(),  mExamList);
            mStreamRecyclerView.setAdapter(mExamAdapter);

            cExamQueryListener = new ExamOnQueryListener(mExamList,this);
            this.mExamSearchView.setOnQueryTextListener(cExamQueryListener);

            mExamSearchView.setOnCloseListener(new ExamSearchCloseListener(mRootView.findViewById(R.id.user_exam_search_hint)));

            if(mExamList != null && mExamList.size() >0){
                mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
                mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
            }else{
                mRootView.findViewById(R.id.empty).setVisibility(View.VISIBLE);
                mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
            }

        }else{
            onUserExamsSelected();
        }
    }

    /**
     *
     * @param searchResults
     */
    public void updateExamList(ArrayList<Exam> searchResults) {
        if(searchResults != null && searchResults.size() >0){
            mRootView.findViewById(R.id.empty).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.VISIBLE);
        }else{
            mRootView.findViewById(R.id.empty).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_education_recycler_view).setVisibility(View.GONE);
        }

        if(mExamAdapter != null){
            mExamAdapter.updateExamList(searchResults);
        }
    }


    private void mEditCurrentEducationLevel(){
        mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_radio_group).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.currently_studying_yet));

        mStreamRecyclerView.setVisibility(View.GONE);
    }

    private void mEditCurrentStream(){
        isStreamSelected = false;
        mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
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
    }

    private void mEditUserExams(){
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.VISIBLE);
        ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
        mStreamRecyclerView.setVisibility(View.VISIBLE);
    }


    public void onExamSubmittedSuccessfully() {
        mStreamRecyclerView.setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_skip_button).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_heading_devider).setVisibility(View.GONE);
        mRootView.findViewById(R.id.user_education_exams_layout).setVisibility(View.VISIBLE);
        mRootView.findViewById(R.id.go_to_dashboard_layout).setVisibility(View.VISIBLE);

        mRootView.findViewById(R.id.user_education_exams_layout).startAnimation(animation);

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
                ((TextView) mRootView.findViewById(R.id.user_education_name)).setText(userName);
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
                ((TextView) mRootView.findViewById(R.id.user_education_phone)).setText(userPhoneNumber);
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
            mListener.onRequestToUpdateUserProfile(userParams);

        if(!isExamSelected){
            mListener.displayMessage(R.string.SELECT_ONE_EXAM);
            return;
        }


        this.mListener.onUserExamSelected(parentJsonObject);

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
        if(MainActivity.mProfile != null){

            String image = MainActivity.mProfile.getImage();
            if (image != null && ! image.isEmpty())
                ((CircularImageView) mRootView.findViewById(R.id.profile_image)).setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());

        }
    }

    public void updateUserExams(ArrayList<Exam> examList){
        this.mExamList.clear();
        mExamList.addAll(examList);
      }

    public void hideNavigationIcon(){
        mRootView.findViewById(R.id.navigation_cd_icon).setVisibility(View.GONE);
    }


    private void mTakeMeToRecommended(){
        if(mListener == null)
            return;
        mListener.OnTakeMeToRecommended();
    }

    private void mTakeMeToDashBoard(){
        if(mListener == null)
            return;
        mListener.OnTakeMeToDashBoard();
    }

    private void mTakeMeToProfile(){
        if(mListener == null)
            return;
        mListener.OnTakeMeToProfile();
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
        void onIknowWhatIWant();
        void onSkipAfterSelectLevel();
        void onUserExamSelected(JSONObject examJson);
        void onRemoveUserExams(JSONObject examJson);
        void displayMessage(int messageId);
        void onRequestToUpdateUserProfile(HashMap<String, String> params);
        void onRequestForUserExams();
        void OnTakeMeToRecommended();
        void OnTakeMeToDashBoard();
        void OnTakeMeToProfile();
        void onProfileImageUploaded();
    }

}
