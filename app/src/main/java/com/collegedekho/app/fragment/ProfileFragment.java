package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.Stream;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.resource.Constants;

import java.util.HashMap;


public class ProfileFragment extends BaseFragment implements View.OnClickListener {

    private static final String ARG_NAME        = "name";
    private static final String ARG_EMAIL       = "email";
    private static final String ARG_STREAM_URI  = "stream_uri";
    private static final String ARG_LEVEL_URI   = "level_uri";
    private static final String ARG_STREAM_NAME = "stream";
    private static final String ARG_LEVEL_NAME  = "level";
    private static final String ARG_PHONE  = "phone";

    private String mName;
    private String mEmail;
    private String mStreamURI;
    private String mLevelURI;
    private String mStreamName;
    private String mLevelName;
    private String mPhone;

    private EditText mNameET;
    private EditText mEmailET;
    private EditText mPhoneET;
    private EditText mStreamTV;
    private EditText mLevelTV;
    private onProfileUpdateListener mListener;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, user.getName());
        args.putString(ARG_EMAIL, user.getEmail());
        args.putString(ARG_STREAM_URI, user.getStream());
        args.putString(ARG_LEVEL_URI, user.getLevel());
        args.putString(ARG_STREAM_NAME, user.getStream_name());
        args.putString(ARG_LEVEL_NAME, user.getLevel_name());
        args.putString(ARG_PHONE, user.getPhone());
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mName  = getArguments().getString(ARG_NAME);
            this.mEmail = getArguments().getString(ARG_EMAIL);
            this.mStreamURI     = getArguments().getString(ARG_STREAM_URI);
            this.mLevelURI      = getArguments().getString(ARG_LEVEL_URI);
            this.mStreamName    = getArguments().getString(ARG_STREAM_NAME);
            this.mLevelName     = getArguments().getString(ARG_LEVEL_NAME);
            this.mPhone     = getArguments().getString(ARG_PHONE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.my_profile2, container, false);
        this.mNameET = (EditText) rootView.findViewById(R.id.profile_name);
        this.mEmailET = (EditText) rootView.findViewById(R.id.profile_email);
        this.mPhoneET =  (EditText) rootView.findViewById(R.id.profile_contact);
        this.mStreamTV = (EditText) rootView.findViewById(R.id.profile_stream);
        this.mLevelTV = (EditText) rootView.findViewById(R.id.profile_level);
        rootView.findViewById(R.id.stream_edit).setOnClickListener(this);
        rootView.findViewById(R.id.level_edit).setOnClickListener(this);
        mSetEnterKeyListener(mPhoneET);
        if(mName.equalsIgnoreCase("Anonymous User"))
        {
            this.mNameET.setText("");
        }else {
            this.mNameET.setText(mName);
        }
        this.mEmailET.setText(mEmail);
        this.mStreamTV.setText(mStreamName );
        this.mLevelTV.setText(mLevelName );
        this.mPhoneET.setText(mPhone);

        //this.mStreamTV.setOnClickListener(this);
        //this.mLevelTV.setOnClickListener(this);
        ((TextView)rootView.findViewById(R.id.profile_update)).setOnClickListener(this);
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void mProfileUpdate() {
        HashMap<String, String> hashMap = new HashMap<>();

        hashMap.put(Constants.USER_NAME, mNameET.getText().toString());
        hashMap.put(Constants.USER_EMAIL,mEmailET.getText().toString());
        hashMap.put(Constants.USER_PHONE, mPhoneET.getText().toString());
        hashMap.put(Constants.USER_STREAM, mStreamURI);
        hashMap.put(Constants.USER_LEVEL,mLevelURI);
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
                                mLevelURI = Constants.BASE_URL + "level/" + (which + 1) + "/";
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
   /* private static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    private static boolean isValidPhone(CharSequence target) {
        return target != null && Patterns.PHONE.matcher(target).matches();
    }*/
}
