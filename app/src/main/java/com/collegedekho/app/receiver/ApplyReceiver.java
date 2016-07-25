package com.collegedekho.app.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.collegedekho.app.R;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.service.ApplyService;

/**
 * Created by Suresh Kumar on 07/09/15.
 */
public class ApplyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent)
    {

       // intent.addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
       ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        if (activeNetwork != null && activeNetwork.isAvailable())
        {
            SharedPreferences preferences = context.getSharedPreferences(context.getString(R.string.PREFS), Context.MODE_PRIVATE);
            if(preferences.getInt(Constants.KEY_APPLY_STATUS,Constants.APPLY_COMPLETE)==Constants.APPLY_PENDING)
            {
                Intent in = new Intent(context, ApplyService.class);
                context.startService(in);
            }

        }

        Constants.IS_CONNECTED_TO_INTERNET = !intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false);
    }

}

