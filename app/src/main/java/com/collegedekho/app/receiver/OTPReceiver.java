package com.collegedekho.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

import com.collegedekho.app.resource.Constants;

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
                                    Intent otpIntent = new Intent(Constants.OTP_INTENT_FILTER);
                                    otpIntent.putExtra(Constants.USER_OTP, otp);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(otpIntent);
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
