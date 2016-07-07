package com.collegedekho.app.fragment;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;

import java.util.HashMap;

/**
 * Created by Bashir on 24/2/16.
 */
public class OTPVerificationFragment extends BaseFragment {
    private LinearLayout mobileNumberLayout, otpLayout;
    private TextView submitMobileNumber, submitOtp, btnResendOtp, btnTerms;
    private EditText edtMobileNumber, edtOTP;
    private CheckBox cbTerms;
    private OTPVerificationListener mListener;
    InputMethodManager imm;
    public static OTPVerificationFragment newInstance() {

        Bundle args = new Bundle();

        OTPVerificationFragment fragment = new OTPVerificationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.otp_fragment_layout, container, false);
        return rootView;
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


        edtMobileNumber = (EditText) view.findViewById(R.id.edt_mobile_number);
        edtOTP = (EditText) view.findViewById(R.id.edt_otp_number);
        edtMobileNumber.addTextChangedListener(mobileNumberWatcher);
        edtOTP.addTextChangedListener(otpWatcher);
        cbTerms = (CheckBox) view.findViewById(R.id.cb_terms);
        edtMobileNumber.requestFocus();

        if (MainActivity.mProfile != null) {
            String phone = MainActivity.mProfile.getPhone_no();
            if (phone != null && !phone.isEmpty()) {
            edtMobileNumber.setText(MainActivity.mProfile.getPhone_no());
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
            this.mListener = (OTPVerificationListener) context;
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
    public void hide() {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {

            case R.id.otp_verify_skip_button:
                getActivity().onBackPressed();
                break;
            case R.id.btn_submit_mobile:
                if (mListener != null) {

                    String number = edtMobileNumber.getText().toString();
                    if (number != null && !number.trim().equals("") && number.trim().length() == 10 && TextUtils.isDigitsOnly(number)) {
                        if (cbTerms.isChecked()) {
                            mListener.onSubmitPhoneNumber(number);
                            if(MainActivity.mProfile != null){
                                MainActivity.mProfile.setPhone_no(number);
                                HashMap<String, String> params = new HashMap<>();
                                params.put(getString(R.string.USER_PHONE), number);
                                mListener.requestForProfile(params, Request.Method.POST);
                            }
                        } else {
                            mListener.displayMessage(R.string.ACCEPT_TERMS_AND_CONDITIONS);
                        }
                    } else {
                        edtMobileNumber.setError("Enter Valid Mobile Number");
                    }
                }
                break;

            case R.id.btn_submit_otp:
                if (mListener != null) {
                    String otp = edtOTP.getText().toString();
                    if (otp != null && !otp.trim().equals("") && otp.trim().length() == 6) {
                        mListener.onSubmitOTP(edtMobileNumber.getText().toString(), otp);
                    } else {
                        edtOTP.setError("Invalid OTP");
                    }

                }
                break;
            case R.id.btn_resend_otp:
                edtOTP.setText("");
                if (mListener != null) {
                    mListener.onResendOTP(edtMobileNumber.getText().toString());
                }
                break;
            case R.id.txt_terms:
                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://m.collegedekho.com/terms-and-conditions/"));
                    startActivity(browserIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    TextWatcher mobileNumberWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            edtMobileNumber.setError(null);
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
            edtOTP.setError(null);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    BroadcastReceiver otpReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String otp = intent.getStringExtra(Constants.USER_OTP);
            edtOTP.setText(otp);
            if (otp != null && !otp.trim().equals("") && otp.trim().length() == 6){
                if (mListener != null) {
                    mListener.onSubmitOTP(edtMobileNumber.getText().toString(), otp);
                }
            }
        }
    };

    public void displayOTPLayout() {
        edtMobileNumber.clearFocus();
        mobileNumberLayout.setVisibility(View.GONE);
        otpLayout.setVisibility(View.VISIBLE);
        edtOTP.requestFocus();
        if(getView() != null)
            getView().findViewById(R.id.otp_verify_skip_button).setVisibility(View.GONE);
    }

    public void onInvalidOtp() {
        if(edtOTP!=null) {
            edtOTP.setError("OTP is invalid");
        }
    }

    public interface OTPVerificationListener {
        void onSubmitPhoneNumber(String mobileNumber);
        void onSubmitOTP(String mobileNumber, String otp);
        void requestForProfile(HashMap<String, String> params, int method);
        void onResendOTP(String mobileNumber);
        void displayMessage(int messageId);
    }
}
