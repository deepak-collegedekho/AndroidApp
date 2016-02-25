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
import android.text.Html;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.Utils;

/**
 * Created by Bashir on 24/2/16.
 */
public class OTPVerificationFragment extends BaseFragment {
    private LinearLayout mobileNumberLayout, otpLayout;
    private TextView submitMobileNumber, submitOtp, btnResendOtp, btnTerms;
    private EditText edtMobileNumber, edtOTP;
    private CheckBox cbTerms;
    private OTPVerificationListener mListener;

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
//        btnTerms.setMovementMethod(LinkMovementMethod.getInstance());
//        String text = "<a href='https://m.collegedekho.com/terms-and-conditions/'> terms and conditions </a>";
//        btnTerms.setText(Html.fromHtml(text));

        edtMobileNumber = (EditText) view.findViewById(R.id.edt_mobile_number);
        edtOTP = (EditText) view.findViewById(R.id.edt_otp_number);
        edtMobileNumber.addTextChangedListener(mobileNumberWatcher);
        edtOTP.addTextChangedListener(otpWatcher);
        cbTerms = (CheckBox) view.findViewById(R.id.cb_terms);
        edtMobileNumber.requestFocus();
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
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_submit_mobile:
                if (mListener != null) {

                    String number = edtMobileNumber.getText().toString();
                    if (number != null && !number.trim().equals("") && number.trim().length() == 10) {
                        if (cbTerms.isChecked()) {
                            mListener.onSubmitMobileNumber(number);
                        } else {
                            Utils.DisplayToast(v.getContext(), "Please read and accept terms conditions.");
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
        }
    };

    public void displayOTPLayout() {
        mobileNumberLayout.setVisibility(View.GONE);
        otpLayout.setVisibility(View.VISIBLE);
        edtOTP.requestFocus();
    }

    public void onInvalidOtp() {
        edtOTP.setError("OTP is invalid");
    }

    public interface OTPVerificationListener {
        public void onSubmitMobileNumber(String mobileNumber);

        public void onSubmitOTP(String mobileNumber, String otp);

        public void onResendOTP(String mobileNumber);
    }
}