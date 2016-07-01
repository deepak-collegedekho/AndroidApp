package com.collegedekho.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.facebook.login.LoginManager;
import com.truecaller.android.sdk.TrueButton;

import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by sureshsaini on 20/11/15.
 */
public class PostAnonymousLoginFragment extends  BaseFragment {

    private OnUserPostAnonymousLoginListener mListener;
    private EditText mPhoneNumberET;
    private EditText mOtpET;
    private static String PARAM1  = "param1";
    private String phone_no;


    public static PostAnonymousLoginFragment newInstance(String phone_no)
    {
        PostAnonymousLoginFragment fragment = new PostAnonymousLoginFragment();
        Bundle args = new Bundle();
        args.putString(PARAM1, phone_no);
        fragment.setArguments(args);
        return  fragment;
    }

    public PostAnonymousLoginFragment(){
        // required empty constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null){
            this.phone_no = args.getString(PARAM1);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_post_anonymous_login, container, false);
        TrueButton trueButton =(TrueButton)rootView.findViewById(R.id.com_truecaller_android_sdk_truebutton);
        boolean usable = trueButton.isUsable();

        if (usable) {
            trueButton.setTrueClient(MainActivity.mTrueClient);
            rootView.findViewById(R.id.login_or_line_layout).setVisibility(View.VISIBLE);
        }else {
            trueButton.setVisibility(View.GONE);
            rootView.findViewById(R.id.login_or_line_layout).setVisibility(View.GONE);
        }


        mPhoneNumberET = (EditText) rootView.findViewById(R.id.login_phone_edit_text);
        mOtpET = (EditText) rootView.findViewById(R.id.login_otp_edit_text);
        mPhoneNumberET.addTextChangedListener(mobileNumberWatcher);
        mOtpET.addTextChangedListener(otpWatcher);

        if(phone_no != null && phone_no != ""){
            mPhoneNumberET.setText(phone_no);
            if(phone_no.length() >= 10 && rootView != null){
                rootView.findViewById(R.id.login_phone_submit_button).setVisibility(View.VISIBLE);
            }
        }

        mPhoneNumberET.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                    mPhoneNumberSubmitted();
                return false;
            }

        });

        mOtpET.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                    mRequestForOtpVerification(mOtpET.getText().toString());
                return false;
            }

        });

        (rootView.findViewById(R.id.fb_login)).setOnClickListener(this);
        (rootView.findViewById(R.id.login_verify_phone_button)).setOnClickListener(this);
        (rootView.findViewById(R.id.login_phone_submit_button)).setOnClickListener(this);
        (rootView.findViewById(R.id.login_resend_otp)).setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mListener = (OnUserPostAnonymousLoginListener) context;
            IntentFilter intentFilter = new IntentFilter(Constants.OTP_INTENT_FILTER);
            LocalBroadcastManager.getInstance(context).registerReceiver(otpReceiver, intentFilter);

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnUserPostAnonymousLoginListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(otpReceiver);
    }


    @Override
    public void show() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void onClick(View view) {
        if (new NetworkUtils(getActivity(), null).getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        switch (view.getId()) {
            case R.id.fb_login:
                LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email", "user_likes", "user_education_history"));
                break;
            case R.id.login_phone_submit_button:
                mPhoneNumberSubmitted();
                break;
            case R.id.login_verify_phone_button:
                mRequestForOtpVerification(mOtpET.getText().toString());
                break;
            case R.id.login_resend_otp:
                mRequestForOtp();
                break;
        }
    }
    private void mPhoneNumberSubmitted(){

        if(mRequestForOtp() && getView() != null) {
            getView().findViewById(R.id.login_otp_layout).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.login_phone_submit_button).setVisibility(View.GONE);
        }
    }

    private boolean mRequestForOtp(){
        if(mListener == null)
            return false;
        mOtpET.setText("");
        String phoneNumber = mPhoneNumberET.getText().toString();
        if (phoneNumber == null || phoneNumber.trim().equals("") ||
                phoneNumber.trim().length() < 10 || !TextUtils.isDigitsOnly(phoneNumber)) {
            mPhoneNumberET.setError("Enter Valid Mobile Number");
            return false;
        }

        mListener.onSubmitPhoneNumber(phoneNumber);
        return true;
    }

    private void mRequestForOtpVerification(String otp){
        if (otp != null && !otp.trim().equals("") && otp.trim().length() == 6){
            if (mListener != null) {
                mListener.onOtpReceived(mPhoneNumberET.getText().toString(), otp);
            }
        }

    }

    public void onInvalidOtp() {
        if(mOtpET!=null) {
            mOtpET.setError("Invalid OTP");
        }
    }

    BroadcastReceiver otpReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String otp = intent.getStringExtra(Constants.USER_OTP);
            mOtpET.setText(otp);
            mRequestForOtpVerification(otp);
        }
    };

    TextWatcher mobileNumberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mPhoneNumberET.setError(null);
        }
        @Override
        public void afterTextChanged(Editable s) {
           if(s.length() >= 10 && getView() != null){
               getView().findViewById(R.id.login_phone_submit_button).setVisibility(View.VISIBLE);
           }
        }
    };

    TextWatcher otpWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mOtpET.setError(null);
        }
        @Override
        public void afterTextChanged(Editable s) {
        }
    };

    public interface OnUserPostAnonymousLoginListener {

//        void onSkipUserLogin(HashMap<String, String> params);
        void onSubmitPhoneNumber(String phoneNumber);
        void onOtpReceived(String phoneNumber, String otp);
        void displayMessage(int messageId);

    }

}
