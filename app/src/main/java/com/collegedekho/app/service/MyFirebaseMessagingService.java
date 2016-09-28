/**
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.collegedekho.app.service;

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
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    private RemoteViews contentBigView;
    private Notification build;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        sendNotification(remoteMessage.getData());
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageDataMap FCM message body received.
     */
    private void sendNotification(Map<String, String> messageDataMap) {
        String bigImageURL = null;
        String title = "";
        String body = "";

        Intent intent = new Intent(this, MainActivity.class);

        intent.setAction("android.intent.action.MAIN");
        intent.addCategory("android.intent.category.LAUNCHER");

        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        for (Map.Entry<String, String> entry : messageDataMap.entrySet())
            intent.putExtra(entry.getKey(), entry.getValue());

        title = messageDataMap.get("title");
        body = messageDataMap.get("body");

        CharSequence titleSequence = title.subSequence(0, title.length());
        CharSequence bodySequence = body.subSequence(0, body.length());

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_ONE_SHOT);

        // Inflate the notification layout as RemoteViews
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notification);

        contentView.setTextViewText(R.id.notification_title, titleSequence);
        long currentTimeMillis = System.currentTimeMillis();
        CharSequence timeSequence = this.getTimeFromMS(currentTimeMillis);
        contentView.setTextViewText(R.id.notification_time, timeSequence);

        if (Build.VERSION.SDK_INT >= 16 && titleSequence.length() > 50) {
            contentView.setTextViewTextSize(R.id.notification_title, 1, 14.0f);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());

        builder.setSmallIcon(R.drawable.ic_notification_lollipop)
                .setDefaults(5)
                .setContentIntent(pendingIntent)
                .setContentTitle(titleSequence)
                .setContentText(bodySequence)
                .setOngoing(false)
                .setWhen(currentTimeMillis)
                .setAutoCancel(true);

        builder.setOnlyAlertOnce(true);

        if (Build.VERSION.SDK_INT >= 21)
            builder.setVisibility(View.INVISIBLE);

        build = builder.build();
        build.contentView = contentView;

        if (Build.VERSION.SDK_INT >= 16) {
            contentBigView = new RemoteViews(getPackageName(), R.layout.expanded_notification);

            String bigTitle = messageDataMap.get("big_title");
            CharSequence bigTitleSequence = bigTitle.subSequence(0, bigTitle.length() -1);

            contentBigView.setTextViewText(R.id.expanded_notification_title, bigTitleSequence);

            bigImageURL = messageDataMap.get("big_image");

            if (bigImageURL != null && !bigImageURL.isEmpty())
                new NotificationImageAsyncTask().execute(messageDataMap.get("big_image"));
            else
            {
                contentBigView.setImageViewBitmap(R.id.news_image, BitmapFactory.decodeResource(getApplicationContext().getResources(), R.drawable.college_logo));
                build.bigContentView = contentBigView;
            }
        }
        if (bigImageURL == null || bigImageURL.isEmpty())
        {
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, build);
        }
    }

    private static String getTimeFromMS(long j) {
        return new SimpleDateFormat("h:mm a", Locale.US).format(new Date(j));
    }

    public class NotificationImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bm = null;
            try {
                URL aURL = new URL(params[0]);
                URLConnection conn = aURL.openConnection();
                conn.connect();
                InputStream is = conn.getInputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                bm = BitmapFactory.decodeStream(bis);
                bis.close();
                is.close();
            } catch (IOException e) {
                Log.e(TAG, "Error getting bitmap", e);
            }
            return bm;
        }


        @Override protected void onPostExecute(Bitmap result) {

            contentBigView.setImageViewBitmap(R.id.news_image , result);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                build.bigContentView = contentBigView;

            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, build);
        }
    }
}
