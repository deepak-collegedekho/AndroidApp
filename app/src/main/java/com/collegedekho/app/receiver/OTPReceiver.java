package com.collegedekho.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.fragment.login.LoginForCounselorFragment;
import com.collegedekho.app.fragment.login.LoginFragment;
import com.collegedekho.app.fragment.login.OTPVerificationFragment;
import com.collegedekho.app.fragment.login.PostAnonymousLoginFragment;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.ProfileMacro;

import java.util.HashMap;

/**
 * Created by Bashir on 22/2/16.
 */
public class OTPReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Return if mDeviceProfile is already verified
        if (MainActivity.mProfile != null && MainActivity.mProfile.getIs_verified() == ProfileMacro.NUMBER_VERIFIED)
            return;

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            try {
                Bundle bundle= intent.getExtras();
                if (bundle != null) {
                    Object[] pdu = (Object[]) bundle.get("pdus");
                    if(pdu!=null) {
                        for (int i = 0; i < pdu.length; i++) {

                            SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu[i]);
                            String senderAddress = sms.getDisplayOriginatingAddress();
                            if(senderAddress != null && senderAddress.contains(Constants.OTP_NUMBER)) {

                                String body = sms.getMessageBody();
                                if (body == null || !body.startsWith(Constants.OTP_BODY))
                                    return;

                                String otp = body.replace(Constants.OTP_BODY, "").trim();
                                if (!otp.matches("") && otp.length() > 0) {

                                  MainActivity mainActivity = (MainActivity)context;
                                    if(mainActivity != null
                                            && (mainActivity.currentFragment instanceof LoginFragment
                                            || mainActivity.currentFragment instanceof OTPVerificationFragment
                                            || mainActivity.currentFragment instanceof LoginForCounselorFragment
                                            || mainActivity.currentFragment instanceof PostAnonymousLoginFragment)) {
                                        Intent otpIntent = new Intent(Constants.OTP_INTENT_FILTER);
                                        otpIntent.putExtra(Constants.USER_OTP, otp);
                                        LocalBroadcastManager.getInstance(context).sendBroadcast(otpIntent);
                                    }else{
                                        Profile profile = MainActivity.mProfile;
                                        if(profile!= null && profile.getPhone_no() != null
                                                && profile.getPhone_no().length() ==10){

                                            HashMap<String, String> params = new HashMap<>();
                                            params.put(context.getString(R.string.USER_PHONE), profile.getPhone_no());
                                            params.put(context.getString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_PHONE_NUMBER);
                                            params.put(context.getString(R.string.OTP_CODE), otp);

                                            if (MainActivity.mNetworkUtils != null)
                                                MainActivity.mNetworkUtils.networkData(Constants.TAG_PHONE_NUMBER_LOGIN, Constants.BASE_URL + "auth/new-common-login/", params);

                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
