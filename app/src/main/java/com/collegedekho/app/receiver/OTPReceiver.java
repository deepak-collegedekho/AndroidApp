package com.collegedekho.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.fragment.OTPVerificationFragment;
import com.collegedekho.app.resource.Constants;

import java.util.HashMap;

/**
 * Created by Bashir on 22/2/16.
 */
public class OTPReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
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

                                    // TODO :: change this code  instead of checking current
                                    // TODO:: instance to some other logic
                                    if(MainActivity.currentFragment != null
                                            && !(MainActivity.currentFragment instanceof OTPVerificationFragment))
                                    {
                                        Profile profile = MainActivity.mProfile;
                                        if(profile!= null && profile.getPhone_no() != null
                                                && profile.getPhone_no().length() ==10){

                                            HashMap<String, String> params = new HashMap<>();
                                            params.put("phone_no", profile.getPhone_no());
                                            params.put(Constants.OTP_CODE, otp);

                                            if (MainActivity.mNetworkUtils != null)
                                                MainActivity.mNetworkUtils.networkData(Constants.TAG_VERIFY_USER_PHONE, Constants.BASE_URL + "verify-otp/", params);

                                        }

                                    }else {
                                        Intent otpIntent = new Intent(Constants.OTP_INTENT_FILTER);
                                        otpIntent.putExtra(Constants.USER_OTP, otp);
                                        LocalBroadcastManager.getInstance(context).sendBroadcast(otpIntent);
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
