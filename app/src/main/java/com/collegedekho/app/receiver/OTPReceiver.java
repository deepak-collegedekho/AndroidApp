package com.collegedekho.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;
import android.util.Log;

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
                    for (int i = 0; i < pdu.length; i++) {

                        SmsMessage sms = SmsMessage.createFromPdu((byte[]) pdu[i]);
                        String sender = sms.getDisplayOriginatingAddress();
                        String text = sender;
                        String body = sms.getMessageBody();
                        String otp;
                        if(body!=null && body.startsWith(Constants.OTP_BODY)){
                            otp=body.replace(Constants.OTP_BODY,"").trim();
                        }else {
                            Log.e("DEBUG",body);
                            return;
                        }
                        if (otp!=null && !otp.matches("") && otp.length()>0) {

//                            Toast.makeText(context, sender+": "+otp, Toast.LENGTH_SHORT).show();
                            Intent otpIntent=new Intent(Constants.OTP_INTENT_FILTER);
                            otpIntent.putExtra(Constants.USER_OTP,otp);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(otpIntent);

                        } else {
                            return;
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
