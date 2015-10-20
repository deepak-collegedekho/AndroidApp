package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_NAME        = "name";
    private static final String ARG_EMAIL       = "email";
    private static final String ARG_STREAM_URI  = "stream_uri";
    private static final String ARG_LEVEL_ID = "level_uri";
    private static final String ARG_STREAM_NAME = "stream";
    private static final String ARG_LEVEL_NAME  = "level";
    private static final String ARG_PHONE  = "phone";
    private static final int SIGNUP = 1;
    private static final int SIGNIN = 0;

    private String mName;
    private String mStreamURI;
    private String mLevelID;
    private String mStreamName;
    private String mLevelName;
    private String mPhone;

    private EditText mNameET;
    private EditText mPhoneET;
    private EditText mStreamTV;
    private EditText mLevelTV;
    private onProfileUpdateListener mListener;
    private SignInFragment signInFragment;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, user.getName());
        args.putString(ARG_EMAIL, user.getEmail());
        args.putString(ARG_STREAM_URI,  Constants.BASE_URL + "streams/" + user.getStream() + "/");
        args.putString(ARG_LEVEL_ID, user.getLevel());
        args.putString(ARG_STREAM_NAME, user.getStream_name());
        args.putString(ARG_LEVEL_NAME, user.getLevel_name());
        args.putString(ARG_PHONE, user.getPhone_no());
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mName  = getArguments().getString(ARG_NAME);
            this.mStreamURI = getArguments().getString(ARG_STREAM_URI);
            this.mLevelID = getArguments().getString(ARG_LEVEL_ID);
            this.mStreamName    = getArguments().getString(ARG_STREAM_NAME);
            this.mLevelName     = getArguments().getString(ARG_LEVEL_NAME);
            this.mPhone     = getArguments().getString(ARG_PHONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        this.mNameET = (EditText) rootView.findViewById(R.id.profile_name);
        this.mPhoneET =  (EditText) rootView.findViewById(R.id.profile_contact);
        this.mStreamTV = (EditText) rootView.findViewById(R.id.profile_stream);
        this.mLevelTV = (EditText) rootView.findViewById(R.id.profile_level);
        mSetEnterKeyListener(mPhoneET);

        this.mStreamTV.setText(mStreamName);
        this.mLevelTV.setText(mLevelName);

        if(MainActivity.user != null && MainActivity.user.getIs_anony())
        {
            LoginPagerAdapter adapter = new LoginPagerAdapter(getChildFragmentManager());
            ViewPager mPager = (ViewPager) rootView.findViewById(R.id.profile_login_pager);
            mPager.setAdapter(adapter);
            mPager.setOffscreenPageLimit(2);

            TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.profile_login_tabs_layout);
            tabLayout.setTabTextColors(getResources().getColor(R.color.white), getResources().getColor(R.color.text_subhead_blue));
            tabLayout.setupWithViewPager(mPager);
            mPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

            rootView.findViewById(R.id.profile_login_pager).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.profile_login_tabs_layout).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.profile_name).setVisibility(View.GONE);
            rootView.findViewById(R.id.profile_contact).setVisibility(View.GONE);
        }
        else
        {
            rootView.findViewById(R.id.profile_login_pager).setVisibility(View.GONE);
            rootView.findViewById(R.id.profile_login_tabs_layout).setVisibility(View.GONE);
            rootView.findViewById(R.id.profile_name).setVisibility(View.VISIBLE);
            rootView.findViewById(R.id.profile_contact).setVisibility(View.VISIBLE);
        }


        rootView.findViewById(R.id.stream_edit).setOnClickListener(this);
        rootView.findViewById(R.id.level_edit).setOnClickListener(this);
        rootView.findViewById(R.id.profile_update).setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.mListener = (onProfileUpdateListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement onProfileUpdateListener");
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(signInFragment != null)
        {
            signInFragment.onActivityResult(requestCode,resultCode,data);
        }

    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void mProfileUpdate() {

        String name = mNameET.getText().toString();
        String phone = mPhoneET.getText().toString();
        View view = getView();
        if(MainActivity.user != null && !MainActivity.user.is_anony()) {
            if (name == null || name.isEmpty()) {
                Utils.DisplayToast(getActivity(), Constants.NAME_EMPTY);
                return;
            } else if (!isValidName(name)) {
                Utils.DisplayToast(getActivity(), Constants.NAME_INVALID);
                return;
            } else if (phone == null || phone.isEmpty()) {
                Utils.DisplayToast(getActivity(), Constants.PHONE_EMPTY);
                return;
            } else if (phone.length() <= 9 || !isValidPhone(phone)) {
                Utils.DisplayToast(getActivity(), Constants.PHONE_INVALID);
                return;
            }
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constants.USER_NAME, name);
        hashMap.put(Constants.USER_PHONE, phone);
        hashMap.put(Constants.USER_STREAM, mStreamURI);
        hashMap.put(Constants.USER_LEVEL, Constants.BASE_URL + "level/" + mLevelID + "/");
        hashMap.put(Constants.USER_STREAM_NAME, mStreamName);
        hashMap.put(Constants.USER_LEVEL_NAME,mLevelName);

        if(mListener!=null) {
            this.mListener.onProfileUpdated(hashMap);
        }
    }

    private void mStreamClicked() {
        if(mListener!=null) {
            this.mListener.onStreamClicked();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.stream_edit:
                mStreamClicked();
            break;
            case R.id.level_edit:
                new AlertDialog.Builder(getActivity())
                        .setTitle("Please select a level")
                        .setSingleChoiceItems(InstituteCourse.CourseLevel.getValues(), -1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mLevelID = ""+(which + 1);
                                mLevelName = InstituteCourse.CourseLevel.getName(which);
                                mLevelTV.setText(mLevelName);
                                dialog.dismiss();
                            }
                        })
                        .show();
            break;
            case R.id.profile_update:
                mProfileUpdate();
            break;

        }
    }

    public void updateStream(String streamUri , String streamName )
    {
        this.mStreamURI = streamUri;
        this.mStreamName = streamName;
        this.mStreamTV.setText(streamName);

    }

    public interface  onProfileUpdateListener
    {
       void  onProfileUpdated(  HashMap<String, String> hashMap);
       void  onStreamClicked();
    }

    @Override
    public void onResume() {
        super.onResume();

        MainActivity mMainActivity = (MainActivity) this.getActivity();

        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
        this.mStreamTV.setText(this.mStreamName);
        if(this.mName.equalsIgnoreCase("Anonymous User"))
        {
            this.mNameET.setText("");
        }else {
            this.mNameET.setText(this.mName);
        }
        if(this.mPhone != null)
        this.mPhoneET.setText(this.mPhone);
    }
    private void mSetEnterKeyListener(EditText editText)
    {
        editText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    mProfileUpdate();
                    return true;
                }
                return false;
            }
        });
    }

    private static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private static boolean isValidPhone(CharSequence target) {
        return target != null && Patterns.PHONE.matcher(target).matches();
    }
    private static boolean isValidName(CharSequence target) {
        Pattern ps = Pattern.compile("^[a-zA-Z ]+$");
        Matcher ms = ps.matcher(target);
        return ms.matches();
    }
    public void updateUI(User user)
    {
        View view = getView();
        if(view != null)
        {
            view.findViewById(R.id.profile_login_pager).setVisibility(View.GONE);
            view.findViewById(R.id.profile_login_tabs_layout).setVisibility(View.GONE);
            view.findViewById(R.id.profile_name).setVisibility(View.VISIBLE);
            view.findViewById(R.id.profile_contact).setVisibility(View.VISIBLE);
            if(user != null)
            {
                this.mName = user.getName();
                this.mPhone = user.getPhone_no();
                this.mStreamTV.setText(this.mStreamName);
                if(this.mName.equalsIgnoreCase("Anonymous User"))
                {
                    this.mNameET.setText("");
                }else {
                    this.mNameET.setText(this.mName);
                }
                if(this.mPhone != null)
                    this.mPhoneET.setText(this.mPhone);

            }
        }
    }

    class LoginPagerAdapter extends FragmentPagerAdapter
    {

        public LoginPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch (i)
            {
                case SIGNIN:
                    return signInFragment = SignInFragment.newInstance("");
                case SIGNUP:
                    return SignUpFragment.newInstance("");
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position)
            {
                case SIGNIN:
                    return "SIGN IN";
                case SIGNUP:
                    return "SIGN UP";
            }
            return super.getPageTitle(position);
        }
    }
}
