package com.collegedekho.app.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.service.ApplyIntentService;

/**
 * Created by harshvardhan on 08/09/15.
 */
public class ConnectivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {
        //Constants.IS_CONNECTED_TO_INTERNET = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);

       /* ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isAvailable())
        {
            SharedPreferences preferences = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
            if(preferences.getInt(Constants.KEY_APPLY_STATUS,Constants.APPLY_COMPLETE)==Constants.APPLY_PENDING)
            {
                Intent in = new Intent(context, ApplyIntentService.class);
                context.startService(in);
            }
        }*/
    }

}

