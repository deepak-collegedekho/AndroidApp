package com.collegedekho.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Exam;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.collegedekho.app.widget.CircularImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by sureshsaini on 6/1/16.
 */
public class ProfileEditFragment extends BaseFragment {

    private onProfileUpdateListener mListener;
    private LinearLayout examsLayout, streamsLayout,recommendedLayout, educationLayout,userExamsLayout, userStreamsLayout, userEducationLayout,userRecommendedLayout,psychometricTestLayout;
    private ImageView btnEditEducation,btnEditStreams,btnEditExams;
    private Button btnTakePsycho;
    public ProfileEditFragment() {
        // required empty constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ProfileEditFragment newInstance() {
        ProfileEditFragment fragment = new ProfileEditFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        rootView.findViewById(R.id.profile_save_button).setOnClickListener(this);

        EditText mProfileName = (EditText) rootView.findViewById(R.id.profile_edit_name);
        EditText mProfilePhone = (EditText) rootView.findViewById(R.id.profile_edit_phone);
        //EditText mProfileEmail  = (EditText) rootView.findViewById(R.id.profile_edit_email);

        CircularImageView mProfileImage = (CircularImageView) rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        examsLayout = (LinearLayout) rootView.findViewById(R.id.exams_list_layout);
        educationLayout = (LinearLayout) rootView.findViewById(R.id.education_list_layout);
        streamsLayout = (LinearLayout) rootView.findViewById(R.id.streams_list_layout);
        recommendedLayout = (LinearLayout) rootView.findViewById(R.id.recommended_list_layout);

        userExamsLayout = (LinearLayout) rootView.findViewById(R.id.user_exams_layout);
        userEducationLayout = (LinearLayout) rootView.findViewById(R.id.user_education_layout);
        userStreamsLayout = (LinearLayout) rootView.findViewById(R.id.user_streams_layout);
        userRecommendedLayout = (LinearLayout) rootView.findViewById(R.id.user_recommended_layout);

        psychometricTestLayout=(LinearLayout)rootView.findViewById(R.id.psychometric_test_layout);
        btnEditEducation=(ImageView)rootView.findViewById(R.id.btn_edit_education);
        btnEditStreams=(ImageView)rootView.findViewById(R.id.btn_edit_streams);
        btnEditExams=(ImageView)rootView.findViewById(R.id.btn_edit_exams);

        btnTakePsycho=(Button)rootView.findViewById(R.id.btn_take_psychometric);

        btnEditExams.setOnClickListener(this);
        btnEditStreams.setOnClickListener(this);
        btnEditEducation.setOnClickListener(this);
        btnTakePsycho.setOnClickListener(this);
        mProfilePhone.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER)
                    mUpdateProfile();
                return false;
            }

        });

        if (MainActivity.user != null) {
            String name = MainActivity.user.getName();
            String email = MainActivity.user.getEmail();
            String phone = MainActivity.user.getPhone_no();

            if (phone != null)
                phone = phone.replace(" ", "");

            if (name.contains("Anonymous User")) {
                if (MainActivity.user.profileData[0] != null)
                    mProfileName.setText(MainActivity.user.profileData[0]);
            } else
                mProfileName.setText(name);


          /*  if(email.contains("anonymouscollegedekho"))
                mProfileEmail.setText(MainActivity.user.getPrimaryEmail());
            else
                mProfileEmail.setText(email);*/

            if (phone == null || phone.isEmpty()) {
                String mPhone = MainActivity.user.getPrimaryPhone();
                if (mPhone != null)
                    mPhone = mPhone.replace(" ", "");
                mProfilePhone.setText(mPhone);
            } else
                mProfilePhone.setText(phone);

            String image = MainActivity.user.getImage();
            if (image != null && !image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<Exam>examsList=new ArrayList<>();
        for (int i=0;i<10;i++){
            Exam exam=new Exam();
            exam.setExam_name("Test "+i);
            examsList.add(exam);
        }

//            if(MainActivity.user.getPsychometric_given()==0 && MainActivity.user.getIs_preparing()=="0" ) {
//                userExamsLayout.setVisibility(View.VISIBLE);
//                userStreamsLayout.setVisibility(View.GONE);
//
//                if (MainActivity.user.getUser_exams() != null && !MainActivity.user.getUser_exams().isEmpty()) {
//                    addExams(getActivity(), examsLayout, new ArrayList<>(MainActivity.user.getUser_exams()));
//                }
//
//            }else{
//                userStreamsLayout.setVisibility(View.VISIBLE);
//                userExamsLayout.setVisibility(View.GONE);
////                (view.findViewById(R.id.user_education_layout)).setVisibility(View.VISIBLE);
//                addStreams(getActivity(),streamsLayout,MainActivity.user.getStream_name());
//            }
//            addEducation(getActivity(),educationLayout,MainActivity.user.getUser_education());


    }

    @Override
    public void onPause() {
        super.onPause();
        try {
            MainActivity activity = (MainActivity) getActivity();
            activity.showOverflowMenu(true);

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            MainActivity activity = (MainActivity) getActivity();
            activity.init();
            activity.showOverflowMenu(false);
            if(MainActivity.user.getPsychometric_given()==0 && MainActivity.user.getIs_preparing().equals("1")) {
                userExamsLayout.setVisibility(View.VISIBLE);
                userStreamsLayout.setVisibility(View.GONE);

                if (MainActivity.user.getUser_exams() != null && !MainActivity.user.getUser_exams().isEmpty()) {
                    addExams(getActivity(), examsLayout, new ArrayList<>(MainActivity.user.getUser_exams()));
                }

            }else if(MainActivity.user.getPsychometric_given()==1){
                btnEditStreams.setVisibility(View.GONE);
                userExamsLayout.setVisibility(View.GONE);
                addRecommended(getActivity(),recommendedLayout,MainActivity.user.getCollegedekho_recommended_streams());
                addStreams(getActivity(),streamsLayout,MainActivity.user.getStream_name());
            }else if(MainActivity.user.getPsychometric_given()==0){
                btnEditStreams.setVisibility(View.GONE);
                userExamsLayout.setVisibility(View.GONE);
                addRecommended(getActivity(),recommendedLayout,MainActivity.user.getCollegedekho_recommended_streams());
                addStreams(getActivity(),streamsLayout,MainActivity.user.getStream_name());
            }else {
                userStreamsLayout.setVisibility(View.VISIBLE);
                userExamsLayout.setVisibility(View.GONE);
//                (view.findViewById(R.id.user_education_layout)).setVisibility(View.VISIBLE);
                addStreams(getActivity(),streamsLayout,MainActivity.user.getStream_name());
            }
            addEducation(getActivity(),educationLayout,MainActivity.user.getUser_education());

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.btn_edit_education:
                    onEditEducation();
                break;
            case R.id.btn_edit_exams:
                    onEditExams();
                break;
            case R.id.btn_edit_streams:
                    onEditStreams();
                break;
            case R.id.btn_take_psychometric:
                onTakePsychometricTest();
                break;
            case R.id.profile_save_button:
                if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
                    Toast.makeText(getActivity(), "Internet connection not found.", Toast.LENGTH_LONG).show();
                    return;
                }
                mUpdateProfile();
                break;
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (onProfileUpdateListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement onProfileUpdateListener");
        }
    }

    private void mUpdateProfile() {
        View v = getView();
        if (v == null) return;

        // String email    = ((EditText) v.findViewById(R.id.profile_edit_email)).getText().toString();
        String name = ((EditText) v.findViewById(R.id.profile_edit_name)).getText().toString();
        String phone = ((EditText) v.findViewById(R.id.profile_edit_phone)).getText().toString();
        if (name == null || name.isEmpty()) {
            Utils.DisplayToast(getActivity(), Constants.NAME_EMPTY);
            return;
        } else if (!Utils.isValidName(name)) {
            Utils.DisplayToast(getActivity(), Constants.NAME_INVALID);
            return;
        } else if (phone == null || phone.isEmpty()) {
            Utils.DisplayToast(getActivity(), Constants.PHONE_EMPTY);
            return;
        } else if (phone.length() <= 9 || !Utils.isValidPhone(phone)) {
            Utils.DisplayToast(getActivity(), Constants.PHONE_INVALID);
            return;
        }
           /* else if (email == null || email.isEmpty()){
                Utils.DisplayToast(getActivity(), Constants.EMAIL_EMPTY);
                return;
            }
            else if(!Utils.isValidEmail(email)){
                Utils.DisplayToast(getActivity(), Constants.EMAIL_INVALID);
                return;
            }*/

        HashMap<String, String> hashMap = new HashMap<>();
        // hashMap.put(Constants.USER_EMAIL, email);
        hashMap.put(Constants.USER_NAME, name);
        hashMap.put(Constants.USER_PHONE, phone);
        if (mListener != null) {
            this.mListener.onProfileUpdated(hashMap);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface onProfileUpdateListener {
        void onProfileUpdated(HashMap<String, String> hashMap);
        void onEditUserEducation();
        void onEditUserStreams();
        void onEditUserExams();
        void onTakePsychometric();
    }

    private void addExams(Context context, LinearLayout parentLayout, ArrayList<ExamDetail> examsList) {
        if(context==null||parentLayout==null||examsList==null||examsList.isEmpty()){
            userExamsLayout.setVisibility(View.GONE);
            return;
        }
        userExamsLayout.setVisibility(View.VISIBLE);
        parentLayout.removeAllViews();

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        for (ExamDetail exam : examsList) {
            CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.exams_card_layout, null);
            cardView.setLayoutParams(layoutParams);
            ((TextView) cardView.findViewById(R.id.exam_name)).setText(exam.getExam_name());
            ((TextView) cardView.findViewById(R.id.exam_year)).setText(exam.getYear());
            parentLayout.addView(cardView);
        }
    }

    private void addEducation(Context context, LinearLayout parentLayout, UserEducation userEducation) {
        if(context==null||parentLayout==null||userEducation==null){
            userEducationLayout.setVisibility(View.GONE);
            return;
        }
        userEducationLayout.setVisibility(View.VISIBLE);
        parentLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
            CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.exams_card_layout, null);
            cardView.setLayoutParams(layoutParams);
            ((TextView) cardView.findViewById(R.id.exam_name)).setText("Level");
            ((TextView) cardView.findViewById(R.id.exam_year)).setText(userEducation.getSublevel());
            parentLayout.addView(cardView);

        CardView cardView1 = (CardView) LayoutInflater.from(context).inflate(R.layout.exams_card_layout, null);
        cardView1.setLayoutParams(layoutParams);
        ((TextView) cardView1.findViewById(R.id.exam_name)).setText("Stream");
        ((TextView) cardView1.findViewById(R.id.exam_year)).setText(userEducation.getStream());
        parentLayout.addView(cardView1);

        CardView cardView2 = (CardView) LayoutInflater.from(context).inflate(R.layout.exams_card_layout, null);
        cardView2.setLayoutParams(layoutParams);
        try {
            float mMarks=Float.valueOf(userEducation.getMarks());
            int marks=(int) (mMarks-5);
            ((TextView) cardView2.findViewById(R.id.exam_year)).setText(""+((marks))+" - "+(marks+10)+" %");
            ((TextView) cardView2.findViewById(R.id.exam_name)).setText("Marks");
            parentLayout.addView(cardView2);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void addStreams(Context context, LinearLayout parentLayout, String streamName) {
        if(context==null||parentLayout==null||streamName==null||streamName.isEmpty()){
            userStreamsLayout.setVisibility(View.GONE);
            return;
        }
        userStreamsLayout.setVisibility(View.VISIBLE);
        parentLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
            CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.exams_card_layout, null);
            cardView.setLayoutParams(layoutParams);
            ((TextView) cardView.findViewById(R.id.exam_name)).setText(streamName);
            ((TextView) cardView.findViewById(R.id.exam_year)).setVisibility(View.GONE);
            parentLayout.addView(cardView);
    }

    private void addRecommended(Context context, LinearLayout parentLayout, ArrayList<String> streams) {
        if(context==null||parentLayout==null||streams==null||streams.isEmpty()){
//            userRecommendedLayout.setVisibility(View.GONE);
            psychometricTestLayout.setVisibility(View.VISIBLE);
            return;
        }
        userRecommendedLayout.setVisibility(View.VISIBLE);
        parentLayout.removeAllViews();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(10, 5, 10, 5);
        for (String streamName:streams) {
            CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.exams_card_layout, null);
            cardView.setLayoutParams(layoutParams);
            ((TextView) cardView.findViewById(R.id.exam_name)).setText(streamName);
            ((TextView) cardView.findViewById(R.id.exam_year)).setVisibility(View.GONE);
            parentLayout.addView(cardView);
        }
    }

    private void onEditEducation() {
        mListener.onEditUserEducation();
    }

    private void onEditExams() {
        mListener.onEditUserExams();
    }

    private void onEditStreams() {
        mListener.onEditUserStreams();
    }

    private void onTakePsychometricTest() {
        mListener.onTakePsychometric();
    }
}
