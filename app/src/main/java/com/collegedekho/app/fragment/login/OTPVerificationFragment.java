package com.collegedekho.app.fragment.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.common.AppUser;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.service.OtpService;
import com.collegedekho.app.utils.Utils;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;

import static com.collegedekho.app.activity.MainActivity.mProfile;

/**
 * Created by Bashir on 24/2/16.
 */
public class OTPVerificationFragment extends BaseLoginFragment {
    private static OTPVerificationFragment sInstance;
    private LinearLayout mobileNumberLayout, otpLayout;
    private TextView submitMobileNumber, submitOtp, btnResendOtp, btnTerms;
    private EditText mPhoneNumberET, mOtpET;
    private CheckBox cbTerms;

    public static OTPVerificationFragment newInstance() {
        synchronized (OTPVerificationFragment.class) {
            if(sInstance == null)
                sInstance = new OTPVerificationFragment();
            return sInstance;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_otp_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mobileNumberLayout = (LinearLayout) view.findViewById(R.id.mobile_number_layout);
        otpLayout = (LinearLayout) view.findViewById(R.id.otp_layout);
        submitMobileNumber = (TextView) view.findViewById(R.id.btn_submit_mobile);
        submitOtp = (TextView) view.findViewById(R.id.btn_submit_otp);
        submitMobileNumber.setOnClickListener(this);
        submitOtp.setOnClickListener(this);
        btnResendOtp = (TextView) view.findViewById(R.id.btn_resend_otp);
        btnResendOtp.setOnClickListener(this);
        btnTerms = (TextView) view.findViewById(R.id.txt_terms);
        btnTerms.setOnClickListener(this);


        mPhoneNumberET = (EditText) view.findViewById(R.id.edt_mobile_number);
        mOtpET = (EditText) view.findViewById(R.id.edt_otp_number);
        mPhoneNumberET.addTextChangedListener(mobileNumberWatcher);
        mOtpET.addTextChangedListener(otpWatcher);
        cbTerms = (CheckBox) view.findViewById(R.id.cb_terms);
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

        if (mProfile != null) {

            String phone = mProfile.getPhone_no();
            if (phone != null && !phone.isEmpty()) {
                mPhoneNumberET.setText(mProfile.getPhone_no());
            ((TextView) view.findViewById(R.id.title_msg)).setText(R.string.Please_verify_your_number);
            ((TextView) view.findViewById(R.id.btn_submit_mobile)).setText(R.string.send_otp);
            }
        }
        view.findViewById(R.id.otp_verify_skip_button).setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        OtpService.IS_LOCAL_BROADCAST_RUNNING = true;
        MainActivity mMainActivity = (MainActivity) this.getActivity();
        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
    }

    @Override
    public void onPause() {
        super.onPause();
        OtpService.IS_LOCAL_BROADCAST_RUNNING = false;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            IntentFilter intentFilter = new IntentFilter(Constants.OTP_INTENT_FILTER);
            LocalBroadcastManager.getInstance(context).registerReceiver(otpReceiver, intentFilter);
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OTPVerificationListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(otpReceiver);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_submit_mobile:
                checkForSmsPermission();
                break;
            case R.id.btn_submit_otp:
               mRequestForOtpVerification();
                break;
            case R.id.btn_resend_otp:
                  onRequestForOTP();
                break;
            case R.id.txt_terms:
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.collegedekho.com/terms-and-conditions-refund-policy/"));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.otp_verify_skip_button:
                if(isAdded()) {
                    getActivity().onBackPressed();
                }
                break;
            default:
                break;
        }
    }

    public void onRequestForOTP(){

        String number = mPhoneNumberET.getText().toString().trim();
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
        if (!cbTerms.isChecked()){
            if(getActivity() != null)
                ((MainActivity)getActivity()).displayMessage(R.string.ACCEPT_TERMS_AND_CONDITIONS);
            return;
        }
        if(mProfile != null){
            mProfile.setPhone_no(number);
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.USER_PHONE), number);
            EventBus.getDefault().post(new Event(AllEvents.ACTION_REQUEST_FOR_PROFILE, params, null));
            AppUser.getInstance(getContext()).setUserStateSession(mProfile);
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


    private void mRequestForOtpVerification() {
        String otp = mOtpET.getText().toString().trim();
        if (!otp.trim().equals("") && otp.trim().length() == 6){
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.USER_PHONE),mPhoneNumberET.getText().toString());
            params.put(getString(R.string.OTP_CODE),otp);
            params.put(getString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_PHONE_NUMBER);
            EventBus.getDefault().post(new Event(AllEvents.ACTION_VERIFY_OTP, params, null));
        } else {
            mOtpET.requestFocus();
            mOtpET.setError(getString(R.string.invalid_otp));
        }
    }

    public void onInvalidOtp() {
        if(mOtpET!=null && isAdded()) {
            mOtpET.requestFocus();
            mOtpET.setError(getString(R.string.invalid_otp));
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
        mobileNumberLayout.setVisibility(View.GONE);
        otpLayout.setVisibility(View.VISIBLE);
        mPhoneNumberET.clearFocus();
        mOtpET.requestFocus();
        if(getView() != null)
            getView().findViewById(R.id.otp_verify_skip_button).setVisibility(View.GONE);
    }


}
