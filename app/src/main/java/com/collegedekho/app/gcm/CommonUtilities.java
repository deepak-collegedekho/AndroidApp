package com.collegedekho.app.gcm;

import android.content.Context;
import android.content.Intent;

public final class CommonUtilities {
	
    public static final String SHARED_PREFS = "push_notification_prefs";
    public static final String IS_REGISTERED = "is_registered";

    // Google project id
    public static final String SENDER_ID = "864760274938";

    /**
     * Tag used on log messages.
     */
    static final String TAG = "GCM";

    static final String DISPLAY_MESSAGE_ACTION =
            "com.np.gpt.DISPLAY_MESSAGE";

    static final String EXTRA_MESSAGE = "message";

    /**
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}
