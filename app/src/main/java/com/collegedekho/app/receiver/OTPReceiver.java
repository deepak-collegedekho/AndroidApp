/*
package com.collegedekho.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.events.AllEvents;
import com.collegedekho.app.events.Event;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.ProfileMacro;

import org.greenrobot.eventbus.EventBus;

*/
/**
 * Created by Bashir on 22/2/16.
 *//*

public class OTPReceiver extends BroadcastReceiver {
    public static boolean IS_LOCAL_BROADCAST_RUNNING = false;
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

                                    if(IS_LOCAL_BROADCAST_RUNNING) {
                                        Intent otpIntent = new Intent(Constants.OTP_INTENT_FILTER);
                                        otpIntent.putExtra(Constants.USER_OTP, otp);
                                        LocalBroadcastManager.getInstance(context).sendBroadcast(otpIntent);
                                    }else{
                                        EventBus.getDefault().post(new Event(AllEvents.ACTION_VERIFY_OTP,null , otp));
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
*/
