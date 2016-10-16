package com.collegedekho.app.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.entities.NotificationPayload;
import com.collegedekho.app.listener.ICollegeDekhoNotifications;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;


/**
 * Created by harshvardhan on 05/10/16.
 */

public class CollegeDekhoNotifications implements ICollegeDekhoNotifications{

    private static final String TAG = "CDNotifications";
    public Context mContext;
    public Notification notification;
    public NotificationPayload mNotificationPayload;
    public NotificationCompat.Builder builder;
    public RemoteViews contentBigView;
    public RemoteViews contentView;
    public PendingIntent pendingIntent;
    public int bigImageURLViewID = -1;

    public static String getTimeFromMS(long j) {
        return new SimpleDateFormat("h:mm a", Locale.US).format(new Date(j));
    }

    @Override
    public void process(Map<String, String> messageDataMap, Context context) {
        this.mContext = context;

        try {
            JSONObject jsonObject = new JSONObject(messageDataMap);

            this.mNotificationPayload = JSON.std.beanFrom(NotificationPayload.class, jsonObject.toString());

            Intent intent = new Intent(context, MainActivity.class);

            intent.setAction("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");

            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

            for (Map.Entry<String, String> entry : messageDataMap.entrySet())
                intent.putExtra(entry.getKey(), entry.getValue());

            this.pendingIntent = PendingIntent.getActivity(this.mContext, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            this.builder = new NotificationCompat.Builder(this.mContext.getApplicationContext());

            long currentTimeMillis = System.currentTimeMillis();

            this.builder = new NotificationCompat.Builder(this.mContext.getApplicationContext());

            this.builder.setSmallIcon(R.drawable.ic_notification_lollipop)
                    .setDefaults(5)
                    .setContentIntent(this.pendingIntent)
                    .setOngoing(false)
                    .setWhen(currentTimeMillis)
                    .setAutoCancel(true);

            this.builder.setOnlyAlertOnce(true);

            if (Build.VERSION.SDK_INT >= 21)
                this.builder.setVisibility(View.INVISIBLE);

            this.notification = this.builder.build();
        }
        catch (IOException e)
        {
            Log.e(TAG, "IO Exception");
        }
        catch (Exception e)
        {
            Log.e(TAG, "Exception");
        }
    }

    @Override
    public Notification getNotification() {
        return this.notification;
    }
}
