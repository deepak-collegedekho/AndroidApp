package com.collegedekho.app.utils;

import android.content.Context;
import android.net.Uri;

import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.tagmanager.DataLayer;
import com.google.android.gms.tagmanager.TagManager;

import java.net.URI;

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
}
