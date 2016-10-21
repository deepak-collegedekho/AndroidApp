package com.collegedekho.app.notifications;


import android.content.Context;
import android.os.Build;
import android.widget.RemoteViews;

import com.collegedekho.app.R;
import com.crashlytics.android.Crashlytics;

import java.util.Map;

import static android.support.v4.app.NotificationCompat.DEFAULT_ALL;

/**
 * Created by harshvardhan on 05/10/16.
 */

public class MyFutureBuddyNotification extends CollegeDekhoNotifications {

    private static final String TAG = "MyFutureBuddyNotification";

    @Override
    public void process(Map<String, String> messageDataMap, Context context) {

        super.process(messageDataMap, context);

        long currentTimeMillis = System.currentTimeMillis();

/*        super.builder.setSmallIcon(this.getNotificationIcon())
                .setDefaults(DEFAULT_ALL)
                .setContentIntent(this.pendingIntent)
                .setOngoing(true)
                .setWhen(currentTimeMillis)
                .setOnlyAlertOnce(true)
                .setAutoCancel(true);

        super.notification = super.builder.build();*/

        String title = "";
        String body = "";

        try {
            title = super.mNotificationPayload.getTitle();
            body = super.mNotificationPayload.getBody();

            // Inflate the notification_my_fb layout as RemoteViews
            super.contentView = new RemoteViews(context.getPackageName(), R.layout.notification_my_fb);

            if (title != null && !title.isEmpty())
            {
                CharSequence titleSequence = title.subSequence(0, title.length());
                super.contentView.setTextViewText(R.id.notification_fb_title, titleSequence);

                if (Build.VERSION.SDK_INT >= 16 && titleSequence.length() > 50) {
                    super.contentView.setTextViewTextSize(R.id.notification_fb_title, 1, 14.0f);
                }
            }
            else
            {
                return;
            }

            if (body != null && !body.isEmpty())
            {
                CharSequence bodySequence = body.subSequence(0, body.length());
                super.contentView.setTextViewText(R.id.notification_fb_desc, bodySequence);
            }

            CharSequence timeSequence = this.getTimeFromMS(currentTimeMillis);
            super.contentView.setTextViewText(R.id.notification_fb_time, timeSequence);

            super.notification.contentView = super.contentView;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                String bigTitle = super.mNotificationPayload.getBig_title();
                String bigBody = super.mNotificationPayload.getBig_body();

                // Inflate the notification_my_fb_expanded layout as RemoteViews
                super.contentBigView = new RemoteViews(super.mContext.getPackageName(), R.layout.notification_my_fb_expanded);

                if (bigTitle != null && !bigTitle.isEmpty())
                {
                    //if bit_title is there, show UI
                    CharSequence bigTitleSequence = bigTitle.subSequence(0, bigTitle.length());

                    super.contentBigView.setTextViewText(R.id.notification_fb_title_expanded, bigTitleSequence);
                }
                else {
                    super.contentBigView.setTextViewText(R.id.notification_fb_title_expanded, title);
                }

                if (bigBody != null && !bigBody.isEmpty())
                {
                    CharSequence bigBodySequence = bigBody.subSequence(0, bigBody.length());

                    super.contentBigView.setTextViewText(R.id.notification_fb_desc_expanded, bigBodySequence);
                }
                else if (body != null && !body.isEmpty())
                {
                    CharSequence bodySequence = body.subSequence(0, body.length());
                    super.contentBigView.setTextViewText(R.id.notification_fb_desc_expanded, bodySequence);
                }

                super.contentBigView.setTextViewText(R.id.notification_fb_time_expanded, timeSequence);

                super.notification.bigContentView = super.contentBigView;
            }
        } catch (Exception e) {
            Crashlytics.logException(e);

            e.printStackTrace();
        }
    }
}
