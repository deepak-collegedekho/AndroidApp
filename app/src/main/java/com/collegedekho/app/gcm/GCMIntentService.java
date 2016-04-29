package com.collegedekho.app.gcm;

import android.os.Bundle;

import com.google.android.gms.gcm.GcmListenerService;

public class GCMIntentService extends GcmListenerService {


    public GCMIntentService() {
        super();
    }


    @Override
    public void onMessageReceived(String from, Bundle data) {

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

}
