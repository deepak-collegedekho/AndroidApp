package com.collegedekho.app.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Telephony;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.telephony.SmsMessage;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.common.AppUser;
import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.listener.DataLoadListener;
import com.collegedekho.app.network.NetworkUtils;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.utils.ProfileMacro;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.collegedekho.app.activity.MainActivity.mProfile;

/**
 * Created by sureshsaini on 20/2/17.
 */

public class OtpService extends Service  implements DataLoadListener{

    private OTPReceiver mOtpReceiver;
    public static boolean IS_LOCAL_BROADCAST_RUNNING = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        // register OTP broadcast receiver if user's phone number
        // is not verified
            if (mOtpReceiver == null)
                mOtpReceiver = new OTPReceiver();

            IntentFilter filter;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                filter = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
            } else {
                filter = new IntentFilter();
                filter.addAction("android.provider.Telephony.SMS_RECEIVED");
            }
            this.registerReceiver(mOtpReceiver, filter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // unregister Otp broadcast receiver
        if (mOtpReceiver != null) {
            unregisterReceiver(mOtpReceiver);
            mOtpReceiver = null;
        }

    }


    public class OTPReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Return if mDeviceProfile is already verified
            if (mProfile != null && mProfile.getIs_verified() == ProfileMacro.NUMBER_VERIFIED)
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
                                            verifyOTP(otp);
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


    private void verifyOTP(String otp) {
        Profile profile = AppUser.getInstance(getApplicationContext()).getUser();
        String phone = profile.getPhone_no();
        if(phone != null && phone.length() == 10){
            HashMap<String, String> params = new HashMap<>();
            params.put(getString(R.string.USER_PHONE), phone);
            params.put(getString(R.string.USER_LOGIN_TYPE), Constants.LOGIN_TYPE_PHONE_NUMBER);
            params.put(getString(R.string.OTP_CODE), otp);

            NetworkUtils networkUtil = new NetworkUtils(getApplicationContext(), this);
            networkUtil.setToken(profile.getToken());
            networkUtil.networkData(Constants.ACTION_VERIFY_OTP, Constants.BASE_URL + "auth/new-common-login/", params);

        }
    }

    @Override
    public void onDataLoaded(String tag, String response) {

        switch (tag) {
            case Constants.ACTION_VERIFY_OTP:
                try {
                    Profile profile = JSON.std.beanFrom(Profile.class, response);
                    AppUser.getInstance(getApplicationContext()).setUserStateSession(profile);

                    //TODO:: set user token in network class
                    MainActivity.setUserToken(profile);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void onError(String tag, String response, int responseCode, String url, Map<String, String> params, int method) {

    }

    @Override
    public void onJsonObjectRequestError(String tag, String response, String url, JSONObject params, int method) {

    }
}
