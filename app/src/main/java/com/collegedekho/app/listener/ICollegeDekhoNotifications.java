package com.collegedekho.app.listener;

import android.app.Notification;
import android.content.Context;

import org.json.JSONObject;

import java.util.Map;

/**
 * Created by harshvardhan on 13/10/16.
 */
public interface ICollegeDekhoNotifications {

    void process(Map<String, String> messageDataMap, Context context);

    Notification getNotification();
}
