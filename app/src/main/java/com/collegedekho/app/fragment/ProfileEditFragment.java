package com.collegedekho.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
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

    ViewPager profilePager;
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

        CircularImageView mProfileImage = (CircularImageView) rootView.findViewById(R.id.profile_image);
        mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
        mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

        profilePager=(ViewPager)rootView.findViewById(R.id.profile_view_pager);

        if (MainActivity.user != null) {
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

            ArrayList<String>titleList=new ArrayList<>();
            titleList.add("About");
            titleList.add("Education");
            if (MainActivity.user.getIs_preparing().equals("1")) {
                titleList.add("Exams");
            }
            titleList.add("Streams");
            ProfilePagerAdapter adapter=new ProfilePagerAdapter(getChildFragmentManager(),titleList.size(),titleList);
            profilePager.setAdapter(adapter);

        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    public interface onProfileUpdateListener {
        void onProfileUpdated(HashMap<String, String> hashMap);
        void onEditUserEducation();
        void onEditUserStreams();
        void onEditUserExams();
        void onTakePsychometric();
    }


    class ProfilePagerAdapter extends FragmentStatePagerAdapter{
        private int NUM_PAGES=1;
        ArrayList<String>titleList;
        public ProfilePagerAdapter(FragmentManager fm,int pageCount,ArrayList<String> titleList) {
            super(fm);
            this.titleList=titleList;
            NUM_PAGES=pageCount;
        }

        @Override
        public Fragment getItem(int position) {
           Fragment fragment= ProfileChildFragment.newInstance();
            Bundle b=new Bundle();
            b.putInt("position", position);
            b.putString("title",titleList.get(position));
            fragment.setArguments(b);
            return  fragment;
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

    public static class ProfileChildFragment extends BaseFragment{
        private int position;
        private LinearLayout examsLayout, streamsLayout,recommendedLayout, educationLayout,psychometricTestLayout;
        CardView userExamsLayout, userStreamsLayout, userEducationLayout,userRecommendedLayout,userProfileInputLayout;
        private ImageView btnEditEducation,btnEditStreams,btnEditExams;
        private Button btnTakePsycho;
        private TextView saveButton;
        private onProfileUpdateListener mListener;
        public ProfileChildFragment() {

        }

        public static ProfileChildFragment newInstance() {
            ProfileChildFragment fragment = new ProfileChildFragment();
            return fragment;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(getArguments()!=null) {
                position = getArguments().getInt("position");
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView=inflater.inflate(R.layout.edit_profile_fragment_layout,container,false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            examsLayout = (LinearLayout) view.findViewById(R.id.exams_list_layout);
            educationLayout = (LinearLayout) view.findViewById(R.id.education_list_layout);
            streamsLayout = (LinearLayout) view.findViewById(R.id.streams_list_layout);
            recommendedLayout = (LinearLayout) view.findViewById(R.id.recommended_list_layout);

            userProfileInputLayout=(CardView) view.findViewById(R.id.user_profile_edit_layout);
            userExamsLayout = (CardView) view.findViewById(R.id.user_exams_layout);
            userEducationLayout = (CardView) view.findViewById(R.id.user_education_layout);
            userStreamsLayout = (CardView) view.findViewById(R.id.user_streams_layout);
            userRecommendedLayout = (CardView) view.findViewById(R.id.user_recommended_layout);

            psychometricTestLayout=(LinearLayout)view.findViewById(R.id.psychometric_test_layout);
            btnEditEducation=(ImageView)view.findViewById(R.id.btn_edit_education);
            btnEditStreams=(ImageView)view.findViewById(R.id.btn_edit_streams);
            btnEditExams=(ImageView)view.findViewById(R.id.btn_edit_exams);

            btnTakePsycho=(Button)view.findViewById(R.id.btn_take_psychometric);

            btnEditExams.setOnClickListener(this);
            btnEditEducation.setOnClickListener(this);
            btnTakePsycho.setOnClickListener(this);

            saveButton=(TextView)view.findViewById(R.id.profile_save_button);
            saveButton.setOnClickListener(this);
            EditText mProfileName = (EditText) view.findViewById(R.id.profile_edit_name);
            EditText mProfilePhone = (EditText) view.findViewById(R.id.profile_edit_phone);
            //EditText mProfileEmail  = (EditText) rootView.findViewById(R.id.profile_edit_email);
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

        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }

        @Override
        public void onResume() {
            super.onResume();
            if(getArguments()!=null){
                position=getArguments().getInt("position");
                String title=getArguments().getString("title");
                switch (title) {
                    case "About":
                        userProfileInputLayout.setVisibility(View.VISIBLE);
                        saveButton.setVisibility(View.VISIBLE);
                        userEducationLayout.setVisibility(View.GONE);
                        userExamsLayout.setVisibility(View.GONE);
                        userStreamsLayout.setVisibility(View.GONE);
                        userRecommendedLayout.setVisibility(View.GONE);
                        break;
                    case "Education":
                        userProfileInputLayout.setVisibility(View.GONE);
                        saveButton.setVisibility(View.GONE);
                        userEducationLayout.setVisibility(View.VISIBLE);
                        userExamsLayout.setVisibility(View.GONE);
                        userStreamsLayout.setVisibility(View.GONE);
                        userRecommendedLayout.setVisibility(View.GONE);
                        break;
                    case "Exams":
                        userProfileInputLayout.setVisibility(View.GONE);
                        saveButton.setVisibility(View.GONE);
                        userEducationLayout.setVisibility(View.GONE);
                        userExamsLayout.setVisibility(View.VISIBLE);
                        userStreamsLayout.setVisibility(View.GONE);
                        userRecommendedLayout.setVisibility(View.GONE);
                        break;
                    case "Streams":
                        userProfileInputLayout.setVisibility(View.GONE);
                        saveButton.setVisibility(View.GONE);
                        userEducationLayout.setVisibility(View.GONE);
                        userExamsLayout.setVisibility(View.GONE);
                        userStreamsLayout.setVisibility(View.VISIBLE);
                        userRecommendedLayout.setVisibility(View.VISIBLE);
                        break;
                }
            }
            if(MainActivity.user.getIs_preparing().equals("1")) {
//                btnEditStreams.setVisibility(View.VISIBLE);
                if (MainActivity.user.getUser_exams() != null && !MainActivity.user.getUser_exams().isEmpty()) {
                    addExams(getActivity(), examsLayout, new ArrayList<>(MainActivity.user.getUser_exams()));
                }

            }else {
                userExamsLayout.setVisibility(View.GONE);
            }
            addRecommended(getActivity(),recommendedLayout,MainActivity.user.getCollegedekho_recommended_streams());
            addEducation(getActivity(),educationLayout,MainActivity.user.getUser_education());
//            btnEditStreams.setVisibility(View.GONE);
            String streamName=MainActivity.user.getStream_name();
//            if(streamName==null||streamName.isEmpty()){
//                streamName=MainActivity.user.getStream();
//            }
            addStreams(getActivity(), streamsLayout, streamName);

            if(MainActivity.user.getIs_preparing().equals("1")) {
//                btnEditStreams.setVisibility(View.VISIBLE);
                if (MainActivity.user.getUser_exams() != null && !MainActivity.user.getUser_exams().isEmpty()) {
                    addExams(getActivity(), examsLayout, new ArrayList<>(MainActivity.user.getUser_exams()));
                }

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

        private void addExams(Context context, LinearLayout parentLayout, ArrayList<ExamDetail> examsList) {
            if(context==null||parentLayout==null||examsList==null||examsList.isEmpty()){
                userExamsLayout.setVisibility(View.GONE);
                return;
            }
//            userExamsLayout.setVisibility(View.VISIBLE);
            parentLayout.removeAllViews();

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(10, 5, 10, 5);
            for (ExamDetail exam : examsList) {
                CardView cardView = (CardView) LayoutInflater.from(context).inflate(R.layout.exams_card_layout, null);
                cardView.setLayoutParams(layoutParams);
                ((TextView) cardView.findViewById(R.id.exam_name)).setText(exam.getExam_short_name());
                ((TextView) cardView.findViewById(R.id.exam_year)).setText(exam.getYear());
                parentLayout.addView(cardView);
            }
        }

        private void addEducation(Context context, LinearLayout parentLayout, UserEducation userEducation) {
            if(context==null||parentLayout==null||userEducation==null){
                userEducationLayout.setVisibility(View.GONE);
                return;
            }
//            userEducationLayout.setVisibility(View.VISIBLE);
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
//            userStreamsLayout.setVisibility(View.VISIBLE);
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
//            userRecommendedLayout.setVisibility(View.VISIBLE);
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

        private void mUpdateProfile() {
            View v = getView();
            if (v == null) return;

            // String email    = ((EditText) v.findViewById(R.id.profile_edit_email)).getText().toString();
            String name = ((EditText) v.findViewById(R.id.profile_edit_name)).getText().toString();
            String phone = ((EditText) v.findViewById(R.id.profile_edit_phone)).getText().toString();
            if (name == null || name.trim().isEmpty()) {
                Utils.DisplayToast(getActivity(), Constants.NAME_EMPTY);
                return;
            } else if (!Utils.isValidName(name)) {
                Utils.DisplayToast(getActivity(), Constants.NAME_INVALID);
                return;
            } else if (phone == null || phone.trim().isEmpty()) {
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
            hashMap.put(Constants.USER_NAME, name.trim());
            hashMap.put(Constants.USER_PHONE, phone.trim());
            if (mListener != null) {
                this.mListener.onProfileUpdated(hashMap);
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
}
