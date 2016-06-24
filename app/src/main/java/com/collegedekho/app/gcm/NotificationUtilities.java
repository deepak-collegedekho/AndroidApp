package com.collegedekho.app.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.collegedekho.app.R;

import java.util.Random;

import static com.collegedekho.app.gcm.CommonUtilities.IS_REGISTERED;
import static com.collegedekho.app.gcm.CommonUtilities.SHARED_PREFS;
import static com.collegedekho.app.gcm.CommonUtilities.TAG;
import static com.collegedekho.app.gcm.CommonUtilities.displayMessage;

public final class NotificationUtilities {
	private static final int MAX_ATTEMPTS = 5;
    private static final int BACKOFF_MILLI_SECONDS = 2000;
    private static final Random random = new Random();
    public static final String REG_ID = "regId";
    private static final String APP_VERSION = "appVersion";

    /**
     * Register this account/device pair within the server.
     *
     */
    public static void register(final Context context, final String regId) {
        Log.i(TAG, "registering device (regId = " + regId + ")");
        long backoff = BACKOFF_MILLI_SECONDS + random.nextInt(1000);
        // Once GCM returns a registration id, we need to register on our server
        // As the server might be down, we will retry it a couple
        // times.
        for (int i = 1; i <= MAX_ATTEMPTS; i++) {
            Log.d(TAG, "Attempt #" + i + " to register");
            try {
                displayMessage(context, context.getString(
                        R.string.server_registering, i, MAX_ATTEMPTS));
                setRegistered(context, true);
                storeRegistrationId(context, regId);
                String message = context.getString(R.string.server_registered);
                CommonUtilities.displayMessage(context, message);
                return;
            } catch (Exception e) {
                // Here we are simplifying and retrying on any error; in a real
                // application, it should retry only on unrecoverable errors
                // (like HTTP error code 503).
                Log.e(TAG, "Failed to register on attempt " + i + ":" + e);
                if (i == MAX_ATTEMPTS) {
                    break;
                }
                try {
                    Log.d(TAG, "Sleeping for " + backoff + " ms before retry");
                    Thread.sleep(backoff);
                } catch (InterruptedException e1) {
                    // Activity finished before we complete - exit.
                    Log.d(TAG, "Thread interrupted: abort remaining retries!");
                    Thread.currentThread().interrupt();
                    return;
                }
                // increase backoff exponentially
                backoff *= 2;
            }
        }
        String message = context.getString(R.string.server_register_error,
                MAX_ATTEMPTS);
        CommonUtilities.displayMessage(context, message);
    }

    public static void storeRegistrationId(Context context, String regId) {
        final SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFS, Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.putInt(APP_VERSION, appVersion);
        editor.apply();
    }


    public static String getRegistrationId(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFS, Context.MODE_PRIVATE);
        String registrationId = prefs.getString(REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i(TAG, "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(TAG, "App version changed.");
//            return "";
        }
        return registrationId;
    }

    public static void setRegistered(Context context, boolean status) {
        final SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFS, Context.MODE_PRIVATE);
        int appVersion = getAppVersion(context);
        Log.i(TAG, "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(IS_REGISTERED, status);
        editor.putInt(APP_VERSION, appVersion);
        editor.apply();
    }


    public static boolean isRegistered(Context context) {
        final SharedPreferences prefs = context.getSharedPreferences(
                SHARED_PREFS, Context.MODE_PRIVATE);
        return prefs.getBoolean(IS_REGISTERED, false);
    }

    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("RegisterActivity",
                    "I never expected this! Going down, going down!" + e);
            throw new RuntimeException(e);
        }
    }

}
