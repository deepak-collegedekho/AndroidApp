package com.collegedekho.app.fragment.login;

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
import android.widget.EditText;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.service.OtpService;
import com.truecaller.android.sdk.TrueButton;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;


/**
 * Created by sureshsaini on 20/11/15.
 */
public class PostAnonymousLoginFragment extends BaseLoginFragment {

    private static PostAnonymousLoginFragment sInstance;
    private EditText mPhoneNumberET;
    private EditText mOtpET;
    private static String PARAM1  = "param1";
    private String phone_no;


    public static PostAnonymousLoginFragment newInstance(String phone_no)
    {
        synchronized (PostAnonymousLoginFragment.class){
            if(sInstance == null)
                sInstance = new PostAnonymousLoginFragment();
            Bundle args = new Bundle();
            args.putString(PARAM1, phone_no);
            sInstance.setArguments(args);
        }
        return  sInstance;
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
        return inflater.inflate(R.layout.fragment_post_anonymous_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TrueButton trueButton =(TrueButton)view.findViewById(R.id.com_truecaller_android_sdk_truebutton);
        boolean usable = trueButton.isUsable();

        if (usable) {
            trueButton.setTrueClient(MainActivity.mTrueClient);
            view.findViewById(R.id.login_or_line_layout).setVisibility(View.VISIBLE);
        }else {
            trueButton.setVisibility(View.GONE);
            view.findViewById(R.id.login_or_line_layout).setVisibility(View.GONE);
        }


        mPhoneNumberET = (EditText) view.findViewById(R.id.login_phone_edit_text);
        mOtpET = (EditText) view.findViewById(R.id.login_otp_edit_text);
        mPhoneNumberET.addTextChangedListener(mobileNumberWatcher);
        mOtpET.addTextChangedListener(otpWatcher);

        if(phone_no != null && phone_no != ""){
            mPhoneNumberET.setText(phone_no);
            if(phone_no.length() >= 10 && view != null){
                view.findViewById(R.id.login_phone_submit_button).setVisibility(View.VISIBLE);
            }
        }

        mPhoneNumberET.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                    checkForSmsPermission();
                return false;
            }

        });

        mOtpET.setOnKeyListener(new View.OnKeyListener(){
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == event.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN)
                    mRequestForOtpVerification();
                return false;
            }

        });

        view.findViewById(R.id.login_skip_layout).setOnClickListener(this);
        view.findViewById(R.id.fb_login).setOnClickListener(this);
        view.findViewById(R.id.login_verify_phone_button).setOnClickListener(this);
        view.findViewById(R.id.login_phone_submit_button).setOnClickListener(this);
        view.findViewById(R.id.login_resend_otp).setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            IntentFilter intentFilter = new IntentFilter(Constants.OTP_INTENT_FILTER);
            LocalBroadcastManager.getInstance(context).registerReceiver(otpReceiver, intentFilter);

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnUserPostAnonymousLoginListener");
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        OtpService.IS_LOCAL_BROADCAST_RUNNING = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        OtpService.IS_LOCAL_BROADCAST_RUNNING = false;
    }


    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(otpReceiver);
    }

    @Override
    public void onClick(View view) {
        if (NetworkUtils.getConnectivityStatus(getContext()) == Constants.TYPE_NOT_CONNECTED) {
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
                onRequestForOTP();
                break;
            case R.id.login_skip_layout:
                if(isAdded()) {
                    getActivity().onBackPressed();
                }
                break;
        }
    }
    @Override
    public void onRequestForOTP(){
        String phoneNumber = mPhoneNumberET.getText().toString();
        if (phoneNumber == null || phoneNumber.trim().isEmpty()){
            mPhoneNumberET.requestFocus();
            mPhoneNumberET.setError(getString(R.string.PHONE_EMPTY));
            return;
        }else if( phoneNumber.trim().length() < 10 || !TextUtils.isDigitsOnly(phoneNumber)) {
            mPhoneNumberET.requestFocus();
            mPhoneNumberET.setError(getString(R.string.PHONE_INVALID));
            return;
        }

        this.mOtpET.setText("");
        HashMap<String, String> params = new HashMap<>();
        params.put(getString(R.string.USER_PHONE),phoneNumber);
        EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_OTP, params, null));
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
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.USER_PHONE),mPhoneNumberET.getText().toString());
            params.put(getString(R.string.OTP_CODE),otp);
            params.put(getString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_PHONE_NUMBER);
            EventBus.getDefault().post(new Event(AllEvents.ACTION_VERIFY_OTP, params, null));
        } else {
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
