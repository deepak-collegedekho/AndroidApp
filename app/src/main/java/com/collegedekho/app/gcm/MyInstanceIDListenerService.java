package com.collegedekho.app.gcm;
import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by Bashiruddin on 03-Sep-15.
 */


public class MyInstanceIDListenerService extends InstanceIDListenerService {

    private static final String TAG = "DEBUG";

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. This ic_call_vector is initiated by the
     * InstanceID provider.
     */
    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Fetch updated Instance ID token and notify our app's server of any changes (if applicable).
        Intent intent = new Intent(this, RegistrationIntentService.class);
        startService(intent);
    }
    // [END refresh_token]
}

