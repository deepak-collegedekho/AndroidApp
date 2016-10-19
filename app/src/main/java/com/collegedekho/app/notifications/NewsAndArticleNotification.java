package com.collegedekho.app.notifications;


import android.app.Notification;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.widget.RemoteViews;

import com.collegedekho.app.R;

import java.util.Map;

/**
 * Created by harshvardhan on 05/10/16.
 */

public class NewsAndArticleNotification extends CollegeDekhoNotifications {

    private static final String TAG = "NewsAndArticleNotification";

    @Override
    public void process(Map<String, String> messageDataMap, Context context) {

        super.process(messageDataMap, context);

        String bigImageURL = null;
        String title = "";
        String body = "";

        try {
            title = super.mNotificationPayload.getTitle();
            body = super.mNotificationPayload.getBody();

            // Inflate the notification_news layout as RemoteViews
            super.contentView = new RemoteViews(context.getPackageName(), R.layout.notification_news);

            if (title != null && !title.isEmpty())
            {
                super.contentView.setViewVisibility(R.id.notification_title, View.VISIBLE);

                CharSequence titleSequence = title.subSequence(0, title.length());
                super.contentView.setTextViewText(R.id.notification_title, titleSequence);

                if (Build.VERSION.SDK_INT >= 16 && titleSequence.length() > 50) {
                    super.contentView.setTextViewTextSize(R.id.notification_title, 1, 14.0f);
                }
            }
            else
            {
                return;
            }

            if (body != null && !body.isEmpty())
            {
                CharSequence bodySequence = body.subSequence(0, body.length());
            }

            long currentTimeMillis = System.currentTimeMillis();
            CharSequence timeSequence = this.getTimeFromMS(currentTimeMillis);
            super.contentView.setTextViewText(R.id.notification_time, timeSequence);

            super.notification.contentView = super.contentView;

            if (Build.VERSION.SDK_INT >= 16) {
                super.contentBigView = new RemoteViews(super.mContext.getPackageName(), R.layout.notification_news_expanded);

                String bigTitle = super.mNotificationPayload.getBig_title();

                if (bigTitle != null && !bigTitle.isEmpty())
                {
                    //if bit_title is there, show UI
                    CharSequence bigTitleSequence = bigTitle.subSequence(0, bigTitle.length() -1);

                    super.contentBigView.setTextViewText(R.id.expanded_notification_title, bigTitleSequence);
                }
                else
                    super.contentBigView.setTextViewText(R.id.expanded_notification_title, title);

                bigImageURL = super.mNotificationPayload.getBig_image();

                if (bigImageURL != null && !bigImageURL.isEmpty())
                    super.bigImageURLViewID = R.id.news_image;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}