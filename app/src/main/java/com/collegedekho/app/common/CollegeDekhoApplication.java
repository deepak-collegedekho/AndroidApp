package com.collegedekho.app.common;

import android.content.Intent;
import android.support.multidex.MultiDexApplication;

import com.collegedekho.app.entities.Profile;
import com.collegedekho.app.resource.Constants;
import com.collegedekho.app.service.OtpService;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.answers.Answers;

import io.fabric.sdk.android.Fabric;

/**
 * Created by sureshsaini on 20/2/17.
 */

public class CollegeDekhoApplication extends MultiDexApplication {

    public CollegeDekhoApplication(){}

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Fabric.with(this, new Answers());

        Profile profile = AppUser.getInstance(getApplicationContext()).getUser();
        if (profile == null || profile.getIs_verified() != Constants.PHONE_VERIFIED) {
            //Intent intent = new Intent(this, OtpService.class);
            //startService(intent);
        }
    }


}
