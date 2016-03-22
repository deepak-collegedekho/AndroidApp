package com.collegedekho.app.gcm;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by Bashiruddin on 26-Aug-15.
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().equals("com.google.android.c2dm.intent.REGISTRATION")){

//            Bundle bundle =intent.getExtras();
//            String regId =bundle.getString("registration_id");
//            Log.e("DEBUG","Broadcast received "+intent.getAction()+" "+regId);
//            if (regId!=null && !regId.matches(NotificationUtilities.getRegistrationId(context))){
//                NotificationUtilities.setRegistered(context,false);
//            }

        }else if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")){

            ComponentName comp = new ComponentName(context.getPackageName(),
                    GCMIntentService.class.getName());
            startWakefulService(context, (intent.setComponent(comp)));
            setResultCode(Activity.RESULT_OK);
        }

    }
}
