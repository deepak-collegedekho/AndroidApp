package com.collegedekho.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.InstituteCourse;
import com.collegedekho.app.entities.User;
import com.collegedekho.app.resource.Constants;

import java.util.HashMap;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_NAME = "name";
    private static final String ARG_EMAIL = "email";
    private static final String ARG_STREAM = "stream";
    private static final String ARG_LEVEL = "level";

    private String mName;
    private String mEmail;
    private String mStreamURI;
    private String mLevelURI;

    private EditText mNameET;
    private EditText mEmailET;
    private EditText mPhoneET;
    private TextView mStreamTV;
    private TextView mLevelTV;
    private onProfileUpdateListener mListener;

    public ProfileFragment() {

    }

    public static ProfileFragment newInstance(User user) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, user.getName());
        args.putString(ARG_EMAIL, user.getEmail());
        args.putString(ARG_STREAM, user.getStream());
        args.putString(ARG_LEVEL, user.getLevel());
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.mName = getArguments().getString(ARG_NAME);
            this.mEmail = getArguments().getString(ARG_EMAIL);
            this.mStreamURI = getArguments().getString(ARG_STREAM);
            this.mLevelURI = getArguments().getString(ARG_LEVEL);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        this.mNameET = (EditText) rootView.findViewById(R.id.profile_name);
        this.mEmailET = (EditText) rootView.findViewById(R.id.profile_email);
        this.mPhoneET =  (EditText) rootView.findViewById(R.id.profile_contact);
        this.mStreamTV = (TextView) rootView.findViewById(R.id.profile_streams);
        this.mLevelTV = (TextView) rootView.findViewById(R.id.profile_level);

        this.mNameET.setText(mName);
        this.mEmailET.setText(mEmail);
        this.mStreamTV.setText(mStreamURI);
        this.mLevelTV.setText(mLevelURI);
        TelephonyManager tMgr = (TelephonyManager)getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        if(mPhoneNumber != null) {
         this.mPhoneET.setText(mPhoneNumber);
        }
        this.mStreamTV.setOnClickListener(this);
        this.mLevelTV.setOnClickListener(this);
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

    private void mProfileUpdate(HashMap<String, String> hashMap) {
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

        if(view.getId() == R.id.profile_streams) {
           mStreamClicked();
        }
        else if(view.getId() == R.id.profile_level) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("Please select a level")
                    .setSingleChoiceItems(InstituteCourse.CourseLevel.getValues(), -1, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mLevelURI = Constants.BASE_URL + "level/" + (which + 1) + "/";
                            mLevelTV.setText(mLevelURI);
                            dialog.dismiss();
                        }
                    })
                    .setCancelable(false)
                    .show();
        }
        else if(view.getId() == R.id.profile_update)
        {
            HashMap<String, String> hashMap = new HashMap<>();

            hashMap.put("name", mNameET.getText().toString());
            hashMap.put("email",mEmailET.getText().toString());
            hashMap.put("phone", mPhoneET.getText().toString());
            hashMap.put("stream", mStreamURI);
            hashMap.put("level",mLevelURI);
            mProfileUpdate(hashMap);
        }
    }

    public void updateStream(String streamUri)
    {
        this.mStreamURI = streamUri;
        mStreamTV.setText(mStreamURI);
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
    }

}
