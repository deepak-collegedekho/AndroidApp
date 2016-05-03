package com.collegedekho.app.utils;

import android.content.Context;
import android.net.Uri;

import com.appsflyer.AppsFlyerLib;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import org.apache.tools.ant.Main;

import java.util.HashMap;
import java.util.Map;

import io.connecto.android.sdk.Properties;

/**
 * Utility class.
 */
public class AnalyticsUtils {
    private AnalyticsUtils() {
        // private constructor.
    }

    /**
     * Push an "openScreen" event with the given screen name. Tags that match that event will fire.
     */
    public static void pushOpenScreenEvent(Context context, String screenName) {
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("openScreen", DataLayer.mapOf("screenName", screenName));
    }

    /**
     * Push a "closeScreen" event with the given screen name. Tags that match that event will fire.
     */
    public static void pushCloseScreenEvent(Context context, String screenName) {
        DataLayer dataLayer = TagManager.getInstance(context).getDataLayer();
        dataLayer.pushEvent("closeScreen", DataLayer.mapOf("screenName", screenName));
    }

    public static void AppIndexingView(String objectName, Uri objectID, Uri objectAppURI, MainActivity context, boolean indexingStarting)
    {
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW,
                objectName,
                objectID,
                objectAppURI
        );

        if (indexingStarting)
            AppIndex.AppIndexApi.start(context.client, viewAction);
        else
            AppIndex.AppIndexApi.end(context.client, viewAction);
    }

    public static void SendAppEvent(String eventCategory, String eventName, Map<String, Object> eventParams, Context context)
    {
        if (MainActivity.user != null)
            eventParams.put(context.getString(R.string.KEY_USER), MainActivity.user.getId());

        Properties properties = new Properties();
        String[] labels = new String[eventParams.size()];
        int index = 0;

        for (Map.Entry<String,Object> entry : eventParams.entrySet())
        {
            properties.putValue(entry.getKey(), entry.getValue());

            labels[index] = entry.getKey() + " : " + entry.getValue().toString();
            index++;
        }

        //GA Events
        if (MainActivity.tracker != null) {
            HitBuilders.EventBuilder eventBuilder = new HitBuilders.EventBuilder();
            eventBuilder.setCategory(eventCategory);
            eventBuilder.setAction(eventName);

            for (String label : labels)
                eventBuilder.setLabel(label);

            MainActivity.tracker.send(eventBuilder.build());
        }

        //AppsFlyer Events
        AppsFlyerLib.getInstance().trackEvent(context, eventName, eventParams);

        //Connecto Events
        MainActivity.connecto.track(eventName, properties);
    }
}
