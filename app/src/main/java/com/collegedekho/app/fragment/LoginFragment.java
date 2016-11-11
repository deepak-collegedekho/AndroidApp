package com.collegedekho.app.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.truecaller.android.sdk.TrueButton;

import java.util.HashMap;

/**
 *Created by ${sureshsaini} on ${20/11/15}.
 */
public class LoginFragment extends  BaseFragment {

    private OnUserLoginListener mListener;
    private EditText mPhoneNumberET;
    private EditText mOtpET;


    public static LoginFragment newInstance()
    {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return  fragment;
    }

    public LoginFragment(){
        // required empty constructor
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
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

        mPhoneNumberET.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    Utils.hideKeyboard(getActivity());
                    mPhoneNumberSubmitted();
                    return  true;
                }
                return false;
            }
        });
        mOtpET.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            Utils.hideKeyboard(getActivity());
                            mRequestForOtpVerification();
                            return  true;
                        }
                        return false;
                    }
                }
        );

        rootView.findViewById(R.id.sign_up_skip_layout).setOnClickListener(this);
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
            this.mListener = (OnUserLoginListener) context;
            IntentFilter intentFilter = new IntentFilter(Constants.OTP_INTENT_FILTER);
            LocalBroadcastManager.getInstance(context).registerReceiver(otpReceiver, intentFilter);

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnSignUpListener");
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
    public String getEntity() {
        return null;
    }

    @Override
    public void hide() {

    }

    @Override
    public void onClick(View view) {
        if (NetworkUtils.getConnectivityStatus() == Constants.TYPE_NOT_CONNECTED) {
            ((MainActivity) getActivity()).displaySnackBar(R.string.INTERNET_CONNECTION_ERROR);
            return;
        }
        switch (view.getId()) {
            case R.id.sign_up_skip_layout:
                mSkipUserLogin();
                break;
            case R.id.fb_login:
                //LoginManager.getInstance().logInWithReadPermissions(getActivity(), Arrays.asList("public_profile", "user_friends", "email", "user_likes", "user_education_history"));
                break;
            case R.id.login_phone_submit_button:
                mPhoneNumberSubmitted();
                break;
            case R.id.login_verify_phone_button:
                mRequestForOtpVerification();
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
        String phoneNumber = mPhoneNumberET.getText().toString().trim();
        if (phoneNumber.trim().equals("") ||
                phoneNumber.trim().length() < 10 || !TextUtils.isDigitsOnly(phoneNumber)) {
            mPhoneNumberET.setError("Enter Valid Mobile Number");
            return false;
        }

        mListener.onSubmitPhoneNumber(phoneNumber);
        return true;
    }

    private void mRequestForOtpVerification(){
        String otp = mOtpET.getText().toString().trim();
        if (!otp.trim().equals("") && otp.trim().length() == 6){
            if (mListener != null) {
                mListener.onOtpReceived(mPhoneNumberET.getText().toString(), otp);
            }
        }else{
            mOtpET.setError("Invalid OTP");
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
            mRequestForOtpVerification();
        }
    };

    private void mSkipUserLogin()
    {
        if(mListener != null) {
            HashMap<String, String> params = new HashMap<>();
           // String deviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);
            //params.put(MainActivity.getResourceString(R.string.USER_DEVICE_ID), deviceId);
            params.put(MainActivity.getResourceString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_ANONYMOUS);

           // mListener.onSkipUserLogin(params);
        }
    }

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
           if(s.length() >= 10 && getView() != null
                   &&  getView().findViewById(R.id.login_otp_layout).getVisibility() == View.GONE){
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

    public interface OnUserLoginListener {

        //void onSkipUserLogin(HashMap<String, String> params);
        void onSubmitPhoneNumber(String phoneNumber);
        void onOtpReceived(String phoneNumber, String otp);
        void displayMessage(int messageId);
    }
}
