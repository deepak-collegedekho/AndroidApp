package com.collegedekho.app.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.collegedekho.app.R;
import com.collegedekho.app.entities.Profile;
import com.fasterxml.jackson.jr.ob.JSON;

import java.io.IOException;

import static android.content.Context.MODE_PRIVATE;
import static com.collegedekho.app.R.string.KEY_TOKEN;

/**
 * Created by sureshsaini on 20/2/17.
 */

public class AppUser {

    private static AppUser sInstance;
    private Profile mProfile;
    private final Context mContext;
    private final SharedPreferences mSharedPref;

    public AppUser(Context context) {
        this.mContext = context;
        this.mSharedPref = context.getSharedPreferences(context.getString(R.string.PREFS), MODE_PRIVATE);
    }

    public static AppUser getInstance(Context context) {
        synchronized (AppUser.class) {
            if (sInstance == null)
                sInstance = new AppUser(context);
        }
        return sInstance;
    }

    private Profile getUserSession() {
            String json = mSharedPref.getString(mContext.getString(R.string.KEY_USER), null);
        if(json != null) {
            try {
                return mProfile = JSON.std.beanFrom(Profile.class, json);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void setUserStateSession(Profile userProfile) {
        Log.d("UserStateCalled","called "+userProfile.getToken());
        if(userProfile == null)
            return;
        try {
            String u = JSON.std.asString(userProfile);
            Log.d("UserProfile"," "+u);

            this.mSharedPref.edit().putString(mContext.getString(KEY_TOKEN),userProfile.getToken()).apply();
            this.mSharedPref.edit().putString(mContext.getString(R.string.KEY_USER), u).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.mProfile = userProfile;
    }

    public Profile getUser() {
        if (mProfile == null) {
            mProfile = getUserSession();
        }
        return mProfile;
    }
}

