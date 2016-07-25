package com.collegedekho.app.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;

import com.android.volley.Request;
import com.collegedekho.app.R;
import com.collegedekho.app.activity.MainActivity;
import com.collegedekho.app.resource.Constants;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


/**
 * @author Suresh Kumar
 *         Created: 06/09/15
 */
public class ApplyIntentService extends IntentService {


    private static final String TAG = "ApplyIntentService";
    private String id;

    public ApplyIntentService() {
        super(TAG);

    }

    @Override
    protected void onHandleIntent(Intent intent) {

        SharedPreferences preferences = getSharedPreferences(getString(R.string.PREFS), Context.MODE_PRIVATE);
        String instituteId = preferences.getString(Constants.INSTITUTE_ID, null);
        if(instituteId != null) {
            Set<String> courseIdList = preferences.getStringSet(instituteId, new HashSet<String>());
            preferences.edit().putInt(Constants.KEY_APPLY_STATUS, Constants.APPLY_COMPLETE).apply();

            Iterator iterator = courseIdList.iterator();
            while (iterator.hasNext()) {
                String courseId = "" + iterator.next();
                applyCourse(courseId , instituteId);
            }
        }

    }

    private void applyCourse(String instituteCourseID, String instituteID)
    {
        HashMap<String, String> map = new HashMap<>();

        if (MainActivity.mProfile != null) {
            map.put(MainActivity.getResourceString(R.string.USER_NAME), MainActivity.mProfile.getName());
            map.put(MainActivity.getResourceString(R.string.USER_EMAIL), MainActivity.mProfile.getEmail());

            TelephonyManager tMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            String mPhoneNumber = tMgr.getLine1Number();
            if (mPhoneNumber != null) {
                map.put(MainActivity.getResourceString(R.string.USER_PHONE), mPhoneNumber);
            } else {
                if (MainActivity.mProfile != null) map.put(MainActivity.getResourceString(R.string.USER_PHONE), MainActivity.mProfile.getPhone_no());
            }
        }
        map.put(MainActivity.getResourceString(R.string.APPLY_COURSE),instituteCourseID);
        map.put(MainActivity.getResourceString(R.string.APPLY_INSTITUTE), "" + instituteID);

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        map.put(MainActivity.getResourceString(R.string.APPLY_YEAR), "" + year);

        String URL = Constants.BASE_URL + "lms/";
        MainActivity.mNetworkUtils.networkData(Constants.TAG_APPLIED_COURSE, URL, map, Request.Method.POST);
    }
}