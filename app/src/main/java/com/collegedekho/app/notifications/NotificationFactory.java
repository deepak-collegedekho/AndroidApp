package com.collegedekho.app.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.RemoteViews;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.NotificationPayload;
import com.collegedekho.app.resource.Constants;
import com.fasterxml.jackson.jr.ob.JSON;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;


/**
 * Created by harshvardhan on 05/10/16.
 */

public class NotificationFactory {

    private static final String TAG = "NotificationFactory";
    private RemoteViews contentBigView;
    private Notification notification;
    private NotificationPayload mNotificationPayload;
    private Context mContext;
    private CollegeDekhoNotifications mCollegeDekhoNotifications;

    public void renderNotification(Map<String, String> messageDataMap, Context context) {
        this.mContext = context;

        try {
            JSONObject jsonObject = new JSONObject(messageDataMap);

            this.mNotificationPayload = JSON.std.beanFrom(NotificationPayload.class, jsonObject.toString());

            switch (this.mNotificationPayload.getScreen())
            {
                case Constants.TAG_FRAGMENT_NEWS_LIST: {
                    this.mCollegeDekhoNotifications = new NewsAndArticleNotification();
                    break;
                }
                case Constants.TAG_FRAGMENT_ARTICLES_LIST: {
                    this.mCollegeDekhoNotifications = new NewsAndArticleNotification();
                    break;
                }
                case Constants.TAG_FRAGMENT_MY_FB_ENUMERATION: {
                    this.mCollegeDekhoNotifications = new MyFutureBuddyNotification();
                    break;
                }
                default:
                {

                }
            }

            this.mCollegeDekhoNotifications.process(messageDataMap, context);
            this.notification = this.mCollegeDekhoNotifications.getNotification();

            if (this.notification != null)
            {
                if (this.mCollegeDekhoNotifications.bigImageURLViewID >= 0)
                    new NotificationImageAsyncTask(this.mCollegeDekhoNotifications.contentBigView,
                            this.mCollegeDekhoNotifications.bigImageURLViewID, this.notification,
                            this.mContext,
                            Integer.parseInt(this.mNotificationPayload.getNotification_id()))
                            .execute(this.mCollegeDekhoNotifications.mNotificationPayload.getBig_image());

                else
                {
                    NotificationManager notificationManager = (NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(Integer.parseInt(this.mNotificationPayload.getNotification_id()), notification);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class NotificationImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        private static final String TAG = "NotificationImgAsyTsk";
        private RemoteViews bigContentView;
        private int imageViewID;
        private Notification notification;
        private int notificationID;
        private Context context;

        public NotificationImageAsyncTask(RemoteViews bigContentView, int imageViewID, Notification notification, Context context, int notificationID)
        {
            this.bigContentView = bigContentView;
            this.imageViewID = imageViewID;
            this.notification = notification;
            this.context = context;
            this.notificationID = notificationID;
        }

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

        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null)
                this.bigContentView.setImageViewBitmap(this.imageViewID, result);
            else
                this.bigContentView.setImageViewBitmap(this.imageViewID, BitmapFactory.decodeResource(this.context.getApplicationContext().getResources(), R.drawable.college_logo));

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                this.notification.bigContentView = this.bigContentView;

            NotificationManager notificationManager = (NotificationManager) this.context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(this.notificationID, this.notification);
        }
    }
}
