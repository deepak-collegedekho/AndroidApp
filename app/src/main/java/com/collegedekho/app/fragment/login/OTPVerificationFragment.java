package com.collegedekho.app.fragment.login;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.fragment.BaseFragment;
import com.collegedekho.app.listener.UserLoginListener;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;

import java.util.HashMap;

import static com.collegedekho.app.activity.MainActivity.mProfile;

/**
 * Created by Bashir on 24/2/16.
 */
public class OTPVerificationFragment extends BaseFragment {
    private LinearLayout mobileNumberLayout, otpLayout;
    private TextView submitMobileNumber, submitOtp, btnResendOtp, btnTerms;
    private EditText mPhoneNumberET, mOtpET;
    private CheckBox cbTerms;
    private UserLoginListener mListener;
    InputMethodManager imm;

    public static OTPVerificationFragment newInstance() {
        return new OTPVerificationFragment();
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
        imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);


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
        MainActivity mMainActivity = (MainActivity) this.getActivity();
        if (mMainActivity != null)
            mMainActivity.currentFragment = this;
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
                    + " must implement OTPVerificationListener");
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
                  mRequestForOTP();
                break;
            case R.id.txt_terms:
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.collegedekho.com/terms-and-conditions/"));
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

    private void checkForSmsPermission(){
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            // Use the Builder class for convenient dialog construction
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(R.string.sms_permission)
                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            ActivityCompat.requestPermissions(getActivity(),  new String[]{Manifest.permission.RECEIVE_SMS},
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

        }else{
            mRequestForOTP();
        }
    }

    public void mRequestForOTP(){

        String number = mPhoneNumberET.getText().toString().trim();
        if (!number.isEmpty() && number.trim().length() == 10 && TextUtils.isDigitsOnly(number)) {
            if (cbTerms.isChecked()) {
                mListener.onRequestForOTP(number);
                if(mProfile != null){
                    mProfile.setPhone_no(number);
                    HashMap<String, String> params = new HashMap<>();
                    params.put(getString(R.string.USER_PHONE), number);
                    mListener.requestForProfile(params);
                }
            } else {
                mListener.displayMessage(R.string.ACCEPT_TERMS_AND_CONDITIONS);
            }
        } else {
            mPhoneNumberET.requestFocus();
            mPhoneNumberET.setError("Enter Valid Mobile Number");
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


    public void onInvalidOtp() {
        if(mOtpET!=null) {
            mOtpET.requestFocus();
            mOtpET.setError("OTP is invalid");
        }
    }

    private void mRequestForOtpVerification() {
        String otp = mOtpET.getText().toString().trim();
        if (!otp.trim().equals("") && otp.trim().length() == 6){
            if (mListener != null) {
                mListener.onVerifyOTP(mPhoneNumberET.getText().toString(), otp);
            }
        } else {
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
        mPhoneNumberET.clearFocus();
        mobileNumberLayout.setVisibility(View.GONE);
        otpLayout.setVisibility(View.VISIBLE);
        mOtpET.requestFocus();
        if(getView() != null)
            getView().findViewById(R.id.otp_verify_skip_button).setVisibility(View.GONE);
    }


}
