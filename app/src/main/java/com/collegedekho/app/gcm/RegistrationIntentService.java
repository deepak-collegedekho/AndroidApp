package com.collegedekho.app.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import static com.collegedekho.app.gcm.CommonUtilities.SENDER_ID;

/**
 * Created by Bashiruddin on 03-Sep-15.
 */
public class RegistrationIntentService extends IntentService {

    private static final String TAG = "DEBUG";
    private static final String[] TOPICS = {"global"};

    public RegistrationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(SENDER_ID, GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            // [END get_token]
            Log.i(TAG, "GCM Registration Token: " + token);
            if(!NotificationUtilities.isRegistered(getApplicationContext())) {
                sendRegistrationToServer(token);
                // Subscribe to topic channels
                subscribeTopics(token);
            }else {
                String oldRegId= NotificationUtilities.getRegistrationId(this);
                if (oldRegId!=null || !oldRegId.matches(token)){
                    sendRegistrationToServer(token);
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            NotificationUtilities.setRegistered(getApplicationContext(), false);
        }
    }

    /**
     * Persist registration to third-party servers.
     *
     * Modify this method to associate the user's GCM registration token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        NotificationUtilities.register(getApplicationContext(), token);
    }

    /**
     * Subscribe to any GCM topics of interest, as defined by the TOPICS constant.
     *
     * @param token GCM token
     * @throws IOException if unable to reach the GCM PubSub service
     */
    // [START subscribe_topics]
    private void subscribeTopics(String token) throws IOException {
        for (String topic : TOPICS) {
            GcmPubSub pubSub = GcmPubSub.getInstance(this);
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }
}
