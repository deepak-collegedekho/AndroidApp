package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.adapter.ExamStreamAdapter;
import com.collegedekho.app.adapter.ExamsAdapter;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.ProfileSpinnerItem;
import com.collegedekho.app.listener.ExamOnQueryListener;
import com.collegedekho.app.listener.ExamSearchCloseListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.ProfileMacro;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;
import com.collegedekho.app.widget.GridSpacingItemDecoration;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
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
public class UserEducationFragment extends BaseFragment {

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
                ((TextView)view.findViewById(R.id.user_education_name)).setText(":  "+userName);
            }else {
                view.findViewById(R.id.user_education_edit_name_til).setVisibility(View.VISIBLE);
                view.findViewById(R.id.user_education_name_layout).setVisibility(View.GONE);
            }

            String userPhoneNumber = MainActivity.user.getPhone_no();
            if(userPhoneNumber != null && !userPhoneNumber.isEmpty()) {
                view.findViewById(R.id.user_education_edit_phone_til).setVisibility(View.GONE);
                view.findViewById(R.id.user_education_phone_layout).setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.user_education_phone)).setText(":  "+userPhoneNumber);
            }else {
                view.findViewById(R.id.user_education_edit_phone_til).setVisibility(View.VISIBLE);
                view.findViewById(R.id.user_education_phone_layout).setVisibility(View.GONE);
            }
        }

        mStreamRecyclerView = (RecyclerView)view.findViewById(R.id.user_education_recycler_view);
        mStreamRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mStreamRecyclerView.addItemDecoration(new GridSpacingItemDecoration(2, 8, true));


        mExamSearchView = (SearchView) mRootView.findViewById(R.id.user_exam_search_view);

        view.findViewById(R.id.user_education_radio_button_school).setOnClickListener(this);
        view.findViewById(R.id.user_education_radio_button_college).setOnClickListener(this);
        view.findViewById(R.id.user_education_radio_button_pg).setOnClickListener(this);
        view.findViewById(R.id.user_education_next_button).setOnClickListener(this);
        view.findViewById(R.id.user_education_level_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_stream_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_exams_edit_btn).setOnClickListener(this);
        view.findViewById(R.id.user_education_skip_button).setOnClickListener(this);
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
            case R.id.user_education_skip_button:
                mUserEducationSkip();
                break;
            case R.id.user_education_radio_button_school:
            case R.id.user_education_radio_button_college:
            case R.id.user_education_radio_button_pg:
                if(mRootView.findViewById(R.id.user_education_radio_group).getVisibility() == View.VISIBLE) {
                    mRootView.findViewById(R.id.user_education_next_button).setVisibility(View.VISIBLE);
                    //mRootView.findViewById(R.id.user_education_skip_button).setTranslationX(-500);
                }
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

    private void mUserEducationSkip() {
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        if(this.mListener ==  null)
            return;
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
            int currentSubLevelId = ProfileMacro.CURRENT_SUB_LEVEL_SCHOOL_12TH;

            if (currentLevelID == ProfileMacro.LEVEL_UNDER_GRADUATE) {
                preferredLevelId = ProfileMacro.LEVEL_POST_GRADUATE;
                currentSubLevelId = ProfileMacro.CURRENT_SUB_LEVEL_COLLEGE_1;
            } else if (currentLevelID == ProfileMacro.LEVEL_POST_GRADUATE) {
                preferredLevelId = ProfileMacro.LEVEL_PHD;
                 currentSubLevelId = ProfileMacro.CURRENT_SUB_LEVEL_PG_1;
            }
            // set user' current  and preferred level locally
            if (MainActivity.mProfile != null) {
                MainActivity.mProfile.setCurrent_sublevel_id(currentSubLevelId);
                MainActivity.mProfile.setCurrent_level_id(currentLevelID);
                MainActivity.mProfile.setPreferred_level(preferredLevelId);
            }


            HashMap<String, String> params = new HashMap<>();
            params.put("current_level_id", "" + currentLevelID);
            params.put("current_sublevel_id", "" + currentSubLevelId);
            params.put("preferred_level", "" + preferredLevelId);

            // check user's name and phone Number
            getUserNameAndPhone(params);

            // save user profile data on server
            this.mListener.onRequestToUpdateUserProfile(params);
            this.mListener.onRequestForUserExams();

            //  show next layout to select current stream
            ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.your_current_stream));
            //mRootView.findViewById(R.id.profile_education_next_button).setVisibility(View.GONE);
            // mRootView.findViewById(R.id.user_education_skip_button).setTranslationX(750);
            radioGroupEducation.setVisibility(View.GONE);
            mStreamRecyclerView.setVisibility(View.VISIBLE);

            // set current level education
            mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.VISIBLE);
            TextView currentLevelTxtView = (TextView) mRootView.findViewById(R.id.user_education_level);
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

            int currentStreamId  =0;
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

            // show stream Layout
            mRootView.findViewById(R.id.user_education_stream_layout).setVisibility(View.VISIBLE);
            TextView  currentStreamTxtView = (TextView)mRootView.findViewById(R.id.user_education_stream);
            currentStreamTxtView.setVisibility(View.VISIBLE);
            currentStreamTxtView.setText(currentStreamName);

            // set user'current stream locally
            if(MainActivity.mProfile != null){
                MainActivity.mProfile.setCurrent_stream_id(currentStreamId);
                MainActivity.mProfile.setCurrent_stream_name(currentStreamName);
            }

            // save user's current stream id on server
            HashMap<String, String> params = new HashMap<>();
            params.put("current_stream_id",""+currentStreamId);

            // check user's name and phone Number
            getUserNameAndPhone(params);

            this.mListener.onRequestToUpdateUserProfile(params);


            ((TextView) mRootView.findViewById(R.id.user_education_heading)).setText(getString(R.string.which_exams_are_you_preparing));
           // mRootView.findViewById(R.id.profile_education_next_button).setVisibility(View.GONE);
            mRootView.findViewById(R.id.user_education_education_layout).setVisibility(View.VISIBLE);
            mRootView.findViewById(R.id.user_exam_search_container).setVisibility(View.VISIBLE);

            if(mExamAdapter == null)
                mExamAdapter = new ExamsAdapter(getActivity(),  mExamList);
            mStreamRecyclerView.setAdapter(mExamAdapter);

            cExamQueryListener = new ExamOnQueryListener(mExamList,mExamAdapter);
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

        //if(mStreamAdapter == null)
            mStreamAdapter = new ExamStreamAdapter(getActivity(), (ArrayList<ProfileSpinnerItem>) mStreamList);

        mStreamRecyclerView.setAdapter(mStreamAdapter);
        //mStreamAdapter.updateStreamList((ArrayList<ProfileSpinnerItem>) mStreamList);
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

    private void getUserNameAndPhone(HashMap<String, String> profileParams){

        View nameView = mRootView.findViewById(R.id.user_education_edit_name_til);
        if(nameView.getVisibility() == View.VISIBLE){
            String userName = ((EditText) mRootView.findViewById(R.id.user_education_name_edit_text)).getText().toString();
            if(userName != null && !userName.isEmpty()) {
                if (!Utils.isValidName(userName)) {
                    mListener.displayMessage(R.string.NAME_INVALID);
                    return;
                }
                profileParams.put("name",userName);

                // hide name EditText
                ((TextView) mRootView.findViewById(R.id.user_education_name)).setText(userName);
                mRootView.findViewById(R.id.user_education_name_layout).setVisibility(View.VISIBLE);
                nameView.setVisibility(View.GONE);
                if(MainActivity.mProfile != null)
                    MainActivity.mProfile.setName(" :"+userName);
            }
        }

        View phoneView = mRootView.findViewById(R.id.user_education_edit_phone_til);
        if(phoneView.getVisibility() == View.VISIBLE){
            String userPhoneNumber = ((EditText) mRootView.findViewById(R.id.user_education_phone_edit_text)).getText().toString();
            if (userPhoneNumber != null && !userPhoneNumber.trim().isEmpty()) {
                if (userPhoneNumber.length() <= 9 || !Utils.isValidPhone(userPhoneNumber)) {
                    mListener.displayMessage(R.string.PHONE_INVALID);
                    return;
                }
                profileParams.put("phone_no",userPhoneNumber);
                ((TextView) mRootView.findViewById(R.id.user_education_phone)).setText(userPhoneNumber);
                mRootView.findViewById(R.id.user_education_phone_layout).setVisibility(View.VISIBLE);
                phoneView.setVisibility(View.GONE);
                if(MainActivity.mProfile != null)
                    MainActivity.mProfile.setPhone_no(" :"+userPhoneNumber);
            }
        }
    }



    private void onUserExamsSelected() {

        if(this.mListener == null)
            return;

        boolean isExamSelected = false;
        JSONObject parentJsonObject=new JSONObject();
        JSONArray parentArray=new JSONArray();
        if(mExamList != null && !mExamList.isEmpty()) {
            for (Exam exam:mExamList) {
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
        if(!isExamSelected){
            mListener.displayMessage(R.string.SELECT_ONE_EXAM);
            return;
        }

        // check user's name and phone Number
        HashMap<String, String> userParams = new HashMap<>();
        getUserNameAndPhone(userParams);
        if(userParams.size() >= 1)
            mListener.onRequestToUpdateUserProfile(userParams);

        this.mListener.onUserExamSelected(parentJsonObject);


    }

    public void updateUserExams(ArrayList<Exam> examList){
        this.mExamList.clear();
        mExamList.addAll(examList);
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
        void displayMessage(int messageId);
        void onRequestToUpdateUserProfile(HashMap<String, String> params);
        void onRequestForUserExams();
        void OnTakeMeToRecommended();
        void OnTakeMeToDashBoard();
        void OnTakeMeToProfile();
    }

}
