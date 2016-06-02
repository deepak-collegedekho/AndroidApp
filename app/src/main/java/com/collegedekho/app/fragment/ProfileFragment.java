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
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.entities.ProfileExam;
import com.collegedekho.app.resource.MySingleton;
import com.collegedekho.app.utils.Utils;
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
        rootView.findViewById(R.id.profile_show_more_info).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (MainActivity.user != null){
            CircularImageView mProfileImage = (CircularImageView) view.findViewById(R.id.profile_image);
            mProfileImage.setDefaultImageResId(R.drawable.ic_profile_default);
            mProfileImage.setErrorImageResId(R.drawable.ic_profile_default);

            String image = MainActivity.user.getImage();
            if (image != null && !image.isEmpty())
                mProfileImage.setImageUrl(image, MySingleton.getInstance(getActivity()).getImageLoader());
        }
        updateUserProfile(this.mProfile);
    }

    public void updateUserProfile(Profile profile) {
        View view = getView();
        if (profile == null || view == null)
            return;

        String name = profile.getName();
        if(name != null && !name.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_user_name)).setText(name);
        }else{
            ((TextView)view.findViewById(R.id.profile_user_name)).setText("");
        }

        String email = profile.getEmail();
        if (email != null && !email.isEmpty() && !email.contains("@anonymouscollegedekho.com")) {
            ((TextView)view.findViewById(R.id.profile_info_email)).setText(email);
        }else{
            ((TextView)view.findViewById(R.id.profile_info_email)).setText("");
        }

        String phone = profile.getPhone_no();
        if (phone != null && !phone.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_info_phone)).setText("+91-"+phone);
        }else{
            ((TextView)view.findViewById(R.id.profile_info_phone)).setText("");
        }

        String currentDegreeName = profile.getCurrent_degree_name();
        if (currentDegreeName != null && !currentDegreeName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_education_degree)).setText(currentDegreeName);
        }

        int currentPassingYear = profile.getCurrent_passing_year();
         ((TextView)view.findViewById(R.id.profile_education_year)).setText(""+currentPassingYear);


        String streamName = profile.getPreferred_stream_short_name();
        if (streamName != null && !streamName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_preferences_degree)).setText(streamName);
        }

        ArrayList<String> cityNamesList = profile.getPreferred_cities_names();

        if (cityNamesList != null){
            int count = cityNamesList.size();
            String cityNames ="";

        for (int i = 0; i < count; i++) {
                if(i == 0){
                    cityNames = cityNamesList.get(i);
                    continue;
                }
                cityNames += ", "+cityNamesList.get(i);
        }
            ((TextView)view.findViewById(R.id.profile_preferences_location)).setText("City  :  "+cityNames);
        }

       ArrayList<ProfileExam> examsList = profile.getYearly_exams();
        if(examsList != null ){
            int count = examsList.size();
            String examsName ="";
            if(count >6)
                count =6;

            for (int i = 0; i < count; i++) {
                ProfileExam exam = examsList.get(i);
                if(exam == null)continue;
                if(i == 0){
                    examsName = exam.getExam_name();
                    continue;
                }
                examsName += ", "+exam.getExam_name() ;
            }
            ((TextView)view.findViewById(R.id.profile_exams_name)).setText(examsName);
        }

        String specializationName = profile.getPreferred_specialization_name();
        if (specializationName != null && !specializationName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_exams_specialization)).setText(specializationName);
        }


        String fatherName = profile.getFathers_name();
        if (fatherName != null && !fatherName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_father_name)).setText("Father : "+fatherName);
        }

        String motherName = profile.getMothers_name();
        if (motherName != null && !motherName.isEmpty()){
            ((TextView)view.findViewById(R.id.profile_mother_name)).setText("Mother : "+motherName);
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
            case R.id.profile_show_more_info:
              // Drawable drawable = Utils.getDrawable(getActivity(),R.drawable.ic_spinner_arrow);
               // animateArrow(true,drawable);
                break;
            default:
                break;
        }
    }

    private void mOnProfileEdited(){
        if(mListener != null)
            mListener.onUserProfileEdited(mProfile);
    }

    public interface  UserProfileListener{
        void onUserProfileEdited(Profile profile);
    }
}
