package com.collegedekho.app.gcm;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import com.collegedekho.app.activity.GCMDialogActivity;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;

/**
 * Created by Bashir on 7/4/16.
 */
public class NotificationIntentService extends IntentService {

    public NotificationIntentService() {
        super("NotificationIntentService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public NotificationIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String resource_uri = intent.getStringExtra("resource_uri");
        String screen = intent.getStringExtra("screen");
        if (resource_uri != null && !resource_uri.trim().matches("") && screen != null && !screen.trim().matches("")) {
            if (MainActivity.IN_FOREGROUND) {
                intent.setAction(Constants.NOTIFICATION_FILTER);
                LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            } else {
                intent.setComponent(new ComponentName(this,MainActivity.class));
                intent.putExtra("screen", intent.getExtras().getString("screen"));
                intent.putExtra("resource_uri", intent.getExtras().getString("resource_uri"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        } else {
            intent.setComponent(new ComponentName(this,GCMDialogActivity.class));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}
