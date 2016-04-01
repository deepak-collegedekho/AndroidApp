package com.collegedekho.app.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.collegedekho.app.R;
import com.collegedekho.app.activity.VideoPlayerActivity;
import com.collegedekho.app.resource.Constants;
import com.google.android.gms.gcm.GcmListenerService;

public class GCMIntentService extends GcmListenerService {


    public GCMIntentService() {
        super();
    }


    @Override
    public void onMessageReceived(String from, Bundle data) {
        sendNotification(NotificationID.getID(), data);
    }

    @Override
    public void onDeletedMessages() {
    }

    @Override
    public void onMessageSent(String msgId) {
    }

    @Override
    public void onSendError(String msgId, String error) {
    }

    private void sendNotification(int id,Bundle data) {
        String message=data.getString(Constants.MESSAGE);
        String collapseKey=data.getString(Constants.COLLAPSE_KEY);
        Intent intent = new Intent(this, VideoPlayerActivity.class);
        intent.putExtra("video_id","ZfIpYXpUpYc");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        int color = 0x6d6f71;
//        int color = 0x8cc63f;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, id /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_cap);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_cap)
                .setLargeIcon(largeIcon)
                .setColor(color)
                .setLights(0xFFFF0000, 1000, 1000)
                .setVibrate(new long[]{100, 100, 100,  100, 100, 300})
                .setContentTitle("CLD Notification Received")
                .setContentText(message)
                .setAutoCancel(true)
                .setPriority(Notification.PRIORITY_HIGH)
                .setSound(defaultSoundUri)
                .setGroup(collapseKey)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(id, notificationBuilder.build());
    }
}
