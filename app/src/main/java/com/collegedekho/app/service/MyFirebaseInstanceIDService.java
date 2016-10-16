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

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import android.util.Log;

import com.collegedekho.app.entities.Profile;
import com.fasterxml.jackson.jr.ob.JSON;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.IOException;


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }
    // [END refresh_token]

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        Log.d(TAG, "Firebase token: " + token);
        Context appContext = getApplicationContext();

        if (appContext != null)
        {
            SharedPreferences sharedPreferences = this.getSharedPreferences("sharedprefs", MODE_PRIVATE);

            if (sharedPreferences != null)
            {
                sharedPreferences.edit().putString("fcmToken", token).apply();

                try {
                    String userJsonString = sharedPreferences.getString("user_id", null);

                    if (userJsonString != null && !userJsonString.isEmpty())
                    {
                        Profile profile = JSON.std.beanFrom(Profile.class, userJsonString);

                        if (profile != null && !profile.getId().isEmpty())
                        {
                            String deviceId = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);

                            if (deviceId != null && !deviceId.isEmpty())
                            {
                                DeviceFcmRegistrationTask applyTask = new DeviceFcmRegistrationTask(token, profile.getToken(), deviceId);
                                applyTask.execute();
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
