package com.collegedekho.app.fragment;

import android.annotation.SuppressLint;
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

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.ExamDetail;
import com.collegedekho.app.entities.UserEducation;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.facebook.login.LoginManager;
import com.truecaller.android.sdk.TrueButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sureshsaini on 6/1/16.
 */
public class ProfileEditFragmentNew extends BaseFragment {

    private ViewPager profilePager;
    private int currentPosition=0;
    private ProfilePagerAdapter profileAdapter;

    public ProfileEditFragmentNew() {
        // required empty constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static ProfileEditFragmentNew newInstance() {
        ProfileEditFragmentNew fragment = new ProfileEditFragmentNew();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_profile_edit_new, container, false);
        profilePager=(ViewPager)rootView.findViewById(R.id.profile_view_pager);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
            ArrayList<String> titleList = new ArrayList<>();
            titleList.add("Info");
            titleList.add("Education");
            titleList.add("Preferred");
            titleList.add("Exams");
            titleList.add("Others");
            profileAdapter=new ProfilePagerAdapter(getChildFragmentManager(),titleList.size(),titleList);
            profilePager.setAdapter(profileAdapter);
            profilePager.setCurrentItem(currentPosition);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    public interface onProfileUpdateListener {
        void onProfileUpdated(HashMap<String, String> hashMap);
        void onEditUserEducation();
        void onEditUserStreams();
        void onEditUserExams();
        void onTakePsychometric();
        void displayMessage(int messageId);
    }


    class ProfilePagerAdapter extends FragmentStatePagerAdapter{
        private int NUM_PAGES=1;
        ArrayList<String> titleList;
        public ProfileChildFragment profileChildFragment;

        public ProfilePagerAdapter(FragmentManager fm, int pageCount, ArrayList<String> titleList) {
            super(fm);
            this.titleList=titleList;
            NUM_PAGES=pageCount;
        }

        @Override
        public Fragment getItem(int position) {
            profileChildFragment= ProfileChildFragment.newInstance();
            Bundle b=new Bundle();
            b.putInt("position", position);
            b.putString("title",titleList.get(position));
            profileChildFragment.setArguments(b);
            return  profileChildFragment;
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
        private int position;
        private CardView userInfoLayout, userEducationLayout, userPreferredLayout, userExamsLayout, userOtherInfoLayout;

        private onProfileUpdateListener mListener;

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
            if(getArguments()!=null) {
                position = getArguments().getInt("position");
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View rootView=inflater.inflate(R.layout.layout_profile_edit_new,container,false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            userInfoLayout = (CardView) view.findViewById(R.id.profile_edit_info_layout);
            userEducationLayout = (CardView) view.findViewById(R.id.profile_edit_education_layout);
            userPreferredLayout = (CardView) view.findViewById(R.id.profile_edit_preferences_layout);
            //userExamsLayout = (CardView) view.findViewById(R.id.profile_edit_exams_layout);
            userOtherInfoLayout = (CardView) view.findViewById(R.id.profile_edit_other_info_layout);
        }

       /* @Override
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
*/
        @Override
        public void onResume() {
            super.onResume();
            if(getArguments()!=null) {
                position = getArguments().getInt("position");
                String title = getArguments().getString("title");
                if(title!=null){
                    switch (title) {
                        case "Info":
                            userInfoLayout.setVisibility(View.VISIBLE);
                            userEducationLayout.setVisibility(View.GONE);
                            userPreferredLayout.setVisibility(View.GONE);
                            //userExamsLayout.setVisibility(View.GONE);
                            userOtherInfoLayout.setVisibility(View.GONE);
                            break;
                        case "Education":
                            userInfoLayout.setVisibility(View.GONE);
                            userEducationLayout.setVisibility(View.VISIBLE);
                            userPreferredLayout.setVisibility(View.GONE);
                            //userExamsLayout.setVisibility(View.GONE);
                            userOtherInfoLayout.setVisibility(View.GONE);
                            break;
                        case "Preferred":
                            userInfoLayout.setVisibility(View.GONE);
                            userEducationLayout.setVisibility(View.GONE);
                            userPreferredLayout.setVisibility(View.VISIBLE);
                            //userExamsLayout.setVisibility(View.GONE);
                            userOtherInfoLayout.setVisibility(View.GONE);
                            break;
                        case "Exams":
                            userInfoLayout.setVisibility(View.GONE);
                            userEducationLayout.setVisibility(View.GONE);
                            userPreferredLayout.setVisibility(View.GONE);
                            //userExamsLayout.setVisibility(View.VISIBLE);
                            userOtherInfoLayout.setVisibility(View.GONE);
                            break;
                        case "Others":
                            userInfoLayout.setVisibility(View.GONE);
                            userEducationLayout.setVisibility(View.GONE);
                            userPreferredLayout.setVisibility(View.GONE);
                            //userExamsLayout.setVisibility(View.GONE);
                            userOtherInfoLayout.setVisibility(View.VISIBLE);
                            break;
                    }
                }
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
