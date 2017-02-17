package com.collegedekho.app.fragment.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
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
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.listener.UserLoginListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.NetworkUtils;
import com.collegedekho.app.utils.Utils;
import com.truecaller.android.sdk.TrueButton;

import java.util.HashMap;

/**
 *Created by ${sureshsaini} on ${20/11/15}.
 */
public class LoginFragment extends BaseFragment {

    private UserLoginListener mListener;
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
                    checkForSmsPermission();
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
            this.mListener = (UserLoginListener) context;
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
            case R.id.login_phone_submit_button:
                checkForSmsPermission();
                break;
            case R.id.login_verify_phone_button:
                mRequestForOtpVerification();
                break;
            case R.id.login_resend_otp:
                mRequestForOTP();
                break;
            case R.id.sign_up_skip_layout:
                mSkipUserLogin();
                break;
        }
    }


    private void mSkipUserLogin()
    {
        if(mListener != null) {
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_ANONYMOUS);
            if(getActivity() != null)
                ((MainActivity)getActivity()).onSplashHelpMeLogin();
        }
    }

    private void checkForSmsPermission() {
        String phoneNumber = mPhoneNumberET.getText().toString();
        if (phoneNumber == null || phoneNumber.trim().isEmpty()){
            mPhoneNumberET.requestFocus();
            mPhoneNumberET.setError("Please Enter Your Mobile Number");
            return;
        }else if( phoneNumber.trim().length() < 10 || !TextUtils.isDigitsOnly(phoneNumber)) {
            mPhoneNumberET.requestFocus();
            mPhoneNumberET.setError("Enter Valid Mobile Number");
            return;
        }
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.sms_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.RECEIVE_SMS},
                                    Constants.RC_HANDLE_SMS_PERM);
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();

        } else {
            mOtpET.setText("");
            if (mListener != null)
                mListener.onRequestForOTP(phoneNumber);
        }
    }

    public boolean mRequestForOTP(){
        String phoneNumber = mPhoneNumberET.getText().toString().trim();
        if (phoneNumber.trim().equals("") ||
                phoneNumber.trim().length() < 10 || !TextUtils.isDigitsOnly(phoneNumber)) {
            mPhoneNumberET.requestFocus();
            mPhoneNumberET.setError("Enter Valid Mobile Number");
            return false;
        }
        mOtpET.setText("");
        if(mListener != null)
            mListener.onRequestForOTP(phoneNumber);
        return true;
    }


    BroadcastReceiver otpReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String otp = intent.getStringExtra(Constants.USER_OTP);
            mOtpET.setText(otp);
            mRequestForOtpVerification();
        }
    };

    private void mRequestForOtpVerification(){
        String otp = mOtpET.getText().toString().trim();
        if (!otp.trim().equals("") && otp.trim().length() == 6){
            if (mListener != null) {
                mListener.onVerifyOTP(mPhoneNumberET.getText().toString(), otp);
            }
        }else{
            mOtpET.requestFocus();
            mOtpET.setError("Invalid OTP");
        }

    }

    public void onInvalidOtp() {
        if(mOtpET!=null) {
            mOtpET.requestFocus();
            mOtpET.setError("Invalid OTP");
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

    public void displayOTPLayout() {
        if(getView() != null) {
            getView().findViewById(R.id.login_otp_layout).setVisibility(View.VISIBLE);
            getView().findViewById(R.id.login_phone_submit_button).setVisibility(View.GONE);
        }
    }
}
